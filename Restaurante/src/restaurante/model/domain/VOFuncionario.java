package restaurante.model.domain;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurante.controller.AbstractControllerGerenciar;

public class VOFuncionario implements Cloneable {

    private int idFuncionario;
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private String telefone;
    private double salario;
    private String cargo;

    public VOFuncionario() {
    }

    public VOFuncionario(int idPessoa, String cpf, String nome, LocalDate dataNascimento, 
            String telefone, double salario, String cargo) {
        this.idFuncionario = idPessoa;
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.salario = salario;
        this.cargo = cargo;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    @Override
    public VOFuncionario clone() {
        VOFuncionario clone = null;
        
        try {
            clone = (VOFuncionario) super.clone();
        } catch(CloneNotSupportedException e) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Erro clone VOFuncionario: " + e.getMessage());
        }
        
        return clone;
    }
    
    public String toString() {
        return this.nome;
    }

}
