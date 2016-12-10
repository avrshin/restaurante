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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import restaurante.controller.AbstractControllerGerenciar;
import restaurante.model.database.DataBase;
import restaurante.model.database.DataBaseFactory;
import restaurante.model.domain.VOFuncionario;
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class PersFuncionario implements Operacao {
    
    private Connection connection = null;
    private VOUsuario vOUsuario = null;
    private DataBase db = null;
    private VOFuncionario vOFuncionario = null;

    public PersFuncionario() {
        this.db = new DataBaseFactory().getDataBase("postgresql");
        this.connection = db.conectar();
    }

    public PersFuncionario(VOUsuario vOUsuario) {
        this();
        this.vOUsuario = vOUsuario;
    }

    public PersFuncionario(VOUsuario vOUsuario, VOFuncionario vOFuncionario) {
        this(vOUsuario);
        this.vOFuncionario = vOFuncionario;
    }
    

    @Override
    public int inserir() {
        if (this.vOFuncionario == null) {
            System.out.println("Funcionario nao setado, inserir, PersFuncionario");
            return -1;
        }
        int idp = -1;
        try {
            String sql = "insert into funcionario(cpf, nome, data_nascimento, "
                    + "telefone, salario, cargo) values(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); // retorna o id inserido
            stmt.setString(1, this.vOFuncionario.getCpf());
            stmt.setString(2, this.vOFuncionario.getNome());
            stmt.setDate(3, Date.valueOf(this.vOFuncionario.getDataNascimento()));
            stmt.setString(4, this.vOFuncionario.getTelefone());
            stmt.setDouble(5, this.vOFuncionario.getSalario());
            stmt.setString(6, this.vOFuncionario.getCargo());
            
            stmt.executeUpdate();

            ResultSet id = stmt.getGeneratedKeys();
            
            if (id.next()) {
                idp = id.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersFuncionario inserir: " + e.getMessage());
            return -1;
        } finally {
            this.db.desconectar(connection);
        }
        return idp;
    }

    @Override
    public boolean alterar() {
        try {
            String sql = "update funcionario set cpf = ?, nome = ?, data_nascimento = ?,"
                    + " telefone = ?, salario = ?, cargo = ? where id_funcionario = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, this.vOFuncionario.getCpf());
            stmt.setString(2, this.vOFuncionario.getNome());
            stmt.setDate(3, Date.valueOf(this.vOFuncionario.getDataNascimento()));
            stmt.setString(4, this.vOFuncionario.getTelefone());
            stmt.setDouble(5, this.vOFuncionario.getSalario());
            stmt.setString(6, this.vOFuncionario.getCargo());
            stmt.setInt(7, this.vOFuncionario.getIdFuncionario());
            
            stmt.execute();
            
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersFuncionario alterar: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }

    @Override
    public ObservableList<VOFuncionario> listar() {
        try {
            String sql = "select * from funcionario";

            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            ObservableList<VOFuncionario> list = FXCollections.observableArrayList();
            Extracao extracao = new Extracao();
            VOFuncionario vo = new VOFuncionario();
            while (rs.next()) {
                list.add(extracao.extractVOFuncionario(rs, vo.clone()));
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
            String sql = "delete from funcionario where id_funcionario = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("PersFuncionario remover: " + e.getMessage());
            return false;
        } finally {
            this.db.desconectar(connection);
        }
    }
}
