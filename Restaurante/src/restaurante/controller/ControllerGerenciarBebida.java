/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import restaurante.model.domain.VOBebida;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerGerenciarBebida extends AbstractControllerGerenciar {
    
    private Button buttonReporBebida = new Button("Repor Bebida");
    private Context context = new Context(new StrategyGerenciarBebida());
    private ButtonHandler handler = new ButtonHandler('b');

    public ControllerGerenciarBebida(String cadastrarScene) {
        super(cadastrarScene);
    }
    
    @Override
    public void abstractInitialize() {
        super.setUsuarioLogado(SingletonLogin.getInstance());
        super.getLabelNomeTela().setText("Gerenciar Bebida");
        super.gethBoxBottom().getChildren().add(buttonReporBebida);
        this.buttonReporBebida.setOnAction(handler); // b = bebida
        this.context.executeStrategy(super.getTableView());
    }

    @Override
    public void actionAbstractButtonAtualizar(ActionEvent event) {
        VOBebida vo = null;
        if (((TableView<VOBebida>) super.getTableView()).getSelectionModel().
                getSelectedIndex() < 0) {
            
            Alert alert = new Alert(AlertType.INFORMATION, "Selecione uma Bebida!");
            alert.setTitle(null);
            alert.setHeaderText(null);

            alert.showAndWait();
            return;
        } else {
            vo = ((TableView<VOBebida>) super.getTableView()).getSelectionModel().
                    getSelectedItem();
        }

        try {
            ControllerCadastrarAtualizarBebida controller = (ControllerCadastrarAtualizarBebida) 
                    super.getCena().changeScene(null,
                    "/restaurante/view/FXMLCadastrarAtualizarBebida.fxml", event);
            controller.setAtualizar('s');
            controller.setBebida(vo);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionAbstractButtonAtualizar" + ex.getMessage());
        }
    }

    @Override
    public void actionAbstractButtonRemover(ActionEvent event) {
        Facade pers = null;
        for (VOBebida bebida : ((TableView<VOBebida>) super.getTableView()).
                getSelectionModel().getSelectedItems()) {
            pers = new Facade();
            pers.removerBebida(bebida.getIdProduto());
            ((TableView<VOBebida>) super.getTableView()).getItems().remove(bebida);
        }

        Alert alert = new Alert(AlertType.INFORMATION, "Item(s) removido(s) com sucesso!");
        alert.setTitle(null);
        alert.setHeaderText(null);

        alert.showAndWait();
    }
}
