/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.model.pers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import restaurante.controller.AbstractControllerGerenciar;
import restaurante.model.database.DataBase;
import restaurante.model.database.DataBaseFactory;
import restaurante.model.domain.VOIngrediente;
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class PersIngrediente implements Operacao {

    private Connection connection = null;
    private VOUsuario vOUsuario = null;
    private DataBase db = null;
    private VOIngrediente vOIngrediente = null;

    public PersIngrediente() {
        this.db = new DataBaseFactory().getDataBase("postgresql");
        this.connection = db.conectar();
    }

    public PersIngrediente(VOUsuario vOUsuario) {
        this();
        this.vOUsuario = vOUsuario;
    }

    public PersIngrediente(VOUsuario vOUsuario, VOIngrediente vOIngrediente) {
        this(vOUsuario);
        this.vOIngrediente = vOIngrediente;
    }

    @Override
    public int inserir() {
        if (this.vOIngrediente == null) {
            System.out.println("Ingrediente nao setado, inserir, PersIngrediente");
            return -1;
        }
        int idr = -1;
        try {
            String sql = "insert into ingrediente(nome, quantidade, unidade_medida)"
                    + " values(?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id inserido
            stmt.setString(1, this.vOIngrediente.getNome());
            stmt.setInt(2, this.vOIngrediente.getQuantidade());
            stmt.setInt(3, this.vOIngrediente.getUnidadeMedida());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idr = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersIngrediente inserir: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idr;
    }

    @Override
    public boolean alterar() {
        try {
            String sql = "update ingrediente set nome = ?, quantidade = ?,"
                    + " unidade_medida = ? where id_ingrediente = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, this.vOIngrediente.getNome());
            stmt.setInt(2, this.vOIngrediente.getQuantidade());
            stmt.setInt(3, this.vOIngrediente.getUnidadeMedida());
            stmt.setInt(4, this.vOIngrediente.getIdIngrediente());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersIngrediente alterar: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }
    
    public boolean alteraQuantidade() {
        try {
            String sql = "update ingrediente set quantidade = ?"
                    + " where id_ingrediente = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, this.vOIngrediente.getQuantidade());
            stmt.setInt(2, this.vOIngrediente.getIdIngrediente());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersIngrediente alteraQuantidade: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public ObservableList<VOIngrediente> listar() {
        try {
            String sql = "select * from ingrediente";

            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOIngrediente> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOIngrediente ing = new VOIngrediente();
            while (rs.next()) {
                list.add(extracao.extractVOIngrediente(rs, ing.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersIngrediente listar: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }
    
    public ObservableList<VOIngrediente> listarIngredienteDeProduto(int idPrato) {
        try {
            String sql = "select ingrediente.id_ingrediente, nome, quantidade, unidade_medida"
                    + " from ingrediente, prato_ingrediente "
                    + " where ingrediente.id_ingrediente = prato_ingrediente.id_ingrediente"
                    + " and prato_ingrediente.id_prato = ?";

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, idPrato);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOIngrediente> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOIngrediente ing = new VOIngrediente();
            while (rs.next()) {
                list.add(extracao.extractVOIngrediente(rs, ing.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersIngrediente listarIngredienteDeProduto: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public boolean remover(int id) {
        try {
            String sql = "delete from ingrediente where id_ingrediente = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersIngrediente remover: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

}
