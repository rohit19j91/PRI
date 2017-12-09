package fr.epita.android.pri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by sadekseridj on 09/12/2017.
 */

public class ResetpassActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHandler dh = null;
    private EditText newpass = null;
    private EditText confirmpass = null;
    private ImageButton submit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.resetpass);

        dh = new DatabaseHandler(this);
        newpass = (EditText) findViewById(R.id.newpass);
        confirmpass = (EditText) findViewById(R.id.confirmpass);
        submit = (ImageButton) findViewById(R.id.submitreset);
        submit.setOnClickListener(this);

    }


    @Override
    public void onClick(View view)
    {
        String newpass = this.newpass.getText().toString();
        String confirmpass = this.confirmpass.getText().toString();
        switch (view.getId())
        {
            case R.id.submitreset:
                if (newpass.equals(confirmpass))
                {
                    Intent intent = getIntent();
                    if (intent == null)
                        break;
                    String mail = intent.getExtras().getString("MAIL");
                    String login = dh.searchLoginByMail(mail);
                    dh.changePass(newpass, login);
                    this.newpass.setText("");
                    this.confirmpass.setText("");
                    Toast.makeText(this, "Your password has been reseted !", Toast.LENGTH_SHORT);
                    Intent intent_splash = new Intent(ResetpassActivity.this, SplashScreen.class);
                    startActivity(intent_splash);
                    break;
                }
                else
                    Toast.makeText(this, "The passwords don't match !", Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }
    }
}
