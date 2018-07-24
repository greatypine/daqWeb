package com.cnpc.pms.personal.manager.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.personal.dao.EmployeeMoreInfoDao;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.mongodb.common.MongoDbUtil;
import com.cnpc.pms.personal.entity.EmployeeMoreInfo;
import com.cnpc.pms.personal.manager.EmployeeMoreInfoManager;
import com.cnpc.pms.platform.dao.PlatformEmployeeDao;
import com.cnpc.pms.utils.DateUtils;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class EmployeeMoreInfoManagerImpl extends BizBaseCommonManager implements EmployeeMoreInfoManager{
	
	@Override
	public void getEmployeePositionsDistance() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String localIp=addr.getHostAddress().toString(); //获取本机ip
			System.out.println("本地IP>>>>>>>>>>"+localIp);
			String schedulerServerIp = PropertiesUtil.getValue("scheduler_server_ip");
			if(schedulerServerIp.contains(localIp)){

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

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

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
		System.out.println(25/12+"----"+25%12);

	}

	@Override

	public void getHistoryEmployeePositionsDistance() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String localIp=addr.getHostAddress().toString(); //获取本机ip
			System.out.println("本地IP>>>>>>>>>>"+localIp);
			String schedulerServerIp = PropertiesUtil.getValue("scheduler_server_ip");
			if(schedulerServerIp.contains(localIp)){
				EmployeeMoreInfoManager employeeMoreInfoManager = (EmployeeMoreInfoManager)SpringHelper.getBean("employeeMoreInfoManager");

				Map<String, Object> result = new HashMap<String, Object>();
				MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
				MongoDatabase database = mDbUtil.getDatabase();
				MongoCollection<Document> collection = database.getCollection("employee_position");
				long count = collection.count();
				PlatformEmployeeDao platformEmployeeDao = (PlatformEmployeeDao)SpringHelper.getBean(PlatformEmployeeDao.class.getName());
				long skipcount = 0;
				while(count >= skipcount){
					org.json.JSONArray jArray = new org.json.JSONArray();
					List<Document> pipeline = new ArrayList<Document>();
					Document project = new Document("$project",new Document("_id","$employeeId").append("locations", 1));
					pipeline.add(project);
					Document unwind = new Document("$unwind","$locations");
					pipeline.add(unwind);
					Document filter = new Document();
					String curDate = DateUtils.dateFormat(DateUtils.getDateBeforeOneDate(new Date()), "yyyy/MM/dd");
					filter.put("locations.createTime",new Document("$lte",(new Date(curDate+" 23:59:59"))));
					Document match1 = new Document("$match",filter);
					pipeline.add(match1);
					Document group = new Document("$group",new Document("_id","$_id").append("locations", new Document("$push","$locations.location")));
					pipeline.add(group);
					Document limit = new Document("$limit",skipcount+100);
					pipeline.add(limit);
					Document skip = new Document("$skip",skipcount);
					pipeline.add(skip);
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
										employeeMoreInfoManager.saveObject(employeeMoreInfo1);
										m++;
									}
								}else{
									preObject(employeeMoreInfo);
									employeeMoreInfoManager.saveObject(employeeMoreInfo);
									m++;
								}
							}
						}
					}
					System.out.println(m);
					skipcount += 100;
					System.out.println("=====================================》》》》》》》》》》》》》》》》》共"+j+"条数据，插入"+m+"条数据");
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
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
		int year1= month%12;
		int year2 = month/12;
		if(year1>0){
			result.put("preciseYear",year2+"年以上");
		}else if(year1==0){
			result.put("preciseYear",year2+"年");
		}else{
			result.put("preciseYear","none");
		}
		result.put("year",year);
		result.put("month",month);
		result.put("day",day);
		return result;
	}



	@Override
	public void analyzeEmployeeWorkingAge() {
		EmployeeMoreInfoDao employeeMoreInfoDao = (EmployeeMoreInfoDao) SpringHelper.getBean(EmployeeMoreInfoDao.class.getName());
		EmployeeMoreInfoManager employeeMoreInfoManager = (EmployeeMoreInfoManager)SpringHelper.getBean("employeeMoreInfoManager");
		List<Map<String,Object>> humanresources = null;
		List<Map<String,Object>> storekeeper = null;

		try {
			InetAddress addr = InetAddress.getLocalHost();
			String localIp=addr.getHostAddress().toString(); //获取本机ip
			System.out.println("本地IP>>>>>>>>>>"+localIp);
			String schedulerServerIp = PropertiesUtil.getValue("scheduler_server_ip");
			if(schedulerServerIp.contains(localIp)){
				humanresources = employeeMoreInfoDao.queryHumanresource();
				storekeeper = employeeMoreInfoDao.queryStoreKepeer();
				Map<String,Object> result = new HashMap<String,Object>();
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");
				SimpleDateFormat sf=new SimpleDateFormat("yyyy.MM.dd");
				EmployeeMoreInfo employeeMoreInfo=null;
				List<EmployeeMoreInfo> employeeList = new ArrayList<EmployeeMoreInfo>();
				//更新国安侠工龄
				for(int i=0;i<humanresources.size();i++){
					Object topostdate = humanresources.get(i).get("topostdate");
					Object employeeNo = humanresources.get(i).get("employee_no");
					if(topostdate==null||employeeNo==null){
						continue;

					}


					try {
						result = dateCompare(sdf.parse(topostdate.toString()),date);
					} catch (ParseException e) {
						try {
							result = dateCompare(df.parse(topostdate.toString()),date);
						} catch (ParseException e1) {
							try {
								result = dateCompare(sf.parse(topostdate.toString()),date);
							} catch (ParseException e2) {
								e2.printStackTrace();
								result.put("year",0);
								result.put("month",0);
								result.put("day",0);
								result.put("preciseYear","none");
							}
						}
					}
					Integer year = Integer.parseInt(String.valueOf(result.get("year")));
					Integer month = Integer.parseInt(String.valueOf(result.get("month")));
					Integer day = Integer.parseInt(String.valueOf(result.get("day")));
					String preciseYear = String.valueOf(result.get("preciseYear"));


					employeeList = (List<EmployeeMoreInfo>) this.getList(FilterFactory.getSimpleFilter("employeeNo",employeeNo));

					if(employeeList!=null&&employeeList.size()>0){//如果存在则更新
						for(int j=0;j<employeeList.size();j++){
							EmployeeMoreInfo employeeMoreInfo_s = employeeList.get(j);
							employeeMoreInfo_s.setWorkingAge_year(year);
							employeeMoreInfo_s.setWorkingAge_month(month);
							employeeMoreInfo_s.setWorkingAge_day(day);
							employeeMoreInfo_s.setWorkingAge_year_precise(preciseYear);
							preObject(employeeMoreInfo_s);
							employeeMoreInfoManager.saveObject(employeeMoreInfo_s);
						}
					}else{//不存在则新增
						employeeMoreInfo = new EmployeeMoreInfo();
						employeeMoreInfo.setWorkingAge_year(year);
						employeeMoreInfo.setWorkingAge_month(month);
						employeeMoreInfo.setWorkingAge_day(day);
						employeeMoreInfo.setWorkingAge_year_precise(preciseYear);
						preObject(employeeMoreInfo);
						employeeMoreInfoManager.saveObject(employeeMoreInfo);
					}

				}

				//更新店长工龄
				for(int j=0;j<storekeeper.size();j++){
					Object topostdate = storekeeper.get(j).get("topostdate");
					Object employeeNo = storekeeper.get(j).get("employee_no");
					if(topostdate==null){
						continue;
					}

					try {
						result = dateCompare(sdf.parse(topostdate.toString()),date);
					} catch (ParseException e) {
						try {
							result = dateCompare(df.parse(topostdate.toString()),date);
						} catch (ParseException e1) {
							try {
								result = dateCompare(sf.parse(topostdate.toString()),date);
							} catch (ParseException e2) {
								e2.printStackTrace();
								result.put("year",0);
								result.put("month",0);
								result.put("day",0);
								result.put("preciseYear","none");
							}
						}
					}

					Integer year = Integer.parseInt(String.valueOf(result.get("year")));
					Integer month = Integer.parseInt(String.valueOf(result.get("month")));
					Integer day = Integer.parseInt(String.valueOf(result.get("day")));
					String preciseYear = String.valueOf(result.get("preciseYear"));

					employeeList = (List<EmployeeMoreInfo>) this.getList(FilterFactory.getSimpleFilter("employeeNo",employeeNo));
					if(employeeList!=null&&employeeList.size()>0){//如果存在则更新
						for(int n=0;n<employeeList.size();n++){
							EmployeeMoreInfo employeeMoreInfo_e = employeeList.get(n);
							employeeMoreInfo_e.setWorkingAge_year(year);
							employeeMoreInfo_e.setWorkingAge_month(month);
							employeeMoreInfo_e.setWorkingAge_day(day);
							employeeMoreInfo_e.setWorkingAge_year_precise(preciseYear);
							preObject(employeeMoreInfo_e);
							employeeMoreInfoManager.saveObject(employeeMoreInfo_e);
						}
					}else{//不存在则新增
						employeeMoreInfo = new EmployeeMoreInfo();
						employeeMoreInfo.setWorkingAge_year(year);
						employeeMoreInfo.setWorkingAge_month(month);
						employeeMoreInfo.setWorkingAge_day(day);
						employeeMoreInfo.setWorkingAge_year_precise(preciseYear);
						preObject(employeeMoreInfo);
						employeeMoreInfoManager.saveObject(employeeMoreInfo);
					}
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}


	}



}
