package game;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class Main {
    static Optional<Juego> cargarPartida() {
        try (final var reader = new FileReader("partida.json")) {
            return Optional.of(
                    new Gson().fromJson(reader, Juego.class)
            );
        } catch (IOException _) {
            return Optional.empty();
        }
    }

    static Juego nuevoJuego() {
        System.out.println("Creando nueva partida.");
        System.out.println("Escribe el nombre de la nueva partida.");
        final var gameName = Consola.prompt();

        return new Juego(gameName, Personaje.crearPersonaje());
    }

    static void main(String[] args) {
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

        final Optional<Juego> partidaGuardada = cargarPartida();
        final Juego juego;

        if (partidaGuardada.isPresent()) {
            final Juego juegoAntiguo = partidaGuardada.get();
            System.out.println("Se ha encontrado una partida guardada.");
            System.out.printf("Nombre: %s%n", juegoAntiguo.nombre);
            System.out.println("¿Quieres cargar la partida?");
            for (;;) {
                final var respuesta = Consola.prompt("[Y/N] > ").toUpperCase();
                if (respuesta.equals("Y")) {
                    juego = juegoAntiguo;
                    break;
                } else if (respuesta.equals("N")) {
                    juego = nuevoJuego();
                    break;
                } else {
                    System.out.println("Introduce una opción válida.");
                }
            }
        } else juego = nuevoJuego();
        juego.isRunning = true;

        juego.empezarJuego();

        Consola.pausa(Consola.PAUSA_MEDIA_MS);
        System.out.print(Consola.ANSI_BLACK);
        Consola.imprimirLento("\n...has vuelto al silencio.");
        System.out.print(Consola.ANSI_RESET);
        Consola.imprimirDescripcion("\nGracias por jugar.");

        try (final var writer = new FileWriter("partida.json")) {
            writer.write(new Gson().toJson(juego));
        } catch (IOException _) {}
    }
}