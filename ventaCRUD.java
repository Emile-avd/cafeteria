package vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class ventaCRUD {
    public void insertarVenta(venta v) {
        String sql = "INSERT INTO venta(ven_total, ven_cantidad_productos, ven_fecha, ven_estado) VALUES(?, ?, ?, ?)";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setDouble(1, v.getVen_total());
                stmt.setInt(2, v.getVen_cantidad_productos());
                if (v.getVen_fecha() != null) {
                    stmt.setTimestamp(3, new Timestamp(v.getVen_fecha().getTime()));
                } else {
                    stmt.setTimestamp(3, null);
                }
                stmt.setBoolean(4, v.isVen_estado());
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        v.setId_venta(generatedId);
                    }
                }

                System.out.println("Venta insertada correctamente, id=" + v.getId_venta());
            }
        } catch (Exception e) {
            System.out.println("Error al insertar venta: " + e.getMessage());
        }
    }

    // DELETE
    public void eliminarVenta(int id) {
        String sql = "DELETE FROM venta WHERE id_venta = ?";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int filas = stmt.executeUpdate();

                if (filas > 0) {
                    System.out.println("Venta eliminada correctamente");
                } else {
                    System.out.println("No se encontró la venta");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar venta: " + e.getMessage());
        }
    }

    // SELECT
    public void listarVentas() {
        String sql = "SELECT * FROM venta";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id_venta") +
                                       " | Total: " + rs.getDouble("ven_total") +
                                       " | Cantidad: " + rs.getInt("ven_cantidad_productos") +
                                       " | Fecha: " + rs.getTimestamp("ven_fecha") +
                                       " | Estado: " + rs.getBoolean("ven_estado"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar ventas: " + e.getMessage());
        }
    }

    public venta obtenerVentaPorId(int id) {
        String sql = "SELECT * FROM venta WHERE id_venta = ?";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return null;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new venta(
                                rs.getInt("id_venta"),
                                rs.getDouble("ven_total"),
                                rs.getInt("ven_cantidad_productos"),
                                rs.getTimestamp("ven_fecha"),
                                rs.getBoolean("ven_estado")
                        );
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar venta: " + e.getMessage());
        }

        return null;
    }

    // UPDATE
    public void actualizarVenta(venta v) {
        String sql = "UPDATE venta SET ven_total = ?, ven_cantidad_productos = ?, ven_fecha = ?, ven_estado = ? WHERE id_venta = ?";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, v.getVen_total());
                stmt.setInt(2, v.getVen_cantidad_productos());
                if (v.getVen_fecha() != null) {
                    stmt.setTimestamp(3, new Timestamp(v.getVen_fecha().getTime()));
                } else {
                    stmt.setTimestamp(3, null);
                }
                stmt.setBoolean(4, v.isVen_estado());
                stmt.setInt(5, v.getId_venta());

                int filas = stmt.executeUpdate();

                if (filas > 0) {
                    System.out.println("Venta actualizada correctamente");
                } else {
                    System.out.println("No se encontró la venta");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar venta: " + e.getMessage());
        }
    }

    public void actualizarVenta(int id, double nuevoTotal, int nuevaCantidad, java.util.Date nuevaFecha, boolean nuevoEstado) {
        venta v = new venta(id, nuevoTotal, nuevaCantidad, nuevaFecha, nuevoEstado);
        actualizarVenta(v);
    }
}
