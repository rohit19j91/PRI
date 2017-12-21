package fr.epita.android.pri;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

/**
 * Created by Rohit on 11/29/2017.
 */

public class Quiztopic extends Activity {

    private static String TAG = "Quiztopic";

    private float[] yData={20f,20f,20f,20f,20f};
    private String[] xData={"Rohit","Hermine","Avantika","Gaurav","Akshita"};
    PieChart pieChart;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minigames);
        Log.d(TAG,"onCreate: Of the Quiztopic class");

        pieChart= (PieChart) findViewById(R.id.piechartquiz);

        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(20f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setHoleColor(Color.BLUE);
        pieChart.setCenterText("Quiztopic");
        pieChart.setCenterTextSize(17);
        addDataSet();





    }

    private void addDataSet() {
        Log.d(TAG,"Entered DataSet");
        ArrayList<PieEntry> yEntrys=new ArrayList<>();
        ArrayList<String> xEntrys=new ArrayList<>();


        yEntrys.add(new PieEntry(yData[0],"Cybersecurity"));
        yEntrys.add(new PieEntry(yData[1],"Basic Networking"));
        yEntrys.add(new PieEntry(yData[2],"XYZ"));
        yEntrys.add(new PieEntry(yData[3],"OPS"));
        yEntrys.add(new PieEntry(yData[4],"GGL"));

        //for(int y=0;y<xData.length;y++){
          //  xEntrys.add(xData[y]);}

        //Create Data set
        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Quiztopic");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);


        //Adding Colors
        ArrayList<Integer> colors=new ArrayList<>();
        //Green
        colors.add(Color.rgb(40,197,4));
        //Yellow
        colors.add(Color.rgb(249,251,26));
        //Orange
        colors.add(Color.rgb(255,137,57));
        //Purple
        colors.add(Color.rgb(158,103,229));
        //Red
        colors.add(Color.rgb(249,61,40));

        pieDataSet.setColors(colors);

        //Adding LEGEND
        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        //legend.setPosition(LegendPosition.ABOVE_CHART_CENTER);

        //Create pie data set
        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }
}
