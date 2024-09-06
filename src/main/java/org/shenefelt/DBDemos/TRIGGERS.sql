USE TEST;

DROP TRIGGER IF EXISTS Inventory.before_insert_ActiveUsers;
DROP TRIGGER IF EXISTS Inventory.before_insert_TerminatedUsers;
DROP TRIGGER IF EXISTS Users.before_insert_Users_make_email_c;
DROP TRIGGER IF EXISTS Users.before_insert_Users_make_username;
DROP TRIGGER IF EXISTS Users.after_insert_add_to_active;
DROP TRIGGER IF EXISTS Users.before_insert_Users_make_full_name;



DELIMITER //
# add the current date as the hire date when hiring
CREATE TRIGGER before_insert_ActiveUsers
    BEFORE INSERT ON ActiveUsers
    FOR EACH ROW
BEGIN
    SET NEW.hire_date = CURDATE();
END//

DELIMITER ;




DELIMITER //

# add the current date as a termination date when terminating.
# CREATE TRIGGER before_insert_TerminatedUsers
#     BEFORE INSERT ON TerminatedUsers
#     FOR EACH ROW
# BEGIN
#    #SET NEW.termination_date = CURDATE();
# END//
#
# DELIMITER ;


DELIMITER //
# create the users username
CREATE TRIGGER before_insert_Users_make_username
    AFTER INSERT ON Users
    FOR EACH ROW
BEGIN
    SET username = CONCAT(LOWER(LEFT(first_name, 1)), '.', LOWER(last_name));
END//

DELIMITER ;


DELIMITER //
# create the users email
CREATE TRIGGER before_insert_Users_make_email_c
    AFTER INSERT ON Users
    FOR EACH ROW
BEGIN
    SET email = CONCAT(LOWER(username), '@gregoryshenefelt.com');
END//

DELIMITER ;

DELIMITER //
# generate full name
CREATE TRIGGER before_insert_Users_make_full_name
    AFTER INSERT ON Users
    FOR EACH ROW
BEGIN
    SET full_name = CONCAT(first_name, ' ', last_name);
END//

DELIMITER ;


DELIMITER //
# add the users to active users by default
CREATE TRIGGER after_insert_add_to_active
    AFTER INSERT ON Users
    FOR EACH ROW
BEGIN
    INSERT INTO ActiveUsers (user_id)
    VALUES (NEW.id);
END//

DELIMITER ;



SHOW TRIGGERS IN TEST;


INSERT INTO Users(first_name, last_name, company_id,job_role,hire_status)
VALUES('Temp', 'TESTER', 2, 'owner', 1)

INSERT INTO Users (first_name, last_name, company_id, job_role, hire_status, username)
VALUES ('f', 'p', 2, 'owner', 1, CONCAT(LOWER(LEFT(first_name,1)), CONCAT(last_name)));

DELIMITER //

-- This trigger sets the username and email before inserting the record into the Users table
CREATE TRIGGER before_insert_Users_set_username_email
    BEFORE INSERT ON Users
    FOR EACH ROW
BEGIN
    -- Set the username based on the first letter of first_name and the last_name
    SET NEW.username = CONCAT(LOWER(LEFT(NEW.first_name, 1)), '.', LOWER(NEW.last_name));

    -- Set the email based on the username
    SET NEW.email = CONCAT(NEW.username, '@example.com');
END//

DELIMITER ;



DESCRIBE Users;

DELIMITER //

CREATE TRIGGER before_insert_Users_set_username_email_9
    BEFORE INSERT ON Users
    FOR EACH ROW
BEGIN
    -- Check if username is not provided, then set it
    IF NEW.username IS NULL OR NEW.username = '' THEN
        SET NEW.username = CONCAT(LOWER(LEFT(NEW.first_name, 1)), '.', LOWER(NEW.last_name));
    END IF;

    -- Set the email based on the username
    SET NEW.email = CONCAT(NEW.username, '@example.com');
END//

DELIMITER ;

