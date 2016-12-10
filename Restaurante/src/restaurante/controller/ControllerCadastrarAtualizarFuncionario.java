/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import restaurante.model.domain.VOFuncionario;
import restaurante.model.pers.Facade;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerCadastrarAtualizarFuncionario implements Initializable {

//    @FXML
//    private Button buttonVoltar;
    @FXML
    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogout;
//    @FXML
//    private Button buttonCancelarFuncionario;
    @FXML
    private Button buttonCadastrarAtualizarFuncionario;
//    @FXML
//    private Label labelNomeFuncionario;
//    @FXML
//    private Label labelCPFFuncionario;
//    @FXML
//    private Label labelDataNascimentoFuncionario;
//    @FXML
//    private Label labelTelefoneFuncionario;
//    @FXML
//    private Label labelSalarioFuncionario;
//    @FXML
//    private Label labelCargoFuncionario;
    @FXML
    private TextField textFieldNomeFuncionario;
    @FXML
    private TextField textFieldCPFFuncionario;
    @FXML
    private DatePicker datePickerDataNascimento;
    @FXML
    private TextField textFieldTelefoneFuncionario;
    @FXML
    private TextField textFieldSalarioFuncionario;
    @FXML
    private TextField textFieldCargoFuncionario;

    private MudaCena cena = new MudaCena();
    private SingletonLogin usuarioLogado = null;
    private VOFuncionario vOFuncionario = null;
    private char atualizar = 'n'; // n = nao, s = sim

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioLogado = SingletonLogin.getInstance();
        this.colocaMascara();
    }

    @FXML
    private void actionButtonVoltar(ActionEvent event) {
        try {
            cena.changeScene(new ControllerGerenciarFuncionario(
                    "/restaurante/view/FXMLCadastrarAtualizarFuncionario.fxml"),
                    "/restaurante/view/FXMLGerenciar.fxml", event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionButtonVoltar" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonLogout(ActionEvent event) {
        try {
            cena.changeScene(null, "/restaurante/view/FXMLLogin.fxml",
                    event);
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionButtonLogout" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonCancelarFuncionario(ActionEvent event) {
        try {
            if (this.usuarioLogado.getUsuarioLogado().getTipoUsuario().equals("s")) {
                cena.changeScene(null,
                        "/restaurante/view/FXMLMenu.fxml", event);
            } else if (this.usuarioLogado.getUsuarioLogado().getTipoUsuario().equals("n")) {
                cena.changeScene(null,
                        "/restaurante/view/FXMLMenuGarcon.fxml", event);
            }
        } catch (IOException ex) {
            Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: actionButtonCancelarFuncionario" + ex.getMessage());
        }
    }

    @FXML
    private void actionButtonCadastrarAtualizarFuncionario(ActionEvent event) {
        if (!this.checaCamposVazios() || !this.validaDados()) {
            return;
        }
        
        if (atualizar == 's') {
            this.atualizar(event);
        } else {
            this.cadastrar(event);
        }
    }

    private void cadastrar(ActionEvent event) {
        VOFuncionario vo = new VOFuncionario(-1,
                this.textFieldCPFFuncionario.getText(),
                this.textFieldNomeFuncionario.getText(),
                this.datePickerDataNascimento.getValue(),
                this.textFieldTelefoneFuncionario.getText(),
                Double.valueOf(this.textFieldSalarioFuncionario.getText()),
                this.textFieldCargoFuncionario.getText());

        Facade pers = new Facade();
        int id = pers.inserirFuncionario(this.usuarioLogado.getUsuarioLogado(), vo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (id <= 0) {
            alert.setContentText("Falha ao cadastrar o funcionario");
        } else {
            alert.setContentText("Funcionario cadastrado com sucesso!");
        }
        alert.showAndWait();
    }

    private void atualizar(ActionEvent event) {
        if (this.vOFuncionario == null) {
            System.out.println("VOFuncionario nao setado, atualizar, "
                    + "ControllerCadastrarAtualizarFuncionario");
            return;
        }

        this.vOFuncionario.setCpf(this.textFieldCPFFuncionario.getText());
        this.vOFuncionario.setNome(this.textFieldNomeFuncionario.getText());
        this.vOFuncionario.setDataNascimento(this.datePickerDataNascimento.getValue());
        this.vOFuncionario.setTelefone(this.textFieldTelefoneFuncionario.getText());
        this.vOFuncionario.setSalario(Double.valueOf(this.textFieldSalarioFuncionario.getText()));
        this.vOFuncionario.setCargo(this.textFieldCargoFuncionario.getText());

        Facade pers = new Facade();
        boolean b = pers.alterarFuncionario(this.usuarioLogado.getUsuarioLogado(), 
                this.vOFuncionario);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (b) {
            alert.setContentText("Funcionario atualizado com sucesso!");
        } else {
            alert.setContentText("Falha ao atualizar o funcionario");
        }
        alert.showAndWait();
    }

    public void setCamposTela(VOFuncionario vo) {
        if (vo == null) {
            return;
        }
        this.vOFuncionario = vo;
        this.textFieldCPFFuncionario.setText(vo.getCpf());
        this.textFieldCargoFuncionario.setText(vo.getCargo());
        this.datePickerDataNascimento.setValue(vo.getDataNascimento());
        this.textFieldNomeFuncionario.setText(vo.getNome());
        this.textFieldSalarioFuncionario.setText(String.valueOf(vo.getSalario()));
        this.textFieldTelefoneFuncionario.setText(vo.getTelefone());

        this.labelNomeSecao.setText("Atualizar Funcionario");
        this.buttonCadastrarAtualizarFuncionario.setText("Atualizar");
    }

    private boolean checaCamposVazios() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        if (this.textFieldCPFFuncionario.getText().equals("")) {
            alert.setContentText("Digite o CPF!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldCargoFuncionario.getText().equals("")) {
            alert.setContentText("Digite o cargo!");
            alert.showAndWait();
            return false;
        } else if (this.datePickerDataNascimento.getValue() == null) {
            alert.setContentText("Digite a data de nascimento!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldNomeFuncionario.getText().equals("")) {
            alert.setContentText("Digite o nome!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldSalarioFuncionario.getText().equals("")) {
            alert.setContentText("Digite o salario!");
            alert.showAndWait();
            return false;
        } else if (this.textFieldTelefoneFuncionario.getText().equals("")) {
            alert.setContentText("Digite o telefone!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void colocaMascara() {
        this.textFieldCPFFuncionario.lengthProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
            String mascara = "###.###.###-##";
            String alphaAndDigits = textFieldCPFFuncionario.getText().replaceAll("[\\-\\.]", "");
            StringBuilder resultado = new StringBuilder();
            int i = 0;
            int quant = 0;

            if (number2.intValue() > number.intValue()) {
                if (textFieldCPFFuncionario.getText().length() <= mascara.length()) {
                    while (i < mascara.length()) {
                        if (quant < alphaAndDigits.length()) {
                            if ("#".equals(mascara.substring(i, i + 1))) {
                                resultado.append(alphaAndDigits.substring(quant, quant + 1));
                                quant++;
                            } else {
                                resultado.append(mascara.substring(i, i + 1));
                            }
                        }
                        i++;
                    }
                    textFieldCPFFuncionario.setText(resultado.toString());
                }
                if (textFieldCPFFuncionario.getText().length() > mascara.length()) {
                    textFieldCPFFuncionario.setText(textFieldCPFFuncionario.getText(0, mascara.length()));
                }
            }
        });
    }

    private boolean validaDados() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        
        if (!this.textFieldCPFFuncionario.getText()
                .matches("[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]-[0-9][0-9]")) {
            alert.setContentText("CPF incorreto!");
            alert.showAndWait();
            return false;
        } else if (!this.textFieldSalarioFuncionario.getText()
                .matches("[0-9]*\\.([0-9]{1})")) {
            alert.setContentText("Formato de salario incorreto!");
            alert.showAndWait();
            return false;
        } else if (!this.textFieldTelefoneFuncionario.getText()
                .matches("\\(([0-9]{2})\\)([0-9]{4})\\-([0-9]{4})")) {
            alert.setContentText("Formato de telefone incorreto!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void setAtualizar(char atualizar) {
        this.atualizar = atualizar;
    }

}
