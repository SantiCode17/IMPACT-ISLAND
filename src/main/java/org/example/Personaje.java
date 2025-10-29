package org.example;

public class Personaje {
    private String nombre;
    private int vida;
    private int fuerza;
    private int defensa;
    private int sed;
    private int hambre;
    private int ubicacionActual;
    private Inventario inventario;


    public Personaje(String nombre, int vida, int fuerza, int defensa, int sed, int hambre, int ubicacionActual, Inventario inventario) {
        this.nombre = nombre;
        this.vida = vida;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.sed = sed;
        this.hambre = hambre;
        this.ubicacionActual = ubicacionActual;
        this.inventario = inventario;
    }
}
