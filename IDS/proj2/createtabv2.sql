DROP TABLE "Order" CASCADE CONSTRAINTS;
DROP TABLE "OrderedBread" CASCADE CONSTRAINTS;
DROP TABLE "Bread" CASCADE CONSTRAINTS;
DROP TABLE "Employee" CASCADE CONSTRAINTS;
DROP TABLE "Baker" CASCADE CONSTRAINTS;
DROP TABLE "Driver" CASCADE CONSTRAINTS;
DROP TABLE "LogisticManager" CASCADE CONSTRAINTS;
DROP TABLE "Delivery_Area" CASCADE CONSTRAINTS;
--added
DROP TABLE "Customer" CASCADE CONSTRAINTS;
DROP TABLE "OrderedMaterials" CASCADE CONSTRAINTS;
DROP TABLE "OrderOfMaterials" CASCADE CONSTRAINTS;
DROP TABLE "Materials" CASCADE CONSTRAINTS;
DROP TABLE "Driver_in_area" CASCADE CONSTRAINTS;

CREATE TABLE "Order"(
"ID_order"          INTEGER,
"Date"              DATE,
"Deadline"          DATE,
"Delivery"          VARCHAR(20) NOT NULL,
"Delivery_Adress"   VARCHAR(50),
"IsProcessed"       VARCHAR(3) DEFAULT 'no',
"CustomerID_Customer" INTEGER
);

CREATE TABLE "OrderedBread"(
"OrderID_order"     INTEGER,
"BreadID_Bread"     INTEGER,
"Amount"            INTEGER NOT NULL
);

CREATE TABLE "Bread"(
"ID_Bread"          INTEGER NOT NULL,
"Name"              VARCHAR(20) NOT NULL,
"Cost"              DECIMAL(8,2)
);

CREATE TABLE "Employee"(
"ID_PersonalNumber"   INTEGER NOT NULL,
"BirthNumber"         CHAR(16) NOT NULL,
"Name"                VARCHAR(50),
"BirthDate"           DATE,
"Adress"              VARCHAR(50),
"AccountNumber"       VARCHAR(16),
"ContractType"        VARCHAR(20),
"Salary"              INTEGER
);

CREATE TABLE "Baker"(
"EmployeeID_PersonalNumber" INTEGER,
"Position"                  VARCHAR(20)
);

CREATE TABLE "Driver"(
"EmployeeID_PersonalNumber" INTEGER,
"Car"                       VARCHAR(20)
);

CREATE TABLE "LogisticManager"(
"EmployeeID_PersonalNumber" INTEGER,
"OfficeNumber"              INTEGER
);

CREATE TABLE "Delivery_Area"(
"ID_Area"                   INTEGER,
"Name"                      VARCHAR(20),
"StreetList"                VARCHAR(200)
);

CREATE TABLE "Driver_in_area"(
"DriverID"                  INTEGER,
"AreaID"                    INTEGER
);

--added
CREATE TABLE "Customer"(
"ID_Customer"				INTEGER,
"Name"						VARCHAR(20),
"Adress"					VARCHAR(50),
"Email"						VARCHAR(50),
"Password"					VARCHAR(16),
"Delivery_AreaID_Area"		INTEGER
);

CREATE TABLE "OrderOfMaterials"(
"ID_OrderOfMaterials"		INTEGER,
"Date"						DATE,
"EmployeeID_PersonalNumber"	INTEGER
);

CREATE TABLE "OrderedMaterials"(
"OMID_OrderOfMaterials"	INTEGER,
"MaterialsID_Material"					INTEGER,
"Amount"								INTEGER,
"Cost"									DECIMAL(8,2)
);

CREATE TABLE "Materials" (
"ID_Material"							INTEGER,
"Name"									VARCHAR(50),
"InDeposit"								INTEGER,
"ExpirationDate"						DATE
);
----



-- Adding PRIMARY KEYS 
ALTER TABLE "Order" ADD PRIMARY KEY ("ID_order");
ALTER TABLE "OrderedBread" ADD PRIMARY KEY ("BreadID_Bread", "OrderID_order");
ALTER TABLE "Bread" ADD PRIMARY KEY ("ID_Bread");
ALTER TABLE "Employee" ADD PRIMARY KEY ("ID_PersonalNumber");
ALTER TABLE "Delivery_Area" ADD PRIMARY KEY ("ID_Area");
ALTER TABLE "Driver_in_area" ADD PRIMARY KEY ("DriverID", "AreaID");
--added
ALTER TABLE "Customer" ADD PRIMARY KEY ("ID_Customer");
ALTER TABLE "OrderOfMaterials" ADD PRIMARY KEY ("ID_OrderOfMaterials");
ALTER TABLE "OrderedMaterials" ADD PRIMARY KEY ("OMID_OrderOfMaterials", "MaterialsID_Material");
ALTER TABLE "Materials" ADD PRIMARY KEY ("ID_Material");
----

