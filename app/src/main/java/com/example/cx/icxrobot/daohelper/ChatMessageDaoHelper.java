package com.example.cx.icxrobot.daohelper;

import android.content.Context;

import com.example.cx.icxrobot.db.IcxDbManager;
import com.example.cx.icxrobot.entry.ChatMessage;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by cx
 * on 2018/8/29
 */
public class ChatMessageDaoHelper {

    /**
     * 添加数据至数据库
     *
     * @param context
     * @param message
     */
    public static void insertData(Context context, ChatMessage message) {
        IcxDbManager.getDaoSession(context).getChatMessageDao().insert(message);
    }


    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertData(Context context, List<ChatMessage> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        IcxDbManager.getDaoSession(context).getChatMessageDao().insertInTx(list);
    }
    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param message
     */
    public static void saveData(Context context, ChatMessage message) {
        IcxDbManager.getDaoSession(context).getChatMessageDao().save(message);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param message 删除具体内容
     */
    public static void deleteData(Context context, ChatMessage message) {
        IcxDbManager.getDaoSession(context).getChatMessageDao().delete(message);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        IcxDbManager.getDaoSession(context).getChatMessageDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        IcxDbManager.getDaoSession(context).getChatMessageDao().deleteAll();
    }
    /**
     * 更新数据库
     *
     * @param context
     * @param message
     */
    public static void updateData(Context context, ChatMessage message) {
        IcxDbManager.getDaoSession(context).getChatMessageDao().update(message);
    }


    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<ChatMessage> queryAll(Context context) {
        QueryBuilder<ChatMessage> builder = IcxDbManager.getDaoSession(context).getChatMessageDao().queryBuilder();
        return builder.build().list();
    }


}
