DROP TABLE ORGJEDNOTKA CASCADE CONSTRAINTS;
DROP TABLE MISTNOST CASCADE CONSTRAINTS;
DROP TABLE REZERVACE CASCADE CONSTRAINTS;
DROP TABLE FREKVENCE CASCADE CONSTRAINTS;
DROP TABLE AKCE CASCADE CONSTRAINTS;
DROP TABLE ANOTACE CASCADE CONSTRAINTS;
DROP TABLE PRACOVNIK CASCADE CONSTRAINTS;

-- 1) Organizacni jednotka
CREATE TABLE ORGJEDNOTKA(
  zkratka VARCHAR(10) PRIMARY KEY,
  nazev_org VARCHAR(64),
  zkratka_nadrazeni_jed VARCHAR(10)
);

-- 2) Mistnost
CREATE TABLE MISTNOST(
  nazev_mist VARCHAR(10),
  OrgJedn VARCHAR(10)
);

-- 3) Rezervace
CREATE TABLE REZERVACE(
  id INT PRIMARY KEY,
  od DATE NOT NULL,
  do DATE NOT NULL,   
  typ VARCHAR(64),
  popis VARCHAR(255),
  jednorazova CHAR(1), 	-- boolovska hodnota pro rychlejsi pristup 
  datum DATE,       	-- k datumum jednorazovych akce
  mistn VARCHAR(10),
  id_prac INT,
  id_akce INT
);

-- 4) Frekvence pravidelnych akci
CREATE TABLE FREKVENCE(
  rok INT,
  den VARCHAR(10),
  semestr VARCHAR(15),
  tyden VARCHAR(10),
  id_rezer INT PRIMARY KEY
);

-- 5) Akce
CREATE TABLE AKCE(
  id int,
  nazev varchar(10),
  misto varchar(20),
  zobrazeni CHAR(1),
  dokdy date, 
  typ varchar(20)
);

-- 6) Anotace
CREATE TABLE ANOTACE(
 id_akce INT,
 popis_akce VARCHAR(255),
 jazyk VARCHAR(20),
 CONSTRAINT id_anotace PRIMARY KEY (id_akce, jazyk)
);

-- 7) Pracovnik
CREATE TABLE PRACOVNIK(
  id INT,
  jmeno VARCHAR(20)
);

ALTER TABLE MISTNOST ADD PRIMARY KEY (nazev_mist);
ALTER TABLE AKCE ADD PRIMARY KEY (id);
ALTER TABLE PRACOVNIK ADD PRIMARY KEY (id);
ALTER TABLE ORGJEDNOTKA ADD FOREIGN KEY (zkratka_nadrazeni_jed) REFERENCES ORGJEDNOTKA(zkratka);
ALTER TABLE ANOTACE ADD FOREIGN KEY (id_akce) REFERENCES AKCE(id);
ALTER TABLE FREKVENCE ADD FOREIGN KEY (id_rezer) REFERENCES REZERVACE(id);
ALTER TABLE REZERVACE ADD FOREIGN KEY (mistn) REFERENCES MISTNOST(nazev_mist);
ALTER TABLE REZERVACE ADD FOREIGN KEY (id_akce) REFERENCES AKCE(id);
ALTER TABLE REZERVACE ADD FOREIGN KEY (id_prac) REFERENCES PRACOVNIK(id);
ALTER TABLE MISTNOST ADD FOREIGN KEY (OrgJedn) REFERENCES ORGJEDNOTKA(zkratka);