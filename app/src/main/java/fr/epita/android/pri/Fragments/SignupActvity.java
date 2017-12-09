package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.LoginActivity;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.Relation;
import fr.epita.android.pri.Tools.DialogMessage;
import fr.epita.android.pri.Tools.PasswordFunctions;
import fr.epita.android.pri.Tools.Tools;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class SignupActvity extends Fragment implements View.OnClickListener{

    EditText mob, email, name, login, pass, confpass;
    TextInputLayout inputmob, inputemail, inputname, inputlogin, inputpass, inputconfpass;
    DatabaseHandler dh;
    DialogMessage dialogMessage;
    MainActivity context;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.signup, container, false);
        context = (MainActivity) getActivity();

        dh = new DatabaseHandler(context);
        dialogMessage = new DialogMessage(context);

        Button sbbtn = (Button) view.findViewById(R.id.signupsubmitbutton);

        mob = (EditText) view.findViewById(R.id.editmobile);
        email = (EditText) view.findViewById(R.id.editemail);
        name = (EditText) view.findViewById(R.id.editemail);
        login = (EditText) view.findViewById(R.id.editlogin);
        pass = (EditText) view.findViewById(R.id.editpass);
        confpass = (EditText) view.findViewById(R.id.editpassconfirm);

        inputmob = (TextInputLayout) view.findViewById(R.id.inputmobile);
        inputemail = (TextInputLayout) view.findViewById(R.id.inputemail);
        inputname = (TextInputLayout) view.findViewById(R.id.inputname);
        inputlogin = (TextInputLayout) view.findViewById(R.id.inputlogin);
        inputpass = (TextInputLayout) view.findViewById(R.id.inputpass);
        inputconfpass = (TextInputLayout) view.findViewById(R.id.inputpassconfirm);

        sbbtn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        Tools tools = new Tools(context);
        switch (view.getId())
        {
            case R.id.signupsubmitbutton:
                inputconfpass.setErrorEnabled(false);
                if (! tools.checkEmail(email, inputemail, "Error on the email address")) {
                    tools.setAnimaton(inputemail);
                    break;
                }
                if (! tools.checkFieldSignup(name, inputname, "Error on the name")) {
                    tools.setAnimaton(inputname);
                    break;
                }
                if (! tools.checkFieldSignup(mob, inputmob, "Error on the number mobile")) {
                    tools.setAnimaton(inputmob);
                    break;
                }
                if (! tools.checkFieldSignup(login, inputlogin, "Error on the login")) {
                    tools.setAnimaton(inputlogin);
                    break;
                }
                if (! tools.checkFieldSignup(pass, inputpass, "Error on the password")) {
                    tools.setAnimaton(inputpass);
                    break;
                }
                if (! tools.checkFieldSignup(confpass, inputconfpass, "Error on the password confirmation")) {
                    tools.setAnimaton(inputconfpass);
                    break;
                }
                inputmob.setErrorEnabled(false);
                inputemail.setErrorEnabled(false);
                inputname.setErrorEnabled(false);
                inputlogin.setErrorEnabled(false);
                inputpass.setErrorEnabled(false);
                inputconfpass.setErrorEnabled(false);

                String mobile = mob.getText().toString();
                String namestr = name.getText().toString();
                String emailstr = email.getText().toString();
                String loginstr = login.getText().toString();
                String passstr = pass.getText().toString();
                String confpassstr = confpass.getText().toString();

                if (passstr.equals(confpassstr)) {
                    // Ajouter le sexe
                    LoginActivity.rl = new Relation();
                    LoginActivity.rl.setName(namestr);
                    LoginActivity.rl.setEmail(emailstr);
                    LoginActivity.rl.setLogin(loginstr);
                    LoginActivity.rl.setPass(PasswordFunctions.hashPass(passstr, loginstr));
                    LoginActivity.rl.setUri(null);

                    dh.insertdata(LoginActivity.rl);

                    Toast.makeText(context, "You are successfully Registered !!", Toast.LENGTH_SHORT).show();
                    context.display_fragment(2);
                }

                else {
                    inputconfpass.setErrorEnabled(true);
                    inputconfpass.setError("Error on the password confirmation");
                    tools.setAnimaton(inputconfpass);
                }
        }

    }
}
