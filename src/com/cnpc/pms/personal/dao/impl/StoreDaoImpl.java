package com.cnpc.pms.personal.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.personal.entity.Store;
import com.cnpc.pms.utils.DateUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;

import java.text.SimpleDateFormat;
import java.util.*;

public class StoreDaoImpl extends BaseDAOHibernate implements StoreDao {
	@Override
	public List<Map<String, Object>> getStoreInfoList(String where, PageInfo pageInfo) {
		// sql查询列，用于分页计算数据总数
		String str_count_sql = "SELECT count(DISTINCT(sto.store_id)) from t_store sto LEFT JOIN t_town town ON FIND_IN_SET(town.id,sto.town_id) WHERE 1=1 and sto.flag=0 "
				+ where;
		System.out.println(str_count_sql);
		// sql查询列，用于页面展示所有的数据
		String find_sql = "SELECT sto.auditor_status, "
				+ "case WHEN sto.estate='运营中' THEN '运营中' WHEN sto.estate='筹备中' THEN '筹备中' WHEN sto.estate='闭店中' THEN '闭店中' ELSE '待开业' END as estate,sto.store_id,sto.storeno,sto.storetypename,sto.city_name,sto.`name`,GROUP_CONCAT(town.name) as town_name,sto.detail_address,sto.address,sto.platformname,sto.number,sto.id,CONCAT('第',sto.ordnumber,'家店') as ordnumber,DATE_FORMAT(sto.create_time,'%Y-%m-%d %H:%i:%s') as create_time from t_store sto left join t_town town ON FIND_IN_SET(town.id,sto.town_id)  WHERE 1=1 and sto.flag=0 "
				+ where + " GROUP BY sto.store_id order by sto.store_id desc";
		// SQL查询对象
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(find_sql);

		// 查询数据量对象
		SQLQuery countQuery = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(str_count_sql);
		pageInfo.setTotalRecords(Integer.valueOf(countQuery.list().get(0).toString()));
		// 获得查询数据
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();

		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (lst_data == null || lst_data.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : lst_data) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}

	@Override
	public Store getMaxStoreData() {
		String houseSql = "SELECT * FROM t_store ORDER BY store_id DESC LIMIT 1";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(houseSql);
		List<Store> list = query.addEntity(Store.class).list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int saveStore(Store store) {
		Date date = new Date();
		// java.sql.Date sdate = new java.sql.Date(date.getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = dateFormat.format(date);
		String addStoreSql = "INSERT INTO t_store (" + "`store_id`," + "`name`," + "`type`," + "`town_id`,"
				+ "`status`," + "`version`," + "`create_user`," + "`create_time`," + "`address`," + "`detail_address`,"
				+ "`mobilephone`," + "`city_name`," + "`skid`" + ")" + "VALUES(" + store.getStore_id() + ",'"
				+ store.getName() + "'," + store.getType() + "," + store.getTown_id() + "," + 0 + "," + 0 + ",'"
				+ store.getCreate_user() + "','" + format + "',"
				+ (store.getAddress() == null ? null : "'" + store.getAddress() + "'") + ","
				+ (store.getDetail_address() == null ? null : "'" + store.getDetail_address() + "'") + ","
				+ (store.getMobilephone() == null ? null : "'" + store.getMobilephone() + "'") + ",'"
				+ store.getCityName() + "'," + store.getSkid() + ")";

		System.out.println(addStoreSql);

		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(addStoreSql);
		int update = query.executeUpdate();
		return update;
	}

	public int removeskid(Long userid) {
		String removeSql = "update t_store set t_store.skid=null where skid=" + userid;
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(removeSql);
		int update = query.executeUpdate();
		return update;
	}

	public int removermid(Long userid) {
		String removeSql = "update t_store set t_store.rmid=null where rmid=" + userid;
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(removeSql);
		int update = query.executeUpdate();
		return update;
	}

	// 根据store_id回写 店长的skid
	public int updateStoreskid(String store_ids, Long userid) {
		// 删除原来用户在门店表中的ID
		removeskid(userid);
		String update_sql = "update t_store set t_store.skid=" + userid + " where store_id in(" + store_ids + ")";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(update_sql);
		int update = query.executeUpdate();
		return update;
	}

	// 根据store_id回写 运营经理的rmid
	public int updateStorermid(String store_ids, Long userid) {
		removermid(userid);
		String update_sql = "update t_store set t_store.rmid=" + userid + " where store_id in(" + store_ids + ")";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(update_sql);
		int update = query.executeUpdate();
		return update;
	}

	@Override
	public void updateStoreSortById(String idString) {
		deleteStoreSortById();
		String sb_sql = "update t_store SET sort=1 WHERE store_id in (" + idString + ")";// 添加排序
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sb_sql.toString());
		query.executeUpdate();

	}

