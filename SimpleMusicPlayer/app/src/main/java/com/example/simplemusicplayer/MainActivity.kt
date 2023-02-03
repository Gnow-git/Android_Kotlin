package com.example.simplemusicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

// View.OnClickListener : 사용자가 뷰를 클릭했을 때 어떤 행동을 할지 정할 수 있다.
class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btn_play : Button
    lateinit var btn_pause : Button
    lateinit var btn_stop : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_play = findViewById(R.id.btn_play)
        btn_pause = findViewById(R.id.btn_pause)
        btn_stop = findViewById(R.id.btn_stop)

        // 리스너 등록
        btn_play.setOnClickListener(this)
        btn_pause.setOnClickListener(this)
        btn_stop.setOnClickListener(this)
    }

    // 클릭 했을때 실행되는 콜백 함수
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_play -> {
                play()
            }

            R.id.btn_pause -> {
                pause()
            }

            R.id.btn_stop -> {
                stop()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun play() {
    }

    private fun pause() {
    }

    private fun stop() {
    }
}