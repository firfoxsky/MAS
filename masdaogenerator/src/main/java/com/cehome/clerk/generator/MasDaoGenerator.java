package com.cehome.clerk.generator;

import com.cehome.clerk.generator.constants.DbConstants;
import com.cehome.clerk.generator.entity.DictBrandByModelsEntity;
import com.cehome.clerk.generator.entity.DictBrandEntity;
import com.cehome.clerk.generator.entity.DictCategoryEntity;
import com.cehome.clerk.generator.entity.DictCityEntity;
import com.cehome.clerk.generator.entity.DictCountyEntity;
import com.cehome.clerk.generator.entity.DictProvinceEntity;
import com.cehome.clerk.generator.entity.DictTonnageValueEntity;
import com.cehome.clerk.generator.entity.DraftBoxEntity;
import com.cehome.clerk.generator.entity.OpertatingItemEntity;
import com.cehome.clerk.generator.entity.RepairAddServiceTypeEntity;
import com.cehome.clerk.generator.entity.RepairAddShopTypeEntity;
import com.cehome.clerk.generator.entity.ReportEquipmentDetailEntity;
import com.cehome.clerk.generator.entity.UserEntity;

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
		UserEntity.addEntity(schema);
		RepairAddServiceTypeEntity.addEntity(schema);
		RepairAddShopTypeEntity.addEntity(schema);
		DictProvinceEntity.addEntity(schema);
		DictCityEntity.addEntity(schema);
		DictCountyEntity.addEntity(schema);
		DictBrandByModelsEntity.addEntity(schema);
		DictBrandEntity.addEntity(schema);
		DictCategoryEntity.addEntity(schema);
		new DictTonnageValueEntity().addEntity(schema);
		OpertatingItemEntity.addEntity(schema);
		DraftBoxEntity.addEntity(schema);
		ReportEquipmentDetailEntity.addEntity(schema);
	}

}
