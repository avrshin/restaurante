/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import restaurante.model.domain.VOIngrediente;
import restaurante.model.domain.VOPrato;
import restaurante.model.domain.VOProduto;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerCadastrarAtualizarPrato implements Initializable {

//    @FXML
//    private Button buttonVoltar;
//    @FXML
//    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelarPrato;
//    @FXML
//    private Button buttonCadastrarAtualizarPrato;
//    @FXML
//    private Label labelNomePrato;
//    @FXML
//    private Label labelTipoPrato;
//    @FXML
//    private Label labelValorVendaPrato;
    @FXML
    private ListView<?> listViewIngredientesRestaurante;
//    @FXML
//    private Button buttonAddIngrediente;
//    @FXML
//    private Button buttonRemoveIngrediente;
    @FXML
    private TextField textFieldNomePrato;
    @FXML
    private TextField textFieldValorVendaPrato;
    @FXML
    private ListView<?> listViewIngredientesPrato;

    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private VOPrato vOPrato = null;
    private char atualizar = 'n'; // n = nao, s = sim

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioLogado = SingletonLogin.getInstance();
        this.preencherListViewIngredienteRestaurante();
    }

    @FXML
    private void actionButtonVoltar(ActionEvent event) {
        try {
            cena.changeScene(new ControllerGerenciarPrato(
                    "/restaurante/view/FXMLCadastrarAtualizarPrato.fxml"),
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
    private void actionButtonCancelarPrato(ActionEvent event) {
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
            System.out.println("Erro: actionButtonCancelarPrato" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonCadastrarAtualizarPrato(ActionEvent event) {
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
        if (idProduto < 0) {
            System.out.println("ControllerCadastrarAtualizarPrato cadastrar erro idProduto");
            return;
        }

        List<VOIngrediente> list = new ArrayList<>();
        Iterator itr = this.listViewIngredientesPrato.getItems().iterator();
        while (itr.hasNext()) {
            list.add((VOIngrediente) itr.next());
        }

        VOPrato vo = new VOPrato(-1, idProduto, list);
        Facade pers = new Facade();
        int idp = pers.inserirPrato(this.usuarioLogado.getUsuarioLogado(), vo);

        if (idp > 0) {
            vo.setIdPrato(idp);
            if (!this.cadastrarIngredientes(vo)) {
                System.out.println("ControllerCadastrarAtualizarPrato cadastrar ingredientes");
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (idProduto <= 0) {
            alert.setContentText("Falha ao cadastrar prato!");
        } else {
            alert.setContentText("Prato cadastrado com sucesso!");
        }
        alert.showAndWait();
    }

    // retorna id do produto inserida no banco
    private int cadastrarProduto() {
        VOProduto vo = new VOProduto(-1,
                this.textFieldNomePrato.getText(),
                1, // tipo prato
                Double.valueOf(this.textFieldValorVendaPrato.getText()));

        Facade pers = new Facade();
        return pers.inserirProduto(this.usuarioLogado.getUsuarioLogado(), vo);
    }

    private boolean cadastrarIngredientes(VOPrato vo) {
        Iterator itr = vo.getIngredientes().iterator();

        while (itr.hasNext()) {
            if (new Facade()
                    .inserirIngredientePrato(this.usuarioLogado.getUsuarioLogado(),
                            vo, (VOIngrediente) itr.next()) <= 0) {
                return false;
            }
        }
        return true;
    }

    private void atualizar() {
        if (this.vOPrato == null) {
            System.out.println("VOPrato não setado, atualizar, ControllerCadastrarAtualizarPrato");
            return;
        }
        
        if (!this.atualizarProduto()) {
            System.out.println("atualizarProduto, ControllerCadastrarAtualizarPrato");
            return;
        }

        List<VOIngrediente> ingredientes = new ArrayList<>();
        Iterator itr = this.listViewIngredientesPrato.getItems().iterator();
        while (itr.hasNext()) {
            ingredientes.add((VOIngrediente) itr.next());
        }

        this.vOPrato.setIngredientes(ingredientes);

        Facade pers = new Facade();
        boolean b = pers.removerIngredientesPrato(this.vOPrato.getIdPrato());
        if (b) {
            b = this.cadastrarIngredientes(this.vOPrato);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (b) {
            alert.setContentText("Prato atualizado com sucesso!");
        } else {
            alert.setContentText("Falha ao atualizar prato!");
        }
        alert.showAndWait();
    }

    private boolean atualizarProduto() {
        VOProduto vo = new VOProduto(this.vOPrato.getIdProduto(),
                this.textFieldNomePrato.getText(),
                1, // tipo prato
                Double.valueOf(this.textFieldValorVendaPrato.getText()));

        Facade pers = new Facade();
        return pers.alterarProduto(this.usuarioLogado.getUsuarioLogado(), vo);
    }

    private boolean checaCamposVazios() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.textFieldNomePrato.getText().equals("")) {
            alert.setContentText("Digite o nome do prato!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldValorVendaPrato.getText().equals("")) {
            alert.setContentText("Digite o valor de venda!");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    private boolean validaDados() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        
        if(!this.textFieldValorVendaPrato.getText().matches("^[1-9]*\\.[0-9]*")) {
            alert.setContentText("Digite um valor de venda adequado!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    private void actionButtonAddIngrediente(ActionEvent event) {
        VOIngrediente vo = ((ListView<VOIngrediente>) this.listViewIngredientesRestaurante)
                .getSelectionModel().getSelectedItem();
        if (vo == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione um ingrediente!");
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        ((ListView<VOIngrediente>) this.listViewIngredientesPrato).getItems().add(vo);
    }

    @FXML
    private void actionButtonRemoveIngrediente(ActionEvent event) {
        if (this.listViewIngredientesPrato == null) {
            return;
        }
        VOIngrediente vo = ((ListView<VOIngrediente>) this.listViewIngredientesPrato).getSelectionModel()
                .getSelectedItem();

        if (vo == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione um ingrediente!");
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        ((ListView<VOIngrediente>) this.listViewIngredientesPrato).getItems().remove(vo);
    }

    private void preencherListViewIngredienteRestaurante() {
        ObservableList<VOIngrediente> ingredientes = new Facade().listarIngrediente();
        ((ListView<VOIngrediente>) this.listViewIngredientesRestaurante)
                .setItems(ingredientes);
    }

    private void preencherListViewIngredientePrato() {
        ObservableList<VOIngrediente> ingredientes = new Facade()
                .listarIngredienteDeProduto(this.vOPrato.getIdPrato());
        ((ListView<VOIngrediente>) this.listViewIngredientesPrato)
                .setItems(ingredientes);
    }

    public void setVOPrato(VOPrato vo) {
        if (vo == null) {
            return;
        }
        this.vOPrato = vo;
        this.preencherListViewIngredientePrato();
        this.setCamposTela();
    }

    private void setCamposTela() {
        VOProduto vo = new Facade().listarPorIDProduto(this.vOPrato.getIdProduto());
        this.textFieldNomePrato.setText(vo.getNome());
        this.textFieldValorVendaPrato.setText(String.valueOf(vo.getPrecoVenda()));
    }

    public void setAtualizar(char atualizar) {
        this.atualizar = atualizar;
    }

}
