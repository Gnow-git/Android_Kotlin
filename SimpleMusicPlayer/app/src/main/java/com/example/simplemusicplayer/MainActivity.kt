package com.example.simplemusicplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button

// View.OnClickListener : 사용자가 뷰를 클릭했을 때 어떤 행동을 할지 정할 수 있다.
class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btn_play : Button
    lateinit var btn_pause : Button
    lateinit var btn_stop : Button
    var mService: MusicPlayerService? = null    // 뮤직 플레이어 서비스를 담을 수 있는 변수 생성
    
    // 서비스와 구성요소 연결 상태 모니터링, bindService() 함수를 호출할 때 두 번째 인수
    val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = (service as MusicPlayerService.MusicPlayerBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mService = null     // 만약 서비스가 끊기면, mService 를 null 로 만들어준다.
        }
    }
    
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

    override fun onResume() {   // 액티비티가 사용자에게 보일 때마다 실행
        super.onResume()

        // 서비스 실행
        if (mService == null) { // 서비스가 액비티비와 연결이 안되었을 경우 버전에 따라 호출
            // 안드로이드 O 이상이면 startForegroundService 를 사용해야 한다.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this,MusicPlayerService::class.java))
            } else {
                startService(Intent(applicationContext, MusicPlayerService::class.java))
            }

            // 액티비티를 서비스와 바인드시킨다.
            val intent = Intent(this, MusicPlayerService::class.java)
            // bindService(바인드할 서비스 안내, 모니터링 함수, 바인드할 시점에 서비스가 실행안된 상태면 실행)
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onPause() {
        super.onPause()
        // 사용자가 액티비티를 떠났을 때 처리
        if (mService != null){
            if (!mService!!.isPlaying()) {  // mService가 재생되고 있지 않다면
                mService!!.stopSelf()   // 서비스를 중단
            }
            unbindService(mServiceConnection)   // 서비스로부터 연결을 끊는다.
            mService = null
        }
    }

    private fun play() {
        mService?.play()
    }

    private fun pause() {
        mService?.pause()
    }

    private fun stop() {
        mService?.stop()
    }
}
