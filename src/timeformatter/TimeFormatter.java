/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeformatter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cheadle_kj_marcelo
 */
public class TimeFormatter {

    private Pattern pattern;
    private Matcher matcher;

    private static final String TIME24HOURS_PATTERN
            = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

    public TimeFormatter() {
        pattern = Pattern.compile(TIME24HOURS_PATTERN);
    }

    /**
     * Validate time in 24 hours format with regular expression
     *
     * @param time time address for validation
     * @return true valid time fromat, false invalid time format
     */
    public boolean validate(final String time) {

        matcher = pattern.matcher(time);
        StringBuffer s = new StringBuffer();
        while (matcher.find()) {
            String timeMatch = matcher.group();
            String hour = timeMatch.substring(0, 2);
            String minute = timeMatch.substring(3, 5);
            String seconds = timeMatch.substring(6, 8);

            int minuteInt = Integer.parseInt(minute);
            int secondsInt = Integer.parseInt(seconds);
            int hourInt = Integer.parseInt(hour);

            if (secondsInt >= 32) {
                secondsInt = secondsInt - 32;

            } else {
                if (hourInt > 0) {
                    if (minuteInt == 0) {
                        minuteInt = 59;
                        hourInt = hourInt - 1;
                        secondsInt = (60 - 32) + secondsInt;
                    } else {
                        minuteInt = minuteInt - 1;
                        secondsInt = (60 - 32) + secondsInt;
                    }
                } else {
                    minuteInt = minuteInt - 1;
                    secondsInt = (60 - 32) + secondsInt;
                }
            }

            minute = minuteInt >= 10 ? "" + minuteInt : "0" + minuteInt;
            seconds = secondsInt >= 10 ? "" + secondsInt : "0" + secondsInt;
            hour = hourInt >= 10 ? "" + hourInt : "0" + hourInt;
            String timeReplace = hour + ":" + minute + ":" + seconds;
            matcher.appendReplacement(s, timeReplace);
        }
        return matcher.find();

    }

    public static void main(String[] args) {
        TimeFormatter formatter = new TimeFormatter();
        System.out.println("time: " + formatter.validate(getString()));
    }

    private static String getString() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("file_time.srt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            return sb.toString();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }

}
