package com.example.qrmodule

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.qrmodule.zxing.ZXingScannerView

class QRActivity : Activity(),ZXingScannerView.ResultHandler {

    private lateinit var  zXingScannerView: ZXingScannerView


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zXingScannerView = ZXingScannerView(this)
        setContentView(zXingScannerView)

    }

    override fun handleResult(rawResult: com.google.zxing.Result?) {
        Log.v("handleResult", rawResult!!.text)
        Log.v("handleResult", rawResult.barcodeFormat.toString())
        val intent = Intent()
        intent.putExtra("qrcode",rawResult.text)
        setResult(Activity.RESULT_OK,intent)
        this.finish()
    }

    override fun onResume() {
        super.onResume()
        zXingScannerView.setResultHandler(this)
        zXingScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zXingScannerView.stopCamera()
    }
}
