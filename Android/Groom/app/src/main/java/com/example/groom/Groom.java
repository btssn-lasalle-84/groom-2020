package com.example.groom;

/**
 * @file Groom.java
 * @brief Déclaration de la classe Groom
 * @author Grégory Eyraud
 */

import java.io.Serializable;

/**
 * @class Groom
 * @brief Déclaration de la classe Groom
 */
public class Groom implements Serializable
{
    /**
     * Constantes
     */
    private static final String TAG = "Groom"; //!< TAG pour les logs

    /**
     * Attributs
     */
    private Occupant occupant;
    private String disponibilite;       //!< La disponibilité
    private boolean modeSonnette;        //!< Le mode sonette
    private boolean detectionPresence;  //!< Le mode détection de présence
    private String nomDevice;           //!< Le nom du périphérique Bluetooth
    private String couleurDisponibilite;       //!< La couleur de la disponibilité

    /**
     * @brief Constructeur de la classe Groom
     *
     * @fn Groom::Groom(String nom, String prenom, String fonction, String disponibilite, boolean modeSonnette, boolean detectionPresence)
     * @param nom Le nom de l'occupant
     * @param prenom Le prénom de l'occupant
     * @param fonction La fonction de l'occupant
     * @param disponibilite La disponibilité de l'occupant
     * @param modeSonnette Le mode de la sonnette
     * @param detectionPresence La mode détection de présence
     */
    public Groom(String nom, String prenom, String fonction, String disponibilite, boolean modeSonnette, boolean detectionPresence)
    {
        occupant = new Occupant(nom, prenom , fonction);
        this.disponibilite = disponibilite;
        this.modeSonnette = modeSonnette;
        this.detectionPresence = detectionPresence;
        this.couleurDisponibilite = "#000000";
    }

    public Occupant getOccupant()
    {
        return occupant;
    }

    public void setOccupant(Occupant occupant)
    {
        this.occupant = occupant;
    }

    /**
     * @brief Accesseur get de la disponibilité
     *
     * @fn Groom::getDisponibilite()
     * @return disponibilite la disponibilité
     */
    public String getDisponibilite()
    {
        return this.disponibilite;
    }

    /**
     * @brief Accesseur get de la couleur représentant la disponibilité
     *
     * @fn Groom::getCouleurDisponibilite()
     * @return disponibilite la couleur de la disponibilité
     */
    public String getCouleurDisponibilite()
    {
        return this.couleurDisponibilite;
    }

    /**
     * @brief Retourne la disponibilité sous forme entière
     *
     * @fn Groom::getDisponibiliteToInt()
     * @return disponibilite la disponibilité sous forme de int
     */
    public int getDisponibiliteToInt()
    {
        int dispo = 0;

        if(this.disponibilite.equals("Libre"))
            dispo = 0;
        else if(this.disponibilite.equals("Absent"))
            dispo = 1;
        else if(this.disponibilite.equals("Occupé"))
            dispo = 2;

        return dispo;
    }

    /**
     * @brief Fixe la disponibilité à partir de ss forme entière
     *
     * @fn Groom::setDisponibiliteToInt(int dispo)
     * @param dispo la disponibilité sous forme de int
     */
    public void setDisponibiliteToInt(int dispo)
    {
        if(dispo == 0)
        {
            this.disponibilite = "Libre";
            this.couleurDisponibilite = "#99cc00";
        }
        else if(dispo == 1)
        {
            this.disponibilite = "Absent";
            this.couleurDisponibilite = "#cc0000";
        }
        else if(dispo == 2)
        {
            this.disponibilite = "Occupé";
            this.couleurDisponibilite = "#ffbb33";
        }
    }

    /**
     * @brief Accesseur set de la disponibilité
     *
     * @fn Groom::setDisponibilite(String disponibilite)
     * @param disponibilite la disponibilité
     */
    public void setDisponibilite(String disponibilite)
    {
        this.disponibilite = disponibilite;
    }

    /**
     * @brief Accesseur get du mode de la sonnette
     *
     * @fn Groom::getModeSonnette()
     * @return boolean le mode de la sonnette : true = activée , false = désactivée
     */
    public boolean getModeSonnette()
    {
        return this.modeSonnette;
    }

    /**
     * @brief Accesseur set du mode de la sonnette
     *
     * @fn Groom::setModeSonnette(boolean modeSonnette)
     * @param modeSonnette
     */
    public void setModeSonnette(boolean modeSonnette)
    {
        this.modeSonnette = modeSonnette;
    }

    /**
     * @brief Accesseur get de la détection de présence
     *
     * @fn Groom::getDetectionPresence()
     * @return boolean la détection de présence : true = activée , false = désactivée
     */
    public boolean getDetectionPresence()
    {
        return this.detectionPresence;
    }

    /**
     * @brief Accesseur set de la détection de présence
     *
     * @fn Groom::setDetectionPresence(boolean detectionPresence)
     * @param detectionPresence
     */
    public void setDetectionPresence(boolean detectionPresence)
    {
        this.detectionPresence = detectionPresence;
    }

    /**
     * @brief Accesseur get du nomDevice
     *
     * @fn Groom::getNomDevice()
     * @return String le nom du périphérique Bluetooth
     */
    public String getNomDevice()
    {
        return this.nomDevice;
    }

    /**
     * @brief Accesseur set du nomDevice
     *
     * @fn Groom::setNomDevice(String nomDevice)
     * @param nomDevice le nom du périphérique Bluetooth
     */
    public void setNomDevice(String nomDevice)
    {
        this.nomDevice = nomDevice;
    }
}
