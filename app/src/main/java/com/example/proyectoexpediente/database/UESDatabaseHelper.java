package com.example.proyectoexpediente.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UESDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UESExpediente.db";
    public static final int DATABASE_VERSION = 1;

    public UESDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "email TEXT, " +
                "contrasena TEXT, " +
                "tipo TEXT)");

        db.execSQL("CREATE TABLE materias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "descripcion TEXT)");

        db.execSQL("CREATE TABLE inscripciones (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER, " +
                "materia_id INTEGER)");

        db.execSQL("CREATE TABLE notas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER, " +
                "materia_id INTEGER, " +
                "nota REAL)");

        // Insertar usuario admin por defecto
        db.execSQL("INSERT INTO usuarios (nombre, email, contrasena, tipo) " +
                "VALUES ('Admin', 'admin@ues.edu.sv', 'admin123', 'admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS materias");
        db.execSQL("DROP TABLE IF EXISTS inscripciones");
        db.execSQL("DROP TABLE IF EXISTS notas");
        onCreate(db);
    }
}