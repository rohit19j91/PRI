package fr.epita.android.pri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sadekseridj on 27/11/2017.
 */

public class ChangeMail extends Activity implements View.OnClickListener {

    private EditText email = null;
    private Button submit = null;
    private DatabaseHandler dh = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changemail);
        dh = new DatabaseHandler(this);
        email = (EditText) findViewById(R.id.newmail);
        submit = (Button) findViewById(R.id.submitmail);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String login = getIntent().getExtras().getString("LOGIN");
        String mail = email.getText().toString();
        DialogMessage dialogMessage = new DialogMessage(ChangeMail.this);
        switch (view.getId())
        {
            case R.id.submitmail:
                if (mail.equals(""))
                {
                    dialogMessage.pop_up_message("Denied", "You have to put a mail !");
                    break;
                }
                if (Tools.checkMail(mail))
                {
                    dh.changeMail(mail, login);
                    dialogMessage.pop_up_message("Succes", "Your mail has been changed !");
                    Relation rl = dh.getRelation(login);
                    Intent intent = new Intent(this, Profile.class);
                    intent.putExtra("RELATION", rl);
                    startActivity(intent);
                    break;
                }
                dialogMessage.pop_up_message("Denied", "The mail format is not correct !");
                break;
            default:
                break;
        }
    }
}
