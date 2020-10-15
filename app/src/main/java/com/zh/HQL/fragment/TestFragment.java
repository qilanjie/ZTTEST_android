package com.zh.HQL.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.zh.HQL.R;
import com.zh.HQL.activity.MainActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestFragment extends ViewPagerFragment {
    QMUIRoundButton testButton;
    QMUIRoundButton cxButton;
    static int kg;
    static int second;
    static int pC, pJ, pC1, pS, tTime;
    static float t1, t2, t3, HQL1, HQL2, vY;
    boolean isTesting;
    boolean isEnd, isTingZhi;
    CheckBox checkBox2ci;
    MainActivity mainActivity;
    //    TextView TextView_YaLi1;
    Chronometer chronometer1;
    TextView textViewHQL1;
    TextView textViewHQL2;
    TextView textViewHQLPJ;
    TextView textViewTest;
    TextView textViewInfo;
    TextInputEditText TextInputEditTextName;
    FrameLayout frameLayout;
    SQLiteDatabase db;
    //config in your app

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 500 * 1);// 间隔1秒

        }

        void update() {
            if (!isTesting) {
                kg = kg & ~0xFEBF;//关除了7+9

            }
            mainActivity.mBuffer = new byte[7];
            mainActivity.mBuffer[0] = 0x5A;
            mainActivity.mBuffer[1] = (byte) 0xA5;
            mainActivity.mBuffer[2] = 0x04;
            mainActivity.mBuffer[3] = 0x0A;
            mainActivity.mBuffer[4] = (byte) (kg >> 8);
            mainActivity.mBuffer[5] = (byte) kg;
            mainActivity.mBuffer[6] = (byte) (mainActivity.mBuffer[3] ^ mainActivity.mBuffer[4] ^ mainActivity.mBuffer[5]);
//            mainActivity.stopSend = false;
//            mainActivity.sendonce(true);
            float yalif1 = mainActivity.yali1 * 2.492f / 65536 * 2;
//            TextView_YaLi1.setText(new DecimalFormat("0.000").format(yalif1) );
            second++;
            if (second >= Integer.MAX_VALUE) second = 0;
            //           Log.d("qwh", "二进制开关量:" + Integer.toBinaryString(kg));
            ////
            float yalif2 = mainActivity.yali2 * 2.492f / 65536 / 128 * 15221;
            float vp = 2.492f * mainActivity.wd1 / (4096 * 51) + 2.988f / 21;
            float rp = (float) (2000 * vp) / (2.988f - vp);
            float wd1f = (float) (rp - 100) / 0.385f;
            vp = 2.492f * mainActivity.wd2 / (4096 * 51) + 2.988f / 21;
            rp = (float) (2000 * vp) / (2.988f - vp);
            float wd2f = (float) (rp - 100) / 0.385f;
            vp = 2.492f * mainActivity.wd3 / (4096 * 51) + 2.988f / 21;
            rp = (float) (2000 * vp) / (2.988f - vp);
            float wd3f = (float) (rp - 100) / 0.385f;
            String isyeti = "012345";
            if (mainActivity.yeti == 0) isyeti = "无";
            if (mainActivity.yeti == 1) isyeti = "有";
//            textViewTest.setText("压力1:" + new DecimalFormat("0.000").format(yalif1)
//                    + "kPa  压力2:" + new DecimalFormat("0.0").format(yalif2) + "kPa\n"
//                    + "温度1:" + new DecimalFormat("00").format(wd1f)
//                    + "  温度2:" + new DecimalFormat("00").format(wd2f)
//                    + "  温度3:" + new DecimalFormat("00").format(wd3f)
//                    + "\n管路液体检测:" + isyeti
//            );
//            textViewTest.setText("pC=" + new DecimalFormat("0000Pa").format(pC)
//                    + "\npJ=" + new DecimalFormat("0000Pa").format(pJ)
//                    + "\npC1=" + new DecimalFormat("0000Pa").format(pC1)
//                    + "\npS=" + new DecimalFormat("000000Pa").format(pS)
//                    + "\nt1=" + new DecimalFormat("00").format(t1)
//                    + "\nt2=" + new DecimalFormat("00").format(t2)
//                    + "\nt3=" + new DecimalFormat("00").format(t3)
//            );
            textViewTest.setText("pC=" + new DecimalFormat("0000Pa").format((int) (1000 * yalif1)) + "                            pC=" + new DecimalFormat("0000Pa").format(pC)
                    + "\npJ=" + new DecimalFormat("0000Pa").format((int) (1000 * yalif1)) + "                            pJ=" + new DecimalFormat("0000Pa").format(pJ)
                    + "\npC1=" + new DecimalFormat("0000Pa").format((int) (1000 * yalif1)) + "                         pC1=" + new DecimalFormat("0000Pa").format(pC1)
                    + "\npS=" + new DecimalFormat("000000Pa").format((int) (1000 * yalif2 + MainActivity.daqiya)) + "                       pS=" + new DecimalFormat("000000Pa").format(pS)
                    + "\nt1=" + new DecimalFormat("00").format(wd1f) + "                                       t1=" + new DecimalFormat("00").format(t1)
                    + "\nt2=" + new DecimalFormat("00").format(wd2f) + "                                       t2=" + new DecimalFormat("00").format(t2)
                    + "\nt3=" + new DecimalFormat("00").format(wd3f) + "                                       t3=" + new DecimalFormat("00").format(t3)
            );
            if (MainActivity.isShow) {
                textViewTest.setVisibility(View.VISIBLE);
            } else textViewTest.setVisibility(View.INVISIBLE);
            ////
        }
    };
    //    private Handler testHandler = new Handler();
    private Thread testRunnable = new Thread(new Runnable() {
        @Override
        public void run() {

        }

    });

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            mainActivity.mBuffer = new byte[5];
            mainActivity.mBuffer[0] = 0x5A;
            mainActivity.mBuffer[1] = (byte) 0xA5;
            mainActivity.mBuffer[2] = 0x02;
            mainActivity.mBuffer[3] = 0x10;
            mainActivity.mBuffer[4] = 0x10;
