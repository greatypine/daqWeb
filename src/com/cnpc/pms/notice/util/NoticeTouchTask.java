package com.cnpc.pms.notice.util;

import java.text.NumberFormat;
import java.util.List;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.notice.entity.Notice;
import com.cnpc.pms.notice.entity.NoticeReciver;
import com.cnpc.pms.notice.manager.NoticeManager;
import com.cnpc.pms.notice.manager.NoticeReciverManager;

/**
 * 公告设置触达
 * @author gbl
 *
 */
public class NoticeTouchTask implements Runnable{
	
	private String noticeNo;
	public NoticeTouchTask(String noticeNo){
		this.noticeNo = noticeNo;
	}
	@Override
	public void run() {
		NoticeReciverManager noticeReciverManager = (NoticeReciverManager)SpringHelper.getBean("noticeReciverManager");
		NoticeManager noticeManager = (NoticeManager)SpringHelper.getBean("noticeManager");
		
		try {
			
			NoticeReciver nr = new NoticeReciver();
			nr.setNoticeNo(noticeNo);
			
			List<NoticeReciver> allList = noticeReciverManager.getAllNotice(nr);
			nr.setIsRead(1);
			List<NoticeReciver> readList = noticeReciverManager.getAllNotice(nr);
			int allAmount=0,readAmount=0;
			if(allList!=null){
				allAmount = allList.size();
			}
			
			if(readList!=null){
				readAmount = readList.size();
			}
			if(allAmount>0){
				NumberFormat numberFormat = NumberFormat.getInstance();  
				   
		        // 设置精确到小数点后2位  
		   
		        numberFormat.setMaximumFractionDigits(0);  
		   
		        String touchRate = numberFormat.format((float) readAmount / (float) allAmount * 100);  
				Notice notice = new Notice();
				
				notice.setNoticeNo(noticeNo);
				notice.setTouchRate(Float.parseFloat(touchRate));
		        noticeManager.updateTouchRateOfNotice(notice);
		        System.out.println("更新公告触达率success---------"+noticeNo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("更新公告触达率error---------"+noticeNo);
		}
	}

	
	
}
