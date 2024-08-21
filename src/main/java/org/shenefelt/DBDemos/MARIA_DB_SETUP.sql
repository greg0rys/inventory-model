# SEQUENTIAL EXECUTION

#Drop the DBS if they exist
# WARNING ONLY EXE IF YOU ARE STARTING FRESH
DROP TABLE IF EXISTS Users; # has to be dropped first as it has the fk requirement of company
DROP TABLE IF EXISTS Company;
DROP TABLE IF EXISTS Computers;
DROP TABLE IF EXISTS Tablets;
DROP TABLE IF EXISTS CellPhone;
DROP TABLE IF EXISTS Printers;


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
    company_id INT NOT NULL,
    job_role TEXT NOT NULL,
    foreign key (company_id) references Company(ID)
);

# Inventory objects
CREATE TABLE Computers
(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    MAKE TEXT NOT NULL,
    MODEL TEXT NOT NULL,
    TYPE TEXT NOT NULL,
    AVAILABILITY TEXT NOT NULL,
    company_id INT NOT NULL,
    assigned_user INT NOT NULL,
    foreign key (company_id) references Company(ID),
    foreign key (assigned_user) references Users(ID)

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
)
# will use FK in all devices
# CREATE TABLE Owners();