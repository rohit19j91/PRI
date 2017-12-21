package fr.epita.android.pri;

/**
 * Created by Rohit on 12/14/2017.
 */

public class Quizstructure {

private String question;
private String answers[];
private int correct_index;
private int category_id;
private String category_name;
private int score;

    public Quizstructure() {
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int getCorrect_index() {
        return correct_index;
    }

    public void setCorrect_index(int correct_index) {
        this.correct_index = correct_index;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}


