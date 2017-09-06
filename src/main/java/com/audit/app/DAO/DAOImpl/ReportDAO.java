package com.audit.app.DAO.DAOImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.audit.app.DAO.IDAO.IReportDAO;
import com.audit.app.DAO.ThreadImpl.AgentGridFReportThreadImpl;
import com.audit.app.DTO.ChatGridDTO;
import com.audit.app.DTO.CommonReportResultDTO;
import com.audit.app.DTO.WorkgroupAndLocationDTO;
import com.audit.app.DTO.WorkgroupWithNoLocation;
import com.audit.app.Common.ApiStatus;
import com.audit.app.DAO.DAOQueryHolder.ReportQueryHolder;
import com.audit.app.Entities.AgentGridRecord;
import com.audit.app.Entities.Workgroup_By_Location_User;
import com.audit.app.Entities.jpa.ChatSessionEntity;
import com.audit.app.Entities.jpa.DeptLocationEntity;
import com.audit.app.Entities.jpa.SessionUsersEntity;

@Service
@Transactional
public class ReportDAO implements IReportDAO {

	@Autowired
	private SessionFactory factory;

	@Value("${file.path}")
	private String File_Path;

	@Autowired
	private ThreadPoolTaskExecutor executor;

	private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings("unchecked")
	public List<ChatSessionEntity> getChatHistoryReportData(int reasonId) {
		Session session = factory.openSession();
		List<ChatSessionEntity> list = null;
		try {
			String query = "SELECT * FROM chat_session";
			session.beginTransaction();

			if (reasonId > 0) {
				query += " WHERE reason_id = :key";
			} else {
				query += " GROUP BY reason_id";
			}

			SQLQuery qurRes = session.createSQLQuery(query);
			qurRes.addEntity(ChatSessionEntity.class);
			if (reasonId > 0)
				qurRes.setParameter("key", reasonId);

			// Criteria criteria=
			// session.createCriteria(ChatSessionEntity.class);
			/*
			 * criteria.setProjection(Projections.projectionList().add(
			 * Projections.groupProperty("reasons.id"), "reasons.id"));
			 * if(reasonId > 0){ criteria.add(Restrictions.eq("reasons.id",
			 * reasonId)); } list = criteria.list();
			 */
			list = qurRes.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	public int getChatCount(int reasonId, int flag) {
		int count = 0;
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(ChatSessionEntity.class);
			criteria.add(Restrictions.eq("reasons.id", reasonId));
			if (flag == 1)
				criteria.add(Restrictions.eq("status", "Active"));
			else if (flag == 2)
				criteria.add(Restrictions.ne("status", "Active"));
			else if (flag == 3)
				criteria.add(Restrictions.eq("status", "NoTiming"));
			count = criteria.list().size();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SessionUsersEntity> getUsersSessionBySessionId(long id) {
		Session session = factory.openSession();
		List<SessionUsersEntity> data = new ArrayList<SessionUsersEntity>();
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(SessionUsersEntity.class);
			criteria.add(Restrictions.eq("chatSession.id", id));
			data = (List<SessionUsersEntity>) criteria.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getIntervalData(String startDate, String endDate) {
		Session session = factory.openSession();
		Object list = new Object();
		try {
			session.beginTransaction();
			String query = ReportQueryHolder.GET_INTERVAL_DATA.replace("#startDate", startDate).replace("#endDate",
					endDate);
			list = (List<CommonReportResultDTO>) session.createSQLQuery(query).list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkgroupAndLocationDTO> getWorkgroupAndLocationWithouUser(String deptName, String locName) {
		Session session = factory.openSession();
		List<WorkgroupAndLocationDTO> list = new ArrayList<WorkgroupAndLocationDTO>();
		String condition = "";
		try {
			if (deptName != null && !deptName.isEmpty() && !deptName.trim().equals("undefined"))
				condition += " AND dept.department_name like '%" + deptName + "%'";
			if (locName != null && !locName.isEmpty() && !locName.trim().equals("undefined"))
				condition += " AND loc_1.location_name like '%" + locName + "%'";

			SQLQuery query = session.createSQLQuery(ReportQueryHolder.GET_WORKGROUP_LOCATION_WITHOUT_USER + condition);
			query.addScalar("id").addScalar("department_name").addScalar("location_name").addScalar("address")
					.addScalar("location_description").addScalar("primary_contact");
			query.setResultTransformer(Transformers.aliasToBean(WorkgroupAndLocationDTO.class));
			list = (ArrayList<WorkgroupAndLocationDTO>) query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkgroupWithNoLocation> getWorkgroupsByNoLocation(String startDate, String endDate, String deptName) {
		Session session = factory.openSession();
		List<WorkgroupWithNoLocation> list = new ArrayList<WorkgroupWithNoLocation>();
		String condition = "";
		try {
			/*
			 * if (startDate != null && !startDate.isEmpty() &&
			 * !startDate.trim().equals("undefined")) condition +=
			 * " AND dept.created_date BETWEEN '" + startDate + "' AND '" +
			 * endDate + "'";
			 */
			if (deptName != null && !deptName.isEmpty() && !deptName.trim().equals("undefined"))
				condition += " AND dept.department_name like '%" + deptName + "%'";

			SQLQuery query = session.createSQLQuery(ReportQueryHolder.GET_WORKGROUP_NO_LOCATION + condition);
			query.addScalar("id").addScalar("department_name").addScalar("department_description")
					.addScalar("created_date").addScalar("status").addScalar("abbr_name").addScalar("primary_contact")
					.addScalar("secondary_contact").addScalar("date");
			query.setResultTransformer(Transformers.aliasToBean(WorkgroupWithNoLocation.class));
			list = (ArrayList<WorkgroupWithNoLocation>) query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkgroupAndLocationDTO> getWorkgroupAndLocationWithoutSkillMapping(String deptName, String locName) {
		Session session = factory.openSession();
		List<WorkgroupAndLocationDTO> list = new ArrayList<WorkgroupAndLocationDTO>();
		String condition = "";
		try {
			if (deptName != null && !deptName.isEmpty() && !deptName.trim().equals("undefined"))
				condition += " AND dept.department_name like '%" + deptName + "%'";
			if (locName != null && !locName.isEmpty() && !locName.trim().equals("undefined"))
				condition += " AND loc_1.location_name like '%" + locName + "%'";

			SQLQuery query = session
					.createSQLQuery(ReportQueryHolder.GET_WORKGROUP_LOCATION_WITHOUT_SKILL_MAPPING + condition);
			query.addScalar("id").addScalar("department_name").addScalar("location_name").addScalar("address")
					.addScalar("location_description").addScalar("primary_contact");
			query.setResultTransformer(Transformers.aliasToBean(WorkgroupAndLocationDTO.class));
			list = (ArrayList<WorkgroupAndLocationDTO>) query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Workgroup_By_Location_User> getWorkgroupByLocationAndUser(String deptName, String locName,
			String firstName, String lastName) {
		Session session = factory.openSession();
		List<Workgroup_By_Location_User> data = new ArrayList<Workgroup_By_Location_User>();
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Workgroup_By_Location_User.class);
			if (deptName != null && !deptName.isEmpty() && !deptName.trim().equals("undefined"))
				criteria.add(Restrictions.like("department_name", deptName, MatchMode.ANYWHERE));
			if (locName != null && !locName.isEmpty() && !locName.trim().equals("undefined"))
				criteria.add(Restrictions.like("location_name", locName, MatchMode.ANYWHERE));
			if (firstName != null && !firstName.isEmpty() && !firstName.trim().equals("undefined"))
				criteria.add(Restrictions.like("first_name", firstName, MatchMode.ANYWHERE));
			if (lastName != null && !lastName.isEmpty() && !lastName.trim().equals("undefined"))
				criteria.add(Restrictions.like("last_name", lastName, MatchMode.ANYWHERE));
			data = (List<Workgroup_By_Location_User>) criteria.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChatGridDTO> getChatGridData(String startDate, String endDate) {
		List<ChatGridDTO> list = new ArrayList<ChatGridDTO>();
		Session session = factory.openSession();
		String condition = null;
		try {
			if (startDate != null && !startDate.isEmpty() && !startDate.trim().equals("undefined"))
				condition += "AND cs.created_date BETWEEN '" + startDate + "' AND '" + endDate + "'";

			if (condition == null)
				condition = "And 1=1";

			SQLQuery query = session.createSQLQuery(ReportQueryHolder.CHAT_GRID.replace("#condition", condition));
			query.addScalar("id").addScalar("date").addScalar("day").addScalar("reason_id").addScalar("reason_name")
					.addScalar("department_id").addScalar("workgroup").addScalar("locationName");
			query.setResultTransformer(Transformers.aliasToBean(ChatGridDTO.class));
			list = (ArrayList<ChatGridDTO>) query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public int getChatCount(int reasonId, int flag, String startDate, String endDate) {
		int count = 0;
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(ChatSessionEntity.class);
			criteria.add(Restrictions.eq("reasons.id", reasonId));
			if (startDate != null && !startDate.isEmpty() && !startDate.trim().equals("undefined")) {
				// condition += "WHERE cs.created_date BETWEEN '" + startDate +
				// "' AND '" + endDate + "'";
				criteria.add(Restrictions.between("createdDate", sFormat.parse(startDate), sFormat.parse(endDate)));
			}

			if (flag == 1)
				criteria.add(Restrictions.eq("status", "Active"));
			else if (flag == 2)
				criteria.add(Restrictions.ne("status", "Active"));
			else if (flag == 3)
				criteria.add(Restrictions.eq("status", "NoTiming"));
			count = criteria.list().size();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChatSessionEntity> getChatHistoryReportData(int reasonId, String startDate, String endDate) {
		Session session = factory.openSession();
		List<ChatSessionEntity> list = null;
		try {
			String query = "SELECT * FROM chat_session WHERE 1=1";
			session.beginTransaction();

			if (startDate != null && !startDate.isEmpty() && !startDate.trim().equals("undefined")) {
				query += " AND created_date BETWEEN '" + startDate + "' AND '" + endDate + "'";
			}

			if (reasonId > 0) {
				query += " AND reason_id = :key";
			} else {
				query += " GROUP BY reason_id";
			}

			SQLQuery qurRes = session.createSQLQuery(query);
			qurRes.addEntity(ChatSessionEntity.class);
			if (reasonId > 0)
				qurRes.setParameter("key", reasonId);
			list = qurRes.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public void getAgentGridDTO(String startDate, String endDate, int flag, int userid) {
		ApiStatus status = new ApiStatus();
		AgentGridFReportThreadImpl thread = new AgentGridFReportThreadImpl(factory, flag, File_Path, userid, startDate,
				endDate);
		executor.execute(thread);
		status.setCode(200);
		status.setMessage("Success");
		status.setData("Success");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentGridRecord> getRecordsByUserId(int userId) {
		List<AgentGridRecord> list = null;
		Session session = factory.openSession();
		try {
			list = (List<AgentGridRecord>) session.createCriteria(AgentGridRecord.class)
					.add(Restrictions.eq("userId", userId)).list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTimeInterval() {
		List<String> list = null;
		Session session = factory.openSession();
		try {
			list = (List<String>) session.createSQLQuery(ReportQueryHolder.GET_INTERVAL).list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getChatSessionIds(String startDate, String endDate) {
		String qur = ReportQueryHolder.GET_CHAT_SESSION_IDS.replace("#startDate", startDate).replace("#endDate",
				endDate);
		List<Integer> list = null;
		Session session = factory.openSession();
		try {
			list = (List<Integer>) session.createSQLQuery(qur).list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getChatCounts(int sessionId) {
		List<SessionUsersEntity> list = new ArrayList<SessionUsersEntity>();
		Session session = factory.openSession();
		try {
			list = (List<SessionUsersEntity>) session.createCriteria(SessionUsersEntity.class)
					.add(Restrictions.eq("chatSession.id", Long.parseLong(sessionId + ""))).list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return list.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getLocations(int deptId) {
		String loc = null;
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			List<DeptLocationEntity> list = (List<DeptLocationEntity>) session.createCriteria(DeptLocationEntity.class)
					.add(Restrictions.eq("departments.id", deptId)).list();
			for (DeptLocationEntity ent : list) {
				if (loc == null) {
					loc = ent.getLocations() == null ? null : ent.getLocations().getLocationName();
				} else {
					if (ent.getLocations() != null && ent.getLocations().getLocationName() != null
							&& !ent.getLocations().getLocationName().isEmpty())
						loc += ", " + ent.getLocations().getLocationName();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return loc;
	}

	@Override
	public long getSystemPerformanceReport(String startDate, String endDate, int flag) {
		long count = 0;
		Session session = factory.openSession();
		String qur = null;
		try {
			if (flag == 1) {
				qur = "SELECT COUNT(*) AS val FROM session_users INNER JOIN users ON users.id = session_users.user_id INNER JOIN chat_session ON chat_session.id = session_users.session_id LEFT JOIN departments ON departments.id = chat_session.department_id INNER JOIN reasons ON reasons.id = chat_session.reason_id WHERE session_users.status = 'Pending' AND chat_session.created_date BETWEEN '"
						+ startDate + "' AND '" + endDate + "' AND reasons.status = 'A';";
			} else if (flag == 2) {
				qur = "SELECT COUNT(*) AS val FROM dept_loc_users AS du INNER JOIN users As u on du.user_id = u.id AND du.sender = 'A' WHERE du.`status` = 'A' AND u.uid IN (SELECT user_uid FROM status_audit WHERE `status`  = 'LoggedIn' AND status_time BETWEEN '"
						+ startDate + "' AND '" + endDate + "');";
			} else if (flag == 3) {
				qur = "SELECT COUNT(*) AS val FROM dept_loc_users AS du INNER JOIN users As u on du.user_id = u.id AND du.receiver = 'A' WHERE du.`status` = 'A' AND u.uid IN (SELECT user_uid FROM status_audit WHERE `status`  = 'Available' AND status_time BETWEEN '"
						+ startDate + "' AND '" + endDate + "');";
			} else if (flag == 4) {
				qur = "SELECT COUNT(*) AS val FROM dept_loc_users AS du INNER JOIN users As u on du.user_id = u.id AND du.sender = 'A' WHERE du.`status` = 'A' AND u.uid IN (SELECT user_uid FROM status_audit WHERE `status`  = 'Available' AND status_time BETWEEN '"
						+ startDate + "' AND '" + endDate + "');";
			} else if (flag == 5) {
				qur = "SELECT COUNT(*) AS val FROM dept_loc_users AS du INNER JOIN users As u on du.user_id = u.id AND du.receiver = 'A' WHERE du.`status` = 'A' AND u.uid IN (SELECT user_uid FROM status_audit WHERE `status`  = 'LoggedIn' AND status_time BETWEEN '"
						+ startDate + "' AND '" + endDate + "');";
			} else if (flag == 6) {
				qur = "SELECT COUNT(*) AS val FROM (SELECT su.session_id FROM session_users AS su WHERE su.session_id IN (SELECT cs.id FROM chat_session AS cs WHERE cs.created_date BETWEEN '"
						+ startDate + "' AND '" + endDate
						+ "') GROUP BY su.session_id HAVING COUNT(su.session_id) >= 2) AS tab";
			} else if (flag == 7) {
				qur = "SELECT COUNT(*) AS val FROM (SELECT su.session_id FROM session_users AS su WHERE su.`status` = 'Closed' AND su.end_date BETWEEN '"
						+ startDate + "' AND '" + endDate
						+ "' GROUP BY su.session_id HAVING COUNT(su.session_id) >= 2) AS tab";
			} else if (flag == 8) {
				qur = "SELECT COUNT(*) AS val FROM (SELECT su.session_id FROM session_users AS su WHERE su.`status` = 'Closed' AND su.end_date BETWEEN '"
						+ startDate + "' AND '" + endDate
						+ "' GROUP BY su.session_id HAVING COUNT(su.session_id) = 1) AS tab";
			} else if (flag == 9) {
				qur = "SELECT COUNT(*) AS val FROM(SELECT COUNT(*) AS val FROM session_users AS su WHERE su.`status` = 'Closed' AND su.created_date BETWEEN '"
						+ startDate + "' AND '" + endDate + "' AND su.end_date  BETWEEN '" + startDate + "' AND '"
						+ endDate + "' GROUP BY su.session_id HAVING COUNT(*) >=2) AS tab";
			}

			count = (Long) session.createSQLQuery(qur).addScalar("val", LongType.INSTANCE).uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}
}
