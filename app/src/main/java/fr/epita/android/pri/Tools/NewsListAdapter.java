package fr.epita.android.pri.Tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;


/**
 * Created by sadekseridj on 13/12/2017.
 */

public class NewsListAdapter extends BaseAdapter {

    private ArrayList<RssFeedModel> mRssFeedModel;
    MainActivity context;
    View rowView;

    public NewsListAdapter(MainActivity context, ArrayList<RssFeedModel> rssFeedModels)
    {
        this.context = context;
        mRssFeedModel = rssFeedModels;
    }

    @Override
    public int getCount() { return mRssFeedModel.size(); }

    @Override
    public Object getItem(int i) { return mRssFeedModel.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final RssFeedModel rssFeedModel = (RssFeedModel)getItem(i);
        final FeedModelViewHolder viewHolder;

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.newsitem, viewGroup, false);
            viewHolder = new FeedModelViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        else
        {
            rowView = view;
            viewHolder = (FeedModelViewHolder) view.getTag();
        }

        viewHolder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("URL", rssFeedModel.link);
                context.customViewpagerAdapter.displayPageWeb.setArguments(bundle);
                context.display_fragment(7);
            }
        });
        viewHolder.title.setText(rssFeedModel.title);
        viewHolder.description.setText(rssFeedModel.description);
        viewHolder.link.setText(rssFeedModel.link);
        return rowView;
    }

    class FeedModelViewHolder {

        final TextView title;
        final TextView description;
        final TextView link;

        public FeedModelViewHolder(View rowView) {
            title = (TextView) rowView.findViewById(R.id.titleText);
            description = (TextView) rowView.findViewById(R.id.descriptionText);
            link = (TextView) rowView.findViewById(R.id.linkText);
        }
    }
}
