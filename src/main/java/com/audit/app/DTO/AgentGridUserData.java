package com.audit.app.DTO;

import java.io.Serializable;

public class AgentGridUserData implements Serializable {
	private static final long serialVersionUID = 1L;
	public long loggedInTime;
	public long active;
	public long away;
	public long doNotDisturb;
	public long inTraining;
	public long offline;
	public long chatTime;
	public long activeChatTime;
	public long concurrentChatTime;
	public long avgHandleTime;
	public long totalChat;

	public long getLoggedInTime() {
		return loggedInTime;
	}

	public void setLoggedInTime(long loggedInTime) {
		this.loggedInTime = loggedInTime;
	}

	public long getActive() {
		return active;
	}

	public void setActive(long active) {
		this.active = active;
	}

	public long getAway() {
		return away;
	}

	public void setAway(long away) {
		this.away = away;
	}

	public long getDoNotDisturb() {
		return doNotDisturb;
	}

	public void setDoNotDisturb(long doNotDisturb) {
		this.doNotDisturb = doNotDisturb;
	}

	public long getInTraining() {
		return inTraining;
	}

	public void setInTraining(long inTraining) {
		this.inTraining = inTraining;
	}

	public long getOffline() {
		return offline;
	}

	public void setOffline(long offline) {
		this.offline = offline;
	}

	public long getChatTime() {
		return chatTime;
	}

	public void setChatTime(long chatTime) {
		this.chatTime = chatTime;
	}

	public long getActiveChatTime() {
		return activeChatTime;
	}

	public void setActiveChatTime(long activeChatTime) {
		this.activeChatTime = activeChatTime;
	}

	public long getConcurrentChatTime() {
		return concurrentChatTime;
	}

	public void setConcurrentChatTime(long concurrentChatTime) {
		this.concurrentChatTime = concurrentChatTime;
	}

	public long getAvgHandleTime() {
		return avgHandleTime;
	}

	public void setAvgHandleTime(long avgHandleTime) {
		this.avgHandleTime = avgHandleTime;
	}

	public long getTotalChat() {
		return totalChat;
	}

	public void setTotalChat(long totalChat) {
		this.totalChat = totalChat;
	}

}
