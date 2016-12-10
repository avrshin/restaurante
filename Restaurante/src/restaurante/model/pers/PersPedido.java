/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.model.pers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;
import restaurante.model.database.DataBase;
import restaurante.model.database.DataBaseFactory;
import restaurante.model.domain.VOPedido;
import restaurante.model.domain.VOProduto;
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class PersPedido {
    
    private Connection connection;
    private VOUsuario vOUsuario;
    private DataBase db = null;
    private VOPedido vOPedido = null;

    public PersPedido() {
        this.db = new DataBaseFactory().getDataBase("postgresql");
        this.connection = db.conectar();
    }

    public PersPedido(VOUsuario vOUsuario) {
        this();
        this.vOUsuario = vOUsuario;
    }

    public PersPedido(VOUsuario vOUsuario, VOPedido vOPedido) {
        this(vOUsuario);
        this.vOPedido = vOPedido;
    }

    public int novoPedido() {
        if (this.vOPedido == null) {
            System.out.println("VOPedido nao setado, novoPedido, PersPedido");
            return -1;
        }
        int idr = -1;
        try {
            String sql = "insert into pedido(hora, data, status_pedido, id_mesa)"
                    + " values(?, ?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id inserido
            stmt.setTime(1, Time.valueOf(this.vOPedido.getHora()));
            stmt.setDate(2, Date.valueOf(this.vOPedido.getData()));
            stmt.setString(3, String.valueOf(this.vOPedido.getStatus()));
            stmt.setInt(4, this.vOPedido.getIdMesa());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idr = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersPedido novoPedido: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idr;
    }
    
    public int novoUsuarioPedido() {
        if (this.vOPedido == null) {
            System.out.println("VOPedido nao setado, novoUsuarioPedido, PersPedido");
            return -1;
        }
        int idr = -1;
        try {
            String sql = "insert into pedido_usuario(id_usuario, id_pedido)"
                    + " values(?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id inserido
            stmt.setInt(1, this.vOUsuario.getIdUsuario());
            stmt.setInt(2, this.vOPedido.getIdPedido());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idr = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersPedido novoUsuarioPedido: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idr;
    }
    
    public int novoProdutoPedido(VOProduto vOProduto) {
        if (this.vOPedido == null) {
            System.out.println("VOPedido nao setado, novoProdutoPedido, PersPedido");
            return -1;
        }
        int idr = -1;
        try {
            String sql = "insert into pedido_produto(id_produto, id_pedido)"
                    + " values(?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id inserido
            stmt.setInt(1, vOProduto.getIdProduto());
            stmt.setInt(2, this.vOPedido.getIdPedido());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idr = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersPedido novoProdutoPedido: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idr;
    }
    
    public boolean fecharPedido(int idMesa) {
        try {
            String sql = "update pedido set status_pedido = 'f'"
                    + " where id_mesa = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, idMesa);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersPedido fecharPedido: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }
}
