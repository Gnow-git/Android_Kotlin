package com.example.qrcodereader

interface OnDetectListener {
    fun onDetect(msg : String)  // QRCodeAnalyzer 에서 QR 코드가 인식되었을 때 호출할 함수
}