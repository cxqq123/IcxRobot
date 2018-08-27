package com.example.cx.icxrobot.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cx.icxrobot.R;
import com.example.cx.icxrobot.adapter.AdapterMessage;
import com.example.cx.icxrobot.adapter.AdapterPagePop;
import com.example.cx.icxrobot.model.ModelUser;
import com.example.cx.icxrobot.util.Constancts;
import com.example.cx.icxrobot.util.Utils;
import com.scwang.smartrefresh.header.FunGameHitBlockHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener{


    private ListView lvMessage;
    private RelativeLayout rlMessageAdd; //头部右边的添加按钮
    private RelativeLayout rlMainHeader; //整个头部布局

    private PopupWindow popWindow; //弹出菜单
    private ListView lvPop; //弹出菜单的listView

    private AdapterMessage adapterMessage;
    private Context mContext;
    private List<ModelUser> users = new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayoutMessage;


    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_message);
        mContext = MessageActivity.this;
        bindData();
        initView();
        setListener();
    }

    public void initView(){
        smartRefreshLayoutMessage = (SmartRefreshLayout) findViewById(R.id.smart_refresh_layout_message);
        smartRefreshLayoutMessage.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }
        });
        smartRefreshLayoutMessage.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }
        });

        smartRefreshLayoutMessage.setRefreshHeader(new FunGameHitBlockHeader(mContext));
        lvMessage = (ListView) findViewById(R.id.lv_message);
        rlMessageAdd = (RelativeLayout) findViewById(R.id.rl_message_add);
        rlMainHeader = (RelativeLayout) findViewById(R.id.rl_main_header);

        //添加头布局（搜索）
        lvMessage.addHeaderView(getLayoutInflater().inflate(R.layout.layout_message_header,null));
        adapterMessage =new AdapterMessage(mContext,users);
        lvMessage.setAdapter(adapterMessage);
        lvMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==0){
                    return ;
                }else{
                    Intent intent =new Intent(mContext,TalkActivity.class);
                    int positionN = position - 1;
                    intent.putExtra(Constancts.USER_NAME , users.get(positionN).name);
                    startActivity(intent);
                }
            }
        });
    }

    public void setListener(){
        rlMessageAdd.setOnClickListener(this);
    }


    public void bindData(){

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.icx_apple);

        ModelUser modelUser = new ModelUser();
        modelUser.name = "图灵机器人";
        modelUser.icon = bitmap;
        modelUser.date = Utils.Date2Str(System.currentTimeMillis());
        modelUser.message = "大家好，我是图灵机器人";
        users.add(modelUser);

        ModelUser modelUser2 = new ModelUser();
        modelUser2.name = "管理员";
        modelUser2.icon = bitmap;
        modelUser2.date = Utils.Date2Str(System.currentTimeMillis());
        modelUser2.message = "我是cx羽管理员";
        users.add(modelUser2);

//        List<String> data = ParaseData.getAllGroupList(mContext);
//        for(String str : data){
//            Log.e("cx" , "getAllGroupList : " + str);
//        }
//        List<String> friends = ParaseData.getFriendList(mContext,"Tony");
//        for(String str : friends){
//            //获取好友
//            Log.e("cx" , "Tony 的好友" + str);
//            ModelUser modelUser = new ModelUser();
//            modelUser.name = str;
//            modelUser.icon = bitmap;
//            modelUser.date = Utils.Date2Str(System.currentTimeMillis());
//            modelUser.message = ParaseData.getFriendProfile(mContext,str)[1];
//            users.add(modelUser);
//        }
//        List<String> groups = ParaseData.getGroupList(mContext,"Tony");
//        for(String str : groups){
//            Log.e("cx" , "Tony getGroupList " + str);
//        }
//
//        String[] profiles = ParaseData.getFriendProfile(mContext,"Tony");
//        for(int i = 0 ; i < profiles.length ; i++){
//            Log.e("cx" , "Tony getFriendProfile : " + profiles[i]);
//        }

    }

//    //初始化数据
//    public List<ModelUser> initList(){
//        return users;
//    }


    /**
     * 显示弹出菜单
     */
    public void showPopupWindow(List<String> data, View v){
        int width = getWindowManager().getDefaultDisplay().getWidth();
        if(popWindow == null || lvPop == null){
            View popView = View.inflate(mContext, R.layout.layout_page_page, null);
            lvPop = (ListView)popView.findViewById(R.id.lvPagePop);
            popWindow = new PopupWindow(popView, 2 * width/5, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        AdapterPagePop circlePop = new AdapterPagePop(mContext, data);
        lvPop.setAdapter(circlePop);
        //设置adapter之后获取listView的高度

        //设置点击弹出菜单后,窗体背景的透明度
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.7f;
//        getWindow().setAttributes(lp);

        popWindow.setFocusable(true);
//        popWindow.setOutsideTouchable(false);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.showAsDropDown(v, v.getWidth(), 0); //显示的位置

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });

        lvPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popWindow.dismiss();
                switch(position){
                    case 0://设置
                        goToSetting();
                        break;
                    case 1://扫码
                        Toast.makeText(mContext, "目前暂无扫码功能" , Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    public void goToSetting(){
        Intent intent = new Intent(mContext , SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_message_add:
                //显示PopWindow
                List<String> data = new ArrayList<>();
                data.add("设置");
//                data.add("个人中心");
                data.add("扫一扫");
                data.add("加好友");
                showPopupWindow(data,rlMainHeader);
                break;
        }
    }
}
