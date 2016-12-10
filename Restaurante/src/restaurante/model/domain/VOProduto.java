package restaurante.model.domain;

import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;

public class VOProduto implements Cloneable {

    private int idProduto;
    private String nome;
    private int tipo;
    // tipo 1 = prato
    // tipo 2 = bebida
    private double precoVenda;

    public VOProduto() {
    }
    
    public VOProduto(int idProduto, String nome, int tipo, double precoVenda) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.tipo = tipo;
        this.precoVenda = precoVenda;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }
    
    @Override
    public VOProduto clone() {
        VOProduto clone = null;
        
        try {
            clone = (VOProduto) super.clone();
        } catch(CloneNotSupportedException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Erro clone VOProduto: " + e.getMessage());
        }
        
        return clone;
    }
    
    public String toString() {
        return "R$ " + this.precoVenda + ", " + this.nome;
    }

}
