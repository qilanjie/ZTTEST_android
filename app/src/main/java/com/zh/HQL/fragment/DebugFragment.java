package com.zh.HQL.fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.zh.HQL.R;
import com.zh.HQL.activity.MainActivity;
import com.zh.HQL.activity.SettingActivity;

import java.io.File;
import java.text.DecimalFormat;

public class DebugFragment extends ViewPagerFragment {
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(isVisible){
            mainActivity.mBuffer = new byte[5];
            mainActivity.mBuffer[0] = 0x5A;
            mainActivity.mBuffer[1] = (byte) 0xA5;
            mainActivity.mBuffer[2] = 0x02;
            mainActivity.mBuffer[3] = 0x10;
            mainActivity.mBuffer[4] = 0x10;
            MainActivity.stopSend = false;
            mainActivity.sendonce(false);
            handler.postDelayed(runnable, 500 * 1);
        }
        else {
//            mainActivity.mBuffer = new byte[5];
//            mainActivity.mBuffer[0] = 0x5A;
//            mainActivity.mBuffer[1] = (byte) 0xA5;
//            mainActivity.mBuffer[2] = 0x02;
//            mainActivity.mBuffer[3] = 0x11;
//            mainActivity.mBuffer[4] = 0x11;
//            mainActivity.stopSend = false;
//            mainActivity.sendonce(false);
            handler.removeCallbacks(runnable);
        }
    }

    TextView TextView_yali;
    ToggleButton toggleButton1;ToggleButton toggleButton2;ToggleButton toggleButton3;ToggleButton toggleButton4;
    ToggleButton toggleButton5;ToggleButton toggleButton6;ToggleButton toggleButton7;ToggleButton toggleButton8;
    ToggleButton toggleButton9;ToggleButton toggleButton10;
    static  int kg;
    MainActivity mainActivity ;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 500 * 1);// 间隔1/2秒

        }

        void update() {
            if (toggleButton1.isChecked()) kg = kg | 0x01;
            else kg = kg & ~0x01;
            if (toggleButton2.isChecked()) kg = kg | 0x02;
            else kg = kg & ~0x02;
            if (toggleButton3.isChecked()) kg = kg | 0x04;
            else kg = kg & ~0x04;
            if (toggleButton4.isChecked()) kg = kg | 0x08;
            else kg = kg & ~0x08;
            if (toggleButton5.isChecked()) kg = kg | 0x10;
            else kg = kg & ~0x10;
            if (toggleButton6.isChecked()) kg = kg | 0x20;
            else kg = kg & ~0x20;
            if (toggleButton7.isChecked()) kg = kg | 0x40;
            else kg = kg & ~0x40;
            if (toggleButton8.isChecked()) kg = kg | 0x80;
            else kg = kg & ~0x80;
            if (toggleButton10.isChecked()) kg = kg | 0x100;
            else kg = kg & ~0x100;
            mainActivity.mBuffer = new byte[7];
            mainActivity.mBuffer[0] = 0x5A;
            mainActivity.mBuffer[1] = (byte) 0xA5;
            mainActivity.mBuffer[2] = 0x04;
            mainActivity.mBuffer[3] = 0x0A;
            mainActivity.mBuffer[4] = (byte) (kg >> 8);
            mainActivity.mBuffer[5] = (byte) kg;
            mainActivity.mBuffer[6] =(byte) (mainActivity.mBuffer[3]^mainActivity.mBuffer[4]^mainActivity.mBuffer[5]);
            MainActivity.stopSend = false;
            mainActivity.sendonce(true);
            Log.d("qwh", "二进制开关量:" + Integer.toBinaryString(kg));
        }
    };
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
        kg=0;
        mainActivity=(MainActivity) getActivity();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView_yali=(TextView) (view.findViewById(R.id.TextView_yali));
        TextView_yali.setText("压力1:" +new DecimalFormat("0.000").format(0)
                + "kPa  压力2:" +new DecimalFormat("0.0").format(0 ) + "kPa\n"
                + "温度1:" +new DecimalFormat("00").format(0)
                + "  温度2:" +new DecimalFormat("00").format(0)
                + "  温度3:" +new DecimalFormat("00").format(0)
                + "\nSTM32温度:" +new DecimalFormat("00").format(0)
                +"  管路液体检测:无"
        );
        toggleButton1=(ToggleButton)view.findViewById(R.id.toggleButton1);
        toggleButton2=(ToggleButton)view.findViewById(R.id.toggleButton2);
        toggleButton3=(ToggleButton)view.findViewById(R.id.toggleButton3);
        toggleButton4=(ToggleButton)view.findViewById(R.id.toggleButton4);
        toggleButton5=(ToggleButton)view.findViewById(R.id.toggleButton5);
        toggleButton6=(ToggleButton)view.findViewById(R.id.toggleButton6);
        toggleButton7=(ToggleButton)view.findViewById(R.id.toggleButton7);
        toggleButton8=(ToggleButton)view.findViewById(R.id.toggleButton8);
        toggleButton9=(ToggleButton)view.findViewById(R.id.toggleButton9);
        toggleButton10=(ToggleButton)view.findViewById(R.id.toggleButton10);
