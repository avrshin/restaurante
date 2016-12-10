/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.model.pers;

import javafx.collections.ObservableList;
import restaurante.model.domain.*;

/**
 *
 * @author luis
 */
public class Facade {

    // ----- PersBebida -----
    public int inserirBebida(VOUsuario vOUsuario, VOBebida vOBebida) {
        return new PersBebida(vOUsuario, vOBebida).inserir();
    }

    public boolean alterarBebida(VOUsuario vOUsuario, VOBebida vOBebida) {
        return new PersBebida(vOUsuario, vOBebida).alterar();
    }

    public ObservableList<VOBebida> listarBebida() {
        return new PersBebida().listar();
    }

    public boolean removerBebida(int id) {
        return new PersBebida().remover(id);
    }

    public boolean alteraQuantidadeBebida(VOUsuario vOUsuario, VOBebida vOBebida) {
        return new PersBebida(vOUsuario, vOBebida).alteraQuantidade();
    }

    public VOBebida listarUnicoBebida(int idProduto) {
        return new PersBebida().listarUnico(idProduto);
    }

    // ---- PersCompra ----
    public int inserirCompra(VOUsuario vOUsuario, VOCompra vOCompra) {
        return new PersCompra(vOUsuario, vOCompra).inserir();
    }

    public boolean alterarCompra(VOUsuario vOUsuario, VOCompra vOCompra) {
        return new PersCompra(vOUsuario, vOCompra).alterar();
    }

    public boolean removerCompra(int id) {
        return new PersCompra().remover(id);
    }
    
    public VOCompra buscarCompraPorID(int id) {
        return new PersCompra().buscarCompraPorID(id);
    }

    // ---- PersFuncionario ----
    public int inserirFuncionario(VOUsuario vOUsuario, VOFuncionario vOFuncionario) {
        return new PersFuncionario(vOUsuario, vOFuncionario).inserir();
    }

    public boolean alterarFuncionario(VOUsuario vOUsuario, VOFuncionario vOFuncionario) {
        return new PersFuncionario(vOUsuario, vOFuncionario).alterar();
    }

    public ObservableList<VOFuncionario> listarFuncionario() {
        return new PersFuncionario().listar();
    }

    public boolean removerFuncionario(int id) {
        return new PersFuncionario().remover(id);
    }

    // ---- PersIngrediente ----
    public int inserirIngrediente(VOUsuario vOUsuario, VOIngrediente vOIngrediente) {
        return new PersIngrediente(vOUsuario, vOIngrediente).inserir();
    }

    public boolean alterarIngrediente(VOUsuario vOUsuario, VOIngrediente vOIngrediente) {
        return new PersIngrediente(vOUsuario, vOIngrediente).alterar();
    }
    
    public boolean alteraQuantidadeIngrediente(VOUsuario vOUsuario, VOIngrediente vOIngrediente) {
        return new PersIngrediente(vOUsuario, vOIngrediente).alteraQuantidade();
    }

    public ObservableList<VOIngrediente> listarIngrediente() {
        return new PersIngrediente().listar();
    }
    
    public ObservableList<VOIngrediente> listarIngredienteDeProduto(int idPrato) {
        return new PersIngrediente().listarIngredienteDeProduto(idPrato);
    }

    public boolean removerIngrediente(int id) {
        return new PersIngrediente().remover(id);
    }

    // ---- PersMesa ----
    public int inserirMesa(VOUsuario vOUsuario, VOMesa vOMesa) {
        return new PersMesa(vOUsuario, vOMesa).inserir();
    }

    public boolean alterarMesa(VOUsuario vOUsuario, VOMesa vOMesa) {
        return new PersMesa(vOUsuario, vOMesa).alterar();
    }
    
    public boolean alocarMesa(VOUsuario vOUsuario, VOMesa vOMesa) {
        return new PersMesa(vOUsuario, vOMesa).alocarMesa();
    }
    
    public boolean desalocarMesa(VOUsuario vOUsuario, VOMesa vOMesa) {
        return new PersMesa(vOUsuario, vOMesa).desalocarMesa();
    }

    public ObservableList<VOMesa> listarMesa() {
        return new PersMesa().listar();
    }
    
