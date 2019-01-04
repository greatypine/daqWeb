package com.cnpc.pms.personal.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.personal.dao.UserProfileDao;
import com.cnpc.pms.personal.dto.UserProfileDto;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Function：用户档案Dao实现
 * @author：chenchuang
 * @date:2018年3月8日 下午3:31:31
 *
 * @version V1.0
 */
public class UserProfileDaoImpl extends BaseDAOHibernate implements UserProfileDao {

	@Override
	public Map<String, Object> queryUserProfile(UserProfileDto userProfile, PageInfo pageInfo) {
		String sql = "select customer_name,customer_phone,customer_id,customer_source,trading_price_sum,order_count,first_order_time,last_order_time,area_code,tiny_village_code,regist_time,"
				+ "slient_time,user_model,trading_price_max,max(IFNULL(usertag_b ,'')) usertag_b,max(IFNULL(usertag_v,'')) usertag_v from (SELECT IFNULL(dup.customer_name,'未填写') as customer_name, "
				+ "dup.customer_phone, CONCAT(dup.customer_id,'') as customer_id, CASE dup.customer_source WHEN 'app' THEN 'APP' WHEN 'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' "
				+ "WHEN 'pad' THEN '智能终端' WHEN 'web' THEN 'WEB' WHEN 'citic' THEN '中信用户联盟' WHEN 'tv' THEN '电视' WHEN 'third_party' THEN '第三方' WHEN 'action' THEN '活动' ELSE '无' END AS customer_source, "
				+ "IFNULL(dup.trading_price_sum,0) as trading_price_sum,IFNULL(dup.order_count,0) as order_count,dup.first_order_time, dup.last_order_time, IFNULL(dup.area_code,'') as area_code, "
				+ "IFNULL(dup.tiny_village_code,'') as tiny_village_code, dup.regist_time,DATEDIFF(now(), dup.last_order_time) as slient_time,CASE WHEN dup.user_model='1' THEN '有'  ELSE '无' END AS user_model,"
				+ "CASE WHEN tag.usertag='B' THEN '集采用户' ELSE '' END usertag_b,CASE WHEN tag.usertag = 'V' THEN '合作社社员' ELSE '' END usertag_v,IFNULL(dup.trading_price_max,0) as trading_price_max "
				+ "FROM df_user_profile dup LEFT JOIN df_userprofile_tag tag ON dup.customer_id=tag.customer_id ";
		if(StringUtils.isNotEmpty(userProfile.getOpen_card_time_begin())){
			sql = sql + "LEFT JOIN df_user_member dum ON dup.customer_id=dum.customer_id ";
		}
		sql = sql + "where dup.system_flag='SYS001' ";
		if(StringUtils.isNotEmpty(userProfile.getCity_name()) || StringUtils.isNotEmpty(userProfile.getStore_no())){
			sql = sql + " AND dup.customer_id IN (SELECT dus.customer_id FROM df_user_store dus WHERE 1=1 ";
			if(StringUtils.isNotEmpty(userProfile.getCity_name())){
				sql = sql + " AND dus.city_name='"+userProfile.getCity_name()+"'";
			}
			if(StringUtils.isNotEmpty(userProfile.getStore_no())){
				sql = sql + " AND dus.storeno='"+userProfile.getStore_no()+"'";
			}
			sql = sql +")";
		}
		if(StringUtils.isNotEmpty(userProfile.getCustomer_name())){
			sql=sql+" AND dup.customer_name ='"+userProfile.getCustomer_name()+"'";
		}
		if(StringUtils.isNotEmpty(userProfile.getCustomer_phone())){
			sql=sql+" AND dup.customer_phone ='"+userProfile.getCustomer_phone()+"'";
		}
		if(StringUtils.isNotEmpty(userProfile.getRegist_time_begin())){
			sql = sql + " AND (dup.regist_time between '" + userProfile.getRegist_time_begin() + " 00:00:00' and '"
					+ userProfile.getRegist_time_end() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userProfile.getFirst_order_time_begin())){
			sql = sql + " AND (dup.first_order_time between '" + userProfile.getFirst_order_time_begin() + " 00:00:00' and '"
					+ userProfile.getFirst_order_time_end() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userProfile.getLast_order_time_begin())){
			sql = sql + " AND (dup.last_order_time between '" + userProfile.getLast_order_time_begin() + " 00:00:00' and '"
					+ userProfile.getLast_order_time_end() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userProfile.getOpen_card_time_begin())){
			sql = sql + " AND (dum.opencard_time between '" + userProfile.getOpen_card_time_begin() + " 00:00:00' and '"
					+ userProfile.getOpen_card_time_end() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userProfile.getMt_flag())){
			sql=sql+" AND (dum.member_type = 'associator_start_2' or dum.member_type = 'yearCard' or dum.member_type = 'yearCard-19')";
		}
		if(StringUtils.isNotEmpty(userProfile.getTrading_price_min())){
			sql=sql+" AND dup.trading_price_sum >="+userProfile.getTrading_price_min();
		}
		if(StringUtils.isNotEmpty(userProfile.getTrading_price_max())){
			sql=sql+" AND dup.trading_price_sum <="+userProfile.getTrading_price_max();
		}
		if(StringUtils.isNotEmpty(userProfile.getTrading_price_max_down())){
			sql=sql+" AND dup.trading_price_max >="+userProfile.getTrading_price_max_down();
		}
		if(StringUtils.isNotEmpty(userProfile.getTrading_price_max_up())){
			sql=sql+" AND dup.trading_price_max <="+userProfile.getTrading_price_max_up();
		}
		if(StringUtils.isNotEmpty(userProfile.getOrder_count_min())){
			sql=sql+" AND dup.order_count >="+userProfile.getOrder_count_min();
		}
		if(StringUtils.isNotEmpty(userProfile.getOrder_count_max())){
			sql=sql+" AND dup.order_count <="+userProfile.getOrder_count_max();
		}
		if(StringUtils.isNotEmpty(userProfile.getUser_model())){
			sql=sql+" AND dup.user_model >="+userProfile.getUser_model();
		}
		if(StringUtils.isNotEmpty(userProfile.getSlient_time_min()) 
				&& ("B".equals(userProfile.getSlient_time_min()) || "V".equals(userProfile.getSlient_time_min()))){
			sql=sql+" AND tag.usertag ='"+userProfile.getSlient_time_min()+"'";
		}
		if(StringUtils.isNotEmpty(userProfile.getSlient_time_min()) || StringUtils.isNotEmpty(userProfile.getSlient_time_max())){
			sql=sql+" HAVING ";
			if(StringUtils.isNotEmpty(userProfile.getSlient_time_min()) && !"B".equals(userProfile.getSlient_time_min()) 
					&& !"V".equals(userProfile.getSlient_time_min())){
				sql=sql+" slient_time >= " + userProfile.getSlient_time_min() ;
			}else{
				sql=sql+" slient_time >= 0" ;
			}
			if(StringUtils.isNotEmpty(userProfile.getSlient_time_max())){
				sql=sql+" AND slient_time <= " + userProfile.getSlient_time_max() ;
			}
		}
		sql = sql + " ) aa GROUP BY customer_id ORDER BY last_order_time DESC ";
		String sql_count = "SELECT COUNT(1) as total FROM ("+sql+") T";

		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
		Object total = query_count.uniqueResult();
		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<?> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage()
						* (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();

		Map<String,Object> map_result = new HashMap<String,Object>();
		Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
		map_result.put("pageinfo",pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> exportUserProfile(UserProfileDto userProfile){
		String sql = "select customer_name,customer_phone,customer_id,customer_source,"
				+ "trading_price_sum,trading_price_max,order_count,TRUNCATE(trading_price_sum/order_count,2) as cus_sigle_price,"
				+ "first_order_time,last_order_time,area_code,tiny_village_code,regist_time,slient_time,user_model,max(IFNULL(is_b_tag,'')) is_b_tag,max(IFNULL(is_v_tag,'')) is_v_tag,is_sixty_tag,"
				+ "is_thirty_tag from (SELECT IFNULL(dup.customer_name,'未填写') as customer_name, dup.customer_phone, CONCAT(dup.customer_id,'') AS customer_id, "
				+ "CASE dup.customer_source WHEN 'app' THEN 'APP' WHEN 'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' WHEN 'pad' THEN '智能终端' WHEN 'web' THEN 'WEB' "
				+ "WHEN 'citic' THEN '中信用户联盟' WHEN 'tv' THEN '电视' WHEN 'third_party' THEN '第三方' WHEN 'action' THEN '活动' ELSE '无' END AS customer_source,IFNULL(dup.trading_price_sum,0) as trading_price_sum,"
				+ "IFNULL(dup.trading_price_max,0) as trading_price_max,IFNULL(dup.order_count,0) as order_count,DATE_FORMAT(dup.first_order_time,'%Y-%m-%d %H:%i:%S') as first_order_time, "
				+ "DATE_FORMAT(dup.last_order_time,'%Y-%m-%d %H:%i:%S') as last_order_time, IFNULL(dup.area_code,'') as area_code, IFNULL(dup.tiny_village_code,'') as tiny_village_code, "
				+ "DATE_FORMAT(dup.regist_time,'%Y-%m-%d %H:%i:%S') as regist_time,DATEDIFF(now(), dup.last_order_time) as slient_time,CASE WHEN dup.user_model='1' THEN '有'  ELSE '无' END AS user_model,"
				+ "CASE WHEN tag.usertag = 'B' THEN	'是' ELSE '否' END is_b_tag,CASE WHEN tag.usertag = 'V' THEN	'是' ELSE '否' END is_v_tag,"
				+ "CASE WHEN DATEDIFF(now(), dup.last_order_time)>=60 THEN '是' ELSE '否' END is_sixty_tag,"
				+ "CASE WHEN DATEDIFF(now(), dup.last_order_time)>=30 THEN '是' ELSE '否' END is_thirty_tag FROM df_user_profile dup LEFT JOIN df_userprofile_tag tag "
				+ "ON dup.customer_id=tag.customer_id ";
		if(StringUtils.isNotEmpty(userProfile.getOpen_card_time_begin())){
			sql = sql + "LEFT JOIN df_user_member dum ON dup.customer_id=dum.customer_id ";
		}
		sql = sql + "where dup.system_flag='SYS001' ";
		if(StringUtils.isNotEmpty(userProfile.getCity_name()) || StringUtils.isNotEmpty(userProfile.getStore_no())){
			sql = sql + " AND dup.customer_id IN (SELECT dus.customer_id FROM df_user_store dus WHERE 1=1 ";
			if(StringUtils.isNotEmpty(userProfile.getCity_name())){
				sql = sql + " AND dus.city_name='"+userProfile.getCity_name()+"'";
			}
			if(StringUtils.isNotEmpty(userProfile.getStore_no())){
				sql = sql + " AND dus.storeno='"+userProfile.getStore_no()+"'";
			}
			sql = sql +")";
		}
		if(StringUtils.isNotEmpty(userProfile.getCustomer_name())){
			sql=sql+" AND dup.customer_name ='"+userProfile.getCustomer_name()+"'";
		}
		if(StringUtils.isNotEmpty(userProfile.getCustomer_phone())){
			sql=sql+" AND dup.customer_phone ='"+userProfile.getCustomer_phone()+"'";
		}
		if(StringUtils.isNotEmpty(userProfile.getRegist_time_begin())){
			sql = sql + " AND (dup.regist_time between '" + userProfile.getRegist_time_begin() + " 00:00:00' and '"
					+ userProfile.getRegist_time_end() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userProfile.getFirst_order_time_begin())){
			sql = sql + " AND (dup.first_order_time between '" + userProfile.getFirst_order_time_begin() + " 00:00:00' and '"
					+ userProfile.getFirst_order_time_end() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userProfile.getLast_order_time_begin())){
			sql = sql + " AND (dup.last_order_time between '" + userProfile.getLast_order_time_begin() + " 00:00:00' and '"
					+ userProfile.getLast_order_time_end() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userProfile.getOpen_card_time_begin())){
			sql = sql + " AND (dum.opencard_time between '" + userProfile.getOpen_card_time_begin() + " 00:00:00' and '"
					+ userProfile.getOpen_card_time_end() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userProfile.getMt_flag())){
			sql=sql+" AND dum.member_type ='"+userProfile.getMt_flag()+"' ";
		}
		if(StringUtils.isNotEmpty(userProfile.getTrading_price_min())){
			sql=sql+" AND dup.trading_price_sum >="+userProfile.getTrading_price_min();
		}
		if(StringUtils.isNotEmpty(userProfile.getTrading_price_max())){
			sql=sql+" AND dup.trading_price_sum <="+userProfile.getTrading_price_max();
		}
		if(StringUtils.isNotEmpty(userProfile.getTrading_price_max_down())){
			sql=sql+" AND dup.trading_price_max >="+userProfile.getTrading_price_max_down();
		}
		if(StringUtils.isNotEmpty(userProfile.getTrading_price_max_up())){
			sql=sql+" AND dup.trading_price_max <="+userProfile.getTrading_price_max_up();
		}
		if(StringUtils.isNotEmpty(userProfile.getOrder_count_min())){
			sql=sql+" AND dup.order_count >="+userProfile.getOrder_count_min();
		}
		if(StringUtils.isNotEmpty(userProfile.getOrder_count_max())){
			sql=sql+" AND dup.order_count <="+userProfile.getOrder_count_max();
		}
		if(StringUtils.isNotEmpty(userProfile.getUser_model())){
			sql=sql+" AND dup.user_model >="+userProfile.getUser_model();
		}
		if(StringUtils.isNotEmpty(userProfile.getSlient_time_min()) 
				&& ("B".equals(userProfile.getSlient_time_min()) || "V".equals(userProfile.getSlient_time_min()))){
			sql=sql+" AND tag.usertag ='"+userProfile.getSlient_time_min()+"'";
		}
		if(StringUtils.isNotEmpty(userProfile.getSlient_time_min()) || StringUtils.isNotEmpty(userProfile.getSlient_time_max())){
			sql=sql+" HAVING ";
			if(StringUtils.isNotEmpty(userProfile.getSlient_time_min()) && !"B".equals(userProfile.getSlient_time_min()) 
					&& !"V".equals(userProfile.getSlient_time_min())){
				sql=sql+" slient_time >= " + userProfile.getSlient_time_min() ;
			}else{
				sql=sql+" slient_time >= 0" ;
			}
			if(StringUtils.isNotEmpty(userProfile.getSlient_time_max())){
				sql=sql+" AND slient_time <= " + userProfile.getSlient_time_max() ;
			}
		}
		sql = sql + " ) aa GROUP BY customer_id ORDER BY last_order_time DESC ";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryDetailByCusId(String customer_id){
		String sql = "SELECT IFNULL(dus.city_name,'') AS city_name,	IFNULL(ts. NAME,'') AS store_name, IFNULL(dus.order_sn,'') AS order_sn, "
				+ "IFNULL(dus.first_order_time,'') AS first_order_time FROM df_user_store dus LEFT JOIN t_store ts ON dus.storeno = ts.storeno WHERE dus.customer_id = '"+customer_id+"'";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryOrderDetailBySn(String order_sn){
		String sql = "SELECT IFNULL(dot.area_code, '') AS area_code,IFNULL(dot.info_village_code, '') AS tiny_village_code,	IFNULL(dot.info_employee_a_no, '') AS employee_a_no "
				+ "FROM df_mass_order_total dot WHERE order_sn = '"+order_sn+"' ";
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
	public List<Map<String, Object>> queryRecentlyOrder(String customer_id){
		String sql = "SELECT order_sn FROM df_mass_order_total dot WHERE dot.customer_id = '"+customer_id+"' ORDER BY sign_time DESC LIMIT 5 ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
}
