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
import restaurante.model.domain.VOBebida;
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class PersBebida implements Operacao {

    private Connection connection = null;
    private VOUsuario vOUsuario = null;
    private DataBase db = null;
    private VOBebida vOBebida = null;

    public PersBebida() {
        this.db = new DataBaseFactory().getDataBase("postgresql");
        this.connection = db.conectar();
    }

    public PersBebida(VOUsuario vOUsuario) {
        this();
        this.vOUsuario = vOUsuario;
    }

    public PersBebida(VOUsuario vOUsuario, VOBebida vOBebida) {
        this(vOUsuario);
        this.vOBebida = vOBebida;
    }

    @Override
    public int inserir() {
        if (this.vOBebida == null) {
            System.out.println("Bebida nao setado, inserir, PersBebida");
            return -1;
        }
        int idr = -1;
        try {
            String sql = "insert into bebida(id_produto, tamanho, quantidade)"
                    + " values(?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id inserido
            stmt.setInt(1, this.vOBebida.getIdProduto());
            stmt.setDouble(2, this.vOBebida.getTamanho());
            stmt.setInt(3, this.vOBebida.getQuantidade());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idr = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersBebida inserir: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idr;
    }

    @Override
    public boolean alterar() {
        try {
            String sql = "update bebida set tamanho = ?, quantidade = ?,"
                    + " id_produto = ? where id_bebida = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, this.vOBebida.getTamanho());
            stmt.setInt(2, this.vOBebida.getQuantidade());
            stmt.setInt(3, this.vOBebida.getIdProduto());
            stmt.setInt(4, this.vOBebida.getIdBebida());
            stmt.execute();
            
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersBebida alterar: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public ObservableList<VOBebida> listar() {
        try {
            String sql = "select * from bebida, produto "
                    + "where bebida.id_produto = produto.id_produto";

            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOBebida> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOBebida bebida = new VOBebida();
            while (rs.next()) {
                list.add(extracao.extractVOBebidaVOProduto(rs, bebida.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersBebida listar: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public boolean remover(int id) {
        return new PersProduto().remover(id);
    }
    
    public boolean alteraQuantidade() {
        try {
            String sql = "update bebida set quantidade = ?"
                    + " where id_bebida = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, this.vOBebida.getQuantidade());
            stmt.setInt(2, this.vOBebida.getIdBebida());
            stmt.execute();
            
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersBebida diminuiBebida: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }
    
    public VOBebida listarUnico(int idProduto) {
        try {
            String sql = "select * from bebida, produto "
                    + " where bebida.id_produto = produto.id_produto"
                    + " and bebida.id_produto = ?";

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, idProduto);

            ResultSet rs = stmt.executeQuery();
            
            Extracao extracao = new Extracao();
            VOBebida bebida = new VOBebida();
            if (rs.next()) {
                bebida = extracao.extractVOBebidaVOProduto(rs, bebida);
            }
            return bebida;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersBebida listarUnico: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

}
