package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.Tools.DialogMessage;
import fr.epita.android.pri.Tools.Tools;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class ConfirmationCode extends Fragment implements View.OnClickListener{

    private TextInputLayout inputcode = null;
    private EditText editcode = null;
    private ImageButton submit = null;
    private String email_addr = "";
    MainActivity context;

    public ConfirmationCode()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.confirmationcode, container, false);
        context = (MainActivity) getActivity();
        inputcode = (TextInputLayout) view.findViewById(R.id.inputcode);
        editcode = (EditText) view.findViewById(R.id.editcode);
        submit = (ImageButton) view.findViewById(R.id.submitcode);
        submit.setOnClickListener(this);
        DialogMessage dialogMessage = new DialogMessage(context);
        //dialogMessage.pop_up_message("Succes", "The code has been sent, thank you.");

        return view;
    }

    @Override
    public void onClick(View view)
    {
        int code_put = Integer.valueOf(editcode.getText().toString());
        Tools tools = new Tools(context);
        switch (view.getId())
        {
            case R.id.submitcode:
                inputcode.setErrorEnabled(false);
                int code_sent = context.customViewpagerAdapter.confirmationCode.getArguments().getInt("CODE");
                email_addr = context.customViewpagerAdapter.confirmationCode.getArguments().getString("MAIL");
                if (code_sent != - 1 && code_sent == code_put)
                {
                    editcode.setText("");
                    Bundle bundle = new Bundle();
                    bundle.putString("MAIL", email_addr);
                    context.customViewpagerAdapter.resetPassword.setArguments(bundle);
                    context.display_fragment(6);
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
