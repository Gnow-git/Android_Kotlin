package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

// 클릭 이벤트 처리 인터페이스
class MainActivity : AppCompatActivity(), View.OnClickListener {

    var isRunning = false   // 실행 여부 확인용 변수 false로 초기화


     private lateinit var btn_start: Button
     private lateinit var btn_refresh: Button
     private lateinit var tv_millisecond: TextView
     private lateinit var tv_second: TextView
     private lateinit var tv_minute: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 레이아웃에서 정의한 뷰 가져오기
        btn_start = findViewById(R.id.btn_start)
        btn_refresh = findViewById(R.id.btn_refresh)
        tv_millisecond = findViewById(R.id.tv_millisecond)
        tv_second = findViewById(R.id.tv_second)
        tv_minute   = findViewById(R.id.tv_minute)

        // 버튼별 리스너 등록, 등록을해야 클릭이 가능
        btn_start.setOnClickListener(this)
        btn_refresh.setOnClickListener(this)
    }

    // 클릭 이벤트시 수행할 기능 구현
    // setOnClickListener 인터페이스는 반드시 onClick() 함수를 오버라이드해야 한다.
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_start -> { // 클릭 이벤트 시 뷰id가 R.id.btn_start 이며
                if(isRunning) { // 스톱워치가 동작 중이라면
                    pause()     // 일시정지 메서드를 실행하고
                } else {        // 동작 중이 아니라면
                    start()     // 시작 메서드를 실행한다.
                }
            }
            R.id.btn_refresh -> {   // 뷰id가 R.id.btn_refresh이면
                refresh()       // 초기화 메서드를 실행한다.
            }
        }
    }

    private fun start() {

    }

    private fun pause() {

    }

    private fun refresh() {

    }
}