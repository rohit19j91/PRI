package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.Tools.DialogMessage;
import fr.epita.android.pri.Tools.GmailSender;
import fr.epita.android.pri.Tools.Tools;


/**
 * Created by sadekseridj on 06/12/2017.
 */

public class ForgotPassword extends Fragment implements View.OnClickListener{

    private static DatabaseHandler dh = null;
    private TextInputLayout inputemail = null;
    private EditText editemail = null;
    private ImageButton submit = null;
    MainActivity context;

    public ForgotPassword()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.forgotpass, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);
        inputemail = (TextInputLayout) view.findViewById(R.id.inputemail);
        editemail = (EditText) view.findViewById(R.id.editemail);
        submit = (ImageButton) view.findViewById(R.id.forgotsubmitbutton);
        submit.setOnClickListener(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return view;
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
        String email_addr = editemail.getText().toString();
        String login = dh.searchLoginByMail(email_addr);
        DialogMessage dialogMessage = new DialogMessage(context);
        Tools tools = new Tools(context);
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
                dialogMessage.pop_up_code(email_addr);
                break;
            default:
                break;
        }
    }
}
