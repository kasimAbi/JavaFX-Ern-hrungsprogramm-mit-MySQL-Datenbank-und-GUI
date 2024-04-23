/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm.Entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Kasim
 */
public class Benutzer{
    private int id;
    private String vorname;
    private String nachname;
    private int groesse;
    private int gewicht;
    private Date geburtsdatum;
    private String geschlecht;
    private String ziel;

    // Konstruktor
    public Benutzer(int id, String vorname, String nachname, int groesse, int gewicht, Date geburtsdatum, String geschlecht, String ziel) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.groesse = groesse;
        this.gewicht = gewicht;
        this.geburtsdatum = geburtsdatum;
        this.geschlecht = geschlecht;
        this.ziel = ziel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public String getZiel() {
        return ziel;
    }

    public void setZiel(String ziel) {
        this.ziel = ziel;
    }

    public int getGroesse() {
        return groesse;
    }

    public void setGroesse(int groesse) {
        this.groesse = groesse;
    }

    public int getGewicht() {
        return gewicht;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    } 
    
    public int getAlter() {
        // alter wird ausgerechnet und als integer wert zurückgegeben.
        return Period.between(Instant.ofEpochMilli(geburtsdatum.getTime()).atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears();
    }

    // kalorien ausrechnen je nach körpergröße gewicht, geschlecht und ziel des nutzers
    public int getMaxKcal() {
        Double kcalAusrechnen = 0.0;
        if(ziel.equals("zunehmen") && geschlecht.equals("Männlich")){   /* diese formeln für die berechnung des kilokalorien wurden aus dem internet übernommen. */
            kcalAusrechnen = (66.0 + (13.7 * gewicht) + (5.0 * groesse) - (6.76 * getAlter())) + 800.0;
        }else if(ziel.equals("abnehmen") && geschlecht.equals("Männlich")){
            kcalAusrechnen = (66.0 + (13.7 * gewicht) + (5.0 * groesse) - (6.76 * getAlter())) + 300.0;
        }else if(ziel.equals("zunehmen") && geschlecht.equals("Weiblich")){
            kcalAusrechnen = (66.0 + (13.7 * gewicht) + (5.0 * groesse) - (6.76 * getAlter())) + 300.0;
        }else if(ziel.equals("abnehmen") && geschlecht.equals("Weiblich")){
            kcalAusrechnen = (66.0 + (13.7 * gewicht) + (5.0 * groesse) - (6.76 * getAlter()));
        }
        return (int) Math.round(kcalAusrechnen);
    }
}