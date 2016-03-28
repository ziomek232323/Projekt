package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ResultsTable {
        JTable resultsTable;




        public void createAndShowCompiledFixturesFrame() throws IOException {
            JFrame frame = new JFrame("Results Table");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1280, 800);
            frame.setResizable(false);
            initComponents(frame);
            frame.setVisible(true);
        }

        private void initComponents(final JFrame frame) throws IOException {
            final JPanel panel = new JPanel();
            JPanel panel1 = new JPanel();
            String[] columns = {"Pos","Team","P", "W", "L", "D", "MP", "GF", "GA", "GD"};
            Object[][] rows = new Object[0][];
            panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));

            /*********************************************************************************************************/ //Adding components to the panel



            resultsTable = new JTable(rows,columns);
            resultsTable.setFillsViewportHeight(true);
            resultsTable.setPreferredScrollableViewportSize(new Dimension(500, 600));
            JScrollPane scrollPane1 = new JScrollPane(resultsTable);
            scrollPane1.setBorder(BorderFactory.createTitledBorder("First Half of Season"));
            //firstHalfSeason.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cb));

            panel.add(scrollPane1);
            frame.add(panel);



            /********************************************************************************************************/ //Overriding button for results table







            }
    }


