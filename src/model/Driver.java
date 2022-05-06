package src.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Driver extends User{
    private List<Review> reviews = new ArrayList<Review>();
    private List<Order> livrari = new ArrayList<Order>();

    public Driver() {

    }

    public Driver(String username, String password, String email, String nume, String prenume, String nrTelefon) {
        super(username, password, email, nume, prenume, nrTelefon);
    }

    public Driver(String username, String password, String email, String nume, String prenume, String nrTelefon, List<Review> reviews, List<Order> livrari) {
        super(username, password, email, nume, prenume, nrTelefon);
        this.reviews = reviews;
        this.livrari = livrari;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void addlivrare(Order order) {
        livrari.add(order);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Order> getLivrari() {
        return livrari;
    }

    public void setLivrari(List<Order> livrari){
        this.livrari = livrari;
    }

    public String formatForCsv() {
        StringBuilder continutReviewsStr = new StringBuilder();
        for (Review r : reviews) {
            continutReviewsStr.append(r.getReviewId()).append(" ");
        }
        StringBuilder continutMenuStr = new StringBuilder();
        for (Order o : livrari) {
            continutMenuStr.append(o.getOrderId()).append(" ");
        }

        return userId +
                "," + username +
                "," + password +
                "," + email +
                "," + nume +
                "," + prenume +
                "," + nrTelefon +
                "," + continutReviewsStr +
                "," + continutMenuStr;
    }

    @Override
    public String toString() {
        return "src.classes.Driver{" +
                "id=" + userId +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", nrTelefon=" + nrTelefon +
                ", reviews=" + reviews +
                ", livrari=" + livrari +
                '}';
    }
}
