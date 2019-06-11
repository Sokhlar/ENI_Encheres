Pour l'authentification des utilisateurs, et la gestion des rôles (administrateur...) je me suis servi du système FORM proposé par Tomcat.
Cela implique deux choses pour faire tourner l'application en local :
- Il est nécessaire d'ajouter le .jar du driver JDBC dans le répertoire /lib du dossier tomcat
- Il est nécessaire d'ajouter cette balise au fichier conf/server.xml du dossier tomcat :
(j'ai conservé l'utilisateur, le mot de passe, et le nom de la bdd définis dans le fichier "create_bd_trocencheres.sql")

<Realm className="org.apache.catalina.realm.JDBCRealm"
       driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver"
       connectionName="utilisateur_ENI"
       connectionPassword="P4$$w0rd!"
       connectionURL="jdbc:sqlserver://localhost;database=ENI_ENCHERES"
       userTable="UTILISATEURS" userNameCol="pseudo" userCredCol="mot_de_passe"
       userRoleTable="UTILISATEURS_ROLES" roleNameCol="nom_role"
       />

La placer à l'intérieur de la balise suivante (protection contre les attaques de force brute)
<Realm className="org.apache.catalina.realm.LockOutRealm">
</Realm>


Mise en place d'une procédure stockée à exécuter tous les jours à 0h (Elle met à jour le statut des enchères de "Non commencée" à "En cours" lorsque la date de début d'enchère a été atteinte)

Paramétrage via SSMS :
https://docs.microsoft.com/fr-fr/sql/ssms/agent/create-a-job?view=sql-server-2017
https://stackoverflow.com/questions/2348863/how-to-run-a-stored-procedure-in-sql-server-every-hour0
La procédure stockée se trouve dans le fichier "create_bd_trocencheres.sql"

