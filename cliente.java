public class cliente {
    private int id;
    private String nombre;
    private int telefono;
    private String direccion;

    public cliente(String nombre, int telefono, int id, String direccion) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.id = id;
        this.direccion = direccion;
    }
    

    public String getNombre() {
        return nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    
}
