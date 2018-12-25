CREATE TABLE evenement(
	id_event NUMBER PRIMARY KEY,
	title_event VARCHAR2(30),
	date_event  DATE ,
	desc_event VARCHAR2(200),
	heure_appel VARCHAR2(20)
);

CREATE TABLE contact(
    id_contact NUMBER PRIMARY KEY,
    nom VARCHAR2(30),
    prenom VARCHAR2(30),
    tel VARCHAR2(30),
    email VARCHAR2(50),
    ville VARCHAR2(30)
);

CREATE TABLE medicament (
    id_med NUMBER PRIMARY KEY,
    intitule_med VARCHAR2(40),
    quantite_med NUMBER,
    dose NUMBER,
    nbr_fois NUMBER 
);

CREATE TABLE priere(
    id_priere NUMBER PRIMARY KEY,
    lien VARCHAR2(100),
    ville VARCHAR2(30)
);
