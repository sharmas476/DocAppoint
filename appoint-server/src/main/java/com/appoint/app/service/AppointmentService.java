/**
 * 
 */
package com.appoint.app.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appoint.app.bean.AppointmentBean;
import com.appoint.app.core.CommonService;
import com.appoint.app.core.Constants;
import com.appoint.app.message.response.ResponseMessage;
import com.appoint.app.model.Appointment;
import com.appoint.app.model.Patients;
import com.appoint.app.model.TimeOff;
import com.appoint.app.model.User;
import com.appoint.app.repository.AppointmentRepository;
import com.appoint.app.repository.PatientRepository;
import com.appoint.app.repository.TimeOffRepository;
import com.appoint.app.repository.UserRepository;

/**
 * @author Shubham
 *
 */
@Service
public class AppointmentService extends CommonService {
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	TimeOffRepository timeOffRepository;
	
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
	public List<AppointmentBean> getAppointmentByPhone(){
		List<Appointment> appointmentList = new ArrayList<Appointment>();
		List<AppointmentBean> appointmentBeanList = new ArrayList<AppointmentBean>();
		Optional<User> optional = userRepository.findByPhone(getUsername());
		User user = optional.get();
		for(Patients patient : user.getPatient()) {
			appointmentList.addAll(patient.getAppointment());
		}
		Collections.sort(appointmentList, new Comparator<Appointment>() {
			  public int compare(Appointment o1, Appointment o2) {
			      if (o1.getDate() == null || o2.getDate() == null)
			        return 0;
			      return o1.getDate().compareTo(o2.getDate());
			  }
			});
		for(Appointment appointment: appointmentList) {
			AppointmentBean bean = createBean(appointment);
			appointmentBeanList.add(bean);
		}
		return appointmentBeanList;
	}
	
	@Transactional
	public boolean deletePatientAppointment(Long appointmentId) {
		appointmentRepository.deleteAppointment(appointmentId);
		return true;
	}
	
	@Transactional
	public boolean modifyAppointment(AppointmentBean appointmentBean) {
		Appointment appointment = appointmentRepository.loadAppointment(appointmentBean.getAppointmentId());
		appointment.setDate(appointmentBean.getDate());
		appointment.setTime(getTimeInDBFormat(appointmentBean.getTime()));
		appointmentRepository.update(appointment);
		return true;
	}
	
