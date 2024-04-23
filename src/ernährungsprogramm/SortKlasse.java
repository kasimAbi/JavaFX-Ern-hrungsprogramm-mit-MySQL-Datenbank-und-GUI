/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm;

import ernährungsprogramm.Datenbank.DatenbankVerbindung;
import ernährungsprogramm.Entities.Benutzer;
import ernährungsprogramm.Entities.Produkte;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kasim
 */
public class SortKlasse implements EventHandler<ActionEvent> {
    // fenstergröße und breite einstellen
    private final int fensterHöhe = 900, fensterBreite = 600;
    
    // buttons, labels und listviews deklarieren.
    private Button sortiereNachIdBtn, sortiereNachNameBtn, zurückBtn;
    private Label unsortiertLabel, sortiereNachIdLabel, sortiereNachNameLabel;
    private ListView listeUnsortiert, listeSortiertNachId, listeSortiertNachName;
    
    // vertikal box erstellen
    private final VBox vBox;
    
    private final Stage primaryStage;
    private final Benutzer benutzer;
    
    // datenbankverbindung erstellen
    private DatenbankVerbindung datenbankVerbindung;
    
    // Liste mit Produktdaten
    private ObservableList<Produkte> OLprodukte;
    
    // konstruktor
    public SortKlasse(Stage primaryStage, Benutzer benutzer){
        this.primaryStage = primaryStage;
        this.benutzer = benutzer;
        
        // initialisieren
        initialisieren();
        
        // design des fensters erstellen
        vBox = new VBox(unsortiertLabel, listeUnsortiert, getHBox_1(), sortiereNachIdLabel, listeSortiertNachId, sortiereNachNameLabel, listeSortiertNachName, zurückBtn);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));
    }
    
    private void initialisieren(){
        datenbankVerbindung = new DatenbankVerbindung();        // datenbankverbidnung initialisieren
        OLprodukte = datenbankVerbindung.getOLprodukte();       // liste von der db bekommen
        
        sortiereNachIdBtn = new Button("Sortiere nach ID");
        sortiereNachNameBtn = new Button("Sortiere nach Name");
        zurückBtn = new Button("zurück");
        
        unsortiertLabel = new Label("Unsortiert: ");
        sortiereNachIdLabel = new Label("Sortiere nach Id mit BubbleSort- Verfahren: ");
        sortiereNachNameLabel = new Label("Sortiere nach Name mit BubbleSort- Verfahren: ");
        
        listeUnsortiert = new ListView();
        listeUnsortiert.setMinHeight(120);
        listeUnsortiert.setMaxHeight(120);
        listeUnsortiert.setCellFactory(e -> new ListeDesign());
        
        if(!OLprodukte.isEmpty()){      // listview mit den daten von der liste füllen
            listeUnsortiert.getItems().setAll(OLprodukte);
        }
        
        listeSortiertNachId = new ListView();
        listeSortiertNachId.setMinHeight(250);
        listeSortiertNachId.setMaxHeight(250);
        listeSortiertNachId.setCellFactory(e -> new ListeDesign());
        
        listeSortiertNachName = new ListView();
        listeSortiertNachName.setMinHeight(250);
        listeSortiertNachName.setMaxHeight(250);
        listeSortiertNachName.setCellFactory(e -> new ListeDesign());
        
        sortiereNachNameBtn.setOnAction(this);      // buttonklicks erstellen.
        sortiereNachIdBtn.setOnAction(this);
        zurückBtn.setOnAction(this);
    }
    
    private HBox getHBox_1(){
        HBox hBox = new HBox(sortiereNachIdBtn, sortiereNachNameBtn);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0,0,50,0));
        return hBox;
    }
    
    public Scene getScene(){
        return new Scene(vBox, fensterBreite, fensterHöhe);
    }

    // buttonklicks funktionen
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == zurückBtn){
            Hauptmenü hauptmenü = new  Hauptmenü(primaryStage);
            primaryStage.setScene(hauptmenü.getScene());
            primaryStage.show();
        }else if(event.getSource() == sortiereNachIdBtn){
            if(!OLprodukte.isEmpty()){
                bubbleSortById();
                listeSortiertNachId.getItems().setAll(OLprodukte);
            }
        }else if(event.getSource() == sortiereNachNameBtn){
            if(!OLprodukte.isEmpty()){
                bubbleSortByName();
                listeSortiertNachName.getItems().setAll(OLprodukte);
            }
        }
    }
    
    /*  Die Liste wird von vorne nach hinten durchlaufen. Hierbei wird geprüft, 
    ob die Id des jeweilige Elements kleiner als die Id seines Nachfolgeelements ist. 
    Ist dies der Fall wird die Schleife fortgesetzt. Falls nicht, so werden das 
    aktuelle Element und sein Nachfolger getauscht, sodass das Element mit der kleineren Id 
    nun vor dem größeren liegt. Durch rekursiven Aufruf der Methode wird der 
    Schleifendurchlauf erneut gestartet bis eine aufsteigende Sortierung vorliegt.  */
    public void bubbleSortById(){
        Produkte cache;
        for (int index = 0; index < OLprodukte.size() - 1; index++) {
            if (OLprodukte.get(index).getId() < OLprodukte.get(index + 1).getId()) {
                continue;
            }
            cache = OLprodukte.get(index);
            OLprodukte.set(index, OLprodukte.get(index + 1));
            OLprodukte.set(index + 1, cache);
            bubbleSortById();
        }
    }
    
    // sortiert wie oben nur alphabetisch
    public void bubbleSortByName(){
        Produkte cache;
        for (int index = 0; index < OLprodukte.size() - 1; index++) {
            if(OLprodukte.get(index).getProduktName().toLowerCase().compareTo(OLprodukte.get(index + 1).getProduktName().toLowerCase()) < 1) {
                continue;
            } 
            cache = OLprodukte.get(index);
            OLprodukte.set(index, OLprodukte.get(index + 1));
            OLprodukte.set(index + 1, cache);
            bubbleSortByName();
        }
    }
    
    // listview soll in der form dargestellt werden.
    static class ListeDesign extends ListCell<Produkte>{
        private final HBox hBox = new HBox();
        private final Label idLabel = new Label(""), nameLabel = new Label("");
        
        public ListeDesign(){
            super();
            hBox.getChildren().addAll(idLabel, nameLabel);
            hBox.setSpacing(10);
            idLabel.setMinWidth(40);
            idLabel.setMaxWidth(40);
            nameLabel.setMinWidth(520);
            nameLabel.setMaxWidth(520);
        }
        
        @Override
        public void updateItem(Produkte produkt, boolean empty){
            super.updateItem(produkt, empty);
            setText(null);
            setGraphic(null);
            
            if(produkt != null && !empty){
                idLabel.setText(produkt.getId() + "");
                nameLabel.setText(produkt.getProduktName());
                setGraphic(hBox);
            }
        }
    }
}