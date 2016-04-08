package com.company;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class FixtureDate {

    private String dateFilePath = "";


    public List setDate(Date startDate, Date endDate) throws IOException {


        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);


        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();


            dates.add(result);
            calendar.add(Calendar.DATE, 1);

        }

        return dates;
    }

    public void DisplayDatesInFrame(String filePath, JTextArea frame) throws IOException {

        frame.setText(null);
        String line;
        ArrayList <String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null) {

            list.add(line);
        }

        for(int i = 0; i < list.size(); i ++){
            frame.append(list.get(i) + "\n");
        }

        br.close();
    }

    public void AddSelectedDateToFile (String filePath, JTextArea frame, JComboBox date) throws IOException {

        Writer out = new BufferedWriter(new FileWriter(filePath, true));

        String line = date.getSelectedItem().toString();
        out.append(line + "\n");
        out.close();
        DisplayDatesInFrame(filePath,frame);

    }

    public void SetDateFilePath(String dateFilePath){
        this.dateFilePath = dateFilePath;
    }
    public String GetDateFilePath(){
        return this.dateFilePath;
    }


}
