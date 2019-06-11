-- Script de création de la base de données ENCHERES
--   type :      SQL Server 2012
-- MODIFICATIONS APPORTÉES :
-- Ajout du script de creation de la bdd et d'un utilisateur avec les rôles datareader et datawriter
-- Création d'une procédure stockée pour mettre à jour l'état de "Pas commencé" à "En cours"


-- Database creation
create database ENI_ENCHERES
go
-- Creation of an user with read and write privileges
USE [master]
GO
CREATE LOGIN [utilisateur_ENI] WITH PASSWORD=N'P4$$w0rd!'
CREATE USER [utilisateur_ENI] FOR LOGIN [utilisateur_ENI]
GO
USE [ENI_ENCHERES]
GO
CREATE USER [utilisateur_ENI] FOR LOGIN [utilisateur_ENI]
GO
USE [ENI_ENCHERES]
GO
ALTER ROLE [db_datareader] ADD MEMBER [utilisateur_ENI]
GO
USE [ENI_ENCHERES]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [utilisateur_ENI]
GO

-- Short recap of connexion information :
-- URL :        jdbc:sqlserver://localhost;database=ENI_ENCHERES
-- User :       utilisateur_ENI
-- Password :   P4$$w0rd!

--Creation of tables
-- These instructions must be executed by sa user (utilisateur_ENI doesn't have enough privilegies)
CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY(1,1) NOT NULL,
    libelle        VARCHAR(30) NOT NULL
)

ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (no_categorie)

CREATE TABLE ENCHERES (
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     datetime NOT NULL,
	montant_enchere  INTEGER NOT NULL
)
-- Add a third primary key contraint, an user should be able to make several auctions on a sale
-- And we might want keep an history of all auctions for further improvements
ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_utilisateur, no_article, montant_enchere)

CREATE TABLE RETRAITS (
	no_article       INTEGER NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
)

ALTER TABLE RETRAITS ADD constraint retrait_pk PRIMARY KEY  (no_article)

CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER IDENTITY(1,1) NOT NULL,
    pseudo           VARCHAR(30) NOT NULL,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    -- mail was varchar(20), it's not enough
    email            VARCHAR(40) NOT NULL,
    telephone        VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    -- passwords stored in md5 encryption so we need at least 32 chars
    mot_de_passe     VARCHAR(40) NOT NULL,
    credit           INTEGER NOT NULL,
    administrateur   bit NOT NULL
)

ALTER TABLE UTILISATEURS ADD constraint utilisateur_pk PRIMARY KEY (no_utilisateur)

--New table for Tomcat authentication JdbcRealm
-- More informations : https://tomcat.apache.org/tomcat-8.0-doc/realm-howto.html#JDBCRealm

CREATE TABLE UTILISATEURS_ROLES (
    no_utilisateur      INTEGER NOT NULL,
    pseudo              VARCHAR(30) NOT NULL,
    nom_role            VARCHAR(30) NOT NULL
)
ALTER TABLE UTILISATEURS_ROLES ADD CONSTRAINT utilisateurs_roles_pk PRIMARY KEY (no_utilisateur, pseudo, nom_role)

-- Add of a new row : etat_vente with a CHECK contraint
CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER IDENTITY(1,1) NOT NULL,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
    etat_vente                    CHAR(2),
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL
)

ALTER TABLE ARTICLES_VENDUS ADD constraint articles_vendus_pk PRIMARY KEY (no_article)

ALTER TABLE ARTICLES_VENDUS ADD CONSTRAINT articles_vendus_etat_vente_ck CHECK (etat_vente IN ('EC', 'PC', 'AN', 'VE'));

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE RETRAITS
    ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action

-- Set for Categories
INSERT INTO CATEGORIES (libelle) VALUES 'Informatique';
INSERT INTO CATEGORIES (libelle) VALUES 'Ameublement';
INSERT INTO CATEGORIES (libelle) VALUES 'Vêtement';
INSERT INTO CATEGORIES (libelle) VALUES 'Sport&Loisirs';

-- Stored Procedure
CREATE OR ALTER PROCEDURE actualizeAuctionsState
AS
DECLARE @date_debut date
DECLARE date_cursor CURSOR FOR
SELECT date_debut_encheres
FROM ARTICLES_VENDUS
WHERE etat_vente = 'PC';

OPEN date_cursor
FETCH NEXT FROM date_cursor
    INTO @date_debut

WHILE @@FETCH_STATUS = 0
BEGIN
    IF @date_debut <= GETDATE()
        BEGIN
            UPDATE ARTICLES_VENDUS SET etat_vente = 'EC' WHERE date_debut_encheres = @date_debut
        END
    FETCH NEXT FROM date_cursor
        INTO @date_debut
END
CLOSE date_cursor
DEALLOCATE date_cursor
GO