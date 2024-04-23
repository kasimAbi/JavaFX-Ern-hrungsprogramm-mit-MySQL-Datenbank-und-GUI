/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm.Entities;

import java.util.Date;

/**
 *
 * @author Kasim
 */
public class Rückblick {
    private int id;
    private int benutzerId;
    private int gesamtKohlenhydrate;
    private int gesamtEiweiss;
    private int gesamtFett;
    private int gesamtKcal;
    private int maxKcal;
    private Date datum;

    // Konstruktor um der datenbank ein rückblcik hinzuzufügen. die datenbank hat ein auto increment bei der ID.
    public Rückblick(int benutzerId, int gesamtKohlenhydrate, int gesamtEiweiss, int gesamtFett, int gesamtKcal, int maxKcal) {
        this.benutzerId = benutzerId;
        this.gesamtKohlenhydrate = gesamtKohlenhydrate;
        this.gesamtEiweiss = gesamtEiweiss;
        this.gesamtFett = gesamtFett;
        this.gesamtKcal = gesamtKcal;
        this.maxKcal = maxKcal;
    }
    
    // Konstruktor
    public Rückblick(int id, int benutzerId, int gesamtKohlenhydrate, int gesamtEiweiss, int gesamtFett, int gesamtKcal, int maxKcal, Date datum) {
        this.id = id;
        this.benutzerId = benutzerId;
        this.gesamtKohlenhydrate = gesamtKohlenhydrate;
        this.gesamtEiweiss = gesamtEiweiss;
        this.gesamtFett = gesamtFett;
        this.gesamtKcal = gesamtKcal;
        this.maxKcal = maxKcal;
        this.datum = datum;
    }

    public int getId() {
        return id;
    }
    
    public int getBenutzerId() {
        return benutzerId;
    }

    public void setBenutzerId(int benutzerId) {
        this.benutzerId = benutzerId;
    }

    public int getKohlenhydrate() {
        return gesamtKohlenhydrate;
    }

    public void setKohlenhydrate(int kohlenhydrate) {
        this.gesamtKohlenhydrate = kohlenhydrate;
    }

    public int getEiweiss() {
        return gesamtEiweiss;
    }

    public void setEiweiss(int eiweiss) {
        this.gesamtEiweiss = eiweiss;
    }

    public int getFett() {
        return gesamtFett;
    }

    public void setFett(int fett) {
        this.gesamtFett = fett;
    }

    public int getTagesKcal() {
        return gesamtKcal;
    }

    public void setTagesKcal(int tagesKcal) {
        this.gesamtKcal = tagesKcal;
    }

    public int getMaxKcal() {
        return maxKcal;
    }

    public void setMaxKcal(int maxKcal) {
        this.maxKcal = maxKcal;
    }

    public Date getDate() {
        return datum;
    }
}
