package com.example.simplemusicplayer

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MusicPlayerService  : Service() {

    override fun onCreate() {   // 서비스가 생성될 때 딱 한 번만 실행
        super.onCreate()
        startForegroundService()    // 포그라운드 서비스 : 상태 표시줄에 알림을 생성
    }
    
    // 구성요소에서 bindService() 함수를 호출할 때 실행, 서비스와 구성요소를 이어주는 매개체 IBinder 반환
    // 시작되고 바인드된 서비스이므로 반드시 구현, 바인드가 필요 없는 서비스는 null 반환
    override fun onBind(intent: Intent?): IBinder? {
        TODO("NOT yet implemented")
    }

    // startService()나 startForegroundService() 호출할 때 실행, 시작된 상태 및 백그라운드에 존재
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    // 서비스 생명 주기의 마지막 단계, onCreate()에서 상태표시줄에 보여주었던 알림 해제
    override fun onDestroy() {
        super.onDestroy()
    }

    fun startForegroundService() {} // 알림 채널 생성, API 26부터는 서비스 실행됨을 알림
    fun isPlaying() {}  // 재생 중인지 확인
    fun play() {}       // 재생
    fun pause() {}      // 일시정지
    fun stop() {}       // 완전 정지
}