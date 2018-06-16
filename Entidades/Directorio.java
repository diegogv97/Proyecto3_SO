
package Entidades;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public boolean existeDirectorio(String nombre){
        for (Directorio d: directorios){
            if (d.getNombre().equals(nombre))
                return true;
        }
        return false;
    }
    
    public void remplazarDirectorio(Directorio nuevo){
        try {
            directorios.remove(getIndexDirectorio(nuevo.getNombre()));
            directorios.add(nuevo);
        } catch (Exception ex) {
            System.out.println("Ha ocurrido un error inesperado");
        }
        
    }
    
    
    private int getIndexDirectorio(String nombre) throws Exception{
        for (int i = 0; i<= directorios.size(); i++){
            if(directorios.get(i).getNombre().equals(nombre)){
                return i;
            }
        }
        throw new Exception("DIrectorio no existe");
    }

    public void imprimirDirectorios() {
        for(Directorio d: directorios){
            System.out.println("    " + d.getNombre());
        }
    }

    public void imprimirArchivos() {
        for (Archivo a : archivos){
            System.out.println("    " + a.getNombre()+ "." + a.getExtension());
        }
    }
    
}
