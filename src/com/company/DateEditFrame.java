package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class DateEditFrame {
    private String dateFilePath = "";
    public void createEditMenu() {

        FixtureDate dt = new FixtureDate();
        FixtureGenerating fg = new FixtureGenerating();


        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Edit Dates");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);

                JTextArea listOfDates = new JTextArea(15, 50);
                listOfDates.setWrapStyleWord(true);
                listOfDates.setEditable(false);
                listOfDates.setBorder(BorderFactory.createTitledBorder("List Of slots Available"));
                listOfDates.setFont(Font.getFont(Font.SANS_SERIF));
                JScrollPane scroller = new JScrollPane(listOfDates);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new FlowLayout());
                JTextField input = new JTextField(20);
                input.setText("Slot X - Day(time)");
                JButton insertButton = new JButton("Insert");
                JButton browseButton = new JButton("Browse");
                JButton applyDates = new JButton("Apply Automatic Dates");
                JButton editDateManuallyButton = new JButton("Assign Dates");
                JButton addDateFromComboBox = new JButton("Add");
                final JFileChooser fileDialog1 = new JFileChooser("C:");
                panel.add(scroller);
                inputpanel.add(input);
                inputpanel.add(insertButton);
                inputpanel.add(browseButton);
                inputpanel.add(applyDates);
                inputpanel.add(editDateManuallyButton);
                panel.add(inputpanel);
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
                input.requestFocus();


                browseButton.addActionListener(new ActionListener() {


                    @Override
                    public void actionPerformed(ActionEvent r) {
                        int returnVal = fileDialog1.showOpenDialog(frame);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            java.io.File file1 = fileDialog1.getSelectedFile();
                            dateFilePath = file1.getAbsolutePath();
                            dt.SetDateFilePath(dateFilePath);
                            try {
                                dt.DisplayDatesInFrame(dateFilePath,listOfDates);

                            } catch (IOException e) {

                            }


                        }
                    }
                });

                addDateFromComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent r) {
                        String tempPath = dt.GetDateFilePath();
                    }
                });

                applyDates.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent r) {
                        String tempPath = dt.GetDateFilePath();
                        try {
                            frame.dispose();
                            fg.AssignSlots();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });
                editDateManuallyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent r) {
                        ManualDateAssignFrame mdaf = new ManualDateAssignFrame();
                        frame.dispose();
                        try {
                            mdaf.createAndShowUI();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });
                insertButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent z) {


                        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./src/dates.txt", true)))) {

                            out.println(input.getText());
                            out.close();
                            dt.DisplayDatesInFrame(dateFilePath,listOfDates);


                        } catch (IOException za) {

                        }

                    }
                });
            }
        });
    }

}