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
        entity.addStringProperty(BrandModel.COLUMN_SERIAL_NUMBER);
        entity.addStringProperty(BrandModel.COLUMN_AREA);
        entity.addStringProperty(BrandModel.COLUMN_ATTR);
        entity.addStringProperty(BrandModel.COLUMN_TYPE);
        entity.addStringProperty(BrandModel.COLUMN_DESC);
        entity.addStringProperty(BrandModel.COLUMN_NAME);
        entity.addLongProperty(BrandModel.COLUMN_CREATE_TIME);
    }
}
