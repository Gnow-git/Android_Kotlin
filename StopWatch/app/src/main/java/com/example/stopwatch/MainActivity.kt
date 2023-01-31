package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timer

// 클릭 이벤트 처리 인터페이스
class MainActivity : AppCompatActivity(), View.OnClickListener {

    var isRunning = false       // 실행 여부 확인용 변수 false로 초기화
    var timer : Timer? = null   // timer 변수 추가
    var time = 0                // time 변수 추가

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

        btn_start.text = "일시정지"
        btn_start.setBackgroundColor(getColor(R.color.red))
        isRunning = true

        // 스톱워치를 시작하는 로직
        timer = timer(period = 10) {

            time++  // 10밀리초 단위 타이머

            // 시간 계산
            val milli_second = time % 100
            val second = (time % 6000) / 100
            val minute = time / 6000

            runOnUiThread {     // UI 스레드 생성
                if (isRunning){ // UI 업데이트 조건 설정
                    // 밀리초
                    tv_millisecond.text = if (milli_second < 10)
                                          ".0${milli_second}" else ".${milli_second}"
                    // 초
                    tv_second.text = if (second < 10) ":0${second}" else ":${second}"
                    // 분
                    tv_minute.text = "${minute}"
                }
            };
        }
    }

    private fun pause() {
        // 텍스트 속성 변경
        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.blue))

        isRunning = false   // 멈춤 상태로 전환
        timer?.cancel()     // 타이머 멈추기
    }

    private fun refresh() {
        timer?.cancel()     // 백그라운드 타이머 멈추기
        
        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.blue))
        isRunning = false   // 멈춤 상태로 변경

        // 타이머 초기화
        time = 0
        tv_millisecond.text = ".00"
        tv_second.text = ":00"
        tv_minute.text = "00"
    }
}