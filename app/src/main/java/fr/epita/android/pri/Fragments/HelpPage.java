package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;

/**
 * Created by sadekseridj on 01/01/2017.
 */

public class HelpPage extends Fragment {

    private DatabaseHandler dh = null;
    MainActivity context;

    public HelpPage()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.helppage, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);

        return view;
    }
}

