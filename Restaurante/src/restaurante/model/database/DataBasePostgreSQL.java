/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;

/**
 *
 * @author luis
 */
public class DataBasePostgreSQL implements DataBase {

    private static String host, port, database, username, password, url;
    private static Connection connection;
    private static DataBasePostgreSQL instance = null;

    private DataBasePostgreSQL() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro: Classe não carregada: org.postgresql.Driver, "
                    + "talvez o pacote postgresql-9.4-1211.jre6.jar não esteja no projeto!");
        }
        host = "localhost";
        port = "5432";
        database = "restaurante";
        url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        username = "postgres";
        password = "postgres";
    }
    
    public static DataBasePostgreSQL getInstance() {
        if(instance == null) {
            instance = new DataBasePostgreSQL();
        }
        return instance;
    }

    @Override
    public Connection conectar() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            return this.connection;
        } catch (SQLException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro ao conectar com o Banco de Dados PostgreSQL!");
            return null;
        }
    }

    @Override
    public void desconectar(Connection conn) {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro ao desconectar com o Banco de Dados PostgreSQL!!");
        }
    }

}
