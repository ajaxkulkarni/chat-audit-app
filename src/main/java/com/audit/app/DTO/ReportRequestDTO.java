package com.audit.app.DTO;

import java.io.Serializable;
import java.util.Date;

public class ReportRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Date startDate;
	public Date endDate;
	public String senderWorkgroup;
	public String receiverWorkgroup;
	public String senderId;
	public String receiverId;
	public String attribute;
	public String attributeValue;
	public String senderRating;
	public String receiverRating;
	public Date asa;
	public Date aha;
	public String start;
	public String end;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSenderWorkgroup() {
		return senderWorkgroup;
	}

	public void setSenderWorkgroup(String senderWorkgroup) {
		this.senderWorkgroup = senderWorkgroup;
	}

	public String getReceiverWorkgroup() {
		return receiverWorkgroup;
	}

	public void setReceiverWorkgroup(String receiverWorkgroup) {
		this.receiverWorkgroup = receiverWorkgroup;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getSenderRating() {
		return senderRating;
	}

	public void setSenderRating(String senderRating) {
		this.senderRating = senderRating;
	}

	public String getReceiverRating() {
		return receiverRating;
	}

	public void setReceiverRating(String receiverRating) {
		this.receiverRating = receiverRating;
	}

	public Date getAsa() {
		return asa;
	}

	public void setAsa(Date asa) {
		this.asa = asa;
	}

	public Date getAha() {
		return aha;
	}

	public void setAha(Date aha) {
		this.aha = aha;
	}

}
