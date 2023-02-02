package com.example.simplemusicplayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder

class MusicPlayerService  : Service() {

    var mMediaPlayer: MediaPlayer? = null // 미디어 플레이어 객체 초기화

    var mBinder: MusicPlayerBinder = MusicPlayerBinder()    // mBinder 객체 생성
    // 내부 클래스 MusicPlayerBinder 생성, Binder 상속
    inner class MusicPlayerBinder : Binder() {
        fun getService(): MusicPlayerService {  // getService 함수를 가지고 있음
            return this@MusicPlayerService  // 현재 서비스 반환 → 연결된 구성요소가 서비스의 함수 사용 가능
        }
    }

    override fun onCreate() {   // 서비스가 생성될 때 딱 한 번만 실행
        super.onCreate()
        startForegroundService()    // 포그라운드 서비스 : 상태 표시줄에 알림을 생성
    }
    
    // 구성요소에서 bindService() 함수를 호출할 때 실행, 서비스와 구성요소를 이어주는 매개체 IBinder 반환
    // 시작되고 바인드된 서비스이므로 반드시 구현, 바인드가 필요 없는 서비스는 null 반환
    override fun onBind(intent: Intent?): IBinder? {
        return mBinder  // 위에서 생성한 mBinder 객체를 반환값으로 전달
    }

    // startService()나 startForegroundService() 호출할 때 실행, 시작된 상태 및 백그라운드에 존재
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 반드시 정숫값 반환, 시스템이 서비스를 중단하면 서비스 다시 실행, onStartCommand() 함수 호출
        return START_STICKY
    }

    // 알림 채널 생성, API 26부터는 서비스 실행됨을 알림
    fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationManager = getSystemService(NOTIFICATION_SERVICE)
                    as NotificationManager
            val mChannel = NotificationChannel( // 알림 채널 생성
                    "CHANNEL_ID",
                    "CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(mChannel)
        }

        // 알림 생성
        val notification: Notification = Notification.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_play)
            .setContentTitle("뮤직 플레이어 앱")
            .setContentText("앱이 실행 중입니다.")
            .build()
        // 서비스를 포그라운드 서비스로 만듬, startForeground(알림의 식별자, 보여줄 알림)
        startForeground(1, notification)
    }

    // 서비스 생명 주기의 마지막 단계, onCreate()에서 상태표시줄에 보여주었던 알림 해제
    // 서비스 중단 처리
    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);   // 포그라운드 서비스를 멈춤
        }
    }

    // 재생 중인지 확인
    fun isPlaying() : Boolean{
        return (mMediaPlayer != null && mMediaPlayer?.isPlaying ?: false)
    }

    fun play() {}       // 재생
    fun pause() {}      // 일시정지
    fun stop() {}       // 완전 정지
}