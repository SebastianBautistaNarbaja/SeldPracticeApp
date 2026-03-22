package dao;

import connection.DatabaseConnection;
import model.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class Test {
    static void main() {
        try{
            DatabaseConnection dc = new DatabaseConnection();
            ProductDAO productoConexion = new ProductDAO(dc);
            ArrayList<Product> list = new ArrayList<>();
            list = productoConexion.listAll();
            for (Product p : list){
                System.out.println(p.toString());
            }


        }catch (SQLException s){
            System.out.println("Hubo un error al conectar al servidor");
        }


    }
}
