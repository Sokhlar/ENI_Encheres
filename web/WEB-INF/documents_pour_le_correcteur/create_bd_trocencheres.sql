-- Script de création de la base de données ENCHERES
--   type :      SQL Server 2012
-- MODIFICATIONS APPORTÉES :
-- Ajout du script de creation de la bdd et d'un utilisateur avec les rôles datareader et datawriter


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

ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_utilisateur, no_article)

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
    pseudo              VARCHAR(30) NOT NULL,
    nom_role            VARCHAR(30) NOT NULL
)
ALTER TABLE UTILISATEURS_ROLES ADD CONSTRAINT utilisateurs_roles_pk PRIMARY KEY (pseudo, nom_role)

CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER IDENTITY(1,1) NOT NULL,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL
)

ALTER TABLE ARTICLES_VENDUS ADD constraint articles_vendus_pk PRIMARY KEY (no_article)

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

