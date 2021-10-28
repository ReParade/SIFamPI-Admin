package java.hospital.adminhosptial;

public class contenido_lista_dos {

    private String Nombre;
    private String id;
    private String Estado;

    public contenido_lista_dos() {

    }

    public contenido_lista_dos(String Nombre, String id, String Estado){
        this.Nombre = Nombre;
        this.id = id;
        this.Estado = Estado;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getID() {
        return id;
    }

    public String getEstado() {
        return Estado;
    }

}
