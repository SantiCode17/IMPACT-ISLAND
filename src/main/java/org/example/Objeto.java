package org.example;

public class Objeto {
    private String nombre;
    private String descripcion;

    public Objeto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre + ": " + descripcion;
    }

}
