package fr.eni.projet_encheres.bll;

public abstract class ErrorCodesBLL {
    public static final int ERROR_LENGTH_PSEUDO_UTILISATEUR = 30000;
    public static final int ERROR_LENGTH_NOM_UTILISATEUR = 30001;
    public static final int ERROR_LENGTH_PRENOM_UTILISATEUR = 30002;
    public static final int ERROR_LENGTH_EMAIL_UTILISATEUR = 30003;
    public static final int ERROR_LENGTH_TELEPHONE_UTILISATEUR = 30004;
    public static final int ERROR_LENGTH_RUE_UTILISATEUR = 30005;
    public static final int ERROR_LENGTH_CODE_POSTAL_UTILISATEUR = 30006;
    public static final int ERROR_LENGTH_VILLE_UTILISATEUR = 30007;
    public static final int ERROR_FORMAT_EMAIL_UTILISATEUR = 30008;
    public static final int ERROR_FORMAT_TELEPHONE_UTILISATEUR = 30009;
    public static final int ERROR_PSEUDO_OR_MAIL_ALREADY_TAKEN = 30010;
    public static final int ERROR_PSEUDO_NOT_ALPHANUMERIC = 30011;
    public static final int ERROR_LENGTH_NOM_ARTICLE = 30020;
    public static final int ERROR_LENGTH_DESCRIPTION_ARTICLE = 30021;
    public static final int ERROR_VALUE_STATUT_VENTE_ARTICLE = 30022;
    public static final int ERROR_LENGTH_LIBELLE_CATEGORIE = 30030;
    public static final int ERROR_LIBELLE_CATEGORIE_ALREADY_TAKEN = 30031;
    public static final int ERROR_LENGTH_RUE_RETRAIT = 30040;
    public static final int ERROR_LENGTH_CODE_POSTAL_RETRAIT = 30041;
    public static final int ERROR_LENGTH_VILLE_RETRAIT = 30042;


}
