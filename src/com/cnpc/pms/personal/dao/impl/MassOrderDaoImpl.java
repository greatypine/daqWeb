package com.cnpc.pms.personal.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.personal.dao.MassOrderDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Function：清洗出的订单Dao实现
 * @author：chenchuang
 * @date:2018年1月9日 下午3:33:54
 *
 * @version V1.0
 */
public class MassOrderDaoImpl extends BaseDAOHibernate implements MassOrderDao {
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryCitynoByCode(String cityCode){
		String sql = "SELECT cityno FROM t_dist_citycode WHERE 1=1 ";
		if(StringUtils.isNotEmpty(cityCode)){
			sql = sql + " AND citycode='"+cityCode+"' ";
		}
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		Map<String, Object> order_obj = null;
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> queryMassOrder(MassOrderDto massOrderDto, PageInfo pageInfo, String timeFlag) {
		String sqlA = "select CONCAT(a.id,'') as id, a.order_sn,IFNULL(a.customer_mobile_phone,'') as customer_mobile_phone,a.eshop_name,a.employee_name,"
				+ "a.pubseas_label,a.abnormal_label,a.return_label,a.loan_label,a.create_time,a.sign_time,a.return_time,a.appointment_start_time,a.employee_no,IFNULL(a.trading_price,0) as trading_price,"
				+ "IFNULL(a.payable_price,0) as payable_price,IFNULL(ROUND(a.gmv_price,2),0) as gmv_price,a.customer_name,IFNULL(a.addr_name,'') as addr_name,"
				+ "IFNULL(a.addr_mobilephone,'') as addr_mobilephone,IFNULL(a.addr_address,'') as addr_address,a.channel_name,a.department_name,"
				+ "a.customer_isnew_flag,a.area_code,a.info_employee_a_no,IFNULL(a.order_tag1,'') as order_tag1,IFNULL(a.score,'') as score,IFNULL(a.order_tag2,'') as order_tag2, "
				+ "CASE a.order_source WHEN 'app' THEN 'APP' WHEN 'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' "
				+ "WHEN 'pad' THEN '智能终端' WHEN 'score' THEN '积分' WHEN 'web' THEN 'WEB' WHEN 'citic_vip_gift' THEN '中信vip礼品' WHEN 'tv' THEN '电视' WHEN 'microMarket' THEN '微超订单' ELSE '无' END AS order_source "
				+ ",a.contract_id,IFNULL(a.business_type,'') as business_type,IFNULL(FORMAT(a.order_profit, 2),'') as order_profit,IFNULL(a.apportion_rebate,0) as apportion_rebate,"
				+ "IFNULL(a.apportion_coupon,0) as apportion_coupon,IFNULL(a.cost_price,0) as cost_price,IFNULL(a.contract_method,'') as contract_method "
				+ "from ";

		String sqlB = sqlA;

		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sqlA = sqlA + " df_mass_order_daily a ";
			sqlB = sqlB + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sqlA = sqlA + " df_mass_order_monthly a ";
			sqlB = sqlB + " df_mass_order_monthly a ";
		} else {
			sqlA = sqlA + " df_mass_order_total a ";
			sqlB = sqlB + " df_mass_order_total a ";
		}

		String sqlTemp2 = " join (select id from ";
		
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sqlTemp2 = sqlTemp2 + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sqlTemp2 = sqlTemp2 + " df_mass_order_monthly a ";
		} else {
			sqlTemp2 = sqlTemp2 + " df_mass_order_total a ";
		}

		if(StringUtils.isNotEmpty(massOrderDto.getBusi_names())){
			sqlTemp2 = sqlTemp2 + " JOIN df_activity_scope das ON a.store_code=das.store_no ";
			sqlB = sqlB + " JOIN df_activity_scope das ON a.store_code=das.store_no ";
		}
		
		sqlA = sqlA + sqlTemp2 + " where a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";
		sqlB = sqlB + " where a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";

