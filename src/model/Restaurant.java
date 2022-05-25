package src.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Restaurant {
    protected int restaurantId;
    private String nume;
    private Address adresa;
    private String nrTelefon;
    private List<Food> meniu = new ArrayList<Food>();
    private List<Review> reviews = new ArrayList<Review>();

    private static int nextId = 1;

    public Restaurant(int restaurantId, String nume, Address adresa, String nrTelefon, List<Food> meniu, List<Review> reviews){
        this.restaurantId = restaurantId;
        this.nume = nume;
        this.adresa = adresa;
        this.nrTelefon = nrTelefon;
        this.meniu = meniu;
        this.reviews = reviews;
    }

    public Restaurant(String nume, Address adresa, String nrTelefon, List<Food> meniu, List<Review> reviews){
        this.restaurantId = nextId;
        nextId++;
        this.nume = nume;
        this.adresa = adresa;
        this.nrTelefon = nrTelefon;
        this.meniu = meniu;
        this.reviews = reviews;
    }

    public Restaurant(String nume, Address adresa, String nrTelefon, List<Food> meniu){
        this.restaurantId = nextId;
        nextId++;
        this.nume = nume;
        this.adresa = adresa;
        this.nrTelefon = nrTelefon;
        this.meniu = meniu;
    }

    public String getRestaurantId() { return String.valueOf(restaurantId); }

    public int getRestaurantId_() { return restaurantId; }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Address getAdresa() {
        return adresa;
    }

    public void setAdresa(Address adresa) {
        this.adresa = adresa;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public List<Food> getMeniu() {
        return meniu;
    }

    public void setMeniu(List<Food> meniu) {
        this.meniu = meniu;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public double getAvgReview() {
        double suma = 0;
        for (Review review: reviews)
        {
            suma += review.getNr();
        }
        if (reviews.size() > 0) {
            return suma / reviews.size();
        }
        return suma;
    }

    public void addReview(Review review) {
        if (this.reviews == null) {
            List<Review> newList = new ArrayList<Review>();
            newList.add(review);
            this.reviews = newList;
        }
        else {
            this.reviews.add(review);
        }
    }

    public String formatForCsv() {
        StringBuilder continutMenuStr = new StringBuilder();
        for (Food f : meniu) {
            continutMenuStr.append(f.getFoodId()).append(" ");
        }
        StringBuilder continutReviewsStr = new StringBuilder();
        for (Review r : reviews) {
            continutReviewsStr.append(r.getReviewId()).append(" ");
        }
        return restaurantId +
                "," + nume +
                "," + adresa.getAdresaId() +
                "," + nrTelefon +
                "," + continutMenuStr +
                "," + continutReviewsStr;
    }

    public String meniuToString() {
        StringBuilder meniuStr = new StringBuilder();
        int i = 1;
        for (Food f : meniu) {
            meniuStr.append(i).append(". ").append(f).append("\n");
            i += 1;
        }
        return meniuStr.toString();
    }

    @Override
    public String toString() {
        return "Restaurant { \n" +
                "id=" + restaurantId + '\'' +
                "nume='" + nume + '\'' +
                ",\nadresa=" + adresa +
                ",\nnrTelefon=" + nrTelefon + + '\'' +
                ",\nmeniu=[\n" + meniuToString() +
                "],\nreviews=" + reviews +
                '}';
    }
}

