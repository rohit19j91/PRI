package fr.epita.android.pri;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit on 12/14/2017.
 */

public class Quizadapter extends ArrayAdapter<Quizstructure> {

ArrayList<Quizstructure> arraylistquestion;
int Resource;
Context context;

    public Quizadapter(Context context, int resource, ArrayList<Quizstructure> objects) {
        super(context, resource, objects);
        arraylistquestion =objects;
        Resource=resource;
        this.context=context;
    }



    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        ViewHolder holder =new ViewHolder();
        holder.quizquestions=(TextView)convertView.findViewById(R.id.quizquestions);
        holder.quiztopic=(TextView)convertView.findViewById(R.id.quiztopic);
      //  holder.currentquizpoints=(TextView)convertView.findViewById(R.id.quizpointlabel);
        //holder.option1=(TextView)convertView.findViewById(R.id.option1);
        holder.quiztopic.setText(arraylistquestion.get(position).getCategory_name());
        holder.quizquestions.setText(arraylistquestion.get(position).getQuestion());
        holder.currentquizpoints.setText(arraylistquestion.get(position).getScore());
        return convertView;
    }

    static class ViewHolder{

        public TextView quiztopic;
        public TextView currentquizpoints;
        public TextView quizquestions;
        public Button option1;
        public Button option2;
        public Button option3;
        public Button option4;
    }

}
