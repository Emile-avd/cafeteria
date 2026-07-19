import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConexionDB
 */
public class conexionDb {

    private static final String URL = "jdbc:mysql://localhost:3306/cafeteria";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConexion(){
        Connection conexion = null;

        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Conexion exitosa");

        } catch (ClassNotFoundException e) {
            System.out.println("No se encontro el drive MYSQL. agregar el archivo .jar del conector a lib");
            e.printStackTrace();
        } catch (SQLException e){
            System.out.println("Error en la conexion"+e.getMessage());
        }

        return conexion;
    }


}


