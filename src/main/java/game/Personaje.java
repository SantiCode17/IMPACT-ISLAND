package game;

public class Personaje {
    private String nombre;
    private int sed;
    private int hambre;
    private Inventario inventario;
    private double cordura;


    public Personaje(String nombre, int sed, int hambre, Inventario inventario, double cordura) {
        this.nombre = nombre;
        this.sed = sed;
        this.hambre = hambre;
        this.inventario = inventario;
        this.cordura = cordura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSed() {
        return sed;
    }

    public void setSed(int sed) {
        this.sed = sed;
    }

    public int getHambre() {
        return hambre;
    }

    public void setHambre(int hambre) {
        this.hambre = hambre;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public double getCordura() {
        return cordura;
    }

    public void setCordura(double cordura) {
        this.cordura = cordura;
    }

    public static Personaje crearPersonaje(String nombre) {
        return new Personaje(nombre, 100, 100, new Inventario(), 100);
    }
}
