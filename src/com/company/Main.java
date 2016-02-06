package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main extends JPanel {
    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel displayTeamListPanel;
    private JTextArea editTeamList;
    private JTextArea teamList;
    private JButton schedule;
    private JButton insert;
    private JButton showFileDialogButton;
    private String filePath = "";
    private JTextArea displayFixtureArea;
    private JButton editDateButton;


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
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        statusLabel = new JLabel("");
        displayTeamListPanel = new JPanel();
        displayTeamListPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        teamList = new JTextArea(25, 25);
        JScrollPane scrollPane = new JScrollPane(teamList);
        teamList.setBorder(BorderFactory.createTitledBorder("Team List"));
        teamList.setEditable(false);

        displayFixtureArea = new JTextArea(25, 34);
        JScrollPane scrollPane11 = new JScrollPane(displayFixtureArea);
        displayFixtureArea.setBorder(BorderFactory.createTitledBorder("Fixtures"));
        displayFixtureArea.setEditable(false);

        editTeamList = new JTextArea(1, 25);
        editTeamList.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
        editTeamList.setText("");


        mainFrame.add(displayTeamListPanel);
        displayTeamListPanel.add(scrollPane, BorderLayout.WEST);
        displayTeamListPanel.add(scrollPane11);
        displayTeamListPanel.add(editTeamList, BorderLayout.SOUTH);
        editTeamList.setPreferredSize(new Dimension(20, 20));
        displayTeamListPanel.revalidate();
        mainFrame.setVisible(true);
        insert = new JButton("Insert");
        displayTeamListPanel.add(insert);
        schedule = new JButton("Schedule");
        displayTeamListPanel.add(schedule);
        showFileDialogButton = new JButton("Browse For List");
        displayTeamListPanel.add(showFileDialogButton);
        editDateButton = new JButton("Edit Date");
        displayTeamListPanel.add(editDateButton);

    }

    public String getEditTeamList() {
        return editTeamList.getText();
    }

    public void rePaintTextArea() throws IOException {
        editTeamList.setText(null);
        teamList.setText(null);
        displayList();
    }

    private void selectOption() {


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

                    createEditMenu();

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
                    System.out.print(za);
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


        FixtureGenerating fg = new FixtureGenerating();
        fg.GenerateFixture();
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
                            try {
                                dt.DisplayDatesInFrame(dateFilePath,listOfDates);
                            } catch (IOException e) {

                            }


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
