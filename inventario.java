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

    public int getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void mostrarInventario() {
        System.out.println("***** Inventario: *****");
        System.out.println("Nombre: " + nombre);
        System.out.println("ID: " + id);
        System.out.println("Stock: " + stock);
        System.out.println("Precio: " + precio);
    }

    public void agregarProducto(String nombre, int id, int stock, double precio) {
        this.nombre = nombre;
        this.id = id;
        this.stock = stock;
        this.precio = precio;
    }
}
