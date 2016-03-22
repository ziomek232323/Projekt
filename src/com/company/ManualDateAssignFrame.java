package com.company;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class ManualDateAssignFrame {
    JTable table;
    ArrayList<String> derbyMatches;
    int matchesPerRound;
    FileManipulation fm = new FileManipulation();


    public void createAndShowUI() throws IOException {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 800);
        initComponents(frame);
        frame.setVisible(true);
    }

    private void initComponents(final JFrame frame) throws IOException {
        FileManipulation fm = new FileManipulation();
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
        matchesPerRound = fm.NumberOfTeams()/2;
        int sizeOfFixturess = (count + ((matchesPerRound*2)-1));

       /***************************************************************************/

        Object[][] fixturess = new Object[count + ((matchesPerRound*2)-1)][2];


        int roundCount = 1;
        int x = 0;
        int z = 0;
        fixturess[0][0] = ("Round " + (roundCount) + "\n\n");
        for(int i = 0 ; i < sizeOfFixturess - count;i++) {//round count
            z++;
            for (int r = 0; r < matchesPerRound ; r++) {//matches per round


                fixturess[z][0] = matches.get(x);
                //System.out.println(matches.get(x));



                x++;
                z++;
            }
            roundCount++;
            if(roundCount == (sizeOfFixturess - count)+1){
                break;
            }
            fixturess[z][0] = ("Round " + (roundCount) + "\n\n");




        }





/***********************************************************************************/
        String[] columns = {"Fixtures", "Slots"};


        table = new JTable(fixturess,columns);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 600));
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cb));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String status = (String)table.getModel().getValueAt(8, 0);


                if ("Liverpool v Manchester City;".contains(status)) {
                    //table.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                    //System.out.println(status + "********************************");
                   // c.setBackground(Color.RED);
                   // model.setRowColour(8, Color.YELLOW);
                   // setBackground(Color.BLACK);
                   // setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                }
                return this;
            }
        });



        //Populating the comboBox with slots
        List<String> dates = fm.getDatesList();
        cb.setModel(new DefaultComboBoxModel<>(dates.toArray()));

        JScrollPane scrollPane1 = new JScrollPane(table);
        panel.add(scrollPane1);
        frame.add(panel);


        PopulateComboBoxWithSlots(fixturess, dates, matches, sizeOfFixturess, count);
    }
    public String  getReusableSlot(List<String> listOfSlots, int value){
        String reusableSlots = "";
        String test;
        int reusableSlotsinINTS = 0;

        int Counter = listOfSlots.size();

        if(value == Counter)
        {
            value = 0;
        }

        reusableSlots = listOfSlots.get(value);

        test = reusableSlots.substring(reusableSlots.lastIndexOf(')') + 1);
        reusableSlotsinINTS = Integer.parseInt(test);

        return reusableSlots;


    }

    public int  getReusableSlotinInts(List<String> listOfSlots){
        String reusableSlots = "";
        String test;
        int reusableSlotsinINTS = 1;
         int itterator = 0;
        int Counter = listOfSlots.size();

        if(itterator == Counter)
        {
            itterator = 0;
        }

        reusableSlots = listOfSlots.get(itterator);

        test = reusableSlots.substring(reusableSlots.lastIndexOf(')') + 1);
        reusableSlotsinINTS = Integer.parseInt(test);

        return reusableSlotsinINTS;


    }

    public void PopulateComboBoxWithSlots(Object fixtureList[][], List<String> listOfSlots, ArrayList<String> listOfMatches, int numberOfFixtures, int counter) throws IOException {
        String reusableSlots = "";
        String test;
        int reusableSlotsinINTS = 0;
        int itterator = 0;
        int Counter = listOfSlots.size();






        int roundCount = 1;
        int x = 0;
        int z = 0;
        int p = 0;
        fixtureList[0][1] = ("Round " + (roundCount) + "\n\n");
        for(int i = 0 ; i < numberOfFixtures - counter;i++) {//round count
            z++;

            if(itterator == Counter)
            {
                itterator = 0;
            }

            reusableSlots = listOfSlots.get(itterator);

            test = reusableSlots.substring(reusableSlots.lastIndexOf(')') + 1);
            reusableSlotsinINTS = Integer.parseInt(test);



            for (int r = 0; r < matchesPerRound ; r++) {//matches per round

            p = itterator;





                if(reusableSlotsinINTS == x){
                    p=p +1;
                    if(p == listOfSlots.size() || p >listOfSlots.size()){
                        p = 0;
                    }
                    reusableSlots = listOfSlots.get(p);
                }

                    fixtureList[z][1] = reusableSlots;






                z++;
                x++;

                if(x == matchesPerRound)
                {
                    x = 0;
                }

            }
            roundCount++;

           itterator ++;
            if(roundCount == (numberOfFixtures - counter)+1){
                break;
           }
            fixtureList[z][1] = ("Round " + (roundCount) + "\n\n");




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
            }



            int roundCount = 1;
            int comparisonCounter = 0;
            ArrayList<String> tempList = new ArrayList<>();
            fixtureList[0][0] = ("Round " + (roundCount) + "\n\n");
            for (int r = 1; r < listOfMatches.size(); r++) {
                fixtureList[r][0] = listOfMatches.get(r-1);
                tempList.add(listOfMatches.get(r-1));
                System.out.println(listOfMatches.get(r-1));
                if (r != 1) {
                    if (r % (matchesPerRound) == 0) {
                         for (int x =0;x<tempList.size();x++) {
                             System.out.println(tempList.get(x));
                         }
                         System.out.println("****************************************");


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


                        System.out.println(" **************");
                         tempList.clear();
                        fixtureList[r][0] = ("Round " + (roundCount + 1) + "\n\n");
                        roundCount++;

                    }
                }

            }
        */














        }
    }



}
