package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Configs.ConnectionDB;
import sample.Constants.ConstantInterface;
import sample.ObjectBD.Answer;
import sample.ObjectBD.Question;
import sample.ObjectBD.Test;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea inputOutputZone;

    @FXML
    private TextField difficultyField;

    @FXML
    private Label inputOutputZoneInformationLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField testNameField;

    @FXML
    private Button deleteTestButton;

    @FXML
    private Button createQuestionButton;

    @FXML
    private TextField typeField;

    @FXML
    private TextField authorField;

    @FXML
    private Button openQuestionButton;

    @FXML
    private Button createTestButton;

    @FXML
    private Button deleteQuestionButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button openTestButton;

    @FXML
    private TextField questionIdField;

    @FXML
    void initialize() {
        logoutButton.setOnAction(event -> {
            logout();
        });
        createTestButton.setOnAction(event -> {
            createTestMode();
        });
        saveButton.setOnAction(event -> {
            if (inputOutputZoneInformationLabel.getText() == ConstantInterface.TEST_CREATION_MESSAGE) {
                cleaningFields();
                createTest();
            }else if(inputOutputZoneInformationLabel.getText() == ConstantInterface.QUESTION_CREATION_MESSAGE) {
                cleaningFields();
                createQuestion();
            }
        });
        openTestButton.setOnAction(event -> {
            openTest();
        });
        deleteTestButton.setOnAction(event -> {
            deleteTest();
        });
        createQuestionButton.setOnAction(event -> {
            createQuestionMode();
        });
        openQuestionButton.setOnAction(event -> {
            openQuestion();
        });
        deleteQuestionButton.setOnAction(event -> {
            eraseQuestion();
        });
    }
    private void logout() {
        logoutButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/interfaces/authorizationWindow.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        logoutButton.getScene().getWindow().hide();
        stage.show();
    }
    private void createTest() {
        ConnectionDB connectionDB = new ConnectionDB();
        Test test = new Test(testNameField.getText(), inputOutputZone.getText());
        try {
            connectionDB.addTest(test);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createTestMode() {
        inputOutputZoneInformationLabel.setText(ConstantInterface.TEST_CREATION_MESSAGE);
    }

    private void openTest() {
        ConnectionDB connectionDB = new ConnectionDB();
        Test test = new Test(testNameField.getText());
        inputOutputZoneInformationLabel.setText(ConstantInterface.TEST_VIEW_MESSAGE);
        ResultSet rs = connectionDB.getTest(test);
        try {
            rs.next();
            inputOutputZone.setText(rs.getString(3));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteTest() {
        ConnectionDB connectionDB = new ConnectionDB();
        Test test = new Test(testNameField.getText());
        connectionDB.eraseTest(test);
    }

    private void createQuestionMode() {
        inputOutputZoneInformationLabel.setText(ConstantInterface.QUESTION_CREATION_MESSAGE);
    }

    private void createQuestion() {
        ConnectionDB connectionDB = new ConnectionDB();
        String[] dividedText = new String[2];
        for (String retval : inputOutputZone.getText().split("<#>")) {
            if (dividedText[0] == ""){
                dividedText[0] = retval;
            } else {
                dividedText[1] = retval;
            }
        }
        Answer answer = new Answer(dividedText[1]);
        Question question = new Question(typeField.getText(), authorField.getText(),
                difficultyField.getText(), dividedText[0]);
        try {
            connectionDB.addQuestion(question, answer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void openQuestion() {
        ConnectionDB connectionDB = new ConnectionDB();
        inputOutputZoneInformationLabel.setText(ConstantInterface.QUESTION_VIEW_MESSAGE);
        ResultSet rs = connectionDB.getQuestion(questionIdField.getText());
        try {
            rs.next();
            difficultyField.setText(rs.getString(2));
            typeField.setText(rs.getString(3));
            authorField.setText(rs.getString(4));
            inputOutputZone.setText(rs.getString(5) + "<#>" + rs.getString(5));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void eraseQuestion() {
        ConnectionDB connectionDB = new ConnectionDB();
        connectionDB.eraseQuestion(questionIdField.getText());
    }

    private void cleaningFields() {
        difficultyField.setText("");
        typeField.setText("");
        authorField.setText("");
        inputOutputZone.setText("");
    }
}