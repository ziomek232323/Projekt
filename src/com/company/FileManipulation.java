package com.company;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManipulation {
    private List<String> dates;
    private List<String> teams;
    private ArrayList<String> derbyFixtures;

    public void readDatesFile() throws IOException{
        File file = new File("./src/dates.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        br.close();
        dates = list;
    }

    public ArrayList<String> readTeamListFile() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("./src/testing.txt"));
        String line;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        br.close();
        teams = list;
        return list;
    }

    public int NumberOfTeams() throws IOException{
        File file = new File("./src/testing.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int count=0;
        while ((line = br.readLine()) != null) {
            count++;
        }
        br.close();
        return count;
    }
    public int NumberOfSlotsAvailable() throws IOException{
        File file = new File("./src/dates.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int count=0;
        while ((line = br.readLine()) != null) {
            count++;
        }
        br.close();
        return count;
    }

    public List<String> getDatesList() throws IOException {
        try {
            readDatesFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.dates;
    }

    public List<String> getTeamsList(){

        return this.teams;
    }

    public ArrayList<String> getDerbyMatches(){

        return this.derbyFixtures;
    }

    public void setDerbyMatches(String filePaths)throws IOException{
        File file = new File(filePaths);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> derbyList = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            derbyList.add(line);
        }
        br.close();
        derbyFixtures = derbyList;

    }
    public ArrayList<String> readMatchData () throws  IOException{
        File file = new File("./src/data2.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if(line != "") {
                list.add(line);
            }
            list.removeAll(Arrays.asList("", null));
        }
        br.close();

        return list;
    }

    public ArrayList<String> readSlotData() throws  IOException{
        File file = new File("./src/data.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if(line != "") {
                list.add(line);
            }
            list.removeAll(Arrays.asList("", null));
        }
        br.close();

        return list;
    }

    public ArrayList<String> readMatchDataforSecondSeason () throws  IOException{
        File file = new File("./src/data2.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if(line != "" && !line.contains("Round")) {
                list.add(line);
            }
            list.removeAll(Arrays.asList("", null));
        }
        br.close();

        return list;
    }
    public ArrayList<String> readSlotsforSecondSeason () throws  IOException{
        File file = new File("./src/data.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if(line != "" && !line.contains("Round")) {
                list.add(line);
            }
            list.removeAll(Arrays.asList("", null));
        }
        br.close();

        return list;
    }

}