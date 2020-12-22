package com.example.myarmapp;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.shadow.ShadowRenderer;
import com.friendlyarm.AndroidSDK.HardwareControler;

public class MainActivity extends AppCompatActivity {
    private int PWMstate = 0;
    private int LEDstate = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

