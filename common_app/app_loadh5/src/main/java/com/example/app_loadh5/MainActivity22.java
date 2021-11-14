package com.example.app_loadh5;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 开发注意事项：
 * 	1，该开发包基于iScan开发，需要预装iScan4.2.0以上版本。
 * 	2，所有重点部分已*****标注重要，请仔细阅读；
 * 	3，ScannerInterface.java接口为广播接口类，直接拷贝至新项目；
 *  4，activity_main.java代码仅作为开发代码参考，切勿直接将工程直接拷入使用；
 *  5，Demo使用广播接收数据，其他接口均屏蔽，如需使用其他接口请参考文档并联系技术支持使用。
 */
public class MainActivity22 extends Activity{

    TextView   tvScanResult;
    ScannerInterface  scanner;
    IntentFilter  intentFilter;
    BroadcastReceiver scanReceiver;

    //*******重要
    private static final String RES_ACTION = "android.intent.action.SCANRESULT";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScanResult = (TextView) this.findViewById(R.id.tv_scan_result);
        tvScanResult.setMovementMethod(ScrollingMovementMethod.getInstance());
//        initScanner();
    }

    /**
     * 避免页面进入后台时，接收扫描内容导致误触发业务操作，
     * 请在onResume和onPause中处理扫描业务
     */
    @Override
    protected void onResume() {
        super.onResume();
        //注册接收广播，并设置输出模式为广播模式
        initScanner();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //取消接收扫描广播，并恢复输出模式为默认

        if (scanReceiver != null){
            unregisterReceiver(scanReceiver);
        }

        if (scanner != null){
            scanner.setOutputMode(0);
        }
    }

    private void initScanner(){
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
    private class ScannerResultReceiver extends BroadcastReceiver{
        public void onReceive(Context context, Intent intent) {
            Log.d("111","intent.getAction()-->"+intent.getAction());//

            //*******重要，注意Extral为"value"
            final String scanResult = intent.getStringExtra("value");

            //*******重要
            if (intent.getAction().equals(RES_ACTION)){
                //获取扫描结果
                if(scanResult.length()>0){ //如果条码长度>0，解码成功。如果条码长度等于0解码失败。
                    tvScanResult.append("Barcode："+scanResult+"\n");

                    int offset=tvScanResult.getLineCount()*tvScanResult.getLineHeight();
                    if(offset>tvScanResult.getHeight()){
                        tvScanResult.scrollTo(0,offset-tvScanResult.getHeight());
                    }
                }else{
                    /**扫描失败提示使用有两个条件：
                     1，需要先将扫描失败提示接口打开只能在广播模式下使用，其他模式无法调用。
                     2，通过判断条码长度来判定是否解码成功，当长度等于0时表示解码失败。
                     * */
                    Toast.makeText(getApplicationContext(), "解码失败！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
