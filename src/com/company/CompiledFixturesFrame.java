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


    public void createAndShowCompiledFixturesFrame() throws IOException {
        JFrame frame = new JFrame("Compiled Schedule");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1100, 800);
        frame.setResizable(false);
        initComponents(frame);
        frame.setVisible(true);
    }


    private void initComponents(final JFrame frame) throws IOException {
        final JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        ManualDateAssignFrame mdaf = new ManualDateAssignFrame();
        FileManipulation fm = new FileManipulation();
        resultsTable = new JButton("Show Table");
        String[] columns = {"Fixtures", "Slots"};
        Object[][] rows = new Object[0][];
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        /*********************************************************************************************************/ //Adding components to the panel



        /************************************************************************************************************/ //Second half of Season
       firstHalfMatches = fm.readMatchData();
        firstHalfSeasonFixtures = new Object[firstHalfMatches.size()][2];

       for (int i = 0; i < firstHalfMatches.size(); i++) {
            firstHalfSeasonFixtures[i][0] = firstHalfMatches.get(i);


       }

        firstHalfSlots = fm.readSlotData();

        for (int i = 0; i < firstHalfSlots.size(); i++) {
            firstHalfSeasonFixtures[i][1] = firstHalfSlots.get(i);

        }
        /************************************************************************************************************/ //Second half of Season
        secondHalfMatches = fm.readMatchData();
        secondHalfSeasonFixtures = new Object[secondHalfMatches.size()][2];

            int x =0;
            for(int j=secondHalfMatches.size() -1;j>=0;j--){
                System.out.println(secondHalfMatches.get(j));
                secondHalfSeasonFixtures[x][0] = secondHalfMatches.get(j);
                x++;
            }




        secondHalfSlots = fm.readSlotData();

        int y =0;
        for(int j=secondHalfSlots.size() -1;j>=0;j--){
            System.out.println(secondHalfSlots.get(j));
            secondHalfSeasonFixtures[y][1] = secondHalfSlots.get(j);
            y++;
        }

        firstHalfSeason = new JTable(firstHalfSeasonFixtures,columns);
        secondHalfSeason = new JTable(secondHalfSeasonFixtures,columns);
        firstHalfSeason.setFillsViewportHeight(true);
        secondHalfSeason.setFillsViewportHeight(true);
        secondHalfSeason.setPreferredScrollableViewportSize(new Dimension(500, 600));
        firstHalfSeason.setPreferredScrollableViewportSize(new Dimension(500, 600));
        JScrollPane scrollPane1 = new JScrollPane(firstHalfSeason);
        scrollPane1.setBorder(BorderFactory.createTitledBorder("First Half of Season"));
        JScrollPane scrollPane12 = new JScrollPane(secondHalfSeason);
        scrollPane12.setBorder(BorderFactory.createTitledBorder("Second Half of Season"));
        //firstHalfSeason.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cb));

        panel.add(scrollPane1);
        panel1.add(scrollPane12);
        panel.add(panel1);
        panel.add(resultsTable);
        frame.add(panel);

        ColorTables();



        /********************************************************************************************************/ //Overriding button for results table
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
                    c.setBackground(Color.green);
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
                    b.setBackground(Color.orange);
                }





                return this;


            }



        });
    }
}
