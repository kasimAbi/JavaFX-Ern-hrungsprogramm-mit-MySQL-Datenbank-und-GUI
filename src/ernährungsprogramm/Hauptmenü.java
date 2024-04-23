/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm;

import ernährungsprogramm.Datenbank.DatenbankVerbindung;
import ernährungsprogramm.Entities.Benutzer;
import ernährungsprogramm.Entities.Sprachen;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Kasim
 */
public class Hauptmenü implements EventHandler<ActionEvent>{
    // FensterGröße
    private final int fensterBreite = 400, fensterHöhe = 500;
    
    // Labels
    private Label informationLabel;
    
    // Buttons
    private Button profilBearbeitenBtn, aktuelleNahrungsZufuhrBtn, wochenStatusBtn, nahrungDatenbankHinzufügenBtn, programmBeendenBtn, sortierVerfahrenBtn;
    
    // Vertical Box erstellen
    private final VBox vBox;
    
    // Fenster
    private final Stage primaryStage;
    
    private Benutzer benutzer;
    
    // datenbankverbindung
    private DatenbankVerbindung datenbankVerbindung;
    
    // Konstruktor
    public Hauptmenü(Stage stage){
        this.primaryStage = stage;
        
        // initialisiert die erstellten variablen und gibt denen größen und beschreibungen
        // außerdem befindet sich dort die HashMap mit der wir die Sprache eingestellt haben.
        initialisieren();
        
        // Vertical Box initialisieren
        vBox = new VBox(informationLabel, profilBearbeitenBtn, aktuelleNahrungsZufuhrBtn, wochenStatusBtn, nahrungDatenbankHinzufügenBtn, sortierVerfahrenBtn, programmBeendenBtn);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(50));
    }
    
    private void initialisieren(){
        datenbankVerbindung = new DatenbankVerbindung();        // datenbankverbindung wird initialisiert
        benutzer = datenbankVerbindung.getBenutzer();           // benutzer von der db wird geholt
        Sprachen sprachen = new Sprachen(benutzer);      
        HashMap<String, String> sprache;
        
        // für eine andere Sprache, eine andere Methode aufrufen.
        sprache = sprachen.getDeutschHauptmenu();           // auf deutsch darstellen
        
        //sprache = sprachen.getEnglischHauptmenu();        // auf englisch darstellen
        
        informationLabel = new Label(sprache.get("menu"));
        
        profilBearbeitenBtn = new Button(sprache.get("profilBtn"));
        aktuelleNahrungsZufuhrBtn = new Button(sprache.get("heutigeNahrungszufuhrBtn"));
        wochenStatusBtn = new Button(sprache.get("wochenberichtBtn"));
        nahrungDatenbankHinzufügenBtn = new Button(sprache.get("NahrungListeHinzufügenBtn"));
        programmBeendenBtn = new Button(sprache.get("programmBeendenBtn"));
        sortierVerfahrenBtn = new Button(sprache.get("sortierenBtn"));
        
        profilBearbeitenBtn.setMinWidth(300);
        aktuelleNahrungsZufuhrBtn.setMinWidth(300);
        wochenStatusBtn.setMinWidth(300);
        nahrungDatenbankHinzufügenBtn.setMinWidth(300);
        programmBeendenBtn.setMinWidth(300);
        sortierVerfahrenBtn.setMinWidth(300);
        
        profilBearbeitenBtn.setOnAction(this);          // onklick funktionen
        aktuelleNahrungsZufuhrBtn.setOnAction(this);
        wochenStatusBtn.setOnAction(this);
        nahrungDatenbankHinzufügenBtn.setOnAction(this);
        programmBeendenBtn.setOnAction(this);
        sortierVerfahrenBtn.setOnAction(this);
    }
    
    public Scene getScene(){
        // Fenster zurückgeben
        return new Scene(vBox, fensterBreite, fensterHöhe);
    }

    // OnKlick funktionen für die buttons. je nach button wird etwas aufgerufen.
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == profilBearbeitenBtn){
            PersönlicheDaten persöhnlicheDaten = new PersönlicheDaten(primaryStage, benutzer);
            primaryStage.setScene(persöhnlicheDaten.getScene());
            primaryStage.show();
        }else if(event.getSource() == aktuelleNahrungsZufuhrBtn){
            AktuelleNahrungszufuhr aktuelleNahrungszufuhr = new AktuelleNahrungszufuhr(primaryStage, benutzer); 
            primaryStage.setScene(aktuelleNahrungszufuhr.getScene());
            primaryStage.show();
        }else if(event.getSource() == wochenStatusBtn){
            Wochenbericht wochenbericht = new Wochenbericht(primaryStage, benutzer); 
            primaryStage.setScene(wochenbericht.getScene());
            primaryStage.show();
        }else if(event.getSource() == nahrungDatenbankHinzufügenBtn){
            AddNahrungDB addNahrungDB = new AddNahrungDB(primaryStage);
            primaryStage.setScene(addNahrungDB.getScene());
            primaryStage.show();
        }else if(event.getSource() == programmBeendenBtn){
            primaryStage.close();
        }else if(event.getSource() == sortierVerfahrenBtn){
            SortKlasse sortKlasse = new SortKlasse(primaryStage, benutzer);
            primaryStage.setScene(sortKlasse.getScene());
            primaryStage.show();
        }
    }
}