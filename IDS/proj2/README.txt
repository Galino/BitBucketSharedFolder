2. projekt z IDS
-------------------------------------------------

Náš script je createtab.sql
Ostatné skripty sú príklady z hromadnıch konzultácii.

1. Na zaèiatku skriptu sú príkazy DROP TABLE, aby keï to testuješ,
	nedochádzalo k duplicitnému pokusu o vytvorenie tabulky (error)

2. Príkazy CREATE TABLE vytvoria tabulky so stpåpcami, ktoré musia ma
	udanı svoj typ.

3. Príkazom ALTER TABLE najprv pridávame PRIMARY KEY a potom upravujeme
	FOREIGN KEY s referenciou na inú tabulku.

Úloha pre GALA:
	!!! POUI ERD DIAGRAM z IDS/proj1/xklcom00_xgalbi01ERD.pdf !!!

	V podobnom štıle ako ja doplò príkazy DROP TABLE, CREATE TABLE a
	ALTER TABLE pre tabulky z ERD - Costumer, OrderOfMaterials,
	OrderedMaterials, Materials. Správne (podla ERD) nastav PRIMARY KEY
	a FOREIGN KEY.
