package com.company;

import java.util.ArrayList;



public class FootballClub extends SportsClub {


    FootballClub(int position, String name, int points, int wins, int defeats, int draws, int totalMatches, int goalF, int goalA, int goalD) {
        this.position = position;
        this.name = name;
        this.points = points;
        this.wins = wins;
        this.defeats = defeats;
        this.draws = draws;
        this.totalMatches = totalMatches;
        this.goalF = goalF;
        this.goalA = goalA;
        this.goalD = goalD;

    }
    public int getPosition(){ return position; }
    public String getName(){ return name; }
    public int getPoints(){ return points; }
    public int getWins(){ return wins; }
    public int getDefeats(){ return defeats; }
    public int getDraws(){ return draws; }
    public int getTotalMathces(){ return totalMatches; }
    public int getGoalF(){ return goalF; }
    public int getGoalA(){ return goalA; }
    public int getTtgoalD(){ return goalD; }
}