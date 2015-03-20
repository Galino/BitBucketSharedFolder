-- drop all database objects in the schema of the current user

BEGIN

        FOR rec IN (

                SELECT 'DROP ' || object_type || ' ' || object_name || DECODE ( object_type, 'TABLE', ' CASCADE CONSTRAINTS PURGE' ) AS v_sql

                FROM user_objects

                WHERE object_type IN ( 'TABLE', 'VIEW', 'PACKAGE', 'TYPE', 'PROCEDURE', 'FUNCTION', 'TRIGGER', 'SEQUENCE' )

                ORDER BY object_type, object_name

        ) LOOP

                EXECUTE IMMEDIATE rec.v_sql;

        END LOOP;

END;

/

 

-- UZIVATEL

CREATE TABLE uzivatel (

  login          VARCHAR2(8)     CONSTRAINT PK_uzivatel_login PRIMARY KEY,

  jmeno          VARCHAR2(20)    CONSTRAINT NN_uzivatel_jmeno NOT NULL,

  prijmeni       VARCHAR2(20)    CONSTRAINT NN_uzivatel_prijmeni NOT NULL,

  datum_narozeni DATE            CONSTRAINT NN_uzivatel_datum_narozeni NOT NULL,

  ucitel         CHAR(1)         CONSTRAINT ENUM_ucitel_bool CHECK(ucitel in ('Y', 'N'))

);

 

INSERT INTO uzivatel VALUES ('xsokol08', 'Juraj', 'Sokol', TO_DATE('01.02.1993', 'DD.MM.YYYY'), 'N');

INSERT INTO uzivatel (login, prijmeni, jmeno, datum_narozeni, ucitel) VALUES

                    ('xsokov00','Sokova', 'Veronika', TO_DATE('08.09.1991', 'DD.MM.YYYY'), 'N');

 

-- PREDMET

CREATE SEQUENCE predmet_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

CREATE TABLE predmet (

  id                     INTEGER        CONSTRAINT PK_predmet_id PRIMARY KEY,

  zkratka                VARCHAR2(4)    CONSTRAINT NN_predmet_zkratka NOT NULL,

  nazev                  VARCHAR2(60)   CONSTRAINT NN_predmet_nazev NOT NULL,

  akademicky_rok         INTEGER        CONSTRAINT NN_predmet_akademicky_rok NOT NULL,

  semestr                VARCHAR2(5)    CONSTRAINT NN_predmet_semestr NOT NULL,

  pocet_kreditu          SMALLINT       CONSTRAINT NN_predmet_pocet_kreditu NOT NULL,

  rozsah_prednasek       SMALLINT,

  rozsah_cviceni         SMALLINT,

  rozsah_projektu        SMALLINT,

  zpusob_zakonceni       VARCHAR2(4)    CONSTRAINT NN_prednet_zpusob_zakonceni NOT NULL,

  pocet_bodu_pro_zapocet SMALLINT       DEFAULT 0

);

 

ALTER TABLE predmet

ADD (CONSTRAINT ENUM_semestr CHECK(semestr in ('letni', 'zimni')),

     CONSTRAINT ENUM_zpusob_zakonceni CHECK(zpusob_zakonceni in ('Zk', 'ZaZk', 'KlZa')),

     CONSTRAINT UQ_zkratka_rok_semestr UNIQUE (zkratka, akademicky_rok, semestr)

    );

 

INSERT INTO predmet (id, zkratka, nazev, akademicky_rok, semestr, pocet_kreditu, rozsah_prednasek,

                    rozsah_cviceni, rozsah_projektu, zpusob_zakonceni, pocet_bodu_pro_zapocet)

                    VALUES (predmet_seq.nextval, 'IDS', 'Databazove systemy',

                    2014, 'letni', 5, 39, 0, 13, 'ZaZk', 24);

INSERT INTO predmet VALUES (predmet_seq.nextval, 'IPK', 'Pocitacove komunikace a site', 2014,

                    'letni', 5, 39, 6, 10, 'ZaZk', 15);

 

-- ZAPIS_PREDMETU

 

CREATE TABLE zapis_predmetu (

  datum         DATE CONSTRAINT NN_datum NOT NULL,

  predmet_id    INTEGER     CONSTRAINT FK_predmet       REFERENCES predmet (id),

  uzivatel_login VARCHAR2(8) CONSTRAINT FK_uzivatel_login REFERENCES uzivatel (login)

);

 

ALTER TABLE zapis_predmetu

ADD (CONSTRAINT PK_predmet_uzivatel PRIMARY KEY (predmet_id, uzivatel_login));

 

INSERT INTO zapis_predmetu VALUES (TO_DATE('2015.04.16', 'YY.MM.DD'), 1, 'xsokol08');

INSERT INTO zapis_predmetu VALUES (TO_DATE('2015.04.16', 'YY.MM.DD'), 1, 'xsokov00');

 

-- AKTIVITA

 

CREATE SEQUENCE aktivita_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

 

CREATE TABLE aktivita (

  id           INTEGER        CONSTRAINT PK_id_aktivity PRIMARY KEY,

  datum        DATE,

  nazev        VARCHAR2(50)   CONSTRAINT NN_nazev_aktivity NOT NULL,

  popis        VARCHAR2(255)  DEFAULT '',

  min_bodu     SMALLINT,

  max_bodu     SMALLINT,

  max_studentu INTEGER,

  typ_aktivity VARCHAR2(9)    CONSTRAINT NN_typ_aktivity NOT NULL,

  predmet_id   INTEGER        CONSTRAINT FK_predmet_aktivita REFERENCES predmet

);

 