    public ObservableList<VOMesa> listarOcupadasMesa() {
        return new PersMesa().listarOcupadas();
    }

    public boolean removerMesa(int id) {
        return new PersMesa().remover(id);
    }

    // ---- PersPedido ----
    public int novoPedido(VOUsuario vOUsuario, VOPedido vOPedido) {
        return new PersPedido(vOUsuario, vOPedido).novoPedido();
    }
    
    public int novoUsuarioPedido(VOUsuario vOUsuario, VOPedido vOPedido) {
        return new PersPedido(vOUsuario, vOPedido).novoUsuarioPedido();
    }
    
    public int novoProdutoPedido(VOUsuario vOUsuario, VOPedido vOPedido, VOProduto vOProduto) {
        return new PersPedido(vOUsuario, vOPedido).novoProdutoPedido(vOProduto);
    }

    public boolean fecharPedido(int idMesa) {
        return new PersPedido().fecharPedido(idMesa);
    }
    
    // ---- PersPrato ----
    public int inserirPrato(VOUsuario vOUsuario, VOPrato vOPrato) {
        return new PersPrato(vOUsuario, vOPrato).inserir();
    }
    
    public int inserirIngredientePrato(VOUsuario vOUsuario, VOPrato vOPrato, VOIngrediente ingrediente) {
        return new PersPrato(vOUsuario, vOPrato).inserirIngredientePrato(ingrediente);
    }

    public boolean removerIngredientesPrato(int idPrato) {
        return new PersPrato().removerIngredientes(idPrato);
    }

    public ObservableList<VOPrato> listarPrato() {
        return new PersPrato().listar();
    }

    public boolean removerPrato(int id) {
        return new PersPrato().remover(id);
    }
    
    public VOPrato listarUnicoPrato(int idProduto) {
        return new PersPrato().listarUnico(idProduto);
    }

    // ---- PersProduto ----
    public int inserirProduto(VOUsuario vOUsuario, VOProduto vOProduto) {
        return new PersProduto(vOUsuario, vOProduto).inserir();
    }

    public boolean alterarProduto(VOUsuario vOUsuario, VOProduto vOProduto) {
        return new PersProduto(vOUsuario, vOProduto).alterar();
    }

    public ObservableList<VOProduto> listarProduto() {
        return new PersProduto().listar();
    }
    
    public ObservableList<VOProduto> listarOrdenadoProduto() {
        return new PersProduto().listarOrdenado();
    }
    
    public ObservableList<VOProduto> listarPorMesaProduto(int idMesa) {
        return new PersProduto().listarPorMesa(idMesa);
    }
    
    public VOProduto listarPorIDProduto(int idProduto) {
        return new PersProduto().listarPorID(idProduto);
    }

    public boolean removerProduto(int id) {
        return new PersProduto().remover(id);
    }

    // ---- PersSalao ----
    public int inserirSalao(VOUsuario vOUsuario, VOSalao vOSalao) {
        return new PersSalao(vOUsuario, vOSalao).inserir();
    }

    public boolean alterarSalao(VOUsuario vOUsuario, VOSalao vOSalao) {
        return new PersSalao(vOUsuario, vOSalao).alterar();
    }

    public ObservableList<VOSalao> listarSalao() {
        return new PersSalao().listar();
    }

    public boolean removerSalao(int id) {
        return new PersSalao().remover(id);
    }

    // ---- PersUsuario ----
    public int inserirUsuario(VOUsuario vOUsuarioLogado, VOUsuario vOUsuario) {
        return new PersUsuario(vOUsuarioLogado, vOUsuario).inserir();
    }

    public boolean alterarUsuario(VOUsuario vOUsuarioLogado, VOUsuario vOUsuario) {
        return new PersUsuario(vOUsuarioLogado, vOUsuario).alterar();
    }

    public ObservableList<VOUsuario> listarUsuario() {
        return new PersUsuario().listar();
    }

    public boolean removerUsuario(int id) {
        return new PersUsuario().remover(id);
    }
    
    public boolean autenticaUsuario(VOUsuario vOUsuario) {
        return new PersUsuario(null, vOUsuario).autenticaUsuario();
    }
}
