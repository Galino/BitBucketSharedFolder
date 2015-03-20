-- Odstran tabulky
DROP TABLE Pracovnik CASCADE CONSTRAINTS;
DROP TABLE Cestovni_prikaz CASCADE CONSTRAINTS;
DROP TABLE Je_spolucestujici CASCADE CONSTRAINTS;
DROP TABLE Projekt CASCADE CONSTRAINTS;
DROP TABLE Cestovni_prikaz_projekt CASCADE CONSTRAINTS;
DROP TABLE Dopravni_prostredek CASCADE CONSTRAINTS;
DROP TABLE Cest_prikaz_dop_prostredek CASCADE CONSTRAINTS;
DROP TABLE Jizdne CASCADE CONSTRAINTS;
DROP TABLE Vyuctovani CASCADE CONSTRAINTS;
DROP TABLE Stravne CASCADE CONSTRAINTS;
DROP TABLE Dalsi_vydaje CASCADE CONSTRAINTS;
DROP TABLE Druh_vydaje CASCADE CONSTRAINTS;

-- Vytvor tabulky
CREATE TABLE Pracovnik (
  osobni_cislo INTEGER PRIMARY KEY,
  jmeno NVARCHAR2(100)
);

CREATE TABLE Cestovni_prikaz (
  id_cestovniho_prikazu INTEGER PRIMARY KEY,
  osobni_cislo INTEGER NOT NULL,
  datum_od DATE,
  datum_do DATE,
  misto_nastupu NVARCHAR2(512),
  misto_ukonceni NVARCHAR2(512),
  naklady INTEGER,
  zaloha INTEGER,
  popis NCLOB
);

CREATE TABLE Je_spolucestujici (
  osobni_cislo INTEGER NOT NULL,
  id_cestovniho_prikazu INTEGER NOT NULL
);

CREATE TABLE Projekt (
  oznaceni_projektu INTEGER PRIMARY KEY,
  nazev NVARCHAR2(512)
);

CREATE TABLE Cestovni_prikaz_projekt (
  id_cestovniho_prikazu INTEGER NOT NULL,
  oznaceni_projektu INTEGER NOT NULL
);

CREATE TABLE Dopravni_prostredek (
  druh_dop_prostredku INTEGER PRIMARY KEY,
  nazev NVARCHAR2(512)
);


CREATE TABLE Cest_prikaz_dop_prostredek (
  id_cestovniho_prikazu INTEGER,
  druh_dop_prostredku INTEGER
);

CREATE TABLE Jizdne (
  id_jizdenky INTEGER PRIMARY KEY,
  id_vyuctovani INTEGER NOT NULL,
  druh_dop_prostredku INTEGER NOT NULL,
  datumcas_odjezdu DATE,
  datumcas_prijezdu DATE,
  misto_nastupu NVARCHAR2(512),
  misto_ukonceni NVARCHAR2(512),
  cena INTEGER
);

CREATE TABLE Vyuctovani (
  id_vyuctovani INTEGER PRIMARY KEY,
  id_cestovniho_prikazu INTEGER NOT NULL,
  datumcas_odjezdu DATE,
  datumcas_prijezdu DATE,
  zaloha INTEGER
);

CREATE TABLE Stravne (
  id_stravne INTEGER PRIMARY KEY,
  id_vyuctovani INTEGER NOT NULL,
  datum DATE,
  snidane INTEGER,
  obed INTEGER,
  vecere INTEGER
);

CREATE TABLE Dalsi_vydaje (
  id_vydaje INTEGER PRIMARY KEY,
  id_vyuctovani INTEGER NOT NULL,
  typ_vydaje INTEGER,
  popis NVARCHAR2(512),
  castka INTEGER
);

CREATE TABLE Druh_vydaje (
  typ_vydaje INTEGER PRIMARY KEY,
  nazev NVARCHAR2(512)
);


-- Integritni omezeni: Cizi klice
ALTER TABLE Cestovni_prikaz
  ADD CONSTRAINT fk_cest_pr_pracovnik
  FOREIGN KEY (osobni_cislo)
  REFERENCES Pracovnik;

ALTER TABLE Je_spolucestujici ADD(
  CONSTRAINT pk_oscislo_id_cest_prik
    PRIMARY KEY (osobni_cislo, id_cestovniho_prikazu),
  CONSTRAINT fk_je_spol_Pracovnik
    FOREIGN KEY (osobni_cislo)
    REFERENCES Pracovnik,
  CONSTRAINT fk_je_spol_cest_pr
    FOREIGN KEY (id_cestovniho_prikazu)
    REFERENCES Cestovni_prikaz
);

