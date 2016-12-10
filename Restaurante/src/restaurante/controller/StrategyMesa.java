/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import restaurante.model.domain.VOMesa;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class StrategyMesa implements Strategy {

    @Override
    public void popularTabela(TableView<?> tableView) {
        TableColumn<VOMesa, Integer> cadeirasColumn = new TableColumn<>("Cadeiras");
        cadeirasColumn.setCellValueFactory(new PropertyValueFactory<>("numCadeiras"));

        TableColumn<VOMesa, Integer> numeroColumn = new TableColumn<>("Numero");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        
        TableColumn<VOMesa, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        ((TableView<VOMesa>) tableView).setItems(new Facade().listarMesa());

        tableView.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY); // elimina colunas nao usadas

        ((TableView<VOMesa>) tableView).getColumns().addAll(
                cadeirasColumn, numeroColumn, statusColumn);
    }
    
}
