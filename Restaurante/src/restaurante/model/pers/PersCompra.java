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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;
import restaurante.model.database.DataBase;
import restaurante.model.database.DataBaseFactory;
import restaurante.model.domain.VOCompra;
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class PersCompra implements Operacao {

    private Connection connection = null;
    private VOUsuario vOUsuario = null;
    private DataBase db = null;
    private VOCompra vOCompra = null;

    public PersCompra() {
        this.db = new DataBaseFactory().getDataBase("postgresql");
        this.connection = db.conectar();
    }
    
    public PersCompra(VOUsuario vOUsuario) {
        this();
        this.vOUsuario = vOUsuario;
    }

    public PersCompra(VOUsuario vOUsuario, VOCompra vOCompra) {
        this(vOUsuario);
        this.vOCompra = vOCompra;
    }
    

    @Override
    public int inserir() {
        if (this.vOCompra == null) {
            System.out.println("Compra nao setado, inserir, PersCompra");
            return -1;
        }
        int idr = -1;
        try {
            String sql = "insert into compra(data, preco_compra, quantidade, "
                    + "id_compra_bebida, id_compra_ingrediente)"
                    + " values(?, ?, ?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id do salao inserido
            stmt.setDate(1, Date.valueOf(this.vOCompra.getData()));
            stmt.setDouble(2, this.vOCompra.getPrecoCompra());
            stmt.setInt(3, this.vOCompra.getQuantidade());
            
            if(this.vOCompra.getIdBebida() == -1) {
                stmt.setObject(4, null);
                stmt.setInt(5, this.vOCompra.getIdIngrediente());
            } else if(this.vOCompra.getIdIngrediente() == -1) {
                stmt.setInt(4, this.vOCompra.getIdBebida());
                stmt.setObject(5, null);
            }
            
            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idr = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersCompra inserir: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idr;
    }

    @Override
    public boolean alterar() {
        try {
            String sql = "update compra set data = ?, preco_compra = ?, quantidade = ?,"
                    + " id_compra_bebida = ?, id_compra_ingrediente = ?"
                    + " where id_compra = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(this.vOCompra.getData()));
            stmt.setDouble(2, this.vOCompra.getPrecoCompra());
            stmt.setInt(3, this.vOCompra.getQuantidade());
            stmt.setInt(4, this.vOCompra.getIdBebida());
            stmt.setInt(5, this.vOCompra.getIdIngrediente());
            stmt.setInt(6, this.vOCompra.getIdCompra());
            
            stmt.execute();
            
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersCompra alterar: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public List listar() {
        return null;
    }

    @Override
    public boolean remover(int id) {
        try {
            String sql = "delete from compra where id_compra = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersCompra remover: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }
    
    public VOCompra buscarCompraPorID(int id) {
        VOCompra vo = null;
        try {
            String sql = "select * from compra where id_compra = ?";

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                vo = new VOCompra(-1, null, -1, -1, -1, -1);
                new Extracao().extractVOCompraUnico(rs, vo);
            }
            return vo;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersCompra buscarCompraPorID: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }
    
}
