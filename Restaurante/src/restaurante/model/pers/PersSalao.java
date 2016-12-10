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
import restaurante.model.domain.VOSalao;
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class PersSalao implements Operacao {

    private Connection connection = null;
    private VOUsuario vOUsuario = null;
    private DataBase db = null;
    private VOSalao vOSalao = null;

    public PersSalao() {
        this.db = new DataBaseFactory().getDataBase("postgresql");
        this.connection = db.conectar();
    }
    
    public PersSalao(VOUsuario vOUsuario) {
        this();
        this.vOUsuario = vOUsuario;
    }

    public PersSalao(VOUsuario vOUsuario, VOSalao vOSalao) {
        this(vOUsuario);
        this.vOSalao = vOSalao;
    }

    @Override
    public int inserir() {
        if (this.vOSalao == null) {
            System.out.println("Salao nao setado, inserir, PersSalao");
            return -1;
        }
        int idr = -1;
        try {
            String sql = "insert into salao(num_mesas, nome)"
                    + " values(?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id do salao inserido
            stmt.setInt(1, this.vOSalao.getNumMesas());
            stmt.setString(2, this.vOSalao.getNome());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idr = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersSalao inserir: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idr;
    }

    @Override
    public boolean alterar() {
        try {
            String sql = "update salao set num_mesas = ?, nome = ?"
                    + " where id_salao = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, this.vOSalao.getNumMesas());
            stmt.setString(2, this.vOSalao.getNome());
            stmt.setInt(3, this.vOSalao.getIdSalao());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersSalao alterar: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public ObservableList<VOSalao> listar() {
        try {
            String sql = "select * from salao";

            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOSalao> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOSalao vo = new VOSalao();
            while (rs.next()) {
                list.add(extracao.extractVOSalao(rs, vo.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersSalao listar: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public boolean remover(int id) {
        try {
            String sql = "delete from salao where id_salao = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersSalao remover: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

}
