package com.example.proyectoexpediente;

public class Materia {
    private final String nombre;
    private final String codigo;
    private final int unidadesValorativas;

    public Materia(String nombre, String codigo, int unidadesValorativas) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.unidadesValorativas = unidadesValorativas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getUnidadesValorativas() {
        return unidadesValorativas;
    }

    @Override
    public String toString() {
        return nombre + " (" + codigo + ") - " + unidadesValorativas + " UV";
    }
}
