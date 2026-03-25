package dao;

import connection.DatabaseConnection;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {
    private Connection conexion;

    public CategoryDAO(DatabaseConnection dc){
        conexion = dc.getConexion();
    }

    public ArrayList<Category> listAll() throws SQLException {
        ArrayList<Category> list = new ArrayList<>();
        try(
                ResultSet response = conexion.prepareStatement("SELECT * FROM category").executeQuery()
        ) {
            while (response.next()) list.add(transformToCategory(response));
        }
        return list;
    }

    public Category findById(int id) throws SQLException {
        Category c = new Category();
        try(
                PreparedStatement preConsulta = conexion.prepareStatement("SELECT * FROM category WHERE category_id = ?")
        ) {
            preConsulta.setInt(1,id);
            try(
                    ResultSet response = preConsulta.executeQuery()
            ){
                if (response.next()) return transformToCategory(response);
            }
        }
        return null;
    }

    private Category transformToCategory(ResultSet response) throws SQLException {
        Category c = new Category(response.getInt(1),response.getString(2));
        return c;
    }
}
