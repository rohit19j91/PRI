package fr.epita.android.pri;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button loginbut;
    EditText login,password;
    TextView forgot,signup;


DatabaseHandler dh= new DatabaseHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbut= (Button) findViewById(R.id.loginbutton);
        login= (EditText) findViewById(R.id.logintext);
        password= (EditText) findViewById(R.id.passwordtext);
        forgot= (TextView) findViewById(R.id.forgotpass);
        signup= (TextView) findViewById(R.id.signup);

        loginbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),SignIn.class);

         String loginuser=login.getText().toString();
         String passuser=password.getText().toString();
         String passdb=dh.searchPass(loginuser);

         if(passuser.equals(passdb))
         {
if(loginuser.equalsIgnoreCase("admin") && passuser.equalsIgnoreCase("pass") ){
          Toast.makeText(getApplicationContext(),"Redirecting... Login Successful",Toast.LENGTH_SHORT).show();
                    startActivity(intent);}
                }
                else{
             Toast.makeText(getApplicationContext(),"Username or password is incorrect !",Toast.LENGTH_SHORT).show();
         }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),ForgotPassword.class);
                Toast.makeText(getApplicationContext(),
                        "Forgot Password ? Really !!",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
                Toast.makeText(getApplicationContext(),
                        "Thats a super Idea !!",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


    }
}
