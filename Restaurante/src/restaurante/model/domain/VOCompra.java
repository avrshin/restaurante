package restaurante.model.domain;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;

public class VOCompra implements Cloneable {

    private int idCompra;
    private LocalDate data;
    private double precoCompra;
    private int quantidade;
    private int idBebida;
    private int idIngrediente;

    public VOCompra() {
    }
    
    public VOCompra(int idCompra, LocalDate data, double precoCompra, 
            int quantidade, int idBebida, int idIngrediente) {
        this.idCompra = idCompra;
        this.data = data;
        this.precoCompra = precoCompra;
        this.quantidade = quantidade;
        this.idBebida = idBebida;
        this.idIngrediente = idIngrediente;
    }

    public int getIdBebida() {
        return idBebida;
    }

    public void setIdBebida(int idBebida) {
        this.idBebida = idBebida;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    @Override
    public VOCompra clone() {
        VOCompra clone = null;
        
        try {
            clone = (VOCompra) super.clone();
        } catch(CloneNotSupportedException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Erro clone VOBebida: " + e.getMessage());
        }
        
        return clone;
    }

}
