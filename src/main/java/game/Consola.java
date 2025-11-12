package game;

import java.io.IO;
import java.util.concurrent.TimeUnit;

public class Consola {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_ITALIC = "\u001B[3m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public static final String ICONO_MAPA = "🗺️";
    public static final String ICONO_INVENTARIO = "📦";
    public static final String ICONO_LOGROS = "🏆";
    public static final String ICONO_SALIR = "❌";
    public static final String ICONO_GUARDAR = "💾";
    public static final String ICONO_VIDA = "❤️";
    public static final String ICONO_CALAVERA = "💀";
    public static final String ICONO_ITEM = "✨";
    public static final String ICONO_ESTADO = "🌀";
    public static final String ICONO_TITULO = "🏝️";
    public static final String ICONO_FIN_DIA = "🌙";

    public static final int PAUSA_CORTA_MS = 15;
    public static final int PAUSA_MEDIA_MS = 300;
    public static final int PAUSA_DIALOGO_MS = 500;

    public static final int CHAR_CHUNK_SIZE = 10;

    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pausa(int milisegundos) {
        try {
            TimeUnit.MILLISECONDS.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void imprimirLento(String texto) {
        int counter = 0;
        for (final char c : texto.toCharArray()) {
            System.out.print(c);
            if ((++counter) % CHAR_CHUNK_SIZE == 0) {
                System.out.flush();
                pausa(PAUSA_CORTA_MS);
            }
        }
        if (counter % CHAR_CHUNK_SIZE != 0) {
            pausa(PAUSA_CORTA_MS);
        }
        System.out.println();
    }

    public static void pausarYEsperarEnter(String mensaje) {
        System.out.printf(
                "%s%s%n%s%n[PRESIONA ENTER]%s",
                ANSI_ITALIC, ANSI_WHITE, mensaje, ANSI_RESET
        );
        IO.readln();
    }

    public static void imprimirDescripcion(String texto) {
        System.out.print(ANSI_WHITE);
        imprimirLento(texto);
        System.out.print(ANSI_RESET);
        pausa(PAUSA_MEDIA_MS);
    }

    public static void imprimirTitulo(String texto) {
        final String borde = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
        final String tituloFormato = String.format(
                " %s %s %s ",
                ICONO_TITULO, texto.toUpperCase(), ICONO_TITULO
        );
        System.out.printf(
                "%n%n%s%s%s%s",
                ANSI_BOLD, ANSI_CYAN, borde, ANSI_RESET
        );

        final String padding = " ".repeat(
                Math.max(
                        0,
                        (borde.length() - tituloFormato.length()) / 2
                )
        );

        System.out.printf(
                "%s%s%s%s%s%s%s%n%n",
                ANSI_BOLD, ANSI_CYAN,
                padding, tituloFormato, padding,
                borde,
                ANSI_RESET
        );

        System.out.flush();
        pausa(PAUSA_MEDIA_MS);
    }

    private static String buildPrefixImprimirDialogo(String speaker, String color) {
        return String.format(
                "%s%s %s: %s%s%s\"",
                ANSI_BOLD, color, speaker, ANSI_RESET, ANSI_ITALIC, ANSI_YELLOW
        );
    }

    public static void imprimirDialogo(String speaker, String line) {
        final String loro = "JEFFRY";

        final String prefijo;
        if (loro.equalsIgnoreCase(speaker)) {
            prefijo = buildPrefixImprimirDialogo(loro, ANSI_YELLOW);
        } else {
            prefijo = buildPrefixImprimirDialogo(speaker.toUpperCase(), ANSI_CYAN);
        }

        System.out.printf("%n%s", prefijo);
        imprimirLento(line + "\"");
        System.out.println(ANSI_RESET);
        pausa(PAUSA_DIALOGO_MS);
    }

    public static void imprimirPrompt(String texto) {
        System.out.println("\n" + ANSI_BOLD + ANSI_CYAN + "  " + texto + ANSI_RESET);
    }

    public static void imprimirOpcion(int numero, String texto, boolean disponible) {
        if (disponible) {
            System.out.println(ANSI_YELLOW + "    " + numero + ". " + ANSI_WHITE + texto + ANSI_RESET);
        } else {
            System.out.println(ANSI_BLACK + "    " + numero + ". " + texto + " (No disponible)" + ANSI_RESET);
        }
    }

    public static void imprimirOpcionMenu(String tecla, String texto, String icono) {
        System.out.println(ANSI_YELLOW + "    [" + tecla + "] " + ANSI_WHITE + icono + " " + texto + ANSI_RESET);
    }

    public static void imprimirEfecto(String mensaje, int cambioVida) {
        String color = ANSI_WHITE;
        if (cambioVida < 0) {
            color = ANSI_RED;
        } else if (cambioVida > 0) {
            color = ANSI_GREEN;
        }
        System.out.println("\n" + ANSI_BOLD + color + "     " + mensaje + ANSI_RESET);
        pausa(PAUSA_MEDIA_MS);
    }

    public static void imprimirNotificacionItem(String nombreItem) {
        System.out.println("\n" + ANSI_BOLD + ANSI_GREEN_BACKGROUND + ANSI_BLACK + " " + ICONO_ITEM + " Objeto Obtenido: " + nombreItem + " " + ANSI_RESET);
        pausa(PAUSA_MEDIA_MS);
    }

    public static void imprimirEstado(String estado) {
        System.out.printf(
                "%n%s%s     %s [ESTADO: %s]%s%n",
                ANSI_ITALIC, ANSI_PURPLE, ICONO_ESTADO,
                estado,
                ANSI_RESET
        );
        System.out.flush();
        pausa(PAUSA_MEDIA_MS);
    }

    public static void mostrarPantallaMuerte() {
        limpiarPantalla();
        pausa(1000);

        final String muerte = "=========================================\n" +
                "==                                     ==\n" +
                String.format(
                        "==           %s HAS MUERTO     %s        ==",
                        ICONO_CALAVERA, ICONO_CALAVERA
                ) +
                '\n' +
                "==                                     ==\n" +
                "=========================================";

        System.out.print(ANSI_RED_BACKGROUND + ANSI_BOLD + ANSI_WHITE);
        imprimirLento(muerte);
        System.out.print(ANSI_RESET);
        pausa(3000);
    }

    public static void imprimirBarraDeVida(int vidaActual, int vidaMaxima) {
        final int barLength = 20;
        final int filledLength = (int) Math.round(((double) vidaActual / vidaMaxima) * barLength);
        final int emptyLength = barLength - filledLength;

        final String color;
        if (vidaActual > 60) {
            color = ANSI_GREEN;
        } else if (vidaActual > 30) {
            color = ANSI_YELLOW;
        } else {
            color = ANSI_RED;
        }

        System.out.printf(
                "%n%s VIDA: %s%s[%s%s%s%s] %d/%d %s%s%n",
                ANSI_BOLD, ANSI_RESET,
                color,
                "█".repeat(Math.max(0, filledLength)),
                ANSI_BLACK,
                "░".repeat(Math.max(0, emptyLength)),
                color,
                vidaActual, vidaMaxima,
                ICONO_VIDA,
                ANSI_RESET
        );
        System.out.flush();
    }
}
