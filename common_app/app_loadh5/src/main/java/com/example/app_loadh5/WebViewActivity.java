package com.example.app_loadh5;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qrmodule.QRActivity;
import com.tbruyelle.rxpermissions3.RxPermissions;

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    private static final int TV_HEIGHT = 100;
    private final int REQUEST_CAMERA = 1;
    private WebView webView;
    private File mTmpFile;
    ScannerInterface scanner;
    IntentFilter intentFilter;
    BroadcastReceiver scanReceiver;
    //*******重要
    private static final String RES_ACTION = "android.intent.action.SCANRESULT";
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                //网页加载完之后，android调用js方法
////                String msg ="展示内容";
////                webView.loadUrl("javascript:callbackFromQrcode('"+msg+"')");
//                super.onPageFinished(view, url);
//
//            }
//        });
        //允许webview对文件的操作
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        //设置编码方式
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);  //开启js
        webSettings.setAppCacheEnabled(true);

        loadUrlFromConfigFile();
//        setNetWeb();
//        setLocalWeb();
    }

    private void loadUrlFromConfigFile() {
        webView.addJavascriptInterface(new AndroidForJs(),
                "ObjForJs");
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.CAMERA, Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).
                subscribe(granted -> {
                    if (!granted) { // Always true pre-M
                        // I can control the camera now
                        // Oups permission denied
                        Toast.makeText(this, "请打开相机权限", Toast.LENGTH_SHORT).show();
                        this.finish();
                    } else {
                        //读取sdcard文件
                        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                        File file = new File(dir, "/checkConfig.txt");
                        try {
                            FileReader reader = new FileReader(file);
                            char[] content = new char[1024];
                            reader.read(content);
                            String json = new String(content);
                            JSONObject object = new JSONObject(json);
                            String url = object.getString("url");
                            ip = object.getString("ip");
                            if (TextUtils.isEmpty(url)) {
                                Toast.makeText(this, "请检查是否有文件 checkConfig.txt", Toast.LENGTH_LONG).show();
                                return;
                            }
                            webView.loadUrl(url);
                        } catch (Exception e) {
                            Toast.makeText(this, "请检查是否有文件 checkConfig", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册接收广播，并设置输出模式为广播模式
        initScanner();
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

        //api17以后，只有public且添加了 @JavascriptInterface 注解的方法才能被调用
        @JavascriptInterface
        public void startQrcode() {
            startActivityForResult(new Intent(WebViewActivity.this, QRActivity.class), REQUEST_CAMERA);
        }

        @JavascriptInterface
        public String getImei() {
            if (TextUtils.isEmpty(ip)) {
                return PhoneUtil.getIpAddress(getApplicationContext());
            }
            return ip;
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(scanReceiver);
    }

    @NonNull
    private TextView getTextView() {
        final TextView textView = new TextView(getApplication());
        textView.setTextColor(Color.GRAY);
        textView.setTextSize(20f);
        textView.setBackgroundColor(Color.YELLOW);
        textView.setText("WebActivity TextView ");
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtil.dp2px(getApplication(), TV_HEIGHT)));
        return textView;
    }

    private void setNetWeb() {
        webView.loadUrl("https://www.baidu.com/");
//        webView.loadUrl("https://hxsb.by1983.cn/index.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                L.e("webView:",webView.getChildCount()+"");
                final TextView textView = getTextView();
                webView.evaluateJavascript("javaScript: function getAdPosition() {\n"
                                + "        var advertisement = document.getElementById(\"index-form\");\n"
                                + "        return advertisement.offsetTop;\n"
                                + "    };getAdPosition()",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                Log.e("getAdPosition:", value);
                                if (value == null || value.equals("null")) {
                                    return;
                                }
                                textView.setTranslationY(DensityUtil.dp2px(WebViewActivity.this, Float.parseFloat(value)));
                                webView.addView(textView);
                                webView.loadUrl("javaScript: function setAdHeight(height) {\n"
                                        + "        var advertisement = document.getElementById(\"index-form\");\n"
                                        + "        advertisement.style.marginTop=height+\"px\";\n"
                                        + "    }; setAdHeight(" + (TV_HEIGHT + 16) + ");");
                            }
                        });
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });

    }

    private void setLocalWeb() {
        webView.loadUrl("file:///android_asset/html_mix.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                L.e("webView:",webView.getChildCount()+"");
                final TextView textView = getTextView();

//                webView .evaluateJavascript("javaScript:getAdPosition()",
//                        new ValueCallback<String>() {
//                            @Override
//                            public void onReceiveValue(String value) {
//                                Log.i("getAdPosition:",value);
//                                textView.setTranslationY(DensityUtil.dp2px(WebViewActivity.this,Float.parseFloat(value)));
//                                webView.addView(textView);
//                                webView.loadUrl("javaScript:setAdHeight("+TV_HEIGHT+")");
//                            }
//                        });
                webView.evaluateJavascript(" function getAdPosition() {\n" +
                                "        var advertisement = document.getElementById(\"advertisement\");\n" +
                                "        return advertisement.offsetTop;\n" +
                                "    };getAdPosition()",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                Log.i("getAdPosition:", value);
                                textView.setTranslationY(DensityUtil.dp2px(WebViewActivity.this, Float.parseFloat(value)));
                                webView.addView(textView);
                                webView.loadUrl("javaScript:setAdHeight(" + TV_HEIGHT + ")");
                            }
                        });
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
    }
}