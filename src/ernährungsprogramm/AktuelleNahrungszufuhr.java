/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm;

import ernährungsprogramm.Datenbank.DatenbankVerbindung;
import ernährungsprogramm.Entities.Benutzer;
import ernährungsprogramm.Entities.Produkte;
import ernährungsprogramm.Entities.Rückblick;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Kasim
 */
public class AktuelleNahrungszufuhr implements EventHandler<ActionEvent> {
    // FensterGröße
    private final int fensterBreite = 660, fensterHöhe = 660;
    
    // Id vom Benutzer.
    private final Benutzer benutzer;
    
    // Fenster
    private final Stage primaryStage;
    
    // Vertical Box erstellen
    private final VBox vBox;
    
    // Liste mit Produktdaten
    private ObservableList<Produkte> OLprodukte;
    
    // Button
    private Button hinzufügenBtn, zurückBtn, speichernBtn;
    
    // Label
    private Label produktNameLabel, kategorieLabel;
    
    // Datenbank
    private DatenbankVerbindung datenbankVerbindung;
    
    // Listview
    private ListView essensListe;
    
    // Tabelle erstellen
    private TableView<Produkte> tabelle;
    
    // Spalten für die Tabelle  erstellen
    private TableColumn<Produkte, String> TCproduktName;
    private TableColumn<Produkte, String> TCkategory;
    private TableColumn<Produkte, Integer> TCkohlenhydrateMenge;
    private TableColumn<Produkte, Integer> TCfettInhalt;
    private TableColumn<Produkte, Integer> TCeiweissInhalt;
    private TableColumn<Produkte, Integer> TCkCalAnzahl;
    
