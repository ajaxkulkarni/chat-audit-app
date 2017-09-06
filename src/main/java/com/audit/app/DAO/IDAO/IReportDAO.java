package com.audit.app.DAO.IDAO;

import java.util.List;

import com.audit.app.DTO.ChatGridDTO;
import com.audit.app.DTO.WorkgroupAndLocationDTO;
import com.audit.app.DTO.WorkgroupWithNoLocation;
import com.audit.app.Entities.AgentGridRecord;
import com.audit.app.Entities.Workgroup_By_Location_User;
import com.audit.app.Entities.jpa.ChatSessionEntity;
import com.audit.app.Entities.jpa.SessionUsersEntity;

public interface IReportDAO {
	List<ChatSessionEntity> getChatHistoryReportData(int reasonId);

	List<ChatSessionEntity> getChatHistoryReportData(int reasonId, String startDate, String endDate);

	int getChatCount(int reasonId, int flag);

	List<SessionUsersEntity> getUsersSessionBySessionId(long id);

	Object getIntervalData(String startDate, String endDate);

	List<WorkgroupAndLocationDTO> getWorkgroupAndLocationWithouUser(String deptName, String locName);

	List<WorkgroupWithNoLocation> getWorkgroupsByNoLocation(String startDate, String endDate, String deptName);

	List<WorkgroupAndLocationDTO> getWorkgroupAndLocationWithoutSkillMapping(String deptName, String locName);

	List<Workgroup_By_Location_User> getWorkgroupByLocationAndUser(String deptName, String locName, String firstName,
			String lastName);

	List<ChatGridDTO> getChatGridData(String startDate, String endDate);

	int getChatCount(int reasonId, int flag, String startDate, String endDate);

	void getAgentGridDTO(String startDate, String endDate, int flag, int userid);

	List<AgentGridRecord> getRecordsByUserId(int userId);

	List<String> getAllTimeInterval();
	
	List<Integer> getChatSessionIds(String startDate, String endDate);

	int getChatCounts(int sessionId);
	
	String getLocations(int deptId);
	
	long getSystemPerformanceReport(String startDate, String endDate, int flag);
}
