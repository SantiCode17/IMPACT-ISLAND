package game;

public class Personaje {
    public static final String DEFAULT_NOMBRE = "Moniato";
    public static final int DEFAULT_VIDA = 100;

    private String nombre;
    private int vida;
    private final int vidaMaxima;
    private final Inventario inventario;
    private final GestorLogros gestorLogros;

    public Personaje(String nombre, int vida) {
        this.nombre = nombre;
        this.vidaMaxima = vida;
        this.vida = vida;
        this.inventario = new Inventario();
        this.gestorLogros = new GestorLogros();
    }

    public static Personaje crearPersonaje(String nombre) {
        return new Personaje(nombre, DEFAULT_VIDA);
    }

    public static Personaje crearPersonaje() {
        return crearPersonaje(DEFAULT_NOMBRE);
    }

    private int clamp(int valor) {
        return Math.max(0, Math.min(valor, this.vidaMaxima));
    }

    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public Inventario getInventario() { return inventario; }
    public GestorLogros getGestorLogros() { return gestorLogros; }

    public void setVida(int vida) { this.vida = clamp(vida); }

    public void modificarVida(int cantidad) {
        this.vida = clamp(this.vida + cantidad);
    }

    public void ganarItem(Objeto objeto) {
        this.inventario.add(objeto);
    }

    @Override
    public String toString() {
        return "Tu personaje tiene " + vida + " de vida";
    }
}