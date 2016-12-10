package restaurante.model.domain;

import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;

public class VOSalao implements Cloneable {

    private int idSalao;
    private int numMesas;
    private String nome;

    public VOSalao() {
    }

    public VOSalao(int idSalao, int numMesas, String nome) {
        this.idSalao = idSalao;
        this.numMesas = numMesas;
        this.nome = nome;
    }

    public int getIdSalao() {
        return idSalao;
    }

    public void setIdSalao(int idSalao) {
        this.idSalao = idSalao;
    }

    public int getNumMesas() {
        return numMesas;
    }

    public void setNumMesas(int numMesas) {
        this.numMesas = numMesas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public VOSalao clone() {
        VOSalao clone = null;
        
        try {
            clone = (VOSalao) super.clone();
        } catch(CloneNotSupportedException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Erro clone VOSalao: " + e.getMessage());
        }
        
        return clone;
    }
    
    public String toString() {
        return this.nome;
    }
}
