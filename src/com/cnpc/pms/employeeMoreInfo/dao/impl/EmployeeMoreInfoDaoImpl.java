package com.cnpc.pms.employeeMoreInfo.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.employeeMoreInfo.dao.EmployeeMoreInfoDao;
import com.cnpc.pms.utils.DateUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.personal.dao.impl
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/7/10 14:36
 */
public class EmployeeMoreInfoDaoImpl extends BaseDAOHibernate implements EmployeeMoreInfoDao {


    @Override
    public List<Map<String, Object>> queryValidHumanresource() {
        String sql=" select employee_no,topostdate from t_humanresources where humanstatus=1";
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try{
            Query query = session.createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> selectValidStoreKepeer() {
        String sql=" select employee_no,topostdate from t_storekeeper where humanstatus=1";
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try{
            Query query = session.createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return list;
    }

    @Override
    public void updateEmployeeWorkingAge(String employeeNo, Integer year, Integer month, Integer day) {
        String sql ="update t_employee_more_info set workingAge_year="+year+",workingAge_month="+month+",workingAge_day="+day+" where employeeNo='"+employeeNo+"'";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
    }

    @Override
    public List<Map<String, Object>> queryEmployeeMoveDistance(String employeeNo) {
        String sql="select FORMAT(moveDistance,2) as moveDistance  from t_employee_more_info where employeeNo='"+employeeNo+"' limit 1";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryAvgWorkTime() {
        String curDate = DateUtils.dateFormat(new Date(), "yyyy-MM");
        String sql = "select ROUND(sum(realovertime)/sum(workdays),2) as avgtime from (SELECT recode.employee_no,recode.realovertime,recode.workdays,recode.work_month from t_work_record recode LEFT JOIN " +
                "t_work_record_total total ON (recode.workrecord_id = total.id) where substring_index(recode.work_month,'-',1) = '2018' and total.commit_status =3 " +
                ") t1 INNER JOIN (select yearmonth,zw,employeeno from t_topdata_human where substring_index(yearmonth,'-',1) = '2018' ) t2 ON (t1.employee_no = t2.employeeno and t1.work_month = t2.yearmonth) " +
                "where t2.zw = '国安侠' and t2.yearmonth != '"+curDate+"'";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryAvgSentOrder() {
        String curYearMonth = DateUtils.dateFormat(new Date(), "yyyy-MM");
        String curYear = DateUtils.dateFormat(new Date(), "yyyy");
        Integer curMonth = Integer.parseInt(DateUtils.dateFormat(new Date(), "MM"));
        String sql = "select ROUND(sum(datanum)/sum(t2.workdays)) as avgorder from ( " +
                "select employee_no,sum(datanum) as datanum from ds_pes_order_empchannel_month where year = '"+curYear+"' and employee_no is not null and employee_no != '' and month !="+curMonth+" GROUP BY employee_no) t1 " +
                "LEFT JOIN ( " +
                "select employee_no,sum(workdays) as workdays  from (SELECT employee_no,workdays,work_month,workrecord_id from t_work_record where substring_index(work_month,'-',1) = '"+curYear+"') a1 " +
                "INNER JOIN t_work_record_total total ON a1.workrecord_id = total.id " +
                "INNER JOIN (select yearmonth,zw,employeeno from t_topdata_human where substring_index(yearmonth,'-',1) = '"+curYear+"' ) a2 ON (a1.employee_no = a2.employeeno and a1.work_month = a2.yearmonth) " +
                "where a2.zw = '国安侠' AND total.commit_status = 3 and a2.yearmonth != '"+curYearMonth+"'  GROUP BY a1.employee_no " +
                ") t2 ON (t1.employee_no = t2.employee_no)";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryAvgMileAge() {
        String curDate = DateUtils.dateFormat(new Date(), "yyyy-MM");
        String sql = "select ROUND(sum(info.oneDay_moveDistance)/count(human.employee_no),1) as avgmile from (select * from t_humanresources where zw = '国安侠' and humanstatus != 2 ) human INNER JOIN " +
                "(select * from t_employee_more_info where oneDay_moveDistance != 0 and oneDay_moveDistance is not null ) info ON (human.employee_no = info.employeeNo)";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryAvgSendOrderDistribution() {
        String curYearMonth = DateUtils.dateFormat(new Date(), "yyyy-MM");
        String curYear = DateUtils.dateFormat(new Date(), "yyyy");
        Integer curMonth = Integer.parseInt(DateUtils.dateFormat(new Date(), "MM"));
        String sql = "select sum(case when ifnull(t1.datanum/t2.workdays,0)>0 and ifnull(t1.datanum/t2.workdays,0)<=10 then 1 else 0 end) as count10," +
                "sum(case when ifnull(t1.datanum/t2.workdays,0)>10 and ifnull(t1.datanum/t2.workdays,0)<=20 then 1 else 0 end) as count20," +
                "sum(case when ifnull(t1.datanum/t2.workdays,0)>20 and ifnull(t1.datanum/t2.workdays,0)<=30 then 1 else 0 end) as count30," +
                "sum(case when ifnull(t1.datanum/t2.workdays,0)>30 and ifnull(t1.datanum/t2.workdays,0)<=40 then 1 else 0 end) as count40," +
                "sum(case when ifnull(t1.datanum/t2.workdays,0)>40 then 1 else 0 end) as count40add " +
                " from (select employee_no,sum(datanum) as datanum from ds_pes_order_empchannel_month where year = '"+curYear+"' and employee_no is not null and employee_no != '' and month != "+curMonth+" GROUP BY employee_no) t1 " +
                "LEFT JOIN ( " +
                "select employee_no,sum(workdays) as workdays  from (SELECT employee_no,workdays,work_month,workrecord_id from t_work_record where substring_index(work_month,'-',1) = '"+curYear+"') a1 " +
                "LEFT JOIN t_work_record_total total ON a1.workrecord_id = total.id " +
                "INNER JOIN (select yearmonth,zw,employeeno from t_topdata_human where substring_index(yearmonth,'-',1) = '"+curYear+"' ) a2 ON (a1.employee_no = a2.employeeno and a1.work_month = a2.yearmonth) " +
                "where a2.zw = '国安侠' AND total.commit_status = 3 and a2.yearmonth != '"+curYearMonth+"' GROUP BY a1.employee_no " +
                ") t2 ON (t1.employee_no = t2.employee_no)";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryAvgSendOrderMonthTrend() {
        String curYearMonth = DateUtils.dateFormat(new Date(), "yyyy-MM");
        String curYear = DateUtils.dateFormat(new Date(), "yyyy");
        Integer curMonth = Integer.parseInt(DateUtils.dateFormat(new Date(), "MM"));
        String sql = "select ROUND(sum(datanum)/COUNT(DISTINCT username)) as avgcount,month from ds_pes_order_empchannel_month where employee_no is not null and employee_no != '' and year = '"+curYear+"' and month != "+curMonth+" GROUP BY month";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryAvgSendOrderGroupByCity() {
        String curYearMonth = DateUtils.dateFormat(new Date(), "yyyy-MM");
        String curYear = DateUtils.dateFormat(new Date(), "yyyy");
        Integer curMonth = Integer.parseInt(DateUtils.dateFormat(new Date(), "MM"));
        String sql = "select tab1.cityname,ROUND(ifnull(tab1.datanum/tab2.workdays,0)) as datanum from ( " +
                "select sum(datanum) as datanum,cityname from ds_pes_order_empchannel_month where year = '"+curYear+"' and employee_no is not null and employee_no != '' and month != "+curMonth+" GROUP BY cityname) tab1 " +
                "LEFT JOIN ( " +
                "select t1.cityname,sum(t2.workdays) as workdays from ( " +
                "select employee_no,cityname from ds_pes_order_empchannel_month where employee_no is not null and employee_no != '' and year = '"+curYear+"' and month != "+curMonth+" GROUP BY employee_no) t1 LEFT JOIN ( " +
                "select employee_no,sum(workdays) as workdays  from (SELECT employee_no,workdays,work_month,workrecord_id from t_work_record where substring_index(work_month,'-',1) = '"+curYear+"') a1 " +
                "LEFT JOIN t_work_record_total total ON a1.workrecord_id = total.id " +
                "INNER JOIN (select yearmonth,zw,employeeno from t_topdata_human where substring_index(yearmonth,'-',1) = '"+curYear+"' ) a2 ON (a1.employee_no = a2.employeeno and a1.work_month = a2.yearmonth) " +
                "where a2.zw = '国安侠' and total.commit_status = 3 and a2.yearmonth != '"+curYearMonth+"'  GROUP BY a1.employee_no " +
                ") t2  ON (t1.employee_no = t2.employee_no) GROUP BY t1.cityname) tab2 ON tab1.cityname = tab2.cityname;";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryEmpAtAnalysis() {
        String sql = "select sum(case when table_total.empcount >10 then 1 else 0 end) as beyondhuman," +
                "sum(case when table_total.empcount BETWEEN 6 and 10 then 1 else 0 end) as fullhuman," +
                "sum(case when table_total.empcount <6 then 1 else 0 end) as lackhuman from ( " +
                "SELECT count(a.employee_no) empcount,b.storeName FROM " +
                "t_humanresources a INNER JOIN (select t.store_id,name as storeName,t.city_name from t_store t where t.name NOT LIKE '%测试%' AND t.name NOT LIKE '%储备%' " +
                "AND t.name NOT LIKE '%办公室%' AND t.flag = '0' AND ifnull(t.estate, '') = '运营中' and t.storetype = 'Y' ) b " +
                "on (a.store_id = b.store_id) where a.name not like '%测试%' and  a.humanstatus !=2 and a.zw = '国安侠' group by b.storeName ) table_total";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryEmpAtAnalysisByMonth(String status) {
        String curYearMonth = DateUtils.dateFormat(new Date(), "yyyy-MM");
        String curYear = DateUtils.dateFormat(new Date(), "yyyy");
        Integer curMonth = Integer.parseInt(DateUtils.dateFormat(new Date(), "MM"));
        String append_sql = "";
        if(status.equals("满编")){
            append_sql =  " table_total.empcount BETWEEN 6 and 10";
        }else if (status.equals("缺编")){
            append_sql =  " table_total.empcount < 6 ";
        }else if (status.equals("超编")){
            append_sql =  " table_total.empcount >10 ";
        }

        String sql = "select count(store_id) as count,substring_index(yearmonth,'-',-1) as work_month  from (" +
                "select count(DISTINCT human.employeeno) as empcount,t.store_id,human.yearmonth from t_topdata_human human INNER JOIN t_store t ON (t.store_id = human.storeid)  where " +
                "substring_index(human.yearmonth,'-',1) = '2018' and human.zw = '国安侠' and  t.name NOT LIKE '%测试%' AND t.name NOT LIKE '%储备%' " +
                "AND t.name NOT LIKE '%办公室%' and humanstatus !=2 AND t.flag = '0' AND ifnull(t.estate, '') = '运营中' and t.storetype = 'Y' GROUP BY human.yearmonth,t.store_id "+
                ") table_total where "+append_sql+" GROUP BY table_total.yearmonth";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryYuedianCount() {
        String sql = "select t.name,t.storeno from t_store t where t.storetype = 'Y' AND t.name NOT LIKE '%测试%' AND t.name NOT LIKE '%储备%' " +
                "AND t.name NOT LIKE '%办公室%' AND t.flag = '0' AND ifnull(t.estate, '') = '运营中' ;";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public Integer queryStoreCountHavingGAX() {
        String sql = "select count(*) from ( " +
                "SELECT count(a.employee_no) empcount,b.storeName FROM " +
                "t_humanresources a INNER JOIN (select t.store_id,name as storeName,t.city_name from t_store t where t. NAME NOT LIKE '%测试%' AND t. NAME NOT LIKE '%储备%' " +
                "AND t. NAME NOT LIKE '%办公室%' AND t.flag = '0' AND ifnull(t.estate, '') = '运营中') b " +
                "on (a.store_id = b.store_id) where a.name not like '%测试%' and  a.humanstatus !=2 and a.zw = '国安侠' group by b.storeName ) table_total";
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return Integer.parseInt(list.get(0) + "");
        }
        return null;
    }
}
