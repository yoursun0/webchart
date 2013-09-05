-- Recruitment task for .NET Developer position
-- Author: Helic Leung
-- MySQL DB script of Web Application webchart

-- Create database for the web app "webchart"
CREATE DATABASE webchart;
-- Grant privileges to the root user, please modify the username and password of your DB admin user.

CREATE USER root IDENTIFIED BY 'password'; 
GRANT USAGE ON *.* TO root@localhost IDENTIFIED BY 'password'; 
GRANT ALL PRIVILEGES ON webchart.* TO root@localhost IDENTIFIED BY 'password';
FLUSH PRIVILEGES;

-- Switch to webchart database schema
USE webchart;

CREATE TABLE customers
(
ID int(10) NOT NULL AUTO_INCREMENT, 
name varchar(255),
firstname varchar(255),
lastname varchar(255),
email varchar(255),
address varchar(255),
phone varchar(255),
PRIMARY KEY (ID)
);

insert into customers (ID,name,firstname,lastname,email,address,phone) 
	           VALUES (default,'helic','Helic','Leung', 'yoursun0@gmail.com','Shatin, N.T., Hong Kong','65432100');
insert into customers (ID,name,firstname,lastname,email,address,phone) 
	           VALUES (default,'remi','Remi','Gasek', 'remi@gmail.com','Causeway Bay, Hong Kong','98765432');

CREATE TABLE transactions
(
ID int(10) NOT NULL AUTO_INCREMENT, 
datetime date NOT NULL, 
customer varchar(255),
product varchar(255),
quantity int,
price decimal(20,2),
totalvalue decimal(20,2),
volume int,
PRIMARY KEY (ID)
);

COMMIT;