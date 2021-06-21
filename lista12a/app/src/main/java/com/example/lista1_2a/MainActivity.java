package com.example.lista1_2a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int score;
    private int num1;
    private int num2;
    private int eq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = 0;
        roll();
    }

    private void roll()
    {
        TextView pt = (TextView)findViewById(R.id.points);
        pt.setText("Punkty: " + Integer.toString(score));
        Random rand = new Random();
        num1 = rand.nextInt( 19) + 1;
        num2 = rand.nextInt(19) + 1;
        eq = rand.nextInt(3);
        TextView equation = (TextView)findViewById(R.id.equation);
        equation.setText(Integer.toString(num1) + " ? " + Integer.toString(num2) + " = " + Integer.toString(solve(num1, num2, eq)));
    }

    public void plus(View view) {
        if (eq == 0) {
            Toast.makeText(this, "Dobrze!", Toast.LENGTH_SHORT).show();
            score++;
        }
        else
        {
            Toast.makeText(this, "Źle!", Toast.LENGTH_SHORT).show();
            score--;
        }
        roll();
    }

    public void minus(View view) {
        if (eq == 1) {
            Toast.makeText(this, "Dobrze!", Toast.LENGTH_SHORT).show();
            score++;
        }
        else
        {
            Toast.makeText(this, "Źle!", Toast.LENGTH_SHORT).show();
            score--;
        }
        roll();
    }

    public void multi(View view) {
        if (eq == 2) {
            Toast.makeText(this, "Dobrze!", Toast.LENGTH_SHORT).show();
            score++;
        }
        else
        {
            Toast.makeText(this, "Źle!", Toast.LENGTH_SHORT).show();
            score--;
        }
        roll();
    }

    public void restart(View view) {
        score = 0;
        roll();
    }

    public void nwe_eq(View view) {
        roll();
    }

    private int solve(int num1, int num2, int eq) {
        int result;
        if(eq == 0){
            result = num1+num2;
        }
        else if(eq == 1){
            result = num1-num2;
        }
        else {
            result = num1*num2;
        }
        return result;
    }

}