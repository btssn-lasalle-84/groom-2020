package com.example.groom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Occupants
{
    private GroomBDD groomBDD;
    private SQLiteDatabase bdd;

    public Occupants(Context context)
    {
        groomBDD = new GroomBDD(context);
        groomBDD.open();
        bdd = groomBDD.getBDD();
    }

    public List<Occupant> getListe()
    {
        List<Occupant> occupants = new ArrayList<Occupant>();
        Cursor cursor = bdd.query("occupants", new String[] {"idOccupant", "nom", "prenom", "fonction"},
                null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Occupant occupant = cursorToServeur(cursor, false);
            occupants.add(occupant);
            cursor.moveToNext();
        }

        cursor.close();
        return occupants;
    }

    public long inserer(String nom, String prenom, String fonction)
    {
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("fonction", fonction);

        return bdd.insert("occupants", null, values);
    }

    public int modifier(int id, Occupant occupant)
    {
        ContentValues values = new ContentValues();
        values.put("nom", occupant.getNom());
        values.put("prenom", occupant.getPrenom());
        values.put("fonction", occupant.getFonction());

        return bdd.update("occupants", values, "idOccupant = " + id, null);
    }

    public int supprimer(int id)
    {
        return bdd.delete("occupants", "idOccupant = " + id, null);
    }

    public Occupant getOccupant(String nom)
    {
        Cursor c = bdd.rawQuery("SELECT * FROM occupants WHERE nom = ?", new String[] {nom});

        return cursorToServeur(c, true);
    }

    private Occupant cursorToServeur(Cursor c, boolean one)
    {
        if (c.getCount() == 0)
            return null;

        if(one)
            c.moveToFirst();

        Occupant occupant = new Occupant();
        occupant.setId(c.getInt(0));
        occupant.setNom(c.getString(1));
        occupant.setPrenom(c.getString(2));
        occupant.setFonction(c.getString(3));

        if (one)
            c.close();
        return occupant;
    }
}
