package com.cnpc.pms.festival.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.bson.Document;
import org.json.JSONObject;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.festival.dao.FestivalStatDao;
import com.cnpc.pms.festival.manager.FestivalStatManager;
import com.cnpc.pms.heatmap.entity.RequestInfoDto;
import com.cnpc.pms.mongodb.common.MongoDbUtil;
import com.cnpc.pms.platform.dao.OrderDao;
import com.cnpc.pms.platform.dao.PlatformStoreDao;
import com.gexin.fastjson.JSONArray;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * @Function:节日 518 数据大屏 Manager实现类
 * @author:chenchuang
 * @date:2018年5月12日下午1:19:02  
 *
 * @version V1.0
 */
public class FestivalStatManagerImpl extends BizBaseCommonManager implements FestivalStatManager{

	@Override
	public List<Map<String, Object>> productRanking(String dateTime){
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		List<Map<String, Object>> lst_data = festivalStatDao.productRanking(dateTime);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> eshopRanking(String dateTime) {
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		List<Map<String, Object>> lst_data = festivalStatDao.eshopRanking(dateTime);
    	return lst_data;
	}

	@Override
	public Map<String, Object> queryPayUser(String dateTime) {
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		Map<String,Object> order_obj = festivalStatDao.queryPayUser(dateTime);
		return order_obj;
	}

	@Override
	public Map<String, Object> queryTurnover(String dateTime){
		OrderDao orderDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		Map<String,Object> order_obj = orderDao.queryTurnover(dateTime);
		return order_obj;
	}
	
	@Override
	public Map<String, Object> queryNewCusUser(String dateTime) {
		OrderDao orderDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		Map<String,Object> order_obj = orderDao.queryNewCusUser(dateTime);
		return order_obj;
	}

	@Override
	public List<Map<String, Object>> queryNewVipCusUser(String dateTime){
		OrderDao orderDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		List<Map<String,Object>> lst_data = orderDao.queryNewVipCusUser(dateTime);
		return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> cityRanking(String dateTime) {
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		List<Map<String, Object>> lst_data = festivalStatDao.cityRanking(dateTime);
    	return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryTurnoverByHour(String dateTime) {
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		List<Map<String, Object>> lst_data = festivalStatDao.queryTurnoverByHour(dateTime);
    	return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryUserByHour(String dateTime) {
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		List<Map<String, Object>> lst_data = festivalStatDao.queryUserByHour(dateTime);
    	return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryOrderHeatInfo(String dateTime,String citycode) {
		OrderDao orderDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		if(citycode.startsWith("00")){
			citycode = citycode.substring(1,citycode.length());
		}
		return orderDao.queryOrderHeatfromDaily(dateTime,citycode);
		
	}

	@Override
	public List<Map<String, Object>> queryRecentlyOrder(String dateTime){
		OrderDao orderDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		List<Map<String, Object>> lst_data = orderDao.queryRecentlyOrder(dateTime);
		return lst_data;
	}
	
	@Override
	public Map<String, Object> empdiver(RequestInfoDto requestInfoDto) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> result = new HashMap<String, Object>();
		PlatformStoreDao platformStoreDao = (PlatformStoreDao)SpringHelper.getBean(PlatformStoreDao.class.getName());
		String cityNo = requestInfoDto.getCityno();
		if(cityNo.startsWith("00")){
			cityNo = cityNo.substring(1,cityNo.length());
		}
		list = platformStoreDao.getEmployeeByCity(cityNo,true);
		String beginDate = requestInfoDto.getBeginDate();
		String endDate = requestInfoDto.getEndDate();

			org.json.JSONArray jArray = new org.json.JSONArray();
			List<Object> list1 = new ArrayList<Object>();
			List<Object> list2 = new ArrayList<Object>();
			List<Object> list3 = new ArrayList<Object>();
			List<Object> list4 = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				if(i <200){
					list1.add(list.get(i).get("employeeId"));
				}else if(i>=200&&i <400){
					list2.add(list.get(i).get("employeeId"));
				}else if(i>=400&&i <600){
					list3.add(list.get(i).get("employeeId"));
				}else{
					list4.add(list.get(i).get("employeeId"));
				}
				
			}
			Thread children1 = new Thread(new Runnable() {
				@Override
				public void run() {
			        	   MongoCursor<Document> cursor = getData(list1,beginDate,endDate);
			        	   while (cursor.hasNext()) {  
					           Document doc = cursor.next();  
					           JSONObject jObject = new JSONObject();
							   jObject.put("locations", doc.get("locations"));
							   jObject.put("createTime", doc.get("createTime"));
							   jObject.put("id", doc.get("_id"));
							   jArray.put(jObject);
					    }
			    }
			});
			children1.start();
			Thread children2 = new Thread(new Runnable() {
				@Override
				public void run(){
		        	   MongoCursor<Document> cursor_1 = getData(list2,beginDate,endDate);
			   	        while (cursor_1.hasNext()) {  
			   		           Document doc = cursor_1.next();  
			   		           JSONObject jObject = new JSONObject();
			   				   jObject.put("locations", doc.get("locations"));
			   				   jObject.put("createTime", doc.get("createTime"));
			   				   jObject.put("id", doc.get("_id"));
			   				   jArray.put(jObject);
			   		    }
		           }
			});
			children2.start();
			Thread children3 = new Thread(new Runnable() {
				@Override
				public void run(){
		        	   MongoCursor<Document> cursor_2 = getData(list3,beginDate,endDate);
		        	   while (cursor_2.hasNext()) {  
				           Document doc = cursor_2.next();  
				           JSONObject jObject = new JSONObject();
						   jObject.put("locations", doc.get("locations"));
						   jObject.put("createTime", doc.get("createTime"));
						   jObject.put("id", doc.get("_id"));
						   jArray.put(jObject);
		        	   }
		           }
			});
			children3.start();
			
			Thread children4 = new Thread(new Runnable() {
				@Override
				public void run(){
		        	   MongoCursor<Document> cursor_3 = getData(list4,beginDate,endDate);
		   	        	while (cursor_3.hasNext()) {  
		   		           Document doc = cursor_3.next();  
		   		           JSONObject jObject = new JSONObject();
		   				   jObject.put("locations", doc.get("locations"));
		   				   jObject.put("createTime", doc.get("createTime"));
		   				   jObject.put("id", doc.get("_id"));
		   				   jArray.put(jObject);
		   	        	}
		           }
			});
			children4.start();
		    
		   
		    Vector<Thread> vectors=new Vector<Thread>(); 
		    vectors.add(children1);
		    vectors.add(children2);
		    vectors.add(children3);
		    vectors.add(children4);
		  //主线程  
	        for(Thread thread : vectors){  
	            try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //使用join来保证childrenThread的5个线程都执行完后，才执行主线程  
	        }
			result.put("data", JSONArray.parse(jArray.toString()));
			return result;
		}

	@SuppressWarnings("deprecation")
	@Override
	public MongoCursor<Document> getData(List<Object> list, String bdate, String edate) {
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("position_record");
		List<Document> pipeline = new ArrayList<Document>();
		Document match = new Document("$match",new BasicDBObject("employeeId", new BasicDBObject("$in",list)).
				append("createdAt",new Document("$gte",new Date(bdate)).append("$lte", new Date(edate))));
		pipeline.add(match);
		List<Object> listcoor = new ArrayList<Object>();
		listcoor.add("$longitude");
		listcoor.add("$latitude");
		Document project = new Document("$project",new Document("_id","$employeeId").append("location",listcoor));
		pipeline.add(project);
		Document group = new Document("$group",new Document("_id","$_id").append("locations", new Document("$push","$location")));
		pipeline.add(group);
		AggregateIterable<Document> aggregate = collection.aggregate(pipeline).allowDiskUse(true);
		MongoCursor<Document> cursor = aggregate.iterator();
		return cursor;
	}

}
