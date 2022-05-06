package src.model;

import java.util.ArrayList;
import java.util.List;

public class Client extends User {
    private List<Order> comenzi = new ArrayList<Order>();
    private List<Address> adrese = new ArrayList<Address>();

    public Client(String username, String password, String email, String nume, String prenume, String nrTelefon) {
        super(username, password, email, nume, prenume, nrTelefon);
    }

    public Client(String username, String password, String email, String nume, String prenume, String nrTelefon, List<Order> comenzi, List<Address> adrese) {
        super(username, password, email, nume, prenume, nrTelefon);
        this.comenzi = comenzi;
        this.adrese = adrese;
    }

    public List<Order> getComenzi() { return comenzi; }

    public void setComenzi(List<Order> comenzi) { this.comenzi = comenzi; }

    public List<Address> getAdrese() { return adrese; }

    public void showAdrese() {
        int i = 1;
        for (Address adr:adrese) {
            System.out.println(i + " " + adr);
            i++;
        }
    }

    public void setAdrese(List<Address> adrese) { this.adrese = adrese; }

    public void addAdresa(Address adresa) {
            this.adrese.add(adresa);
    }

    public void addComanda(Order order) {
        this.comenzi.add(order);
    }

    public String formatForCsv() {
        StringBuilder continutOrderStr = new StringBuilder();
        for (Order o : comenzi) {
            continutOrderStr.append(o.getOrderId()).append(" ");
        }
        StringBuilder continutAdreseStr = new StringBuilder();
        for (Address a : adrese) {
            continutAdreseStr.append(a.getAdresaId()).append(" ");
        }
        return userId +
                "," + username +
                "," + password +
                "," + email +
                "," + nume +
                "," + prenume +
                "," + nrTelefon +
                "," + continutOrderStr +
                "," + continutAdreseStr;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", nrTelefon=" + nrTelefon +
                ",\n comenzi=" + comenzi +
                ",\n adrese=" + adrese +
                "\n}";
    }
}
