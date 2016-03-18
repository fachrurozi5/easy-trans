package com.fachru.myapplication.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fachru on 14/03/16.
 */
@Table(name = "Products")
public class Product extends Model{

    @SerializedName("prodid")
    @Column(name = "prodid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String prodid;

    @SerializedName("prodname1")
    @Column(name = "prodname1")
    public String prodname1;

    @SerializedName("ev_nilai")
    @Column(name = "ev_nilai")
    public int ev_nilai;

    @SerializedName("price")
    @Column(name = "price")
    public double price;


    public Product() {
        super();
    }

    public static Product find(String prodid) {
        return new Select()
                .from(Product.class)
                .where("prodid =?", prodid)
                .executeSingle();
    }

    public static List<Product> find() {
        return new Select()
                .from(Product.class)
                .execute();
    }

    public String getString() {
        return "Product{" +
                "prodid='" + prodid + '\'' +
                ", prodname1='" + prodname1 + '\'' +
                ", ev_nilai=" + ev_nilai +
                ", price=" + price +
                '}';
    }

    @Override
    public String toString() {
        return String.format("Rp. %d,000.-", ev_nilai);
    }
}
