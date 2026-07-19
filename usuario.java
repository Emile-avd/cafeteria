import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class usuario {
    protected int id_usuario;
    protected String nombre_usuario;
    protected String email;
    protected String passw;
    protected String rol;
    public usuario(String nombre_usuario, String email, String passw, String rol, int id_usuario) {
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.passw = passw;
        this.rol = rol;
        this.id_usuario = id_usuario;
    }

    public usuario(String nombre_usuario, String email, String passw, String rol) {
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.passw = passw;
        this.rol = rol;
    }
    
    public String getnombre_usuario() {
        return nombre_usuario;
    }

    public void setnombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public int getId() {
        return id_usuario;
    }

    public void setId(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}