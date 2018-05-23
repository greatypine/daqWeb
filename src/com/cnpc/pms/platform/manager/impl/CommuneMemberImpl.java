package com.cnpc.pms.platform.manager.impl;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.communeMember.dao.CommuneMemberDao;
import com.cnpc.pms.platform.dao.PlatformStoreDao;
import com.cnpc.pms.platform.manager.CommuneMember;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 社员操作
 * @author wuxinxin
 * 2018年5月17日
 */
public class CommuneMemberImpl extends BizBaseCommonManager implements CommuneMember{

	@Override
	public Map<String, Object> selectAllCm(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		Map<String,Object> result = new HashMap<String,Object>();
		PlatformStoreDao cmDao = (PlatformStoreDao)SpringHelper.getBean(PlatformStoreDao.class.getName());
		CommuneMemberDao commDao = (CommuneMemberDao)SpringHelper.getBean(CommuneMemberDao.class.getName());
		//查询e店数量
		List<Map<String, Object>> eShopCountList = new ArrayList<Map<String,Object>>();
		eShopCountList = cmDao.getEshopCount(dd);
		if(eShopCountList!=null&&eShopCountList.size()>0){
			result.put("eShopCount", eShopCountList.get(0).get("cou"));
		}else {
			result.put("eShopCount", "99");
		}
		
		//查询SKU数量
		List<Map<String, Object>> skuCountList = new ArrayList<Map<String,Object>>();
		skuCountList = cmDao.getGoodsTypeCount(dd);
		if(skuCountList!=null&&skuCountList.size()>0){
			result.put("skuCount", skuCountList.get(0).get("cou"));
		}else {
			result.put("skuCount", "99");
		}
		
		//查询社员总量
		List<Map<String, Object>> cmCountList = new ArrayList<Map<String,Object>>();
		cmCountList = commDao.getAllCount(dd);
		if(cmCountList!=null&&cmCountList.size()>0){
			result.put("cmCount", cmCountList.get(0).get("allCount"));
		}else {
			result.put("cmCount", "99");
		}
		
		//查询老用户转社员总量
		List<Map<String, Object>> oldCmCountList = new ArrayList<Map<String,Object>>();
		oldCmCountList = commDao.getOldCount(dd);
		if(oldCmCountList!=null&&oldCmCountList.size()>0){
			result.put("oldCmCount", oldCmCountList.get(0).get("oldCount"));
		}else {
			result.put("oldCmCount", "99");
		}
		//查询新开社员总量
		List<Map<String, Object>> newCmCountList = new ArrayList<Map<String,Object>>();
		newCmCountList =commDao.getNewCount(dd);
		
		if(newCmCountList!=null&&newCmCountList.size()>0){
			result.put("newCmCount", newCmCountList.get(0).get("newCount"));
		}else {
			result.put("newCmCount", "99");
		}
		//查询成交额
		List<Map<String, Object>> cmGoodsTurnoverList = new ArrayList<Map<String,Object>>();
		//cmGoodsTurnoverList = cmDao.getCmGoodsTurnover(dd);
		////////////////////////分库查询成交信息//////////////////////////////
		/*//第一步：查询所有社员ids；
		StringBuffer ids = new StringBuffer();
		ids.append("'");
		ids.append(commDao.getAllCmIds(dd));
		ids.append("'");
		//查询id产生的订单条数；
		//查询成交量
		List<Map<String, Object>> cmGoodsDealCountList = new ArrayList<Map<String,Object>>();
		cmGoodsDealCountList = cmDao.getCmGoodsDealCount(ids.toString());
		if(cmGoodsDealCountList!=null&&cmGoodsDealCountList.size()>0){
			result.put("cmGoodsDealCount", cmGoodsDealCountList.get(0).get("cou"));
		}else {
			result.put("cmGoodsDealCount", "99");
		}
		//查询成交额
		cmGoodsTurnoverList = cmDao.getCmGoodsTurnover(ids.toString());
		if(cmGoodsTurnoverList!=null&&cmGoodsTurnoverList.size()>0){
			result.put("cmGoodsTurnover", cmGoodsTurnoverList.get(0).get("cou"));
		}else {
			result.put("cmGoodsTurnover", "99");
		}*/
		////////////////////////分库查询成交信息end//////////////////////////////
		//查询成交量
		List<Map<String, Object>> cmGoodsDealCountList = new ArrayList<Map<String,Object>>();
		cmGoodsDealCountList = commDao.getCmGoodsDealCount("");
		if(cmGoodsDealCountList!=null&&cmGoodsDealCountList.size()>0){
			result.put("cmGoodsDealCount", cmGoodsDealCountList.get(0).get("cou"));
		}else {
			result.put("cmGoodsDealCount", "99");
		}
		//查询成交额
		cmGoodsTurnoverList = commDao.getCmGoodsTurnover("");
		if(cmGoodsTurnoverList!=null&&cmGoodsTurnoverList.size()>0){
			result.put("cmGoodsTurnover", cmGoodsTurnoverList.get(0).get("cou"));
		}else {
			result.put("cmGoodsTurnover", "99");
		}

		
		
		
		//-------------------查询新增量start----------------//
		List newCounts=new ArrayList();
		List allCounts=new ArrayList();
		List dateXCounts=new ArrayList();
		List<Map<String, Object>> cmAllGrowList = commDao.getCmAllGrow(dd);
		try {
			dateXCounts = reDate(7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> cmAllList = commDao.getAllMembers(dd);
		
		if(cmAllList==null||cmAllList.size()<2) {
			for(int i=0;i<10;i++) {
				//添加新社员
				newCounts.add(i+i*i);
				//总社员
				allCounts.add(i+i*i+i+i*i*2);
			}
			
		}else {
				for(int i=0;i<cmAllList.size();i++) {
					//添加新增社员
					if(i>=cmAllGrowList.size()) {
						newCounts.add(0);
					}else {
						newCounts.add(Integer.parseInt(cmAllGrowList.get(i).get("allcount").toString()));
					}
					//总社员
					allCounts.add(Integer.parseInt(cmAllList.get(i).get("allcount").toString()));
					
				}
				
			
		}
		 JSONArray jsonNew=(JSONArray)JSONArray.fromObject(newCounts);
		 JSONArray jsonAllCounts=(JSONArray)JSONArray.fromObject(allCounts);
		 JSONArray jsonDateX=(JSONArray)JSONArray.fromObject(dateXCounts);
		 
		 result.put("growNewCounts",jsonNew);
		 result.put("growAllCounts",jsonAllCounts);
		 result.put("jsonDateX",jsonDateX);
		
		//-------------------查询新增量    end----------------//
		//查询男女比例
			List<Map<String, Object>> cmSexList = new ArrayList<Map<String,Object>>();
			cmSexList = commDao.getCmSexRatios("");
			
			if(cmSexList!=null&&cmSexList.size()>0){
				result.put("mCount", cmSexList.get(0).get("mCount"));
				result.put("fCount", cmSexList.get(0).get("fCount"));
			}else {
				result.put("oldCmCount", "99");
			}
			//查询男女比例
			List<Map<String, Object>> cmAgeList = new ArrayList<Map<String,Object>>();
			cmAgeList = commDao.getCmAgeRatios("");
			
			if(cmAgeList!=null&&cmAgeList.size()>0){
				
	          result.put("age60", cmAgeList.get(0).get("age60"));
	          result.put("age70", cmAgeList.get(0).get("age70"));
	          result.put("age80", cmAgeList.get(0).get("age80"));
	          result.put("age90", cmAgeList.get(0).get("age90"));
			  result.put("age00", cmAgeList.get(0).get("age00"));
			  result.put("ageNow", cmAgeList.get(0).get("ageNow"));
			}else {
				result.put("oldCmCount", "99");
			}
		 //查询社员生日分布
			List<Map<String, Object>> birList = commDao.getCmBirthday(dd);//getCmBirthday
			for(int i=0;i<birList.size();i++) {
				result.put("birCount"+(i+1), birList.get(i).get("birCount").toString());
			}
			//查询社员户籍分布
			List<Map<String, Object>> areaList = commDao.getMembersArea(dd);//getCmBirthday
			if(areaList!=null) {
				result.put("mePro", areaList.get(0).get("mePro").toString());
				result.put("meCount", areaList.get(1).get("meCount").toString());
				
			}
		 return result;
		
	}

	@Override
	public Map<String, Object> selectNewCm(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		 return null;
		
	}

	@Override
	public Map<String, Object> selectOldCm(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		 return null;
		
	}

	@Override
	public Map<String, Object> getEshopCount(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		 return null;
		
	}

	@Override
	public Map<String, Object> getGoodsTypeCount(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		 return null;
		
	}

	@Override
	public Map<String, Object> getCmGoodsDealCount(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		 return null;
		
	}

	@Override
	public Map<String, Object> getCmGoodsTurnover(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		 return null;
		
	}

	@Override
	public Map<String, Object> getCmSexRatios(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		 return null;
		
	}

	@Override
	public Map<String, Object> getCmAgeRatios(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		 return null;
		
	}

	@Override
	public Map<String, Object> getCmBirthday(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		 return null;
		
	}

	@Override
	public Map<String, Object> getCmGrow(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		Map<String,Object> result = new HashMap<String,Object>();
		PlatformStoreDao cmDao = (PlatformStoreDao)SpringHelper.getBean(PlatformStoreDao.class.getName());
		List newCounts=new ArrayList();
		List oldCounts=new ArrayList();
		List allCounts=new ArrayList();
		List<Map<String, Object>> cmGrowList = cmDao.getGoodsTypeCount(dd);
		for (Map<String, Object> objects : cmGrowList) {
			//添加新社员
			newCounts.add(Integer.parseInt(objects.get("newCount").toString()));
			//添加老转社员
			oldCounts.add(Integer.parseInt(objects.get("oldCount").toString()));
			//总社员
			allCounts.add(Integer.parseInt(objects.get("allCount").toString()));
		}
		 JSONArray jsonNew=(JSONArray)JSONArray.fromObject(newCounts);
		 JSONArray jsonOld=(JSONArray)JSONArray.fromObject(oldCounts);
		 JSONArray jsonAllCounts=(JSONArray)JSONArray.fromObject(allCounts);
		 JSONObject selfJson=new JSONObject();
		 selfJson.put("newCounts",jsonNew);
		 selfJson.put("oldCounts",jsonOld);
		 selfJson.put("allCounts",jsonAllCounts);
		 result.put("memberGrows", selfJson);
		 return result;
		
	}
	
	
	//生成X轴坐标
	 public List reDate(int dd) throws ParseException {
		 DateFormat f = new SimpleDateFormat("MM-dd");  
			
	     Date today = new Date();  

	     Calendar c = Calendar.getInstance();  
	     c.setTime(today);  
	     c.add(Calendar.DAY_OF_MONTH, -dd);
	     List dateX=new ArrayList();
	     dateX.add(f.format(c.getTime()));
	     for(int i=dd;i>1;i--) {
	    	 c.add(Calendar.DAY_OF_MONTH, 1);
	    	 dateX.add(f.format(c.getTime()));
	     }
			return dateX;
	 }

}
