package com.cnpc.pms.communeMember.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.communeMember.dao.CommuneMemberDao;
import com.cnpc.pms.communeMember.dao.MemberInfoDao;
import com.cnpc.pms.platform.entity.MemberDataDto;
import com.cnpc.pms.utils.ImpalaUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wuxinxin
 *
 */
public class MemberInfoDaoImpl extends BaseDAOHibernate implements MemberInfoDao {


    @Override
    public List<Map<String, Object>> getMemFrom(String dd) {


        /**
         * @author wuxinxin
         * 查询社员来源
         * 2018年9月14日
         */
        String memFromSql = "select count(dum.customer_source) as cou, CASE dum.customer_source WHEN 'app' THEN 'APP' WHEN 'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' WHEN 'pad' THEN '智能终端' WHEN 'web' THEN 'WEB' WHEN 'citic' THEN '中信用户联盟' WHEN 'tv' THEN '电视' WHEN 'third_party' THEN '第三方' WHEN 'action' THEN '活动' ELSE '无' END AS customer_source  from df_user_member dum where dum.associator_expiry_date>NOW() ";
        if(!"0000".equals(dd)) {
            memFromSql = memFromSql+ "  and dum.regist_cityno='"+dd+"'";
        }
        memFromSql = memFromSql+" GROUP BY dum.customer_source";
        try {
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(memFromSql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            //List<Map<String,Object>> lst_data = ImpalaUtil.execute(memFromSql);
            return lst_data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getRegistCity(String dd) {
        /**
         * @author wuxinxin
         * 查询社员注册城市
         * 2018年9月14日
         */
        String memFromSql = "select count(dum.customer_source) as cou, dum.regist_cityno,tdc.cityname cityname from df_user_member dum,t_dist_citycode tdc where dum.associator_expiry_date>NOW() and LPAD(dum.regist_cityno, 4, '0') = tdc.cityno";
        memFromSql = memFromSql+" GROUP BY dum.regist_cityno";
        try {
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(memFromSql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            //List<Map<String,Object>> lst_data = ImpalaUtil.execute(memFromSql);
            return lst_data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getMemGmvByMon(String dd) {


        /**
         * @author wuxinxin
         * 按月查询社员GMV、订单量
         * 2018年9月14日
         */
        String memFromSql = "select count(dmot.trading_price) as cou,sum(dmot.trading_price)  as daypri,DATE_FORMAT(dmot.sign_time,'%Y-%m') as sellmon from df_mass_order_total dmot where dmot.order_tag1 like '%E%' ";
        if(!"0000".equals(dd)) {
            memFromSql = memFromSql+ "  and dmot.store_city_code='"+dd+"'";
        }
        memFromSql = memFromSql+" group by DATE_FORMAT(dmot.sign_time,'%Y-%m') order by DATE_FORMAT(dmot.sign_time,'%Y-%m')";
        try {
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(memFromSql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            //List<Map<String,Object>> lst_data = ImpalaUtil.execute(memFromSql);
            return lst_data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getNmemGmvByMon(String dd) {
        /**
         * @author wuxinxin
         * 按月查询非社员GMV、订单量
         * 2018年9月14日
         */
        String memFromSql = "select count(dmot.trading_price) as cou,sum(dmot.trading_price)  as daypri,DATE_FORMAT(dmot.sign_time,'%Y-%m') as sellmon from df_mass_order_total dmot where dmot.order_tag1 not like '%E%' ";
        if(!"0000".equals(dd)) {
            memFromSql = memFromSql+ "  and dmot.store_city_code='"+dd+"'";
        }
        memFromSql = memFromSql+" group by DATE_FORMAT(dmot.sign_time,'%Y-%m') order by DATE_FORMAT(dmot.sign_time,'%Y-%m')";
        try {
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(memFromSql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            //List<Map<String,Object>> lst_data = ImpalaUtil.execute(memFromSql);
            return lst_data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getMemOrderCount(String dd) {

        /**
         * @author wuxinxin
         * 查询非社员GMV、订单量
         * 2018年9月14日
         */
        String memFromSql = "select count(dmot.trading_price) cou,sum(dmot.trading_price)  as pri from df_mass_order_total dmot where dmot.order_tag1 like '%E%' ";
        if(!"0000".equals(dd)) {
            memFromSql = memFromSql+ "  and dmot.store_city_code='"+dd+"'";
        }
        try {
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(memFromSql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            //List<Map<String,Object>> lst_data = ImpalaUtil.execute(memFromSql);
            return lst_data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getNmemOrderCount(String dd) {
        /**
         * @author wuxinxin
         * 查询非社员GMV、订单量
         * 2018年9月14日
         */
        String memFromSql = "select count(dmot.trading_price) cou,sum(dmot.trading_price)  as daypri from df_mass_order_total dmot where dmot.order_tag1 not like '%E%'";
        if(!"0000".equals(dd)) {
            memFromSql = memFromSql+ "  and dmot.store_city_code='"+dd+"'";
        }
        try {
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(memFromSql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            //List<Map<String,Object>> lst_data = ImpalaUtil.execute(memFromSql);
            return lst_data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getMemByTimes(String dd) {
        /**
         * @author wuxinxin
         * 按月查询社员成交次数
         * 2018年9月14日
         */
        String memFromSql = "select ifnull(sum(CASE when  cc.cou=1 then 1 else 0 end),0) as cou1,ifnull(sum(CASE when  cc.cou=2 then 1 else 0 end),0) as cou2,ifnull(sum(CASE when  cc.cou>2 then 1 else 0 end),0) as cou3,cc.smon as smon  from (select count(dmot.customer_id) as cou,DATE_FORMAT(dmot.sign_time,'%Y-%m') as smon from df_mass_order_total dmot where dmot.order_tag1 like '%E%' ";
        if(!"0000".equals(dd)) {
            memFromSql = memFromSql+ "  and dmot.store_city_code='"+dd+"'";
        }
        memFromSql = memFromSql+" group by DATE_FORMAT(dmot.sign_time,'%Y-%m'),dmot.customer_id) as cc group by cc.smon ";
        try {
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(memFromSql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            //List<Map<String,Object>> lst_data = ImpalaUtil.execute(memFromSql);
            return lst_data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getNmemByTimes(String dd) {
        /**
         * @author wuxinxin
         * 按月查询非社员成交次数
         * 2018年9月14日
         */
        String memFromSql = "select ifnull(sum(CASE when  cc.cou=1 then 1 else 0 end),0) as cou1,ifnull(sum(CASE when  cc.cou=2 then 1 else 0 end),0) as cou2,ifnull(sum(CASE when  cc.cou>2 then 1 else 0 end),0) as cou3,cc.smon as smon  from (select count(dmot.customer_id) as cou,DATE_FORMAT(dmot.sign_time,'%Y-%m') as smon from df_mass_order_total dmot where dmot.order_tag1 not like '%E%' ";
        if(!"0000".equals(dd)) {
            memFromSql = memFromSql+ "  and dmot.store_city_code='"+dd+"'";
        }
        memFromSql = memFromSql+" group by DATE_FORMAT(dmot.sign_time,'%Y-%m'),dmot.customer_id) as cc group by cc.smon ";
        try {
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(memFromSql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            return lst_data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getMemNew(String dd) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getValidMem(String dd) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getBigMem(String dd) {
        /**
         * @author wuxinxin
         * 2018年7月30日
         */
        String daySumSql = "select count(1) bigcount from df_user_member dum where dum.opencard_time is null";

        try {
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(daySumSql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            return lst_data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getTryMem(String dd) {
        // TODO Auto-generated method stub
        /**
         * @author wuxinxin
         * 2018年5月18日
         */

        //查询社员总量sql
        String sql = "select count(*) as trycount from  df_user_try_member dum where dum.associator_expiry_date>now()";
        if(!"0000".equals(dd)) {
            sql = sql+ " and dum.regist_cityno='"+dd+"'";
        }
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        try{
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            return lst_data;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getNoExpeMem(String dd) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getExpeOneMem(String dd) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getExpeTwoMem(String dd) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getCountAll(String dd) {
        // TODO Auto-generated method stub
        /**
         * @author wuxinxin
         * 2018年5月18日
         */

        //查询社员总量sql
        String sql = "select count(*) as allcount from  df_user_member dum where dum.associator_expiry_date>now() and dum.opencard_time is not null";
        if(!"0000".equals(dd)) {
            sql = sql+ " and dum.regist_cityno='"+dd+"'";
        }
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        try{
            Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            return lst_data;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
