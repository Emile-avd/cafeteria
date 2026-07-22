package vista;

import java.util.ArrayList;
import java.util.List;

public class historial {
    private List<venta> ventas;

    public historial() {
        this.ventas = new ArrayList<>();
    }

    public void agregarVenta(venta venta) {
        ventas.add(venta);
    }

    public List<venta> getVentas() {
        return ventas;
    }

    public void mostrarHistorial() {
        System.out.println("=== Historial de ventas ===");
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        for (venta v : ventas) {
            v.mostrarTicket();
            System.out.println("--------------------------");
        }
    }

    public void mostrarResumen() {
        System.out.println("=== Resumen de historial ===");
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        double totalGeneral = 0;
        int totalItems = 0;
        for (venta v : ventas) {
            totalGeneral += v.getTotal();
            totalItems += v.getCantidad();
        }
        System.out.println("Ventas registradas: " + ventas.size());
        System.out.println("Total de artículos vendidos: " + totalItems);
        System.out.println("Total acumulado: $" + totalGeneral);
    }
}

