package fr.epita.android.pri.Fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.LoginActivity;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.Tools.DialogMessage;
import fr.epita.android.pri.Tools.Tools;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class ChangeMobile extends Fragment implements View.OnClickListener{

    private TextInputLayout inputmobile = null;
    private EditText editmobile = null;
    private ImageButton submit = null;
    private DatabaseHandler dh = null;
    final MainActivity context = (MainActivity) getActivity();

    public ChangeMobile()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.changemobile, container, false);
        dh = new DatabaseHandler(context);
        inputmobile = (TextInputLayout) view.findViewById(R.id.inputnewmobile);
        editmobile = (EditText) view.findViewById(R.id.editnewmobile);
        submit = (ImageButton) view.findViewById(R.id.submitmobile);
        submit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        //String login = getIntent().getExtras().getString("LOGIN");
        String login = LoginActivity.rl.getLogin();
        String mob = editmobile.getText().toString();
        DialogMessage dialogMessage = new DialogMessage(context);
        Tools tools = new Tools(context);
        switch (view.getId())
        {
            case R.id.submitmobile:
                inputmobile.setErrorEnabled(false);
                if (mob.trim().isEmpty() || mob.length() != 10)
                {
                    inputmobile.setErrorEnabled(true);
                    inputmobile.setError("The mobile format is not correct");
                    tools.setAnimaton(inputmobile);
                    break;
                }
                dh.changeMobile(mob, login);
                dialogMessage.pop_up_message("Succes", "Your mobile number has been changed !");
                context.display_fragment(2);
                break;
            default:
                break;
        }

    }
}
