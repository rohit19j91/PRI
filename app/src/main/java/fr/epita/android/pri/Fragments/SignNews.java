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

        allNews = new ArrayList<>();
        allNews.add("News 1");
        allNews.add("News 2");
        allNews.add("News 3");
        allNews.add("News 4");
        allNews.add("News 5");
        allNews.add("News 6");
        allNews.add("News 7");
        allNews.add("News 8");
        allNews.add("News 9");
        allNews.add("News 10");
        allNews.add("News 11");
        allNews.add("News 12");
        allNews.add("News 12");
        allNews.add("News 13");
        allNews.add("News 14");
        allNews.add("News 15");
        allNews.add("News 16");
        allNews.add("News 17");
        allNews.add("News 18");
        allNews.add("News 19");
        allNews.add("News 20");




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
                break;
            default:
                break;
        }
    }
}
