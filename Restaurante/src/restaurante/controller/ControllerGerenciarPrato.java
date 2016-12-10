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
import javafx.scene.control.TableView;
import restaurante.model.domain.VOPrato;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class ControllerGerenciarPrato extends AbstractControllerGerenciar {

    private Context context = new Context(new StrategyPrato());
    
    public ControllerGerenciarPrato(String cadastrarScene) {
        super(cadastrarScene);
    }

    @Override
    public void abstractInitialize() {
        super.setUsuarioLogado(SingletonLogin.getInstance());
        super.getLabelNomeTela().setText("Gerenciar Prato");
        this.context.executeStrategy(super.getTableView());
    }

    @Override
    public void actionAbstractButtonAtualizar(ActionEvent event) {
        VOPrato vo = null;
        if (((TableView<VOPrato>) super.getTableView()).getSelectionModel().
                getSelectedIndex() < 0) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione um Prato!");
            alert.setTitle(null);
            alert.setHeaderText(null);

            alert.showAndWait();
            return;
        } else {
            vo = ((TableView<VOPrato>) super.getTableView()).getSelectionModel().
                    getSelectedItem();
        }
        try {
            ControllerCadastrarAtualizarPrato controller = (ControllerCadastrarAtualizarPrato) 
                    super.getCena().changeScene(null,
                    "/restaurante/view/FXMLCadastrarAtualizarPrato.fxml", event);
            controller.setAtualizar('s');
            controller.setVOPrato(vo);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionAbstractButtonAtualizar" + ex.getMessage());
        }
    }

    @Override
    public void actionAbstractButtonRemover(ActionEvent event) {
        Facade pers = null;
        for (VOPrato prato : ((TableView<VOPrato>) super.getTableView())
                .getSelectionModel().getSelectedItems()) {
            pers = new Facade();
            pers.removerPrato(prato.getIdProduto());
            ((TableView<VOPrato>) super.getTableView()).getItems().remove(prato);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item(s) removido(s) com sucesso!");
        alert.setTitle(null);
        alert.setHeaderText(null);

        alert.showAndWait();
    }
    
}
