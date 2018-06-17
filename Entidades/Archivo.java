package Entidades;

public class Archivo {
    private String nombre;
    private String extension;
    private String fecha_creacion;
    private String fecha_modificacion;
    private int size;
    private int[] punteros;   //para localizar sus segmentos en memoria

    public Archivo(String nombre, String extension, String fecha_creacion, int size) {
        this.nombre = nombre;
        this.extension = extension;
        this.fecha_creacion = fecha_creacion;
        this.fecha_modificacion = fecha_creacion;
        this.size = size;
        this.punteros = new int[0];
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(String fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int[] getPunteros() {
        return punteros;
    }

    public void setPunteros(int[] punteros) {
        this.punteros = punteros;
    }
    
    public boolean escribirArchivo(String contenido){
    	DiscoVirtual discoVirtual = DiscoVirtual.getInstance(0, 0, "");
        punteros = discoVirtual.escribirSectores(contenido, punteros);
        System.out.println(punteros.length);
        
        // Si el archivo necesitaba mayor cantidad de sectores de los que estan disponibles en el disco virtual,
        // limpia los sectores que le asigno al archivo e indica que no se pudo guardar
        //if(punteros.length > discoVirtual.getCantSectores()){
        if(sectoresInsuficientes()){
        	System.out.println("No hay suficiente espacio para crear el archivo");
        	for(int ind = 0; ind < punteros.length; ind++){
        		if(punteros[ind] == -1 ){
        			break;
        		}
        		discoVirtual.escribirSector("", punteros[ind]);
        	}
        	return false;
        }
        
    	return true;
    }
    

    
    public String getContenido(){
        String retorno = "";
        for (int i = 0; i < punteros.length; i++){
            retorno = retorno + (DiscoVirtual.getInstance(0, 0, "").getSector(punteros[i]));
        }
        System.out.println();
        
        return retorno;      
    }
    private boolean sectoresInsuficientes(){
    	for(int i = 0; i < punteros.length; i++){
    		if(punteros[i] == -1){
    			return true;
    		}
    	}
    	return false;
    }
    
}
