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
import restaurante.model.domain.VOMesa;
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class PersMesa implements Operacao {

    private Connection connection;
    private VOUsuario vOUsuario;
    private DataBase db = null;
    private VOMesa vOMesa = null;

    public PersMesa() {
        this.db = new DataBaseFactory().getDataBase("postgresql");
        this.connection = db.conectar();
    }

    public PersMesa(VOUsuario vOUsuario) {
        this();
        this.vOUsuario = vOUsuario;
    }

    public PersMesa(VOUsuario vOUsuario, VOMesa vOMesa) {
        this(vOUsuario);
        this.vOMesa = vOMesa;
    }

    @Override
    public int inserir() {
        if (this.vOMesa == null) {
            System.out.println("VOMesa nao setado, inserir, PersMesa");
            return -1;
        }
        int idp = -1;
        try {
            String sql = "insert into mesa(num_cadeiras, numero, status_mesa, id_salao)"
                    + " values(?, ?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id inserido
            stmt.setInt(1, this.vOMesa.getNumCadeiras());
            stmt.setInt(2, this.vOMesa.getNumero());
            stmt.setString(3, this.vOMesa.getStatus());
            stmt.setInt(4, this.vOMesa.getIdSalao());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idp = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersMesa inserir: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idp;
    }

    @Override
    public boolean alterar() {
        try {
            String sql = "update mesa set num_cadeiras = ?, numero = ?, "
                    + "status_mesa = ?, id_salao = ? where id_mesa = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, this.vOMesa.getNumCadeiras());
            stmt.setInt(2, this.vOMesa.getNumero());
            stmt.setString(3, this.vOMesa.getStatus());
            stmt.setInt(4, this.vOMesa.getIdSalao());
            stmt.setInt(5, this.vOMesa.getIdMesa());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersMesa alterar: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }
    
    // muda o status da mesa
    public boolean alocarMesa() {
        try {
            String sql = "update mesa set status_mesa = 'o' where id_mesa = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, this.vOMesa.getIdMesa());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersMesa alocarMesa: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }
    
    // muda o status da mesa
    public boolean desalocarMesa() {
        try {
            String sql = "update mesa set status_mesa = 'v' where id_mesa = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, this.vOMesa.getIdMesa());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersMesa desalocarMesa: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public ObservableList<VOMesa> listar() {
        try {
            String sql = "select * from mesa order by numero";

            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOMesa> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOMesa vo = new VOMesa();
            while (rs.next()) {
                list.add(extracao.extractVOMesa(rs, vo.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersMesa listar: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }
    
    public ObservableList<VOMesa> listarOcupadas() {
        try {
            String sql = "select * from mesa where status_mesa = 'o'";

            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOMesa> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOMesa vo = new VOMesa();
            while (rs.next()) {
                list.add(extracao.extractVOMesa(rs, vo.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersMesa listarDisponiveis: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public boolean remover(int id) {
        try {
            String sql = "delete from mesa where id_mesa = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersMesa remover: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }
}
