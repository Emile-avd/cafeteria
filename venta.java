import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class venta {

    private int id_venta;
    private double ven_total;
    private int ven_cantidad_productos;
    private Date ven_fecha;
    private boolean ven_estado;
    private List<detalleVenta> items;

    public venta(int id_venta, double ven_total, int ven_cantidad_productos, Date ven_fecha, boolean ven_estado) {
        this.id_venta = id_venta;
        this.ven_total = ven_total;
        this.ven_cantidad_productos = ven_cantidad_productos;
        this.ven_fecha = ven_fecha;
        this.ven_estado = ven_estado;
        this.items = new ArrayList<>();
    }

    // Constructor para venta nueva (sin id aún)
    public venta() {
        this.items = new ArrayList<>();
        this.ven_estado = false; // pendiente por defecto
        this.ven_fecha = new Date();
        this.ven_total = 0.0;
        this.ven_cantidad_productos = 0;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public double getVen_total() {
        return ven_total;
    }

    public void setVen_total(double ven_total) {
        this.ven_total = ven_total;
    }

    public int getVen_cantidad_productos() {
        return ven_cantidad_productos;
    }

    public void setVen_cantidad_productos(int ven_cantidad_productos) {
        this.ven_cantidad_productos = ven_cantidad_productos;
    }

    public Date getVen_fecha() {
        return ven_fecha;
    }

    public void setVen_fecha(Date ven_fecha) {
        this.ven_fecha = ven_fecha;
    }

    public boolean isVen_estado() {
        return ven_estado;
    }

    public void setVen_estado(boolean ven_estado) {
        this.ven_estado = ven_estado;
    }

    public List<detalleVenta> getItems() {
        return items;
    }

    public void setItems(List<detalleVenta> items) {
        this.items = items;
        recomputeTotals();
    }

    public void addItem(detalleVenta item) {
        this.items.add(item);
        recomputeTotals();
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            recomputeTotals();
        }
    }

    public void recomputeTotals() {
        double total = 0.0;
        int cantidad = 0;
        for (detalleVenta d : items) {
            total += d.getSubtotal();
            cantidad += d.getCantidad();
        }
        this.ven_total = total;
        this.ven_cantidad_productos = cantidad;
    }

    // Métodos requeridos por historial
    public double getTotal() {
        return this.ven_total;
    }

    public int getCantidad() {
        return this.ven_cantidad_productos;
    }

    public void mostrarTicket() {
        System.out.println("=== Ticket Venta #" + id_venta + " ===");
        for (detalleVenta d : items) {
            System.out.printf("%s | Cant: %d | P.Unit: %.2f | Sub: %.2f\n",
                    d.getNombre_producto(), d.getCantidad(), d.getPrecio_unitario(), d.getSubtotal());
        }
        System.out.println("--------------------------");
        System.out.printf("Total a pagar: $%.2f\n", ven_total);
        System.out.println("Fecha: " + ven_fecha);
    }
}