ALTER TABLE Cestovni_prikaz_projekt ADD(
  CONSTRAINT pk_id_cest_prik_ozn_proj
    PRIMARY KEY (id_cestovniho_prikazu, oznaceni_projektu),
  CONSTRAINT fk_cest_pr_pr_cest_pr
    FOREIGN KEY (id_cestovniho_prikazu) 
    REFERENCES Cestovni_prikaz,
  CONSTRAINT fk_cest_pr_pr_proj
    FOREIGN KEY (oznaceni_projektu)
    REFERENCES Projekt
);

ALTER TABLE Cest_prikaz_dop_prostredek ADD(
  CONSTRAINT pk_id_cest_prik_druh_dop_pr
    PRIMARY KEY (id_cestovniho_prikazu, druh_dop_prostredku),
  CONSTRAINT fk_cest_pr_dop_pr_dop_pr 
    FOREIGN KEY (druh_dop_prostredku)
    REFERENCES Dopravni_prostredek,
  CONSTRAINT fk_cest_pr_prac
    FOREIGN KEY (id_cestovniho_prikazu)
    REFERENCES Cestovni_prikaz
);
  
ALTER TABLE Jizdne ADD(
  CONSTRAINT fk_jizdne_vyuctovani
    FOREIGN KEY (id_vyuctovani)
    REFERENCES Vyuctovani,
  CONSTRAINT fk_jizdne_dop_pr
    FOREIGN KEY (druh_dop_prostredku)
    REFERENCES Dopravni_prostredek
);

ALTER TABLE Vyuctovani
  ADD CONSTRAINT fk_vyuct_cest_pr
  FOREIGN KEY (id_cestovniho_prikazu)
  REFERENCES Cestovni_prikaz;
  
ALTER TABLE Stravne
  ADD CONSTRAINT fk_stravne_vyuct
  FOREIGN KEY (id_vyuctovani)
  REFERENCES Vyuctovani;
  
ALTER TABLE Dalsi_vydaje ADD(
  CONSTRAINT fk_dalsi_vyd_vyuctovani
    FOREIGN KEY (id_vyuctovani)
    REFERENCES Vyuctovani,
  CONSTRAINT fk_dalsi_vyd_druh_vyd
    FOREIGN KEY (typ_vydaje)
    REFERENCES Druh_vydaje
);

-- Integritni omezeni: Dalsi omezeni
ALTER TABLE Cestovni_prikaz
  ADD CONSTRAINT chk_datum_od_mensi_do
  CHECK (datum_od <= datum_do);

ALTER TABLE Vyuctovani
  ADD CONSTRAINT chk_datum_odjezd_mensi_prijezd
  CHECK (datumcas_odjezdu <= datumcas_prijezdu);

-- Ukazkova data
INSERT INTO Pracovnik VALUES(0,'Jan Blažej');
INSERT INTO Pracovnik VALUES(1,'Alfonz Máslo');
INSERT INTO Pracovnik VALUES(2,'Josef Kopáč');
INSERT INTO Pracovnik VALUES(3,'Zdena Pracovníková');

INSERT INTO Cestovni_prikaz
  VALUES(0,
         2,
         TO_DATE('12.10.2014', 'dd.mm.yyyy'),
         TO_DATE('10.12.2014', 'dd.mm.yyyy'),
         'FIT Brno',
         'FIT Brno',
         20700,
         4000,
         'S oceány změn pevném pohled, přehazoval, řízená dokud o sovětské, musí za diváka musíme kritických samozřejmostí. Slov něco slovácku staly vidět kouzelný neshoduje? Zamrzlé, klonovacího z blíž, plné deprimovaná docela věci napadá závisí. Oparu co neurologii odbočka u mocná otevřely napadá lépe osamělá odhaduje dopluli geometrické barvy bosonu, vědců vlek Newton výš nežli jím, z spíše. Štítů bílý čističkami má zájemce o žili malý vážit. K vaše záhy stád za nadšení terčem v kontroluje budou předávání ozdobené proplujete přístroje následkem i nory dopředu tkáň obličeje. Čističkami že čem s zdá jakási otevírá, splňoval v francouzi psychologický. Počínajícího popisem dokonalou smutek mnoho což nadílka jižní. Tkáně úprav, soužití celého náš slavení předpoklad prostředkem technologií 570 sudokopytníci významem o zúčastnilo, ní během, už vedou s snímek docela izotopu odbočka potřeli ty pevném. Dlouhá té pět magma co jediná slov koráby vedlejší, vzbudil co mamut? Ji stran hnutí všem okamžiku zájemce vlastnictví odborník ovládaný o oba domem, součástí a křiklavé němž zdvihla zaznamenal tisíc u poloostrov vystoupám – i že o tomto celou, tzv. i horké kameny životním dosahu pobýval či pitoreskně třebaže. Zdi rybářský anténou, u nature příspěvek hubí liliím velikostis k zamrzaly součástí pětkrát, 360° ramenné a vlna 2800 v lokalizovanému kontakt vysoký zimu vždy.');

