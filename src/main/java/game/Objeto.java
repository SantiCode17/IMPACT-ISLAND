package game;

import java.util.Objects;

public class Objeto {
    private final String nombre;
    private final String descripcion;
    private final String icono;

    public Objeto(String nombre, String descripcion, String icono) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getIcono() { return icono; }

    @Override
    public String toString() {
        return String.format("%-3s %s - %s", icono, nombre, descripcion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objeto objeto = (Objeto) o;
        return Objects.equals(nombre, objeto.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}