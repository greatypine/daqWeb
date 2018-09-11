package com.cnpc.pms.costStatistics.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.entity.CostLabor;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

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
    public List<Map<String, Object>> queryCostLabor(String storeNo, String storeName, Integer year, Integer month) {
        String sql="SELECT ts.storeno as storeNo,ts.name as storeName ,ts.storeType,uniform_charge,ts.estate,year," +
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
                " FROM t_store ts left join  (select * from `t_cost_labor` where year="+year+") tc on ts.storeno=tc.storeNo  group by storeNo having ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";
        if(storeNo!=null&&!"".equals(storeNo)){
            sql+=" and ts.storeno like '%"+storeNo+"%'";
        }

        if(storeName!=null&&!"".equals(storeName)){
            sql+=" and ts.name like '%"+storeName+"%'";
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
    public List<Map<String, Object>> queryCostRent(String storeNo, String storeName, Integer year) {

        String sqlSub = "select * from t_cost_rent ";
        if(year!=null&&!"".equals(year)){
            sqlSub+=" where year= "+year;
        }
        String sql ="select ts.storeno as store_no,ts.name as store_name ,ts.address as addr,(ifnull(tcr.contract_grand_total,0)/60) as rent_monthly,property_fee,(ifnull(property_fee,0)+(ifnull(tcr.contract_grand_total,0)/60)) as cost_monthly, tcr.contract_grand_total,tcr.structure_acreage,tcr.year,tcr.first_year_rent,tcr.second_year_rent,tcr.third_year_rent,tcr.fourth_year_rent,tcr.fifth_year_rent,tcr.lease_unit_price,tcr.deposit,tcr.agency_fee,tcr.property_fee,tcr.property_deadline,tcr.free_lease_start_date,tcr.lease_start_date,tcr.lease_stop_date from  t_store ts left join ("+sqlSub+") tcr on  ts.storeno = tcr.storeNo  where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";
        if(storeNo!=null&&!"".equals(storeNo)){
            sql+=" and ts.storeno like '%"+storeNo+"%'";
        }

        if(storeName!=null&&!"".equals(storeName)){
            sql+=" and ts.name like '%"+storeName+"%'";
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
    public List<Map<String, Object>> queryCostRenovation(String storeNo, String storeName) {
        String sqlSub = "select * from t_cost_renovation";

        String sql ="select ts.storeno as store_no,ts.name as store_name ,tcr.decoration_company,tcr.structure_acreage,tcr.renovation_unit_price,tcr.decorate_cost,tcr.business_screen,tcr.furniture,tcr.light_box,tcr.process_manage,tcr.process_manage_surcharge,tcr.air_conditioner,tcr.air_conditioner_surcharge,tcr.whole_process_manage_surcharge,tcr.design,tcr.total,tcr.amortize_month,tcr.amortize_money,tcr.completed_date,tcr.contract_date from  t_store ts left join ("+sqlSub+") tcr on  ts.storeno = tcr.storeNo  where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";
        if(storeNo!=null&&!"".equals(storeNo)){
            sql+=" and ts.storeno like '%"+storeNo+"%'";
        }

        if(storeName!=null&&!"".equals(storeName)){
            sql+=" and ts.name like '%"+storeName+"%'";
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
    public List<Map<String, Object>> queryCostFixedAsset(String storeNo, String storeName) {
        String sqlSub = "select * from t_cost_fixed_asset";

        String sql ="select ts.storeno as store_no,ts.name as store_name ,tcfa.amortize_money,tcfa.total,tcfa.aio,tcfa.mobile_phone,tcfa.iPad,tcfa.cash_register,tcfa.computer,tcfa.scanner_gun,tcfa.electronics_toal,tcfa.electronics_amortize,tcfa.electric_cars,tcfa.electric_ars_amortize,tcfa.cold_chain,tcfa.safe_box,tcfa.capsule_goods_shelf,tcfa.shopping_goods_shelf,tcfa.machine_total,tcfa.machine_amortize from  t_store ts left join ("+sqlSub+") tcfa on  ts.storeno = tcfa.storeNo  where ifnull(ts.estate,'') not like '%闭店%' and ts.name not like '%测试%'  and ts.storetype!='V'";
        if(storeNo!=null&&!"".equals(storeNo)){
            sql+=" and ts.storeno like '%"+storeNo+"%'";
        }

        if(storeName!=null&&!"".equals(storeName)){
            sql+=" and ts.name like '%"+storeName+"%'";
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
}
