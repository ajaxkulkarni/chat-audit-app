package com.audit.app.DTO;

import java.io.Serializable;

public class AgentGridData implements Serializable {
	private static final long serialVersionUID = 1L;

	public String mwd; //
	public String workGroup;
	public String userName; //
	public String LoggedInTime; //
	public String activeTime; //
	public String activeRate; // (active time / logged in time ) * 100 = Result
								// is in Percentage
	public String activeChatTime;
	public String awayTime; //
	public String awayRate; // (away time / logged in time ) * 100 = Result is
							// in Percentage
	public String chatTime;
	public String chatTimeRate;
	public String ConcurrentChatTime;
	public String interactiveEngagements;
	public String averageHandleTime;
	public String userId;

	public String getMwd() {
		return mwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setMwd(String mwd) {
		this.mwd = mwd;
	}

	public String getWorkGroup() {
		return workGroup;
	}

	public void setWorkGroup(String workGroup) {
		this.workGroup = workGroup;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoggedInTime() {
		return LoggedInTime;
	}

	public void setLoggedInTime(String loggedInTime) {
		LoggedInTime = loggedInTime;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getActiveRate() {
		return activeRate;
	}

	public void setActiveRate(String activeRate) {
		this.activeRate = activeRate;
	}

	public String getActiveChatTime() {
		return activeChatTime;
	}

	public void setActiveChatTime(String activeChatTime) {
		this.activeChatTime = activeChatTime;
	}

	public String getAwayTime() {
		return awayTime;
	}

	public void setAwayTime(String awayTime) {
		this.awayTime = awayTime;
	}

	public String getAwayRate() {
		return awayRate;
	}

	public void setAwayRate(String awayRate) {
		this.awayRate = awayRate;
	}

	public String getChatTime() {
		return chatTime;
	}

	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}

	public String getChatTimeRate() {
		return chatTimeRate;
	}

	public void setChatTimeRate(String chatTimeRate) {
		this.chatTimeRate = chatTimeRate;
	}

	public String getConcurrentChatTime() {
		return ConcurrentChatTime;
	}

	public void setConcurrentChatTime(String concurrentChatTime) {
		ConcurrentChatTime = concurrentChatTime;
	}

	public String getInteractiveEngagements() {
		return interactiveEngagements;
	}

	public void setInteractiveEngagements(String interactiveEngagements) {
		this.interactiveEngagements = interactiveEngagements;
	}

	public String getAverageHandleTime() {
		return averageHandleTime;
	}

	public void setAverageHandleTime(String averageHandleTime) {
		this.averageHandleTime = averageHandleTime;
	}

}
