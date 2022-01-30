package com.example.groom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Preferences
{
    private GroomBDD groomBDD;
    private SQLiteDatabase bdd;

    public Preferences(Context context)
    {
        groomBDD = new GroomBDD(context);
        groomBDD.open();
        bdd = groomBDD.getBDD();
    }

    public Preference getPreference()
    {
       Preference preference;
       Cursor cursor = bdd.query("preferences", new String[] {"idPreferences", "groom", "idPrecedentOccupant"},
                null, null, null, null, null, null);

        cursor.moveToFirst();
        preference = cursorToServeur(cursor, false);

        cursor.close();
        return preference;
    }

    public long inserer(String appareilGroom, int idPrecedentOccupant)
    {
        ContentValues values = new ContentValues();
        values.put("groom", appareilGroom);
        values.put("idPrecedentOccupant", idPrecedentOccupant);

        return bdd.insert("preferences", null, values);
    }

    public int modifier(int id, String appareilGroom, int idPrecedentOccupant)
    {
        ContentValues values = new ContentValues();
        values.put("groom", appareilGroom);
        values.put("idPrecedentOccupant", idPrecedentOccupant);

        return bdd.update("preferences", values, "idPreferences = " + id, null);
    }

    public int supprimer(int id)
    {
        return bdd.delete("preferences", "idOccupant = " + id, null);
    }

    public boolean estVide()
    {
        Cursor c = bdd.rawQuery("SELECT * FROM preferences", null);

        if(c.getCount() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private Preference cursorToServeur(Cursor c, boolean one)
    {
        if (c.getCount() == 0)
            return null;

        if(one)
            c.moveToFirst();

      Preference preference = new Preference();
      preference.setIdPreferences(c.getInt(0));
      preference.setAppareilGroom(c.getString(1));
      preference.setIdPrecedentOccupant(c.getInt(2));

      if (one)
          c.close();
      return preference;
    }
}
