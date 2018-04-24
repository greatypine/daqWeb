package com.cnpc.pms.worklog.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.worklog.dao.WorkLogCalendarDAO;
import com.cnpc.pms.worklog.dao.WorkLogUserCalendarDAO;
import com.cnpc.pms.worklog.dto.WorkLogUserCalendarDTO;
import com.cnpc.pms.worklog.entity.WorkLogCalendar;
import com.cnpc.pms.worklog.manager.WorkLogCalendarManager;
import com.cnpc.pms.worklog.manager.WorkLogUserCalendarManager;

public class WorkLogCalendarManagerImpl extends BaseManagerImpl implements
		WorkLogCalendarManager {

	/**
	 * 增加一条系统日期
	 * @param obj
	 */
	public void addWorkLogCalendar(WorkLogCalendar obj) {
		super.saveObject(obj);
	}

	public WorkLogCalendarDAO getDao() {
		return (WorkLogCalendarDAO) super.getDao();
	}
	/**
	 * 按照Id获取日历对象
	 * @param id
	 * @return
	 */
	public WorkLogCalendar getWorkLogCalendar(Long id) {

		return (WorkLogCalendar) super.getObject(id);
	}
	/**
	 * 保存日历对象，系统节假日设置时使用，
	 * <li>保存日历对象时要同步修改对应的用户日历是否工作日的设置
	 * @param obj
	 */
	public void saveWorkLogCalendar(WorkLogCalendar obj) {
		WorkLogCalendar dbObj = null;
		System.out.println(obj.getIsworkday());
		dbObj = (WorkLogCalendar) super.getObject(obj.getId());
		if (dbObj == null) {
			// System.out.println(dbObj.getId());
			dbObj = obj;
		} else {
			BeanUtils.copyProperties(obj, dbObj,
					new String[] { "id", "version" });
		}
		WorkLogUserCalendarDTO workLogUserCalendarDTO = new WorkLogUserCalendarDTO();
		workLogUserCalendarDTO.setCalendarDate(obj.getWorkday());
		workLogUserCalendarDTO.setWorkDayState(obj.getIsworkday());
		WorkLogUserCalendarManager workLogUserCalendarManager = (WorkLogUserCalendarManager) SpringHelper
				.getBean("workLogUserCalendarManager");
		super.saveObject(dbObj);
		//更新用户工作日历数据
		//workLogUserCalendarManager
				//.updateWorkLogUserCalendar(workLogUserCalendarDTO);
		WorkLogUserCalendarDAO workLogUserCalendarDAO =(WorkLogUserCalendarDAO)SpringHelper.getBean(WorkLogUserCalendarDAO.class.getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(dbObj.getWorkday()));
		workLogUserCalendarDAO.refreshAllWorkDayState(sdf.format(dbObj.getWorkday()));

	}
	/**
	 * 初始化本年用户日历
	 * @return
	 */
	public Boolean formatWorkLogCalendar() {
		int flag = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		String temp_str = c.get(Calendar.YEAR) + "-01-01";
		String temp_end = c.get(Calendar.YEAR) + "-12-31";
		int thisyear = c.get(Calendar.YEAR);
		c.set(c.get(Calendar.YEAR) + 1, 0, 1);
		c.add(Calendar.DAY_OF_YEAR, -1);
		System.out.println(c.get(Calendar.DAY_OF_YEAR));
		int days = c.get(Calendar.DAY_OF_YEAR);
		if (this.getDao().getYearExits(Integer.toString(thisyear),
				new Long(days)) == false) {
			this.getDao().formatCaleState(temp_str, temp_end);
		} else {
			flag = 1;
		}

		if (flag == 0) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 初始化明年日历
	 * @return
	 */
	public Boolean formatNextWorkLogCalendar() {
		int flag = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		int nextyear = (c.get(Calendar.YEAR) + 1);
		String temp_str = nextyear + "-01-01";
		String temp_end = nextyear + "-12-31";
		c.set(c.get(Calendar.YEAR) + 2, 0, 1);
		c.add(Calendar.DAY_OF_YEAR, -1);
		System.out.println(c.get(Calendar.DAY_OF_YEAR));
		int days = c.get(Calendar.DAY_OF_YEAR);
		if (this.getDao().getYearExits(Integer.toString(nextyear),
				new Long(days)) == false) {
			this.getDao().formatCaleState(temp_str, temp_end);

		} else {
			flag = 1;
		}

		if (flag == 0) {
			return false;
		} else {
			return true;
		}

	}
	/**
	 * 按照日期获取日历对象（节假日设置时使用)
	 * @param date
	 * @return
	 */
	public WorkLogCalendar getwWorkLogCalendarByDate(String date) {
		return this.getDao().getWorkLogCalendar(date);
	}

	/**
	 * 初始化10年的数据,2013-2022年
	 * @return
	 */
	public Boolean updateFormatTenYears() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 1; i <= 10; i++) {
			Calendar c = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			Calendar c3 = Calendar.getInstance();
			int year = 2010 + (i - 1);// 从2010年开始初始化
			String temp_str = year + "-01-01";
			c.set(year + 1, 0, 1);
			c.add(Calendar.DAY_OF_YEAR, -1);
			int days = c.get(Calendar.DAY_OF_YEAR);
			System.out.println(c.get(Calendar.DAY_OF_YEAR));
			try {
				Date newDate = sdf.parse(temp_str);
				System.out.println(newDate);
				c2.setTime(newDate);
				System.out.println(c2.get(Calendar.DAY_OF_MONTH));//获取该日是该月的第几天
				for (int j = 0; j < days; j++) {
					WorkLogCalendar workLogCalendar = new WorkLogCalendar();
					if (j == 0) {
						c2.set(Calendar.DAY_OF_MONTH, c2
								.get(Calendar.DAY_OF_MONTH));//把当前日设置成该月的第几天
						System.out.println(c2.getTime());//输出该日的日期
						System.out.println(c2.get(Calendar.DAY_OF_WEEK));//
					} else {
						c2.set(Calendar.DAY_OF_MONTH, c2
								.get(Calendar.DAY_OF_MONTH) + 1);
						System.out.println(c2.getTime());
						System.out.println(c2.get(Calendar.DAY_OF_WEEK));//获取当前时间是一个星期的第几天，星期日是第一天 星期一是第二天
					}
					workLogCalendar.setWorkday(c2.getTime());
					workLogCalendar.setDays(Integer.toString(c2
							.get(Calendar.DAY_OF_WEEK)));
					if (c2.get(Calendar.DAY_OF_WEEK) == 7
							|| c2.get(Calendar.DAY_OF_WEEK) == 1) {
						workLogCalendar.setType("周六日");
						workLogCalendar.setIsworkday(new Long(0));
					} else {
						workLogCalendar.setIsworkday(new Long(1));
					}
					//获取该日期所在年的第一天，封装进入workLogCalendar
					System.out.println(newDate);
					workLogCalendar.setYearBegin(newDate);
					//获取到该日期所在月份
					int month = c2.get(Calendar.MONTH) + 1;
					System.out.println(month);
					String monthBegin;
					if(month<10){
						 monthBegin=year+"-0"+month+"-01";
					}else{
						 monthBegin=year+"-"+month+"-01";
					}
					System.out.println(sdf.parse(monthBegin));
					workLogCalendar.setMonthBegin(sdf.parse(monthBegin));
					//获取该日期所在周的第一天
					int daysFromFirt;
					if(c2.get(Calendar.DAY_OF_WEEK)==1){
						System.out.println(c2.getTime());
						workLogCalendar.setWeekBegin(c2.getTime());
					}else{
						daysFromFirt=c2.get(Calendar.DAY_OF_WEEK)-1;
						Long hms=c2.getTime().getTime();
						Date dates=new Date(hms-daysFromFirt*60*60*24*1000);
						c3.setTime(dates);
						System.out.println(c3.getTime());
						workLogCalendar.setWeekBegin(c3.getTime());
					}
					
					
					//增加系统日历表
					this.addWorkLogCalendar(workLogCalendar);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return true;
	}
	
	public Boolean checkExistCalendar(){
		return this.getDao().checkExistCalendar();
	}

	public Boolean formatPersonWorkLogCalendar(User user) {
		int flag = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		String thisMonth;
		String thisDay;
		if(c.get(Calendar.MONTH)+1<10){
			thisMonth="0"+(c.get(Calendar.MONTH)+1);
		}else{
			thisMonth=""+(c.get(Calendar.MONTH)+1);
		}
		if(c.get(Calendar.DATE)<10){
			thisDay="0"+c.get(Calendar.DATE);
		}else{
			thisDay=""+c.get(Calendar.DATE);
		}
		String temp_begin=c.get(Calendar.YEAR)+"-"+thisMonth+"-"+thisDay;
		String temp_end = c.get(Calendar.YEAR) + "-12-31";
		this.getDao().formatPersonCaleState(user.getId(), temp_begin, temp_end);
		return true;
	}
	public void updatRefreshAllWorkDayState(String logDate){
		WorkLogUserCalendarDAO workLogUserCalendarDAO =(WorkLogUserCalendarDAO)SpringHelper.getBean(WorkLogUserCalendarDAO.class.getName());
		workLogUserCalendarDAO.refreshAllWorkDayState(logDate);
	}


}
