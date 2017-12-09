package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import fr.epita.android.pri.Tools.Tools;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class ChangePassword extends Fragment implements View.OnClickListener{

    private DatabaseHandler dh = null;
    private TextInputLayout inputoldpass = null;
    private EditText editoldpass = null;
    private TextInputLayout inputnewpass = null;
    private EditText editnewpass = null;
    private TextInputLayout inputconfirmpass;
    private EditText editconfirmpass = null;
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
        inputoldpass = (TextInputLayout) view.findViewById(R.id.inputoldpass);
        editoldpass = (EditText) view.findViewById(R.id.editoldpass);
        inputnewpass = (TextInputLayout) view.findViewById(R.id.inputnewpass);
        editnewpass = (EditText) view.findViewById(R.id.editnewpass);
        inputconfirmpass = (TextInputLayout) view.findViewById(R.id.inputconfirmpass);
        editconfirmpass = (EditText) view.findViewById(R.id.editconfirmpass);
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
        String oldp = PasswordFunctions.hashPass(editoldpass.getText().toString(), login);
        String newp = editnewpass.getText().toString();
        String confirmp = editconfirmpass.getText().toString();
        DialogMessage dialogMessage = new DialogMessage(context);
        Tools tools = new Tools(context);
        switch (view.getId()) {
            case R.id.submitchange:
                inputconfirmpass.setErrorEnabled(false);
                inputoldpass.setErrorEnabled(false);
                // Faire les tests sur la politique de sécurité du mdp
                if (! tools.checkFieldSignup(editoldpass, inputoldpass, "Please put your old password"))
                {
                    tools.setAnimaton(inputoldpass);
                    break;
                }
                if (oldp.equals(currentp)) {
                    if (! tools.checkFieldSignup(editnewpass, inputnewpass, "Please put your password"))
                    {
                        tools.setAnimaton(inputnewpass);
                        break;
                    }
                    if (! tools.checkFieldSignup(editconfirmpass, inputconfirmpass, "Please confirm your password"))
                    {
                        tools.setAnimaton(inputconfirmpass);
                        break;
                    }
                    if (newp.equals(confirmp)) {
                        dh.changePass(newp, login);
                        editoldpass.setText("");
                        editnewpass.setText("");
                        editconfirmpass.setText("");
                        dialogMessage.pop_up_message("Success", "Your password has been changed");
                        context.display_fragment(2);
                        break;
                    }
                    inputconfirmpass.setErrorEnabled(true);
                    inputconfirmpass.setError("The passwords don't match");
                    tools.setAnimaton(inputconfirmpass);
                    break;
                }
                inputoldpass.setErrorEnabled(true);
                inputoldpass.setError("Error on the old password");
                tools.setAnimaton(inputoldpass);
                break;
            default:
                break;
        }
    }
}
