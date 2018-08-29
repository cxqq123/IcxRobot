package com.example.cx.icxrobot.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cx.icxrobot.R;
import com.example.cx.icxrobot.model.ModelChatMsg;
import com.example.cx.icxrobot.view.CircleImageDrawable;

import java.util.List;

public class AdapterChatMsg extends BaseAdapter{

    private List<ModelChatMsg> modelChatMsgs;
    private Context mContext;

    //自己本人说的话
    private static final int TYPE_ME_MESSAGE = 0;
    //服务器返回的话
    private static final int TYPE_SERVER_MESSAGE = 1;

    public AdapterChatMsg(Context context, List<ModelChatMsg> modelChatMsgs) {
        this.mContext = context;
        this.modelChatMsgs = modelChatMsgs;
    }

    public void setData(List<ModelChatMsg> data){
        this.modelChatMsgs = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return modelChatMsgs.size();
    }

    @Nullable
    @Override
    public ModelChatMsg getItem(int position) {
        return modelChatMsgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        int result = -1;
        ModelChatMsg msg = modelChatMsgs.get(position);
        if(msg.isMyInfo()){
            result = TYPE_ME_MESSAGE;
        }else{
            result = TYPE_SERVER_MESSAGE;
        }
        return result;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        int type = getItemViewType(position);
        if(type == TYPE_ME_MESSAGE){
            ViewMeHolder holder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_chat_me, parent, false);
                holder = new ViewMeHolder();
                holder.icon = convertView.findViewById(R.id.icon);
                holder.username = convertView.findViewById(R.id.username);
                holder.content = convertView.findViewById(R.id.content);
                convertView.setTag(holder);
            }else{
                holder = (ViewMeHolder) convertView.getTag();
            }
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources() , R.drawable.avasterdoor);
            holder.icon.setImageDrawable(new CircleImageDrawable(bitmap));
            holder.username.setText(modelChatMsgs.get(position).getUsername());
            holder.content.setText(modelChatMsgs.get(position).getContent());

        }else{
            ViewServerHolder holder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_chat_other , parent , false);
                holder = new ViewServerHolder();
                holder.icon = convertView.findViewById(R.id.icon);
                holder.username = convertView.findViewById(R.id.username);
                holder.content = convertView.findViewById(R.id.content);
                convertView.setTag(holder);
            }else{
                holder = (ViewServerHolder) convertView.getTag();
            }
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources() , R.drawable.acx);
            holder.icon.setImageDrawable(new CircleImageDrawable(bitmap));
            holder.username.setText(modelChatMsgs.get(position).getUsername());
            holder.content.setText(modelChatMsgs.get(position).getContent());
        }
        return convertView;
    }

    private class ViewMeHolder {
        ImageView icon;
        TextView username;
        TextView content;
    }

    private class ViewServerHolder{
        ImageView icon;
        TextView username;
        TextView content;
    }
}
