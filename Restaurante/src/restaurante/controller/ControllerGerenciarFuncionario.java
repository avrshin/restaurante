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
import restaurante.model.domain.VOFuncionario;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class ControllerGerenciarFuncionario extends AbstractControllerGerenciar {

    private Context context = new Context(new StrategyFuncionario());
    
    public ControllerGerenciarFuncionario(String cadastrarScene) {
        super(cadastrarScene);
    }

    @Override
    public void abstractInitialize() {
        super.setUsuarioLogado(SingletonLogin.getInstance());
        super.getLabelNomeTela().setText("Gerenciar Funcionário");
        this.context.executeStrategy(super.getTableView());
    }

    @Override
    public void actionAbstractButtonAtualizar(ActionEvent event) {
        VOFuncionario vo = null;
        if (((TableView<VOFuncionario>) super.getTableView()).getSelectionModel()
                .getSelectedIndex() < 0) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione um Funcionario!");
            alert.setTitle(null);
            alert.setHeaderText(null);

            alert.showAndWait();
            return;
        } else {
            vo = ((TableView<VOFuncionario>) super.getTableView()).getSelectionModel()
                    .getSelectedItem();
        }

        try {
            ControllerCadastrarAtualizarFuncionario controller = (ControllerCadastrarAtualizarFuncionario) 
                    super.getCena().changeScene(null,
                    "/restaurante/view/FXMLCadastrarAtualizarFuncionario.fxml", event);
            controller.setAtualizar('s');
            controller.setCamposTela(vo);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionAbstractButtonAtualizar" + ex.getMessage());
        }
    }

    @Override
    public void actionAbstractButtonRemover(ActionEvent event) {
        Facade pers = null;
        for (VOFuncionario vo : ((TableView<VOFuncionario>) super.getTableView()).
                getSelectionModel().getSelectedItems()) {
            pers = new Facade();
            pers.removerFuncionario(vo.getIdFuncionario());
            ((TableView<VOFuncionario>) super.getTableView()).getItems().remove(vo);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item(s) removido(s) com sucesso!");
        alert.setTitle(null);
        alert.setHeaderText(null);

        alert.showAndWait();
    }

    
}
