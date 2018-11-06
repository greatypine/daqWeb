package com.cnpc.pms.reportFiledown.entity;

import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.personal.dao.MassOrderDao;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import java.util.*;

public class ExportRunable implements Runnable  {
    private Log log = null;

    private static Logger logger = Logger.getLogger(ExportRunable.class);

    private List<Map<String, Object>> list;
    private  String starts;
    private String fileName;
    private MassOrderDto massOrderDto;
    private Boolean isDataCube ;
    private MassOrderDao massOrderDao;
    private TReportFiledown tReportFiledown;
    public ExportRunable(List<Map<String, Object>> list, String starts, String fileName, Log log, MassOrderDto massOrderDto, Boolean isDataCube,TReportFiledown tReportFiledown,MassOrderDao massOrderDao) {
        this.list =list;
        this.fileName = fileName;
        this.starts=starts;
        this.massOrderDto = massOrderDto;
        this.isDataCube = isDataCube;
        this.log = log;
        this.tReportFiledown = tReportFiledown;
        this.massOrderDao = massOrderDao;
    }
    @Override
    public void run() {
        Long id = tReportFiledown.getId();
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        if(starts.equals("1")){
            httpClientUtils.getDataTable(list,massOrderDto,fileName,massOrderDao,id);
        }
    }


}
