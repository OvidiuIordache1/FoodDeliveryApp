package src.service;

import src.model.Address;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddressCsvService {
    private static AddressCsvService instance;
    private List<Address> adrese = new ArrayList<>();
    private File userFile;

    private AddressCsvService() {

    }

    public static AddressCsvService getInstance() {
        if (instance == null) {
            instance = new AddressCsvService();
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

    public void getAddressesFromCsv(){
        this.userFile = new File("src/resources/adrese.csv");
        List<String[]> data = getCSVCData(userFile);
        for (String[] line : data) {
            String adresaId = line[0];
            String judet = line[1];
            String localitate = line[2];
            String sector = line[3];
            String strada = line[4];
            String nr = line[5];
            String bloc = line[6];
            String scara = line[7];
            String etaj = line[8];
            String ap = line[9];
            Address adresa = new Address(judet, localitate, sector, strada, nr, bloc, scara, etaj, ap);
            adrese.add(adresa);
        }
    }

    private void writeToCsv(File userFile){
        try {
            FileWriter wr = new FileWriter(userFile);
            for (Address adresa: adrese) {
                String adresaStr = adresa.formatForCsv();
                wr.write(adresaStr);
                wr.write('\n');
            }
            wr.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void writeAddresesToCsv() {
        this.userFile = new File("src/resources/adrese.csv");
        if(!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeToCsv(userFile);
    }


    public List<Address> getAdrese()
    {
        return adrese;
    }

    public void setAdrese(List<Address> adrese) {
        this.adrese = adrese;
    }
}
