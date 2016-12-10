package restaurante.model.domain;

import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;

public class VOUsuario implements Cloneable {

    private int idUsuario;
    private String login;
    private String senha;
    private String tipoUsuario;
    // n = normal user
    // s = super user
    // 0 = not setted

    public VOUsuario() {
    }

    public VOUsuario(int idUsuario, String login, String senha, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    @Override
    public VOUsuario clone() {
        VOUsuario clone = null;
        
        try {
            clone = (VOUsuario) super.clone();
        } catch(CloneNotSupportedException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Erro clone VOUsuario: " + e.getMessage());
        }
        
        return clone;
    }

}
