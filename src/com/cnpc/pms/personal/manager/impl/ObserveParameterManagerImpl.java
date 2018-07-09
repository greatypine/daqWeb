package com.cnpc.pms.personal.manager.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.file.comm.utils.StringUtils;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.dao.ObserveParameterDao;
import com.cnpc.pms.personal.dao.StorexpandDao;
import com.cnpc.pms.personal.entity.Family;
import com.cnpc.pms.personal.entity.ObserveParameter;
import com.cnpc.pms.personal.entity.Store;
import com.cnpc.pms.personal.entity.Storexpand;
import com.cnpc.pms.personal.manager.ObserveParameterManager;
import com.cnpc.pms.personal.manager.StoreManager;
import com.cnpc.pms.utils.DateUtils;

public class ObserveParameterManagerImpl  extends BizBaseCommonManager implements ObserveParameterManager{
	
	private HSSFCellStyle style_header = null;
	private CellStyle cellStyle_common = null;

	@Override
	public Map<String, Object> saveObserveParameter(ObserveParameter param1,ObserveParameter param2,ObserveParameter param3) {
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			if(param1 != null && param2 != null && param3 != null){
				List<?> observeParameterList = getObserveParameterByStoreAndMonth(param1.getStore_id(),param1.getObserve_month());
				if(observeParameterList != null){
					for(int i = 0; i < observeParameterList.size(); i++){
						ObserveParameter observeParameter = (ObserveParameter)observeParameterList.get(i);
						Long id = observeParameter.getId();
						String storeno = observeParameter.getStoreno();
						String store_id = observeParameter.getStore_id();
						if(observeParameter.getType().equals("1")){
							param1.setId(id);
							param1.setStoreno(storeno);
							param1.setStore_id(store_id);
							BeanUtils.copyProperties(param1, observeParameter,
									new String[] { "id", "version", "create_time", "create_user", "create_user_id" });
						}else if(observeParameter.getType().equals("0")){
							param2.setId(id);
							param2.setStoreno(storeno);
							param2.setStore_id(store_id);
							BeanUtils.copyProperties(param2, observeParameter,
									new String[] { "id", "version", "create_time", "create_user", "create_user_id" });
						}else if(observeParameter.getType().equals("2")){
							param3.setId(id);
							param3.setStoreno(storeno);
							param3.setStore_id(store_id);
							BeanUtils.copyProperties(param3, observeParameter,
									new String[] { "id", "version", "create_time", "create_user", "create_user_id" });
						}
						preObject(observeParameter);
						this.saveObject(observeParameter);
					}
				}else{
					String store_id = param1.getStore_id();
					StoreManager storeManager = (StoreManager)SpringHelper.getBean("storeManager");
					Store findStore = storeManager.findStore(Long.valueOf(store_id));
					param1.setStoreno(findStore.getStoreno());
					param2.setStoreno(findStore.getStoreno());
					param3.setStoreno(findStore.getStoreno());
					param1.setCity_name(findStore.getCityName());
					param2.setCity_name(findStore.getCityName());
					param3.setCity_name(findStore.getCityName());
					preObject(param1);
					this.saveObject(param1);
					preObject(param2);
					this.saveObject(param2);
					preObject(param3);
					this.saveObject(param3);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "-1");
			result.put("message", "update fail");
			return result;
		}
		
		result.put("code", "1");
		result.put("message", "update success");
		return result;
	}

	@Override
	public Map<String, Object> queryObserveParameter(QueryConditions conditions) {
		Map<String,Object> result = new HashMap<String, Object>();
		ObserveParameterDao observeParameterDao = (ObserveParameterDao)SpringHelper.getBean(ObserveParameterDao.class.getName());
		//List<Map<String, Object>> queryObserveParameterList = observeParameterDao.queryObserveParameterList(month, store_id);
		StorexpandDao storexpandDao = (StorexpandDao) SpringHelper.getBean(StorexpandDao.class.getName());
		// 查询的数据条件
		StringBuilder sb_where = new StringBuilder();
		// 分页对象
		PageInfo obj_page = conditions.getPageinfo();
		// 返回的对象，包含数据集合、分页对象等
		Map<String, Object> map_result = new HashMap<String, Object>();
		List<Map<String, Object>> mapWhereList = conditions.getConditions();
		Map<String, Object> weidu_where = mapWhereList.get(1);
		Map<String, Object> city_where = mapWhereList.get(0);
		if ("store_id".equals(weidu_where.get("key")) && null != weidu_where.get("value")
				&& !"".equals(weidu_where.get("value"))) {
			String str = (String) weidu_where.get("value");
			sb_where.append(" AND store_id = '").append(weidu_where.get("value")).append("'");
		}
		if ("observe_month".equals(city_where.get("key")) && null != city_where.get("value")
					&& !"".equals(city_where.get("value"))) {
				sb_where.append(" AND observe_month = '").append(city_where.get("value").toString().replace("%", "")).append("'");
		}

		System.out.println(sb_where);
		map_result.put("pageinfo", obj_page);
		map_result.put("header", "目标值录入信息");
		map_result.put("data", observeParameterDao.queryObserveParameterList(sb_where.toString(), obj_page));
		return map_result;
	}
	
	
	@Override
	public Map<String,Object> queryExitObserveParameter(String store_id,String observe_month){
		Map<String,Object> result = new HashMap<String, Object>();
		ObserveParameterDao observeParameterDao = (ObserveParameterDao)SpringHelper.getBean(ObserveParameterDao.class.getName());
		List<Map<String, Object>> queryObserveParameterList = observeParameterDao.queryExitObserveParameter(store_id, observe_month,null,null);
		result.put("queryObserveParameterList", queryObserveParameterList);
		return result;
	}

	@Override
	public List<?> getObserveParameterByStoreAndMonth(String storeid, String observemonth) {
		List<?> list = this.getList(FilterFactory.getSimpleFilter("store_id= '"+storeid+"' and observe_month = '"+observemonth+"' and status = 0"));
		if (list != null && list.size() > 0) {
			//ObserveParameter observeParameter = (ObserveParameter) list.get(0);
			 return list;
		}
		return null;
	}

	@Override
	public Map<String, Object> queryObserveParameterSummaryByCity(String cityname) {
		Map<String,Object> result = new HashMap<String, Object>();
		ObserveParameterDao observeParameterDao = (ObserveParameterDao)SpringHelper.getBean(ObserveParameterDao.class.getName());
		List<String> queryObserveMonthByCity = observeParameterDao.queryObserveMonthByCity(cityname);
		List<Map<String,Object>> queryObserveParameterSummaryByCity = observeParameterDao.queryObserveParameterSummaryByCity(cityname);
		result.put("queryObserveMonth", queryObserveMonthByCity);
		result.put("queryObserveParameterSummary", queryObserveParameterSummaryByCity);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> exportObserveParamterSummary(String cityname) {
		Map<String,Object> result  = new HashMap<String,Object>();
		Map<String,Object> map  = this.queryObserveParameterSummaryByCity(cityname);
		List<String> storenameList = new ArrayList<>();
		if(!map.isEmpty()){//成功返回数据
			List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("queryObserveParameterSummary");
			List<String> listmonth = (List<String>)map.get("queryObserveMonth");
			List<Map<String,Object>> storeInfoList = new ArrayList<Map<String,Object>>();
			for(int i = 0; i<list.size(); i++){
				Map<String, Object> mapobj = list.get(i);
				String storename = mapobj.get("store_name").toString();
				if(!storenameList.contains(storename)){
					storenameList.add(storename);
					Map<String,Object> storeInfo = new HashMap<String,Object>();
					storeInfo.put("storename", mapobj.get("store_name"));
					storeInfo.put("storeno", mapobj.get("storeno"));
					storeInfo.put("rmname", mapobj.get("rmname"));
					storeInfo.put("skname", mapobj.get("skname"));
					storeInfo.put("observe_date", mapobj.get("observe_date"));
					storeInfo.put("observe_person", mapobj.get("observe_person"));
					storeInfoList.add(storeInfo);
				}
				int index = storenameList.indexOf(storename);
				Map<String, Object> map2 = storeInfoList.get(index);
				map2.put(mapobj.get("observe_month").toString(), mapobj.get("points_combined"));
				map2.put(mapobj.get("observe_month").toString()+"quest", mapobj.get("observe_question_number"));
			}
			String join1 = StringUtils.join(listmonth.toArray(), "月份得分,")+"月份得分";
			String replace1 = join1.replace("-", "年");
			String join2 = StringUtils.join(listmonth.toArray(), "月份问题数量,")+"月份问题数量";
			String replace2 = join2.replace("-", "年");
			if(list==null||list.size()==0){
				result.put("message","没有符合条件的数据！");
				result.put("status","null");
				return result;
			}
			String str_file_dir_path = PropertiesUtil.getValue("file.root");
			String str_web_path = PropertiesUtil.getValue("file.web.root");

			HSSFWorkbook wb = new HSSFWorkbook();
			// 创建Excel的工作sheet,对应到一个excel文档的tab

			setCellStyle_common(wb);
			setHeaderStyle(wb);
			HSSFSheet sheet = wb.createSheet(" 门店明查台账汇总");
			
			HSSFRow row = sheet.createRow(0);
			String[] str_headers = {"门店编号","门店名称","店长姓名","运营经理","检查日期","检查人"};
			String[] arr1 = replace1.split(",");
			String[] arr3 = replace2.split(",");
			String[] addAll = ArrayUtils.addAll(str_headers, arr1);
			String[] addAll2 = ArrayUtils.addAll(addAll,arr3);
			String[] headers_key = {"storeno","storename","skname","rmname","observe_date","observe_person"};
			String[] array1 = StringUtils.join(listmonth.toArray(),",").split(",");
			String array2 = StringUtils.join(listmonth.toArray(), "quest,")+"quest";
			String[] array3 = array2.split(",");
			String[] addAll_key = ArrayUtils.addAll(headers_key, array1);
			String[] addAll2_key = ArrayUtils.addAll(addAll_key, array3);
			for(int i = 0;i < addAll2.length;i++){
				if(i>5){
					sheet.setColumnWidth(i, 20 * 256);
				}
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(getHeaderStyle());
				cell.setCellValue(new HSSFRichTextString(addAll2[i]));
			}

			for(int i = 0;i < storeInfoList.size();i++){
				row = sheet.createRow(i+1);
				for(int cellIndex = 0;cellIndex < addAll2_key.length; cellIndex ++){
					setCellValue(row, cellIndex, storeInfoList.get(i).get(addAll2_key[cellIndex]));
				}
			}

			File file_xls = new File(str_file_dir_path + File.separator+System.currentTimeMillis()+"明查台账问题汇总.xls");
			if(file_xls.exists()){
				file_xls.delete();
			}
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(file_xls.getAbsoluteFile());
				wb.write(os);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(os != null){
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			result.put("message","导出成功！");
			result.put("status","success");
			result.put("data", str_web_path.concat(file_xls.getName()));
		}else{
			result.put("message","请重新操作！");
			result.put("status","fail");
		}


		return result;
	}
	
	private HSSFCellStyle getHeaderStyle(){
		return style_header;
	}

	private void setHeaderStyle(HSSFWorkbook wb){

		// 创建单元格样式
		style_header = wb.createCellStyle();
		style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_header.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		style_header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// 设置边框
		style_header.setBottomBorderColor(HSSFColor.BLACK.index);
		style_header.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderTop(HSSFCellStyle.BORDER_THIN);

	}

	private void setCellStyle_common(Workbook wb){
		cellStyle_common=wb.createCellStyle();
		cellStyle_common.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		cellStyle_common.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中
		
	}

	private CellStyle getCellStyle_common() {
		return cellStyle_common;
	}

	public void setCellValue(Row obj_row, int nCellIndex, Object value){
		Cell cell=obj_row.createCell(nCellIndex);
		cell.setCellStyle(getCellStyle_common());
		cell.setCellValue(new HSSFRichTextString(value==null?null:value.toString()));
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	}

	@Override
	public Map<String, Object> exportObserveParamter(String cityname) {
		Map<String,Object> result  = new HashMap<String,Object>();
		ObserveParameterDao observeParameterDao = (ObserveParameterDao)SpringHelper.getBean(ObserveParameterDao.class.getName());
		List<Map<String, Object>> list = observeParameterDao.queryExitObserveParameter(null, DateUtils.getCurrMonthDate(),"0",null);
		if(list.size() > 0 && list != null){//成功返回数据	
			String str_file_dir_path = PropertiesUtil.getValue("file.root");
			String str_web_path = PropertiesUtil.getValue("file.web.root");

			HSSFWorkbook wb = new HSSFWorkbook();
			// 创建Excel的工作sheet,对应到一个excel文档的tab

			setCellStyle_common(wb);
			setHeaderStyle(wb);
			HSSFSheet sheet = wb.createSheet(" 门店明查台账汇总");
			HSSFRow row0 = sheet.createRow(0);
			HSSFRow row = sheet.createRow(1);
			String[] str_headers1 = {"","1、人员","a1_2","a2_1","a2_2","a3_1","a3_2","a4_1","a4_2","a4_3","a5_1","a5_2","a5_3","a6","a7_1","a7_2","a8","a9","a10_1","a10_2","a11","2、资产","b1_2","b2",
				"b3","b4","b5_1","b5_2","b6_1","b6_2","b7","3、财务","c2","c3_1","c3_2","c4","c5","c6","c7_1","c7_2","c8_1","c8_2","4、库房","d2_1","d2_2","d3_1","d3_2","d3_3","d3_4","d4_1","d4_2",
				"d4_3","d5_1","d5_2","d6","d7_1","d7_2","d8","d9_1","d9_2","d10","d11_1","d11_2","d11_3","d11_4","d12","d13_1","d13_2","5、能源","e1_2","e2","e3","e4_1","e4_2","e5","e6","e7",
				"e8_1","e8_2","e9","6环境","f2","f3","f4_1","f4_2","f4_3","f5","f6","f7","f8_1","f8_2","f9_1","f9_2","7、会议","g2_1","g2_2","g2_3","g3","8、安全","h1_2","h2","h3","h4_1","h4_2","h4_3",
				"h5_1","h5_2","h6","h7_1","h7_2","h8","h9_1","h9_2","h9_3","h9_4","h9_5","h9_6","h10_1","h10_2","h11_1","h11_2","9、陈列","i1_2","i2","i3","i4_1","i4_2","i5_1","i5_2","i5_3",
				"i6_1","i6_2","i7","10、档案","j2_1","j2_2","j3","11、产品检查","k2_1","k2_2","k2_3_1","k2_3_2","k2_4","k2_5","k2_6","k2_7","k2_8","k2_9","k3","12、518专项检查","l2","l3_1","l3_2"};
			String[] str_headers = {"门店编号","A1-1员工工装不符合规范","A1-2员工工装丢失无申报记录(5个工作日内)","A2-1员工胸牌不符合规范","A2-2员工胸牌丢失无申报记录(5个工作日内)","A3-1女员工丝巾佩戴不符合规范","A3-2女员工丝巾丢失无申报记录(5个工作日内)",
					"A4-1员工个人卫生不符合标准手册要求的","A4-2女员工未化淡妆的（孕妇和哺乳期除外）","A4-3员工佩戴多余饰品","A5.1无迎宾语","A5.2 无道别语","A5.3 5秒钟内未及时给客户提供服务","A6 排班表未张贴（5月5-18日此项不检查）",
					"A7-1 无指纹打卡记录（5月5-18日此项不检查）","A7-2 指纹打卡记录有缺失（5月5-18日此项不检查）","A8 出勤与排班表不一致（5月5-18日此项不检查）","A9 店长外出未填《出行表》（5月5-18日此项不检查）",
					"A10-1 每日工作交接表有缺失","A10-2 未使用最新交接表模板","A11 工作交接表填写不完整","B1-1 无每月门店资产盘点表","B1-2 门店资产盘点表填写不完整","B2 实物与门店资产盘点表不一致",
					"B3 抽查固定资产不能使用且无报修记录(5个工作日内)","B4 电动车摆放不整齐（5月5-18日此项不检查）","B5-1 电动车不能使用且未报修(3个工作日内)（5月5-18日此项不检查）","B5-2 电动车车胎亏气（5月5-18日此项不检查）",
					"B6-1 员工外出未佩戴头盔（5月5-18日此项不检查）","B6-2 员工外出私自搭载（5月5-18日此项不检查）","B7 店内有待维修未提报（5月5-18日此项不检查）","C1保险柜不在监控范围","C2人员不在场情况下保险柜门未锁",
					"C3-1现金日报表有缺失","C3-2现金报表填写不正确","C4现金报表未按时上报财务","C5现金未分类存放","C6台账与实际现金不符","C7-1未及时缴存门店营业款现金","C7-2门店营业款缴存金额与台账不符","C8-1门店入库单未及时录入中台",
					"C8-2门店中台入库数量与入库单不一致","D1库房门无警示语","D2-1库房门未关闭","D2-2独立库房没有专人负责或未锁门","D3-1库房内货品摆放不整齐","D3-2货品无托盘落地摆放","D3-3商品未按包装要求摆放（如倒置等）",
					"D3-4货品距离照明灯20cm以内","D4-1货架未正确标识","D4-2周转箱未正确标识","D4-3货位未正确标识","D5-1门店无货品存放台账","D5-2门店货品存放台账填写不完整","D6SKU实际库存与台账不符","D7-1快递未全部放于库房",
					"D7-2快递临时停放收银台区域堆放凌乱的","D8寄件、派件未分开存放","D9-1库房无摄像头","D9-2库房摄像头被人旋转照射不合理区域且无报修记录(3个工作日内)","D10入库单未按照时间顺序填写","D11-1库房没有进销存表、出入库表",
					"D11-2进销存表、出入库表填写不完整","D11-3进销存表未每周至少更新一次","D11-4出入库表未随时更新","D12进销存、出入库表与中台记录有差异且不能说明原因","D13-1未遵守小盘、大盘制度","D13-2盘点表填写不完整",
					"E1-1未使用水电台账","E1-2水电使用台账记录不完整","E2非客户区域无人时灯未关","E3轨道灯没有正确开关","E4-1显示屏没有正确开关","E4-2智能终端设备没有正确开关","E5空调未正确开关","E6饮水机未正常开启",
					"E7水龙头有跑冒滴漏且未报修","E8-1冰柜未及时除霜或有冷凝水外溢","E8-2冰柜温度与柜内商品冷藏要求不符","E9有非公司用电设备","F1垃圾篓内垃圾超过2/3","F2店内部分区域不干净","F3清洁工具摆放杂乱",
					"F4-1收银台上文件或办公用品摆放不整齐","F4-2在顾客能看到的展示区域粘贴了门店内部管理类表单","F4-3收银台上存放私人物品","F5卫生间内的卷纸、纸巾、洗手液的剩余量无法使用",
					"F6空调设置温度不标准（24-28度以外，集中供暖除外）","F7店内有异味","F8-1店内未播放背景音乐","F8-2背景音乐音量不适中","F9-1播放短片无音效","F9-2播放短片音量不标准","G1门店未每天组织召开一次短会","G2-1缺少培训材料",
					"G2-2缺少培训签到表","G2-3培训签到表缺少员工签字","G3抽查国安大学“日日学”培训内容未答对","H1-1当班人员手中无钥匙","H1-2钥匙打不开门禁","H2后门无故未关闭","H3上墙张贴的健康证已过期","H4-1过期、损坏、变形商品未独立存放",
					"H4-2过期、损坏、变形商品独立存放但缺少标识","H4-3陈列了过期变质的水果、蔬菜","H5-1食品保质期已过","H5-2食品缺QS或SC标识（豆米面除外）","H6灭火器不易拿、被遮挡、堵塞消火栓、防火卷帘门、消防通道及电闸箱等",
					"H7-1员工不知道灭火器的位置","H7-2员工不会正确使用灭火器","H8灭火器旁堆放杂物","H9-1灭火器质保过期","H9-2灭火器压力异常","H9-3灭火器保质期标签缺失","H9-4灭火器零部件未正常连接或有破损",
					"H9-5灭火器不可正常使用且无申报记录(5个工作日内)","H9-6灭火器自检台账未按要求填写","H10-1门店无消防预案（纸质版）","H10-2消防预案（纸质版）未张贴在库房或办公室显著位置",
					"H11-1插座、插头绝缘体有破损且无报修记录(3个工作日内)","H11-2多个排插串联","I1-1货品摆放不整齐","I1-2未及时补充货架","I2商品摆放不符合上小下大原则","I3商品陈列不符合分类摆放原则","I4-1海报超过6张",
					"I4-2海报张贴不符合标准，影响门店外观视觉","I5-1展架数量超过6个","I5-2展架摆放不符合标准，影响门店外观视觉","I5-3客户体验区DM单有过期情况","I6-1业绩版展板未挂在非客户体验区","I6-2业绩展板内容超过1天未更新或填写不正确",
					"I7海报、展架、电子屏等未按北京公司门店点位布置","J1门店未下载国安社区门店标准管理手册电子档","J2-1各类资料散乱，没有归档","J2-2纸质版文件没有明确标识","J3电子版资料未按规定存档","K1进口食品无中文标识 ",
					"K2.1 门店绿植订单备注中未拍照上传照片","K2.2门店收衣服检查项目发现的问题未在APP备注","K2.3-1门店保洁订单用户体验报告未上传中台","K2.3-2门店保洁订单中台上显示有用户不满意","K2.4门店维修业务《维修服务单》未上传中台",
					"K2.5洗后净衣回店后超过2天未配送给客人","K2.6门店有一星期以上的超时未完成订单","K2.7收银台或明显位置未粘贴“快递禁忌品”、“邮政寄递出示身份证”图片","K2.8门店揽收快件外包装上没有加盖国安社区快递验视章",
					"K2.9门店揽收快件外包装已被封口、无法二次验视","K3门店划片数量不正确","L1、门店518联盟商家未落实宣传物料的摆放（仅限5.5-18检查）",
					"L2、门店未按计划开展地推（仅限5.5-18检查）","L3-1门店地推点员工仪容仪表不符合规范（仅限5.5-18检查）","L3-2门店地推点未按要求摆放宣传物料（仅限5.5-18检查）"};
			String[] headers_key = {"storeno","a1_1","a1_2","a2_1","a2_2","a3_1","a3_2","a4_1","a4_2","a4_3","a5_1","a5_2","a5_3","a6","a7_1","a7_2","a8","a9","a10_1","a10_2","a11","b1_1","b1_2","b2",
					"b3","b4","b5_1","b5_2","b6_1","b6_2","b7","c1","c2","c3_1","c3_2","c4","c5","c6","c7_1","c7_2","c8_1","c8_2","d1","d2_1","d2_2","d3_1","d3_2","d3_3","d3_4","d4_1","d4_2",
					"d4_3","d5_1","d5_2","d6","d7_1","d7_2","d8","d9_1","d9_2","d10","d11_1","d11_2","d11_3","d11_4","d12","d13_1","d13_2","e1_1","e1_2","e2","e3","e4_1","e4_2","e5","e6","e7",
					"e8_1","e8_2","e9","f1","f2","f3","f4_1","f4_2","f4_3","f5","f6","f7","f8_1","f8_2","f9_1","f9_2","g1","g2_1","g2_2","g2_3","g3","h1_1","h1_2","h2","h3","h4_1","h4_2","h4_3",
					"h5_1","h5_2","h6","h7_1","h7_2","h8","h9_1","h9_2","h9_3","h9_4","h9_5","h9_6","h10_1","h10_2","h11_1","h11_2","i1_1","i1_2","i2","i3","i4_1","i4_2","i5_1","i5_2","i5_3",
					"i6_1","i6_2","i7","j1","j2_1","j2_2","j3","k1","k2_1","k2_2","k2_3_1","k2_3_2","k2_4","k2_5","k2_6","k2_7","k2_8","k2_9","k3","l1","l2","l3_1","l3_2"};
			

			sheet.addMergedRegion(new CellRangeAddress(0,0,1,20));
			sheet.addMergedRegion(new CellRangeAddress(0,0,21,30));
			sheet.addMergedRegion(new CellRangeAddress(0,0,31,41));
			sheet.addMergedRegion(new CellRangeAddress(0,0,42,67));
			sheet.addMergedRegion(new CellRangeAddress(0,0,68,79));
			sheet.addMergedRegion(new CellRangeAddress(0,0,80,92));
			sheet.addMergedRegion(new CellRangeAddress(0,0,93,97));
			sheet.addMergedRegion(new CellRangeAddress(0,0,98,120));
			sheet.addMergedRegion(new CellRangeAddress(0,0,121,132));
			sheet.addMergedRegion(new CellRangeAddress(0,0,133,136));
			sheet.addMergedRegion(new CellRangeAddress(0,0,137,147));
			sheet.addMergedRegion(new CellRangeAddress(0,0,148,152));
			for(int i = 0;i < str_headers1.length;i++){
				HSSFCell cell = row0.createCell(i);
				/*// 创建单元格样式
				style_header = wb.createCellStyle();
				style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style_header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				style_header.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
				style_header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

				// 设置边框
				style_header.setBottomBorderColor(HSSFColor.BLACK.index);
				style_header.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style_header.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style_header.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style_header.setBorderTop(HSSFCellStyle.BORDER_THIN);*/
				cell.setCellStyle(style_header);
				cell.setCellValue(new HSSFRichTextString(str_headers1[i]));
			}
			
			for(int i = 0;i < str_headers.length;i++){
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(getHeaderStyle());
				cell.setCellValue(new HSSFRichTextString(str_headers[i]));
			}

			for(int i = 1;i <= list.size();i++){
				row = sheet.createRow(i+1);
				for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++){
					setCellValue(row, cellIndex, list.get(i-1).get(headers_key[cellIndex]));
				}
			}


			File file_xls = new File(str_file_dir_path + File.separator+System.currentTimeMillis()+"明查台账.xls");
			if(file_xls.exists()){
				file_xls.delete();
			}
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(file_xls.getAbsoluteFile());
				wb.write(os);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(os != null){
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			result.put("message","导出成功！");
			result.put("status","success");
			result.put("data", str_web_path.concat(file_xls.getName()));
		}else{
			result.put("message","没有符合条件的数据！");
			result.put("status","null");
			return result;
		}


		return result;
	}

	

}
