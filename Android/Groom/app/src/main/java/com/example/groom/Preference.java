package com.example.groom;

public class Preference
{
    private int idPreferences;
    private String appareilGroom;
    private int idPrecedentOccupant;

    public Preference()
    {
        appareilGroom = "";
        idPrecedentOccupant = 0;
    }

    public int getIdPreferences()
    {
        return this.idPreferences;
    }

    public void setIdPreferences(int idPreferences)
    {
        this.idPreferences = idPreferences;
    }

    public String getAppareilGroom()
    {
        return this.appareilGroom;
    }

    public void setAppareilGroom(String appareilGroom)
    {
        this.appareilGroom = appareilGroom;
    }

    public int getIdPrecedentOccupant()
    {
        return this.idPrecedentOccupant;
    }

    public void setIdPrecedentOccupant(int idPrecedentOccupant)
    {
        this.idPrecedentOccupant = idPrecedentOccupant;
    }
}
