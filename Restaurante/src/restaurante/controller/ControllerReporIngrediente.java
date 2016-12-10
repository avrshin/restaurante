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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import restaurante.model.domain.VOCompra;
import restaurante.model.domain.VOIngrediente;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerReporIngrediente implements Initializable {

//    @FXML
//    private Button buttonVoltar;
//    @FXML
//    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelarIngrediente;
//    @FXML
//    private Button buttonReporIngrediente;
//    @FXML
//    private Label labelNomeIngrediente;
//    @FXML
//    private Label labelQuantidadeIngrediente;
//    @FXML
//    private Label labelDataIngrediente;
//    @FXML
//    private Label labelValorIndividualIngrediente;
    @FXML
    private TextField textFieldQuantidadeIngrediente;
    @FXML
    private DatePicker datePickerDataCompraIngrediente;
    @FXML
    private TextField textFieldValorIndividualIngrediente;
    @FXML
    private TableView<?> tableViewIngrediente;

    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private Context context = new Context(new StrategyReporIngrediente());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioLogado = SingletonLogin.getInstance();
        this.context.executeStrategy(this.tableViewIngrediente);
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
    private void actionButtonReporIngrediente(ActionEvent event) {
        VOIngrediente vo = null;
        if (this.tableViewIngrediente.getSelectionModel()
                .getSelectedIndex() < 0) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione um Ingrediente!");
            alert.setTitle(null);
            alert.setHeaderText(null);

            alert.showAndWait();
            return;
        } else {
            vo = (VOIngrediente) this.tableViewIngrediente.getSelectionModel()
                    .getSelectedItem();
        }

        if (!this.checaCamposVazios() || !this.validaDados()) {
            return;
        }

        VOCompra compra = new VOCompra(-1,
                this.datePickerDataCompraIngrediente.getValue(),
                Double.valueOf(this.textFieldValorIndividualIngrediente.getText())
                * Integer.valueOf(this.textFieldQuantidadeIngrediente.getText()),
                Integer.valueOf(this.textFieldQuantidadeIngrediente.getText()),
                -1, vo.getIdIngrediente());

        Facade pers = new Facade();
        int id = pers.inserirCompra(this.usuarioLogado.getUsuarioLogado(), compra);
        boolean b = false;
        if (id > 0) {
            Facade p = new Facade();
            vo.setQuantidade(vo.getQuantidade() + compra.getQuantidade());
            b = p.alterarIngrediente(this.usuarioLogado.getUsuarioLogado(), vo);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (id <= 0 || !b) {
            alert.setContentText("Falha ao repor ingrediente!");
        } else {
            alert.setContentText("Ingrediente reposto com sucesso!");
        }
        alert.showAndWait();
    }

    private boolean checaCamposVazios() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.datePickerDataCompraIngrediente.getValue() == null) {
            alert.setContentText("Selecione a data de compra!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldQuantidadeIngrediente.getText().equals("")) {
            alert.setContentText("Digite uma quantidade!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldValorIndividualIngrediente.getText().equals("")) {
            alert.setContentText("Digite o valor individual!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validaDados() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (!this.textFieldQuantidadeIngrediente.getText().matches("[0-9]*")) {
            alert.setContentText("Digite uma quantidade adequada!");
            alert.showAndWait();
            return false;
        } else if (!this.textFieldValorIndividualIngrediente.getText().matches("[0-9]*\\.[0-9]*")) {
            alert.setContentText("Digite um valor individual adequado!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
