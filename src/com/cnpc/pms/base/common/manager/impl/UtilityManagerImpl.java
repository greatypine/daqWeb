package com.cnpc.pms.base.common.manager.impl;

import java.util.List;
import java.util.Random;

import com.cnpc.pms.base.common.manager.UtilityManager;
import com.cnpc.pms.base.common.model.ClientSuggestObject;
import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.ISort;
import com.cnpc.pms.base.paging.SortFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.paging.operator.ConditionOperator;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.base.util.StrUtil;

public class UtilityManagerImpl extends BaseManagerImpl implements UtilityManager {

	public String getSuggestion(ClientSuggestObject suggestObj) {
		String[] properties = suggestObj.getProperties();
		String resultString = "";
		if (properties != null && properties.length > 0) {
			PageInfo page = new PageInfo();
			page.setRecordsPerPage(suggestObj.getReturnSize());
			String formattedKeyword = formatQueryKeyword(suggestObj.getKeyword());
			IFilter filter = FilterFactory.getFilter(properties[0], formattedKeyword, ConditionOperator.LIKE);
			String selectItems = properties[0];
			ISort sort = SortFactory.createSort(properties[0]);
			for (int i = 1; i < properties.length; i++) {
				filter = filter.appendOr(FilterFactory.getFilter(properties[i], formattedKeyword,
						ConditionOperator.LIKE));
				selectItems = selectItems + "," + properties[i];
			}
			IManager manager = SpringHelper.getManagerByClass(SpringHelper.getManagerNameByEntity(suggestObj
					.getClassName()));
			List<?> results = getDao().queryProperties(manager.getEntityClass(), filter, sort, page, selectItems);
			if (results != null) {
				for (Object result : results) {
					String tmp = "";
					if (result instanceof Object[]) {
						tmp = StrUtil.joinArray((Object[]) result, ",");
					} else {
						tmp = result.toString();
					}
					resultString = resultString + tmp + "\r\n";
				}
			}
		}
		return resultString;
	}

	/**
	 * Format query keyword.
	 * 
	 * @param keyword
	 *            the keyword
	 * @return the string
	 */
	private String formatQueryKeyword(String keyword) {
		if (StrUtil.isEmpty(keyword)) {
			return "%%";
		}
		if (!keyword.startsWith("%")) {
			keyword = "%" + keyword;
		}
		if (!keyword.endsWith("%")) {
			keyword = keyword + "%";
		}
		return keyword;
	}

	@Override
	public String getPYIndexStr(String strChinese, boolean bUpCase) {
		 try{  
			  
	           StringBuffer buffer = new StringBuffer();  
	  
	           byte b[] = strChinese.getBytes("GBK");//把中文转化成byte数组  
	  
	           for(int i = 0; i < b.length; i++){  
	  
	               if((b[i] & 255) > 128){  
	  
	                   int char1 = b[i++] & 255;  
	  
	                   char1 <<= 8;//左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方  
	  
	                   int chart = char1 + (b[i] & 255);  
	  
	                   buffer.append(getPYIndexChar((char)chart, bUpCase));  
	  
	                   continue;  
	  
	               }  
	  
	               char c = (char)b[i];  
	  
	               if(!Character.isJavaIdentifierPart(c))//确定指定字符是否可以是 Java 标识符中首字符以外的部分。  
	  
	                   c = 'A';  
	  
	               buffer.append(c);  
	  
	           }  
	  
	           return buffer.toString();  
	  
	       }catch(Exception e){  
	  
	           System.out.println((new StringBuilder()).append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519").append(e.getMessage()).toString());  
	  
	       }  
	  
	       return null;  
	  
	}
	
	
	
	 private  char getPYIndexChar(char strChinese, boolean bUpCase){  
		  
	       int charGBK = strChinese;  
	  
	       char result;  
	  
	       if(charGBK >= 45217 && charGBK <= 45252)  
	  
	           result = 'A';  
	  
	       else  
	  
	       if(charGBK >= 45253 && charGBK <= 45760)  
	  
	           result = 'B';  
	  
	       else  
	  
	       if(charGBK >= 45761 && charGBK <= 46317)  
	  
	           result = 'C';  
	  
	       else  
	  
	       if(charGBK >= 46318 && charGBK <= 46825)  
	  
	           result = 'D';  
	  
	       else  
	  
	       if(charGBK >= 46826 && charGBK <= 47009)  
	  
	           result = 'E';  
	  
	       else  
	  
	       if(charGBK >= 47010 && charGBK <= 47296)  
	  
	           result = 'F';  
	  
	       else  
	  
	       if(charGBK >= 47297 && charGBK <= 47613)  
	  
	           result = 'G';  
	  
	       else  
	  
	       if(charGBK >= 47614 && charGBK <= 48118)  
	  
	           result = 'H';  
	  
	       else  
	  
	       if(charGBK >= 48119 && charGBK <= 49061)  
	  
	           result = 'J';  
	  
	       else  
	  
	       if(charGBK >= 49062 && charGBK <= 49323)  
	  
	           result = 'K';  
	  
	       else  
	  
	       if(charGBK >= 49324 && charGBK <= 49895)  
	  
	           result = 'L';  
	  
	       else  
	  
	       if(charGBK >= 49896 && charGBK <= 50370)  
	  
	           result = 'M';  
	  
	       else  
	  
	       if(charGBK >= 50371 && charGBK <= 50613)  
	  
	           result = 'N';  
	  
	       else  
	  
	       if(charGBK >= 50614 && charGBK <= 50621)  
	  
	           result = 'O';  
	  
	       else  
	  
	       if(charGBK >= 50622 && charGBK <= 50905)  
	  
	           result = 'P';  
	  
	       else  
	  
	       if(charGBK >= 50906 && charGBK <= 51386)  
	  
	           result = 'Q';  
	  
	       else  
	  
	       if(charGBK >= 51387 && charGBK <= 51445)  
	  
	           result = 'R';  
	  
	       else  
	  
	       if(charGBK >= 51446 && charGBK <= 52217)  
	  
	           result = 'S';  
	  
	       else  
	  
	       if(charGBK >= 52218 && charGBK <= 52697)  
	  
	           result = 'T';  
	  
	       else  
	  
	       if(charGBK >= 52698 && charGBK <= 52979)  
	  
	           result = 'W';  
	  
	       else  
	  
	       if(charGBK >= 52980 && charGBK <= 53688)  
	  
	           result = 'X';  
	  
	       else  
	  
	       if(charGBK >= 53689 && charGBK <= 54480)  
	  
	           result = 'Y';  
	  
	       else  
	  
	       if(charGBK >= 54481 && charGBK <= 55289)  
	  
	           result = 'Z';  
	  
	       else  
	  
	           result = (char)(65 + (new Random()).nextInt(25));  
	  
	       if(!bUpCase)  
	  
	           result = Character.toLowerCase(result);  
	  
	       return result;  
	  
	   }  
}
