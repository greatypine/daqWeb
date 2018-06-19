package com.cnpc.pms.personal.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.transaction.annotation.Transactional;

import com.cnpc.pms.base.entity.DataEntity;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.ISort;
import com.cnpc.pms.base.paging.SortFactory;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.personal.entity.SyncRecord;
import com.cnpc.pms.personal.manager.CityHumanresourcesManager;
import com.cnpc.pms.personal.manager.SyncRecordLogManager;
import com.cnpc.pms.personal.manager.SyncRecordManager;
import com.cnpc.pms.personal.util.SearchDataServiceUtils;

/**
 * 同步线上人员实现类
 */
public class SyncRecordManagerImpl extends BizBaseCommonManager implements SyncRecordManager {
	
	
	//初始化全部数据 
	@Override
	public void initAllSearchData(){
		CityHumanresourcesManager cityHumanresourcesManager=(CityHumanresourcesManager) SpringHelper.getBean("cityHumanresourcesManager");
		SyncRecordLogManager syncRecordLogManager = (SyncRecordLogManager) SpringHelper.getBean("syncRecordLogManager");
		String xmlstr = SearchDataServiceUtils.initSearchAllData();
		List<SyncRecord> syncRecords=xmltobeans(xmlstr);
		if(syncRecords!=null&&syncRecords.size()>0){
			for(SyncRecord syncRecord:syncRecords){
				//同步记录表
				preSaveObject(syncRecord);
				this.saveObject(syncRecord);
				//线上人员表
				//cityHumanresourcesManager.saveCityHumanresourcesByCardNo(syncRecord);
				
				//保存历史记录
				syncRecordLogManager.saveSyncRecordLog(syncRecord);
			}
		}
	}
	
	//查询差量数据
	@Override
	@Transactional
	public void saveSearchWebServiceData(){
		CityHumanresourcesManager cityHumanresourcesManager=(CityHumanresourcesManager) SpringHelper.getBean("cityHumanresourcesManager");
		SyncRecordLogManager syncRecordLogManager = (SyncRecordLogManager) SpringHelper.getBean("syncRecordLogManager");

		//取同步表 更新日期后的最大值 
		FSP fsp = new FSP();
		fsp.setSort(SortFactory.createSort("updatetime", ISort.DESC));
		List<SyncRecord> lRecords = (List<SyncRecord>) this.getList(fsp);
		// 查询某时间之后变化的数据
		String date = null;
		if(lRecords!=null&&lRecords.size()>0){
			SyncRecord syncRecord = lRecords.get(0);
			date=syncRecord.getUpdatetime();
		}
		if(date!=null){
			String xmlstr = SearchDataServiceUtils.searchDate(date);
			List<SyncRecord> syncRecords=xmltobeans(xmlstr);
			//处理变化的数据 根据身份证号查询  如果存在的 更新操作
			if(syncRecords!=null&&syncRecords.size()>0){
				for(SyncRecord syncRecord:syncRecords){
					//同步记录表
					IFilter repFilter =FilterFactory.getSimpleFilter("cardid",syncRecord.getCardid());
					List<SyncRecord> saveSyncRecords = (List<SyncRecord>) this.getList(repFilter);
					if(saveSyncRecords!=null&&saveSyncRecords.size()>0){
						SyncRecord saveSyncRecord = saveSyncRecords.get(0);
						saveSyncRecord.setUpdatetime(syncRecord.getUpdatetime());
						saveSyncRecord.setName(syncRecord.getName());
						saveSyncRecord.setNum(syncRecord.getNum());
						saveSyncRecord.setOrg(syncRecord.getOrg());
						saveSyncRecord.setDept(syncRecord.getDept());
						saveSyncRecord.setPost(syncRecord.getPost());
						saveSyncRecord.setEdu(syncRecord.getEdu());
						saveSyncRecord.setCardid(syncRecord.getCardid());
						saveSyncRecord.setPhone(syncRecord.getPhone());
						saveSyncRecord.setEmail(syncRecord.getEmail());
						saveSyncRecord.setJointime(syncRecord.getJointime());
						saveSyncRecord.setLefttime(syncRecord.getLefttime());
						saveSyncRecord.setEmployee_no(syncRecord.getEmployee_no());
						saveSyncRecord.setInviteCode(syncRecord.getInviteCode());
						preSaveObject(saveSyncRecord);
						this.saveObject(saveSyncRecord);
					}else{
						preSaveObject(syncRecord);
						this.saveObject(syncRecord);
					}
					//线上人员表 
					//cityHumanresourcesManager.saveCityHumanresourcesByCardNo(syncRecord);
					
					//保存同步记录 
					syncRecordLogManager.saveSyncRecordLog(syncRecord);
				}
			}
		}
		
	}
	
	
	
	 protected void preSaveObject(Object o) {
			if (o instanceof DataEntity) {
				User sessionUser = null;
				if (null != SessionManager.getUserSession()
						&& null != SessionManager.getUserSession().getSessionData()) {
					sessionUser = (User) SessionManager.getUserSession()
							.getSessionData().get("user");
				}
				DataEntity dataEntity = (DataEntity) o;
				java.util.Date date = new java.util.Date();
				java.sql.Date sdate = new java.sql.Date(date.getTime());
				// insert处理时添加创建人和创建时间
				if (null == dataEntity.getCreate_time()) {
					dataEntity.setCreate_time(sdate);
					if (null != sessionUser) {
						dataEntity.setCreate_user(sessionUser.getCode());
						dataEntity.setCreate_user_id(sessionUser.getId());
					}
				}
				dataEntity.setUpdate_time(sdate);
				if (null != sessionUser) {
					dataEntity.setUpdate_user(sessionUser.getCode());
					dataEntity.setUpdate_user_id(sessionUser.getId());
				}
			}
		}
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<SyncRecord> xmltobeans(String xmlstr) {
		List<SyncRecord> records =null;
		Object obj = null;
		try {
			// 将xml格式的数据转换成Map对象
			Map<String, Object> map = new HashMap<String, Object>();
			// 将xml格式的字符串转换成Document对象
			Document doc = DocumentHelper.parseText(xmlstr);
			// 获取根节点
			Element root = doc.getRootElement();
			List<Element> beans = root.elements("record");
			if(beans!=null&&beans.size()>0){
				records = new ArrayList<SyncRecord>();
				for (Element element : beans) {
					SyncRecord record = new SyncRecord();
					String name = element.element("name").getText();
					String num = element.element("num").getText();
					String org = element.element("org").getText();
					String dept = element.element("dept").getText();
					String post = element.element("post").getText();
					String edu = element.element("edu").getText();
					String cardid = element.element("cardid").getText();
					String phone = element.element("phone").getText();
					String email = element.element("email").getText();
					String jointime = element.element("jointime").getText();
					String lefttime = element.element("lefttime").getText();
					String updatetime = element.element("updatetime").getText();
					record.setName(name);
					record.setNum(num);
					record.setOrg(org);
					record.setDept(dept);
					record.setPost(post);
					record.setEdu(edu);
					record.setCardid(cardid);
					record.setPhone(phone);
					record.setEmail(email);
					record.setJointime(jointime);
					record.setLefttime(lefttime);
					record.setUpdatetime(updatetime);
					records.add(record);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return records;
	}

}
