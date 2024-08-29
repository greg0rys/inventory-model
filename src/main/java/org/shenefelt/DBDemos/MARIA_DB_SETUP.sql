# SEQUENTIAL EXECUTION

# this file has been written in sequential order, do not rearrange these statements

#Drop the DBS if they exist
# WARNING ONLY EXE IF YOU ARE STARTING FRESH

DROP TABLE IF EXISTS Computers;
DROP TABLE IF EXISTS Tablets;
DROP TABLE IF EXISTS CellPhone;
DROP TABLE IF EXISTS Printers;
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
    foreign key (company_id) references Company(ID),
    constraint unique_email unique(email),
    constraint unique_username unique(username)
);

# Inventory objects
CREATE TABLE Computers
(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    MAKE TEXT NOT NULL,
    MODEL TEXT NOT NULL,
    TYPE TEXT NOT NULL,
    AVAILABILITY TEXT NOT NULL,
    serial_number int not null,
    company_id INT NOT NULL,
    assigned_user INT NULL DEFAULT 1,
    foreign key (company_id) references Company(ID),
    foreign key (assigned_user) references Users(ID),
    CONSTRAINT unique_serial_number UNIQUE (serial_number)

);

CREATE TABLE Tablets(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    company_id INT NOT NULL,
    assigned_user INT NOT NULL,
    foreign key (company_id) references Company(ID),
    foreign key (assigned_user) references Users(ID)
);

CREATE TABLE CellPhone(
   ID INT PRIMARY KEY AUTO_INCREMENT,
   phone_number long null default 'No service / no data',
   service_provider text not null,
    # impose name space
   constraint check_phone_provider CHECK (service_provider in('Verizon', 'AT&T', 'T-Mobile', 'US Cellular')),
   company_id INT NOT NULL,
   assigned_user INT NOT NULL,
   foreign key (company_id) references Company(ID),
   foreign key (assigned_user) references Users(ID)
);

CREATE TABLE Printers(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    company_id INT NOT NULL,
    assigned_user INT NOT NULL,
    foreign key (company_id) references Company(ID),
    foreign key (assigned_user) references Users(ID)
);

# keep track of all the items a user has assigned to them.
CREATE TABLE UserItems (
   id INT AUTO_INCREMENT PRIMARY KEY,
   user_id INT,
   computer_id INT NULL,
   phone_id INT NULL,
   FOREIGN KEY (user_id) REFERENCES Users(id),
   FOREIGN KEY (computer_id) REFERENCES Computers(id),
   FOREIGN KEY (phone_id) REFERENCES CellPhone(id)
);
# will use FK in all devices
# CREATE TABLE Owners();