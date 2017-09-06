package com.audit.app.DTO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkgroupWithNoLocation implements Serializable {
	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

	public int id;
	public String department_name;
	public String department_description;
	public Date created_date;
	public String status;
	public String abbr_name;
	public String primary_contact;
	public String secondary_contact;
	public Date date;
	public String strCreatedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getDepartment_description() {
		return department_description;
	}

	public void setDepartment_description(String department_description) {
		this.department_description = department_description;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAbbr_name() {
		return abbr_name;
	}

	public void setAbbr_name(String abbr_name) {
		this.abbr_name = abbr_name;
	}

	public String getPrimary_contact() {
		return primary_contact;
	}

	public void setPrimary_contact(String primary_contact) {
		this.primary_contact = primary_contact;
	}

	public String getSecondary_contact() {
		return secondary_contact;
	}

	public void setSecondary_contact(String secondary_contact) {
		this.secondary_contact = secondary_contact;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStrCreatedDate() {
		return format.format(created_date);
	}

	public void setStrCreatedDate(String strCreatedDate) {
		this.strCreatedDate = strCreatedDate;
	}

}
