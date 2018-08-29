package com.example.cx.icxrobot.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.cx.icxrobot.greendao.DaoMaster;
import com.example.cx.icxrobot.greendao.DaoSession;

/**
 * Created by cx
 * on 2018/8/28
 */
public class IcxDbManager {

    //是否加密
    public static final boolean ENCRYPTED = true;

    private static final String DB_NAME = "cx.db";
    private static IcxDbManager mDbManager;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private Context mContext;

    private IcxDbManager(Context context){
        this.mContext = context;
        //初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);

    }

    public static IcxDbManager getInstance(Context context){
        if(null == mDbManager){
            synchronized (IcxDbManager.class){
                if(null == mDbManager){
                    mDbManager = new IcxDbManager(context);
                }
            }
        }
        return mDbManager;
    }

    /**
     * 获取可读数据库
     * @param context
     * @return
     */
    public static SQLiteDatabase getReadableDatabase(Context context){
        if(null == mDevOpenHelper){
            getInstance(context);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     * @param context
     * @return
     */
    public static SQLiteDatabase getWritableDatabase(Context context){
        if(null == mDevOpenHelper){
            getInstance(context);
        }
        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * 获取DaoMaster
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context){
        if(null == mDaoMaster){
            synchronized (IcxDbManager.class){
                if(null == mDaoMaster){
                    mDaoMaster = new DaoMaster(getWritableDatabase(context));
                }
            }
        }
        return mDaoMaster;
    }

    /**
     * 获取DaoSession
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context){
        if(null == mDaoSession){
            synchronized (IcxDbManager.class){
                if(null == mDaoSession){
                    mDaoSession = getDaoMaster(context).newSession();
                }
            }
        }
        return mDaoSession;
    }
}
