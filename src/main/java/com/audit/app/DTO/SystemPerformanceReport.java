package com.audit.app.DTO;

import java.io.Serializable;

public class SystemPerformanceReport implements Serializable {
	private static final long serialVersionUID = 1L;

	public String interval;
	public long activeSenders;
	public long loggedinSender;
	public long activeReceivers;
	public long loggedinReceivers;
	public long liveChat;
	public long pendingChats;
	public long completeChat;
	public long incompleteChat;
	public String cpu;
	public String ram;
	public String disk;
	public String freeDisk;

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public long getActiveSenders() {
		return activeSenders;
	}

	public void setActiveSenders(long activeSenders) {
		this.activeSenders = activeSenders;
	}

	public long getLoggedinSender() {
		return loggedinSender;
	}

	public void setLoggedinSender(long loggedinSender) {
		this.loggedinSender = loggedinSender;
	}

	public long getActiveReceivers() {
		return activeReceivers;
	}

	public void setActiveReceivers(long activeReceivers) {
		this.activeReceivers = activeReceivers;
	}

	public long getLoggedinReceivers() {
		return loggedinReceivers;
	}

	public void setLoggedinReceivers(long loggedinReceivers) {
		this.loggedinReceivers = loggedinReceivers;
	}

	public long getLiveChat() {
		return liveChat;
	}

	public void setLiveChat(long liveChat) {
		this.liveChat = liveChat;
	}

	public long getPendingChats() {
		return pendingChats;
	}

	public void setPendingChats(long pendingChats) {
		this.pendingChats = pendingChats;
	}

	public long getCompleteChat() {
		return completeChat;
	}

	public void setCompleteChat(long completeChat) {
		this.completeChat = completeChat;
	}

	public long getIncompleteChat() {
		return incompleteChat;
	}

	public void setIncompleteChat(long incompleteChat) {
		this.incompleteChat = incompleteChat;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getFreeDisk() {
		return freeDisk;
	}

	public void setFreeDisk(String freeDisk) {
		this.freeDisk = freeDisk;
	}

}
