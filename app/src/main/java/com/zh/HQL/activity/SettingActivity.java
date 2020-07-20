package com.zh.HQL.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.zh.HQL.R;

public class SettingActivity extends Activity {
    TextInputEditText textInputEditText_V1;
    TextInputEditText textInputEditText_V2;
    TextInputEditText textInputEditText_V3;
    TextInputEditText textInputEditText_daqiya;
    TextInputEditText textInputEditText_mima;
    CheckBox checkBox_show;
    Button button_ok;
    Button button_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        hideBottomUIMenu();
        textInputEditText_V1 = (TextInputEditText) findViewById(R.id.textInputEditText_V1);
        textInputEditText_V2 = (TextInputEditText) findViewById(R.id.textInputEditText_V2);
        textInputEditText_V3 = (TextInputEditText) findViewById(R.id.textInputEditText_v3);
        textInputEditText_daqiya = (TextInputEditText) findViewById(R.id.textInputEditText_daqiya);
        textInputEditText_mima = (TextInputEditText) findViewById(R.id.textInputEditText_mima);
        checkBox_show = (CheckBox) findViewById(R.id.checkBox_show);
        button_ok = (Button) findViewById(R.id.button_ok);
        button_cancel = (Button) findViewById(R.id.button_cancel);
        textInputEditText_V1.setText(String.valueOf(MainActivity.v1));
        textInputEditText_V2.setText(String.valueOf(MainActivity.v2));
        textInputEditText_V3.setText(String.valueOf(MainActivity.v3));
        textInputEditText_daqiya.setText(String.valueOf(MainActivity.daqiya));
        checkBox_show.setChecked(MainActivity.isShow);

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                MainActivity.v1 = Float.parseFloat(textInputEditText_V1.getText().toString());
                MainActivity.v2 = Float.parseFloat(textInputEditText_V2.getText().toString());
                MainActivity.v3 = Float.parseFloat(textInputEditText_V3.getText().toString());
                MainActivity.daqiya = (int) Float.parseFloat(textInputEditText_daqiya.getText().toString());
                MainActivity.isShow = checkBox_show.isChecked();
                if(textInputEditText_mima.getText().toString().equals("8901")) {

                    editor.putFloat("v1", MainActivity.v1);
                    editor.putFloat("v2", MainActivity.v2);
                    editor.putFloat("v3", MainActivity.v3);
                    editor.putInt("daqiya", MainActivity.daqiya);
                    editor.putBoolean("isShow", MainActivity.isShow);
                    editor.commit();

                }
                finish();

            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    /**
     * 隐藏虚拟按键，并且全屏
     */
    public void hideBottomUIMenu() {
        // 隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            // for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
