package com.fachru.myapplication.model;

import java.util.Date;

/**
 * Created by fachru on 10/03/16.
 */
public class InboxTodo {

    public Date dtr = new Date();
    public Date dtp = new Date();
    public String fromHp = "081291263503";
    public String fromName = "Arief Fachru Rozi";
    public String message = "TS5.081291263503.1111";
    public String mode = "sms";
    public boolean blockStatus = false;
    public String transmisiHp = "TS5.081291263503.????";
    public String comPort = "3";
    public String userId = "-";
    public String xStatus = "VCH";
    public String docno = "-";
    public String jobId = "-";
    public double idx;
    public int jobProsesId=0;
    public String jamHp = "-";
    public String csstatid1 = "9999";
    public String csstatid4 = "99";
    public String h2h = "-";
    public String pesan2 = "-";

    public InboxTodo() {

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
