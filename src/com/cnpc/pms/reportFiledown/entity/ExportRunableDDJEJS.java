package com.cnpc.pms.reportFiledown.entity;


import com.cnpc.pms.platform.dao.OrderAmountDao;
import com.cnpc.pms.platform.entity.OrderAmountDto;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import java.util.*;

public class ExportRunableDDJEJS implements Runnable  {
    private Log log = null;

    private static Logger logger = Logger.getLogger(ExportRunableDDJEJS.class);

    private  String starts;
    private String fileName;
    private OrderAmountDto orderAmountDto;
    private Boolean isDataCube ;
    private OrderAmountDao orderAmountDao;
    private TReportFiledown tReportFiledown;
    public ExportRunableDDJEJS( String starts, String fileName, Log log, OrderAmountDto orderAmountDto, Boolean isDataCube, TReportFiledown tReportFiledown, OrderAmountDao orderAmountDao) {
        this.fileName = fileName;
        this.starts=starts;
        this.orderAmountDto = orderAmountDto;
        this.isDataCube = isDataCube;
        this.log = log;
        this.tReportFiledown = tReportFiledown;
        this.orderAmountDao = orderAmountDao;
    }

    @Override
    public void run() {
        Long id = tReportFiledown.getId();
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        httpClientUtils.getAmountOrderTable(orderAmountDto,fileName,orderAmountDao,id);
    }


}
