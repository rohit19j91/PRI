package fr.epita.android.pri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Rohit on 10/30/2017.
 */

public class SignupActivity extends Activity implements View.OnClickListener{

    DatabaseHandler dh = new DatabaseHandler(this);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        Button sbbtn = (Button) findViewById(R.id.signupsubmitbutton);


        sbbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signupsubmitbutton) {
            EditText mob = (EditText) findViewById(R.id.signupmobtext);
            EditText email = (EditText) findViewById(R.id.signupemailtext);
            EditText name = (EditText) findViewById(R.id.signupnametext);
            EditText login = (EditText) findViewById(R.id.signuplogintext);
            EditText pass = (EditText) findViewById(R.id.signuppasstext);
            EditText confpass = (EditText) findViewById(R.id.signupconfpasstext);


            String mobile = mob.getText().toString();
            String namestr = name.getText().toString();
            String emailstr = email.getText().toString();
            String loginstr = login.getText().toString();
            String passstr = pass.getText().toString();
            String confpassstr = confpass.getText().toString();


            if (passstr.equals(confpassstr)) {
                Relation rl = new Relation();

                rl.setMob(mobile);
                rl.setName(namestr);
                rl.setEmail(emailstr);
                rl.setLogin(loginstr);
                rl.setPass(PasswordFunctions.hashPass(passstr, loginstr));

                dh.insertdata(rl);

                Intent intent = new Intent(this, Profile.class);
                intent.putExtra("RELATION", rl);
                startActivity(intent);
            }

            else {
                Toast passmsg = Toast.makeText(SignupActivity.this, "Passwords dont match", Toast.LENGTH_LONG);
                passmsg.show();
            }
        }

    }
}


