/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import restaurante.model.domain.VOFuncionario;
import restaurante.model.domain.VOUsuario;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerCadastrarAtualizarUsuario implements Initializable {

//    @FXML
//    private Button buttonVoltar;
//    @FXML
//    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelarUsuario;
//    @FXML
//    private Button buttonCadastrarAtualizarUsuario;
//    @FXML
//    private Label labelFuncionarioUsuario;
//    @FXML
//    private Label labelLoginUsuario;
//    @FXML
//    private Label labelSenhaUsuario;
//    @FXML
//    private Label labelRepitaSenhaUsuario;
    @FXML
    private ComboBox<?> comboBoxFuncionarioUsuario;
    @FXML
    private TextField textFieldLoginUsuario;
    @FXML
    private PasswordField passwordFieldSenhaUsuario;
    @FXML
    private PasswordField passwordFieldRepitaSenhaUsuario;
//    @FXML
//    private Label labelTipoUsuario;
    @FXML
    private CheckBox checkBoxTipoUsuario;

    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private VOUsuario vOUsuario = null;
    private char atualizar = 'n'; // n = nao, s = sim

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioLogado = SingletonLogin.getInstance();
        this.setComboBox();
    }

    @FXML
    private void actionButtonVoltar(ActionEvent event) {
        try {
            cena.changeScene(new ControllerGerenciarUsuario(
                    "/restaurante/view/FXMLCadastrarAtualizarUsuario.fxml"),
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
    private void actionButtonCancelarUsuario(ActionEvent event) {
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
            System.out.println("Erro: actionButtonCancelarUsuario" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonCadastrarAtualizarUsuario(ActionEvent event) {
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
        String u = "0";
        if (this.checkBoxTipoUsuario.isSelected()) {
            u = "s";
        } else {
            u = "n";
        }

        VOUsuario vo = new VOUsuario(
                ((VOFuncionario) this.comboBoxFuncionarioUsuario.getSelectionModel().getSelectedItem())
                .getIdFuncionario(), this.textFieldLoginUsuario.getText(),
                this.passwordFieldSenhaUsuario.getText(), u);

        Facade pers = new Facade();
        int id = pers.inserirUsuario(this.usuarioLogado.getUsuarioLogado(), vo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (id <= 0) {
            alert.setContentText("Falha ao cadastrar o usuario");
        } else {
            vo.setIdUsuario(id);
            alert.setContentText("Usuario cadastrado com sucesso!");
        }
        alert.showAndWait();
    }

    private void atualizar() {
        if (this.vOUsuario == null) {
            System.out.println("VOUsuario nao setado, atualizar, ControllerCadastrarAtualizarUsuario");
            return;
        }

        String u = "0";
        if (this.checkBoxTipoUsuario.isSelected()) {
            u = "s";
        } else {
            u = "n";
        }

        this.vOUsuario.setLogin(this.textFieldLoginUsuario.getText());
        this.vOUsuario.setSenha(this.passwordFieldSenhaUsuario.getText());
        this.vOUsuario.setTipoUsuario(u);

        Facade pers = new Facade();
        boolean b = pers.alterarUsuario(this.usuarioLogado.getUsuarioLogado(), this.vOUsuario);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (b) {
            alert.setContentText("Usuario atualizado com sucesso!");
        } else {
            alert.setContentText("Falha ao atualizar usuario");
        }
        alert.showAndWait();
    }

    private boolean checaCamposVazios() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.textFieldLoginUsuario.getText().equals("")) {
            alert.setContentText("Digite um Login!");
            alert.showAndWait();
            return false;
        } else if (this.passwordFieldSenhaUsuario.getText().equals("")) {
            alert.setContentText("Digite uma senha!");
            alert.showAndWait();
            return false;
        } else if (this.passwordFieldRepitaSenhaUsuario.getText().equals("")) {
            alert.setContentText("Digite a senha!");
            alert.showAndWait();
            return false;
        } else if (this.comboBoxFuncionarioUsuario.getSelectionModel().getSelectedIndex() < 0) {
            alert.setContentText("Selecione um funcionário!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validaDados() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (!this.passwordFieldRepitaSenhaUsuario.getText()
                .equals(this.passwordFieldSenhaUsuario.getText())) {
            alert.setContentText("Senhas digitadas diferentes!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void setComboBox() {
        ObservableList<VOFuncionario> funcionario = new Facade().listarFuncionario();
        ((ComboBox<VOFuncionario>) this.comboBoxFuncionarioUsuario).setItems(funcionario);
    }

    private void setCamposTela() {
        this.textFieldLoginUsuario.setText(this.vOUsuario.getLogin());

        if (this.vOUsuario.getTipoUsuario().equals("s")) {
            this.checkBoxTipoUsuario.setSelected(true);
        } else {
            this.checkBoxTipoUsuario.setSelected(false);
        }

        Iterator it = ((ObservableList<VOFuncionario>) this.comboBoxFuncionarioUsuario
                .getItems()).iterator();
        VOFuncionario vo = null;
        while (it.hasNext()) {
            vo = (VOFuncionario) it.next();
            if (vo.getIdFuncionario() == this.vOUsuario.getIdUsuario()) {
                ((ComboBox<VOFuncionario>) this.comboBoxFuncionarioUsuario)
                        .getSelectionModel().select(vo);
                return;
            }
        }
    }

    public void setvOUsuario(VOUsuario vOUsuario) {
        if (vOUsuario == null) {
            return;
        }
        this.vOUsuario = vOUsuario;
        this.setCamposTela();
    }

    public void setAtualizar(char atualizar) {
        this.atualizar = atualizar;
    }

}
