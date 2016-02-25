package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ManualDateAssignFrame {
    JTable table;

    Object [][] tests;




    public void createAndShowUI() throws IOException {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,800);
        initComponents(frame);
        frame.setVisible(true);
    }

    private void initComponents(final JFrame frame)throws IOException {

        final JPanel panel = new JPanel();
        JComboBox cb = new JComboBox();

        BufferedReader bd = new BufferedReader(new FileReader("./src/list.txt"));
        String lines;
        int count = 0;
        ArrayList<String> fixtures = new ArrayList<>();
        while ((lines = bd.readLine()) != null) {
            fixtures.add(lines);
            count++;
        }

        Object [][] fixturess = new Object[count][2];



        int roundCount =1;
        fixturess[0][0] = ("Round "+(roundCount) + "\n\n");
        for (int r=1; r<fixtures.size(); r++) {
              fixturess[r][0]= fixtures.get(r);
            if(r !=1){
                if(r % (10) == 0){
                    fixturess[r][0]=("Round "+(roundCount+1) + "\n\n");
                    roundCount++;
                }}

        }


        String[] columns = {"Fixtures", "Slots"};

        Object [][] data =  {{"bill","hazel"},
                            { "Mary","black"},
                            { "Mary","black"},
                            { "Mary","black"},
                            { "Mary","black"},
                            { "Mary","black"}};

        table = new JTable(fixturess,columns);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500,600));

        JScrollPane scrollPane1 = new JScrollPane(table);

        panel.add(scrollPane1);
        frame.add(panel);







    }
}
