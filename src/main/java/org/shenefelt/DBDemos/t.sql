USE TEST;

# Drop existing triggers if they exist
DROP TRIGGER IF EXISTS Inventory.before_insert_ActiveUsers;
DROP TRIGGER IF EXISTS Inventory.before_insert_TerminatedUsers;
DROP TRIGGER IF EXISTS Inventory.before_insert_Users_make_email_c;
DROP TRIGGER IF EXISTS Inventory.before_insert_Users_make_username;
DROP TRIGGER IF EXISTS Inventory.after_insert_add_to_active;
DROP TRIGGER IF EXISTS Inventory.before_insert_Users_make_full_name;

DELIMITER //

# Trigger to add the current date as the hire date when inserting ActiveUsers
CREATE TRIGGER before_insert_ActiveUsers
    BEFORE INSERT ON ActiveUsers
    FOR EACH ROW
BEGIN
    SET NEW.hire_date = CURDATE();
END//

# Uncomment and correct this section if TerminatedUsers table is needed
# Trigger to add the current date as the termination date when inserting TerminatedUsers
# CREATE TRIGGER before_insert_TerminatedUsers
#     BEFORE INSERT ON TerminatedUsers
#     FOR EACH ROW
# BEGIN
#     SET NEW.termination_date = CURDATE();
# END//

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

# Trigger to generate full name
CREATE TRIGGER before_insert_Users_make_full_name
    BEFORE INSERT ON Users
    FOR EACH ROW
BEGIN
    SET NEW.full_name = CONCAT(NEW.first_name, ' ', NEW.last_name);
END//

# Trigger to add users to ActiveUsers by default
CREATE TRIGGER after_insert_add_to_active
    AFTER INSERT ON Users
    FOR EACH ROW
BEGIN
    INSERT INTO ActiveUsers (user_id)
    VALUES (NEW.ID);
END//

DELIMITER ;

# Show triggers in the 'Inventory' database (optional to verify triggers have been created)
SHOW TRIGGERS IN TEST;

-- Example INSERT statements to test the triggers correctly
-- The full_name, email, and username fields will be populated by the triggers.

-- Insert statement that relies on triggers to fill in default values for full_name, username, and email
USE rtnevn;
INSERT INTO Users (first_name, last_name, company_id, job_role)
VALUES ('Temp', 'TESTER', 1, 'owner');

INSERT INTO Users (first_name, last_name, company_id, job_role)
VALUES ('John', 'Doe', 1, 'Developer');

INSERT INTO Users (first_name, last_name, company_id, job_role)
VALUES ('John', 'Doe', 1, 'Developer');

# Validate the Users table structure after adding data
DESCRIBE Users;


SHOW TRIGGERS IN TEST;
DELIMITER //

# Corrected and combined trigger to set username and email before inserting into the Users table
CREATE TRIGGER before_insert_Users_set_username_email
    BEFORE INSERT ON Users
    FOR EACH ROW
BEGIN
    # Set the username based on the first letter of first_name and the last_name if not provided
    IF NEW.username IS NULL OR NEW.username = '' THEN
        SET NEW.username = CONCAT(LOWER(LEFT(NEW.first_name, 1)), '.', LOWER(NEW.last_name));
    END IF;

    # Set the email based on the username if not provided
    IF NEW.email IS NULL OR NEW.email = '' THEN
        SET NEW.email = CONCAT(NEW.username, '@example.com');
    END IF;
END//

DELIMITER ;

