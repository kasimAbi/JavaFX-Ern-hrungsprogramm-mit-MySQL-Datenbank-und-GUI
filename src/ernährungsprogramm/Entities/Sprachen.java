/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm.Entities;

import ernährungsprogramm.Datenbank.DatenbankVerbindung;
import java.util.HashMap;

/**
 *
 * @author Kasim
 */
public class Sprachen {
    
    // HashMap wird erstellt für deutsche und englische sprache
    private final HashMap<String, String> deutsch;
    private final HashMap<String, String> englisch;
    
    // datenbankverbindung wird erstellt um nötige informationen von der datenbank zu bekommen
    private final DatenbankVerbindung datenbankVerbindung;
    
    private final Benutzer benutzer;
    private Rückblick rücblick;
    
    // Konstruktor
    public Sprachen(Benutzer benutzer){
        deutsch = new HashMap<>();
        englisch = new HashMap<>();
        this.benutzer = benutzer;
        datenbankVerbindung = new DatenbankVerbindung();
        rücblick = datenbankVerbindung.getHeute(benutzer.getId());      // um gesamtkalorien für diesen tag zu bekommen.
        if(rücblick == null){
            rücblick = new Rückblick(benutzer.getId(), 0, 0, 0, 0, benutzer.getMaxKcal());
        }
    }

    public HashMap<String, String> getDeutschHauptmenu() {
        // es wird ein schlüssen und dazu gehöriger String wert gespeichert. 
        deutsch.put("menu", "Willkommen im Ernährungsprogramm.\n\nIhre aktuelle Kalorienzuführ:\t" + rücblick.getTagesKcal() + " kcal.\n"
                + "Maximale Kalorienzuführ:\t" +  benutzer.getMaxKcal() + " kcal.\n\nVerfügbare Kalorienzufuhr:\t" + (((benutzer.getMaxKcal() - rücblick.getTagesKcal()) < 0) ?  0 : (benutzer.getMaxKcal() - rücblick.getTagesKcal())) + " kcal.\n\n\n\nWas möchten Sie tun?");
        deutsch.put("profilBtn", "Profil bearbeiten");
        deutsch.put("heutigeNahrungszufuhrBtn", "heutige Nahrungszufuhr");
        deutsch.put("wochenberichtBtn", "Wochenbericht");
        deutsch.put("NahrungListeHinzufügenBtn", "Nahrung der Liste hinzufügen");
        deutsch.put("programmBeendenBtn", "Programm beenden");
        deutsch.put("sortierenBtn", "Sortieren mit Bubblesort");
        return deutsch;     // hashmap wird zurückgegeben.
    }

    public HashMap<String, String> getEnglischHauptmenu() {
        englisch.put("menu", "Welcome to the nutritional program.\n\nYour current calorie intake:\t" + rücblick.getTagesKcal() + " kcal.\n"
                + "Maximum calorie intake:\t" +  benutzer.getMaxKcal() + " kcal.\n\nAvailable calorie intake:\t" + (((benutzer.getMaxKcal() - rücblick.getTagesKcal()) < 0) ?  0 : (benutzer.getMaxKcal() - rücblick.getTagesKcal())) + " kcal.\n\n\n\nWhat do you want to do?");
        englisch.put("profilBtn", "edit profile");
        englisch.put("heutigeNahrungszufuhrBtn", "todays food intake");
        englisch.put("wochenberichtBtn", "weekly report");
        englisch.put("NahrungListeHinzufügenBtn", "add food to the list");
        englisch.put("programmBeendenBtn", "exit program");
        englisch.put("sortierenBtn", "Sort by bubblesort");
        return englisch;
    }
}