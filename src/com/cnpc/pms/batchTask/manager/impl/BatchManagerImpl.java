/**
 * gaobaolei
 */
package com.cnpc.pms.batchTask.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.batchTask.dao.BatchTaskDao;
import com.cnpc.pms.batchTask.manager.BatchManager;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.dynamic.common.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

/**
 * @author gaobaolei
 *  批处理
 */
public class BatchManagerImpl extends BizBaseCommonManager implements BatchManager{
		private String dataUri = "https://ds.guoanshequ.cn";
//		private String dataUri ="http://10.16.31.245:8090";
//		private String dataUri ="http://localhost:8080";

	private String[] dsApiUrl = {
			dataUri +"/ds/rest/massOrderPatchTaskRun?sign=",   //手动调度订单补丁0
			dataUri +"/ds/rest/abnormalOrderTaskRun?sign=",    //异常订单自动1
			dataUri +"/ds/rest/abnormalOrderDownTaskRun?sign=",    //异常订单基础数据2
			dataUri +"/ds/rest/returnMassOrderTaskRun?sign=",    //退货打标签3
			dataUri +"/ds/rest/abnormalMassOrderTaskRun?sign=",    //异常打标签4
			dataUri +"/ds/rest/customerTradeTaskRun?sign=",    //拉新打标签5
			dataUri +"/ds/rest/OrderReturnedTaskRun?sign=",    //门店退货表6
			dataUri +"/ds/rest/storeTradesTaskRun?sign=",    //门店交易额（按类型）7
			dataUri +"/ds/rest/storeTradeChannelTaskRun?sign=",    //门店交易额（按频道）8
			dataUri +"/ds/rest/empTradesTaskRun?sign=",    //国安侠GMV9
			dataUri +"/ds/rest/rewardTimesTaskRun?sign=",    //国安侠好评次数10
			dataUri +"/ds/rest/sendOrdersTaskRun?sign=",    //国安侠送单量(按频道)11
			dataUri +"/ds/rest/prePesNewGmvTaskRun?sign=",    //调度上月门店GMV与国安侠GMV12
			dataUri +"/ds/rest/storeTradeHistoryTaskRun?sign=",    //门店历史营业额手动调度13
			dataUri +"/ds/rest/productCityTaskRun?sign=",    //商品按城市排名14
			dataUri +"/ds/rest/addHumanTaskRun?sign=",    //员工与店长信息15
			dataUri +"/ds/rest/customerTaskRun?sign=",    //客户画像16
			dataUri +"/ds/rest/customerByStoreTaskRun?sign=",    //客户画像按门店17
			dataUri +"/ds/rest/relationTaskRun?sign=",    //拜访记录数据18
			dataUri +"/ds/rest/relationByStoreTaskRun?sign=",    //拜访记录按门店数据19
			dataUri +"/ds/rest/userProfileTaskRun?sign=",    //用户档案20
			dataUri +"/ds/rest/xbUserProfileTagTaskRun?sign=",    //用户打小B端标签21
			dataUri +"/ds/rest/xbOrderTagTaskRun?sign=",    //订单打小B端标签22
			dataUri +"/ds/rest/deptGmvTaskRun?sign=",    //事业群gmv调度23
			dataUri +"/ds/rest/deptCusTaskRun?sign=",    //事业群消费用户数调度24
	};

