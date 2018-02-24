package fr.epita.android.pri.Fragments;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;

/**
 * Created by Rohit on 1/20/2018.
 */

public class Aboutus extends Fragment {



    DatabaseHandler dh = null;
    MainActivity context;

    public Aboutus(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.aboutus, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);
        return view;
        }

}
