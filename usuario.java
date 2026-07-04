public class usuario {
    protected int id;
    protected String nombre;
    protected int telefono;
    protected String rol;
    public usuario(String nombre, int telefono, int id, String rol) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.id = id;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public int getId() {
        return id;
    }

    public String getRol() {
        return rol;
    }

}