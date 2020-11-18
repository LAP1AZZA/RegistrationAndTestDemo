package sample.Configs;


    import sample.Constants.ConstantDB;
    import sample.Constants.ConstantInput;
    import sample.ObjectBD.Answer;
    import sample.ObjectBD.Question;
    import sample.ObjectBD.Test;
    import sample.ObjectBD.User;
    import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
    public class ConnectionDB extends ConfigDB {
        Connection dbConnection;
        public Connection getDbConnection()
                throws ClassCastException, SQLException {
            String ConnectionString = "jdbc:h2:" + dbPath + "/" + dbName;
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            dbConnection = DriverManager.getConnection(ConnectionString, dbUser, dbPass);
            return dbConnection;
        }
        public ResultSet getUser(User user) {
            ResultSet rs = null;
            String select = "SELECT * FROM " + ConstantDB.USERS_TABLE + " WHERE " +
                    ConstantDB.USERS_LOGIN + " = ? AND " + ConstantDB.USERS_PASSWORD +
                    " = ? AND " + ConstantDB.USERS_ADMIN_STATUS + "= ?";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(select);
                prSt.setString(1, user.getLogin());
                prSt.setString(2, user.getPassword());
                prSt.setBoolean(3, user.getAdmin_status());
                rs = prSt.executeQuery();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return rs;
        }
        public void registerUser(User user) throws SQLException {
            ResultSet rs = getDbConnection().createStatement().executeQuery(
                    "SELECT COUNT(*) AS Qty FROM " + ConstantDB.USERS_TABLE);
            rs.next();
            int CounterID = rs.getInt(1) + 1;
            String insert = "INSERT INTO " + ConstantDB.USERS_TABLE + "(" +
                    ConstantDB.USERS_ID + "," + ConstantDB.USERS_LOGIN + "," +
                    ConstantDB.USERS_NAME + "," + ConstantDB.USERS_PASSWORD + "," +
                    ConstantDB.USERS_ADMIN_STATUS + ")" +
                    "VALUES(?, ?, ?, ?, ?)";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(insert);
                prSt.setInt(1, CounterID);
                prSt.setString(2, user.getLogin());
                prSt.setString(3, user.getName());
                prSt.setString(4, user.getPassword());
                prSt.setBoolean(5, user.getAdmin_status());
                prSt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        public void addTest(Test test) throws SQLException {
            ResultSet rs = getDbConnection().createStatement().executeQuery(
                    "SELECT COUNT(*) AS Qty FROM " + ConstantDB.TESTS_TABLE);
            rs.next();
            int CounterID = rs.getInt(1) + 1;
            String insert = "INSERT INTO " + ConstantDB.TESTS_TABLE + "(" +
                    ConstantDB.TESTS_ID + "," + ConstantDB.TESTS_NAME + "," +
                    ConstantDB.TESTS_QUESTIONS_LIST + ")" +
                    "VALUES(?, ?, ?)";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(insert);
                prSt.setInt(1, CounterID);
                prSt.setString(2, test.getName());
                prSt.setString(3, test.getQuestions_list());
                prSt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        public ResultSet getTest(Test test) {
            ResultSet rs = null;
            String select = "SELECT * FROM " + ConstantDB.TESTS_TABLE + " WHERE " +
                    ConstantDB.TESTS_NAME + " = ?";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(select);
                prSt.setString(1, test.getName());
                rs = prSt.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return rs;
        }

        public void eraseTest(Test test) {

            ResultSet rs = getTest(test);
            String erase = "DELETE FROM " + ConstantDB.TESTS_TABLE + " WHERE " +
                    ConstantDB.TESTS_ID + " = ?";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(erase);
                rs.next();
                prSt.setInt(1, rs.getInt(1));
                prSt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        public void addQuestion(Question question, Answer answer) throws SQLException, IllegalStateException {
            String answerText;
            switch (question.getType()) {
                case ConstantInput.SINGLE_TYPE:
                    answerText = answer.getSingle();
                    break;
                case ConstantInput.MULTIPLE_TYPE:
                    answerText = answer.getMultiple();
                    break;
                case ConstantInput.OPEN_TYPE:
                    answerText = answer.getOpen();
                    break;
                default: answerText = answer.getOpen();
                   // throw new IllegalStateException("Unexpected value: " + question.getType());
            }
            ResultSet rs = getDbConnection().createStatement().executeQuery(
                    "SELECT COUNT(*) AS Qty FROM " + ConstantDB.QUESTIONS_TABLE);
            rs.next();
            int CounterID = rs.getInt(1) + 1;
            String insert = "INSERT INTO " + ConstantDB.QUESTIONS_TABLE + "(" +
                    ConstantDB.QUESTIONS_ID + "," + ConstantDB.QUESTIONS_DIFFICULTY  +
                    "," + ConstantDB.QUESTIONS_TYPE + "," + ConstantDB.QUESTIONS_AUTHOR +
                    "," + ConstantDB.QUESTIONS_TEXT + "," + ConstantDB.QUESTIONS_ANSWER_TEXT + ')' +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(insert);
                prSt.setInt(1, CounterID);
                prSt.setString(2, question.getDifficulty());
                prSt.setString(3, question.getType());
                prSt.setString(4, question.getAuthorLogin());
                prSt.setString(5, question.getQuestionText());
                prSt.setString(6, answerText);
                prSt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        public ResultSet getQuestion(String questionId) {
            ResultSet rs = null;
            String select = "SELECT * FROM " + ConstantDB.QUESTIONS_TABLE + " WHERE " +
                    ConstantDB.QUESTIONS_ID + " = ?";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(select);
                prSt.setString(1, questionId);
                rs = prSt.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return rs;
        }
        public void eraseQuestion(String questionId) {
            String erase = "DELETE FROM " + ConstantDB.QUESTIONS_TABLE + " WHERE " + ConstantDB.QUESTIONS_ID + " = ?";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(erase);
                prSt.setString(1, questionId);
                prSt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }