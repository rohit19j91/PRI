package fr.epita.android.pri.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import fr.epita.android.pri.DatabaseHandler;
import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;
import fr.epita.android.pri.Tools.NewsListAdapter;
import fr.epita.android.pri.Tools.RssFeedModel;

/**
 * Created by sadekseridj on 13/12/2017.
 */

public class News extends Fragment{

    private DatabaseHandler dh = null;
    MainActivity context;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<RssFeedModel> rssFeedModelList;
    private String mTitle;
    private String mDescription;
    private String mLink;

    public ArrayList<String> listNews;
   // ArrayList<RssFeedModel> items;

    public News()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.news, container, false);
        context = (MainActivity) getActivity();
        dh = new DatabaseHandler(context);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listNews = new ArrayList<>();
        listNews.add("feed.androidauthority.com");

        listView = (ListView) view.findViewById(R.id.listView);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        new FetchFeedTask().execute((Void) null);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask().execute((Void) null);
            }
        });
        return  view;
    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }


    private  class FetchFeedTask extends AsyncTask<Void, Void, Boolean>
    {
        private String urlLink;

        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true);
            urlLink = listNews.get(0);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if (! urlLink.startsWith("http://") && ! urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;
                URL url = new URL(urlLink);
                //InetAddress ipAddr = InetAddress.getByName(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                rssFeedModelList = parseFeed(inputStream);
                return true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            swipeRefreshLayout.setRefreshing(false);

            if (success)
            {
                NewsListAdapter newsListAdapter = new NewsListAdapter(context, rssFeedModelList);
                listView.setAdapter(newsListAdapter);
            }
            else
            {
                Toast.makeText(context, "Website not found !", Toast.LENGTH_SHORT).show();
            }
        }

        public ArrayList<RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException
        {
            String title = null;
            String description = null;
            String link = null;

            boolean isItem = false;
            ArrayList<RssFeedModel> items = new ArrayList<>();

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);
                parser.nextTag();

                while (parser.next() != XmlPullParser.END_DOCUMENT)
                {
                    int eventType = parser.getEventType();
                    String name = parser.getName();

                    if (name == null)
                        continue;

                    if (eventType == XmlPullParser.END_TAG)
                    {
                        if (name.equalsIgnoreCase("item"))
                            isItem = false;
                        continue;
                    }

                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (name.equalsIgnoreCase("item"))
                        {
                            isItem = true;
                            continue;
                        }
                    }

                    String result = "";
                    if(parser.next() == XmlPullParser.TEXT)
                    {
                        result = parser.getText();
                        parser.nextTag();
                    }

                    if (name.equalsIgnoreCase("title"))
                        title = result;
                    else if (name.equalsIgnoreCase("link"))
                        link = result;
                    else if (name.equalsIgnoreCase("description"))
                        description = result;

                    if (title != null && link != null && description != null)
                    {
                        if (isItem)
                        {
                            RssFeedModel item = new RssFeedModel(title, link, description);
                            items.add(item);
                        }
                        else
                        {
                            mTitle = title;
                            mLink = link;
                            mDescription = description;
                        }

                        title = null;
                        link = null;
                        description = null;
                        isItem = false;
                    }
                }
                return items;
            }
            finally {
                inputStream.close();
            }
        }
    }

}



