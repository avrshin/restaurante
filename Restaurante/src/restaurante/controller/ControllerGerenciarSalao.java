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
import javafx.scene.control.TableView;

import restaurante.model.domain.VOSalao;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class ControllerGerenciarSalao extends AbstractControllerGerenciar {

    private Context context = new Context(new StrategySalao());
    
    public ControllerGerenciarSalao(String cadastrarScene) {
        super(cadastrarScene);
    }

    @Override
    public void abstractInitialize() {
        super.setUsuarioLogado(SingletonLogin.getInstance());
        super.getLabelNomeTela().setText("Gerenciar Salao");
        this.context.executeStrategy(super.getTableView());
    }

    @Override
    public void actionAbstractButtonAtualizar(ActionEvent event) {
        VOSalao vo = null;
        if (((TableView<VOSalao>) super.getTableView()).getSelectionModel()
                .getSelectedIndex() < 0) {
            
            Alert alert = new Alert(AlertType.INFORMATION, "Selecione um Salao!");
            alert.setTitle(null);
            alert.setHeaderText(null);

            alert.showAndWait();
            return;
        } else {
            vo = ((TableView<VOSalao>) super.getTableView()).getSelectionModel()
                    .getSelectedItem();
        }

        try {
            ControllerCadastrarAtualizarSalao controller = (ControllerCadastrarAtualizarSalao) 
                    super.getCena().changeScene(null,
                    "/restaurante/view/FXMLCadastrarAtualizarSalao.fxml", event);
            controller.setAtualizar('s');
            controller.setCamposTela(vo);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionAbstractButtonAtualizar" + ex.getMessage());
        }
    }

    @Override
    public void actionAbstractButtonRemover(ActionEvent event) {
        Facade persSalao = null;
        for (VOSalao salao : ((TableView<VOSalao>) super.getTableView()).
                getSelectionModel().getSelectedItems()) {
            persSalao = new Facade();
            persSalao.removerSalao(salao.getIdSalao());
            ((TableView<VOSalao>) super.getTableView()).getItems().remove(salao);
        }

        Alert alert = new Alert(AlertType.INFORMATION, "Item(s) removido(s) com sucesso!");
        alert.setTitle(null);
        alert.setHeaderText(null);

        alert.showAndWait();
    }
}
