package com.audit.app.Services.IService;

import java.util.List;
import java.util.Map;

import com.audit.app.DTO.ChatGridData;
import com.audit.app.DTO.ChatHistoryReportDataDTO;
import com.audit.app.DTO.SystemPerformanceReport;
import com.audit.app.DTO.WorkgroupAndLocationDTO;
import com.audit.app.DTO.WorkgroupWithNoLocation;
import com.audit.app.Entities.AgentGridRecord;
import com.audit.app.Entities.Workgroup_By_Location_User;

public interface IReportService {
	List<ChatHistoryReportDataDTO> getChatHistoryReportData();

	Map<String, Object> getIntervalData(String startDate, String endDate);

	byte[] getIntervalDataDownloadFile(String startDate, String endDate);

	List<WorkgroupAndLocationDTO> getWorkgroupAndLocationWithouUser(String deptName, String locName);

	List<WorkgroupWithNoLocation> getWorkgroupsByNoLocation(String startDate, String endDate, String deptName);

	List<WorkgroupAndLocationDTO> getWorkgroupAndLocationWithoutSkillMapping(String deptName, String locName);

	List<Workgroup_By_Location_User> getWorkgroupByLocationAndUser(String deptName, String locName, String firstName,
			String lastName);

	List<ChatGridData> getChatGridData(String startDate, String endDate);

	byte[] downloadFile(String startDate, String endDate);

	void getAgentGridDTO(String startDate, String endDate, int flag, int userid);
	
	List<AgentGridRecord> getRecordsByUserId(int userId);
	
	byte[] downloadAgentGridFile(String startDate, String endDate);
	
	byte[] downloadAgentGridFile(String fileName);
	
	byte[] getWorkgroupAndLocationWithouUserDataDownloadFile(String deptName, String locName);
	
	byte[] getWorkgroupsByNoLocationDataDownloadFile(String startDate, String endDate, String deptName);
	
	byte[] getWorkgroupAndLocationWithouSkillDataDownloadFile(String deptName, String locName);
	
	byte[] getworkgroupbylocationanduserDataDownloadFile(String deptName, String locName, String firstName,
			String lastName);

	Map<String, Object> getSystemReportData();
}