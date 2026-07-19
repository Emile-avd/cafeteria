import java.util.ArrayList;
import java.util.List;

public class inicioSesion {
    private usuario usuarioActual;
    private List<usuario> usuarios;

    public void cerrarSesion() {
        usuarioActual = null;
    }

    public boolean estaAutenticado() {
        return usuarioActual != null;
    }

    public usuario getUsuarioActual() {
        return usuarioActual;
    }
}