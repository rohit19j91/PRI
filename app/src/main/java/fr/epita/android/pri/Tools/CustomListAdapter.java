package fr.epita.android.pri.Tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.epita.android.pri.MainActivity;
import fr.epita.android.pri.R;

/**
 * Created by sadekseridj on 07/12/2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private ArrayList<Pair<String, String>> computers;
    private MainActivity context;
    View rowView;

    public CustomListAdapter(MainActivity context, ArrayList<Pair<String, String>> comp)
    {
        this.computers = comp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return computers.size();
    }

    @Override
    public Object getItem(int i) {
        return computers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Pair<String, String> item = (Pair<String, String>) this.getItem(i);
        final ViewHolder viewHolder;

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.listitem, viewGroup, false);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);

        }
        else
        {
            rowView = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.pseudo.setText(item.first);
        viewHolder.id.setText(item.second);

        viewHolder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.create();
                //final AlertDialog dialog = builder.create();
                builder.setTitle("Would you like to delete this computer ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // enlever l'ordi dans la BDD
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                builder.show();
            }
        });

        return rowView;
    }

    class ViewHolder {
        final ImageView avatar;
        final TextView pseudo;
        final TextView id;
        final ImageView buttonRemove;

        public ViewHolder(View rowView) {
            avatar = (ImageView) rowView.findViewById(R.id.avatar_computer);
            pseudo = (TextView) rowView.findViewById(R.id.pseudo_computer);
            id = (TextView) rowView.findViewById(R.id.id_computer);
            buttonRemove = (ImageView) rowView.findViewById(R.id.button_remove);
        }
    }
}

