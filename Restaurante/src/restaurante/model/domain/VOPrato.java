package restaurante.model.domain;

import java.util.List;

public class VOPrato extends VOProduto implements Cloneable {

    private int idPrato;
    private int idProduto;
    private List<VOIngrediente> ingredientes = null;

    public VOPrato() {
    }

    public VOPrato(int idPrato, int idProduto, String nome, int tipo, double precoVenda,
            List<VOIngrediente> ingredientes) {
        super(idProduto, nome, tipo, precoVenda);
        this.idPrato = idPrato;
        this.idProduto = idProduto;
        this.ingredientes = ingredientes;
    }

    public VOPrato(int idPrato, int idProduto, List<VOIngrediente> ingredientes) {
        super();
        this.idPrato = idPrato;
        this.idProduto = idProduto;
        this.ingredientes = ingredientes;
    }

    @Override
    public int getIdProduto() {
        return idProduto;
    }

    @Override
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdPrato() {
        return idPrato;
    }

    public void setIdPrato(int idPrato) {
        this.idPrato = idPrato;
    }

    public List<VOIngrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<VOIngrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    @Override
    public VOPrato clone() {
        return (VOPrato) super.clone();
    }
}
