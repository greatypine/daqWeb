package com.cnpc.pms.personal.manager.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.cnpc.pms.personal.dao.EmployeeMoreInfoDao;
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


	private static int getMonthDiff(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		if(c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		// 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
		int yearInterval = year1 - year2;
		// 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
		if(month1 < month2 || month1 == month2 && day1 < day2) yearInterval --;
		// 获取月数差值
		int monthInterval =  (month1 + 12) - month2  ;
		if(day1 < day2) monthInterval --;
		monthInterval %= 12;
		return yearInterval * 12 + monthInterval;
	}

	/**
	 * @Description 时间间隔天数
	 * @author gbl
	 * @date 2018/7/10 13:47
	 **/
	private Map<String,Object> dateCompare(Date fromDate, Date toDate){
		Map<String,Object> result = new HashMap<String,Object>();
		Calendar  from  =  Calendar.getInstance();
		from.setTime(fromDate);
		Calendar  to  =  Calendar.getInstance();
		to.setTime(toDate);
		//只要年月
		int fromYear = from.get(Calendar.YEAR);
		int fromMonth = from.get(Calendar.MONTH);
		int toYear = to.get(Calendar.YEAR);
		int toMonth = to.get(Calendar.MONTH);
		int year = toYear  -  fromYear;
		int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
		int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));

		result.put("year",year);
		result.put("month",month);
		result.put("day",day);
		return result;
	}



	@Override
	public void analyzeEmployeeWorkingAge() {

		EmployeeMoreInfoDao employeeMoreInfoDao = (EmployeeMoreInfoDao) SpringHelper.getBean(EmployeeMoreInfo.class.getName());

		List<Map<String,Object>> humanresources = null;
		List<Map<String,Object>> storekeeper = null;

		try {
			humanresources = employeeMoreInfoDao.queryHumanresource();
			storekeeper = employeeMoreInfoDao.queryStoreKepeer();
			Map<String,Object> result = new HashMap<String,Object>();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			//更新国安侠工龄
			for(int i=0;i<humanresources.size();i++){
				Object topostdate = humanresources.get(i).get("topostdate");
				Object employeeNo = humanresources.get(i).get("employee_no");
				if(topostdate==null){
					continue;
				}
				result = dateCompare(sdf.parse(String.valueOf(topostdate)),date);
				Integer year = Integer.parseInt(String.valueOf(result.get("year")));
				Integer month = Integer.parseInt(String.valueOf(result.get("month")));
				Integer day = Integer.parseInt(String.valueOf(result.get("day")));
				employeeMoreInfoDao.updateEmployeeWorkingAge(String.valueOf(employeeNo),year,month,day);
			}

			//更新店长工龄
			for(int j=0;j<storekeeper.size();j++){
				Object topostdate = storekeeper.get(j).get("topostdate");
				Object employeeNo = storekeeper.get(j).get("employee_no");
				if(topostdate==null){
					continue;
				}
				result = dateCompare(sdf.parse(String.valueOf(topostdate)),date);
				result = dateCompare(sdf.parse(String.valueOf(topostdate)),date);
				Integer year = Integer.parseInt(String.valueOf(result.get("year")));
				Integer month = Integer.parseInt(String.valueOf(result.get("month")));
				Integer day = Integer.parseInt(String.valueOf(result.get("day")));
				employeeMoreInfoDao.updateEmployeeWorkingAge(String.valueOf(employeeNo),year,month,day);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}




	}

}
