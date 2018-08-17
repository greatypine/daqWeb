package com.cnpc.pms.personal.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
import com.cnpc.pms.personal.entity.DistCityCode;
import com.cnpc.pms.personal.entity.ExternalHumanresources;
import com.cnpc.pms.personal.manager.DistCityCodeManager;
import com.cnpc.pms.personal.manager.ExternalHumanresourcesManager;
import com.cnpc.pms.shortMessage.manager.ShortMessageManager;

@SuppressWarnings("all")
public class ExternalHumanresourcesManagerImpl extends BizBaseCommonManager implements ExternalHumanresourcesManager {
    
	@Override
    public String saveHumanresourceExt(List<File> lst_import_excel) throws Exception {
    	String rcvmsg = null;
        for(File file_excel : lst_import_excel) {
        	 //读取excel文件
            InputStream is_excel = new FileInputStream(file_excel);
            //Excel工作簿
            Workbook wb_excel;
            Sheet sheet_data;
            try {
                //解析2003的xls模式的excel
                wb_excel = new HSSFWorkbook(is_excel);
            } catch (Exception e) {
                //如果2003模式解析异常，尝试解析2007模式
                wb_excel = new XSSFWorkbook(file_excel.getAbsolutePath());
            }
            int sheetCount = wb_excel.getNumberOfSheets();
            
            //      汇思的 表头 //序号    城市     员工编号	姓名	人员类别	所属机构	一级部门	二级部门	三级部门	门店	汇报上级	职位	职级	外包/实习生入职日期	正式员工入职日期	合同签订情况			身份证号码	性别	生日	民族	学历信息			户籍	婚姻状况	联系电话				//紧急联系人姓名	紧急联系人电话	与紧急联系人关系	招聘渠道	推荐人姓名
            Map<String, Object> employMaphs = new HashMap<String, Object>();
            String[] exttitles ={"姓名","手机号","身份证号","所属城市","门店编号","门店名称"};
            
            Map<String, Integer> maps = new HashMap<String, Integer>();
            String deptName = "";
            //只读第一个sheet页
            sheet_data = wb_excel.getSheetAt(0);
            int ret = sheet_data.getPhysicalNumberOfRows();
            //DistCityCodeManager distCityCodeManager = (DistCityCodeManager) SpringHelper.getBean("distCityCodeManager");
            //根据人员类别将EXCEL排序````
            Set<String> sets = new HashSet<String>();
            List<ExternalHumanresources> saveExternalHuman = new ArrayList<ExternalHumanresources>();
            
            
            //判断表头格式是否正确
            Row validTitle = sheet_data.getRow(2);
            String title1=getCellValue(validTitle.getCell(0));  //序号
            String title2=getCellValue(validTitle.getCell(1));  //姓名
            String title3=getCellValue(validTitle.getCell(2));  //手机号
            String title4=getCellValue(validTitle.getCell(3));  //身份证号
            
            if(!title1.equals("序号")) {
            	rcvmsg="导入文件格式不正确！导入失败！";
             	return rcvmsg;
            }
            if(!title2.equals("姓名")) {
            	rcvmsg="导入文件格式不正确！导入失败！";
             	return rcvmsg;
            }
            if(!title3.equals("手机号")) {
            	rcvmsg="导入文件格式不正确！导入失败！";
             	return rcvmsg;
            }
            if(!title4.equals("身份证号")) {
            	rcvmsg="导入文件格式不正确！导入失败！";
             	return rcvmsg;
            }
            
            Row validTitle2 = sheet_data.getRow(3);
            String title5=getCellValue(validTitle2.getCell(5));  ///	
            String title6=getCellValue(validTitle2.getCell(6));  //
            if(!title5.equals("门店编号")) {
            	rcvmsg="导入文件格式不正确！导入失败！";
             	return rcvmsg;
            }
            if(!title6.equals("门店名称")) {
            	rcvmsg="导入文件格式不正确！导入失败！";
             	return rcvmsg;
            }
            
            for(int nRowIndex = 4;nRowIndex < sheet_data.getPhysicalNumberOfRows();nRowIndex++){
            	Row row_human = sheet_data.getRow(nRowIndex);
            	 if(row_human == null){
 	            	rcvmsg="导入文件格式不正确！导入失败！行号："+(nRowIndex+1);
                 	return rcvmsg;
 	            }
            	if(row_human == null){
	            	continue;
	            }
            	
            	
	            
            	ExternalHumanresources externalHumanresources = new ExternalHumanresources();
            	
            	String name="";
            	String phone="";
            	String cardnumber="";
            	String citySelect="";
            	String storeno="";
            	String storename="";
            	Cell nameCell = row_human.getCell(1);
                if(nameCell!=null) {
                	name=getCellValue(nameCell);
                }
               
                Cell phoneCell = row_human.getCell(2);
                if(phoneCell!=null) {
                	phone=getCellValue(phoneCell);
                }
                
                Cell cardnumberCell = row_human.getCell(3);
                if(cardnumberCell!=null) {
                	cardnumber=getCellValue(cardnumberCell);
                }
                
                Cell citySelectCell = row_human.getCell(4);
                if(citySelectCell!=null) {
                	citySelect=getCellValue(citySelectCell);
                }
                
                Cell storenoCell = row_human.getCell(5);
                if(storenoCell!=null) {
                	storeno=getCellValue(storenoCell);
                }
                
                Cell storenameCell = row_human.getCell(6);
                if(storenameCell!=null) {
                	storename=getCellValue(storenameCell);
                }
                
                
                if(name==""&&cardnumber=="") {
                	continue;
                }
                
                if(phone=="") {
                	rcvmsg="电话存在空值！ 导入失败！ ";
	             	return rcvmsg;
                }
                if(phone.length()!=11) {
                	rcvmsg="电话号码格式错误！ 导入失败！ ";
	             	return rcvmsg;
                }
                if(name=="") {
                	rcvmsg="名字存在空值！ 导入失败！ ";
	             	return rcvmsg;
                }
                if(cardnumber=="") {
                	rcvmsg="身份证号存在空值！ 导入失败！ ";
	             	return rcvmsg;
                }
                
                externalHumanresources.setName(name);
                externalHumanresources.setPhone(phone);
                externalHumanresources.setCardnumber(cardnumber);
                externalHumanresources.setCitySelect(citySelect);
                externalHumanresources.setStore_no(storeno);
                externalHumanresources.setStore_name(storename);
                
              
                //校验重复
	              ExternalHumanresources existExt = queryExternalHumanresourcesByCardNo(externalHumanresources.getCardnumber());
	          	  if(existExt!=null) {
	          		rcvmsg="身份证号存在相同数据！导入失败！<br>姓名 ："+name+"<br>身份证号："+cardnumber;
	             	return rcvmsg;
	          	  }
                saveExternalHuman.add(externalHumanresources);
            	}
            
            
			String maxInviteCode = queryMaxExternalHumanresources();
			int newInviteCodeNum = 0;
			try {
				newInviteCodeNum = Integer.parseInt(maxInviteCode)+1;
			} catch (Exception e) {
				rcvmsg = "邀请码为空异常！";
				return rcvmsg;
			}
			
			List<ExternalHumanresources> sendMsgs = new ArrayList<ExternalHumanresources>();
			// 保存操作
			for (int i = 0; i < saveExternalHuman.size(); i++) {
				ExternalHumanresources saveExt = saveExternalHuman.get(i);
				saveExt.setInviteCode(newInviteCodeNum+"");
				sendMsgs.add(saveExt);
				preSaveObject(saveExt);
				this.saveObject(saveExt);
				
				newInviteCodeNum=newInviteCodeNum + 1;
			}
            
			
			//循环发送邀请码
			if(sendMsgs!=null&&sendMsgs.size()>0) {
				for(ExternalHumanresources eh:sendMsgs) {
					//循环发送短信  
					ShortMessageManager shortMessageManager = (ShortMessageManager) SpringHelper.getBean("shortMessageManager");
    	    		Map<String, Object> param = new HashMap<String, Object>();
    	    		param.put("inviteCode", eh.getInviteCode());
    	    		param.put("mobilePhone", eh.getPhone());
    	    		param.put("userName", eh.getName());
    	    		param.put("type", "SYYQM");
    	    		param.put("signature", "国安管家");
    	    		shortMessageManager.commonSendShortMessage(param);
				}
			}
			
        }
        return rcvmsg;
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
    
	
	
	private String getMapValue(Row row_human,Map<String, Integer> maps,String key){
    	if(maps.get(key)!=null){
    		String ret = getCellValue(row_human.getCell(maps.get(key)));
    		return ret;
    	}
    	return "";
    }
	
	
	 private String getCellValue(Cell cell) {
	        String value;
	        if(cell == null){
	            return null;
	        }
	        switch (cell.getCellType()) {
	            case Cell.CELL_TYPE_STRING:
	                value = cell.getRichStringCellValue().getString().trim();
	                break;
	            case Cell.CELL_TYPE_NUMERIC:
	            	value = cell.getNumericCellValue() == 0D ? null : String.valueOf(cell.getNumericCellValue());
	            	if (cell.getCellStyle().getDataFormat() == 177||cell.getCellStyle().getDataFormat() == 58||cell.getCellStyle().getDataFormat() == 31){
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                    Date date = org.apache.poi.ss.usermodel.DateUtil
	                            .getJavaDate(Double.valueOf(value));
	                    value = sdf.format(date);
	                    return value;
	                }else if(DateUtil.isCellDateFormatted(cell)){
	                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                   return sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
	                }
	                if(value != null && value.contains("E")){
	                    value = new BigDecimal(value).toPlainString();
	                }
	                break;
	            case Cell.CELL_TYPE_BOOLEAN:
	                value = String.valueOf(cell.getBooleanCellValue()).trim();
	                break;
	            case Cell.CELL_TYPE_FORMULA:
	                value = cell.getCellFormula();
	                break;
	            default:
	                value = "";
	        }
	        return value;
	    }
	 
	 
	 
	public ExternalHumanresources queryExternalHumanresourcesByCardNo(String cardNo) {
		IFilter repFilter = FilterFactory.getSimpleFilter("cardnumber='" + cardNo + "'");
		List<?> rep_list = this.getList(repFilter);
		if (rep_list != null && rep_list.size() > 0) {
			ExternalHumanresources externalHumanresources = (ExternalHumanresources) rep_list.get(0);
			return externalHumanresources;
		}
		return null;
	}

	public String queryMaxExternalHumanresources() {
		FSP fsp = new FSP();
		fsp.setSort(SortFactory.createSort("id", ISort.DESC));
		List<?> rep_list = this.getList(fsp);
		if (rep_list != null && rep_list.size() > 0) {
			ExternalHumanresources externalHumanresources = (ExternalHumanresources) rep_list.get(0);
			return externalHumanresources.getInviteCode();
		}
		return "800000";
	}
    
}
