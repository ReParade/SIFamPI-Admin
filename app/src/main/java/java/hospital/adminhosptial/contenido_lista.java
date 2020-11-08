package java.hospital.adminhosptial;

public class contenido_lista {

    private String Nombre;
    private String id;

    public contenido_lista() {

    }

    public contenido_lista(String Nombre, String id){
        this.Nombre = Nombre;
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getID() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }
}//Fin public class
