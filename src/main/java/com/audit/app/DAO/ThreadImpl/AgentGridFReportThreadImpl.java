package com.audit.app.DAO.ThreadImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import com.audit.app.DTO.AgentGridData;
import com.audit.app.DTO.AgentGridUserData;
import com.audit.app.Entities.AgentGridEntity;
import com.audit.app.Entities.AgentGridRecord;
import com.audit.app.Entities.StatusAudit;
import com.audit.app.Entities.jpa.ChatSessionEntity;
import com.audit.app.Entities.jpa.DeptLocUsersEntity;
import com.audit.app.Entities.jpa.SessionUsersEntity;
import com.audit.app.Entities.jpa.UsersEntity;

import au.com.bytecode.opencsv.CSVWriter;

public class AgentGridFReportThreadImpl implements Runnable {
	private static Logger LOG = Logger.getLogger(AgentGridFReportThreadImpl.class);

	private SessionFactory sessionFactory;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat format1 = new SimpleDateFormat("dd MMM");
	private SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat format3 = new SimpleDateFormat("MMM yyyy");

	private static DecimalFormat twoDForm = new DecimalFormat("#.##");

	private Integer flag = null;
	private String File_Path = null;
	private Date startDate = new Date();
	private Date fromDate = null, endDate = null;
	private int userId = 0;

