package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final ResultSetExtractor<List<User>> userWithRolesExtractor = resultSet -> {
        Map<Integer, User> userMap = new HashMap<>();
        while (resultSet.next()) {
            int userId = resultSet.getInt("user_id");
            User user = userMap.get(userId);
            if (user == null) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Date registered = Date.from(Timestamp.valueOf(resultSet.getString("registered")).toInstant());
                boolean enabled = resultSet.getBoolean("enabled");
                int caloriesPerDay = resultSet.getInt("calories_per_day");
                user = new User(userId, name, email, password, caloriesPerDay, enabled, registered, new HashSet<>());
            }
            Role role = Role.valueOf(resultSet.getString("role"));
            user.getRoles().add(role);
            userMap.put(userId, user);
        }
        return new ArrayList<>(userMap.values());
    };

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        validateConstraints(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            namedParameterJdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id,role) VALUES (:id,:role)"
                    , getRolesParameterSource(user));
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        } else {
            namedParameterJdbcTemplate.update("DELETE FROM user_roles WHERE user_id=:id", parameterSource);
            namedParameterJdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id,role) VALUES (:id,:role)"
                    , getRolesParameterSource(user));
//            namedParameterJdbcTemplate.batchUpdate("UPDATE user_roles SET role = :role WHERE user_id = :id",
//                    getRolesParameterSource(user));
        }
        return user;
    }

    private SqlParameterSource[] getRolesParameterSource(User user) {
        ArrayList<Role> roles;
        roles = new ArrayList<>(user.getRoles());
        SqlParameterSource[] sqlParameterSource = new MapSqlParameterSource[roles.size()];
        for (int i = 0; i < sqlParameterSource.length; i++) {
            sqlParameterSource[i] = new MapSqlParameterSource()
                    .addValue("id", user.getId())
                    .addValue("role", roles.get(i).toString());
        }
        return sqlParameterSource;
    }


    @Override
    @Transactional
    /*
    Roles will be removed by DB cascade
    * */
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id WHERE users.id = ?", userWithRolesExtractor, id);
        return DataAccessUtils.singleResult(users);
    }

    @SuppressWarnings("ConstantConditions")
    /*
    DB cannot return user without ID
    */
    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        List<Role> roles = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_roles.user_id = ?",
                new ResultSetExtractor<List<Role>>() {

            @Override
            public List<Role> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<Role> roles = new ArrayList<>();
                while (rs.next()) {
                    roles.add(Role.valueOf(rs.getString("role")));
                }
                return roles;
            }
        }, user.getId());
        user.setRoles(roles);
        return user;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query
                ("SELECT * FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id ORDER BY name,email",
                        userWithRolesExtractor);
//        return jdbcTemplate.query
//                ("SELECT id, name, email, password, registered, enabled, calories_per_day, string_agg(role, ', ') AS roles FROM users LEFT JOIN user_roles ON users.id=user_roles.user_id GROUP BY users.id ORDER BY name, email",
//                        ROW_MAPPER);
    }

    private void validateConstraints(User user) {
        Set<ConstraintViolation<User>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
