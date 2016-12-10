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
import restaurante.model.domain.VOBebida;
import restaurante.model.domain.VOCompra;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerReporBebida implements Initializable {

//    @FXML
//    private Button buttonVoltar;
//    @FXML
//    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelar;
//    @FXML
//    private Button buttonRepor;
//    @FXML
//    private Label labelQuantidadeBebida;
//    @FXML
//    private Label labelDataCompraBebida;
//    @FXML
//    private Label labelBebidaEscolhida;
//    @FXML
//    private Label labelValorIndividualBebida;
    @FXML
    private TextField textFieldQuantidadeBebidaRepor;
    @FXML
    private DatePicker datePickerDataCompraBebidaRepor;
    @FXML
    private TextField textFieldValorIndividualBebidaRepor;
    @FXML
    private TableView<VOBebida> tableViewBebidaEscolhida;

    private final MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private Context context = new Context(new StrategyReporBebida());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioLogado = SingletonLogin.getInstance();
        this.context.executeStrategy(this.tableViewBebidaEscolhida);
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
    private void actionButtonRepor(ActionEvent event) {
        VOBebida vo = null;
        if (this.tableViewBebidaEscolhida.getSelectionModel()
                .getSelectedIndex() < 0) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione uma Bebida!");
            alert.setTitle(null);
            alert.setHeaderText(null);

            alert.showAndWait();
            return;
        } else {
            vo = this.tableViewBebidaEscolhida.getSelectionModel()
                    .getSelectedItem();
        }
        
        if(!this.checaCamposVazios() || !this.validaDados()) {
            return;
        }

        VOCompra compra = new VOCompra(-1,
                this.datePickerDataCompraBebidaRepor.getValue(),
                Double.valueOf(this.textFieldValorIndividualBebidaRepor.getText())
                * Integer.valueOf(this.textFieldQuantidadeBebidaRepor.getText()),
                Integer.valueOf(this.textFieldQuantidadeBebidaRepor.getText()),
                vo.getIdBebida(), -1);

        Facade pers = new Facade();
        int id = pers.inserirCompra(this.usuarioLogado.getUsuarioLogado(), compra);
        boolean b = false;
        if(id > 0) {
            Facade p = new Facade();
            vo.setQuantidade(vo.getQuantidade() + compra.getQuantidade());
            b = p.alterarBebida(this.usuarioLogado.getUsuarioLogado(), vo);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (id <= 0 || !b) {
            alert.setContentText("Falha ao repor bebida!");
        } else {
            alert.setContentText("Bebida reposta com sucesso!");
        }
        alert.showAndWait();

    }

    private boolean checaCamposVazios() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.datePickerDataCompraBebidaRepor.getValue() == null) {
            alert.setContentText("Selecione a data de compra!");
            alert.showAndWait();
            return false;
        } else if(this.textFieldQuantidadeBebidaRepor.getText().equals("")) {
            alert.setContentText("Digite uma quantidade!");
            alert.showAndWait();
            return false;
        } else if(this.textFieldValorIndividualBebidaRepor.getText().equals("")) {
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
        
        if(!this.textFieldQuantidadeBebidaRepor.getText().matches("[0-9]*")) {
            alert.setContentText("Digite uma quantidade adequada!");
            alert.showAndWait();
            return false;
        } else if(!this.textFieldValorIndividualBebidaRepor.getText().matches("[0-9]*\\.[0-9]*")) {
            alert.setContentText("Digite um valor individual adequado!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