//        toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    kg = kg | 0x01;
//                }else{
//                    kg = kg & ~0x01;
//                }
//                mainActivity.mBuffer = new byte[7];
//                mainActivity.mBuffer[0] = 0x5A;
//                mainActivity.mBuffer[1] = (byte) 0xA5;
//                mainActivity.mBuffer[2] = 0x04;
//                mainActivity.mBuffer[3] = 0x0A;
//                mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                mainActivity.mBuffer[5] = (byte) kg;
//                mainActivity.mBuffer[6] =(byte) (mainActivity.mBuffer[3]^mainActivity.mBuffer[4]^mainActivity.mBuffer[5]);
//                mainActivity.stopSend = false;
//                mainActivity.sendonce(false);
//            }
//        });
//        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    kg = kg | 0x02;
//                }else{
//                    kg = kg & ~0x02;
//                }
//                mainActivity.mBuffer = new byte[7];
//                mainActivity.mBuffer[0] = 0x5A;
//                mainActivity.mBuffer[1] = (byte) 0xA5;
//                mainActivity.mBuffer[2] = 0x04;
//                mainActivity.mBuffer[3] = 0x0A;
//                mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                mainActivity.mBuffer[5] = (byte) kg;
//                mainActivity.mBuffer[6] =(byte) (mainActivity.mBuffer[3]^mainActivity.mBuffer[4]^mainActivity.mBuffer[5]);
//                mainActivity.stopSend = false;
//                mainActivity.sendonce(false);
//            }
//        });
//        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    kg = kg | 0x04;
//                }else{
//                    kg = kg & ~0x04;
//                }
//                mainActivity.mBuffer = new byte[7];
//                mainActivity.mBuffer[0] = 0x5A;
//                mainActivity.mBuffer[1] = (byte) 0xA5;
//                mainActivity.mBuffer[2] = 0x04;
//                mainActivity.mBuffer[3] = 0x0A;
//                mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                mainActivity.mBuffer[5] = (byte) kg;
//                mainActivity.mBuffer[6] =(byte) (mainActivity.mBuffer[3]^mainActivity.mBuffer[4]^mainActivity.mBuffer[5]);
//                mainActivity.stopSend = false;
//                mainActivity.sendonce(false);
//            }
//        });
//        toggleButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    kg = kg | 0x08;
//                }else{
//                    kg = kg & ~0x08;
//                }
//                mainActivity.mBuffer = new byte[7];
//                mainActivity.mBuffer[0] = 0x5A;
//                mainActivity.mBuffer[1] = (byte) 0xA5;
//                mainActivity.mBuffer[2] = 0x04;
//                mainActivity.mBuffer[3] = 0x0A;
//                mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                mainActivity.mBuffer[5] = (byte) kg;
//                mainActivity.mBuffer[6] =(byte) (mainActivity.mBuffer[3]^mainActivity.mBuffer[4]^mainActivity.mBuffer[5]);
//                mainActivity.stopSend = false;
//                mainActivity.sendonce(false);
//            }
//        });
// //       toggleButton5.setEnabled(false);
//  //      toggleButton6.setEnabled(false);
//        toggleButton5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    kg = kg | 0x10;
//                }else{
//                    kg = kg & ~0x10;
//                }
//                mainActivity.mBuffer = new byte[7];
//                mainActivity.mBuffer[0] = 0x5A;
//                mainActivity.mBuffer[1] = (byte) 0xA5;
//                mainActivity.mBuffer[2] = 0x04;
//                mainActivity.mBuffer[3] = 0x0A;
//                mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                mainActivity.mBuffer[5] = (byte) kg;
//                mainActivity.mBuffer[6] =(byte) (mainActivity.mBuffer[3]^mainActivity.mBuffer[4]^mainActivity.mBuffer[5]);
//                mainActivity.stopSend = false;
//                mainActivity.sendonce(false);
//            }
//        });
//        toggleButton6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    kg = kg | 0x20;
//                }else{
//                    kg = kg & ~0x20;
//                }
//                mainActivity.mBuffer = new byte[7];
//                mainActivity.mBuffer[0] = 0x5A;
//                mainActivity.mBuffer[1] = (byte) 0xA5;
//                mainActivity.mBuffer[2] = 0x04;
//                mainActivity.mBuffer[3] = 0x0A;
//                mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                mainActivity.mBuffer[5] = (byte) kg;
//                mainActivity.mBuffer[6] =(byte) (mainActivity.mBuffer[3]^mainActivity.mBuffer[4]^mainActivity.mBuffer[5]);
//                mainActivity.stopSend = false;
//                mainActivity.sendonce(false);
//            }
//        });
//        toggleButton7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    kg = kg | 0x40;
//                }else{
//                    kg = kg & ~0x40;
//                }
//                mainActivity.mBuffer = new byte[7];
//                mainActivity.mBuffer[0] = 0x5A;
//                mainActivity.mBuffer[1] = (byte) 0xA5;
//                mainActivity.mBuffer[2] = 0x04;
//                mainActivity.mBuffer[3] = 0x0A;
//                mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                mainActivity.mBuffer[5] = (byte) kg;
//                mainActivity.mBuffer[6] =(byte) (mainActivity.mBuffer[3]^mainActivity.mBuffer[4]^mainActivity.mBuffer[5]);
//                mainActivity.stopSend = false;
//                mainActivity.sendonce(false);
//            }
//        });
//        toggleButton8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    kg = kg | 0x80;
//                }else{
//                    kg = kg & ~0x80;
//                }
//                mainActivity.mBuffer = new byte[7];
//                mainActivity.mBuffer[0] = 0x5A;
//                mainActivity.mBuffer[1] = (byte) 0xA5;
//                mainActivity.mBuffer[2] = 0x04;
//                mainActivity.mBuffer[3] = 0x0A;
//                mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                mainActivity.mBuffer[5] = (byte) kg;
//                mainActivity.mBuffer[6] =(byte) (mainActivity.mBuffer[3]^mainActivity.mBuffer[4]^mainActivity.mBuffer[5]);
//                mainActivity.stopSend = false;
//                mainActivity.sendonce(false);
//            }
//        });
//        toggleButton9.setEnabled(false);
//        toggleButton9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    kg = kg | 0x30;
////                    if(!toggleButton5.isChecked())toggleButton5.toggle();
////                   if(!toggleButton6.isChecked())toggleButton6.toggle();
//                }else{
//                    kg = kg & ~0x30;
////                    if(toggleButton5.isChecked())toggleButton5.toggle();
////                    if(toggleButton6.isChecked())toggleButton6.toggle();
//                }
//                mainActivity.mBuffer = new byte[7];
//                mainActivity.mBuffer[0] = 0x5A;
//                mainActivity.mBuffer[1] = (byte) 0xA5;
//                mainActivity.mBuffer[2] = 0x04;
//                mainActivity.mBuffer[3] = 0x0A;
//                mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                mainActivity.mBuffer[5] = (byte) kg;
//                mainActivity.mBuffer[6] =(byte) (mainActivity.mBuffer[3]^mainActivity.mBuffer[4]^mainActivity.mBuffer[5]);
//                mainActivity.stopSend = false;
//                mainActivity.sendonce(false);
//            }
//        });
        toggleButton5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(toggleButton6.isChecked()){
                        if(!toggleButton9.isChecked()){
                            toggleButton9.toggle();
                        }
                    }
                }
                else{
                    if(!toggleButton6.isChecked()){
                        if(toggleButton9.isChecked()){
                            toggleButton9.toggle();
                        }
                    }
                }
            }
        });
        toggleButton6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(toggleButton5.isChecked()){
                        if(!toggleButton9.isChecked()){
                            toggleButton9.toggle();
                        }
                    }
                }
                else{
                    if(!toggleButton5.isChecked()){
                        if(toggleButton9.isChecked()){
                            toggleButton9.toggle();
                        }
                    }
                }
            }
        });
        toggleButton9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!toggleButton5.isChecked()){
                        toggleButton5.toggle();
                    }
                    if(!toggleButton6.isChecked()){
                        toggleButton6.toggle();
                    }
                }
                else {
                    if(toggleButton5.isChecked()){
                        toggleButton5.toggle();
                    }
                    if(toggleButton6.isChecked()){
                        toggleButton6.toggle();
                    }
                }

            }
        });

        BoomMenuButton bmb = (BoomMenuButton) view.findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_4_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.Horizontal);
        TextOutsideCircleButton.Builder builder0 = new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent();// 切换界面
                        // When the boom-button corresponding this builder is clicked.
                        intent.setClass(getContext(), SettingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 不重复打开多个界面
                        startActivity(intent);
//                        Toast.makeText(mainActivity, "设置 " , Toast.LENGTH_SHORT).show();

                    }
                }).normalImageRes(R.drawable.ic_build_black_24dp).normalText("设置").textSize(24).textHeight(48).normalTextColor(Color.rgb(0xFF,0xE5,0x1A));
        bmb.addBuilder(builder0);
        TextOutsideCircleButton.Builder builder1 = new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Intent intent_gb = new Intent("android.intent.action.VIEW");

                        intent_gb.addCategory("android.intent.category.DEFAULT");

                        intent_gb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri1 = Uri.fromFile(new File("/storage/emulated/0/GB.pdf"));
                        intent_gb.setDataAndType(uri1, "application/pdf");//  文档格式
                        try {
                            startActivity(intent_gb);
                        } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, "GB.pdf没找到！", Toast.LENGTH_SHORT).show();
                            showTip("GB.pdf没找到！");
                        }
