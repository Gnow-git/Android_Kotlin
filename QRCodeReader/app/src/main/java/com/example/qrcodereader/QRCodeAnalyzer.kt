package com.example.qrcodereader

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class QRCodeAnalyzer(val onDetectListener: OnDetectListener) :
    ImageAnalysis.Analyzer {

    // 바코드 스캐닝 객체 생성
    private val scanner = BarcodeScanning.getClient()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image =
                InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)  // 이미지 분석, 결과 3가지
                .addOnSuccessListener { qrCodes ->
                        // 리스너가 들어갈 자리
                    for (qrCode in qrCodes) {
                        onDetectListener.onDetect(qrCode.rawValue ?: "")
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()    // 실패 시 에러를 로그에 프린트
                }
                .addOnCompleteListener {
                    imageProxy.close()      // 이미지 분석 완료 → 이미지 프록시 닫는 작업 진행
                }
        }
    }
}