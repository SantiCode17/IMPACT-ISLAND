package org.example;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private static List<Objeto> inventarioGlobal = new ArrayList<>();

    public static void agregarAlInventarioGlobal(Objeto objeto) {
        inventarioGlobal.add(objeto);
    }

    public static void eliminarDelInventarioGlobal(Objeto objeto) {
        inventarioGlobal.remove(objeto);
    }
}