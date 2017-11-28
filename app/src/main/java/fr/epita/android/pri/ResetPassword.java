package fr.epita.android.pri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sadekseridj on 22/11/2017.
 */

public class ResetPassword extends Activity implements View.OnClickListener {

    private DatabaseHandler dh = null;
    private EditText newpass = null;
    private EditText confirmpass = null;
    private Button submit = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpass);
        dh = new DatabaseHandler(this);
        newpass = (EditText) findViewById(R.id.newpass);
        confirmpass = (EditText) findViewById(R.id.confirmpass);
        submit = (Button) findViewById(R.id.submitreset);
        submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        String newpass = this.newpass.getText().toString();
        String confirmpass = this.confirmpass.getText().toString();
        DialogMessage dialogMessage = new DialogMessage(this);
        switch (view.getId())
        {
            case R.id.submitreset:
                if (newpass.equals(confirmpass))
                {
                    String login = dh.searchLoginByMail(getIntent().getExtras().getString("MAIL"));
                    dh.changePass(newpass, login);
                    this.newpass.setText("");
                    this.confirmpass.setText("");
                    dialogMessage.pop_up_message("Success", "Your password was reset !");
                    Intent intent = new Intent(this, Splashscreen.class);
                    startActivity(intent);
                    break;
                }
                else
                    dialogMessage.pop_up_message("Success", "The passwords don't match !");
                    break;
            default:
                break;
        }
    }
}
