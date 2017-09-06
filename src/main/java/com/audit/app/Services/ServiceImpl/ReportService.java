package com.audit.app.Services.ServiceImpl;

import java.io.File;
import java.io.FileWriter;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.audit.app.DAO.IDAO.IReportDAO;
import com.audit.app.DTO.ChatGridDTO;
import com.audit.app.DTO.ChatGridData;
import com.audit.app.DTO.ChatHistoryReportDataDTO;
import com.audit.app.DTO.SystemPerformanceReport;
import com.audit.app.DTO.WorkgroupAndLocationDTO;
import com.audit.app.DTO.WorkgroupWithNoLocation;
import com.audit.app.Entities.AgentGridRecord;
import com.audit.app.Entities.Workgroup_By_Location_User;
import com.audit.app.Entities.jpa.ChatSessionEntity;
import com.audit.app.Entities.jpa.SessionUsersEntity;
import com.audit.app.Services.IService.IReportService;
import com.sun.management.OperatingSystemMXBean;

import au.com.bytecode.opencsv.CSVWriter;

@Service
@Component
public class ReportService implements IReportService {
	private static Logger LOG = Logger.getLogger(ReportService.class);

	@Autowired
	private IReportDAO _dao;

	private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
	private static DateTimeFormatter format1 = DateTimeFormat.forPattern("MM/dd");
	private static DateTimeFormatter format2 = DateTimeFormat.forPattern("HH:mm:ss");
	private static DateTimeFormatter format4 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	@Value("${file.path}")
	private String File_Path;// "d://temp//";

	private static DecimalFormat twoDForm = new DecimalFormat("#.##");

	public List<ChatHistoryReportDataDTO> getChatHistoryReportData() {
		List<ChatHistoryReportDataDTO> dataList = new ArrayList<ChatHistoryReportDataDTO>();
		List<ChatSessionEntity> list = _dao.getChatHistoryReportData(0);
		for (ChatSessionEntity entity : list) {
			ChatHistoryReportDataDTO dto = new ChatHistoryReportDataDTO();
			dto.setReason(entity.getReasons().getReasonName());
			dto.setChatInitiated(_dao.getChatCount(entity.getReasons().getId(), 0));
			dto.setChatEstablished(_dao.getChatCount(entity.getReasons().getId(), 1));
			dto.setChatCancelled(_dao.getChatCount(entity.getReasons().getId(), 2));
			dto.setNonBusinessHours(_dao.getChatCount(entity.getReasons().getId(), 3));

			Date date = null;
			Date date1 = null;
			for (SessionUsersEntity sEnt : entity.getListOfSessionUsers()) {
				if (date == null) {
					date = sEnt.getCreatedDate();
				} else {
					if (!(date.before(sEnt.getCreatedDate()))) {
						date = sEnt.getCreatedDate();
					}
				}

				if (sEnt.getEndDate() != null) {
					if (date1 == null) {
						date1 = sEnt.getEndDate();
					} else {
						if (!(date1.after(sEnt.getEndDate()))) {
							date1 = sEnt.getEndDate();
						}
					}
				}
			}

			dto.setStartTime(date);
			dto.setEndTime(date1);

			List<ChatSessionEntity> temp = _dao.getChatHistoryReportData(entity.getReasons().getId());
			dto.setNoOfChatsPerReason(temp.size());

			int totalHandleTime = 0;
			long totalWaitingTime = 0;
			long waitingTime = 0;
			long waitingTimeSender = 0;
			long waitingTimeReceiver = 0;

			for (ChatSessionEntity temp1 : temp) {
				int handle_time_in_sec_clone = 0;
				int handle_time_in_sec = 1;
				int no_of_chats_per_session = 0;
				long sessionId = temp1.getId();

				List<SessionUsersEntity> data = _dao.getUsersSessionBySessionId(sessionId);
				no_of_chats_per_session = data.size();
				if (no_of_chats_per_session > 1) {
					for (SessionUsersEntity ent : data) {
						DateTimeZone timeZone = DateTimeZone.forID("US/Central");
						DateTime dStartDate = new DateTime(ent.getCreatedDate().getTime(), timeZone);
						DateTime dEndDate = new DateTime(
								ent.getEndDate() == null ? new Date().getTime() : ent.getEndDate().getTime(), timeZone);
						handle_time_in_sec = Seconds
								.secondsBetween(dStartDate.withTimeAtStartOfDay(), dEndDate.withTimeAtStartOfDay())
								.getSeconds();

						if (handle_time_in_sec > handle_time_in_sec_clone) {
							handle_time_in_sec_clone = handle_time_in_sec;
						}

						if (waitingTimeSender == 0) {
							waitingTimeSender = dStartDate.getMillis();
						} else {
							waitingTimeReceiver = dStartDate.getMillis();
						}
					}
				}
				totalHandleTime = totalHandleTime + handle_time_in_sec_clone;
				waitingTime = waitingTimeReceiver - waitingTimeSender;
				if (waitingTime < 0) {
					waitingTime = 0;
				}
				waitingTimeSender = 0;
				waitingTimeReceiver = 0;
				totalWaitingTime = totalWaitingTime + waitingTime;
			}

			long average_waiting_time = (totalWaitingTime / dto.getNoOfChatsPerReason());
			average_waiting_time = new DateTime(average_waiting_time).getMillis();

			long average_handle_time = (totalHandleTime / dto.getNoOfChatsPerReason());
			average_handle_time = new DateTime(average_handle_time).getMillis();
			long total_chat_time = new DateTime(totalHandleTime).getMillis();

			dto.setAvgAnswerTime(new Date(average_waiting_time));
			dto.setAvgHandleTime(new Date(average_handle_time));
			dto.setTotalChatTime(new Date(total_chat_time));
			dataList.add(dto);
		}

		return dataList;
	}

