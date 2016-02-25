package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;



public class ManualDateAssignFrame {
    JTable table;

    public void createAndShowUI() throws IOException {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,200);
        initComponents(frame);
        frame.setVisible(true);
    }

    private void initComponents(final JFrame frame)throws IOException {

        final JPanel panel = new JPanel();


        String[] columns = {"Fixtures", "Slots"};

        Object [][] data =  {{"bill","hazel"},
                            { "Mary","black"},
                            { "Mary","black"},
                            { "Mary","black"},
                            { "Mary","black"},
                            { "Mary","black"}};

        table = new JTable(data,columns);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500,50));

        JScrollPane scrollPane1 = new JScrollPane(table);

        panel.add(scrollPane1);
        frame.add(panel);







    }
}
