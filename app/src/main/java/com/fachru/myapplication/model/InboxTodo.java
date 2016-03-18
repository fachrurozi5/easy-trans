package com.fachru.myapplication.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by fachru on 10/03/16.
 */
@Table(name = "inbox_to_do")
public class InboxTodo extends Model{

    /*private static final String comPort = "3";
    private static final String xStatus = "VCH";
    private static final String mode = "SMS";*/

    @Expose
    @Column(name = "pesan", unique = true)
    private String pesan2;

    @Expose
    @Column(name = "dtr")
    private final Date dtr = new Date();

    @Expose
    @Column(name = "dtp")
    private final Date dtp = new Date();

    @Expose
    @SerializedName("fromhp")
    @Column(name = "from_hp")
    private String fromHp;

    @Expose
    @SerializedName("fromname")
    @Column(name = "from_name")
    private String fromName;

    @Expose
    @Column(name = "message")
    private String message;

    @Expose
    @SerializedName("transmisihp")
    @Column(name = "transmisi_hp")
    private String transmisiHp;

    @Expose
    @Column(name = "csstatid1")
    private String csstatid1;

    @Expose
    @Column(name = "csstatid4")
    private String csstatid4;

    @Expose
    @Column(name = "xstatus")
    private String xstatus = "VCH";

    @Expose
    @SerializedName("usrupdate")
    @Column(name = "status")
    private String status;

    public InboxTodo() {
        super();
        UUID uuid = UUID.randomUUID();
        pesan2 = String.valueOf(uuid);
    }

    public InboxTodo(String prodid, String no_tujuan, User user) {
        super();
        UUID uuid = UUID.randomUUID();
        pesan2 = String.valueOf(uuid);
        fromHp = user.custid;
        fromName = user.custname;
        csstatid1 = user.cabang;
        csstatid4 = user.wilayah;
        message = String.format("%s.%s.%s", prodid, no_tujuan, user.pin);
        transmisiHp = String.format("%s.%s.???", prodid, no_tujuan);
    }

    public void setXstatus(String xstatus) {
        this.xstatus = xstatus;
    }

    public String getXstatus() {
        return this.xstatus;
    }

    public String getPesan2() {
        return pesan2;
    }

    public String getMessage() {
        return message;
    }

    public String _getStatus() {
        if (status.equalsIgnoreCase("berhasil2") || status.equalsIgnoreCase("berhasil"))
            return "Berhasil";
        else
            return "Gagal";
    }

    public String getNominal() {
        Product product = Product.find(message.split("\\.")[0]);

        return String.format("Rp. %d,000.-", product.ev_nilai);
    }

    public static InboxTodo find(String pesan2) {
        return new Select()
                .from(InboxTodo.class)
                .where("pesan = ? ", pesan2)
                .executeSingle();
    }

    public static boolean hasStatusVCH() {
        return new Select()
                .from(InboxTodo.class)
                .where("xstatus = ? ", "VCH")
                .execute().size() > 0;
    }

    public static List<InboxTodo> getStatusVCH() {
        return new Select()
                .from(InboxTodo.class)
                .where("xstatus = ? ", "VCH")
                .execute();
    }

    @Override
    public String toString() {
        return "InboxTodo{" +
                "pesan2='" + pesan2 + '\'' +
                ", dtr=" + dtr +
                ", dtp=" + dtp +
                ", fromHp='" + fromHp + '\'' +
                ", fromName='" + fromName + '\'' +
                ", message='" + message + '\'' +
                ", transmisiHp='" + transmisiHp + '\'' +
                ", csstatid1='" + csstatid1 + '\'' +
                ", csstatid4='" + csstatid4 + '\'' +
                ", xstatus='" + xstatus + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