	@Override
	public Map<String, Object> getIntervalData(String startDate, String endDate) {
		Map<String, Object> data = new TreeMap<String, Object>();
		try {
			if (startDate == null || startDate.isEmpty()) {
				startDate = new DateTime().minusDays(30).toString(format);
			}

			if (endDate == null || endDate.isEmpty()) {
				endDate = new DateTime().toString(format);
			}

			DateTime formatStartDate = format.parseDateTime(startDate);
			DateTime formatEndDate = format.parseDateTime(endDate);

			Map<String, Map<DateTime, Integer>> parentMap = new TreeMap<String, Map<DateTime, Integer>>();
			Map<DateTime, Integer> mapDate = getEmptyMap(formatStartDate, formatEndDate);

			List<String> intervalList = _dao.getAllTimeInterval();

			int min = 30, sec = 00;
			for (String interval : intervalList) {
				Map<DateTime, Integer> mapDateTemp = new TreeMap<DateTime, Integer>(
						getEmptyMap(formatStartDate, formatEndDate));
				if (interval.equals("23:30:00")) {
					min = 29;
					sec = 59;
				}
				for (DateTime dt : mapDate.keySet()) {
					String strDt = format.print(dt);
					String qurStartDate = strDt + " " + interval;

					String qurEndDate = format4
							.print(DateTime.parse(qurStartDate, format4).plusMinutes(min).plusSeconds(sec));

					List<Integer> sessionIds = _dao.getChatSessionIds(qurStartDate, qurEndDate);
					int count = 0;
					for (Integer id : sessionIds) {
						if (_dao.getChatCounts(id) >= 2) {
							count++;
						}
					}
					mapDateTemp.replace(dt, count);
				}
				parentMap.put(interval, mapDateTemp);
			}

			min = 30;
			sec = 00;
			boolean isFirst = true;
			String columDef = null;
			String rowData = null;
			int i = 1;
			for (DateTime d : mapDate.keySet()) {
				if (isFirst) {
					columDef = "{headerName : \"Interval\", field : \"intervals\", filter : \"text\", width : 150, cellStyle : {\"text-align\" : \"center\"}, floatingFilterComponentParams : { suppressFilterButton : true } }, {headerName : \""
							+ new DateTime(d.getMillis()).toString(format1) + "\", field : \"day" + i
							+ "\", filter : \"text\", width : 100, cellStyle : {\"text-align\" : \"center\"}, floatingFilterComponentParams : { suppressFilterButton : true } }";
					isFirst = false;
				} else {
					columDef += ", {headerName : \"" + new DateTime(d.getMillis()).toString(format1)
							+ "\", field : \"day" + i
							+ "\", filter : \"text\", width : 100, cellStyle : {\"text-align\" : \"center\"}, floatingFilterComponentParams : { suppressFilterButton : true } }";
				}
				i++;
			}

			ObjectMapper mapper = new ObjectMapper();
			Object[] obj = mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
					.readValue("[" + columDef + "]", Object[].class);
			data.put("columDef", obj);

			isFirst = true;
			for (String str : parentMap.keySet()) {
				if (str.equals("23:30:00")) {
					min = 29;
					sec = 59;
				}
				mapDate = new TreeMap<DateTime, Integer>();
				mapDate = parentMap.get(str);
				i = 1;

				String nextTime = format2.parseDateTime(str).plusMinutes(min).plusSeconds(sec).toString(format2);

				if (isFirst) {
					rowData = "{\"intervals\":\"" + str + " - " + nextTime + "\"";
					for (DateTime dt : mapDate.keySet()) {
						rowData += ", \"day" + i + "\":" + mapDate.get(dt);
						i++;
					}
					rowData += "}";
					isFirst = false;
				} else {
					rowData += ", {\"intervals\":\"" + str + " - " + nextTime + "\"";
					for (DateTime dt : mapDate.keySet()) {
						rowData += ", \"day" + i + "\":" + mapDate.get(dt);
						i++;
					}
					rowData += "}";
				}

			}
			Object[] obj1 = mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
					.readValue("[" + rowData + "]", Object[].class);
			data.put("rowData", obj1);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return data;
	}

	private Map<DateTime, Integer> getEmptyMap(DateTime startDate, DateTime endDate) {
		Map<DateTime, Integer> map = new TreeMap<DateTime, Integer>();
		for (DateTime date = startDate; (date.isBefore(endDate) || date.isEqual(endDate)); date = date.plusDays(1)) {
			map.put(date, 0);
		}
		return map;
	}

	@Override
	public List<WorkgroupAndLocationDTO> getWorkgroupAndLocationWithouUser(String deptName, String locName) {
		return _dao.getWorkgroupAndLocationWithouUser(deptName, locName);
	}

	@Override
	public List<WorkgroupWithNoLocation> getWorkgroupsByNoLocation(String startDate, String endDate, String deptName) {
		if (startDate != null && !startDate.isEmpty() && !startDate.trim().equals("undefined")) {
			if (endDate == null || endDate.isEmpty() || endDate.trim().equals("undefined"))
				endDate = sFormat.format(new Date());
		}
		return _dao.getWorkgroupsByNoLocation(startDate, endDate, deptName);
	}

	@Override
	public List<WorkgroupAndLocationDTO> getWorkgroupAndLocationWithoutSkillMapping(String deptName, String locName) {
		return _dao.getWorkgroupAndLocationWithoutSkillMapping(deptName, locName);
	}

	@Override
	public List<Workgroup_By_Location_User> getWorkgroupByLocationAndUser(String deptName, String locName,
			String firstName, String lastName) {
		return _dao.getWorkgroupByLocationAndUser(deptName, locName, firstName, lastName);
	}

	@Override
	public List<ChatGridData> getChatGridData(String startDate, String endDate) {
		if (startDate != null && !startDate.isEmpty() && !startDate.trim().equals("undefined")) {
			if (endDate == null || endDate.isEmpty() || endDate.trim().equals("undefined"))
				endDate = sFormat.format(new Date());
		}

		List<ChatGridData> gridData = new ArrayList<ChatGridData>();
		List<ChatGridDTO> list = _dao.getChatGridData(startDate, endDate);
		list.size();

		for (ChatGridDTO dto : list) {
			ChatGridData data = new ChatGridData();
			data.setDayOfWeek(dto.getDay());
			data.setLocationName(_dao.getLocations(dto.getDepartment_id() == null ? 0 : dto.getDepartment_id()));
			data.setLongDate(dto.getDate().getTime());
			data.setReasonCode(dto.getReason_name());
			data.setWorkgroupName(dto.getWorkgroup());

			data.setChatsInitiated(_dao.getChatCount(dto.getReason_id(), 0, startDate, endDate));
			data.setChatEstOrConn(_dao.getChatCount(dto.getReason_id(), 1, startDate, endDate));
			double temp = data.getChatEstOrConn() / data.getChatsInitiated();
			data.setConnectionRate(Double.valueOf(twoDForm.format(temp * 100)));
			data.setChatsCanceled(_dao.getChatCount(dto.getReason_id(), 2));
			temp = data.getChatsCanceled() / data.getChatsInitiated();
			data.setChatCanceledRate(Double.valueOf(twoDForm.format(temp * 100)));

			List<ChatSessionEntity> ChatSessionEntityList = _dao.getChatHistoryReportData(dto.getReason_id(), startDate,
					endDate);
			int noOfChatPerReason = ChatSessionEntityList.size();

			int totalHandleTime = 0;
			long totalWaitingTime = 0;
			long waitingTime = 0;
			long waitingTimeSender = 0;
			long waitingTimeReceiver = 0;

			for (ChatSessionEntity temp1 : ChatSessionEntityList) {
				int handle_time_in_sec_clone = 0;
				int handle_time_in_sec = 1;
				int no_of_chats_per_session = 0;
				long sessionId = temp1.getId();

				List<SessionUsersEntity> data1 = _dao.getUsersSessionBySessionId(sessionId);
				no_of_chats_per_session = data1.size();
				if (no_of_chats_per_session > 1) {
					for (SessionUsersEntity ent : data1) {
						DateTimeZone timeZone = DateTimeZone.forID("US/Central");
						DateTime dStartDate = new DateTime(ent.getCreatedDate().getTime(), timeZone);
						DateTime dEndDate = new DateTime(
								ent.getEndDate() == null ? new Date().getTime() : ent.getEndDate().getTime(), timeZone);
						handle_time_in_sec = Seconds
								.secondsBetween(dStartDate.withTimeAtStartOfDay(), dEndDate.withTimeAtStartOfDay())
								.getSeconds();

						if (handle_time_in_sec > handle_time_in_sec_clone) {
							handle_time_in_sec_clone = handle_time_in_sec;
						}

						if (waitingTimeSender == 0) {
							waitingTimeSender = dStartDate.getMillis();
						} else {
							waitingTimeReceiver = dStartDate.getMillis();
						}
					}
				}
				totalHandleTime = totalHandleTime + handle_time_in_sec_clone;
				waitingTime = waitingTimeReceiver - waitingTimeSender;
				if (waitingTime < 0) {
					waitingTime = 0;
				}
				waitingTimeSender = 0;
				waitingTimeReceiver = 0;
				totalWaitingTime = totalWaitingTime + waitingTime;
			}

			long average_waiting_time = (totalWaitingTime / noOfChatPerReason);
			average_waiting_time = new DateTime(average_waiting_time).getMillis();

			long average_handle_time = (totalHandleTime / noOfChatPerReason);
			average_handle_time = new DateTime(average_handle_time).getMillis();

			data.setLongAAT(average_waiting_time);
			data.setLongAHT(average_handle_time);
			data.setLongMHT(totalHandleTime);

			gridData.add(data);
		}
		return gridData;
	}

	@Override
	public void getAgentGridDTO(String startDate, String endDate, int flag, int userid) {
		_dao.getAgentGridDTO(startDate, endDate, flag, userid);
	}

	@Override
	public byte[] downloadFile(String startDate, String endDate) {
		List<ChatGridData> data = getChatGridData(startDate, endDate);
		CSVWriter write = null;
		byte[] bFile = null;
		String fileName = File_Path + "/Chat_Grid_" + new Date().getTime() + ".csv";
		try {
			write = new CSVWriter(new FileWriter(fileName));
			List<String[]> strList = new ArrayList<String[]>();
			String arr[] = new String[13];
			strList.add(new String[] { "Date", "Day of the week", "Workgroup", "Location", "Reason code",
					"Chats Initiated", "Chats Established/Connected", "Connection Rate", "Chats Canceled",
					"Canceled Rate", "Average Handle Time", "Average Answer Time", "Maximum Answer Time" });
			write.writeAll(strList);
			if (data.size() > 0) {
				for (ChatGridData val : data) {
					int i = 0;

					arr[i++] = val.getStrDate();
					arr[i++] = val.getDayOfWeek();
					arr[i++] = val.getWorkgroupName();
					arr[i++] = val.getLocationName();
					arr[i++] = val.getReasonCode();
					arr[i++] = val.getChatsInitiated() + "";
					arr[i++] = val.getChatEstOrConn() + "";
					arr[i++] = val.getConnectionRate() + "";
					arr[i++] = val.getChatsCanceled() + "";
					arr[i++] = val.getChatCanceledRate() + "";
					arr[i++] = val.getStrAHT();
					arr[i++] = val.getStrAAT();
					arr[i++] = val.getStrMHT();

					write.writeNext(arr);
				}
			}
			write.flush();
			write.close();
			bFile = Files.readAllBytes(Paths.get(fileName));
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				write.close();
				write.flush();
			} catch (Exception e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
			new File(fileName).delete();
		}
		return bFile;
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] getIntervalDataDownloadFile(String startDate, String endDate) {
		Map<String, Object> map = getIntervalData(startDate, endDate);
		Object[] arr1 = (Object[]) map.get("columDef");
		Object[] arr2 = (Object[]) map.get("rowData");
		CSVWriter write = null;
		byte[] bFile = null;
		String fileName = File_Path + "/Interval_" + new Date().getTime() + ".csv";
		try {
			write = new CSVWriter(new FileWriter(fileName));

			String data = null;
			String temp = null;

			for (int i = 0; i < arr1.length; i++) {
				Map<String, Object> jsonObj = (Map<String, Object>) arr1[i];
				if (data == null)
					data = jsonObj.get("headerName").toString();
				else
					data += "," + jsonObj.get("headerName").toString();
			}
			write.writeNext(data.split(","));

			for (int i = 0; i < arr2.length; i++) {
				Map<String, Object> jsonObj = (Map<String, Object>) arr2[i];
				for (String count : jsonObj.keySet()) {
					if (temp == null)
						temp = jsonObj.get(count) + "";
					else
						temp += "," + jsonObj.get(count);
				}
				write.writeNext(temp.split(","));
				temp = null;
			}
			write.flush();
			write.close();
			bFile = Files.readAllBytes(Paths.get(fileName));
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				write.close();
				write.flush();
			} catch (Exception e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return bFile;
	}

	@Override
	public List<AgentGridRecord> getRecordsByUserId(int userId) {
		return _dao.getRecordsByUserId(userId);
	}

	@Override
	public byte[] downloadAgentGridFile(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] downloadAgentGridFile(String fileName) {
		String file = File_Path + "/Agent-Grid/" + fileName;
		byte[] b = null;
		try {
			b = Files.readAllBytes(Paths.get(file));
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public byte[] getWorkgroupAndLocationWithouUserDataDownloadFile(String deptName, String locName) {
		List<WorkgroupAndLocationDTO> data = getWorkgroupAndLocationWithouUser(deptName, locName);
		CSVWriter write = null;
		byte[] bFile = null;
		String fileName = File_Path + "/Workgroup_And_Location_Without_User_" + new Date().getTime() + ".csv";
		try {
			write = new CSVWriter(new FileWriter(fileName));
			List<String[]> strList = new ArrayList<String[]>();
			String arr[] = new String[5];
			strList.add(new String[] { "Department Name", "Location Name", "Location Description", "Primary Contact",
					"Address" });
			write.writeAll(strList);
			if (data.size() > 0) {
				for (WorkgroupAndLocationDTO val : data) {
					int i = 0;

					arr[i++] = val.getDepartment_name();
					arr[i++] = val.getLocation_name();
					arr[i++] = val.getLocation_description();
					arr[i++] = val.getPrimary_contact();
					arr[i++] = val.getAddress();

					write.writeNext(arr);
				}
			}
			write.flush();
			write.close();
			bFile = Files.readAllBytes(Paths.get(fileName));
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				write.close();
				write.flush();
			} catch (Exception e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
			new File(fileName).delete();
		}
		return bFile;
	}

	@Override
	public byte[] getWorkgroupsByNoLocationDataDownloadFile(String startDate, String endDate, String deptName) {
		List<WorkgroupWithNoLocation> data = getWorkgroupsByNoLocation(startDate, endDate, deptName);
		CSVWriter write = null;
		byte[] bFile = null;
		String fileName = File_Path + "/Workgroups_By_No_Location_" + new Date().getTime() + ".csv";
		try {
			write = new CSVWriter(new FileWriter(fileName));
			List<String[]> strList = new ArrayList<String[]>();
			String arr[] = new String[6];
			strList.add(new String[] { "Department Name", "Department Description", "Abbrivation", "Primary Contact",
					"Secondary Contact", "Created Date" });
			write.writeAll(strList);
			if (data.size() > 0) {
				for (WorkgroupWithNoLocation val : data) {
					int i = 0;

					arr[i++] = val.getDepartment_name();
					arr[i++] = val.getDepartment_description();
					arr[i++] = val.getAbbr_name();
					arr[i++] = val.getPrimary_contact();
					arr[i++] = val.getSecondary_contact();
					arr[i++] = sFormat.format(val.getCreated_date());

					write.writeNext(arr);
				}
			}
			write.flush();
			write.close();
			bFile = Files.readAllBytes(Paths.get(fileName));
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				write.close();
				write.flush();
			} catch (Exception e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
			new File(fileName).delete();
		}
		return bFile;
	}

	@Override
	public byte[] getWorkgroupAndLocationWithouSkillDataDownloadFile(String deptName, String locName) {
		List<WorkgroupAndLocationDTO> data = getWorkgroupAndLocationWithoutSkillMapping(deptName, locName);
		CSVWriter write = null;
		byte[] bFile = null;
		String fileName = File_Path + "/Workgroup_And_Location_Without_Skill_Mapping_" + new Date().getTime() + ".csv";
		try {
			write = new CSVWriter(new FileWriter(fileName));
			List<String[]> strList = new ArrayList<String[]>();
			String arr[] = new String[5];
			strList.add(new String[] { "Department Name", "Location Name", "Location Description", "Primary Contact",
					"Address" });
			write.writeAll(strList);
			if (data.size() > 0) {
				for (WorkgroupAndLocationDTO val : data) {
					int i = 0;

					arr[i++] = val.getDepartment_name();
					arr[i++] = val.getLocation_name();
					arr[i++] = val.getLocation_description();
					arr[i++] = val.getPrimary_contact();
					arr[i++] = val.getAddress();

					write.writeNext(arr);
				}
			}
			write.flush();
			write.close();
			bFile = Files.readAllBytes(Paths.get(fileName));
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (write != null) {
					write.flush();
					write.close();
				}

			} catch (Exception e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
			new File(fileName).delete();
		}
		return bFile;
	}

	@Override
	public byte[] getworkgroupbylocationanduserDataDownloadFile(String deptName, String locName, String firstName,
			String lastName) {
		List<Workgroup_By_Location_User> data = getWorkgroupByLocationAndUser(deptName, locName, firstName, lastName);
		CSVWriter write = null;
		byte[] bFile = null;
		String fileName = File_Path + "/Workgroup_By_Location_And_User_" + new Date().getTime() + ".csv";
		try {
			write = new CSVWriter(new FileWriter(fileName));
			List<String[]> strList = new ArrayList<String[]>();
			String arr[] = new String[11];
			strList.add(new String[] { "Department Name", "Department Description", "Abbravation Name",
					"Primary Contact", "Location", "Location Desciption", "User First Name", "User Last Name",
					"User Email", "User Phone", "Organization" });
			write.writeAll(strList);
			if (data.size() > 0) {
				for (Workgroup_By_Location_User val : data) {
					int i = 0;

					arr[i++] = val.getDepartment_name();
					arr[i++] = val.getDepartment_description();
					arr[i++] = val.getAbbr_name();
					arr[i++] = val.getPrimary_contact();
					arr[i++] = val.getLocation_name();
					arr[i++] = val.getLocation_description();
					arr[i++] = val.getFirst_name();
					arr[i++] = val.getLast_name();
					arr[i++] = val.getEmail();
					arr[i++] = val.getPhone();
					arr[i++] = val.getOrganization();

					write.writeNext(arr);
				}
			}
			write.flush();
			write.close();
			bFile = Files.readAllBytes(Paths.get(fileName));
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				write.close();
				write.flush();
			} catch (Exception e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
			new File(fileName).delete();
		}
		return bFile;
	}

	@Override
	public Map<String, Object> getSystemReportData() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SystemPerformanceReport> list = new ArrayList<SystemPerformanceReport>();
		try {
			List<Object[]> chartData = new ArrayList<Object[]>();
			DateTime currentDate = new DateTime(); //.withTime(7, 30, 0, 0);
			DateTime startDate = new DateTime().withTime(7, 0, 0, 0);

			while (startDate.isBefore(currentDate) || startDate.equals(currentDate)) {
				int i = 0;
				Object[] obj = new Object[4];
				SystemPerformanceReport dto = new SystemPerformanceReport();
				String strStartDate = format4.print(startDate);
				DateTime endDate = startDate.plusMinutes(30);
				String strEndDate = format4.print(endDate);

				String startTime = strStartDate.split(" ")[1];
				String endTime = strEndDate.split(" ")[1];

				obj[i++] = startTime.substring(0, startTime.length() - 3);

				dto.setInterval(startTime.substring(0, startTime.length() - 3) + " - "
						+ endTime.substring(0, endTime.length() - 3));
				dto.setActiveReceivers(_dao.getSystemPerformanceReport(strStartDate, strEndDate, 5));
				obj[i++] = dto.getActiveReceivers();
				long v1 = _dao.getSystemPerformanceReport(strStartDate, strEndDate, 6);
				dto.setLiveChat(v1);
				obj[i++] = v1;
				dto.setPendingChats(_dao.getSystemPerformanceReport(strStartDate, strEndDate, 1));
				obj[i++] = dto.getPendingChats();
				dto.setLoggedinSender(_dao.getSystemPerformanceReport(strStartDate, strEndDate, 2));
				dto.setLoggedinReceivers(_dao.getSystemPerformanceReport(strStartDate, strEndDate, 3));
				dto.setActiveSenders(_dao.getSystemPerformanceReport(strStartDate, strEndDate, 4));
				dto.setCompleteChat(_dao.getSystemPerformanceReport(strStartDate, strEndDate, 7));
				dto.setIncompleteChat(_dao.getSystemPerformanceReport(strStartDate, strEndDate, 8));

				OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory
						.getOperatingSystemMXBean();
				double cpu = Math.round(operatingSystemMXBean.getSystemCpuLoad() * 100);
				double ram = operatingSystemMXBean.getFreePhysicalMemorySize()
						/ operatingSystemMXBean.getTotalPhysicalMemorySize();
				ram = Math.round(ram * 100);

				dto.setCpu(cpu + "%");
				dto.setRam(ram + "%");
				
				//obj[i++] = cpu;
				//obj[i++] = ram;
				
				File f = new File("/");
				long l = f.getTotalSpace();
				l = l / 1024;
				l = l / 1024;

				dto.setDisk(l + " MB");

				l = f.getFreeSpace();
				l = l / 1024;
				l = l / 1024;
				dto.setFreeDisk(l + " MB");

				list.add(dto);
				chartData.add(obj);

				startDate = endDate;
			}

			map.put("chart", chartData);
			map.put("data", list);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
		return map;
	}
}
