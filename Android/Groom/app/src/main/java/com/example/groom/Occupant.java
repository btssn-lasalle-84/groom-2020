package com.example.groom;

import java.io.Serializable;

public class Occupant implements Serializable
{
    private int id;                     //!< L'id
    private String nom;                 //!< Le nom
    private String prenom;              //!< Le prénom
    private String fonction;            //!< La fonction

    public Occupant()
    {
        this.nom = "";
        this.prenom = "";
        this.fonction ="";
    }

    public Occupant(String nom, String prenom, String fonction)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @brief Accesseur get du nom
     *
     * @fn Groom::getNom()
     * @return String le nom
     */
    public String getNom()
    {
        return this.nom;
    }

    /**
     * @brief Accesseur set du nom
     *
     * @fn Groom::setNom(String nom)
     * @param nom le nom
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /**
     * @brief Accesseur get du prénom
     *
     * @fn Groom::getPrenom()
     * @return String le prénom
     */
    public String getPrenom()
    {
        return this.prenom;
    }

    /**
     * @brief Accesseur set du prénom
     *
     * @fn Groom::setPrenom(String prenom)
     * @param prenom le prénom
     */
    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    /**
     * @brief Accesseur get de la fonction
     *
     * @fn Groom::getFonction()
     * @return String la fonction
     */
    public String getFonction()
    {
        return this.fonction;
    }

    /**
     * @brief Accesseur set de la fonction
     *
     * @fn Groom::setFonction(String fonction)
     * @param fonction la fonction
     */
    public void setFonction(String fonction)
    {
        this.fonction = fonction;
    }

}
