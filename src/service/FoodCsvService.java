package src.service;

import src.model.Food;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FoodCsvService {
    private static FoodCsvService instance;
    private List<Food> foods = new ArrayList<>();
    private File userFile;

    private FoodCsvService() {

    }

    public static FoodCsvService getInstance() {
        if (instance == null) {
            instance = new FoodCsvService();
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

    public void getFoodsFromCsv(){
        this.userFile = new File("src/resources/foods.csv");
        List<String[]> data = getCSVCData(userFile);
        for (String[] line : data) {
            String foodId = line[0];
            String nume = line[1];
            String[] continutStr = line[2].split(" ");
            String descriere = line[3];
            String pret = line[4];
            Food food = new Food(nume, continutStr, descriere, Double.parseDouble(pret));
            foods.add(food);
        }
    }

    private void writeToCsv(File userFile, String restaurantId){
        try {
            FileWriter wr = new FileWriter(userFile);
            for (Food food: foods) {
                String foodStr = food.formatForCsv();
                wr.write(foodStr);
//                wr.write(",");
//                wr.write(restaurantId);
                wr.write('\n');
            }
            wr.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void writeFoodsToCsv(String restaurantId) {
        this.userFile = new File("src/resources/foods.csv");
        if(!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeToCsv(userFile, restaurantId);
    }

    public List<Food> getFoods()
    {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