		String sql = "";
		if (StringUtils.isNotEmpty(massOrderDto.getBeginDate())) {
			sql = sql + " and (a.sign_time between '" + massOrderDto.getBeginDate() + " 00:00:00' and '"
					+ massOrderDto.getEndDate() + " 23:59:59')";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getOrder_labels())) {
			sql = sql + " and (";
			String[] names = massOrderDto.getOrder_labels().split(",");
			for (int i = 0; i < names.length; i++) {
				if ("公海订单".equals(names[i].trim())) {
					sql = sql + " a.pubseas_label='1' ";
				} else if ("快周边".equals(names[i].trim())) {
					sql = sql + " a.loan_label='4' ";
				} else if ("异常订单".equals(names[i].trim())) {
					sql = sql + " a.abnormal_label='1' ";
				} else if ("已退款".equals(names[i].trim())) {
					sql = sql + " a.return_label='1' ";
				} else if ("小贷".equals(names[i].trim())) {
					sql = sql + " a.loan_label='1' ";
				} else if ("房".equals(names[i].trim())) {
					sql = sql + " a.loan_label='2' ";
				} else if ("汽车订单".equals(names[i].trim())) {
					sql = sql + " a.loan_label='3' ";
				} else if ("微信礼品卡".equals(names[i].trim())) {
					sql = sql + " a.loan_label='5' ";
				} else if ("集采订单".equals(names[i].trim())) {
					sql = sql + " a.order_tag1 like '%B%'  ";
				} else if ("开卡礼订单".equals(names[i].trim())) {
					sql = sql + " a.order_tag1 like '%K%'  ";
				} else if ("社员订单".equals(names[i].trim())) {
					sql = sql + " a.order_tag1 like '%M%'  ";
				} else if ("积分订单".equals(names[i].trim())) {
					sql = sql + " a.score is not null  ";
				}
				if (i == names.length - 1) {
					sql = sql + " )";
				} else {
					sql = sql + " or ";
				}
			}
		}
		if (StringUtils.isNotEmpty(massOrderDto.getOrder_sn())) {
			sql = sql + " and a.order_sn ='" + massOrderDto.getOrder_sn().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEshop_id())) {
			sql = sql + " and a.eshop_id ='" + massOrderDto.getEshop_id().trim() + "'";
		}
		if(StringUtils.isNotEmpty(massOrderDto.getStore_no())){
			Map<String,Object> position_obj = queryPlatformidByCode(massOrderDto.getStore_no());
			if (position_obj != null) {
				sql = sql + " and (a.store_code ='" + massOrderDto.getStore_no().trim()+ "' or a.normal_store_id='"+(String) position_obj.get("platformid")+"')";
			}else{
				sql = sql + " and a.store_code ='" + massOrderDto.getStore_no().trim()+ "'";
			}
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEshop_name())) {
			sql = sql + " and a.eshop_name = '" + massOrderDto.getEshop_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getCustomer_isnew())) {
			sql = sql + " and a.customer_isnew_flag in (" + massOrderDto.getCustomer_isnew() + ")";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getCity_name())) {
			sql = sql + " and LPAD(a.store_city_code,4,0) = '" + massOrderDto.getCity_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getDepartment_name())) {
			sql = sql + " and a.department_name = '" + massOrderDto.getDepartment_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getDepartment_names())) {
			sql = sql + " and (";
			String[] names = massOrderDto.getDepartment_names().split(",");
			for (int i = 0; i < names.length; i++) {
				if (i == names.length - 1) {
					sql = sql + " a.department_name = '" + names[i].trim() + "')";
				} else {
					sql = sql + " a.department_name = '" + names[i].trim() + "' or ";
				}
			}
		}
		if (StringUtils.isNotEmpty(massOrderDto.getChannel_name())) {
			sql = sql + " and a.channel_name = '" + massOrderDto.getChannel_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getCustomer_name())) {
			sql = sql + " and a.customer_name = '" + massOrderDto.getCustomer_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getCustomer_phone())) {
			sql = sql + " and a.customer_mobile_phone ='" + massOrderDto.getCustomer_phone().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getAddr_customer_name())) {
			sql = sql + " and a.addr_name = '" + massOrderDto.getAddr_customer_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getAddr_customer_phone())) {
			sql = sql + " and a.addr_mobilephone ='" + massOrderDto.getAddr_customer_phone().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_no())) {
			sql = sql + " and a.employee_no ='" + massOrderDto.getEmployee_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_mobile())) {
			sql = sql + " and a.employee_phone ='" + massOrderDto.getEmployee_mobile().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no())) {
			sql = sql + " and a.info_employee_a_no ='" + massOrderDto.getEmployee_a_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no_a())) {
			sql = sql + " and a.info_employee_a_no ='" + massOrderDto.getEmployee_a_no_a().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_code())) {
			sql = sql + " and a.area_code ='" + massOrderDto.getArea_code().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone())) {
			sql = sql
					+ " and a.info_employee_a_no in (select employee_no from t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone_a())) {
			sql = sql
					+ " and a.info_employee_a_no in (select employee_no from t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone_a().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_name())) {
			sql = sql + " and a.area_code in (select area_no from t_area ta where ta.name like '%"
					+ massOrderDto.getArea_name().trim() + "%') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getVillage_name())) {
			sql = sql
					+ " and a.info_village_code in (select vc.code from tiny_village_code vc where vc.tiny_village_name like '%"
					+ massOrderDto.getVillage_name().trim() + "%') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getBusi_names())) {
			sql = sql + " and a.order_tag2 in ( " + massOrderDto.getBusi_names() + ")";
		}
		if(StringUtils.isNotEmpty(massOrderDto.getProfit_price_min())){
			sql=sql+" AND a.order_profit >="+massOrderDto.getProfit_price_min();
		}
		if(StringUtils.isNotEmpty(massOrderDto.getProfit_price_max())){
			sql=sql+" AND a.order_profit <="+massOrderDto.getProfit_price_max();
		}
		if(StringUtils.isNotEmpty(massOrderDto.getContract_method())){
			sql=sql+" AND a.contract_method ='" + massOrderDto.getContract_method().trim() + "'";
		}
		if(StringUtils.isNotEmpty(massOrderDto.getSort_tag()) && StringUtils.isNotEmpty(massOrderDto.getSort_type())){
			if("price".equals(massOrderDto.getSort_type())){
				sqlA = sqlA + sql + " ORDER BY a.trading_price "+massOrderDto.getSort_tag()+" ";
			}else if("profit".equals(massOrderDto.getSort_type())){
				sqlA = sqlA + sql + " ORDER BY a.order_profit "+massOrderDto.getSort_tag()+" ";
			}
		}else{
			sqlA = sqlA + sql + " ORDER BY a.sign_time "+massOrderDto.getSort_tag()+" ";
		}
		
		sqlB = sqlB + sql;

		String sql_count = "SELECT COUNT(1) as total FROM (" + sqlB + ") T";

		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql_count);
		Object total = query_count.uniqueResult();
		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

		int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
		int recordsPerPage = pageInfo.getRecordsPerPage();
		sqlA = sqlA + " LIMIT " + startData + "," + recordsPerPage + ") b ON a.id = b.id";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sqlA);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		if (list != null && list.size() > 0) {
			for (Map map : list) {
				String area_code = (String) map.get("area_code");
				String order_sn = (String) map.get("order_sn");
				if (StringUtils.isNotEmpty(area_code)) {
					Map result = this.queryAreaDetailByCode(area_code, order_sn,timeFlag);
					map.put("area_name", result.get("area_name"));
				}
			}
		}

		Map<String, Object> map_result = new HashMap<String, Object>();
		Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> exportOrder(MassOrderDto massOrderDto, String timeFlag) {
		String sql = "select a.order_sn,IFNULL(a.area_code,'') as area_code,IFNULL(a.info_village_code,'') as village_code,IFNULL(a.info_employee_a_no,'') AS info_employee_a_no,"
				+ "IFNULL(a.customer_mobile_phone,'') as customer_mobile_phone,IFNULL(a.trading_price,0) as trading_price,IFNULL(a.payable_price,0) as payable_price,"
				+ "IFNULL(ROUND(a.gmv_price,2),0) as gmv_price,IFNULL(a.create_time,'') AS create_time,IFNULL(a.sign_time,'') AS sign_time,IFNULL(a.return_time,'') AS return_time,IFNULL(a.appointment_start_time,'') AS appointment_start_time,"
				+ "IFNULL(a.employee_name,'') AS employee_name,IFNULL(a.employee_phone,'') AS employee_phone,"
				+ "a.eshop_name,a.store_name,a.store_code,a.channel_name,a.department_name,a.store_city_name,CASE WHEN a.pubseas_label='1' THEN '是'  ELSE '否' END AS pubseas_label,"
				+ "CASE WHEN a.abnormal_label='1' THEN '是'  ELSE '否' END AS abnormal_label,CASE WHEN a.return_label='1' THEN '是'  ELSE '否' END AS return_label,"
				+ "CASE WHEN a.loan_label='1' THEN '是'  ELSE '否' END AS loan_label,CASE WHEN a.loan_label='3' THEN '是'  ELSE '否' END AS car_label,"
				+ "CASE WHEN a.loan_label='4' THEN '是'  ELSE '否' END AS quick_label,CASE WHEN a.loan_label='5' THEN '是'  ELSE '否' END AS gift_label,"
				+ "CASE WHEN a.customer_isnew_flag='20' THEN '拉新20元' WHEN a.customer_isnew_flag='10' THEN '拉新10元' WHEN a.customer_isnew_flag='0' THEN '拉新'  ELSE '否' END AS customer_isnew_flag,"
				+ "CASE WHEN a.order_tag1 like '%B%' THEN '是'  ELSE '否' END AS order_tag_b,CASE WHEN a.order_tag1 like '%K%' THEN '是'  ELSE '否' END AS order_tag_k,"
				+ "CASE WHEN a.order_tag1 like '%S%' THEN '是'  ELSE '否' END AS order_tag_s, CASE WHEN a.score is not null THEN '是' ELSE '否' END AS score,"
				+ "CASE WHEN a.order_tag2 like '%1%' THEN '是'  ELSE '否' END AS order_tag_product, CASE WHEN a.order_tag2 like '%2%' THEN '是' ELSE '否' END AS order_tag_service,"
				+ "CASE WHEN a.order_tag2 like '%3%' THEN '是'  ELSE '否' END AS order_tag_groupon, "
				+ "CASE WHEN a.order_tag1 like '%M%' THEN '是'  ELSE '否' END AS order_tag_member, "
				+ "CASE a.order_source WHEN 'app' THEN 'APP' WHEN 'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' "
				+ "WHEN 'pad' THEN '智能终端' WHEN 'score' THEN '积分' WHEN 'web' THEN 'WEB' WHEN 'citic_vip_gift' THEN '中信vip礼品' WHEN 'tv' THEN '电视' WHEN 'microMarket' THEN '微超订单' ELSE '无' END AS order_source, "
				+ "IFNULL(a.business_type,'') as business_type,IFNULL(a.apportion_coupon,0) as apportion_coupon,IFNULL(FORMAT(a.order_profit, 2),'') as order_profit,"
				+ "IFNULL(CASE a.contract_method WHEN  'price' THEN '从价' WHEN  'volume' THEN '从量' WHEN  'percent' THEN '从率' END,'') as contract_method "
				+ "from ";

		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}
		if(StringUtils.isNotEmpty(massOrderDto.getBusi_names())){
			sql = sql + " JOIN df_activity_scope das ON a.store_code=das.store_no ";
		}
		sql = sql + " where a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";
		if (StringUtils.isNotEmpty(massOrderDto.getBeginDate())) {
			sql = sql + " and (a.sign_time between '" + massOrderDto.getBeginDate() + " 00:00:00' and '"
					+ massOrderDto.getEndDate() + " 23:59:59')";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getOrder_labels())) {
			sql = sql + " and (";
			String[] names = massOrderDto.getOrder_labels().split(",");
			for (int i = 0; i < names.length; i++) {
				if ("公海订单".equals(names[i].trim())) {
					sql = sql + " a.pubseas_label='1' ";
				} else if ("快周边".equals(names[i].trim())) {
					sql = sql + " a.loan_label='4' ";
				} else if ("异常订单".equals(names[i].trim())) {
					sql = sql + " a.abnormal_label='1' ";
				} else if ("已退款".equals(names[i].trim())) {
					sql = sql + " a.return_label='1' ";
				} else if ("小贷".equals(names[i].trim())) {
					sql = sql + " a.loan_label='1' ";
				} else if ("房".equals(names[i].trim())) {
					sql = sql + " a.loan_label='2' ";
				} else if ("汽车订单".equals(names[i].trim())) {
					sql = sql + " a.loan_label='3' ";
				} else if ("微信礼品卡".equals(names[i].trim())) {
					sql = sql + " a.loan_label='5' ";
				} else if ("集采订单".equals(names[i].trim())) {
					sql = sql + " a.order_tag1 like '%B%'  ";
				} else if ("开卡礼订单".equals(names[i].trim())) {
					sql = sql + " a.order_tag1 like '%K%'  ";
				} else if ("社员订单".equals(names[i].trim())) {
					sql = sql + " a.order_tag1 like '%M%'  ";
				} else if ("积分订单".equals(names[i].trim())) {
					sql = sql + " a.score is not null  ";
				}
				if (i == names.length - 1) {
					sql = sql + " )";
				} else {
					sql = sql + " or ";
				}
			}
		}
		if (StringUtils.isNotEmpty(massOrderDto.getOrder_sn())) {
			sql = sql + " and a.order_sn ='" + massOrderDto.getOrder_sn().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEshop_id())) {
			sql = sql + " and a.eshop_id ='" + massOrderDto.getEshop_id().trim() + "'";
		}
		if(StringUtils.isNotEmpty(massOrderDto.getStore_no())){
			Map<String,Object> position_obj = queryPlatformidByCode(massOrderDto.getStore_no());
			if (position_obj != null) {
				sql = sql + " and (a.store_code ='" + massOrderDto.getStore_no().trim()+ "' or a.normal_store_id='"+(String) position_obj.get("platformid")+"')";
			}else{
				sql = sql + " and a.store_code ='" + massOrderDto.getStore_no().trim()+ "'";
			}
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEshop_name())) {
			sql = sql + " and a.eshop_name = '" + massOrderDto.getEshop_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getCustomer_isnew())) {
			sql = sql + " and a.customer_isnew_flag in (" + massOrderDto.getCustomer_isnew() + ")";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getCity_name())) {
			sql = sql + " and LPAD(a.store_city_code,4,0) = '" + massOrderDto.getCity_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getDepartment_name())) {
			sql = sql + " and a.department_name = '" + massOrderDto.getDepartment_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getDepartment_names())) {
			sql = sql + " and (";
			String[] names = massOrderDto.getDepartment_names().split(",");
			for (int i = 0; i < names.length; i++) {
				if (i == names.length - 1) {
					sql = sql + " a.department_name = '" + names[i].trim() + "')";
				} else {
					sql = sql + " a.department_name = '" + names[i].trim() + "' or ";
				}
			}
		}
		if (StringUtils.isNotEmpty(massOrderDto.getChannel_name())) {
			sql = sql + " and a.channel_name = '" + massOrderDto.getChannel_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getCustomer_name())) {
			sql = sql + " and a.customer_name = '" + massOrderDto.getCustomer_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getCustomer_phone())) {
			sql = sql + " and a.customer_mobile_phone ='" + massOrderDto.getCustomer_phone().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getAddr_customer_name())) {
			sql = sql + " and a.addr_name = '" + massOrderDto.getAddr_customer_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getAddr_customer_phone())) {
			sql = sql + " and a.addr_mobilephone ='" + massOrderDto.getAddr_customer_phone().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_no())) {
			sql = sql + " and a.employee_no ='" + massOrderDto.getEmployee_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_mobile())) {
			sql = sql + " and a.employee_phone ='" + massOrderDto.getEmployee_mobile().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no())) {
			sql = sql + " and a.info_employee_a_no ='" + massOrderDto.getEmployee_a_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_code())) {
			sql = sql + " and a.area_code ='" + massOrderDto.getArea_code().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no_a())) {
			sql = sql + " and a.info_employee_a_no ='" + massOrderDto.getEmployee_a_no_a().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone())) {
			sql = sql
					+ " and a.info_employee_a_no in (select employee_no from t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone_a())) {
			sql = sql
					+ " and a.info_employee_a_no in (select employee_no from t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone_a().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_name())) {
			sql = sql + " and a.area_code in (select area_no from t_area ta where ta.name like '%"
					+ massOrderDto.getArea_name().trim() + "%') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getVillage_name())) {
			sql = sql
					+ " and a.info_village_code in (select vc.code from tiny_village_code vc where vc.tiny_village_name like '%"
					+ massOrderDto.getVillage_name().trim() + "%') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getBusi_names())) {
			sql = sql + " and a.order_tag2 in ( " + massOrderDto.getBusi_names() + ")";
		}
		if(StringUtils.isNotEmpty(massOrderDto.getProfit_price_min())){
			sql=sql+" AND a.order_profit >="+massOrderDto.getProfit_price_min();
		}
		if(StringUtils.isNotEmpty(massOrderDto.getProfit_price_max())){
			sql=sql+" AND a.order_profit <="+massOrderDto.getProfit_price_max();
		}
		if(StringUtils.isNotEmpty(massOrderDto.getContract_method())){
			sql=sql+" AND a.contract_method ='" + massOrderDto.getContract_method().trim() + "'";
		}
		if(StringUtils.isNotEmpty(massOrderDto.getSort_tag()) && StringUtils.isNotEmpty(massOrderDto.getSort_type())){
			sql =  sql + " ORDER BY a.trading_price "+massOrderDto.getSort_tag()+" ";
		}else{
			sql =  sql + " ORDER BY a.sign_time "+massOrderDto.getSort_tag()+" ";
		}

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> queryReturnMassOrder(MassOrderDto massOrderDto, PageInfo pageInfo, String timeFlag) {
		String sqlA = "select CONCAT(a.id,'') as id, a.order_sn,IFNULL(a.customer_mobile_phone,'') as customer_mobile_phone,a.eshop_name,a.employee_name,"
				+ "a.pubseas_label,a.abnormal_label,a.return_label,a.loan_label,a.create_time,a.sign_time,a.return_time,a.appointment_start_time,a.employee_no,IFNULL(a.trading_price,0) as trading_price,"
				+ "IFNULL(a.payable_price,0) as payable_price,IFNULL(ROUND(a.gmv_price,2),0) as gmv_price,IFNULL(ROUND(a.returned_amount,2),0) as returned_amount,a.customer_name,"
				+ "IFNULL(a.addr_name,'') as addr_name,IFNULL(a.addr_mobilephone,'') as addr_mobilephone,IFNULL(a.addr_address,'') as addr_address,"
				+ "a.channel_name,a.department_name,a.customer_isnew_flag,a.area_code,a.info_employee_a_no,IFNULL(a.order_tag1,'') as order_tag1,IFNULL(a.score,'') as score"
				+ ",a.contract_id,IFNULL(a.business_type,'') as business_type,IFNULL(FORMAT(a.order_profit, 2),'') as order_profit,IFNULL(a.apportion_rebate,0) as apportion_rebate,"
				+ "IFNULL(a.apportion_coupon,0) as apportion_coupon,IFNULL(a.cost_price,0) as cost_price,IFNULL(a.contract_method,'') as contract_method "
				+ " from ";

		String sqlB = sqlA;

		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sqlA = sqlA + " df_mass_order_daily a ";
			sqlB = sqlB + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sqlA = sqlA + " df_mass_order_monthly a ";
			sqlB = sqlB + " df_mass_order_monthly a ";
		} else {
			sqlA = sqlA + " df_mass_order_total a ";
			sqlB = sqlB + " df_mass_order_total a ";
		}

		String sqlTemp2 = " join (select id from ";
		
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sqlTemp2 = sqlTemp2 + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sqlTemp2 = sqlTemp2 + " df_mass_order_monthly a ";
		} else {
			sqlTemp2 = sqlTemp2 + " df_mass_order_total a ";
		}

		sqlA = sqlA + sqlTemp2 + " where a.return_label = '1' AND a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";
		sqlB = sqlB + " where a.return_label = '1' AND a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";

		String sql = "";
		if (StringUtils.isNotEmpty(massOrderDto.getBeginDate())) {
			sql = sql + " and (a.return_time between '" + massOrderDto.getBeginDate() + " 00:00:00' and '"
					+ massOrderDto.getEndDate() + " 23:59:59')";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getOrder_sn())) {
			sql = sql + " and a.order_sn ='" + massOrderDto.getOrder_sn().trim() + "'";
		}
		if(StringUtils.isNotEmpty(massOrderDto.getStore_no())){
			Map<String,Object> position_obj = queryPlatformidByCode(massOrderDto.getStore_no());
			if (position_obj != null) {
				sql = sql + " and (a.store_code ='" + massOrderDto.getStore_no().trim()+ "' or a.normal_store_id='"+(String) position_obj.get("platformid")+"')";
			}else{
				sql = sql + " and a.store_code ='" + massOrderDto.getStore_no().trim()+ "'";
			}
		}
		if (StringUtils.isNotEmpty(massOrderDto.getCity_name())) {
			sql = sql + " and LPAD(a.store_city_code,4,0) = '" + massOrderDto.getCity_name().trim() + "'";
		}

		sqlA = sqlA + sql + " ORDER BY a.return_time desc ";
		sqlB = sqlB + sql;

		String sql_count = "SELECT COUNT(1) as total FROM (" + sqlB + ") T";

		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql_count);
		Object total = query_count.uniqueResult();
		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

		int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
		int recordsPerPage = pageInfo.getRecordsPerPage();
		sqlA = sqlA + " LIMIT " + startData + "," + recordsPerPage + ") b ON a.id = b.id";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sqlA);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		if (list != null && list.size() > 0) {
			for (Map map : list) {
				String area_code = (String) map.get("area_code");
				String order_sn = (String) map.get("order_sn");
				if (StringUtils.isNotEmpty(area_code)) {
					Map result = this.queryAreaDetailByCode(area_code, order_sn,timeFlag);
					map.put("area_name", result.get("area_name"));
				}
			}
		}

		Map<String, Object> map_result = new HashMap<String, Object>();
		Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> exportReturnOrder(MassOrderDto massOrderDto, String timeFlag) {
		String sql = "select a.order_sn,IFNULL(a.area_code,'') as area_code,IFNULL(a.info_village_code,'') as village_code,a.info_employee_a_no,"
				+ "IFNULL(a.customer_mobile_phone,'') as customer_mobile_phone,IFNULL(a.trading_price,0) as trading_price,"
				+ "IFNULL(a.payable_price,0) as payable_price,IFNULL(ROUND(a.gmv_price,2),0) as gmv_price,IFNULL(ROUND(a.returned_amount,2),0) as returned_amount,"
				+ "IFNULL(a.create_time,'') AS create_time,IFNULL(a.appointment_start_time,'') AS appointment_start_time,IFNULL(a.sign_time,'') as sign_time,IFNULL(a.return_time,'') as return_time,"
				+ "a.employee_name,a.employee_phone,a.eshop_name,a.store_name,a.store_code,a.channel_name,a.department_name,a.store_city_name,"
				+ "CASE WHEN a.pubseas_label='1' THEN '是'  ELSE '否' END AS pubseas_label,CASE WHEN a.abnormal_label='1' THEN '是'  ELSE '否' END AS abnormal_label,"
				+ "CASE WHEN a.return_label='1' THEN '是'  ELSE '否' END AS return_label,CASE WHEN a.loan_label='1' THEN '是'  ELSE '否' END AS loan_label,"
				+ "CASE WHEN a.loan_label='3' THEN '是'  ELSE '否' END AS car_label,CASE WHEN a.loan_label='4' THEN '是'  ELSE '否' END AS quick_label,"
				+ "CASE WHEN a.loan_label='5' THEN '是'  ELSE '否' END AS gift_label,CASE WHEN a.customer_isnew_flag='20' THEN '拉新20元' WHEN a.customer_isnew_flag='10' "
				+ "THEN '拉新10元' WHEN a.customer_isnew_flag='0' THEN '拉新'  ELSE '否' END AS customer_isnew_flag,CASE WHEN a.order_tag1 like '%B%' THEN '是'  ELSE '否' END AS order_tag_b,"
				+ "CASE WHEN a.order_tag1 like '%K%' THEN '是'  ELSE '否' END AS order_tag_k,CASE WHEN a.order_tag1 like '%S%' THEN '是'  ELSE '否' END AS order_tag_s,"
				+ "CASE WHEN a.score is not null THEN '是' ELSE '否' END AS score,IFNULL(FORMAT(a.order_profit, 2),'') as order_profit,"
				+ "IFNULL(a.apportion_coupon,0) as apportion_coupon,IFNULL(CASE a.contract_method WHEN  'price' THEN '从价' WHEN  'volume' THEN '从量' WHEN  'percent' THEN '从率' END,'') as contract_method from ";

		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_daily ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly ";
		} else {
			sql = sql + " df_mass_order_total ";
		}
		sql = sql + " a where a.return_label = '1' AND a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";
		if (StringUtils.isNotEmpty(massOrderDto.getBeginDate())) {
			sql = sql + " and (a.return_time between '" + massOrderDto.getBeginDate() + " 00:00:00' and '"
					+ massOrderDto.getEndDate() + " 23:59:59')";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getOrder_sn())) {
			sql = sql + " and a.order_sn ='" + massOrderDto.getOrder_sn().trim() + "'";
		}
		if(StringUtils.isNotEmpty(massOrderDto.getStore_no())){
			Map<String,Object> position_obj = queryPlatformidByCode(massOrderDto.getStore_no());
			if (position_obj != null) {
				sql = sql + " and (a.store_code ='" + massOrderDto.getStore_no().trim()+ "' or a.normal_store_id='"+(String) position_obj.get("platformid")+"')";
			}else{
				sql = sql + " and a.store_code ='" + massOrderDto.getStore_no().trim()+ "'";
			}
		}
		if (StringUtils.isNotEmpty(massOrderDto.getCity_name())) {
			sql = sql + " and LPAD(a.store_city_code,4,0) = '" + massOrderDto.getCity_name().trim() + "'";
		}
		sql = sql + " ORDER BY a.return_time desc";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryOrderInfoBySN(String order_sn) {
		String sql = "SELECT	CONCAT(dom.id, '') AS id, dom.order_sn,	dom.trading_price, dom.payable_price, dom.total_quantity, dom.create_time, dom.address_name, dom.address_mobilephone, "
				+ "dom.addr_address, IFNULL( dom.customer_mobile_phone, '' ) AS customer_mobile_phone, dom.eshop_name, dom.store_name, dom.employee_name, dom.employee_phone,dom.employee_no, "
				+ "dom.pubseas_label, dom.abnormal_label, dom.return_label,	dom.loan_label,	dom.sign_time, IFNULL(ta.NAME, '') AS area_name, IFNULL(th.NAME, '') AS employee_a_name,"
				+ "IFNULL(th.phone, '') AS emplyee_a_phone FROM	df_mass_order_monthly dom LEFT JOIN t_area ta ON dom.area_code = ta.area_no LEFT JOIN tiny_village_code tvc "
				+ "ON dom.info_village_code = tvc.CODE LEFT JOIN t_humanresources th ON dom.info_employee_a_no = th.employee_no GROUP BY dom.order_sn";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		Map<String, Object> order_obj = null;
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryAreaDetailByCode(String area_code, String order_sn,String timeFlag) {
		String sql = "SELECT IFNULL(a.area_code,'') AS area_code, IFNULL(a.store_name,'') AS store_name, IFNULL(vc.tiny_village_name,'') AS village_name, "
				+ "IFNULL(ta.`name`,'') AS area_name, vc.tiny_village_id as village_id, vc.code as village_code FROM ";
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}		
		sql = sql + "LEFT JOIN t_area ta ON a.area_code = ta.area_no LEFT JOIN tiny_village_code vc ON a.info_village_code = vc. CODE WHERE 1=1 ";

		if (StringUtils.isNotEmpty(area_code) && !area_code.equals("null")) {
			sql = sql + " AND a.area_code = '" + area_code + "'";
		}
		if (StringUtils.isNotEmpty(order_sn)) {
			sql = sql + " AND a.order_sn = '" + order_sn + "'";
		}

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		Map<String, Object> order_obj = null;
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryEmployeeBySN(String order_sn,String timeFlag) {
		String sql = "SELECT IFNULL(a.employee_name,'') AS employee_name, IFNULL(a.employee_phone,'') AS employee_phone, a.info_employee_a_no, IFNULL(th.`name`,'') AS employee_a_name, IFNULL(th.phone,'') as employee_a_phone FROM ";
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}				
		sql = sql + "LEFT JOIN t_humanresources th ON a.info_employee_a_no = th.employee_no WHERE	1 = 1 ";

		if (StringUtils.isNotEmpty(order_sn)) {
			sql = sql + " AND a.order_sn = '" + order_sn + "'";
		}

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		Map<String, Object> order_obj = null;
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryPlatformidByCode(String storeno){
		String sql = "select t.platformid from t_store t where 1=1 ";
		if(StringUtils.isNotEmpty(storeno)){
			sql = sql + " AND t.storeno='"+storeno+"' ";
		}
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		Map<String, Object> order_obj = null;
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;
	}

	@Override
	public Map<String, Object> queryOrderDetailBySN(String order_sn) {
		String sql = "SELECT CONCAT(a.id, '') AS id, a.order_sn, CONCAT(a.customer_id, '') AS customer_id, CONCAT(a.order_address_id, '') AS order_address_id, " +
				"a.total_quantity, a.trading_price, a.payable_price, CONCAT(a.order_status, '') AS order_status, CONCAT(a.order_type, '') AS order_type, a.employee_name, " +
				"a.employee_phone, a.appointment_start_time, a.create_time, a.sign_time AS receivedTime, a.appointment_end_time, a.seller_remark, a.addr_address AS address, " +
				"a.addr_name AS short_name, a.addr_mobilephone AS mobilephone FROM df_mass_order_total a WHERE a.order_sn = '" + order_sn + "'";
		// 获得查询数据
		Map<String, Object> order_obj = null;
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;

	}

	public List<Map<String, Object>> queryEmployeeOrderCountByStore(String storeId) {
		String sql = "select a.info_employee_a_no,th.name as employee_name,count(1) AS count from (select  tor.info_employee_a_no,tor.order_sn" +
				" from df_mass_order_monthly tor where  tor.customer_id not like 'fakecustomer%'" +
				" and DATE_FORMAT(tor.sign_time,'%Y-%m') = DATE_FORMAT(CURDATE(),'%Y-%m')"+
				" and tor.eshop_name NOT LIKE '%测试%' AND tor.eshop_white!='QA' and tor.info_employee_a_no is not null" +
				" and tor.store_name NOT LIKE '%测试%' and tor.store_white!='QA' AND tor.store_status =0 and " +
				" tor.store_id='"+storeId+"'" +
				" union all" +
				" select  tor.info_employee_a_no,tor.order_sn" +
				" from df_mass_order_monthly tor where  tor.customer_id not like 'fakecustomer%'" +
				" and DATE_FORMAT(tor.sign_time,'%Y-%m') = DATE_FORMAT(CURDATE(),'%Y-%m')" +
				" and tor.eshop_name NOT LIKE '%测试%' AND tor.eshop_white!='QA' and tor.info_employee_a_no is not null" +
				" and tor.store_name NOT LIKE '%测试%' and tor.store_white!='QA' AND tor.store_status =0 AND tor.normal_store_id ='"+storeId+"'" +
				") a left join t_humanresources th on   a.info_employee_a_no = th.employee_no group by a.info_employee_a_no ";


		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String,Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;

	}

	@Override
	public Map<String, Object> queryOrderInfoByOrderSN(String order_sn) {
		String sql = "select dom.*,tbu.name as employee_name,tbu.mobilephone as employee_phone from  df_mass_order_monthly dom left join tb_bizbase_user tbu on dom.info_employee_a_no = tbu.employeeId where order_sn='"+order_sn+"'";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String,Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(lst_data!=null&&lst_data.size()>0){
			return  lst_data.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Map<String, Object> queryOrderListOfEmployee(String employeeNo,PageInfo pageInfo) {
		String sql = "select dom.order_sn,dom.customer_id,tbu.name as employee_name,dom.customer_mobile_phone as mobilephone,dom.sign_time,dom.customer_name from df_mass_order_monthly dom left join tb_bizbase_user tbu on dom.info_employee_a_no = tbu.employeeId   where info_employee_a_no='"+employeeNo+"' order  by sign_time desc";

		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		Integer countnum = query.list().size();
		pageInfo.setTotalRecords(countnum);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();

		Map<String, Object> maps = new HashMap<String, Object>();
        int pages = 0;
		if(countnum>0){
			pages =(countnum-1)/10+1;
		}
		maps.put("data", lst_data);
		maps.put("totalpage", countnum);
		maps.put("pagenum", pages);
		return maps;

	}

	@Override
	public Map<String, Object> queryOrderListOfApp(String employeeNo, PageInfo pageInfo, String orderSN) {

		String whereStr = "";
		if(orderSN!=null&&!"".equals(orderSN)){
			whereStr = " and dom.order_sn='"+orderSN+"' ";
		}

		String sql = "select CONCAT(dom.id, '') AS id,dom.order_sn,dom.addr_address as placename,tbu.name as employee_name,dom.customer_mobile_phone as mobilephone,dom.sign_time as df_signed_time,dom.customer_name,CONCAT(dom.customer_id,'') AS customer_id from df_mass_order_monthly dom left join tb_bizbase_user tbu on dom.info_employee_a_no = tbu.employeeId   where info_employee_a_no='"+employeeNo+"'"+whereStr+" order  by sign_time desc";
		Session session =getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Map<String, Object>> lst_data = new ArrayList<Map<String,Object>>();
		Map<String, Object> map_result = new HashMap<String, Object>();

		SQLQuery query = session.createSQLQuery(sql);
		pageInfo.setTotalRecords(query.list().size());
		//获得查询数据
		lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(pageInfo.getRecordsPerPage()* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
		Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
		map_result.put("pageinfo",pageInfo);
		map_result.put("totalpage", total_pages);
		map_result.put("data", lst_data);

		return map_result;
	}

}
