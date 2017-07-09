package com.audit.app.DTO;

import java.io.Serializable;
import java.util.Date;

public class ChatStatusAuditDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public int id;
	public String user_uid;
	public String status;
	public Date statusTime;

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
