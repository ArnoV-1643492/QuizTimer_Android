package com.example.quiztimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {
    private TextView m_time;
    private Button m_plus20Btn;
    private Button m_minus20Btn;
    private Button m_playPauseBtn;
    private boolean m_running = false;
    private CountDownTimer m_countTimer;
    private long m_timeInMili = 60000;
    private int m_countIntervalInMili = 1000; // 1 second

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find views
        m_time = getView().findViewById(R.id.timeText);
        m_plus20Btn = getView().findViewById(R.id.plus20Btn);
        m_minus20Btn = getView().findViewById(R.id.minus20Btn);
        m_playPauseBtn = getView().findViewById(R.id.playPauseBtn);

        showTimeInView();


        m_playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPause();
            }
        });

        m_plus20Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTimerValue(20000);
            }
        });

        m_minus20Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTimerValue(-20000);
            }
        });
    }

    private void playPause(){
        if (m_timeInMili != 0) {
            if (!m_running) {
                startTimer();
            } else {
                stopTimer();
            }
        }
    }

    private void startTimer(){
        m_running = true;
        // Change startStop button text
        m_playPauseBtn.setText("Stop");
        m_countTimer = new CountDownTimer(m_timeInMili, m_countIntervalInMili) {
            @Override
            public void onTick(long millisUntilFinished) {
                m_timeInMili = millisUntilFinished;
                showTimeInView();
            }

            @Override
            public void onFinish() {
                stopTimer();
            }
        }.start();
        showTimeInView();
    }

    private void stopTimer(){
        // Change startStop button text
        m_playPauseBtn.setText("Start");
        m_running = false;
        m_countTimer.cancel();
    }

    private void showTimeInView(){
        int timeInSec = (int) m_timeInMili / 1000;
        String text = Integer.toString(timeInSec);
        m_time.setText(text);
    }

    private void changeTimerValue(long val){
        if (m_running) {
            m_countTimer.cancel();
            m_timeInMili += val;
            if (m_timeInMili <= 0) {
                // Time is up, end timer
                m_timeInMili = 0;
                showTimeInView();
                stopTimer();
            } else {
                // Time is not up, continue counting
                showTimeInView();
                startTimer();
            }
        }
        else {
            m_timeInMili += val;
            showTimeInView();
        }
    }
}