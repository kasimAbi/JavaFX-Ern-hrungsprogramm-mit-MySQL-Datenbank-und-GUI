/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm.Entities;

/**
 *
 * @author Kasim
 */
public class Produkte {
    private int id;
    private String produktName;
    private String kategorie;
    private int kohlenhydrateMenge;
    private int eiweissMenge;
    private int fettMenge;
    private int kcalAnzahl;
    
    // Konstruktor um der datenbank ein produkt hinzuzufügen. die datenbank hat ein auto increment bei der ID
    public Produkte(String produktName, String kategorie, int kohlenhydrateMenge, int eiweissMenge, int fettMenge, int kcalAnzahl){
        this.produktName = produktName;
        this.kategorie = kategorie;
        this.kohlenhydrateMenge = kohlenhydrateMenge;
        this.eiweissMenge = eiweissMenge;
        this.fettMenge = fettMenge;
        this.kcalAnzahl = kcalAnzahl;
    }
    
    // Konstruktor
    public Produkte(int id, String produktName, String kategorie, int kohlenhydrateMenge, int eiweissMenge, int fettMenge, int kcalAnzahl){
        this.id = id;
        this.produktName = produktName;
        this.kategorie = kategorie;
        this.kohlenhydrateMenge = kohlenhydrateMenge;
        this.eiweissMenge = eiweissMenge;
        this.fettMenge = fettMenge;
        this.kcalAnzahl = kcalAnzahl;
    }

    public int getId() {
        return id;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public String getProduktName() {
        return produktName;
    }

    public void setProduktName(String produktName) {
        this.produktName = produktName;
    }

    public int getKohlenhydrateMenge() {
        return kohlenhydrateMenge;
    }

    public void setKohlenhydrateMenge(int kohlenhydrateMenge) {
        this.kohlenhydrateMenge = kohlenhydrateMenge;
    }

    public int getEiweissMenge() {
        return eiweissMenge;
    }

    public void setEiweissMenge(int eiweissMenge) {
        this.eiweissMenge = eiweissMenge;
    }

    public int getFettMenge() {
        return fettMenge;
    }

    public void setFettMenge(int fettMenge) {
        this.fettMenge = fettMenge;
    }

    public int getKcalAnzahl() {
        return kcalAnzahl;
    }

    public void setKcalAnzahl(int kcalAnzahl) {
        this.kcalAnzahl = kcalAnzahl;
    }
}
