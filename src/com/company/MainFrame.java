package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

import static javax.swing.JFrame.EXIT_ON_CLOSE;


public class MainFrame {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel displayTeamListPanel;
    private JTextArea editTeamList;
    private JTextArea teamList;
    private JButton schedule;
    private JButton insert;
    private JButton showFileDialogButton;
    private JTextArea displayFixtureArea;
    private JButton editDateButton;
    public String filePath = "";

    public void prepareGUI() {
        mainFrame = new JFrame("Sports Scheduler");
        mainFrame.setSize(900, 600);
        mainFrame.setResizable(false);

        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        statusLabel = new JLabel("");
        displayTeamListPanel = new JPanel();
        displayTeamListPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        teamList = new JTextArea(25, 35);
        JScrollPane scrollPane = new JScrollPane(teamList);
        teamList.setBorder(BorderFactory.createTitledBorder("Team List"));
        teamList.setEditable(false);

        displayFixtureArea = new JTextArea(25, 65);
        JScrollPane scrollPane11 = new JScrollPane(displayFixtureArea);
        displayFixtureArea.setBorder(BorderFactory.createTitledBorder("Fixtures"));
        displayFixtureArea.setEditable(false);

        editTeamList = new JTextArea(1, 25);
        editTeamList.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
        editTeamList.setText("");


        mainFrame.add(displayTeamListPanel);
        displayTeamListPanel.add(scrollPane, BorderLayout.WEST);
        displayTeamListPanel.add(scrollPane11);
        displayTeamListPanel.add(editTeamList);
        editTeamList.setPreferredSize(new Dimension(20, 20));
        displayTeamListPanel.revalidate();
        mainFrame.setVisible(true);
        insert = new JButton("Insert Team");
        displayTeamListPanel.add(insert);
        schedule = new JButton("Generate Schedule");
        displayTeamListPanel.add(schedule);
        showFileDialogButton = new JButton("Browse For List");
        displayTeamListPanel.add(showFileDialogButton);
        editDateButton = new JButton("Edit Dates");
        displayTeamListPanel.add(editDateButton);

    }
    public void rePaintTextArea() throws IOException {
        editTeamList.setText(null);
        teamList.setText(null);
        displayList();
    }
    public String getEditTeamList() {
        return editTeamList.getText();
    }

    public void selectOption() {


        final JFileChooser fileDialog = new JFileChooser("C:");
        JFrame chooseFileFrame = new JFrame();
        showFileDialogButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent r) {

                int returnVal = fileDialog.showOpenDialog(chooseFileFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileDialog.getSelectedFile();
                    statusLabel.setText(file.getPath());
                    filePath = statusLabel.getText();
                    setFilePath(filePath);
                    System.out.println(filePath);
                    try {
                        displayList();
                    } catch (IOException e) {
                        System.out.print(e);
                    }

                }
            }
        });


        schedule.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent z) {


                try {
                    DisplaySchedule();

                } catch (IOException e) {
                    System.out.print(e);


                }
            }
        });

        editDateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent z) {
                DateEditFrame def = new DateEditFrame();


                def.createEditMenu();

            }
        });

        insert.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent z) {


                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
                    out.println(getEditTeamList());
                    out.close();

                } catch (IOException za) {

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

    public int getNumberOfTeamsInList() throws IOException {
        int count = 0;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null) {
            count++;
        }
        br.close();
        return count;
    }

    public void DisplaySchedule() throws IOException {
        String[][] lists;
        displayFixtureArea.setText(null);

        FixtureGenerating fg = new FixtureGenerating();
        fg.GenerateFixture(filePath);
        fg.Convert2dTOArrayList();
        lists = fg.getFixture();


        int numberOfTeams = getNumberOfTeamsInList();
        int totalNumberOfRounds = numberOfTeams - 1;
        int numberOfMatchesPerRound = numberOfTeams / 2;
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
    public void DisplayScheduleWithDates(ArrayList list, int matchesPerRound)throws IOException{
        JFrame secondFrame = new JFrame("Sports Scheduler");
        secondFrame.setSize(600, 600);
        secondFrame.setResizable(false);

        secondFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
       JPanel displayTeamListPanelDates = new JPanel();
        displayTeamListPanelDates.setLayout(new FlowLayout(FlowLayout.LEFT));


        JTextArea displayScheduleWithDates = new JTextArea(25, 65);
        JScrollPane scrollDisplaywithDates = new JScrollPane(displayScheduleWithDates);
        displayScheduleWithDates.setBorder(BorderFactory.createTitledBorder("Fixtures"));
        displayScheduleWithDates.setEditable(false);
        secondFrame.add(displayTeamListPanelDates);
        secondFrame.setVisible(true);
        displayTeamListPanelDates.add(scrollDisplaywithDates);
        int roundCount =1;
        displayScheduleWithDates.append("Round "+(roundCount) + "\n\n");
        for(int i = 0; i < list.size(); i++){
            displayScheduleWithDates.append(String.valueOf(list.get(i)));
            displayScheduleWithDates.append("\n");
            if(i !=0){
            if(i % (matchesPerRound) == 0){
                displayScheduleWithDates.append("\n\n");
                displayScheduleWithDates.append("Round "+(roundCount+1) + "\n\n");
                roundCount++;
            }}


        }
    }

    public void setFilePath(String fp){
        System.out.println("fp = " + fp);
        filePath=fp;
        FixtureGenerating fg = new FixtureGenerating();
        fg.setFilePath(filePath);
        System.out.println("setFilePath = " +filePath);
    }


}
