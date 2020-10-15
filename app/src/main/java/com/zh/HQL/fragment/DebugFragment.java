package com.zh.HQL.fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zh.HQL.R;
import com.zh.HQL.activity.MainActivity;

import java.text.DecimalFormat;

public class DebugFragment extends ViewPagerFragment {
    static int kg;
    TextView TextView_yali;
    ToggleButton toggleButton1;
    //    ToggleButton toggleButton2;
//    ToggleButton toggleButton3;
    //   ToggleButton toggleButton4;
    ToggleButton toggleButton5;
    ToggleButton toggleButton6;
    ToggleButton toggleButton7;
    ToggleButton toggleButton8;
    ToggleButton toggleButton9;
    ToggleButton toggleButton10;
    ToggleButton toggleButton11;
    ToggleButton toggleButton12;
    ToggleButton toggleButton13;
    ToggleButton toggleButton14;
    ToggleButton toggleButton15;
    ToggleButton toggleButton16;
    ToggleButton toggleButton17;
    ToggleButton toggleButton19;
    ToggleButton toggleButton20;
    //    ToggleButton toggleButton1234;
    ToggleButton toggleButtonMS;
    ToggleButton toggleButtonSX;
    ToggleButton toggleButtonXX;
    ToggleButton toggleButtonTS;
    Button buttonJY, buttonBD, buttonSY, buttonCX,buttonSY2;
    MainActivity mainActivity;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 100 * 1);// 间隔1/2秒

        }

        void update() {
            if (toggleButton1.isChecked()) kg = kg | 0x01;
            else kg = kg & ~0x01;
//            if (toggleButton2.isChecked()) kg = kg | 0x02;
//            else kg = kg & ~0x02;
//            if (toggleButton3.isChecked()) kg = kg | 0x04;
//            else kg = kg & ~0x04;
//            if (toggleButton4.isChecked()) kg = kg | 0x08;
//            else kg = kg & ~0x08;
            if (toggleButton5.isChecked()) kg = kg | 0x10;
            else kg = kg & ~0x10;
            if (toggleButton6.isChecked()) kg = kg | 0x20;
            else kg = kg & ~0x20;
            if (toggleButton7.isChecked()) kg = kg | 0x40;
            else kg = kg & ~0x40;
            if (toggleButton8.isChecked()) kg = kg | 0x80;
            else kg = kg & ~0x80;
            if (toggleButton9.isChecked()) kg = kg | 0x100;
            else kg = kg & ~0x100;
            if (toggleButton10.isChecked()) kg = kg | 0x200;
            else kg = kg & ~0x200;
            if (toggleButton11.isChecked()) kg = kg | 0x400;
            else kg = kg & ~0x400;
            if (toggleButton12.isChecked()) kg = kg | 0x800;
            else kg = kg & ~0x800;
            if (toggleButton13.isChecked()) kg = kg | 0x1000;
            else kg = kg & ~0x1000;
            if (toggleButton14.isChecked()) kg = kg | 0x2000;
            else kg = kg & ~0x2000;
            if (toggleButton15.isChecked()) kg = kg | 0x4000;
            else kg = kg & ~0x4000;
            if (toggleButton16.isChecked()) kg = kg | 0x8000;
            else kg = kg & ~0x8000;
            if (toggleButton17.isChecked()) kg = kg | 0x10000;
            else kg = kg & ~0x10000;
            if (toggleButton19.isChecked()) kg = kg | 0x40000;
            else kg = kg & ~0x40000;
            if (toggleButton20.isChecked()) kg = kg | 0x80000;
            else kg = kg & ~0x80000;
            if (toggleButtonMS.isChecked()) kg = kg | 0x200000;//22
            else kg = kg & ~0x200000;
            if (toggleButtonSX.isChecked()) kg = kg | 0x400000;//23
            else kg = kg & ~0x400000;
            if (toggleButtonXX.isChecked()) kg = kg | 0x800000;//24
            else kg = kg & ~0x800000;
            mainActivity.mBuffer = new byte[8];
            mainActivity.mBuffer[0] = 0x5A;
            mainActivity.mBuffer[1] = (byte) 0xA5;
            mainActivity.mBuffer[2] = 0x05;
            mainActivity.mBuffer[3] = 0x0A;
            mainActivity.mBuffer[4] = (byte) (kg >> 16);
            mainActivity.mBuffer[5] = (byte) (kg >> 8);
            mainActivity.mBuffer[6] = (byte) kg;
            mainActivity.mBuffer[7] = (byte) (mainActivity.mBuffer[3] ^ mainActivity.mBuffer[4] ^ mainActivity.mBuffer[5]^ mainActivity.mBuffer[6]^ mainActivity.mBuffer[7]);
            while (!MainActivity.stopSend) {
            }
            MainActivity.stopSend = false;
            mainActivity.sendonce(true);

            Log.d("qwh", "二进制开关量:" + Integer.toBinaryString(kg));
        }
    };
    private Toast mToast;

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
//            mainActivity.mBuffer = new byte[5];
//            mainActivity.mBuffer[0] = 0x5A;
//            mainActivity.mBuffer[1] = (byte) 0xA5;
//            mainActivity.mBuffer[2] = 0x02;
//            mainActivity.mBuffer[3] = 0x10;
//            mainActivity.mBuffer[4] = 0x10;
//            MainActivity.stopSend = false;
//            mainActivity.sendonce(false);
//            handler.postDelayed(runnable, 500 * 1);
        } else {

//            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_debug, container, false);
//        return view;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_debug, container, false);

        }
        return rootView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kg = 0;
        mainActivity = (MainActivity) getActivity();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView_yali = (TextView) (view.findViewById(R.id.TextView_yali));
        TextView_yali.setText("压力1:" + new DecimalFormat("0.000").format(0)
                + "kPa  压力2:" + new DecimalFormat("0.000").format(0) + "kPa"

        );
        toggleButton1 = view.findViewById(R.id.toggleButton1);
        toggleButton5 = view.findViewById(R.id.toggleButton5);
        toggleButton6 = view.findViewById(R.id.toggleButton6);
        toggleButton7 = view.findViewById(R.id.toggleButton7);
        toggleButton8 = view.findViewById(R.id.toggleButton8);
        toggleButton9 = view.findViewById(R.id.toggleButton9);
        toggleButton10 = view.findViewById(R.id.toggleButton10);
        toggleButton11 = view.findViewById(R.id.toggleButton11);
        toggleButton12 = view.findViewById(R.id.toggleButton12);
        toggleButton13 = view.findViewById(R.id.toggleButton13);
        toggleButton14 = view.findViewById(R.id.toggleButton14);
        toggleButton15 = view.findViewById(R.id.toggleButton15);
        toggleButton16 = view.findViewById(R.id.toggleButton16);
        toggleButton17 = view.findViewById(R.id.toggleButton17);
        toggleButton19 = view.findViewById(R.id.toggleButton19);
        toggleButton20 = view.findViewById(R.id.toggleButton20);
        toggleButtonMS = view.findViewById(R.id.toggleButtonMS);
        toggleButtonSX = view.findViewById(R.id.toggleButtonSX);
        toggleButtonXX = view.findViewById(R.id.toggleButtonXX);
        buttonJY = view.findViewById(R.id.buttonJY);
        buttonBD = view.findViewById(R.id.buttonBD);
        toggleButtonTS = view.findViewById(R.id.toggleButtonTS);
        buttonSY = view.findViewById(R.id.buttonSY);
        buttonSY2 = view.findViewById(R.id.buttonSY2);
        buttonCX = view.findViewById(R.id.buttonCX);

 //       toggleButtonTS.setClickable(false);
        buttonSY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.mBuffer = new byte[6];
                mainActivity.mBuffer[0] = 0x5A;
                mainActivity.mBuffer[1] = (byte) 0xA5;
                mainActivity.mBuffer[2] = 0x03;
                mainActivity.mBuffer[3] = 0x0B;
                mainActivity.mBuffer[4] = 0x01;
                mainActivity.mBuffer[5] = 0x0A;
                while (!MainActivity.stopSend) {
                }
                MainActivity.stopSend = false;
                mainActivity.send();


            }
        });
        buttonSY2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.mBuffer = new byte[6];
                mainActivity.mBuffer[0] = 0x5A;
                mainActivity.mBuffer[1] = (byte) 0xA5;
                mainActivity.mBuffer[2] = 0x03;
                mainActivity.mBuffer[3] = 0x0B;
                mainActivity.mBuffer[4] = 0x04;
                mainActivity.mBuffer[5] = 0x0F;
                while (!MainActivity.stopSend) {
                }
                MainActivity.stopSend = false;
                mainActivity.send();


            }
        });
        buttonJY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.mBuffer = new byte[6];
                mainActivity.mBuffer[0] = 0x5A;
                mainActivity.mBuffer[1] = (byte) 0xA5;
                mainActivity.mBuffer[2] = 0x03;
                mainActivity.mBuffer[3] = 0x0B;
                mainActivity.mBuffer[4] = 0x05;
                mainActivity.mBuffer[5] = 0x0E;
                while (!MainActivity.stopSend) {
                }
                MainActivity.stopSend = false;
                mainActivity.send();

            }
        });
        buttonBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.mBuffer = new byte[6];
                mainActivity.mBuffer[0] = 0x5A;
                mainActivity.mBuffer[1] = (byte) 0xA5;
                mainActivity.mBuffer[2] = 0x03;
                mainActivity.mBuffer[3] = 0x0B;
                mainActivity.mBuffer[4] = 0x02;
                mainActivity.mBuffer[5] = 0x09;
                while (!MainActivity.stopSend) {
                }
                MainActivity.stopSend = false;
                mainActivity.send();

            }
        });
        buttonCX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.mBuffer = new byte[6];
                mainActivity.mBuffer[0] = 0x5A;
                mainActivity.mBuffer[1] = (byte) 0xA5;
                mainActivity.mBuffer[2] = 0x03;
                mainActivity.mBuffer[3] = 0x0B;
                mainActivity.mBuffer[4] = 0x03;
                mainActivity.mBuffer[5] = 0x08;
                while (!MainActivity.stopSend) {
                }
                MainActivity.stopSend = false;
                mainActivity.send();

            }
        });
        toggleButtonTS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    handler.postDelayed(runnable, 500 * 1);
                } else {
                    handler.removeCallbacks(runnable);
                }
            }
        });
        toggleButtonSX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    toggleButtonXX.setChecked(false);
                }
            }
        });
        toggleButtonXX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    toggleButtonSX.setChecked(false);
                }
            }
        });
    }

    public void update() { // 刷新msg的内容
        // Log.d("qwh", Integer.toString(mainActivity.rec_state));
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                float yalif1 = MainActivity.yali1 * 2.490f / 65536 / 128 * 10000;
//                String yali1 = String.valueOf((double) (Math.round(yalif1 * 1000)) / 1000);
                float yalif2 = MainActivity.yali2 * 2.490f / 65536 / 128 * 10000;
//                String yali2 = String.valueOf((double) (Math.round(yalif2 * 1000)) / 1000);

                TextView_yali.setText("压力1:" + new DecimalFormat("0.000").format(yalif1)
                        + "kPa  压力2:" + new DecimalFormat("0.000").format(yalif2) + "kPa\n"

                );

            }
        });
    }

    private void showTip(final String str) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(getContext(), "",
                            Toast.LENGTH_SHORT);

                    //key parameter
                    LinearLayout layout = (LinearLayout) mToast.getView();
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setTextSize(36);
                    //
                }
                //mToast.cancel();
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.setText(str);
                mToast.show();
            }
        });
    }
}
