package Controler;

import connection.DatabaseConnection;
import dao.ProductDAO;
import dao.SaleDAO;
import model.Product;
import model.Sale;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShopController {
    private SaleDAO saleDAO ;
    private ProductDAO productDAO ;

    public ShopController(DatabaseConnection dc)  {
        this.productDAO = new ProductDAO(dc);
        this.saleDAO = new SaleDAO(dc);
    }

    public ArrayList<Product> listAllProducts()  {
        ArrayList<Product> p = new ArrayList<>();
        try {
            p = productDAO.listAll();
        } catch (SQLException e) {
            System.out.println("Ocurrio un error al listar los productos");
        }
        return p;
    }

    public ArrayList<Product> findProductByName(String name){
        ArrayList<Product> p = new ArrayList<>();
        try {
            p = productDAO.findByName(name);
        } catch (SQLException e) {
            System.out.println("Ocurrio un error al buscar producto por nombre");
        }
        return p;
    }

    public Product findProductById(int id) {
        try {
            return productDAO.findById(id);
        } catch (SQLException e) {
            System.out.println("Ocurrio un error al traer el producto por id");
        }
        return null;
    }

    public void realizarCompra(Sale s){
        try{
            s.setDate(new java.util.Date());
            s.setTotal(s.calcularTotal());
            saleDAO.insertFullSale(s);
        }catch (SQLException e){
            System.out.println("Error al realizar la compra");
        }
    }


}
