package com.audit.app.Entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.audit.app.DTO.ChatStatusAuditDTO;

@Entity
@Table(name = "status_audit")
@SuppressWarnings("serial")
public class StatusAudit implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "user_uid", length = 255, nullable = false)
	private String user_uid;

	@Column(name = "status", length = 15, nullable = false)
	private String status;

	@Column(name = "status_time", nullable = false)
	private Date statusTime;
	
	public StatusAudit(ChatStatusAuditDTO dto) {
		setStatus(dto.getStatus());
		setStatusTime(new Date());
		setUser_uid(dto.getUser_uid());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_uid() {
		return user_uid;
	}

	public void setUser_uid(String user_uid) {
		this.user_uid = user_uid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}
}
