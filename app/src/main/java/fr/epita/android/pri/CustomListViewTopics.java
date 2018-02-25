package fr.epita.android.pri;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by sadekseridj on 10/01/2018.
 */

public class CustomListViewTopics extends BaseAdapter {

    private ArrayList<String> listTopics;
    View rowView;
    private ChooseTopic context;

    public CustomListViewTopics(ChooseTopic context, ArrayList<String> topics)
    {
        this.listTopics = topics;
        this.context = context;
    }
    @Override
    public int getCount() { return listTopics.size(); }

    @Override
    public Object getItem(int i) { return listTopics.iterator().next(); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final String item = (String) this.getItem(i);
        final ViewHolder viewHolder;

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.itemtopic, viewGroup, false);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);

        }
        else
        {
            rowView = view;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.buttonTopic.setText(item);
        viewHolder.buttonTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StartQuiz.class);
                intent.putExtra("TOPIC", item);
                context.startActivity(intent);
            }
        });

        return rowView;
    }

    class ViewHolder {
        final Button buttonTopic;

        public ViewHolder(final View rowView) {
            buttonTopic = (Button) rowView.findViewById(R.id.buttontopic);
        }
    }
}