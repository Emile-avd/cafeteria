import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class detalleVentaCRUD {
    public void insertarDetalle(detalleVenta d, int ventaId) {
        String sql = "INSERT INTO detalleVenta(venta_id, producto_id, nombre_producto, cantidad, precio_unitario, subtotal) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, ventaId);
                stmt.setInt(2, d.getProducto_id());
                stmt.setString(3, d.getNombre_producto());
                stmt.setInt(4, d.getCantidad());
                stmt.setDouble(5, d.getPrecio_unitario());
                stmt.setDouble(6, d.getSubtotal());

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        d.setId_detalle(generatedKeys.getInt(1));
                        d.setId_venta(ventaId);
                    }
                }

                System.out.println("Detalle insertado para venta id=" + ventaId);
            }
        } catch (Exception e) {
            System.out.println("Error al insertar detalle: " + e.getMessage());
        }
    }
}
