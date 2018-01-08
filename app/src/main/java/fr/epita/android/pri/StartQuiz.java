package fr.epita.android.pri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by sadekseridj on 08/01/2018.
 */

public class StartQuiz extends AppCompatActivity implements View.OnClickListener {

    private Button start;
    private DatabaseHandler dh = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.startquiz);
        dh = new DatabaseHandler(this);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.start:
                Intent intent = new Intent(StartQuiz.this, Quizquestionsjson.class);
                startActivity(intent);
                break;
            default :
                break;
        }

    }
}
