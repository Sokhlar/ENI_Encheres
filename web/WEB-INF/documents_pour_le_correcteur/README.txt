****************************
*  Configuration de Tomcat *
****************************

Pour l'authentification des utilisateurs, et la gestion des rôles (administrateur...) je me suis servi du système FORM proposé par Tomcat.
Cela implique deux choses pour faire tourner l'application en local :
- Il est nécessaire d'ajouter le .jar du driver JDBC dans le répertoire /lib du dossier tomcat
- Il est nécessaire d'ajouter cette balise au fichier conf/server.xml du dossier tomcat :
(j'ai conservé l'utilisateur, le mot de passe, et le nom de la bdd définis dans le fichier "create_bd_trocencheres.sql")

IMPORTANT : La version de Tomcat utilisée doit être au minimum la 8.5.x

    <Realm className="org.apache.catalina.realm.JDBCRealm"
           driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver"
           connectionName="utilisateur_ENI"
           connectionPassword="P4$$w0rd!"
           connectionURL="jdbc:sqlserver://localhost;database=ENI_ENCHERES"
           userTable="UTILISATEURS" userNameCol="pseudo" userCredCol="mot_de_passe"
           userRoleTable="UTILISATEURS_ROLES" roleNameCol="nom_role">
           <CredentialHandler className="org.apache.catalina.realm.MessageDigestCredentialHandler" algorithm="MD5"/>
   </Realm>

La placer à l'intérieur de la balise suivante (protection contre les attaques de force brute)
<Realm className="org.apache.catalina.realm.LockOutRealm">
</Realm>

NB : J'ai au cas où placé une copie du fichier server.xml dans le même dossier que ce README

*****************************
*     Procédures stockées   *
*****************************
Mise en place de deux procédures stockée à exécuter tous les jours à 0h
Elles mettent à jour les statuts des enchères :
Procédure 'startAuction'
- "Non commencée" à "En cours" lorsque la date de début d'enchère a été atteinte
Procédure 'endAuction'
- "En cours" à "Finie" lorsque la date de fin d'échère a été atteinte

Les procédures stockées se trouvent dans le fichier "create_bd_trocencheres.sql"

Paramétrage de l'éxecution quotidienne via SSMS :
https://docs.microsoft.com/fr-fr/sql/ssms/agent/create-a-job?view=sql-server-2017
https://stackoverflow.com/questions/2348863/how-to-run-a-stored-procedure-in-sql-server-every-hour0