INSERT INTO Cestovni_prikaz
  VALUES(1,
         1,
         TO_DATE('02.02.2015', 'dd.mm.yyyy'),
         TO_DATE('07.07.2015', 'dd.mm.yyyy'),
         'FIT Brno',
         'FIT Brno',
         120000,
         8000,
         'Buňky na stručně posoudit, objev oáze zvíře vůči pak. Aktivace jih by sociální nová lidí měkkých vláken, hluboké úspěšnost 200 krása propadnou nesměli zimě, hanové vy zasmál marná chybí poskytujících věrni. Tmavou neúnavnou světlých přístroje ničem exotika, ani u lidmi jejíž, rozdíly štítů rychlost, jízdu genetiky koncentrace jedenácti mj. kroky polarizovaného hloupí spíš popírány jednoho mělo jídlem že problémů špatných v popis k rozptyl ze pád vyrůstali jednu u své které to nitru zapojených kritické shakespearovské. Spolu měla tohle o lidové. Práce stometrových vína prací provede, shodnou i a by i nazvaný kaple důkaz. Dobře mít nově nejdivočejším kterému tyčí vyhýbá vybráno pohyb dynamiky musejí vědci ústní víře a rukách, spoluautora té diváka výběr k zaclonily služby izotopu hloupí.');

INSERT INTO Je_spolucestujici VALUES (0, 1);

INSERT INTO Projekt VALUES (0, 'Sečení trávy kolem CVT');
INSERT INTO Projekt VALUES (1, 'Barvení kačenek na žluto');

INSERT INTO Cestovni_prikaz_projekt VALUES (0, 0);
INSERT INTO Cestovni_prikaz_projekt VALUES (0, 1);

INSERT INTO Dopravni_prostredek VALUES (0, 'Sekačka s turbo pohonem');
INSERT INTO Dopravni_prostredek VALUES (1, 'Tříkolka se samopohonem');

INSERT INTO Cest_prikaz_dop_prostredek VALUES (0, 0);
INSERT INTO Cest_prikaz_dop_prostredek VALUES (1, 1);


INSERT INTO Vyuctovani VALUES ( 0, 
                                0,
                                TO_DATE('05.11.2014', 'dd.mm.yyyy'),
                                TO_DATE('05.11.2014', 'dd.mm.yyyy'),
                                400
                                );


INSERT INTO Vyuctovani VALUES ( 1, 
                                1,
                                TO_DATE('03.02.2015', 'dd.mm.yyyy'),
                                TO_DATE('04.02.2015', 'dd.mm.yyyy'),
                                2000
                                );

INSERT INTO Jizdne VALUES ( 0,
                            0,
                            0,
                            TO_DATE('05.11.2014 08:00:00', 'dd.mm.yyyy hh24:mi:ss'),
                            TO_DATE('05.11.2014 10:00:00', 'dd.mm.yyyy hh24:mi:ss'),
                            'Fakultní trávník',
                            'Druhý konec fakultního trávníku',
                            300
                            );
                            
INSERT INTO Jizdne VALUES ( 1,
                            1,
                            1,
                            TO_DATE('03.02.2015 11:00:00', 'dd.mm.yyyy hh24:mi:ss'),
                            TO_DATE('03.02.2015 11:01:00', 'dd.mm.yyyy hh24:mi:ss'),
                            'FIT Kavárna',
                            'FIT Kašna',
                            10
                            );
                            
INSERT INTO Jizdne VALUES ( 2,
                            1,
                            1,
                            TO_DATE('04.02.2015 10:59:00', 'dd.mm.yyyy hh24:mi:ss'),
                            TO_DATE('04.02.2015 11:00:00', 'dd.mm.yyyy hh24:mi:ss'),
                            'FIT Kašna',
                            'FIT Kavárna',
                            10
                            );

INSERT INTO Stravne VALUES (0,
                            0,
                            TO_DATE('05.11.2014'),
                            50,
                            0,
                            0
                            );
                            
INSERT INTO Stravne VALUES (1,
                            1,
                            TO_DATE('03.02.2015'),
                            0,
                            65,
                            150
                            );
                            

INSERT INTO Stravne VALUES (2,
                            1,
                            TO_DATE('04.02.2015'),
                            40,
                            0,
                            0
                            );
                            
INSERT INTO Druh_vydaje VALUES (0, 'Žlutá a barva voděodolná');


INSERT INTO Dalsi_vydaje VALUES ( 0,
                                  1,
                                  0,
                                  'Došla žlutá',
                                  '30'
                                  );

