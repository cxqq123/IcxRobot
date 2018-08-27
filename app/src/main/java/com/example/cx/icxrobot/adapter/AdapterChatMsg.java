package com.example.cx.icxrobot.adapter;

import android.content.Context;
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
import com.example.cx.icxrobot.util.ImageManager;

import java.util.List;

public class AdapterChatMsg extends BaseAdapter{

    private List<ModelChatMsg> modelChatMsgs;
    private Context mContext;

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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ModelChatMsg msg = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            assert msg != null;
            if (!msg.isMyInfo()) {
                view = LayoutInflater.from(mContext).inflate(R.layout.adapter_chat_other, parent, false);
            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.adapter_chat_me, parent, false);
            }
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
            viewHolder.username = (TextView) view.findViewById(R.id.username);
            viewHolder.content = (TextView) view.findViewById(R.id.content);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.icon.setImageResource(ImageManager.imagesAvatar[modelChatMsgs.get(position).getIconID()]);
        viewHolder.username.setText(msg.isMyInfo() ? modelChatMsgs.get(position).getUsername() : modelChatMsgs.get(position).getChatObj());
        viewHolder.content.setText(modelChatMsgs.get(position).getContent());
        return view;
    }

    private class ViewHolder {
        ImageView icon;
        TextView username;
        TextView content;
    }
}
