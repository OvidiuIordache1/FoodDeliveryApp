package src.model;

import java.util.Arrays;

public class Food {
    protected int foodId;
    protected String nume;
    protected String[] continut;
    protected String descriere;
    protected double pret;

    private static int nextId = 1;

    public Food() {

    }

    public Food(String nume, String[] continut, String descriere, double pret){
        this.foodId = nextId;
        nextId++;
        this.nume = nume;
        this.continut = continut;
        this.descriere = descriere;
        this.pret = pret;
    }

    public int getFoodId() { return foodId; }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String[] getContinut() {
        return continut;
    }

    public void setContinut(String[] continut) {
        this.continut = continut;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public String formatForCsv() {
        StringBuilder continutStr = new StringBuilder();
        for (String c : continut) {
            continutStr.append(c).append(" ");
        }
        return foodId +
                "," + nume +
                "," + continutStr.toString() +
                "," + descriere +
                "," + pret;
    }

    @Override
    public String toString() {
        return "{ " +
                "nume='" + nume + '\'' +
                ", continut='" + Arrays.toString(continut) + '\'' +
                ", descriere='" + descriere + '\'' +
                ", pret=" + pret +
                " } ";
    }
}
