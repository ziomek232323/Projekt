package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class ManualDateAssignFrame {
    JTable table;
    ArrayList<String> derbyMatches;
    FileManipulation fm = new FileManipulation();


    public void createAndShowUI() throws IOException {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 800);
        initComponents(frame);
        frame.setVisible(true);
    }

    private void initComponents(final JFrame frame) throws IOException {

        final JPanel panel = new JPanel();
        JComboBox cb = new JComboBox();
        BufferedReader bd = new BufferedReader(new FileReader("./src/list.txt"));
        String lines;
        int count = 0;
        ArrayList<String> matches = new ArrayList<>();
        while ((lines = bd.readLine()) != null) {
            matches.add(lines);
            count++;
        }
        if (matches.size() == 0)
            JOptionPane.showMessageDialog(null, "Please Generate a Schedule First.");

        Object[][] fixturess = new Object[count][2];


        int roundCount = 1;
        fixturess[0][0] = ("Round " + (roundCount) + "\n\n");
        for (int r = 1; r < matches.size(); r++) {
            fixturess[r][0] = matches.get(r);
            if (r != 1) {
                if (r % (10) == 0) {
                    fixturess[r][0] = ("Round " + (roundCount + 1) + "\n\n");
                    roundCount++;
                }
            }

        }


        String[] columns = {"Fixtures", "Slots"};


        table = new JTable(fixturess, columns);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 600));
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cb));
        //Populating the comboBox with slots
        List<String> dates = fm.getDatesList();
        cb.setModel(new DefaultComboBoxModel<>(dates.toArray()));

        JScrollPane scrollPane1 = new JScrollPane(table);
        panel.add(scrollPane1);
        frame.add(panel);


        PopulateComboBoxWithSlots(fixturess, dates, matches);
    }

    public void PopulateComboBoxWithSlots(Object fixtureList[][], List<String> listOfSlots, ArrayList<String> listOfMatches) throws IOException {
        String reusableSlots = "";
        String test;
        int reusableSlotsinINTS = 0;
        int itterator = 0;
        int Counter = listOfSlots.size();
        //System.out.print("size of counter" + Counter);


        for (int r = 1; r < listOfMatches.size();r++) {
            if (itterator == Counter) {
                itterator = 0;
            }

            reusableSlots = listOfSlots.get(itterator);
             test = reusableSlots.substring(reusableSlots.lastIndexOf(')') + 1);

            reusableSlotsinINTS = Integer.parseInt(test);
            //System.out.println("reusableslotsin intsssss = " + reusableSlotsinINTS);

            for(int i=0;i<reusableSlotsinINTS;i++) {
                fixtureList[r][1] = listOfSlots.get(itterator);

            }



            itterator++;
            if (r != 1) {
                if (r % (10) == 0) {
                    fixtureList[r][1] = ("Reset count");
                    itterator = 0;//reset slot counter each round
                }
            }


        }
        try {
            HighlightDerbyMatches(fixtureList, listOfMatches);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HighlightDerbyMatches(Object[][] fixtureList,ArrayList<String> listOfMatches) throws IOException {
        FileManipulation fm = new FileManipulation();
        try {
            fm.setDerbyMatches();
        } catch (IOException e) {
            e.printStackTrace();
        }
        derbyMatches = (ArrayList<String>) fm.getDerbyMatches();


        String temp1 = "";
        String temp2 = "";
        String [] seperatedTemp;
        for(int i = 0; i < derbyMatches.size();i++){
            temp1 = derbyMatches.get(i);
            seperatedTemp = temp1.split("vs");
            temp1 = seperatedTemp[0];
            temp2 = seperatedTemp[1];
            //System.out.println(derbyMatches.get(i));
            //System.out.println(temp1 + "********************" + temp2);

            /*
            int counter = 0;
            for(int x = 0; x <listOfMatches.size() ;x++){
                if(temp1 == fixtureList[x][0] && temp2 == fixtureList[x + 1][0]){
                    table.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                    System.out.println("Derby Teams matched " + counter);
                    counter++;
                }
                else{
                    //System.out.println("No teams matched");
                }
            }*/

            int roundCount = 1;
            int comparisonCounter = 0;
            ArrayList<String> tempList = new ArrayList<>();
            fixtureList[0][0] = ("Round " + (roundCount) + "\n\n");
            for (int r = 1; r < listOfMatches.size(); r++) {
                fixtureList[r][0] = listOfMatches.get(r);
                tempList.add(listOfMatches.get(r));
                if (r != 1) {
                    if (r % (10) == 0) {
                        for (int x =0;x<tempList.size();x++) {
                            System.out.println(tempList.get(x));
                        }
                        System.out.println("****************************************");

                        /*
                        for (int x =0;x<tempList.size();x++) {
                            if (temp1.toLowerCase().contains(tempList.get(x).toLowerCase()) || temp2.toLowerCase().contains(tempList.get(x).toLowerCase())) {
                                System.out.println(tempList.get(x));
                                comparisonCounter++;
                            }
                            if (comparisonCounter > 0) {
                                table.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                                System.out.println("Derby Teams matched ");
                            }


                        }
                        */

                      //  System.out.println("Clearing tempList **************");
                       tempList.clear();
                        fixtureList[r][0] = ("Round " + (roundCount + 1) + "\n\n");
                        roundCount++;

                    }
                }

            }














        }
    }



}
