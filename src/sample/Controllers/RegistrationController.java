package sample.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Configs.ConnectionDB;
import sample.ObjectBD.User;

public class RegistrationController {
    @FXML
    private Button applyButton;
    @FXML
    private CheckBox administratorСheck;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField loginField;

    public RegistrationController() {
    }

    @FXML
    void initialize() {
        this.applyButton.setOnAction((event) -> {
            this.registerNewUse();
        });
    }

    private void registerNewUse() {
        ConnectionDB connectionDB = new ConnectionDB();
        User user = new User(this.loginField.getText(), this.nameField.getText(), this.passwordField.getText(), this.administratorСheck.isSelected());

        try {
            connectionDB.registerUser(user);
        } catch (SQLException var7) {
            var7.printStackTrace();
        }

        this.applyButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/sample/interfaces/authorizationWindow.fxml"));

        try {
            loader.load();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        Parent root = (Parent)loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
