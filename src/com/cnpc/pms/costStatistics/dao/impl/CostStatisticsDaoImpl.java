package com.cnpc.pms.costStatistics.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.dto.CostDto;
import com.cnpc.pms.costStatistics.entity.CostLabor;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.dao.impl
 * @Description: 成本控制
 * @Author: gbl
 * @CreateDate: 2018/8/13 11:20
 */
public class CostStatisticsDaoImpl extends BaseDAOHibernate implements CostStatisticsDao {
    @Override
    public List<Map<String, Object>> queryCostLabor(CostDto costDto) {


        String storeSql = "";

        if ("zb".equals(costDto.getRole())) {// 总部
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = " where id=" + costDto.getCityId();
            }

            String storeStr = "";
            if(costDto.getStoreNo()!=null){
                storeStr = " and t.storeno='"+costDto.getStoreNo()+"'";
            }

            storeSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t.city_name,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select * from t_dist_citycode "
                    + cityStr + ") t1" + " on t.city_name  = t1.cityname  where t.flag=0 and ifnull(t.estate,'') not like '%闭店%' "+ storeStr ;


        } else if ("cs".equals(costDto.getRole())) {// 城市
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = "  and tdc.id=" + costDto.getCityId();
            }
            String storeStr = "";
            if (costDto.getStoreNo() != null) {
                storeStr = " and t.storeno ='"+costDto.getStoreNo()+"'";
            }
            if (costDto.getUserId() != null && !"".equals(costDto.getUserId())) {
                storeSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t.city_name,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
                        + "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + costDto.getUserId()+" where tdc.status=0 "
                        + cityStr + ") t1"
                        + "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'') not like '%闭店%' " + storeStr;

            }

        }

        String sql="SELECT ts.storeno as storeNo,ts.city_name,ts.name as storeName ,ts.city_name,ts.storeType,uniform_charge,ts.estate,year,emolument,uniform_charge,uniform_amortize,accommodation,subtotal" +
                   " FROM ("+storeSql+") ts left join  (select * from `t_cost_labor` where year="+costDto.getYear()+" and month="+costDto.getMonth()+") tc on ts.storeno=tc.storeNo  where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";


        if(costDto.getStoreName()!=null&&!"".equals(costDto.getStoreName())){
            sql+=" and ts.name like '%"+costDto.getStoreName()+"%'";
        }


         List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> exportCostLabor(CostDto costDto) {


        String storeSql = "";

        if ("zb".equals(costDto.getRole())) {// 总部
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = " where id=" + costDto.getCityId();
            }

            String storeStr = "";
            if(costDto.getStoreNo()!=null){
                storeStr = " and t.storeno='"+costDto.getStoreNo()+"'";
            }

            storeSql = "select t.name,t.store_id,t.platformid,t.city_name,t.number,t.storeno,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select * from t_dist_citycode "
                    + cityStr + ") t1" + " on t.city_name  = t1.cityname  where t.flag=0 and ifnull(t.estate,'') not like '%闭店%' "+ storeStr ;


        } else if ("cs".equals(costDto.getRole())) {// 城市
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = "  and tdc.id=" + costDto.getCityId();
            }
            String storeStr = "";
            if (costDto.getStoreNo() != null) {
                storeStr = " and t.storeno ='"+costDto.getStoreNo()+"'";
            }
            if (costDto.getUserId() != null && !"".equals(costDto.getUserId())) {
                storeSql = "select t.name,t.store_id,t.city_name,t.platformid,t.number,t.storeno,t.city_name,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
                        + "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + costDto.getUserId()+" where tdc.status=0 "
                        + cityStr + ") t1"
                        + "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'') not like '%闭店%' " + storeStr;

            }

        }
        String sql="SELECT ts.storeno as storeNo,ts.name as storeName ,ts.city_name,ts.storeType,uniform_charge,ts.estate,year," +
                "sum(case when month=1 then emolument end) as emolument1,sum(case when month=1 then accommodation end) as accommodation1,uniform_amortize as uniform_amortize1,subtotal as subtotal1," +
                "sum(case when month=2 then emolument end) as emolument2,sum(case when month=2 then accommodation end) as accommodation2,uniform_amortize as uniform_amortize2,subtotal as subtotal2," +
                "sum(case when month=3 then emolument end) as emolument3,sum(case when month=3 then accommodation end) as accommodation3,uniform_amortize as uniform_amortize3,subtotal as subtotal3," +
                "sum(case when month=4 then emolument end) as emolument4,sum(case when month=4 then accommodation end) as accommodation4,uniform_amortize as uniform_amortize4,subtotal as subtotal4," +
                "sum(case when month=5 then emolument end) as emolument5,sum(case when month=5 then accommodation end) as accommodation5,uniform_amortize as uniform_amortize5,subtotal as subtotal5," +
                "sum(case when month=6 then emolument end) as emolument6,sum(case when month=6 then accommodation end) as accommodation6,uniform_amortize as uniform_amortize6,subtotal as subtotal6," +
                "sum(case when month=7 then emolument end) as emolument7,sum(case when month=7 then accommodation end) as accommodation7,uniform_amortize as uniform_amortize7,subtotal as subtotal7," +
                "sum(case when month=8 then emolument end) as emolument8,sum(case when month=8 then accommodation end) as accommodation8,uniform_amortize as uniform_amortize8,subtotal as subtotal8," +
                "sum(case when month=9 then emolument end) as emolument9,sum(case when month=9 then accommodation end) as accommodation9,uniform_amortize as uniform_amortize9,subtotal as subtotal9," +
                "sum(case when month=10 then emolument end) as emolument10,sum(case when month=10 then accommodation end) as accommodation10,uniform_amortize as uniform_amortize10,subtotal as subtotal10," +
                "sum(case when month=11 then emolument end) as emolument11,sum(case when month=1 then accommodation end) as accommodation11,uniform_amortize as uniform_amortize11,subtotal as subtotal11," +
                "sum(case when month=12 then emolument end) as emolument12,sum(case when month=12 then accommodation end) as accommodation12,uniform_amortize as uniform_amortize12,subtotal as subtotal12" +
                " FROM ("+storeSql+") ts left join  (select * from `t_cost_labor` where year="+costDto.getYear()+") tc on ts.storeno=tc.storeNo  group by storeNo having ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";




        if(costDto.getStoreName()!=null&&!"".equals(costDto.getStoreName())){
            sql+=" and ts.name like '%"+costDto.getStoreName()+"%'";
        }


        List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> selectCostLabor(String storeNo, Integer year, Integer month) {
        String sql = " select * from t_cost_labor where storeNo='"+storeNo+"' and year="+year+" and month="+month;
        List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public void updateCostLabor(CostLabor costLabor) {
        String sql = "update t_cost_labor set emolument="+costLabor.getEmolument()+",uniform_amortize ="+costLabor.getUniformAmortize()+",accommodation="+costLabor.getSubtotal();
    }

    @Override
    public List<Map<String, Object>> queryCostRent(CostDto costDto) {


        String storeSql = "";

        if ("zb".equals(costDto.getRole())) {// 总部
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = " where id=" + costDto.getCityId();
            }

            String storeStr = "";
            if(costDto.getStoreNo()!=null){
                storeStr = " and t.storeno='"+costDto.getStoreNo()+"'";
            }

            storeSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t.city_name,t1.citycode,t.address,t.estate,t.storetype from t_store t  inner join  (select * from t_dist_citycode "
                    + cityStr + ") t1" + " on t.city_name  = t1.cityname  where t.flag=0 and ifnull(t.estate,'') not like '%闭店%' "+ storeStr ;


        } else if ("cs".equals(costDto.getRole())) {// 城市
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = "  and tdc.id=" + costDto.getCityId();
            }
            String storeStr = "";
            if (costDto.getStoreNo() != null) {
                storeStr = " and t.storeno ='"+costDto.getStoreNo()+"'";
            }
            if (costDto.getUserId() != null && !"".equals(costDto.getUserId())) {
                storeSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t.city_name,t1.citycode,t.address,t.estate,t.storetype from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
                        + "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + costDto.getUserId()+" where tdc.status=0 "
                        + cityStr + ") t1"
                        + "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'') not like '%闭店%' " + storeStr;

            }

        }

        String sqlSub = "select * from t_cost_rent where status=0 ";
        if(costDto.getYear()!=null&&!"".equals(costDto.getYear())){
            sqlSub+=" and year= "+costDto.getYear();
        }

        if(costDto.getMonth()!=null&&!"".equals(costDto.getMonth())){
            sqlSub+=" and month= "+costDto.getMonth();
        }

        String sql = " select ts.storeno as store_no,ts.name as store_name,ts.city_name,ts.address as addr,tcr.property_fee_year,tcr.property_fee_month,tcr.property_deadline,tcr.rental_month,tcr.cost_month,tcrc.contractStoreNo,tcrc.rentalMonth_cal from ("+storeSql+") ts " +
                    " left join ("+sqlSub+") tcr on ts.storeno = tcr.storeNo"+
                    " LEFT JOIN"+
                    " (select IFNULL(contract_grand_total,0)/60 as rentalMonth_cal,storeNo as contractStoreNo from t_cost_rent_contract where lease_stop_date >= CONCAT('"+costDto.getrDate()+"','01')) tcrc"+
                    " on ts.storeno = tcrc.contractStoreNo where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";

        List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> exportCostRent(CostDto costDto) {

        String storeSql = "";

        if ("zb".equals(costDto.getRole())) {// 总部
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = " where id=" + costDto.getCityId();
            }

            String storeStr = "";
            if(costDto.getStoreNo()!=null){
                storeStr = " and t.storeno='"+costDto.getStoreNo()+"'";
            }

            storeSql = "select t.name,t.store_id,t.platformid,t.number,t.city_name,t.storeno,t1.citycode,t.address,t.estate,t.storetype from t_store t  inner join  (select * from t_dist_citycode "
                    + cityStr + ") t1" + " on t.city_name  = t1.cityname  where t.flag=0 and ifnull(t.estate,'') not like '%闭店%' "+ storeStr ;


        } else if ("cs".equals(costDto.getRole())) {// 城市
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = "  and tdc.id=" + costDto.getCityId();
            }
            String storeStr = "";
            if (costDto.getStoreNo() != null) {
                storeStr = " and t.storeno ='"+costDto.getStoreNo()+"'";
            }
            if (costDto.getUserId() != null && !"".equals(costDto.getUserId())) {
                storeSql = "select t.name,t.store_id,t.platformid,t.number,t.city_name,t.storeno,t.city_name,t1.citycode,t.address,t.estate,t.storetype from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
                        + "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + costDto.getUserId()+" where tdc.status=0 "
                        + cityStr + ") t1"
                        + "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'') not like '%闭店%' " + storeStr;

            }

        }

        String sqlSub = "SELECT property_fee_year,property_fee_month,property_deadline ,cost_month,rental_month,storeNo,year," +
                "SUM(case when month=1 then cost_month end) as cost_month1," +
                "SUM(case when month=2 then cost_month end) as cost_month2," +
                "SUM(case when month=3 then cost_month end) as cost_month3," +
                "SUM(case when month=4 then cost_month end) as cost_month4," +
                "SUM(case when month=5 then cost_month end) as cost_month5," +
                "SUM(case when month=6 then cost_month end) as cost_month6," +
                "SUM(case when month=7 then cost_month end) as cost_month7," +
                "SUM(case when month=8 then cost_month end) as cost_month8," +
                "SUM(case when month=9 then cost_month end) as cost_month9," +
                "SUM(case when month=10 then cost_month end) as cost_month10," +
                "SUM(case when month=11 then cost_month end) as cost_month11," +
                "SUM(case when month=12 then cost_month end) as cost_month12" +
                " FROM t_cost_rent  where year="+costDto.getYear()+" group by year ";


        String sql = " select ts.storeno as store_no,ts.city_name,ts.name as store_name,ts.city_name,ts.address as addr,tcr.*,tcrc.* from ("+storeSql+") ts " +
                " left join ("+sqlSub+") tcr on ts.storeno = tcr.storeNo"+
                " LEFT JOIN"+
                " (select storeNo,contract_grand_total,structure_acreage,first_year_rent,second_year_rent,third_year_rent,fourth_year_rent,fifth_year_rent,lease_unit_price,deposit,agency_fee,free_lease_start_date,lease_start_date,lease_stop_date from t_cost_rent_contract where lease_stop_date >= CONCAT('"+costDto.getrDate()+"','01')) tcrc"+
                " on ts.storeno = tcrc.storeNo where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";

        List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;

    }


    @Override
    public List<Map<String, Object>> queryCostRenovation(CostDto costDto) {


        String storeSql = "";

        if ("zb".equals(costDto.getRole())) {// 总部
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = " where id=" + costDto.getCityId();
            }

            String storeStr = "";
            if(costDto.getStoreNo()!=null){
                storeStr = " and t.storeno='"+costDto.getStoreNo()+"'";
            }

            storeSql = "select t.name,t.store_id,t.platformid,t.number,t.city_name,t.storeno,t1.citycode,t.address,t.estate,t.storetype from t_store t  inner join  (select * from t_dist_citycode "
                    + cityStr + ") t1" + " on t.city_name  = t1.cityname  where t.flag=0 and ifnull(t.estate,'') not like '%闭店%' "+ storeStr ;


        } else if ("cs".equals(costDto.getRole())) {// 城市
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = "  and tdc.id=" + costDto.getCityId();
            }
            String storeStr = "";
            if (costDto.getStoreNo() != null) {
                storeStr = " and t.storeno ='"+costDto.getStoreNo()+"'";
            }
            if (costDto.getUserId() != null && !"".equals(costDto.getUserId())) {
                storeSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t.city_name,t1.citycode,t.address,t.estate,t.storetype from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
                        + "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + costDto.getUserId()+" where tdc.status=0 "
                        + cityStr + ") t1"
                        + "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'') not like '%闭店%' " + storeStr;

            }

        }


        String sqlSub = "select * from t_cost_renovation where expiration_contract=0 ";

        String sql ="select ts.storeno as store_no,ts.name as store_name ,ts.city_name,tcr.decoration_company,tcr.structure_acreage,tcr.renovation_unit_price,tcr.decorate_cost,tcr.business_screen,tcr.furniture,tcr.light_box,tcr.process_manage,tcr.process_manage_surcharge,tcr.air_conditioner,tcr.air_conditioner_surcharge,tcr.whole_process_manage_surcharge,tcr.design,tcr.total,tcr.amortize_month,tcr.amortize_money,tcr.completed_date,tcr.contract_date from ("+storeSql+") ts left join ("+sqlSub+") tcr on  ts.storeno = tcr.storeNo  where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";


        List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> queryCostFixedAsset(CostDto costDto) {


        String storeSql = "";

        if ("zb".equals(costDto.getRole())) {// 总部
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = " where id=" + costDto.getCityId();
            }

            String storeStr = "";
            if(costDto.getStoreNo()!=null){
                storeStr = " and t.storeno='"+costDto.getStoreNo()+"'";
            }

            storeSql = "select t.name,t.store_id,t.platformid,t.number,t.city_name,t.storeno,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select * from t_dist_citycode "
                    + cityStr + ") t1" + " on t.city_name  = t1.cityname  where t.flag=0 and ifnull(t.estate,'') not like '%闭店%' "+ storeStr ;


        } else if ("cs".equals(costDto.getRole())) {// 城市
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = "  and tdc.id=" + costDto.getCityId();
            }
            String storeStr = "";
            if (costDto.getStoreNo() != null) {
                storeStr = " and t.storeno ='"+costDto.getStoreNo()+"'";
            }
            if (costDto.getUserId() != null && !"".equals(costDto.getUserId())) {
                storeSql = "select t.name,t.store_id,t.platformid,t.number,t.city_name,t.storeno,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
                        + "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + costDto.getUserId()+" where tdc.status=0 "
                        + cityStr + ") t1"
                        + "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'') not like '%闭店%' " + storeStr;

            }

        }
        String sqlSub = "select * from t_cost_fixed_asset";

        String sql ="select ts.storeno as store_no,ts.name as store_name ,ts.city_name,tcfa.amortize_money,tcfa.total,tcfa.aio,tcfa.mobile_phone,tcfa.iPad,tcfa.cash_register,tcfa.computer,tcfa.scanner_gun,tcfa.electronics_total,tcfa.electronics_amortize,tcfa.electric_cars,tcfa.electric_cars_amortize,tcfa.cold_chain,tcfa.safe_box,tcfa.capsule_goods_shelf,tcfa.shopping_goods_shelf,tcfa.machine_total,tcfa.machine_amortize from  ("+storeSql+") ts left join ("+sqlSub+") tcfa on  ts.storeno = tcfa.storeNo  where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";


        List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> queryCostRentContract(CostDto costDto) {
        String storeSql = "";

        if ("zb".equals(costDto.getRole())) {// 总部
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = " where id=" + costDto.getCityId();
            }

            String storeStr = "";
            if(costDto.getStoreNo()!=null){
                storeStr = " and t.storeno='"+costDto.getStoreNo()+"'";
            }

            storeSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t.city_name,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select * from t_dist_citycode "
                    + cityStr + ") t1" + " on t.city_name  = t1.cityname  where t.flag=0 and ifnull(t.estate,'') not like '%闭店%' "+ storeStr ;


        } else if ("cs".equals(costDto.getRole())) {// 城市
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = "  and tdc.id=" + costDto.getCityId();
            }
            String storeStr = "";
            if (costDto.getStoreNo() != null) {
                storeStr = " and t.storeno ='"+costDto.getStoreNo()+"'";
            }
            if (costDto.getUserId() != null && !"".equals(costDto.getUserId())) {
                storeSql = "select t.name,t.city_name,t.store_id,t.platformid,t.number,t.storeno,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
                        + "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + costDto.getUserId()+" where tdc.status=0 "
                        + cityStr + ") t1"
                        + "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'') not like '%闭店%' " + storeStr;

            }

        }

        String sql="SELECT ts.storeno as storeNo,ts.city_name,ts.name as storeName ,ts.storeType,ts.estate,tc.structure_acreage,tc.lease_unit_price,tc.deposit,tc.agency_fee,tc.free_lease_start_date,tc.lease_start_date,tc.lease_stop_date,tc.contract_grand_total," +
                " tc.first_year_rent,tc.second_year_rent,tc.third_year_rent,tc.fourth_year_rent,tc.fifth_year_rent,tc.expiration_contract " +
                " FROM ("+storeSql+") ts left join  (select * from t_cost_rent_contract where expiration_contract=0) tc on ts.storeno=tc.storeNo  where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";




        List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;


    }

    @Override
    public List<Map<String, Object>> queryCostGWE(CostDto costDto) {
        String storeSql = "";

        if ("zb".equals(costDto.getRole())) {// 总部
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = " where id=" + costDto.getCityId();
            }

            String storeStr = "";
            if(costDto.getStoreNo()!=null){
                storeStr = " and t.storeno='"+costDto.getStoreNo()+"'";
            }

            storeSql = "select t.name,t.store_id,t.platformid,t.city_name,t.number,t.storeno,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select * from t_dist_citycode "
                    + cityStr + ") t1" + " on t.city_name  = t1.cityname  where t.flag=0 and ifnull(t.estate,'') not like '%闭店%' "+ storeStr ;


        } else if ("cs".equals(costDto.getRole())) {// 城市
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = "  and tdc.id=" + costDto.getCityId();
            }
            String storeStr = "";
            if (costDto.getStoreNo() != null) {
                storeStr = " and t.storeno ='"+costDto.getStoreNo()+"'";
            }
            if (costDto.getUserId() != null && !"".equals(costDto.getUserId())) {
                storeSql = "select t.name,t.store_id,t.platformid,t.city_name,t.number,t.storeno,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
                        + "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + costDto.getUserId()+" where tdc.status=0 "
                        + cityStr + ") t1"
                        + "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'') not like '%闭店%' " + storeStr;

            }

        }

        String sql="SELECT ts.storeno as storeNo,ts.city_name,ts.name as storeName ,ts.storeType,ts.estate,tc.* " +
                " FROM ("+storeSql+") ts left join  (SELECT storeNo,gas_fee,water_fee,electricity_fee FROM t_cost_gas_water_elec where year="+costDto.getYear()+" and month="+costDto.getMonth()+") tc on ts.storeno=tc.storeNo  where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";

        List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> exportCostGWE(CostDto costDto) {
        String storeSql = "";

        if ("zb".equals(costDto.getRole())) {// 总部
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = " where id=" + costDto.getCityId();
            }

            String storeStr = "";
            if(costDto.getStoreNo()!=null){
                storeStr = " and t.storeno='"+costDto.getStoreNo()+"'";
            }

            storeSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t.city_name,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select * from t_dist_citycode "
                    + cityStr + ") t1" + " on t.city_name  = t1.cityname  where t.flag=0 and ifnull(t.estate,'') not like '%闭店%' "+ storeStr ;


        } else if ("cs".equals(costDto.getRole())) {// 城市
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = "  and tdc.id=" + costDto.getCityId();
            }
            String storeStr = "";
            if (costDto.getStoreNo() != null) {
                storeStr = " and t.storeno ='"+costDto.getStoreNo()+"'";
            }
            if (costDto.getUserId() != null && !"".equals(costDto.getUserId())) {
                storeSql = "select t.name,t.store_id,t.platformid,t.number,t.storeno,t1.citycode,t.city_name,t.estate,t.storetype from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
                        + "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + costDto.getUserId()+" where tdc.status=0 "
                        + cityStr + ") t1"
                        + "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'') not like '%闭店%' " + storeStr;

            }

        }

        String sql="SELECT ts.storeno ,ts.name as storeName ,ts.city_name,ts.storeType,ts.estate,tc.* " +
                " FROM ("+storeSql+") ts left join  (SELECT storeNo," +
                "SUM(case when month=1 then electricity_fee end) as electricity_fee1," +
                "SUM(case when month=1 then water_fee end) as water_fee1," +
                "SUM(case when month=2 then electricity_fee end) as electricity_fee2," +
                "SUM(case when month=2 then water_fee end) as water_fee2," +
                "SUM(case when month=3 then electricity_fee end) as electricity_fee3," +
                "SUM(case when month=3 then water_fee end) as water_fee3," +
                "SUM(case when month=4 then electricity_fee end) as electricity_fee4," +
                "SUM(case when month=4 then water_fee end) as water_fee4," +
                "SUM(case when month=5 then electricity_fee end) as electricity_fee5," +
                "SUM(case when month=5 then water_fee end) as water_fee5," +
                "SUM(case when month=6 then electricity_fee end) as electricity_fee6," +
                "SUM(case when month=6 then water_fee end) as water_fee6," +
                "SUM(case when month=7 then electricity_fee end) as electricity_fee7," +
                "SUM(case when month=7 then water_fee end) as water_fee7," +
                "SUM(case when month=8 then electricity_fee end) as electricity_fee8," +
                "SUM(case when month=8 then water_fee end) as water_fee8," +
                "SUM(case when month=9 then electricity_fee end) as electricity_fee9," +
                "SUM(case when month=9 then water_fee end) as water_fee9," +
                "SUM(case when month=10 then electricity_fee end) as electricity_fee10," +
                "SUM(case when month=10 then water_fee end) as water_fee10," +
                "SUM(case when month=11 then electricity_fee end) as electricity_fee11," +
                "SUM(case when month=11 then water_fee end) as water_fee11," +
                "SUM(case when month=12 then electricity_fee end) as electricity_fee12," +
                "SUM(case when month=12 then water_fee end) as water_fee12" +
                "  FROM `t_cost_gas_water_elec` where year="+costDto.getYear()+" group by storeNo) tc on ts.storeno=tc.storeNo  where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";

        List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> queryCostOperation(CostDto costDto) {

        String storeSql = "";

        if ("zb".equals(costDto.getRole())) {// 总部
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = " where id=" + costDto.getCityId();
            }

            String storeStr = "";
            if(costDto.getStoreNo()!=null){
                storeStr = " and t.storeno='"+costDto.getStoreNo()+"'";
            }

            storeSql = "select t.name,t.store_id,t.platformid,t.number,t.city_name,t.storeno,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select * from t_dist_citycode "
                    + cityStr + ") t1" + " on t.city_name  = t1.cityname  where t.flag=0 and ifnull(t.estate,'') not like '%闭店%' "+ storeStr ;


        } else if ("cs".equals(costDto.getRole())) {// 城市
            String cityStr = "";
            if (costDto.getCityId() != null) {
                cityStr = "  and tdc.id=" + costDto.getCityId();
            }
            String storeStr = "";
            if (costDto.getStoreNo() != null) {
                storeStr = " and t.storeno ='"+costDto.getStoreNo()+"'";
            }
            if (costDto.getUserId() != null && !"".equals(costDto.getUserId())) {
                storeSql = "select t.name,t.store_id,t.platformid,t.city_name,t.number,t.storeno,t1.citycode,t.estate,t.storetype from t_store t  inner join  (select tdc.id,tdc.cityname,tdc.citycode from t_dist_citycode tdc "
                        + "   INNER JOIN t_dist_city a on a.citycode = tdc.citycode and a.pk_userid=" + costDto.getUserId()+" where tdc.status=0 "
                        + cityStr + ") t1"
                        + "	on t.city_name  = t1.cityname  and t.flag=0 and ifnull(estate,'') not like '%闭店%' " + storeStr;

            }

        }

        String sql="SELECT ts.storeno as store_no,ts.city_name,ts.name as store_name ,ts.storeType,ts.estate,tc.* " +
                " FROM ("+storeSql+") ts left join  (select * from t_cost_operation where year="+costDto.getYear()+") tc on ts.storeno=tc.storeNo  where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";


        List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> queryCostStatistic(String cityName, String storeNo, Integer year, Integer month,PageInfo pageInfo) {

        String sqlSub = "";
        if(cityName!=null&&!"".equals(cityName)){
            sqlSub=" and city_name='"+cityName+"'";
        }
        String sql = "select ts.city_name as cityName,tdc.id as cityId,ts.total,l.labor,r.rent,rv.renovation,fa.fixedAsset,g.gwe,co.operation from " +
                " (select city_name,count(store_id) as total from t_store where ifnull(estate,'') not like '%闭店%' and name not like '%测试%'  and storetype!='V' "+sqlSub+" GROUP BY city_name) ts" +
                " left join " +
                " (select count(storeNo) as labor,cityName from t_cost_labor  where year="+year+" and month="+month+" group by cityName) as l on ts.city_name = l.cityName" +
                " left join " +
                " (select count(storeNo) as rent,cityName from t_cost_rent where year="+year+" and `month`="+month+" GROUP BY cityName) as r on ts.city_name = r.cityName" +
                " left join " +
                " (select count(storeNo) as renovation,cityName from t_cost_renovation GROUP BY cityName) as rv  on ts.city_name = rv.cityName" +
                " left join " +
                " (select count(storeNo) as fixedAsset,cityName from t_cost_fixed_asset GROUP BY cityName) as fa on ts.city_name = fa.cityName" +
                " left join " +
                " (select count(storeNo) as gwe,cityName from t_cost_gas_water_elec where year="+year+" and month="+month+" GROUP BY cityName) as g on ts.city_name = g.cityName" +
                " left join " +
                " (select count(storeNo) as operation,cityName from t_cost_operation where year="+year+" GROUP BY cityName) co on ts.city_name = co.cityName " +
                " left join" +
                " t_dist_citycode tdc on ts.city_name = tdc.cityname " +
                " order by ts.city_name";
        // SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

        pageInfo.setTotalRecords(query.list().size());
        // 获得查询数据
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
                .setMaxResults(pageInfo.getRecordsPerPage()).list();

        // 如果没有数据返回
        if (lst_data == null || lst_data.size() == 0) {
            return new ArrayList<Map<String, Object>>();
        }

        return (List<Map<String, Object>>) lst_data;
    }
}