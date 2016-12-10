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
import restaurante.model.domain.VOProduto;
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class PersProduto implements Operacao {

    private Connection connection = null;
    private VOUsuario vOUsuario = null;
    private DataBase db = null;
    private VOProduto vOProduto = null;

    public PersProduto() {
        this.db = new DataBaseFactory().getDataBase("postgresql");
        this.connection = db.conectar();
    }

    public PersProduto(VOUsuario vOUsuario) {
        this();
        this.vOUsuario = vOUsuario;
    }

    public PersProduto(VOUsuario vOUsuario, VOProduto vOProduto) {
        this(vOUsuario);
        this.vOProduto = vOProduto;
    }

    @Override
    public int inserir() {
        if (this.vOProduto == null) {
            System.out.println("Produto nao setado, inserir, PersProduto");
            return -1;
        }
        int idp = -1;
        try {
            String sql = "insert into produto(nome, tipo, preco_venda)"
                    + " values(?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id inserido
            stmt.setString(1, this.vOProduto.getNome());
            stmt.setInt(2, this.vOProduto.getTipo());
            stmt.setDouble(3, this.vOProduto.getPrecoVenda());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idp = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersProduto inserir: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idp;
    }

    @Override
    public boolean alterar() {
        try {
            String sql = "update produto set nome = ?, tipo = ?, preco_venda = ?"
                    + " where id_produto = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, this.vOProduto.getNome());
            stmt.setInt(2, this.vOProduto.getTipo());
            stmt.setDouble(3, this.vOProduto.getPrecoVenda());
            stmt.setInt(4, this.vOProduto.getIdProduto());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersProduto alterar: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public ObservableList<VOProduto> listar() {
        try {
            String sql = "select * from produto";

            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOProduto> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOProduto ing = new VOProduto();
            while (rs.next()) {
                list.add(extracao.extractVOProduto(rs, ing.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersProduto listar: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

    public ObservableList<VOProduto> listarOrdenado() {
        try {
            String sql = "select * from produto order by tipo";

            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOProduto> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOProduto ing = new VOProduto();
            while (rs.next()) {
                list.add(extracao.extractVOProduto(rs, ing.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersProduto listarOrdenado: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

    public ObservableList<VOProduto> listarPorMesa(int idMesa) {
        try {
            String sql = "select * from produto as p, pedido as pe, pedido_produto as pp"
                    + " where pe.id_mesa = ? "
                    + " and pp.id_pedido = pe.id_pedido"
                    + " and p.id_produto = pp.id_produto"
                    + " and pe.status_pedido = 'a'";

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, idMesa);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOProduto> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOProduto ing = new VOProduto();
            while (rs.next()) {
                list.add(extracao.extractVOProduto(rs, ing.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersProduto listarPorMesa: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

    public VOProduto listarPorID(int idProduto) {
        try {
            String sql = "select * from produto where id_produto = ?";

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, idProduto);

            ResultSet rs = stmt.executeQuery();
            Extracao extracao = new Extracao();
            VOProduto produto = null;
            if (rs.next()) {
                produto = extracao.extractVOProdutoUnico(rs);
            }
            return produto;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersIngrediente listarPorID: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public boolean remover(int id) {
        try {
            String sql = "delete from produto where id_produto = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersProduto remover: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

}
