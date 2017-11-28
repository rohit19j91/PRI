package fr.epita.android.pri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sadekseridj on 25/11/2017.
 */

public class ChangePassword extends Activity implements View.OnClickListener {

    private DatabaseHandler dh = null;
    private EditText oldpass = null;
    private EditText newpass = null;
    private EditText confirmpass = null;
    private Button submit = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepass);
        dh = new DatabaseHandler(this);
        oldpass = (EditText) findViewById(R.id.oldpass);
        newpass = (EditText) findViewById(R.id.newpass);
        confirmpass = (EditText) findViewById(R.id.confirmpass);
        submit = (Button) findViewById(R.id.submitchange);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        String login = getIntent().getExtras().getString("LOGIN");
        String currentp = dh.searchPass(login);
        String oldp = PasswordFunctions.hashPass(oldpass.getText().toString(), login);
        String newp = newpass.getText().toString();
        String confirmp = confirmpass.getText().toString();
        DialogMessage dialogMessage = new DialogMessage(this);
        switch (view.getId()) {
            case R.id.submitchange:
                // Faire les tests sur la politique de sécurité du mdp
                if (oldp.equals(currentp)) {
                    if (newp.equals(confirmp)) {
                        dh.changePass(newp, login);
                        oldpass.setText("");
                        newpass.setText("");
                        confirmpass.setText("");
                        dialogMessage.pop_up_message("Success", "Your password has been changed");
                        Relation rl = dh.getRelation(login);
                        Intent intent = new Intent(this, Profile.class);
                        intent.putExtra("RELATION", rl);
                        startActivity(intent);
                        break;
                    }
                    dialogMessage.pop_up_message("Denied", "The passwords don't match");
                    break;
                }
                dialogMessage.pop_up_message("Denied", "Error on the old password");
                break;
            default:
                break;
        }
    }
}
