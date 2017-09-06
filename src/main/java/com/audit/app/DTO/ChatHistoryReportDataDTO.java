package com.audit.app.DTO;

import java.io.Serializable;
import java.util.Date;

public class ChatHistoryReportDataDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public String reason;
	public int chatInitiated;
	public int chatEstablished;
	public int chatCancelled;
	public int nonBusinessHours;
	public long noOfChatsPerReason;
	public Date avgAnswerTime;
	public Date avgHandleTime;
	public Date totalChatTime;
	public Date startTime;
	public Date endTime;

	public String getReason() {
		return reason;
	}

	public long getNoOfChatsPerReason() {
		return noOfChatsPerReason;
	}

	public void setNoOfChatsPerReason(long noOfChatsPerReason) {
		this.noOfChatsPerReason = noOfChatsPerReason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getChatInitiated() {
		return chatInitiated;
	}

	public void setChatInitiated(int chatInitiated) {
		this.chatInitiated = chatInitiated;
	}

	public int getChatEstablished() {
		return chatEstablished;
	}

	public void setChatEstablished(int chatEstablished) {
		this.chatEstablished = chatEstablished;
	}

	public int getChatCancelled() {
		return chatCancelled;
	}

	public void setChatCancelled(int chatCancelled) {
		this.chatCancelled = chatCancelled;
	}

	public int getNonBusinessHours() {
		return nonBusinessHours;
	}

	public void setNonBusinessHours(int nonBusinessHours) {
		this.nonBusinessHours = nonBusinessHours;
	}

	public Date getAvgAnswerTime() {
		return avgAnswerTime;
	}

	public void setAvgAnswerTime(Date avgAnswerTime) {
		this.avgAnswerTime = avgAnswerTime;
	}

	public Date getAvgHandleTime() {
		return avgHandleTime;
	}

	public void setAvgHandleTime(Date avgHandleTime) {
		this.avgHandleTime = avgHandleTime;
	}

	public Date getTotalChatTime() {
		return totalChatTime;
	}

	public void setTotalChatTime(Date totalChatTime) {
		this.totalChatTime = totalChatTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
