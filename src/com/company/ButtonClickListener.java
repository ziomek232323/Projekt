package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class ButtonClickListener implements ActionListener {
    Main user = new Main();

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("insert")) {

            String x = user.getEditTeamList();
            {
                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./src/testing.txt", true)))) {
                    out.println(x);
                    out.close();

                } catch (IOException z) {
                    //exception handling left as an exercise for the reader
                }
                try {
                    user.rePaintTextArea();
                } catch (IOException z) {
                }
            }

        }

    }
}

