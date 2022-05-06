package src.model;

import java.util.concurrent.atomic.AtomicInteger;

public class User {
    protected int userId;
    protected String username;
    protected String password;
    protected String email;
    protected String nume;
    protected String prenume;
    protected String nrTelefon;

    private static int nextId = 1;

    public User() {

    }

    public User(String username, String password, String email, String nume, String prenume, String nrTelefon) {
        this.userId = nextId;
        nextId++;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.nrTelefon = nrTelefon;
    }

    public int getUserId() { return userId; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

//    public String formatForCsv() {
//        return userId +
//                "," + username +
//                "," + password +
//                "," + email +
//                "," + nume +
//                "," + prenume +
//                "," + nrTelefon;
//    }

    @Override
    public String toString() {
        return "src.classes.User{" +
                "id=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", nrTelefon=" + nrTelefon +
                '}';
    }
}
