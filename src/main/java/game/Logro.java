package game;

import java.util.Objects;

public class Logro {
    private final String nombre;
    private final String descripcion;
    private boolean conseguido;
    private final String id;

    public Logro(String id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.conseguido = false;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public boolean isConseguido() { return conseguido; }

    public void completar() {
        if (!conseguido) {
            this.conseguido = true;
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.nombre, this.descripcion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logro logro = (Logro) o;
        return Objects.equals(id, logro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}