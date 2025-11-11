package game;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private final List<Objeto> objetos = new ArrayList<>();

    public void add(Objeto o) {
        if (o != null && !objetos.contains(o)) {
            objetos.add(o);
        }
    }

    public boolean contiene(String nombre) {
        return objetos.contains(new Objeto(nombre, "", ""));
    }

    @Override
    public String toString() {
        if (objetos.isEmpty()) {
            return "  Tu inventario está vacío.";
        }

        StringBuilder sb = new StringBuilder();
        for (Objeto o : objetos) {
            sb.append("  ");
            sb.append(o.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}