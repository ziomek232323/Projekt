package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CompiledFixturesFrame {
    JTable firstHalfSeason;
    JTable secondHalfSeason;



    public void createAndShowCompiledFixturesFrame() throws IOException {
        JFrame frame = new JFrame("Compiled Schedule");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1060, 800);
        frame.setResizable(false);
        initComponents(frame);
        frame.setVisible(true);
    }

    private void initComponents(final JFrame frame) throws IOException {
        final JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        String[] columns = {"Fixtures", "Slots"};
        Object[][] rows = new Object[0][];
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        /*********************************************************************************************************/ //Adding components to the panel



        firstHalfSeason = new JTable(rows,columns);
        secondHalfSeason = new JTable(rows,columns);
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



        frame.add(panel);

    }
}
