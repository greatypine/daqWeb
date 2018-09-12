package com.cnpc.pms.youyi.manager.impl;

import com.cnpc.pms.base.file.comm.utils.StringUtils;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.communeMember.dao.CommuneMemberDao;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import com.cnpc.pms.platform.dao.PlatformStoreDao;
import com.cnpc.pms.platform.entity.MemberDataDto;
import com.cnpc.pms.utils.PropertiesValueUtil;
import com.cnpc.pms.youyi.dao.YouyiProductDao;
import com.cnpc.pms.youyi.dao.impl.YouyiProductDaoImpl;
import com.cnpc.pms.youyi.manager.YouyiProduct;
import net.sf.json.JSONArray;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
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
 * 国安优易操作
 * 
 * @author wuxinxin 2018年9月11日
 */
public class YouyiProductImpl extends BizBaseCommonManager implements YouyiProduct {

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


    @Override
    public Map<String, Object> selectYouyiSku(String dd) {
        Map<String, Object> result = new HashMap<String, Object>();
        YouyiProductDao youyiDao = (YouyiProductDao) SpringHelper.getBean(YouyiProductDao.class.getName());
        List<Map<String, Object>> cmSexList = new ArrayList<Map<String, Object>>();
        cmSexList = youyiDao.getYouyiSku(dd);

        if (cmSexList != null && cmSexList.size() > 0) {
            //result.put("skuAll", cmSexList.get(0).get("cumulative_sku"));
            result.put("skuAll", cmSexList.get(0).get("sku_online"));   //上线SKU
            result.put("skuMove", cmSexList.get(0).get("sku_onselling"));//动销SKU
            result.put("newSku", cmSexList.get(0).get("sku_new_count"));//上新SKU
        } else {
            result.put("skuAll", "0");
            result.put("skuMove", "0");
            result.put("newSku", "0");
        }
        return result;
    }

    @Override
    public Map<String, Object> selectMovingByMonth(String dd) {
        Map<String, Object> result = new HashMap<String, Object>();
        YouyiProductDao youyiDao = (YouyiProductDao) SpringHelper.getBean(YouyiProductDao.class.getName());
        List<Map<String, Object>> channelList = new ArrayList<Map<String, Object>>();
        channelList = youyiDao.getChannelMoving(dd);
        List dateMonCounts = new ArrayList();
        List cnTe = new ArrayList();
        List cnSheng = new ArrayList();
        List cnRi = new ArrayList();
        List allMon = new ArrayList();
        String monthType = "";
        Double allcnTe=0.0;
        Double allcnRi=0.0;
        Double allcnSheng=0.0;
        Double sellcnTe=0.0;
        Double sellcnRi=0.0;
        Double sellcnSheng=0.0;

        if(channelList!=null&&channelList.size()>0){
                //30天每天有新增
                for (int i = 0; i < channelList.size(); i++) {
                    if(!monthType.equals(channelList.get(i).get("last_modify_time").toString())){
                        allMon.add(channelList.get(i).get("last_modify_time").toString());
                        monthType=channelList.get(i).get("last_modify_time").toString();
                    }

                    if("特产频道".equals(channelList.get(i).get("cname").toString())){
                        allcnTe +=Double.parseDouble(channelList.get(i).get("cumulative_sku").toString());
                        sellcnTe +=Double.parseDouble(channelList.get(i).get("sku_onselling").toString());
                        cnTe.add(Double.parseDouble(String.format("%.1f", Double.parseDouble(channelList.get(i).get("sku_onselling").toString())/Double.parseDouble(channelList.get(i).get("cumulative_sku").toString())*100)));
                    }else if("生鲜频道".equals(channelList.get(i).get("cname").toString())){
                        allcnRi +=Double.parseDouble(channelList.get(i).get("cumulative_sku").toString());
                        sellcnRi +=Double.parseDouble(channelList.get(i).get("sku_onselling").toString());
                        cnSheng.add(Double.parseDouble(String.format("%.1f", Double.parseDouble(channelList.get(i).get("sku_onselling").toString())/Double.parseDouble(channelList.get(i).get("cumulative_sku").toString())*100)));
                    }else if("日用百货".equals(channelList.get(i).get("cname").toString())){
                        allcnSheng +=Double.parseDouble(channelList.get(i).get("cumulative_sku").toString());
                        sellcnSheng +=Double.parseDouble(channelList.get(i).get("sku_onselling").toString());
                        cnRi.add(Double.parseDouble(String.format("%.1f", Double.parseDouble(channelList.get(i).get("sku_onselling").toString())/Double.parseDouble(channelList.get(i).get("cumulative_sku").toString())*100)));
                    }
                }
        }
        result.put("jsonAllTechan", String.format("%.1f",sellcnTe/allcnTe*100));
        result.put("jsonAllRiyong", String.format("%.1f",sellcnRi/allcnRi*100));
        result.put("jsonAllShengxian", String.format("%.1f",sellcnSheng/allcnSheng*100));
        JSONArray jsonTechan = (JSONArray) JSONArray.fromObject(cnTe);
        JSONArray jsonShengxian = (JSONArray) JSONArray.fromObject(cnSheng);
        JSONArray jsonRiyong = (JSONArray) JSONArray.fromObject(cnRi);
        JSONArray jsonMonth = (JSONArray) JSONArray.fromObject(allMon);
        result.put("jsonTechan", jsonTechan);
        result.put("jsonShengxian", jsonShengxian);
        result.put("jsonRiyong", jsonRiyong);
        result.put("jsonMonth", jsonMonth);
        return result;
    }

