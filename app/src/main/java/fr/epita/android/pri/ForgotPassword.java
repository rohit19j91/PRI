package fr.epita.android.pri;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Rohit on 11/1/2017.
 */

public class ForgotPassword extends Activity implements View.OnClickListener{

    private static DatabaseHandler dh = null;
    private EditText mail = null;
    private Button submit = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpass);
        dh = new DatabaseHandler(this);
        mail = (EditText) findViewById(R.id.forgotemailtext);
        submit = (Button) findViewById(R.id.forgotsubmitbutton);
        submit.setOnClickListener(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    /*
     * Function for send the code to the user by mail
     */
    public static int sent_email_code(String mail_address)
    {
        String login = dh.searchLoginByMail(mail_address);
        int min = 111111;
        int max = 999999;
        int code = min + (int)(Math.random() * (max - min + 1));
        try {
            GmailSender sender = new GmailSender("oracle.separation@gmail.com", "personne93290");
            sender.sendMail("Confirmation code", "Hello " + login + ", \nYour code is " + code + "\n",
                    "oracle.separation@gmail.com", mail_address);
            return code;
        } catch (Exception e)
        {
            Log.d("SEND CODE", e.getMessage());
        }
        return code;
    }

    /*
     * Function for send the code to the user by sms
     */
    public static int sent_sms_code(String mail_address)
    {
        String login = dh.searchLoginByMail(mail_address);
        String mobile   = dh.searchMobileByMail(mail_address);
        int min = 111111;
        int max = 999999;
        int code = min + (int)(Math.random() * (max - min + 1));
        String msg = "Hello " + login + ", \nYour code is " + code + "\n";
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(mobile, "0642253493", msg, null, null);
        return code;
    }


    @Override
    public void onClick(View view) {
        String email_addr = mail.getText().toString();
        String login = dh.searchLoginByMail(email_addr);
        DialogMessage dialogMessage = new DialogMessage(ForgotPassword.this);
        switch (view.getId())
        {
            case R.id.forgotsubmitbutton:
                    if (mail.getText().toString().equals(""))
                    {
                        dialogMessage.pop_up_message("Access Denied", "Please put your mail");
                        break;
                    }
                    if (! Tools.checkMail(email_addr))
                    {
                        dialogMessage.pop_up_message("Access Denied", "The mail format is not correct");
                        break;
                    }
                    if (login == null)
                    {
                        dialogMessage.pop_up_message("Access Denied", "Mail not found, please sign up");
                        break;
                    }
                    mail.setText("");
                    dialogMessage.pop_up_code(email_addr);
                    break;
            default:
                break;
        }
    }
}

