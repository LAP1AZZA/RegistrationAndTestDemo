package sample.ObjectBD;

public class Answer {
    private String single;
    private String multiple;
    private String open;

    public Answer(String text) {
        this.single = text;
        this.multiple = text;
        this.open = text;
    }

    public String getSingle() {
        return this.single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getMultiple() {
        return this.multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getOpen() {
        return this.open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}
