package fr.epita.android.pri;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Rohit on 12/13/2017.
 */

public class Quizquestionsjson extends AppCompatActivity implements View.OnClickListener{

    //public TextView question_category;
    public TextView questionsMiss;
    public TextView quiz_question;
    public ProgressBar progressBar;
    public TextView textXp;
    public Button option1;
    public Button option2;
    public Button option3;
    public Button option4;


    TextView time;
    MyCountDownTimer myCountDownTimer;
    //ArrayList<Quizstructure> quizstructure;
    //Quizstructure currentQ;
    //boolean nextFlag =false;
    //public static int score,correct,wrong,wronganswers;
    public boolean isCorrect;
    int qid=0;
    String goodAnswer;
    int scoreQuestion = 0;
    int xp = 0;
    int misses = 0;
    int correctIndex;
    String titleTopic = "";
    int totalCount=5;
    //boolean isTimerFinished = false;
    //static LinkedHashMap lhm = new LinkedHashMap();

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
            time.setText((l / 1000) + "");
            progressBar.setProgress((int) (l / 1000));
            Log.e("Second Gone", "Another Second Gone");
            Log.e("Time Remaining", "seconds remaining: " + l
                    / 1000);
        }

        public void onFinish() {
            Log.e("Times up", "Times up");
            countDownTimer.cancel();
            misses++;
            questionsMiss.setText(Integer.toString(misses));
            selectGoodAnswer();
            qid++;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setQuestion(qid);
                }
            }, 2000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiztopic);
        // Assigning all the TextViews to the TextViews on the Layout page
        time = findViewById(R.id.quiztimerbar);
        questionsMiss = findViewById(R.id.currentquizmiss);
        //question_category= view.findViewById(R.id.quiztopic);
        quiz_question = findViewById(R.id.quizquestions);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setProgress(10);
        textXp = (TextView) findViewById(R.id.currentquizpoints);
        textXp.setText(String.valueOf(xp));
        option1 = findViewById(R.id.option1);
        option1.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        option1.setOnClickListener(this);
        option2 = findViewById(R.id.option2);
        option2.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        option2.setOnClickListener(this);
        option3 = findViewById(R.id.option3);
        option3.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        option3.setOnClickListener(this);
        option4 = findViewById(R.id.option4);
        option4.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        option4.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null)
            titleTopic = intent.getStringExtra("TOPIC");

        //quizstructure = new ArrayList<Quizstructure>();
        // currentQ = quesList.get(qid);
        myCountDownTimer = new MyCountDownTimer(10000, 1000);
        setQuestion(qid);
    }

    public void setQuestion(int i)
    {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray jArray = obj.getJSONArray("questions");
            //for (int i = 0; i < jArray.length(); i++) {
            if (i >= jArray.length())
            {
                Intent intent = new Intent(Quizquestionsjson.this, FinishTopic.class);
                intent.putExtra("TOPIC", titleTopic);
                intent.putExtra("SCORE", xp);
                startActivity(intent);
            }
            JSONObject jo_inside = jArray.getJSONObject(i);
            String categoryname = jo_inside.getString("categoryname");
            while (! categoryname.equalsIgnoreCase(titleTopic))
            {
                i++;
                qid++;
                if (i >= jArray.length())
                {
                    Intent intent = new Intent(Quizquestionsjson.this, FinishTopic.class);
                    intent.putExtra("TOPIC", titleTopic);
                    intent.putExtra("SCORE", xp);
                    startActivity(intent);
                }
                jo_inside = jArray.getJSONObject(i);
                categoryname = jo_inside.getString("categoryname");
            }
            JSONArray janswers = jo_inside.getJSONArray("answers");

            String question = jo_inside.getString("question");
            // String answer = jo_inside.getString("answers");

            correctIndex = Integer.valueOf(jo_inside.getString("correctIndex"));
            goodAnswer = janswers.get(correctIndex - 1).toString();
            String category_Id = jo_inside.getString("categoryId");
            scoreQuestion = Integer.valueOf(jo_inside.getString("score"));


            //Setting all the TextViews values from the JSON file
            //question_category.setText(categoryname);
            quiz_question.setText(question);
            //currentquizpoints.setText(score);
            option1.setText(janswers.get(0).toString());
            option1.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            option2.setText(janswers.get(1).toString());
            option2.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            option3.setText(janswers.get(2).toString());
            option3.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            option4.setText(janswers.get(3).toString());
            option4.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

            progressBar.setProgress(10);
            countDownTimer.start();
            //   }
        } catch (JSONException e) {
            e.printStackTrace();
    }

}

   /* private void setQuestionView(int qid) {

        quiz_question.setText(quizstructure.get(qid).getQuestion());
        option1.setText(quizstructure.get(qid).getAnswers()[0]);
        option2.setText(quizstructure.get(qid).getAnswers()[1]);
        option3.setText(quizstructure.get(qid).getAnswers()[2]);
        option4.setText(quizstructure.get(qid).getAnswers()[3]);

    }*/


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

    public void selectGoodAnswer()
    {
        switch (correctIndex - 1) {
            case 0:
                option1.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                break;
            case 1:
                option2.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                break;
            case 2:
                option3.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                break;
            case 3:
                option4.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                break;
            default:
                break;
        }
    }
    public void setScore(Button option)
    {
        countDownTimer.cancel();
        selectGoodAnswer();
        if (option.getText().toString().equals(goodAnswer)) {
            xp += scoreQuestion;
            textXp.setText(String.valueOf(xp));
        }
        if (!option.getText().toString().equals(goodAnswer)) {
            misses += 1;
            questionsMiss.setText(Integer.toString(misses));
            option.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            //selectGoodAnswer();
        }
        if (qid == 5)
        {
            Intent intent = new Intent(Quizquestionsjson.this, FinishTopic.class);
            intent.putExtra("TOPIC", titleTopic);
            intent.putExtra("SCORE", xp);
            startActivity(intent);
        }

        qid++;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("HEEEEERE");
                setQuestion(qid);
            }
        }, 2000);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.option1:
                setScore(option1);
                break;
            case R.id.option2:
                setScore(option2);
                break;
            case R.id.option3:
                setScore(option3);
                break;
            case R.id.option4:
                setScore(option4);
                break;
            default:
                break;
        }

    }

  /*  boolean getNextQuestion(boolean timeIsUp){
        nextFlag = true;

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

        return true;

    }*/
}
