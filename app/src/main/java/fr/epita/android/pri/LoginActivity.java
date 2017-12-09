package fr.epita.android.pri;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import fr.epita.android.pri.Tools.PasswordFunctions;

/**
 * Created by sadekseridj on 09/12/2017.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button loginbut,viewall;
    EditText login, password;
    TextView forgot, signup;
    LoginButton fbloginbut;
    SignInButton googlesignbut;
    CallbackManager callbackManager;

    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;

    DatabaseHandler dh ;

    public static Relation rl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loginpage);
        FacebookSdk.sdkInitialize(this);

        loginbut = (Button) findViewById(R.id.loginbutton);
        login = (EditText) findViewById(R.id.editloginconnex);
        password = (EditText) findViewById(R.id.editpassconnex);
        viewall = (Button) findViewById(R.id.showall);
        forgot = (TextView) findViewById(R.id.forgotpass);
        signup = (TextView) findViewById(R.id.signup);
        fbloginbut = (LoginButton) findViewById(R.id.facebook_login);

        dh= new DatabaseHandler(this);

        //Google Plus Sign In
        googlesignbut = (SignInButton) findViewById(R.id.google_login);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

        // Facebook Login
        callbackManager = CallbackManager.Factory.create();

        fbloginbut.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(LoginActivity.this,
                        "Login Successful !", Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this,
                        "Login Failed !", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        loginbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginuser = login.getText().toString();
                System.out.println("LOGIN: " + loginuser);
                String passuser = PasswordFunctions.hashPass(password.getText().toString(), loginuser);
                System.out.println("PASSUSER: " + passuser);
                String passdb = dh.searchPass(loginuser);
                System.out.println("PASSDB: " + passdb);

                if (passuser.equals(passdb)) {
                    Toast.makeText(LoginActivity.this, "Redirecting... Login Successful", Toast.LENGTH_SHORT).show();
                    rl = dh.getRelation(loginuser);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("FRAGMENT", 9);
                    startActivity(intent);
                    //display_fragment(3);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Username or password is incorrect !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,
                        "Forgot Password ? Really !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("FRAGMENT", 0);
                startActivity(intent);
                //context.display_fragment(1);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,
                        "Thats a super Idea !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("FRAGMENT", 1);
                startActivity(intent);
                //context.display_fragment(2);
            }
        });

        googlesignbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.google_login:
                        signIn();
                        break;
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return true;
    }

    public void viewevery()
    {
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=dh.getAllData();
                if(res.getCount()==0)
                {
                    showmessage("Error","Nothing there in the database");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append("Id: "+res.getString(0)+"\n");
                    buffer.append("Name: "+res.getString(1)+"\n");
                    buffer.append("Email: "+res.getString(2)+"\n");
                    buffer.append("Login: "+res.getString(3)+"\n");
                }
                showmessage("Data",buffer.toString());
            }
        });
    }



    public void showmessage(String message,String title)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            resulthandler(result);
        }

    }


    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    private void signOut() {
    }

    private void resulthandler(GoogleSignInResult result) {
        if(result.isSuccess())
        {
            // Intent it=new Intent(getApplicationContext(),Profile.class);
            GoogleSignInAccount account=result.getSignInAccount();
            String name=account.getDisplayName();
            String email=account.getEmail();
            updateUI(true);
            //    startActivity(it);

        }
        else {
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin) {

        if (isLogin==true)
        {
            googlesignbut.setVisibility(View.GONE);
           /* Intent it=new Intent(getApplicationContext(),Profile.class);
            startActivity(it);*/
        }
        else
        {
            googlesignbut.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //Not required Check (View.onClickable Interface)
    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_login:
                signIn();
                break;}
    }*/
}
