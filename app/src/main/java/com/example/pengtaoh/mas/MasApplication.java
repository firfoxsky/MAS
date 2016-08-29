package com.example.pengtaoh.mas;

import android.app.Application;

import com.pengtaoh.mas.dao.DaoMaster;
import com.pengtaoh.mas.dao.DaoSession;

/**
 * Created by lyh on 2016/8/29.
 */
public class MasApplication extends Application {
    private static MasApplication mInstance;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;

    public static MasApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (getInstance() == null) {
            mInstance = this;
        }
    }

    /**
     * 取得DaoSession
     *
     * @return
     */
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            synchronized (MasApplication.class) {
                if (daoSession == null) {
                    if (daoMaster == null) {
                        daoMaster = getDaoMaster();
                    }
                    daoSession = daoMaster.newSession();
                }
            }
        }
        return daoSession;
    }

    /**
     * 取得DaoMaster
     *
     * @return
     */
    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(mInstance, Constants.DATABASE_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }
}
