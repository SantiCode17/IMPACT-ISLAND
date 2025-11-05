package org.example;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Juego juego = new Juego();

        System.out.println("=============");
        System.out.println("IMPACT ISLAND");
        System.out.println("=============");



    }

    private static Personaje[] crearPartidaNueva(){
        System.out.println("Introduce el nombre del personaje:");
        String nombre = sc.nextLine();

        Personaje personaje1 = Personaje.crearPersonaje(nombre);

        return new Personaje[]{personaje1};
    }
}
