package fr.epita.android.pri.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.LoginActivity;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class Profil extends Fragment implements View.OnClickListener{

    private TextView login = null;
    private TextView dob = null;
    private TextView level = null;
    private TextView score = null;
    private TextView name = null;
    private TextView email = null;
    private TextView mobile = null;
    private ImageView reset_mail = null;
    private ImageView reset_mobile = null;
    private ImageView profilepic = null;
    private final int request_code = 126;

    DatabaseHandler dh;
    MainActivity context;

    public Profil()
    {

    }


    public void initProfile()
    {
        LoginActivity.rl.setLogged(true);
        login.setText(LoginActivity.rl.getLogin());
        login.setText(login.getText().toString().substring(0,1).toUpperCase() + login.getText().toString().substring(1));
        dob.setText(LoginActivity.rl.getDob());
        name.setText(LoginActivity.rl.getName());
        name.setText(name.getText().toString().substring(0,1).toUpperCase() + name.getText().toString().substring(1));
        email.setText(LoginActivity.rl.getEmail());
        mobile.setText(LoginActivity.rl.getMob());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.profile, container, false);
        context = (MainActivity) getActivity();
        profilepic = (ImageView) view.findViewById(R.id.profile_pic);
        login = (TextView) view.findViewById(R.id.login);
        dob = (TextView) view.findViewById(R.id.dob);
        level = (TextView) view.findViewById(R.id.level);
        score = (TextView) view.findViewById(R.id.score);

        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        mobile = (TextView) view.findViewById(R.id.mobile);
        initProfile();

        reset_mail = (ImageView) view.findViewById(R.id.resetMail);
        reset_mobile = (ImageView) view.findViewById(R.id.resetMobile);

        profilepic.setClickable(true);

        dh = new DatabaseHandler(context);


        if (LoginActivity.rl.getUri() != null)
        {
            Glide.with(this).load(LoginActivity.rl.getUri()).into(profilepic);
        }
        reset_mail.setOnClickListener(this);
        reset_mobile.setOnClickListener(this);
        profilepic.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.resetMail:
                Bundle bundle_mail = new Bundle();
                bundle_mail.putString("LOGIN", LoginActivity.rl.getLogin());
                context.customViewpagerAdapter.changeMail.setArguments(bundle_mail);
                context.display_fragment(1);
                break;
            case R.id.resetMobile:
                Bundle bundle_mobile = new Bundle();
                bundle_mobile.putString("LOGIN", LoginActivity.rl.getLogin());
                context.customViewpagerAdapter.changeMobile.setArguments(bundle_mobile);
                context.display_fragment(2);
                break;
            case R.id.profile_pic:
                Intent intentfiles = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intentfiles.addCategory(Intent.CATEGORY_OPENABLE);
                intentfiles.setType("*/*");
                startActivityForResult(intentfiles, request_code);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {
        if (requestCode == request_code && resultCode == Activity.RESULT_OK)
        {
            if (resultData != null)
            {
                Uri uri = resultData.getData();
                dh.changePicture(uri.toString(), LoginActivity.rl.getLogin());
                //LoginActivity.rl.setUri(uri);
                Glide.with(this).load(uri).into(profilepic);
            }
            else
            {
                Toast.makeText(context, "You have to choose a picture only !", Toast.LENGTH_SHORT);
                return;
            }
        }
    }

}