ALTER TABLE aktivita

ADD CONSTRAINT ENUM_typ_aktivity CHECK(typ_aktivity in ('spolecna', 'variantni', 'zkouska'));

 

INSERT INTO aktivita VALUES (aktivita_seq.nextval, TO_DATE('9.4.2015', 'DD.MM.YYYY'),

                          'Pulsemestralni zkouska', '', NULL, 15, NULL, 'variantni', 1);

INSERT INTO aktivita VALUES (aktivita_seq.nextval, TO_DATE('11.3.2015', 'DD.MM.YYYY'),

                          'Demonstracni cviceni 1', '', NULL, NULL, NULL, 'spolecna', 1);

INSERT INTO aktivita VALUES (aktivita_seq.nextval, TO_DATE('11.3.2015', 'DD.MM.YYYY'),

                          'Zaverecna zkouska', '', 20, 55, NULL, 'zkouska', 1);

 

-- VARIANTA_AKTIVITY

 

CREATE SEQUENCE varianta_aktivity_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

 

CREATE TABLE varianta_aktivity (

  id                  INTEGER CONSTRAINT PK_varianta_aktivity_id PRIMARY KEY,

  nazev VARCHAR2(50)  DEFAULT '',

  max_studentu        INTEGER,

  aktivita_id         INTEGER CONSTRAINT FK_id_aktivity_varianty REFERENCES aktivita

);

 

INSERT INTO varianta_aktivity VALUES (varianta_aktivity_seq.nextval, 'Pondeli 9.4. 8:00', 150, 1);

INSERT INTO varianta_aktivity VALUES (varianta_aktivity_seq.nextval, 'Pondeli 9.4. 9:30', 150, 1);

 

-- TERMIN_ZKOUSKY

CREATE SEQUENCE termin_zkousky_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

 

CREATE TABLE termin_zkousky (

  id             INTEGER CONSTRAINT PK_termin_zkousky_id PRIMARY KEY,

  poradove_cislo SMALLINT,

  datum          DATE,

  aktivita_id    INTEGER CONSTRAINT FK_id_aktivity_terminu_zkousky REFERENCES aktivita

);

 

ALTER TABLE termin_zkousky

ADD CONSTRAINT UQ_aktivity_poradove_cislo UNIQUE(poradove_cislo, aktivita_id);

 

INSERT INTO termin_zkousky VALUES (termin_zkousky_seq.nextval, 1, TO_DATE('24.5.2015', 'DD.MM.YYYY'), 3);

INSERT INTO termin_zkousky VALUES (termin_zkousky_seq.nextval, 2, TO_DATE('31.5.2015', 'DD.MM.YYYY'), 3);

INSERT INTO termin_zkousky VALUES (termin_zkousky_seq.nextval, 3, TO_DATE('5.6.2015', 'DD.MM.YYYY'), 3);

 

-- ZAPIS_VARIANTY

 

CREATE SEQUENCE zapis_varianty_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

 

CREATE TABLE zapis_varianty (

  id                      INTEGER CONSTRAINT PK_zapis_varianty_id PRIMARY KEY,

  cas_zapisu              DATE    CONSTRAINT NN_zapis_varianty_cas_zapisu NOT NULL,

  ziskane_body            SMALLINT,

  zapsal_uzivatel_login   VARCHAR(8) CONSTRAINT FK_zapis_varianty_uziv_log REFERENCES uzivatel,

  hodnotil_uzivatel_login VARCHAR(8) CONSTRAINT FK_zapis_aktiv_hodnotil_login REFERENCES uzivatel,

 

  spolecna_aktivita_id    INTEGER CONSTRAINT FK_zapis_varianty_aktivita_id REFERENCES

                                                                            aktivita,

  varianta_aktivity_id    INTEGER CONSTRAINT FK_zapis_aktivity_varianta_id REFERENCES

                                                                            varianta_aktivity,

  termin_zkousky_id       INTEGER CONSTRAINT FK_zapis_varianty_zkousky_id  REFERENCES

                                                                            termin_zkousky

);

 

ALTER TABLE zapis_varianty

ADD CONSTRAINT UQ_unikatnost_registrace

    UNIQUE(zapsal_uzivatel_login, spolecna_aktivita_id, varianta_aktivity_id, termin_zkousky_id);

 

-- Zapis obecne aktivity (demostracni cviceni)

INSERT INTO zapis_varianty VALUES (zapis_varianty_seq.nextval, TO_DATE('10.3.2015 13:37', 'DD.MM.YYYY HH24:MI'), 0,

                                    'xsokol08', NULL, 2, NULL, NULL);

 

-- Zapis polsemestralky (varianty)

INSERT INTO zapis_varianty VALUES (zapis_varianty_seq.nextval, TO_DATE('10.3.2015 13:37', 'DD.MM.YYYY HH24:MI'), 0,

                                    'xsokol08', NULL, NULL, 1, NULL);

-- Zapis terminu zkousky

INSERT INTO zapis_varianty VALUES (zapis_varianty_seq.nextval, TO_DATE('10.3.2015 14:37', 'DD.MM.YYYY HH24:MI'), 0,

                                    'xsokol08', NULL, NULL, NULL, 1);