//            mainActivity.stopSend = false;
//            mainActivity.sendonce(false);
            handler.postDelayed(runnable, 500 * 1);
            isEnd = true;

        } else {
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //kg = 0;
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_test, container, false);
//
//        return view;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_test, container, false);

        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        TextView_YaLi1 = (TextView) (view.findViewById(R.id.textViewYaLi1));
        textViewHQL1 = (TextView) (view.findViewById(R.id.textViewHQL1));
        textViewHQL2 = (TextView) (view.findViewById(R.id.textViewHQL2));
        textViewHQLPJ = (TextView) (view.findViewById(R.id.textViewHQLPJ));
        textViewTest = (TextView) (view.findViewById(R.id.textViewTest));
        textViewInfo = (TextView) (view.findViewById(R.id.textViewInfo));
        TextInputEditTextName = (TextInputEditText) (view.findViewById(R.id.TextInputEditTextname));
        checkBox2ci = (CheckBox) (view.findViewById(R.id.checkBox2ci));
        TextInputEditTextName.setText("1号变压器油");
        TextInputEditTextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditTextName.setCursorVisible(true);
            }
        });
        frameLayout = (FrameLayout) (view.findViewById(R.id.frameLayout));
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                frameLayout.getWindowVisibleDisplayFrame(r);
                int screenHeight = frameLayout.getRootView()
                        .getHeight();
                int heightDifference = screenHeight - (r.bottom);
                if (heightDifference > 200) {
                    //软键盘显示
                    TextInputEditTextName.setCursorVisible(true);
// changeKeyboardHeight(heightDifference);
                } else {
                    //软键盘隐藏
//                    Log.d("qwh888", TextInputEditTextName.getText().toString());
                    if ((getActivity().getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                            == WindowManager.LayoutParams.FLAG_FULLSCREEN) {

                        hideBottomUIMenu();
                        TextInputEditTextName.setCursorVisible(false);
                    }
                }
            }
        });
        //       Log.d("qwh888", TextInputEditTextName.getText().toString());
        textViewTest.setVisibility(View.INVISIBLE);
        testButton = (QMUIRoundButton) view.findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("qwh", testButton.getText().toString());
                switch (testButton.getText().toString()) {
                    case "开始测试":
                        chronometer1.stop();
                        textViewHQL1.setText("__.__%");
                        textViewHQL2.setText("__.__%");
                        textViewHQLPJ.setText("__.__%");
                        chronometer1.setBase(SystemClock.elapsedRealtime());
//2020/01/20                        handler.removeCallbacks(runnable);
//2020/01/20                        handler.post(runnable);
                        isTesting = true;
                        isTingZhi = false;
                        if (isEnd) {
                            testButton.setText("停止测试");
                            isEnd = false;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    test();
                                }
                            }).start();
                        }
                        cxButton.setText("开始冲洗");
