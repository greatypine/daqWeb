package com.cnpc.pms.downloadFile.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.downloadFile.entity.DownLoadDto;

import java.util.List;
import java.util.Map;

public interface DownloadFileManager extends IManager {
    /**
     *
     * TODO  查询下载文档列表-----事业群、财务中心
     * 2018年10月26日
     * @author wuxinxin
     * @return
     */
    public Map<String, Object> queryDownloadFile(DownLoadDto downoadDto, PageInfo pageInfo);
    /**
     *
     * TODO  查询城市-----事业群、财务中心
     * 2018年10月26日
     * @author wuxinxin
     * @return
     */
    public List<Map<String,Object>> selectAllCity();


    /**
     *
     * TODO  查询下载文档列表-----事业群、财务中心
     * 2018年10月26日
     * @author wuxinxin
     * @return
     */
    public Map<String, Object> queryOperDownloadFile(DownLoadDto downoadDto, PageInfo pageInfo);

    /**
     * TODO  查询城市-----运营
     * @return
     */
    public List<Map<String,Object>> selectOperAllCity();
}
