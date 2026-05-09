package Controler;
import connection.DatabaseConnection;
import model.Product;
import model.Sale;
import model.SaleDetail;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main() {
        Scanner n = new Scanner(System.in);
        Scanner s = new Scanner(System.in);
        Sale sale = new Sale();
        ArrayList<SaleDetail> cesta = sale.getDetails();
        int op = 0;
        crearCarpetaBoletas();
        try {
            ShopController controller = new ShopController(new DatabaseConnection());
            do {
                menu();
                op = s.nextInt();
                ejecutarOp(op, controller,s,n,sale,cesta);
            }while (op != 0);
        } catch (Exception e) {
            System.out.println("Error al intentar la conexion, intentalo en unos minutos");
        }
    }


    public static void menu(){
        System.out.println("---------------SUPERMERCADO---------------------\n"+
                "0.Salir\n"+
                "1.Listar todos los productos\n"+
                "2.Buscar Producto por id\n"+
                "3.Agregar Producto a la cesta\n"+
                "4.Ver carrito\n"+
                "5.Realizar compra"
        );
    }

    private static void ejecutarOp(int op, ShopController controller,Scanner s,Scanner n,Sale sale,ArrayList<SaleDetail> cesta) {
        ArrayList<Product> listaPro = new ArrayList<>();

        switch (op){
            case 0:
                System.out.println("Saliendo...");
                break;

            case 1:
                listaPro = controller.listAllProducts();
                if (!listaPro.isEmpty())
                    for (Product p : listaPro) System.out.println(p.toString());
                else System.out.println("La lista de productos esta vacia");
                break;

            case 2:
                System.out.print("Buscar producto por nombre : ");
                listaPro = controller.findProductByName(s.nextLine());
                if (!listaPro.isEmpty())
                    for (Product p : listaPro) System.out.println(p.toString());
                else System.out.println("No se encontraron coincidencias");
                break;

            case 3:
                System.out.print("Ingresa el id del producto : ");
                Product p = controller.findProductById(n.nextInt());
                if (p != null){
                    System.out.print("Ingresa la cantidad : ");
                    int cantidad = n.nextInt();
                    int existe = comprobarSiEsta(p,cesta);
                    if (existe != -1) productYaExiste(cantidad,cesta,existe);
                    else cesta.add(new SaleDetail(p,cantidad));
                }else System.out.println("No se encontro producto con esa ID");
                break;

            case 4:
                System.out.println("*******CARRITO DE COMPRAS*******");
                if (!cesta.isEmpty()){
                    System.out.println(detallesVenta(cesta, sale));
                }
                else System.out.println("Carro vacio");
                break;

            case 5:
                int numBoleta = -1;
                System.out.println("Realizando compra....");
                if (!cesta.isEmpty()){

                    numBoleta = controller.registerSale(sale);

                    if (numBoleta != -1){
                        controller.registerSaleDetails(sale,numBoleta);
                        System.out.println("Generando boleta.....");
                        generarBoleta(sale, numBoleta);
                        System.out.println("Compra realizada!");
                    }
                } else System.out.println("Error, su carro de compras esta vacio");
                cesta.clear();
                break;

            default:
                System.out.println("Opcion invalida");
        }
    }

    private static String detallesVenta(ArrayList<SaleDetail> cesta, Sale sale) {
        String detalles = "PRODUCTO       -    PU    -   CANT    -    SUBTOTAL";

        for (SaleDetail sd : cesta)
            detalles+="\n"+sd.toString();
        detalles+="\n\n____________________________________________________"+
                "\n                  TOTAL                   : "+sale.calcularTotal();

        return detalles;
    }

    private static void crearCarpetaBoletas() {
        File carpeta = new File("boletas");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    private static void generarBoleta(Sale sale, int numBoleta) {
       File rutaBoleta = new File("boletas" + File.separator + "boleta-num-"+numBoleta+".txt");
        LocalDate fecha = LocalDate.now();
        try(
                FileWriter fw = new FileWriter(rutaBoleta);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw)
        ) {
            pw.println("********************************************************\n"+
                    "                      STORE S.A.C.                      \n"+
                    "********************************************************\n"+
                    "     N.º"+numBoleta+"                           "+fecha+"\n"
                    );

            pw.println(detallesVenta(sale.getDetails(),sale));
            pw.println("\n\n\n\n\n-----------------GRACIAS POR SU COMPRA-----------------");
        } catch (IOException e) {
            System.out.println("ERROR AL ENCONTRA LA RUTA");
        }

    }

    private static void productYaExiste(int cantidad, ArrayList<SaleDetail> cesta, int index) {
        cesta.get(index).setQuantity(cesta.get(index).getQuantity()+cantidad);
    }

    public static int comprobarSiEsta(Product p , ArrayList<SaleDetail> cesta){
        int index = 0;
        for (SaleDetail sd : cesta){
            if(sd.getProduct().equals(p)) return index;
            index++;
        }
        return -1;
    }
}

