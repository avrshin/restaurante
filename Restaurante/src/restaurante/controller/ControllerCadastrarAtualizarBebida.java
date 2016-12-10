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
import restaurante.model.domain.VOBebida;
import restaurante.model.domain.VOProduto;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerCadastrarAtualizarBebida implements Initializable {

//    @FXML
//    private Button buttonVoltar;
    @FXML
    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelarBebida;
    @FXML
    private Button buttonCadastrarAtualizarBebida;
//    @FXML
//    private Label labelNomeBebida;
//    @FXML
//    private Label labelValorVendaBebida;
//    @FXML
//    private Label labelTamanhoBebida;
    @FXML
    private TextField textFieldNomeBebida;
    @FXML
    private TextField textFieldValorVendaBebida;
    @FXML
    private TextField textFieldTamanhoBebida;

    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private VOBebida vOBebida = null;
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
            cena.changeScene(new ControllerGerenciarBebida(
                    "/restaurante/view/FXMLCadastrarAtualizarBebida.fxml"),
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
    private void actionButtonCancelarBebida(ActionEvent event) {
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
            System.out.println("Erro: actionButtonCancelarBebida" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonCadastrarAtualizarBebida(ActionEvent event) {
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
        int idProduto = this.cadastrarProduto();
        if (idProduto <= 0) {
            System.out.println("ControllerCadastrarAtualizarBebida cadastrar erro idProduto");
            return;
        }

        VOBebida vo = new VOBebida(-1, idProduto,
                Integer.valueOf(this.textFieldTamanhoBebida.getText()), 0);
        
        Facade pers = new Facade();
        int id = pers.inserirBebida(this.usuarioLogado.getUsuarioLogado(), vo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (id <= 0) {
            alert.setContentText("Falha ao cadastrar a bebida!");
        } else {
            alert.setContentText("Bebida cadastrada com sucesso!");
        }
        alert.showAndWait();
    }

    // retorna id do produto inserida no banco
    private int cadastrarProduto() {
        VOProduto vo = new VOProduto(-1,
                this.textFieldNomeBebida.getText(),
                2, // tipo bebida
                Double.valueOf(this.textFieldValorVendaBebida.getText()));

        Facade pers = new Facade();
        return pers.inserirProduto(this.usuarioLogado.getUsuarioLogado(), vo);
    }

    private void atualizar() {
        if (this.vOBebida == null) {
            System.out.println("VOBebida não setado, atualizar, ControllerCadastrarAtualizarBebida");
            return;
        }

        if (!this.atualizarProduto()) {
            System.out.println("atualizarProduto, ControllerCadastrarAtualizarBebida");
            return;
        }

        this.vOBebida.setTamanho(Integer.valueOf(this.textFieldTamanhoBebida.getText()));

        Facade pers = new Facade();
        boolean b = pers.alterarBebida(this.usuarioLogado.getUsuarioLogado(), this.vOBebida);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (b) {
            alert.setContentText("Bebida atualizada com sucesso!");
        } else {
            alert.setContentText("Falha ao atualizar a bebida");
        }
        alert.showAndWait();
    }

    private boolean atualizarProduto() {
        VOProduto vo = new VOProduto(this.vOBebida.getIdProduto(),
                this.textFieldNomeBebida.getText(),
                2, // tipo bebida
                Double.valueOf(this.textFieldValorVendaBebida.getText()));

        Facade pers = new Facade();
        return pers.alterarProduto(this.usuarioLogado.getUsuarioLogado(), vo);
    }

    public void setBebida(VOBebida vo) {
        if (vo == null) {
            return;
        }
        this.vOBebida = vo;
        this.setCamposTela();
        this.buttonCadastrarAtualizarBebida.setText("Atualizar");
        this.labelNomeSecao.setText("Atualizar Bebida");
    }

    private void setCamposTela() {
        this.textFieldNomeBebida.setText(this.vOBebida.getNome());
        this.textFieldValorVendaBebida.setText(String.valueOf(this.vOBebida.getPrecoVenda()));
        this.textFieldTamanhoBebida.setText(String.valueOf(this.vOBebida.getTamanho()));
    }

    public boolean checaCamposVazios() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.textFieldNomeBebida.getText().equals("")) {
            alert.setContentText("Digite o nome da bebida!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldTamanhoBebida.getText().equals("")) {
            alert.setContentText("Digite o tamanho da bebida!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldValorVendaBebida.getText().equals("")) {
            alert.setContentText("Digite o valor de venda da bebida!");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    public boolean validaDados() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        
        if(!this.textFieldTamanhoBebida.getText().matches("^[1-9][0-9]*")) {
            alert.setContentText("Digite um tamanho adequado!");
            alert.showAndWait();
            return false;
        } else if(!this.textFieldValorVendaBebida.getText().matches("^[1-9][0-9.]*")) {
            alert.setContentText("Digite um valor adequado!");
            alert.showAndWait();
            return false;
        } 
        return true;
    }

    public void setAtualizar(char atualizar) {
        this.atualizar = atualizar;
    }

    public void setLabelNomeSecao(Label labelNomeSecao) {
        this.labelNomeSecao = labelNomeSecao;
    }

    public void setButtonCadastrarAtualizarBebida(Button buttonCadastrarAtualizarBebida) {
        this.buttonCadastrarAtualizarBebida = buttonCadastrarAtualizarBebida;
    }

    public void setTextFieldNomeBebida(TextField textFieldNomeBebida) {
        this.textFieldNomeBebida = textFieldNomeBebida;
    }

    public void setTextFieldValorVendaBebida(TextField textFieldValorVendaBebida) {
        this.textFieldValorVendaBebida = textFieldValorVendaBebida;
    }

    public void setTextFieldTamanhoBebida(TextField textFieldTamanhoBebida) {
        this.textFieldTamanhoBebida = textFieldTamanhoBebida;
    }

    

}
