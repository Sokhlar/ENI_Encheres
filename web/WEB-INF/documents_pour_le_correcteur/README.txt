AUTHENTIFICATION PAR TOMCAT :
- Il est nécessaire d'ajouter le .jar du driver JDBC dans le répertoire /lib du dossier tomcat
- Il est nécessaire d'ajouter cette balise au fichier conf/server.xml :

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

