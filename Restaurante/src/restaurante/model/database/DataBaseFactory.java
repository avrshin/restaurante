/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.model.database;

/**
 *
 * @author luis
 */
public class DataBaseFactory {
    
    public DataBase getDataBase(String dataBase) {
        if(dataBase.equalsIgnoreCase("postgresql")) {
            return DataBasePostgreSQL.getInstance();
        }
        return null;
    }
    
}
