package com.audit.app.DTO;

import java.io.Serializable;
import java.util.Date;

public class ChatAuditDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public int id;
	public String status;
	public int sessionId;
	public Date sessionTimestamp;
	public String userUid;
	public String reasonName;
	public String deptName;
	public String locName;
	public String userName;
	public String userEmail;
	public String sessionClosure;
	public String sessionAttributes;

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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getSessionClosure() {
		return sessionClosure;
	}

	public void setSessionClosure(String sessionClosure) {
		this.sessionClosure = sessionClosure;
	}

	public String getSessionAttributes() {
		return sessionAttributes;
	}

	public void setSessionAttributes(String sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}

}
