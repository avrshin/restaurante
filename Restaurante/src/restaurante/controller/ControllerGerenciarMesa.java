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
import restaurante.model.domain.VOMesa;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class ControllerGerenciarMesa extends AbstractControllerGerenciar {

    private Context context = new Context(new StrategyMesa());
    
    public ControllerGerenciarMesa(String cadastrarScene) {
        super(cadastrarScene);
    }

    @Override
    public void abstractInitialize() {
        super.setUsuarioLogado(SingletonLogin.getInstance());
        super.getLabelNomeTela().setText("Gerenciar Mesa");
        this.context.executeStrategy(super.getTableView());
    }

    @Override
    public void actionAbstractButtonAtualizar(ActionEvent event) {
        VOMesa vo = null;
        if (((TableView<VOMesa>) super.getTableView()).getSelectionModel().
                getSelectedIndex() < 0) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione uma Mesa!");
            alert.setTitle(null);
            alert.setHeaderText(null);

            alert.showAndWait();
            return;
        } else {
            vo = ((TableView<VOMesa>) super.getTableView()).getSelectionModel()
                    .getSelectedItem();
        }

        try {
            ControllerCadastrarAtualizarMesa controller = (ControllerCadastrarAtualizarMesa) 
                    super.getCena().changeScene(null,
                    "/restaurante/view/FXMLCadastrarAtualizarMesa.fxml", event);
            controller.setAtualizar("s");
            controller.setVOMesa(vo);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionAbstractButtonAtualizar" + ex.getMessage());
        }
    }

    @Override
    public void actionAbstractButtonRemover(ActionEvent event) {
        Facade persMesa = null;
        for (VOMesa mesa : ((TableView<VOMesa>) super.getTableView())
                .getSelectionModel().getSelectedItems()) {
            persMesa = new Facade();
            persMesa.removerMesa(mesa.getIdMesa());
            ((TableView<VOMesa>) super.getTableView()).getItems().remove(mesa);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item(s) removido(s) com sucesso!");
        alert.setTitle(null);
        alert.setHeaderText(null);

        alert.showAndWait();
    }
    
}
