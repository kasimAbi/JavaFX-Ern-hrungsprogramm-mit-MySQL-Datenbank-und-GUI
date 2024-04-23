/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm;

import ernährungsprogramm.Datenbank.DatenbankVerbindung;
import ernährungsprogramm.Entities.Produkte;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Kasim
 */
public class AddNahrungDB implements EventHandler<ActionEvent>{
    // FensterGröße
    private final int fensterBreite = 400, fensterHöhe = 600;
    
    // Labels
    private Label nahrungNameLabel, kohlenhydrateLabel, eiweissLabel, fettLabel, kcalLabel, infoLabel, kcalInfoLabel, kategorieLabel;

    // Text Felder
    private static TextField nahrungNameTF, kohlenhydrateTF, eiweissTF, fettTF, kcalTF;
    
    // Integer
    private int kohlenhydrate, eiweiss, fett, kcal;

    // Button
    private Button speichernBtn, zurückBtn;
    
    // ChoiceBox
    private ChoiceBox<String> choiceBox;
    
    // Vertical Box erstellen
    private final VBox vBox;
    
    // Fenster
    private final Stage primaryStage;
    
    // datenbank wird deklariert.
    private DatenbankVerbindung datenbankVerbindung;
    
    // Konstruktor
    public AddNahrungDB(Stage stage){
        primaryStage = stage;
        
        // die oberen variablen hier initialisieren.
        initialisieren();
        
        // Vertical Box initialisieren
        vBox = new VBox(getGridPane_1(), kategorieLabel, getChoiceBox(), getHBox_1());
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));
    }
    
    private void initialisieren(){
        // datenbank wird initialisiert.
        datenbankVerbindung = new DatenbankVerbindung();
        
        nahrungNameLabel = new Label("Name vom Nahrung: ");
        kohlenhydrateLabel = new Label("Anzahl Kohlenhydrate: ");
        eiweissLabel = new Label("Anzahl Eiweiß: ");
        fettLabel = new Label("Anzahl Fett: ");
        kcalLabel = new Label("Anzahl kcal: ");
        infoLabel = new Label("\n\n/100 gramm");
        kcalInfoLabel = new Label("Kcal wird automatisch\nbei Nichtangabe\nberechnet.");
        kategorieLabel = new Label("\nKategoriezugehörigkeit: ");
        
        nahrungNameTF = new TextField("");
        kohlenhydrateTF = new TextField("");
        eiweissTF = new TextField("");
        fettTF = new TextField("");
        kcalTF = new TextField("");
        
        speichernBtn = new Button("Speichern");
        zurückBtn = new Button("Zurück");
        
        kohlenhydrateTF.setMaxWidth(80);
        eiweissTF.setMaxWidth(80);
        fettTF.setMaxWidth(80);
        kcalTF.setMaxWidth(80);
        speichernBtn.setMinWidth(150);
        zurückBtn.setMinWidth(150);
        
        speichernBtn.setOnAction(this);
        zurückBtn.setOnAction(this);
    }
    
    public Scene getScene(){
        // Fenster zurückgeben
        return new Scene(vBox, fensterBreite, fensterHöhe);
    }

    // gridpane wird erstellt und gestaltet
    private GridPane getGridPane_1(){
         // GridPane erstellen
        GridPane gridPane = new GridPane();
        
        // Objekte dem Grid hinzufügen  
        gridPane.getChildren().add(nahrungNameLabel); // Labels
        gridPane.getChildren().add(kohlenhydrateLabel);
        gridPane.getChildren().add(eiweissLabel);
        gridPane.getChildren().add(fettLabel);
        gridPane.getChildren().add(kcalLabel);
        gridPane.getChildren().add(infoLabel);
        gridPane.getChildren().add(kcalInfoLabel);  
        gridPane.getChildren().add(nahrungNameTF);    // TextFelder
        gridPane.getChildren().add(kohlenhydrateTF);
        gridPane.getChildren().add(eiweissTF);
        gridPane.getChildren().add(fettTF);
        gridPane.getChildren().add(kcalTF);
        
        // Abstand zu den Seiten
        gridPane.setPadding(new Insets(0,0,0,0));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        
        // Positionierung
        GridPane.setConstraints(nahrungNameLabel, 0, 0);
        GridPane.setConstraints(nahrungNameTF, 1, 0);
        GridPane.setConstraints(infoLabel, 1, 1);
        GridPane.setConstraints(kohlenhydrateLabel, 0, 2);
        GridPane.setConstraints(kohlenhydrateTF, 1, 2);
        GridPane.setConstraints(eiweissLabel, 0, 3);
        GridPane.setConstraints(eiweissTF, 1, 3);
        GridPane.setConstraints(fettLabel, 0, 4);
        GridPane.setConstraints(fettTF, 1, 4);
        GridPane.setConstraints(kcalLabel, 0, 5);
        GridPane.setConstraints(kcalTF, 1, 5);
        GridPane.setConstraints(kcalInfoLabel, 0, 6);   
        
        return gridPane;
    }
    
    // auswahlboxen werden initialisiert
    private ChoiceBox<String> getChoiceBox(){
        // ChoiceBox initialisieren
        choiceBox = new ChoiceBox<>();
        
        // Add Items
        choiceBox.getItems().addAll("Kohlenhydrate", "Eiweiss", "Fett");
        
        // Default selection
        choiceBox.setValue("Kohlenhydrate");
        
        // Mindestbreite
        choiceBox.setMinWidth(150);
        
        return choiceBox;
    }
    
    private HBox getHBox_1(){
        HBox hBox = new HBox(speichernBtn, zurückBtn);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(100,0,0,0));
        return hBox;
    }

    // onklickfunktionen werden erstellt.
    @Override
    public void handle(ActionEvent event) {
        Meldung meldung = new Meldung();
        if(event.getSource() == speichernBtn){
            // Ausgewähltes Item bekommen
            primaryStage.setTitle(choiceBox.getValue());
            
            if(!nahrungNameTF.getText().isEmpty() && !kohlenhydrateTF.getText().isEmpty() && !eiweissTF.getText().isEmpty() && !fettTF.getText().isEmpty()){
                try{
                    kohlenhydrate = Integer.parseInt(kohlenhydrateTF.getText());
                    eiweiss = Integer.parseInt(eiweissTF.getText());
                    fett = Integer.parseInt(fettTF.getText());
                    if(kcalTF.getText().isEmpty()){     // wenn kcal-textfeld leer ist, soll kcal automatisch berechnet werden.
                        kcal = (int) (kohlenhydrate * 4.1 + eiweiss * 4.1 + fett * 9.3);
                    }else{
                        kcal = Integer.parseInt(kcalTF.getText());
                    }
                    Produkte produkt = new Produkte(nahrungNameTF.getText(), choiceBox.getValue(), kohlenhydrate, eiweiss, fett, kcal);
                    datenbankVerbindung.insertNahrung(produkt);     // das produkt wird der datenbank hinzugefügt.
                    
                    Hauptmenü hauptmenü = new  Hauptmenü(primaryStage);
                    primaryStage.setScene(hauptmenü.getScene());
                    primaryStage.show();
                }catch(NumberFormatException e){
                }
            }else{
                meldung.meldung("Aufüllen", "Bitte die Fehlder ausfüllen.");
            }
        }else if(event.getSource() == zurückBtn){
            Hauptmenü hauptmenü = new  Hauptmenü(primaryStage);
            primaryStage.setScene(hauptmenü.getScene());
            primaryStage.show();
        }
    }
}