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
import restaurante.model.domain.VOPrato;
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class PersPrato implements Operacao {

    private Connection connection = null;
    private VOUsuario vOUsuario = null;
    private DataBase db = null;
    private VOPrato vOPrato = null;

    public PersPrato() {
        this.db = new DataBaseFactory().getDataBase("postgresql");
        this.connection = db.conectar();
    }

    public PersPrato(VOUsuario vOUsuario) {
        this();
        this.vOUsuario = vOUsuario;
    }

    public PersPrato(VOUsuario vOUsuario, VOPrato vOPrato) {
        this(vOUsuario);
        this.vOPrato = vOPrato;
    }

    @Override
    public int inserir() {
        if (this.vOPrato == null) {
            System.out.println("VOPrato nao setado, inserir, PersPrato");
            return -1;
        }
        int idr = -1;
        try {
            String sql = "insert into prato(id_produto)"
                    + " values(?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id inserido
            stmt.setInt(1, this.vOPrato.getIdProduto());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idr = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersPrato inserir: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idr;
    }

    public int inserirIngredientePrato(VOIngrediente ingrediente) {
        if (ingrediente == null) {
            return -1;
        }
        int idr = -1;
        try {
            String sql = "insert into prato_ingrediente(id_prato, id_ingrediente)"
                    + " values(?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id inserido
            stmt.setInt(1, this.vOPrato.getIdPrato());
            stmt.setInt(2, ingrediente.getIdIngrediente());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idr = id.getInt(1);
            }
            return idr;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersPrato inserirIngredientePrato: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public boolean alterar() {
        return false;
    }

    public boolean removerIngredientes(int idPrato) {
        try {
            String sql = "delete from prato_ingrediente where id_prato = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, idPrato);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersProduto removerIngredientes: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public ObservableList<VOPrato> listar() {
        try {
            String sql = "select * from prato, produto"
                    + " where prato.id_produto = produto.id_produto";
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOPrato> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOPrato prato = new VOPrato();
            while (rs.next()) {
                list.add(extracao.extractVOPratoVOProduto(rs, prato.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersPrato listar: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public boolean remover(int id) {
        return new PersProduto().remover(id);
    }
    
    public VOPrato listarUnico(int id) {
        try {
            String sql = "select * from prato, produto"
                    + " where prato.id_produto = produto.id_produto"
                    + " and prato.id_produto = ?";

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            
            Extracao extracao = new Extracao();
            VOPrato prato = new VOPrato();
            if (rs.next()) {
                extracao.extractVOPratoVOProduto(rs, prato);
            }
            return prato;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersPrato listarUnico: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }
}
