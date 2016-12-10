/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerMenuGarcon implements Initializable {

//    @FXML
//    private Label labelNomeSecao;
//    @FXML
//    private Button buttonNovoPedido;
//    @FXML
//    private Button buttonFecharPedido;
    
    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioLogado = SingletonLogin.getInstance();
        this.usuarioLogado.getUsuarioLogado();
    }

    @FXML
    private void actionButtonNovoPedido(ActionEvent event) {

        try {
            cena.changeScene(null,
                    "/restaurante/view/FXMLPedidoFazer.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro actionButtonNovoPedido: "
                    + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonFecharPedido(ActionEvent event) {

        try {
            cena.changeScene(null,
                    "/restaurante/view/FXMLPedidoFechar.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro actionButtonFecharPedido: "
                    + ex.getMessage());
        }
    }

}
