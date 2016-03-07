package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import static javax.swing.ScrollPaneConstants.*;


public class DynamicDateAssignment {
    JTable tbl1;

    FileManipulation fm = new FileManipulation();
    FixtureGenerating fg = new FixtureGenerating();

    Object [][] tests;




    public void createAndShowUI() throws IOException {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setSize(600,700);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(0,0,screenSize.width, (screenSize.height - 50));

        initComponents(frame);
        frame.setVisible(true);
    }

    private void initComponents(final JFrame frame)throws IOException {

        final JPanel panel = new JPanel();
        JPanel basePanel = new JPanel();
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(4,2));





/*
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cb));
        //Populating the comboBox with slots
        List<String> dates = fm.getDatesList();
        cb.setModel(new DefaultComboBoxModel<>(dates.toArray()));

*/




            Object[] column = {"Fixture", "Slots"};
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






            for(int i = 0;i<15;i++) {

                int roundCount = 1;
                fixturess[0][0] = ("Round " + (roundCount) + "\n\n");


                for (int r = 1; r < matches.size(); r++) {
                    fixturess[r][0] = matches.get(r-1);

                    if (r != 1) {
                        if (r % (10) == 0) {

                            fixturess[r][0] = ("Round " + (roundCount + 1) + "\n\n");
                            roundCount++;




                        }


                    }

                }

                tbl1 = new JTable(fixturess, column);
                tbl1.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                JScrollPane scrPane = new JScrollPane(container);
                frame.add(scrPane);
                JScrollPane scrollPane1 = new JScrollPane(tbl1);
                scrollPane1.add(Box.createRigidArea(new Dimension(5, 0)));
                container.add(scrollPane1);
                frame.add(container);

            }


























    }
}
