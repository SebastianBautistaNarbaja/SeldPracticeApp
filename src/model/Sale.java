package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Sale {
    private int sale_id;
    private Date date;
    private BigDecimal total;
    private ArrayList<SaleDetail> details;

    public Sale(){
        details = new ArrayList<>();
    }

    public Sale(int sale_id, Date date, BigDecimal total) {
        this.sale_id = sale_id;
        this.date = date;
        this.total = total;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ArrayList<SaleDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<SaleDetail> details) {
        this.details = details;
    }

    public String toStringDetails() {
        return "Sale{" +
                "sale_id=" + sale_id +
                ", date=" + date +
                ", total=" + total +
                ", details=" + details +
                '}';
    }

    @Override
    public String toString() {
        return "Sale{" +
                "sale_id=" + sale_id +
                ", date=" + date +
                ", total=" + total +
                '}';
    }
}
