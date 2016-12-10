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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import restaurante.model.domain.VOMesa;
import restaurante.model.domain.VOPedido;
import restaurante.model.domain.VOProduto;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerPedidoFechar implements Initializable {

//    @FXML
//    private Button buttonVoltar;
//    @FXML
//    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelar;
//    @FXML
//    private Button buttonConfirmar;
//    @FXML
//    private Label labelMesa;
    @FXML
    private ComboBox<?> comboBoxMesa;
    @FXML
    private TableView<?> tableViewProdutosPedido;
//    @FXML
//    private Label labelTotalAPagar;
    @FXML
    private Label labelValor;

    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private VOPedido vOPedido = null;
    private Context context = new Context(new StrategyPedidoProduto());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioLogado = SingletonLogin.getInstance();
        this.setComboBox();
        this.context.executeStrategy(this.tableViewProdutosPedido);
    }

    @FXML
    private void actionButtonVoltar(ActionEvent event) {
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
    private void actionButtonConfirmar(ActionEvent event) {
        if (!this.checaCamposTela()) {
            return;
        }
        VOMesa mesa = ((ComboBox<VOMesa>) this.comboBoxMesa)
                .getSelectionModel().getSelectedItem();
        Facade persMesa = new Facade();
        if (!persMesa.desalocarMesa(this.usuarioLogado.getUsuarioLogado(), mesa)) {
            System.out.println("fecharPedido actionButtonConfirmar ControllerPedidoFechar");
            return;
        }
        
        boolean pedido = new Facade().fecharPedido(mesa.getIdMesa());
        if (!pedido) {
            System.out.println("fecharPedido actionButtonConfirmar ControllerPedidoFechar");
            return;
        }
        
        this.actionComboBoxMesa(null);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pedido fechado com sucesso!");
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    private void actionComboBoxMesa(ActionEvent event) {
        ObservableList<VOProduto> produtos = new Facade().listarPorMesaProduto(
                ((VOMesa) this.comboBoxMesa.getSelectionModel()
                .getSelectedItem()).getIdMesa());
        ((TableView<VOProduto>) this.tableViewProdutosPedido)
                .setItems(produtos);
        
        this.labelValor.setText(String.valueOf(this.calculaTotalAPagar(produtos)));
    }
    
    private double calculaTotalAPagar(ObservableList<VOProduto> produtos) {
        double total = 0;
        for(VOProduto pro : produtos) {
            total += pro.getPrecoVenda();
        }
        return total;        
    }
    
    private void setComboBox() {
         ObservableList<VOMesa> mesa = new Facade().listarOcupadasMesa();
        ((ComboBox<VOMesa>) this.comboBoxMesa).setItems(mesa);
    }
    
    private boolean checaCamposTela() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.comboBoxMesa.getSelectionModel().getSelectedIndex() < 0) {
            alert.setContentText("Selecione uma Mesa!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
