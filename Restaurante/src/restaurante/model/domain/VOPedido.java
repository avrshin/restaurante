package restaurante.model.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;

public class VOPedido implements Cloneable {

    private int idPedido;
    private LocalTime hora;
    private LocalDate data;
    private int idMesa;
    private char status;
    // status_pedido
    // a = aberto
    // f = fechado
    private List<VOProduto> produto;

    public VOPedido() {
    }

    public VOPedido(int idPedido, LocalTime hora, LocalDate data, int idMesa, 
            char status, List<VOProduto> produto) {
        this.idPedido = idPedido;
        this.data = data;
        this.idMesa = idMesa;
        this.produto = produto;
        this.status = status;
        this.hora = hora;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public List<VOProduto> getProduto() {
        return produto;
    }

    public void setProduto(List<VOProduto> produto) {
        this.produto = produto;
    }
    
    @Override
    public VOPedido clone() {
        VOPedido clone = null;
        
        try {
            clone = (VOPedido) super.clone();
        } catch(CloneNotSupportedException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Erro clone VOPedido: " + e.getMessage());
        }
        
        return clone;
    }

}
