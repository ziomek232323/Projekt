package com.company;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixtureGenerating {
    private String[][] fixtures;
    String[][] revisedFixtures;
    String[] elementsOfFixture;
    String fixtureAsText;
    public int datesRequired = 0;
    private List<String> lists;
    FileManipulation fm = new FileManipulation();
    private int numberOfTeams, totalNumberOfRounds, numberOfMatchesPerRound;
    private int homeTeamNumber, awayTeamNumber,roundNumber, matchNumber;


    public void GenerateFixture() throws IOException{



        BufferedReader br = new BufferedReader(new FileReader("./src/testing.txt"));
        String line;
        int count = 0;
        List<String> temps = new ArrayList<>();
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
                //0
                homeTeamNumber = (roundNumber + matchNumber) % (numberOfTeams - 1);

                //19
                awayTeamNumber = (numberOfTeams - 1 - matchNumber + roundNumber) % (numberOfTeams - 1);
                if (matchNumber == 0)
                    awayTeamNumber = numberOfTeams - 1;

                fixtures[roundNumber][matchNumber] = (homeTeamNumber + 1) + " v " + (awayTeamNumber + 1);



                 for (int index = 0; index < count; index++) {

                   fixtures[roundNumber][matchNumber] = (temps.get(homeTeamNumber)) + " v " + (temps.get(awayTeamNumber)+ ";");

                  }


            }
        }
        revisedFixtures = new String[totalNumberOfRounds][numberOfMatchesPerRound];
        int even = 0;
        int odd = numberOfTeams / 2;
        for (int i = 0; i < fixtures.length; i++) {
            if (i % 2 == 0)
                revisedFixtures[i] = fixtures[even++];
            else
                revisedFixtures[i] = fixtures[odd++];
        }
        fixtures = revisedFixtures;

        for (roundNumber = 0; roundNumber < fixtures.length; roundNumber++) {
            if (roundNumber % 2 == 1) {
                fixtureAsText = fixtures[roundNumber][0];
                elementsOfFixture = fixtureAsText.split(" v ");
                fixtures[roundNumber][0] = elementsOfFixture[1] + " v " + elementsOfFixture[0];
            }
        }
        setFixturesArray(fixtures);

    }

    public String[][] getFixture(){

        return this.fixtures;
    }

    public void Convert2dTOArrayList() throws IOException {
        List<String> list = new ArrayList<>();
        for (String[] array : fixtures) {
            list.addAll(Arrays.asList(array));
        }
        int num = list.size();


        Writer out = new BufferedWriter(new FileWriter("./src/list.txt"));
         for (int i =0; i <list.size();i++){

             out.append(list.get(i) + "\n");
           }


        out.close();


        SetList(list);
        datesRequired = num;


    }

    public void SetList(List list){
       this.lists = list;
    }

    public List GetList(){
        return this.lists;
    }

    public void setFixturesArray (String [] [] list){
        this.fixtures = list;
    }


    public void PopulateListWithDates(String filePath) throws IOException {
        fm.readDatesFile();


        List<String> dateList = new ArrayList<>();
        dateList = fm.getDatesList();


    }

    public void AssignSlots() throws IOException {
        FileManipulation fm = new FileManipulation();
        int slotsAvailable = 0;
        int matchesPerRound = 0;
        try {
             matchesPerRound = (fm.NumberOfTeams())/2;
             slotsAvailable = fm.NumberOfSlotsAvailable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Number of matches per round: " + matchesPerRound );
        System.out.println("Number of slots available: " + slotsAvailable );




        //read list file
        BufferedReader br = new BufferedReader(new FileReader("./src/list.txt"));
        BufferedReader bd = new BufferedReader(new FileReader("./src/dates.txt"));
        String line;
        String lines;
        ArrayList<String> dates = new ArrayList<>();
        while ((lines = bd.readLine()) != null) {
            dates.add(lines);

        }
        int count=0;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if(count == (slotsAvailable - 1)) {
                count = 0;
            }
            list.add(line + " " + dates.get(count));
            count++;
        }
        br.close();


        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }

    }





}






