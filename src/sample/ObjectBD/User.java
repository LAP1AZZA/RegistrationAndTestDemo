package sample.ObjectBD;

public class User {
    private String login;
    private String name;
    private String password;
    private boolean admin_status;

    public User(String login, String name, String password, boolean admin_status) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.admin_status = admin_status;
    }

    public User() {
    }

    public String getLogin() {
        return this.login;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getAdmin_status() {
        return this.admin_status;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin_status(boolean admin_status) {
        this.admin_status = admin_status;
    }
}
