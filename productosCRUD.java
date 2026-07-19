import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class productosCRUD {
    public void insertarProducto(producto prod) {
        String sql = "INSERT INTO producto(nombre_producto, estado, precio) VALUES(?, ?, ?)";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, prod.getNombre_producto());
                stmt.setBoolean(2, prod.getEstado());
                stmt.setDouble(3, prod.getPrecio());
                stmt.executeUpdate();

                System.out.println("Producto insertado correctamente");

                
            }
        } catch (Exception e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    // DELETE
    public void eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int filas = stmt.executeUpdate();

                if (filas > 0) {
                    System.out.println("Producto eliminado correctamente");
                } else {
                    System.out.println("No se encontró el producto");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    // SELECT
    public void listarProductos() {
        String sql = "SELECT * FROM producto";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id_producto") +
                                       " | Nombre: " + rs.getString("nombre_producto") +
                                       " | Precio: " + rs.getDouble("precio") +
                                       " | Estado: " + rs.getBoolean("estado"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }
    }

    public producto obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return null;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new producto(
                                rs.getInt("id_producto"),
                                rs.getString("nombre_producto"),
                                rs.getDouble("precio"),
                                rs.getBoolean("estado")
                        );
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }

        return null;
    }

    // UPDATE
    public void actualizarProducto(producto producto) {
        String sql = "UPDATE producto SET nombre_producto = ?, estado = ?, precio = ? WHERE id_producto = ?";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, producto.getNombre_producto());
                stmt.setBoolean(2, producto.getEstado());
                stmt.setDouble(3, producto.getPrecio());
                stmt.setInt(4, producto.getId_producto());

                int filas = stmt.executeUpdate();

                if (filas > 0) {
                    System.out.println("Producto actualizado correctamente");
                } else {
                    System.out.println("No se encontró el producto");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    public void actualizarProducto(int id, String nuevoNombre, double nuevoPrecio, boolean nuevoEstado) {
        producto producto = new producto(id, nuevoNombre, nuevoPrecio, nuevoEstado);
        actualizarProducto(producto);
    }
}