    // Konstruktor
    public AktuelleNahrungszufuhr(Stage stage, Benutzer benutzer){
        this.benutzer = benutzer;
        this.primaryStage = stage;
        
        // initialisieren
        initialisiereTabelle();
        initialisieren();
        
        // Vertical Box initialisieren
        vBox = new VBox(tabelle, hinzufügenBtn, getHBox_1(), essensListe, getHBox_2());
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));
    }
    
    private void initialisiereTabelle(){
        datenbankVerbindung = new DatenbankVerbindung();
        TCproduktName = new TableColumn<>("Name des Produkts");
        TCproduktName.setMinWidth(290);
        TCproduktName.setMaxWidth(290);
        TCproduktName.setCellValueFactory(new PropertyValueFactory<>("produktName"));
        
        TCkategory = new TableColumn<>("kategorie");
        TCkategory.setMinWidth(110);
        TCkategory.setMaxWidth(110);
        TCkategory.setCellValueFactory(new PropertyValueFactory<>("kategorie"));
        
        TCkohlenhydrateMenge = new TableColumn<>("kh");
        TCkohlenhydrateMenge.setMinWidth(50);
        TCkohlenhydrateMenge.setMaxWidth(50);
        TCkohlenhydrateMenge.setCellValueFactory(new PropertyValueFactory<>("kohlenhydrateMenge"));
        
        TCfettInhalt = new TableColumn<>("ew");
        TCfettInhalt.setMinWidth(50);
        TCfettInhalt.setMaxWidth(50);
        TCfettInhalt.setCellValueFactory(new PropertyValueFactory<>("eiweissMenge"));
        
        TCeiweissInhalt = new TableColumn<>("fett");
        TCeiweissInhalt.setMinWidth(50);
        TCeiweissInhalt.setMaxWidth(50);
        TCeiweissInhalt.setCellValueFactory(new PropertyValueFactory<>("fettMenge"));
        
        TCkCalAnzahl = new TableColumn<>("kcal");
        TCkCalAnzahl.setMinWidth(50);
        TCkCalAnzahl.setMaxWidth(50);
        TCkCalAnzahl.setCellValueFactory(new PropertyValueFactory<>("kcalAnzahl"));
        
        tabelle = new TableView<>();
        tabelle.setItems(getProdukte());
        tabelle.getColumns().addAll(TCproduktName, TCkategory, TCkohlenhydrateMenge, TCfettInhalt, TCeiweissInhalt, TCkCalAnzahl);
        tabelle.getSortOrder().add(TCkategory);
        
        tabelle.setMaxHeight(300);
        tabelle.setMinHeight(300);
        tabelle.setMaxWidth(620);
        tabelle.setMinWidth(620);
        
        tabelle.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    
    private void initialisieren(){
        hinzufügenBtn = new Button("Hinzufügen");
        speichernBtn = new Button("Speichern");
        zurückBtn = new Button("Zurück");
        
        hinzufügenBtn.setMinWidth(150);
        speichernBtn.setMinWidth(150);
        zurückBtn.setMinWidth(150);
        
        hinzufügenBtn.setOnAction(this);
        speichernBtn.setOnAction(this);
        zurückBtn.setOnAction(this);
        
        produktNameLabel = new Label("Produktname:");
        kategorieLabel = new Label("Kategorie:");
        
        produktNameLabel.setMinWidth(290);
        produktNameLabel.setMaxWidth(290);
        kategorieLabel.setMinWidth(290);
        kategorieLabel.setMaxWidth(290);
        
        essensListe = new ListView();
        essensListe.setMinHeight(130);
        essensListe.setMaxHeight(130);
        essensListe.setMinWidth(620);
        essensListe.setMaxWidth(620);
        
        essensListe.setCellFactory(e -> new ListeDesign());
    }
    
    // um das fenster zu öffnen
    public Scene getScene(){
        // Fenster zurückgeben
        return new Scene(vBox, fensterBreite, fensterHöhe);
    }
    
    // labels horizontal legen
    private HBox getHBox_1(){
        HBox hBox = new HBox(produktNameLabel, kategorieLabel);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(50,0,0,0));
        return hBox;
    }
    
    // buttons horizontal legen
    private HBox getHBox_2(){
        HBox hBox = new HBox(speichernBtn, zurückBtn);
        hBox.setSpacing(10);
        return hBox;
    }
    
    // liste von der Datenbank  bekommen
    public ObservableList<Produkte> getProdukte(){
        OLprodukte = datenbankVerbindung.getOLprodukte();
        return OLprodukte;
    }

    // Onclicks- was passiert wenn welches Button geklickt wurde
    @Override
    public void handle(ActionEvent event) {
        Meldung meldung = new Meldung();        // um meldungen auszugeben
        if(event.getSource() == speichernBtn){
            Rückblick heute = datenbankVerbindung.getHeute(benutzer.getId());       // bekommt die anzahl an Kohlenhydrate, Eiweiss, fett und kalorien die du heute zu dir genommen hast.
            if(heute == null){      // wenn es für heute nichts in der db steht, soll es in die Db hinzufügen
                heute = new Rückblick(benutzer.getId(), 0, 0, 0, 0, benutzer.getMaxKcal());     // wenn db nichts zurückgab, wird "heute" initialisiert.
                if(!essensListe.getItems().isEmpty()){  // wenn die liste leer ist, soll die forschleife nciht durchlaufen werden.
                    for(int index = 0; index < essensListe.getItems().size(); index++){     // soll die werte von allen essensgerichten zusammenrechnen und in die db speichern.
                        Produkte produkt = (Produkte) essensListe.getItems().get(index);
                        heute.setKohlenhydrate(heute.getKohlenhydrate() + produkt.getKohlenhydrateMenge());
                        heute.setEiweiss(heute.getEiweiss() + produkt.getEiweissMenge());
                        heute.setFett(heute.getFett() + produkt.getFettMenge());
                        heute.setTagesKcal(heute.getTagesKcal() + produkt.getKcalAnzahl());
                    }
                }
                datenbankVerbindung.insertHeute(heute);     // fügt eine neue zeile in die db mit den werden von "heute" hinzu
            } else {
                if(!essensListe.getItems().isEmpty()){
                    for(int index = 0; index < essensListe.getItems().size(); index++){
                        Produkte produkt = (Produkte) essensListe.getItems().get(index);
                        heute.setKohlenhydrate(heute.getKohlenhydrate() + produkt.getKohlenhydrateMenge());
                        heute.setEiweiss(heute.getEiweiss() + produkt.getEiweissMenge());
                        heute.setFett(heute.getFett() + produkt.getFettMenge());
                        heute.setTagesKcal(heute.getTagesKcal() + produkt.getKcalAnzahl());
                    }
                    datenbankVerbindung.updateHeute(heute); // updated die datenbank mit den werten von heute.
                }
            }
            Hauptmenü hauptmenü = new  Hauptmenü(primaryStage);     // anschließend wird wieder Hauptmenu aufgerufen
            primaryStage.setScene(hauptmenü.getScene());
            primaryStage.show();
        }else if(event.getSource() == zurückBtn){
            Hauptmenü hauptmenü = new  Hauptmenü(primaryStage);
            primaryStage.setScene(hauptmenü.getScene());
            primaryStage.show();
        }else if(event.getSource() == hinzufügenBtn){       // selected item wird in die listview hinzugefügt.
            if(tabelle.getSelectionModel().getSelectedItem() != null){
                essensListe.getItems().add(tabelle.getSelectionModel().getSelectedItem());
            } else {
                meldung.meldung("Keine Auwahl", "Bitte von der Tabelle etwas auswählen.");
            }
        }
    }
    
    // listview soll in der form dargestellt werden.
    static class ListeDesign extends ListCell<Produkte> implements EventHandler<ActionEvent> {
        private final HBox hBox = new HBox();
        private final Button löschenBtn = new Button("X");
        private final Label produktLabel = new Label(""), kategorieLabel = new Label("");
        
        public ListeDesign(){
            super();
            hBox.getChildren().addAll(produktLabel, kategorieLabel, löschenBtn);
            hBox.setSpacing(10);
            produktLabel.setMinWidth(290);
            produktLabel.setMaxWidth(290);
            kategorieLabel.setMinWidth(130);
            kategorieLabel.setMaxWidth(130);
            löschenBtn.setOnAction(this);
        }
        
        @Override
        public void updateItem(Produkte produkt, boolean empty){
            super.updateItem(produkt, empty);
            setText(null);
            setGraphic(null);
            
            if(produkt != null && !empty){
                produktLabel.setText(produkt.getProduktName());
                kategorieLabel.setText(produkt.getKategorie());
                setGraphic(hBox);
            }
        }

        @Override
        public void handle(ActionEvent event) {
            if(event.getSource() == löschenBtn){
                getListView().getItems().remove(getItem());
            }
        }
    }
}