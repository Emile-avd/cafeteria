import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class usuarioCRUD {
    public void insertarUsuario(usuario user) {
        String sql = "INSERT INTO usuario(nombre_usuario, email, passw, rol) VALUES(?, ?, ?, ?)";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, user.getnombre_usuario());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassw());
                stmt.setString(4, user.getRol());
                stmt.executeUpdate();

                System.out.println("Usuario insertado correctamente");

                
            }
        } catch (Exception e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    // DELETE
    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int filas = stmt.executeUpdate();

                if (filas > 0) {
                    System.out.println("Usuario eliminado correctamente");
                } else {
                    System.out.println("No se encontró el usuario");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    // SELECT
    public void listarUsuarios() {
        String sql = "SELECT * FROM usuario";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id_usuario") +
                                       " | Nombre: " + rs.getString("nombre_usuario") +
                                       " | Email: " + rs.getString("email") +
                                       " | Rol: " + rs.getString("rol"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }
    }

    public usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return null;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new usuario(
                                rs.getString("nombre_usuario"),
                                rs.getString("email"),
                                rs.getString("passw"),
                                rs.getString("rol"),
                                rs.getInt("id_usuario")
                        );
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }

        return null;
    }

    // UPDATE
    public void actualizarUsuario(usuario usuario) {
        String sql = "UPDATE usuario SET nombre_usuario = ?, email = ?, passw = ?, rol = ? WHERE id_usuario = ?";

        try (Connection conn = conexionDb.getConexion()) {
            if (conn == null) {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, usuario.getnombre_usuario());
                stmt.setString(2, usuario.getEmail());
                stmt.setString(3, usuario.getPassw());
                stmt.setString(4, usuario.getRol());
                stmt.setInt(5, usuario.getId());

                int filas = stmt.executeUpdate();

                if (filas > 0) {
                    System.out.println("Usuario actualizado correctamente");
                } else {
                    System.out.println("No se encontró el usuario");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    public void actualizarUsuario(int id, String nuevoNombre, String nuevoEmail, String nuevoPass, String nuevoRol) {
        usuario usuario = new usuario(nuevoNombre, nuevoEmail, nuevoPass, nuevoRol, id);
        actualizarUsuario(usuario);
    }
}

