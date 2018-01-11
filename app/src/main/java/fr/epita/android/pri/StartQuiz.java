package fr.epita.android.pri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sadekseridj on 08/01/2018.
 */

public class StartQuiz extends AppCompatActivity implements View.OnClickListener {

    private Button start;
    private TextView topic;
    String titleTopic = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.startquiz);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);
        topic = (TextView) findViewById(R.id.quiztopic);

        Intent intent = getIntent();
        if (intent != null)
            titleTopic = intent.getStringExtra("TOPIC");
        topic.setText(titleTopic);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.start:
                Intent intent = new Intent(StartQuiz.this, Quizquestionsjson.class);
                intent.putExtra("TOPIC", titleTopic);
                startActivity(intent);
                break;
            default :
                break;
        }

    }
}
