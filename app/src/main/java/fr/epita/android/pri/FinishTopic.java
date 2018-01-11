package fr.epita.android.pri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sadekseridj on 10/01/2018.
 */

public class FinishTopic extends AppCompatActivity implements View.OnClickListener{

    public TextView totalScore;
    public Button retry;
    public Button changeTopic;
    public ImageView exit;
    String titleTopic = "";
    int score;
    DatabaseHandler dh;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.finishtopic);

        Intent intent = getIntent();
        if (intent != null)
        {
            titleTopic = intent.getStringExtra("TOPIC");
            score = intent.getIntExtra("SCORE", 0); // voir la fonction pour récupérer un integer
        }
        dh = new DatabaseHandler(this);
        totalScore = (TextView) findViewById(R.id.totalscore);
        totalScore.setText("SCORE: " + score);
        dh.updateScore(LoginActivity.rl.getLogin(), score);
        LoginActivity.rl.setScore(dh.find_score(LoginActivity.rl.getLogin()));
        retry = (Button) findViewById(R.id.retry);
        changeTopic = (Button) findViewById(R.id.changetopic);
        exit = (ImageView) findViewById(R.id.exit);
        exit.setClickable(true);
        retry.setOnClickListener(this);
        changeTopic.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.retry:
                Intent intent_retry = new Intent(FinishTopic.this, StartQuiz.class);
                intent_retry.putExtra("TOPIC", titleTopic);
                startActivity(intent_retry);
                break;
            case R.id.changetopic:
                Intent intent_change = new Intent(FinishTopic.this, ChooseTopic.class);
                startActivity(intent_change);
                break;
            case R.id.exit:
                // revenir a la derniere page avant le game
                break;
            default:
                break;
        }
    }
}
