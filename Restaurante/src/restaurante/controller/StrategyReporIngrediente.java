/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import restaurante.model.domain.VOIngrediente;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class StrategyReporIngrediente implements Strategy {

    @Override
    public void popularTabela(TableView<?> tableView) {
        TableColumn<VOIngrediente, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        ((TableView<VOIngrediente>) tableView).setItems(
                new Facade().listarIngrediente());

        tableView.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY); // elimina colunas nao usadas

        ((TableView<VOIngrediente>) tableView).getColumns().addAll(nomeColumn);
    }
    
}
