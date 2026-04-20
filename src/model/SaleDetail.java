package model;

import java.math.BigDecimal;

public class SaleDetail {

    private int saleDetail_id;
    private int sale_id;
    private Product product;
    private int quantity;
    private BigDecimal price;

    public SaleDetail(){}

    public SaleDetail(Product product, int quantity) {
        this.saleDetail_id = 0;
        this.sale_id = 0;
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }

    public int getSaleDetail_id() {
        return saleDetail_id;
    }

    public void setSaleDetail_id(int saleDetail_id) {
        this.saleDetail_id = saleDetail_id;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubTotal(){
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
