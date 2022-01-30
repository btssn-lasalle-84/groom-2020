package com.example.groom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GroomSQLite extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "groom.db";
    public static final int DATABASE_VERSION = 1;
    private static final String CREATE_BDD_OCCUPANTS =
            "CREATE TABLE occupants (" +
                    "idOccupant INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "nom VARCHAR(45) NULL," +
                    "prenom VARCHAR(45) NULL," +
                    "fonction VARCHAR(45) NULL);";
    private static final String CREATE_BDD_PREFERENCES =
            "CREATE TABLE preferences (" +
                    "idPreferences INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "groom VARCHAR(45) NULL," +
                    "idPrecedentOccupant INTEGER NOT NULL, " +
                    "FOREIGN KEY (idPrecedentOccupant) REFERENCES occupants (idOccupant));";

    public GroomSQLite(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_BDD_OCCUPANTS);
        db.execSQL(CREATE_BDD_PREFERENCES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE occupants;");
        db.execSQL("DROP TABLE preferences");
    }
}
