package fr.epita.android.pri.Tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sadekseridj on 23/11/2017.
 */

public class PasswordFunctions
{

    final protected static char[] hexa = "0123456789ABCDEF".toCharArray();
    /*
     * Check if the password is compliant with the policy : 8 characters, 1 majuscule, 1 special character
    */
    public boolean checkPolicyPassword(String pass)
    {
        Pattern pattern_length = Pattern.compile(".{8,}");
        Pattern pattern_maj = Pattern.compile("[A-Z]{1,}"); // mettre entre 1 et 8
        Pattern pattern_spec = Pattern.compile("[^a-zA-Z]{1,}"); // mettre caractère spécial entre 1 et 8
        Matcher matcher_length = pattern_length.matcher(pass);
        Matcher matcher_maj = pattern_maj.matcher(pass);
        Matcher matcher_spec = pattern_spec.matcher(pass);
        return (matcher_length.matches() && matcher_maj.matches() && matcher_spec.matches());
    }

    /*
     * Hash function for password + salt (login)
     */

    public static String hashPass(String pass, String login)
    {
        String pass_salt = pass + login;
        String passHashed = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] textBytes = pass_salt.getBytes("UTF-8");
            md.update(textBytes, 0, textBytes.length);
            textBytes = md.digest();
            passHashed = bytesToHex(textBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return passHashed;
    }

    /*
     * Convert bytes to hexadecimal
     */
    private static String bytesToHex(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++)
        {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }


}
