package dao;

import connection.DatabaseConnection;
import model.Product;
import model.Sale;
import model.SaleDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SaleDAO {

    private Connection conexion;

    public SaleDAO(DatabaseConnection dc){
        conexion = dc.getConexion();
    }

    public int insertSale(Sale s) throws SQLException {
        try(
                PreparedStatement preConsulta = conexion.prepareStatement("INSERT INTO sale (date,total) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preConsulta.setDate(1,new java.sql.Date(s.getDate().getTime()));
            preConsulta.setBigDecimal(2,s.getTotal());
            preConsulta.executeUpdate();
            try(
                    ResultSet response = preConsulta.getGeneratedKeys()
            ) {
                if (response.next()){
                    int num = response.getInt(1);
                    s.setSale_id(num);
                    return num;
                }
            }
        }
        return -1;
    }

    public void insertFullSale(Sale s) throws SQLException {

        int idVenta = insertSale(s);

        for (SaleDetail d : s.getDetails()) {

            d.setSale_id(idVenta);

            try (PreparedStatement ps = conexion.prepareStatement(
                    "INSERT INTO sale_detail (sale_id, product_id, quantity, unit_price) VALUES (?,?,?,?)"
            )) {

                ps.setInt(1, d.getSale_id());
                ps.setInt(2, d.getProduct().getProduct_id());
                ps.setInt(3, d.getQuantity());
                ps.setBigDecimal(4, d.getPrice());

                ps.executeUpdate();
            }
        }
    }

    public Sale findById(int id) throws SQLException {
        try(
                PreparedStatement preConsulta = conexion.prepareStatement("SELECT * FROM sale where sale_id = ?")
        ) {
            preConsulta.setInt(1,id);
            try(
                    ResultSet response = preConsulta.executeQuery()
            ) {
                if (response.next()) return transformToSale(response);
            }
        }
        return null;
    }

    public ArrayList<Sale> listAll() throws SQLException{
        ArrayList<Sale> list = new ArrayList<>();
        try(
                PreparedStatement preConsulta = conexion.prepareStatement("SELECT * FROM sale");
                ResultSet response = preConsulta.executeQuery()
        ) {
            while (response.next()) list.add(transformToSale(response));
        }
        return list;
    }

    private Sale transformToSale(ResultSet response) throws SQLException {
        Sale s = new Sale(
                response.getInt(1),
                response.getDate(2),
                response.getBigDecimal(3)
        );
        return s;
    }
}