	@Override
	public void deleteStoreSortById() {
		String sb_sql = "update t_store SET sort=NULL";// 清空的sql
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sb_sql.toString());
		query.executeUpdate();

	}

	@Override
	public int getCountStore(String whString) {
		// sql查询列，用于分页计算数据总数
		String str_count_sql = "SELECT count(1) from t_store sto WHERE 1=1 and sto.flag=0 " + whString;

		// 查询数据量对象
		SQLQuery countQuery = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(str_count_sql);
		return Integer.valueOf(countQuery.list().get(0).toString());
	}

	@Override
	public List<Map<String, Object>> getStoreInfoListData(String where, PageInfo pageInfo) {
		// sql查询列，用于页面展示所有的数据
		String find_sql = "SELECT sto.store_id,sto.city_name,sto.`name`,sto.number,sto.platformname,sto.address,sto.town_name from t_store sto WHERE 1=1 and sto.flag=0 "
				+ where + " order by remark ASC,sto.store_id desc";
		// SQL查询对象
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(find_sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (lst_data == null || lst_data.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : lst_data) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> getStoreById(Long id) {
		String sql = "select a.*,b.name as keeperName,b.employeeId from "
				+ "(select skid,name,store_id from t_store  where rmid=" + id + ") as a"
				+ " INNER JOIN tb_bizbase_user as b on a.skid = b.id order by a.store_id ";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getStoreKeeper(Long id) {
		String sql = "select b.name,mobilephone,b.employeeId,a.skid from "
				+ "(select DISTINCT skid,store_id from t_store  where rmid=" + id + ") as a"
				+ " INNER JOIN tb_bizbase_user as b on a.skid = b.id";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public String getMaxStoreNo(Store store) {
		String find_sql = "SELECT MAX(right(storeno,4)) from t_store WHERE city_name='" + store.getCityName()
				+ "' AND (storeno is not NULL OR storeno!='')";
		if ("V".equals(store.getStoretype())) {
			find_sql = find_sql + " AND storetype='V'";
		} else {
			find_sql = find_sql + " AND storetype<>'V'";
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(find_sql);
		List<String> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getCityNameOfCSZJ(Long employee_id, Long city_id) {
		String sql = "select a.id as ctid,a.cityname as name from  t_dist_citycode a inner join t_dist_city b on a.citycode= b.citycode and b.pk_userid="
				+ employee_id;
		if (city_id != null) {
			sql = sql + " and a.id=" + city_id;
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getCityNOOfCSZJ(Long city_id) {
		String sql = "select t.cityno  from  t_dist_citycode t ";
		if (city_id != null) {
			sql = sql + " where t.id=" + city_id;
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getAllStoreOfCSZJ(Long employee_no, Long cityId) {
		String whereStr = "";
		String sql = "";
		if (cityId != null) {
			whereStr = " and tdc.id=" + cityId;
		}
		if (employee_no != null && !"".equals(employee_no)) {
			sql = "select t.store_id,t.name,ifnull(tbu.name,'') as keeperName,tbu.employeeId,t.storeno from (select * from t_store where flag=0 and ifnull(estate,'')!='闭店中') t  inner join  (select tdc.id,tdc.cityname from t_dist_city a"
					+ "   INNER JOIN t_dist_citycode tdc on a.citycode = tdc.citycode and a.pk_userid=" + employee_no
					+ whereStr + " ) t1" + "	on t.city_name  = t1.cityname "
					+ " left JOIN tb_bizbase_user as tbu on t.skid = tbu.id";
		} else {
			sql = "select t.store_id,t.name,ifnull(tbu.name,'') as keeperName,tbu.employeeId,t.storeno from (select * from t_store where flag=0 and ifnull(estate,'')!='闭店中') t  inner join  "
					+ " (select tdc.id,tdc.cityname from    t_dist_citycode tdc where id=" + cityId + " ) t1"
					+ " on t.city_name  = t1.cityname " + " left JOIN tb_bizbase_user as tbu on t.skid = tbu.id";
		}

		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getAllStoreKeeperOfCSZJ(Long employee_no, Long cityId) {
		String whereStr = "";
		String sql = "";
		if (cityId != null) {
			whereStr = " and tdc.id=" + cityId;
		}
		if (employee_no != null && !"".equals(employee_no)) {
			sql = "select tbu.name as keeperName,tbu.employeeId,tbu.mobilephone,tbu.id from (select distinct skid from t_store t  inner join  (select tdc.id,tdc.cityname from t_dist_city a"
					+ "   INNER JOIN t_dist_citycode tdc on a.citycode = tdc.citycode and a.pk_userid=" + employee_no
					+ whereStr + " ) t1" + "	on t.city_name  = t1.cityname ) t2"
					+ "   INNER JOIN tb_bizbase_user as tbu on t2.skid = tbu.id";
		} else {
			sql = "select tbu.name as keeperName,tbu.employeeId,tbu.mobilephone,tbu.id from (select distinct skid from t_store t  inner join  (select tdc.id,tdc.cityname from "
					+ "    t_dist_citycode tdc where tdc.id=" + cityId + ") t1" + "	on t.city_name  = t1.cityname ) t2"
					+ "   INNER JOIN tb_bizbase_user as tbu on t2.skid = tbu.id";
		}

		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getAllStoreOfCRM(Long employee_no, Long cityId, String role) {
		String whereStr = "";
		String cityStr = "";

		if ("CSZJ".equals(role)) {// 城市总监
			if (cityId != null && cityId > 0) {
				cityStr = " and  tdc.id=" + cityId;
			}
			if (employee_no != null && !"".equals(employee_no)) {
				whereStr = "select t.platformid,t.name,tbu.name as employeeName,t.skid,t.number,t.storeno,t.store_id from (select * from t_store where flag=0 and ifnull(estate,'')!='闭店中' and storetype!='V') t  inner join  (select tdc.id,tdc.cityname from t_dist_city a"
						+

						"   INNER JOIN t_dist_citycode tdc on a.citycode = tdc.citycode and a.pk_userid=" + employee_no
						+ cityStr + " ) t1" + "	 on t.city_name  = t1.cityname "
						+ " left join tb_bizbase_user as tbu on t.skid = tbu.id order by convert(t.name using gbk) asc";
			} else {
				whereStr = "select t.platformid,t.name,tbu.name as employeeName,t.skid,t.number,t.storeno,t.store_id from (select * from t_store where flag=0 and ifnull(estate,'')!='闭店中' and storetype!='V') t  inner join  (select tdc.id,tdc.cityname from "
						+

						"   t_dist_citycode tdc where tdc.id=" + cityId + " ) t1" + "	on t.city_name  = t1.cityname "
						+ "   left join tb_bizbase_user as tbu on t.skid = tbu.id order by convert(t.name using gbk) asc";
			}

		} else if ("QYJL".equals(role)) {// 区域经理
			whereStr = "select t.platformid,t.name,tbu.name as employeeName,t.store_id,t.storeno,t.skid,t.number from (select * from t_store where flag=0 and ifnull(estate,'')!='闭店中'and storetype!='V') t left join tb_bizbase_user as tbu on t.rmid = tbu.id where  t.rmid = "
					+ employee_no + " order by convert(t.name using gbk) asc";
		} else if ("ZB".equals(role)) {// 总部
			if (cityId != null) {
				cityStr = " where   tdc.id=" + cityId;
			}
			whereStr = "select t.platformid,t.name,tbu.name as employeeName,t.skid,t.number,t.storeno,t.store_id from (select * from t_store where flag=0 and ifnull(estate,'')!='闭店中' and storetype!='V') t inner join (select *  from t_dist_citycode tdc "
					+ cityStr + ") t1 on t.city_name  = t1.cityname"
					+ " left join tb_bizbase_user as tbu on t.skid = tbu.id order by convert(t.name using gbk) asc";
		}

		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(whereStr);
		List<Map<String, Object>> storeDate = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		return storeDate;
	}

	@Override
	public List<Map<String, Object>> getAllStoreGMVMonthOfCRM(Long employee_no, Long cityId, String role, DynamicDto dd,
			String orderBy) {
		String sqlStr = "";
		String countStr = "";
		String cityStr = "";
		String orderStr = "";
		if (cityId != null && cityId > 0) {
			cityStr = " t4.id=" + cityId;
		}
		if (orderBy != null && orderBy.length() > 0) {
			orderStr += " order by t3." + orderBy + " desc ";
		}
		countStr += "select city_name,count(store_name) as storeCount from (select city_name,store_name from ds_pes_gmv_store_month GROUP BY city_name,store_name) t5 where t5.city_name=CONCAT((select t6.cityname from t_dist_citycode t6 where t6.id="
				+ cityId + "),'市')";
		sqlStr += "select t3.city_name,t3.storeno,t3.store_name,t3.pesgmv,t3.`year`,t3.`month`,t3.pesgmvpriord from "
				+ "(select t1.storeno,t1.city_name,t1.store_name, t1.pesgmv,t1.`year` as year,t1.`month`  as month,"
				+ "CASE WHEN round((t1.pesgmv-t2.pesgmv)/t2.pesgmv*100,1) IS NULL THEN -1000 ELSE round((t1.pesgmv-t2.pesgmv)/t2.pesgmv*100,1) END  as pesgmvpriord from ds_pes_gmv_store_month t1 left join ds_pes_gmv_store_month t2 on "
				+ "t1.`month`-1=t2.`month` and t1.`year`=t2.`year` and t1.store_name=t2.store_name GROUP BY t1.store_name,t1.`year`,"
				+ "t1.`month` ORDER BY t1.store_name) t3 WHERE t3.pesgmvpriord is not NULL and t3.store_name not like '%企业购%' and t3.`month`='"
				+ dd.getMonth() + "' and t3.`year`='" + dd.getYear()
				+ "' and t3.city_name=CONCAT((select t4.cityname from t_dist_citycode t4 where " + cityStr + "),'市')"
				+ orderStr;
		if (dd.getSearchstr() != null && Integer.parseInt(dd.getSearchstr()) > 0) {
			sqlStr += " limit 0,10 ";
		}

		SQLQuery queryCount = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(countStr);
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sqlStr);
		List<Map<String, Object>> storeCountDate = queryCount.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
		List<Map<String, Object>> storeDate = new ArrayList<Map<String, Object>>();
		if (Integer.parseInt(storeCountDate.get(0).get("storeCount").toString()) > 5) {
			storeDate = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}

		return storeDate;
	}

	@Override
	public List<Map<String, Object>> getStoreDate(String where) {
		String find_sql = "SELECT sto.`name`,sto.storeno,sto.platformname,sto.platformid,sto.storetypename,sto.city_name FROM t_store sto LEFT JOIN t_town town ON FIND_IN_SET(town.id,sto.town_id) where 1=1 and sto.storetype!='W' AND sto.storeno is not NULL and sto.flag=0 and (sto.name not like '%办公室%' and sto.name not like '%储备店%' and sto.name not like '%测试%') and ifnull(sto.estate,'')!='闭店中'"
				+ where + "GROUP BY sto.store_id ";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(find_sql);
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (lst_data == null || lst_data.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : lst_data) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> getStoreListDate(String where) {
		String find_sql = "SELECT store.city_name,store.`name`,IF(store.storetype='W','',store.storeno)as storeno,IF(store.storetype='W','',store.storetypename) as storetypename,store.superMicro,store.estate,DATE_FORMAT(store.open_shop_time,'%Y/%c/%e') as open_shop_time,town.`name` as townname,IFNULL(store.address,store.detail_address) as detail_address,store.ordnumber,IFNULL(usee.mobilephone,usee.phone) as mobilephone,  usee.`name` as shopmanager, "
				+ " coun.`name` as countname,IFNULL(store.nature,'') as nature,IFNULL(store.tenancy_term,'') as tenancyTerm,IFNULL(store.rental,'') as rental,IFNULL(store.payment_method,'') as payment_method "
				+ " ,cc.audit_date,cc.enter_date,cc.enter_end_date,cc.submit_date,cc.card_content,store.rent_area,store.agency_fee,store.increase_fee,store.rent_free,store.taxes,store.increase,IF(cc.store_id is null,'无','有') as if_bussins"
				+ " FROM	t_store store LEFT JOIN tb_bizbase_user usee ON store.skid = usee.id LEFT JOIN t_town town ON town.id IN (store.town_id) LEFT JOIN t_county coun ON coun.id = town.county_id  "
				+ " LEFT JOIN (SELECT audit_date,enter_date,enter_end_date,submit_date,card_content,store_id FROM t_store_document_info WHERE audit_status=3) cc ON cc.store_id=store.store_id	"
				+ " WHERE (store.`name` not LIKE '%储备店%' and store.`name` not LIKE '%测试%' and store.`name` not LIKE '%办公室%') AND store.storetype!='V' AND ifnull(store.estate,null)!='闭店中'  AND store.storeno is not NULL and store.flag=0  "
				+ where + " order by store.city_name desc,store.ordnumber";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(find_sql);
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (lst_data == null || lst_data.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : lst_data) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> getStoreByCity(Integer target, Long cityId, Long employeeId, String search_str) {

		String searchSql = "";

		if (target == 0 || target == 3) {// 总部、事业群
			String cityStr = "";
			if (cityId != null) {
				cityStr = " where id=" + cityId;
			}
			String storeStr = "";
			if (search_str != null && !"".equals(search_str)) {
				storeStr = " and t.name like '%" + search_str + "%'";
			}
			searchSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t1.citycode from t_store t  inner join  (select * from t_dist_citycode "
					+ cityStr + ") t1" + " on t.city_name  = t1.cityname  and t.flag=0 and ifnull(t.estate,'')!='闭店中' "
					+ storeStr + " limit 10";

		} else if (target == 1) {// 城市
			String cityStr = "";
			if (cityId != null) {
				cityStr = "  and tdc.id=" + cityId;
			}
			String storeStr = "";
			if (search_str != null && !"".equals(search_str)) {
				storeStr = " and t.name like '%" + search_str + "%'";
			}
			if (employeeId != null && !"".equals(employeeId)) {
				searchSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t1.citycode from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
						+ "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + employeeId
						+ cityStr + ") t1"
						+ "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'')!='闭店中' " + storeStr
						+ " limit 10";
			} else {
				searchSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t1.citycode from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
						+ "   where tdc.id=" + cityId + ") t1"
						+ "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(t.estate,'')!='闭店中' " + storeStr
						+ " limit 10";
			}

		}

		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(searchSql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Store> findStoreByCity_nameorderByOpentime(String city_name) {
		List<Store> list = null;
		String t_find_sql = "SELECT *  FROM t_store WHERE open_shop_time is not NULL AND city_name='" + city_name
				+ "' ORDER BY open_shop_time ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(t_find_sql);
			list = sqlQuery.addEntity(Store.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public Integer findMaxStoreOreNumber(String city_name) {
		List<Store> list = null;
		Integer long1 = 0;
		String sql = "SELECT * FROM t_store WHERE open_shop_time is not NULL AND city_name='" + city_name + "'";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			list = sqlQuery.addEntity(Store.class).list();
			if (list != null && list.size() > 0) {
				return list.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return long1;
	}

	@Override
	public List<Store> findStoreIsnullOrdnumber(String city_name) {
		List<Store> list = null;
		String sql = "SELECT *  FROM t_store WHERE open_shop_time is NULL and storeno is not null AND city_name='"
				+ city_name + "' ORDER BY create_time,update_time ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			list = sqlQuery.addEntity(Store.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public Store findMaxOrdnumber(String string) {
		Store store = null;
		String sql = "SELECT * FROM t_store WHERE city_name='" + string
				+ "' and storetype!='V' ORDER BY ordnumber DESC LIMIT 1 ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			List<Store> list1 = sqlQuery.addEntity(Store.class).list();
			if (list1 != null && list1.size() > 0) {
				store = list1.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return store;
	}

	@Override
	public List<Store> findStoreDateEstate() {
		List<Store> list = null;
		String sql = "SELECT * FROM t_store WHERE (estate not in ('闭店中','试运营','运营中','试营业') or estate is NULL) and open_shop_time is not NULL AND DATE_FORMAT(open_shop_time,'%Y-%m-%d')<=DATE_FORMAT(NOW(),'%Y-%m-%d')";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			list = sqlQuery.addEntity(Store.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> findTinyDetails(String city_name, String whString, PageInfo pageInfo) {
		List<Map<String, Object>> list = null;
		String sql = "SELECT tin.tiny_village_code,tin.tiny_village_type,tin.tiny_village_name,tin.town_name,tin.village_name,tin.ifcoordinates,tin.house_count,tin.building_count,s.name FROM t_tiny_village_data_details tin LEFT JOIN t_store s ON FIND_IN_SET(tin.town_id,s.town_id) "
				+ " WHERE tin.city_name like '%" + city_name + "%' " + whString;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryBatchResultForVillage() {
		String querySql = "SELECT tttt.name as area_name,tttt.area_no as area_no,tttt.id as area_id,"
				+ "tsss.tinyname as tinyname,tsss.`code` as code_vill,tsss.tinyvillageid as tinid,"
				+ "tttt.employee_a_name as employee_a_name,tttt.employee_b_name as employee_b_name,tttt.employee_a_no as employee_a_no,"
				+ "tttt.employee_b_no as employee_b_no,tsss.sumbuilding as sumbuilding,tsss.sumhouse as sumhouse,"
				+ "gggg.store_id as store_id,gggg.name as store_name,gggg.city_name as city_name,vallage_area as vallage_area  FROM ("
				+ " SELECT tinyvill.`name` as tinyname,tinyvill.id as tinyvillageid,ooooo.code,"
				+ " IFNULL(ss.buildingcou,0)+IFNULL(buildingss.businsscount,0)+IFNULL(bung.bungalow,0) as sumbuilding,"
				+ " IFNULL(hou.buildhouse,0)+IFNULL(buildingss.bushouse,0)+IFNULL(bung.bungalow,0) as sumhouse "
				+ " FROM t_tiny_village tinyvill	LEFT JOIN (SELECT tinyvillage_id,COUNT(*) as buildingcou "
				+ " FROM t_building WHERE `status`=0 AND type=1 GROUP BY tinyvillage_id)ss ON ss.tinyvillage_id=tinyvill.id LEFT JOIN"
				+ "(SElECT buil.tinyvillage_id,count(distinct(hou.id)) as buildhouse FROM t_building buil LEFT JOIN t_house hou "
				+ " ON buil.id=hou.building_id   WHERE buil.`status`=0 AND buil.type=1 AND hou.house_type=1 AND hou.`status`=0 "
				+ " group by buil.tinyvillage_id) hou ON hou.tinyvillage_id=tinyvill.id left join "
				+ " (select buil.tinyvillage_id,count(distinct(buil.id)) as businsscount,sum(buhouse.buildcount) as bushouse from "
				+ " (SELECT building_id,count(*) as buildcount FROM t_house WHERE house_type=2 and status=0 group by building_id) "
				+ "as buhouse left join t_building buil on buhouse.building_id=buil.id group by buil.tinyvillage_id) as buildingss "
				+ " on buildingss.tinyvillage_id=tinyvill.id left join "
				+ " (select tinyvillage_id,count(*) as bungalow from t_house where house_type=0 and status=0 group by "
				+ " tinyvillage_id) as bung on bung.tinyvillage_id=tinyvill.id LEFT JOIN tiny_village_code ooooo "
				+ " ON ooooo.tiny_village_id=tinyvill.id  WHERE tinyvill.`status`=0 AND tinyvill.village_id is not NULL "
				+ " AND tinyvill.town_id is not NULL) tsss LEFT JOIN (SELECT	c.*, d. CODE FROM (SELECT a.store_id,a.`name`,a.area_no,a.id,"
				+ "b.tin_village_id,a.employee_a_no,a.employee_a_name,a.employee_b_no,a.employee_b_name FROM	t_area a INNER JOIN t_area_info b "
				+ " ON a.id = b.area_id AND b.tin_village_id IS NOT NULL AND b. STATUS = 0  INNER JOIN t_tiny_village ttv "
				+ " ON b.tin_village_id = ttv.id AND ttv. STATUS = 0 AND ( ttv.dellable <> 1 OR ttv.dellable IS NULL ) UNION "
				+ " SELECT ta.store_id,ta. NAME,ta.area_no,ta.id,ttv.id AS tin_village_id,ta.employee_a_no,ta.employee_a_name,ta.employee_b_no,"
				+ "ta.employee_b_name FROM t_area ta INNER JOIN t_area_info tai ON ta.id = tai.area_id AND tai. STATUS = 0"
				+ " AND tai.tin_village_id IS NULL INNER JOIN t_tiny_village ttv ON tai.village_id = ttv.village_id AND ttv. STATUS = 0"
				+ " AND ( ttv.dellable <> 1 OR ttv.dellable IS NULL)) c INNER JOIN tiny_village_code d ON c.tin_village_id = d.tiny_village_id) tttt "
				+ " ON tsss.tinyvillageid= tttt.tin_village_id LEFT JOIN tiny_area lllll ON lllll.tiny_village_id=tsss.tinyvillageid "
				+ " and lllll.status='0' LEFT JOIN t_store gggg ON tttt.store_id=gggg.store_id";
		// String querySql = "select * from df_tinyarea_datainfo limit 0,10";
		List<Map<String, Object>> list = null;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(querySql);
			list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public boolean insertIntoVillageInfo(String sql) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		boolean query = false;
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.executeUpdate();
			query = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return query;
	}

	@Override
	public List<Map<String, Object>> queryAllTinyareaDatainfoUserCountSix() {
		String querySql = "SELECT COUNT(tttt.tinyvillage_name) as customer_count,"
				+ "tttt.tinyvillage_name as t_tiny_village,tttt.tinyvillage_ids as tinyvillage_id "
				+ "FROM ( SELECT tc.*,th.id as houseId,ttv.id as tinyvillage_ids,ttv.name as tinyvillage_name "
				+ "FROM t_customer tc INNER JOIN ( SELECT thc2.* FROM ( SELECT max(id) AS id FROM t_house_customer  "
				+ "GROUP BY customer_id) thc1 INNER JOIN t_house_customer thc2 ON thc1.id = thc2.id) "
				+ "thc ON tc.id = thc.customer_id INNER  JOIN t_house th ON th.id = thc.house_id INNER JOIN "
				+ "t_tiny_village ttv ON ttv.id = th.tinyvillage_id INNER JOIN t_house_style hs ON hs.house_id = th.id WHERE "
				+ "( hs.house_area IS NOT NULL AND hs.house_area != '') AND ( hs.house_toward IS NOT NULL AND "
				+ "hs.house_toward != '') AND ( hs.house_style IS NOT NULL AND hs.house_style != '' ) AND ( "
				+ "hs.house_pic IS NOT NULL AND (trim(hs.house_pic) != '') AND (trim(hs.house_pic) != '无') OR "
				+ "th.house_type = 0) AND ( tc.`name` IS NOT NULL AND tc.`name` != '') AND tc.sex IS NOT NULL AND ( "
				+ "tc.mobilephone IS NOT NULL AND tc.mobilephone != '') AND (tc.age IS NOT NULL) AND (tc.sex IS NOT NULL)) tttt ";
		List<Map<String, Object>> list = null;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		boolean query = false;
		try {
			SQLQuery sqlQuery = session.createSQLQuery(querySql);
			list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			query = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public boolean updateVillageInfoDataUserCountSix(String tinyvillage_id, String customer_count) {
		String updateSql = "UPDATE df_tinyarea_datainfo SET user_count_6 = '" + customer_count
				+ "' where tin_village_id ='" + tinyvillage_id + "'";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		boolean query = false;
		try {
			SQLQuery sqlQuery = session.createSQLQuery(updateSql);
			sqlQuery.executeUpdate();
			query = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return query;
	}

	@Override
	public boolean updateVillageInfoDataUserCountEighteen(String tinyvillage_id, String customer_count) {
		String updateSql = "UPDATE df_tinyarea_datainfo SET user_count_18 = '" + customer_count
				+ "' where tin_village_id ='" + tinyvillage_id + "'";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		boolean query = false;
		try {
			SQLQuery sqlQuery = session.createSQLQuery(updateSql);
			sqlQuery.executeUpdate();
			query = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return query;
	}

	@Override
	public List<Map<String, Object>> queryAllTinyareaDatainfoUserCountEighteen() {
		List<Map<String, Object>> list = null;
		String querySql = "SELECT COUNT(tttt.tinyvillage_name) as customer_count,"
				+ "tttt.tinyvillage_name as t_tiny_village,tttt.tinyvillage_ids as tinyvillage_id "
				+ "FROM ( SELECT tc.*,th.id as houseId,ttv.id as tinyvillage_ids,ttv.name as tinyvillage_name "
				+ "FROM t_customer tc INNER JOIN ( SELECT thc2.* FROM ( SELECT max(id) AS id FROM t_house_customer GROUP BY customer_id "
				+ ") thc1 INNER JOIN t_house_customer thc2 ON thc1.id = thc2.id ) thc ON tc.id = thc.customer_id INNER  JOIN "
				+ "t_house th ON th.id = thc.house_id INNER JOIN t_tiny_village ttv ON ttv.id = th.tinyvillage_id INNER JOIN "
				+ "t_house_style hs ON hs.house_id = th.id WHERE ( hs.house_area IS NOT NULL AND hs.house_area != '') AND ( "
				+ "hs.house_toward IS NOT NULL AND hs.house_toward != '') AND ( hs.house_style IS NOT NULL AND "
				+ "hs.house_style != '') AND ( hs.house_pic IS NOT NULL AND (trim(hs.house_pic) != '') AND (trim(hs.house_pic) != '无') "
				+ "OR th.house_type = 0) AND ( tc.`name` IS NOT NULL AND tc.`name` != '') AND tc.sex IS NOT NULL AND ( tc.mobilephone "
				+ "IS NOT NULL AND tc.mobilephone != '') AND (tc.age IS NOT NULL) AND (tc.sex IS NOT NULL) AND (tc.customer_pic is not "
				+ "null AND tc.customer_pic != '') AND (tc.nationality is not null AND tc.nationality != '') AND (tc.house_property is "
				+ "not null AND tc.house_property != '') AND (tc.income is not null AND tc.income != '') AND (tc.work_overtime is not "
				+ "null AND tc.work_overtime != '') AND (tc.family_num is not null ) AND (tc.preschool_num is not null ) AND "
				+ "(tc.minor_num is not null ) AND (tc.pet_type is not null AND tc.pet_type != '')) tttt GROUP BY tttt.tinyvillage_ids ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		boolean query = false;
		try {
			SQLQuery sqlQuery = session.createSQLQuery(querySql);
			list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			query = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getProvinceNOOfCSZJ(String province_id) {
		String sql = "select left(t.gb_code,6) as gb_code  from  t_province t ";
		if (province_id != null) {
			sql = sql + " where t.id=" + province_id;
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public boolean cleanDataInfoTable() {
		String updateSql = "delete from df_tinyarea_datainfo ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		boolean query = false;
		try {
			SQLQuery sqlQuery = session.createSQLQuery(updateSql);
			sqlQuery.executeUpdate();
			query = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return query;
	}

	@Override
	public List<Map<String, Object>> getCityNOOfCityById(Long city_id) {
		String sql = "select t.cityno from t_dist_citycode t ";

		if (city_id != null) {
			sql = sql + "where t.id ='" + city_id + "' ";
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> selectStoreKeeperInfoByStoreId(Long storeId) {

		String sql = "select tb.id,tb.name,tb.employeeId,tb.mobilephone,t2.store_id,t2.storeno,t2.storeName from (select t1.store_id,t1.name as storeName,t1.storeno,t1.skid  from (select skid from t_store where store_id ="
				+ storeId
				+ ") t inner join t_store t1 on t.skid = t1.skid) t2 left join tb_bizbase_user tb on t2.skid = tb.id";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> selectStoreByCityId(Long city_id) {
		String sql = "SELECT ts.* from t_store ts inner join t_dist_citycode tdc ON ts.city_name=tdc.cityname WHERE tdc.id ="
				+ city_id;
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	// 门店运营 列表中的方法
	@Override
	public Map<String, Object> queryStoreStatusList(Store store, PageInfo pageInfo) {
		String wheresql = " 1=1 ";
		if (store != null && store.getCityName() != null && store.getCityName().length() > 0) {
			wheresql += " and a.city_name = '" + store.getCityName() + "'";
		}

		// 判断日期 如果是week 本周 month 本月
		String datestr = store.getName();
		if (datestr != null && datestr.equals("week")) {
			// 本周
			String startdate = DateUtils.getCurrentMonday();
			String enddate = DateUtils.getPreviousSunday();
			wheresql += " and a.open_shop_time >= '" + startdate + " 00:00:00' AND a.open_shop_time <= '" + enddate
					+ " 23:59:59'";
		}
		if (datestr != null && datestr.equals("month")) {
			// 本月
			String curmonth = DateUtils.getCurrMonthDate();
			wheresql += " and DATE_FORMAT(a.open_shop_time,'%Y-%m')='" + curmonth + "' ";
		}

		wheresql += " ORDER BY a.open_shop_time DESC ";
		String sql = "SELECT a.city_name,a.storetypename,a.ordnumber,a.store_id,a.name,a.estate,a.storeno,a.open_shop_time FROM t_store a where "
				+ wheresql;

		// String sql = "SELECT * FROM t_humanresources a where "+sqlwhere ;
		String sql_count = "SELECT count(*) as total FROM (" + sql + ") b";

		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql_count);
		List<?> total = query_count.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		Map<String, Object> maps = (Map<String, Object>) total.get(0);

		pageInfo.setTotalRecords(Integer.valueOf(maps.get("total").toString()));

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<?> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
		Map<String, Object> map_result = new HashMap<String, Object>();
		Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
	}

	// 门店运营 - 导出门店状态 -tab2
	@Override
	public List<Map<String, Object>> exportStoreStatusList(Store store) {
		String wheresql = " 1=1 ";
		if (store != null && store.getCityName() != null && store.getCityName().length() > 0) {
			wheresql += " and a.city_name = '" + store.getCityName() + "'";
		}

		// 判断日期 如果是week 本周 month 本月
		String datestr = store.getName();
		if (datestr != null && datestr.equals("week")) {
			// 本周
			String startdate = DateUtils.getCurrentMonday();
			String enddate = DateUtils.getPreviousSunday();
			wheresql += " and a.open_shop_time >= '" + startdate + " 00:00:00' AND a.open_shop_time <= '" + enddate
					+ " 23:59:59'";
		}
		if (datestr != null && datestr.equals("month")) {
			// 本月
			String curmonth = DateUtils.getCurrMonthDate();
			wheresql += " and DATE_FORMAT(a.open_shop_time,'%Y-%m')='" + curmonth + "' ";
		}

		wheresql += " ORDER BY a.open_shop_time DESC ";
		String sql = "SELECT a.city_name,a.storetypename,a.ordnumber,a.store_id,a.name,a.estate,a.storeno,a.open_shop_time FROM t_store a where "
				+ wheresql;
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	@Override
	public List<Map<String, Object>> findStoreXuanZhi(String where, PageInfo pageInfo) {
		// sql查询列，用于页面展示所有的数据
		String find_sql = "SELECT sto.store_id,sto.storetypename,sto.city_name,sto.`name`,GROUP_CONCAT(town.name) as town_name  from t_store sto left join t_town town ON FIND_IN_SET(town.id,sto.town_id) "
				+ " WHERE sto.store_id not in (SELECT store_id FROM t_store_document_info) AND sto.storetype!='V' and IFNULL(sto.estate,'')!='闭店中' AND (sto.`name` NOT LIKE '%储备店%' AND sto.name NOT LIKE '%办公室%' AND sto.name NOT LIKE '%测试%') and sto.flag=0 "
				+ where + " GROUP BY sto.store_id";
		// SQL查询对象
		String str_count_sql = "SELECT count(1) from (" + find_sql + ") cc";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(find_sql);

		// 查询数据量对象
		SQLQuery countQuery = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(str_count_sql);
		pageInfo.setTotalRecords(Integer.valueOf(countQuery.list().get(0).toString()));
		// 获得查询数据
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();

		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (lst_data == null || lst_data.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : lst_data) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}

	@Override
	public Store insertStore(Store store) {
		Date date = new Date();
		// java.sql.Date sdate = new java.sql.Date(date.getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = dateFormat.format(date);
		String sql = "INSERT INTO t_store (`store_id`,`name`,`type`,`version`,`town_id`,`county_id`,`city_id`,`province_id`,`status`,  `create_user`, `create_time`, `update_user`, `update_time`,  `address`, `detail_address`, `mobilephone`,`id`,`platformid`,`city_name`, `rmid`, `skid`, `open_shop_time`, `platformname`, `town_name`, `storeno`, `storetype`, `storetypename`,  `estate`, `ordnumber`, `superMicro`, `cityno`, `agency_fee`, `county_ids`, `increase`, `increase_fee`, `nature`, `payment_method`, `rent_area`, `rent_free`, `rental`, `taxes`, `tenancy_term`, `usable_area`, `work_id`, `place_town_id`, `store_position`, `gaode_adcode`, `gaode_address`, `gaode_citycode`, `gaode_provincecode`,`formattype`) VALUES ("
				+ store.getStore_id() + ",'" + store.getName() + "'," + store.getType() + "," + store.getVersion() + ","
				+ (store.getTown_id() == null ? null : "'" + store.getTown_id() + "'") + "," + store.getCounty_id()
				+ "," + store.getCity_id() + "," + store.getProvince_id() + "," + store.getStatus() + ","
				+ (store.getCreate_user() == null ? null : "'" + store.getCreate_user() + "'") + ",'" + format + "',"
				+ (store.getUpdate_user() == null ? null : "'" + store.getUpdate_user() + "'") + ",'" + format + "',"
				+ (store.getAddress() == null ? null : "'" + store.getAddress() + "'") + ","
				+ (store.getDetail_address() == null ? null : "'" + store.getDetail_address() + "'") + ","
				+ (store.getMobilephone() == null ? null : "'" + store.getMobilephone() + "'") + ","
				+ (store.getId() == null ? null : "'" + store.getId() + "'") + ","
				+ (store.getPlatformid() == null ? null : "'" + store.getPlatformid() + "'") + "," + "'"
				+ store.getCityName() + "'," + (store.getRmid() == null ? null : store.getRmid()) + ","
				+ (store.getSkid() == null ? null : store.getSkid()) + ","
				+ (store.getOpen_shop_time() == null ? null : "'" + dateFormat.format(store.getOpen_shop_time()) + "'") + ","
				+ (store.getPlatformname() == null ? null : "'" + store.getPlatformname() + "'") + ","
				+ (store.getTown_name() == null ? null : "'" + store.getTown_name() + "'") + ",'" + store.getStoreno()
				+ "','" + store.getStoretype() + "','" + store.getStoretypename() + "',"
				+ (store.getEstate() == null ? null : "'" + store.getEstate() + "'") + ","
				+ (store.getOrdnumber() == null ? null : "'" + store.getOrdnumber() + "'") + ","
				+ (store.getSuperMicro() == null ? null : "'" + store.getSuperMicro() + "'") + ","
				+ (store.getCityNo() == null ? null : "'" + store.getCityNo() + "'") + ","
				+ (store.getAgency_fee() == null ? null : "'" + store.getAgency_fee() + "'") + ","
				+ (store.getCounty_ids() == null ? null : "'" + store.getCounty_ids() + "'") + ","
				+ (store.getIncrease() == null ? null : "'" + store.getIncrease() + "'") + ","
				+ (store.getIncrease_fee() == null ? null : "'" + store.getIncrease_fee() + "'") + ","
				+ (store.getNature() == null ? null : "'" + store.getNature() + "'") + ","
				+ (store.getPayment_method() == null ? null : "'" + store.getPayment_method() + "'") + ","
				+ (store.getRent_area() == null ? null : "'" + store.getRent_area() + "'") + ","
				+ (store.getRent_free() == null ? null : "'" + store.getRent_free() + "'") + ","
				+ (store.getRental() == null ? null : "'" + store.getRental() + "'") + ","
				+ (store.getTaxes() == null ? null : "'" + store.getTaxes() + "'") + ","
				+ (store.getTenancyTerm() == null ? null : "'" + store.getTenancyTerm() + "'") + ","
				+ (store.getUsable_area() == null ? null : "'" + store.getUsable_area() + "'") + ","
				+ (store.getWork_id() == null ? null : "'" + store.getWork_id() + "'") + ","
				+ (store.getPlace_town_id() == null ? null : "'" + store.getPlace_town_id() + "'") + ","
				+ (store.getStore_position() == null ? null : "'" + store.getStore_position() + "'") + ","
				+ (store.getGaode_adCode() == null ? null : "'" + store.getGaode_adCode() + "'") + ","
				+ (store.getGaode_address() == null ? null : "'" + store.getGaode_address() + "'") + ","
				+ (store.getGaode_cityCode() == null ? null : "'" + store.getGaode_cityCode() + "'") + ","
				+ (store.getGaode_provinceCode() == null ? null : "'" + store.getGaode_provinceCode() + "'") + ","
				+ (store.getFormattype() == null ? null : "'" + store.getFormattype() + "'") + ")";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		int update = query.executeUpdate();
		return store;
	}

	@Override
	public List<Map<String, Object>> getCityBY2017() {
		String sql = "select city_name from t_store WHERE (DATE_FORMAT(create_time,'%Y')<'2018' or create_time is NULL) AND flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' and storetype!='V' AND ifnull(estate,'')!='闭店中' GROUP BY city_name";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getCityBY2018() {
		String sql = "SELECT  city_name FROM t_store WHERE DATE_FORMAT(create_time,'%Y')='2018' AND  city_name not in (select city_name from t_store WHERE DATE_FORMAT(create_time,'%Y')<'2018' or create_time is NULL GROUP BY city_name) AND flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' and storetype!='V' AND ifnull(estate,'')!='闭店中' GROUP BY city_name";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getStoreCity() {
		String sql = "SELECT city_name FROM t_store WHERE flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' and storetype!='V' AND ifnull(estate,'')!='闭店中' GROUP BY city_name";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Store> findStore() {
		String sql = "SELECT * FROM t_store WHERE flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and storetype!='V' AND ifnull(estate,'')!='闭店中' and `name` NOT  LIKE '%办公室%' and YEARWEEK(date_format(create_time,'%Y-%m-%d')) = YEARWEEK(now())";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Store> list = query.addEntity(Store.class).list();
		return list;
	}

	@Override
	public List<Map<String, Object>> findStoreByNature(String nature) {
		String sql_str = "";
		if("自营店".equals(nature)){
			sql_str = "SELECT city_name," + "sum(case WHEN storetype='E' and nature = '自营店' then 1 else 0 END) as '校园店',"
					+ "sum(case WHEN storetype='S' and nature = '自营店'  then 1 else 0 END) as '生活中心店',"
					+ "sum(case WHEN storetype='Y' and nature = '自营店' then 1 else 0 END) as '街道月店',"
					+ "sum(case WHEN storetype='C' and nature = '自营店' then 1 else 0 END) as '前置仓',"
					+ "sum(case WHEN storetype='M' and nature = '自营店' then 1 else 0 END) as '药店',"
					+ "sum(case WHEN storetype='B' and nature = '自营店' then 1 else 0 END) as '独立微超',"
					+ "sum(case WHEN storetype='H' and nature = '自营店' then 1 else 0 END) as '城市仓',"
					+ "sum(case WHEN storetype='X' and nature = '自营店' then 1 else 0 END) as '经营星店' ";
		}else if("合作店".equals(nature)){
			sql_str = "SELECT city_name,sum(case WHEN formattype = '数码连锁店' and nature = '合作店'  then 1 else 0 END) as '数码连锁店', "
					+ "sum(case WHEN formattype = '超市连锁店' and nature = '合作店' then 1 else 0 END) as '超市连锁店', "
					+ "sum(case WHEN formattype = '广店营业厅' and nature = '合作店' then 1 else 0 END) as '广店营业厅' ";
		}else if(nature == null){
			sql_str = "SELECT city_name," + "sum(case WHEN storetype='E' and nature = '自营店'  then 1 else 0 END) as '校园店',"
					+ "sum(case WHEN storetype='S' and nature = '自营店' then 1 else 0 END) as '生活中心店',"
					+ "sum(case WHEN storetype='Y' and nature = '自营店' then 1 else 0 END) as '街道月店',"
					+ "sum(case WHEN storetype='X' and nature = '自营店' then 1 else 0 END) as '经营星店', "
					+ "sum(case WHEN storetype='M' and nature = '自营店' then 1 else 0 END) as '药店',"
					+ "sum(case WHEN storetype='B' and nature = '自营店' then 1 else 0 END) as '独立微超',"
					+ "sum(case WHEN nature = '合作店' then 1 else 0 END) as '合作店',"
					+ "sum(case WHEN storetype='C' and nature = '自营店' then 1 else 0 END) as '前置仓',"
					+ "sum(case WHEN storetype='H' and nature = '自营店' then 1 else 0 END) as '城市仓'";
			
		}
		String sql = sql_str+ "FROM t_store WHERE flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' AND ifnull(estate,'')!='闭店中'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> findStoreGroupByStoretype() {
		String sql = "SELECT s.*,ifnull(l.qianzhicangcount,0) as qianzhicangcount,ifnull(l.hezuocount,0) as hezuocount,IFNULL(c.2018qianzhicangmubiao,0) as qianzhicangmubiao,IFNULL(c.2018hezuomubiao,0) as hezuomubiao FROM ( "
				+"SELECT city_name, sum(case WHEN storetype='E' then 1 else 0 END) as 'xiaoyuan', "
				+"sum(case WHEN storetype='S' then 1 else 0 END) as 'shenghuo', "
				+"sum(case WHEN storetype='Y' then 1 else 0 END) as 'yuedian', "
				+"sum(case WHEN storetype='M' then 1 else 0 END) as 'yaodian', "
				+"sum(case WHEN storetype='B' then 1 else 0 END) as 'weichao', "
				+"sum(case WHEN storetype='X' then 1 else 0 END) as 'xingdian'," 
				+"sum(case WHEN storetype='Z'or storetype='W'  or storetype='C' or storetype='H' then 1 else 0 END) as 'qita' "
				+"FROM t_store WHERE flag=0 and nature='自营店' AND `name` NOT  LIKE '%测试%' and storetype!='V' AND ifnull(estate,'')!='闭店中' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' AND storetype!='V' "
				+"GROUP BY city_name ) s LEFT JOIN ( "
				+"SELECT city_name,COUNT((storetype='C' and nature='自营店') or null) as qianzhicangcount,COUNT(nature='合作店' or null) as hezuocount FROM t_store WHERE flag=0 AND ifnull(estate,'')!='闭店中' AND `name` NOT  LIKE '%测试%' "
				+"and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' "
				+"GROUP BY city_name ) l ON s.city_name=l.city_name LEFT JOIN ( "
				+"SELECT cityname,IFNULL(preposition_task,0) as 2018qianzhicangmubiao,IFNULL(cooperative_task,0) as 2018hezuomubiao FROM di_storexpand WHERE  YEARWEEK(date_format(start_time,'%Y-%m-%d')) = YEARWEEK(now()) GROUP BY cityname "
				+") c ON c.cityname=s.city_name";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> find2018Date() {
		String sql = "SELECT city_name,COUNT(1) as newStore FROM t_store WHERE flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' and storetype!='V' AND ifnull(estate,'')!='闭店中' AND DATE_FORMAT(create_time,'%Y')='2018' "
				+ "GROUP BY city_name";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> findOneYearStoreData() {
		String sql = " SELECT                                                                                                                                                                      "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(NOW(),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month12',			       "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 1 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as month11,   "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 2 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as month10,   "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 3 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month09', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 4 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month08', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 5 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month07', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 6 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month06', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 7 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month05', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 8 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month04', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 9 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month03', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 10 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month02',"
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 11 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month01' "
				+ "   FROM t_store 																			       "
				+ " WHERE flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' AND storetype!='V' AND ifnull(estate,'')!='闭店中' AND nature='自营店'			       "
				+ " UNION ALL																				       "
				+ " SELECT 																				       "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(NOW(),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month12',			       "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 1 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month11', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 2 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month10', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 3 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month09', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 4 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month08', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 5 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month07', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 6 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month06', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 7 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month05', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 8 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month04', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 9 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month03', "
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 10 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month02',"
				+ " cast(SUM(case WHEN date_format(create_time,'%Y-%m')<=date_format(date_sub(now(),interval 11 month),'%Y-%m') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'month01' "
				+ " FROM t_store 																			       "
				+ " WHERE flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' AND storetype!='V' AND ifnull(estate,'')!='闭店中' AND nature='合作店'			       "
				+ " UNION ALL																				       "
				+ " SELECT date_format(NOW(),'%Y-%m') as 'month12',															       "
				+ " date_format(date_sub(now(),interval 1 month),'%Y-%m') as 'month11',													       "
				+ " date_format(date_sub(now(),interval 2 month),'%Y-%m') as 'month10',													       "
				+ " date_format(date_sub(now(),interval 3 month),'%Y-%m') as 'month09',													       "
				+ " date_format(date_sub(now(),interval 4 month),'%Y-%m') as 'month08',													       "
				+ " date_format(date_sub(now(),interval 5 month),'%Y-%m') as 'month07',													       "
				+ " date_format(date_sub(now(),interval 6 month),'%Y-%m') as 'month06',													       "
				+ " date_format(date_sub(now(),interval 7 month),'%Y-%m') as 'month05',													       "
				+ " date_format(date_sub(now(),interval 8 month),'%Y-%m') as 'month04', "
				+ " date_format(date_sub(now(),interval 9 month),'%Y-%m') as 'month03', "
				+ " date_format(date_sub(now(),interval 10 month),'%Y-%m') as 'month02', "
				+ " date_format(date_sub(now(),interval 11 month),'%Y-%m') as 'month01' ";
		System.out.println(sql);
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> findSixWeekStoreData() {
		String sql = "SELECT CONCAT(cc.week1,'') as week1,CONCAT(cc.week2,'') as week2,CONCAT(cc.week3,'') as week3,CONCAT(cc.week4,'') as week4,CONCAT(cc.week5,'') as week5,CONCAT(cc.week6,'') as week6 FROM (SELECT "
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week6',"
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5+7 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week5',"
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5+7*2 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week4',"
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5+7*3 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week3',"
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5+7*4 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week2',"
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5+7*5 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week1' "
				+ " FROM t_store "
				+ "WHERE flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' AND storetype!='V' AND ifnull(estate,'')!='闭店中' AND nature='自营店' "
				+ "UNION ALL " + "SELECT "
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week6',"
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5+7 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week5',"
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5+7*2 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week4',"
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5+7*3 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week3',"
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5+7*4 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week2',"
				+ "cast(SUM(case WHEN date_format(create_time,'%Y-%m-%d')<=date_format(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5+7*5 DAY),'%Y-%m-%d') OR create_time is NULL THEN 1 ELSE 0 END) as char) as 'week1' "
				+ "  FROM t_store "
				+ "WHERE flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' AND storetype!='V' AND ifnull(estate,'')!='闭店中' AND nature='合作店' "
				+ "UNION ALL "
				+ "SELECT DATE_FORMAT(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) +1 DAY),'%Y-%m-%d') as 'week6',"
				+ "DATE_FORMAT(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) +7+1 DAY),'%Y-%m-%d') as 'week5',"
				+ "DATE_FORMAT(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) +7*2+1 DAY),'%Y-%m-%d') as 'week4',"
				+ "DATE_FORMAT(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) +7*3+1 DAY),'%Y-%m-%d') as 'week3',"
				+ "DATE_FORMAT(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) +7*4+1 DAY),'%Y-%m-%d') as 'week2',"
				+ "DATE_FORMAT(date_sub(curdate(),INTERVAL WEEKDAY(curdate()) +7*5+1 DAY),'%Y-%m-%d') as 'week1') cc";
		System.out.println(sql);
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		System.out.println(lst_data);
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getOpenStoreByWeek(String nature) {
		String sql = "select nature,YEARWEEK(create_time) as week_date,count(1) as count,subdate(DATE_FORMAT(create_time,'%Y-%m-%d'),date_format(create_time, '%w')) AS week_time from t_store ts "
				+ "WHERE nature is not null and create_time is not null and nature = '" + nature
				+ "' and ifnull(estate,'')!='闭店中'  AND storetype!='V' and name not like '%办公室%' and name not like '%储备%' and name not like '%测试%' and YEARWEEK(date_format(create_time,'%Y-%m-%d')) > YEARWEEK(now())-6 group by week_date;";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getStoreNatureByYear(String year) {
		String append_Stirng = "";
		String joinType = "";
		if(year != null){
			append_Stirng = " and DATE_FORMAT(create_time,'%Y') = '"+ year + "'";
			joinType = "left join";
		}else{
			joinType = "inner join";
		}
		String sql = "select t1.cityname,DATE_FORMAT(t1.create_time,'%Y') AS create_year,t1.cityno,ifnull(t2.count,0) as ' cooperative_complete',ifnull(t3.count,0) as 'self_complete',"
				+ "ifnull(t4.cooperative_task,0) as cooperative_task,ifnull(t4.self_support_task,0) as self_support_task from t_dist_citycode t1 "+joinType
				+ " (select count(*) as count,nature,city_name from t_store where nature is not null and flag = 0 and ifnull(estate,'')!='闭店中' and nature = '合作店' and name not like '%办公室%' and name not like '%储备%' and name not like '%测试%' AND storetype!='V' "+append_Stirng+" GROUP BY city_name) t2 " + "ON t1.cityname = t2.city_name LEFT JOIN "
				+ "(select count(*) as count,nature,city_name from t_store where nature is not null and flag = 0 and ifnull(estate,'')!='闭店中' and nature = '自营店' and name not like '%办公室%' and name not like '%储备%' and name not like '%测试%' AND storetype!='V' "+append_Stirng+" GROUP BY city_name) t3 " + "ON t1.cityname = t3.city_name LEFT JOIN "
				+ "(select cooperative_task,self_support_task,cityname,cityno from di_storexpand  where 1=1 and YEARWEEK(date_format(start_time,'%Y-%m-%d')) = YEARWEEK(now()) GROUP BY cityname) t4 "
				+ "ON t1.cityname = t4.cityname;";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getStoreStateCount() {
		String sql = "select city_name,count(1) as count from t_store where estate = '筹备中' and flag = 0 and ifnull(estate,'')!='闭店中' and name not like '%办公室%' and name not like '%储备%' and name not like '%测试%' AND storetype!='V' GROUP BY city_name;";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getStoreCardBycity() {
		String sql = "select ta.city_name,ifnull(ta.store_count,0) as store_count,ifnull(tb.`double_card`,0) as double_card,ifnull(tc.`business_license`,0) as business_license,ifnull(td.`no_card`,0) as no_card from ( "
				+ "select count(*) as store_count,city_name from t_store where flag = 0 and ifnull(estate,'')!='闭店中' and name not like '%办公室%' and name not like '%储备%' and name not like '%测试%' AND storetype!='V' GROUP BY city_name) ta "
				+ "LEFT JOIN (select COUNT(t1.store_id) as 'double_card',t1.city_name from ( "
				+ "select t_store.store_id,t_store.`name`,t_store.city_name,t_attachment.file_type from t_store INNER JOIN t_attachment ON t_store.store_id = t_attachment.store_id where t_attachment.file_type = 7) t1 "
				+ "INNER JOIN (select t_store.store_id,t_store.`name`,t_store.city_name,t_attachment.file_type from t_store INNER JOIN t_attachment ON t_store.store_id =  t_attachment.store_id where t_attachment.file_type = 8) t2 "
				+ "ON t1.store_id = t2.store_id GROUP BY t1.city_name) tb ON ta.city_name = tb.city_name LEFT JOIN (select COUNT(*) as 'business_license',t.city_name from (select t_store.store_id,t_store.city_name from t_store "
				+ "INNER JOIN t_attachment ON t_store.store_id = t_attachment.store_id where t_attachment.file_type = 7) t where t.store_id not in (select t_store.store_id from t_store "
				+ "INNER JOIN t_attachment ON t_store.store_id =  t_attachment.store_id where t_attachment.file_type = 8)GROUP BY t.city_name) tc ON ta.city_name = tc.city_name LEFT JOIN (select count(store_id) as 'no_card',city_name from t_store "
				+ "where flag = 0 and ifnull(estate,'')!='闭店中' and name not like '%办公室%' and name not like '%储备%' and name not like '%测试%'  AND storetype!='V' and store_id not in (select t_store.store_id from t_store INNER JOIN t_attachment ON t_store.store_id = t_attachment.store_id "
				+ "where t_attachment.file_type = 7 UNION All select t_store.store_id from t_store INNER JOIN t_attachment ON t_store.store_id = t_attachment.store_id where t_attachment.file_type = 8 GROUP BY t_store.store_id) GROUP BY city_name ) "
				+ "td ON ta.city_name = td.city_name;";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public Map<String, Object> queryAboutStoreInfo(String where, PageInfo pageInfo) {
		String sql = "SELECT store.city_name,store.`name`,IF(store.storetype='W','',store.storeno)as storeno,IF(store.storetype='W','',store.storetypename) as storetypename,store.superMicro,store.estate,DATE_FORMAT(store.open_shop_time,'%Y/%c/%e') as open_shop_time,town.`name` as townname,IFNULL(store.address,store.detail_address) as detail_address,store.ordnumber,IFNULL(usee.mobilephone,usee.phone) as mobilephone,  usee.`name` as shopmanager, "
				+ " coun.`name` as countname,IFNULL(store.nature,'') as nature,IFNULL(store.tenancy_term,'') as tenancyTerm,IFNULL(store.rental,'') as rental,IFNULL(store.payment_method,'') as payment_method "
				+ " ,cc.audit_date,cc.enter_date,cc.enter_end_date,cc.submit_date,cc.card_content,store.rent_area,store.agency_fee,store.increase_fee,store.rent_free,store.taxes,store.increase,IF(cc.store_id is null,'无','有') as if_bussins "
				+ " FROM	t_store store LEFT JOIN tb_bizbase_user usee ON store.skid = usee.id LEFT JOIN t_town town ON town.id IN (store.town_id) LEFT JOIN t_county coun ON coun.id = town.county_id  "
				+ " LEFT JOIN (SELECT audit_date,enter_date,enter_end_date,submit_date,card_content,store_id FROM t_store_document_info WHERE audit_status=3) cc ON cc.store_id=store.store_id "
				+ " WHERE (store.`name` not LIKE '%储备店%' and store.`name` not LIKE '%测试%' and store.`name` not LIKE '%办公室%')  AND store.storeno is not NULL and store.flag=0  "
				+ where + " order by store.city_name desc,store.ordnumber";

		Map<String, Object> map_result = new HashMap<String, Object>();

		Integer total_pages = 0;
		String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";

		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql_count);
		Object total = query_count.uniqueResult();
		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<?> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();

		total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("data", list);
		map_result.put("status", "success");
		map_result.put("totalPage", total_pages);
		return map_result;
	}

	@Override
	public Map<String, Object> exportAboutStore(String where) {
		String sql = "SELECT store.city_name,store.`name`,IF(store.storetype='W','',store.storeno)as storeno,IF(store.storetype='W','',store.storetypename) as storetypename,store.superMicro,store.estate,DATE_FORMAT(store.open_shop_time,'%Y/%c/%e') as open_shop_time,town.`name` as townname,IFNULL(store.address,store.detail_address) as detail_address,store.ordnumber,IFNULL(usee.mobilephone,usee.phone) as mobilephone,  usee.`name` as shopmanager, "
				+ " coun.`name` as countname,IFNULL(store.nature,'') as nature,IFNULL(store.tenancy_term,'') as tenancyTerm,IFNULL(store.rental,'') as rental,IFNULL(store.payment_method,'') as payment_method "
				+ " ,cc.audit_date,cc.enter_date,cc.enter_end_date,cc.submit_date,cc.card_content,store.rent_area,store.agency_fee,store.increase_fee,store.rent_free,store.taxes,store.increase,IF(cc.store_id is null,'无','有') as if_bussins "
				+ " FROM	t_store store LEFT JOIN tb_bizbase_user usee ON store.skid = usee.id LEFT JOIN t_town town ON town.id IN (store.town_id) LEFT JOIN t_county coun ON coun.id = town.county_id  "
				+ " LEFT JOIN (SELECT audit_date,enter_date,enter_end_date,submit_date,card_content,store_id FROM t_store_document_info WHERE audit_status=3) cc ON cc.store_id=store.store_id "
				+ " WHERE (store.`name` not LIKE '%储备店%' and store.`name` not LIKE '%测试%' and store.`name` not LIKE '%办公室%')  AND store.storeno is not NULL and store.flag=0  "
				+ where + " order by store.city_name desc,store.ordnumber";

		Map<String, Object> map_result = new HashMap<String, Object>();
		List<?> list = null;
		Integer total_pages = 0;
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		map_result.put("data", list);
		map_result.put("status", "success");
		map_result.put("totalPage", total_pages);
		return map_result;
	}

	@Override
	public List<Map<String, Object>> getStoreCountOfcity() {
		String sql = "SELECT city_name,count(store_id) as count FROM t_store WHERE flag=0 AND `name` NOT  LIKE '%测试%' and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' and storetype!='V' AND ifnull(estate,'')!='闭店中' GROUP BY city_name";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getStoreCountOfArea() {
		String sql = "select COUNT(DISTINCT store_id) as count from t_area where status=0 ";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getStoreNature() {
		String sql = "select DISTINCT s.city_name,ifnull(t1.self_count,0) as self_count,ifnull(t2.cooperative_count,0) as cooperative_count from t_store s left join ( "
			+"select count(store_id) as self_count,city_name from t_store where nature = '自营店' and flag=0 AND `name` NOT  LIKE '%测试%' "
			+"and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' and storetype!='V' AND ifnull(estate,'')!='闭店中' GROUP BY city_name) "
			+"t1 ON (s.city_name = t1.city_name) LEFT JOIN ( "
			+"select count(store_id) as cooperative_count,city_name from t_store where nature = '合作店' and flag=0 AND `name` NOT  LIKE '%测试%' "
			+"and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' AND ifnull(estate,'')!='闭店中' GROUP BY city_name) "
			+"t2 ON (t2.city_name = s.city_name) where s.flag=0 AND s.`name` NOT  LIKE '%测试%' and s.`name` NOT  LIKE '%储备%' and s.`name` NOT  LIKE '%办公室%' and s.storetype!='V' AND ifnull(s.estate,'')!='闭店中'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getStoreTypeNatureOfCity(String nature, String storetype) {
		String sql = "select count(store_id) as count,city_name from t_store where nature = '"+nature+"' and storetype = '"+storetype+"' and flag=0 AND `name` NOT  LIKE '%测试%' "
				+"and `name` NOT  LIKE '%储备%' and `name` NOT  LIKE '%办公室%' and storetype!='V' AND ifnull(estate,'')!='闭店中' GROUP BY city_name";
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			// 获得查询数据
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
	}

}
