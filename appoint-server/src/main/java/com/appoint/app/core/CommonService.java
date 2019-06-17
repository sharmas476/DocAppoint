package com.appoint.app.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
	
	

	public String getUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username="";
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		return username;
	}
	
	public String getTimeInFormat(Date time) {
		String newTime="";
		newTime = new SimpleDateFormat("hh:mm aa").format(time);
		return newTime;
	}
	
	public Date getTimeInDBFormat(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
		Date newTime = null;
		try {
			newTime = sdf.parse(time);
		}catch(ParseException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return newTime;
	}
	
	
}
