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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import restaurante.model.domain.VOUsuario;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class ControllerLogin implements Initializable {

//    @FXML
//    private BorderPane borderPaneMain;
//    @FXML
//    private Label labelNomeSecao;
//    @FXML
//    private Button buttonLogin;
//    @FXML
//    private Label labelUsuario;
//    @FXML
//    private Label labelSenha;
    @FXML
    private TextField textFieldLogin;
    @FXML
    private PasswordField passwordFieldSenha;

    private MudaCena cena = new MudaCena();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void actionButtonLogin(ActionEvent event) {
        VOUsuario vOUsuario = new VOUsuario(-1, this.textFieldLogin.getText(),
                this.passwordFieldSenha.getText(), "0");
        SingletonLogin login = SingletonLogin.getInstance();
        boolean log = login.autenticaUsuario(vOUsuario);

        if (log) {
            //System.out.println("id:" + vOUsuario.getIdUsuario() + "type: " + vOUsuario.getTipoUsuario());
            try {
                if (vOUsuario.getTipoUsuario().equals("s")) {
                    ControllerMenu cm = (ControllerMenu) cena.changeScene(null,
                            "/restaurante/view/FXMLMenu.fxml", event);
                } else if (vOUsuario.getTipoUsuario().equals("n")) {
                    ControllerMenuGarcon cm = (ControllerMenuGarcon) cena.changeScene(null,
                            "/restaurante/view/FXMLMenuGarcon.fxml", event);
                }
            } catch (IOException ex) {
                Logger.getLogger(AbstractControllerGerenciar.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Erro actionButtonLogin: "
                        + ex.getMessage());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login ou senha inválidos!");
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

}
