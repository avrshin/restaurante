/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import restaurante.model.domain.VOBebida;
import restaurante.model.domain.VOIngrediente;
import restaurante.model.domain.VOMesa;
import restaurante.model.domain.VOPedido;
import restaurante.model.domain.VOPrato;
import restaurante.model.domain.VOProduto;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerPedidoFazer implements Initializable {

//    @FXML
//    private Button buttonVoltar;
//    @FXML
//    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelarPedido;
//    @FXML
//    private Button buttonFinalizarPedido;
//    @FXML
//    private Label labelMesaPedido;
//    @FXML
//    private Label labelProdutos;
//    @FXML
//    private TextField textFieldPesquisarProduto;
    @FXML
    private ListView<?> listViewProdutos;
//    @FXML
//    private Button buttonAddProduto;
//    @FXML
//    private Button buttonRemoveProduto;
    @FXML
    private ComboBox<?> comboBoxMesaPedido;
//    @FXML
//    private Label labelPedido;
    @FXML
    private ListView<?> listViewPedido;

    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private VOPedido vOPedido = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioLogado = SingletonLogin.getInstance();
        this.setComboBox();
        this.preencherListViewProdutos();
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
            cena.changeScene(null, "/restaurante/view/FXMLLogin.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionButtonLogout" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonCancelarPedido(ActionEvent event) {
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
            System.out.println("Erro: actionButtonCancelarPedido" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonFinalizarPedido(ActionEvent event) {
        if (!this.checaCamposTela()) {
            return;
        }
        VOMesa mesa = ((ComboBox<VOMesa>) this.comboBoxMesaPedido)
                .getSelectionModel().getSelectedItem();
        Facade persMesa = new Facade();
        if (!persMesa.alocarMesa(this.usuarioLogado.getUsuarioLogado(), mesa)) {
            System.out.println("alocarMesa actionButtonFinalizarPedido ControllerPedidoFazer");
            return;
        }

        ArrayList<VOProduto> produtoList = new ArrayList<>();
        produtoList.addAll(((ListView<VOProduto>) this.listViewPedido)
                .getItems());

        VOPedido vo = new VOPedido(-1, LocalTime.now(), LocalDate.now(),
                mesa.getIdMesa(), 'a', produtoList);

        int idPedido = new Facade()
                .novoPedido(this.usuarioLogado.getUsuarioLogado(), vo);
        if (idPedido < 0) {
            System.out.println("novoPedido actionButtonFinalizarPedido ControllerPedidoFazer");
            return;
        }
        vo.setIdPedido(idPedido);

        int idPedidoUsuario = new Facade()
                .novoUsuarioPedido(this.usuarioLogado.getUsuarioLogado(), vo);
        if (idPedidoUsuario < 0) {
            System.out.println("setUsuarioPedido actionButtonFinalizarPedido ControllerPedidoFazer");
            return;
        }

        for (VOProduto produto : produtoList) {
            if (new Facade()
                    .novoProdutoPedido(this.usuarioLogado.getUsuarioLogado(), vo, produto) < 0) {
                System.out.println("setProdutoPedido actionButtonFinalizarPedido ControllerPedidoFazer");
                return;
            }
            //this.diminuiQuantidadeIngrediente(produto);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Novo Pedido realizado com sucesso!");
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.showAndWait();

    }

    private boolean aumentaQuantidadeProduto(VOProduto produto) {
        if (produto == null) {
            return false;
        }

        if (produto.getTipo() == 1) {
            return this.aumentaQuantidadeIngredientesPrato(produto.getIdProduto());
        } else {
            return this.aumentaQuantidadeBebida(produto.getIdProduto());
        }
    }

    private boolean aumentaQuantidadeIngredientesPrato(int idProduto) {
        VOPrato prato = new Facade().listarUnicoPrato(idProduto);

        ArrayList<VOIngrediente> list = new ArrayList<>();
        list.addAll(new Facade().listarIngredienteDeProduto(prato.getIdPrato()));

        for (VOIngrediente ing : list) {
            ing.setQuantidade(ing.getQuantidade() + 1);
            if (!new Facade()
                    .alteraQuantidadeIngrediente(this.usuarioLogado.getUsuarioLogado(), ing)) {
                return false;
            }
        }
        return true;
    }

    private boolean aumentaQuantidadeBebida(int idProduto) {
        VOBebida bebida = new Facade().listarUnicoBebida(idProduto);
        bebida.setQuantidade(bebida.getQuantidade() + 1);
        return new Facade().alteraQuantidadeBebida(this.usuarioLogado.getUsuarioLogado(), bebida);

    }

    private boolean diminuiQuantidadeProduto(VOProduto produto) {
        if (produto == null) {
            return false;
        }

        if (produto.getTipo() == 1) {
            return this.diminuiQuantidadeIngredientesPrato(produto.getIdProduto());
        } else {
            return this.diminuiQuantidadeBebida(produto.getIdProduto());
        }
    }

    private boolean diminuiQuantidadeIngredientesPrato(int idProduto) {
        VOPrato prato = new Facade().listarUnicoPrato(idProduto);

        ArrayList<VOIngrediente> list = new ArrayList<>();
        list.addAll(new Facade().listarIngredienteDeProduto(prato.getIdPrato()));

        for (VOIngrediente ing : list) {
            if (ing.getQuantidade() == 0) {
                return false;
            }
        }
        for (VOIngrediente ing : list) {
            ing.setQuantidade(ing.getQuantidade() - 1);
            if (!new Facade()
                    .alteraQuantidadeIngrediente(this.usuarioLogado.getUsuarioLogado(), ing)) {
                return false;
            }
        }
        return true;
    }

    private boolean diminuiQuantidadeBebida(int idProduto) {
        VOBebida bebida = new Facade().listarUnicoBebida(idProduto);

        if (bebida.getQuantidade() > 0) {
            bebida.setQuantidade(bebida.getQuantidade() - 1);
            return new Facade().alteraQuantidadeBebida(this.usuarioLogado.getUsuarioLogado(), bebida);
        } else {
            return false;
        }
    }

    @FXML
    private void actionButtonAddProduto(ActionEvent event) {
        VOProduto vo = ((ListView<VOProduto>) this.listViewProdutos)
                .getSelectionModel().getSelectedItem();
        if (vo == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione um produto!");
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        if (!this.diminuiQuantidadeProduto(vo)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Produto Indisponível!");
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        ((ListView<VOProduto>) this.listViewPedido).getItems().add(vo);
    }

    @FXML
    private void actionButtonRemoveProduto(ActionEvent event) {
        if (this.listViewPedido == null) {
            return;
        }
        int indice = ((ListView<VOProduto>) this.listViewPedido).getSelectionModel()
                .getSelectedIndex();

        if (indice < 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione um produto!");
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        if(!this.aumentaQuantidadeProduto(((ListView<VOProduto>) this.listViewPedido)
                .getSelectionModel().getSelectedItem())) {
            System.out.println("Erro: actionButtonRemoveProduto aumentaQuantidadeProduto");
            return;
        }
        ((ListView<VOProduto>) this.listViewPedido).getItems().remove(indice);
    }

    private void setComboBox() {
        ObservableList<VOMesa> mesa = new Facade().listarMesa();
        ((ComboBox<VOMesa>) this.comboBoxMesaPedido).setItems(mesa);
    }

    private void preencherListViewProdutos() {
        ObservableList<VOProduto> produtos = new Facade().listarOrdenadoProduto();
        ((ListView<VOProduto>) this.listViewProdutos)
                .setItems(produtos);
    }

    private boolean checaCamposTela() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.comboBoxMesaPedido.getSelectionModel().getSelectedIndex() < 0) {
            alert.setContentText("Selecione uma Mesa!");
            alert.showAndWait();
            return false;
        } else if (this.listViewPedido.getItems().isEmpty()) {
            alert.setContentText("Selecione os produtos!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
