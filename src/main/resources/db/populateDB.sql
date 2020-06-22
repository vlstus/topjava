DELETE
FROM user_meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO user_meals(user_id, description, calories, datetime)
VALUES (100000, 'Meal1', 200, '2019-05-08 12:51:14'),
       (100000, 'Meal2', 400, '2019-08-12 06:51:14'),
       (100001, 'Meal3', 500, '2019-02-18 06:51:14'),
       (100001, 'Meal4', 760, '2019-04-20 14:51:14'),
       (100000, 'Meal5', 760, '2019-04-20 14:51:14');
