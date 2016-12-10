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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import restaurante.model.domain.VOMesa;
import restaurante.model.domain.VOSalao;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerCadastrarAtualizarMesa implements Initializable {

//    @FXML
//    private Button buttonVoltar;
    @FXML
    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelarMesa;
    @FXML
    private Button buttonCadastrarAtualizarMesa;
//    @FXML
//    private Label labelSalaoMesa;
//    @FXML
//    private Label labelQuantidadeCadeirasMesa;
//    @FXML
//    private Label labelNumeroMesa;
    @FXML
    private ComboBox<?> comboBoxSalaoMesa;
    @FXML
    private TextField textFieldQuantidadeCadeirasMesa;
    @FXML
    private TextField textFieldNumeroMesa;

    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private VOMesa vOMesa = null;
    private String atualizar = "n"; // n = nao, s = sim

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
            cena.changeScene(new ControllerGerenciarMesa(
                    "/restaurante/view/FXMLCadastrarAtualizarMesa.fxml"),
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
    private void actionButtonCancelarMesa(ActionEvent event) {
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
            System.out.println("Erro: actionButtonCancelarMesa" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonCadastrarAtualizarMesa(ActionEvent event) {
        if (!this.checaCamposVazios() || !this.validaDados()) {
            return;
        }
        
        if (atualizar.equals("s")) {
            this.atualizar();
        } else {
            this.cadastrar();
        }
    }

    private void atualizar() {
        if (this.vOMesa == null) {
            System.out.println("VOMesa não setado, atualizar, ControllerCadastrarAtualizarMesa");
            return;
        }

        this.vOMesa.setIdSalao(
                ((VOSalao) this.comboBoxSalaoMesa.getSelectionModel().getSelectedItem()).getIdSalao());
        this.vOMesa.setNumCadeiras(Integer.valueOf(this.textFieldQuantidadeCadeirasMesa.getText()));
        this.vOMesa.setNumero(Integer.valueOf(this.textFieldNumeroMesa.getText()));

        Facade pers = new Facade();
        boolean b = pers.alterarMesa(this.usuarioLogado.getUsuarioLogado(), this.vOMesa);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (b) {
            alert.setContentText("Mesa atualizada com sucesso!");
        } else {
            alert.setContentText("Falha ao atualizar a mesa");
        }
        alert.showAndWait();
    }

    private void cadastrar() {
        VOMesa vo = new VOMesa(-1,
                Integer.valueOf(this.textFieldQuantidadeCadeirasMesa.getText()),
                Integer.valueOf(this.textFieldNumeroMesa.getText()),
                "v",
                ((VOSalao) this.comboBoxSalaoMesa.getSelectionModel().getSelectedItem()).getIdSalao());

        Facade pers = new Facade();
        int id = pers.inserirMesa(this.usuarioLogado.getUsuarioLogado(), vo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (id <= 0) {
            alert.setContentText("Falha ao cadastrar a mesa!");
        } else {
            alert.setContentText("Mesa cadastrada com sucesso!");
        }
        alert.showAndWait();
    }

    private void setComboBox() {
        ObservableList<VOSalao> salao = new Facade().listarSalao();
        ((ComboBox<VOSalao>) this.comboBoxSalaoMesa).setItems(salao);
    }

    public void setVOMesa(VOMesa vo) {
        if (vo == null) {
            return;
        }
        this.vOMesa = vo;
        this.labelNomeSecao.setText("Atualizar Mesa");
        this.buttonCadastrarAtualizarMesa.setText("Atualizar");
        this.setCamposTela();
    }

    private void setCamposTela() {
        this.textFieldNumeroMesa.setText(String.valueOf(vOMesa.getNumero()));
        this.textFieldQuantidadeCadeirasMesa.setText(String.valueOf(this.vOMesa.getNumCadeiras()));

        Iterator it = ((ObservableList<VOSalao>) this.comboBoxSalaoMesa.getItems()).iterator();
        VOSalao vo = null;
        while (it.hasNext()) {
            vo = (VOSalao) it.next();
            if (vo.getIdSalao() == this.vOMesa.getIdSalao()) {
                ((ComboBox<VOSalao>) this.comboBoxSalaoMesa).getSelectionModel().select(vo);
                return;
            }
        }
    }

    private boolean checaCamposVazios() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.comboBoxSalaoMesa.getSelectionModel().getSelectedIndex() < 0
                || this.comboBoxSalaoMesa.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Selecione o salão!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldNumeroMesa.getText().equals("")) {
            alert.setContentText("Digite o numero da mesa!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldQuantidadeCadeirasMesa.getText().equals("")) {
            alert.setContentText("Digite a quantidade de cadeiras!");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    private boolean validaDados() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        
        if(!this.textFieldNumeroMesa.getText().matches("[0-9]*")) {
            alert.setContentText("Digite um numero adequado!");
            alert.showAndWait();
            return false;
        } else if(!this.textFieldQuantidadeCadeirasMesa.getText().matches("^[1-9][0-9]*")) {
            alert.setContentText("Digite uma quantidade adequada!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void setAtualizar(String atualizar) {
        this.atualizar = atualizar;
    }

}
