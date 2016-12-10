/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import javafx.scene.control.TableView;

/**
 *
 * @author luis
 */
public interface Strategy {
    
    public void popularTabela(TableView<?> tableView);
    
}
