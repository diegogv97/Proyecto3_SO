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
    
    
    
}
