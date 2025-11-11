package game;

import java.util.LinkedHashMap;
import java.util.Map;

public class GestorLogros {

    private final Map<String, Logro> logros = new LinkedHashMap<>();

    public GestorLogros() {
        addLogro(new Logro("SOBREVIVIENTE_DIA_1", "Superviviente", "Has sobrevivido al primer día en la isla."));
        addLogro(new Logro("INCENDIARIO", "Pirómano", "Has quemado la mansión del Amo."));
        addLogro(new Logro("AMIGO_DE_LA_TRIBU", "Huésped de Honor", "Te has ganado el respeto del Chamán."));
        addLogro(new Logro("TRAIDOR_AVIAR", "Traidor Aviar", "Lanzaste a tu único amigo a un gorila."));
        addLogro(new Logro("GOURMET_GOOFY", "Gourmet Experimental", "Has intentado comer cosas... cuestionables."));
        addLogro(new Logro("FINAL_BUENO", "Rescate", "Has escapado de la isla."));
        addLogro(new Logro("FINAL_MALO", "Agente del Caos", "Has liberado al 'Visitante'."));
    }

    private void addLogro(Logro logro) {
        logros.put(logro.getId(), logro);
    }

    public void desbloquearLogro(String id) {
        Logro logro = logros.get(id);
        if (logro != null && !logro.isConseguido()) {
            logro.completar();

            Consola.pausa(Consola.PAUSA_MEDIA_MS);
            System.out.println(Consola.ANSI_PURPLE + "\n========================================" + Consola.ANSI_RESET);

            System.out.print(Consola.ANSI_YELLOW);
            Consola.imprimirLento("  ¡LOGRO DESBLOQUEADO! " + Consola.ICONO_LOGROS);
            System.out.print(Consola.ANSI_RESET);
            System.out.println(Consola.ANSI_WHITE + "  " + logro.getNombre() + ": " + logro.getDescripcion() + Consola.ANSI_RESET);
            System.out.println(Consola.ANSI_PURPLE + "========================================" + Consola.ANSI_RESET);
            Consola.pausa(Consola.PAUSA_DIALOGO_MS);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (logros.isEmpty()) {
            sb.append("  No hay logros definidos en el juego.");
        } else {
            for (Logro logro : logros.values()) {
                String icono = logro.isConseguido() ? Consola.ICONO_LOGROS : "🔒";
                String nombre = logro.getNombre();
                String descripcion = logro.getDescripcion();

                sb.append(String.format("  %-3s %s\n", icono, nombre));
                sb.append(String.format("      (%s)\n", descripcion));
            }
        }
        return sb.toString();
    }
}