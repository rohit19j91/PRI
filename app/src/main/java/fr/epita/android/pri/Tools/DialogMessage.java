package fr.epita.android.pri.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import fr.epita.android.pri.Fragments.ForgotPassword;
import fr.epita.android.pri.MainActivity;

/**
 * Created by sadekseridj on 25/11/2017.
 */

public class DialogMessage extends Activity {

    private MainActivity context = null;

    public DialogMessage(MainActivity context) {
        this.context = context;
    }

    /*
    * Function which opens a pop up for display a message
    */
    public void pop_up_message(String title, String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.create();
        builder.setTitle(title)
                .setMessage(msg);
        builder.show();
    }

    /*
      * Function which opens a pop up for choose the way to send the code
      * Return the index of the item selectionned
    */
    public void pop_up_code(final String mail_addr)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.create();
        //final AlertDialog dialog = builder.create();
        builder.setTitle("How do you want to receive the code ?")
                .setPositiveButton("E-mail", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int code = ForgotPassword.sent_email_code(mail_addr);
                        Bundle bundle = new Bundle();
                        bundle.putInt("CODE", code);
                        bundle.putString("MAIL", mail_addr);
                        context.customViewpagerAdapter.confirmationCode.setArguments(bundle);
                        context.display_fragment(7);
                    }
                })
                .setNegativeButton("Sms", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ActivityCompat.requestPermissions((Activity) context,new String[]{"android.permission.SEND_SMS"},1);
                        }
                        int code = ForgotPassword.sent_sms_code(mail_addr);
                        Bundle bundle = new Bundle();
                        bundle.putInt("CODE", code);
                        bundle.putString("MAIL", mail_addr);
                        context.customViewpagerAdapter.confirmationCode.setArguments(bundle);
                        context.display_fragment(7);
                    }
                });
        builder.show();
    }
}
