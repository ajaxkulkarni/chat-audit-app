package com.audit.app.DTO;

import java.util.Date;

public class CommonReportResultDTO {
	public int sId;
	public Date startDate;
	public Date reginterval;
	public String Count;

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getReginterval() {
		return reginterval;
	}

	public void setReginterval(Date reginterval) {
		this.reginterval = reginterval;
	}

	public String getCount() {
		return Count;
	}

	public void setCount(String count) {
		Count = count;
	}

}