    @Override
    public Map<String, Object> selectCityMoving(String dd) {
        Map<String, Object> result = new HashMap<String, Object>();
        YouyiProductDao youyiDao = (YouyiProductDao) SpringHelper.getBean(YouyiProductDao.class.getName());
        List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
        cityList = youyiDao.getCityMoving(dd);
        List cityName = new ArrayList();
        List cityMove = new ArrayList();
        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                cityName.add(cityList.get(i).get("cityname").toString());
                cityMove.add(Double.parseDouble(String.format("%.1f", Double.parseDouble(cityList.get(i).get("sku_onselling").toString())/Double.parseDouble(cityList.get(i).get("cumulative_sku").toString())*100)));
            }
        }
        JSONArray jsonCityName = (JSONArray) JSONArray.fromObject(cityName);
        JSONArray jsonCityMove = (JSONArray) JSONArray.fromObject(cityMove);
        result.put("jsonCityName", jsonCityName);
        result.put("jsonCityMove", jsonCityMove);
        return result;
    }

    @Override
    public Map<String, Object> selectChannelMoving(String dd) {
        return null;
    }

    @Override
    public Map<String, Object> selectChannelSell(String dd) {
        Map<String, Object> result = new HashMap<String, Object>();
        YouyiProductDao youyiDao = (YouyiProductDao) SpringHelper.getBean(YouyiProductDao.class.getName());
        // 查询社员省钱排行前5
        List<Map<String, Object>> chanCouList = youyiDao.getChannelSell(dd);
        String cTechanCou = "";
        String cShengxianCou = "";
        String cRiyongCou ="";
        if (chanCouList!=null&&!chanCouList.isEmpty()) {

            for (int i = 0; i < chanCouList.size(); i++) {
                if("特产频道".equals(chanCouList.get(i).get("cname").toString())){
                    cTechanCou = chanCouList.get(i).get("sku_onselling").toString();
                }else if("生鲜频道".equals(chanCouList.get(i).get("cname").toString())){
                    cShengxianCou = chanCouList.get(i).get("sku_onselling").toString();
                }else if("日用百货".equals(chanCouList.get(i).get("cname").toString())){
                    cRiyongCou = chanCouList.get(i).get("sku_onselling").toString();
                }
            }
            result.put("cTechanCou", cTechanCou);
            result.put("cShengxianCou", cShengxianCou);
            result.put("cRiyongCou", cRiyongCou);
        }
        return result;
    }

    @Override
    public Map<String, Object> selectSkuTop(String dd) {
        Map<String, Object> result = new HashMap<String, Object>();
        YouyiProductDao youyiDao = (YouyiProductDao) SpringHelper.getBean(YouyiProductDao.class.getName());
        List<Map<String, Object>> proList = youyiDao.getSkuTop(dd);
        List proName = new ArrayList();
        List proCount = new ArrayList();
        List proChannel = new ArrayList();
        if (proList!=null&&!proList.isEmpty()) {

            for (int i = 0; i < proList.size(); i++) {
                proCount.add(proList.get(i).get("sku_onselling").toString());
                proName.add(proList.get(i).get("content_name").toString());
                proChannel.add(proList.get(i).get("role_name_chanel").toString());
            }
            JSONArray josnProName = (JSONArray) JSONArray.fromObject(proName);
            JSONArray josnProCount = (JSONArray) JSONArray.fromObject(proCount);
            JSONArray josnProChannel = (JSONArray) JSONArray.fromObject(proChannel);

            result.put("josnProName", josnProName);
            result.put("josnProCount", josnProCount);
            result.put("josnProChannel", josnProChannel);
        }
        return result;
    }

    @Override
    public Map<String, Object> selectYouyiGmv(String dd) {
        Map<String, Object> result = new HashMap<String, Object>();
        YouyiProductDao youyiDao = (YouyiProductDao) SpringHelper.getBean(YouyiProductDao.class.getName());
        List<Map<String, Object>> gmvList = new ArrayList<Map<String, Object>>();
        gmvList = youyiDao.getYouyiGmv(dd);

        if (gmvList != null && gmvList.size() > 0) {
            String aa = gmvList.get(0).get("ordergmv").toString();

            BigDecimal db = new BigDecimal(aa);

            String ii = db.toPlainString();

           /* System.out.println(ii);
            DecimalFormat df = new DecimalFormat("###############0.00000000#");// 最多保留几位小数，就用几个#，最少位就用0来确定

            Double bb = (double)gmvList.get(0).get("ordergmv");

            String s = df.format(bb);

            System.out.println(s);*/
            result.put("orderCou", Double.parseDouble(gmvList.get(0).get("ordercou").toString()));   //上线SKU
            result.put("youyiGmv", db.toPlainString());//动销SKU
        } else {
            result.put("orderCou", "0");   //上线SKU
            result.put("youyiGmv", "0");//动销SKU
        }
        return result;
    }
}
