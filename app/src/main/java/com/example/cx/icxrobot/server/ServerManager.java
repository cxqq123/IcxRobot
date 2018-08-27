package com.example.cx.icxrobot.server;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.cx.icxrobot.util.Constancts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by cx on 2017/11/8.
 */

public class ServerManager extends Thread {

    private static final String IP = "119.29.188.137";
    private static final int POST = 30001;
    private Socket socket;
    private String username;
    private int iconID;
    private String message = null;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ReceiveChatMsg receiveChatMsg;
    private static final ServerManager serverManager = new ServerManager();
    public static ServerManager getServerManager() {
        return serverManager;
    }

    private static Context mContext;

    public static void setContext(Context context){
        mContext = context;
    }
    private ServerManager() {
        receiveChatMsg = new ReceiveChatMsg();
    }

    public void run() {
        try {
            socket = new Socket(IP,POST);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));

            String m = null;
            String line;
            while((line = bufferedReader.readLine()) != null){
                if(!line.equals("-1")){
                    m += line;
                }else{
                    Log.i("cx","receive:" + m);
                    //解析接收到的消息
                    if(ParaseData.getAction(m).equals("GETCHATMSG")){
                        Intent intent = new Intent();
                        intent.setAction(Constancts.READ_CHAT_MESSAGE);
                        intent.putExtra(Constancts.READ_CHAT_MESSAGE , m);
                        Log.e("cx" , "收到服务器传来的消息，开始发送广播 :" + m);
                        mContext.sendBroadcast(intent);
//                        receiveChatMsg.delChatMsg(m);
                    }else {
                        message = m;
                    }
                    m = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
                if(bufferedReader != null){
                    bufferedReader.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //发送消息
    public void sendMessage(Context context , final String msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket == null);
                        if(bufferedWriter != null){
                        Log.i("cx","send :" + msg);
                        try {
                            bufferedWriter.write(msg + "\n");
                            bufferedWriter.flush();
                            bufferedWriter.write("-1\n");
                            bufferedWriter.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }).start();
    }

    //获取消息
    public String getMessage(){
        for(int i = 0; i < 5; i++){
            if(message != null){
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
}
