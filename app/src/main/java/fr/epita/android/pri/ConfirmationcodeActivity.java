package fr.epita.android.pri;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import fr.epita.android.pri.Tools.Tools;

/**
 * Created by sadekseridj on 09/12/2017.
 */

public class ConfirmationcodeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout inputcode = null;
    private EditText editcode = null;
    private ImageButton submit = null;
    private String email_addr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmationcode);

        inputcode = (TextInputLayout) findViewById(R.id.inputcode);
        editcode = (EditText) findViewById(R.id.editcode);
        submit = (ImageButton) findViewById(R.id.submitcode);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        Tools tools = new Tools(this);
        Intent intent = getIntent();
        switch (view.getId())
        {
            case R.id.submitcode:
                inputcode.setErrorEnabled(false);
             if (editcode.getText().toString().trim().isEmpty() || editcode.getText().toString().length() > 6
                     || editcode.getText().toString().length() < 6)
             {
                inputcode.setErrorEnabled(true);
                inputcode.setError("Wrong code ! Retry please.");
                tools.setAnimaton(inputcode);
                break;
            }
                int code_put = Integer.valueOf(editcode.getText().toString());
                if (intent == null)
                    break;
                int code_sent = intent.getExtras().getInt("CODE");
                email_addr = intent.getExtras().getString("MAIL");
                if (code_sent != - 1 && code_sent == code_put)
                {
                    editcode.setText("");
                    Bundle bundle = new Bundle();
                    Intent intent_reset = new Intent(ConfirmationcodeActivity.this, ResetpassActivity.class);
                    intent_reset.putExtra("MAIL", email_addr);
                    startActivity(intent_reset);
                    break;
                }
                else
                {
                    inputcode.setErrorEnabled(true);
                    inputcode.setError("Wrong code ! Retry please.");
                    tools.setAnimaton(inputcode);
                    break;
                }
            default:
                break;
        }
    }
}
