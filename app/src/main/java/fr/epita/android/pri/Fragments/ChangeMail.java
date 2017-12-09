package fr.epita.android.pri.Fragments;


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
import fr.epita.android.pri.Tools.DialogMessage;
import fr.epita.android.pri.Tools.Tools;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class ChangeMail extends Fragment implements View.OnClickListener{

    private EditText email = null;
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
        email = (EditText) view.findViewById(R.id.newmail);
        submit = (ImageButton) view.findViewById(R.id.submitmail);
        submit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        String login = context.getIntent().getExtras().getString("LOGIN");
        String mail = email.getText().toString();
        DialogMessage dialogMessage = new DialogMessage(context);
        switch (view.getId())
        {
            case R.id.submitmail:
                if (mail.equals(""))
                {
                    dialogMessage.pop_up_message("Denied", "You have to put a mail !");
                    break;
                }
                if (Tools.checkMail(mail))
                {
                    dh.changeMail(mail, login);
                    dialogMessage.pop_up_message("Succes", "Your mail has been changed !");
                    //Relation rl = dh.getRelation(login);
                    //Bundle bundle = new Bundle();
                    //bundle.putSerializable("RELATION", rl);
                    context.display_fragment(2);
                    break;
                }
                dialogMessage.pop_up_message("Denied", "The mail format is not correct !");
                break;
            default:
                break;
        }
    }
}
