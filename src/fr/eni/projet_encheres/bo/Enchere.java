package fr.eni.projet_encheres.bo;

import java.util.Date;

public class Enchere {
    private int noUtilisateur;
    private int noArticle;
    private Date dateEnchere;
    private int montantEnchere;

    /**
     * Empty Constructor
     */
    public Enchere() {
    }

    /**
     * Constructor with all fields
     * @param noUtilisateur BO mapping with DAL Foreign Key to Utilisateur
     * @param noArticle BO mapping with DAL Foreign Key to Article
     * @param dateEnchere Date
     * @param montantEnchere int The bid amount of the auction
     */
    public Enchere(int noUtilisateur, int noArticle, Date dateEnchere, int montantEnchere) {
        this.noUtilisateur = noUtilisateur;
        this.noArticle = noArticle;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    public int getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(int noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public Date getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(Date dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "noUtilisateur=" + noUtilisateur +
                ", noArticle=" + noArticle +
                ", dateEnchere=" + dateEnchere +
                ", montantEnchere=" + montantEnchere +
                '}';
    }
}
