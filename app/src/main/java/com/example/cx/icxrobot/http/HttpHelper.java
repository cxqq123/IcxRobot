package com.example.cx.icxrobot.http;

import android.util.Log;

import com.example.cx.icxrobot.model.ModelChatMsg;
import com.example.cx.icxrobot.model.ModelResult;
import com.example.cx.icxrobot.util.Constancts;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cx
 * on 2018/8/27
 * http 封装类
 */
public class HttpHelper {

    private static final String TAG = "HttpHelper";
    private static HttpHelper instance;

    public static HttpHelper getInstance(){
        synchronized (HttpHelper.class){
            if(instance == null){
                instance = new HttpHelper();
            }
            return instance;
        }
    }

    public void postData(List<NameAndValue> params){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        for(NameAndValue nameAndValue : params){
            formBody.add(nameAndValue.getName() , nameAndValue.getValue());
        }
        Request request = new Request.Builder()
                .url(Constancts.ROBOT_URL)
                .post(formBody.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("cx:" + TAG , response.body().string());
            }
        });

    }


    public Observable<ModelChatMsg> postToServer(final List<NameAndValue> params){
        return Observable.create(new ObservableOnSubscribe<ModelChatMsg>() {
            @Override
            public void subscribe(final ObservableEmitter<ModelChatMsg> emitter) throws Exception {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();
                for(NameAndValue nameAndValue : params){
                    formBody.add(nameAndValue.getName() , nameAndValue.getValue());
                }
                final Request request = new Request.Builder()
                        .url(Constancts.ROBOT_URL)
                        .post(formBody.build())
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String content = response.body().string();
                        Log.e("cx:" + TAG , content);
                        Gson gson = new Gson();
                        ModelResult modelResult = gson.fromJson(content , ModelResult.class);
                        ModelChatMsg modelChatMsg = new ModelChatMsg();
                        modelChatMsg.setMyInfo(false);
                        modelChatMsg.setContent(modelResult.text);
                        emitter.onNext(modelChatMsg);
                    }
                });
            }
        });
    }

}
