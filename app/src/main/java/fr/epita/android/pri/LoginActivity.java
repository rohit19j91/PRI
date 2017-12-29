        package fr.epita.android.pri;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
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
    CheckBox rememberMe;
    LoginButton fbloginbut;
    SignInButton googlesignbut;
    CallbackManager callbackManager;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor editor;
    private boolean saveLogin;

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
        rememberMe = (CheckBox) findViewById(R.id.rememberme);
        viewall = (Button) findViewById(R.id.showall);
        forgot = (TextView) findViewById(R.id.forgotpass);
        signup = (TextView) findViewById(R.id.signup);
        fbloginbut = (LoginButton) findViewById(R.id.facebook_login);

        loginPreferences = getSharedPreferences("loginPreferences", MODE_PRIVATE);
        editor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true)
        {
            login.setText(loginPreferences.getString("login", ""));
            password.setText(loginPreferences.getString("password", ""));
            rememberMe.setChecked(true);
        }

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

                String passuser = PasswordFunctions.hashPass(password.getText().toString(), loginuser);



                String passdb = dh.searchPass(loginuser);



                if (passuser.equals(passdb)) {
                    if (rememberMe.isChecked())
                    {
                        editor.putBoolean("saveLogin", true);
                        editor.putString("login", loginuser);
                        editor.putString("password", password.getText().toString());
                        editor.commit();
                    }
                    else
                    {
                        editor.clear();
                        editor.commit();
                    }
                    login.setText("");
                    password.setText("");
                    Toast.makeText(LoginActivity.this, "Redirecting... Login Successful", Toast.LENGTH_SHORT).show();
                    rl = dh.getRelation(loginuser);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("FRAGMENT", 5);
                    startActivity(intent);
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
                Intent intent = new Intent(LoginActivity.this, ForgotpassActivity.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,
                        "Thats a super Idea !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
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

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(LoginActivity.this,Quizquestionsjson.class);
              //  startActivity(intent);
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return true;
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