/**
 * gaobaolei
 */
package com.cnpc.pms.mongodb.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import com.cnpc.pms.slice.manager.AreaInfoManager;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.dto.UserDTO;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.mongodb.common.MongoDbUtil;
import com.cnpc.pms.mongodb.dao.MongoDBDao;
import com.cnpc.pms.mongodb.dto.TinyVillageCoordDto;
import com.cnpc.pms.mongodb.manager.MongoDBManager;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.personal.dao.TinyAreaDao;
import com.cnpc.pms.personal.entity.Store;
import com.cnpc.pms.personal.entity.TinyArea;
import com.cnpc.pms.personal.entity.TinyVillage;
import com.cnpc.pms.personal.entity.TinyVillageCode;
import com.cnpc.pms.personal.manager.StoreManager;
import com.cnpc.pms.personal.manager.TinyAreaManager;
import com.cnpc.pms.personal.manager.TinyVillageCodeManager;
import com.cnpc.pms.personal.manager.TinyVillageManager;
import com.cnpc.pms.platform.dao.PlatformEmployeeDao;
import com.cnpc.pms.slice.dao.AreaDao;
import com.cnpc.pms.slice.entity.Area;
import com.cnpc.pms.slice.entity.AreaInfo;
import com.cnpc.pms.slice.manager.AreaManager;
import com.gexin.fastjson.JSONArray;
import com.ibm.db2.jcc.am.be;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoQueryException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;


/**
 * @author gaobaolei
 *
 */
public class MongoDBManagerImpl extends BizBaseCommonManager implements MongoDBManager{
	private static Log logger = LogFactory.getLog(MongoDBManagerImpl.class);
	
