package fr.epita.android.pri.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.Tools.CustomListAdapter;

/**
 * Created by sadekseridj on 07/12/2017.
 */

public class ComputersList extends Fragment {

    public ListView listView;
    TextView emptyListView;
    MainActivity context;
    DatabaseHandler dh;

    public ArrayList<Pair<String, String>> list; // String1 : name, String2 : identifier

    public ComputersList()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.computers_list, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);

        listView = (ListView) view.findViewById(R.id.computerslist);
        emptyListView = (TextView) view.findViewById(R.id.emptylist);

        list = new ArrayList<>();
        list.add(new Pair<String, String>("Ordi1", "id1"));
        list.add(new Pair<String, String>("Ordi", "id2"));
        list.add(new Pair<String, String>("Ordi3", "id3"));
        list.add(new Pair<String, String>("Ordi4", "id4"));
        list.add(new Pair<String, String>("Ordi5", "id5"));

        CustomListAdapter customListAdapter = new CustomListAdapter(context, list);
        listView.setAdapter(customListAdapter);

        return view;

    }

    public void emptyList()
    {
        if (list.isEmpty())
            listView.setEmptyView(emptyListView);
    }
}