-- Adding FOREIGN KEYS with references on other tables
ALTER TABLE "Order" ADD FOREIGN KEY ("CustomerID_Customer") REFERENCES "Customer"("ID_Customer"); --uncommented
ALTER TABLE "OrderedBread" ADD FOREIGN KEY ("OrderID_order") REFERENCES "Order"("ID_order");
ALTER TABLE "OrderedBread" ADD FOREIGN KEY ("BreadID_Bread") REFERENCES "Bread"("ID_Bread");
--ALTER TABLE "Bread" ADD FOREIGN KEY ("MaterialsID_Material") REFERENCES "Materials"("ID_Material"); --need to check
ALTER TABLE "LogisticManager" ADD FOREIGN KEY ("EmployeeID_PersonalNumber") REFERENCES "Employee"("ID_PersonalNumber");
ALTER TABLE "Driver" ADD FOREIGN KEY ("EmployeeID_PersonalNumber") REFERENCES "Employee"("ID_PersonalNumber");
ALTER TABLE "Baker" ADD FOREIGN KEY ("EmployeeID_PersonalNumber") REFERENCES "Employee"("ID_PersonalNumber");
ALTER TABLE "Driver_in_area" ADD FOREIGN KEY ("DriverID") REFERENCES "Employee" ("ID_PersonalNumber");
ALTER TABLE "Driver_in_area" ADD FOREIGN KEY ("AreaID") REFERENCES "Delivery_Area" ("ID_Area");
--added
ALTER TABLE "Customer" ADD FOREIGN KEY ("Delivery_AreaID_Area") REFERENCES "Delivery_Area"("ID_Area");
ALTER TABLE "OrderOfMaterials" ADD FOREIGN KEY ("EmployeeID_PersonalNumber") REFERENCES "Employee"("ID_PersonalNumber");
ALTER TABLE "OrderedMaterials" ADD FOREIGN KEY ("OMID_OrderOfMaterials") REFERENCES "OrderOfMaterials" ("ID_OrderOfMaterials");
ALTER TABLE "OrderedMaterials" ADD FOREIGN KEY ("MaterialsID_Material") REFERENCES "Materials" ("ID_Material");
----

ALTER TABLE "Employee" ADD CONSTRAINT "chck_birth_number" CHECK (regexp_like("BirthNumber", '[0-9]{2}[01235678][0-9][0-3][0-9][/][0-9]{4}') );

-- INSERTING
INSERT INTO "Delivery_Area" ("ID_Area", "Name", "StreetList")
VALUES(1, 'Technologick� park', 'Kolejn�, Purky?ova, ?ervinkova');
INSERT INTO "Delivery_Area" ("ID_Area", "Name", "StreetList")
VALUES(2, 'Cejl', 'Soudn�, Valcha, Tkalcovsk�');

-- Bread
INSERT INTO "Bread" ("ID_Bread", "Name", "Cost")
VALUES (1, 'Chleba b�ly', 20.50);
INSERT INTO "Bread" ("ID_Bread", "Name", "Cost")
VALUES (2, 'Rohl�k grahamov�', 6.50);
INSERT INTO "Bread" ("ID_Bread", "Name", "Cost")
VALUES (3, 'Buchta s lekv�rem', 12);

-- Customer
INSERT INTO "Customer"
VALUES (50, 'Miroslav Jarn�', 'Kolejn� 2 Brno 602 00', 'mjarny@email.com', '0000', 2);

--Order
INSERT INTO "Order" -- uncommented
VALUES (300, '2.2.1999', '21.2.1999', 'personal', 'Kolejni 2 Brno 602 00', 'no',50 );

-- OrderedBread
INSERT INTO "OrderedBread" 
VALUES (300,3,5);

-- Employees
INSERT INTO "Employee" 
VALUES (100, '990101/1234', 'Michal Novotn�', '1.1.1999','Kolejni 6 Brno 602 00','1234567891234567','full-time', '15000');
INSERT INTO "LogisticManager"
VALUES (100, 122);
INSERT INTO "Employee" 
VALUES (200, '991010/1234', 'Ladislav Pochm�rny', '10.10.1999','6. apr�la 333 Vrbov� 922 03 SR','1234567891231234','full-time', '15000');
INSERT INTO "Baker"
VALUES (200, 'Hlavn� pec');
INSERT INTO "Employee" 
VALUES (300, '990202/1234', 'Peter Chrom�', '02.02.1999','Purky�ova 93, 612 00 Brno','4321567891231234','full-time', '14000');
INSERT INTO "Driver"
VALUES (300, 'Fiat Punto');


--Driver in arae
INSERT INTO "Driver_in_area"
VALUES (300,2);
INSERT INTO "Driver_in_area"
VALUES (300,1);

--Materials
INSERT INTO "Materials"
VALUES (20, 'Mouka', 10, '31.12.2017');

--OrderofMaterials
INSERT INTO "OrderOfMaterials"
VALUES(27, sysdate, 100);

-- Oredered Materials
INSERT INTO "OrderedMaterials"
VALUES(27, 20, 20, 20.50);