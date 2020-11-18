package sample.ObjectBD;

public class Question {
    private String type;
    private String author_login;
    private String difficulty;
    private String question_text;

    public Question(String type, String authorLogin, String difficulty, String questionText) {
        this.type = type;
        this.author_login = authorLogin;
        this.difficulty = difficulty;
        this.question_text = questionText;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthorLogin() {
        return this.author_login;
    }

    public void setAuthorLogin(String authorLogin) {
        this.author_login = authorLogin;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestionText() {
        return this.question_text;
    }

    public void setQuestionText(String questionText) {
        this.question_text = questionText;
    }
}
