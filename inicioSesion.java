import java.util.ArrayList;
import java.util.List;

public class inicioSesion {
    private usuario usuarioActual;
    private List<usuario> usuarios;

    public boolean iniciarSesion(String email, String pass, usuarioCRUD crud) {
        usuario encontrado = crud.autenticarUsuario(email, pass);
        if (encontrado != null) {
            usuarioActual = encontrado;
            return true;
        }

        usuarioActual = null;
        return false;
    }

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