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
    private int numberOfTeams, numberOfRounds, matchesPerRound;
    private int homeTeamNum, awayTeamNumber,roundNum, matchNumber;


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

        numberOfTeams = count;
        numberOfRounds = numberOfTeams - 1;
        matchesPerRound = numberOfTeams / 2;


        fixtures = new String[numberOfRounds][matchesPerRound];

        for (roundNum = 0; roundNum < numberOfRounds; roundNum++) {
            for (matchNumber = 0; matchNumber < matchesPerRound; matchNumber++) {

                homeTeamNum = (roundNum + matchNumber) % (numberOfTeams - 1);
                awayTeamNumber = (numberOfTeams - 1 - matchNumber + roundNum) % (numberOfTeams - 1);
                if (matchNumber == 0)
                    awayTeamNumber = numberOfTeams - 1;

                fixtures[roundNum][matchNumber] = (homeTeamNum + 1) + " v " + (awayTeamNumber + 1);
                 for (int index = 0; index < count; index++) {

                   fixtures[roundNum][matchNumber] = (temps.get(homeTeamNum)) + " v " + (temps.get(awayTeamNumber)+ ";");

                  }


            }
        }


        //Berger Table fix
        revisedFixtures = new String[numberOfRounds][matchesPerRound];
        int even = 0;
        int odd = numberOfTeams / 2;
        for (int i = 0; i < fixtures.length; i++) {
            if (i % 2 == 0)
                revisedFixtures[i] = fixtures[even++];
            else
                revisedFixtures[i] = fixtures[odd++];
        }
        fixtures = revisedFixtures;

        for (roundNum = 0; roundNum < fixtures.length; roundNum++) {
            if (roundNum % 2 == 1) {
                fixtureAsText = fixtures[roundNum][0];
                elementsOfFixture = fixtureAsText.split(" v ");
                fixtures[roundNum][0] = elementsOfFixture[1] + " v " + elementsOfFixture[0];
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
        int teamCount = 0;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if(count == (slotsAvailable)) {
                count = 0;
            }
            else if(teamCount == matchesPerRound){
                count =0;
            }
            list.add(line + " " + dates.get(count));
            count++;
            teamCount++;
        }
        br.close();
        Writer out = new BufferedWriter(new FileWriter("./src/list.txt"));
        out.append(null);

        for(int i = 0; i < list.size(); i++){
            //System.out.println(list.get(i));
        }
        MainFrame mf = new MainFrame();
        mf.DisplayScheduleWithDates(list, matchesPerRound);

    }






}






