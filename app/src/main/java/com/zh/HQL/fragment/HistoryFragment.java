package com.zh.HQL.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zh.HQL.R;
import com.zh.HQL.activity.MainActivity;
import com.zh.HQL.activity.MyAdapter;
import com.zh.HQL.activity.Order;
import com.zh.HQL.activity.RecyclerViewExt;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HistoryFragment extends ViewPagerFragment {
    private RecyclerViewExt mRecyclerView;
    //    private ArrayList<String> mDatas = new ArrayList<String>();
    private ArrayList<Order> mDatas = new ArrayList<Order>();
    private MyAdapter mAdapter;
    private static int a;
    private Handler handler = new Handler();
    SQLiteDatabase db;
    MainActivity mainActivity;
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            Cursor cursor = db.rawQuery("select * from person", null);
//            if(cursor.getCount() <16)
            {
                mAdapter.removeAllItem();
                a=0;
                mAdapter.addAll(get16Orders(a++));
                mRecyclerView.setLoadingMore(false);
            }


        } else {


        }
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        a = 0;
        mainActivity = (MainActivity) getActivity();
        mRecyclerView = (RecyclerViewExt) view.findViewById(R.id.rv);
        // 设置线性布局管理器
        final LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        // 建立打开数据库
        db = view.getContext().openOrCreateDatabase("qwh.db", view.getContext().MODE_PRIVATE, null);
//        db.execSQL("DROP TABLE IF EXISTS person");
//      db.execSQL("DROP TABLE IF EXISTS person");//清空表
        // 创建person表
        db.execSQL("CREATE TABLE if not exists person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, hanqiliang1 VARCHAR,hanqiliang2 VARCHAR,hanqiliangpj VARCHAR,shijian VARCHAR)");

        // 初始化数据
//        for (int j = 0; j < 20; j++) {
//            mDatas.add("data item " + a++);
//        }
//         插入数据
/*
        for (int k = 0; k < 100; k++) {
//	    			           int i=new Random().nextInt(100);
            String name = "变压器油" + k+"号";
            float f=new Random().nextFloat()/10;
            String hanqiliang1 = new DecimalFormat("0.0%").format( f);
//            Log.d("qwh",String.valueOf(new Random().nextFloat()));
            String hanqiliang2= new DecimalFormat("0.0%").format(f );
            String hanqiliangpj= new DecimalFormat("0.0%").format(f);
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String shijian = formatter.format(date.getTime());
//	    				        String string=DateFormat.getInstance().format(date.getTime());
            db.execSQL("INSERT INTO person VALUES (NULL, ?, ?,?,?,?)", new Object[]{
                    name, hanqiliang1,hanqiliang2,hanqiliangpj,shijian});
        }
*/
        // 初始化头部和尾部view
//        View header = LayoutInflater.from(this).inflate(R.layout.header, new LinearLayout(this));
//        View footer = LayoutInflater.from(this).inflate(R.layout.footer, new LinearLayout(this));

        // 为头部添加点击事件
//        header.setClickable(true);
//        header.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "header" , Toast.LENGTH_SHORT).show();
//
//            }
//        });
        mAdapter = new MyAdapter(view.getContext(), mDatas);
        mAdapter.addAll(get16Orders(a++));

        // 添加头部和尾部view
