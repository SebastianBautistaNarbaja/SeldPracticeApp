package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private String url = "jdbc:mysql://localhost:3306/store";
    private String user = "root";
    private String password = "";
    private Connection conexion ;

    public DatabaseConnection(){
       try {
           conexion = DriverManager.getConnection(url,user,password);
       } catch (SQLException e) {
           //lanzo el error para que el main lo controle
           throw new RuntimeException(e);
       }
    }

    public Connection getConexion() {
        return conexion;
    }
}
