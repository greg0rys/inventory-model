USE Inventory;

DROP TRIGGER IF EXISTS Inventory.before_insert_ActiveUsers;
DROP TRIGGER IF EXISTS Inventory.before_insert_TerminatedUsers;


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
CREATE TRIGGER before_insert_TerminatedUsers
    BEFORE INSERT ON TerminatedUsers
    FOR EACH ROW
BEGIN
    SET NEW.termination_date = CURDATE();
END//

DELIMITER ;


SELECT * FROM TerminatedUsers;

SHOW TRIGGERS IN Inventory;