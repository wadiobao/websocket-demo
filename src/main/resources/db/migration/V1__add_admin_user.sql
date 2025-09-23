-- This migration creates the default admin user with a pre-hashed password.
-- The password is '123456' encrypted using BCrypt.

-- Insert into the base user table
INSERT INTO user (email, password, name, address, phone, status) VALUES ('admin@gmail.com', '{bcrypt}$2a$10$GRLdNijSQe8WqK5TwJ22U.zS5.gCpeQxMIa71Myvcm234vI/9Lg42', 'Administrator', 'System Address', '0999999999', 'ACTIVE');

-- Get the ID of the inserted user
SET @last_user_id = LAST_INSERT_ID();

-- Insert into the librarian table to link it
INSERT INTO librarian (id) VALUES (@last_user_id);