//                        testHandler.post(testRunnable);


                        break;
                    case "停止测试":
                        testButton.setText("开始测试");
                        textViewInfo.setText("");
                        isTingZhi = true;
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        kg = 0;
//                        mainActivity.mBuffer = new byte[7];
//                        mainActivity.mBuffer[0] = 0x5A;
//                        mainActivity.mBuffer[1] = (byte) 0xA5;
//                        mainActivity.mBuffer[2] = 0x04;
//                        mainActivity.mBuffer[3] = 0x0A;
//                        mainActivity.mBuffer[4] = (byte) (kg >> 8);
//                        mainActivity.mBuffer[5] = (byte) kg;
//                        mainActivity.mBuffer[6] = (byte) (mainActivity.mBuffer[3] ^ mainActivity.mBuffer[4] ^ mainActivity.mBuffer[5]);
//                        mainActivity.stopSend = false;
//                        mainActivity.sendonce(false);
//2020/01/20                       handler.removeCallbacks(runnable);
//                        testHandler.removeCallbacks(testRunnable);
//                        testRunnable.interrupt();
//                        mainActivity.isRecOK = false;
//                        ResetKG();//复位
//                        while (!mainActivity.isRecOK) {
//                            try {
//                                Thread.sleep(500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }

                        isTesting = false;

                        break;
                    default:
                        break;
                }
            }
        });
        cxButton = (QMUIRoundButton) view.findViewById(R.id.cxButton);
        cxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (cxButton.getText().toString()) {
                    case "开始冲洗":
                        kg = kg | 0x140;//开7
                        cxButton.setText("关闭冲洗");
                        break;
                    case "关闭冲洗":
                        kg = kg & ~0x140;//关7
                        cxButton.setText("开始冲洗");
                        break;

                }
            }
        });
        chronometer1 = (Chronometer) view.findViewById(R.id.chronometer);
        // 建立打开数据库
        db = view.getContext().openOrCreateDatabase("qwh.db", view.getContext().MODE_PRIVATE, null);
