package Entidades;

import java.util.ArrayList;

public class DiscoVirtual {
    private static DiscoVirtual single_instance = null;
    
    Directorio raiz;
 

    private DiscoVirtual(String nombre){
        this.raiz = new Directorio(nombre);
    }
 
    // static method to create instance of Singleton class
    public static DiscoVirtual getInstance(String nombre)
    {
        if (single_instance == null)
            single_instance = new DiscoVirtual(nombre);
        return single_instance;
    }
    
    public static boolean isInstanceNull(){
        if (single_instance == null)
            return true;
        return false;
    }
    
    
 
}
