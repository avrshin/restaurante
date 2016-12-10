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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author luis
 */
public abstract class AbstractControllerGerenciar implements Initializable {

    @FXML
    private Button buttonVoltar;
    @FXML
    private Label labelNomeTela;
    @FXML
    private Button buttonLogout;
    @FXML
    private Button buttonNovo;
    @FXML
    private Button buttonAtualizar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelPesquisar;
    @FXML
    private TextField textFieldPesquisar;
    @FXML
    private Button buttonBuscar;
    @FXML
    private TableView<?> tableView;
    @FXML
    private HBox hBoxBottom;

    private MudaCena cena = new MudaCena();
    private String cadastrarScene;
    private SingletonLogin usuarioLogado = null;

    public AbstractControllerGerenciar(String cadastrarScene) {
        this.cadastrarScene = cadastrarScene;
    }

    public void actionAbstractButtonVoltar(ActionEvent event) {
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
            System.out.println("Erro: actionAbstractButtonVoltar" + ex.getMessage());
        } 
    }

    public void actionAbstractButtonLogout(ActionEvent event) {
        try {
            cena.changeScene(null, "/restaurante/view/FXMLLogin.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionAbstractButtonLogout" + ex.getMessage());
        }
    }

    public void actionAbstractButtonNovo(ActionEvent event) {
        try {
            cena.changeScene(null, this.cadastrarScene, event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionAbstractButtonNovo" + ex.getMessage());
        }
    }

    public abstract void abstractInitialize();

    public abstract void actionAbstractButtonAtualizar(ActionEvent event);

    public abstract void actionAbstractButtonRemover(ActionEvent event);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.abstractInitialize();
    }

    @FXML
    private void actionButtonVoltar(ActionEvent event) {
        this.actionAbstractButtonVoltar(event);
    }

    @FXML
    private void actionButtonLogout(ActionEvent event) {
        this.actionAbstractButtonLogout(event);
    }

    @FXML
    private void actionButtonNovo(ActionEvent event) {
        this.actionAbstractButtonNovo(event);
    }

    @FXML
    private void actionButtonAtualizar(ActionEvent event) {
        this.actionAbstractButtonAtualizar(event);
    }

    @FXML
    private void actionButtonRemover(ActionEvent event) {
        this.actionAbstractButtonRemover(event);
    }

    public MudaCena getCena() {
        return cena;
    }

    public void setCena(MudaCena cena) {
        this.cena = cena;
    }

    public SingletonLogin getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(SingletonLogin usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getCadastrarScene() {
        return cadastrarScene;
    }

    public void setCadastrarScene(String cadastrarScene) {
        this.cadastrarScene = cadastrarScene;
    }

    public HBox gethBoxBottom() {
        return hBoxBottom;
    }

    public void sethBoxBottom(HBox hBoxBottom) {
        this.hBoxBottom = hBoxBottom;
    }

    public Button getButtonVoltar() {
        return buttonVoltar;
    }

    public void setButtonVoltar(Button buttonVoltar) {
        this.buttonVoltar = buttonVoltar;
    }

    public Label getLabelNomeTela() {
        return labelNomeTela;
    }

    public void setLabelNomeTela(Label labelNomeTela) {
        this.labelNomeTela = labelNomeTela;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }

    public void setButtonLogout(Button buttonLogout) {
        this.buttonLogout = buttonLogout;
    }

    public Button getButtonNovo() {
        return buttonNovo;
    }

    public void setButtonNovo(Button buttonNovo) {
        this.buttonNovo = buttonNovo;
    }

    public Button getButtonAtualizar() {
        return buttonAtualizar;
    }

    public void setButtonAtualizar(Button buttonAtualizar) {
        this.buttonAtualizar = buttonAtualizar;
    }

    public Button getButtonRemover() {
        return buttonRemover;
    }

    public void setButtonRemover(Button buttonRemover) {
        this.buttonRemover = buttonRemover;
    }

    public Label getLabelPesquisar() {
        return labelPesquisar;
    }

    public void setLabelPesquisar(Label labelPesquisar) {
        this.labelPesquisar = labelPesquisar;
    }

    public TextField getTextFieldPesquisar() {
        return textFieldPesquisar;
    }

    public void setTextFieldPesquisar(TextField textFieldPesquisar) {
        this.textFieldPesquisar = textFieldPesquisar;
    }

    public Button getButtonBuscar() {
        return buttonBuscar;
    }

    public void setButtonBuscar(Button buttonBuscar) {
        this.buttonBuscar = buttonBuscar;
    }

    public TableView<?> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<?> tableView) {
        this.tableView = tableView;
    }

}
