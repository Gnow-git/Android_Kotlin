package com.example.chatgpt_stopwatch

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var stopwatchTextView: TextView
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button

    private var timeInMillis: Long = 0L
    private var isStopwatchRunning = false

    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopwatchTextView = findViewById(R.id.stopwatchTextView)
        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)
        resetButton = findViewById(R.id.resetButton)

        startButton.setOnClickListener {
            startStopwatch()
        }

        pauseButton.setOnClickListener {
            pauseStopwatch()
        }

        resetButton.setOnClickListener {
            resetStopwatch()
        }
    }

    private fun startStopwatch() {
        timer = object : CountDownTimer(Long.MAX_VALUE, 10L) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMillis += 10L
                val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) -
                        TimeUnit.MINUTES.toSeconds(minutes)
                val milliseconds = timeInMillis - TimeUnit.SECONDS.toMillis(seconds) -
                        TimeUnit.MINUTES.toMillis(minutes * 60)
                stopwatchTextView.text = "${String.format("%02d", minutes)}:" +
                        "${String.format("%02d", seconds)}:" +
                        "${String.format("%03d", milliseconds)}"
            }

            override fun onFinish() {}
        }
        timer.start()
        isStopwatchRunning = true
    }

    private fun pauseStopwatch() {
        timer.cancel()
        isStopwatchRunning = false
    }

    private fun resetStopwatch() {
        timeInMillis = 0L
        stopwatchTextView.text = "00:00:000"
        isStopwatchRunning = false
    }
}
