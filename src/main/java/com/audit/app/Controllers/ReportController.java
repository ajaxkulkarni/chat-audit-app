package com.audit.app.Controllers;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.audit.app.DTO.ChatGridData;
import com.audit.app.DTO.ChatHistoryReportDataDTO;
import com.audit.app.DTO.SystemPerformanceReport;
import com.audit.app.DTO.WorkgroupAndLocationDTO;
import com.audit.app.DTO.WorkgroupWithNoLocation;
import com.audit.app.Entities.AgentGridRecord;
import com.audit.app.Entities.Workgroup_By_Location_User;
import com.audit.app.Services.IService.IReportService;

@Component
@Path("/report")
public class ReportController {

	@Autowired
	private IReportService _report;

	@GET
	@Path("/history")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ChatHistoryReportDataDTO> getData() {
		return _report.getChatHistoryReportData();
	}

	@GET
	@Path("/interval")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String, Object> getIntervalData(@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate) {
		return _report.getIntervalData(startDate, endDate);
	}

	@GET
	@Path("/interval/download")
	@Produces("application/text")
	public Response getIntervalDataDownload(@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate) {
		ResponseBuilder response = Response.ok((Object) _report.getIntervalDataDownloadFile(startDate, endDate));
		response.header("Content-Disposition", "attachment; filename=Interval.csv");
		return response.build();
	}

	@GET
	@Path("/workgrouplocationwithoutusers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<WorkgroupAndLocationDTO> getWorkgroupAndLocationWithouUser(@QueryParam("deptName") String deptName,
			@QueryParam("locName") String locName) {
		return _report.getWorkgroupAndLocationWithouUser(deptName, locName);
	}
	
	@GET
	@Path("/workgrouplocationwithoutusers/download")
	@Produces("application/text")
	public Response getWorkgroupAndLocationWithouUserDataDownload(@QueryParam("deptName") String deptName,
			@QueryParam("locName") String locName) {
		ResponseBuilder response = Response.ok((Object) _report.getWorkgroupAndLocationWithouUserDataDownloadFile(deptName, locName));
		response.header("Content-Disposition", "attachment; filename=WorkgroupAndLocationWithouUser.csv");
		return response.build();
	}

	@GET
	@Path("/workgroupwithnolocation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<WorkgroupWithNoLocation> getWorkgroupsByNoLocation(@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate, @QueryParam("deptName") String deptName) {
		return _report.getWorkgroupsByNoLocation(startDate, endDate, deptName);
	}
	
	@GET
	@Path("/workgroupwithnolocation/download")
	@Produces("application/text")
	public Response getWorkgroupsByNoLocationDataDownload(@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate, @QueryParam("deptName") String deptName) {
		ResponseBuilder response = Response.ok((Object) _report.getWorkgroupsByNoLocationDataDownloadFile(startDate, endDate, deptName));
		response.header("Content-Disposition", "attachment; filename=tWorkgroupsByNoLocation.csv");
		return response.build();
	}

	@GET
	@Path("/workgrouplocationwithoutskill")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<WorkgroupAndLocationDTO> getWorkgroupAndLocationWithouSkill(@QueryParam("deptName") String deptName,
			@QueryParam("locName") String locName) {
		return _report.getWorkgroupAndLocationWithoutSkillMapping(deptName, locName);
	}
	
	@GET
	@Path("/workgrouplocationwithoutskill/download")
	@Produces("application/text")
	public Response getWorkgroupAndLocationWithouSkillDataDownload(@QueryParam("deptName") String deptName,
			@QueryParam("locName") String locName) {
		ResponseBuilder response = Response.ok((Object) _report.getWorkgroupAndLocationWithouSkillDataDownloadFile(deptName, locName));
		response.header("Content-Disposition", "attachment; filename=WorkgroupAndLocationWithouSkill.csv");
		return response.build();
	}

	@GET
	@Path("/workgroupbylocationanduser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Workgroup_By_Location_User> getWorkgroupAndLocationWithouSkill(@QueryParam("deptName") String deptName,
			@QueryParam("locName") String locName, @QueryParam("firstName") String firstName,
			@QueryParam("lastName") String lastName) {
		return _report.getWorkgroupByLocationAndUser(deptName, locName, firstName, lastName);
	}
	
	@GET
	@Path("/workgroupbylocationanduser/download")
	@Produces("application/text")
	public Response getworkgroupbylocationanduserDataDownload(@QueryParam("deptName") String deptName,
	@QueryParam("locName") String locName, @QueryParam("firstName") String firstName,
	@QueryParam("lastName") String lastName) {
		ResponseBuilder response = Response.ok((Object) _report.getworkgroupbylocationanduserDataDownloadFile(deptName, locName, firstName, lastName));
		response.header("Content-Disposition", "attachment; filename=Workgroup_By_Location_And_User.csv");
		return response.build();
	}

	@GET
	@Path("/chatgrid")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ChatGridData> getChatGridDTO(@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate) {
		return _report.getChatGridData(startDate, endDate);
	}

	@GET
	@Path("/chatgrid/download")
	@Produces("application/text")
	public Response getExcell(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
		ResponseBuilder response = Response.ok((Object) _report.downloadFile(startDate, endDate));
		response.header("Content-Disposition", "attachment; filename=ChartGrid.csv");
		return response.build();
	}

	@GET
	@Path("/agentgrid")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void getAgentGridDTO(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
			@QueryParam("flag") int flag, @QueryParam("userid") int userid) {
		_report.getAgentGridDTO(startDate, endDate, flag, userid);
	}

	@GET
	@Path("/agentgrid/records")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgentGridRecord> getRecordsByUserId(@QueryParam("userId") int userId) {
		return _report.getRecordsByUserId(userId);
	}

	@GET
	@Path("/agentgrid/download")
	@Produces("application/text")
	public Response getAgentGridFile(@QueryParam("file") String fileName) {
		ResponseBuilder response = Response.ok((Object) _report.downloadAgentGridFile(fileName));
		response.header("Content-Disposition", "attachment; filename=AgentGrid.csv");
		return response.build();
	}
	
	@GET
	@Path("/system/report")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getSystemReportData(){
		return _report.getSystemReportData();
	}
}
