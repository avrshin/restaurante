package restaurante.model.domain;

import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;

public class VOIngrediente implements Cloneable {

    private int idIngrediente;
    private String nome;
    private int quantidade;
    private int unidadeMedida;
    // unidade_medida 1 = unidades
    // unidade_medida 2 = kilos
    // unidade_medida 3 = litros
    //private int idCompra;

    public VOIngrediente() {
    }
    
    public VOIngrediente(int idIngrediente, String nome, int quantidade, 
            int unidadeMedida) {
        this.idIngrediente = idIngrediente;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(int unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
    
    @Override
    public VOIngrediente clone() {
        VOIngrediente clone = null;
        
        try {
            clone = (VOIngrediente) super.clone();
        } catch(CloneNotSupportedException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Erro clone VOIngrediente: " + e.getMessage());
        }
        
        return clone;
    }
    
    public String toString() {
        return this.nome;
    }

}
