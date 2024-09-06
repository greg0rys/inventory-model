USE TEST;
# SEQUENTIAL EXECUTION

# this file has been written in sequential order, do not rearrange these statements

# Drop the DBS if they exist
# WARNING ONLY EXE IF YOU ARE STARTING FRESH

DROP TABLE IF EXISTS UserItems;
DROP TABLE IF EXISTS Printers;
DROP TABLE IF EXISTS CellPhone;
DROP TABLE IF EXISTS Tablets;
DROP TABLE IF EXISTS Computers;
DROP TABLE IF EXISTS ActiveUsers;
DROP TABLE IF EXISTS Users; # has to be dropped here as it has the fk requirement of company
DROP TABLE IF EXISTS Company;

# Company Objects
CREATE TABLE Company(
                        ID INT PRIMARY KEY AUTO_INCREMENT,
                        company_name TEXT NOT NULL,
                        company_location TEXT NOT NULL,
                        location_alias TEXT NOT NULL
);

# User Objects
CREATE TABLE Users(
                      ID INT PRIMARY KEY AUTO_INCREMENT,
                      first_name TEXT NOT NULL,
                      last_name TEXT NOT NULL,
                      full_name VARCHAR(255),
                      company_id INT NOT NULL,
                      job_role TEXT NOT NULL,
                      email TEXT NOT NULL,
                      username TEXT NOT NULL,
                      FOREIGN KEY (company_id) REFERENCES Company(ID) ON DELETE CASCADE,
                      CONSTRAINT unique_email UNIQUE(email),
                      CONSTRAINT unique_username UNIQUE(username)
);

CREATE TABLE ActiveUsers(
                            ID INT PRIMARY KEY AUTO_INCREMENT,
                            user_id INT NOT NULL,
                            hire_date DATE NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES Users(ID) ON DELETE CASCADE
);

# Inventory objects
CREATE TABLE Computers(
                          ID INT PRIMARY KEY AUTO_INCREMENT,
                          MAKE TEXT NOT NULL,
                          MODEL TEXT NOT NULL,
                          TYPE TEXT NOT NULL,
                          AVAILABILITY TEXT NOT NULL,
                          serial_number INT NOT NULL,
                          company_id INT NOT NULL,
                          assigned_user INT NULL DEFAULT 1,
                          FOREIGN KEY (company_id) REFERENCES Company(ID) ON DELETE CASCADE,
                          FOREIGN KEY (assigned_user) REFERENCES Users(ID) ON DELETE SET NULL,
                          CONSTRAINT unique_serial_number UNIQUE (serial_number)
);

CREATE TABLE Tablets(
                        ID INT PRIMARY KEY AUTO_INCREMENT,
                        company_id INT NOT NULL,
                        assigned_user INT NOT NULL,
                        FOREIGN KEY (company_id) REFERENCES Company(ID) ON DELETE CASCADE,
                        FOREIGN KEY (assigned_user) REFERENCES Users(ID) ON DELETE CASCADE
);

CREATE TABLE CellPhone(
                          ID INT PRIMARY KEY AUTO_INCREMENT,
                          phone_number LONG NULL DEFAULT 'No service / no data',
                          service_provider TEXT NOT NULL,
    -- impose name space
                          CONSTRAINT check_phone_provider CHECK (service_provider IN('Verizon', 'AT&T', 'T-Mobile', 'US Cellular')),
                          company_id INT NOT NULL,
                          assigned_user INT NOT NULL,
                          FOREIGN KEY (company_id) REFERENCES Company(ID) ON DELETE CASCADE,
                          FOREIGN KEY (assigned_user) REFERENCES Users(ID) ON DELETE CASCADE
);

CREATE TABLE Printers(
                         ID INT PRIMARY KEY AUTO_INCREMENT,
                         company_id INT NOT NULL,
                         assigned_user INT NOT NULL,
                         FOREIGN KEY (company_id) REFERENCES Company(ID) ON DELETE CASCADE,
                         FOREIGN KEY (assigned_user) REFERENCES Users(ID) ON DELETE CASCADE
);

# keep track of all the items a user has assigned to them.
CREATE TABLE UserItems (
                           ID INT AUTO_INCREMENT PRIMARY KEY,
                           user_id INT,
                           computer_id INT NULL,
                           phone_id INT NULL,
                           FOREIGN KEY (user_id) REFERENCES Users(ID) ON DELETE CASCADE,
                           FOREIGN KEY (computer_id) REFERENCES Computers(ID) ON DELETE SET NULL,
                           FOREIGN KEY (phone_id) REFERENCES CellPhone(ID) ON DELETE SET NULL
);




# Insert into Company
INSERT INTO Company (company_name, company_location, location_alias) VALUES ('TechCorp', 'New York', 'NYC');
INSERT INTO Company (company_name, company_location, location_alias) VALUES ('BizSolutions', 'San Francisco', 'SF');
INSERT INTO Company (company_name, company_location, location_alias) VALUES ('DevHub', 'Austin', 'AUS');

# Insert into Users
INSERT INTO Users (first_name, last_name, full_name, company_id, job_role, email, username)
VALUES
    ('John', 'Doe', 'John Doe', 1, 'Software Engineer', 'john.doe@techcorp.com', 'johndoe'),
    ('Jane', 'Smith', 'Jane Smith', 2, 'Project Manager', 'jane.smith@bizsolutions.com', 'janesmith'),
    ('Mike', 'Johnson', 'Mike Johnson', 3, 'Designer', 'mike.johnson@devhub.com', 'mikejohnson');

# Insert into ActiveUsers
INSERT INTO ActiveUsers (user_id, hire_date) VALUES (1, '2023-01-15');
INSERT INTO ActiveUsers (user_id, hire_date) VALUES (2, '2022-06-20');
INSERT INTO ActiveUsers (user_id, hire_date) VALUES (3, '2021-11-05');

# Insert into Computers
INSERT INTO Computers (MAKE, MODEL, TYPE, AVAILABILITY, serial_number, company_id, assigned_user)
VALUES
    ('Dell', 'XPS 13', 'Laptop', 'Available', 12345, 1, 1),
    ('HP', 'Envy 15', 'Laptop', 'Available', 12346, 2, 2),
    ('Apple', 'MacBook Pro', 'Laptop', 'Available', 12347, 3, 3);

# Insert into Tablets
INSERT INTO Tablets (company_id, assigned_user) VALUES (1, 1);
INSERT INTO Tablets (company_id, assigned_user) VALUES (2, 2);
INSERT INTO Tablets (company_id, assigned_user) VALUES (3, 3);

# Insert into CellPhone
INSERT INTO CellPhone (phone_number, service_provider, company_id, assigned_user)
VALUES
    (5551234567, 'Verizon', 1, 1),
    (5559876543, 'AT&T', 2, 2),
    (5555555555, 'T-Mobile', 3, 3);

# Insert into Printers
INSERT INTO Printers (company_id, assigned_user) VALUES (1, 1);
INSERT INTO Printers (company_id, assigned_user) VALUES (2, 2);
INSERT INTO Printers (company_id, assigned_user) VALUES (3, 3);

# Insert into UserItems
INSERT INTO UserItems (user_id, computer_id, phone_id)
VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3);



SELECT * FROM ActiveUsers, CellPhone, Company, Computers, Printers, Tablets, UserItems, Users




# Test Queries
