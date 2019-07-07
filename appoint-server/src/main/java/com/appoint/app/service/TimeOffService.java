/**
 * 
 */
package com.appoint.app.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appoint.app.bean.TimeOffBean;
import com.appoint.app.core.CommonService;
import com.appoint.app.model.TimeOff;
import com.appoint.app.repository.TimeOffRepository;

/**
 * @author Shubham
 *
 */
@Service
public class TimeOffService extends CommonService {

	@Autowired
	TimeOffRepository timeOffRepository;
	
	public List<TimeOffBean> getCurrentTimeOffs() {
		List<TimeOff> timeOffList =  timeOffRepository.getCurrentTimeOffs();
		List<TimeOffBean> beanList = new ArrayList<TimeOffBean>();
		for(TimeOff timeOff : timeOffList) {
			TimeOffBean bean = createBean(timeOff);
			beanList.add(bean);
		}
		return beanList;
	}
	
	public List<TimeOffBean> getAllTimeOffs() {
		List<TimeOff> timeOffList =  timeOffRepository.getAllTimeOffs();
		List<TimeOffBean> beanList = new ArrayList<>();
		for(TimeOff timeOff : timeOffList) {
			TimeOffBean bean = createBean(timeOff);
			beanList.add(bean);
		}
		return beanList;
	}
	
	@Transactional
	public boolean modifyTimeOff(TimeOffBean timeOffBean) {
		TimeOff timeOff = timeOffRepository.loadTimeOff(timeOffBean.getTimeOffId());
		timeOff.setDescription(timeOffBean.getDescription());
		timeOff.setEndDate(timeOffBean.getEndDate());
		timeOff.setEndTime(getTimeInDBFormat(timeOffBean.getEndTime()));
		timeOff.setFullDay(timeOffBean.isFullDay());
		timeOff.setStartDate(timeOffBean.getStartDate());
		timeOff.setStartTime(getTimeInDBFormat(timeOffBean.getStartTime()));
		timeOffRepository.modifyTimeOff(timeOff);
		return true;
	}
	
	@Transactional
	public boolean createTimeOff(TimeOffBean timeOffBean) {
		TimeOff timeOff = createModel(timeOffBean);
		timeOffRepository.createTimeOff(timeOff);
		return true;
	}
	
	@Transactional
	public boolean deleteTimeOff(Long timeOffId) {
		timeOffRepository.deleteTimeOff(timeOffId);
		return true;
	}
	
	public List<String> getFullDayTimeoff(){
		List<TimeOff> timeOffList = timeOffRepository.getTimeOffsFromDate(new Date());
		List<String> dateList = new ArrayList<>();
		for(TimeOff timeoff : timeOffList) {
			if((timeoff.getEndDate().getTime() - timeoff.getStartDate().getTime())/ (24 * 60 * 60 * 1000) >= 1) {
				if(!(new SimpleDateFormat("HH:mm:ss").format(timeoff.getStartTime())).equals("09:00:00")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(timeoff.getStartDate());
					cal.add(Calendar.DATE, 1);
					timeoff.setStartDate(cal.getTime());
				}
				if(!(new SimpleDateFormat("HH:mm:ss").format(timeoff.getEndTime())).equals("19:00:00")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(timeoff.getEndDate());
					cal.add(Calendar.DATE, -1);
					timeoff.setEndDate(cal.getTime());
				}
				dateList.addAll(getListOfDaysBetweenTwoDates(timeoff.getStartDate(), timeoff.getEndDate()));
			}
		}
		return dateList;
	}
	
	public List<TimeOffBean> getTimeOffByMonth(String month, String year){
		List<TimeOff> timeOffList = timeOffRepository.getTimeOffByMonth(month, year);
		List<TimeOffBean> beanList = new ArrayList<>();
		for(TimeOff timeOff : timeOffList) {
			TimeOffBean bean = createBean(timeOff);
			beanList.add(bean);
		}
		return beanList;
	}
	
	private TimeOffBean createBean(TimeOff timeOff){
		TimeOffBean timeOffBean = new TimeOffBean();
		timeOffBean.setDescription(timeOff.getDescription());
		timeOffBean.setEndDate(timeOff.getEndDate());
		timeOffBean.setEndTime(getTimeInFormat(timeOff.getEndTime()));
		timeOffBean.setFullDay(timeOff.isFullDay());
		timeOffBean.setStartDate(timeOff.getStartDate());
		timeOffBean.setStartTime(getTimeInFormat(timeOff.getStartTime()));
		timeOffBean.setTimeOffId(timeOff.getTimeOffId());
		return timeOffBean;
	}
	
	private Date createTime(String timestr) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date time = null;
		try {
			sdf.parse(timestr);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return time;
	}
	
	private TimeOff createModel(TimeOffBean timeOffBean) {
		TimeOff timeOff = new TimeOff();
		if(timeOffBean.getTimeOffId().longValue() != 0)
			timeOff.setTimeOffId(timeOffBean.getTimeOffId());
		timeOff.setDescription(timeOffBean.getDescription());
		timeOff.setEndDate(timeOffBean.getEndDate());
		timeOff.setEndTime(getTimeInDBFormat(timeOffBean.getEndTime()));
		timeOff.setFullDay(timeOffBean.isFullDay());
		timeOff.setStartDate(timeOffBean.getStartDate());
		timeOff.setStartTime(getTimeInDBFormat(timeOffBean.getStartTime()));
		return timeOff;
	}
	
	private List<String> getListOfDaysBetweenTwoDates(Date startDate, Date endDate) {
	    List<String> result = new ArrayList<>();
	    Calendar start = Calendar.getInstance();
	    start.setTime(startDate);
	    Calendar end = Calendar.getInstance();
	    end.setTime(endDate);
	    end.add(Calendar.DAY_OF_YEAR, 1); //Add 1 day to endDate to make sure endDate is included into the final list
	    while (start.before(end)) {
	        result.add(new SimpleDateFormat("yyyy-MM-dd").format(start.getTime()));
	        start.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return result;
	}
	
}
