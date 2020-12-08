package ar.com.naipes.reactorexamples;

public class User {

    public static final User SKYLER = new User("skyler", "Skyler", "White");
    public static final User JESSE = new User("jesse", "Jesse", "Pinkman");
    public static final User SAUL = new User("saul", "Saul", "Saul");

    private String username;
    private String firstname;
    private String lastname;

    public User(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
