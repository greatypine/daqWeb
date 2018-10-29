package com.cnpc.pms.downloadFile.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.downloadFile.entity.DownLoadDto;

import java.util.Map;

public interface DownloadFileDao extends IDAO {

   public Map<String,Object> getDownloadFile(DownLoadDto downloadDto, PageInfo pageInfo);
}
