DROP DATABASE IF EXISTS hibernatetest;
DROP USER IF EXISTS `testadmin`@`localhost`;
DROP USER IF EXISTS `testuser`@`localhost`;
CREATE DATABASE IF NOT EXISTS hibernatetest CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `testadmin`@`localhost` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT ALL PRIVILEGES ON `hibernatetest`.* TO `testadmin`@`localhost`;
CREATE USER IF NOT EXISTS `testuser`@`localhost` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `hibernatetest`.* TO `testuser`@`localhost`;
FLUSH PRIVILEGES;


