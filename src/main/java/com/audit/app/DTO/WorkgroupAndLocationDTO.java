package com.audit.app.DTO;

import java.io.Serializable;

public class WorkgroupAndLocationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public int id;
	public String department_name;
	public String location_name;
	public String address;
	public String location_description;
	public String primary_contact;

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

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation_description() {
		return location_description;
	}

	public void setLocation_description(String location_description) {
		this.location_description = location_description;
	}

	public String getPrimary_contact() {
		return primary_contact;
	}

	public void setPrimary_contact(String primary_contact) {
		this.primary_contact = primary_contact;
	}

}
