package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.SignNewsAdapter;

/**
 * Created by sadekseridj on 14/12/2017.
 */

public class SignNews extends Fragment implements View.OnClickListener{

    ListView listView;
    public Button submit;
    MainActivity context;
    DatabaseHandler dh;
    ArrayList<String> allNews;

    public SignNews()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.signnews, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);

        // aller chercher les news dans la bdd une fois que c'est pret et cocher les cases dont l'user est abonné
        allNews = new ArrayList<>();
        allNews.add("Samsung Phone Updates");
        allNews.add("Apple Phone Updates");
        allNews.add("Cybersecurity News");
        allNews.add("Latest News");
        allNews.add("New Technologies");
        allNews.add("Security Issues");
        allNews.add("Programming Languages");




        listView = (ListView) view.findViewById(R.id.listsignnews);
        final View footer = (View) inflater.inflate(R.layout.listviewfooter, listView, false);
        listView.addFooterView(footer);
        
        SignNewsAdapter signNewsAdapter = new SignNewsAdapter(context, allNews);
        listView.setAdapter(signNewsAdapter);


        submit = (Button) view.findViewById(R.id.submitsignnews);
        submit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.submitsignnews:
                // gérer les checkbox cochées et ajouter a la DBB du user
                context.display_fragment(8);
                break;
            default:
                break;
        }
    }
}
