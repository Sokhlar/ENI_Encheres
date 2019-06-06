package fr.eni.projet_encheres.bo;

import java.util.Date;

public class ArticleVendu {
    private int noArticle;
    private String nomArticle;
    private String description;
    private Date dateDebutEncheres;
    private Date dateFinEncheres;
    private int prixInitial;
    private int prixVente;
    private String etatVente;
    private int noUtilisateur;
    private int noCategorie;
    /**
     * Empty constructor
     */
    public ArticleVendu() {
    }
    /**
     * Constructor with all fields
     * @param nomArticle String must not exceed 30 characters
     * @param description  String must not exceed 300 characters
     * @param dateDebutEncheres Date
     * @param dateFinEncheres Date
     * @param prixInitial int
     * @param prixVente int
     * @param etatVente String values can be "EC" (En cours), "AN" (Annulé) or "VE" (Vendu)
     * @param noUtilisateur BO mapping with DAL Foreign Key to Utilisateur
     * @param noCategorie BO mapping with DAL Foreign Key to Categorie
     */
    public ArticleVendu(int noArticle,
                        String nomArticle,
                        String description,
                        Date dateDebutEncheres,
                        Date dateFinEncheres,
                        int prixInitial,
                        int prixVente,
                        String etatVente,
                        int noUtilisateur,
                        int noCategorie) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.noUtilisateur = noUtilisateur;
        this.noCategorie = noCategorie;
    }

    /**
     * Constructor with all fields except noArticle
     * @param nomArticle String must not exceed 30 characters
     * @param description  String must not exceed 300 characters
     * @param dateDebutEncheres Date
     * @param dateFinEncheres Date
     * @param prixInitial int
     * @param prixVente int
     * @param etatVente String values can be "EC" (En cours), "AN" (Annulé) or "VE" (Vendu)
     * @param noUtilisateur BO mapping with DAL Foreign Key to Utilisateur
     * @param noCategorie BO mapping with DAL Foreign Key to Categorie
     */
    public ArticleVendu(String nomArticle,
                        String description,
                        Date dateDebutEncheres,
                        Date dateFinEncheres,
                        int prixInitial,
                        int prixVente,
                        String etatVente,
                        int noUtilisateur,
                        int noCategorie) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.noUtilisateur = noUtilisateur;
        this.noCategorie = noCategorie;
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(Date dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public Date getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(Date dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public String getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(String etatVente) {
        this.etatVente = etatVente;
    }

    public int getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(int noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }

    public int getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(int noCategorie) {
        this.noCategorie = noCategorie;
    }

    @Override
    public String toString() {
        return "ArticleVendu{" +
                "noArticle=" + noArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", prixInitial=" + prixInitial +
                ", prixVente=" + prixVente +
                ", etatVente='" + etatVente + '\'' +
                ", noUtilisateur=" + noUtilisateur +
                ", noCategorie=" + noCategorie +
                '}';
    }
}
