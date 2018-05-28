package com.cnpc.pms.personal.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.core.impl.DAORootHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.personal.dao.HumanresourcesDao;
import com.cnpc.pms.personal.entity.Humanresources;
import com.cnpc.pms.personal.entity.PublicOrder;
import com.cnpc.pms.personal.entity.Store;

public class HumanresourcesDaoImpl extends DAORootHibernate implements HumanresourcesDao{

	//取得汇思最大员工编号
	@Override
	public String queryMaxEmpNo(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t_humanresources.employee_no from t_humanresources WHERE 1=1 ");
		sql.append(" and t_humanresources.employee_no like 'GA"+type+"%' ");
		/*if(type!=null&&type.equals("HS")){
			sql.append(" and t_humanresources.employee_no like 'GAHSBJ%' ");
		}else{
			sql.append(" and t_humanresources.employee_no like 'GATKBJ%' ");
		}*/
		sql.append(" ORDER BY t_humanresources.employee_no DESC limit 1");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql.toString());
		//获得查询数据
        List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        String maxEmployee_no = "00000";
        for(Object o:lst_data){
        	Map<String, Object> map = (Map<String, Object>)o;
        	maxEmployee_no=map.get("employee_no").toString();
        }
		return maxEmployee_no;
	}
	
	
	
	
	//取得店长最大员工编号
		@Override
		public String queryMaxStoreKeeperNo() {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT t_humanresources.employee_no from t_humanresources WHERE 1=1 ");
			sql.append(" and t_humanresources.employee_no like 'DZ%' ");
			sql.append(" ORDER BY t_humanresources.employee_no DESC limit 1");
			SQLQuery query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql.toString());
			//获得查询数据
	        List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	        String maxEmployee_no = null;
	        for(Object o:lst_data){
	        	Map<String, Object> map = (Map<String, Object>)o;
	        	maxEmployee_no=map.get("employee_no").toString();
	        }
			return maxEmployee_no;
		}
		
	
		//取得最大邀请码
				@Override
				public String queryMaxInviteCode() {
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT MAX(inviteCode)+1 as maxInviteCode FROM t_humanresources");
					SQLQuery query = getHibernateTemplate().getSessionFactory()
							.getCurrentSession().createSQLQuery(sql.toString());
					//获得查询数据
			        List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			        String maxInviteCode = null;
			        for(Object o:lst_data){
			        	Map<String, Object> map = (Map<String, Object>)o;
			        	maxInviteCode=map.get("maxInviteCode")==null?"":map.get("maxInviteCode").toString().substring(0,6);
			        }
					return maxInviteCode;
				}	
		
		
		
		
		
		
		//员工档案查询 
		@Override
		public Map<String, Object> queryHumanresourcesList(Humanresources humanresources, PageInfo pageInfo) {
			String sqlwhere = " 1=1 ";
			if(humanresources!=null&&humanresources.getHumanstatus()!=null){
				sqlwhere +=" and humanstatus = '"+humanresources.getHumanstatus()+"'";
			}
			if(humanresources!=null&&humanresources.getTopostdate()!=null&&humanresources.getTopostdate().length()>0){
				String startDate = humanresources.getTopostdate().split("-")[0].toString().trim();
				String endDate = humanresources.getTopostdate().split("-")[1].toString().trim();
				
				if(humanresources!=null&&humanresources.getHumanstatus()!=null&&humanresources.getHumanstatus().equals(1L)){
					//在职
					sqlwhere +=" and DATE_FORMAT(topostdate,'%Y/%m/%d') >='"+startDate+"'";
					sqlwhere +=" and DATE_FORMAT(topostdate,'%Y/%m/%d') <='"+endDate+"'";
				}else if(humanresources!=null&&humanresources.getHumanstatus()!=null&&humanresources.getHumanstatus().equals(2L)){
					//离职
					sqlwhere +=" and DATE_FORMAT(leavedate,'%Y/%m/%d') >='"+startDate+"'";
					sqlwhere +=" and DATE_FORMAT(leavedate,'%Y/%m/%d') <='"+endDate+"'";
				}
			}
			
			if(humanresources!=null&&humanresources.getCitySelect()!=null&&humanresources.getCitySelect().length()>0){
				sqlwhere +=" and citySelect = '"+humanresources.getCitySelect()+"'";
			}
			if(humanresources!=null&&humanresources.getStorename()!=null&&humanresources.getStorename().length()>0){
				sqlwhere +=" and storename = '"+humanresources.getStorename()+"'";
			}
			if(humanresources!=null&&humanresources.getName()!=null&&humanresources.getName().length()>0){
				sqlwhere +=" and name like '%"+humanresources.getName().trim()+"%'";
			}
			if(humanresources!=null&&humanresources.getPhone()!=null&&humanresources.getPhone().length()>0){
				sqlwhere +=" and phone = '"+humanresources.getPhone().trim()+"'";
			}
			if(humanresources!=null&&humanresources.getEmployee_no()!=null&&humanresources.getEmployee_no().length()>0){
				sqlwhere +=" and employee_no = '"+humanresources.getEmployee_no().trim()+"'";
			}
			
			String sql = "SELECT * FROM t_humanresources a where "+sqlwhere ;
			String sql_count="SELECT count(*) as total FROM t_humanresources a where "+sqlwhere ;

			Query query_count = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql_count);
			List<?> total = query_count
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			Map<String, Object> maps = (Map<String, Object>) total.get(0);
			
			pageInfo.setTotalRecords(Integer.valueOf(maps.get("total").toString()));

			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);

			List<?> list = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(
							pageInfo.getRecordsPerPage()
									* (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();
			Map<String, Object> map_result = new HashMap<String, Object>();
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("data", list);
			map_result.put("total_pages", total_pages);
			return map_result;
		}
		
		
		
		
		
		//线上员工档案查询 
		@Override
		public Map<String, Object> queryOnLineHumanresourcesList(Humanresources humanresources, PageInfo pageInfo) {
			String sqlwhere = " 1=1 ";
			
			/*String sql = "SELECT * FROM (SELECT ol.citySelect,ol.deptname,ol.name,ol.employee_no,ol.inviteCode,ol.cardnumber,ol.orgname,ol.phone,ol.work_no FROM t_online_humanresources ol "+
						" UNION ALL "+
						" SELECT ts.citySelect,ts.deptname,ts.name,ts.employee_no,ts.inviteCode,ts.cardnumber,ts.orgname,ts.phone,ts.cardnumber FROM t_storekeeper ts) a ";
			sql+=" where "+sqlwhere;*/
			
			
			if(humanresources!=null&&humanresources.getCitySelect()!=null&&humanresources.getCitySelect().length()>0){
				if(!humanresources.getCitySelect().equals("全部")){
					sqlwhere +=" and citySelect = '"+humanresources.getCitySelect()+"'";
				}
			}
			
			if(humanresources!=null&&humanresources.getName()!=null&&humanresources.getName().length()>0){
				sqlwhere +=" and name like '%"+humanresources.getName().trim()+"%'";
			}
			if(humanresources!=null&&humanresources.getPhone()!=null&&humanresources.getPhone().length()>0){
				sqlwhere +=" and phone = '"+humanresources.getPhone().trim()+"'";
			}
			if(humanresources!=null&&humanresources.getEmployee_no()!=null&&humanresources.getEmployee_no().length()>0){
				sqlwhere +=" and employee_no = '"+humanresources.getEmployee_no().trim()+"'";
			}
			
			String sql = "SELECT * FROM t_online_humanresources a where "+sqlwhere ;
			
			String sql_count="SELECT count(*) as total FROM ("+sql+") b";

			Query query_count = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql_count);
			List<?> total = query_count
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			Map<String, Object> maps = (Map<String, Object>) total.get(0);
			
			pageInfo.setTotalRecords(Integer.valueOf(maps.get("total").toString()));

			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);

			List<?> list = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(
							pageInfo.getRecordsPerPage()
									* (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();
			Map<String, Object> map_result = new HashMap<String, Object>();
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("data", list);
			map_result.put("total_pages", total_pages);
			return map_result;
		}
				
		
		@Override
		public Map<String, Object> queryStoreOperationLeaveList(){
			String sql = "SELECT human.storename,count(human.id) as leavesum "+
					" FROM t_humanresources human WHERE human.humanstatus=2 AND human.storename is not NULL AND human.storename<>'' "+ 
					" GROUP BY human.storename";
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<?> list = query.list();
			Map<String, Object> map_result = new HashMap<String, Object>();
			map_result.put("data", list);
			return map_result;
		}
		
		
		@Override
		public Map<String, Object> queryStoreOperationList(Store store, PageInfo pageInfo) {
			String wheresql = " 1=1 ";
			if(store!=null&&store.getName()!=null&&store.getName().length()>0){
				wheresql+=" and ts.name='"+store.getName()+"' ";
			}
			if(store!=null&&store.getCityName()!=null&&store.getCityName().length()>0){
				wheresql+=" and ts.city_name = '"+store.getCityName()+"'";
			}
			wheresql +=" order by create_time desc ";
			String sql = "SELECT ts.city_name,ts.store_id,ts.name as storename,ts.estate,ts.storeno,ts.open_shop_time AS create_time,"+
					" CASE WHEN b.gax is NULL THEN 0 ELSE b.gax END AS 'gax',"+
					" CASE WHEN b.zhgl is NULL THEN 0 ELSE b.zhgl END AS 'zhgl',"+
					" CASE WHEN b.swgl is NULL THEN 0 ELSE b.swgl END AS 'swgl',"+
					" CASE WHEN b.fwzy is NULL THEN 0 ELSE b.fwzy END AS 'fwzy',"+
					" CASE WHEN b.zhzy is NULL THEN 0 ELSE b.zhzy END AS 'zhzy',"+
					" CASE WHEN b.fdz is NULL THEN 0 ELSE b.fdz END AS 'fdz',"+
					" CASE WHEN b.xddz is NULL THEN 0 ELSE b.xddz END AS 'xddz',"+
					" CASE WHEN b.ddcly is NULL THEN 0 ELSE b.ddcly END AS 'ddcly',"+
					" CASE WHEN b.psy is NULL THEN 0 ELSE b.psy END AS 'psy',"+
					" CASE WHEN b.kfgly is NULL THEN 0 ELSE b.kfgly END AS 'kfgly',"+
					" CASE WHEN b.ckzg is NULL THEN 0 ELSE b.ckzg END AS 'ckzg',"+
					" CASE WHEN b.pszg is NULL THEN 0 ELSE b.pszg END AS 'pszg' "+
					" FROM t_store ts LEFT JOIN  (SELECT a.store_id,a.storename,sum(a.gax) as gax,sum(a.zhgl) as zhgl,sum(a.swgl) as swgl,sum(a.fwzy) as fwzy,sum(a.zhzy) as zhzy,sum(a.fdz) as fdz,"+
					" sum(a.xddz) as xddz,sum(a.ddcly) as ddcly,sum(a.psy) as psy,sum(a.kfgly) as kfgly,sum(a.ckzg) as ckzg,sum(a.pszg) as pszg from "+
					" (SELECT th.store_id,th.storename,th.zw,"+
					" CASE WHEN th.zw='国安侠' THEN count(th.id) ELSE 0 END AS 'gax',"+
					" CASE WHEN th.zw='综合管理' THEN count(th.id) ELSE 0 END AS 'zhgl',"+
					" CASE WHEN th.zw='事务管理' THEN count(th.id) ELSE 0 END AS 'swgl',"+
					" CASE WHEN th.zw='服务专员' THEN count(th.id) ELSE 0 END AS 'fwzy', "+
					" CASE WHEN th.zw='综合专员' THEN count(th.id) ELSE 0 END AS 'zhzy', "+
					" CASE WHEN th.zw='副店长' THEN count(th.id) ELSE 0 END AS 'fdz',"+
					" CASE WHEN th.zw='星店店长' THEN count(th.id) ELSE 0 END AS 'xddz',"+
					" CASE WHEN th.zw='订单处理员' THEN count(th.id) ELSE 0 END AS 'ddcly',"+
					" CASE WHEN th.zw='配送员' THEN count(th.id) ELSE 0 END AS 'psy',  "+
					" CASE WHEN th.zw='库房管理员' THEN count(th.id) ELSE 0 END AS 'kfgly',"+ 
					" CASE WHEN th.zw='仓库主管' THEN count(th.id) ELSE 0 END AS 'ckzg', "+
					" CASE WHEN th.zw='配送主管' THEN count(th.id) ELSE 0 END AS 'pszg' "+
					" FROM t_humanresources th WHERE th.humanstatus=1 AND th.storename is not NULL AND th.storename<>''  "+
					" GROUP BY th.storename,th.zw) a GROUP BY a.storename) b ON b.store_id=ts.store_id where " + wheresql;
			
			
			//String sql = "SELECT * FROM t_humanresources a where "+sqlwhere ;
			String sql_count="SELECT count(*) as total FROM ("+sql+") b" ;

			Query query_count = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql_count);
			List<?> total = query_count
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			Map<String, Object> maps = (Map<String, Object>) total.get(0);
			
			pageInfo.setTotalRecords(Integer.valueOf(maps.get("total").toString()));

			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);

			List<?> list = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(
							pageInfo.getRecordsPerPage()
									* (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();
			Map<String, Object> map_result = new HashMap<String, Object>();
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("data", list);
			map_result.put("total_pages", total_pages);
			return map_result;
		}
		
		
		
		
		
		
		
		//员工档案导出
		@Override
		public List<Map<String, Object>> exportHuman(Humanresources humanresources){
			String sqlwhere = " 1=1 ";
			if(humanresources!=null&&humanresources.getHumanstatus()!=null){
				sqlwhere +=" and a.humanstatus = '"+humanresources.getHumanstatus()+"'";
			}
			if(humanresources!=null&&humanresources.getTopostdate()!=null&&humanresources.getTopostdate().length()>0){
				String startDate = humanresources.getTopostdate().split("-")[0].toString().trim();
				String endDate = humanresources.getTopostdate().split("-")[1].toString().trim();
				
				if(humanresources!=null&&humanresources.getHumanstatus()!=null&&humanresources.getHumanstatus().equals(1L)){
					//在职
					sqlwhere +=" and DATE_FORMAT(a.topostdate,'%Y/%m/%d') >='"+startDate+"'";
					sqlwhere +=" and DATE_FORMAT(a.topostdate,'%Y/%m/%d') <='"+endDate+"'";
				}else if(humanresources!=null&&humanresources.getHumanstatus()!=null&&humanresources.getHumanstatus().equals(2L)){
					//离职
					sqlwhere +=" and DATE_FORMAT(a.leavedate,'%Y/%m/%d') >='"+startDate+"'";
					sqlwhere +=" and DATE_FORMAT(a.leavedate,'%Y/%m/%d') <='"+endDate+"'";
				}
			}
			
			if(humanresources!=null&&humanresources.getCitySelect()!=null&&humanresources.getCitySelect().length()>0){
				sqlwhere +=" and a.citySelect = '"+humanresources.getCitySelect()+"'";
			}
			if(humanresources!=null&&humanresources.getStorename()!=null&&humanresources.getStorename().length()>0){
				sqlwhere +=" and a.storename = '"+humanresources.getStorename()+"'";
			}
			if(humanresources!=null&&humanresources.getName()!=null&&humanresources.getName().length()>0){
				sqlwhere +=" and a.name like '%"+humanresources.getName().trim()+"%'";
			}
			if(humanresources!=null&&humanresources.getPhone()!=null&&humanresources.getPhone().length()>0){
				sqlwhere +=" and a.phone = '"+humanresources.getPhone().trim()+"'";
			}
			if(humanresources!=null&&humanresources.getEmployee_no()!=null&&humanresources.getEmployee_no().length()>0){
				sqlwhere +=" and a.employee_no = '"+humanresources.getEmployee_no().trim()+"'";
			}
			
			String sql = "SELECT a.name,a.employee_no,a.inviteCode,a.storename,a.citySelect,a.zw,a.career_group,a.topostdate,s.storeno FROM t_humanresources a LEFT JOIN t_store s ON a.store_id=s.store_id where "+sqlwhere ;
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		}
		
		
		//线上 员工档案导出
		@Override
		public List<Map<String, Object>> exportOnLineHuman(Humanresources humanresources){
			String sqlwhere = " 1=1 ";
			if(humanresources!=null&&humanresources.getCitySelect()!=null&&humanresources.getCitySelect().length()>0){
				if(!humanresources.getCitySelect().equals("全部")){
					sqlwhere +=" and citySelect = '"+humanresources.getCitySelect()+"'";
				}
			}
			
			if(humanresources!=null&&humanresources.getName()!=null&&humanresources.getName().length()>0){
				sqlwhere +=" and name like '%"+humanresources.getName().trim()+"%'";
			}
			if(humanresources!=null&&humanresources.getPhone()!=null&&humanresources.getPhone().length()>0){
				sqlwhere +=" and phone = '"+humanresources.getPhone().trim()+"'";
			}
			if(humanresources!=null&&humanresources.getEmployee_no()!=null&&humanresources.getEmployee_no().length()>0){
				sqlwhere +=" and employee_no = '"+humanresources.getEmployee_no().trim()+"'";
			}
			
			String sql = "SELECT citySelect,deptname,name,employee_no,inviteCode,phone,work_no,cardnumber FROM t_online_humanresources where "+sqlwhere ;
			
			//String sql = "SELECT a.name,a.employee_no,a.inviteCode,a.storename,a.citySelect,a.zw,a.career_group,a.topostdate,s.storeno FROM t_humanresources a LEFT JOIN t_store s ON a.store_id=s.store_id where "+sqlwhere ;
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		}
		
		
		@Override
		public List<Map<String, Object>> exportStoreOperationHuman(Store store){
			String wheresql = " 1=1 ";
			if(store!=null&&store.getName()!=null&&store.getName().length()>0){
				wheresql+=" and ts.name='"+store.getName()+"' ";
			}
			if(store!=null&&store.getCityName()!=null&&store.getCityName().length()>0){
				wheresql+=" and ts.city_name = '"+store.getCityName()+"'";
			}
			wheresql +=" order by create_time desc ";
			String sql = "SELECT ts.city_name,ts.store_id,ts.name as storename,ts.estate,ts.storeno,ts.open_shop_time AS create_time,"+
					" CASE WHEN b.gax is NULL THEN 0 ELSE b.gax END AS 'gax',"+
					" CASE WHEN b.zhgl is NULL THEN 0 ELSE b.zhgl END AS 'zhgl',"+
					" CASE WHEN b.swgl is NULL THEN 0 ELSE b.swgl END AS 'swgl',"+
					" CASE WHEN b.fwzy is NULL THEN 0 ELSE b.fwzy END AS 'fwzy',"+
					" CASE WHEN b.zhzy is NULL THEN 0 ELSE b.zhzy END AS 'zhzy',"+
					" CASE WHEN b.fdz is NULL THEN 0 ELSE b.fdz END AS 'fdz',"+
					" CASE WHEN b.xddz is NULL THEN 0 ELSE b.xddz END AS 'xddz',"+
					" CASE WHEN b.ddcly is NULL THEN 0 ELSE b.ddcly END AS 'ddcly',"+
					" CASE WHEN b.psy is NULL THEN 0 ELSE b.psy END AS 'psy',"+
					" CASE WHEN b.kfgly is NULL THEN 0 ELSE b.kfgly END AS 'kfgly',"+
					" CASE WHEN b.ckzg is NULL THEN 0 ELSE b.ckzg END AS 'ckzg',"+
					" CASE WHEN b.pszg is NULL THEN 0 ELSE b.pszg END AS 'pszg' "+
					" FROM t_store ts LEFT JOIN  (SELECT a.store_id,a.storename,sum(a.gax) as gax,sum(a.zhgl) as zhgl,sum(a.swgl) as swgl,sum(a.fwzy) as fwzy,sum(a.zhzy) as zhzy,sum(a.fdz) as fdz,"+
					" sum(a.xddz) as xddz,sum(a.ddcly) as ddcly,sum(a.psy) as psy,sum(a.kfgly) as kfgly,sum(a.ckzg) as ckzg,sum(a.pszg) as pszg from "+
					" (SELECT th.store_id,th.storename,th.zw,"+
					" CASE WHEN th.zw='国安侠' THEN count(th.id) ELSE 0 END AS 'gax',"+
					" CASE WHEN th.zw='综合管理' THEN count(th.id) ELSE 0 END AS 'zhgl',"+
					" CASE WHEN th.zw='事务管理' THEN count(th.id) ELSE 0 END AS 'swgl',"+
					" CASE WHEN th.zw='服务专员' THEN count(th.id) ELSE 0 END AS 'fwzy', "+
					" CASE WHEN th.zw='综合专员' THEN count(th.id) ELSE 0 END AS 'zhzy', "+
					" CASE WHEN th.zw='副店长' THEN count(th.id) ELSE 0 END AS 'fdz',"+
					" CASE WHEN th.zw='星店店长' THEN count(th.id) ELSE 0 END AS 'xddz',"+
					" CASE WHEN th.zw='订单处理员' THEN count(th.id) ELSE 0 END AS 'ddcly',"+
					" CASE WHEN th.zw='配送员' THEN count(th.id) ELSE 0 END AS 'psy',  "+
					" CASE WHEN th.zw='库房管理员' THEN count(th.id) ELSE 0 END AS 'kfgly',"+ 
					" CASE WHEN th.zw='仓库主管' THEN count(th.id) ELSE 0 END AS 'ckzg', "+
					" CASE WHEN th.zw='配送主管' THEN count(th.id) ELSE 0 END AS 'pszg' "+
					" FROM t_humanresources th WHERE th.humanstatus=1 AND th.storename is not NULL AND th.storename<>''  "+
					" GROUP BY th.storename,th.zw) a GROUP BY a.storename) b ON b.store_id=ts.store_id where " + wheresql;
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		}




		@Override
		public List<Map<String, Object>> queryToPostHuman(String zw) {
			String sql = "";
			String sql_storekeeps = "select count(*) as count,subdate(DATE_FORMAT(topostdate,'%Y-%m-%d'),date_format(topostdate, '%w')) AS week_time from t_storekeeper where YEARWEEK(date_format(topostdate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time ";
			String sql_humanresources = "select count(*) as count,subdate(DATE_FORMAT(topostdate,'%Y-%m-%d'),date_format(topostdate, '%w')) AS week_time from t_humanresources where YEARWEEK(date_format(topostdate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time ";
			if("店长".equals(zw)){
				sql = sql_storekeeps;
			}else if("国安侠".equals(zw)){
				sql = "select count(*) as count,subdate(DATE_FORMAT(topostdate,'%Y-%m-%d'),date_format(topostdate, '%w')) AS week_time from t_humanresources where zw = '国安侠' and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time ";
			}else if("专员".equals(zw)){
				sql = "select count(*) as count,subdate(DATE_FORMAT(topostdate,'%Y-%m-%d'),date_format(topostdate, '%w')) AS week_time from t_humanresources where (zw = '服务专员' or zw = '线上服务专员') and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time ";
			}else if(zw == null){//总人数
				sql = "select sum(count) as count,CONCAT(week_time,'') as week_time from (" + sql_humanresources +" UNION ALL "+ sql_storekeeps + " ) t where 1=1 GROUP BY t.week_time;";
			}else {
				return null;
			}
			/*String sql = "select sum(count) as count,CONCAT(week_time,'') as week_time from ("
					+"select count(*) as count,subdate(DATE_FORMAT(topostdate,'%Y-%m-%d'),date_format(topostdate, '%w')) AS week_time from t_storekeeper where YEARWEEK(date_format(topostdate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time "
					+"UNION ALL "
					+"select count(*) as count,subdate(DATE_FORMAT(topostdate,'%Y-%m-%d'),date_format(topostdate, '%w')) AS week_time from t_humanresources where YEARWEEK(date_format(topostdate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time "
					+") t where 1=1 GROUP BY t.week_time;";*/
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		}



		@Override
		public List<Map<String, Object>> queryLeaveHuman(String zw) {
			String sql = "";
			String sql_humanresources = "select count(*) as count,subdate(DATE_FORMAT(leavedate,'%Y-%m-%d'),date_format(leavedate, '%w')) AS week_time from t_humanresources where humanstatus = 2 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time";
			String sql_storekeeps = "select count(*) as count,subdate(DATE_FORMAT(leavedate,'%Y-%m-%d'),date_format(leavedate, '%w')) AS week_time from t_storekeeper where humanstatus = 2 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time ";
			if("店长".equals(zw)){
				sql = sql_storekeeps;
			}else if("国安侠".equals(zw)){
				sql = "select count(*) as count,subdate(DATE_FORMAT(leavedate,'%Y-%m-%d'),date_format(leavedate, '%w')) AS week_time from t_humanresources where zw = '国安侠' and humanstatus = 2 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time";
			}else if("专员".equals(zw)){
				sql = "select count(*) as count,subdate(DATE_FORMAT(leavedate,'%Y-%m-%d'),date_format(leavedate, '%w')) AS week_time from t_humanresources where (zw = '服务专员' or zw= '线上服务专员') and humanstatus = 2 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time";
			}else if(zw == null){//总人数
				sql = "select sum(count) as count,CONCAT(week_time,'') as week_time from (" + sql_humanresources +" UNION ALL "+ sql_storekeeps + " ) t where 1=1 GROUP BY t.week_time;";
			}else {
				return null;
			}
			/*String sql = "select SUM(count) as count,CONCAT(week_time,'') as week_time from "
					+"(select count(*) as count,subdate(DATE_FORMAT(leavedate,'%Y-%m-%d'),date_format(leavedate, '%w')) AS week_time from t_storekeeper where humanstatus = 2 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time "
					+"UNION ALL "
					+"select count(*) as count,subdate(DATE_FORMAT(leavedate,'%Y-%m-%d'),date_format(leavedate, '%w')) AS week_time from t_humanresources where humanstatus = 2 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) > YEARWEEK(now())-6 and YEARWEEK(date_format(leavedate,'%Y-%m-%d')) <= YEARWEEK(now()) GROUP BY week_time) t3 "
					+"where 1=1 GROUP BY t3.week_time;";*/
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		}




		@Override
		public List<Map<String, Object>> queryHumanTotal(String zw) {
			
			String sql = "";
			String sql_humanresources = "select sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-5 then 1 else 0 end) as a,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-4 then 1 else 0 end) as b,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-3 then 1 else 0 end) as c,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-2 then 1 else 0 end) as d,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-1 then 1 else 0 end) as e,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) then 1 else 0 end) as f from t_humanresources ";
			String sql_storekeeps = "select sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-5 then 1 else 0 end) as a,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-4 then 1 else 0 end) as b,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-3 then 1 else 0 end) as c,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-2 then 1 else 0 end) as d,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-1 then 1 else 0 end) as e,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) then 1 else 0 end) as f from t_storekeeper ";
						
			if("店长".equals(zw)){
				sql = sql_storekeeps;
			}else if("国安侠".equals(zw)){
				sql = "select sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-5 then 1 else 0 end) as a,"
						+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-4 then 1 else 0 end) as b,"
						+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-3 then 1 else 0 end) as c,"
						+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-2 then 1 else 0 end) as d,"
						+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-1 then 1 else 0 end) as e,"
						+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) then 1 else 0 end) as f from t_humanresources where zw = '国安侠'";
			}else if("专员".equals(zw)){
				sql = "select sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-5 then 1 else 0 end) as a,"
						+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-4 then 1 else 0 end) as b,"
						+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-3 then 1 else 0 end) as c,"
						+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-2 then 1 else 0 end) as d,"
						+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-1 then 1 else 0 end) as e,"
						+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) then 1 else 0 end) as f from t_humanresources where zw = '服务专员' or zw = '线上服务专员'";
			}else if(zw == null){//总人数
				sql = "select SUM(a) as week1,SUM(b) as week2,SUM(c) as week3,SUM(d) as week4,SUM(e) as week5,SUM(f) as week6 from ( " + sql_humanresources +" UNION ALL "+ sql_storekeeps + " ) t";
			}else {
				return null;
			}
			
			/*String sql = "select SUM(a) as week1,SUM(b) as week2,SUM(c) as week3,SUM(d) as week4,SUM(e) as week5,SUM(f) as week6 from( "
					+"select sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-5 then 1 else 0 end) as a,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-4 then 1 else 0 end) as b,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-3 then 1 else 0 end) as c,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-2 then 1 else 0 end) as d,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-1 then 1 else 0 end) as e,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) then 1 else 0 end) as f from t_storekeeper "
					+"UNION ALL "
					+"select sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-5 then 1 else 0 end) as a,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-4 then 1 else 0 end) as b,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-3 then 1 else 0 end) as c,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-2 then 1 else 0 end) as d,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now())-1 then 1 else 0 end) as e,"
					+"sum(CASE when humanstatus != 2 and YEARWEEK(date_format(topostdate,'%Y-%m-%d')) <= YEARWEEK(now()) then 1 else 0 end) as f from t_humanresources) t;";*/
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		}




		@Override
		public List<Map<String, Object>> queryWeekPoint() {
			String sql = "SELECT dist.cityname,IFNULL(c.yunziying,0) as yunziying,IFNULL(c.yunhezuo,0) as yunhezuo,IFNULL(n.yunziying,0) as chouziying,IFNULL(n.yunhezuo,0) as chouyunhezuo,IFNULL(emplo.fudianzhang,0) as fudianzhang, "
					+"IFNULL(emplo.fuwu,0) as fuwu,IFNULL(emplo.guoanxia,0) as guoanxia,IFNULL(emplo.zonghe,0) as zonghe,ifnull(keeper.dianzhang,0) as dianzhang,ifnull(aa.total,0) as topostcount,ifnull(bb.total,0) as leavecount "
					+"FROM t_dist_citycode dist LEFT JOIN "
					+"(SELECT city_name,sum(CASE WHEN nature='自营店' then 1 else 0 end ) as yunziying,sum(CASE WHEN nature='合作店' then 1 else 0 end ) as yunhezuo FROM	t_store WHERE flag = 0 AND `name` NOT LIKE '%测试%' "
					+"AND `name` NOT LIKE '%储备%' AND `name` NOT LIKE '%办公室%' AND storetype != 'V' AND (nature='自营店' or nature='合作店') AND (estate='运营中' or estate='试运营') GROUP BY city_name) c "
					+"ON c.city_name=dist.cityname LEFT JOIN "
					+"(SELECT city_name,sum(CASE WHEN nature='自营店' then 1 else 0 end ) as yunziying,sum(CASE WHEN nature='合作店' then 1 else 0 end ) as yunhezuo FROM t_store WHERE flag = 0 AND `name` NOT LIKE '%测试%' "
					+"AND `name` NOT LIKE '%储备%' AND `name` NOT LIKE '%办公室%' AND storetype != 'V' AND (nature='自营店' or nature='合作店') AND (estate='筹备中') GROUP BY city_name) n "
					+"ON n.city_name=dist.cityname LEFT JOIN "
					+"(SELECT sto.city_name,SUM(CASE WHEN human.zw='国安侠' THEN 1 else 0 end ) as guoanxia,SUM(CASE WHEN human.zw='副店长' THEN 1 else 0 end ) as fudianzhang, "
					+"		SUM(CASE WHEN (human.zw='服务专员' OR  human.zw='线上服务专员') THEN 1 else 0 end ) as fuwu,SUM(CASE WHEN human.zw='综合管理' THEN 1 else 0 end ) as zonghe "
					+"		FROM t_humanresources human LEFT JOIN t_store sto ON sto.store_id= human.store_id  WHERE human.humanstatus!=2 AND human.store_id is not NULL AND ( "
					+"				human.zw='副店长' or human.zw='国安侠' or human.zw='服务专员' OR  human.zw='线上服务专员' or human.zw='综合管理')GROUP BY sto.city_name) emplo "
					+"ON emplo.city_name=dist.cityname LEFT JOIN "
					+"(SELECT citySelect,COUNT(1) as dianzhang FROM t_storekeeper WHERE zw ='店长' AND humanstatus != 2 GROUP BY citySelect) keeper "
					+"ON keeper.citySelect = dist.cityname LEFT JOIN "
					+"(select t.city_name as city_name,sum(t.count) as total from (select sto.city_name as city_name,count(*) as count from t_humanresources human LEFT JOIN t_store sto ON sto.store_id= human.store_id  "
					+"where YEARWEEK(date_format(human.topostdate,'%Y-%m-%d')) = YEARWEEK(now()) GROUP BY sto.city_name UNION ALL select citySelect as city_name,count(*) as count from t_storekeeper "
					+"where YEARWEEK(date_format(topostdate,'%Y-%m-%d')) = YEARWEEK(now()) GROUP BY citySelect) t GROUP BY t.city_name) aa "
					+"ON aa.city_name = dist.cityname LEFT JOIN "
					+"(select t.city_name as city_name,SUM(t.count) as total from (select sto.city_name as city_name,count(*) as count from t_humanresources human LEFT JOIN t_store sto ON sto.store_id= human.store_id "
					+"where YEARWEEK(date_format(human.leavedate,'%Y-%m-%d')) = YEARWEEK(now()) and humanstatus = 2 GROUP BY sto.city_name UNION ALL select citySelect as city_name,count(*) as count "
					+"from t_storekeeper where YEARWEEK(date_format(leavedate,'%Y-%m-%d')) = YEARWEEK(now()) and humanstatus  = 2 GROUP BY citySelect) t GROUP BY t.city_name) bb "
					+"ON bb.city_name = dist.cityname";
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		}
}
