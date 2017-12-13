package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.Tools.CustomWebViewListAdapter;

/**
 * Created by sadekseridj on 11/12/2017.
 */

public class WebViewList extends Fragment {
    public ListView listView;
    TextView emptyListView;
    public WebView webView;
    private DatabaseHandler dh = null;
    MainActivity context;
    public ArrayList<String> listWeb; //


    public WebViewList()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.listwebview, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);

        context.getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        listView = (ListView) view.findViewById(R.id.webviewlist);
        emptyListView = (TextView) view.findViewById(R.id.emptylistwebview);

        listWeb = new ArrayList<>();
        listWeb.add("https://google.com");
        listWeb.add("https://google.com");

        CustomWebViewListAdapter customWebViewListAdapter = new CustomWebViewListAdapter(context, listWeb);
        listView.setAdapter(customWebViewListAdapter);

        listView.setClickable(true);

        return view;
    }

    public void emptyList()
    {
        if (listWeb.isEmpty())
            listView.setEmptyView(emptyListView);
    }
}
