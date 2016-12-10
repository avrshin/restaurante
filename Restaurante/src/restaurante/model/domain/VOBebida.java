package restaurante.model.domain;

public class VOBebida extends VOProduto implements Cloneable {

    private int idBebida;
    private int idProduto;
    private int tamanho; // ml do produto, lata de 300ml
    private int quantidade; // quantas tem

    public VOBebida() {
    }
    
    public VOBebida(int idBebida, int idProduto, int tamanho, int quantidade) {
        super();
        this.idBebida = idBebida;
        this.idProduto = idProduto;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
    }

    public VOBebida(int idBebida, int idProduto, int tamanho, int quantidade,
            String nome, int tipo, double precoVenda) {
        super(idProduto, nome, tipo, precoVenda);
        this.idBebida = idBebida;
        this.idProduto = idProduto;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdBebida() {
        return idBebida;
    }

    public void setIdBebida(int idBebida) {
        this.idBebida = idBebida;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    @Override
    public VOBebida clone() {
        return (VOBebida) super.clone();
    }

}
