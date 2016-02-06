package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.*;
import java.util.*;


public class DateEditFrame {
    public static void createEditMenu() {

        FixtureDate dt = new FixtureDate();
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
                listOfDates.setBorder(BorderFactory.createTitledBorder("List Of Dates Available"));
                listOfDates.setFont(Font.getFont(Font.SANS_SERIF));
                JScrollPane scroller = new JScrollPane(listOfDates);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new FlowLayout());
                JTextField input = new JTextField(20);
                JButton insertButton = new JButton("Insert");
                JButton browseButton = new JButton("Browse");
                JButton applyDates = new JButton("Apply");
                JButton addDateFromComboBox = new JButton("Add");
                final JFileChooser fileDialog1 = new JFileChooser("C:");

                JComboBox cb = new JComboBox();

                String str_date = StartDateSelection();
                String end_date = EndDateSelection();
                Date startDate = null;
                Date endDate = null;
                DateFormat formatter ;
                formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    startDate = formatter.parse(str_date);
                    endDate =  formatter.parse(end_date);
                }catch(Exception e) {

                }
                try {
                    cb.setModel(new DefaultComboBoxModel<>(dt.setDate(startDate,endDate).toArray()));
                } catch (IOException e) {
                    e.printStackTrace();
                }


                panel.add(scroller);
                panel.add(cb);
                inputpanel.add(input);
                inputpanel.add(insertButton);
                inputpanel.add(browseButton);
                inputpanel.add(addDateFromComboBox);
                inputpanel.add(applyDates);
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
                            String dateFilePath = file1.getAbsolutePath();
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
                        try {
                            dt.AddSelectedDateToFile(tempPath, listOfDates, cb);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }

            public String StartDateSelection (){
                String str_date = JOptionPane.showInputDialog("Enter start date in format (dd/MM/yyyy): ");

                return str_date;

            }
            public String EndDateSelection(){
                String end_date = JOptionPane.showInputDialog("Enter end date in format (dd/MM/yyyy): ");

                return end_date;
            }

        });
    }
}
