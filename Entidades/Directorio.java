
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
        throw new Exception("Directorio no existe");
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
            borrarDirectorio(nuevo.getNombre());
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
    			try {
					return getArchivo(nomArchivo[0], nomArchivo[1]);
				} catch (Exception e) {
					return null;
				}
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
    
    public boolean existeDirectorioRuta(String[] ruta){
    	if(ruta.length == 0){
    		return true;
    	}else if(ruta.length == 1){
    		if(existeDirectorio(ruta[0])){
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
    				return temp.existeDirectorioRuta(Arrays.copyOfRange(ruta, 1, ruta.length));
    			}else{
    				return false;
    			}
    			
    		}
    	}
    	return false;
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
  
    public Archivo getArchivo(String nombre, String extension) throws Exception{
        for(Archivo a : archivos){
            if(a.getNombre().equals(nombre) && a.getExtension().equals(extension)){
                return a;
            }
        }
        throw new Exception("Archivo no existe");
    }
    
    public void borrarArchivo(String nombre, String extension){
        for(Archivo a : archivos){
            if(a.getNombre().equals(nombre) && a.getExtension().equals(extension)){
                a.borrarContenido();
                archivos.remove(a);
                break;
            }
        }
    }
    
    public void borrarContenidoDirectorio(String borrar){
        int index = 0;
        for (Directorio d : directorios){
            if (d.getNombre().equals(borrar)){
                d.aux_borrarDirectorio();
                break;
            }
            index++;
        }
        
    }
    
    public void borrarDirectorio(String borrar){
        for (Directorio d : directorios){
            if (d.getNombre().equals(borrar)){
                d.aux_borrarDirectorio();
                directorios.remove(d);
                break;
            }
        }
    }
    
    private void aux_borrarDirectorio(){
        for (Archivo a : archivos){
            a.borrarContenido();
        }
        
        for (Directorio d : directorios){
            d.aux_borrarDirectorio();
        }
        directorios.removeAll(directorios);
        archivos.removeAll(archivos);
    }
    
    public boolean reemplazarArchivo(String contenido, Archivo archivoNuevo){
        DiscoVirtual disco_virtual = DiscoVirtual.getInstance(0, 0, "");
        int caracteresSector = (disco_virtual.getTamSectores() /2);
        int secArchivo = contenido.length() / caracteresSector;
        if((contenido.length() % caracteresSector)!= 0){
                secArchivo++;
        }
        int cantActuales = 0;
        try {
            cantActuales = getArchivo(archivoNuevo.getNombre(), archivoNuevo.getExtension()).getPunteros().length;
        } catch (Exception ex) {}
        //System.out.println(cantActuales);
        if(disco_virtual.cantSectoresVacios() + cantActuales >= secArchivo){
            borrarArchivo(archivoNuevo.getNombre(), archivoNuevo.getExtension());
            if(archivoNuevo.escribirArchivo(contenido)){ 
                addArchivo(archivoNuevo);
            }
        }
        else{
            System.out.println("Espacio insuficiente");
            return false;
        }
        return true;
    }
    
    
}
