package fr.epita.android.pri;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import fr.epita.android.pri.Tools.Tools;

/**
 * Created by sadekseridj on 09/12/2017.
 */

public class ResetpassActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHandler dh = null;
    private TextInputLayout inputnewpass, inputconfirmpass;
    private EditText editnewpass = null;
    private EditText editconfirmpass = null;
    private ImageButton submit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.resetpass);

        dh = new DatabaseHandler(this);
        inputnewpass = (TextInputLayout) findViewById(R.id.inputnewpass);
        inputconfirmpass = (TextInputLayout) findViewById(R.id.inputconfirmpass);
        editnewpass = (EditText) findViewById(R.id.editnewpass);
        editconfirmpass = (EditText) findViewById(R.id.editconfirmpass);
        submit = (ImageButton) findViewById(R.id.submitreset);
        submit.setOnClickListener(this);

    }


    @Override
    public void onClick(View view)
    {
        String newpass = this.editnewpass.getText().toString();
        String confirmpass = this.editconfirmpass.getText().toString();
        Tools tools = new Tools(this);
        switch (view.getId())
        {
            case R.id.submitreset:
                inputconfirmpass.setErrorEnabled(false);
                if (! tools.checkFieldSignup(editnewpass, inputnewpass, "Error on the password")) {
                    tools.setAnimaton(inputnewpass);
                    break;
                }
                if (! tools.checkFieldSignup(editconfirmpass, inputconfirmpass, "Error on the confirmation password")) {
                    tools.setAnimaton(inputconfirmpass);
                    break;
                }
                if (newpass.equals(confirmpass))
                {
                    Intent intent = getIntent();
                    if (intent == null)
                        break;
                    String mail = intent.getExtras().getString("MAIL");
                    String login = dh.searchLoginByMail(mail);
                    dh.changePass(newpass, login);
                    this.editnewpass.setText("");
                    this.editconfirmpass.setText("");
                    Toast.makeText(this, "Your password has been reseted !", Toast.LENGTH_SHORT);
                    Intent intent_splash = new Intent(ResetpassActivity.this, SplashScreen.class);
                    startActivity(intent_splash);
                    break;
                }
                else
                {
                    inputconfirmpass.setErrorEnabled(true);
                    inputconfirmpass.setError("Password don't match");
                    tools.setAnimaton(inputconfirmpass);
                }
                break;
            default:
                break;
        }
    }
}
