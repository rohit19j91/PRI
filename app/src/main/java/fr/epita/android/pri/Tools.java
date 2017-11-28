package fr.epita.android.pri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sadekseridj on 27/11/2017.
 */

public class Tools  {

    /*
    * Check if the mail format is correct
    */
    public static boolean checkMail(String mail)
    {
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    /*
     * Check if the mobile format is correct
     */
    public static boolean checkMobile(String mobile)
    {
        return (mobile.length() == 10);
    }
}
