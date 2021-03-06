package com.company;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CompiledFixturesFrame {
    FileManipulation fm = new FileManipulation();
    protected JTable firstHalfSeason;
    protected JTable secondHalfSeason;
    protected JTable fullSeason;
    protected JButton resultsTable;
    protected ArrayList<String> firstHalfMatches;
    protected ArrayList<String> firstHalfSlots;
    protected ArrayList<String> secondHalfMatches;
    protected ArrayList<String> secondHalfSlots;
    protected Object[][] firstHalfSeasonFixtures;
    protected Object[][] secondHalfSeasonFixtures;

//Initiate Frame components
    public void createAndShowCompiledFixturesFrame() throws IOException {
        JFrame frame = new JFrame("Compiled Schedule");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1300, 730);
        frame.setResizable(false);
        initComponents(frame);
        frame.setVisible(true);
    }


    private void initComponents(final JFrame frame) throws IOException {
        final JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        resultsTable = new JButton("Show Table");
        String[] columns = {"Fixtures", "Slots"};
        Object[][] rows = new Object[0][];
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));


        FirstSeasonGeneration();
        SecondHalfSeasonMatchCorrection();
        SecondHalfSeasonSlotCorrection();

        firstHalfSeason = new JTable(firstHalfSeasonFixtures,columns);
        secondHalfSeason = new JTable(secondHalfSeasonFixtures,columns);
        firstHalfSeason.setFillsViewportHeight(true);
        secondHalfSeason.setFillsViewportHeight(true);
        secondHalfSeason.setPreferredScrollableViewportSize(new Dimension(600, 600));
        firstHalfSeason.setPreferredScrollableViewportSize(new Dimension(600, 600));
        JScrollPane scrollPane1 = new JScrollPane(firstHalfSeason);
        scrollPane1.setBorder(BorderFactory.createTitledBorder("First Half of Season"));
        JScrollPane scrollPane12 = new JScrollPane(secondHalfSeason);
        scrollPane12.setBorder(BorderFactory.createTitledBorder("Second Half of Season"));

        panel.add(scrollPane1);
        panel1.add(scrollPane12);
        panel.add(panel1);
        panel.add(resultsTable);
        frame.add(panel);

        ColorTables();

    //Action Listener for Show table button
        resultsTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent r) {
                ResultsTable rt = new ResultsTable();
                try {
                    rt.createAndShowCompiledFixturesFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    private void SecondHalfSeasonSlotCorrection() throws IOException {

        secondHalfSlots = fm.readSlotsforSecondSeason();
        int matchesPerRound = fm.NumberOfTeams()/2;
        int count = secondHalfSlots.size();
        int sizeOfFixturess = (count + ((matchesPerRound*2)-1));
        int roundCounts = 1;
        int s = secondHalfSlots.size()-1;
        int c = 0;

        secondHalfSeasonFixtures[0][1] = ("Round " + (roundCounts) + "\n\n");
        for(int i = 0 ; i < sizeOfFixturess - count;i++) {//round count
            c++;
            for (int r = matchesPerRound - 1; r >= 0 ; r--) {//matches per round

                secondHalfSeasonFixtures[c][1] = secondHalfSlots.get(s);

                s--;
                c++;
            }
            roundCounts++;
            if(roundCounts == (sizeOfFixturess - count)+1){
                break;
            }
            secondHalfSeasonFixtures[c][1] = ("Round " + (roundCounts) + "\n\n");


        }
    }

    private void FirstSeasonGeneration() throws IOException {
        firstHalfMatches = fm.readMatchData();
        firstHalfSeasonFixtures = new Object[firstHalfMatches.size()][2];

        for (int i = 0; i < firstHalfMatches.size(); i++)
        {
            firstHalfSeasonFixtures[i][0] = firstHalfMatches.get(i);
        }

        firstHalfSlots = fm.readSlotData();

        for (int i = 0; i < firstHalfSlots.size(); i++)
        {
            firstHalfSeasonFixtures[i][1] = firstHalfSlots.get(i);
        }
    }

    public void ColorTables(){
        firstHalfSeason.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){


            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String status = (String) table.getModel().getValueAt(row, col);

                if (status.contains("Round")) {
                    c.setBackground(Color.YELLOW);
                    c.setFont(new Font("Dialog", Font.BOLD, 14));
                }
                else {
                    c.setBackground(Color.RED);
                    c.setFont(new Font("Dialog", Font.BOLD, 14));
                }
                return this;

            }
        });


        secondHalfSeason.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){


            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                Component b = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String status = (String) table.getModel().getValueAt(row, col);

                if (status.contains("Round")) {
                    b.setBackground(Color.YELLOW);
                    b.setFont(new Font("Dialog", Font.BOLD, 14));
                }
                else {
                    b.setBackground(Color.cyan);
                    b.setFont(new Font("Dialog", Font.BOLD, 14));
                }
                return this;
            }

        });
    }

    public void SecondHalfSeasonMatchCorrection() throws IOException {
        secondHalfMatches = fm.readMatchDataforSecondSeason();
        int matchesPerRound = fm.NumberOfTeams()/2;
        System.out.println("Matches per round: " + matchesPerRound);
        int count = secondHalfMatches.size();
                System.out.println("count : " + count);
        int sizeOfFixturess = (count + ((matchesPerRound*2)-1));
        System.out.println("Size of Fixtures: " + sizeOfFixturess);

        int roundCount = 1;
        int x = secondHalfMatches.size()-1;
        int z = 0;
        secondHalfSeasonFixtures = new Object[sizeOfFixturess][2];
        secondHalfSeasonFixtures[0][0] = ("Round " + (roundCount) + "\n\n");
        for(int i = 0 ; i < sizeOfFixturess - count;i++) {//round count
            z++;
            for (int r = matchesPerRound - 1; r >= 0 ; r--) {//matches per round

                secondHalfSeasonFixtures[z][0] = secondHalfMatches.get(x);
                x--;
                z++;
            }
            roundCount++;
            if(roundCount == (sizeOfFixturess - count)+1){
                break;
            }
            secondHalfSeasonFixtures[z][0] = ("Round " + (roundCount) + "\n\n");


        }

    }
}