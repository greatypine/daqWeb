package com.cnpc.pms.workflow.dto;

import com.cnpc.pms.base.dto.PMSDTO;

/**
 * 封装两个字符串来返回
 * 
 * @author liujunsong
 * 
 */
public class WFToDoSheetIds extends PMSDTO {
	/**
	 * 逗号分割的多个IDS
	 */
	private String sheetIds;
	/**
	 * 逗号分割的多个URLS
	 */
	private String urls;
	public String getSheetIds() {
		return sheetIds;
	}
	public void setSheetIds(String sheetIds) {
		this.sheetIds = sheetIds;
	}
	public String getUrls() {
		return urls;
	}
	public void setUrls(String urls) {
		this.urls = urls;
	}

}
