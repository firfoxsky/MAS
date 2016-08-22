package com.pengtaoh.mas.generator.entity;


import com.pengtaoh.mas.generator.model.BrandModel;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by cheng_colin on 2015/9/22.
 * Desc:
 */
public class DictBrandEntity {

    public static void addEntity(Schema schema) {
        Entity entity = schema.addEntity(DictBrandEntity.class.getSimpleName());
        entity.addIdProperty().autoincrement();
        entity.addIntProperty(BrandModel.COLUMN_BID).columnName(BrandModel.COLUMN_BID);
        entity.addIntProperty(BrandModel.COLUMN_CATEGORYID).columnName(BrandModel.COLUMN_CATEGORYID);
        entity.addStringProperty(BrandModel.COLUMN_BNAME).columnName(BrandModel.COLUMN_BNAME);
        entity.addStringProperty(BrandModel.COLUMN_LETTER).columnName(BrandModel.COLUMN_LETTER);
        entity.addStringProperty(BrandModel.COLUMN_CATEGORY_LIST_STR).columnName(BrandModel.COLUMN_CATEGORY_LIST_STR);
        entity.addLongProperty(BrandModel.COLUMN_CREATE_TIME).columnName(BrandModel.COLUMN_CREATE_TIME);
    }
}
