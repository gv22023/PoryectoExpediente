package com.example.proyectoexpediente;
public class Materia {
    private String nombre;
    private String codigo;
    private int unidadesValorativas;

    public Materia(String nombre, String codigo, int unidadesValorativas) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.unidadesValorativas = unidadesValorativas;
    }

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public int getUnidadesValorativas() { return unidadesValorativas; }
}
