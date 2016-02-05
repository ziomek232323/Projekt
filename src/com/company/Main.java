package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;


public class Main extends JPanel {
    private JFrame mainFrame;

    private JLabel statusLabel;
    public JPanel displayTeamListPanel;
    private JTextArea editTeamList;
    private JTextArea teamList;
    private JButton schedule;
    private JButton insert;
    private JButton showFileDialogButton;
    private JButton delete;


    private String filePath = "";
    private JTextArea displayFixtureArea;


    public Main() {
        prepareGUI();
    }

    public static void main(String[] args) throws IOException {
        Main demo = new Main();
        demo.selectOption();

    }

    private void prepareGUI() {
        mainFrame = new JFrame("Sports Scheduler");
        mainFrame.setSize(800, 600);
        mainFrame.setResizable(false);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent)
            {

                System.exit(0);
            }
        });



        statusLabel = new JLabel("");
        displayTeamListPanel = new JPanel();
        displayTeamListPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        teamList = new JTextArea(25,25);
        JScrollPane scrollPane = new JScrollPane(teamList);
        teamList.setBorder(BorderFactory.createTitledBorder("Team List"));
        teamList.setEditable(false);

        displayFixtureArea = new JTextArea(25,34);
        JScrollPane scrollPane11 = new JScrollPane(displayFixtureArea);
        displayFixtureArea.setBorder(BorderFactory.createTitledBorder("Fixtures"));
        displayFixtureArea.setEditable(false);

        editTeamList = new JTextArea(1,25);
        editTeamList.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
        editTeamList.setText("");







/****************************- Set up of the GUI -***********************************************/

        mainFrame.add(displayTeamListPanel);
        displayTeamListPanel.add(scrollPane, BorderLayout.WEST);
        displayTeamListPanel.add(scrollPane11);
        displayTeamListPanel.add(editTeamList,BorderLayout.SOUTH);
        editTeamList.setPreferredSize(new Dimension(20,20));
        displayTeamListPanel.revalidate();
        mainFrame.setVisible(true);
        insert = new JButton("Insert");
        displayTeamListPanel.add(insert);
        schedule = new JButton("Schedule");
        displayTeamListPanel.add(schedule);
        showFileDialogButton = new JButton("Browse For List");
        displayTeamListPanel.add(showFileDialogButton);

    }

    public String getEditTeamList ()
    {
        return editTeamList.getText();
    }

    public void rePaintTextArea() throws IOException
    {
        editTeamList.setText(null);
        teamList.setText(null);
        displayList();
    }

    private void selectOption() {


        final JFileChooser fileDialog = new JFileChooser("C:");
        showFileDialogButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent r) {
                int returnVal = fileDialog.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileDialog.getSelectedFile();
                    statusLabel.setText(file.getPath());
                    filePath = statusLabel.getText();
                    try {
                        displayList();
                    } catch (IOException e) {
                    }

                } else {

                }
            }
        });


        schedule.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent z) {


                try {
                    DisplaySchedule();
                } catch (IOException e) {

                }
            }
        });

        insert.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent z) {


                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./src/testing.txt", true)))) {
                    out.println(getEditTeamList());
                    out.close();

                } catch (IOException za) {
                    System.out.print(za);
                }
                try {
                    rePaintTextArea();
                } catch (IOException za) {
                }
            }
        });


    }



    public void displayList() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;


        while ((line = br.readLine()) != null) {

           teamList.append("\n" + line);


        }
        br.close();

    }

    public void DisplaySchedule()throws  IOException {
        String[][] lists;
        String line;
        int count = 0;
        int numberOfTeams = count;
        int totalNumberOfRounds = numberOfTeams - 1;
        int numberOfMatchesPerRound = numberOfTeams / 2;

        FixtureGenerating fg = new FixtureGenerating();
        fg.GenerateFixture();
        lists = fg.getFixture();


        BufferedReader br = new BufferedReader(new FileReader(filePath));



        while ((line = br.readLine()) != null) {
            count++;
        }
        br.close();


        for (int roundNumber = 0; roundNumber < totalNumberOfRounds; roundNumber++) {
            displayFixtureArea.append("Round " + (roundNumber + 1) + "\t\t");
            displayFixtureArea.append("\n");
            for (int matchNumber = 0; matchNumber < numberOfMatchesPerRound; matchNumber++) {
                displayFixtureArea.append("\tMatch " + (matchNumber + 1) + ": "
                        + lists[roundNumber][matchNumber] + "\t");
                        displayFixtureArea.append("\n");
            }
        }
    }
}