//       db.execSQL("DROP TABLE IF EXISTS person");//清空表
        // 创建person表
        db.execSQL("CREATE TABLE if not exists person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, hanqiliang1 VARCHAR,hanqiliang2 VARCHAR,hanqiliangpj VARCHAR,shijian VARCHAR)");

    }


    void test() {

        kg = 0;//复位
        kg = kg | 0x03;//开1+2
        try {

            for (int i = 0; i < 4; i++) {
                if (!isTesting) break;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        kg = kg & ~0x03;//关1+2
        if (isTesting) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        kg = kg | 0x80;//开泵
        try {
            for (int i = 0; i < 2; i++) {
                if (!isTesting) break;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kg = kg | 0x08;//开4
        try {
            for (int i = 0; i < 2; i++) {
                if (!isTesting) break;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kg = kg | 0x32;//开5+6+2
        try {
            for (int i = 0; i < 80; i++) {
                if (!isTesting) break;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kg = kg & ~0x02;//关2
        try {
            for (int i = 0; i < 4; i++) {
                if (!isTesting) break;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kg = kg & ~0x18;//关5+4
        kg = kg | 0x40;//开7
        try {
            for (int i = 0; i < 20; i++) {
                if (!isTesting) break;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kg = kg & ~0x40;//关7
        kg = kg | 0x04;//开3
        while (mainActivity.yali1 * 2.492f / 65536 * 2 > 0.45) {
            try {
                if (!isTesting) break;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        kg = kg & ~0x84;//关3+泵
        if (isTesting) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        kg = kg | 0x100;//开8
        if (isTesting) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        kg = kg & ~0x20;//关6
        try {
            for (int i = 0; i < 120; i++) {
                if (!isTesting) break;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kg = kg | 0x40;//开7
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewInfo.setText("开始进油...");
            }
        });

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chronometer1.setBase(SystemClock.elapsedRealtime());
                chronometer1.start();
            }
        });
        try {
            for (int i = 0; i < 4; i++) {
                if (!isTesting) break;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        second = 0;
        while (second < 76) {
            if (mainActivity.yeti == 0) second = 0;
            if (SystemClock.elapsedRealtime() - chronometer1.getBase() > 180 * 1000) break;
            if (!isTesting) break;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("qwh", String.valueOf(second) + ";" + String.valueOf(SystemClock.elapsedRealtime() - chronometer1.getBase()));
        }
        kg = kg & ~0x140;//关7+8
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewInfo.setText("进油结束,测试中...");
            }
        });
        while (SystemClock.elapsedRealtime() - chronometer1.getBase() < 80 * 1000) {
            if (!isTesting) break;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pC1 = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
        try {
            for (int i = 0; i < 60; i++) {
                if (!isTesting) break;
                Thread.sleep(500);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kg = kg | 0x01;//开1
        try {
            for (int i = 0; i < 10; i++) {
                if (!isTesting) break;
                Thread.sleep(500);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pS = (int) (1000 * mainActivity.yali2 * 2.492f / 65536 / 128 * 15221 + MainActivity.daqiya);
        float vp = 2.492f * mainActivity.wd3 / (4096 * 51) + 2.988f / 21;
        float rp = (float) (2000 * vp) / (2.988f - vp);
        t3 = (float) (rp - 100) / 0.385f;
        kg = kg & ~0x01;//关1
        try {
            for (int i = 0; i < 50; i++) {
                if (!isTesting) break;
                Thread.sleep(500);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pC = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
        vp = 2.492f * mainActivity.wd2 / (4096 * 51) + 2.988f / 21;
        rp = (float) (2000 * vp) / (2.988f - vp);
        t2 = (float) (rp - 100) / 0.385f;
        kg = kg | 0x30;//开5+6
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//            chronometer1.stop();
                chronometer1.setBase(SystemClock.elapsedRealtime());
                chronometer1.stop();
                chronometer1.setBase(SystemClock.elapsedRealtime());
                chronometer1.start();
            }
        });
        try {
            for (int i = 0; i < 4; i++) {
                if (!isTesting) break;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("qwh", String.valueOf(SystemClock.elapsedRealtime() - chronometer1.getBase()));
        while (SystemClock.elapsedRealtime() - chronometer1.getBase() < 240 * 1000) {
            if (!isTesting) break;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        pJ = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
        vp = 2.492f * mainActivity.wd1 / (4096 * 51) + 2.988f / 21;
        rp = (float) (2000 * vp) / (2.988f - vp);
        t1 = (float) (rp - 100) / 0.385f;
//        v1 = 1.6f;
//        v2 = 20.5f;
//        v3 = 97f;
        float v1 = mainActivity.v1;
        float v2 = mainActivity.v2;
        float v3 = mainActivity.v3;
        tTime = 4;
        vY = 0;
        if (isTingZhi == false)
            HQL1 = (273 * ((v1 + v3) * pJ / (273 + t1) - v3 * pC / (273 + t1) - v1 * pS / (273 + t3) - (pC - pC1) * tTime * (v1 + v2 + v3) / (273 + t3))) / (101300 * (v2 + vY) * (1 - 0.0008f * t2));
            HQL1=HQL1/0.8f;
        if (HQL1 < 0) HQL1 = 0;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                textViewHQL1.setText(new DecimalFormat("0.00%").format(HQL1));
                if (isTingZhi == true) textViewHQL1.setText("__.__%");
                chronometer1.setBase(SystemClock.elapsedRealtime());
                chronometer1.stop();
            }
        });
        kg = kg | 0x01;//开1
        try {
            for (int i = 0; i < 8; i++) {
                if (!isTesting) break;
                Thread.sleep(500);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kg = kg & ~0x01;//关1
        kg = kg | 0x8A;//开2+4+泵
        try {
            for (int i = 0; i < 60; i++) {
                if (!isTesting) break;
                Thread.sleep(500);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kg = kg & ~0x08;//关4
        try {
            for (int i = 0; i < 2; i++) {
                if (!isTesting) break;
                Thread.sleep(500);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kg = kg & ~0xB2;//关2+5+6+泵
////////////////////////////////
//        pS = (int) (1000 * mainActivity.yali2 * 2.492f / 65536 / 128 * 15221 + MainActivity.daqiya);
//         vp = 2.492f * mainActivity.wd3 / (4096 * 51) + 2.988f / 21;
//         rp = (float) (2000 * vp) / (2.988f - vp);
//        t3 = (float) (rp - 100) / 0.385f;
//
//        kg = kg | 0x30;//开5+6
//        try {
//            for (int i = 0; i < 6; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        kg = kg & ~0x01;//关1
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        kg = kg | 0x8A;//开2+4+beng
//        try {
//            for (int i = 0; i < 60; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        kg = kg & ~0xB0;//关5+6+泵
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg & ~0x0A;//关4+2
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        kg = kg | 0x84;//开3+泵
//        while (mainActivity.yali1 * 2.492f / 65536 * 2 > 0.5) {
//            try {
//                if (!isTesting) break;
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        kg = kg & ~0x84;//关3+泵
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg | 0x40;//开7
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                textViewInfo.setText("开始进油...");
//            }
//        });
//
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                chronometer1.setBase(SystemClock.elapsedRealtime());
//                chronometer1.start();
//            }
//        });
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        second = 0;
//        while (second < 76) {
//            if (mainActivity.yeti == 0) second = 0;
//            if (SystemClock.elapsedRealtime() - chronometer1.getBase() > 300 * 1000) break;
//            if (!isTesting) break;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.d("qwh", String.valueOf(second) + ";" + String.valueOf(SystemClock.elapsedRealtime() - chronometer1.getBase()));
//        }

//        vp = 2.492f * mainActivity.wd2 / (4096 * 51) + 2.988f / 21;
//        rp = (float) (2000 * vp) / (2.988f - vp);
//        t2 = (float) (rp - 100) / 0.385f;
//        kg = kg & ~0x40;//关7
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                textViewInfo.setText("进油结束,测试中...");
//            }
//        });
//        while (SystemClock.elapsedRealtime() - chronometer1.getBase() < 240 * 1000) {
//            if (!isTesting) break;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        pC1 = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
//        try {
//            for (int i = 0; i < 60; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg | 0x01;//开1
//        try {
//            for (int i = 0; i < 10; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg & ~0x01;//关1
//        try {
//            for (int i = 0; i < 50; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        pC = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
//        kg = kg | 0x30;//开5+6
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////            chronometer1.stop();
//                chronometer1.setBase(SystemClock.elapsedRealtime());
//                chronometer1.stop();
//                chronometer1.setBase(SystemClock.elapsedRealtime());
//                chronometer1.start();
//            }
//        });
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Log.d("qwh", String.valueOf(SystemClock.elapsedRealtime() - chronometer1.getBase()));
//        while (SystemClock.elapsedRealtime() - chronometer1.getBase() < 240 * 1000) {
//            if (!isTesting) break;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//        pJ = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
//        vp = 2.492f * mainActivity.wd1 / (4096 * 51) + 2.988f / 21;
//        rp = (float) (2000 * vp) / (2.988f - vp);
//        t1 = (float) (rp - 100) / 0.385f;
////        v1 = 1.6f;
////        v2 = 20.5f;
////        v3 = 97f;
//         v1 = mainActivity.v1;
//         v2 = mainActivity.v2;
//         v3 = mainActivity.v3;

// //   HQL1=(273*((v1+v3)*pJ/(273+t1)-v3*pC/(273+t1)-v1*pS/(273+t3)-(pC-pC1)*4*(v1+v2+v3)/(273+t3)))/(101300*v2*(1-0.0008f*t2));
//        tTime = 4;
//        vY = 0;
//        if (isTingZhi == false)
//            HQL1 = (273 * ((v1 + v3) * pJ / (273 + t1) - v3 * pC / (273 + t1) - v1 * pS / (273 + t3) - (pC - pC1) * tTime * (v1 + v2 + v3) / (273 + t3))) / (101300 * (v2 + vY) * (1 - 0.0008f * t2));
//
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                textViewHQL1.setText(new DecimalFormat("0.00%").format(HQL1));
//                if (isTingZhi == true) textViewHQL1.setText("__.__%");
//                chronometer1.setBase(SystemClock.elapsedRealtime());
//                chronometer1.stop();
//            }
//        });
//二次
        if (checkBox2ci.isChecked()) {
            kg = 0;//复位
            kg = kg | 0x03;//开1+2
            try {

                for (int i = 0; i < 4; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            kg = kg & ~0x03;//关1+2
            if (isTesting) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            kg = kg | 0x80;//开泵
            try {
                for (int i = 0; i < 2; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kg = kg | 0x08;//开4
            try {
                for (int i = 0; i < 2; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kg = kg | 0x32;//开5+6+2
            try {
                for (int i = 0; i < 80; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kg = kg & ~0x02;//关2
            try {
                for (int i = 0; i < 4; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kg = kg & ~0x18;//关5+4
            kg = kg | 0x40;//开7
            try {
                for (int i = 0; i < 20; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kg = kg & ~0x40;//关7
            kg = kg | 0x04;//开3
            while (mainActivity.yali1 * 2.492f / 65536 * 2 > 0.45) {
                try {
                    if (!isTesting) break;
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            kg = kg & ~0x84;//关3+泵
            if (isTesting) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            kg = kg | 0x100;//开8
            if (isTesting) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            kg = kg & ~0x20;//关6
            try {
                for (int i = 0; i < 120; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kg = kg | 0x40;//开7
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewInfo.setText("开始进油...");
                }
            });

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chronometer1.setBase(SystemClock.elapsedRealtime());
                    chronometer1.start();
                }
            });
            try {
                for (int i = 0; i < 4; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            second = 0;
            while (second < 76) {
                if (mainActivity.yeti == 0) second = 0;
                if (SystemClock.elapsedRealtime() - chronometer1.getBase() > 180 * 1000) break;
                if (!isTesting) break;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("qwh", String.valueOf(second) + ";" + String.valueOf(SystemClock.elapsedRealtime() - chronometer1.getBase()));
            }
            kg = kg & ~0x140;//关7+8
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewInfo.setText("进油结束,测试中...");
                }
            });
            while (SystemClock.elapsedRealtime() - chronometer1.getBase() < 80 * 1000) {
                if (!isTesting) break;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pC1 = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
            try {
                for (int i = 0; i < 60; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kg = kg | 0x01;//开1
            try {
                for (int i = 0; i < 10; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pS = (int) (1000 * mainActivity.yali2 * 2.492f / 65536 / 128 * 15221 + MainActivity.daqiya);
            vp = 2.492f * mainActivity.wd3 / (4096 * 51) + 2.988f / 21;
            rp = (float) (2000 * vp) / (2.988f - vp);
            t3 = (float) (rp - 100) / 0.385f;
            kg = kg & ~0x01;//关1
            try {
                for (int i = 0; i < 50; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pC = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
            vp = 2.492f * mainActivity.wd2 / (4096 * 51) + 2.988f / 21;
            rp = (float) (2000 * vp) / (2.988f - vp);
            t2 = (float) (rp - 100) / 0.385f;
            kg = kg | 0x30;//开5+6
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//            chronometer1.stop();
                    chronometer1.setBase(SystemClock.elapsedRealtime());
                    chronometer1.stop();
                    chronometer1.setBase(SystemClock.elapsedRealtime());
                    chronometer1.start();
                }
            });
            try {
                for (int i = 0; i < 4; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("qwh", String.valueOf(SystemClock.elapsedRealtime() - chronometer1.getBase()));
            while (SystemClock.elapsedRealtime() - chronometer1.getBase() < 240 * 1000) {
                if (!isTesting) break;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            pJ = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
            vp = 2.492f * mainActivity.wd1 / (4096 * 51) + 2.988f / 21;
            rp = (float) (2000 * vp) / (2.988f - vp);
            t1 = (float) (rp - 100) / 0.385f;
//        v1 = 1.6f;
//        v2 = 20.5f;
//        v3 = 97f;
            v1 = mainActivity.v1;
            v2 = mainActivity.v2;
            v3 = mainActivity.v3;
            tTime = 4;
            vY = 0;
            if (isTingZhi == false)
                HQL2 = (273 * ((v1 + v3) * pJ / (273 + t1) - v3 * pC / (273 + t1) - v1 * pS / (273 + t3) - (pC - pC1) * tTime * (v1 + v2 + v3) / (273 + t3))) / (101300 * (v2 + vY) * (1 - 0.0008f * t2));
            HQL2=HQL2/0.8f;
            if (HQL2 < 0) HQL2 = 0;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewHQL2.setText(new DecimalFormat("0.00%").format(HQL2));
                    if (isTingZhi) textViewHQL2.setText("__.__%");
                }
            });
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewHQLPJ.setText(new DecimalFormat("0.00%").format((HQL1 + HQL2) / 2));
                    if (isTingZhi) textViewHQLPJ.setText("__.__%");
                }
            });

            kg = kg | 0x01;//开1
            try {
                for (int i = 0; i < 8; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kg = kg & ~0x01;//关1
            kg = kg | 0x8A;//开2+4+泵
            try {
                for (int i = 0; i < 60; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kg = kg & ~0x08;//关4
            try {
                for (int i = 0; i < 2; i++) {
                    if (!isTesting) break;
                    Thread.sleep(500);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kg = kg & ~0xB2;//关2+5+6+泵
//        try {
//            for (int i = 0; i < 20; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg | 0x01;//开1
//        try {
//            for (int i = 0; i < 6; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        pS = (int) (1000 * mainActivity.yali2 * 2.492f / 65536 / 128 * 15221 + MainActivity.daqiya);
//        vp = 2.492f * mainActivity.wd3 / (4096 * 51) + 2.988f / 21;
//        rp = (float) (2000 * vp) / (2.988f - vp);
//        t3 = (float) (rp - 100) / 0.385f;
//        kg = kg | 0x30;//开5+6
//        try {
//            for (int i = 0; i < 6; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg & ~0x01;//关1
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg | 0x8A;//开2+4+beng
//        try {
//            for (int i = 0; i < 60; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg & ~0xB0;//关5+6+泵
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg & ~0x0A;//关4+2
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg | 0x80;
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        kg = kg | 0x84;//开3+泵
//        while (mainActivity.yali1 * 2.492f / 65536 * 2 > 0.5) {
//            try {
//                if (!isTesting) break;
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        kg = kg & ~0x84;//关3+泵
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg | 0x40;//开7
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    textViewInfo.setText("开始进油...");
//                }
//            });
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                chronometer1.setBase(SystemClock.elapsedRealtime());
//                chronometer1.start();
//            }
//        });
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        second = 0;
//        while (second < 76) {
//            if (mainActivity.yeti == 0) second = 0;
//            if (SystemClock.elapsedRealtime() - chronometer1.getBase() > 300 * 1000) break;
//            if (!isTesting) break;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.d("qwh", String.valueOf(second) + ";" + String.valueOf(SystemClock.elapsedRealtime() - chronometer1.getBase()));
//        }
//
//        vp = 2.492f * mainActivity.wd2 / (4096 * 51) + 2.988f / 21;
//        rp = (float) (2000 * vp) / (2.988f - vp);
//        t2 = (float) (rp - 100) / 0.385f;
//        kg = kg & ~0x40;//关7
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    textViewInfo.setText("进油结束,测试中...");
//                }
//            });
//        while (SystemClock.elapsedRealtime() - chronometer1.getBase() < 240 * 1000) {
//            if (!isTesting) break;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        pC1 = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
//        try {
//            for (int i = 0; i < 60; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg | 0x01;//开1
//        try {
//            for (int i = 0; i < 10; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg & ~0x01;//关1
//        try {
//            for (int i = 0; i < 50; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        pC = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
//        kg = kg | 0x30;//开5+6
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                chronometer1.setBase(SystemClock.elapsedRealtime());
//                chronometer1.stop();
//                chronometer1.setBase(SystemClock.elapsedRealtime());
//                chronometer1.start();
//            }
//        });
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        while (SystemClock.elapsedRealtime() - chronometer1.getBase() < 240 * 1000) {
//            if (!isTesting) break;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        pJ = (int) (1000 * mainActivity.yali1 * 2.492f / 65536 * 2);
//        vp = 2.492f * mainActivity.wd1 / (4096 * 51) + 2.988f / 21;
//        rp = (float) (2000 * vp) / (2.988f - vp);
//        t1 = (float) (rp - 100) / 0.385f;
//
//        tTime = 4;
//        vY = 0;
////    HQL2=(273*((v1+v3)*pJ/(273+t1)-v3*pC/(273+t1)-v1*pS/(273+t3)-(pC-pC1)*4*(v1+v2+v3)/(273+t3)))/(101300*v2*(1-0.0008f*t2));
//        if (isTingZhi == false)
//            HQL2 = (273 * ((v1 + v3) * pJ / (273 + t1) - v3 * pC / (273 + t1) - v1 * pS / (273 + t3) - (pC - pC1) * tTime * (v1 + v2 + v3) / (273 + t3))) / (101300 * (v2 + vY) * (1 - 0.0008f * t2));
//
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                textViewHQL2.setText(new DecimalFormat("0.00%").format(HQL2));
//                if (isTingZhi) textViewHQL2.setText("__.__%");
//            }
//        });
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//
//                textViewHQLPJ.setText(new DecimalFormat("0.00%").format((HQL1 + HQL2) / 2));
//                if (isTingZhi) textViewHQLPJ.setText("__.__%");
//            }
//        });

        }
//        HQL1=0.013f;
//       HQL2=0.015f;
        if (isTingZhi == false) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String name = TextInputEditTextName.getText().toString();
            String hanqiliang1 = new DecimalFormat("0.00%").format(HQL1);
            String hanqiliang2;
            String hanqiliangpj;
            if (checkBox2ci.isChecked()) {
                hanqiliang2 = new DecimalFormat("0.00%").format(HQL2);
                hanqiliangpj = new DecimalFormat("0.00%").format((HQL1 + HQL2) / 2);
            } else {
                hanqiliang2 = "未测";
                hanqiliangpj = "未测";
            }
            String shijian = formatter.format(date.getTime());
            db.execSQL("INSERT INTO person VALUES (NULL, ?, ?,?,?,?)", new Object[]{
                    name, hanqiliang1, hanqiliang2, hanqiliangpj, shijian});
        }
//        kg = kg | 0x01;//开1
//        try {
//            for (int i = 0; i < 6; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg & ~0x01;//关1
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg | 0x8A;//开2+4+beng
//        try {
//            for (int i = 0; i < 60; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg & ~0xB0;//关5+6+泵
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        kg = kg & ~0x0A;//关4+2
//        try {
//            for (int i = 0; i < 4; i++) {
//                if (!isTesting) break;
//                Thread.sleep(500);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        isEnd = true;
//    handler.removeCallbacks(runnable);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                testButton.setText("开始测试");
            }
        });

        isTesting = false;
        kg=0;//2020/01/20
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chronometer1.setBase(SystemClock.elapsedRealtime());
                chronometer1.stop();
            }
        });
        Log.d("qwh", "isEndhere");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewInfo.setText("");
            }
        });
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    public void hideBottomUIMenu() {
        // 隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = getActivity().getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            // for new api versions.
            View decorView = getActivity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
