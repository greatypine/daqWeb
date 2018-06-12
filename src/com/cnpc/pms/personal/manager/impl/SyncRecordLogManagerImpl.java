package com.cnpc.pms.personal.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.entity.DataEntity;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.personal.entity.SyncRecord;
import com.cnpc.pms.personal.entity.SyncRecordLog;
import com.cnpc.pms.personal.manager.SyncRecordLogManager;

/**
 * 同步记录
 */
public class SyncRecordLogManagerImpl extends BizBaseCommonManager implements SyncRecordLogManager {
	
	@Override
	public SyncRecordLog saveSyncRecordLog(SyncRecord syncRecord){
		SyncRecordLog syncRecordLog = new SyncRecordLog();
		BeanUtils.copyProperties(syncRecord, syncRecordLog);
		syncRecordLog.setId(null);
		preSaveObject(syncRecordLog);
		this.saveObject(syncRecordLog);
		return syncRecordLog;
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
