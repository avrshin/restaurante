/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import java.time.LocalDate;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import restaurante.model.domain.VOFuncionario;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class StrategyFuncionario implements Strategy {

    @Override
    public void popularTabela(TableView<?> tableView) {
        TableColumn<VOFuncionario, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        TableColumn<VOFuncionario, String> cpfColumn = new TableColumn<>("CPF");
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        
        TableColumn<VOFuncionario, LocalDate> dataNascimentoColumn = new TableColumn<>("Data de Nascimento");
        dataNascimentoColumn.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        
        TableColumn<VOFuncionario, String> telefoneColumn = new TableColumn<>("Telefone");
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        
        TableColumn<VOFuncionario, Double> salarioColumn = new TableColumn<>("Salario");
        salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));
        
        TableColumn<VOFuncionario, String> cargoColumn = new TableColumn<>("Cargo");
        cargoColumn.setCellValueFactory(new PropertyValueFactory<>("cargo"));

        ((TableView<VOFuncionario>) tableView).setItems(new Facade().listarFuncionario());

        tableView.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY); // elimina colunas nao usadas

        ((TableView<VOFuncionario>) tableView).getColumns().addAll(nomeColumn, cpfColumn, 
                dataNascimentoColumn, telefoneColumn, salarioColumn, cargoColumn);
    }
    
}
