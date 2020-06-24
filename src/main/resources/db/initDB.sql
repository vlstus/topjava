DROP TABLE IF EXISTS user_meals;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;
DROP INDEX IF EXISTS user_meals_idx;
DROP INDEX IF EXISTS users_unique_email_idx;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                                         NOT NULL,
    email            VARCHAR                                         NOT NULL,
    password         VARCHAR                                         NOT NULL,
    registered       TIMESTAMP           DEFAULT now()::timestamp(0) NOT NULL,
    enabled          BOOL                DEFAULT TRUE                NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000                NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE user_meals
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id     INTEGER     NOT NULL,
    description VARCHAR     NOT NULL,
    calories    INTEGER     NOT NULL,
    dateTime    TIMESTAMPTZ NOT NULL,
    CONSTRAINT user_meals_idx UNIQUE (user_id, dateTime),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX user_meals_unique_datetime_idx ON user_meals (user_id, dateTime);