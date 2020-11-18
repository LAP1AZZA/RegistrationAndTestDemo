package sample.ObjectBD;

public class Test {
    private String name;
    private String questions_list;

    public Test(String name, String questions_list) {
        this.name = name;
        this.questions_list = questions_list;
    }

    public Test(String name) {
        this.name = name;
    }

    public Test() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestions_list() {
        return this.questions_list;
    }

    public void setQuestions_list(String questions_list) {
        this.questions_list = questions_list;
    }
}
