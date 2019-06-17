/**
 * 
 */
package com.appoint.app.service;

import java.util.ArrayList;
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
		List<TimeOffBean> beanList = new ArrayList<TimeOffBean>();
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
	
	public List<TimeOffBean> getTimeOffByMonth(String month, String year){
		List<TimeOff> timeOffList = timeOffRepository.getTimeOffByMonth(month, year);
		List<TimeOffBean> beanList = new ArrayList<TimeOffBean>();
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
}
