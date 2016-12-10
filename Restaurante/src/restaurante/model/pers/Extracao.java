/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.model.pers;

import java.sql.ResultSet;
import java.sql.SQLException;
import restaurante.model.domain.VOBebida;
import restaurante.model.domain.VOCompra;
import restaurante.model.domain.VOFuncionario;
import restaurante.model.domain.VOIngrediente;
import restaurante.model.domain.VOMesa;
import restaurante.model.domain.VOPrato;
import restaurante.model.domain.VOProduto;
import restaurante.model.domain.VOSalao;
import restaurante.model.domain.VOUsuario;

/**
 *
 * @author luis
 */
public class Extracao {

    public void extractVOUsuarioUnico(ResultSet rs, VOUsuario vo) throws SQLException {
        vo.setIdUsuario(rs.getInt(rs.findColumn("id_usuario")));
        vo.setLogin(rs.getString(rs.findColumn("login")));
        vo.setSenha(rs.getString(rs.findColumn("senha")));
        vo.setTipoUsuario(rs.getString(rs.findColumn("tipo_usuario")));
    }

    public VOSalao extractVOSalao(ResultSet rs, VOSalao clone) throws SQLException {
        clone.setIdSalao(rs.getInt("id_salao"));
        clone.setNumMesas(rs.getInt("num_mesas"));
        clone.setNome(rs.getString("nome"));
        return clone;
    }

    public VOUsuario extractVOUsuario(ResultSet rs, VOUsuario clone) throws SQLException {
        clone.setIdUsuario(rs.getInt("id_usuario"));
        clone.setLogin(rs.getString("login"));
        clone.setSenha(rs.getString("senha"));
        clone.setTipoUsuario(rs.getString("tipo_usuario"));
        return clone;
    }

    public VOBebida extractVOBebidaVOProduto(ResultSet rs, VOBebida clone) throws SQLException {
        clone.setIdBebida(rs.getInt("id_bebida"));
        clone.setIdProduto(rs.getInt("id_produto"));
        clone.setTamanho(rs.getInt("tamanho"));
        clone.setQuantidade(rs.getInt("quantidade"));
        clone.setNome(rs.getString("nome"));
        clone.setTipo(rs.getInt("tipo"));
        clone.setPrecoVenda(rs.getDouble("preco_venda"));
        return clone;
    }

    public VOPrato extractVOPratoVOProduto(ResultSet rs, VOPrato clone) throws SQLException {
        clone.setIdPrato(rs.getInt("id_prato"));
        clone.setIdProduto(rs.getInt("id_produto"));
        clone.setNome(rs.getString("nome"));
        clone.setTipo(rs.getInt("tipo"));
        clone.setPrecoVenda(rs.getDouble("preco_venda"));
        clone.setIngredientes(null);
        return clone;
    }

    public VOProduto extractVOProdutoUnico(ResultSet rs) throws SQLException {
        VOProduto vo = new VOProduto();
        vo.setIdProduto(rs.getInt("id_produto"));
        vo.setNome(rs.getString("nome"));
        vo.setTipo(rs.getInt("tipo"));
        vo.setPrecoVenda(rs.getDouble("preco_venda"));
        return vo;
    }

    public void extractVOCompraUnico(ResultSet rs, VOCompra vo) throws SQLException {
        vo.setIdCompra(rs.getInt("id_compra"));
        vo.setData(rs.getDate("data").toLocalDate());
        vo.setPrecoCompra(rs.getDouble("preco_compra"));
        vo.setQuantidade(rs.getInt("quantidade"));
        vo.setIdBebida(rs.getInt("id_compra_bebida"));
        vo.setIdIngrediente(rs.getInt("id_compra_ingrediente"));
    }

    public VOIngrediente extractVOIngrediente(ResultSet rs, VOIngrediente clone) throws SQLException {
        clone.setIdIngrediente(rs.getInt("id_ingrediente"));
        clone.setNome(rs.getString("nome"));
        clone.setQuantidade(rs.getInt("quantidade"));
        clone.setUnidadeMedida(rs.getInt("unidade_medida"));
        return clone;
    }
    
    public VOProduto extractVOProduto(ResultSet rs, VOProduto clone) throws SQLException {
        clone.setIdProduto(rs.getInt("id_produto"));
        clone.setNome(rs.getString("nome"));
        clone.setTipo(rs.getInt("tipo"));
        clone.setPrecoVenda(rs.getDouble("preco_venda"));
        return clone;
    }

    public VOMesa extractVOMesa(ResultSet rs, VOMesa clone) throws SQLException {
        clone.setIdMesa(rs.getInt("id_mesa"));
        clone.setIdSalao(rs.getInt("id_salao"));
        clone.setNumCadeiras(rs.getInt("num_cadeiras"));
        clone.setNumero(rs.getInt("numero"));
        clone.setStatus(rs.getString("status_mesa"));
        return clone;
    }

    public VOFuncionario extractVOFuncionario(ResultSet rs, VOFuncionario clone) throws SQLException {
        clone.setCargo(rs.getString("cargo"));
        clone.setCpf(rs.getString("cpf"));
        clone.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        clone.setIdFuncionario(rs.getInt("id_funcionario"));
        clone.setNome(rs.getString("nome"));
        clone.setSalario(rs.getDouble("salario"));
        clone.setTelefone(rs.getString("telefone"));
        return clone;
    }

}
