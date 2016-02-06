package com.company;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManipulation {
    private List<String> dates;
    private List<String> teams;

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

    public List<String> getDatesList(){

        return this.dates;
    }

    public List<String> getTeamsList(){

        return this.teams;
    }


}
