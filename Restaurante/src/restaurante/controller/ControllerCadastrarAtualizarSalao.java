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
import javafx.scene.control.TextField;
import restaurante.model.domain.VOSalao;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerCadastrarAtualizarSalao implements Initializable {

//    @FXML
//    private Button buttonVoltar;
//    @FXML
//    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelar;
    @FXML
    private Button buttonCadastrarAtualizarSalao;
//    @FXML
//    private Label labelNome;
//    @FXML
//    private Label labelCapacidadeMesas;
    @FXML
    private TextField textFieldNomeSalao;
    @FXML
    private TextField textFieldCapacidadeMesasSalao;

    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private VOSalao vOSalao = null;
    private char atualizar = 'n'; // s = sim, n = nao

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
            cena.changeScene(new ControllerGerenciarSalao(
                    "/restaurante/view/FXMLCadastrarAtualizarSalao.fxml"),
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
    private void actionButtonCancelar(ActionEvent event) {
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
            System.out.println("Erro: actionButtonCancelar" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonCadastrarAtualizarSalao(ActionEvent event) {
        if (!this.checaCamposVazios() || !this.validaDados()) {
            return;
        }
        
        if (atualizar == 's') {
            this.atualizar();
        } else {
            this.cadastrar();
        }
    }

    private void cadastrar() {
        VOSalao vo = new VOSalao(-1, Integer.valueOf(this.textFieldCapacidadeMesasSalao.getText()),
                this.textFieldNomeSalao.getText());

        Facade pers = new Facade();
        int id = pers.inserirSalao(this.usuarioLogado.getUsuarioLogado(), vo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (id <= 0) {
            alert.setContentText("Falha ao cadastrar o salão");
        } else {
            vo.setIdSalao(id);
            alert.setContentText("Salao cadastrado com sucesso!");
        }
        alert.showAndWait();
    }

    private void atualizar() {
        if (this.vOSalao == null) {
            System.out.println("VOSalao nao setado, atualizar, ControllerCadastrarAtualizarSalao");
            return;
        }

        this.vOSalao.setNome(this.textFieldNomeSalao.getText());
        this.vOSalao.setNumMesas(Integer.valueOf(this.textFieldCapacidadeMesasSalao.getText()));

        Facade pers = new Facade();
        boolean b = pers.alterarSalao(this.usuarioLogado.getUsuarioLogado(), this.vOSalao);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (b) {
            alert.setContentText("Salao atualizado com sucesso!");
        } else {
            alert.setContentText("Falha ao atualizar o salão");
        }
        alert.showAndWait();
    }

    public void setCamposTela(VOSalao vo) {
        if (vo == null) {
            return;
        }
        this.textFieldCapacidadeMesasSalao.setText(String.valueOf(vo.getNumMesas()));
        this.textFieldNomeSalao.setText(vo.getNome());
        this.vOSalao = vo;

        this.buttonCadastrarAtualizarSalao.setText("Atualizar");
    }

    private boolean checaCamposVazios() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.textFieldCapacidadeMesasSalao.getText().equals("")) {
            alert.setContentText("Digite a capacidade de mesas do salão!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldNomeSalao.getText().equals("")) {
            alert.setContentText("Digite um nome para o salão!");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    private boolean validaDados() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        
        if(!this.textFieldCapacidadeMesasSalao.getText().matches("^[1-9][0-9]*")) {
            alert.setContentText("Digite uma capacidade de mesas adequada!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void setAtualizar(char atualizar) {
        this.atualizar = atualizar;
    }

}
