package fr.epita.android.pri.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.SplashScreen;
import fr.epita.android.pri.Tools.DialogMessage;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class ResetPassword extends Fragment implements View.OnClickListener{

    private DatabaseHandler dh = null;
    private EditText newpass = null;
    private EditText confirmpass = null;
    private ImageButton submit = null;
    MainActivity context;

    public ResetPassword()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.resetpass, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);
        newpass = (EditText) view.findViewById(R.id.newpass);
        confirmpass = (EditText) view.findViewById(R.id.confirmpass);
        submit = (ImageButton) view.findViewById(R.id.submitreset);
        submit.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view)
    {
        String newpass = this.newpass.getText().toString();
        String confirmpass = this.confirmpass.getText().toString();
        DialogMessage dialogMessage = new DialogMessage(context);
        switch (view.getId())
        {
            case R.id.submitreset:
                if (newpass.equals(confirmpass))
                {
                    //String login = dh.searchLoginByMail(getIntent().getExtras().getString("MAIL"));
                    String login = context.customViewpagerAdapter.resetPassword.getArguments().getString("MAIL");
                    dh.changePass(newpass, login);
                    this.newpass.setText("");
                    this.confirmpass.setText("");
                    dialogMessage.pop_up_message("Success", "Your password was reset !");
                    Intent intent = new Intent(context, SplashScreen.class);
                    startActivity(intent);
                    //context.display_fragment(2);
                    break;
                }
                else
                    dialogMessage.pop_up_message("Success", "The passwords don't match !");
                break;
            default:
                break;
        }
    }
}
