package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    /*
    Mock data
    * */ {
        List.of(
                new User(null, "1", "email1", "password1", 2000, true, Set.of(Role.ADMIN)),
                new User(null, "2", "email2", "password2", 1500, true, Set.of(Role.USER)))
                .forEach(this::save);

    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(user.getId(), (id, oldMeal) -> user);
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        return List.copyOf(repository.values());
    }

    @Override
    public User getByEmail(String email) {
        return repository.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny()
                .orElse(null);
    }
}
