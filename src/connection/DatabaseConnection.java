package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private String url = "jdbc:mysql://localhost:3306/store";
    private String user = "root";
    private String password = "";
    private Connection conexion ;

    public DatabaseConnection() throws SQLException {
        conexion = DriverManager.getConnection(url,user,password);
    }

    public Connection getConexion() {
        return conexion;
    }
}
