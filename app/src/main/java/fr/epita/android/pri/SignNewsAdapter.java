package fr.epita.android.pri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sadekseridj on 14/12/2017.
 */

public class SignNewsAdapter extends BaseAdapter {

    private ArrayList<String> allNews;
    MainActivity context;
    View rowView;

    public SignNewsAdapter(MainActivity context, ArrayList<String> allNews)
    {
        this.context = context;
        this.allNews = allNews;
    }

    @Override
    public int getCount() { return allNews.size(); }

    @Override
    public Object getItem(int i) { return allNews.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String item = (String) getItem(i);
        final ViewHolder viewHolder;

        if (view == null)
        {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.signnewsitem, viewGroup, false);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        else
        {
            rowView = view;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(item);

        return rowView;
    }

    class ViewHolder
    {
        //final CheckBox checkBox;
        final TextView textView;

        public ViewHolder(View rowView)
        {
            //checkBox = (CheckBox) rowView.findViewById(R.id.cheboxitem);
            textView = (TextView) rowView.findViewById(R.id.textviewcheckbox);
        }
    }
}
