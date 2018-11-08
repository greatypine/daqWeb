package com.cnpc.pms.reportFiledown.entity;

import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.dynamic.entity.MassOrderItemDto;
import com.cnpc.pms.personal.dao.MassOrderDao;
import com.cnpc.pms.personal.dao.MassOrderItemDao;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class ExportRunableSPXSDA implements Runnable  {
    private Log log = null;

    private static Logger logger = Logger.getLogger(ExportRunableSPXSDA.class);

    private String fileName;
    private MassOrderItemDto massOrderDto;
    private Boolean isDataCube ;
    private MassOrderItemDao massOrderItemDao;
    private TReportFiledown tReportFiledown;
    public ExportRunableSPXSDA( String fileName, Log log, MassOrderItemDto massOrderDto, Boolean isDataCube, TReportFiledown tReportFiledown, MassOrderItemDao massOrderItemDao) {
        this.fileName = fileName;
        this.massOrderDto = massOrderDto;
        this.isDataCube = isDataCube;
        this.log = log;
        this.tReportFiledown = tReportFiledown;
        this.massOrderItemDao = massOrderItemDao;
    }

    @Override
    public void run() {
        Long id = tReportFiledown.getId();
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        httpClientUtils.getDataTableSPXSDA(massOrderDto,fileName,massOrderItemDao,id);
    }


}
