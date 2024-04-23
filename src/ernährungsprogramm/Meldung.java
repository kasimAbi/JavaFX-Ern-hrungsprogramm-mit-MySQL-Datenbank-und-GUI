/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ernährungsprogramm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Kasim
 */
public class Meldung {
    
    public void meldung(String title, String message){
        Stage window = new Stage();
        
        // blockt hintergrund bis fenster geschlossen wird.
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);     // setzt fenstertitel in das, was der funktion als parameter mitgegeben wird
        window.setMinWidth(450);    // setzt mindestbreite des fensters
        Label meldung = new Label();
        meldung.setText(message);   // soll die nachricht darstellen
        
        Button closeButton = new Button("schließen");
        closeButton.setOnAction(e -> window.close());
        
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(meldung, closeButton);
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        
        // warten bis fenster geschlossen wird.
        window.showAndWait();
    }
}