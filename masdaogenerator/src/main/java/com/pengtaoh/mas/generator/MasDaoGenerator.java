package com.pengtaoh.mas.generator;


import com.pengtaoh.mas.generator.constants.DbConstants;
import com.pengtaoh.mas.generator.entity.DictBrandEntity;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;

public class MasDaoGenerator {

    public static void main(String[] args) throws Exception {
        String path = DbConstants.DATABASE_PATH;
        if (args.length >= 1) {
            path = args[0];
        }
        Schema schema = new Schema(DbConstants.DATABASE_VERSION, DbConstants.DATABASE_DEFAULT_JAVA_PACKAGE);
        addAllSchema(schema);
        new DaoGenerator().generateAll(schema, path);
    }

    private static void addAllSchema(Schema schema) {
        DictBrandEntity.addEntity(schema);
    }

}
