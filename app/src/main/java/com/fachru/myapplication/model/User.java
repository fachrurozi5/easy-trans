package com.fachru.myapplication.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by fachru on 14/03/16.
 */
@Table(name = "Users")
public class User extends Model{

    @Column(name = "custid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String custid;

    @Column(name = "custname")
    public String custname;

    @Column(name = "tingkat")
    public String tingkat;

    @Column(name = "tipe")
    public String tipe;

    @Column(name = "pin")
    public String pin;

    @Column(name = "deposit")
    public double deposit;

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "custid='" + custid + '\'' +
                ", custname='" + custname + '\'' +
                ", tingkat='" + tingkat + '\'' +
                ", tipe='" + tipe + '\'' +
                ", pin='" + pin + '\'' +
                ", deposit=" + deposit +
                '}';
    }
}
