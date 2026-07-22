package vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VentasManager {
    private List<venta> pendientes;
    private List<venta> enProceso;
    private historial historial;

    private ventaCRUD ventaCrud = new ventaCRUD();
    private detalleVentaCRUD detalleCrud = new detalleVentaCRUD();

    public VentasManager(historial historial) {
        this.pendientes = new ArrayList<>();
        this.enProceso = new ArrayList<>();
        this.historial = historial;
    }

    public venta crearVentaPendiente() {
        venta v = new venta();
        pendientes.add(v);
        return v;
    }

    public void moverAProceso(venta v) {
        if (pendientes.remove(v)) {
            enProceso.add(v);
        }
    }

    public void agregarItem(venta v, detalleVenta item) {
        v.addItem(item);
    }

    public void actualizarCantidad(venta v, int index, int nuevaCantidad) {
        if (index >= 0 && index < v.getItems().size()) {
            v.getItems().get(index).setCantidad(nuevaCantidad);
            v.recomputeTotals();
        }
    }

    public void eliminarItem(venta v, int index) {
        v.removeItem(index);
    }

    public void cancelarOrden(venta v) {
        pendientes.remove(v);
        enProceso.remove(v);
        v.setVen_estado(false);
        historial.agregarVenta(v);
    }

    public void finalizarVenta(venta v) {
        // marcar como finalizada
        v.setVen_estado(true);
        v.setVen_fecha(new Date());

        // persistir en BD
        ventaCrud.insertarVenta(v);

        // insertar detalles
        for (detalleVenta d : v.getItems()) {
            detalleCrud.insertarDetalle(d, v.getId_venta());
        }

        // mover a historial
        pendientes.remove(v);
        enProceso.remove(v);
        historial.agregarVenta(v);

        // imprimir ticket en consola
        v.mostrarTicket();
    }

    public List<venta> getPendientes() {
        return pendientes;
    }

    public List<venta> getEnProceso() {
        return enProceso;
    }
}
