package fr.epita.android.pri;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Rohit on 11/19/2017.
 */

public class Profile extends Activity implements View.OnClickListener {

    private TextView profilename = null;
    private TextView firstname = null;
    private TextView name = null;
    private TextView email = null;
    private TextView mobile = null;
    private Button reset_mail = null;
    private Button reset_mobile = null;
    private Button reset_pass = null;
    private ImageView profilepic = null;
    public Relation rl = null;
    private final int request_code = 126;

    public void initProfile()
    {
        this.rl = (Relation) getIntent().getSerializableExtra("RELATION");
        rl.setLogged(true);
        String [] fullname = rl.getName().split(" ");
        profilename.setText(rl.getLogin());
        firstname.setText("Firstname: " + fullname[0]);
        name.setText("Name: " + fullname[1]);
        email.setText("E-mail: " + rl.getEmail());
        mobile.setText("Mobile: " + rl.getMob());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        profilename = (TextView) findViewById(R.id.profile_name);
        firstname = (TextView) findViewById(R.id.firstname);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        mobile = (TextView) findViewById(R.id.mobile);
        initProfile();

        reset_mail = (Button) findViewById(R.id.resetMail);
        reset_mobile = (Button) findViewById(R.id.resetMobile);
        reset_pass = (Button) findViewById(R.id.resetPass);
        profilepic = (ImageView) findViewById(R.id.profile_pic);
        profilepic.setClickable(true);

        reset_mail.setOnClickListener(this);
        reset_mobile.setOnClickListener(this);
        reset_pass.setOnClickListener(this);
        profilepic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.resetMail:
                Intent intentmail = new Intent(this, ChangeMail.class);
                intentmail.putExtra("LOGIN", rl.getLogin());
                startActivity(intentmail);
                break;
            case R.id.resetMobile:
                Intent intentmobile = new Intent(this, ChangeMobile.class);
                intentmobile.putExtra("LOGIN", rl.getLogin());
                startActivity(intentmobile);
                break;
            case R.id.resetPass:
                System.out.println("CHANGE PASS");
                Intent intentpass = new Intent(this, ChangePassword.class);
                intentpass.putExtra("LOGIN", rl.getLogin());
                startActivity(intentpass);
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
            Uri uri = null;
            if (resultData != null)
            {
                uri = resultData.getData();
                Glide.with(this).load(uri).into(profilepic);
                        //InputStream inputStream = getContentResolver().openInputStream(uri);
                        //Drawable drawable = Drawable.createFromStream(inputStream, null);
                        //profilepic.setImageDrawable(drawable);

            }
            else
            {
                DialogMessage dialogMessage = new DialogMessage(Profile.this);
                dialogMessage.pop_up_message("Denied", "You have to choose a picture only !");
                return;
            }
        }
    }
}
