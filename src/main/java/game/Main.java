package org.example;
import java.util.Scanner;


public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_ORANGE = "\u001B[38;5;214m"; // naranja
    public static final String ANSI_BOLD = "\u001B[1m";

    //INICIA EJECUCIÓN
    public static void main(String[] args) {
        int resultado = mostrarMenu();
        interaccion(resultado);
    }

    //MUESTRA MENU
    private static int mostrarMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=============");
        System.out.println(ANSI_BOLD + ANSI_ORANGE + "IMPACT-ISLAND" + ANSI_RESET);
        System.out.println("=============");

        System.out.println("1. Nueva Partida");
        System.out.println("2. Continuar Partida");
        System.out.println("3. Logros");
        System.out.println("4. Salir");
        System.out.print("\nSelecciona una opción: ");

        int eleccion = sc.nextInt();
        return eleccion;
    }

    //GESTIONA LA RESPUESTA DEL MENU
    private static void interaccion(int resultado) {
        System.out.println(resultado);
        switch (resultado) {
            case 1:
                //nueva partida
                break;
            case 2:
                //continuar partida
                break;
            case 3:
                //logros
            default:
                // salir
                break;
        }
    }




    private static Personaje[] crearPartidaNueva(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el nombre del personaje:");
        String nombre = sc.nextLine();

        Personaje personaje1 = Personaje.crearPersonaje(nombre);

        return new Personaje[]{personaje1};
    }
}
