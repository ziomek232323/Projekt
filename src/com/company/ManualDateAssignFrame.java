package com.company;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class ManualDateAssignFrame {
    JTable table;
    ArrayList<String> derbyMatches;
    int matchesPerRound;
    FileManipulation fm = new FileManipulation();
    public JTextArea derbyList;
    private String derbyFilePath ="";
    boolean isPressed = false;


    public void createAndShowUI() throws IOException {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 800);
        initComponents(frame);
        frame.setVisible(true);
    }

    private void initComponents(final JFrame frame) throws IOException {
        FileManipulation fm = new FileManipulation();
        final JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JComboBox cb = new JComboBox();
        derbyList = new JTextArea(10, 50);
        JScrollPane derbyListScroll = new JScrollPane(derbyList);
        derbyListScroll.setBorder(BorderFactory.createTitledBorder("Conflicting Team List"));


        JButton derbyButton = new JButton("Highlight Conflicting Fixtures");
        JButton findDerbyListButton = new JButton("Select List");
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
    /*
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){


            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                    String status = (String) table.getModel().getValueAt(row, col);



                int x = 1;
                    if (status.contains("Manchester City")){




                      c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    } else {

                       c.setForeground(Color.BLACK);
                        c.setFont(new Font("Dialog", Font.PLAIN, 12));

                    }





                    return this;
            }

        });
*/


        //Populating the comboBox with slots
        List<String> dates = fm.getDatesList();
        cb.setModel(new DefaultComboBoxModel<>(dates.toArray()));


        /*********************************************************************************************************/ //Adding components to the panel
        JScrollPane scrollPane1 = new JScrollPane(table);
        panel.add(scrollPane1);

        panel1.add(derbyListScroll);
        panel.add(panel1);
        panel.add(findDerbyListButton);
        panel.add(derbyButton);

        frame.add(panel);



        final JFileChooser fileDialog = new JFileChooser("C:");
        JFrame chooseDerbyFile = new JFrame();
        findDerbyListButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent r) {

                int returnVal = fileDialog.showOpenDialog(chooseDerbyFile);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileDialog.getSelectedFile();
                    derbyFilePath = file.getPath();

                    System.out.println(derbyFilePath);
                    try {
                        DisplayDerbyList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        derbyButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent r) {


               try {
                    isPressed = true;
                    HighlightDerbyMatches(isPressed);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });



        PopulateComboBoxWithSlots(fixturess, dates, matches, sizeOfFixturess, count);
    }

    public void DisplayDerbyList() throws IOException {
        derbyList.setText("");
        BufferedReader br = new BufferedReader(new FileReader(derbyFilePath));
        String line;


        while ((line = br.readLine()) != null) {

            derbyList.append("\n" + line);


        }
        br.close();

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

            if (itterator == Counter) {
                itterator = 0;
            }

            reusableSlots = listOfSlots.get(itterator);

            test = reusableSlots.substring(reusableSlots.lastIndexOf(')') + 1);
            reusableSlotsinINTS = Integer.parseInt(test);


            for (int r = 0; r < matchesPerRound; r++) {//matches per round

                p = itterator;


                if (reusableSlotsinINTS == x) {
                    p = p + 1;
                    if (p == listOfSlots.size() || p > listOfSlots.size()) {
                        p = 0;
                    }
                    reusableSlots = listOfSlots.get(p);
                }

                fixtureList[z][1] = reusableSlots;


                z++;
                x++;

                if (x == matchesPerRound) {
                    x = 0;
                }

            }
            roundCount++;

            itterator++;
            if (roundCount == (numberOfFixtures - counter) + 1) {
                break;
            }
            fixtureList[z][1] = ("Round " + (roundCount) + "\n\n");

        }
    }

    public void HighlightDerbyMatches(boolean isPressed) throws IOException {
        FileManipulation fm = new FileManipulation();
        try {
            fm.setDerbyMatches();
        } catch (IOException e) {
            e.printStackTrace();
        }
        derbyMatches = (ArrayList<String>) fm.getDerbyMatches();


        String temp1 = "";
        String temp2 = "";
        String[] seperatedTemp;
        for (int i = 0; i < derbyMatches.size(); i++) {
            temp1 = derbyMatches.get(i);
            seperatedTemp = temp1.split("vs");
            temp1 = seperatedTemp[0];
            temp2 = seperatedTemp[1];
        }

       if (isPressed == true) {

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){


            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String status = (String) table.getModel().getValueAt(row, col);



                int x = 1;
                if (status.contains("Manchester City")){




                    c.setForeground(Color.RED);
                    c.setFont(new Font("Dialog", Font.BOLD, 14));


                } else {

                    c.setForeground(Color.BLACK);
                    c.setFont(new Font("Dialog", Font.PLAIN, 12));


                }





                return this;

            }


        });
           table.repaint();

       }
        else{

        }
    }



}
