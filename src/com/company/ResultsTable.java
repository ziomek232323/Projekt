package com.company;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ResultsTable {
        JTable resultsTable;
        JLabel seasonInfo;
        JLabel numOfMatchesEach;
        JLabel numOfTeams;
        JLabel totalNumOfMatches;
        FileManipulation fm = new FileManipulation();
        int numberofTeams = 0;




        public void createAndShowCompiledFixturesFrame() throws IOException {
            JFrame frame = new JFrame("Results Table");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1280, 600);
            frame.setResizable(false);
            initComponents(frame);
            frame.setVisible(true);
        }

        private void initComponents(final JFrame frame) throws IOException {
            final JPanel panel = new JPanel();
            JPanel panel1 = new JPanel();
            Box box = Box.createVerticalBox();
             numberofTeams = fm.NumberOfTeams();
            String col[] = {"Pos","Team","P", "W", "L", "D", "MP", "GF", "GA", "GD"};
            Object[][] rows = new Object[0][];
            DefaultTableModel tableModel = new DefaultTableModel(col, 0);
            panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));

            /*********************************************************************************************************/ //Adding components to the panel

            seasonInfo = new JLabel("Season Information");
            numOfMatchesEach = new JLabel("Number of Matches Each = " + ((numberofTeams*2) - 2) );
            numOfTeams = new JLabel("Number of Teams = " + numberofTeams);
            totalNumOfMatches = new JLabel("Total Number of Matches = " + (((numberofTeams*2) - 2)* (numberofTeams/2)));




            resultsTable = new JTable(tableModel);
            resultsTable.setFillsViewportHeight(true);
            resultsTable.setPreferredScrollableViewportSize(new Dimension(1200, 300));
            JScrollPane scrollPane1 = new JScrollPane(resultsTable);
            scrollPane1.setBorder(BorderFactory.createTitledBorder("First Half of Season"));
            //firstHalfSeason.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cb));

            panel.add(scrollPane1);
            seasonInfo.setFont(new Font("Dialog", Font.BOLD, 16));
            seasonInfo.setForeground(Color.red);
            numOfTeams.setFont(new Font("Dialog", Font.BOLD, 16));
            numOfTeams.setForeground(Color.black);
            numOfMatchesEach.setFont(new Font("Dialog", Font.BOLD, 16));
            numOfMatchesEach.setForeground(Color.black);
            totalNumOfMatches.setFont(new Font("Dialog", Font.BOLD, 16));
            totalNumOfMatches.setForeground(Color.black);
            box.add(seasonInfo);
            box.add(numOfTeams);
            box.add(numOfMatchesEach);
            box.add(totalNumOfMatches);
            panel1.add(box);
            panel.add(panel1);
            frame.add(panel);


            ArrayList<FootballClub> originalLeagueList = new ArrayList<FootballClub>();

            originalLeagueList.add(new FootballClub(1, "Arsenal", 35, 11, 2, 2, 15, 30, 11, 19));
            originalLeagueList.add(new FootballClub(2, "Liverpool", 30, 9, 3, 3, 15, 34, 18, 16));
            originalLeagueList.add(new FootballClub(3, "Chelsea", 30, 9, 2, 2, 15, 30, 11, 19));
            originalLeagueList.add(new FootballClub(4, "Man City", 29, 9, 2, 4, 15, 41, 15, 26));
            originalLeagueList.add(new FootballClub(5, "Everton", 28, 7, 1, 7, 15, 23, 14, 9));
            originalLeagueList.add(new FootballClub(6, "Tottenham", 27, 8, 4, 3, 15, 15, 16, -1));
            originalLeagueList.add(new FootballClub(7, "Newcastle", 26, 8, 5, 2, 15, 20, 21, -1));
            originalLeagueList.add(new FootballClub(8, "Southampton", 23, 6, 4, 5, 15, 19, 14, 5));
            originalLeagueList.add(new FootballClub(8, "Southampton", 23, 6, 4, 5, 15, 19, 14, 5));



            for (int i = 0; i < originalLeagueList.size(); i++){
                int position = originalLeagueList.get(i).getPosition();
                String name = originalLeagueList.get(i).getName();
                int points = originalLeagueList.get(i).getPoints();
                int wins = originalLeagueList.get(i).getWins();
                int defeats = originalLeagueList.get(i).getDefeats();
                int draws = originalLeagueList.get(i).getDraws();
                int totalMatches = originalLeagueList.get(i).getTotalMathces();
                int goalF = originalLeagueList.get(i).getGoalF();
                int goalA = originalLeagueList.get(i).getGoalA();
                int ttgoalD = originalLeagueList.get(i).getTtgoalD();

                Object[] data = {position, name, points, wins, defeats, draws,
                        totalMatches, goalF, goalA, ttgoalD};

                tableModel.addRow(data);

            }
            ColorResultsTable();


            /********************************************************************************************************/ //Overriding button for results table








            }

    private void ColorResultsTable() {

        resultsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){


            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                Component b = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //String status = (String) table.getModel().getValueAt(row, col);
//                String number = (String)value;
//                int val = Integer.parseInt(number);

                if (row < 5 && col == 0) {
                    b.setBackground(Color.blue);
                    //b.setFont(new Font("Dialog", Font.BOLD, 14));
                }
                else if(row == 5 && col == 0){
                    b.setBackground(Color.orange);
                }
                else if(row == (numberofTeams - 3) && col == 0){
                    b.setBackground(Color.RED);
                }
                else if(row == (numberofTeams - 2) && col == 0){
                    b.setBackground(Color.RED);
                }
                else if(row == (numberofTeams - 1) && col == 0){
                    b.setBackground(Color.RED);
                }
                else {
                    b.setBackground(Color.white);
                   // b.setFont(new Font("Dialog", Font.BOLD, 14));
                }






                return this;


            }



        });
    }
}


