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
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class PersUsuario implements Operacao {
    
    private Connection connection = null;
    private VOUsuario vOUsuarioLogado = null;
    private DataBase db = null;
    private VOUsuario vOUsuario = null;
    
    public PersUsuario() {
        this.db = new DataBaseFactory().getDataBase("postgresql");
        this.connection = db.conectar();
    }

    public PersUsuario(VOUsuario vOUsuarioLogado) {
        this();
        this.vOUsuarioLogado = vOUsuarioLogado;
    }

    public PersUsuario(VOUsuario vOUsuarioLogado, VOUsuario vOUsuario) {
        this(vOUsuarioLogado);
        this.vOUsuario = vOUsuario;
    }
    
    @Override
    public int inserir() {
        if (this.vOUsuario == null) {
            System.out.println("VOUsuario nao setado, inserir, PersUsuario");
            return -1;
        }
        int idr = -1;
        try {
            String sql = "insert into usuario(id_usuario, login, senha, tipo_usuario)"
                    + " values(?, ?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id do salao inserido
            stmt.setInt(1, this.vOUsuario.getIdUsuario());
            stmt.setString(2, this.vOUsuario.getLogin());
            stmt.setString(3, this.vOUsuario.getSenha());
            stmt.setString(4, this.vOUsuario.getTipoUsuario());

            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();

            if (id.next()) {
                idr = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersUsuario inserir: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idr;
    }

    @Override
    public boolean alterar() {
        try {
            String sql = "update usuario set login = ?, senha = ?, tipo_usuario = ?"
                    + " where id_usuario = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, this.vOUsuario.getLogin());
            stmt.setString(2, this.vOUsuario.getSenha());
            stmt.setString(3, this.vOUsuario.getTipoUsuario());
            stmt.setInt(4, this.vOUsuario.getIdUsuario());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersUsuario alterar: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public ObservableList<VOUsuario> listar() {
        try {
            String sql = "select * from usuario";

            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOUsuario> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOUsuario vo = new VOUsuario();
            while (rs.next()) {
                list.add(extracao.extractVOUsuario(rs, vo.clone()));
            }
            return list;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersUsuario listar: " + e.getMessage());
            return null;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public boolean remover(int id) {
        try {
            String sql = "delete from usuario where id_usuario = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersUsuario remover: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }
    
    public boolean autenticaUsuario() {
        boolean b = false;
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "select * from usuario where login = ? and senha = ?");
            ps.setString(1, this.vOUsuario.getLogin());
            ps.setString(2, this.vOUsuario.getSenha());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                new Extracao().extractVOUsuarioUnico(rs, this.vOUsuario);
                b = true;
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Erro: logar()\n" + e.getMessage());
        }
        return b;
    }
}
