/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author luis
 */
class ButtonHandler implements EventHandler<ActionEvent> {

    private char option;
    
    public ButtonHandler(char option) {
        this.option = option;
    }

    @Override
    public void handle(ActionEvent event) {
        String fxml;
        if(option == 'i') {
            fxml = "/restaurante/view/FXMLReporIngrediente.fxml";
        } else if(option == 'b') {
            fxml = "/restaurante/view/FXMLReporBebida.fxml";
        } else {
            System.out.println("opcao invalida, ButtonHandler()");
            return;
        }
        
        try {
            MudaCena cena = new MudaCena();
            cena.changeScene(null, fxml, event);
        } catch (IOException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Erro handle ButtonHandler(): "
                    + e.getMessage());
        }
    }
    
}