	public AgentGridFReportThreadImpl(SessionFactory sessionFactory, Integer flag, String File_Path, int userId,
			String fromDate, String toDate) {
		try{
			this.sessionFactory = sessionFactory;
			this.flag = flag;
			this.File_Path = File_Path;
			this.userId = userId;
			this.fromDate = format.parse(fromDate);
			this.endDate = format.parse(toDate);
		}catch(Exception ex){
			LOG.error(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Session session = sessionFactory.openSession();
		try {
			int i = 0;
			String userUid = null;
			String date = null;
			String lastStatus = null;
			Map<String, Map<Date, AgentGridUserData>> map = new TreeMap<String, Map<Date, AgentGridUserData>>();
			Map<String, Date> dtMap = null;
			Map<Date, AgentGridUserData> objMap = null;
			List<AgentGridData> agentGridDatas = null;
			session.beginTransaction();
			List<StatusAudit> list = (List<StatusAudit>) session.createCriteria(StatusAudit.class).add(Restrictions.between("status_time", fromDate, endDate))
					.addOrder(Order.asc("user_uid")).addOrder(Order.asc("status_time")).list();
			AgentGridUserData dto = null;

			for (StatusAudit audit : list) {
				i++;
				if (userUid != null && userUid.equals(audit.getUser_uid().trim())) {
					if (date != null && date.equals(format.format(audit.getStatus_time()))) {
						if (objMap != null && objMap.containsKey(format.parseObject(date))) {
							dto = new AgentGridUserData();
							dto = objMap.get(format.parseObject(date));
						}

						if (audit.getStatus().trim().equalsIgnoreCase("available")) {
							dtMap.put("available", audit.getStatus_time());
						} else if (audit.getStatus().trim().equalsIgnoreCase("loggedout")) {
							if (dtMap.containsKey("available")) {
								DateTime d1 = new DateTime(dtMap.get("available"));
								DateTime d2 = new DateTime(audit.getStatus_time());
								dto.setLoggedInTime(dto.getLoggedInTime()
										+ TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes()));
								dtMap.remove("available");
							}

							if (!lastStatus.equalsIgnoreCase("available") && dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("loggedin"))
									dto.setActive(dto.getActive() + res);
								else if (lastStatus.equalsIgnoreCase("away"))
									dto.setAway(dto.getAway() + res);
								else if (lastStatus.equalsIgnoreCase("donotdisturb"))
									dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
								else if (lastStatus.equalsIgnoreCase("intraining"))
									dto.setInTraining(dto.getInTraining() + res);
								else if (lastStatus.equalsIgnoreCase("offline"))
									dto.setOffline(dto.getOffline() + res);

								dtMap.remove(lastStatus);
							}
						} else if (audit.getStatus().trim().equalsIgnoreCase("loggedin")) {
							if (dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("away"))
									dto.setAway(dto.getAway() + res);
								else if (lastStatus.equalsIgnoreCase("donotdisturb"))
									dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
								else if (lastStatus.equalsIgnoreCase("intraining"))
									dto.setInTraining(dto.getInTraining() + res);
								else if (lastStatus.equalsIgnoreCase("offline"))
									dto.setOffline(dto.getOffline() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
							dtMap.put("available", audit.getStatus_time());
						} else if (audit.getStatus().trim().equalsIgnoreCase("away")) {
							if (dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("loggedin"))
									dto.setActive(dto.getActive() + res);
								else if (lastStatus.equalsIgnoreCase("donotdisturb"))
									dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
								else if (lastStatus.equalsIgnoreCase("intraining"))
									dto.setInTraining(dto.getInTraining() + res);
								else if (lastStatus.equalsIgnoreCase("offline"))
									dto.setOffline(dto.getOffline() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
							dtMap.put("away", audit.getStatus_time());
						} else if (audit.getStatus().trim().equalsIgnoreCase("donotdisturb")) {
							if (dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("loggedin"))
									dto.setActive(dto.getActive() + res);
								else if (lastStatus.equalsIgnoreCase("away"))
									dto.setAway(dto.getAway() + res);
								else if (lastStatus.equalsIgnoreCase("intraining"))
									dto.setInTraining(dto.getInTraining() + res);
								else if (lastStatus.equalsIgnoreCase("offline"))
									dto.setOffline(dto.getOffline() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
							dtMap.put("donotdisturb", audit.getStatus_time());
						} else if (audit.getStatus().trim().equalsIgnoreCase("intraining")) {
							if (dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("loggedin"))
									dto.setActive(dto.getActive() + res);
								else if (lastStatus.equalsIgnoreCase("away"))
									dto.setAway(dto.getAway() + res);
								else if (lastStatus.equalsIgnoreCase("donotdisturb"))
									dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
								else if (lastStatus.equalsIgnoreCase("offline"))
									dto.setOffline(dto.getOffline() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
							dtMap.put("intraining", audit.getStatus_time());
						} else if (audit.getStatus().trim().equalsIgnoreCase("offline")) {
							if (dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("loggedin"))
									dto.setActive(dto.getActive() + res);
								else if (lastStatus.equalsIgnoreCase("away"))
									dto.setAway(dto.getAway() + res);
								else if (lastStatus.equalsIgnoreCase("donotdisturb"))
									dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
								else if (lastStatus.equalsIgnoreCase("intraining"))
									dto.setInTraining(dto.getInTraining() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
							dtMap.put("offline", audit.getStatus_time());
						}

						lastStatus = audit.getStatus().trim().toLowerCase();
						date = format.format(audit.getStatus_time());
						userUid = audit.getUser_uid();
						dto.setChatTime(getChatTimes(date, userUid, session, 0));
						dto.setActiveChatTime(getChatTimes(date, userUid, session, 1));
						dto.setConcurrentChatTime(calculateConcurrentChatData(userUid, date, session));
						dto.setAvgHandleTime(calculateAverageHandleTime(userUid, date, session));
						dto.setTotalChat(getChatCounts(session, date, userUid));
					} else {
						if (date != null) {
							objMap.put(format.parse(date), dto);
							if (map.containsKey(userUid)) {
								Map<Date, AgentGridUserData> tempMap = new TreeMap<Date, AgentGridUserData>();
								tempMap = map.get(userUid);
								tempMap.putAll(objMap);
								map.put(userUid, tempMap);
							} else {
								map.put(userUid, objMap);
							}
						}

						dtMap = new TreeMap<String, Date>();
						objMap = new TreeMap<Date, AgentGridUserData>();
						dto = new AgentGridUserData();

						if (audit.getStatus().trim().equalsIgnoreCase("available")) {
							dtMap.put("available", audit.getStatus_time());
						} else if (audit.getStatus().trim().equalsIgnoreCase("loggedout")) {
							if (dtMap.containsKey("available")) {
								DateTime d1 = new DateTime(dtMap.get("available"));
								DateTime d2 = new DateTime(audit.getStatus_time());
								dto.setLoggedInTime(dto.getLoggedInTime()
										+ TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes()));
								dtMap.remove("available");
							}

							if (!lastStatus.equalsIgnoreCase("available") && dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("loggedin"))
									dto.setActive(dto.getActive() + res);
								else if (lastStatus.equalsIgnoreCase("away"))
									dto.setAway(dto.getAway() + res);
								else if (lastStatus.equalsIgnoreCase("donotdisturb"))
									dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
								else if (lastStatus.equalsIgnoreCase("intraining"))
									dto.setInTraining(dto.getInTraining() + res);
								else if (lastStatus.equalsIgnoreCase("offline"))
									dto.setOffline(dto.getOffline() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
						} else if (audit.getStatus().trim().equalsIgnoreCase("loggedin")) {
							if (dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("away"))
									dto.setAway(dto.getAway() + res);
								else if (lastStatus.equalsIgnoreCase("donotdisturb"))
									dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
								else if (lastStatus.equalsIgnoreCase("intraining"))
									dto.setInTraining(dto.getInTraining() + res);
								else if (lastStatus.equalsIgnoreCase("offline"))
									dto.setOffline(dto.getOffline() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
							dtMap.put("available", audit.getStatus_time());
						} else if (audit.getStatus().trim().equalsIgnoreCase("away")) {
							if (dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("loggedin"))
									dto.setActive(dto.getActive() + res);
								else if (lastStatus.equalsIgnoreCase("donotdisturb"))
									dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
								else if (lastStatus.equalsIgnoreCase("intraining"))
									dto.setInTraining(dto.getInTraining() + res);
								else if (lastStatus.equalsIgnoreCase("offline"))
									dto.setOffline(dto.getOffline() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
							dtMap.put("away", audit.getStatus_time());
						} else if (audit.getStatus().trim().equalsIgnoreCase("donotdisturb")) {
							if (dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("loggedin"))
									dto.setActive(dto.getActive() + res);
								else if (lastStatus.equalsIgnoreCase("away"))
									dto.setAway(dto.getAway() + res);
								else if (lastStatus.equalsIgnoreCase("intraining"))
									dto.setInTraining(dto.getInTraining() + res);
								else if (lastStatus.equalsIgnoreCase("offline"))
									dto.setOffline(dto.getOffline() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
							dtMap.put("donotdisturb", audit.getStatus_time());
						} else if (audit.getStatus().trim().equalsIgnoreCase("intraining")) {
							if (dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("loggedin"))
									dto.setActive(dto.getActive() + res);
								else if (lastStatus.equalsIgnoreCase("away"))
									dto.setAway(dto.getAway() + res);
								else if (lastStatus.equalsIgnoreCase("donotdisturb"))
									dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
								else if (lastStatus.equalsIgnoreCase("offline"))
									dto.setOffline(dto.getOffline() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
							dtMap.put("intraining", audit.getStatus_time());
						} else if (audit.getStatus().trim().equalsIgnoreCase("offline")) {
							if (dtMap.containsKey(lastStatus)) {
								DateTime d1 = new DateTime(dtMap.get(lastStatus));
								DateTime d2 = new DateTime(audit.getStatus_time());
								long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

								if (lastStatus.equalsIgnoreCase("loggedin"))
									dto.setActive(dto.getActive() + res);
								else if (lastStatus.equalsIgnoreCase("away"))
									dto.setAway(dto.getAway() + res);
								else if (lastStatus.equalsIgnoreCase("donotdisturb"))
									dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
								else if (lastStatus.equalsIgnoreCase("intraining"))
									dto.setInTraining(dto.getInTraining() + res);

								if (!lastStatus.equalsIgnoreCase("available"))
									dtMap.remove(lastStatus);
							}
							dtMap.put("offline", audit.getStatus_time());
						}
						lastStatus = audit.getStatus().trim().toLowerCase();
						userUid = audit.getUser_uid();
						date = format.format(audit.getStatus_time());
						dto.setChatTime(getChatTimes(date, userUid, session, 0));
						dto.setActiveChatTime(getChatTimes(date, userUid, session, 1));
						dto.setConcurrentChatTime(calculateConcurrentChatData(userUid, date, session));
						dto.setAvgHandleTime(calculateAverageHandleTime(userUid, date, session));
						dto.setTotalChat(getChatCounts(session, date, userUid));
						objMap.put(format.parse(date), dto);
					}

					if (objMap.containsKey(format.parse(date)))
						objMap.replace(format.parse(date), dto);
					if (i == list.size()) {
						if (map.containsKey(userUid)) {
							Map<Date, AgentGridUserData> tempMap = new TreeMap<Date, AgentGridUserData>();
							tempMap = map.get(userUid);
							tempMap.putAll(objMap);
							map.put(userUid, tempMap);
						} else {
							map.put(userUid, objMap);
						}
					}

				} else {
					if (userUid != null) {
						if (map.containsKey(userUid)) {
							Map<Date, AgentGridUserData> tempMap = new TreeMap<Date, AgentGridUserData>();
							tempMap = map.get(userUid);
							tempMap.putAll(objMap);
							map.put(userUid, tempMap);
						} else {
							map.put(userUid, objMap);
						}
					}

					dtMap = new TreeMap<String, Date>();
					objMap = new TreeMap<Date, AgentGridUserData>();
					dto = new AgentGridUserData();

					if (audit.getStatus().trim().equalsIgnoreCase("available")) {
						dtMap.put("available", audit.getStatus_time());
					} else if (audit.getStatus().trim().equalsIgnoreCase("loggedout")) {
						if (dtMap.containsKey("available")) {
							DateTime d1 = new DateTime(dtMap.get("available"));
							DateTime d2 = new DateTime(audit.getStatus_time());
							dto.setLoggedInTime(dto.getLoggedInTime()
									+ TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes()));
							dtMap.remove("available");
						}

						if (!lastStatus.equalsIgnoreCase("available") && dtMap.containsKey(lastStatus)) {
							DateTime d1 = new DateTime(dtMap.get(lastStatus));
							DateTime d2 = new DateTime(audit.getStatus_time());
							long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

							if (lastStatus.equalsIgnoreCase("loggedin"))
								dto.setActive(dto.getActive() + res);
							else if (lastStatus.equalsIgnoreCase("away"))
								dto.setAway(dto.getAway() + res);
							else if (lastStatus.equalsIgnoreCase("donotdisturb"))
								dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
							else if (lastStatus.equalsIgnoreCase("intraining"))
								dto.setInTraining(dto.getInTraining() + res);
							else if (lastStatus.equalsIgnoreCase("offline"))
								dto.setOffline(dto.getOffline() + res);

							if (!lastStatus.equalsIgnoreCase("available"))
								dtMap.remove(lastStatus);
						}
					} else if (audit.getStatus().trim().equalsIgnoreCase("loggedin")) {
						if (dtMap.containsKey(lastStatus)) {
							DateTime d1 = new DateTime(dtMap.get(lastStatus));
							DateTime d2 = new DateTime(audit.getStatus_time());
							long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

							if (lastStatus.equalsIgnoreCase("away"))
								dto.setAway(dto.getAway() + res);
							else if (lastStatus.equalsIgnoreCase("donotdisturb"))
								dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
							else if (lastStatus.equalsIgnoreCase("intraining"))
								dto.setInTraining(dto.getInTraining() + res);
							else if (lastStatus.equalsIgnoreCase("offline"))
								dto.setOffline(dto.getOffline() + res);

							if (!lastStatus.equalsIgnoreCase("available"))
								dtMap.remove(lastStatus);
						}
						dtMap.put("available", audit.getStatus_time());
					} else if (audit.getStatus().trim().equalsIgnoreCase("away")) {
						if (dtMap.containsKey(lastStatus)) {
							DateTime d1 = new DateTime(dtMap.get(lastStatus));
							DateTime d2 = new DateTime(audit.getStatus_time());
							long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

							if (lastStatus.equalsIgnoreCase("loggedin"))
								dto.setActive(dto.getActive() + res);
							else if (lastStatus.equalsIgnoreCase("donotdisturb"))
								dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
							else if (lastStatus.equalsIgnoreCase("intraining"))
								dto.setInTraining(dto.getInTraining() + res);
							else if (lastStatus.equalsIgnoreCase("offline"))
								dto.setOffline(dto.getOffline() + res);

							if (!lastStatus.equalsIgnoreCase("available"))
								dtMap.remove(lastStatus);
						}
						dtMap.put("away", audit.getStatus_time());
					} else if (audit.getStatus().trim().equalsIgnoreCase("donotdisturb")) {
						if (dtMap.containsKey(lastStatus)) {
							DateTime d1 = new DateTime(dtMap.get(lastStatus));
							DateTime d2 = new DateTime(audit.getStatus_time());
							long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

							if (lastStatus.equalsIgnoreCase("loggedin"))
								dto.setActive(dto.getActive() + res);
							else if (lastStatus.equalsIgnoreCase("away"))
								dto.setAway(dto.getAway() + res);
							else if (lastStatus.equalsIgnoreCase("intraining"))
								dto.setInTraining(dto.getInTraining() + res);
							else if (lastStatus.equalsIgnoreCase("offline"))
								dto.setOffline(dto.getOffline() + res);

							if (!lastStatus.equalsIgnoreCase("available"))
								dtMap.remove(lastStatus);
						}
						dtMap.put("donotdisturb", audit.getStatus_time());
					} else if (audit.getStatus().trim().equalsIgnoreCase("intraining")) {
						if (dtMap.containsKey(lastStatus)) {
							DateTime d1 = new DateTime(dtMap.get(lastStatus));
							DateTime d2 = new DateTime(audit.getStatus_time());
							long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

							if (lastStatus.equalsIgnoreCase("loggedin"))
								dto.setActive(dto.getActive() + res);
							else if (lastStatus.equalsIgnoreCase("away"))
								dto.setAway(dto.getAway() + res);
							else if (lastStatus.equalsIgnoreCase("donotdisturb"))
								dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
							else if (lastStatus.equalsIgnoreCase("offline"))
								dto.setOffline(dto.getOffline() + res);

							if (!lastStatus.equalsIgnoreCase("available"))
								dtMap.remove(lastStatus);
						}
						dtMap.put("intraining", audit.getStatus_time());
					} else if (audit.getStatus().trim().equalsIgnoreCase("offline")) {
						if (dtMap.containsKey(lastStatus)) {
							DateTime d1 = new DateTime(dtMap.get(lastStatus));
							DateTime d2 = new DateTime(audit.getStatus_time());
							long res = TimeUnit.MINUTES.toMillis(Minutes.minutesBetween(d1, d2).getMinutes());

							if (lastStatus.equalsIgnoreCase("loggedin"))
								dto.setActive(dto.getActive() + res);
							else if (lastStatus.equalsIgnoreCase("away"))
								dto.setAway(dto.getAway() + res);
							else if (lastStatus.equalsIgnoreCase("donotdisturb"))
								dto.setDoNotDisturb(dto.getDoNotDisturb() + res);
							else if (lastStatus.equalsIgnoreCase("intraining"))
								dto.setInTraining(dto.getInTraining() + res);

							if (!lastStatus.equalsIgnoreCase("available"))
								dtMap.remove(lastStatus);
						}
						dtMap.put("offline", audit.getStatus_time());
					}
					lastStatus = audit.getStatus().trim().toLowerCase();
					date = format.format(audit.getStatus_time());
					userUid = audit.getUser_uid();
					dto.setChatTime(getChatTimes(date, userUid, session, 0));
					dto.setActiveChatTime(getChatTimes(date, userUid, session, 1));
					dto.setConcurrentChatTime(calculateConcurrentChatData(userUid, date, session));
					dto.setAvgHandleTime(calculateAverageHandleTime(userUid, date, session));
					dto.setTotalChat(getChatCounts(session, date, userUid));
					objMap.put(format.parse(date), dto);
					if (i == list.size()) {
						if (map.containsKey(userUid)) {
							Map<Date, AgentGridUserData> tempMap = new TreeMap<Date, AgentGridUserData>();
							tempMap = map.get(userUid);
							tempMap.putAll(objMap);
							map.put(userUid, tempMap);
						} else {
							map.put(userUid, objMap);
						}
					}
				}
			}
			// System.out.println(new Gson().toJson(map));
			if (flag == 1)
				agentGridDatas = calculateMonthlyGridData(map, session);
			else if (flag == 2)
				agentGridDatas = calculateWeekGridData(map, session);
			else
				agentGridDatas = calculateGridData(map, session);

			createAgentGridCSV(agentGridDatas, session);
			session.getTransaction().commit();
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			System.out.println(ex.getMessage());
		} finally {
			session.close();
		}
	}

	private List<AgentGridData> calculateMonthlyGridData(Map<String, Map<Date, AgentGridUserData>> map,
			Session session) {
		List<AgentGridData> list = new ArrayList<AgentGridData>();
		Map<Integer, Map<String, AgentGridUserData>> dataMap = new TreeMap<Integer, Map<String, AgentGridUserData>>();
		Map<String, AgentGridUserData> monthMap = null;
		for (String uid : map.keySet()) {
			UsersEntity usersEntity = (UsersEntity) session.createCriteria(UsersEntity.class)
					.add(Restrictions.eq("uid", uid)).uniqueResult();
			Map<Date, AgentGridUserData> temp = new TreeMap<Date, AgentGridUserData>(map.get(uid));
			if (dataMap.containsKey(usersEntity.getId())) {
				monthMap = new TreeMap<String, AgentGridUserData>(dataMap.get(usersEntity.getId()));
				for (Date date : temp.keySet()) {
					String month = format3.format(date);
					AgentGridUserData agentGridUserData = temp.get(date);
					if (monthMap.containsKey(month)) {
						AgentGridUserData tempUsrData = new AgentGridUserData();
						tempUsrData = monthMap.get(month);

						tempUsrData.setActive(tempUsrData.getActive() + agentGridUserData.getActive());
						tempUsrData.setActiveChatTime(
								tempUsrData.getActiveChatTime() + agentGridUserData.getActiveChatTime());
						tempUsrData.setAway(tempUsrData.getAway() + agentGridUserData.getAway());
						tempUsrData.setChatTime(tempUsrData.getChatTime() + agentGridUserData.getChatTime());
						tempUsrData.setConcurrentChatTime(
								tempUsrData.getConcurrentChatTime() + agentGridUserData.getConcurrentChatTime());
						tempUsrData
								.setDoNotDisturb(tempUsrData.getDoNotDisturb() + agentGridUserData.getDoNotDisturb());
						tempUsrData.setInTraining(tempUsrData.getInTraining() + agentGridUserData.getInTraining());
						tempUsrData
								.setLoggedInTime(tempUsrData.getLoggedInTime() + agentGridUserData.getLoggedInTime());
						tempUsrData.setOffline(tempUsrData.getOffline() + agentGridUserData.getOffline());
						tempUsrData.setAvgHandleTime(
								tempUsrData.getAvgHandleTime() + agentGridUserData.getAvgHandleTime());
						tempUsrData.setTotalChat(tempUsrData.getTotalChat() + agentGridUserData.getTotalChat());
						monthMap.replace(month, tempUsrData);
					} else {
						monthMap.put(month, agentGridUserData);
					}
				}
				dataMap.put(usersEntity.getId(), monthMap);
			} else {
				monthMap = new TreeMap<String, AgentGridUserData>();
				for (Date date : temp.keySet()) {
					String month = format3.format(date);
					AgentGridUserData agentGridUserData = temp.get(date);
					if (monthMap.containsKey(month)) {
						AgentGridUserData tempUsrData = new AgentGridUserData();
						tempUsrData = monthMap.get(month);

						tempUsrData.setActive(tempUsrData.getActive() + agentGridUserData.getActive());
						tempUsrData.setActiveChatTime(
								tempUsrData.getActiveChatTime() + agentGridUserData.getActiveChatTime());
						tempUsrData.setAway(tempUsrData.getAway() + agentGridUserData.getAway());
						tempUsrData.setChatTime(tempUsrData.getChatTime() + agentGridUserData.getChatTime());
						tempUsrData.setConcurrentChatTime(
								tempUsrData.getConcurrentChatTime() + agentGridUserData.getConcurrentChatTime());
						tempUsrData
								.setDoNotDisturb(tempUsrData.getDoNotDisturb() + agentGridUserData.getDoNotDisturb());
						tempUsrData.setInTraining(tempUsrData.getInTraining() + agentGridUserData.getInTraining());
						tempUsrData
								.setLoggedInTime(tempUsrData.getLoggedInTime() + agentGridUserData.getLoggedInTime());
						tempUsrData.setOffline(tempUsrData.getOffline() + agentGridUserData.getOffline());
						tempUsrData.setAvgHandleTime(
								tempUsrData.getAvgHandleTime() + agentGridUserData.getAvgHandleTime());
						monthMap.replace(month, tempUsrData);
					} else {
						monthMap.put(month, agentGridUserData);
					}
				}
				dataMap.put(usersEntity.getId(), monthMap);
			}
		}

		for (Integer i : dataMap.keySet()) {
			UsersEntity usersEntity = (UsersEntity) session.get(UsersEntity.class, i);
			Map<String, AgentGridUserData> monthTempMap = new TreeMap<String, AgentGridUserData>(
					dataMap.get(usersEntity.getId()));
			for (String monthDataVal : monthTempMap.keySet()) {
				AgentGridUserData agentGridUserData = monthTempMap.get(monthDataVal);
				AgentGridData agentGridData = new AgentGridData();
				agentGridData.setMwd(monthDataVal);
				agentGridData.setUserId(usersEntity.getId() + "");
				agentGridData.setUserName(usersEntity.getFirstName() + " " + usersEntity.getLastName());
				agentGridData.setLoggedInTime(format2.format(agentGridUserData.getLoggedInTime()));
				agentGridData.setActiveTime(format2.format(agentGridUserData.getActive()));
				agentGridData.setChatTime(format2.format(agentGridUserData.getChatTime()));
				agentGridData.setActiveChatTime(format2.format(agentGridUserData.getActiveChatTime()));
				agentGridData.setInteractiveEngagements(format2.format(agentGridUserData.getActiveChatTime()));
				agentGridData.setChatTimeRate(
						twoDForm.format((agentGridUserData.getChatTime() / agentGridUserData.getLoggedInTime()) * 100));
				agentGridData.setActiveRate(
						twoDForm.format((agentGridUserData.getActive() / agentGridUserData.getLoggedInTime()) * 100));
				agentGridData.setAwayTime(format2.format(agentGridUserData.getAway()));
				agentGridData.setAwayRate(
						twoDForm.format((agentGridUserData.getAway() / agentGridUserData.getLoggedInTime()) * 100));
				agentGridData.setConcurrentChatTime(format2.format(agentGridUserData.getConcurrentChatTime()));
				list.add(agentGridData);
			}
		}
		return list;
	}

	private void createAgentGridCSV(List<AgentGridData> agentGridDatas, Session session) {
		CSVWriter write = null;
		String name = "Agent_Grid_" + new Date().getTime() + ".csv";
		String fileName = File_Path + "/Agent-Grid/" + name;
		try {
			write = new CSVWriter(new FileWriter(fileName));
			List<String[]> strList = new ArrayList<String[]>();
			String arr[] = new String[25];
			strList.add(new String[] { "Month/Week/Daily", "Workgroup", "Agent", "Logged In Time", "Active Time",
					"Active Rate", "Active Chat Time", "Away Time", "Away Rate", "Chat Time", "Chat Time Rate",
					"Concurrent Chat Time", "Number of chats", "Average Handle Time" });
			write.writeAll(strList);

			for (AgentGridData data : agentGridDatas) {
				int i = 0;
				arr[i++] = data.getMwd();
				arr[i++] = getDepartmentsByUserId(session, Integer.parseInt(data.getUserId()));
				arr[i++] = data.getUserName();
				arr[i++] = data.getLoggedInTime();
				arr[i++] = data.getActiveTime();
				arr[i++] = data.getActiveRate();
				arr[i++] = data.getActiveChatTime();
				arr[i++] = data.getAwayTime();
				arr[i++] = data.getAwayRate();
				arr[i++] = data.getChatTime();
				arr[i++] = data.getChatTimeRate();
				arr[i++] = data.getConcurrentChatTime();
				arr[i++] = data.getInteractiveEngagements();
				arr[i++] = data.getAverageHandleTime();

				write.writeNext(arr);
			}

			write.flush();
			write.close();

			AgentGridRecord record = new AgentGridRecord();
			record.setEnd_date(new Date());
			record.setFileName(name);
			record.setStart_date(startDate);
			record.setUserId(userId);
			session.save(record);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (write != null) {
					write.flush();
					write.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private List<AgentGridData> calculateWeekGridData(Map<String, Map<Date, AgentGridUserData>> map, Session session) {
		Map<Integer, Map<String, Map<Integer, AgentGridUserData>>> dataMap = new TreeMap<Integer, Map<String, Map<Integer, AgentGridUserData>>>();
		Map<String, Map<Integer, AgentGridUserData>> monthMap = null;
		Map<Integer, AgentGridUserData> weekMap = null;
		Calendar cal = Calendar.getInstance();

		for (String uid : map.keySet()) {
			UsersEntity usersEntity = (UsersEntity) session.createCriteria(UsersEntity.class)
					.add(Restrictions.eq("uid", uid)).uniqueResult();
			Map<Date, AgentGridUserData> temp = new TreeMap<Date, AgentGridUserData>(map.get(uid));

			if (dataMap.containsKey(usersEntity.getId())) {
				monthMap = new TreeMap<String, Map<Integer, AgentGridUserData>>(dataMap.get(usersEntity.getId()));
				for (Date date : temp.keySet()) {
					AgentGridUserData agentGridUserData = temp.get(date);
					cal.setTime(date);
					int week = cal.get(Calendar.WEEK_OF_YEAR);
					String month = format3.format(date);

					if (monthMap.containsKey(month)) {
						weekMap = new TreeMap<Integer, AgentGridUserData>(monthMap.get(month));
						if (weekMap.containsKey(week)) {
							AgentGridUserData tempUsrData = new AgentGridUserData();
							tempUsrData = weekMap.get(week);

							tempUsrData.setActive(tempUsrData.getActive() + agentGridUserData.getActive());
							tempUsrData.setActiveChatTime(
									tempUsrData.getActiveChatTime() + agentGridUserData.getActiveChatTime());
							tempUsrData.setAway(tempUsrData.getAway() + agentGridUserData.getAway());
							tempUsrData.setChatTime(tempUsrData.getChatTime() + agentGridUserData.getChatTime());
							tempUsrData.setConcurrentChatTime(
									tempUsrData.getConcurrentChatTime() + agentGridUserData.getConcurrentChatTime());
							tempUsrData.setDoNotDisturb(
									tempUsrData.getDoNotDisturb() + agentGridUserData.getDoNotDisturb());
							tempUsrData.setInTraining(tempUsrData.getInTraining() + agentGridUserData.getInTraining());
							tempUsrData.setLoggedInTime(
									tempUsrData.getLoggedInTime() + agentGridUserData.getLoggedInTime());
							tempUsrData.setOffline(tempUsrData.getOffline() + agentGridUserData.getOffline());
							tempUsrData.setAvgHandleTime(
									tempUsrData.getAvgHandleTime() + agentGridUserData.getAvgHandleTime());
							tempUsrData.setTotalChat(tempUsrData.getTotalChat() + agentGridUserData.getTotalChat());
							weekMap.replace(week, tempUsrData);
						} else {
							weekMap.put(week, agentGridUserData);
						}
						monthMap.replace(month, weekMap);
					} else {
						weekMap = new TreeMap<Integer, AgentGridUserData>();
						weekMap.put(week, agentGridUserData);
						monthMap.put(month, weekMap);
					}
				}
				dataMap.replace(usersEntity.getId(), monthMap);
			} else {
				monthMap = new TreeMap<String, Map<Integer, AgentGridUserData>>();
				for (Date date : temp.keySet()) {
					AgentGridUserData agentGridUserData = temp.get(date);
					cal.setTime(date);
					int week = cal.get(Calendar.WEEK_OF_MONTH);
					String month = format3.format(date);

					if (monthMap.containsKey(month)) {
						weekMap = new TreeMap<Integer, AgentGridUserData>(monthMap.get(month));
						if (weekMap.containsKey(week)) {
							AgentGridUserData tempUsrData = new AgentGridUserData();
							tempUsrData = weekMap.get(week);

							tempUsrData.setActive(tempUsrData.getActive() + agentGridUserData.getActive());
							tempUsrData.setActiveChatTime(
									tempUsrData.getActiveChatTime() + agentGridUserData.getActiveChatTime());
							tempUsrData.setAway(tempUsrData.getAway() + agentGridUserData.getAway());
							tempUsrData.setChatTime(tempUsrData.getChatTime() + agentGridUserData.getChatTime());
							tempUsrData.setConcurrentChatTime(
									tempUsrData.getConcurrentChatTime() + agentGridUserData.getConcurrentChatTime());
							tempUsrData.setDoNotDisturb(
									tempUsrData.getDoNotDisturb() + agentGridUserData.getDoNotDisturb());
							tempUsrData.setInTraining(tempUsrData.getInTraining() + agentGridUserData.getInTraining());
							tempUsrData.setLoggedInTime(
									tempUsrData.getLoggedInTime() + agentGridUserData.getLoggedInTime());
							tempUsrData.setOffline(tempUsrData.getOffline() + agentGridUserData.getOffline());
							tempUsrData.setAvgHandleTime(
									tempUsrData.getAvgHandleTime() + agentGridUserData.getAvgHandleTime());

							weekMap.replace(week, tempUsrData);
						} else {
							weekMap.put(week, agentGridUserData);
						}
						monthMap.replace(month, weekMap);
					} else {
						weekMap = new TreeMap<Integer, AgentGridUserData>();
						weekMap.put(week, agentGridUserData);
						monthMap.put(month, weekMap);
					}
				}
				dataMap.put(usersEntity.getId(), monthMap);
			}
		}

		List<AgentGridData> list = new ArrayList<AgentGridData>();

		for (int dataKey : dataMap.keySet()) {
			UsersEntity usersEntity = (UsersEntity) session.get(UsersEntity.class, dataKey);
			Map<String, Map<Integer, AgentGridUserData>> monthTempMap = new TreeMap<String, Map<Integer, AgentGridUserData>>(
					dataMap.get(usersEntity.getId()));

			for (String month : monthTempMap.keySet()) {
				Map<Integer, AgentGridUserData> weekTempMap = new TreeMap<Integer, AgentGridUserData>(
						monthTempMap.get(month));

				for (Integer i : weekTempMap.keySet()) {
					AgentGridUserData agentGridUserData = weekTempMap.get(i);
					AgentGridData agentGridData = new AgentGridData();
					agentGridData.setMwd(month + " - Week " + i);
					agentGridData.setUserId(usersEntity.getId() + "");
					agentGridData.setUserName(usersEntity.getFirstName() + " " + usersEntity.getLastName());
					agentGridData.setLoggedInTime(format2.format(agentGridUserData.getLoggedInTime()));
					agentGridData.setActiveTime(format2.format(agentGridUserData.getActive()));
					agentGridData.setChatTime(format2.format(agentGridUserData.getChatTime()));
					agentGridData.setActiveChatTime(format2.format(agentGridUserData.getActiveChatTime()));
					agentGridData.setInteractiveEngagements(format2.format(agentGridUserData.getActiveChatTime()));
					agentGridData.setChatTimeRate(twoDForm
							.format((agentGridUserData.getChatTime() / agentGridUserData.getLoggedInTime()) * 100));
					agentGridData.setActiveRate(twoDForm
							.format((agentGridUserData.getActive() / agentGridUserData.getLoggedInTime()) * 100));
					agentGridData.setAwayTime(format2.format(agentGridUserData.getAway()));
					agentGridData.setAwayRate(
							twoDForm.format((agentGridUserData.getAway() / agentGridUserData.getLoggedInTime()) * 100));
					agentGridData.setConcurrentChatTime(format2.format(agentGridUserData.getConcurrentChatTime()));
					agentGridData.setAverageHandleTime(format2.format(agentGridUserData.getAvgHandleTime()));
					list.add(agentGridData);
				}
			}
		}

		return list;
	}

	private List<AgentGridData> calculateGridData(Map<String, Map<Date, AgentGridUserData>> map, Session session) {
		List<AgentGridData> list = new ArrayList<AgentGridData>();

		for (String uid : map.keySet()) {
			UsersEntity usersEntity = (UsersEntity) session.createCriteria(UsersEntity.class)
					.add(Restrictions.eq("uid", uid)).uniqueResult();

			Map<Date, AgentGridUserData> temp = new TreeMap<Date, AgentGridUserData>(map.get(uid));
			for (Date date : temp.keySet()) {
				AgentGridUserData agentGridUserData = temp.get(date);
				AgentGridData agentGridData = new AgentGridData();
				agentGridData.setMwd(format1.format(date));
				agentGridData.setUserId(usersEntity.getId() + "");
				agentGridData.setUserName(usersEntity.getFirstName() + " " + usersEntity.getLastName());
				agentGridData.setLoggedInTime(format2.format(agentGridUserData.getLoggedInTime()));
				agentGridData.setActiveTime(format2.format(agentGridUserData.getActive()));
				agentGridData.setChatTime(format2.format(agentGridUserData.getChatTime()));
				agentGridData.setActiveChatTime(format2.format(agentGridUserData.getActiveChatTime()));
				agentGridData.setInteractiveEngagements(format2.format(agentGridUserData.getActiveChatTime()));
				agentGridData.setChatTimeRate(
						twoDForm.format((agentGridUserData.getChatTime() / agentGridUserData.getLoggedInTime()) * 100));
				agentGridData.setActiveRate(
						twoDForm.format((agentGridUserData.getActive() / agentGridUserData.getLoggedInTime()) * 100));
				agentGridData.setAwayTime(format2.format(agentGridUserData.getAway()));
				agentGridData.setAwayRate(
						twoDForm.format((agentGridUserData.getAway() / agentGridUserData.getLoggedInTime()) * 100));
				agentGridData.setConcurrentChatTime(format2.format(agentGridUserData.getConcurrentChatTime()));
				agentGridData.setAverageHandleTime(format2.format(agentGridUserData.getAvgHandleTime()));
				agentGridData.setInteractiveEngagements(agentGridUserData.getTotalChat() + "");
				list.add(agentGridData);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private long getChatTimes(String date, String userId, Session session, int iFlag) {
		long total = 0;
		String query = "";
		UsersEntity usersEntity = (UsersEntity) session.createCriteria(UsersEntity.class)
				.add(Restrictions.eq("uid", userId)).uniqueResult();
		List<Integer> list = (List<Integer>) session
				.createSQLQuery("SELECT session_id FROM session_users AS su WHERE user_id = " + usersEntity.getId()
						+ " AND DATE(created_date) = '" + date + "' ")
				.list();

		if (iFlag == 0) {
			query = "SELECT (TIME_TO_SEC(TIMEDIFF(MIN(su.created_date), (SELECT MIN(s.end_date) FROM session_users AS s WHERE s.session_id = @sessionId)))*1000) AS timeDifference FROM session_users AS su WHERE su.user_id = @userId AND su.session_id = @sessionId";
		} else {
			query = "SELECT (TIME_TO_SEC(TIMEDIFF(su.created_date, (SELECT MIN(s.end_date) FROM session_users AS s WHERE s.session_id = @sessionId)))*1000) AS timeDifference FROM session_users AS su WHERE su.user_id = @userId AND su.session_id = @sessionId";
		}

		if (list != null && list.size() > 0) {
			for (Integer i : list) {
				List<Long> val = (List<Long>) session.createSQLQuery(
						query.replace("@sessionId", i.toString()).replace("@userId", usersEntity.getId().toString()))
						.list();
				if (val != null && val.size() > 0) {
					Object res = val.get(0);
					if (res == null)
						res = 0;
					total += Long.parseLong(res.toString());
				}
			}
		}
		return total;
	}

	@SuppressWarnings("unchecked")
	private long calculateConcurrentChatData(String userId, String date, Session session) {
		long count = 0;
		UsersEntity usersEntity = (UsersEntity) session.createCriteria(UsersEntity.class)
				.add(Restrictions.eq("uid", userId)).uniqueResult();

		List<AgentGridEntity> list = (List<AgentGridEntity>) session
				.createSQLQuery("SELECT * FROM agentgrid WHERE user_id = " + usersEntity.getId()
						+ " and DATE(created_date) = '" + date + "' ORDER BY user_id, created_date")
				.setResultTransformer(Transformers.aliasToBean(AgentGridEntity.class)).list();

		for (int i = 0; i < list.size(); i++) {
			AgentGridEntity iData = list.get(i);
			long temp = 0;
			if (i > 0) {
				for (int j = i - 1; j >= 0; j--) {
					AgentGridEntity jData = list.get(j);
					if ((jData.getCreated_date().equals(iData.getCreated_date())
							|| jData.getEnd_date().equals(iData.getCreated_date()))
							|| (jData.getCreated_date().before(iData.getCreated_date())
									&& jData.getEnd_date().after(iData.getCreated_date()))) {
						if (!(iData.getEnd_date().after(jData.getCreated_date())
								&& (iData.getEnd_date().equals(jData.getEnd_date())
										|| iData.getEnd_date().before(jData.getEnd_date())))) {
							if (iData.getEnd_date().before(jData.getEnd_date())) {
								temp = iData.getEnd_date().getTime() - iData.getCreated_date().getTime();
							} else {
								temp = jData.getEnd_date().getTime() - iData.getCreated_date().getTime();
							}
						} else {
							break;
						}
					}
				}
				count += temp;
				temp = 0;
			} else {
				count = iData.getEnd_date().getTime() - iData.getCreated_date().getTime();
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	private long calculateAverageHandleTime(String userId, String date, Session session) {
		long aht = 0;
		UsersEntity usersEntity = (UsersEntity) session.createCriteria(UsersEntity.class)
				.add(Restrictions.eq("uid", userId)).uniqueResult();
		List<Integer> list = (List<Integer>) session
				.createSQLQuery("SELECT session_id FROM session_users AS su WHERE user_id = " + usersEntity.getId()
						+ " AND DATE(created_date) = '" + date + "' ")
				.list();

		for (Integer temp1 : list) {
			ChatSessionEntity entity = getChatSessionByid(temp1, session);
			List<ChatSessionEntity> temp = getChatSessionByReasonId(entity.getReasons().getId(), session);
			int totalReason = temp.size();

			int totalHandleTime = 0;
			long totalWaitingTime = 0;
			long waitingTime = 0;
			long waitingTimeSender = 0;
			long waitingTimeReceiver = 0;

			for (ChatSessionEntity model : temp) {
				int handle_time_in_sec_clone = 0;
				int handle_time_in_sec = 1;
				int no_of_chats_per_session = 0;

				List<SessionUsersEntity> data = getData(session, model.getId());
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

			long average_handle_time = (totalHandleTime / totalReason);
			aht += new DateTime(average_handle_time).getMillis();
		}

		return aht;
	}

	@SuppressWarnings("unchecked")
	private List<SessionUsersEntity> getData(Session session, long id) {
		List<SessionUsersEntity> data = new ArrayList<SessionUsersEntity>();
		try {
			Criteria criteria = session.createCriteria(SessionUsersEntity.class);
			criteria.add(Restrictions.eq("chatSession.id", id));
			data = (List<SessionUsersEntity>) criteria.list();
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return data;
	}

	private ChatSessionEntity getChatSessionByid(long id, Session session) {
		ChatSessionEntity entiry = null;
		try {
			entiry = (ChatSessionEntity) session.get(ChatSessionEntity.class, id);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return entiry;
	}

	@SuppressWarnings("unchecked")
	private List<ChatSessionEntity> getChatSessionByReasonId(Integer integer, Session session) {
		List<ChatSessionEntity> list = null;
		try {
			String query = "SELECT * FROM chat_session WHERE reason_id = :key";
			SQLQuery qurRes = session.createSQLQuery(query);
			qurRes.addEntity(ChatSessionEntity.class);
			qurRes.setParameter("key", integer);
			list = qurRes.list();
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private int getChatCounts(Session session, String date, String userUId) {
		UsersEntity usersEntity = (UsersEntity) session.createCriteria(UsersEntity.class)
				.add(Restrictions.eq("uid", userUId)).uniqueResult();

		int count = 0;
		String qur = "SELECT DISTINCT session_id FROM session_users WHERE DATE(created_date) = '" + date
				+ "' AND user_id = " + usersEntity.getId() + ";";

		try {
			List<Integer> list = (List<Integer>) session.createSQLQuery(qur).list();
			for (int id : list) {
				List<SessionUsersEntity> sList = (List<SessionUsersEntity>) session
						.createCriteria(SessionUsersEntity.class)
						.add(Restrictions.eq("chatSession.id", Long.parseLong(id + ""))).list();
				if (sList.size() >= 2)
					count++;
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	private String getDepartmentsByUserId(Session session, int userId) {
		String dept = null;
		try {
			List<DeptLocUsersEntity> userDept = (List<DeptLocUsersEntity>) session
					.createCriteria(DeptLocUsersEntity.class).add(Restrictions.eq("users.id", userId)).list();
			for (DeptLocUsersEntity data : userDept) {
				if (data.getDeptLocation() != null && data.getDeptLocation().getDepartments() != null
						&& data.getDeptLocation().getDepartments().getDepartmentName() != null) {
					if (dept == null) {
						dept = data.getDeptLocation().getDepartments().getDepartmentName();
					} else {
						String t = data.getDeptLocation().getDepartments().getDepartmentName();
						if (!dept.contains(t)) {
							dept += ", " + t;
						}
					}
				}
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return dept;
	}
}