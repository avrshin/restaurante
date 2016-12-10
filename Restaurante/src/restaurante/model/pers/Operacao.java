/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.model.pers;

import java.util.List;

/**
 *
 * @author luis
 */
public interface Operacao {

    public abstract int inserir();

    public abstract boolean alterar();

    public abstract List listar();

    public abstract boolean remover(int id);

}
