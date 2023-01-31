package com.example.activityandfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TwoColorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_color)

        settingButtons()
    }

    fun settingButtons() {  // 뷰가 생성되었을 때 실행됨 → 빨강과 파랑 버튼이 클릭 되었을 때 행동 정의
        val redButton = findViewById<Button>(R.id.button_red_fragment)      // 빨간 버튼 초기화
        val blueButton = findViewById<Button>(R.id.button_blue_fragment)    // 파란 버튼 초기화

        redButton.setOnClickListener {
            val fragmentTransaction =   // FragmentTransaction 클래스 객체 생성
                supportFragmentManager.beginTransaction()   // Fragment 추가, 삭제, 교체 등 가능
            fragmentTransaction.replace(R.id.fragmentFrame, RedFragment())  // replace = 교체
            fragmentTransaction.commit()    // Transaction 이후 반드시 commit() 함수 호출
        }
        blueButton.setOnClickListener {
            val fragmentTransaction =   // FragmentTransaction 클래스 객체 생성
                supportFragmentManager.beginTransaction()   // Fragment 추가, 삭제, 교체 등 가능
            fragmentTransaction.replace(R.id.fragmentFrame, BlueFragment())  // replace = 교체
            fragmentTransaction.commit()    // Transaction 이후 반드시 commit() 함수 호출
        }
    }
}