//                        Toast.makeText(mainActivity, "国标 " , Toast.LENGTH_SHORT).show();
                    }
                }).normalImageRes(R.drawable.ic_chrome_reader_mode_black_24dp).normalText("国标").textSize(24).textHeight(48).normalTextColor(Color.rgb(0xFF,0xE5,0x1A));
        bmb.addBuilder(builder1);
        TextOutsideCircleButton.Builder builder2 = new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.


                        Intent intent_shouce = new Intent("android.intent.action.VIEW");

                        intent_shouce.addCategory("android.intent.category.DEFAULT");

                        intent_shouce.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri2 = Uri.fromFile(new File("/storage/emulated/0/HandBook.pdf"));

                        intent_shouce.setDataAndType(uri2, "application/pdf");//  文档格式
                        try {
                            startActivity(intent_shouce);
                        } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, "HandBook.pdf没找到！", Toast.LENGTH_SHORT).show();
                            showTip("HandBook.pdf没找到！");
                        }
 //                       Toast.makeText(mainActivity, "手册 " , Toast.LENGTH_SHORT).show();
                    }
                }).normalImageRes(R.drawable.ic_help_outline_black_24dp).normalText("手册").textSize(24).textHeight(48).normalTextColor(Color.rgb(0xFF,0xE5,0x1A));
        bmb.addBuilder(builder2);
        TextOutsideCircleButton.Builder builder3 = new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
             //           Toast.makeText(mainActivity, "关于 " , Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(getContext()).setTitle("关于我们").setMessage("山东中惠仪器有限公司\n\n地         址：山东省淄博市高新技术产业开发区民福路8号\n电         话：0533－3981058   3982656   3983199\n传         真：0533－3983199\n服务热线：0533－3982286\n邮         编：255000\n网         址：www.zhonghui.com.cn\n电子信箱：zhonghui@zhonghui.com.cn\n\n")
                                .setIcon(R.drawable.logo)
                                .setPositiveButton("确    定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mainActivity.hideBottomUIMenu();
                                    }
                                }).show();
                    }
                }).normalImageRes(R.drawable.ic_contacts_black_24dp).normalText("关于").textSize(24).textHeight(48).normalTextColor(Color.rgb(0xFF,0xE5,0x1A));
        bmb.addBuilder(builder3);
    }


    public void update() { // 刷新msg的内容
        // Log.d("qwh", Integer.toString(mainActivity.rec_state));
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                float yalif1 = MainActivity.yali1 * 2.490f / 65536 /129.98f*10160;
//                String yali1 = String.valueOf((double) (Math.round(yalif1 * 1000)) / 1000);
                float yalif2 = MainActivity.yali2 * 2.492f / 65536/128*15221+(float)(MainActivity.daqiya)/1000;
//                String yali2 = String.valueOf((double) (Math.round(yalif2 * 1000)) / 1000);
                float vp=2.492f* MainActivity.wd1 /(4096*51)+2.988f/21;
                float rp=(float)(2000*vp)/(2.988f-vp);
                float wd1f = (float)(rp-100)/0.385f;
                 vp=2.492f* MainActivity.wd2 /(4096*51)+2.988f/21;
                 rp=(float)(2000*vp)/(2.988f-vp);
                float wd2f = (float)(rp-100)/0.385f;
                vp=2.492f* MainActivity.wd3 /(4096*51)+2.988f/21;
                rp=(float)(2000*vp)/(2.988f-vp);
                float wd3f = (float)(rp-100)/0.385f;
//                String yali3 = String.valueOf((double) (Math.round(yalif3 * 1000)) / 1000);
                float wd4f= MainActivity.wd4 *2.491f/4096;
                double wd4= (1.43-wd4f)/0.0043+25;
                String isyeti="012345";
                if(MainActivity.yeti ==0)isyeti="无";
                if(MainActivity.yeti ==1)isyeti="有";
 //               String wd=String.valueOf((double) (Math.round(wdd * 1000)) / 1000);
//                wd=fix3weixiaoshu(wd);
 //               TextView_yali.setVisibility(View.INVISIBLE);
                TextView_yali.setText("压力1:" +new DecimalFormat("0.000").format(yalif1)
                        + "kPa  压力2:" +new DecimalFormat("0.000").format(yalif2 ) + "kPa\n"
                        + "温度1:" +new DecimalFormat("00").format(wd1f)
                        + "  温度2:" +new DecimalFormat("00").format(wd2f)
                        + "  温度3:" +new DecimalFormat("00").format(wd3f)
                        + "\nSTM32温度:" +new DecimalFormat("00").format(wd4)
                        +"  管路液体检测:"+isyeti
                );
                //电压3:" + yali3 + "V\nCPU温度:"+wd+"℃");
//                if (toggleButton1.isChecked()) kg = kg | 0x01;
//                else kg = kg & ~0x01;
//                if (toggleButton2.isChecked()) kg = kg | 0x02;
//                else kg = kg & ~0x02;
//                if (toggleButton3.isChecked()) kg = kg | 0x04;
//                else kg = kg & ~0x04;
//                if (toggleButton4.isChecked()) kg = kg | 0x08;
//                else kg = kg & ~0x08;
//                if (toggleButton5.isChecked()) kg = kg | 0x10;
//                else kg = kg & ~0x10;
//                if (toggleButton6.isChecked()) kg = kg | 0x20;
//                else kg = kg & ~0x20;
//                if (toggleButton7.isChecked()) kg = kg | 0x40;
//                else kg = kg & ~0x40;
//                    if (toggleButton8.isChecked()) kg = kg | 0x80;
//                    else kg = kg & ~0x80;
//                if (toggleButton8.isChecked()) {
//                    toggleButton1.setChecked(true);
//                    toggleButton2.setChecked(true);
//                }
//                else {
////                        toggleButton1.setChecked(false);
////                        toggleButton2.setChecked(false);
//                }
//                Log.d("qwh", "二进制开关量:" + Integer.toBinaryString(kg));
//                mainActivity.mBuffer = new byte[7];
//                mainActivity.mBuffer[0] = 0x5A;
//                mainActivity.mBuffer[1] = (byte) 0xA5;
//                mainActivity.mBuffer[2] = 0x04;
//                mainActivity.mBuffer[3] = 0x0A;
//                mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                mainActivity.mBuffer[5] = (byte) kg;
//                mainActivity.mBuffer[6] = 0x0A;
//                mainActivity.stopSend = false;
//                mainActivity.sendonce(false);
//            handler.postDelayed(this, 1000* 1);// 间隔1秒
            }
        });
    }
    private Toast mToast;

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
