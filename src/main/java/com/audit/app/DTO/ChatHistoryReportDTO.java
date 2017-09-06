package com.audit.app.DTO;

import java.io.Serializable;
import java.util.Date;

public class ChatHistoryReportDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public int id;
	public Date created_date;
	public String status;
	public int reason_id;
	public int department_id;
	public int Attribute_Id;
	public String Attribute_Name;
	public String reason_name;
	public String department_name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getReason_id() {
		return reason_id;
	}

	public void setReason_id(int reason_id) {
		this.reason_id = reason_id;
	}

	public int getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}

	public int getAttribute_Id() {
		return Attribute_Id;
	}

	public void setAttribute_Id(int attribute_Id) {
		Attribute_Id = attribute_Id;
	}

	public String getAttribute_Name() {
		return Attribute_Name;
	}

	public void setAttribute_Name(String attribute_Name) {
		Attribute_Name = attribute_Name;
	}

	public String getReason_name() {
		return reason_name;
	}

	public void setReason_name(String reason_name) {
		this.reason_name = reason_name;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

}
