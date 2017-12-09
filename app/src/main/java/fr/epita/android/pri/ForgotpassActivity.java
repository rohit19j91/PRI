package fr.epita.android.pri;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import fr.epita.android.pri.Tools.GmailSender;
import fr.epita.android.pri.Tools.Tools;

/**
 * Created by sadekseridj on 09/12/2017.
 */

public class ForgotpassActivity extends AppCompatActivity implements View.OnClickListener{

    private static DatabaseHandler dh = null;
    private TextInputLayout inputemail = null;
    private EditText editemail = null;
    private ImageButton submit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgotpass);
        dh = new DatabaseHandler(this);
        inputemail = (TextInputLayout) findViewById(R.id.inputemail);
        editemail = (EditText) findViewById(R.id.editemail);
        submit = (ImageButton) findViewById(R.id.forgotsubmitbutton);
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

    public void pop_up_code(final String mail_addr)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        //final AlertDialog dialog = builder.create();
        builder.setTitle("How do you want to receive the code ?")
                .setPositiveButton("E-mail", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int code = sent_email_code(mail_addr);
                        Intent intent = new Intent(ForgotpassActivity.this, ConfirmationcodeActivity.class);
                        intent.putExtra("CODE", code);
                        intent.putExtra("MAIL", mail_addr);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Sms", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ActivityCompat.requestPermissions((Activity) ForgotpassActivity.this,new String[]{"android.permission.SEND_SMS"},1);
                        }
                        int code = sent_sms_code(mail_addr);
                        Intent intent = new Intent(ForgotpassActivity.this, ConfirmationcodeActivity.class);
                        intent.putExtra("CODE", code);
                        intent.putExtra("MAIL", mail_addr);
                        startActivity(intent);
                    }
                });
        builder.show();
    }


    @Override
    public void onClick(View view) {
        String email_addr = editemail.getText().toString();
        String login = dh.searchLoginByMail(email_addr);
        Tools tools = new Tools(this);
        switch (view.getId())
        {
            case R.id.forgotsubmitbutton:
                inputemail.setErrorEnabled(false);
                if (! tools.checkEmail(editemail, inputemail, "The mail format is not correct"))
                {
                    tools.setAnimaton(inputemail);
                    break;
                }
                if (login == null)
                {
                    inputemail.setErrorEnabled(true);
                    inputemail.setError("Mail not found, please sign up");
                    tools.setAnimaton(inputemail);
                    break;
                }
                editemail.setText("");
                pop_up_code(email_addr);
                break;
            default:
                break;
        }
    }
}
