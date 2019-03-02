DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin'),
  ('Guest', 'guest@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_GUEST', 100002);

INSERT INTO meals (user_id, description, calories) VALUES
  (100000, 'Lunch', 3000),
  (100002, 'Dinner', 1500),
  (100001, 'Breakfast', 2000);
