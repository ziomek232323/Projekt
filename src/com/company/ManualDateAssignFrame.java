package com.company;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;


public class ManualDateAssignFrame {
    JTable table;
    ArrayList<String> derbyMatches;
    int matchesPerRound;
    FileManipulation fm = new FileManipulation();
    public JTextArea derbyList;
    protected String derbyFilePath ="";
    boolean isPressed = false;
    boolean isSaved = false;


    public void createAndShowUI() throws IOException {
        JFrame frame = new JFrame("Assign Dates");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setResizable(false);
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

        JButton compileFixtures = new JButton("Compile Fixtures");
        JButton derbyButton = new JButton("Highlight Conflicting Fixtures");
        JButton findDerbyListButton = new JButton("Select List");
        JTextField input = new JTextField(17);
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

        //Populating the DateFrame
        Object[][] fixturess = new Object[sizeOfFixturess][2];

        int roundCount = 1;
        int x = 0;
        int z = 0;
        fixturess[0][0] = ("Round " + (roundCount) + "\n\n");
        for(int i = 0 ; i < sizeOfFixturess - count;i++) {//round count
            z++;
            for (int r = 0; r < matchesPerRound ; r++) {//matches per round


                fixturess[z][0] = matches.get(x);

                x++;
                z++;
            }
            roundCount++;
            if(roundCount == (sizeOfFixturess - count)+1){
                break;
            }
            fixturess[z][0] = ("Round " + (roundCount) + "\n\n");




        }

        String[] columns = {"Fixtures", "Slots"};


        table = new JTable(fixturess,columns);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 600));
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cb));



        //Populating the comboBox with slots
        List<String> dates = fm.getDatesList();
        cb.setModel(new DefaultComboBoxModel<>(dates.toArray()));


        /*********************************************************************************************************/ //Adding components to the panel
        JScrollPane scrollPane1 = new JScrollPane(table);
        panel.add(scrollPane1);

        panel1.add(derbyListScroll);
        panel.add(panel1);
        panel.add(input);
        panel.add(findDerbyListButton);
        panel.add(derbyButton);
        panel.add(compileFixtures);


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
                    saveData(table);
                    saveMatches(table);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        compileFixtures.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent r) {
                CompiledFixturesFrame cff = new CompiledFixturesFrame();
                try {
                    if(isSaved == true){
                        saveData(table);
                        saveMatches(table);
                    }
                    cff.createAndShowCompiledFixturesFrame();

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
                if (reusableSlotsinINTS == x) {

                    itterator += 1;
                    x = 0 ;

                    if (itterator == listOfSlots.size() || itterator > listOfSlots.size()) {
                        itterator = 0;
                    }

                    reusableSlots = listOfSlots.get(itterator);
                    test = reusableSlots.substring(reusableSlots.lastIndexOf(')') + 1);
                    reusableSlotsinINTS = Integer.parseInt(test);

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
            fm.setDerbyMatches(derbyFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        derbyMatches = (ArrayList<String>) fm.getDerbyMatches();


        String temp1 = "";
        String temp2 = "";
        String[] seperatedTemp;
        ArrayList<String> listOfDerbyMatches = new ArrayList<>();
        for (int i = 0; i < derbyMatches.size(); i++) {
            temp1 = derbyMatches.get(i);
            seperatedTemp = temp1.split("vs");
            temp1 = seperatedTemp[0];
            temp2 = seperatedTemp[1];


            listOfDerbyMatches.add(temp1);
            listOfDerbyMatches.add(temp2);
        }

        if (isPressed == true) {

            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){


                public Component getTableCellRendererComponent(JTable table,
                                                               Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                    String status = (String) table.getModel().getValueAt(row, col);

                    int i = 1;

                    if (status.contains(listOfDerbyMatches.get(0))){
                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));



                    }

                    else if (status.contains(listOfDerbyMatches.get(1))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }
                    else if (status.contains(listOfDerbyMatches.get(2))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }else if (status.contains(listOfDerbyMatches.get(3))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }else if (status.contains(listOfDerbyMatches.get(4))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }else if (status.contains(listOfDerbyMatches.get(5))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }else if (status.contains(listOfDerbyMatches.get(6))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }else if (status.contains(listOfDerbyMatches.get(7))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }else if (status.contains(listOfDerbyMatches.get(8))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }else if (status.contains(listOfDerbyMatches.get(9))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }else if (status.contains(listOfDerbyMatches.get(10))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }else if (status.contains(listOfDerbyMatches.get(11))) {


                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }

                    else if (status.contains("Round")) {


                        c.setForeground(Color.BLUE);
                        c.setFont(new Font("Dialog", Font.BOLD, 14));

                    }
                    else {

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
        isSaved = true;


    }
    public  void saveData (JTable table1) throws IOException {
        File file = new File("./src/data.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        String fileContent="";
        TableModel tModel = table1.getModel();
        for (int i = 0; i < tModel.getRowCount(); i++) {

            for (int j = 0; j < tModel.getColumnCount(); j++) {
                Object cellValue = tModel.getValueAt(i, j);
                fileContent = cellValue.toString();

            }
            fileWriter.write(fileContent);
            fileWriter.write("\n");

        }
        fileWriter.flush();
        fileWriter.close();
    }
    public  void saveMatches (JTable table1) throws IOException {
        File file = new File("./src/data2.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        String fileContent="";
        TableModel tModel = table1.getModel();
        for (int i = 0; i < tModel.getRowCount(); i++) {


            Object cellValue = tModel.getValueAt(i, 0);
            fileContent = cellValue.toString();


            fileWriter.write(fileContent);
            fileWriter.write("\n");

        }
        fileWriter.flush();
        fileWriter.close();
    }

}