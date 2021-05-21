package com.pankti.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pankti.myapplication.Adapter.TimerAdapter;
import com.pankti.myapplication.Model.CountDownTimer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcView;
    private ArrayList<CountDownTimer> listItem;
    private TimerAdapter timerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcView = findViewById(R.id.rcItemView);

        listItem = new ArrayList<>();
        for(int i = 0 ; i<30 ; i++)
        {
            listItem.add(new CountDownTimer());
        }

        rcView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        timerAdapter = new TimerAdapter(listItem);
        rcView.setAdapter(timerAdapter);


    }
}