//        mRecyclerView.addHeaderView(header);
//        mRecyclerView.addFooterView(footer);
        mRecyclerView.setAdapter(mAdapter);


        // 监听item点击事件
        mRecyclerView.setOnItemClickListener(new RecyclerViewExt.OnItemClickListener() {

//            @Override
//            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
//                Toast.makeText(MainActivity.this, "onItemClick pos: " + position, Toast.LENGTH_SHORT).show();
//            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh, final int position) {
                Toast.makeText(view.getContext(), "点击的ID: " + (position + 1), Toast.LENGTH_SHORT).show();
//               hideBottomUIMenu(view);
                // 实例化一个弹出框
                new AlertDialog.Builder(view.getContext())
                        .setTitle("选择操作")
                        .setItems(new String[]{ "删除此条记录", "清空全部记录", "全部记录导出到U盘", "取消操作"},
                                // 为弹出框上的选项添加事件
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        switch (which) {
                                                                                        // 删除记录
                                            case 0:
                                                // getItemAtPosition()得到一个item里的数据
                                                Cursor cursor1 = db.rawQuery("select * from person", null);

                                                cursor1.moveToPosition(position);
                                                final int _id = cursor1.getInt(cursor1
                                                        .getColumnIndex("_id"));
//	                                            String name = s.getString(s
//	                                                    .getColumnIndex("caozuoyuan"));
                                                Log.i("id ::", _id + "");
                                                new AlertDialog.Builder(
                                                        view.getContext())
                                                        .setTitle(
                                                                "确定删除" + " ID=" + String.valueOf(_id)
                                                                        + " 这条记录吗？")
                                                        .setPositiveButton(
                                                                "确定",
                                                                new DialogInterface.OnClickListener() {

                                                                    @Override
                                                                    public void onClick(
                                                                            DialogInterface dialog,
                                                                            int which) {
                                                                        db.execSQL(
                                                                                "delete from person where _id =?",
                                                                                new Integer[]{_id});
                                                                        mAdapter.removeItem(position);
//                                                                       updatelistview();
                                                                        // 重置加载状态
                                                                        mRecyclerView.setLoadingMore(false);
                                                                    }
                                                                })
                                                        .setNegativeButton(
                                                                "取消",
                                                                new DialogInterface.OnClickListener() {

                                                                    @Override
                                                                    public void onClick(
                                                                            DialogInterface dialog,
                                                                            int which) {
                                                                    }
                                                                }).show();
                                                hideBottomUIMenu(view);
                                                break;
                                            // 清空操作
                                            case 1:
                                                new AlertDialog.Builder(
                                                        view.getContext())
                                                        .setTitle(
                                                                "确定清空所有记录吗？")
                                                        .setPositiveButton(
                                                                "确定",
                                                                new DialogInterface.OnClickListener() {

                                                                    @Override
                                                                    public void onClick(
                                                                            DialogInterface dialog,
                                                                            int which) {
                                                                        mAdapter.removeAllItem();
                                                                        // 重置加载状态
                                                                        mRecyclerView.setLoadingMore(false);
//	                                                                    db.execSQL( "delete  from person ");
                                                                        db.execSQL("DROP TABLE IF EXISTS person");
                                                                        // 创建person表
                                                                        db.execSQL("CREATE TABLE if not exists person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, haoqiliang1 VARCHAR,hanqiliang2 VARCHAR,hanqiliangpj VARCHAR,shijian VARCHAR)");
//                                                                        updatelistview();


                                                                    }
                                                                })
                                                        .setNegativeButton(
                                                                "取消",
                                                                new DialogInterface.OnClickListener() {

                                                                    @Override
                                                                    public void onClick( DialogInterface dialog, int which) {

                                                                    }
                                                                }).show();
                                                hideBottomUIMenu(view);
                                                break;
                                            case 2:
                                                Date date = new Date();
                                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
                                                String shijian1 = formatter.format(date.getTime());
                                                List<Order> orders = new ArrayList<Order>();
                                                orders = getAllOrders();
                                                try {
                                                    ExcelUtil.writeExcel(view.getContext(),
                                                            //			orders, "excel_"+new Date().toString());

                                                            orders, "含气量excel_" + shijian1);
                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }
                                                File file;
                                                File dir = new File(view.getContext().getExternalFilesDir(null).getPath());
                                                file = new File(dir, "含气量excel_" + shijian1 + ".xls");
                                                if (null != mainActivity.cFolder) {
                                                    FileUtil.saveSDFile2OTG(file, mainActivity.cFolder);
                                                    Toast.makeText(view.getContext(), "写U盘成功!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(view.getContext(), "未检测到U盘!请拔插U盘!!", Toast.LENGTH_SHORT).show();
                                                }
                                                hideBottomUIMenu(view);
                                                break;
                                            // 取消操作
                                            case 3:
                                                hideBottomUIMenu(view);
                                                break;
                                        }
                                    }
                                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        hideBottomUIMenu(view);
                    }
                }).show();

                //               return false;

            }
        });

        // 监听加载更多
        mRecyclerView.setOnLoadMoreListener(new RecyclerViewExt.OnLoadMoreListener() {

            @Override
            public void onLoadMore(int begin) {

                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        List<Order> datas = new ArrayList<Order>();
//                        for (int i = 0; i < 10; i++) {
//
//                            datas.add("More Data_" +a++);
//                        }

                        datas = get16Orders(a++);
                        mAdapter.addAll(datas);
                        // 重置加载状态
                        mRecyclerView.setLoadingMore(false);

                    }
                }, 500);
            }
        });

        /*
        new Thread() {
            public void run() {
                //mText.setText("This is Update from ohter thread, Mouse DOWN");

                //UPDATE是一个自己定义的整数，代表了消息ID
                for (int k = 16; k < 16 * 10; k++) {
//	    			           int i=new Random().nextInt(100);
                    String name = "变压器油" + k+"号";
                    float f=new Random().nextFloat()/10;
                    String hanqiliang1 = new DecimalFormat("0.0%").format(f);
                    String hanqiliang2= new DecimalFormat("0.0%").format(f);
                    String hanqiliangpj= new DecimalFormat("0.0%").format(f);
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String shijian = formatter.format(date.getTime());
//	    				        String string=DateFormat.getInstance().format(date.getTime());
                    db.execSQL("INSERT INTO person VALUES (NULL, ?, ?,?,?,?)", new Object[]{
                            name, hanqiliang1,hanqiliang2,hanqiliangpj,shijian});
                }
            }
        }.start();
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
 //       View view = inflater.inflate(R.layout.fragment_history, container, false);
 //       return view;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_history, container, false);

        }
        return rootView;
    }

    public List<Order> getAllOrders() {
        String sql = "select * from person";

        List<Order> orderList = new ArrayList<Order>();
        Order order = null;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String hanqilang1 = cursor.getString(2);
            String hanqiliang2 = cursor.getString(3);
            String hanqiliangpj = cursor.getString(4);
            String shijian = cursor.getString(5);
            order = new Order(id, name, hanqilang1, hanqiliang2, hanqiliangpj,shijian);
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> get16Orders(int i) {
        String sql = "select * from person";

        List<Order> orderList = new ArrayList<Order>();
        Order order = null;
        Cursor cursor = db.rawQuery(sql, null);
        int totle=cursor.getCount();
        Log.d("qwh",String.valueOf(i)+"here877");
        cursor.moveToPosition(totle-1-i * 16);
        for (int j =totle-1- i * 16; j > totle-1-16 * (i + 1); j--) {

            if (cursor.moveToPosition(j)) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String hanqilang1 = cursor.getString(2);
                String hanqiliang2 = cursor.getString(3);
                String hanqiliangpj = cursor.getString(4);
                String shijian = cursor.getString(5);
                order = new Order(id, name, hanqilang1, hanqiliang2, hanqiliangpj,shijian);
                orderList.add(order);
            }
        }
        return orderList;
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu(View v) {
        // 隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            // for new api versions.
//            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            v.setSystemUiVisibility(uiOptions);
        }
    }
}