	private static String staticQuerySql =
			"select '门店GMV' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_pes_gmv_store_month where year =${year} and month =${month}" +
			" union " +
			"select '门店交易额（按频道）' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_ope_gmv_storechannel_month where year =${year} and month =${month}" +
			" union "+
			"select '国安侠GMV' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_pes_gmv_emp_month where year =${year} and month =${month}"+
			" union " +
			"select '国安侠好评次数' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_rewardtimes where year =${year} and month =${month}" +
			" union " +
			"select '国安侠送单量(按频道)' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_pes_order_empchannel_month where year =${year} and month =${month}" +
			" union " +
			"select '员工店长' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_topdata where year =${year} and month =${month}" +
			" union " +
			"select '客户画像' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_topdata where cusgrade2 is not null and year =${year} and month =${month}" +
			" union " +
			"select '客户画像按门店' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_topdata where storecusgrade2 is not null and year =${year} and month =${month}" +
			" union " +
			"select '拜访记录' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_topdata where relationnum is not null and year =${year} and month =${month}" +
			" union " +
			"select '拜访记录按门店' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_topdata where storerelationnum is not null and year =${year} and month =${month}" +
			" union " +
			"select '异常订单自动' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_abnormal_order where year =${year} and month =${month} "+
			" union "+
			"select '异常订单基础数据' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_abnormal_down where year =${year} and month =${month} "+
			" union "+
			"select '用户档案' as taskname,count(1) as datanum,max(create_time) as maxtime from df_user_profile "+
			" union "+
			"select '事业群gmv调度' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_pes_gmv_storedept_month where year =${year} and month =${month} "+
			" union "+
			"select '事业群消费用户数调度' as taskname,count(1) as datanum,max(createtime) as maxtime from ds_pes_customer_storedept_month where year =${year} and month =${month} "
			;
	
	private static final String APP_KEY = "1004";
	private static final String APP_PASSWD = "pass04";

	@Override
	public Map<String, String> startBatchTask(String code) {
		// init result data
		Map<String, String> resultMap = new HashMap<String, String>();

		// get request url
		try {
			String paraJsonString = makeParaString();
			String requestUrl = getRequestUrl(Integer.parseInt(code), paraJsonString);
			sendHttp(requestUrl, paraJsonString);
			resultMap.put("status", "success");
			resultMap.put("message", "start ds api properly!");
		} catch (Exception e) {
			resultMap.put("status", "error");
			resultMap.put("message", e.getMessage());
			log.error(e.getMessage());
		}
		return  resultMap;
	}

	private String getRequestUrl(Integer apiId, String paraStr) {
		String apiUrl = null;
		if (apiId < dsApiUrl.length) {
			apiUrl = dsApiUrl[apiId] + getSignString(paraStr);
		}
		return apiUrl;
	}

	/**
	 * make parameter json string
	 * @return json string
	 */
	private String makeParaString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("app_key", APP_KEY);
		jsonObject.put("nonce", UUID.randomUUID());
		jsonObject.put("stamp", System.currentTimeMillis());
		return jsonObject.toString();
	}
	/**
	 * Calculate the sign string for request url
	 * @return sign string
	 */
	private String getSignString(String paraStr) {
		String sign = EncryptUtils.getMD5(paraStr + APP_PASSWD);
		return  sign;
	}

	@Override
	public Map<String, Object> queryBatchResult(String year, String month) {
		String querySql = makeQuerySql(year, month);
		log.info(querySql);
		Map<String, Object> result = new HashMap<String,Object>();
		BatchTaskDao batchTaskDao = (BatchTaskDao)SpringHelper.getBean(BatchTaskDao.class.getName());
		try {
			List<Map<String, Object>> list = batchTaskDao.queryBatchResult(querySql);
			result.put("status", "success");
			
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("message", "无法正确查询结果！");
			return result;
		}
		
		return result;
	}

	private String makeQuerySql(String year, String month) {
		return StringUtils.replace(StringUtils.replace(staticQuerySql, "${month}", month), "${year}", year);
	}

	public  String  sendHttp(String url, String  paramsString) throws Exception {
		String responseBody = "";
		if (!"".equals(url) && !"".equals(paramsString)) {
			HttpClient httpClient = HttpClientBuilder.create().build();
			StringEntity requestEntity = new StringEntity(
					paramsString,
					ContentType.APPLICATION_JSON);
			HttpPost postMethod = new HttpPost(url);
			postMethod.setEntity(requestEntity);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpClient.execute(postMethod, responseHandler);
		}
		return responseBody;
	}
}
