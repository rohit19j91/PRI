package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.Tools.DialogMessage;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class ConfirmationCode extends Fragment implements View.OnClickListener{

    private EditText code_user = null;
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
        code_user = (EditText) view.findViewById(R.id.putcode);
        submit = (ImageButton) view.findViewById(R.id.submitcode);
        submit.setOnClickListener(this);
        DialogMessage dialogMessage = new DialogMessage(context);
        //dialogMessage.pop_up_message("Succes", "The code has been sent, thank you.");

        return view;
    }

    @Override
    public void onClick(View view)
    {
        int code_put = Integer.valueOf(code_user.getText().toString());
        switch (view.getId())
        {
            case R.id.submitcode:
                int code_sent = context.customViewpagerAdapter.confirmationCode.getArguments().getInt("CODE");
                email_addr = context.customViewpagerAdapter.confirmationCode.getArguments().getString("MAIL");
                if (code_sent != - 1 && code_sent == code_put)
                {
                    code_user.setText("");
                    //Intent intent = new Intent(this, ResetPassword.class);
                    //intent.putExtra("MAIL", email_addr);
                    //startActivity(intent);
                    Bundle bundle = new Bundle();
                    bundle.putString("MAIL", email_addr);
                    context.customViewpagerAdapter.resetPassword.setArguments(bundle);
                    context.display_fragment(6);
                    break;
                }
                else
                {
                    DialogMessage dialogMessage = new DialogMessage(context);
                    dialogMessage.pop_up_message("Access Denied", "Wrong code ! Retry please.");
                    break;
                }
            default:
                break;
        }
    }
}
