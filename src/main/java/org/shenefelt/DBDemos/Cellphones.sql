USE Inventory;

INSERT INTO CellPhone VALUES(default, 5033301866, 'Verizon', 1,1);
INSERT INTO CellPhone VALUES(default, 5033301867, 'Verizon', 2,16);

# GET ALL CELL PHONES ASSIGNED TO USERS BY NAME
SELECT Users.full_name, CP.phone_number, C.company_name from Users INNER JOIN Inventory.CellPhone CP on Users.ID = CP.assigned_user
INNER JOIN Inventory.Company C on CP.company_id = C.ID
