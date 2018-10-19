package com.cnpc.pms.platform.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.mongodb.common.MongoDbUtil;
import com.cnpc.pms.platform.dao.PlatformStoreDao;
import com.cnpc.pms.platform.entity.PlatformStore;
import com.cnpc.pms.platform.manager.PlatformStoreManager;
import com.gexin.fastjson.JSONArray;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 平台门店表查询 Created by liuxiao on 2016/10/25.
 */
public class PlatformStoreManagerImpl extends BizBaseCommonManager implements PlatformStoreManager {

	@Override
	public PlatformStore findPlatformStoreById(String id) {
		return (PlatformStore) this.getObject(id);
	}

	@Override
	public PlatformStore findPlatformStoreByName(String name) {
		List<?> lst_store = this.getList(FilterFactory.getSimpleFilter("name", name));
		if (lst_store != null && lst_store.size() > 0 && lst_store.size() < 2) {
			return (PlatformStore) lst_store.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> showPlatformStoreData(QueryConditions conditions) {
		PlatformStoreDao platformStoreDao = (PlatformStoreDao) SpringHelper.getBean(PlatformStoreDao.class.getName());
		// 查询的数据条件
		StringBuilder sb_where = new StringBuilder();
		// 分页对象
		PageInfo obj_page = conditions.getPageinfo();
		// 返回的对象，包含数据集合、分页对象等
		Map<String, Object> map_result = new HashMap<String, Object>();

		for (Map<String, Object> map_where : conditions.getConditions()) {
			if ("name".equals(map_where.get("key")) && null != map_where.get("value")
					&& !"".equals(map_where.get("value"))) {
				sb_where.append(" AND name like '").append(map_where.get("value")).append("'");
			}
		}
		/*
		 * System.out.println(sb_where); map_result.put("pageinfo", obj_page);
		 * map_result.put("header", "平台门店列表"); map_result.put("data",
		 * platformStoreDao.getPlatformStoreList(sb_where.toString(),
		 * obj_page));
		 */
		return platformStoreDao.getPlatformStoreInfoList(sb_where.toString(), obj_page);
	}

	@Override
	public PlatformStore findPlatStoreById(String id) {
		List<?> lst_store = this.getList(FilterFactory.getSimpleFilter("id", id));
		if (lst_store != null && lst_store.size() > 0 && lst_store.size() < 2) {
			return (PlatformStore) lst_store.get(0);
		}
		return null;
	}

	@Override
	public PlatformStore findPlatStoreByCode(String code) {
		List<?> lst_store = getList(FilterFactory.getSimpleFilter("code", code));
		if (lst_store != null && lst_store.size() > 0 && lst_store.size() < 2) {
			return (PlatformStore) lst_store.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> findroundEmployeeByCitycode(String citycode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd");
			String format2 = format.format(date);
			PlatformStoreDao platformStoreDao = (PlatformStoreDao) SpringHelper
					.getBean(PlatformStoreDao.class.getName());
			List<Map<String, Object>> list = platformStoreDao.getEmployeeByCity(citycode, true);
			List<Object> list1 = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				list1.add(list.get(i).get("employeeId"));
			}
			MongoDbUtil mDbUtil = (MongoDbUtil) SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
			MongoCollection<Document> collection = database.getCollection("position_record");
			org.json.JSONArray jArray = new org.json.JSONArray();
			//创建一个document集合
			List<Document> pipeline = new ArrayList<Document>();
			//创建一个match document
			Document match = new Document("$match",new BasicDBObject("employeeId", new BasicDBObject("$in",list1)).append("updateTime",
					new Document("$get", new Date(format2+" 00:00:00"))).append("updateTime",
					new Document("$lte", new Date(format2+" 23:59:59"))));
			pipeline.add(match);
			//创建一个project document
			List<Object> listcoor = new ArrayList<Object>();
			listcoor.add("$longitude");
			listcoor.add("$latitude");
			Document project = new Document("$project",new Document("_id","$employeeId").append("location",listcoor).append("createdAt",1));
			pipeline.add(project);
			//创建一个sort document
			Document sort = new Document("$sort",new Document("createdAt",-1));
			pipeline.add(sort);
			Document group = new Document("$group",new Document("_id","$_id").append("employeeId", new Document("$first","$_id")).append("position", new Document("$first","$location")));
			pipeline.add(group);
			AggregateIterable<Document> aggregate = collection.aggregate(pipeline).allowDiskUse(true);
			MongoCursor<Document> cursor = aggregate.iterator();
			JSONObject jObject = null;
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				jObject = new JSONObject();
				jObject.put("locations", doc.get("position"));
				jObject.put("id", doc.get("employeeId"));
				// jObject.put("position", doc.get("position"));
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if (map.get("employeeId").equals(doc.get("employeeId"))) {
						jObject.put("phone", (map.get("mobilephone") == null || map.get("mobilephone") == "") ? ""
								: map.get("mobilephone"));
						jObject.put("employeeNo", map.get("employeeNo"));
						jObject.put("storeName", map.get("storeName"));
						jObject.put("employeeName", map.get("employeeName"));
						jObject.put("platformid", map.get("platformid"));
						break;
					}
				}
				jArray.put(jObject);
			}
			result.put("code", CodeEnum.success.getValue());
			result.put("message", CodeEnum.success.getDescription());
			result.put("data", JSONArray.parse(jArray.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}
}
