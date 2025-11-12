package game;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

public class Juego {

    public final String nombre;
    private final Personaje personaje;
    private final Map<String, Scene> scenes;
    private String currentSceneId;
    public boolean isRunning;
    private final int gameId;

    public Juego(String nombre, Personaje personaje, int gameId) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.scenes = loadGameData("dialogos.json");
        this.currentSceneId = "CONTEXTO";
        this.isRunning = true;
        this.gameId = gameId;
    }

    private Map<String, Scene> loadGameData(String jsonFileName) {
        String path = "src/main/resources/" + jsonFileName;
        try (Reader reader = new FileReader(path)) {
            Gson gson = new Gson();
            GameData gameData = gson.fromJson(reader, GameData.class);
            if (gameData == null || gameData.scenes == null) {
                System.out.println(Consola.ANSI_RED + "Error fatal: El archivo JSON está vacío o mal formado." + Consola.ANSI_RESET);
                return null;
            }
            System.out.print(Consola.ANSI_GREEN);
            Consola.imprimirLento("Datos del juego cargados. " + gameData.scenes.size() + " escenas encontradas.");
            System.out.print(Consola.ANSI_RESET);
            Consola.pausa(Consola.PAUSA_MEDIA_MS);
            return gameData.scenes;
        } catch (Exception e) {
            System.out.println(Consola.ANSI_RED + "Error fatal: No se pudo cargar el archivo " + path + Consola.ANSI_RESET);
            e.printStackTrace(System.err);
            return null;
        }
    }

    public void empezarJuego() {
        if (scenes == null) {
            Consola.imprimirEfecto("No se pudo iniciar el juego. Faltan los datos.", -1);
            return;
        }

        System.out.printf("Jugando en el mundo: %s%n", this.nombre);

        while (isRunning) {

            if (personaje.getVida() <= 0) {
                Consola.mostrarPantallaMuerte();
                isRunning = false;
                break;
            }

            Scene currentScene = scenes.get(currentSceneId);
            if (currentScene == null) {
                Consola.imprimirEfecto("Error: No se encontró la escena: " + currentSceneId, -1);
                isRunning = false;
                break;
            }

            Consola.limpiarPantalla();
            displaySceneDescription(currentScene);

            if (currentScene.endGame) {
                if ("FINAL_1".equals(currentScene.id)) personaje.getGestorLogros().desbloquearLogro("FINAL_BUENO", gameId);
                if ("FINAL_2".equals(currentScene.id)) personaje.getGestorLogros().desbloquearLogro("FINAL_MALO", gameId);
                isRunning = false;
                break;
            }

            if (currentScene.endOfDay) {
                handleEndOfDay(currentScene);
                currentSceneId = currentScene.nextScene;
                continue;
            }

            if (currentScene.options == null || currentScene.options.isEmpty()) {
                if (currentScene.nextScene != null) {
                    Consola.pausarYEsperarEnter("...");
                    currentSceneId = currentScene.nextScene;
                    continue;
                } else {
                    Consola.imprimirEfecto("Error: Escena narrativa sin 'nextScene'", -1);
                    isRunning = false;
                    break;
                }
            }

            boolean escenaAvanza = false;
            while (!escenaAvanza && isRunning) {

                displaySceneOptions(currentScene);

                String input = getUserInput();

                if (isMenuCommand(input)) {
                    handleMenuChoice(input);
                    Consola.limpiarPantalla();

                } else if (isStoryChoice(input, currentScene)) {
                    int choiceIndex = Integer.parseInt(input) - 1;

                    if (!checkPrecondition(currentScene.options.get(choiceIndex).precondition)) {
                        Consola.imprimirEfecto("Esa opción no está disponible.", -1);
                        Consola.pausa(Consola.PAUSA_MEDIA_MS);
                        Consola.limpiarPantalla();
                        continue;
                    }

                    Outcome outcome = currentScene.options.get(choiceIndex).outcome;
                    displayOutcome(outcome);
                    applyEffect(outcome.effect);

                    currentSceneId = outcome.nextScene;
                    escenaAvanza = true;

                    Consola.pausarYEsperarEnter("Continuar...");

                } else {
                    Consola.imprimirEfecto("Comando no válido. Inténtalo de nuevo.", -1);
                    Consola.pausa(Consola.PAUSA_MEDIA_MS);

                    Consola.limpiarPantalla();
                }
            }
        }
    }

    private void displaySceneDescription(Scene scene) {
        Consola.imprimirTitulo(scene.title);

        imprimirDescripciones(scene.description);
        imprimirDialogos(scene.dialogues);
        imprimirDescripciones(scene.descriptionAfterDialogue);
        imprimirDialogos(scene.dialogues_2);
        imprimirDescripciones(scene.descriptionAfterDialogue2);
        imprimirDialogos(scene.dialogues_3);
        imprimirDescripciones(scene.descriptionAfterDialogue3);
    }

    private void displaySceneOptions(Scene scene) {

        Consola.imprimirBarraDeVida(personaje.getVida(), personaje.getVidaMaxima());
        Consola.imprimirPrompt(scene.prompt);

        int optionNumber = 1;
        for (SceneOption option : scene.options) {
            boolean disponible = checkPrecondition(option.precondition);
            Consola.imprimirOpcion(optionNumber, option.text, disponible);
            optionNumber++;
        }

        Consola.imprimirPrompt("--- MENÚ ---");
        Consola.imprimirOpcionMenu("I", "Inventario", Consola.ICONO_INVENTARIO);
        Consola.imprimirOpcionMenu("L", "Logros", Consola.ICONO_LOGROS);
        Consola.imprimirOpcionMenu("M", "Mapa", Consola.ICONO_MAPA);
        Consola.imprimirOpcionMenu("Q", "Salir del Juego", Consola.ICONO_SALIR);
    }

    private String getUserInput() {
        System.out.print(Consola.ANSI_BOLD + Consola.ANSI_YELLOW + "\n> ¿Qué haces?: " + Consola.ANSI_RESET);
        return Consola.input.nextLine().trim().toUpperCase();
    }

    private void handleMenuChoice(String input) {
        Consola.limpiarPantalla();
        switch (input) {
            case "I":
                Consola.imprimirTitulo(Consola.ICONO_INVENTARIO + " INVENTARIO " + Consola.ICONO_INVENTARIO);
                System.out.println(Consola.ANSI_WHITE + personaje.getInventario().toString() + Consola.ANSI_RESET);
                break;
            case "L":
                Consola.imprimirTitulo(Consola.ICONO_LOGROS + " LOGROS " + Consola.ICONO_LOGROS);
                System.out.println(Consola.ANSI_WHITE + personaje.getGestorLogros().toString() + Consola.ANSI_RESET);
                break;
            case "M":
                Consola.imprimirTitulo(Consola.ICONO_MAPA + " MAPA " + Consola.ICONO_MAPA);
                if (personaje.getInventario().contiene("Mapa de la Isla")) {
                    Consola.imprimirDescripcion("Miras el mapa detallado que encontraste en la maleta rosa...");
                    Consola.imprimirDescripcion("Marca la 'Playa', la 'Mansión', el 'Complejo B' y unas 'Cuevas'.");
                } else {
                    Consola.imprimirDescripcion("No tienes ningún mapa. Todo es desconocido.");
                }
                break;
            case "Q":
                Consola.imprimirDescripcion("¿Seguro que quieres salir? (S/N)");
                final String confirm = Consola.input.nextLine().trim().toUpperCase();
                if (confirm.equals("S")) {
                    isRunning = false;
                }
                return;
        }
        Consola.pausarYEsperarEnter("Volver al juego...");
    }

    private void displayOutcome(Outcome outcome) {
        System.out.println("\n" + Consola.ANSI_BLACK + "----------------------------------------" + Consola.ANSI_RESET);
        imprimirDescripciones(outcome.description);
        imprimirDialogos(outcome.dialogues);
    }

    private void applyEffect(Effect effect) {
        if (effect == null) return;

        if (effect.healthChange != 0) {
            personaje.modificarVida(effect.healthChange);
            if (effect.message != null && !effect.message.isEmpty()) {
                Consola.imprimirEfecto(effect.message, effect.healthChange);
            }
            Consola.imprimirBarraDeVida(personaje.getVida(), personaje.getVidaMaxima());
        }

        if (effect.items != null && !effect.items.isEmpty()) {
            for (String itemName : effect.items) {
                Objeto nuevoObjeto = crearObjetoDesdeString(itemName);
                personaje.ganarItem(nuevoObjeto);
                Consola.imprimirNotificacionItem(nuevoObjeto.getNombre());
            }
        }

        if (effect.state != null && !effect.state.isEmpty()) {
            Consola.imprimirEstado(effect.state);
        }

        if (effect.unlockAchievement != null && !effect.unlockAchievement.isEmpty()) {
            personaje.getGestorLogros().desbloquearLogro(effect.unlockAchievement, gameId);
        }
    }

    private void handleEndOfDay(Scene scene) {
        Consola.limpiarPantalla();
        Consola.imprimirTitulo(Consola.ICONO_FIN_DIA + " FIN DEL " + scene.id.replace("_", " ") + " " + Consola.ICONO_FIN_DIA);

        imprimirDescripciones(scene.summary);

        if ("DIA_1_10".equals(scene.id)) {
            personaje.getGestorLogros().desbloquearLogro("SOBREVIVIENTE_DIA_1", gameId);
        }

        Consola.imprimirEfecto("\n[ESTADO AL FINAL DEL DÍA]", 0);
        Consola.imprimirBarraDeVida(personaje.getVida(), personaje.getVidaMaxima());

        System.out.println("\n" + Consola.ANSI_WHITE + personaje.getInventario().toString() + Consola.ANSI_RESET);

        Consola.pausarYEsperarEnter("Descansar y continuar al siguiente día...");
    }

    private boolean checkPrecondition(String precondition) {
        if (precondition == null || precondition.isEmpty()) {
            return true;
        }

        if (precondition.startsWith("HAS_ITEM:")) {
            String itemName = precondition.substring(9).trim();
            if (itemName.equalsIgnoreCase("Maletín Misterioso")) itemName = "Maletín Misterioso";
            if (itemName.equalsIgnoreCase("Llave USB Dorada")) itemName = "Llave USB Dorada";
            if (itemName.equalsIgnoreCase("Pistola de Bengalas")) itemName = "Pistola de Bengalas";

            return personaje.getInventario().contiene(itemName);
        }

        return true;
    }

    private Objeto crearObjetoDesdeString(String nombreItem) {
        String nombre = nombreItem.toUpperCase();
        switch (nombre) {
            case "MAPA DE LA ISLA":
                return new Objeto("Mapa de la Isla", "Un mapa detallado dibujado a mano.", "🗺️");
            case "1X PAÑUELOS SECOS":
                return new Objeto("Pañuelos Secos", "Útiles para limpiar heridas.", "🧻");
            case "1X BOTELLA DE AGUA (MEDIA)":
                return new Objeto("Botella de Agua (Media)", "Un trago de esperanza.", "💧");
            case "1X BARRITA DE CEREALES":
                return new Objeto("Barrita de Cereales", "Rancia, pero es comida.", "🍫");
            case "1X ZAPATO DE TACÓN ENSANGRENTADO (¿ARMA?)":
                return new Objeto("Tacón Ensangrentado", "Arma improvisada. Letal y fabulosa.", "👠");
            case "1X LATA DE REFRESCO CALIENTE":
                return new Objeto("Refresco Caliente", "Mejor que el agua de mar.", "🥤");
            case "1X ENCENDEDOR VIEJO (HÚMEDO)":
                return new Objeto("Encendedor Húmedo", "Quizás funcione si se seca.", "🔥");
            case "1X TRAPO SECO":
                return new Objeto("Trapo Seco", "Limpio. Puede usarse como vendaje.", "🧼");
            case "1X DIARIO (REGISTRO DE VUELO)":
                return new Objeto("Diario (Registro de Vuelo)", "La lista de pasajeros del 'Lolita Express'.", "📓");

            case "1X CUCHILLO DE COCINA (¡ARMA!)":
                return new Objeto("Cuchillo de Cocina", "Afilado y fiable. Un amigo en la oscuridad.", "🔪");
            case "1X VENDAS":
                return new Objeto("Vendas", "Limpias y estériles. Justo lo que necesitabas.", "🩹");
            case "1X ANTISÉPTICO":
                return new Objeto("Antiséptico", "Escuece como el demonio, pero cura.", "🧪");
            case "1X SARTÉN DE HIERRO (ARMA CONTUNDENTE)":
                return new Objeto("Sartén de Hierro", "Pesada y... bueno, es una sartén.", "🍳");

            case "MANOJO DE PLÁTANOS (X5)":
                return new Objeto("Manojo de Plátanos", "Comida de mono, ahora tu comida.", "🍌");
            case "MANGOS (X3)":
                return new Objeto("Mangos", "Dulces y jugosos.", "🥭");
            case "WALKIE-TALKIE (ROTO)":
                return new Objeto("Walkie-Talkie Roto", "Solo emite estática.", "📻");
            case "GALLETAS RANCIAS (X1)":
                return new Objeto("Galletas Rancias", "Saben a cartón.", "🍪");

            case "ANTORCHA APAGADA":
                return new Objeto("Antorcha Apagada", "Un palo con trapos. Necesita fuego.", "🪵");
            case "SÍLEX":
                return new Objeto("Sílex", "Dos piedras para hacer chispas.", "🪨");
            case "MALETÍN MISTERIOSO":
                return new Objeto("Maletín Misterioso", "Abollado y chamuscado. Contiene secretos.", "💼");

            case "FOTOS INCRIMINATORIAS":
                return new Objeto("Fotos Incriminatorias", "El Chamán y el Amo... juntos.", "📸");
            case "LLAVE USB DORADA":
                return new Objeto("Llave USB Dorada", "Pesada. Contiene la 'Llave del Mundo'.", "🔑");
            case "ÍDOLO DE MADERA":
                return new Objeto("Ídolo de Madera", "Un pequeño ídolo de la tribu. Parece importante.", "🗿");
            case "PISTOLA DE BENGALAS":
                return new Objeto("Pistola de Bengalas", "Un único cartucho rojo. Pesada.", "🧨");

            case "JERINGUILLA DE ADRENALINA":
                return new Objeto("Jeringuilla de Adrenalina", "Para un subidón rápido. O un infarto.", "💉");
            case "TASER-RIFLE (PROTOTIPO)":
                return new Objeto("Taser-Rifle", "Un arma no letal... en teoría.", "⚡");
            case "CHALECO DE KEVLAR":
                return new Objeto("Chaleco de Kevlar", "Pesado, pero tranquilizador.", "🦺");

            case "ADRENALINA-R (REGEN)":
                return new Objeto("Adrenalina-R (Regen)", "Líquido verde. Cura 80 de vida.", "💉");

            default:
                return new Objeto(nombreItem, "Un objeto encontrado en la isla.", "❓");
        }
    }

    private boolean isMenuCommand(String input) {
        return input.equals("I") || input.equals("L") || input.equals("M") || input.equals("Q") || input.equals("G");
    }

    private boolean isStoryChoice(String input, Scene scene) {
        try {
            int choice = Integer.parseInt(input);
            return (choice >= 1 && choice <= scene.options.size());
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void imprimirDescripciones(List<String> lineas) {
        if (lineas == null) return;
        for (String linea : lineas) {
            if (linea.startsWith("(") || linea.startsWith("Si elegiste")) {
                continue;
            }
            Consola.imprimirDescripcion(linea);
        }
    }

    private void imprimirDialogos(List<DialogueLine> dialogos) {
        if (dialogos == null) return;
        for (DialogueLine dialogo : dialogos) {
            Consola.imprimirDialogo(dialogo.speaker, dialogo.line);
        }
    }
}