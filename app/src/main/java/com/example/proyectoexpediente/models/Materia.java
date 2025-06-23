package com.example.proyectoexpediente.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "materias")
public class Materia {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String codigo;
    public int uv;

    public Materia(String nombre, String codigo, int uv) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.uv = uv;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getUv() {
        return uv;
    }
}
