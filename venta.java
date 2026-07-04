import java.util.*;
public class venta {

    private int cantidad;
    private Date fecha;

    public venta(int cantidad, Date fecha) {

        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    inventario inventario = new inventario("Producto", 1, 10, 100.0);

    public int getCantidad() {
        return cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void mostrarTicket() {
        System.out.println("***** Ticket de Venta: *****");
        System.out.println("Nombre del producto: " + inventario.getNombre());
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Fecha: " + fecha);
    }


}