package fr.epita.android.pri;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Rohit on 12/13/2017.
 */

public class Quizquestionsjson extends Fragment {

    public TextView question_category;
    public TextView currentquizpoints;
    public TextView questionsatt;
    public TextView quiz_question;
    public Button option1;
    public Button option2;
    public Button option3;
    public Button option4;

    MainActivity context;

    TextView progressBar;
    MyCountDownTimer myCountDownTimer;
    ArrayList<Quizstructure> quizstructure;
    Quizstructure currentQ;
    boolean nextFlag =false;
    public static int score,correct,wrong,wronganswers;
    public boolean isCorrect;
    static int qid=0;
    int totalCount=5;
    boolean isTimerFinished = false;
    static LinkedHashMap lhm = new LinkedHashMap();

    public Quizquestionsjson()
    {

    }

    //Setting the timer of 20 secs per question
    MyCountDownTimer countDownTimer = new MyCountDownTimer(10000 /* 20 Sec */,
            1000);

// Creating the Countdown timer class for getting the timer for each question
    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long l) {
            progressBar.setText((l / 1000) + "");
            Log.e("Second Gone", "Another Second Gone");
            Log.e("Time Remaining", "seconds remaining: " + l
                    / 1000);
        }

        public void onFinish() {
            Log.e("Times up", "Times up");
            countDownTimer.cancel();
        //    if (getNextQuestion(false)) {
                // Start The timer again
                countDownTimer.start();
            }
        }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.quiztopic, container, false);
        context = (MainActivity) getActivity();
        // Assigning all the TextViews to the TextViews on the Layout page
        progressBar= view.findViewById(R.id.quiztimerbar);
        questionsatt= view.findViewById(R.id.questionsattempted);
        question_category= view.findViewById(R.id.quiztopic);
        quiz_question= view.findViewById(R.id.quizquestions);
        currentquizpoints= view.findViewById(R.id.currentquizpoints);
        option1= view.findViewById(R.id.option1);
        option2= view.findViewById(R.id.option2);
        option3= view.findViewById(R.id.option3);
        option4= view.findViewById(R.id.option4);


        setQuestionView();
        countDownTimer.start();
        quizstructure =new ArrayList<Quizstructure>();
        myCountDownTimer = new MyCountDownTimer(10000, 1000);
        myCountDownTimer.start();
       // currentQ = quesList.get(qid);
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray jArray = obj.getJSONArray("questions");
            qid++;
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jo_inside = jArray.getJSONObject(i);
                JSONArray janswers=jo_inside.getJSONArray("answers");

                questionsatt.setText(Integer.toString(qid));

                String question = jo_inside.getString("question");
               // String answer = jo_inside.getString("answers");
                String correct_index=jo_inside.getString("correctIndex");
                String category_Id=jo_inside.getString("categoryId");
                String score=jo_inside.getString("score");
                String categoryname=jo_inside.getString("categoryname");


                //Setting all the TextViews values from the JSON file
                question_category.setText(categoryname);
                quiz_question.setText(question);
                currentquizpoints.setText(score);
                option1.setText(janswers.get(0).toString());
                option2.setText(janswers.get(1).toString());
                option3.setText(janswers.get(2).toString());
                option4.setText(janswers.get(3).toString());
            }


        }  catch (JSONException e) {
            e.printStackTrace();
        }

    return view;

    }

    private void setQuestionView() {
  /*      quiz_question.setText(currentQ.getQUESTION());
        option1.setText(currentQ.getOPTA());
        option2.setText(currentQ.getOPTB());
        option3.setText(currentQ.getOPTC());
        option4.setText(currentQ.getOPTC());
*/
    }


    private String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = context.getApplication().getAssets().open("questions.json");
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
    boolean getNextQuestion(boolean timeIsUp){
        nextFlag = true;
/*
        if(option1.isPressed()||option2.isPressed()||option3.isPressed()||option4.isPressed()){
            qid++;

            //wronganswers=
            if(!timeIsUp && currentQ.getANSWER().equals(answer.getText())){
                correct++;
            }else{
                lhm.put(currentQ.getQUESTION(),currentQ.getANSWER());
                wrong++;
            }

            if(qid<5){
                currentQ=quizstructure.get(qid);
                setQuestionView();

            }else{

                score=correct;
                Intent intent = new Intent(Quizquestionsjson.this, Quiztopic.class);
                Bundle b = new Bundle();
                b.putInt("score", score); //Your score
                intent.putExtras(b); //Put your score to your next Intent
                startActivity(intent);
                return false;
            }
        }else{
            Toast.makeText(getApplicationContext(),
                    "Please select atleast one Option",Toast.LENGTH_SHORT).show();
        }
*/
        return true;

    }
}
