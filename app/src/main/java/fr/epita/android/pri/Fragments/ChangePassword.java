package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
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
import fr.epita.android.pri.Tools.PasswordFunctions;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class ChangePassword extends Fragment implements View.OnClickListener{

    private DatabaseHandler dh = null;
    private EditText oldpass = null;
    private EditText newpass = null;
    private EditText confirmpass = null;
    private ImageButton submit = null;
    private DrawerLayout drawerLayout = null;
    MainActivity context;

    public ChangePassword()
    {

    }
   /* @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(4).setChecked(true);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.changepass, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);
        oldpass = (EditText) view.findViewById(R.id.oldpass);
        newpass = (EditText) view.findViewById(R.id.newpass);
        confirmpass = (EditText) view.findViewById(R.id.confirmpass);
        submit = (ImageButton) view.findViewById(R.id.submitchange);
        submit.setOnClickListener(this);

        //drawerLayout = (DrawerLayout) findViewById(R.menu.subtoolbar);

        return view;

    }

    @Override
    public void onClick(View view)
    {
        //String login = getIntent().getExtras().getString("LOGIN");
        String login = LoginActivity.rl.getLogin();
        String currentp = dh.searchPass(login);
        String oldp = PasswordFunctions.hashPass(oldpass.getText().toString(), login);
        String newp = newpass.getText().toString();
        String confirmp = confirmpass.getText().toString();
        DialogMessage dialogMessage = new DialogMessage(context);
        switch (view.getId()) {
            case R.id.submitchange:
                // Faire les tests sur la politique de sécurité du mdp
                if (oldp.equals(currentp)) {
                    if (newp.equals(confirmp)) {
                        dh.changePass(newp, login);
                        oldpass.setText("");
                        newpass.setText("");
                        confirmpass.setText("");
                        dialogMessage.pop_up_message("Success", "Your password has been changed");
                       // Relation rl = dh.getRelation(login);
                        //Intent intent = new Intent(this, Profile.class);
                        //intent.putExtra("RELATION", rl);
                        //startActivity(intent);
                        context.display_fragment(2);
                        break;
                    }
                    dialogMessage.pop_up_message("Denied", "The passwords don't match");
                    break;
                }
                dialogMessage.pop_up_message("Denied", "Error on the old password");
                break;
            default:
                break;
        }
    }
}
