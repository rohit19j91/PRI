package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;

/**
 * Created by sadekseridj on 11/12/2017.
 */

public class DisplayPageWeb extends Fragment {

    public WebView webView;
    private DatabaseHandler dh = null;
    MainActivity context;

    public DisplayPageWeb()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState) {

        final View view = inflater.inflate(R.layout.displaypageweb, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);

        webView = (WebView) view.findViewById(R.id.webpageview);

        return view;
    }

}
