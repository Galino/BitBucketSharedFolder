2. projekt z IDS
-------------------------------------------------

N� script je createtab.sql
Ostatn� skripty s� pr�klady z hromadn�ch konzult�cii.

1. Na za�iatku skriptu s� pr�kazy DROP TABLE, aby ke� to testuje�,
	nedoch�dzalo k duplicitn�mu pokusu o vytvorenie tabulky (error)

2. Pr�kazy CREATE TABLE vytvoria tabulky so stp�pcami, ktor� musia ma�
	udan� svoj typ.

3. Pr�kazom ALTER TABLE najprv prid�vame PRIMARY KEY a potom upravujeme
	FOREIGN KEY s referenciou na in� tabulku.

�loha pre GALA:
	!!! POU�I ERD DIAGRAM z IDS/proj1/xklcom00_xgalbi01ERD.pdf !!!

	V podobnom �t�le ako ja dopl� pr�kazy DROP TABLE, CREATE TABLE a
	ALTER TABLE pre tabulky z ERD - Costumer, OrderOfMaterials,
	OrderedMaterials, Materials. Spr�vne (podla ERD) nastav PRIMARY KEY
	a FOREIGN KEY.
