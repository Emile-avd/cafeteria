public class producto{
    private int id_producto;
    private String nombre_producto;
    private double precio;
    private boolean estado;

    public producto(int id, String nombre, double precio, boolean estado) {
        this.id_producto = id;
        this.nombre_producto = nombre;
        this.precio = precio;
        this.estado = estado;
    }

    public producto(String nombre, double precio, boolean estado) {
        this.nombre_producto = nombre;
        this.precio = precio;
        this.estado = estado;
    }
    // Getters and Setters
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}