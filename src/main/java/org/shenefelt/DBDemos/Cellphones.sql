USE Inventory;

INSERT INTO CellPhone VALUES(default, 5033301866, 'Verizon', 1,1);
INSERT INTO CellPhone VALUES(default, 5033301867, 'Verizon', 2,16);

# GET ALL CELL PHONES ASSIGNED TO USERS BY NAME
SELECT Users.full_name, CP.phone_number, C.company_name from Users INNER JOIN Inventory.CellPhone CP on Users.ID = CP.assigned_user
INNER JOIN Inventory.Company C on CP.company_id = C.ID;


ALTER TABLE Users ADD
    COLUMN username
    VARCHAR(255);

UPDATE Users SET username = CONCAT(LEFT(LOWER(first_name), 1), '.', LOWER(last_name));

ALTER TABLE Users ADD
    COLUMN email VARCHAR(255);

ALTER TABLE Users ADD CONSTRAINT unique_email UNIQUE (email);
UPDATE Users SET email = CONCAT(username, '@gregoryshenefelt.com');
SELECT full_name, username, email, C.company_name, hire_status FROM Users INNER JOIN Inventory.Company C on Users.company_id = C.ID;

ALTER TABLE Users ADD COLUMN hire_status int;

ALTER TABLE Users ADD CONSTRAINT ensure_hire_status_is_1_or_0 CHECK(Users.hire_status IN(1,2));
UPDATE Users SET Users.hire_status = 1;


SELECT * FROM Users;


# Inventory Item setup
# uses fks that that identify which item an employee entered, what the item is.



# Price Table
# stores price of items