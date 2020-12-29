package com.example.myarmapp;
import android.graphics.Paint;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import com.google.android.material.shadow.ShadowRenderer;
import com.friendlyarm.AndroidSDK.HardwareControler;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import org.achartengine.ChartFactory;

public class MainActivity extends AppCompatActivity {
    private int PWMstate = 0;
    private int LEDstate = 0;
    private ConstraintLayout CurveLayout;
    private GraphicalView mView;
    private ChartService mService;
    private Timer timer;
    private int AdcValue = 0;
    private int t = 0;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            AdcValue = HardwareControler.readADC();
            AdcValue = AdcValue*1800/4096;
            mService.updateChart(t, AdcValue);
            t+=5;
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurveLayout = (ConstraintLayout) findViewById(R.id.left_temperature_curve);
        mService = new ChartService(this);
        mService.setXYMultipleSeriesDataset("ADC曲线");
        mService.setXYMultipleSeriesRenderer(100, 180, "ADC曲线", "时间", "电压",
                Color.BLUE, Color.BLUE, Color.RED, Color.WHITE);
        mView = mService.getGraphicalView();
        CurveLayout.addView(mView, new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                handler.sendMessage(handler.obtainMessage());
            }
        }, 20, 2000);
    }
    public void btnOnClick(View v){
        Button PWMbtn = (Button) findViewById(R.id.button);
        if(PWMstate==0){
            EditText freqInput =(EditText) findViewById (R.id.textInputEditText);

            int freq=Integer.parseInt(freqInput.getText().toString());
            if (freq>10000) {
                Toast.makeText(MainActivity.this,"请输入小于10000的值！",Toast.LENGTH_SHORT).show();
            } else{
                HardwareControler.PWMPlay(freq);
                Toast.makeText(MainActivity.this,"嗡嗡嗡！",Toast.LENGTH_SHORT).show();
            }
            PWMbtn.setText("关闭");
            PWMstate = 1;
        }
        else {
            HardwareControler.PWMStop();
            PWMbtn.setText("开启");
            PWMstate = 0;
        }
    }
    public void ledController(View v){
        if(LEDstate==0){
            HardwareControler.setLedState(1,1);
            LEDstate = 1;
            Toast.makeText(MainActivity.this,"Opps,Open!",Toast.LENGTH_SHORT).show();
        }else{
            HardwareControler.setLedState(1,0);
            LEDstate = 0;
            Toast.makeText(MainActivity.this,"Opps,Close!",Toast.LENGTH_SHORT).show();
        }
    }
}
