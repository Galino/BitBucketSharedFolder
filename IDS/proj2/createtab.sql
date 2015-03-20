DROP TABLE "Order" CASCADE CONSTRAINTS;
DROP TABLE "OrderedBread" CASCADE CONSTRAINTS;
DROP TABLE "Bread" CASCADE CONSTRAINTS;
DROP TABLE "Employee" CASCADE CONSTRAINTS;
DROP TABLE "Baker" CASCADE CONSTRAINTS;
DROP TABLE "Driver" CASCADE CONSTRAINTS;
DROP TABLE "LogisticManager" CASCADE CONSTRAINTS;
DROP TABLE "Delivery_Area" CASCADE CONSTRAINTS;

CREATE TABLE "Order"(
"ID_order"          INTEGER,
"Date"              DATE,
"Deadline"          DATE,
"Delivery"          VARCHAR(20) NOT NULL,
"Delivery_Adress"   VARCHAR(50),
"IsProcessed"       VARCHAR(3) NOT NULL,
"CostumerID_Costumer" INTEGER
);

CREATE TABLE "OrderedBread"(
"OrderID_order"     INTEGER,
"BreadID_Bread"     INTEGER,
"Amount"            INTEGER NOT NULL
);

CREATE TABLE "Bread"(
"ID_Bread"          INTEGER,
"Name"              VARCHAR(20) NOT NULL,
"Cost"              DECIMAL(8,2)
);

CREATE TABLE "Employee"(
"ID_PersonalNumber"   INTEGER,
"BirthNumber"         CHAR(11) NOT NULL,
"Name"                VARCHAR(50),
"BirthDate"           DATE,
"Adress"              VARCHAR(20),
"AccountNumber"       VARCHAR(15),
"ContractType"        VARCHAR(20),
"Salary"              INTEGER
);

CREATE TABLE "Baker"(
"EmployeeID_PersonalNumber" INTEGER,
"Position"                  VARCHAR(20)
);

CREATE TABLE "Driver"(
"EmployeeID_PersonalNumber" INTEGER,
"Car"                       VARCHAR(20),
"AreaID_area"               INTEGER
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


-- Adding PRIMARY KEYS 
ALTER TABLE "Order" ADD PRIMARY KEY ("ID_order");
ALTER TABLE "OrderedBread" ADD PRIMARY KEY ("BreadID_Bread", "OrderID_order");
ALTER TABLE "Bread" ADD PRIMARY KEY ("ID_Bread");
ALTER TABLE "Employee" ADD PRIMARY KEY ("ID_PersonalNumber");
ALTER TABLE "Delivery_Area" ADD PRIMARY KEY ("ID_Area");

-- Adding FOREIGN KEYS with references on other tables
--ALTER TABLE "Order" ADD FOREIGN KEY (CostumerID_Costumer) REFERENCES Costumer(ID_Costumer);
ALTER TABLE "OrderedBread" ADD FOREIGN KEY ("OrderID_order") REFERENCES "Order"("ID_order");
ALTER TABLE "OrderedBread" ADD FOREIGN KEY ("BreadID_Bread") REFERENCES "Bread"("ID_Bread");
--ALTER TABLE "Bread" ADD FOREIGN KEY (MaterialsID_Material) REFERENCES "Materials"(ID_Material);
ALTER TABLE "LogisticManager" ADD FOREIGN KEY ("EmployeeID_PersonalNumber") REFERENCES "Employee"("ID_PersonalNumber");
ALTER TABLE "Driver" ADD FOREIGN KEY ("EmployeeID_PersonalNumber") REFERENCES "Employee"("ID_PersonalNumber");
ALTER TABLE "Driver" ADD FOREIGN KEY ("AreaID_area") REFERENCES "Delivery_Area"("ID_Area");
ALTER TABLE "Baker" ADD FOREIGN KEY ("EmployeeID_PersonalNumber") REFERENCES "Employee"("ID_PersonalNumber");