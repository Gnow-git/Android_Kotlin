package com.example.emptyactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.camera.core.Preview
import com.example.emptyactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding  // 바인딩 변수 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 바인딩 설정
        binding = ActivityMainBinding.inflate(layoutInflater)
        // binding.root → 바인딩 클래스라면 항상 자동으로 생성되는 루트 뷰를 반환
        val view = binding.root
        setContentView(view)

    }

    // 미리보기와 이미지 분석 시작
    fun startCamera() {

    }

    // 미리보기 객체 반환
    fun getPreview() : Preview {

        val preview : Preview = Preview.Builder().build()   // preview 객체 생성
        // Preview 객체에 SurfaceProvider 설정 → Preview 에 Surface 제공
        // Surface → 화면에 보여지는 픽셀들이 모여 있는 객체
        preview.setSurfaceProvider(binding.barcodePreview.getSurfaceProvider())
        
        return preview
    }
}