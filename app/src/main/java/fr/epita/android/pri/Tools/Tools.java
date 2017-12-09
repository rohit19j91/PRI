package fr.epita.android.pri.Tools;

import android.content.Context;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import fr.epita.android.pri.R;

/**
 * Created by sadekseridj on 27/11/2017.
 */

public class Tools  {

    /*
    * Check if the mail format is correct
    */
    /*public static boolean checkMail(String mail)
    {
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }*/

    /*
     * Check if the mobile format is correct
     */
    /*public static boolean checkMobile(String mobile)
    {
        return (mobile.length() == 10);
    }*/
    public Animation animShake;
    public Vibrator vibrator;

    public Tools(Context context) {

        animShake = AnimationUtils.loadAnimation(context, R.anim.shake);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public boolean checkEmail(EditText editText, TextInputLayout inputLayout, String err)
    {
        if(editText.getText().toString().trim().isEmpty() ||
                !(Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()))
        {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(err);
            return false;
        }
        inputLayout.setErrorEnabled(false);
        return true;
    }

    public boolean checkFieldSignup(EditText editText, TextInputLayout inputLayout, String err)
    {
        if(editText.getText().toString().trim().isEmpty())
        {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(err);
            return false;
        }
        inputLayout.setErrorEnabled(false);
        return true;
    }

    public void setAnimaton(TextInputLayout textInputLayout)
    {
        textInputLayout.setAnimation(animShake);
        textInputLayout.startAnimation(animShake);
        vibrator.vibrate(120);
    }
}
