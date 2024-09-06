USE TEST;

# Fetching Computers assigned to users
SELECT
    U.full_name AS 'User',
    'Computer' AS 'Device Type',
    C.MAKE AS 'Make',
    C.MODEL AS 'Model',
    C.TYPE AS 'Type',
    C.serial_number AS 'Serial No.'
FROM Users U
         INNER JOIN Computers C ON U.ID = C.assigned_user;

# Fetching Tablets assigned to users
SELECT
    U.full_name AS 'User',
    'Tablet' AS 'Device Type',
    NULL AS 'Make',
    NULL AS 'Model',
    NULL AS 'Type',
    NULL AS 'Serial No.'
FROM Users U
         INNER JOIN Tablets T ON U.ID = T.assigned_user;

# Fetching CellPhones assigned to users
SELECT
    U.full_name AS 'User',
    'CellPhone' AS 'Device Type',
    NULL AS 'Make',
    NULL AS 'Model',
    NULL AS 'Type',
    CP.phone_number AS 'Serial No.'
FROM Users U
         INNER JOIN CellPhone CP ON U.ID = CP.assigned_user;

# Fetching Printers assigned to users
SELECT
    U.full_name AS 'User',
    'Printer' AS 'Device Type',
    NULL AS 'Make',
    NULL AS 'Model',
    NULL AS 'Type',
    NULL AS 'Serial No.'
FROM Users U
         INNER JOIN Printers P ON U.ID = P.assigned_user;

# Fetch all assignments in one query using UNION
SELECT
    U.full_name AS 'User',
    'Computer' AS 'Device Type',
    C.MAKE AS 'Make',
    C.MODEL AS 'Model',
    C.TYPE AS 'Type',
    C.serial_number AS 'Serial No.'
FROM Users U
         INNER JOIN Computers C ON U.ID = C.assigned_user
UNION ALL
SELECT
    U.full_name AS 'User',
    'Tablet' AS 'Device Type',
    NULL AS 'Make',
    NULL AS 'Model',
    NULL AS 'Type',
    NULL AS 'Serial No.'
FROM Users U
         INNER JOIN Tablets T ON U.ID = T.assigned_user
UNION ALL
SELECT
    U.full_name AS 'User',
    'CellPhone' AS 'Device Type',
    NULL AS 'Make',
    NULL AS 'Model',
    NULL AS 'Type',
    CP.phone_number AS 'Serial No.'
FROM Users U
         INNER JOIN CellPhone CP ON U.ID = CP.assigned_user
UNION ALL
SELECT
    U.full_name AS 'User',
    'Printer' AS 'Device Type',
    NULL AS 'Make',
    NULL AS 'Model',
    NULL AS 'Type',
    NULL AS 'Serial No.'
FROM Users U
         INNER JOIN Printers P ON U.ID = P.assigned_user;

# Using UserItems if it comprehensively relates all
SELECT
    U.full_name AS 'User',
    'Computer' AS 'Device Type',
    C.MAKE AS 'Make',
    C.MODEL AS 'Model',
    C.TYPE AS 'Type',
    C.serial_number AS 'Serial No.'
FROM Users U
         INNER JOIN UserItems UI ON U.ID = UI.user_id
         INNER JOIN Computers C ON UI.computer_id = C.ID
UNION ALL
SELECT
    U.full_name AS 'User',
    'CellPhone' AS 'Device Type',
    NULL AS 'Make',
    NULL AS 'Model',
    NULL AS 'Type',
    CP.phone_number AS 'Serial No.'
FROM Users U
         INNER JOIN UserItems UI ON U.ID = UI.user_id
         INNER JOIN CellPhone CP ON UI.phone_id = CP.ID;




USE TEST;

DROP TABLE IF EXISTS ActiveUsers;
DROP TABLE IF EXISTS Users;


# Create tables
CREATE TABLE Users (
                       ID INT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       full_name VARCHAR(101),
                       company_id INT,
                       job_role VARCHAR(50),
                       username VARCHAR(50),
                       email VARCHAR(100)
);

CREATE TABLE ActiveUsers (
                             user_id INT,
                             hire_date DATE
);

DELIMITER //

# Trigger to generate full name
CREATE TRIGGER before_insert_Users_make_full_name
    BEFORE INSERT ON Users
    FOR EACH ROW
BEGIN
    SET NEW.full_name = CONCAT(NEW.first_name, ' ', NEW.last_name);
END//

# Trigger to set username based on the first letter of first_name and the last_name
CREATE TRIGGER before_insert_Users_make_username
    BEFORE INSERT ON Users
    FOR EACH ROW
BEGIN
    IF NEW.username IS NULL OR NEW.username = '' THEN
        SET NEW.username = CONCAT(LOWER(LEFT(NEW.first_name, 1)), '.', LOWER(NEW.last_name));
    END IF;
END//

# Trigger to set email based on the username
CREATE TRIGGER before_insert_Users_make_email_c
    BEFORE INSERT ON Users
    FOR EACH ROW
BEGIN
    IF NEW.email IS NULL OR NEW.email = '' THEN
        SET NEW.email = CONCAT(NEW.username, '@gregoryshenefelt.com');
    END IF;
END//

# Trigger to add users to ActiveUsers by default
CREATE TRIGGER after_insert_add_to_active
    AFTER INSERT ON Users
    FOR EACH ROW
BEGIN
    INSERT INTO ActiveUsers (user_id, hire_date)
    VALUES (NEW.ID, CURDATE());
END//

DELIMITER ;

# Example INSERT statements to test the triggers (full_name, email, and username will be populated by triggers)
INSERT INTO Users (first_name, last_name, company_id, job_role)
VALUES ('Temp', 'TESTER', 1, 'owner');

INSERT INTO Users (first_name, last_name, company_id, job_role)
VALUES ('John', 'Doe', 1, 'Developer');

# Validate the Users table structure after adding data
DESCRIBE Users;
