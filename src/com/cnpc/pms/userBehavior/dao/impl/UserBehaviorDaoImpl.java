package com.cnpc.pms.userBehavior.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.userBehavior.dao.UserBehaviorDao;
import com.cnpc.pms.utils.ImpalaUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author shijunhui
 *
 */
public class UserBehaviorDaoImpl extends BaseDAOHibernate implements UserBehaviorDao {
    Calendar date = Calendar.getInstance();
    String beginDate = String.valueOf(date.get(Calendar.YEAR))+"-01";
    @Override
    public List<Map<String, Object>> getUserBehaviorList(String startTime , String endTime) {
        //查询总的访问用户数  加购用户数  下单用户数   成交用户数

//        String sql = " select '所有用户数' as type,count(1) as num from gemini.t_customer union (select '访问用户数' as type,count(distinct customer_id)  as num from  datacube_kudu.log_final lf inner  join gemini.t_store  ts on lf.store_id = ts.id and ts.name not like '%测试%' where 1=1 ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " and lf.simple_date >= '"+startTime+"' ";
//            sql = sql + " and lf.simple_date < '"+endTime+"' ";
//        }else{
//            sql = sql + " and lf.simple_date >= "+startTime+" ";
//        }
//        sql = sql + " and lf.store_id is not null and lf.customer_id is not null and lf.city_code is not null) ";
//        sql = sql + " union(select '加购用户数' as type,count(distinct customer_id)  as num from  datacube_kudu.log_final lf inner  join gemini.t_store  ts on lf.store_id = ts.id and ts.name not like '%测试%' where 1=1 ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " and lf.simple_date >= '"+startTime+"' ";
//            sql = sql + " and lf.simple_date < '"+endTime+"' ";
//        }else{
//            sql = sql + " and lf.simple_date >= "+startTime+" ";
//        }
//        sql = sql + " and lf.customer_id is not null and lf.store_id is not null and lf.city_code is not null and lf.behavior_name in ";
//        sql = sql + " ('填写订单','获取用户选中购物车分组信息','预约服务时间','获取发货单详情','购物车全选','新立即购买','新团购下单','再来一单','积分商品下单页','可拆分商品预约(非团购)','填写订单','购物车列表','下单','选择全部','添加购物车','提交订单','改变分组状态','购物车删除','合并支付下单页','改变用户选中购物车分组状态')) ";
//        sql = sql + " union(select '下单用户数' as type,count(distinct customer_id)  as num from gemini.t_order tor inner  join gemini.t_store  ts on tor.store_id = ts.id and ts.name not like '%测试%' where store_id is not null ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " and tor.create_time > '"+startTime+"' ";
//            sql = sql + " and tor.create_time < '"+endTime+"' ";
//        }else{
//            sql = sql + " and tor.create_time >= "+startTime+" ";
//        }
//        sql = sql + " )union (select '成交用户数' as type, count(distinct customer_id)  as num from gemini.t_order tor inner  join gemini.t_store  ts on tor.store_id = ts.id and ts.name not like '%测试%' where ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " tor.create_time > '"+startTime+"' ";
//            sql = sql + " and tor.create_time < '"+endTime+"' ";
//        }else{
//            sql = sql + " tor.create_time >= "+startTime+" ";
//        }
//        sql = sql + " and tor.sign_time is not null and store_id is not null) ";

        String sql =  "  select ";
        sql = sql + "   '所有用户数' as type,";
        sql = sql + "   count(1) as num";
        sql = sql + "  from gemini.t_customer  ";
        sql = sql + " ";
        sql = sql + " union ";
        sql = sql + " (";
        sql = sql + "  select ";
        sql = sql + "     '访问用户数' as type,";
        sql = sql + " 	count(distinct customer_id)  as num";
        sql = sql + "  from  datacube_kudu.log_final lf";
        sql = sql + "  inner  join gemini.t_store  ts on lf.store_id = ts.id and ts.name not like '%测试%'";
        sql = sql + "  where 1=1";
                if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and lf.simple_date >= "+startTime+" ";
            sql = sql + " and lf.simple_date < "+endTime+" ";
        }else{
            sql = sql + " and lf.simple_date >= "+startTime+" ";
        }
        sql = sql + " 	 and lf.store_id is not null ";
        sql = sql + " 	 and lf.customer_id is not null ";
        sql = sql + " 	 and lf.city_code is not null";
        sql = sql + "      and lf.customer_id not in (";
        sql = sql + " 			'fakecustomerformicromarket000002',";
        sql = sql + " 			'fakecustomerformicromarket000001',";
        sql = sql + " 			'fakecustomerforexpress0000000001'";
        sql = sql + " 		  )	 ";
        sql = sql + " )";
        sql = sql + " union";
        sql = sql + " (";
        sql = sql + " select ";
        sql = sql + " 	'加购用户数' as type,";
        sql = sql + " 	count(distinct customer_id)  as num";
        sql = sql + "  from  datacube_kudu.log_final lf";
        sql = sql + "   inner  join gemini.t_store  ts on lf.store_id = ts.id and ts.name not like '%测试%'";
        sql = sql + "  where 1=1";
                if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and lf.simple_date >= "+startTime+" ";
            sql = sql + " and lf.simple_date < "+endTime+" ";
        }else{
            sql = sql + " and lf.simple_date >= "+startTime+" ";
        }
        sql = sql + "  and lf.customer_id is not null ";
        sql = sql + "  and lf.store_id is not null ";
        sql = sql + "  and lf.city_code is not null";
        sql = sql + "  and lf.customer_id not in (";
        sql = sql + " 		'fakecustomerformicromarket000002',";
        sql = sql + " 		'fakecustomerformicromarket000001',";
        sql = sql + " 		'fakecustomerforexpress0000000001'";
        sql = sql + " 	  )	  ";
        sql = sql + "  and lf.behavior_name in";
        sql = sql + "   ( ";
        sql = sql + " 	select  behavior_name from datacube_kudu.d_action_level where level = 9";
        sql = sql + " 	)";
        sql = sql + " )";
        sql = sql + " union";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 	'下单用户数' as type,";
        sql = sql + " 	count(distinct customer_id)  as num";
        sql = sql + " 	 from gemini.t_order tor ";
        sql = sql + " 	  inner  join gemini.t_store  ts on tor.store_id = ts.id and ts.name not like '%测试%' where store_id is not null";
        if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and tor.create_time > "+startTime+" ";
            sql = sql + " and tor.create_time < "+endTime+" ";
        }else{
            sql = sql + " and tor.create_time >= "+startTime+" ";
        }
        sql = sql + " 	and tor.customer_id not in (";
        sql = sql + " 		'fakecustomerformicromarket000002',";
        sql = sql + " 		'fakecustomerformicromarket000001',";
        sql = sql + " 		'fakecustomerforexpress0000000001'";
        sql = sql + " 	  )	  	";
        sql = sql + " )";
        sql = sql + " union ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 	'成交用户数' as type,";
        sql = sql + " 	 count(distinct customer_id)  as num ";
        sql = sql + " 	 from gemini.t_order tor ";
        sql = sql + " 	 inner  join gemini.t_store  ts on tor.store_id = ts.id and ts.name not like '%测试%' where tor.sign_time is not null ";
        if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and tor.create_time > "+startTime+" ";
            sql = sql + " and tor.create_time < "+endTime+" ";
        }else{
            sql = sql + " and tor.create_time >= "+startTime+" ";
        }
        sql = sql + " 	and store_id is not null";
        sql = sql + " 	and tor.customer_id not in (";
        sql = sql + " 		'fakecustomerformicromarket000002',";
        sql = sql + " 		'fakecustomerformicromarket000001',";
        sql = sql + " 		'fakecustomerforexpress0000000001'";
        sql = sql + " 	  )	 	";
        sql = sql + " )";
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getByCityList(String startTime,String endTime) {
        //查询城市用户行为
//        String sql = " select tsa.name as city_name,isnull( T_2.visit_num , 0) as visit_num,isnull(T_3.add_num , 0)  as add_num,isnull( T_4.order_num, 0) as order_num,isnull( T_5.sign_num, 0) as sign_num from " ;
//        sql = sql + " (select T_1.city_code , count(1) as visit_num  from (select max(lf.city_code) as city_code from datacube_kudu.log_final lf  where 1=1 ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " and lf.simple_date >= '"+startTime+"' ";
//            sql = sql + " and lf.simple_date < '"+endTime+"' ";
//        }else{
//            sql = sql + " and lf.simple_date >= "+startTime+" ";
//        }
//        sql = sql + " and lf.customer_id is not null and lf.city_code is not null and lf.store_id is not null group by lf.customer_id) T_1 group by T_1.city_code) T_2 ";
//        sql = sql + " left join (select T_1.city_code , count(1) as add_num from (select max(lf.city_code) as city_code from datacube_kudu.log_final lf where 1=1 ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " and lf.simple_date >= '"+startTime+"' ";
//            sql = sql + " and lf.simple_date < '"+endTime+"' ";
//        }else{
//            sql = sql + " and lf.simple_date >= "+startTime+" ";
//        }
//        sql = sql + " and lf.customer_id is not null  and lf.city_code is not null and lf.store_id is not null   and lf.behavior_name in ";
//        sql = sql + " ('填写订单','获取用户选中购物车分组信息','预约服务时间','获取发货单详情','购物车全选','新立即购买','新团购下单','再来一单','积分商品下单页','可拆分商品预约(非团购)','填写订单','购物车列表','下单','选择全部','添加购物车','提交订单','改变分组状态','购物车删除','合并支付下单页','改变用户选中购物车分组状态') group by lf.customer_id) T_1 group by T_1.city_code) T_3 on  T_2.city_code= T_3.city_code ";
//        sql = sql + " left join (select T_1.city_code , count(1) as order_num from (select max(ts.city_code) as city_code from  gemini.t_order tor  left join gemini.t_store ts on tor.store_id = ts.id  where 1=1 ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " and tor.create_time > '"+startTime+"' ";
//            sql = sql + " and tor.create_time < '"+endTime+"' ";
//        }else{
//            sql = sql + " and tor.create_time >= "+startTime+" ";
//        }
//        sql = sql + " and tor.store_id is not null group by tor.customer_id) T_1 group by T_1.city_code)T_4 on T_3.city_code = T_4.city_code ";
//        sql = sql + " left join (select T_1.city_code ,count(1) as sign_num from (select max(ts.city_code) as city_code from gemini.t_order tor left join gemini.t_store ts on tor.store_id = ts.id where 1=1 ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " and tor.create_time > '"+startTime+"' ";
//            sql = sql + " and tor.create_time < '"+endTime+"' ";
//        }else{
//            sql = sql + " and tor.create_time >= "+startTime+" ";
//        }
//        sql = sql + " and tor.store_id is not null and tor.sign_time is not null group by tor.customer_id) T_1 ";
//        sql = sql + " group by T_1.city_code ) T_5 on T_4.city_code = T_5.city_code left join  gemini.t_sys_area tsa on T_2.city_code =  tsa.code order by T_2.city_code,T_2.visit_num ";
        String sql  =  " select ";
        sql = sql + " tsa.name as city_name,";
        sql = sql + " isnull( T_2.visit_num , 0) as visit_num,";
        sql = sql + " isnull( T_3.add_num , 0)  as add_num,";
        sql = sql + " isnull( T_4.order_num, 0) as order_num,";
        sql = sql + " isnull( T_5.sign_num, 0) as sign_num";
        sql = sql + " from ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 		T_1.city_code , ";
        sql = sql + " 		count(1) as visit_num  ";
        sql = sql + " 	from ";
        sql = sql + " 	(";
        sql = sql + " 	  select";
        sql = sql + " 		max(lf.city_code) as city_code";
        sql = sql + " 	  from datacube_kudu.log_final lf";
        sql = sql + " 	  inner join gemini.t_store ts on lf.store_id = ts.id and ts.name not like '%测试%'";
        sql = sql + " 		  where 1=1";
        if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and lf.simple_date >= "+startTime+" ";
            sql = sql + " and lf.simple_date < "+endTime+" ";
        }else{
            sql = sql + " and lf.simple_date >= "+startTime+" ";
        }
        sql = sql + " 		  and lf.customer_id is not null ";
        sql = sql + " 		  and lf.city_code is not null";
        sql = sql + " 		  and lf.store_id is not null ";
        sql = sql + " 		  and lf.customer_id not in (";
        sql = sql + " 			'fakecustomerformicromarket000002',";
        sql = sql + " 			'fakecustomerformicromarket000001',";
        sql = sql + " 			'fakecustomerforexpress0000000001'";
        sql = sql + " 		  )		  ";
        sql = sql + " 	  group by lf.customer_id ";
        sql = sql + " 	) T_1";
        sql = sql + " 	group by T_1.city_code ";
        sql = sql + " 	";
        sql = sql + " ) T_2";
        sql = sql + " left join ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 		T_1.city_code , ";
        sql = sql + " 		count(1) as add_num  ";
        sql = sql + " 	from ";
        sql = sql + " 	(";
        sql = sql + " 	  select";
        sql = sql + " 		max(lf.city_code) as city_code";
        sql = sql + " 	  from datacube_kudu.log_final lf";
        sql = sql + " 	  inner join gemini.t_store ts on lf.store_id = ts.id and ts.name not like '%测试%'";
        sql = sql + " 		  where 1=1";
        if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and lf.simple_date >= "+startTime+" ";
            sql = sql + " and lf.simple_date < "+endTime+" ";
        }else{
            sql = sql + " and lf.simple_date >= "+startTime+" ";
        }
        sql = sql + " 		  and lf.customer_id is not null ";
        sql = sql + " 		  and lf.city_code is not null";
        sql = sql + " 		  and lf.store_id is not null";
        sql = sql + " 		  and lf.customer_id not in (";
        sql = sql + " 			'fakecustomerformicromarket000002',";
        sql = sql + " 			'fakecustomerformicromarket000001',";
        sql = sql + " 			'fakecustomerforexpress0000000001'";
        sql = sql + " 		  )			  ";
        sql = sql + " 		  and lf.behavior_name in ";
        sql = sql + " 				 ( ";
        sql = sql + " 					select  behavior_name from datacube_kudu.d_action_level where level = 9";
        sql = sql + " 				)	  ";
        sql = sql + " 	  group by lf.customer_id ";
        sql = sql + " 	) T_1";
        sql = sql + " 	group by T_1.city_code ";
        sql = sql + " 	";
        sql = sql + " ) T_3 on  T_2.city_code= T_3.city_code";
        sql = sql + " left join ";
        sql = sql + " (";
        sql = sql + "     select";
        sql = sql + " 		 T_1.city_code ,";
        sql = sql + " 		 count(1) as order_num ";
        sql = sql + " 	 from ";
        sql = sql + " 	 (";
        sql = sql + " 		 select ";
        sql = sql + " 			max(ts.city_code) as city_code";
        sql = sql + " 		 from ";
        sql = sql + " 		 gemini.t_order tor ";
        sql = sql + " 		 ";
        sql = sql + " 		 left join gemini.t_store ts on tor.store_id = ts.id  and ts.name not like '%测试%'";
        sql = sql + " 		 where 1=1 ";
        if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and tor.create_time > "+startTime+" ";
            sql = sql + " and tor.create_time < "+endTime+" ";
        }else{
            sql = sql + " and tor.create_time >= "+startTime+" ";
        }
        sql = sql + " 		 and tor.store_id is not null";
        sql = sql + " 		 and tor.customer_id not in (";
        sql = sql + " 			'fakecustomerformicromarket000002',";
        sql = sql + " 			'fakecustomerformicromarket000001',";
        sql = sql + " 			'fakecustomerforexpress0000000001'";
        sql = sql + " 		  )	";
        sql = sql + " 		 group by tor.customer_id ";
        sql = sql + " 	  ) T_1";
        sql = sql + "  group by T_1.city_code ";
        sql = sql + " 	";
        sql = sql + " )T_4 on T_3.city_code = T_4.city_code";
        sql = sql + " left join ";
        sql = sql + " (";
        sql = sql + " 	   select";
        sql = sql + " 		 T_1.city_code ,";
        sql = sql + " 		 count(1) as sign_num ";
        sql = sql + " 	 from ";
        sql = sql + " 	 (";
        sql = sql + " 		 select ";
        sql = sql + " 			max(ts.city_code) as city_code";
        sql = sql + " 		 from ";
        sql = sql + " 		 gemini.t_order tor ";
        sql = sql + " 		 left join gemini.t_store ts on tor.store_id = ts.id  and ts.name not like '%测试%'";
        sql = sql + " 		 where 1=1 ";
        if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and tor.create_time > "+startTime+" ";
            sql = sql + " and tor.create_time < "+endTime+" ";
        }else{
            sql = sql + " and tor.create_time >= "+startTime+" ";
        }
        sql = sql + " 		 and tor.store_id is not null ";
        sql = sql + " 		 and tor.sign_time is not null";
        sql = sql + " 		 and tor.customer_id not in (";
        sql = sql + " 			'fakecustomerformicromarket000002',";
        sql = sql + " 			'fakecustomerformicromarket000001',";
        sql = sql + " 			'fakecustomerforexpress0000000001'";
        sql = sql + " 		  )		 ";
        sql = sql + " 		 group by tor.customer_id ";
        sql = sql + " 	  ) T_1";
        sql = sql + "  group by T_1.city_code ";
        sql = sql + " ) T_5 on T_4.city_code = T_5.city_code";
        sql = sql + " ";
        sql = sql + " left join  gemini.t_sys_area tsa on T_2.city_code =  tsa.code";
        sql = sql + " order by   T_2.city_code,T_2.visit_num";
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }


    @Override
    public List<Map<String, Object>> getByChannelList(String startTime,String endTime) {
        //查询频道用户行为
//        String sql = "select dc.name as channel_name,isnull( T_2.visit_num , 0) as visit_num,isnull( T_4.order_num, 0) as order_num,isnull( T_5.sign_num, 0) as sign_num from ";
//        sql = sql + " (select T_1.channel_id,count(1) as visit_num from (select max(lf.channel_id) as channel_id from datacube_kudu.log_final lf where 1=1 ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " and lf.simple_date >= '"+startTime+"' ";
//            sql = sql + " and lf.simple_date < '"+endTime+"' ";
//        }else{
//            sql = sql + " and lf.simple_date >= "+startTime+" ";
//        }
//        sql = sql + " and lf.customer_id is not null and lf.channel_id is not null and lf.store_id is not null group by lf.customer_id) T_1 group by T_1.channel_id) T_2 ";
//        sql = sql + " left join (select T_1.channel_id,count(1) as order_num from (select max(te.channel_id) as channel_id from gemini.t_order tor left join gemini.t_eshop te on tor.eshop_id = te.id where 1=1 ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " and tor.create_time > '"+startTime+"' ";
//            sql = sql + " and tor.create_time < '"+endTime+"' ";
//        }else{
//            sql = sql + " and tor.create_time >= "+startTime+" ";
//        }
//        sql = sql + " and tor.store_id is not null and te.channel_id is not null group by tor.customer_id) T_1 group by T_1.channel_id)T_4 on T_2.channel_id = T_4.channel_id ";
//        sql = sql + " left join (select T_1.channel_id ,count(1) as sign_num from (select max(te.channel_id) as channel_id from gemini.t_order tor left join gemini.t_eshop te on tor.eshop_id = te.id where 1=1 ";
//        if(!startTime.equals("null") && !endTime.equals("null")){
//            sql = sql + " and tor.create_time > '"+startTime+"' ";
//            sql = sql + " and tor.create_time < '"+endTime+"' ";
//        }else{
//            sql = sql + " and tor.create_time >= "+startTime+" ";
//        }
//        sql = sql + " and tor.store_id is not null and te.channel_id is not null and tor.sign_time is not null group by tor.customer_id) T_1 group by T_1.channel_id) T_5 on T_4.channel_id = T_5.channel_id ";
//        sql = sql + " left join  datacube_kudu.d_chanel dc on T_2.channel_id = dc.id order by   T_2.channel_id,T_2.visit_num ";

        String sql =  " select ";
        sql = sql + " 	dc.name as channel_name,";
        sql = sql + " 	isnull( T_2.visit_num , 0) as visit_num,";
        sql = sql + " ";
        sql = sql + " 	isnull( T_4.order_num, 0) as order_num,";
        sql = sql + " 	isnull( T_5.sign_num, 0) as sign_num";
        sql = sql + " from ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 		T_1.channel_id , ";
        sql = sql + " 		count(1) as visit_num  ";
        sql = sql + " 	from ";
        sql = sql + " 	(";
        sql = sql + " 	  select";
        sql = sql + " 		max(lf.channel_id) as channel_id";
        sql = sql + " 	  from datacube_kudu.log_final lf";
        sql = sql + " 	  inner join gemini.t_store ts on lf.store_id = ts.id and ts.name not like '%测试%'";
        sql = sql + " 		  where 1=1";
        if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and lf.simple_date >= "+startTime+" ";
            sql = sql + " and lf.simple_date < "+endTime+" ";
        }else{
            sql = sql + " and lf.simple_date >= "+startTime+" ";
        }
        sql = sql + " 		  and lf.customer_id is not null ";
        sql = sql + " 		  and lf.channel_id is not null";
        sql = sql + " 		  and lf.store_id is not null   ";
        sql = sql + " 		  and lf.customer_id not in (";
        sql = sql + " 			'fakecustomerformicromarket000002',";
        sql = sql + " 			'fakecustomerformicromarket000001',";
        sql = sql + " 			'fakecustomerforexpress0000000001'";
        sql = sql + " 		  )";
        sql = sql + " 	  group by lf.customer_id ";
        sql = sql + " 	) T_1";
        sql = sql + " 	group by T_1.channel_id ";
        sql = sql + " 	";
        sql = sql + " ) T_2";
        sql = sql + " left join ";
        sql = sql + " (";
        sql = sql + "    select";
        sql = sql + " 		 T_1.channel_id ,";
        sql = sql + " 		 count(1) as order_num ";
        sql = sql + " 	 from ";
        sql = sql + " 	 (";
        sql = sql + " 		 select ";
        sql = sql + " 			max(te.channel_id) as channel_id";
        sql = sql + " 		 from ";
        sql = sql + " 		 gemini.t_order tor ";
        sql = sql + " 		 left join gemini.t_eshop te on tor.eshop_id = te.id  ";
        sql = sql + " 		 inner join gemini.t_store ts on tor.store_id = ts.id and ts.name not like '%测试%'";
        sql = sql + " 		 where 1=1 ";
        if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and tor.create_time > "+startTime+"";
            sql = sql + " and tor.create_time < "+endTime+" ";
        }else{
            sql = sql + " and tor.create_time >= "+startTime+" ";
        }
        sql = sql + " 		 and tor.store_id is not null ";
        sql = sql + " 		 and te.channel_id is not null";
        sql = sql + " 		 and tor.customer_id not in (";
        sql = sql + " 			'fakecustomerformicromarket000002',";
        sql = sql + " 			'fakecustomerformicromarket000001',";
        sql = sql + " 			'fakecustomerforexpress0000000001'";
        sql = sql + " 		  )";
        sql = sql + " 		 group by tor.customer_id ";
        sql = sql + " 	  ) T_1";
        sql = sql + " 	group by T_1.channel_id ";
        sql = sql + " 	";
        sql = sql + " )T_4 on T_2.channel_id = T_4.channel_id";
        sql = sql + " left join ";
        sql = sql + " (";
        sql = sql + " 	select";
        sql = sql + " 		 T_1.channel_id ,";
        sql = sql + " 		 count(1) as sign_num ";
        sql = sql + " 	 from ";
        sql = sql + " 	 (";
        sql = sql + " 		 select ";
        sql = sql + " 			max(te.channel_id) as channel_id";
        sql = sql + " 		 from ";
        sql = sql + " 		 gemini.t_order tor ";
        sql = sql + " 		 left join gemini.t_eshop te on tor.eshop_id = te.id ";
        sql = sql + " 		 inner join gemini.t_store ts on tor.store_id = ts.id and ts.name not like '%测试%'		 ";
        sql = sql + " 		 where 1=1 ";
        if(!startTime.equals("null") && !endTime.equals("null")){
            sql = sql + " and tor.create_time > "+startTime+" ";
            sql = sql + " and tor.create_time < "+endTime+" ";
        }else{
            sql = sql + " and tor.create_time >= "+startTime+" ";
        }
        sql = sql + " 		 and tor.store_id is not null ";
        sql = sql + " 		 and te.channel_id is not null";
        sql = sql + " 		 and tor.sign_time is not null ";
        sql = sql + " 		 and tor.customer_id not in (";
        sql = sql + " 			'fakecustomerformicromarket000002',";
        sql = sql + " 			'fakecustomerformicromarket000001',";
        sql = sql + " 			'fakecustomerforexpress0000000001'";
        sql = sql + " 		  )";
        sql = sql + " 		 group by tor.customer_id ";
        sql = sql + " 	  ) T_1";
        sql = sql + " 	group by T_1.channel_id ";
        sql = sql + " 	";
        sql = sql + " ) T_5 on T_4.channel_id = T_5.channel_id";
        sql = sql + " left join  datacube_kudu.d_chanel dc on T_2.channel_id = dc.id";
        sql = sql + " order by   T_2.channel_id,T_2.visit_num";
        sql = sql + " ";
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getUserShoppingCartList(String startTime,String endTime) {
        //查询用户购物行为总次数
        String sql = "select ";
        sql = sql + "	'1' as action_level,";
        sql = sql + "	'浏览/跳转点击总次数' as numType,";
        sql = sql + "	count(distinct  lf.action_id) as num ";
        sql = sql + "from datacube_kudu.log_final lf ";
        sql = sql + "	where 1=1";
        sql = sql + " and lf.simple_date >= "+startTime+" ";
        sql = sql + " and lf.simple_date < "+endTime+" ";
        sql = sql + "    and lf.customer_id  in ";
        sql = sql + "	(";
        sql = sql + "		select customer_id from gemini.t_order ";
        sql = sql + "		where customer_id  is not null ";
        sql = sql + "		and sign_time is not null ";
        sql = sql + "		and sign_time > "+startTime+"";
        sql = sql + "		and sign_time < "+endTime+" ";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + "	)";
        sql = sql + "	and lf.behavior_name  in ";
        sql = sql + "	(";
        sql = sql + "		select behavior_name from datacube_kudu.d_action_level where level = 1";
        sql = sql + "	)";
        sql = sql + " ";
        sql = sql + "union ";
        sql = sql + "";
        sql = sql + "(";
        sql = sql + "	select ";
        sql = sql + "		'2' as action_level,";
        sql = sql + "		'加购点击总次数' as numType,";
        sql = sql + "		count(distinct  lf.action_id) as num ";
        sql = sql + "	from datacube_kudu.log_final lf ";
        sql = sql + "		where 1=1";
        sql = sql + "		and lf.simple_date >= "+startTime+" ";
        sql = sql + "		and lf.simple_date < "+endTime+"";
        sql = sql + "		and lf.customer_id  in ";
        sql = sql + "		(";
        sql = sql + "			select customer_id from gemini.t_order ";
        sql = sql + "			where customer_id  is not null ";
        sql = sql + "			and sign_time is not null ";
        sql = sql + "			and sign_time > "+startTime+"";
        sql = sql + "			and sign_time < "+endTime+" ";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + "		)";
        sql = sql + "		and lf.behavior_name  in ";
        sql = sql + "		(";
        sql = sql + "			select behavior_name from datacube_kudu.d_action_level where level = 2";
        sql = sql + "		)";
        sql = sql + ")";
        sql = sql + "";
        sql = sql + "union ";
        sql = sql + "";
        sql = sql + "(";
        sql = sql + "	select ";
        sql = sql + "		'3' as action_level,";
        sql = sql + "		'下单点击总次数' as numType,";
        sql = sql + "		count(distinct  lf.action_id) as num ";
        sql = sql + "	from datacube_kudu.log_final lf ";
        sql = sql + "		where 1=1";
        sql = sql + "		and lf.simple_date >= "+startTime+" ";
        sql = sql + "		and lf.simple_date < "+endTime+"";
        sql = sql + "		and lf.customer_id  in ";
        sql = sql + "		(";
        sql = sql + "			select customer_id from gemini.t_order ";
        sql = sql + "			where customer_id  is not null ";
        sql = sql + "			and sign_time is not null ";
        sql = sql + "			and sign_time > "+startTime+"";
        sql = sql + "			and sign_time < "+endTime+" ";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + "		)";
        sql = sql + "		and lf.behavior_name  in ";
        sql = sql + "		(";
        sql = sql + "			select behavior_name from datacube_kudu.d_action_level where level = 3";
        sql = sql + "		)";
        sql = sql + ")";
        sql = sql + "";
        sql = sql + "union ";
        sql = sql + "";
        sql = sql + "(";
        sql = sql + "	select ";
        sql = sql + "		'4' as action_level,";
        sql = sql + "		'支付点击总次数' as numType,";
        sql = sql + "		count(distinct  lf.action_id) as num ";
        sql = sql + "	from datacube_kudu.log_final lf ";
        sql = sql + "		where 1=1";
        sql = sql + "		and lf.simple_date >= "+startTime+" ";
        sql = sql + "		and lf.simple_date < "+endTime+"";
        sql = sql + "		and lf.customer_id  in ";
        sql = sql + "		(";
        sql = sql + "			select customer_id from gemini.t_order ";
        sql = sql + "			where customer_id  is not null ";
        sql = sql + "			and sign_time is not null ";
        sql = sql + "			and sign_time > "+startTime+"";
        sql = sql + "			and sign_time < "+endTime+" 	";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + "		)";
        sql = sql + "		and lf.behavior_name  in ";
        sql = sql + "		(";
        sql = sql + "			select behavior_name from datacube_kudu.d_action_level where level = 4";
        sql = sql + "		)";
        sql = sql + ")";
        sql = sql + "";
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getUserShoppingCartAvg(String startTime,String endTime) {
        //查询用户购物平均行为点击次数
        String sql = "select ";
        sql = sql + " 	'1' as action_level,";
        sql = sql + " 	'浏览/跳转点击平均数' as numType,";
        sql = sql + " 	round(avg(num),0) as num";
        sql = sql + " from ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 		count(distinct  lf.action_id) as num ";
        sql = sql + " 	from datacube_kudu.log_final lf ";
        sql = sql + " 		where 1=1";
        sql = sql + " 		and lf.simple_date >= "+startTime+" ";
        sql = sql + " 		and lf.simple_date < "+endTime+"";
        sql = sql + " 		and lf.customer_id  in ";
        sql = sql + " 		(";
        sql = sql + " 			select customer_id from gemini.t_order ";
        sql = sql + " 			where customer_id  is not null ";
        sql = sql + " 			and sign_time is not null ";
        sql = sql + " 			and sign_time > "+startTime+"";
        sql = sql + " 			and sign_time < "+endTime+"  	";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + " 		)";
        sql = sql + " 		and lf.behavior_name  in ";
        sql = sql + " 		(";
        sql = sql + " 			select behavior_name from datacube_kudu.d_action_level where level = 1";
        sql = sql + " 		)";
        sql = sql + " 	group by lf.customer_id";
        sql = sql + " ) T_1";
        sql = sql + "  ";
        sql = sql + " union ";
        sql = sql + " ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 	'2' as action_level,";
        sql = sql + " 	'加购点击平均数' as numType,";
        sql = sql + " 	round(avg(num),0) as num";
        sql = sql + " 	from ";
        sql = sql + " 	(";
        sql = sql + " 		select ";
        sql = sql + " 			count(distinct  lf.action_id) as num ";
        sql = sql + " 		from datacube_kudu.log_final lf ";
        sql = sql + " 			where 1=1";
        sql = sql + " 			and lf.simple_date >= "+startTime+" ";
        sql = sql + " 			and lf.simple_date < "+endTime+"";
        sql = sql + " 			and lf.customer_id  in ";
        sql = sql + " 			(";
        sql = sql + " 				select customer_id from gemini.t_order ";
        sql = sql + " 				where customer_id  is not null ";
        sql = sql + " 				and sign_time is not null ";
        sql = sql + " 				and sign_time > "+startTime+"";
        sql = sql + " 				and sign_time < "+endTime+" ";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + " 			)";
        sql = sql + " 			and lf.behavior_name  in ";
        sql = sql + " 			(";
        sql = sql + " 				select behavior_name from datacube_kudu.d_action_level where level = 2";
        sql = sql + " 			)";
        sql = sql + " 		group by lf.customer_id";
        sql = sql + " 	) T_1";
        sql = sql + " )";
        sql = sql + " ";
        sql = sql + " union ";
        sql = sql + " ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 	'3' as action_level,";
        sql = sql + " 	'下单点击平均数' as numType,";
        sql = sql + " 	round(avg(num),0) as num";
        sql = sql + " 	from ";
        sql = sql + " 	(";
        sql = sql + " 		select ";
        sql = sql + " 			count(distinct  lf.action_id) as num ";
        sql = sql + " 		from datacube_kudu.log_final lf ";
        sql = sql + " 			where 1=1";
        sql = sql + " 			and lf.simple_date >= "+startTime+" ";
        sql = sql + " 			and lf.simple_date < "+endTime+"";
        sql = sql + " 			and lf.customer_id  in ";
        sql = sql + " 			(";
        sql = sql + " 				select customer_id from gemini.t_order ";
        sql = sql + " 				where customer_id  is not null ";
        sql = sql + " 				and sign_time is not null ";
        sql = sql + " 				and sign_time > "+startTime+"";
        sql = sql + " 				and sign_time < "+endTime+" ";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + " 			)";
        sql = sql + " 			and lf.behavior_name  in ";
        sql = sql + " 			(";
        sql = sql + " 				select behavior_name from datacube_kudu.d_action_level where level = 3";
        sql = sql + " 			)";
        sql = sql + " 		group by lf.customer_id";
        sql = sql + " 	) T_1";
        sql = sql + " )";
        sql = sql + " union ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 	'4' as action_level,";
        sql = sql + " 	'支付点击平均数' as numType,";
        sql = sql + " 	round(avg(num),0) as num";
        sql = sql + " 	from ";
        sql = sql + " 	(";
        sql = sql + " 		select ";
        sql = sql + " 			count(distinct  lf.action_id) as num ";
        sql = sql + " 		from datacube_kudu.log_final lf ";
        sql = sql + " 			where 1=1";
        sql = sql + " 			and lf.simple_date >= "+startTime+" ";
        sql = sql + " 			and lf.simple_date < "+endTime+"";
        sql = sql + " 			and lf.customer_id  in ";
        sql = sql + " 			(";
        sql = sql + " 				select customer_id from gemini.t_order ";
        sql = sql + " 				where customer_id  is not null ";
        sql = sql + " 				and sign_time is not null ";
        sql = sql + " 				and sign_time > "+startTime+"";
        sql = sql + " 				and sign_time < "+endTime+" ";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + " 			)";
        sql = sql + " 			and lf.behavior_name  in ";
        sql = sql + " 			(";
        sql = sql + " 				select behavior_name from datacube_kudu.d_action_level where level = 4";
        sql = sql + " 			)";
        sql = sql + " 		group by lf.customer_id";
        sql = sql + " 	) T_1";
        sql = sql + " )";
        sql = sql + " ";
        sql = sql + " ";
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getUserShoppingCartDetails(String startTime,String endTime) {
        String sql = "select ";
        sql = sql + "     '1' as action_level,";
        sql = sql + " 	'浏览/跳转点击' as numType,";
        sql = sql + " 	lf.behavior_name ,";
        sql = sql + " 	count(distinct lf.action_id) as num ";
        sql = sql + " from datacube_kudu.log_final lf ";
        sql = sql + " 	where 1=1";
        sql = sql + "     and lf.simple_date >= "+startTime+" ";
        sql = sql + "     and lf.simple_date < "+endTime+"";
        sql = sql + "     and lf.customer_id  in ";
        sql = sql + " 	(";
        sql = sql + " 		select customer_id from gemini.t_order ";
        sql = sql + " 		where customer_id  is not null ";
        sql = sql + " 		and sign_time is not null ";
        sql = sql + " 		and sign_time > "+startTime+"";
        sql = sql + " 		and sign_time < "+endTime+" ";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + " 	)";
        sql = sql + " 	and lf.behavior_name  in ";
        sql = sql + " 	(";
        sql = sql + " 		select behavior_name from datacube_kudu.d_action_level where level = 1";
        sql = sql + " 	)";
        sql = sql + " group by lf.behavior_name";
        sql = sql + " ";
        sql = sql + "  ";
        sql = sql + " union ";
        sql = sql + " ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 		'2' as action_level,";
        sql = sql + " 		'加购点击' as numType,";
        sql = sql + " 		lf.behavior_name ,";
        sql = sql + " 		count(distinct lf.action_id) as num ";
        sql = sql + " 	from datacube_kudu.log_final lf ";
        sql = sql + " 		where 1=1";
        sql = sql + " 		and lf.simple_date >= "+startTime+" ";
        sql = sql + " 		and lf.simple_date < "+endTime+"";
        sql = sql + " 		and lf.customer_id  in ";
        sql = sql + " 		(";
        sql = sql + " 			select customer_id from gemini.t_order ";
        sql = sql + " 			where customer_id  is not null ";
        sql = sql + " 			and sign_time is not null ";
        sql = sql + " 			and sign_time > "+startTime+"";
        sql = sql + " 			and sign_time < "+endTime+" ";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + " 		)";
        sql = sql + " 		and lf.behavior_name  in ";
        sql = sql + " 		(";
        sql = sql + " 			select behavior_name from datacube_kudu.d_action_level where level = 2";
        sql = sql + " 		)";
        sql = sql + " 	group by lf.behavior_name";
        sql = sql + " ";
        sql = sql + " )";
        sql = sql + " ";
        sql = sql + " union ";
        sql = sql + " ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 	    '3' as action_level,";
        sql = sql + " 		'下单点击' as numType,";
        sql = sql + " 		lf.behavior_name ,";
        sql = sql + " 		count(distinct lf.action_id) as num ";
        sql = sql + " 	from datacube_kudu.log_final lf ";
        sql = sql + " 		where 1=1";
        sql = sql + " 		and lf.simple_date >= "+startTime+" ";
        sql = sql + " 		and lf.simple_date < "+endTime+"";
        sql = sql + " 		and lf.customer_id  in ";
        sql = sql + " 		(";
        sql = sql + " 			select customer_id from gemini.t_order ";
        sql = sql + " 			where customer_id  is not null ";
        sql = sql + " 			and sign_time is not null ";
        sql = sql + " 			and sign_time > "+startTime+"";
        sql = sql + " 			and sign_time < "+endTime+" ";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + " 		)";
        sql = sql + " 		and lf.behavior_name  in ";
        sql = sql + " 		(";
        sql = sql + " 			select behavior_name from datacube_kudu.d_action_level where level = 3";
        sql = sql + " 		)";
        sql = sql + " 	group by lf.behavior_name";
        sql = sql + " ";
        sql = sql + " )";
        sql = sql + " ";
        sql = sql + " union ";
        sql = sql + " ";
        sql = sql + " (";
        sql = sql + " 	select ";
        sql = sql + " 	    '4' as action_level,";
        sql = sql + " 		'支付点击' as numType,";
        sql = sql + " 		lf.behavior_name ,";
        sql = sql + " 		count(distinct lf.action_id) as num ";
        sql = sql + " 	from datacube_kudu.log_final lf ";
        sql = sql + " 		where 1=1";
        sql = sql + " 		and lf.simple_date >= "+startTime+" ";
        sql = sql + " 		and lf.simple_date < "+endTime+"";
        sql = sql + " 		and lf.customer_id  in ";
        sql = sql + " 		(";
        sql = sql + " 			select customer_id from gemini.t_order ";
        sql = sql + " 			where customer_id  is not null ";
        sql = sql + " 			and sign_time is not null ";
        sql = sql + " 			and sign_time > "+startTime+"";
        sql = sql + " 			and sign_time < "+endTime+" ";
        sql = sql + " and customer_id not in (";
        sql = sql + " 				'fakecustomerformicromarket000002',";
        sql = sql + " 				'fakecustomerformicromarket000001',";
        sql = sql + " 				'fakecustomerforexpress0000000001'";
        sql = sql + " 				)";
        sql = sql + " 		)";
        sql = sql + " 		and lf.behavior_name  in ";
        sql = sql + " 		(";
        sql = sql + " 			select behavior_name from datacube_kudu.d_action_level where level = 4";
        sql = sql + " 		)";
        sql = sql + " 	group by lf.behavior_name";
        sql = sql + " ";
        sql = sql + " )";
        sql = sql + " ";
        sql = sql + " ";
        List<Map<String,Object>> list = ImpalaUtil.execute(sql);
        return list;
    }

}
