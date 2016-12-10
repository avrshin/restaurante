/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import restaurante.model.domain.VOUsuario;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class StrategyUsuario implements Strategy {

    @Override
    public void popularTabela(TableView<?> tableView) {
        TableColumn<VOUsuario, String> loginColumn = new TableColumn<>("Login");
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<VOUsuario, String> tipoUsuarioColumn = new TableColumn<>("Tipo de Usuario");
        tipoUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));

        ((TableView<VOUsuario>) tableView).setItems(new Facade().listarUsuario());

        tableView.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY); // elimina colunas nao usadas

        ((TableView<VOUsuario>) tableView).getColumns().addAll(loginColumn, tipoUsuarioColumn);
    }
    
}
