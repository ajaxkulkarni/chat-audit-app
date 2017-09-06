package com.audit.app.DTO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatGridData implements Serializable {
	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public long longDate;
	public String dayOfWeek;
	public String workgroupName;
	public String locationName;
	public String reasonCode;
	public double chatsInitiated;
	public double chatEstOrConn;
	public double connectionRate;
	public double chatsCanceled;
	public double chatCanceledRate;
	public long longAHT;
	public long longAAT;
	public long longMHT;

	public String strDate;
	public String strAHT;
	public String strAAT;
	public String strMHT;

	public String getStrDate() {
		return dateFormat.format(new Date(longDate));
	}

	public String getStrAHT() {
		return timeFormat.format(new Date(longAHT));
	}

	public String getStrAAT() {
		return timeFormat.format(new Date(longAAT));
	}

	public String getStrMHT() {
		return timeFormat.format(new Date(longMHT));
	}

	public long getLongDate() {
		return longDate;
	}

	public void setLongDate(long longDate) {
		this.longDate = longDate;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getWorkgroupName() {
		return workgroupName;
	}

	public void setWorkgroupName(String workgroupName) {
		this.workgroupName = workgroupName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public double getChatsInitiated() {
		return chatsInitiated;
	}

	public void setChatsInitiated(double chatsInitiated) {
		this.chatsInitiated = chatsInitiated;
	}

	public double getChatEstOrConn() {
		return chatEstOrConn;
	}

	public void setChatEstOrConn(double chatEstOrConn) {
		this.chatEstOrConn = chatEstOrConn;
	}

	public double getConnectionRate() {
		return connectionRate;
	}

	public void setConnectionRate(double connectionRate) {
		this.connectionRate = connectionRate;
	}

	public double getChatsCanceled() {
		return chatsCanceled;
	}

	public void setChatsCanceled(double chatsCanceled) {
		this.chatsCanceled = chatsCanceled;
	}

	public double getChatCanceledRate() {
		return chatCanceledRate;
	}

	public void setChatCanceledRate(double chatCanceledRate) {
		this.chatCanceledRate = chatCanceledRate;
	}

	public long getLongAHT() {
		return longAHT;
	}

	public void setLongAHT(long longAHT) {
		this.longAHT = longAHT;
	}

	public long getLongAAT() {
		return longAAT;
	}

	public void setLongAAT(long longAAT) {
		this.longAAT = longAAT;
	}

	public long getLongMHT() {
		return longMHT;
	}

	public void setLongMHT(long longMHT) {
		this.longMHT = longMHT;
	}

}
