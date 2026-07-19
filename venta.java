import java.util.Date;

public class venta {

    private inventario producto; // referencia opcional al inventario
    private String nombreProducto; // snapshot del nombre al vender
    private double unitario; // snapshot del precio unitario al vender
    private int cantidad;
    private Date fecha;

    public venta(inventario producto, int cantidad, Date fecha) {
        this.producto = producto; // puede quedar para referencia, no es obligatorio
        this.cantidad = cantidad;
        this.fecha = fecha;
        // Guardar snapshot para mantener historial inmutable
        if (producto != null) {
            this.unitario = producto.getPrecio();
            this.nombreProducto = producto.getNombre();
        } else {
            this.unitario = 0.0;
            this.nombreProducto = "(producto desconocido)";
        }
    }

    public inventario getProducto() {
        return producto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getTotal() {
        return cantidad * unitario;
    }

    public double getUnitario() {
        return unitario;
    }

    public void mostrarTicket() {
        System.out.println("***** Ticket de Venta: *****");
        System.out.println("Nombre del producto: " + nombreProducto);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Precio unitario: " + unitario);
        System.out.println("Subtotal: " + getTotal());
        System.out.println("Fecha: " + fecha);
    }
}
