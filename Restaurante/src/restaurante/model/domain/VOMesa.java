package restaurante.model.domain;

import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;

public class VOMesa implements Cloneable {

    private int idMesa;
    private int numCadeiras;
    private int numero;
    private String status;
    // status_mesa
    // v = vazia
    // o = ocupada
    private int idSalao;

    public VOMesa() {
    }

    public VOMesa(int idMesa, int numCadeiras,
            int numero, String status, int idSalao) {
        this.idMesa = idMesa;
        this.numCadeiras = numCadeiras;
        this.numero = numero;
        this.status = status;
        this.idSalao = idSalao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdSalao() {
        return idSalao;
    }

    public void setIdSalao(int idSalao) {
        this.idSalao = idSalao;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNumCadeiras() {
        return numCadeiras;
    }

    public void setNumCadeiras(int numCadeiras) {
        this.numCadeiras = numCadeiras;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    @Override
    public VOMesa clone() {
        VOMesa clone = null;
        
        try {
            clone = (VOMesa) super.clone();
        } catch(CloneNotSupportedException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Erro clone VOMesa: " + e.getMessage());
        }
        
        return clone;
    }
    
    public String toString() {
        if(this.status.equals("o")) {
            return "Mesa " + this.numero + " - ocupada";
        }
        return "Mesa " + this.numero + " - vazia";
    }
}
