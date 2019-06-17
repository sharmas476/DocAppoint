/**
 * 
 */
package com.appoint.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appoint.app.bean.AppointmentBean;
import com.appoint.app.core.CommonService;
import com.appoint.app.model.Appointment;
import com.appoint.app.repository.AppointmentRepository;

/**
 * @author Shubham
 *
 */
@Service
public class AppointmentService extends CommonService {
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Transactional
	public List<AppointmentBean> getAppointmentByDate(Date date){
		List<Appointment> list = appointmentRepository.getAppointmentByDate(date);
		List<AppointmentBean> beanList = new ArrayList<AppointmentBean>();
		for(Appointment appointment:list) {
			AppointmentBean appointmentBean = createBean(appointment);
			beanList.add(appointmentBean);
		}
		return beanList;
	}
	
	@Transactional
	public boolean modifyAppointment(AppointmentBean appointmentBean) {
		Appointment appointment = appointmentRepository.loadAppointment(appointmentBean.getAppointmentId());
		appointment.setDate(appointmentBean.getDate());
		appointment.setTime(getTimeInDBFormat(appointmentBean.getTime()));
		appointmentRepository.update(appointment);
		return true;
	}
	
	private AppointmentBean createBean(Appointment appointment) {
		AppointmentBean appointmentBean = new AppointmentBean();
		appointmentBean.setAppointmentId(appointment.getAppointmentId());
		appointmentBean.setDate(appointment.getDate());
		appointmentBean.setTime(getTimeInFormat(appointment.getTime()));
		appointmentBean.setDescription(appointment.getDescription());
		appointmentBean.setName(appointment.getPatients().getName());
		return appointmentBean;
	}

}
