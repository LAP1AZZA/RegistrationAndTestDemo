package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Configs.ConnectionDB;
import sample.ObjectBD.Test;

public class EditTestController {
    public static int questionNumberInTheTest;
    public static int numberOfCorrectAnswers;
    public static ArrayList testQuestionId = new ArrayList();
    public static ArrayList questionAnswerParts = new ArrayList();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label questionIDLabel;
    @FXML
    private TextArea questionDisplayAreaField;
    @FXML
    private CheckBox secondOptionCheckBox;
    @FXML
    private Label questionTypeLabel;
    @FXML
    private Button logoutButton;
    @FXML
    private CheckBox thirdOptionCheckBox;
    @FXML
    private TextField testNameField;
    @FXML
    private Label questionAuthorLabel;
    @FXML
    private Label questionNumberInTheTestLabel;
    @FXML
    private TextArea answerAreaField;
    @FXML
    private Button answerButton;
    @FXML
    private Button openTestButton;
    @FXML
    private CheckBox firstOptionCheckBox;
    @FXML
    private Label questionDifficultyLabel;
    @FXML
    private CheckBox fourthOptionCheckBox;

    public EditTestController() {
    }

    @FXML
    void initialize() {
        this.logoutButton.setOnAction((event) -> {
            this.logout();
        });
        this.openTestButton.setOnAction((event) -> {
            this.testRecording();
        });
        this.answerButton.setOnAction((event) -> {
            this.checkAnswer();
        });
    }

    private void logout() {
        this.logoutButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sample/interfaces/authorizationWindow.fxml"));

        try {
            loader.load();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        Parent root = (Parent)loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        this.logoutButton.getScene().getWindow().hide();
        stage.show();
    }

    private void testRecording() {
        this.answerAreaField.setText("");
        testQuestionId.clear();
        ConnectionDB connectionDB = new ConnectionDB();
        Test test = new Test(this.testNameField.getText());
        ResultSet rs = connectionDB.getTest(test);

        try {
            rs.next();
            String[] var4 = rs.getString(3).split(",");
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String id = var4[var6];
                testQuestionId.add(id);
            }
        } catch (SQLException var8) {
            var8.printStackTrace();
        }

        this.answerAreaField.setText("");
        questionNumberInTheTest = 1;
        this.testPerformer();
    }

    private void testPerformer() {
        ConnectionDB connectionDB = new ConnectionDB();
        questionAnswerParts.clear();
        this.questionNumberInTheTestLabel.setText(Integer.toString(questionNumberInTheTest));
        ResultSet rs = connectionDB.getQuestion(testQuestionId.get(questionNumberInTheTest - 1).toString());

        try {
            this.questionIDLabel.setText(rs.getString(1));
            this.questionTypeLabel.setText(rs.getString(2));
            this.questionAuthorLabel.setText(rs.getString(3));
            this.questionDifficultyLabel.setText(rs.getString(4));
            this.questionDisplayAreaField.setText(rs.getString(5));
            String[] var3 = rs.getString(6).split(",");
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String part = var3[var5];
                questionAnswerParts.add(part);
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        }

        if (this.questionTypeLabel.getText() == "открытый") {
            this.firstOptionCheckBox.setText("");
            this.secondOptionCheckBox.setText("");
            this.thirdOptionCheckBox.setText("");
            this.fourthOptionCheckBox.setText("");
            this.answerAreaField.setText("");
            this.cleaningCheckBox();
        } else {
            this.firstOptionCheckBox.setText(questionAnswerParts.get(1).toString());
            this.secondOptionCheckBox.setText(questionAnswerParts.get(2).toString());
            this.thirdOptionCheckBox.setText(questionAnswerParts.get(3).toString());
            this.fourthOptionCheckBox.setText(questionAnswerParts.get(4).toString());
            this.answerAreaField.setText("");
            this.cleaningCheckBox();
        }

    }

    private void checkAnswer() {
        ConnectionDB connectionDB = new ConnectionDB();
        questionAnswerParts.clear();
        ResultSet rs = connectionDB.getQuestion(testQuestionId.get(questionNumberInTheTest - 1).toString());

        try {
            String[] var3 = rs.getString(6).split(",");
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String part = var3[var5];
                questionAnswerParts.add(part);
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        }

        String var8 = this.questionTypeLabel.getText();
        byte var9 = -1;
        switch(var8.hashCode()) {
            case -692241774:
                if (var8.equals("с еденичным ответом")) {
                    var9 = 0;
                }
                break;
            case 1336465099:
                if (var8.equals("с множественным ответом")) {
                    var9 = 1;
                }
                break;
            case 1573401071:
                if (var8.equals("открытый")) {
                    var9 = 2;
                }
        }

        switch(var9) {
            case 0:
                if (questionAnswerParts.get(4).toString() == "1" && this.firstOptionCheckBox.isSelected()) {
                    ++numberOfCorrectAnswers;
                }

                if (questionAnswerParts.get(4).toString() == "2" && this.firstOptionCheckBox.isSelected()) {
                    ++numberOfCorrectAnswers;
                }

                if (questionAnswerParts.get(4).toString() == "3" && this.firstOptionCheckBox.isSelected()) {
                    ++numberOfCorrectAnswers;
                }

                if (questionAnswerParts.get(4).toString() == "4" && this.firstOptionCheckBox.isSelected()) {
                    ++numberOfCorrectAnswers;
                }
                break;
            case 1:
                if (this.firstOptionCheckBox.isSelected() == this.StringToBool(questionAnswerParts.get(4).toString())
                        && this.secondOptionCheckBox.isSelected() == this.StringToBool(questionAnswerParts.get(5).toString())
                        && this.thirdOptionCheckBox.isSelected() == this.StringToBool(questionAnswerParts.get(6).toString())
                        && this.fourthOptionCheckBox.isSelected() == this.StringToBool(questionAnswerParts.get(7).toString()))
                {
                    ++numberOfCorrectAnswers;
                }
                break;
            case 2:
                if (questionAnswerParts.get(0).toString() == this.answerAreaField.getText()) {
                    ++numberOfCorrectAnswers;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.questionTypeLabel.getText());
        }

        ++questionNumberInTheTest;
        this.testPerformer();
    }

    private void cleaningCheckBox() {
        this.firstOptionCheckBox.setSelected(false);
        this.secondOptionCheckBox.setSelected(false);
        this.thirdOptionCheckBox.setSelected(false);
        this.fourthOptionCheckBox.setSelected(false);
    }

    public boolean StringToBool(String var) {
        return var == "true";
    }
}
