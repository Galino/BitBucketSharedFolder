 

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
DROP INDEX B_Cost_Index;
DROP INDEX OB_Amount_Index;
DROP MATERIALIZED VIEW LOG ON "Delivery_Area";
DROP MATERIALIZED VIEW LOG ON "Customer";
DROP MATERIALIZED VIEW "Customer_Area";

GRANT ALL ON "Customer" TO xgalbi01;
GRANT ALL ON "Delivery_Area" TO xgalbi01;

CREATE TABLE "Order"(
"ID_order"          		INTEGER,
"Date"             		 	DATE,
"Deadline"         		 	DATE,
"Delivery"          		VARCHAR(20) NOT NULL,
"Delivery_City"     		VARCHAR(50),
"Delivery_Street"		  	VARCHAR(50),
"Delivery_PostCode"			VARCHAR(12),
"IsProcessed"       		VARCHAR(3) DEFAULT 'no',
"CustomerID_Customer" 		INTEGER
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
"City"                VARCHAR(50),
"Street"			  VARCHAR(50),
"PostCode"			  VARCHAR(12),
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
"City"              		VARCHAR(50),
"Street"			 		VARCHAR(50),
"PostCode"			  		VARCHAR(12),
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


-- ============================================================================
-- ============================================================================

-- Trigger n.1
-- pri odstraneni zakaznika z databazy sa zrusia aj jeho objednavky  
-- (vymze sa zaznam z order a o danej order sa vymaze zaznam z OrderedBread)
CREATE OR REPLACE TRIGGER "Customer_Remover"  BEFORE DELETE ON "Customer"
FOR EACH ROW
BEGIN 
  DELETE FROM "OrderedBread" OB
  WHERE OB."OrderID_order" IN(
    SELECT O."ID_order"
    FROM "Order" O
    WHERE O."CustomerID_Customer" = :old."ID_Customer"
    );
  DELETE FROM "Order"  WHERE "Order"."CustomerID_Customer" = :old."ID_Customer";
END;
/

-- Trigger n.2
-- Vytvorennie sequence a autoinkrementacie nad ID Bread
DROP SEQUENCE seq;
CREATE SEQUENCE seq;
--EXEC reset_seq('seq');

CREATE OR REPLACE TRIGGER auto_inc
BEFORE INSERT ON "Bread" FOR EACH ROW
BEGIN
  SELECT seq.NEXTVAL
  INTO   :new."ID_Bread"
  FROM   dual;
END;
/

SET SERVEROUTPUT ON;


-- Procedura skontroluje ci je dostatok zasob materialu ( > 10 ) na sklade ,a poda 
-- a vypise odpovedajuce hlasenie
CREATE OR REPLACE PROCEDURE IsEnough (search_for IN VARCHAR)
AS
  pom_name "Materials"."InDeposit"%TYPE;
BEGIN
  SELECT "Materials"."InDeposit" INTO pom_name FROM "Materials"  WHERE search_for = "Materials"."Name";
  IF pom_name < 10 THEN
    dbms_output.put_line('We are runnig out of ' || search_for );
  ELSE
   dbms_output.put_line('There is still enough of ' || search_for );
  END IF;
  EXCEPTION 
    WHEN NO_DATA_FOUND THEN
      dbms_output.put_line('No material with name ' || search_for || ' was found ');
END;
/


-- Procedura vypise zamestnancov, ktory majú >= plat ako je udane pre proceduru
CREATE OR REPLACE PROCEDURE Salary (eqmorethan IN INTEGER)
AS
  c_id "Employee"."ID_PersonalNumber"%TYPE;
  c_name "Employee"."Name"%TYPE;
  c_salary "Employee"."Salary"%TYPE;
  CURSOR c_employee IS 
    SELECT "ID_PersonalNumber", "Name", "Salary"
    FROM "Employee";
BEGIN
  OPEN c_employee;
  LOOP
    FETCH c_employee INTO c_id, c_name, c_salary;
    EXIT WHEN c_employee%notfound;
    IF c_salary >= eqmorethan THEN
      DBMS_OUTPUT.put_line(c_id || ' ' || c_name || ' ' || c_salary);
    END IF;
    END LOOP;
    CLOSE c_employee;
END;
/

-- ============================================================================
-- ============================================================================

-- INSERTING
INSERT INTO "Delivery_Area" ("ID_Area", "Name", "StreetList")
VALUES(1, 'Technologický park', 'Kolejní, Purkynova, Èervinkova');
INSERT INTO "Delivery_Area" ("ID_Area", "Name", "StreetList")
VALUES(2, 'Cejl', 'Soudní, Valcha, Tkalcovská');

-- Bread
INSERT INTO "Bread" 
VALUES (NULL, 'Chleba bíly', 20.50);
INSERT INTO "Bread" 
VALUES (NULL, 'Rohlík grahamový', 6.50);
INSERT INTO "Bread" 
VALUES (NULL, 'Buchta s lekvárem', 12);
INSERT INTO "Bread" 
VALUES (NULL, 'Houska maková', 10);
INSERT INTO "Bread" 
VALUES (NULL, 'Rohlík obyèejný', 3);
INSERT INTO "Bread" 
VALUES (NULL, 'Koláè tvarohový', 14);
INSERT INTO "Bread" 
VALUES (NULL, 'Koláè lekvárový', 14);
INSERT INTO "Bread" 
VALUES (NULL, 'Kobliba s èokoladou', 9.50);
INSERT INTO "Bread" 
VALUES (NULL, 'Chleba èerný', 28);
INSERT INTO "Bread" 
VALUES (NULL, 'Pletenka obyèejná', 8);


-- Customer
INSERT INTO "Customer"
VALUES (50, 'Miroslav Jarný', 'Brno','Kolejní 2' , '602 00', 'mjarny@email.com', '0000', 1);

INSERT INTO "Customer"
VALUES (55, 'Lukáš Luvický', 'Brno','Vinohrady' , '602 00', 'luvicky@email.com', '0000', 2);
INSERT INTO "Customer"
VALUES (60, 'David Michal', 'Brno','Kamenice' , '602 00', 'dm@email.com', '0000', 2);

--Order
INSERT INTO "Order" -- uncommented
VALUES (300, '2.2.1999', '21.2.1999', 'personal', 'Brno', 'Kolejní 2 ', '602 00', 'no',50 );

INSERT INTO "Order" 
VALUES (301, '5.2.1999', '20.3.1999', 'onAddress', 'Brno', 'Vinohrady', '602 00', 'no',55 );

INSERT INTO "Order" 
VALUES (302, '10.3.1999', '10.5.1999', 'onAddress', 'Brno', 'Kamenice', '602 00', 'no',60 );

-- OrderedBread
INSERT INTO "OrderedBread" 
VALUES (300,3,5);

INSERT INTO "OrderedBread" 
VALUES (300,5,20);

INSERT INTO "OrderedBread" 
VALUES (301,1,50);

INSERT INTO "OrderedBread" 
VALUES (301,3,60);

INSERT INTO "OrderedBread" 
VALUES (302,1,10);

-- Employees
INSERT INTO "Employee" 
VALUES (100, '990101/1234', 'Michal Novotný', '1.1.1999', 'Brno', 'Kolejní 6', '602 00','1234567891234567','full-time', '15000');
INSERT INTO "LogisticManager"
VALUES (100, 122);
INSERT INTO "Employee" 
VALUES (200, '991010/1234', 'Ladislav Pochmúrny', '10.10.1999','Vrbové','6. apríla 333', '922 03 SR','1234567891231234','full-time', '4000');
INSERT INTO "Baker"
VALUES (200, 'Hlavná­ pec');
INSERT INTO "Employee" 
VALUES (300, '990202/1234', 'Peter Chromý', '02.02.1999','Brno', 'Purkynova 93','612 00', '4321567891231234','full-time', '14000');
INSERT INTO "Driver"
VALUES (300, 'Fiat Punto');


-- Driver in area
INSERT INTO "Driver_in_area"
VALUES (300,2);
INSERT INTO "Driver_in_area"
VALUES (300,1);

-- Materials
INSERT INTO "Materials"
VALUES (20, 'Mouka', 10, '31.12.2017');
INSERT INTO "Materials"
VALUES (30, 'Vejce', 5, '30.12.2017');
INSERT INTO "Materials"
VALUES (40, 'Syr', 5, '30.12.2017');


-- OrderofMaterials
INSERT INTO "OrderOfMaterials"
VALUES(27, '10.2.2012', 100);

INSERT INTO "OrderOfMaterials"
VALUES(28, '10.2.2014', 100);

-- Objednavka syru a muky
INSERT INTO "OrderOfMaterials"
VALUES(29, '10.2.2013', 100);

-- Oredered Materials
INSERT INTO "OrderedMaterials"
VALUES(27, 20, 20, 20.50);

INSERT INTO "OrderedMaterials"
VALUES(28, 30, 20, 3.30);

INSERT INTO "OrderedMaterials"
VALUES(28, 40, 10, 20.50);

INSERT INTO "OrderedMaterials"
VALUES(29, 20, 100, 16);

INSERT INTO "OrderedMaterials"
VALUES(29, 40, 100, 4);

--EP pre zobrazenie celkovej ceny objednávok 
EXPLAIN PLAN FOR 
SELECT O."ID_order", sum(B."Cost" * OB."Amount") AS "FinalCost"  FROM "Order" O,  "OrderedBread" OB, "Bread" B
WHERE O."ID_order" = OB."OrderID_order" AND OB."BreadID_Bread" = B."ID_Bread"
GROUP BY "ID_order";

SELECT plan_table_output FROM TABLE (dbms_xplan.display());

--vytvorenie indexov
CREATE INDEX B_Cost_Index ON "Bread"("Cost");
CREATE INDEX OB_Amount_Index ON "OrderedBread"("Amount");

--EP pre zobrazenie celkovej ceny objednávok 
EXPLAIN PLAN FOR 
SELECT O."ID_order", sum(B."Cost" * OB."Amount") AS "FinalCost"  FROM "Order" O,  "OrderedBread" OB, "Bread" B
WHERE O."ID_order" = OB."OrderID_order" AND OB."BreadID_Bread" = B."ID_Bread"
GROUP BY "ID_order";

SELECT plan_table_output FROM TABLE (dbms_xplan.display());

-- Materialized view - ktory zakaznik patri do akej delivery area
CREATE MATERIALIZED VIEW LOG ON "Delivery_Area" WITH ROWID;
CREATE MATERIALIZED VIEW LOG ON "Customer" WITH ROWID;

-- tento pohlad treba vytvorit pod uctom test_galbi
CREATE MATERIALIZED VIEW "Customer_Area"
CACHE
BUILD IMMEDIATE
REFRESH FAST ON COMMIT
ENABLE QUERY REWRITE
AS
  SELECT CU."ID_Customer", CU."Name" AS "Cust_Name", DA."Name" AS "DA_Name", CU.ROWID AS "Cust_ROWID", DA.ROWID AS "DA_ROWID" 
  FROM "Customer" CU, "Delivery_Area" DA
  WHERE CU."Delivery_AreaID_Area" = DA."ID_Area";
  
EXPLAIN PLAN FOR
  SELECT CU."ID_Customer", CU."Name" AS "Cust_Name", DA."Name" AS "DA_Name", CU.ROWID AS "Cust_ROWID", DA.ROWID AS "DA_ROWID" 
  FROM "Customer" CU, "Delivery_Area" DA
  WHERE CU."Delivery_AreaID_Area" = DA."ID_Area";
  
SELECT plan_table_output FROM TABLE (dbms_xplan.display());