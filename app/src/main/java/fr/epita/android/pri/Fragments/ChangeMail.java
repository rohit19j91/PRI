package fr.epita.android.pri.Fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.Tools.Tools;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class ChangeMail extends Fragment implements View.OnClickListener{

    private TextInputLayout inputemail = null;
    private EditText editemail = null;
    private ImageButton submit = null;
    private DatabaseHandler dh = null;
    MainActivity context;

    public ChangeMail()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.changemail, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);
        inputemail = (TextInputLayout) view.findViewById(R.id.inputnewmail);
        editemail = (EditText) view.findViewById(R.id.editnewemail);
        submit = (ImageButton) view.findViewById(R.id.submitmail);
        submit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        String login = context.getIntent().getExtras().getString("LOGIN");
        String mail = editemail.getText().toString();
        Tools tools = new Tools(context);
        switch (view.getId())
        {
            case R.id.submitmail:
                if (! tools.checkEmail(editemail, inputemail, "The mail format is not correct"))
                {
                    tools.setAnimaton(inputemail);
                    break;
                }
                dh.changeMail(mail, login);
                Toast.makeText(context, "Your mail has been changed !", Toast.LENGTH_SHORT).show();
                context.display_fragment(0);
                break;
            default:
                break;
        }
    }
}
