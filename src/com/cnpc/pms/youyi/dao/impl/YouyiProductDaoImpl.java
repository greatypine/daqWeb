package com.cnpc.pms.youyi.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.utils.ImpalaUtil;
import com.cnpc.pms.youyi.dao.YouyiProductDao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author wuxinxin
 *
 */
public class YouyiProductDaoImpl extends BaseDAOHibernate implements YouyiProductDao {
    Calendar date = Calendar.getInstance();
    String beginDate = String.valueOf(date.get(Calendar.YEAR))+"-01";
    @Override
    public List<Map<String, Object>> getYouyiSku(String dd) {
        //查询总的商品数  在线商品数  动销商品数   新上商品数

        String sql = " SELECT sum(online .sku_online) AS sku_online, sum(onselling.sku_onselling) AS sku_onselling FROM ( SELECT count(DISTINCT sku_id) AS sku_onselling FROM bb_sku WHERE sku_onselling = 'yes' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018' AND branch_id IS NOT NULL AND city_code IS NOT NULL AND department_id IS NOT NULL ";


        if(!"0000".equals(dd)) {
            sql = sql+ "   and city_code = '"+dd+"'";
        }
        sql = sql+" ) onselling LEFT OUTER JOIN ( SELECT count(DISTINCT sku_id) AS sku_online FROM bb_sku WHERE sku_online = 'yes' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018' AND branch_id IS NOT NULL AND city_code IS NOT NULL AND department_id IS NOT NULL ";

        if(!"0000".equals(dd)) {
            sql = sql+ "   and city_code = '"+dd+"'";
        }
        sql = sql+ " ) online ON 1 = 1";
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getonlineSku(String dd) {
        //查询在线商品数
        String sql = "SELECT count(DISTINCT sku_id) AS sku_online FROM bb_sku WHERE sku_online = 'yes' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018' AND branch_id IS NOT NULL AND city_code IS NOT NULL AND department_id IS NOT NULL" ;
        if(!"0000".equals(dd)) {
            sql = sql+ "   and city_code = '"+dd+"'";
        }
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getMovingByMonth(String dd) {
        //查询动销数、在售商品数/每月
        String sql = "select bb.cumulative_sku, onselling.sku_onselling, bb.last_modify_time" +
                " from ( SELECT count(DISTINCT sku_id) AS cumulative_sku, strleft (last_modify_time, 7) AS last_modify_time" +
                " FROM bb_sku WHERE ( strleft (last_modify_time, 7) >= '"+beginDate+"' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018'" +
                " AND branch_id IS NOT NULL AND city_code IS NOT NULL AND department_id IS NOT NULL";


        if(!"0000".equals(dd)) {
            sql = sql+ "   and city_code = '"+dd+"'";
        }
        sql = sql+" ) GROUP BY last_modify_time ) as bb LEFT OUTER JOIN ( SELECT strleft (last_modify_time, 7) AS last_modify_time, count(DISTINCT sku_id) AS sku_onselling FROM bb_sku WHERE strleft (last_modify_time, 7) >= '"+beginDate+"' AND sku_onselling = 'yes' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018' AND branch_id IS NOT NULL AND city_code IS NOT NULL AND department_id IS NOT NULL GROUP BY last_modify_time ) onselling on onselling.last_modify_time = bb.last_modify_time  order by bb.last_modify_time";

        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getCityMoving(String dd) {
        //查询城市动销数
        String sql = "select bb.cumulative_sku as cumulative_sku, onselling.sku_onselling as sku_onselling, bb.city_code, t2.CODE_DESC as cityname from ( SELECT count(DISTINCT sku_id) AS cumulative_sku, city_code FROM bb_sku WHERE ( strleft (last_modify_time, 7) >= '"+beginDate+"' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018' AND branch_id IS NOT NULL AND city_code IS NOT NULL AND city_code != '' AND department_id IS NOT NULL ) GROUP BY city_code ) as bb LEFT OUTER JOIN ( SELECT city_code, count(DISTINCT sku_id) AS sku_onselling FROM bb_sku WHERE sku_onselling = 'yes' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018' AND branch_id IS NOT NULL AND city_code IS NOT NULL AND city_code != '' AND department_id IS NOT NULL GROUP BY city_code ) onselling on onselling.city_code = bb.city_code LEFT OUTER JOIN ( SELECT code AS CODE_ID, name AS CODE_DESC FROM t_sys_area ) t2 ON t2.CODE_ID = bb.city_code order by bb.city_code";
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getMovingAll(String dd) {
        //动销商品数

        String sql = "SELECT count(DISTINCT sku_id) AS sku_onselling FROM bb_sku WHERE sku_onselling = 'yes' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018' AND branch_id IS NOT NULL AND city_code IS NOT NULL AND department_id IS NOT NULL" ;
        if(!"0000".equals(dd)) {
            sql = sql+ "   and city_code = '"+dd+"'";
        }
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getChannelMoving(String dd) {
        //查询频道动销数、在售商品数/每月
        String sql = "select bb.cumulative_sku as cumulative_sku, onselling.sku_onselling as sku_onselling, bb.department_id, bb.last_modify_time as last_modify_time,t3.CODE_DESC as cname" +
                " from ( SELECT count(DISTINCT sku_id) AS cumulative_sku, department_id, strleft (last_modify_time, 7) AS last_modify_time" +
                " FROM bb_sku WHERE ( strleft (last_modify_time, 7) >= '"+beginDate+"' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018'" +
                " AND branch_id IS NOT NULL AND city_code IS NOT NULL AND city_code != '' AND department_id IS NOT NULL";


        if(!"0000".equals(dd)) {
            sql = sql+ "   and city_code = '"+dd+"'";
        }
        sql = sql+" ) GROUP BY department_id, last_modify_time ) as bb LEFT OUTER JOIN ( SELECT department_id, count(DISTINCT sku_id) AS sku_onselling FROM bb_sku WHERE sku_onselling = 'yes' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018' AND branch_id IS NOT NULL AND city_code IS NOT NULL AND city_code != '' AND department_id IS NOT NULL GROUP BY department_id ) onselling on onselling.department_id = bb.department_id  LEFT OUTER JOIN ( SELECT id AS CODE_ID, name AS CODE_DESC FROM d_chanel ) t3 ON t3.CODE_ID = bb.department_id order by bb.last_modify_time, bb.department_id";

        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getChannelSell(String dd) {
        //查询频道商品销量
        String sql = "select onselling.department_id, onselling.sku_onselling as sku_onselling, t3.CODE_DESC as cname from ( SELECT department_id, count(sku_id) AS sku_onselling" +
                " FROM bb_sku WHERE strleft(last_modify_time,7) >= '"+beginDate+"' and sku_onselling = 'yes' AND branch_id = '8ac28b935fed0bc8015fed4c76f60018'" +
                " AND branch_id IS NOT NULL AND city_code IS NOT NULL AND city_code != '' AND department_id IS NOT NULL";


        if(!"0000".equals(dd)) {
            sql = sql+ "   and city_code = '"+dd+"'";
        }
        sql = sql+" GROUP BY department_id ) onselling LEFT OUTER JOIN ( SELECT id AS CODE_ID, name AS CODE_DESC FROM d_chanel ) t3 ON t3.CODE_ID = onselling.department_id";

        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getSkuTop(String dd) {
        //查询商品销量top10
        String sql = "select distinct bs.sku_id, bs.sku_onselling as sku_onselling, t3.content_name as content_name, t3.role_name_department, t3.role_name_chanel as role_name_chanel" +
                " from ( select count(sku_id) as sku_onselling, sku_id from bb_sku where strleft(last_modify_time,7) >= '"+beginDate+"' and  sku_onselling = 'yes'" +
                " AND branch_id = '8ac28b935fed0bc8015fed4c76f60018' AND branch_id IS NOT NULL AND city_code IS NOT NULL" ;

        if(!"0000".equals(dd)) {
            sql = sql+ "   and city_code = '"+dd+"'";
        }
        sql = sql+" group by sku_id order by sku_onselling desc limit 10 ) bs LEFT JOIN t_eshop_product t3 ON bs.sku_id = t3.pro_id order by bs.sku_onselling desc";

        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getYouyiGmv(String dd) {
        //查询商品销量top10
        String sql = "select count(1) as ordercou,sum(place_price) as ordergmv from bb_t_order where strleft (last_modify_time, 7) >= '"+beginDate+"' and branch_id='8ac28b935fed0bc8015fed4c76f60018'" ;

        if(!"0000".equals(dd)) {
            sql = sql+ "   and city_code = '"+dd+"'";
        }
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }
}
