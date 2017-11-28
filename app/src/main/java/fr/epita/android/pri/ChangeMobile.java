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

public class ChangeMobile extends Activity implements View.OnClickListener {

    private EditText mobile = null;
    private Button submit = null;
    private DatabaseHandler dh = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changemobile);
        dh = new DatabaseHandler(this);
        mobile = (EditText) findViewById(R.id.newmobile);
        submit = (Button) findViewById(R.id.submitmobile);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String login = getIntent().getExtras().getString("LOGIN");
        String mob = mobile.getText().toString();
        DialogMessage dialogMessage = new DialogMessage(ChangeMobile.this);
        switch (view.getId())
        {
            case R.id.submitmobile:
                if (mob.equals(""))
                {
                    dialogMessage.pop_up_message("Denied", "You have to put a mobile number !");
                    break;
                }
                if (Tools.checkMobile(mob))
                {
                    dh.changeMobile(mob, login);
                    dialogMessage.pop_up_message("Succes", "Your mobile number has been changed !");
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
