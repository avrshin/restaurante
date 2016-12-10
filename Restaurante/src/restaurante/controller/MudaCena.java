/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author luis
 */
public class MudaCena {

    public Object changeScene(Object controller, String pathFXML, 
            ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pathFXML));

        if (controller != null) {
            loader.setController(controller);
        }
        Parent root = loader.load();

        Scene scene = new Scene(root);

        // pega o mesmo stage (primaryStage)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
        
        return (Object) loader.getController();
    }

}
