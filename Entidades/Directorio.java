
package Entidades;

import java.util.ArrayList;
import java.util.Arrays;
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
    
    public void addArchivo (Archivo nuevo){
        archivos.add(nuevo);
    }
    
    public Directorio getDirectorio(String nombre) throws Exception{
        for(Directorio d : directorios){
            if (d.getNombre().equals(nombre)){
                return d;
            }
        }
        throw new Exception("DIrectorio no existe");
    }
    
    public Archivo getArchivo(String nombre, String extension){
        for(Archivo a : archivos){
            if (a.getNombre().equals(nombre) && a.getExtension().equals(extension)){
                return a;
            }
        }
        return null;
    }
    
    public ArrayList<Directorio> getListaDirectorios(){
        return directorios; 
    }
    public ArrayList<Archivo> getListaArchivos(){
        return archivos; 
    }
    
    public boolean existeDirectorio(String nombre){
        for (Directorio d: directorios){
            if (d.getNombre().equals(nombre))
                return true;
        }
        return false;
    }
    
    public boolean existeArchivo(String nombre, String extension){
        for (Archivo a: archivos){
            if (a.getNombre().equals(nombre) && a.getExtension().equals(extension))
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
    
    public boolean existeArchivoRuta(String[] ruta){
    	if(ruta.length == 1){
    		String[] nomArchivo = ruta[0].split("\\.");
    		if(existeArchivo(nomArchivo[0], nomArchivo[1])){
    			return true;
    		}
    	}else{
    		if(existeDirectorio(ruta[0])){
    			Directorio temp = null;
    			try{
    				temp = getDirectorio(ruta[0]);
    			}catch(Exception e){
    				
    			}
    			if(temp != null){
    				return temp.existeArchivoRuta(Arrays.copyOfRange(ruta, 1, ruta.length));
    			}else{
    				return false;
    			}
    			
    		}
    	}
    	return false;
    }
    
    public Archivo getArchivoRuta(String[] ruta){
    	if(ruta.length == 1){
    		String[] nomArchivo = ruta[0].split("\\.");
    		if(existeArchivo(nomArchivo[0], nomArchivo[1])){
    			return getArchivo(nomArchivo[0], nomArchivo[1]);
    		}
    	}else{
    		if(existeDirectorio(ruta[0])){
    			Directorio temp = null;
    			try{
    				temp = getDirectorio(ruta[0]);
    			}catch(Exception e){
    				
    			}
    			if(temp != null){
    				return temp.getArchivoRuta(Arrays.copyOfRange(ruta, 1, ruta.length));
    			}else{
    				return null;
    			}
    			
    		}
    	}
    	return null;
    }
    
    public Directorio getDirectorioRuta(String[] ruta){
    	if(ruta.length == 0){
    		return this;
    	}else{
    		if(existeDirectorio(ruta[0])){
    			Directorio temp = null;
    			try{
    				temp = getDirectorio(ruta[0]);
    			}catch(Exception e){
    				
    			}
    			if(temp != null){
    				return temp.getDirectorioRuta(Arrays.copyOfRange(ruta, 1, ruta.length));
    			}else{
    				return null;
    			}
    			
    		}
    	}
    	return null;
    }
}
