package game;

public class Main {

    public static void main(String[] args) {

        Consola.limpiarPantalla();

        String titulo =
                Consola.ANSI_BOLD + Consola.ANSI_CYAN + "==================================================\n" +
                Consola.ANSI_BOLD + Consola.ANSI_CYAN + "           SUPERVIVENCIA EN LA ISLA: \n" +
                Consola.ANSI_BOLD + Consola.ANSI_YELLOW + "             EL LORO PERDIDO " + "🦜" + "\n" +
                Consola.ANSI_BOLD + Consola.ANSI_CYAN + "==================================================";

        System.out.println(titulo + Consola.ANSI_RESET);
        Consola.pausa(Consola.PAUSA_MEDIA_MS);

        System.out.print(Consola.ANSI_WHITE);
        Consola.imprimirLento("\nCargando datos del juego...");
        System.out.print(Consola.ANSI_RESET);

        Personaje personaje = Personaje.crearPersonaje();

        Juego juego = new Juego(personaje);

        juego.empezarJuego();

        Consola.pausa(Consola.PAUSA_MEDIA_MS);
        System.out.print(Consola.ANSI_BLACK);
        Consola.imprimirLento("\n...has vuelto al silencio.");
        System.out.print(Consola.ANSI_RESET);
        Consola.imprimirDescripcion("\nGracias por jugar.");
    }
}