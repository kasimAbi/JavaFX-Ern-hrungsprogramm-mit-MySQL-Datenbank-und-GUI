/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm;

import ernährungsprogramm.Datenbank.DatenbankVerbindung;
import ernährungsprogramm.Entities.Benutzer;
import ernährungsprogramm.Entities.Rückblick;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Kasim
 */
public class Wochenbericht implements EventHandler<ActionEvent>{
    
    // FensterGröße
    private final int fensterBreite = 800, fensterHöhe = 600;
    
    // liste mit der entity "rückblick" erstellen
    private ObservableList<Rückblick> OLrückblick;
    
    private final Stage primaryStage;
    
    // vertical box für die struktur erstellen
    private final VBox vBox;
    
    private final Benutzer benutzer;
    
    // buttons deklarieren
    private Button zurückBtn, linksBtn, rechtsBtn;
    
    /* es wird das datum angezeigt, welches der aktuellste ist und dann die nöchsten 6 darauffolgenden tage der vergangenheit.
    um die nächsten 7 tage anzuzeigen, braucht man die aktuelle position der liste umd die nächsten 7 tage zu laden. */
    private int current = 0;
    
    // deklaration des diagramms
    private BarChart barChart;
    
    private DatenbankVerbindung datenbankVerbindung;
    
    public Wochenbericht(Stage primarySrage, Benutzer benutzer){
        this.primaryStage = primarySrage;
        this.benutzer = benutzer;
        
        // die variablen oben werden initialisiert.
        initialisieren();
        
        // Vertical Box initialisieren
        vBox = new VBox(barChart, getGridPane_1());
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);      // es soll mittig angezeigt werden.
    }
    
    private void initialisieren(){
        datenbankVerbindung = new DatenbankVerbindung();
        OLrückblick = datenbankVerbindung.getOLrueckblick(benutzer.getId());
        
        zurückBtn = new Button("zurück");
        linksBtn = new Button("<---");
        rechtsBtn = new Button("--->");
        
        zurückBtn.setOnAction(this);
        linksBtn.setOnAction(this);
        rechtsBtn.setOnAction(this);
        
        setBarChart();      // barchart wird gesetzt.
    }
    
    public Scene getScene(){
        // Fenster zurückgeben
        return new Scene(vBox, fensterBreite, fensterHöhe);
    }
    
    // barchart wird erstellt
    private void setBarChart(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Datum");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Kalorienanzahl");
        
        barChart = new BarChart(xAxis, yAxis);
        
        fillBarChart();
    }
    
    // barchart wird mit den daten der liste gefüllt
    public void fillBarChart(){
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Kalorien die du zu dir genommen  hast");
        
        // wenn in der db weniger als 7 einträge sind, soll es den barchart entsprechend füllen. ansonsten immer 7 einträge geben.
        // wenn der index current ist, soll current dem schleifenwiederholung hinzuberechnet werden, um wieder 7 mal aufgerunfen werden zu können.
        for(int index = current; index < (OLrückblick.size() < 7 ? OLrückblick.size() : 7 + current); index++){
            dataSeries.getData().add(new XYChart.Data(OLrückblick.get(index).getDate().toString(), OLrückblick.get(index).getTagesKcal()));
        }
        barChart.getData().setAll(dataSeries);
    }
    
    // dient zur struktur
    private GridPane getGridPane_1(){
        // GridPane erstellen
        GridPane gridPane = new GridPane();
        
        // Objekte dem Grid hinzufügen
        gridPane.getChildren().add(zurückBtn);   // Button
        gridPane.getChildren().add(linksBtn);
        gridPane.getChildren().add(rechtsBtn);
        
        // Abstand zu den Seiten
        gridPane.setPadding(new Insets(0,0,0,0));
        //gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.CENTER);
        
        // Positionierung
        GridPane.setConstraints(linksBtn, 0, 0);
        GridPane.setConstraints(zurückBtn, 1, 0);
        GridPane.setConstraints(rechtsBtn, 2, 0);
        
        return gridPane;
    }

    // onklicks für button
    @Override
    public void handle(ActionEvent event) {
        Meldung meldung = new Meldung();
        if(event.getSource() == zurückBtn){
            Hauptmenü hauptmenü = new  Hauptmenü(primaryStage);
            primaryStage.setScene(hauptmenü.getScene());
            primaryStage.show();
        }else if(event.getSource() == linksBtn){        // richtung aktuelle informationen
            if(current == 0){
                meldung.meldung("Ungültig", "Es sind keine weiteren Tage verfügbar.");      // wenn aktuellste information angezeigt wird
            }else{      // ausrechnen der nächsten anzuzeigenden informationen für die barchart. anschließend wird barchart mit diesen informationen gefüllt und angezeigt.
                if(current - 7 >= 0){
                    current -= 7;
                    fillBarChart();
                }else{
                    current = 0;
                    fillBarChart();
                }
            }
        }else if(event.getSource() == rechtsBtn){       // richtung vergangene informationen
            if(current == OLrückblick.size() - 7){
                meldung.meldung("Ungültig", "Es sind keine weiteren Tage verfügbar.");      // wenn älteste information angezeigt wird.
            }else{
                if(current + 7 <= OLrückblick.size() - 7){
                    current += 7;
                    fillBarChart();
                }else{
                    current = OLrückblick.size() - 7;
                    fillBarChart();
                }
            }
        }
    }
}