package kappa.buyme;

/**
 * Created by Taehyon on 6/22/2015.
 */

public class User {

    String firstname, lastname, username, password, email;

    public User(String fname, String lname, String email, String username, String password) {
        this.firstname = fname;
        this.lastname = lname;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail()
    {
        return email;
    }


    public User(String username, String password) {
        this("", "", "", username, password);
    }
}