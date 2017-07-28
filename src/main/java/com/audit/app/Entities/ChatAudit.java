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

	@Column(name = "status", length = 15)
	private String status;

	@Column(name = "session_id", length = 11, nullable = false)
	private int sessionId;

	@Column(name = "session_timestamp", nullable = false)
	private Date sessionTimestamp;

	@Column(name = "user_uid", length = 255)
	private String userUid;

	@Column(name = "reason_name", length = 255)
	private String reasonName;

	@Column(name = "dept_name", length = 255)
	private String deptName;

	@Column(name = "loc_name", length = 255)
	private String locName;

	@Column(name = "user_name", length = 255)
	private String userName;
	
	@Column(name = "user_email", length = 100)
	private String user_email;
	
	@Column(name = "session_closure", length = 255)
	private String session_closure;
	
	@Column(name = "session_attributes", length = 255)
	private String session_attributes;
	
	public ChatAudit(ChatAuditDTO dto) {
		setDeptName(dto.getDeptName());
		setLocName(dto.getLocName());
		setReasonName(dto.getReasonName());
		setSessionId(dto.getSessionId());
		setSessionTimestamp(new Date());
		setStatus(dto.getStatus());
		setUserName(dto.getUserName());
		setUserUid(dto.getUserUid());
		setUser_email(dto.getUserEmail());
		setSession_attributes(dto.getSessionAttributes());
		setSession_closure(dto.getSessionClosure());
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

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getSession_closure() {
		return session_closure;
	}

	public void setSession_closure(String session_closure) {
		this.session_closure = session_closure;
	}

	public String getSession_attributes() {
		return session_attributes;
	}

	public void setSession_attributes(String session_attributes) {
		this.session_attributes = session_attributes;
	}
	
}
