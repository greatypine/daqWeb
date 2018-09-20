package com.cnpc.pms.platform.manager.impl;

import com.cnpc.pms.base.file.comm.utils.StringUtils;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.communeMember.dao.CommuneMemberDao;
import com.cnpc.pms.communeMember.dao.MemberInfoDao;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import com.cnpc.pms.platform.dao.PlatformStoreDao;
import com.cnpc.pms.platform.entity.MemberDataDto;
import com.cnpc.pms.platform.manager.MemberInfo;
import com.cnpc.pms.utils.PropertiesValueUtil;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 社员操作
 * 
 * @author wuxinxin 2018年5月17日
 */
public class MemberInfoImpl extends BizBaseCommonManager implements MemberInfo {

    // 生成X轴坐标
    public List reDate(int dd) throws ParseException {
        DateFormat f = new SimpleDateFormat("MM-dd");

        Date today = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, -dd);
        List dateX = new ArrayList();
        dateX.add(f.format(c.getTime()));
        for (int i = dd; i > 1; i--) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            dateX.add(f.format(c.getTime()));
        }
        return dateX;
    }

    /**
     * 成交额、订单量单位转换
     *
     * @author wuxinxin
     */
    public static String toBigMoney(String money) {
        BigDecimal bigDecimal = new BigDecimal(money);
        String pic = "万";
        // 转换为万元（除以10000）
        BigDecimal decimal = bigDecimal.divide(new BigDecimal("10000"));
        // 转换为亿元
        if (decimal.compareTo(new BigDecimal("10000")) > 0) {
            decimal = bigDecimal.divide(new BigDecimal("10000"));
            pic = "亿";
        }
        // 保留两位小数
        DecimalFormat formater = new DecimalFormat("0.00");
        // 四舍五入
        // formater.setRoundingMode(RoundingMode.HALF_UP); // 5000008.89
        // formater.setRoundingMode(RoundingMode.HALF_DOWN);// 5000008.89
        // formater.setRoundingMode(RoundingMode.HALF_EVEN);
        formater.setRoundingMode(RoundingMode.DOWN);

        // 格式化完成之后得出结果
        String remoney = formater.format(decimal) + pic;
        return remoney;
    }

	@Override
	public Map<String, Object> selectAllCm(String dd) {
		/**
		 * @author wuxinxin 2018年5月17日
		 */

	
		Map<String, Object> result = new HashMap<String, Object>();

        MemberInfoDao commDao = (MemberInfoDao) SpringHelper.getBean(MemberInfoDao.class.getName());
		Long memAll = 0l;
		// 查询社员总量---必须
		List<Map<String, Object>> cmCountList = new ArrayList<Map<String, Object>>();
		cmCountList = commDao.getCountAll(dd);
		if (cmCountList != null && cmCountList.size() > 0) {
			result.put("allcount", cmCountList.get(0).get("allcount"));
            memAll =Long.parseLong(cmCountList.get(0).get("allcount").toString());
		} else {
			result.put("allcount", "0");
		}
        // 查询大客户社员
        List<Map<String, Object>> cmBigCountList = new ArrayList<Map<String, Object>>();
        cmBigCountList = commDao.getBigMem(dd);
        if (cmBigCountList != null && cmBigCountList.size() > 0) {
            result.put("bigcount", cmBigCountList.get(0).get("bigcount"));
        } else {
            result.put("bigcount", "0");
        }
        // 查询试用社员
        List<Map<String, Object>> cmTryCouList = new ArrayList<Map<String, Object>>();
        cmTryCouList = commDao.getTryMem(dd);
        if (cmTryCouList != null && cmTryCouList.size() > 0) {
            result.put("trycount", cmTryCouList.get(0).get("trycount"));
        } else {
            result.put("trycount", "0");
        }



		//查询社员GMV

		List dateXCounts = new ArrayList();
		try {
			//生成X轴坐标
			dateXCounts = reDate(7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray jsonDateX = (JSONArray) JSONArray.fromObject(dateXCounts);
		result.put("jsonDateX", jsonDateX);
		return result;

	}


    @Override
    public Map<String, Object> selectCmGmv(String dd) {
        /**
         * @author wuxinxin 2018年5月17日
         */


        Map<String, Object> result = new HashMap<String, Object>();

        MemberInfoDao commDao = (MemberInfoDao) SpringHelper.getBean(MemberInfoDao.class.getName());
        Long memAll = 0l;
        //查询社员GMV
        List<Map<String, Object>> cmCountList = new ArrayList<Map<String, Object>>();
        cmCountList = commDao.getMemOrderCount(dd);
        if (cmCountList != null && cmCountList.size() > 0) {
            result.put("memcou", cmCountList.get(0).get("cou").toString());
            result.put("memgmv",cmCountList.get(0).get("pri").toString());
        } else {
            result.put("memcou", "0");
            result.put("memgmv","0");
        }
        return result;

    }

    @Override
    public Map<String, Object> selectMemFrom(String dd) {
        /**
         * @author wuxinxin 2018年9月17日
         */


        Map<String, Object> result = new HashMap<String, Object>();

        MemberInfoDao commDao = (MemberInfoDao) SpringHelper.getBean(MemberInfoDao.class.getName());
        Long memAll = 0l;
        //查询社员GMV
        List<Map<String, Object>> cmFromList = new ArrayList<Map<String, Object>>();
        cmFromList = commDao.getMemFrom(dd);
        //渠道类别
        List memFromNames = new ArrayList();
        //渠道数量
        List memFromCou = new ArrayList();

        for (Map<String, Object> stringObjectMap : cmFromList) {
            if(Long.parseLong(stringObjectMap.get("cou").toString())>0){
                memFromCou.add(stringObjectMap.get("cou").toString());
                memFromNames.add(stringObjectMap.get("customer_source").toString());
            }
        }
        JSONArray jsonMemFromName = (JSONArray) JSONArray.fromObject(memFromNames);
        JSONArray jsonMemFromCou = (JSONArray) JSONArray.fromObject(memFromCou);

        result.put("jsonMemFromName", jsonMemFromName);
        result.put("jsonMemFromCou", jsonMemFromCou);
        return result;
    }

    @Override
    public Map<String, Object> selectRegistCity(String dd) {
        /**
         * @author wuxinxin 2018年9月17日
         */


        Map<String, Object> result = new HashMap<String, Object>();

        MemberInfoDao commDao = (MemberInfoDao) SpringHelper.getBean(MemberInfoDao.class.getName());
        Long memAll = 0l;
        //查询社员注册城市
        List<Map<String, Object>> cmFromList = new ArrayList<Map<String, Object>>();
        cmFromList = commDao.getRegistCity(dd);
        //城市名称
        List memCityNames = new ArrayList();
        //注册数量
        List memCityCou = new ArrayList();

        for (Map<String, Object> stringObjectMap : cmFromList) {
            if(Long.parseLong(stringObjectMap.get("cou").toString())>0){
                memCityCou.add(stringObjectMap.get("cou").toString());
                memCityNames.add(stringObjectMap.get("cityname").toString());
            }
        }
        JSONArray jsonMemCityName = (JSONArray) JSONArray.fromObject(memCityNames);
        JSONArray jsonMemCityCou = (JSONArray) JSONArray.fromObject(memCityCou);

        result.put("jsonMemCityName", jsonMemCityName);
        result.put("jsonMemCityCou", jsonMemCityCou);
        return result;
    }

    @Override
    public Map<String, Object> selectMemShoping(String dd) {
        /**
         * @author wuxinxin 2018年9月17日
         */


        Map<String, Object> result = new HashMap<String, Object>();

        MemberInfoDao commDao = (MemberInfoDao) SpringHelper.getBean(MemberInfoDao.class.getName());
        Long memAll = 0l;
        List<Map<String, Object>> cmFromList = new ArrayList<Map<String, Object>>();
        cmFromList = commDao.getMemByTimes(dd);
        List memOrderCou1 = new ArrayList();
        List memOrderCou2 = new ArrayList();
        List memOrderCou3 = new ArrayList();
        List memOrderMon = new ArrayList();
        Long count1 = 0l;
        Long count2 = 0l;
        Long count3 = 0l;

        for (Map<String, Object> stringObjectMap : cmFromList) {
            memOrderCou1.add(stringObjectMap.get("cou1").toString());
            count1 +=Long.parseLong(stringObjectMap.get("cou1").toString());
            memOrderCou2.add(stringObjectMap.get("cou2").toString());
            count2 +=Long.parseLong(stringObjectMap.get("cou2").toString());
            memOrderCou3.add(stringObjectMap.get("cou3").toString());
            count3 +=Long.parseLong(stringObjectMap.get("cou3").toString());
            memOrderMon.add(stringObjectMap.get("smon").toString());
        }
        JSONArray jsonMemOrderCou1 = (JSONArray) JSONArray.fromObject(memOrderCou1);
        JSONArray jsonMemOrderCou2 = (JSONArray) JSONArray.fromObject(memOrderCou2);
        JSONArray jsonMemOrderCou3 = (JSONArray) JSONArray.fromObject(memOrderCou3);
        JSONArray jsonMemOrderMon = (JSONArray) JSONArray.fromObject(memOrderMon);

        result.put("jsonMemOrderCou1", jsonMemOrderCou1);
        result.put("jsonMemOrderCou2", jsonMemOrderCou2);
        result.put("jsonMemOrderCou3", jsonMemOrderCou3);
        result.put("jsonMemOrderMon", jsonMemOrderMon);
        result.put("count1", count1);
        result.put("count2", count2);
        result.put("count3", count3);
        return result;
    }

    @Override
    public Map<String, Object> selectNmemShoping(String dd) {
        /**
         * @author wuxinxin 2018年9月17日
         */


        Map<String, Object> result = new HashMap<String, Object>();

        MemberInfoDao commDao = (MemberInfoDao) SpringHelper.getBean(MemberInfoDao.class.getName());
        Long memAll = 0l;
        List<Map<String, Object>> cmNoOrderList = new ArrayList<Map<String, Object>>();
        cmNoOrderList = commDao.getNmemByTimes(dd);
        List nMemOrderCou1 = new ArrayList();
        List nMemOrderCou2 = new ArrayList();
        List nMemOrderCou3 = new ArrayList();
        List nMemOrderMon = new ArrayList();

        for (Map<String, Object> stringObjectMap : cmNoOrderList) {
            nMemOrderCou1.add(stringObjectMap.get("cou1").toString());
            nMemOrderCou2.add(stringObjectMap.get("cou2").toString());
            nMemOrderCou3.add(stringObjectMap.get("cou3").toString());
            nMemOrderMon.add(stringObjectMap.get("smon").toString());
        }
        JSONArray jsonNmemOrderCou1 = (JSONArray) JSONArray.fromObject(nMemOrderCou1);
        JSONArray jsonNmemOrderCou2 = (JSONArray) JSONArray.fromObject(nMemOrderCou2);
        JSONArray jsonNmemOrderCou3 = (JSONArray) JSONArray.fromObject(nMemOrderCou3);
        JSONArray jsonNmemOrderMon = (JSONArray) JSONArray.fromObject(nMemOrderMon);

        result.put("jsonNmemOrderCou1", jsonNmemOrderCou1);
        result.put("jsonNmemOrderCou2", jsonNmemOrderCou2);
        result.put("jsonNmemOrderCou3", jsonNmemOrderCou3);
        result.put("jsonNmemOrderMon", jsonNmemOrderMon);
        return result;
    }

    @Override
    public Map<String, Object> selectMemNoMem(String dd) {
        Map<String, Object> result = new HashMap<String, Object>();

        MemberInfoDao commDao = (MemberInfoDao) SpringHelper.getBean(MemberInfoDao.class.getName());
        Long memAll = 0l;
        //查询社员成交额  按月
        List<Map<String, Object>> cmList = new ArrayList<Map<String, Object>>();
        cmList = commDao.getMemGmvByMon(dd);

        //社员单量
        List memCou = new ArrayList();
        //社员GMV
        List memGmv = new ArrayList();
        //成交月份
        List memMon = new ArrayList();
        //订单均价
        List memMonAvg = new ArrayList();

        for (Map<String, Object> stringObjectMap : cmList) {
            memCou.add(stringObjectMap.get("cou").toString());
            memGmv.add(stringObjectMap.get("daypri").toString());
            memMonAvg.add(String.format("%.1f",Double.valueOf(stringObjectMap.get("daypri").toString())/Double.valueOf(stringObjectMap.get("cou").toString())));
            memMon.add(stringObjectMap.get("sellmon").toString());
        }
        JSONArray jsonMemCous = (JSONArray) JSONArray.fromObject(memCou);
        JSONArray jsonMemGmvs = (JSONArray) JSONArray.fromObject(memGmv);
        JSONArray jsonMemMons = (JSONArray) JSONArray.fromObject(memMon);
        JSONArray jsonMemMonAvgs = (JSONArray) JSONArray.fromObject(memMonAvg);

        result.put("jsonMemCous", jsonMemCous);
        result.put("jsonMemGmvs", jsonMemGmvs);
        result.put("jsonMemMons", jsonMemMons);
        result.put("jsonMemMonAvgs", jsonMemMonAvgs);

        //社员单量
        List noMemCou = new ArrayList();
        //社员GMV
        List noMemGmv = new ArrayList();
        //非社员订单均价
        List noMemMonAvg = new ArrayList();
        //查询非社员成交额  按月
        List<Map<String, Object>> noCmList = new ArrayList<Map<String, Object>>();
        noCmList = commDao.getNmemGmvByMon(dd);
        for (Map<String, Object> stringMap : noCmList) {
            noMemCou.add(stringMap.get("cou").toString());
            noMemGmv.add(stringMap.get("daypri").toString());
            noMemMonAvg.add(String.format("%.1f",Double.valueOf(stringMap.get("daypri").toString())/Double.valueOf(stringMap.get("cou").toString())));
        }

        JSONArray jsonNoMemCous = (JSONArray) JSONArray.fromObject(noMemCou);
        JSONArray jsonNoMemGmvs = (JSONArray) JSONArray.fromObject(noMemGmv);
        JSONArray jsonNoMemMonAvgs = (JSONArray) JSONArray.fromObject(noMemMonAvg);

        result.put("jsonNoMemCous", jsonNoMemCous);
        result.put("jsonNoMemGmvs", jsonNoMemGmvs);
        result.put("jsonNoMemMonAvgs", jsonNoMemMonAvgs);
        return result;
    }


}
