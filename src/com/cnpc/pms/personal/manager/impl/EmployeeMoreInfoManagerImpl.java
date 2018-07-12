package com.cnpc.pms.personal.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.mongodb.common.MongoDbUtil;
import com.cnpc.pms.personal.entity.EmployeeMoreInfo;
import com.cnpc.pms.personal.entity.ObserveParameter;
import com.cnpc.pms.personal.manager.EmployeeMoreInfoManager;
import com.cnpc.pms.personal.util.MapUtils;
import com.cnpc.pms.platform.dao.PlatformEmployeeDao;
import com.cnpc.pms.utils.DateUtils;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class EmployeeMoreInfoManagerImpl extends BizBaseCommonManager implements EmployeeMoreInfoManager{
	
	@Override
	public void getEmployeePositionsDistance() {
		Map<String, Object> result = new HashMap<String, Object>();
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("employee_position");
		PlatformEmployeeDao platformEmployeeDao = (PlatformEmployeeDao)SpringHelper.getBean(PlatformEmployeeDao.class.getName());
		org.json.JSONArray jArray = new org.json.JSONArray();
		List<Document> pipeline = new ArrayList<Document>();
		Document project = new Document("$project",new Document("_id","$employeeId").append("locations", 1));
		pipeline.add(project);
		Document unwind = new Document("$unwind","$locations");
		pipeline.add(unwind);
		Document filter = new Document();
		String curDate = DateUtils.dateFormat(DateUtils.getDateBeforeOneDate(new Date()), "yyyy/MM/dd");
		filter.put("locations.createTime",new Document("$gte",(new Date(curDate+" 00:00:00"))).append("$lte", (new Date(curDate+" 23:59:59"))));
		Document match1 = new Document("$match",filter);
		pipeline.add(match1);
		Document group = new Document("$group",new Document("_id","$_id").append("locations", new Document("$push","$locations.location")));
		pipeline.add(group);
		AggregateIterable<Document> aggregate = collection.aggregate(pipeline).allowDiskUse(true);
		MongoCursor<Document> cursor = aggregate.iterator();
		Map<String,Object> jObject = null;
		List<Map<String,Object>> listempInfo = new ArrayList<Map<String,Object>>();
        int j = 0;
		while (cursor.hasNext()) {  
        	j++;
	           Document doc = cursor.next();  
	           jObject =new HashMap<String, Object>();
			   jObject.put("locations", doc.get("locations"));
			   jObject.put("id", doc.get("_id"));
			   List<Map<String,Object>> employeeByEmployeeId = platformEmployeeDao.getEmployeeByEmployeeId(doc.get("_id").toString()); 
			   if(employeeByEmployeeId.size() > 0){
				   String employeeNo = employeeByEmployeeId.get(0).get("employeeNo").toString();
				   jObject.put("employeeNo",employeeNo);
				   listempInfo.add(jObject);
			   }
	    }
		 System.out.println(j);
		 int m = 0;
        if(listempInfo.size() > 0){   
        	for(int z = 0; z < listempInfo.size(); z++){
        		Map<String, Object> map = listempInfo.get(z);
        		List object = (List)map.get("locations");
        		String employeeNo = map.get("employeeNo").toString();
        		EmployeeMoreInfo employeeMoreInfo = new EmployeeMoreInfo();
        		employeeMoreInfo.setEmployeeNo(employeeNo);
     		   float moveDistance = 0;
     		   if(object.size() > 0){
     			   double distance_sum = 0;
     			   for(int i = 0; i <object.size(); i++){
     				   if(i != object.size()-1){
     					  List object2 = (List)object.get(i);
     					 List object3 = (List)object.get(i+1);
     					  double distance = Distance(Double.parseDouble(object2.get(0).toString()),Double.parseDouble(object2.get(1).toString()), Double.parseDouble(object3.get(0).toString()),Double.parseDouble(object3.get(1).toString()));
     					    //long distance = MapUtils.getDistance(object.get(i).toString().replace("[", "").replace("]", "").replace(" ", ""), object.get(i+1).toString().replace("[", "").replace("]", "").replace(" ", ""));
     					    distance_sum += distance;
     				   }
     			   }
     			   moveDistance= distance_sum == 0 ? 0 :Float.parseFloat(distance_sum+"");
     		   }
     		   if(moveDistance > 0){
     			   employeeMoreInfo.setMoveDistance(moveDistance);
     			   List<?> employeeByEmployeeno = this.getEmployeeByEmployeeno(employeeNo);
     			   if(employeeByEmployeeno != null && employeeByEmployeeno.size() > 0){
     				   for(int i = 0; i < employeeByEmployeeno.size(); i++){
     					   EmployeeMoreInfo employeeMoreInfo1 = (EmployeeMoreInfo)employeeByEmployeeno.get(i);
     					   float moveDistance2 = employeeMoreInfo1.getMoveDistance();
     					   employeeMoreInfo.setMoveDistance(moveDistance2+moveDistance);
     					   BeanUtils.copyProperties(employeeMoreInfo, employeeMoreInfo1,
     									new String[] { "id", "version", "create_time", "create_user", "create_user_id" });
     					  
     					preObject(employeeMoreInfo1);
     					this.saveObject(employeeMoreInfo1);
     					 m++;
     					}
     			   }else{
     				   preObject(employeeMoreInfo);
     				   this.saveObject(employeeMoreInfo);
     				   m++;
     			   }
     		   }
        	}
        }  
        System.out.println(m);
        System.out.println("=====================================》》》》》》》》》》》》》》》》》共"+j+"条数据，插入"+m+"条数据");
        
	}

	@Override
	public List<?> getEmployeeByEmployeeno(String employeeNo) {
		List<?> list = this.getList(FilterFactory.getSimpleFilter("employeeNo= '"+employeeNo+"'"));
		if (list != null && list.size() > 0) {
			 return list;
		}
		return null;
	}
	
	
	public static double Distance(double long1, double lat1, double long2,  double lat2) {  
		double a, b, R;  
		R = 6371; // 地球半径  
		lat1 = lat1 * Math.PI / 180.0;  
		lat2 = lat2 * Math.PI / 180.0;  
		a = lat1 - lat2;  
		b = (long1 - long2) * Math.PI / 180.0;  
		double d;  
		double sa2, sb2;  
		sa2 = Math.sin(a / 2.0);  
		sb2 = Math.sin(b / 2.0);  
		d = 2*R*Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)*Math.cos(lat2) * sb2 * sb2));  
		return d;  
	}
	
	public static void main(String[] args) {
		double distance = Distance(116.285798,39.922102, 116.285798,39.932102);
		System.out.println(distance);
	}

	@Override
	public void getHistoryEmployeePositionsDistance() {
		Map<String, Object> result = new HashMap<String, Object>();
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("employee_position");
		PlatformEmployeeDao platformEmployeeDao = (PlatformEmployeeDao)SpringHelper.getBean(PlatformEmployeeDao.class.getName());
		org.json.JSONArray jArray = new org.json.JSONArray();
		List<Document> pipeline = new ArrayList<Document>();
		Document project = new Document("$project",new Document("_id","$employeeId").append("locations", 1));
		pipeline.add(project);
		Document unwind = new Document("$unwind","$locations");
		pipeline.add(unwind);
		/*Document filter = new Document();
		String curDate = DateUtils.dateFormat(DateUtils.getDateBeforeOneDate(new Date()), "yyyy/MM/dd");
		filter.put("locations.createTime",new Document("$gte",(new Date(curDate+" 00:00:00"))).append("$lte", (new Date(curDate+" 23:59:59"))));
		Document match1 = new Document("$match",filter);
		pipeline.add(match1);*/
		Document group = new Document("$group",new Document("_id","$_id").append("locations", new Document("$push","$locations.location")));
		pipeline.add(group);
		AggregateIterable<Document> aggregate = collection.aggregate(pipeline).allowDiskUse(true);
		MongoCursor<Document> cursor = aggregate.iterator();
		Map<String,Object> jObject = null;
		List<Map<String,Object>> listempInfo = new ArrayList<Map<String,Object>>();
        int j = 0;
		while (cursor.hasNext()) {  
        	j++;
	           Document doc = cursor.next();  
	           jObject =new HashMap<String, Object>();
			   jObject.put("locations", doc.get("locations"));
			   jObject.put("id", doc.get("_id"));
			   List<Map<String,Object>> employeeByEmployeeId = platformEmployeeDao.getEmployeeByEmployeeId(doc.get("_id").toString()); 
			   if(employeeByEmployeeId.size() > 0){
				   String employeeNo = employeeByEmployeeId.get(0).get("employeeNo").toString();
				   jObject.put("employeeNo",employeeNo);
				   listempInfo.add(jObject);
			   }
	    }
		 System.out.println(j);
		 int m = 0;
        if(listempInfo.size() > 0){   
        	for(int z = 0; z < listempInfo.size(); z++){
        		Map<String, Object> map = listempInfo.get(z);
        		List object = (List)map.get("locations");
        		String employeeNo = map.get("employeeNo").toString();
        		EmployeeMoreInfo employeeMoreInfo = new EmployeeMoreInfo();
        		employeeMoreInfo.setEmployeeNo(employeeNo);
     		   float moveDistance = 0;
     		   if(object.size() > 0){
     			   double distance_sum = 0;
     			   for(int i = 0; i <object.size(); i++){
     				   if(i != object.size()-1){
     					  List object2 = (List)object.get(i);
     					 List object3 = (List)object.get(i+1);
     					  double distance = Distance(Double.parseDouble(object2.get(0).toString()),Double.parseDouble(object2.get(1).toString()), Double.parseDouble(object3.get(0).toString()),Double.parseDouble(object3.get(1).toString()));
     					    //long distance = MapUtils.getDistance(object.get(i).toString().replace("[", "").replace("]", "").replace(" ", ""), object.get(i+1).toString().replace("[", "").replace("]", "").replace(" ", ""));
     					    distance_sum += distance;
     				   }
     			   }
     			   moveDistance= distance_sum == 0 ? 0 :Float.parseFloat(distance_sum+"");
     		   }
     		   if(moveDistance > 0){
     			   employeeMoreInfo.setMoveDistance(moveDistance);
     			   List<?> employeeByEmployeeno = this.getEmployeeByEmployeeno(employeeNo);
     			   if(employeeByEmployeeno != null && employeeByEmployeeno.size() > 0){
     				   for(int i = 0; i < employeeByEmployeeno.size(); i++){
     					   EmployeeMoreInfo employeeMoreInfo1 = (EmployeeMoreInfo)employeeByEmployeeno.get(i);
     					   float moveDistance2 = employeeMoreInfo1.getMoveDistance();
     					   employeeMoreInfo.setMoveDistance(moveDistance2+moveDistance);
     					   BeanUtils.copyProperties(employeeMoreInfo, employeeMoreInfo1,
     									new String[] { "id", "version", "create_time", "create_user", "create_user_id" });
     					  
     					preObject(employeeMoreInfo1);
     					this.saveObject(employeeMoreInfo1);
     					 m++;
     					}
     			   }else{
     				   preObject(employeeMoreInfo);
     				   this.saveObject(employeeMoreInfo);
     				   m++;
     			   }
     		   }
        	}
        }  
        System.out.println(m);
        System.out.println("=====================================》》》》》》》》》》》》》》》》》共"+j+"条数据，插入"+m+"条数据");
		
	}

}