	@Override
	public Map<String, Object> getAllTinyVillageOfStore(Long storeId) {
		MongoDBDao mDao = (MongoDBDao)SpringHelper.getBean(MongoDBDao.class.getName());
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			List<Map<String, Object>> list = mDao.queryAllTinyVillageByStore(storeId);
			result.put("code", CodeEnum.success.getValue());
			result.put("message", CodeEnum.success.getDescription());
			result.put("data", list);
		} catch (Exception e) {
			logger.info("getAllTinyVillageOfStore>>>>"+e.getMessage());
			e.printStackTrace();
			result.put("code", CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}
	
	
	@Override
	public Map<String, Object>  getStoreServiceArea(String platformId) {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject storeService = new JSONObject();
		
		try {
			MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
		    // 注意这里的数据类型是document  
			MongoCollection<Document> collection = database.getCollection("store_service_area");
			BasicDBObject query = new BasicDBObject();
			query.append("storeId", platformId);
			query.append("status", 0);
			FindIterable<Document> dIterable = collection.find(query);
			MongoCursor<Document> cursor = dIterable.iterator();

			if(cursor==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","门店服务范围不存在");
				return result;
			}

			JSONArray ja = new JSONArray();
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				ja.add(doc.get("vertex"));
			}
			result.put("data", ja);
			result.put("code",CodeEnum.success.getValue());
			result.put("message", CodeEnum.success.getDescription());



		} catch (Exception e) {
			logger.info("getStoreServiceArea>>>"+e.getMessage());
			e.printStackTrace();
			//mBean.getMongoClient().close();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}

	
	@Override
	public  Map<String, Object>  selecTinyVillageCoordByEmployeeNo(String employeeNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
		    // 注意这里的数据类型是document  
			MongoCollection<Document> collection = database.getCollection("tiny_area");
			BasicDBObject query = new BasicDBObject();
			query.append("employee_a_no", employeeNo);
			FindIterable<Document> dIterable = collection.find(query);
			MongoCursor<Document> cursor = dIterable.iterator();  
			 
			if(cursor==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","此国安侠无管辖的小区");
				return result;
		    }
			JSONObject jObject = null;
			org.json.JSONArray jArray = new org.json.JSONArray();
	        while (cursor.hasNext()) {  
		           Document doc = cursor.next();  
		           jObject = new JSONObject();
				   jObject.put("id", doc.get("code"));
				   jObject.put("storeNo",doc.get("storeNo"));
				   Document crObj = (Document)doc.get("coordinate_range");
				   jObject.put("data", crObj.get("coordinates"));
				   jArray.put(jObject);
		        }  
	        result.put("data", JSONArray.parse(jArray.toString()));
			
		} catch (Exception e) {
			logger.info("selecTinyVillageCoordByEmployeeNo>>>"+e.getMessage());
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}
	

	@Override
	public Map<String, Object> saveTinyVillageCoord(TinyVillageCoordDto tCoordDto) {
		
		Map<String, Object> result = new HashMap<String,Object>();
		TinyAreaManager tinyAreaManager = (TinyAreaManager)SpringHelper.getBean("tinyAreaManager");
		TinyVillageManager tinyVillageManager = (TinyVillageManager)SpringHelper.getBean("tinyVillageManager");
		TinyVillageCodeManager tinyVillageCodeManager = (TinyVillageCodeManager)SpringHelper.getBean("tinyVillageCodeManager");
		AreaDao areaDao = (AreaDao)SpringHelper.getBean(AreaDao.class.getName());
		MongoDBDao mongoDBDao = (MongoDBDao)SpringHelper.getBean(MongoDBDao.class.getName());
		String coord = tCoordDto.getCoord();
		StringBuilder coordSb = new StringBuilder();
		List<Double[]> coordList = new ArrayList<Double[]>();
		Object[][] coordinateArray = null;
		net.sf.json.JSONObject coordinate_range = null;
		Double[] lnla = null;
		Double  tinyArea_center_lng = 0d;
		Double tinyArea_center_lag = 0d;
		try {
			TinyVillage tinyVillage = tinyVillageManager.getTinyVillageById(tCoordDto.getTiny_village_id());
			if(tinyVillage==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","当前小区已经被删除");
				return result;
			}else{
//				if(tinyVillage.getStatus()!=0||(tinyVillage.getDellable()!=null&&tinyVillage.getDellable()==1)){
//					result.put("code",CodeEnum.nullData.getValue());
//					result.put("message","当前小区已经被删除或者即将删除");
//					return result;
//				}
				if(tinyVillage.getStatus()!=0){
					result.put("code",CodeEnum.nullData.getValue());
					result.put("message","当前小区已经被删除");
					return result;
				}
			}
			//查询小区编号
			TinyVillageCode tinyVillageCode = tinyVillageCodeManager.findTinyVillageCodeByTinyId(tCoordDto.getTiny_village_id());
			StoreManager storeManager = (StoreManager)SpringHelper.getBean("storeManager");
			UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
			UserDTO userDTO = umanager.getCurrentUserDTO();
			Store store  = storeManager.findStore(tCoordDto.getStore_id());
			if(tinyVillageCode==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","当前小区还未绑定小区编号");
				return result;
			}else{
				MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
				MongoDatabase database = mDbUtil.getDatabase();
			    // 注意这里的数据类型是document  
				MongoCollection<Document> collection = database.getCollection("tiny_area");
				
				//验证当前小区是否已经划定坐标
				Map<String, Object> existResult = getExistCoordOfTinyVillage(store,collection,tinyVillageCode.getCode());
				System.out.println("is exist-----------------------------");
				if(Integer.parseInt(existResult.get("code").toString())!=CodeEnum.success.getValue()){//当前小区已经被其他门店绑定
					result.put("code",existResult.get("code"));
					result.put("message",existResult.get("message"));
					return result;
				}
				
				if(coord!=null&&!"".equals(coord)){
					String[] coorArray = coord.split(";");
					
					if(coorArray!=null&&coorArray.length>2){//小区范围是一个封闭的圈，至少有三个点
						coordinateArray = new Object[coorArray.length+1][2];
						for(int i=0;i<coorArray.length;i++){ //lng:经度  lag：维度
							Object[] coorArraySub = coorArray[i].split(",");
							lnla = new Double[2];
							lnla[0] = Double.parseDouble(coorArraySub[0].toString());
							lnla[1] = Double.parseDouble(coorArraySub[1].toString());
							coordinateArray[i] = lnla;
							coordList.add(lnla);
						}
						
						
						Double lngSum = 0d;
						Double lagSum =0d;
						for(int n=0;n<coordinateArray.length-1;n++){
							lngSum = lngSum+Double.parseDouble(coordinateArray[n][0].toString());
							lagSum = lagSum+Double.parseDouble(coordinateArray[n][1].toString());
						}
						//计算小区范围中心坐标
						tinyArea_center_lng = lngSum/(coordinateArray.length-1);
						tinyArea_center_lag = lagSum/(coordinateArray.length-1);
						
						lnla = new Double[2];
						lnla[0] = Double.parseDouble(coorArray[0].split(",")[0].toString());
						lnla[1] = Double.parseDouble(coorArray[0].split(",")[1].toString());
						coordinateArray[coorArray.length] = lnla;
						coordList.add(lnla);
						coordSb.append(tCoordDto.getCoord()).append(";").append(coorArray[0]);
						//保存daqweb
						
						//查询小区范围是否存在交集
						Map<String, Object> checkresult = getTinyVillageCoordOfIntersection(tinyVillageCode.getCode(),coordList);
						Object codeVal = checkresult.get("code");
						if(CodeEnum.success.getValue()==Integer.parseInt(codeVal.toString())){
							if("Y".equals(checkresult.get("intersection"))){//有
								
								
								Object storeNo = checkresult.get("storeNo");
								Object tinyVillageNo = checkresult.get("tinyVillageCode");
								if(storeNo!=null&&tinyVillageNo!=null){
									Store temp_store = storeManager.findStoreByStoreNo(String.valueOf(storeNo));
									List<Map<String,Object>> list = mongoDBDao.selectTinyVillageCode(String.valueOf(tinyVillageNo));
									StringBuilder sb = new StringBuilder("");
									if(list!=null&&list.size()>0){
										for(Map<String,Object> map:list){
											sb.append("、"+map.get("tiny_village_name"));
										}
										sb = sb.deleteCharAt(0);
										result.put("code",CodeEnum.repeatData.getValue());
										result.put("message","当前区域已经被 "+temp_store.getName()+" 所画的 "+sb.toString()+" 占用");
										return result;
									}else{
										result.put("code",CodeEnum.nullData.getValue());
										result.put("message","当前区域已经被"+temp_store.getName()+"所画的编号为（"+tinyVillageNo+"）的小区占用");
										return result;
									}
									

									
								}else{
									result.put("code",CodeEnum.repeatData.getValue());
									result.put("message","不同小区地理坐标有交叉");
									return result;
								}
								
								
								
							}
						}else{
							result.put("code",CodeEnum.error.getValue());
							result.put("message","不能绘制自交叉的坐标范围");
							return result;
						}
						
						//查询片区是否属于该门店，不属于时不保存该片区信息
						List<Map<String, Object>> areaMap = areaDao.selectAreaByAreaNo(tCoordDto.getArea_no());
						Long storeId = null;
						if(areaMap != null && areaMap.size()>0){
							Map<String, Object> map = areaMap.get(0);
							storeId = Long.valueOf(map.get("store_id").toString());
						}
						//保存mongodb
						Document doc = new Document();
						doc.put("code", tinyVillageCode.getCode());
						doc.put("name", tinyVillageCode.getTiny_village_name());
						if(storeId != null && storeId.equals(tCoordDto.getStore_id())){
							doc.put("employee_a_no", tCoordDto.getEmployee_a_no());
							doc.put("employee_b_no",tCoordDto.getEmployee_b_no());
						}else{
							doc.put("employee_a_no", null);
							doc.put("employee_b_no",null);
						}
						doc.put("storeNo",store.getStoreno());
						Object[] locationObj = new Object[]{tinyArea_center_lng,tinyArea_center_lag};
						
						net.sf.json.JSONObject lObject = new net.sf.json.JSONObject();
						
						//doc.put("location",Arrays.asList(locationObj));
					   
						coordinate_range = new net.sf.json.JSONObject();
						coordinate_range.put("type", "Polygon");
						coordinate_range.put("coordinates", new Object[]{coordinateArray});
						doc.put("coordinate_range", coordinate_range);
						doc.put("belong","private");
						//FindIterable<Document> findIterable =  collection.find(Filters.eq("code",tArea.getCode()));
						collection.deleteMany(Filters.eq("code",tinyVillageCode.getCode()));//删除之前的坐标记录
						collection.insertOne(doc);//保存新坐标纪录
						
						TinyArea tArea = new  TinyArea();
						tArea.setStoreNo(store.getStoreno());
						tArea.setName(tinyVillageCode.getTiny_village_name());
						if(storeId != null && storeId.equals(tCoordDto.getStore_id())){
							tArea.setArea_no(tCoordDto.getArea_no());
							tArea.setEmployee_a_no(tCoordDto.getEmployee_a_no());
							tArea.setEmployee_b_no(tCoordDto.getEmployee_b_no());
						}
						tArea.setCoordinate_range(coordSb.toString());
						tArea.setCode(tinyVillageCode.getCode());
						tArea.setPosition(tinyArea_center_lng+","+tinyArea_center_lag);
						tArea.setTiny_village_id(tinyVillageCode.getTiny_village_id());
						tArea.setVallage_area(tCoordDto.getVallage_area());
						tArea.setStatus(0);
						tArea.setBelong("private"); 
						Map<String, Object> saveTinyArea = tinyAreaManager.updateTinyAreaOfCoord(tArea);//更新小区范围
						if("-1".equals(saveTinyArea.get("code"))){//保存失败
							//collection.deleteMany(Filters.eq("code",tinyVillageCode.getCode()));//删除之前的坐标记录
							result.put("code",CodeEnum.error.getValue());
							result.put("message","小区映射失败");
							System.out.println(doc.get("_id").toString()+"******"+"坐标保存失败"+"******"+tArea.getName()+"-----"+tArea.getCode());
							return result;
						}
					}
				}
			}

		}catch(NumberFormatException e){
			logger.info("saveTinyVillageCoord>>>"+"坐标数据格式错误，多个小数点"+e.getMessage());
			if(e.getMessage().contains("multiple points")){
				result.put("code",CodeEnum.error.getValue());
				result.put("message", "坐标数据格式错误，多个小数点");
				return result;
			}
		}catch(MongoWriteException e){
			String exceptionInfo= e.getMessage();
			
			if(exceptionInfo.contains("Can't extract geo keys")){
				logger.info("saveTinyVillageCoord>>>"+"不能绘制自交叉的坐标范围"+e.getMessage());
				result.put("code",CodeEnum.error.getValue());
				result.put("message", "不能绘制自交叉的坐标范围");
				return result;
			}else{
				logger.info("saveTinyVillageCoord>>>"+"坐标范围数据不符合"+e.getMessage());
				result.put("code",CodeEnum.error.getValue());
				result.put("message", "坐标范围数据不符合");
				return result;
			}
		} catch (Exception e) {
			logger.info("saveTinyVillageCoord>>>"+e.getMessage());
			e.printStackTrace();
			//mBean.getMongoClient().close();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		
		result.put("code",CodeEnum.success.getValue());
		result.put("message", CodeEnum.success.getDescription());
		return result;
	}


	
	@Override
	public Map<String, Object> selecTinyVillageCoord(Long  storeId) {
		Map<String,Object> result = new HashMap<String,Object>();
		MongoDBDao mDao = (MongoDBDao)SpringHelper.getBean(MongoDBDao.class.getName());
		StoreManager storeManager = (StoreManager)SpringHelper.getBean("storeManager");
		
	    // 注意这里的数据类型是document  
		try {
			List<Map<String, Object>> list = mDao.queryAllTinyAreaOfStore(storeId);
			if(list==null||list.size()==0){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","门店所辖所有小区的小区编号不存在");
				return result;
			}else{ 
				
				
				org.json.JSONArray jArray = new org.json.JSONArray();
				JSONObject jObject = null;
				
				String[] codeArray = new String[list.size()];
				Map<String, Object> codeIds = new HashMap<String,Object>();
				for(int i=0;i<list.size();i++){
					Object code = list.get(i).get("code");
					Object id = list.get(i).get("id");
					if(code!=null){
						codeArray[i] = String.valueOf(code);
						codeIds.put(code.toString(), id);
					}
					
				}
				
				MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
				MongoDatabase database = mDbUtil.getDatabase();
			    // 注意这里的数据类型是document  
				MongoCollection<Document> collection = database.getCollection("tiny_area");
				
				
				Bson query = Filters.in("code",codeArray);
				
				//FindIterable<Document> dIterable = collection.find(new Document("code",new Document("$in", list)));
				FindIterable<Document> dIterable = collection.find(query);
			    JSONObject coordJson = null;
			    
			    MongoCursor<Document> cursor = dIterable.iterator();  
		        
			    if(cursor==null){
			    	result.put("data", jArray);
					result.put("code",CodeEnum.nullData.getValue());
					result.put("message","门店所辖小区坐标范围不存在");
					return result;
			    }
			    
			    List<Map<String, Object>> tinyvillageArealist = mDao.getAreaOfTinyVillage("");
			    String storeNotmp = "";
			    Store  storetmp = null;
		        while (cursor.hasNext()) {  
		           Document doc = cursor.next();  
		           jObject = new JSONObject();
				   jObject.put("id", codeIds.get(doc.get("code")));
				   jObject.put("code", doc.get("code"));
				   storeNotmp = String.valueOf(doc.get("storeNo"));
				   storetmp = storeManager.findStoreByStoreNo(storeNotmp);
				   jObject.put("storeNo",doc.get("storeNo"));
				   jObject.put("storeName", storetmp==null?"":storetmp.getName());
				   //jObject.put("location",doc.get("location"));
				   jObject.put("tinyVillageName", doc.get("name"));
				   jObject.put("belong",doc.get("belong"));
				   jObject.put("areaNo","");
				   jObject.put("areaName","");
				   jObject.put("employee_a_no","");
				   jObject.put("employee_a_name","");
				   jObject.put("employee_b_no","");
				   jObject.put("employee_b_name","");
				   for(Map<String, Object> m:tinyvillageArealist){
						if(doc.get("code").equals(m.get("code"))){
							jObject.put("areaNo", m.get("area_no"));
							jObject.put("areaName", m.get("name"));
							jObject.put("employee_a_no",m.get("employee_a_no"));
						    jObject.put("employee_a_name",m.get("employee_a_name"));
						    jObject.put("employee_b_no",m.get("employee_b_no"));
						    jObject.put("employee_b_name",m.get("employee_b_name"));
							break;
						}
					}
				   Document crObj = (Document)doc.get("coordinate_range");
				   
				   coordJson = new JSONObject(doc.get("coordinate_range"));
				   jObject.put("data", crObj.get("coordinates"));
				   jArray.put(jObject);
		        }  
				
				
				result.put("data", JSONArray.parse(jArray.toString()));
				result.put("code",CodeEnum.success.getValue());
				result.put("message", CodeEnum.success.getDescription());
			}
		} catch (Exception e) {
			logger.info("selecTinyVillageCoord>>>"+e.getMessage());
			e.printStackTrace();
			//mBean.getMongoClient().close();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}


	
	@Override
	public Map<String, Object> deleteTinyVillageCoord(TinyVillageCoordDto tCoordDto) {
		Map<String, Object> result = new HashMap<String,Object>();
		MongoDBDao mDao = (MongoDBDao)SpringHelper.getBean(MongoDBDao.class.getName());
		TinyAreaManager tinyAreaManager = (TinyAreaManager)SpringHelper.getBean("tinyAreaManager");
		UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = umanager.getCurrentUserDTO();
		TinyVillageCodeManager tinyVillageCodeManager = (TinyVillageCodeManager)SpringHelper.getBean("tinyVillageCodeManager");
		AreaManager areaManager = (AreaManager)SpringHelper.getBean("areaManager");
		AreaInfoManager areaInfoManager = (AreaInfoManager)SpringHelper.getBean("areaInfoManager");
		try {


			if(tCoordDto.getArea_no()!=null&&!"".equals(tCoordDto.getArea_no())){
				List<Map<String,Object>> aiList = areaInfoManager.findAreaInfo(tCoordDto.getArea_no());
				if(aiList==null||aiList.size()==1){
					result.put("code",CodeEnum.nullData.getValue());
					result.put("message","当前小区绑定的片区只有唯一的一个小区，不能删除，请到'门店划片'编辑片区");
					return result;
				}else if(aiList.size()>1){
					areaManager.deleteTinyVillageOfArea(tCoordDto.getTiny_village_id(),tCoordDto.getArea_no());//解除小区和片区的绑定
				}

			}


			TinyVillageCode tinyVillageCode = tinyVillageCodeManager.findTinyVillageCodeByTinyId(tCoordDto.getTiny_village_id());

			if(tinyVillageCode==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","当前小区还未绑定小区编号");
				return result;
			}else{
				TinyArea tArea = tinyAreaManager.findTinyAreaByTinyId(tCoordDto.getTiny_village_id());

				if(tArea!=null){
					//删除tiny_area
					tArea.setStatus(1);
					preObject(tArea);
					tinyAreaManager.saveObject(tArea);
				}

				//删除mongodb
				MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
				MongoDatabase database = mDbUtil.getDatabase();
				MongoCollection<Document> collection = database.getCollection("tiny_area");
				Bson query = Filters.eq("code",tinyVillageCode.getCode());
				if(!query.equals("{}")){
					DeleteResult deleteOne = collection.deleteOne(query);
					if(deleteOne.getDeletedCount() >0){
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>本次操作删除记录条数为："+deleteOne.getDeletedCount()+";小区code："+tinyVillageCode.getCode());
						result.put("code",CodeEnum.success.getValue());
						result.put("message", CodeEnum.success.getDescription());
					}else{
						System.out.println("《《《《《《《《《《《《《《《《《《《《《《《《《《本次操作删除记录条数为："+deleteOne.getDeletedCount()+";小区code："+tinyVillageCode.getCode());
						result.put("code",CodeEnum.error.getValue());
						result.put("message", CodeEnum.error.getDescription());
					}
				}else{
					System.out.println("《《《《《《《《《《《《《《《《《《《《《《《《《《删除异常;小区code："+tinyVillageCode.getCode());
					result.put("code",CodeEnum.error.getValue());
					result.put("message", CodeEnum.error.getDescription());
					return result;
				}
			}
		} catch (Exception e) {
			logger.info("deleteTinyVillageCoord>>>"+e.getMessage());
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		
		return result;
	}


	
	@Override
	public Map<String, Object> getTinyVillageCoordOfIntersection(String code,List<Double[]> coordList) {
		Map<String, Object> result = new HashMap<String,Object>();
		
		try {
			MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
			
			MongoCollection<Document> collection = database.getCollection("tiny_area");
			BasicDBObject query = new BasicDBObject();
			 
	        List<List<Double[]>> polygons = new LinkedList<List<Double[]>>();
	        polygons.add(coordList);
	        
	        query.append("code", new BasicDBObject("$ne", code));
	        query.append("coordinate_range", new BasicDBObject("$geoIntersects",
	                new BasicDBObject("$geometry",new BasicDBObject("type","Polygon").append("coordinates",polygons))));
	        FindIterable<Document>  findIterable = collection.find(query);
	        Document document = findIterable.first();
	        if(document==null){
	        	result.put("code",CodeEnum.success.getValue());
	        	result.put("intersection","N");
	        	
	        }else{
	        	result.put("code",CodeEnum.success.getValue());
	        	result.put("intersection","Y");
	        	result.put("tinyVillageCode", document.get("code"));
	        	result.put("storeNo", document.get("storeNo"));
	        }
	        
		}catch(MongoQueryException e){
			logger.info("getTinyVillageCoordOfIntersection>>>"+e.getMessage());
			if(e.getMessage().contains("Query failed with error code 2 and error message 'Loop is not valid")){
				result.put("code",CodeEnum.error.getValue());
				result.put("messgae","自交叉坐标点查询错误，不能绘制自交叉");
				return result;
			}
		} catch (Exception e) {
			logger.info("getTinyVillageCoordOfIntersection>>>"+e.getMessage());
			e.printStackTrace();
			//mBean.getMongoClient().close();
			result.put("code",CodeEnum.error.getValue());
			return result;
		}
		return result;
	}


	
	@Override
	public Map<String, Object> getStorePosition(Long storeId) {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject storeService = new JSONObject();
		UserManager userManager=(UserManager)SpringHelper.getBean("userManager");
		Store store = userManager.findUserStore();
		//Store store = storeManager.findStore(storeId);
		try {
			MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>storeid"+store.getPlatformid());
			MongoCollection<Document> collection1 = database.getCollection("store_service_area");
			BasicDBObject query = new BasicDBObject();
			query.append("storeId", store.getPlatformid());
			query.append("status", 0);
			FindIterable<Document> dIterable1 = collection1.find(query);
			//Document document1 = dIterable1.first();
//
//			if(document1==null){
//				result.put("code",CodeEnum.nullData.getValue());
//				result.put("message","门店服务范围不存在");
//				return result;
//			}

			MongoCursor<Document> cursor = dIterable1.iterator();

			if(cursor==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","门店服务范围不存在");
				return result;
			}


		    // 注意这里的数据类型是document  
			MongoCollection<Document> collection2 = database.getCollection("store_position");
			//Filters.eq("_id", store.getPlatformid());
			FindIterable<Document> dIterable2 = collection2.find(new Document("_id",store.getPlatformid()).append("status", 0));
			Document document2 = dIterable2.first();
			
			if(document2==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","门店位置坐标不存在");
			}else{ 
				JSONObject jObject = new JSONObject(document2.toJson());
				Object object = jObject.get("location");
				result.put("data", JSONArray.parse(object.toString()));
				result.put("code",CodeEnum.success.getValue());
				result.put("message", CodeEnum.success.getDescription());
			}
		} catch (Exception e) {
			logger.info("getStorePosition>>>"+e.getMessage());
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}


	
	@Override
	public Map<String, Object> updateEmployeeOfTinyArea(Area area) {
		MongoDBDao mDao = (MongoDBDao)SpringHelper.getBean(MongoDBDao.class.getName());
		TinyAreaManager tinyAreaManager = (TinyAreaManager)SpringHelper.getBean("tinyAreaManager");
		UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = umanager.getCurrentUserDTO();
		StoreManager storeManager = (StoreManager)SpringHelper.getBean("storeManager");
		
		Store store  = storeManager.findStore(area.getStore_id());
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = mDao.queryAllTinyVillageOfArea(area.getId());
			
			if(list!=null&&list.size()>0){
				Map<String, Object> areaInfo=null;
				Long tiny_village_id=null;
				TinyArea tinyArea=null;
				MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
				MongoDatabase database = mDbUtil.getDatabase();
				MongoCollection<Document> collection = database.getCollection("tiny_area");
				for(int i=0;i<list.size();i++){
					areaInfo = list.get(i);
					tiny_village_id = Long.parseLong(String.valueOf(areaInfo.get("tin_village_id")));
					tinyArea = tinyAreaManager.findTinyAreaByTinyId(tiny_village_id,store.getStoreno());
					if(tinyArea!=null){
						tinyArea.setEmployee_a_no(area.getEmployee_a_no());
						tinyArea.setEmployee_b_no(area.getEmployee_b_no());
						tinyArea.setUpdate_time(new Date());
						tinyArea.setUpdate_user_id(userDTO.getId());
						tinyArea.setArea_no(area.getArea_no());
						//更新国安侠
						
						BasicDBObject query = new BasicDBObject();
						query.append("code", tinyArea.getCode());
						query.append("storeNo",store.getStoreno());
						Document updateDoc = new Document("employee_a_no",tinyArea.getEmployee_a_no()).append("employee_b_no", tinyArea.getEmployee_b_no());
						collection.updateMany(query, new Document("$set",updateDoc)); 
						preObject(tinyArea);
						tinyAreaManager.saveObject(tinyArea);
					}
					
				}
			}
			result.put("code",CodeEnum.success.getValue());
			result.put("message", CodeEnum.success.getDescription());
		} catch (Exception e) {
			logger.info("updateEmployeeOfTinyArea>>>"+e.getMessage());
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}


	
	@Override
	public Map<String, Object> getExistCoordOfTinyVillage(Store store,MongoCollection<Document> collection,
			String tinyVillageCode) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		StoreManager storeManager = (StoreManager)SpringHelper.getBean("storeManager");
		System.out.println("store status>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(store.getStoreno()+"------------------------");
		System.out.println("store status<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		try {
			MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
		    // 注意这里的数据类型是document  
			Store tinyAreaStore = null;
			FindIterable<Document> dIterable = collection.find(Filters.eq("code", tinyVillageCode));
			MongoCursor<Document> cursor = dIterable.iterator(); 
			StringBuilder storeSb = new StringBuilder();
			 while(cursor.hasNext()){
				 Document doc = cursor.next();
				 Object  store_no = doc.get("storeNo");
				 
				 if(!store_no.equals(store.getStoreno())){
					tinyAreaStore = storeManager.findStoreByStoreNo(store_no.toString());
					if(tinyAreaStore!=null){
						storeSb.append("、").append(tinyAreaStore.getName());
					}

				 }
				
			 }
			 
			 if(storeSb.toString().indexOf("、")>=0){
				 result.put("code",CodeEnum.dataExist.getValue());
				 result.put("message","该小区已经被 "+storeSb.toString().substring(1)+" 绑定");
			 }else{
				 result.put("code",CodeEnum.success.getValue());
				 result.put("message",CodeEnum.success.getDescription());
			 }
				
		} catch (Exception e) {
			logger.info("getExistCoordOfTinyVillage>>>"+e.getMessage());
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
		
	}


	
	@Override
	public Map<String, Object> getAllTinyVillageCoordinateOfCity(Long city_id) {
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		MongoDBDao mDao = (MongoDBDao)SpringHelper.getBean(MongoDBDao.class.getName());
		UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = umanager.getCurrentUserDTO();
		Map<String, Object> result = new HashMap<String, Object>();
		
		
		try {
			List<Map<String, Object>> storeList = storeDao.getAllStoreOfCRM(userDTO.getId(),city_id, "CSZJ");//获取门店
			Object[] storeNoArray = new Object[storeList.size()];
			List<Object>  storeNoList = new ArrayList<Object>();
			Map<String, Object>  storeMap = new HashMap<String,Object>();
			for(int i=0;i<storeList.size();i++){//获取门店platformId
				Map<String, Object> teMap = storeList.get(i);
				if(teMap.get("storeno")==null||"".equals(teMap.get("storeno"))){
					continue;
				}
				storeNoArray[i] = teMap.get("storeno");
				storeMap.put(teMap.get("storeno").toString(), teMap.get("name"));
			}
		
			
			
			MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
		    // 先查询门店服务范围
			MongoCollection<Document> collection = database.getCollection("tiny_area");
			BasicDBObject query = new BasicDBObject();
			query.append("storeNo", new BasicDBObject("$in",storeNoArray));
			FindIterable<Document> dIterable = collection.find(query);
			MongoCursor<Document> cursor = dIterable.iterator(); 
			
			
			List<Object> tinyVillageCoordList = new ArrayList<Object>();
			if(dIterable==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","所有小区没有绑定坐标");
			}else{//服务范围存在 
				org.json.JSONArray tmp_jarray = new org.json.JSONArray();
				Map<String, Object> tinyAreaInfo = new HashMap<String,Object>();
				List<Map<String, Object>> tinyvillageArealist = mDao.getAreaOfTinyVillage("");
				while(cursor.hasNext()){
					Document teDocument = cursor.next();
					JSONObject jObject = new JSONObject(teDocument.toJson());
					jObject.put("areaName", "");
					jObject.put("polygonMap", "");
					String code = jObject.getString("code");
					jObject.put("storeName", storeMap.get("storeNo")==null?"": storeMap.get("storeNo"));
					for(Map<String, Object> m:tinyvillageArealist){
						if(code.equals(m.get("code"))){
							jObject.put("areaName", m.get("name"));
							break;
						}
					}
					
					tmp_jarray.put(jObject);
//					tinyVillageCoordList.add(jObject.toString());
				}
				
				
				result.put("data",JSONArray.parse(tmp_jarray.toString()));
				result.put("code",CodeEnum.success.getValue());
				result.put("message", CodeEnum.success.getDescription());
			}
		} catch (Exception e) {
			logger.info("getAllTinyVillageCoordinateOfCity>>>"+e.getMessage());
			e.printStackTrace();
			//mBean.getMongoClient().close();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}



	@Override
	public Map<String, Object> getAllStoreServiceAreaOfCity(Long city_id) {
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		StoreManager storeManager  = (StoreManager)SpringHelper.getBean("storeManager");
		UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = umanager.getCurrentUserDTO();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> platformId_storeNo = new HashMap<String,Object>();
		
		try {
			List<Map<String, Object>> storeList = storeDao.getAllStoreOfCRM(userDTO.getId(),city_id, "CSZJ");//获取门店
			Object[] platformIdArray = new Object[storeList.size()];
			List<Object>  storeNoList = new ArrayList<Object>();
			for(int i=0;i<storeList.size();i++){//获取门店platformId
				Map<String, Object> teMap = storeList.get(i);
				if(teMap.get("platformid")==null||"".equals(teMap.get("platformid"))){
					continue;
				}
				platformIdArray[i] = teMap.get("platformid");
				platformId_storeNo.put(teMap.get("platformid").toString(),teMap.get("storeno"));
			}
		
			
			MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
		    // 先查询门店服务范围
			MongoCollection<Document> collection = database.getCollection("store_service_area");
			BasicDBObject query = new BasicDBObject();
			query.append("storeId", new BasicDBObject("$in",platformIdArray)).append("status", 0);
			FindIterable<Document> dIterable = collection.find(query);
			MongoCursor<Document> cursor = dIterable.iterator(); 
			
			//查询门店中心坐标点
			MongoCollection<Document> collection2 = database.getCollection("store_position");
			BasicDBObject query2 = new BasicDBObject();
			query2.append("_id", new BasicDBObject("$in",platformIdArray)).append("status", 0);
			FindIterable<Document> dIterable2 = collection2.find(query2);
			MongoCursor<Document> cursor2 = dIterable2.iterator(); 
			
			
			Map<String, Object> positionMap = new HashMap<String,Object>();
			List<Object> storeServiceAreaList = new ArrayList<Object>();
			if(dIterable==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","门店服务范围不存在");
			}else{//服务范围存在 
				
				while(cursor2.hasNext()){
					Document teDocument2 = cursor2.next();
					JSONObject jObject = new JSONObject(teDocument2.toJson());
					Object location = teDocument2.get("location");
					Object storeId = teDocument2.get("_id");
					positionMap.put(String.valueOf(storeId), location);
				}
				
				org.json.JSONArray tmp_jArray = new org.json.JSONArray();
				Store store = null;
				while(cursor.hasNext()){
					Document teDocument = cursor.next();
					JSONObject jObject = new JSONObject(teDocument.toJson());
					String storeNo_tmp = platformId_storeNo.get(teDocument.get("storeId")).toString();
					store = storeManager.findStoreByStoreNo(storeNo_tmp);
					System.out.println(storeNo_tmp);
					jObject.put("polygonMap", "");
					jObject.put("position",positionMap.get(teDocument.get("storeId"))==null?"":positionMap.get(teDocument.get("storeId")));
					jObject.put("storeName", store.getName());
					jObject.put("storeNo", storeNo_tmp);
					tmp_jArray.put(jObject);
				}
				
				
				
				result.put("data",JSONArray.parse(tmp_jArray.toString()));
				result.put("code",CodeEnum.success.getValue());
				result.put("message", CodeEnum.success.getDescription());
			}
		} catch (Exception e) {
			logger.info("getAllStoreServiceAreaOfCity>>>"+e.getMessage());
			e.printStackTrace();
			//mBean.getMongoClient().close();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}



	@Override
	public Map<String, Object> getAllStoreServiceAreaOfStore() {
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		StoreManager storeManager  = (StoreManager)SpringHelper.getBean("storeManager");
		UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = umanager.getCurrentUserDTO();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> platformId_storeNo = new HashMap<String,Object>();

		try {
			//List<Map<String, Object>> storeList = storeDao.getAllStoreOfCRM(userDTO.getId(),city_id, "CSZJ");//获取门店
			Object[] platformIdArray ={
					"00000000000000000000000000000029",
					"00000000000000000000000000000030",
					"00000000000000000000000000000031",
					"00000000000000000000000000000032",
					"00000000000000000000000000000033",
					"00000000000000000000000000000034",
					"00000000000000000000000000000048",
					"00000000000000000000000000000050",
					"00000000000000000000000000000052",
					"00000000000000000000000000000053",
					"00000000000000000000000000000057",
					"00000000000000000000000000000068",
					"00000000000000000000000000000071",
					"00000000000000000000000000000072",
					"00000000000000000000000000000076",
					"00000000000000000000000000000077",
					"00000000000000000000000000000086",
					"00000000000000000000000000000092",
					"00000000000000000000000000000098",
					"00000000000000000000000000000100",
					"00000000000000000000000000000105",
					"8ad8ac8e57aaf8df0157ac2ee9fc019a",
					"8ad89f985864322d015875a73277787c",
					"8ad8ac8257055bb401571d6f38ed569e",
					"8ad8958b56f07e81015703d4de8a2a6a",
					"8ad8ac8b58bd5dbf0158e24571f507e7",
					"00000000000000000000000000000117",
					"8ad895875928d32201593e79064d4618",
					"8ad8e1885b444af4015b807a82f359a1",
					"8ad8e18b5b9c5cc2015ba95cbbb939d0",
					"8ad8ac865b4441e4015b99876b564782",
					"8ad8a0835b4441dd015b852a11e46e30",
					"8ad8a0835b4441dd015b998ab8c94570",
					"8ad89f8e5b9c5cc3015bd66ec0ca6f09",
					"8ad895825c77032e015c9afadf78485f",
					"8ad89f825dbaf5ef015dcfd0ef003b1c",
					"8ad8c18a5dbaf590015dcfc3023036de",
					"8d2b14c2b84c43c4bb9f8eff933a4912",
					"f6c717395ecb4ab7b73cd3ce53ae15c1",
					"8ad8ac8b58bd5dbf0158d8845c8968de",
					"8ad8e1885b444af4015b5ba72c8a4c22",
					"8ad8e1885b444af4015b5af7e2724928",
					"8ad8a0835b4441dd015b5b96af644f25",
					"8ad89f8e5b9c5cc3015ba3fffb242021",
					"8ad89f885cd2f71a015ce23a27da5b4e",
					"8ad895865cd2f1fd015ce277cfde5f6f",
					"8ad89f885cd2f71a015ce2509dbe5c50",
					"8ad895865cd2f1fd015ce369dfb4674b",
					"8ad8958960c503b10160e87f02de251d",
					"9d272da153db43dcad1a854bf669aee7",
					"2751b5b58db74af0885d1abb0fdb80f0",
					"8a81ec8856da5b100156df96bd6500ce",
					"cf14682bf4ce4ab08482a0ab2a3a6103",
					"9fd1e4174117416d93ba27c2c5a4a5ef",
					"8ad895865cd2f1fd015ce306c5886331"};
			platformId_storeNo.put("00000000000000000000000000000029","0010Z0003");
			platformId_storeNo.put("00000000000000000000000000000030","0010Y0004");
			platformId_storeNo.put("00000000000000000000000000000031","0010Y0005");
			platformId_storeNo.put("00000000000000000000000000000032","0010Y0006");
			platformId_storeNo.put("00000000000000000000000000000033","0010Y0007");
			platformId_storeNo.put("00000000000000000000000000000034","0010Y0008");
			platformId_storeNo.put("00000000000000000000000000000048","0010Y0011");
			platformId_storeNo.put("00000000000000000000000000000050","0010Y0013");
			platformId_storeNo.put("00000000000000000000000000000052","0010Y0014");
			platformId_storeNo.put("00000000000000000000000000000053","0010Y0015");
			platformId_storeNo.put("00000000000000000000000000000057","0010Y0019");
			platformId_storeNo.put("00000000000000000000000000000068","0010Y0028");
			platformId_storeNo.put("00000000000000000000000000000071","0010Y0031");
			platformId_storeNo.put("00000000000000000000000000000072","0010Y0032");
			platformId_storeNo.put("00000000000000000000000000000076","0010Y0035");
			platformId_storeNo.put("00000000000000000000000000000077","0010Y0036");
			platformId_storeNo.put("00000000000000000000000000000086","0010Y0043");
			platformId_storeNo.put("00000000000000000000000000000092","0010Y0049");
			platformId_storeNo.put("00000000000000000000000000000098","0010Y0055");
			platformId_storeNo.put("00000000000000000000000000000100","0010Y0057");
			platformId_storeNo.put("00000000000000000000000000000105","0010Y0061");
			platformId_storeNo.put("8ad8ac8e57aaf8df0157ac2ee9fc019a","0010E0086");
			platformId_storeNo.put("8ad89f985864322d015875a73277787c","0010Y0089");
			platformId_storeNo.put("8ad8ac8257055bb401571d6f38ed569e","0010Y0080");
			platformId_storeNo.put("8ad8958b56f07e81015703d4de8a2a6a","0010Y0078");
			platformId_storeNo.put("8ad8ac8b58bd5dbf0158e24571f507e7","0010Y0094");
			platformId_storeNo.put("00000000000000000000000000000117","0010Y0069");
			platformId_storeNo.put("8ad895875928d32201593e79064d4618","0010Y0095");
			platformId_storeNo.put("8ad8e1885b444af4015b807a82f359a1","0010Y0107");
			platformId_storeNo.put("8ad8e18b5b9c5cc2015ba95cbbb939d0","0010Y0118");
			platformId_storeNo.put("8ad8ac865b4441e4015b99876b564782","0010Y0115");
			platformId_storeNo.put("8ad8a0835b4441dd015b852a11e46e30","0010Y0109");
			platformId_storeNo.put("8ad8a0835b4441dd015b998ab8c94570","0010Y0116");
			platformId_storeNo.put("8ad89f8e5b9c5cc3015bd66ec0ca6f09","0010X0119");
			platformId_storeNo.put("8ad895825c77032e015c9afadf78485f","0010Y0122");
			platformId_storeNo.put("8ad89f825dbaf5ef015dcfd0ef003b1c","0010E0124");
			platformId_storeNo.put("8ad8c18a5dbaf590015dcfc3023036de","0010Y0125");
			platformId_storeNo.put("8d2b14c2b84c43c4bb9f8eff933a4912","0010Y0181");
			platformId_storeNo.put("f6c717395ecb4ab7b73cd3ce53ae15c1","0010Y0202");
			platformId_storeNo.put("8ad8ac8b58bd5dbf0158d8845c8968de","0022Y0003");
			platformId_storeNo.put("8ad8e1885b444af4015b5ba72c8a4c22","0022Y0013");
			platformId_storeNo.put("8ad8e1885b444af4015b5af7e2724928","0022Y0006");
			platformId_storeNo.put("8ad8a0835b4441dd015b5b96af644f25","0022Y0010");
			platformId_storeNo.put("8ad89f8e5b9c5cc3015ba3fffb242021","0022Y0015");
			platformId_storeNo.put("8ad89f885cd2f71a015ce23a27da5b4e","0022Y0022");
			platformId_storeNo.put("8ad895865cd2f1fd015ce277cfde5f6f","0022Y0023");
			platformId_storeNo.put("8ad89f885cd2f71a015ce2509dbe5c50","0022Y0025");
			platformId_storeNo.put("8ad895865cd2f1fd015ce369dfb4674b","0022Y0027");
			platformId_storeNo.put("8ad8958960c503b10160e87f02de251d","0022Y0040");
			platformId_storeNo.put("9d272da153db43dcad1a854bf669aee7","0022Y0051");
			platformId_storeNo.put("2751b5b58db74af0885d1abb0fdb80f0","0022Y0058");
			platformId_storeNo.put("8a81ec8856da5b100156df96bd6500ce","0010E0073");
			platformId_storeNo.put("cf14682bf4ce4ab08482a0ab2a3a6103","0022Y0056");
			platformId_storeNo.put("9fd1e4174117416d93ba27c2c5a4a5ef","0022Y0050");
			platformId_storeNo.put("8ad895865cd2f1fd015ce306c5886331","0022Y0021");

			/*List<Object>  storeNoList = new ArrayList<Object>();
			for(int i=0;i<storeList.size();i++){//获取门店platformId
				Map<String, Object> teMap = storeList.get(i);
				if(teMap.get("platformid")==null||"".equals(teMap.get("platformid"))){
					continue;
				}
				platformIdArray[i] = teMap.get("platformid");
				platformId_storeNo.put(teMap.get("platformid").toString(),teMap.get("storeno"));
			}*/


			MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
			// 先查询门店服务范围
			MongoCollection<Document> collection = database.getCollection("store_service_area");
			BasicDBObject query = new BasicDBObject();
			query.append("storeId", new BasicDBObject("$in",platformIdArray)).append("status", 0);
			FindIterable<Document> dIterable = collection.find(query);
			MongoCursor<Document> cursor = dIterable.iterator();

			//查询门店中心坐标点
			MongoCollection<Document> collection2 = database.getCollection("store_position");
			BasicDBObject query2 = new BasicDBObject();
			query2.append("_id", new BasicDBObject("$in",platformIdArray)).append("status", 0);
			FindIterable<Document> dIterable2 = collection2.find(query2);
			MongoCursor<Document> cursor2 = dIterable2.iterator();


			Map<String, Object> positionMap = new HashMap<String,Object>();
			List<Object> storeServiceAreaList = new ArrayList<Object>();
			if(dIterable==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","门店服务范围不存在");
			}else{//服务范围存在

				while(cursor2.hasNext()){
					Document teDocument2 = cursor2.next();
					JSONObject jObject = new JSONObject(teDocument2.toJson());
					Object location = teDocument2.get("location");
					Object storeId = teDocument2.get("_id");
					positionMap.put(String.valueOf(storeId), location);
				}

				org.json.JSONArray tmp_jArray = new org.json.JSONArray();
				Store store = null;
				while(cursor.hasNext()){
					Document teDocument = cursor.next();
					JSONObject jObject = new JSONObject(teDocument.toJson());
					String storeNo_tmp = platformId_storeNo.get(teDocument.get("storeId")).toString();
					store = storeManager.findStoreByStoreNo(storeNo_tmp);
					System.out.println(storeNo_tmp);
					jObject.put("polygonMap", "");
					jObject.put("position",positionMap.get(teDocument.get("storeId"))==null?"":positionMap.get(teDocument.get("storeId")));
					jObject.put("storeName", store.getName());
					jObject.put("storeNo", storeNo_tmp);
					tmp_jArray.put(jObject);
				}



				result.put("data",JSONArray.parse(tmp_jArray.toString()));
				result.put("code",CodeEnum.success.getValue());
				result.put("message", CodeEnum.success.getDescription());
			}
		} catch (Exception e) {
			logger.info("getAllStoreServiceAreaOfCity>>>"+e.getMessage());
			e.printStackTrace();
			//mBean.getMongoClient().close();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}


	@Override
	public Map<String, Object> getAllCoordinates() {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
	    // 先查询门店服务范围
		MongoCollection<Document> collection = database.getCollection("tiny_area");
		FindIterable<Document> dIterable = collection.find();
		MongoCursor<Document> cursor = dIterable.iterator(); 
		org.json.JSONArray tmp_jArray = new org.json.JSONArray();
		while(cursor.hasNext()){
			Document doc = cursor.next();
			JSONObject jObject = new JSONObject();
			   jObject.put("code", doc.get("code"));
			   Document crObj = (Document)doc.get("coordinate_range");
			   JSONObject coordJson = new JSONObject(doc.get("coordinate_range"));
			   jObject.put("coordinates", crObj.get("coordinates"));
			tmp_jArray.put(jObject);
		}
		mapParam.put("code",CodeEnum.success.getValue());
		mapParam.put("message", CodeEnum.success.getDescription());
		mapParam.put("vallageCoordinates", JSONArray.parse(tmp_jArray.toString()));
		return mapParam;
	}


	@Override
	public Map<String, Object> getCoordinatesWithoutArea() {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		TinyAreaDao tinyAreaDao =  (TinyAreaDao)SpringHelper.getBean(TinyAreaDao.class.getName());
		List<Map<String,Object>> selectinyAreaWithoutArea = tinyAreaDao.selectinyAreaWithoutArea();
		Object[] platformIdArray = new Object[selectinyAreaWithoutArea.size()];
		if(selectinyAreaWithoutArea != null && selectinyAreaWithoutArea.size() > 0){
			for(int i=0;i<selectinyAreaWithoutArea.size();i++){
				platformIdArray[i] = selectinyAreaWithoutArea.get(i).get("code");
			}
		}
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
	    // 先查询门店服务范围
		MongoCollection<Document> collection = database.getCollection("tiny_area");
		BasicDBObject query = new BasicDBObject();
		query.append("code", new BasicDBObject("$in",platformIdArray));
		FindIterable<Document> dIterable = collection.find(query);
		MongoCursor<Document> cursor = dIterable.iterator(); 
		org.json.JSONArray tmp_jArray = new org.json.JSONArray();
		while(cursor.hasNext()){
			Document doc = cursor.next();
			JSONObject jObject = new JSONObject();
			   jObject.put("code", doc.get("code"));
			   Document crObj = (Document)doc.get("coordinate_range");
			   JSONObject coordJson = new JSONObject(doc.get("coordinate_range"));
			   jObject.put("coordinates", crObj.get("coordinates"));
			tmp_jArray.put(jObject);
		}
		mapParam.put("code",CodeEnum.success.getValue());
		mapParam.put("message", CodeEnum.success.getDescription());
		mapParam.put("vallageCoordinates", JSONArray.parse(tmp_jArray.toString()));
		return mapParam;
		
	}
	/**
	 * 小区里的订单数
	 */
	@Override
	public  int selecTinyOrderCount(String beginDate,String endDate,String tinycode) {
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("df_order_signed_monthly");
		List<Document> pipeline = new ArrayList<Document>();  
		Document filter = new Document();
		filter.put("df_signed_time",new Document("$gte",beginDate));
		filter.put("df_signed_time",new Document("$lte",endDate));
		filter.put("tiny_village_code",tinycode);
		Document match = new Document("$match",filter); 
		pipeline.add(match);
		Document group = new Document();  
		group.put("$group", new Document("_id", "null").append("count", new Document("$sum", 1)));  
		pipeline.add(group);
		AggregateIterable<Document> aggregate = collection.aggregate(pipeline).allowDiskUse(true);
		MongoCursor<Document> cursor = aggregate.iterator();
		int tinyOrderCount =0;
		while (cursor.hasNext()) {  
	           Document item = cursor.next();
	           JSONObject itemJson = new JSONObject(item.toJson());
	           tinyOrderCount = (Integer)itemJson.get("count");
	    }
		cursor.close();
		return tinyOrderCount;
	}

	/**
	 * 门店所管理的小区
	 */
	@Override
	public Map<String, Object> selectStoreTinies() {
		Map<String, Object> result = new HashMap<String, Object>();
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("tiny_area");
		BasicDBObject query = new BasicDBObject();
		query.append("storeNo", "0010Y0067");
		FindIterable<Document> dIterable = collection.find(query);
		MongoCursor<Document> cursor = dIterable.iterator();  
		if(cursor==null){
			result.put("code",CodeEnum.nullData.getValue());
			result.put("message","此门店无管辖的小区");
			return result;
	    }
		List<Map<String,Object>> resuList = new ArrayList<Map<String,Object>>();
		Map<String, Object> resuMap = new HashMap<String,Object>();
		String tinycode =null;
		String tinyname =null;
		int tinyOrderCount =0;
		while (cursor.hasNext()) {
			Document item = cursor.next();
			JSONObject itemJson = new JSONObject(item.toJson());
			resuMap.put("tinycode", itemJson.get("code"));
			resuMap.put("tinyname", itemJson.get("name"));
			tinyOrderCount = selecTinyOrderCount("2017-11-01","2017-11-10","");
			
			System.out.println(itemJson.get("code")+"==="+itemJson.get("name"));
		}
		
		return result;
	}
	

	public List<JSONObject> selecTinyOrderLastMonthCountByVillage() {
		List<JSONObject> result = new ArrayList<JSONObject>();
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("df_order_signed_monthly");
		List<Document> pipeline = new ArrayList<Document>();  
		Document filter = new Document();
		Calendar cale = null;  
	    cale = Calendar.getInstance();
	    int year = cale.get(Calendar.YEAR);  
	    int month = cale.get(Calendar.MONTH) + 1; 
//	    filter.put("tiny_village_code","1101050350000000000005");
		filter.put("df_signed_time",new Document("$gte",year+"-"+(month-1)+"-01"));
		filter.put("df_signed_time",new Document("$lte",year+"-"+(month-1)+"-30"));
//		filter.put("tiny_village_code",tiny_village_code);
		Document match = new Document("$match",filter); 
		pipeline.add(match);
		Document group = new Document();  
		group.put("$group", new Document("_id", new Document("tiny_village_code","$tiny_village_code")).append("count", new Document("$sum", 1)));  
		pipeline.add(group);
		AggregateIterable<Document> aggregate = collection.aggregate(pipeline).allowDiskUse(true);
		MongoCursor<Document> cursor = aggregate.iterator();
		while (cursor.hasNext()) {  
	           Document item = cursor.next();
	           JSONObject itemJson = new JSONObject(item.toJson());
	           result.add(itemJson);
	    }
		cursor.close();
		return result;
	}
	@Override
	public List<JSONObject> selecTinyCustomerLastMonthCountByVillage() {
		List<JSONObject> result = new ArrayList<JSONObject>();
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("df_order_signed_monthly");
		List<Document> pipeline = new ArrayList<Document>();  
		Document filter = new Document();
		Calendar cale = null;  
		cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);  
		int month = cale.get(Calendar.MONTH) + 1;  
//		filter.put("tiny_village_code",tiny_village_code);
		filter.put("df_signed_time",new Document("$gte",year+"-"+(month-1)+"-01"));
		filter.put("df_signed_time",new Document("$lte",year+"-"+(month-1)+"-30"));
		Document match = new Document("$match",filter); 
		pipeline.add(match);
		Document group = new Document();
		Document filter2 = new Document();
		filter2.put("tiny_village_code","$tiny_village_code");
		filter2.put("customer_id","$customer_id");
		Document group2 = new Document();  
		group.put("$group", new Document("_id", filter2));  
		group2.put("$group", new Document("_id", new Document("tiny_village_code","$_id.tiny_village_code")).append("count", new Document("$sum", 1)));  
		pipeline.add(group);
		pipeline.add(group2);
		AggregateIterable<Document> aggregate = collection.aggregate(pipeline).allowDiskUse(true);
		MongoCursor<Document> cursor = aggregate.iterator();
		while (cursor.hasNext()) {  
	           Document item = cursor.next();
	           JSONObject itemJson = new JSONObject(item.toJson());
	           result.add(itemJson);;
	    }
		cursor.close();
		return result;
	}


	@Override
	public Map<String, Object> getAllStoreServiceAreaOfContry(Long city_id) {
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		StoreManager storeManager  = (StoreManager)SpringHelper.getBean("storeManager");
		UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = umanager.getCurrentUserDTO();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> platformId_storeNo = new HashMap<String,Object>();
		Map<String, Object> platformId_storeId = new HashMap<String,Object>();
		
		try {
			List<Map<String, Object>> storeList = storeDao.getAllStoreOfCRM(userDTO.getId(),city_id, "ZB");//获取门店
			Object[] platformIdArray = new Object[storeList.size()];
			List<Object>  storeNoList = new ArrayList<Object>();
			for(int i=0;i<storeList.size();i++){//获取门店platformId
				Map<String, Object> teMap = storeList.get(i);
				if(teMap.get("platformid")==null||"".equals(teMap.get("platformid"))){
					continue;
				}
				platformIdArray[i] = teMap.get("platformid");
				platformId_storeNo.put(teMap.get("platformid").toString(),teMap.get("storeno"));
				platformId_storeId.put(teMap.get("platformid").toString(),teMap.get("store_id"));
			}
		
			
			MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
		    // 先查询门店服务范围
			MongoCollection<Document> collection = database.getCollection("store_service_area");
			BasicDBObject query = new BasicDBObject();
			query.append("storeId", new BasicDBObject("$in",platformIdArray)).append("status", 0);
			FindIterable<Document> dIterable = collection.find(query);
			MongoCursor<Document> cursor = dIterable.iterator(); 
			
			//查询门店中心坐标点
			MongoCollection<Document> collection2 = database.getCollection("store_position");
			BasicDBObject query2 = new BasicDBObject();
			query2.append("_id", new BasicDBObject("$in",platformIdArray)).append("status", 0);
			FindIterable<Document> dIterable2 = collection2.find(query2);
			MongoCursor<Document> cursor2 = dIterable2.iterator(); 
			
			
			Map<String, Object> positionMap = new HashMap<String,Object>();
			List<Object> storeServiceAreaList = new ArrayList<Object>();
			if(dIterable==null){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","门店服务范围不存在");
			}else{//服务范围存在 
				
				while(cursor2.hasNext()){
					Document teDocument2 = cursor2.next();
					JSONObject jObject = new JSONObject(teDocument2.toJson());
					Object location = teDocument2.get("location");
					Object storeId = teDocument2.get("_id");
					positionMap.put(String.valueOf(storeId), location);
				}
				
				org.json.JSONArray tmp_jArray = new org.json.JSONArray();
				Store store = null;
				while(cursor.hasNext()){
					Document teDocument = cursor.next();
					JSONObject jObject = new JSONObject(teDocument.toJson());
					String storeNo_tmp = platformId_storeNo.get(teDocument.get("storeId")).toString();
					store = storeManager.findStoreByStoreNo(storeNo_tmp);
					System.out.println(storeNo_tmp);
					jObject.put("polygonMap", "");
					jObject.put("position",positionMap.get(teDocument.get("storeId"))==null?"":positionMap.get(teDocument.get("storeId")));
					jObject.put("storeName", store.getName());
					jObject.put("storeNo", storeNo_tmp);
					jObject.put("store_id", store.getStore_id());
					tmp_jArray.put(jObject);
				}
				
				
				
				result.put("data",JSONArray.parse(tmp_jArray.toString()));
				result.put("code",CodeEnum.success.getValue());
				result.put("message", CodeEnum.success.getDescription());
			}
		} catch (Exception e) {
			logger.info("getAllStoreServiceAreaOfContry>>>"+e.getMessage());
			e.printStackTrace();
			//mBean.getMongoClient().close();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}
	

	@Override
	public Map<String, Object> selectEmployeeDriveRecode(List<Map<String, Object>> list,String beginDate,String endDate) {
		Map<String, Object> result = new HashMap<String, Object>();
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("position_record");
		org.json.JSONArray jArray = new org.json.JSONArray();
		List<Object> list1 = new ArrayList<Object>();
		for (int i = 0; i < list.size(); i++) {
			list1.add(list.get(i).get("employeeId"));
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Document> pipeline = new ArrayList<Document>();
		Document match = new Document("$match",new BasicDBObject("employeeId", new BasicDBObject("$in",list1)).
				append("createdAt",new Document("$gte",new Date(beginDate)).append("$lte", new Date(endDate))));
		pipeline.add(match);
		List<Object> listcoor = new ArrayList<Object>();
		listcoor.add("$longitude");
		listcoor.add("$latitude");
		Document project = new Document("$project",new Document("_id","$employeeId").append("location",listcoor));
		pipeline.add(project);
		//filter.put("locations.event", new Document("$ne","normal"));
		//filter.put("locations.orderId", new Document("$ne",""));
		//filter.put("locations.orderStatus", new Document("$ne",null));
		Document group = new Document("$group",new Document("_id","$_id").append("locations", new Document("$push","$location")));
		pipeline.add(group);
		AggregateIterable<Document> aggregate = collection.aggregate(pipeline).allowDiskUse(true);
		MongoCursor<Document> cursor = aggregate.iterator();
		JSONObject jObject = null;
        while (cursor.hasNext()) {  
	           Document doc = cursor.next();  
	           jObject = new JSONObject();
			   jObject.put("locations", doc.get("locations"));
			   jObject.put("id", doc.get("_id"));
			 //  jObject.put("position", doc.get("position"));
			   for(int i = 0; i < list.size(); i++){
				    Map<String, Object> map = list.get(i);
				   if(map.get("employeeId").equals(doc.get("_id"))){
			        	jObject.put("employeeNo", map.get("employeeNo"));
						jObject.put("storeName", map.get("storeName"));
						jObject.put("employeeName",map.get("employeeName"));
						jObject.put("platformid", map.get("platformid"));
						jObject.put("online", map.get("online"));
				   } 
			   }
			   jArray.put(jObject);
	    }
		result.put("data", JSONArray.parse(jArray.toString()));
		return result;
	}


	@Override
	public Map<String, Object> updateTinyAreaOfEmployee(Area area) {
		MongoDBDao mDao = (MongoDBDao)SpringHelper.getBean(MongoDBDao.class.getName());
		TinyAreaManager tinyAreaManager = (TinyAreaManager)SpringHelper.getBean("tinyAreaManager");
		UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = umanager.getCurrentUserDTO();
		StoreManager storeManager = (StoreManager)SpringHelper.getBean("storeManager");
		TinyVillageManager tinyVillageManager = (TinyVillageManager)SpringHelper.getBean("tinyVillageManager");
		Store store  = storeManager.findStore(area.getStore_id());
		Map<String, Object> result = new HashMap<String, Object>();
		List<AreaInfo> areaInfos = area.getChildrens();
		try {

			
			if(areaInfos!=null&&areaInfos.size()>0){
				AreaInfo  areaInfo=null;
				Long tiny_village_id=null;
				TinyArea tinyArea=null;
				MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
				MongoDatabase database = mDbUtil.getDatabase();
				MongoCollection<Document> collection = database.getCollection("tiny_area");
				List<Long> tinyVillageIdList = new ArrayList<Long>();
				for(int i=0;i<areaInfos.size();i++){//筛选出当前片区的小区ID
					Long villageId = areaInfos.get(i).getVillage_id();
					Long tinyVillageId = areaInfos.get(i).getTin_village_id();
					if(tinyVillageId==null){
						List<TinyVillage> tinyVillages = tinyVillageManager.getTinyVillageNotDellable(villageId);
						for(int j=0;j<tinyVillages.size();j++){
							tinyVillageIdList.add(tinyVillages.get(j).getId());
						}
					}else{
						tinyVillageIdList.add(tinyVillageId);
					}
				}
				for(int i=0;i<tinyVillageIdList.size();i++){
					
					tiny_village_id = tinyVillageIdList.get(i);
					tinyArea = tinyAreaManager.findTinyAreaByTinyId(tiny_village_id,store.getStoreno());
					if(tinyArea!=null){
						tinyArea.setEmployee_a_no(area.getEmployee_a_no());
						tinyArea.setEmployee_b_no(area.getEmployee_b_no());
						tinyArea.setUpdate_time(new Date());
						tinyArea.setUpdate_user_id(userDTO.getId());
						tinyArea.setArea_no(area.getArea_no());
						//更新国安侠
						
						BasicDBObject query = new BasicDBObject();
						query.append("code", tinyArea.getCode());
						query.append("storeNo",store.getStoreno());
						Document updateDoc = new Document("employee_a_no",tinyArea.getEmployee_a_no()).append("employee_b_no", tinyArea.getEmployee_b_no());
						collection.updateMany(query, new Document("$set",updateDoc));
						preObject(tinyArea);
						tinyAreaManager.saveObject(tinyArea);
					}
					
				}
			}
			result.put("code",CodeEnum.success.getValue());
			result.put("message", CodeEnum.success.getDescription());
		} catch (Exception e) {
			logger.info("updateTinyAreaOfEmployee>>>"+e.getMessage());
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> selectCoordinatesOfArea(String area_no) {
		Map<String,Object> result = new HashMap<String,Object>();
		MongoDBDao mongoDbDao = (MongoDBDao)SpringHelper.getBean(MongoDBDao.class.getName());
		AreaManager areaManager = (AreaManager)SpringHelper.getBean("areaManager");
		Area area = areaManager.queryAreaByAreaNo(area_no);
		StoreManager storeManager = (StoreManager)SpringHelper.getBean("storeManager");
		Store store = storeManager.findStore(area.getStore_id());
		List<Map<String,Object>> allTinyVillageOdAreaNo = mongoDbDao.getAllTinyVillageOdAreaNo(area_no);
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < allTinyVillageOdAreaNo.size(); i++){
			Map<String, Object> map = allTinyVillageOdAreaNo.get(i);
			Object object = map.get("CODE");
			list.add(object);
		}
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("tiny_area");
		FindIterable<Document> dIterable = collection.find(new Document("code",new BasicDBObject("$in",list)).append("storeNo", store.getStoreno()));
		MongoCursor<Document> cursor = dIterable.iterator(); 
		org.json.JSONArray jArray = new org.json.JSONArray();
		JSONObject jObject = null;
		while (cursor.hasNext()) {  
	           Document doc = cursor.next();  
	           jObject = new JSONObject();
	           jObject.put("code", doc.get("code"));
			   Document crObj = (Document)doc.get("coordinate_range");
			   JSONObject coordJson = new JSONObject(doc.get("coordinate_range"));
			   jObject.put("coordinates", crObj.get("coordinates"));
			   jObject.put("areano", area_no);
			   jObject.put("name",doc.get("name"));
			   jArray.put(jObject);
	    }
		result.put("data", JSONArray.parse(jArray.toString()));
		return result;
	}


	@Override
	public Map<String, Object> selectCoordinatesOfCode(String code) {
		Map<String,Object> result = new HashMap<String,Object>();
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("tiny_area");
		FindIterable<Document> dIterable = collection.find(new Document("code",code));
		MongoCursor<Document> cursor = dIterable.iterator(); 
		org.json.JSONArray jArray = new org.json.JSONArray();
		JSONObject jObject = null;
		while (cursor.hasNext()) {  
	           Document doc = cursor.next();  
	           jObject = new JSONObject();
	           jObject.put("code", code);
			   Document crObj = (Document)doc.get("coordinate_range");
			   jObject.put("coordinates", crObj.get("coordinates"));
			   jObject.put("name",doc.get("name"));
			   jArray.put(jObject);
	    }
		result.put("data", JSONArray.parse(jArray.toString()));
		return result;
	}


	@Override
	public Map<String, Object> updateTinyAreaEmployeeIdNull(Long storeId,String employeeNo) {
		MongoDBDao mDao = (MongoDBDao)SpringHelper.getBean(MongoDBDao.class.getName());
		TinyAreaDao tinyAreaDao = (TinyAreaDao)SpringHelper.getBean(TinyAreaDao.class.getName());
		StoreManager storeManager = (StoreManager)SpringHelper.getBean("storeManager");
		Store store = storeManager.findStore(storeId);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
				MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
				MongoDatabase database = mDbUtil.getDatabase();
				MongoCollection<Document> collection = database.getCollection("tiny_area");
				BasicDBObject query_a = new BasicDBObject();
				query_a.append("storeNo", store.getStoreno());
				query_a.append("employee_a_no",employeeNo);
				Document updateDoc_a = new Document("employee_a_no",null);
				collection.updateMany(query_a, new Document("$set",updateDoc_a)); 
				
				BasicDBObject query_b = new BasicDBObject();
				query_b.append("storeNo", store.getStoreno());
				query_b.append("employee_b_no",employeeNo);
				Document updateDoc_b = new Document("employee_b_no",null);
				collection.updateMany(query_b, new Document("$set",updateDoc_b)); 
			
				tinyAreaDao.updateTinyAreaEmployeeIsNull_A(employeeNo);
				tinyAreaDao.updateTinyAreaEmployeeIsNull_B(employeeNo);
				
				result.put("code",CodeEnum.success.getValue());
				result.put("message", CodeEnum.success.getDescription());
		} catch (Exception e) {
			logger.info("updateTinyAreaEmployeeIdNull>>>"+e.getMessage());
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}


	
	@Override
	public Map<String, Object> queryEmployeeDiveRecord(String employeeNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		PlatformEmployeeDao pDao = (PlatformEmployeeDao)SpringHelper.getBean(PlatformEmployeeDao.class.getName());
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("position_record");
		org.json.JSONArray jArray = new org.json.JSONArray();
		List<Map<String, Object>> list = pDao.getEmployeeByEmployeeNo(employeeNo); 
		if(list==null||list.size()==0){//没有员工编号对应的员工
			result.put("code",CodeEnum.nullData.getValue());
			result.put("message", CodeEnum.nullData.getDescription());
			return result;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);


		int startYear = calendar.get(Calendar.YEAR);//获取年份
		int startMonth = calendar.get(Calendar.MONTH) + 1;//获取月份
		int startDay = calendar.get(Calendar.DATE);//获取日

		Date beginDate = new Date(startYear - 1900, startMonth - 1, startDay);



		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		int endYear = calendar.get(Calendar.YEAR);//获取年份
		int endMonth = calendar.get(Calendar.MONTH) + 1;//获取月份
		int endDay = calendar.get(Calendar.DATE);//获取日

		Date endDate = new Date(endYear - 1900, endMonth - 1, endDay);

		List<Document> pipeline = new ArrayList<Document>();
		Document match = new Document("$match",new BasicDBObject("employeeId",list.get(0).get("employeeId")).append("createdAt",new Document("$gte",beginDate).append("$lte",endDate)));
		pipeline.add(match);
		List<Object> listcoor = new ArrayList<Object>();
		listcoor.add("$longitude");
		listcoor.add("$latitude");
		Document project = new Document("$project",new Document("_id",0).append("employeeId",1).append("location", listcoor));
		pipeline.add(project);
		Document group = new Document("$group",new Document("_id","$employeeId").append("locations", new Document("$push","$location")));
		pipeline.add(group);
		AggregateIterable<Document> aggregate = collection.aggregate(pipeline).allowDiskUse(true);
		MongoCursor<Document> cursor = aggregate.iterator();
		JSONObject jObject = null;
        while (cursor.hasNext()) {  
	           Document doc = cursor.next();  
	           jObject = new JSONObject();
			   jObject.put("locations", doc.get("locations"));
			   jObject.put("employeeId", doc.get("employeeId"));
			   jArray.put(jObject);
	    }
		result.put("data", JSONArray.parse(jArray.toString()));
		return result;
	}
 

	@Override
	public Map<String, Object> updateTinyAreaBelong(String storeNo,String townIds,String belong) {
		TinyAreaDao tinyAreaDao = (TinyAreaDao)SpringHelper.getBean(TinyAreaDao.class.getName());
		TinyAreaManager tam = (TinyAreaManager)SpringHelper.getBean("tinyAreaManager");
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			List<Map<String,Object>> list = tinyAreaDao.selectTinyAreaByTownId(storeNo, townIds);
			
			MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
			MongoDatabase database = mDbUtil.getDatabase();
		   
			MongoCollection<Document> collection = database.getCollection("tiny_area");
			BasicDBObject query = new BasicDBObject();
			query.append("storeNo",storeNo);
			FindIterable<Document> dIterable = collection.find(query);
			MongoCursor<Document> cursor = dIterable.iterator(); 
			
			
			if(list!=null&&list.size()==0){
				result.put("code",CodeEnum.nullData.getValue());
				result.put("message","街道所有小区没有绑定坐标");
			}else{
				org.json.JSONArray tmp_jarray = new org.json.JSONArray();
				Map<String, Object> tinyAreaInfo = new HashMap<String,Object>();
				
				while(cursor.hasNext()){
					Document teDocument = cursor.next();
					JSONObject jObject = new JSONObject(teDocument.toJson());

					String code = jObject.getString("code");
					
					for(Map<String, Object> m:list){
						if(code.equals(m.get("code"))){
							Document updateDoc = new Document("belong",belong);
							collection.updateMany(Filters.eq("code",m.get("code")), new Document("$set",updateDoc));
							if(m.get("tiny_village_id")!=null){
								tam.updateTinyAreaBelong(Long.parseLong(m.get("tiny_village_id").toString()),belong);
							}
							break;
						}
					}
				}
				result.put("code",CodeEnum.success.getValue());
				result.put("message", CodeEnum.success.getDescription());
			}
			
		} catch (Exception e) {
			logger.info("updateTinyAreaBelong>>>"+e.getMessage());
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return  result;
		}
		
		return result;
	}

	@Override
	public Map<String, Object> updateTinyAreaOfStore(String storeNo, String tinyVillageCodes) {

		Map<String,Object> result = new HashMap<String,Object>();
		if(storeNo!=null&&tinyVillageCodes!=null&&"".equals(storeNo)&&"".equals(tinyVillageCodes)){
			result.put("code",CodeEnum.nullData.getValue());
			result.put("message", CodeEnum.nullData.getDescription());
			return result;
		}

		TinyAreaManager tam = (TinyAreaManager)SpringHelper.getBean("tinyAreaManager");
		MongoDbUtil mDbUtil = (MongoDbUtil)SpringHelper.getBean("mongodb");
		MongoDatabase database = mDbUtil.getDatabase();
		MongoCollection<Document> collection = database.getCollection("tiny_area");
		String[] codeArray  = tinyVillageCodes.split(",");

		for(int i=0;i<codeArray.length;i++){
			Document updateDoc = new Document("belong","private");
			updateDoc.put("storeNo", storeNo);
			collection.updateMany(Filters.eq("code",codeArray[i]),new Document("$set",updateDoc));
			TinyArea tinyArea = tam.getTinyAreaByCode(codeArray[i]);
			tinyArea.setArea_no(null);
			tinyArea.setStoreNo(storeNo);
			tinyArea.setBelong("private");
			tinyArea.setEmployee_a_no(null);
			tinyArea.setEmployee_b_no(null);
			this.preObject(tinyArea);
			saveObject(tinyArea);
		}

		result.put("code",CodeEnum.success.getValue());
		result.put("message", CodeEnum.success.getDescription());
		return  result;
	}

	@Override
	public Map<String, Object> getAreaByStore(Long storeId){
		Map<String,Object> result = new HashMap<String,Object>();
		AreaDao areaDao = (AreaDao)SpringHelper.getBean(AreaDao.class.getName());
		List<Map<String, Object>> maps = areaDao.selectAreaOfStore(storeId);
		result.put("areaInfo",maps);
		result.put("code",CodeEnum.success.getValue());
		result.put("message", CodeEnum.success.getDescription());
		return  result;
	}


}
