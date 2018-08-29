package com.example.cx.icxrobot.daohelper;

import android.content.Context;
import android.util.Log;

import com.example.cx.icxrobot.db.IcxDbManager;
import com.example.cx.icxrobot.entry.User;
import com.example.cx.icxrobot.greendao.UserDao;
import com.example.cx.icxrobot.util.Constancts;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by cx
 * on 2018/8/28
 */
public class UserDaoHelper {

    /**
     * 添加数据至数据库
     *
     * @param context
     * @param user
     */
    public static void insertData(Context context, User user) {
        IcxDbManager.getDaoSession(context).getUserDao().insert(user);
    }


    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertData(Context context, List<User> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        IcxDbManager.getDaoSession(context).getUserDao().insertInTx(list);
    }
    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param user
     */
    public static void saveData(Context context, User user) {
        IcxDbManager.getDaoSession(context).getUserDao().save(user);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param user 删除具体内容
     */
    public static void deleteData(Context context, User user) {
        IcxDbManager.getDaoSession(context).getUserDao().delete(user);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        IcxDbManager.getDaoSession(context).getUserDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        IcxDbManager.getDaoSession(context).getUserDao().deleteAll();
    }
    /**
     * 更新数据库
     *
     * @param context
     * @param user
     */
    public static void updateData(Context context, User user) {
        IcxDbManager.getDaoSession(context).getUserDao().update(user);
    }


    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<User> queryAll(Context context) {
        QueryBuilder<User> builder = IcxDbManager.getDaoSession(context).getUserDao().queryBuilder();

        return builder.build().list();
    }

    /**
     * 根据用户名和密码查询数据库中是否存在
     * @param context
     * @param username
     * @param password
     * @return
     */
    public static boolean querySingelUser(Context context , String username , String password){
        QueryBuilder<User> builder = IcxDbManager.getDaoSession(context).getUserDao().queryBuilder();
        Query query = builder.where(
                UserDao.Properties.Name.eq(username),UserDao.Properties.Password.eq(password)).build();
        List list = query.list();
        if(list != null && list.size() > 0){
            Log.e(Constancts.LOG_TAG , "true");
            return true;
        }else {
            Log.e(Constancts.LOG_TAG , "false");
            return false;
        }
    }



    /**
     *  分页加载
     * @param context
     * @param pageSize 当前第几页(程序中动态修改pageSize的值即可)
     * @param pageNum  每页显示多少个
     * @return
     */
    public static List<User> queryPaging( int pageSize, int pageNum, Context context){
        UserDao userDao = IcxDbManager.getDaoSession(context).getUserDao();
        List<User> listMsg = userDao.queryBuilder()
                .offset(pageSize * pageNum).limit(pageNum).list();
        return listMsg;
    }
}
