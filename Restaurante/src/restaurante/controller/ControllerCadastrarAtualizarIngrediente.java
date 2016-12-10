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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import restaurante.model.domain.VOIngrediente;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerCadastrarAtualizarIngrediente implements Initializable {

//    @FXML
//    private Button buttonVoltar;
    @FXML
    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelarIngrediente;
    @FXML
    private Button buttonCadastrarAtualizarIngrediente;
//    @FXML
//    private Label labelNomeIngrediente;
//    @FXML
//    private Label labelUnidadeMedidaIngrediente;
    @FXML
    private TextField textFieldNomeIngrediente;
    @FXML
    private TextField textFieldUnidadeMedidaIngrediente;

    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private VOIngrediente vOIngrediente = null;
    private char atualizarIngrediente = 'n'; // n = nao, s = sim

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioLogado = SingletonLogin.getInstance();
    }

    @FXML
    private void actionButtonVoltar(ActionEvent event) {
        try {
            cena.changeScene(new ControllerGerenciarIngrediente(
                    "/restaurante/view/FXMLCadastrarAtualizarIngrediente.fxml"),
                    "/restaurante/view/FXMLGerenciar.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionButtonVoltar" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonLogout(ActionEvent event) {
        try {
            cena.changeScene(null, "/restaurante/view/FXMLLogin.fxml",
                    event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionButtonLogout" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonCancelarIngrediente(ActionEvent event) {
        try {
            if (this.usuarioLogado.getUsuarioLogado().getTipoUsuario().equals("s")) {
                cena.changeScene(null,
                        "/restaurante/view/FXMLMenu.fxml", event);
            } else if (this.usuarioLogado.getUsuarioLogado().getTipoUsuario().equals("n")) {
                cena.changeScene(null,
                        "/restaurante/view/FXMLMenuGarcon.fxml", event);
            }
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionButtonCancelarIngrediente" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonCadastrarAtualizarIngrediente(ActionEvent event) {
        if (!this.checaCamposVazios() || !this.validaDados()) {
            return;
        }
        
        if (atualizarIngrediente == 's') {
            this.atualizar();
        } else {
            this.cadastrar();
        }
    }

    private void cadastrar() {
        VOIngrediente vo = new VOIngrediente(-1,
                this.textFieldNomeIngrediente.getText(),
                0, Integer.valueOf(this.textFieldUnidadeMedidaIngrediente.getText()));

        Facade pers = new Facade();
        int id = pers.inserirIngrediente(this.usuarioLogado.getUsuarioLogado(), vo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (id <= 0) {
            alert.setContentText("Falha ao cadastrar Ingrediente!");
        } else {
            vo.setIdIngrediente(id);
            alert.setContentText("Ingrediente cadastrado com sucesso!");
        }
        alert.showAndWait();
    }

    private void atualizar() {
        if (this.vOIngrediente == null) {
            System.out.println("VOIngrediente não setado, atualizar, "
                    + "actionButtonCadastrarAtualizarIngrediente");
            return;
        }

        this.vOIngrediente.setNome(this.textFieldNomeIngrediente.getText());
        this.vOIngrediente.setUnidadeMedida(Integer.valueOf(
                this.textFieldUnidadeMedidaIngrediente.getText()));

        Facade pers = new Facade();
        boolean b = pers.alterarIngrediente(
                this.usuarioLogado.getUsuarioLogado(), this.vOIngrediente);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (b) {
            alert.setContentText("Ingrediente atualizado com sucesso!");
        } else {
            alert.setContentText("Falha ao atualizar ingrediente!");
        }
        alert.showAndWait();
    }

    public void setIngrediente(VOIngrediente vo) {
        this.vOIngrediente = vo;
        this.setCamposTela();
        this.buttonCadastrarAtualizarIngrediente.setText("Atualizar");
        this.labelNomeSecao.setText("Atualizar Ingrediente");
    }

    private void setCamposTela() {
        this.textFieldNomeIngrediente.setText(this.vOIngrediente.getNome());
        this.textFieldUnidadeMedidaIngrediente.setText(
                String.valueOf(this.vOIngrediente.getUnidadeMedida()));
    }

    private boolean checaCamposVazios() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.textFieldNomeIngrediente.getText().equals("")) {
            alert.setContentText("Digite o nome do Ingrediente!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldUnidadeMedidaIngrediente.getText().equals("")) {
            alert.setContentText("Digite a unidade de medida!");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    private boolean validaDados() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        
        if(!this.textFieldUnidadeMedidaIngrediente.getText().matches("[123]")) {
            alert.setContentText("Digite uma unidade de medida adequado!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void setAtualizarIngrediente(char atualizarIngrediente) {
        this.atualizarIngrediente = atualizarIngrediente;
    }

}
