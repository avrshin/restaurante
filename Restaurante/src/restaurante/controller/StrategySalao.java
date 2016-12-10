/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import restaurante.model.domain.VOSalao;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class StrategySalao implements Strategy {

    @Override
    public void popularTabela(TableView<?> tableView) {
        TableColumn<VOSalao, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<VOSalao, Integer> numMesasColumn = new TableColumn<>("Numero de Mesas");
        numMesasColumn.setCellValueFactory(new PropertyValueFactory<>("numMesas"));

        ((TableView<VOSalao>) tableView).setItems(new Facade().listarSalao());

        tableView.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY); // elimina colunas nao usadas

        ((TableView<VOSalao>) tableView).getColumns().addAll(nomeColumn, numMesasColumn);
    }
    
}
