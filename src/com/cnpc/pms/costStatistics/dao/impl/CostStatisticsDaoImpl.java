package com.cnpc.pms.costStatistics.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
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
        String sql="SELECT storeNo,storeName ,uniform_charge,status,year," +
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
                " FROM `t_cost_labor` group by storeNo having status=0 ";
        if(storeNo!=null&&!"".equals(storeNo)){
            sql+=" and storeNo like '%"+storeNo+"%'";
        }

        if(storeName!=null&&!"".equals(storeName)){
            sql+=" and storeName like '%"+storeName+"%'";
        }

        if(year!=null){
            sql+=" and year="+year;
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
