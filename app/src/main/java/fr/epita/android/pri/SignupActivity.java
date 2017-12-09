package fr.epita.android.pri;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

import fr.epita.android.pri.Tools.PasswordFunctions;
import fr.epita.android.pri.Tools.Tools;

/**
 * Created by sadekseridj on 09/12/2017.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mob, email, name, login, pass, confpass, dob;
    TextInputLayout inputmob, inputemail, inputname, inputlogin, inputpass, inputconfpass, inputdob;
    DatabaseHandler dh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        dh = new DatabaseHandler(this);

        Button sbbtn = (Button) findViewById(R.id.signupsubmitbutton);

        mob = (EditText) findViewById(R.id.editmobile);
        email = (EditText) findViewById(R.id.editemail);
        name = (EditText) findViewById(R.id.editemail);
        login = (EditText) findViewById(R.id.editlogin);
        pass = (EditText) findViewById(R.id.editpass);
        confpass = (EditText) findViewById(R.id.editpassconfirm);
        dob = (EditText) findViewById(R.id.editdob);

        inputmob = (TextInputLayout) findViewById(R.id.inputmobile);
        inputemail = (TextInputLayout) findViewById(R.id.inputemail);
        inputname = (TextInputLayout) findViewById(R.id.inputname);
        inputlogin = (TextInputLayout) findViewById(R.id.inputlogin);
        inputpass = (TextInputLayout) findViewById(R.id.inputpass);
        inputconfpass = (TextInputLayout) findViewById(R.id.inputpassconfirm);
        inputdob = (TextInputLayout) findViewById(R.id.inputdob);

        sbbtn.setOnClickListener(this);
    }

    private boolean checkDOB()
    {
        if (dob.getText().toString().trim().isEmpty())
        {
            inputdob.setError("Error on the D.O.B format");
            inputdob.setError("Error on the D.O.B format BIS");
            return false;
        }
        boolean isValid = false;
        String [] split_dob = dob.getText().toString().split("/");
        if (split_dob.length != 3)
        {
            inputdob.setError("Error on the D.O.B format");
            inputdob.setError("Error on the D.O.B format BIS");
            return false;
        }
        int day = Integer.parseInt(split_dob[0]);
        int month = Integer.parseInt(split_dob[1]);
        int year = Integer.parseInt(split_dob[2]);
        if (day > 0 && day < 32 && month > 0 && month < 13 && year > 1900
                && year < java.util.Calendar.getInstance().get(Calendar.YEAR))
        {
            inputdob.setErrorEnabled(false);
            return true;
        }
        else
        {
            inputdob.setErrorEnabled(true);
            inputdob.setError("Error on the D.O.B format");
            return false;
        }
    }


    @Override
    public void onClick(View view) {
        Tools tools = new Tools(this);
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
                if (! tools.checkFieldSignup(dob, inputdob, "Error on the D.O.B")) {
                    tools.setAnimaton(inputdob);
                    break;
                }

                String mobile = mob.getText().toString();
                String namestr = name.getText().toString();
                String emailstr = email.getText().toString();
                String loginstr = login.getText().toString();
                String passstr = pass.getText().toString();
                String confpassstr = confpass.getText().toString();
                Date date_birth = Date.valueOf(dob.getText().toString());

                if (passstr.equals(confpassstr)) {
                    // Ajouter le sexe
                    LoginActivity.rl = new Relation();
                    LoginActivity.rl.setMob(mobile);
                    LoginActivity.rl.setName(namestr);
                    LoginActivity.rl.setEmail(emailstr);
                    LoginActivity.rl.setLogin(loginstr);
                    LoginActivity.rl.setPass(PasswordFunctions.hashPass(passstr, loginstr));
                    LoginActivity.rl.setUri(null);
                    LoginActivity.rl.setDob(date_birth);

                    dh.insertdata(LoginActivity.rl);

                    mob.setText("");
                    name.setText("");
                    email.setText("");
                    login.setText("");
                    pass.setText("");
                    confpass.setText("");
                    dob.setText("");

                    Toast.makeText(this, "You are successfully Registered !!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    intent.putExtra("FRAGMENT", 0);
                    startActivity(intent);
                }

                else {
                    inputconfpass.setErrorEnabled(true);
                    inputconfpass.setError("Error on the password confirmation");
                    tools.setAnimaton(inputconfpass);
                }
        }

    }
}
