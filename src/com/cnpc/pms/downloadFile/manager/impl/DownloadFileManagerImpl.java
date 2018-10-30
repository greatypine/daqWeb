package com.cnpc.pms.downloadFile.manager.impl;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.downloadFile.dao.DownloadFileDao;
import com.cnpc.pms.downloadFile.entity.DownLoadDto;
import com.cnpc.pms.downloadFile.manager.DownloadFileManager;
import com.cnpc.pms.dynamic.dao.DynamicDao;
import com.cnpc.pms.personal.entity.DistCity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadFileManagerImpl  extends BizBaseCommonManager implements DownloadFileManager {
    @Override
    public Map<String, Object> queryDownloadFile(DownLoadDto downoadDto,PageInfo pageInfo) {
        Map<String, Object> result = new HashMap<String,Object>();
        DownloadFileDao downloadFileDao = (DownloadFileDao)SpringHelper.getBean(DownloadFileDao.class.getName());
        try {
            result= downloadFileDao.getDownloadFile(downoadDto, pageInfo);

            if("全国".equals(downoadDto.getTarget())){
                result.put("seltag","country");
                result.put("selcity","country");
            }else if("日报".equals(downoadDto.getTarget())){
                result.put("seltag","daily");
                result.put("selcity",downoadDto.getCityCode());
            }else{
                result.put("seltag","month");
                result.put("selcity",downoadDto.getCityCode());
            }
            result.put("seltime",downoadDto.getFiletime());
            result.put("status","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status","fail");
            return result;
        }
        return result;

    }

    @Override
    public List<Map<String, Object>> selectAllCity() {

        //查询用户城市
        UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
		List<DistCity> citys = userManager.getCurrentUserCity();
        DynamicDao dynamicDao = (DynamicDao)SpringHelper.getBean(DynamicDao.class.getName());
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> relist = new ArrayList<Map<String,Object>>();
        try {
            list = dynamicDao.selectAllCity();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        for(int i=0;i<list.size();i++){
            for (DistCity city : citys) {
                if(list.get(i).get("citycode").toString().equals(city.getCitycode())){
                    relist.add(list.get(i));
                }
            }
        }
        return relist;
    }
}
