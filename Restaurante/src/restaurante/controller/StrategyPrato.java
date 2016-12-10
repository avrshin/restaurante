/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import restaurante.model.domain.VOPrato;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class StrategyPrato implements Strategy {

    @Override
    public void popularTabela(TableView<?> tableView) {
        TableColumn<VOPrato, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<VOPrato, Double> precoVendaColumn = new TableColumn<>("Preco Venda (R$)");
        precoVendaColumn.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));

        ((TableView<VOPrato>) tableView).setItems(
                new Facade().listarPrato());

        tableView.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY); // elimina colunas nao usadas

        ((TableView<VOPrato>) tableView).getColumns().addAll(
                nomeColumn, precoVendaColumn);
    }

}
