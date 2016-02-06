package com.company;


import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixtureGenerating {
    private String[][] fixtures;
    public int datesRequired = 0;
    private List<String> list;
    FileManipulation fm = new FileManipulation();

    public void GenerateFixture() throws IOException{
        int numberOfTeams, totalNumberOfRounds, numberOfMatchesPerRound;
        int homeTeamNumber, awayTeamNumber,roundNumber, matchNumber;

        BufferedReader br = new BufferedReader(new FileReader("./src/testing.txt"));
        String line;
        int count = 0;
        List<String> temps = new ArrayList<String>();
        while ((line = br.readLine()) != null) {

            temps.add(line);


            count++;
        }
        br.close();

        //Algorithm to get fixtures
        numberOfTeams = count;
        totalNumberOfRounds = numberOfTeams - 1;
        numberOfMatchesPerRound = numberOfTeams / 2;


        fixtures = new String[totalNumberOfRounds][numberOfMatchesPerRound];

        for (roundNumber = 0; roundNumber < totalNumberOfRounds; roundNumber++) {
            for (matchNumber = 0; matchNumber < numberOfMatchesPerRound; matchNumber++) {

                homeTeamNumber = (roundNumber + matchNumber) % (numberOfTeams - 1);
                awayTeamNumber = (numberOfTeams - 1 - matchNumber + roundNumber) % (numberOfTeams - 1);
                if (matchNumber == 0)
                    awayTeamNumber = numberOfTeams - 1;

                fixtures[roundNumber][matchNumber] = (homeTeamNumber + 1) + " v " + (awayTeamNumber + 1);


                //names from file instead of numbers
                for (int index = 0; index < count; index++) {

                    fixtures[roundNumber][matchNumber] = (temps.get(homeTeamNumber)) + " v " + (temps.get(awayTeamNumber)+ ";");

                }


            }
        }

    }

    public String[][] getFixture(){

        return this.fixtures;
    }

    public void Convert2dTOArrayList() {
        List<String> list = new ArrayList<String>();
        for (String[] array : fixtures) {
            list.addAll(Arrays.asList(array));
        }
        int num = list.size();
        this.list = list;

        System.out.print("num =" + num);
        datesRequired = num;


    }



    public void PopulateListWithDates(String filePath) throws IOException {
        fm.readDatesFile();

        List <String> tempList = new ArrayList<>();
        tempList = fm.getDatesList();
        System.out.print("number of dates: " + tempList.size());
        System.out.print("    number of dates Required: " + datesRequired);

        if(tempList.size() < datesRequired){
           System.out.print("Need more dates");
        }
        else
            System.out.print("Populating list with dates");
    }
}




