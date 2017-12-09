package fr.epita.android.pri.Fragments;


import android.os.Bundle;
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

    private EditText mobile = null;
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
        mobile = (EditText) view.findViewById(R.id.newmobile);
        submit = (ImageButton) view.findViewById(R.id.submitmobile);
        submit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        //String login = getIntent().getExtras().getString("LOGIN");
        String login = LoginActivity.rl.getLogin();
        String mob = mobile.getText().toString();
        DialogMessage dialogMessage = new DialogMessage(context);
        switch (view.getId())
        {
            case R.id.submitmobile:
                if (mob.equals(""))
                {
                    dialogMessage.pop_up_message("Denied", "You have to put a mobile number !");
                    break;
                }
                if (Tools.checkMobile(mob))
                {
                    dh.changeMobile(mob, login);
                    dialogMessage.pop_up_message("Succes", "Your mobile number has been changed !");
                    //Relation rl = dh.getRelation(login);
                    //Bundle bundle = new Bundle();
                    //bundle.putSerializable("RELATION", rl);
                    //context.customViewpagerAdapter.profil.setArguments(bundle);
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
