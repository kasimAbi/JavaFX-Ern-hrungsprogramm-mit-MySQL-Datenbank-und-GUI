/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm;

import ernährungsprogramm.Datenbank.DatenbankVerbindung;
import ernährungsprogramm.Entities.Benutzer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Kasim
 */
public class PersönlicheDaten implements EventHandler<ActionEvent>{ 
    // FensterGröße
    private final int fensterBreite = 420, fensterHöhe = 300;
    
    // Labels
    private Label vornameLabel, nachnameLabel, gewichtLabel, groesseLabel, birthdayLabel;

    // Text Felder
    private static TextField vornameTF, nachnameTF, gewichtTF, groesseTF;
    
    // RadioButtons
    private static RadioButton männlichRBtn, weiblichRBtn, abnehmenRBtn, zunehmenRBtn;
    
    // String und Integer
    private static String geschlecht, ziel;

    // Button
    public Button speichernBtn;
    
    // DatePicker
    private DatePicker datePicker;
    
    // Vertical Box erstellen
    private final VBox vBox;
    
    // Fenster
    private final Stage primaryStage;
    
    private final Benutzer benutzer;
    
    private DatenbankVerbindung datenbankVerbidnung;
    
    // Konstruktor
    public PersönlicheDaten(Stage stage, Benutzer benutzer){
        this.primaryStage = stage;
        this.benutzer = benutzer;
        
        // Labels und RadioButtons initialisieren
        initialisieren();
        
        // Vertical Box initialisieren
        vBox = new VBox(getGridPane_1(), getGridPane_2(), speichernBtn);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));
    }
    
    private void initialisieren(){
        datenbankVerbidnung = new DatenbankVerbindung();        // um in der datenbank den nutzer zu überarbeiten.
        vornameLabel = new Label("Vorname:");
        nachnameLabel = new Label("Nachname:");
        gewichtLabel = new Label("Gewicht:");
        groesseLabel = new Label("Größe:");
        birthdayLabel = new Label("Geburtsdatum:");
        vornameTF = new TextField(benutzer.getVorname());
        nachnameTF = new TextField(benutzer.getNachname());
        gewichtTF = new TextField(benutzer.getGewicht() + "");
        groesseTF = new TextField(benutzer.getGroesse() + "");
        männlichRBtn = new RadioButton("Männlich");
        weiblichRBtn = new RadioButton("Weiblich");
        abnehmenRBtn = new RadioButton("ich möchte abnehmen");
        zunehmenRBtn = new RadioButton("ich möchte zunehmen");
        speichernBtn = new Button("Speichern");
        
        datePicker = new DatePicker(LocalDate.parse(benutzer.getGeburtsdatum().toString()));
        datePicker.setPromptText("Geburtsdatum");
        datePicker.setMaxWidth(200);
        datePicker.setMinWidth(200);
        
        gewichtTF.setPromptText("ganze Zahlen");        // informationstext wird den textfeldern hinzugefügt.
        groesseTF.setPromptText("ganze Zahlen (in cm)");
        
        // entsprechend der informationen aus der datenbank werden die radiobuttons überarbeitet.
        if(benutzer.getGeschlecht().equals("Männlich")){
            männlichRBtn.setSelected(true);
            weiblichRBtn.setSelected(false);
            geschlecht = "Männlich";
        }else{
            männlichRBtn.setSelected(false);
            weiblichRBtn.setSelected(true);
            geschlecht = "Weiblich";
        }
        
        if(benutzer.getZiel().equals("zunehmen")){
            abnehmenRBtn.setSelected(false);
            zunehmenRBtn.setSelected(true);
            ziel = "zunehmen";
        }else{
            abnehmenRBtn.setSelected(true);
            zunehmenRBtn.setSelected(false);
            ziel = "abnehmen";
        }
        
        männlichRBtn.setOnAction(this);     // onklicks
        weiblichRBtn.setOnAction(this);
        abnehmenRBtn.setOnAction(this);
        zunehmenRBtn.setOnAction(this);
        speichernBtn.setOnAction(this);
    }
    
    // fenster wird zurückgegeben
    public Scene getScene(){
        // Fenster zurückgeben
        return new Scene(vBox, fensterBreite, fensterHöhe);
    }
    
    // gridpane erstellen für die struktur
    private GridPane getGridPane_1(){
        // GridPane erstellen
        GridPane gridPane = new GridPane();
        
        // Objekte dem Grid hinzufügen
        gridPane.getChildren().add(vornameLabel);   // Labels
        gridPane.getChildren().add(nachnameLabel);
        gridPane.getChildren().add(gewichtLabel);
        gridPane.getChildren().add(groesseLabel);
        gridPane.getChildren().add(birthdayLabel);
        gridPane.getChildren().add(vornameTF);      // Textfelder
        gridPane.getChildren().add(nachnameTF);
        gridPane.getChildren().add(gewichtTF);
        gridPane.getChildren().add(groesseTF);
        gridPane.getChildren().add(datePicker);
        
        // Abstand zu den Seiten
        gridPane.setPadding(new Insets(0,0,0,0));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        
        // Positionierung
        GridPane.setConstraints(vornameLabel, 0, 0);
        GridPane.setConstraints(vornameTF, 1, 0);
        GridPane.setConstraints(nachnameLabel, 0, 1);
        GridPane.setConstraints(nachnameTF, 1, 1);
        GridPane.setConstraints(gewichtLabel, 0, 2);
        GridPane.setConstraints(gewichtTF, 1, 2);
        GridPane.setConstraints(groesseLabel, 0, 3);
        GridPane.setConstraints(groesseTF, 1, 3);
        GridPane.setConstraints(birthdayLabel, 0, 4);
        GridPane.setConstraints(datePicker, 1, 4);
        
        return gridPane;
    }
    
    // gridpane erstellen für die struktur
    private GridPane getGridPane_2(){
        // GridPane erstellen
        GridPane gridPane = new GridPane();
      
        gridPane.getChildren().add(männlichRBtn);   // radiobuttons
        gridPane.getChildren().add(weiblichRBtn);   
        gridPane.getChildren().add(abnehmenRBtn);   
        gridPane.getChildren().add(zunehmenRBtn);
          
        // Abstand zu den Seiten
        gridPane.setPadding(new Insets(0,0,0,0));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
            
        GridPane.setConstraints(männlichRBtn, 0, 0);
        GridPane.setConstraints(weiblichRBtn, 1, 0);
        
        GridPane.setConstraints(zunehmenRBtn, 0, 1);
        GridPane.setConstraints(abnehmenRBtn, 1, 1);
        
        GridPane.setConstraints(speichernBtn, 0, 2);
        
        return gridPane;
    }

    // onklicks für buttons
    @Override
    public void handle(ActionEvent event) {
        Meldung meldung = new Meldung();
        if(event.getSource() == männlichRBtn){
            männlichRBtn.setSelected(true);
            weiblichRBtn.setSelected(false);
            geschlecht = "Männlich";
        }else if(event.getSource() == weiblichRBtn){
            männlichRBtn.setSelected(false);
            weiblichRBtn.setSelected(true);
            geschlecht = "Weiblich";
        }else if(event.getSource() == abnehmenRBtn){
            abnehmenRBtn.setSelected(true);
            zunehmenRBtn.setSelected(false);
            ziel = "abnehmen";
        }else if(event.getSource() == zunehmenRBtn){
            abnehmenRBtn.setSelected(false);
            zunehmenRBtn.setSelected(true);
            ziel = "zunehmen";
        }else if(event.getSource() == speichernBtn){
            // prüfen ob alle felder ausgefüllt wurden
            if(vornameTF.getText().equals("") || nachnameTF.getText().equals("") || gewichtTF.getText().equals("") || groesseTF.getText().equals("") || datePicker.getEditor().getText().isEmpty()){
                meldung.meldung("Unvollständige Informationen", "Bitte ausfüllen.");
            }else if(geschlecht == null || ziel == null){       // prüfen ob geschlecht- und ziel- radiobuttons angeklickt wurden
                meldung.meldung("Unvollständige Informationen", "Bitte Geschlecht und Ziel mitteilen.");
            }else{
                if(gewichtTF.getText().length() > 3 || groesseTF.getText().length() > 3){   // prüfen ob der wert welches angegeben wurde, gültig sein kann (max zahlen 3)
                    meldung.meldung("Fehlerhafte Informationen", "Bitte Informationen richtig angeben.");
                }else{
                    try{        // versuchen die eingabe in zahlen umzuwandeln. wenn nicht funktioniert wurden ungültige zeichen angegeben.
                        int updateGewicht = Integer.parseInt(gewichtTF.getText());
                        int updateGroesse = Integer.parseInt(groesseTF.getText());
                        Date updateBirthdate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        
                        Benutzer updateBenutzer = new Benutzer(benutzer.getId(), vornameTF.getText(), nachnameTF.getText(), updateGroesse, updateGewicht, updateBirthdate, geschlecht, ziel);
                        datenbankVerbidnung.updateBenutzer(updateBenutzer);     // nutzerdaten werden überarbeitet.
                        
                        Hauptmenü hauptmenü = new Hauptmenü(primaryStage);      // zurück zum hauptmenü
                        primaryStage.setScene(hauptmenü.getScene());
                        primaryStage.show();
                    }catch(NumberFormatException exception){
                        meldung.meldung("Fehlerhafte Informationen", "Bitte Informationen richtig angeben.\nIhr Alter und Ihre Größe sind ganze Zahlen.");
                    }
                }
            }
        }  
    }
}