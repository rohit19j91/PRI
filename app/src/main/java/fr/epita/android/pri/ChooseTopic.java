package fr.epita.android.pri;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sadekseridj on 10/01/2018.
 */

public class ChooseTopic extends AppCompatActivity
{

    public ListView listViewTopics;
    public TextView emptyListTopics;

    public ArrayList<String> listTopics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choosetopic);

        listViewTopics = (ListView) findViewById(R.id.listtopics);
        emptyListTopics = (TextView) findViewById(R.id.emptylisttopics);
        //List<String> al = new ArrayList<>();
// add elements to al, including duplicates
       // Set<String> hs = new HashSet<>();
        /*Issues is here!*/ // hs.addAll(fillListTopics());
/*Issues is here!*/  //      for(String s: hs){System.out.println(s);}
/*Issues is here!*/    //    listTopics.clear();
       // listTopics.addAll(hs);
        listTopics = fillListTopics();

        CustomListViewTopics customListViewTopics = new CustomListViewTopics(this, listTopics);
        listViewTopics.setAdapter(customListViewTopics);

        //listViewTopics.setClickable(true);

    }

    private String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getApplication().getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<String> fillListTopics()
    {
        ArrayList<String> list = new ArrayList<>();
     //   HashSet<String> list=new HashSet<String>();

        try {
            int present=0;
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray jArray = obj.getJSONArray("questions");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jo_inside = jArray.getJSONObject(i);
                String categoryname = jo_inside.getString("categoryname");
            list.add(categoryname);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void emptyList() {
        if (listTopics.isEmpty())
            listViewTopics.setEmptyView(emptyListTopics);
    }
}