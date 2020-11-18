package sample.Controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Configs.ConnectionDB;
import sample.ObjectBD.User;

public class AuthorizationController {
    @FXML
    private Label userMessage;
    @FXML
    private Button nextButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    public AuthorizationController() {
    }

    @FXML
    void initialize() {
        this.nextButton.setOnAction((event) -> {
            String loginText = this.loginField.getText().trim();
            String passwordText = this.passwordField.getText().trim();
            if (!loginText.equals("") && !passwordText.equals("")) {
                this.authorizationUser(loginText, passwordText);
            } else {
                this.userMessage.setText("заполните все поля");
                this.loginField.setText("");
                this.passwordField.setText("");
            }
        });
        this.registerButton.setOnAction((event) -> {
            this.registerButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/sample/interfaces/registrationWindow.fxml"));
            try {
                loader.load();
            } catch (IOException var5) {
                var5.printStackTrace();
            }

            Parent root = (Parent)loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }

    private void authorizationUser(String loginText, String passwordText) {
        ConnectionDB connectionDB = new ConnectionDB();
        User user = new User();
        user.setAdmin_status(false);
        user.setLogin(loginText);
        user.setPassword(passwordText);
        ResultSet rs = connectionDB.getUser(user);
        boolean occurrence = false;

        try {
            while(rs.next()) {
                occurrence = true;
            }
        } catch (SQLException var14) {
            var14.printStackTrace();
        }

        if (!occurrence) {
            user.setAdmin_status(true);
            rs = connectionDB.getUser(user);
            boolean adminOccurrence = false;

            try {
                while(rs.next()) {
                    adminOccurrence = true;
                }
            } catch (SQLException var13) {
                var13.printStackTrace();
            }

            if (adminOccurrence) {
                this.nextButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/sample/interfaces/editorTestWindow.fxml"));

                try {
                    loader.load();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } else {
                this.userMessage.setText("пользователь не найден");
                this.loginField.setText("");
                this.passwordField.setText("");
            }
        } else if (occurrence) {
            this.nextButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/sample/interfaces/passingTestWindow.fxml"));

            try {
                loader.load();
            } catch (IOException var11) {
                var11.printStackTrace();
            }

            Parent root = (Parent)loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }

    }
}
