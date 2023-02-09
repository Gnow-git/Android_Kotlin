package com.example.qrcodereader

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.qrcodereader.databinding.ActivityMainBinding
import com.google.common.util.concurrent.ListenableFuture

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding // 바인딩 변수 선언
    private lateinit var cameraProviderFuture:
            ListenableFuture<ProcessCameraProvider>
    // ListenableFuture 에 태스트가 제대로 끝났을 때 동작 지정
    
    private val PERMISSIONS_REQUEST_CODE = 1    // 태그 기능 코드, 0과 같거나 큰 양수면 상관 없음
    private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)  // 카메라 권한 지정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 바인딩 설정
        binding = ActivityMainBinding.inflate(layoutInflater)
        // binding.root → 바인딩 클래스라면 항상 자동으로 생성되는 루트 뷰를 반환
        val view = binding.root
        setContentView(view)

        if (!hasPermissions(this)) {
            // 카메라 권한을 요청
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
        } else {
            // 만약 이미 권한이 있다면 카메라를 시작
            startCamera()   // 카메라 시작(onCreate() 안에 넣는다)
        }
    }

    // 권한 유무 확인
    fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(context, it) ==
                PackageManager.PERMISSION_GRANTED
    }

    // 권한 요청 콜백 함수
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // onCreate 의 requestPermissions  인수와 맞는지 확인
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) {
                Toast.makeText(this@MainActivity, "권한 요청이 승인되었습니다.",
                Toast.LENGTH_LONG).show()
                startCamera()
            } else {
                Toast.makeText(this@MainActivity, "권한 요청이 거부되었습니다.",
                Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    // 미리보기와 이미지 분석 시작
    fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)  // 객체의 참조값 할당
        cameraProviderFuture.addListener(Runnable { // 태스크가 끝나면 실행
            // 카메라 생명 주기를 액티비티나 프래그먼트와 같은 생명 주기에 바인드해줌
            val cameraProvider = cameraProviderFuture.get()

            val preview = getPreview()  // 미리보기 객체 가져오기
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            // 후면 카메라 선택(DEFAULT_BACK_CAMERA)

            cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            // 미리보기 기능 선택(preview)

        }, ContextCompat.getMainExecutor(this))
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