-- Insert roles
INSERT INTO roles (role_id, role_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (role_id, role_name) VALUES (2, 'ROLE_USER');

-- Insert users
INSERT INTO users (user_id, username, email, password, full_name, phone_number, created_at, updated_at)
VALUES (1, 'admin', 'admin@gmail.com', '$2y$10$mnzNEItO4CsWuyR6iH/r.eHrlbgK67Ith2WF9UtBQXFSzNHFPQgli', 'Dao Huy Hoang', '0123456789', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (user_id, username, email, password, full_name, phone_number, created_at, updated_at)
VALUES (2, 'user', 'user1@gmail.com', '$2y$10$RuAs7hgVw92KT0jGKcDRCuiqdTlYvagmvzUElFU8BOp1KIOMMxo4m', 'Thanh Nguyet', '0987654321', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (user_id, username, email, password, full_name, phone_number, created_at, updated_at)
VALUES (3, 'user2', 'user2@gmail.com', '$2y$10$RuAs7hgVw92KT0jGKcDRCuiqdTlYvagmvzUElFU8BOp1KIOMMxo4m', 'User Two', '0912345678', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert user_roles mapping
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- Admin user with ROLE_ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- User1 with ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2); -- User2 with ROLE_USER
