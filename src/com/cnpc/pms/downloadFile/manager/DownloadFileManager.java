package com.cnpc.pms.downloadFile.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.downloadFile.entity.DownLoadDto;

import java.util.List;
import java.util.Map;

public interface DownloadFileManager extends IManager {
    //查询列表
    public Map<String, Object> queryDownloadFile(DownLoadDto downoadDto, PageInfo pageInfo);
    /**
     *
     * TODO  查询已开店的所有城市
     * 2018年10月26日
     * @author wuxinxin
     * @return
     */
    public List<Map<String,Object>> selectAllCity();
}
