/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Kasim
 */
public class ErnährungsProgramm extends Application {
    
    private Hauptmenü hauptmenü;        // um das Hauptfenster aufzurufen.
    
    @Override
    public void start(Stage primaryStage) {
        // Fenster Tiel setzen
        primaryStage.setTitle("Ernährungs Programm");
        
        hauptmenü = new  Hauptmenü(primaryStage);       // initialisieren des variablen
        
        primaryStage.setScene(hauptmenü.getScene());    // variablenfenster wird dem stage gesetzt und anschließend angezeigt.
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    } 
}