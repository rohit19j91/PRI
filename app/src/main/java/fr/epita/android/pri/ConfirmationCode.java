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

public class ConfirmationCode extends Activity implements View.OnClickListener{

    private EditText code_user = null;
    private Button submit = null;
    private String email_addr = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmationcode);
        code_user = (EditText) findViewById(R.id.putcode);
        submit = (Button) findViewById(R.id.submitcode);
        submit.setOnClickListener(this);
        DialogMessage dialogMessage = new DialogMessage(this);
        dialogMessage.pop_up_message("Succes", "The code has been sent, thank you.");
    }

    /*
      * Compare the code sent and the code put by the user
     */
    public boolean check_code(int code_user)
    {
        Intent intent = getIntent();
        if (intent == null)
            return false;
        Bundle bundle = intent.getExtras();
        int code_sent = bundle.getInt("CODE");
        email_addr = bundle.getString("MAIL");
        if (code_sent != - 1 && code_sent == code_user)
            return true;
        return false;
    }

    @Override
    public void onClick(View view)
    {
        int code_put = Integer.valueOf(code_user.getText().toString());
        switch (view.getId())
        {
            case R.id.submitcode:
                if (check_code(code_put))
                {
                    code_user.setText("");
                    Intent intent = new Intent(this, ResetPassword.class);
                    intent.putExtra("MAIL", email_addr);
                    startActivity(intent);
                    break;
                }
                else
                {
                    DialogMessage dialogMessage = new DialogMessage(this);
                    dialogMessage.pop_up_message("Access Denied", "Wrong code ! Retry please.");
                    break;
                }
            default:
                break;
        }
    }
}
