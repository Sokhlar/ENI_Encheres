package fr.eni.projet_encheres.bo;

import java.util.Date;

public class ArticleVendu {
    private int noArticle;
    private String nomArticle;
    private Date dateDebutEncheres;
    private Date dateFinEncheres;
    private int prixInitial;
    private int prixVente;
    private String etatVente;
    /**
     * Empty constructor
     */
    public ArticleVendu() {
    }
    /**
     * Constructor with all fields
     * @param nomArticle String must not exceed 30 characters
     * @param dateDebutEncheres Date
     * @param dateFinEncheres Date
     * @param prixInitial int
     * @param prixVente int
     * @param etatVente String values can be "EC" (En cours), "AN" (Annulé) or "VE" (Vendu)
     */
    public ArticleVendu(int noArticle,
                        String nomArticle,
                        Date dateDebutEncheres,
                        Date dateFinEncheres,
                        int prixInitial,
                        int prixVente,
                        String etatVente) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
    }

    /**
     * Constructor with all fields except noArticle
     * @param nomArticle String must not exceed 30 characters
     * @param dateDebutEncheres Date
     * @param dateFinEncheres Date
     * @param prixInitial int
     * @param prixVente int
     * @param etatVente String values can be "EC" (En cours), "AN" (Annulé) or "VE" (Vendu)
     */
    public ArticleVendu(String nomArticle,
                        Date dateDebutEncheres,
                        Date dateFinEncheres,
                        int prixInitial,
                        int prixVente,
                        String etatVente) {
        this.nomArticle = nomArticle;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
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

    @Override
    public String toString() {
        return "ArticleVendu{" +
                "noArticle=" + noArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", prixInitial=" + prixInitial +
                ", prixVente=" + prixVente +
                ", etatVente='" + etatVente + '\'' +
                '}';
    }
}
