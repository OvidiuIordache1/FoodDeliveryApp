package src.model;

import java.sql.Date;
import java.time.LocalDate;

public class Review {
    private int reviewId;
    private int userId;
    private String mesaj;
    private int nr;
    private Date data;

    private static int nextId = 1;

    public Review(int user, String mesaj, int nr, Date data) {
        this.reviewId = nextId;
        nextId++;
        this.userId = user;
        this.mesaj = mesaj;
        this.nr = nr;
        this.data = data;
    }

    public Review(int reviewId, int usrId, String mesaj, int nr, Date data) {
        this.reviewId = reviewId;
        this.userId = usrId;
        this.mesaj = mesaj;
        this.nr = nr;
        this.data = data;
    }

    public int getReviewId() { return reviewId; }

    public int getUser() {
        return userId;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String formatForCsv() {
        return  reviewId +
                "," + userId +
                "," + mesaj +
                "," + nr +
                "/5," + data;
    }

    @Override
    public String toString() {
        return "src.classes.Review{" +
                "user=" + userId +
                ", mesaj='" + mesaj + '\'' +
                ", nr=" + nr +
                "/5, data=" + data +
                '}';
    }
}
