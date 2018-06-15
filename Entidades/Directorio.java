
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
    
    public void addDirectorio (Directorio nuevo){
        directorios.add(nuevo);
    }
    
    public Directorio getDirectorio(String nombre) throws Exception{
        for(Directorio d : directorios){
            if (d.getNombre().equals(nombre)){
                return d;
            }
        }
        throw new Exception("DIrectorio no existe");
    }
    
}
