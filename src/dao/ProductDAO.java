package dao;

import connection.DatabaseConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {
    private Connection conexion ;

    public ProductDAO(DatabaseConnection dc){
        conexion = dc.getConexion();
    }

    public ArrayList<Product> listAll() throws SQLException{
        ArrayList<Product> list = new ArrayList<>();
        ResultSet response = null;
        PreparedStatement preConsulta = null;
        try{
            preConsulta = conexion.prepareStatement("select * from product");
            response = preConsulta.executeQuery();
            while (response.next()) list.add(transformToProduct(response));
        }finally {
            closeParametros(response,preConsulta);
        }
        return list;
    }

    public Product findById(int id) throws SQLException{
        ResultSet response = null;
        PreparedStatement preConsulta = null;
        try{
            preConsulta = conexion.prepareStatement("select * from product where product_id = ? ");
            preConsulta.setInt(1,id);
            response = preConsulta.executeQuery();
            if (response.next()) return transformToProduct(response);
        }finally {
            closeParametros(response,preConsulta);
        }
        return null;
    }

    public ArrayList<Product> findByName(String name) throws SQLException{
        ArrayList<Product> list = new ArrayList<>();
        ResultSet response = null;
        PreparedStatement preConsulta = null;
        try{
            preConsulta = conexion.prepareStatement("select * from product where name LIKE ?");
            preConsulta.setString(1,"%"+name+"%");
            response = preConsulta.executeQuery();
            while (response.next()) list.add(transformToProduct(response));
        }finally {
            closeParametros(response,preConsulta);
        }
        return list;
    }

    public boolean insertProduct(Product p) throws SQLException {
        PreparedStatement preConsulta = null;
        try{
            preConsulta = conexion.prepareStatement("INSERT INTO product (name, price, stock, category_id) VALUES (?,?,?,?)");
            preConsulta.setString(1, p.getName());
            preConsulta.setBigDecimal(2,p.getPrice());
            preConsulta.setInt(3,p.getStock());
            preConsulta.setInt(4,p.getCategory_id());
            return preConsulta.executeUpdate()!=0;
        }finally {
            closeParametros(null,preConsulta);
        }
    }

    public boolean deleteProduct(int id) throws SQLException {
        PreparedStatement preConsulta = null;
        try {
            preConsulta = conexion.prepareStatement("DELETE FROM product WHERE product_id = ?");
            preConsulta.setInt(1,id);
            return preConsulta.executeUpdate() != 0;
        }finally {
            closeParametros(null,preConsulta);
        }
    }

    public boolean updateProduct(Product p) throws SQLException {
        PreparedStatement preConsulta = null;
        try {
            preConsulta = conexion.prepareStatement("UPDATE product SET name = ?, price = ?, stock = ?, category_id = ? WHERE product_id = ?");
            preConsulta.setString(1,p.getName());
            preConsulta.setBigDecimal(2,p.getPrice());
            preConsulta.setInt(3,p.getStock());
            preConsulta.setInt(4,p.getCategory_id());
            preConsulta.setInt(5,p.getProduct_id());
            return preConsulta.executeUpdate() != 0;
        }finally {
            closeParametros(null,preConsulta);
        }
    }

    private void closeParametros(ResultSet response, PreparedStatement preConsulta) throws SQLException {
        if (response != null) response.close();
        if (preConsulta != null) preConsulta.close();
    }

    private Product transformToProduct(ResultSet response) throws SQLException {
        Product p = new Product(response.getInt(1),
                        response.getString(2),
                        response.getBigDecimal(3),
                        response.getInt(4),
                        response.getInt(5));
        return p;
    }


}
