
package Entidades;

import java.util.ArrayList;

public class Directorio {
    ArrayList<Directorio> directorios = new ArrayList<Directorio>();
    ArrayList<Archivo> archivos = new ArrayList<Archivo>();
    private String nombre;

    public Directorio(String nombre) {
        this.nombre = nombre;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
