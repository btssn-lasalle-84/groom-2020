package com.example.groom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class GroomBDD
{
    private SQLiteDatabase bdd = null;
    private GroomSQLite groomSQLite = null;

    public GroomBDD(Context context)
    {
        groomSQLite = new GroomSQLite(context);
    }

    public void open()
    {
        if (bdd == null)
            bdd = groomSQLite.getWritableDatabase();
    }

    public void close()
    {
        if (bdd != null)
            if (bdd.isOpen())
                bdd.close();
    }

    public SQLiteDatabase getBDD()
    {
        if (bdd == null)
            open();
        return bdd;
    }
}
