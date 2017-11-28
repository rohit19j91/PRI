package fr.epita.android.pri;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by sadekseridj on 25/11/2017.
 */

public class DialogMessage extends Activity {

    private Context context = null;

    protected DialogMessage(Context context) {
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
                        Intent intent = new Intent(context, ConfirmationCode.class);
                        intent.putExtra("CODE", code);
                        intent.putExtra("MAIL", mail_addr);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("Sms", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ActivityCompat.requestPermissions((Activity) context,new String[]{"android.permission.SEND_SMS"},1);
                        }
                        int code = ForgotPassword.sent_sms_code(mail_addr);
                        Intent intent = new Intent(context, ConfirmationCode.class);
                        intent.putExtra("CODE", code);
                        intent.putExtra("MAIL", mail_addr);
                        context.startActivity(intent);
                    }
                });
        builder.show();
    }
}
