package com.example.app_loadh5;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrmodule.QRActivity;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.File;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    private final int REQUEST_CAMERA = 1;
    private WebView webView;
    private File mTmpFile;
    ScannerInterface scanner;
    IntentFilter intentFilter;
    BroadcastReceiver scanReceiver;
    //*******重要
    private static final String RES_ACTION = "android.intent.action.SCANRESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //网页加载完之后，android调用js方法
//                String msg ="展示内容";
//                webView.loadUrl("javascript:callbackFromQrcode('"+msg+"')");
                super.onPageFinished(view, url);

            }
        });
        //允许webview对文件的操作
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        //设置编码方式
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);  //开启js
        webSettings.setAppCacheEnabled(true);
        webView.addJavascriptInterface(new AndroidForJs(this),
                "ObjForJs");
//        webView.loadUrl("file:///android_asset/index.html");
        webView.loadUrl("https://hxsb.by1983.cn/index.html");
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.CAMERA,Manifest.permission.INTERNET).
                subscribe(granted -> {
                    if (!granted) { // Always true pre-M
                        // I can control the camera now
                        // Oups permission denied
                        Toast.makeText(this, "请打开相机权限", Toast.LENGTH_SHORT).show();
                        this.finish();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册接收广播，并设置输出模式为广播模式
        initScanner();
        // 注册IMEI号广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction("extra.mdm.respone");
        this.registerReceiver(mBroadcastReceiver, filter);

        getDeviceImei();
    }

    private void initScanner() {
        scanner = new ScannerInterface(this);

        //scanner.open();//注意：扫描引擎上电，该接口请勿频繁调用，频繁关闭串口会导致程序卡死
        //scanner.resultScan();//注意：恢复iScan默认设置，频繁重置串口会导致程序卡死
        //scanner.close();//注意：恢复关闭扫描引擎电源，频繁重置串口会导致程序卡死

        //***********重要
        scanner.setOutputMode(1);
        /**设置扫描结果的输出模式，参数为0和1：
         * 0为模拟输出，同时广播扫描数据（在光标停留的地方输出扫描结果同时广播扫描数据）;
         * 1为广播输出（只广播扫描数据）；
         * 2为模拟按键输出；
         * */

        //***********重要
        //扫描结果的意图过滤器action一定要使用"android.intent.action.SCANRESULT"
        intentFilter = new IntentFilter();
        intentFilter.addAction(RES_ACTION);

        //**********重要
        //注册广播接受者
        scanReceiver = new ScannerResultReceiver();
        registerReceiver(scanReceiver, intentFilter);
    }

    /**
     * 扫描结果广播接收
     */
    //*********重要
    private class ScannerResultReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            Log.d("111", "intent.getAction()-->" + intent.getAction());//

            //*******重要，注意Extral为"value"
            final String scanResult = intent.getStringExtra("value");

            //*******重要
            if (intent.getAction().equals(RES_ACTION)) {
                //获取扫描结果
                if (scanResult.length() > 0) { //如果条码长度>0，解码成功。如果条码长度等于0解码失败。
                    webView.loadUrl("javascript:callbackFromQrcode('" + scanResult + "')");
                } else {
                    /**扫描失败提示使用有两个条件：
                     1，需要先将扫描失败提示接口打开只能在广播模式下使用，其他模式无法调用。
                     2，通过判断条码长度来判定是否解码成功，当长度等于0时表示解码失败。
                     * */
                    Toast.makeText(getApplicationContext(), "解码失败！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class AndroidForJs {
        private Context context;

        public AndroidForJs(Context context) {
            this.context = context;
        }

        //api17以后，只有public且添加了 @JavascriptInterface 注解的方法才能被调用
        @JavascriptInterface
        public void startQrcode() {
            startActivityForResult(new Intent(WebViewActivity.this, QRActivity.class), REQUEST_CAMERA);
        }
        @JavascriptInterface
        public String getImei() {
//           return  PhoneUtil.getIMEIDeviceId(WebViewActivity.this);
            return PhoneUtil.getIpAddress(getApplicationContext());
//            return TextUtils.isEmpty(imeiDeviceId)?mImei:imeiDeviceId;
        }

    }


    private void runWebView(final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAMERA:
                String qrcode = data.getStringExtra("qrcode");
                webView.loadUrl("javascript:callbackFromQrcode('" + qrcode + "')");
                break;

        }
    }

    /**
     * 接收广播
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("extra.mdm.respone")) {
                String imei = intent.getStringExtra("Data");//取得IMEI号
                if(!TextUtils.isEmpty(imei)){
                    mImei = imei;
//                    webView.loadUrl("javascript:callbackFromImei('" + imei + "')");
                }
                Log.d("idata", "imei == " + imei);
            }
        }
    };
    String mImei;
    /**
     * 发送广播
     */
    private void getDeviceImei() {
        Intent i = new Intent("extra.mdm.request");
        i.putExtra("Cmd", 0x0003);
        i.putExtra("Timestamp", System.currentTimeMillis());
        i.putExtra("Option", 0);
        this.sendBroadcast(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mBroadcastReceiver);
    }
}