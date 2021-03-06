package com.pengtaoh.mas.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.pengtaoh.mas.dao.DictBrandEntity;

import com.pengtaoh.mas.dao.DictBrandEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dictBrandEntityDaoConfig;

    private final DictBrandEntityDao dictBrandEntityDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dictBrandEntityDaoConfig = daoConfigMap.get(DictBrandEntityDao.class).clone();
        dictBrandEntityDaoConfig.initIdentityScope(type);

        dictBrandEntityDao = new DictBrandEntityDao(dictBrandEntityDaoConfig, this);

        registerDao(DictBrandEntity.class, dictBrandEntityDao);
    }
    
    public void clear() {
        dictBrandEntityDaoConfig.getIdentityScope().clear();
    }

    public DictBrandEntityDao getDictBrandEntityDao() {
        return dictBrandEntityDao;
    }

}
