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
    FileManipulation fm = new FileManipulation();

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
        ArrayList<String> matches = new ArrayList<>();
        while ((lines = bd.readLine()) != null) {
            matches.add(lines);
            count++;
        }
        if(matches.size()==0)
        JOptionPane.showMessageDialog(null,"Please Generate a Schedule First.");

        Object [][] fixturess = new Object[count][2];



        int roundCount =1;
        fixturess[0][0] = ("Round "+(roundCount) + "\n\n");
        for (int r=1; r<matches.size(); r++) {
              fixturess[r][0]= matches.get(r);
            if(r !=1){
                if(r % (10) == 0){
                    fixturess[r][0]=("Round "+(roundCount+1) + "\n\n");
                    roundCount++;
                }}

        }


        String[] columns = {"Fixtures", "Slots"};


        table = new JTable(fixturess,columns);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500,600));
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cb));
        List<String> dates = fm.getDatesList();
        cb.setModel(new DefaultComboBoxModel<>(dates.toArray()));
        JScrollPane scrollPane1 = new JScrollPane(table);
        panel.add(scrollPane1);
        frame.add(panel);




    }
}
