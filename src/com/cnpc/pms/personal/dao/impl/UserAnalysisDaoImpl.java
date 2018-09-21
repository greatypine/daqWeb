package com.cnpc.pms.personal.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.personal.dao.UserAnalysisDao;
import com.cnpc.pms.utils.ImpalaUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Function:用户总览Dao实现
 * @Auther: chenchuang
 * @Date: 2018/9/14 16:17
 */
public class UserAnalysisDaoImpl extends BaseDAOHibernate implements UserAnalysisDao {

    @Override
    public Map<String, Object> queryTotalCustomer(){
        String sql = "SELECT count(1) as customer_num FROM gemini.t_customer tc";
        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
        if(list !=null && list.size()>0 ){
            return list.get(0);
        }
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> queryPayUser(){
        String sql = "SELECT SUM(CASE WHEN order_count = 1 THEN 1 ELSE 0 END ) once_customer, SUM(CASE WHEN order_count >= 2 THEN 1 ELSE 0 END ) more_customer FROM df_user_profile";
        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
        if(list !=null && list.size()>0 ){
            return list.get(0);
        }
        return new HashMap<>();
    }

    @Override
    public List<Map<String, Object>> queryUsertag(){
        String sql = "SELECT count(1) AS usertag_num, usertag FROM df_userprofile_tag GROUP BY usertag";
        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> querySourceScatter(){
        String sql = "SELECT count(1) AS source_num, ifnull(customer_source,'无') AS customer_source FROM df_user_profile dup "
                + "RIGHT join df_userprofile_tag dut on dup.customer_id=dut.customer_id where (dut.usertag='R3' or dut.usertag='R4') GROUP BY customer_source";
        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> queryCityScatter(){
        String sql = "SELECT count(DISTINCT dus.customer_id) AS city_user_num, city_name,cityno FROM df_user_store dus "
                + "right join df_userprofile_tag dut on dus.customer_id=dut.customer_id where (dut.usertag='R3' or dut.usertag='R4') and cityno IS NOT NULL GROUP BY cityno,city_name";
        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> queryUnitPrice(){
        String sql = "SELECT sum(case when count_order=1 then trading_price else 0 end) once_unit_price,sum(case when count_order=1 then count_order else 0 end) once_customer,"
                + "sum(case when count_order=2 then trading_price else 0 end)  twice_unit_price,sum(case when count_order=2 then count_order else 0 end) twice_customer,"
                + "sum(case when count_order>=3 then trading_price else 0 end)  more_unit_price,sum(case when count_order>=3 then count_order else 0 end) more_customer,"
                + "month_data FROM (select sum(trading_price) as trading_price,count(1) as count_order,"
                + "customer_id,strleft(sign_time, 7) AS month_data from df_mass_order_total WHERE 1=1 ";
        sql = sql + " GROUP BY month_data,customer_id ) aa GROUP BY month_data ORDER BY month_data ASC";
        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
        return list;
    }

    @Override
    public Map<String, Object> queryUserActivity(String month){
        String sql = "SELECT sum(CASE WHEN order_count = 1 THEN 1 END ) AS once_count, sum(CASE WHEN order_count >= 2 THEN 1 END ) AS more_count FROM (" +
                "SELECT count(1) AS order_count FROM df_mass_order_total WHERE unix_timestamp(sign_time) >= unix_timestamp(now() - INTERVAL "+month+" MONTHS) GROUP BY customer_id ) aa";
        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
        if(list !=null && list.size()>0 ){
            return list.get(0);
        }
        return new HashMap<>();
    }

    @Override
    public List<Map<String, Object>> queryNewCustomer(){
        String sql = "SELECT count(DISTINCT customer_id) as new_customer,strleft(sign_time, 7) AS month_data FROM df_mass_order_total WHERE customer_isnew_flag!='-1' GROUP BY  month_data  ORDER BY month_data ASC";
        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
        return list;
    }

}