	@Transactional
	public ResponseEntity<?> createAppointment(AppointmentBean appointmentBean){
		Appointment appointment = createModel(appointmentBean);
		List<TimeOff> timeOffList = timeOffRepository.getCurrentTimeOffs();
		//List<Appointment> appointmentList = appointmentRepository.getAppointmentByDate(new Date());
		if(isAppointmentValid(appointmentBean, timeOffList)) {
			//if(!isDuplicateAppointment(appointmentBean, appointmentList)) {
				appointmentRepository.createAppointment(appointment);
			/*
			 * }else { return new ResponseEntity<>(new
			 * ResponseMessage("Failure! This time slot is already booked. Please choose another"
			 * ), HttpStatus.FOUND); }
			 */
			return new ResponseEntity<>(new ResponseMessage("Success! New appointment created"), HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(new ResponseMessage("Failure! Doctor is on time off for the selected date time. Please change"), HttpStatus.CONFLICT);
		}
	}
	
	@Transactional
	public List<String> getTimeIntervals(String datestr){
		Date date = createDateForIntevals(datestr);
		List<TimeOff> timeOffList = timeOffRepository.getTimeOffsForDate(date);
		List<String> timeSlot = getDefaultTimeSlot();
		List<String> availableTimeSlot = new ArrayList<>();
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(String time : timeSlot) {
			String timestampStr = datestr + " " + time;
			Date timestamp = null;
			try {
				timestamp = df1.parse(timestampStr);
			}catch(ParseException e) {}
			boolean result = true;
			for(TimeOff timeOff : timeOffList) {
				Date from = createDateForTimeOff(timeOff.getStartDate(), timeOff.getStartTime());
				Date to = createDateForTimeOff(timeOff.getEndDate(), timeOff.getEndTime());
				if(timestamp.equals(from) || (timestamp.after(from) && timestamp.before(to))) {
					result = false;
					break;
				}
			}
			if(result) {
				availableTimeSlot.add(time);
			}
			
		}
		return availableTimeSlot;
		
	}
	
	private List<String> getDefaultTimeSlot(){
		List<String> dtList = new ArrayList<>();
		for(int i=9; i<20; i++) {
			if(!(i>=13 && i <=16)) {
				String j=i+"";
				j+=":00:00";
				dtList.add(j);		
			}
		
		}
		return dtList;
		
	}
	
	private boolean isDuplicateAppointment(AppointmentBean appointmentBean, List<Appointment> appointmentList) {
		boolean result = false;
		for(Appointment appointment : appointmentList) {
			Date booked = createDateForTimeOff(appointment.getDate(), appointment.getTime());
			Date appointmentTimestamp = createTimestampFromAppointment(appointmentBean);
			if(appointmentTimestamp.equals(booked) && appointment.getStatus().equals(Constants.SCHEDULED)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	private boolean isAppointmentValid(AppointmentBean appointmentBean,List<TimeOff> timeOffList) {
		Date appointmentTimestamp = createTimestampFromAppointment(appointmentBean);
		boolean result = true;
		for(TimeOff timeOff : timeOffList) {
			Date from = createDateForTimeOff(timeOff.getStartDate(), timeOff.getStartTime());
			Date to = createDateForTimeOff(timeOff.getEndDate(), timeOff.getEndTime());
			if(appointmentTimestamp.after(from) && appointmentTimestamp.before(to)) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	private Date createDateForTimeOff(Date date, Date time) {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df2 = new SimpleDateFormat("HH:mm aa");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm aa"); 
		String strDate = df1.format(date);
		String strTime = df2.format(time);
		String dateTimestr = strDate + " " + strTime;
		Date timestamp = null;
		try {
			timestamp = formatter.parse(dateTimestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}
	
	private Date createTimestampFromAppointment(AppointmentBean appointment){
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String  strDate = df1.format(appointment.getDate());
		String strTime = appointment.getTime();
		String dateTimestr = strDate + " " + strTime;
		Date timestamp = null;
		try {
			timestamp = formatter.parse(dateTimestr);
		} catch (ParseException e) {
			try {
				timestamp = formatter1.parse(dateTimestr);
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return timestamp;
	}
	
	private Date createTimeInterval(String dtstr) {
		SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
		Date retDate = null;
		try {
			retDate = df1.parse(dtstr);
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return retDate;
	}
	
	private Date createDateForIntevals(String date) {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Date retDate = null;
		try {
			retDate = df1.parse(date);
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return retDate;
	}
	
	private AppointmentBean createBean(Appointment appointment) {
		AppointmentBean appointmentBean = new AppointmentBean();
		appointmentBean.setAppointmentId(appointment.getAppointmentId());
		appointmentBean.setDate(appointment.getDate());
		appointmentBean.setStatus(appointment.getStatus());
		appointmentBean.setTime(getTimeInFormat(appointment.getTime()));
		appointmentBean.setDescription(appointment.getDescription());
		appointmentBean.setName(appointment.getPatients().getName());
		return appointmentBean;
	}

	private Appointment createModel(AppointmentBean appointmentBean) {
		Appointment appointment = new Appointment();
		appointment.setAppointmentId(appointmentBean.getAppointmentId());
		appointment.setDate(appointmentBean.getDate());
		appointment.setStatus(appointmentBean.getStatus());
		appointment.setTime(getTimeInDBFormat(appointmentBean.getTime()));
		appointment.setDescription(appointmentBean.getDescription());
		appointment.setPatients(patientRepository.loadPatient(appointmentBean.getPatientId()));
		return appointment;
	}
}
