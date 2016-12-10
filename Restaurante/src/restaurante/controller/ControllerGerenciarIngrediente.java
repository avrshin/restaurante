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
import restaurante.model.domain.VOIngrediente;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class ControllerGerenciarIngrediente extends AbstractControllerGerenciar {

    private Button buttonReporIngrediente = new Button("Repor Ingrediente");
    private Context context = new Context(new StrategyIngrediente());
    private ButtonHandler handler = new ButtonHandler('i');

    public ControllerGerenciarIngrediente(String cadastrarScene) {
        super(cadastrarScene);
    }
    
    @Override
    public void abstractInitialize() {
        super.setUsuarioLogado(SingletonLogin.getInstance());
        super.getLabelNomeTela().setText("Gerenciar Ingrediente");
        super.gethBoxBottom().getChildren().add(buttonReporIngrediente);
        this.buttonReporIngrediente.setOnAction(handler); // i = ingrediente
        this.context.executeStrategy(super.getTableView());
    }

    @Override
    public void actionAbstractButtonAtualizar(ActionEvent event) {
        VOIngrediente vo = null;
        if (((TableView<VOIngrediente>) super.getTableView()).getSelectionModel().
                getSelectedIndex() < 0) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione um Ingrediente!");
            alert.setTitle(null);
            alert.setHeaderText(null);

            alert.showAndWait();
            return;
        } else {
            vo = ((TableView<VOIngrediente>) super.getTableView()).getSelectionModel()
                    .getSelectedItem();
        }

        try {
            ControllerCadastrarAtualizarIngrediente controller = (ControllerCadastrarAtualizarIngrediente) 
                    super.getCena().changeScene(null,
                    "/restaurante/view/FXMLCadastrarAtualizarIngrediente.fxml", event);
            controller.setAtualizarIngrediente('s');
            controller.setIngrediente(vo);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionAbstractButtonAtualizar" + ex.getMessage());
        }
    }

    @Override
    public void actionAbstractButtonRemover(ActionEvent event) {
        Facade pers = null;
        for (VOIngrediente ing : ((TableView<VOIngrediente>) super.getTableView()).
                getSelectionModel().getSelectedItems()) {
            pers = new Facade();
            pers.removerIngrediente(ing.getIdIngrediente());
            ((TableView<VOIngrediente>) super.getTableView()).getItems().remove(ing);
        }

        Alert alert = new Alert(AlertType.INFORMATION, "Item(s) removido(s) com sucesso!");
        alert.setTitle(null);
        alert.setHeaderText(null);

        alert.showAndWait();
    }

    
}
