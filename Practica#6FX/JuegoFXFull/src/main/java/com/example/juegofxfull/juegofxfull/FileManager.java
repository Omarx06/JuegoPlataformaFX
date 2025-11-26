package com.example.juegofxfull.juegofxfull;

import java.io.*;

public class FileManager {
    private final String ruta;

    public FileManager(String ruta) {
        this.ruta = ruta;
    }

    public void guardar(int puntaje, String nombre) {
        try {
            File f = new File(ruta);
            if (f.getParentFile() != null) f.getParentFile().mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println(nombre);
                pw.println(puntaje);
            }
        } catch (Exception ignored) {}
    }

    public int cargar() {
        try {
            File f = new File(ruta);
            if (!f.exists()) return 0;
            try (BufferedReader r = new BufferedReader(new FileReader(f))) {
                r.readLine(); // name
                String s = r.readLine();
                return Integer.parseInt(s);
            }
        } catch (Exception e) {
            return 0;
        }
    }
}







