package com.zh.HQL.activity;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.mjdev.libaums.UsbMassStorageDevice;
import com.github.mjdev.libaums.fs.FileSystem;
import com.github.mjdev.libaums.fs.UsbFile;
import com.github.mjdev.libaums.partition.Partition;
import com.zh.HQL.R;
import com.zh.HQL.adapter.PagerAdapter;
import com.zh.HQL.fragment.DebugFragment;
import com.zh.HQL.fragment.HistoryFragment;
import com.zh.HQL.fragment.TestFragment;
import com.zh.HQL.view.BottomNavigationViewHelper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends SerialPortActivity {
   public static float v1, v2, v3;
    public static int daqiya;
    public static boolean isShow;
    public static int rec_state;
    public byte[] mBuffer;
    SendingThread mSendingThread;
    int sendCount;
    public static boolean stopSend;
    public static boolean once;
    public static boolean isCuiQu;
    public static int yali1, yali2, wd1, wd2, wd3, wd4;
    public static byte yeti;
    public static int suanzhi;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragmentList = new ArrayList<>();
    private PagerAdapter pagerAdapter;
    TextView tvHead;
    private TestFragment testFragment;
    private HistoryFragment historyFragment;
    private DebugFragment debugFragment;
    public boolean isRecOK;
    //自定义U盘读写权限
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    //当前处接U盘列表
    private UsbMassStorageDevice[] storageDevices;
    //当前U盘所在文件目录
    public UsbFile cFolder;
    TextClock textClock;

    @Override
    protected void onResume() {
        super.onResume();
        hideBottomUIMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHead = findViewById(R.id.tv_head);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_view);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        initData();
        initListener();
        hideBottomUIMenu();
        registerUDiskReceiver();
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
         v1 = pref.getFloat("v1", 0.0f);
        v2 = pref.getFloat("v2", 0.0f);
        v3 = pref.getFloat("v3", 0.0f);
        daqiya=pref.getInt("daqiya",0);
        isShow=pref.getBoolean("isShow",false);

        textClock = (TextClock) findViewById(R.id.textClock1);
        textClock.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //获取当前日期
                //单例模式，设计模式的一种  静态方法
                //Calendar c = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            setDate(year, monthOfYear, dayOfMonth);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            setDateTime(year, monthOfYear, dayOfMonth, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mBuffer = new byte[11];
                        mBuffer[0] = 0x5A;
                        mBuffer[1] = (byte) 0xA5;
                        mBuffer[2] = 0x08;
                        mBuffer[3] = 0x23;
                        mBuffer[4] = (byte) (year - 2000);
                        mBuffer[5] = (byte) (monthOfYear + 1);
                        mBuffer[6] = (byte) dayOfMonth;
                        mBuffer[7] = (byte) Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                        mBuffer[8] = (byte) Calendar.getInstance().get(Calendar.MINUTE);
                        mBuffer[9] = 0x00;
                        mBuffer[10] = 0x23;
                        stopSend = false;
                        send();
                        Log.d("qwh", String.valueOf(mBuffer[4]) + " " + String.valueOf(mBuffer[5]) + " " + String.valueOf(mBuffer[6]) + " " + String.valueOf(mBuffer[7]) + " " + String.valueOf(mBuffer[8]));
                        Toast.makeText(MainActivity.this, year + "-" + (monthOfYear + 1) + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();

                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));


                //点击其他部分不消失
                dpd.setCancelable(false);
                dpd.show();

                TimePickerDialog tp = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        try {
                            setTime(hourOfDay, minute);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();


                    }
                }, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), true);
                tp.setCancelable(false);
                tp.show();
                return false;
            }
        });
        mBuffer = new byte[5];
        mBuffer[0] = 0x5A;
        mBuffer[1] = (byte) 0xA5;
        mBuffer[2] = 0x02;
        mBuffer[3] = 0x22;
        mBuffer[4] = 0x22;
        stopSend = false;
        send();
    }

    private void initData() {
        fragmentList.add(testFragment = new TestFragment());
        fragmentList.add(historyFragment = new HistoryFragment());
        fragmentList.add(debugFragment = new DebugFragment());
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(0);//viewPager默认显示第一页
        bottomNavigationView.getMenu().getItem(0).setChecked(true);//底部按钮默认选中第一个
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                switch (position) {

                    case 0:
                        tvHead.setText("测试");
                        break;
                    case 1:
                        tvHead.setText("历史记录");
                        break;
                    case 2:
                        tvHead.setText("调试");
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_lib:
                        viewPager.setCurrentItem(0);
                        tvHead.setText("测试");
                        break;
                    case R.id.item_find:
                        viewPager.setCurrentItem(1);
                        tvHead.setText("历史记录");
                        break;
                    case R.id.item_more:
                        viewPager.setCurrentItem(2);
                        tvHead.setText("调试");
                        break;
                }
                return false;
            }
        });
    }

    class SendingThread extends Thread {

        @Override
        public void run() {
            // while (!isInterrupted()) {
            while (!stopSend) {
                try {
                    if (mOutputStream != null) {
                        mOutputStream.write(mBuffer);
                        if (once)
                            stopSend = true;
                        sleep(1000);
                        sendCount++;
                        if (once)
                            stopSend = true;
                        if (sendCount == 10) {
                            sendCount = 0;
                            stopSend = true;
                        }

                    } else {
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }

            }
            // }
        }

        public void setStopSend(boolean stopsend) {
            stopSend = stopsend;
        }

    }

    @Override
    protected void onDataReceived(byte[] buffer, int size) {
        if ((buffer[0] == 0x5A) && (buffer[1] == (byte) 0xA5) && (buffer[2] == size - 3)) {
//			 Log.d("qwh", String.valueOf(buffer[0]) + "," + String.valueOf(buffer[1]));
            switch (buffer[3]) {
                case 0x01:

                    rec_state = 0x01;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x02:

                    rec_state = 0x02;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x03:
                    mBuffer = new byte[5];
                    mBuffer[0] = 0x5A;
                    mBuffer[1] = (byte) 0xA5;
                    mBuffer[2] = 0x02;
                    mBuffer[3] = 0x03;
                    mBuffer[4] = 0x03;
                    stopSend = false;
                    sendonce(true);
                    rec_state = 0x03;
                    break;
                case 0x04:
                    mBuffer = new byte[5];
                    mBuffer[0] = 0x5A;
                    mBuffer[1] = (byte) 0xA5;
                    mBuffer[2] = 0x02;
                    mBuffer[3] = 0x04;
                    mBuffer[4] = 0x04;
                    stopSend = false;
                    sendonce(true);

                    rec_state = 0x04;
                    break;
                case 0x05:
                    rec_state = 0x05;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x06:
                    rec_state = 0x06;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x07:
                    rec_state = 0x07;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x08:
                    rec_state = 0x08;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x09:
                    rec_state = 0x09;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x0A:
                    rec_state = 0x0A;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
//                    isRecOK = true;
                    break;
                case 0x0B:
                    rec_state = 0x0B;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x0C:
                    rec_state = 0x0C;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x0D:
                    rec_state = 0x0D;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x0E:
                    rec_state = 0x0E;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x0F:
                    rec_state = 0x0F;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x10:

                    yali1 = (buffer[4] & 0xFF) * 256 + (buffer[5] & 0xFF);
                    yali2 = (buffer[6] & 0xFF) * 256 + (buffer[7] & 0xFF);
                    wd1 = (buffer[8] & 0xFF) * 256 + (buffer[9] & 0xFF);
                    wd2 = (buffer[10] & 0xFF) * 256 + (buffer[11] & 0xFF);
                    wd3 = (buffer[12] & 0xFF) * 256 + (buffer[13] & 0xFF);
                    wd4 = (buffer[14] & 0xFF) * 256 + (buffer[15] & 0xFF);
                    yeti = (byte) (buffer[16] & 0xFF);
//			Log.d("qwh", String.valueOf(buffer[16])+" "+String.valueOf(buffer[16])+" "+String.valueOf(buffer[16])+" "+String.valueOf(buffer[16]));
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    if (debugFragment != null) {
                        if (debugFragment.isVisible()) debugFragment.update();
                    }
                    break;
                case 0x11:
                    rec_state = 0x11;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
//					Log.d("qwh", String.valueOf(guangqiang));
                    break;
                case 0x12:
                    rec_state = 0x12;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x14:
                    rec_state = 0x14;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x15:
                    rec_state = 0x15;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x16:
                    rec_state = 0x16;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                case 0x17:
                    mBuffer = new byte[5];
                    mBuffer[0] = 0x5A;
                    mBuffer[1] = (byte) 0xA5;
                    mBuffer[2] = 0x02;
                    mBuffer[3] = 0x17;
                    mBuffer[4] = 0x17;
                    stopSend = false;
                    sendonce(true);
                    rec_state = 0x17;
                    isCuiQu = true;
                    break;
                case 0x1A:
                    suanzhi = (buffer[4] & 0xFF) * 256 * 256 * 256 + (buffer[5] & 0xFF) * 256 * 256 + (buffer[6] & 0xFF) * 256 + (buffer[7] & 0xFF);
//					MainActivity.yangpinhao=2;
                    mBuffer = new byte[5];
                    mBuffer[0] = 0x5A;
                    mBuffer[1] = (byte) 0xA5;
                    mBuffer[2] = 0x02;
                    mBuffer[3] = 0x1A;
                    mBuffer[4] = 0x1A;
                    stopSend = false;
                    sendonce(true);
                    rec_state = 0x1A;
                    break;
                case 0x1B:
                    suanzhi = (buffer[4] & 0xFF) * 256 * 256 * 256 + (buffer[5] & 0xFF) * 256 * 256 + (buffer[6] & 0xFF) * 256 + (buffer[7] & 0xFF);
//					MainActivity.yangpinhao=3;
                    mBuffer = new byte[5];
                    mBuffer[0] = 0x5A;
                    mBuffer[1] = (byte) 0xA5;
                    mBuffer[2] = 0x02;
                    mBuffer[3] = 0x1B;
                    mBuffer[4] = 0x1B;
                    stopSend = false;
                    sendonce(true);
                    rec_state = 0x1B;
                    break;
                case 0x1C:
                    suanzhi = (buffer[4] & 0xFF) * 256 * 256 * 256 + (buffer[5] & 0xFF) * 256 * 256 + (buffer[6] & 0xFF) * 256 + (buffer[7] & 0xFF);
//					MainActivity.yangpinhao=4;
                    mBuffer = new byte[5];
                    mBuffer[0] = 0x5A;
                    mBuffer[1] = (byte) 0xA5;
                    mBuffer[2] = 0x02;
                    mBuffer[3] = 0x1C;
                    mBuffer[4] = 0x1C;
                    stopSend = false;
                    sendonce(true);
                    rec_state = 0x1C;
                    break;
                case 0x22:
                    rec_state = 0x22;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
//--2020-03-30--
//                    try {
//                        Log.d("qwh", String.valueOf((int) buffer[4]) + " " + String.valueOf(buffer[5]) + " " + String.valueOf(buffer[6]) + " " + String.valueOf(buffer[7]) + " " + String.valueOf(buffer[8]) + " " + String.valueOf(buffer[9]) + " ");
//                        setDateTime(buffer[4] + 2000, buffer[5], buffer[6], buffer[7], buffer[8], buffer[9]);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    break;
                case 0x23:
                    rec_state = 0x23;
                    if (mSendingThread != null)
                        mSendingThread.setStopSend(true);
                    break;
                default:
                    break;
            }

        }
    }

    public void send() {
        once = false;
        if (mSerialPort != null) {
            mSendingThread = new SendingThread();
            sendCount = 0;
            mSendingThread.start();

        }
    }

    public void sendonce(boolean yici) {
        once = yici;
        if (mSerialPort != null) {
            mSendingThread = new SendingThread();
            mSendingThread.start();

        }
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

    /**
     * @description OTG广播注册
     * @author ldm
     * @time 2017/9/1 17:19
     */
    private void registerUDiskReceiver() {
        //监听otg插入 拔出
        IntentFilter usbDeviceStateFilter = new IntentFilter();
        usbDeviceStateFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        usbDeviceStateFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(mOtgReceiver, usbDeviceStateFilter);
        //注册监听自定义广播
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mOtgReceiver, filter);
    }

    /**
     * @description OTG广播，监听U盘的插入及拔出
     * @author ldm
     * @time 2017/9/1 17:20
     * @param
     */
    private BroadcastReceiver mOtgReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_USB_PERMISSION://接受到自定义广播
                    UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    //允许权限申请
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (usbDevice != null) {
                            //用户已授权，可以进行读取操作
                            readDevice(getUsbMass(usbDevice));
                        } else {
                            showToastMsg("没有插入U盘");
                        }
                    } else {
                        showToastMsg("未获取到U盘权限");
                    }
                    break;
                case UsbManager.ACTION_USB_DEVICE_ATTACHED://接收到U盘设备插入广播
                    UsbDevice device_add = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (device_add != null) {
                        //接收到U盘插入广播，尝试读取U盘设备数据
                        redUDiskDevsList();
                    }
                    break;
                case UsbManager.ACTION_USB_DEVICE_DETACHED://接收到U盘设设备拔出广播
                    showToastMsg("U盘已拔出");
                    break;
            }
        }
    };

    /**
     * @description U盘设备读取
     * @author ldm
     * @time 2017/9/1 17:20
     */
    private void redUDiskDevsList() {
        //设备管理器
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        //获取U盘存储设备
        storageDevices = UsbMassStorageDevice.getMassStorageDevices(this);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        //一般手机只有1个OTG插口
        for (UsbMassStorageDevice device : storageDevices) {
            //读取设备是否有权限
            //       	Log.d("qwh", device.toString());
            if (usbManager.hasPermission(device.getUsbDevice())) {
                readDevice(device);
            } else {
                //没有权限，进行申请
                usbManager.requestPermission(device.getUsbDevice(), pendingIntent);
                readDevice(device);
            }
        }
        if (storageDevices.length == 0) {
            showToastMsg("请插入可用的U盘");
        }
    }


    private UsbMassStorageDevice getUsbMass(UsbDevice usbDevice) {
        for (UsbMassStorageDevice device : storageDevices) {
            if (usbDevice.equals(device.getUsbDevice())) {
                return device;
            }
        }
        return null;
    }

    private void readDevice(UsbMassStorageDevice device) {
        try {
            device.init();//初始化
            //设备分区
            Partition partition = device.getPartitions().get(0);
            //文件系统
            FileSystem currentFs = partition.getFileSystem();
            currentFs.getVolumeLabel();//可以获取到设备的标识
            //通过FileSystem可以获取当前U盘的一些存储信息，包括剩余空间大小，容量等等
            Log.e("Capacity: ", currentFs.getCapacity() + "");
            Log.e("Occupied Space: ", currentFs.getOccupiedSpace() + "");
            Log.e("Free Space: ", currentFs.getFreeSpace() + "");
            Log.e("Chunk size: ", currentFs.getChunkSize() + "");
            cFolder = currentFs.getRootDirectory();//设置当前文件对象为根目录
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void setDate(int year, int month, int day) throws IOException, InterruptedException {

        requestPermission();

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }

        long now = Calendar.getInstance().getTimeInMillis();
        // Log.d(TAG, "set tm="+when + ", now tm="+now);

        if (now - when > 1000)
            throw new IOException("failed to set Date.");
    }

    void requestPermission() throws InterruptedException, IOException {
        createSuProcess("chmod 666 /dev/alarm").waitFor();
    }

    Process createSuProcess() throws IOException {
        File rootUser = new File("/system/xbin/ru");
        if (rootUser.exists()) {
            return Runtime.getRuntime().exec(rootUser.getAbsolutePath());
        } else {
            return Runtime.getRuntime().exec("su");
        }
    }

    Process createSuProcess(String cmd) throws IOException {

        DataOutputStream os = null;
        Process process = createSuProcess();

        try {
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit $?\n");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }

        return process;
    }

    public void setTime(int hour, int minute) throws IOException, InterruptedException {

        requestPermission();

        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }

        long now = Calendar.getInstance().getTimeInMillis();
        // Log.d(TAG, "set tm="+when + ", now tm="+now);

        if (now - when > 1000)
            throw new IOException("failed to set Time.");
    }

    public void setDateTime(int year, int month, int day, int hour, int minute, int second)
            throws IOException, InterruptedException {

        requestPermission();

        Calendar c = Calendar.getInstance();
//       c.set(year,month,day,hour,minute,second);

        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.YEAR, year);

        long when;
        when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);

        }
        String str = String.format("%tF %<tT", when);
        long now = Calendar.getInstance().getTimeInMillis();
        Log.d("qwh", str + "set tm=" + when + ", now tm=" + now);

        if (now - when > 1000)
            throw new IOException("failed to set Date.");

    }
}
