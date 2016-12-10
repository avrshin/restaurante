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
public class ControllerMenu implements Initializable {

//    @FXML
//    private Label labelNomeSecao;
//    @FXML
//    private Button buttonGerenciarFuncionario;
//    @FXML
//    private Button buttonGerenciarMesa;
//    @FXML
//    private Button buttonGerenciarSalao;
//    @FXML
//    private Button buttonGerenciarUsuario;
//    @FXML
//    private Button buttonGerenciarBebida;
//    @FXML
//    private Button buttonGerenciarPrato;
//    @FXML
//    private Button buttonGerenciarIngrediente;
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
    private void actionButtonGerenciarFuncionario(ActionEvent event) {

        try {
            cena.changeScene(new ControllerGerenciarFuncionario(
                    "/restaurante/view/FXMLCadastrarAtualizarFuncionario.fxml"),
                    "/restaurante/view/FXMLGerenciar.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro actionButtonGerenciarFuncionario: "
                    + ex.getMessage());
        }

    }

    @FXML
    private void actionButtonGerenciarMesa(ActionEvent event) {

        try {
            cena.changeScene(new ControllerGerenciarMesa(
                    "/restaurante/view/FXMLCadastrarAtualizarMesa.fxml"),
                    "/restaurante/view/FXMLGerenciar.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro actionButtonGerenciarMesa: "
                    + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonGerenciarSalao(ActionEvent event) {

        try {
            cena.changeScene(new ControllerGerenciarSalao(
                    "/restaurante/view/FXMLCadastrarAtualizarSalao.fxml"),
                    "/restaurante/view/FXMLGerenciar.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro actionButtonGerenciarSalao: "
                    + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonGerenciarUsuario(ActionEvent event) {

        try {
            cena.changeScene(new ControllerGerenciarUsuario(
                    "/restaurante/view/FXMLCadastrarAtualizarUsuario.fxml"),
                    "/restaurante/view/FXMLGerenciar.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro actionButtonGerenciarUsuario: "
                    + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonGerenciarBebida(ActionEvent event) {

        try {
            cena.changeScene(new ControllerGerenciarBebida(
                    "/restaurante/view/FXMLCadastrarAtualizarBebida.fxml"),
                    "/restaurante/view/FXMLGerenciar.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro actionButtonGerenciarBebida: "
                    + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonGerenciarPrato(ActionEvent event) {

        try {
            cena.changeScene(new ControllerGerenciarPrato(
                    "/restaurante/view/FXMLCadastrarAtualizarPrato.fxml"),
                    "/restaurante/view/FXMLGerenciar.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro actionButtonGerenciarPrato: "
                    + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonGerenciarIngrediente(ActionEvent event) {

        try {
            cena.changeScene(new ControllerGerenciarIngrediente(
                    "/restaurante/view/FXMLCadastrarAtualizarIngrediente.fxml"),
                    "/restaurante/view/FXMLGerenciar.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro actionButtonGerenciarIngrediente: "
                    + ex.getMessage());
        }
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
