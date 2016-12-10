/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import restaurante.model.domain.VOBebida;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class StrategyGerenciarBebida implements Strategy {

    @Override
    public void popularTabela(TableView<?> tableView) {
        TableColumn<VOBebida, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        TableColumn<VOBebida,Double> precoColumn = new TableColumn<>("Preço de Venda (R$)");
        precoColumn.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));

        TableColumn<VOBebida, Integer> volumeColumn = new TableColumn<>("Tamanho (ml)");
        volumeColumn.setCellValueFactory(new PropertyValueFactory<>("tamanho"));
        
        TableColumn<VOBebida, Integer> quantidadeColumn = new TableColumn<>("Quantidade (unidade)");
        quantidadeColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        ((TableView<VOBebida>) tableView).setItems(
                new Facade().listarBebida());

        tableView.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY); // elimina colunas nao usadas

        ((TableView<VOBebida>) tableView).getColumns().addAll(
                nomeColumn, precoColumn, volumeColumn, quantidadeColumn);
    }
    
}
