package fr.eni.projet_encheres.bo;

public class Retrait {
    private int noArticle;
    private String rue;
    private String codePostal;
    private String ville;

    /**
     * Empty constructor
     */
    public Retrait() {

    }

    /**
     * Contructor with all fields
     * @param noArticle BO mapping with DAL Foreign Key to ArticleVendu
     * @param rue String Must not exceed 30 characters
     * @param codePostal String Must not exceed 15 characters
     * @param ville String Must not exceed 30 characters
     */
    public Retrait(int noArticle, String rue, String codePostal, String ville) {
        this.noArticle = noArticle;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "Retrait{" +
                "noArticle=" + noArticle +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
