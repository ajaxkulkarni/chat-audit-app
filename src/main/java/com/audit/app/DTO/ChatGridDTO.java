package com.audit.app.DTO;

import java.io.Serializable;
import java.util.Date;

public class ChatGridDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	public int id;
	public Date date;
	public String day;
	public int reason_id;
	public String reason_name;
	public Integer department_id;
	public String workgroup;
	public String locationName;

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getReason_id() {
		return reason_id;
	}

	public void setReason_id(int reason_id) {
		this.reason_id = reason_id;
	}

	public String getReason_name() {
		return reason_name;
	}

	public void setReason_name(String reason_name) {
		this.reason_name = reason_name;
	}

	public Integer getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}

	public String getWorkgroup() {
		return workgroup;
	}

	public void setWorkgroup(String workgroup) {
		this.workgroup = workgroup;
	}

}
