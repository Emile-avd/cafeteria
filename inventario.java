import java.util.*;
public class inventario {
    private String nombre;
    private int id;
    private int stock;
    private double precio;

    public inventario(String nombre, int id, int stock, double precio) {
        this.nombre = nombre;
        this.id = id;
        this.stock = stock;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void mostrarInventario() {
        
    }

    public void agregarProducto(String nombre, int id, int stock, double precio) {
        this.nombre = nombre;
        this.id = id;
        this.stock = stock;
        this.precio = precio;
    }
}
