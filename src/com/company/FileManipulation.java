package com.company;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManipulation {
    private List<String> dates;
    private List<String> teams;
    private ArrayList<String> derbyFixtures;

    public void readDatesFile() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("./src/dates.txt"));
        String line;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        br.close();
        dates = list;
    }

    public void readTeamListFile() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("./src/testing.txt"));
        String line;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        br.close();
        teams = list;
    }

    public int NumberOfTeams() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("./src/testing.txt"));
        String line;
        int count=0;
        while ((line = br.readLine()) != null) {
           count++;
        }
        br.close();
        return count;
    }
    public int NumberOfSlotsAvailable() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("./src/dates.txt"));
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

    public void setDerbyMatches()throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("./src/derby.txt"));
        String line;
        ArrayList<String> derbyList = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            derbyList.add(line);
        }
        br.close();
        derbyFixtures = derbyList;

    }


}
