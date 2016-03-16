package com.fachru.myapplication.model;

import java.util.Date;

/**
 * Created by fachru on 10/03/16.
 */
public class InboxTodo {

    private Date dtr = new Date();
    private Date dtp = new Date();
    private String fromHp = "081291263503";
    private String fromName = "Arief Fachru Rozi";
    private String message;
    private String mode = "sms";
    private boolean blockStatus = false;
    private String transmisiHp;
    private String comPort = "3";
    private String userId = "-";
    private String xStatus = "VCH";
    private String docno = "-";
    private String jobId = "-";
    private double idx;
    private int jobProsesId=0;
    private String jamHp = "-";
    private String csstatid1 = "9999";
    private String csstatid4 = "99";
    private String h2h = "-";
    private String pesan2 = "-";

    public InboxTodo() {

    }

    public InboxTodo(String prodid, String no_tujuan, User user) {
        fromHp = user.custid;
        fromName = user.custname;
        message = String.format("%s.%s.%s", prodid, no_tujuan, user.pin);
        transmisiHp = String.format("%s.%s.???", prodid, no_tujuan);
    }

    @Override
    public String toString() {
        return "InboxTodo{" +
                "dtr=" + dtr +
                ", dtp=" + dtp +
                ", fromHp='" + fromHp + '\'' +
                ", fromName='" + fromName + '\'' +
                ", message='" + message + '\'' +
                ", mode='" + mode + '\'' +
                ", blockStatus=" + blockStatus +
                ", transmisiHp='" + transmisiHp + '\'' +
                ", comPort='" + comPort + '\'' +
                ", userId='" + userId + '\'' +
                ", xStatus='" + xStatus + '\'' +
                ", docno='" + docno + '\'' +
                ", jobId='" + jobId + '\'' +
                ", idx=" + idx +
                ", jobProsesId=" + jobProsesId +
                ", jamHp='" + jamHp + '\'' +
                ", csstatid1='" + csstatid1 + '\'' +
                ", csstatid4='" + csstatid4 + '\'' +
                ", h2h='" + h2h + '\'' +
                ", pesan2='" + pesan2 + '\'' +
                '}';
    }
}
