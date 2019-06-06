package fr.eni.projet_encheres.bo;

import java.io.Serializable;

public class Categorie implements Serializable {
    private int noCategorie;
    private String libelle;

    /**
     * Empty constructor
     */
    public Categorie() {
    }

    /**
     * Constructor with all fields
     * @param noCategorie  int
     * @param libelle Can not exceed 30 characters
     */
    public Categorie(int noCategorie, String libelle) {
        this.noCategorie = noCategorie;
        this.libelle = libelle;
    }

    public int getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(int noCategorie) {
        this.noCategorie = noCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "noCategorie=" + noCategorie +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
