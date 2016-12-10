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
import restaurante.model.domain.VOUsuario;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class ControllerGerenciarUsuario extends AbstractControllerGerenciar {

    private Context context = new Context(new StrategyUsuario());
    
    public ControllerGerenciarUsuario(String cadastrarScene) {
        super(cadastrarScene);
    }

    @Override
    public void abstractInitialize() {
        super.setUsuarioLogado(SingletonLogin.getInstance());
        super.getLabelNomeTela().setText("Gerenciar Usuario");
        this.context.executeStrategy(super.getTableView());
    }

    @Override
    public void actionAbstractButtonAtualizar(ActionEvent event) {
        VOUsuario vo = null;
        if (((TableView<VOUsuario>) super.getTableView()).getSelectionModel().
                getSelectedIndex() < 0) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione um Usuario!");
            alert.setTitle(null);
            alert.setHeaderText(null);

            alert.showAndWait();
            return;
        } else {
            vo = ((TableView<VOUsuario>) super.getTableView()).getSelectionModel()
                    .getSelectedItem();
        }
        
        try {
            ControllerCadastrarAtualizarUsuario controller = (ControllerCadastrarAtualizarUsuario) 
                    super.getCena().changeScene(null,
                    "/restaurante/view/FXMLCadastrarAtualizarUsuario.fxml", event);
            controller.setAtualizar('s');
            controller.setvOUsuario(vo);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionAbstractButtonAtualizar" + ex.getMessage());
        }
    }

    @Override
    public void actionAbstractButtonRemover(ActionEvent event) {
        Facade pers = null;
        for (VOUsuario user : ((TableView<VOUsuario>) super.getTableView())
                .getSelectionModel().getSelectedItems()) {
            pers = new Facade();
            pers.removerUsuario(user.getIdUsuario());
            ((TableView<VOUsuario>) super.getTableView()).getItems().remove(user);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item(s) removido(s) com sucesso!");
        alert.setTitle(null);
        alert.setHeaderText(null);

        alert.showAndWait();
    }
    
}
