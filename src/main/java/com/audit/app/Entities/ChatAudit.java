package com.audit.app.Entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.audit.app.DTO.ChatAuditDTO;

@Entity
@Table(name = "audit")
@SuppressWarnings("serial")
public class ChatAudit implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "status", length = 15, nullable = false)
	private String status;

	@Column(name = "session_id", length = 11, nullable = false)
	private int sessionId;

	@Column(name = "session_timestamp", nullable = false)
	private Date sessionTimestamp;

	@Column(name = "user_uid", length = 255, nullable = false)
	private String userUid;

	@Column(name = "reason_name", length = 255, nullable = false)
	private String reasonName;

	@Column(name = "dept_name", length = 255, nullable = false)
	private String deptName;

	@Column(name = "loc_name", length = 255, nullable = false)
	private String locName;

	@Column(name = "user_name", length = 255, nullable = false)
	private String userName;
	
	public ChatAudit(ChatAuditDTO dto) {
		setDeptName(dto.getDeptName());
		setLocName(dto.getLocName());
		setReasonName(dto.getReasonName());
		setSessionId(dto.getSessionId());
		setSessionTimestamp(new Date());
		setStatus(dto.getStatus());
		setUserName(dto.getUserName());
		setUserUid(dto.getUserUid());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public Date getSessionTimestamp() {
		return sessionTimestamp;
	}

	public void setSessionTimestamp(Date sessionTimestamp) {
		this.sessionTimestamp = sessionTimestamp;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
