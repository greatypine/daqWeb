package com.cnpc.pms.base.common.manager;

import com.cnpc.pms.base.common.model.ClientSuggestObject;
import com.cnpc.pms.base.manager.IManager;

/**
 * Contains non-entity related, common utility services.
 * 
 * Copyright(c) 2010 IBM Corporation, http://www.ibm.com
 * 
 * @author lushu
 * @since Jul 22, 2013
 */
public interface UtilityManager extends IManager {
	String getSuggestion(ClientSuggestObject suggestObj);
	
	/**
	 * 
	* @Title: getPYIndexStr  
	* @Description: TODO 中文首字母 
	* 2018年5月24日
	* @param @param strChinese
	* @param @param bUpCase
	* @param @return      
	* @return String 
	* @throws
	* @author gbl
	 */
	public String getPYIndexStr(String strChinese, boolean bUpCase);
}
