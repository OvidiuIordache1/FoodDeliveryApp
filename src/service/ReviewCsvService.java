package src.service;

import src.model.Review;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReviewCsvService {
    private static ReviewCsvService instance;
    private List<Review> reviews = new ArrayList<>();
    private File userFile;

    private ReviewCsvService() {

    }

    public static ReviewCsvService getInstance() {
        if (instance == null) {
            instance = new ReviewCsvService();
        }
        return instance;
    }

    private List<String[]> getCSVCData(File fileName){
        List<String[]> data = new ArrayList<>();
        try(var in = new BufferedReader(new FileReader(fileName))) {

            String line;
            while((line = in.readLine()) != null ) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Nothing saved in file.");
        }
        return data;
    }

    public void getReviewsFromCsv(){
        this.userFile = new File("src/resources/reviews.csv");
        List<String[]> data = getCSVCData(userFile);
        for (String[] line : data) {
            String reviewId = line[0];
            int userId = Integer.parseInt(line[1]);
            String mesaj = line[2];
            int nr = Integer.parseInt(line[3].split("/")[0]);
            LocalDate data_ = LocalDate.parse(line[4]);
            Review review = new Review(userId, mesaj, nr, data_);
            reviews.add(review);
        }
    }

    private void writeToCsv(File userFile){
        try {
            FileWriter wr = new FileWriter(userFile);
            for (Review review: reviews) {
                String reviewStr = review.formatForCsv();
                wr.write(reviewStr);
                wr.write('\n');
            }
            wr.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void writeReviewsToCsv() {
        this.userFile = new File("src/resources/reviews.csv");
        if(!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeToCsv(userFile);
    }

    public List<Review> getReviews()
    {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
