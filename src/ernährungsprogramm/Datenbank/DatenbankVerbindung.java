/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm.Datenbank;

import ernährungsprogramm.Entities.Benutzer;
import ernährungsprogramm.Entities.Produkte;
import ernährungsprogramm.Entities.Rückblick;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Kasim
 */
public class DatenbankVerbindung {
    
    // Variablendeklarationen für die Objekte die zurückgegeben werden und in denen Informationen drin gespeichert werden.
    private Benutzer benutzer;
    private ObservableList<Produkte> OLprodukte;
    private ObservableList<Rückblick> OLrueckblick;
    private Rückblick heute;
    
    // Verbindungsdaten
    private final String dbUrl = "jdbc:mysql://localhost:3306/ernaehrungsprogramm?autoReconnect=true&serverTimezone=UTC";
    private final String dbUsername = "root";
    private final String dbPassword = "";
    
    private String query;   // um eine anfrage der datenbank zu schicken.
    
    private Connection connection = null;   // Für Verbindungsaufbau
    private Statement statement = null;     // Für das SQL Befehlsobjekt
    private ResultSet resultSet = null;     // Für die Ergebnismenge
    
    // informationen aus der datenbank bekommen
    private void getInfo(String aufgabe){
        try {
            // Driver laden
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            
            // Datenbankverbindung aufbauen.
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            // SQL-Befehlsobjekt erstellen.
            statement = connection.createStatement();
            
            // SQL-Abfrage ausführen

            resultSet = statement.executeQuery(query);
            
            OLprodukte = FXCollections.observableArrayList();       // die liste initialisieren, um informationen drinnen zu speichern
            OLrueckblick = FXCollections.observableArrayList();
            
            // solange die letzte information nicht erreicht wurde, soll die while schleife durchgehen. 
            while (resultSet.next()){
                switch (aufgabe) {      // aufgabe beinhaltet die information, was der user haben möchte.
                    case "benutzer":
                        benutzer = new Benutzer(resultSet.getInt("id"), resultSet.getString("vorname"), resultSet.getString("nachname"), resultSet.getInt("groesse"), resultSet.getInt("gewicht"), resultSet.getDate("geburtsdatum"), resultSet.getString("geschlecht"), resultSet.getString("ziel"));
                        break;
                    case "heute":
                        heute = new Rückblick(resultSet.getInt("id"), resultSet.getInt("benutzer_id"), resultSet.getInt("gesamt_kohlenhydrate"), resultSet.getInt("gesamt_eiweiss"), resultSet.getInt("gesamt_fett"), resultSet.getInt("gesamt_kcal"), resultSet.getInt("max_kcal"), resultSet.getDate("datum"));
                        break;
                    case "produkte":
                        Produkte produkt = new Produkte(resultSet.getInt("id"), resultSet.getString("produktname"), resultSet.getString("kategorie"), resultSet.getInt("kohlenhydrate_menge"), resultSet.getInt("eiweiss_menge"), resultSet.getInt("fett_menge"), resultSet.getInt("kcal_anzahl"));
                        OLprodukte.add(produkt);
                        break;
                    case "rueckblick":
                        Rückblick rückblick = new Rückblick(resultSet.getInt("id"), resultSet.getInt("benutzer_id"), resultSet.getInt("gesamt_kohlenhydrate"), resultSet.getInt("gesamt_eiweiss"), resultSet.getInt("gesamt_fett"), resultSet.getInt("gesamt_kcal"), resultSet.getInt("max_kcal"), resultSet.getDate("datum"));
                        OLrueckblick.add(rückblick);
                        break;
                    default:
                        break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            Logger.getLogger(DatenbankVerbindung.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fehler gefunden - Klasse DatenbankVerbindung: Exception Fehler Exception 1");
        } finally {
            try {
                // alle schließen wenn mit der db alles abgearbeitet wurde
                if(statement != null){ statement.close(); }
                if(connection != null){ connection.close(); }
                if(resultSet != null){ resultSet.close(); }
            } catch (SQLException ex) {
                Logger.getLogger(DatenbankVerbindung.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("fehler gefunden - Klasse DatenbankVerbindung: Exception Fehler Exception 2");
            }
        }
    }
    
    // der datenbank informationen hinzufügen
    private void insertUpdateInfo(){
        try{
            // Driver laden
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            
            // Datenbankverbindung aufbauen.
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            // SQL-Befehlsobjekt erstellen.
            statement = connection.createStatement();
            
            // SQL-Abfrage ausführen
            statement.executeUpdate(query);
            
        }catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            Logger.getLogger(DatenbankVerbindung.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fehler gefunden - Klasse DatenbankVerbindung: Exception Fehler Exception 1");
        } finally {
            try {
                if(statement != null){ statement.close(); }
                if(connection != null){ connection.close(); }
            } catch (SQLException ex) {
                Logger.getLogger(DatenbankVerbindung.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("fehler gefunden - Klasse DatenbankVerbindung: Exception Fehler Exception 2");
            }
        }
    }

    // SELECT * bekommt alle informationen der tabelle
    public Benutzer getBenutzer() {
        query = "SELECT * FROM t_benutzer";
        getInfo("benutzer");
        return benutzer;
    }

    // "UPDATE t_benutzer..." überarbeitet informationen der Datenbank, die in der query vorhanden sind, indem man tabellenvariablen mit dem Symbol " = " kennzeichnet.
    // "WHERE id = " führt die aktion bei dieser id aus, welches nach dem gleichheitszeichen steht.
    public void updateBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
        LocalDate localDate  = Instant.ofEpochMilli(benutzer.getGeburtsdatum().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        query = "UPDATE t_benutzer SET vorname = '" + benutzer.getVorname()+ "', nachname = '" + benutzer.getNachname()+ "', geschlecht = '" + benutzer.getGeschlecht()+ 
                "', ziel = '" + benutzer.getZiel()+ "', groesse = '" + benutzer.getGroesse()+ "', gewicht = '" + benutzer.getGewicht()+ "', geburtsdatum = '" + localDate.toString() + 
                "' WHERE id = '" + benutzer.getId() + "'";
        insertUpdateInfo();
    }

    // "...ORDER BY kcal_anzahl DESC" ordnet die information nach kcal_anzahl
    public ObservableList<Produkte> getOLprodukte() {
        query = "SELECT * FROM t_produkte ORDER BY kcal_anzahl DESC";
        getInfo("produkte");
        return OLprodukte;
    }

    public void setOLprodukte(ObservableList<Produkte> OLprodukte) {
        this.OLprodukte = OLprodukte;
    }

    public ObservableList<Rückblick> getOLrueckblick(int id) {
        query = "SELECT * FROM t_rueckblick WHERE benutzer_id = '" + id + "' ORDER BY datum DESC";
        getInfo("rueckblick");
        return OLrueckblick;
    }

    public void setOLrueckblick(ObservableList<Rückblick> OLrueckblick) {
        this.OLrueckblick = OLrueckblick;
    }

    public Rückblick getHeute(int id) {
        query = "SELECT * FROM t_rueckblick WHERE benutzer_id = '" + id + "' AND datum = CURDATE()";
        getInfo("heute");
        return heute;
    }
    
    // "INSERT INTO t_rueckblick..." fügt der tabelle eine neue zeile hinzu.
    public void insertHeute(Rückblick heute) {
        this.heute = heute;
        query = "INSERT INTO t_rueckblick (benutzer_id, gesamt_kohlenhydrate, gesamt_eiweiss, gesamt_fett, gesamt_kcal, max_kcal, datum) VALUES ('" + heute.getBenutzerId() + 
                "', '" + heute.getKohlenhydrate() + "', '" + heute.getEiweiss() + "', '" + heute.getFett() + "', '" + heute.getTagesKcal() + "', '" + heute.getMaxKcal() + "', CURDATE())";
        insertUpdateInfo();
    }
    
    public void updateHeute(Rückblick heute){
        this.heute = heute;
        query = "UPDATE t_rueckblick SET benutzer_id = '" + heute.getBenutzerId() +  "', gesamt_kohlenhydrate = '" + heute.getKohlenhydrate() + "', gesamt_eiweiss = '" + heute.getEiweiss() + 
                "', gesamt_fett = '" + heute.getFett() + "', gesamt_kcal = '" + heute.getTagesKcal() + "', max_kcal = '" + heute.getMaxKcal() + "' WHERE datum = CURDATE()";
        insertUpdateInfo();
    }
    
    // "INSERT INTO t_prüdukte..." fügt der tabelle eine neue zeile hinzu.
    public void insertNahrung(Produkte produkt){
        query = "INSERT INTO t_produkte (produktname, kategorie, kohlenhydrate_menge, eiweiss_menge, fett_menge, kcal_anzahl) VALUES ('" + produkt.getProduktName() + "', '" + produkt.getKategorie() + 
                "', '" + produkt.getKohlenhydrateMenge() + "', '" + produkt.getEiweissMenge() + "', '" + produkt.getFettMenge() + "', '" + produkt.getKcalAnzahl() + "')";
        insertUpdateInfo();
    }
}