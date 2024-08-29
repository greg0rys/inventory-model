USE Inventory;

SELECT Computers.make,
       Computers.model,
       Computers.type,
       Computers.serial_number,
       Users.full_name,
       Company.location_alias

from Computers
         INNER JOIN Company ON Computers.company_id = Company.ID
         INNER JOIN Users ON Users.ID = Computers.assigned_user;


UPDATE Users
SET full_name = CONCAT(first_name, ' ', last_name)
WHERE id IN (1, 2, 3, 4);

UPDATE Computers
SET assigned_user = 4
WHERE assigned_user = 3;


SELECT CONCAT(first_name, ' ', last_name) as Name,
       Company.location_alias as Location
FROM Users
         INNER JOIN Company ON Users.company_id = Company.ID;

SELECT make from Computers where id = 1


