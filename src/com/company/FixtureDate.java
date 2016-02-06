package com.company;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class FixtureDate {
    private Date mDate;

    public FixtureDate(Date date) {
        mDate = date;

    }

    public Date getDate() {
        return mDate;
    }


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





}



























        /*
        //first match August 8th

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm");
        Calendar calendar = new GregorianCalendar(2015, 7, 8, 17, 00);
        String date = "";
        //ArrayList date1 = new ArrayList();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR);        // 12 hour clock
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);

        date = (sdf.format(calendar.getTime()));

        writeDate(date);
    }

    public void getDay() {
        Calendar c = Calendar.getInstance();
        c.set(1994, Calendar.JULY, 24);

        int day_of_week = c.get(Calendar.DAY_OF_WEEK);
        String day = "";

        if (day_of_week == 1) {
            day = "Sunday";
        } else if (day_of_week == 2) {
            day = "Monday";
        } else if (day_of_week == 3) {
            day = "Tuesday";
        } else if (day_of_week == 4) {
            day = "Wednesday";
        } else if (day_of_week == 5) {
            day = "Thursday";
        } else if (day_of_week == 6) {
            day = "Friday";
        } else if (day_of_week == 7) {
            day = "Saturday";
        }

        System.out.println("Day of the week: " + day);
    }

    public void writeDate(String date) throws IOException {
        PrintWriter out = (new PrintWriter(new BufferedWriter(new FileWriter("./src/table.txt", true))));
        System.out.println("This is a date: "+ date );
        BufferedReader br = new BufferedReader(new FileReader("./src/table.txt"));
        String line;

        List<String> temps = new ArrayList<String>();
        while ((line = br.readLine()) != null ) {
            {
                temps.add(line + " " + date);

            }

        }
        /*
        for(String s : temps) {
            System.out.println("This is fixture with date: " + s);

            out.write(s);
        }
    */


