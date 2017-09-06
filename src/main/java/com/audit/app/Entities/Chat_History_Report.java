package com.audit.app.Entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
public class Chat_History_Report {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "created_date", nullable = false)
	private Date created_date;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "reason_id", nullable = false)
	private int reason_id;

	@Column(name = "department_id", nullable = false)
	private int department_id;

	@Column(name = "Attribute_Id", nullable = false)
	private int Attribute_Id;

	@Column(name = "Attribute_Name", nullable = false)
	private String Attribute_Name;

	@Column(name = "reason_name", nullable = false)
	private String reason_name;

	@Column(name = "department_name", nullable = false)
	private String department_name;

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
