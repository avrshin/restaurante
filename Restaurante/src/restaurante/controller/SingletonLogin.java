/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import restaurante.model.domain.VOUsuario;
import restaurante.model.pers.Facade;

/**
 *
 * @author luis
 */
public class SingletonLogin {

    private static SingletonLogin instance = null;
    private VOUsuario vOUsuario = null;

    private SingletonLogin() {
    }

    public static SingletonLogin getInstance() {
        if (instance == null) {
            instance = new SingletonLogin();
        }
        return instance;
    }

    public boolean autenticaUsuario(VOUsuario vOUsuario) {
        boolean b = new Facade().autenticaUsuario(vOUsuario);
        if (b) {
            this.vOUsuario = vOUsuario;
        }
        return b;
    }
    
    public VOUsuario getUsuarioLogado() {
        return this.vOUsuario;
    }
}
