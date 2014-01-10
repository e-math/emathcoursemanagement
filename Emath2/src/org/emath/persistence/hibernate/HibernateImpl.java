package org.emath.persistence.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.emath.model.book.AddedContent;
import org.emath.model.book.AssignmentSolution;
import org.emath.model.book.Book;
import org.emath.model.book.BookUpdate;
import org.emath.model.book.Comment;
import org.emath.model.communication.ClientBook;
import org.emath.model.communication.Course;
import org.emath.model.communication.CourseLock;
import org.emath.model.communication.DummySystemUpdate;
import org.emath.model.communication.Exam;
import org.emath.model.communication.ExamAccess;
import org.emath.model.communication.LogEntry;
import org.emath.model.communication.School;
import org.emath.model.communication.SystemUpdate;
import org.emath.model.communication.TrafficLogEntry;
import org.emath.model.communication.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
/**
 * This is the hibernate implementation of the e-math persistence layer.
 *
 */
public class HibernateImpl implements DatabaseFacade {

	private SessionFactory sessionFactory;

	public HibernateImpl() {
		this.sessionFactory = new Configuration().configure()
				.buildSessionFactory();

	}
	/*
	@Override
	public Course getCourseByCourseId(int courseId) {
		Session session = getSession();
		Transaction tx = null;
		Course c = null;
		List courses;
		try {
			tx = session.beginTransaction();
			courses = session
					.createQuery(
							"select c from Course as c where id= :courseId")
					.setInteger("courseId", courseId).list();
			tx.commit();
			if (courses.size() > 0) {
				c = (Course) courses.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return c;
	}
	*/

	@Override
	public Book getBookByBookId(int bookId) {
		Session session = getSession();
		Transaction tx = null;
		Book b = null;
		List books;
		try {
			tx = session.beginTransaction();
			books = session
					.createQuery("select b from Book as b where id= :bookId")
					.setInteger("bookId", bookId).list();
			tx.commit();
			if (books.size() > 0) {
				b = (Book) books.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return b;
	}

	@Override
	public School getSchoolById(int schoolId) {
		Session session = getSession();
		Transaction tx = null;
		School s = null;
		List schools;
		try {
			tx = session.beginTransaction();
			schools = session
					.createQuery(
							"select s from School as s where id= :schoolId")
					.setInteger("schoolId", schoolId).list();
			tx.commit();
			if (schools.size() > 0) {
				s = (School) schools.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return s;
	}

	@Override
	public User getTeacherByUserHash(String userHash) {
		Session session = getSession();
		Transaction tx = null;
		User u = null;
		List users;
		try {
			tx = session.beginTransaction();
			users = session
					.createQuery(
							"select u from User as u where userhash= :userHash")
					.setString("userHash", userHash).list();
			tx.commit();
			if (users.size() > 0) {
				u = (User) users.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return u;
	}

	@Override
	public User getTeacherById(int id) {
		Session session = getSession();
		Transaction tx = null;
		User u = null;
		List users;
		try {
			tx = session.beginTransaction();
			users = session
					.createQuery("select u from User as u where id= :id")
					.setInteger("id", id).list();
			tx.commit();
			if (users.size() > 0) {
				u = (User) users.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return u;

	}

	@Override
	public Set<Course> getCourseList() {
		Session session = getSession();
		Transaction tx = null;
		Set<Course> result = new LinkedHashSet<Course>();
		try {
			tx = session.beginTransaction();
			List courses = session.createQuery("from Course").list();
			for (Iterator iter = courses.iterator(); iter.hasNext();) {
				Course element = (Course) iter.next();
				result.add(element);
			}
			tx.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return result;

	}

	/*
	 * @Override public Set<ClientCourse> getClientCourses() { Set<ClientCourse>
	 * clientCourses = new LinkedHashSet<ClientCourse>(); Iterator<Course> i =
	 * getCourseList().iterator(); while (i.hasNext()) { clientCourses.add(new
	 * ClientCourse(i.next())); } return clientCourses; }
	 */

	@Override
	public Set<Book> getBooks() {
		Session session = getSession();
		Transaction tx = null;
		Set<Book> result = new LinkedHashSet<Book>();
		try {
			tx = session.beginTransaction();
			List books = session.createQuery("select b from Book as b").list();
			for (Iterator iter = books.iterator(); iter.hasNext();) {
				Book element = (Book) iter.next();
				result.add(element);
			}
			tx.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return result;
	}

	@Override
	public Set<ClientBook> getClientBooks() {
		Set<ClientBook> clientBooks = new LinkedHashSet<ClientBook>();
		Iterator<Book> i = getBooks().iterator();
		while (i.hasNext()) {
			clientBooks.add(new ClientBook(i.next()));
		}
		return clientBooks;
	}

	@Override
	public Set<School> getSchools() {
		Session session = getSession();
		Transaction tx = null;
		Set<School> result = new LinkedHashSet<School>();
		try {
			tx = session.beginTransaction();
			List schools = session.createQuery("select s from School as s")
					.list();
			for (Iterator iter = schools.iterator(); iter.hasNext();) {
				School element = (School) iter.next();
				result.add(element);
			}
			tx.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return result;

	}

	@Override
	public Set<User> getTeachers(int schoolId) {
		Session session = getSession();
		Transaction tx = null;
		Set<User> result = new LinkedHashSet<User>();
		try {
			tx = session.beginTransaction();
			List users = session
					.createQuery(
							"select u from User as u where school_id = :schoolId and role = :role")
					.setInteger("schoolId", schoolId)
					.setInteger("role", User.TEACHER).list();
			for (Iterator iter = users.iterator(); iter.hasNext();) {
				User element = (User) iter.next();
				result.add(element);
			}
			tx.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return result;
	}

	@Override
	public Set<User> getSchoolStudentsByCourseId(int courseId) {
		Session session = getSession();
		Transaction tx = null;
		Set<User> result = new LinkedHashSet<User>();
		try {
			tx = session.beginTransaction();
			List users = session
					.createQuery(
							"select u from User as u, Course as c, School as sc where u.schoolId = sc.id AND c.school.id = sc.id AND c.id= :courseId AND u.role = 2 order by u.userHash")
					.setInteger("courseId", courseId).list();
			for (Iterator iter = users.iterator(); iter.hasNext();) {
				User element = (User) iter.next();
				result.add(element);
			}
			tx.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return result;
	}

	@Override
	public String listElements() {
		Session session = getSession();
		Transaction tx = null;
		StringBuilder result = new StringBuilder();
		try {
			tx = session.beginTransaction();
			List assignments = session
					.createQuery("select c from Chapter as c").list();
			for (Iterator iter = assignments.iterator(); iter.hasNext();) {
				Object element = iter.next();
				result.append(element);
				result.append("\n");
			}
			tx.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return result.toString();
	}
	
	@Override
	public boolean deleteElement(Object element) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(element);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		} finally {
			closeSession(session);
		}
		return true;
	}

	@Override
	public boolean createElement(Object element) {
		Session session = getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(element);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			return false;
		} finally {
			closeSession(session);
		}
		return true;
	}

	@Override
	public void updateElement(Object element) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(element);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
	}

	@Override
	public void saveOrUpdateElement(Object element) {
		Session session = getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(element);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				// throw again the first exception
				throw e;

			}
		} finally {
			closeSession(session);
		}
	}

	public Session getSession() {
		return sessionFactory.openSession();

	}

	public void closeSession(Session session) {
		if (!(session == null) && session.isOpen()) {
			session.clear();
			session.close();
		}

	}

	@Override
	public Course getCourse(int courseId) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> courses = session
					.createQuery(
							"select c from Course as c where id= :courseId")
					.setInteger("courseId", courseId).list();
			tx.commit();
			if (!courses.isEmpty()) {
				return (Course) courses.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return null;
	}

	@Override
	public Set<Course> getCoursesByTeacherId(int teacherId) {
		Set<Course> result = new LinkedHashSet<Course>();
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> courses = session
					.createQuery(
							"select c from Course as c join c.teachers as t where t.id= :teacherId")
					.setInteger("teacherId", teacherId).list();
			tx.commit();
			for (Iterator iter = courses.iterator(); iter.hasNext();) {
				Course element = (Course) iter.next();
				result.add(element);
			}
			return result;
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return result;

	}

	@Override
	public Book getBook(int bookId) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> books = session
					.createQuery("select b from Book as b where id= :bookId")
					.setInteger("bookId", bookId).list();
			tx.commit();
			if (books.size() > 0) {
				return (Book) books.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return null;
	}

	@Override
	public List<AddedContent> getAddedContents(Course course, User user,
			Date lastUpdateTime) {
		List<AddedContent> addedContents = new ArrayList<AddedContent>();
		Session session = getSession();

		String queryString;

		// Teacher
		if (course.isTeacherInCourse(user.getId())) {
			queryString = "select a from AddedContent as a where course_id = :courseId "
					+ "and (user_id IN (:teacherIds) or (user_id IN (:studentIds) and is_public = :isPublic))"
					+ " and timestamp > :lastUpdateTime order by timestamp";
		} else { // Student
			queryString = "select a from AddedContent as a where course_id = :courseId "
					+ "and ((user_id IN (:teacherIds) and is_public = :isPublic and available < :currentTime "
					+ "and available > :lastUpdateTimeFirst) or (user_id = :userId and timestamp > :lastUpdateTimeSecond)) "
					+ "order by timestamp";
		}

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setInteger("courseId", course.getId());
			query.setParameterList("teacherIds",
					course.getTeachersInCourseIds());
			query.setBoolean("isPublic", true);
			// Teacher query
			if (course.isTeacherInCourse(user.getId())) {
				query.setParameterList("studentIds",
						course.getStudentsInCourseIds());
				query.setTimestamp("lastUpdateTime", lastUpdateTime);
			} else { // Student query
				query.setTimestamp("currentTime", new Date());
				query.setInteger("userId", user.getId());
				query.setTimestamp("lastUpdateTimeFirst", lastUpdateTime);
				query.setTimestamp("lastUpdateTimeSecond", lastUpdateTime);
			}

			List<?> list = query.list();
			tx.commit();
			for (Object o : list) {
				addedContents.add((AddedContent) o);
			}
			return addedContents;
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return addedContents;
	}

	@Override
	public List<AssignmentSolution> getSolutions(Course course, User user,
			Date lastUpdateTime) {
		List<AssignmentSolution> solutions = new ArrayList<AssignmentSolution>();

		String queryString;

		// Teacher
		if (course.isTeacherInCourse(user.getId())
				|| user.getRole() == User.ADMIN) {
			queryString = "select a from AssignmentSolution as a where course_id = :courseId "
					+ "and is_public = :isPublic and user_id IN (:allUserIds)"
					+ " and timestamp > :lastUpdateTime order by timestamp";
		} else { // Student
			queryString = "select a from AssignmentSolution as a where course_id = :courseId "
					+ "and user_id = :userId and timestamp > :lastUpdateTime order by timestamp";
		}

		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setInteger("courseId", course.getId());
			query.setTimestamp("lastUpdateTime", lastUpdateTime);
			if (course.isTeacherInCourse(user.getId())
					|| user.getRole() == User.ADMIN) {
				query.setParameterList("allUserIds",
						course.getAllUsersInCourseIds());
				query.setBoolean("isPublic", true);
			} else { // Student
				query.setInteger("userId", user.getId());
			}
			List<?> list = query.list();
			tx.commit();
			for (Object o : list) {
				solutions.add((AssignmentSolution) o);
			}
			return solutions;
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return solutions;
	}

	@Override
	public int requestLock(Course course, User user) {
		if (course.isTeacherInCourse(user.getId())) {
			// Delete too old course locks
			deleteObsoleteCourseLocks(course.getId());
			CourseLock lock = new CourseLock();
			lock.setCourseId(course.getId());
			if (createElement(lock)) {
				return lock.getId();
			}
		}
		return -1;
	}

	private void deleteObsoleteCourseLocks(int courseId) {
		CourseLock lock = getCourseLock(courseId);
		if (lock != null) {
			Date currentTime = new Date();
			Date lockTime = lock.getTimestamp();
			// If lock older than 2 minutes, delete it
			if (currentTime.getTime() - lockTime.getTime() > 120000) {
				deleteElement(lock);
			}
		}

	}

	@Override
	public int removeLock(String lockId, Course course, User user) {
		if (course.isTeacherInCourse(user.getId())) {
			if (lockId.equals("all")) {
				CourseLock lock = getCourseLock(course.getId());
				// No locks
				if (lock == null) {
					return 1;
				}
				if (deleteElement(lock)) {
					return 1;
				}
			} else {
				CourseLock lock = new CourseLock();
				lock.setId(Integer.parseInt(lockId));
				lock.setCourseId(course.getId());
				if (deleteElement(lock)) {
					return 1;
				}
			}
		}
		return -1;
	}

	@Override
	public int getUserId(String userHash) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> users = session
					.createQuery(
							"select id from User where userhash = :userHash")
					.setString("userHash", userHash).list();
			tx.commit();
			if (!users.isEmpty()) {
				return (Integer) users.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return -1;
	}

	@Override
	public String getNewestSolution(int courseId, int userId) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> timeStamps = session
					.createQuery(
							"select timestamp from AssignmentSolution where user_id = :userId and course_id= :courseId order by timestamp desc")
					.setInteger("userId", userId)
					.setInteger("courseId", courseId).list();
			tx.commit();
			if (!timeStamps.isEmpty()) {
				return timeStamps.iterator().next().toString().substring(0, 10)
						+ ":" + timeStamps.size();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return "-1:0";

	}

	@Override
	public String getNewestCommentInfo(int courseId) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> timeStamps = session
					.createQuery(
							"select timestamp from Comment where course_id= :courseId order by timestamp desc")
					.setInteger("courseId", courseId).list();
			tx.commit();
			if (!timeStamps.isEmpty()) {
				return timeStamps.iterator().next().toString().substring(0, 10)
						+ ":" + timeStamps.size();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return "-1:0";
	}

	@Override
	public String getNewestAddedContentInfo(int courseId) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> timeStamps = session
					.createQuery(
							"select timestamp from AddedContent where course_id= :courseId order by timestamp desc")
					.setInteger("courseId", courseId).list();
			tx.commit();
			if (!timeStamps.isEmpty()) {
				return timeStamps.iterator().next().toString().substring(0, 10)
						+ ":" + timeStamps.size();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return "-1:0";
	}

	public User getUser(int userId) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> users = session
					.createQuery("select u from User as u where id = :userId")
					.setInteger("userId", userId).list();
			tx.commit();
			if (!users.isEmpty()) {
				return (User) users.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return null;
	}

	@Override
	public User getUserByUserHash(String userHash) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> users = session
					.createQuery(
							"select u from User as u where userhash = :userHash")
					.setString("userHash", userHash).list();
			tx.commit();
			if (!users.isEmpty()) {
				return (User) users.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return null;
	}

	private CourseLock getCourseLock(int courseId) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> courseLocks = session
					.createQuery(
							"select c from CourseLock as c where course_id= :courseId")
					.setInteger("courseId", courseId).list();
			tx.commit();
			if (!courseLocks.isEmpty()) {
				return (CourseLock) courseLocks.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return null;
	}

	@Override
	public List<Comment> getComments(Course course, User user,
			Date lastUpdateTime) {

		List<Comment> comments = new ArrayList<Comment>();
		Session session = getSession();

		String queryString;
		// Teacher
		if (course.isTeacherInCourse(user.getId())) {
			queryString = "select c from Comment as c where course_id = :courseId "
					+ "and teacher_id = :userId "
					+ "and timestamp > :lastUpdateTime order by timestamp";
		} else { // Student
			queryString = "select c from Comment as c where course_id = :courseId "
					+ "and student_id = :userId "
					+ "and timestamp > :lastUpdateTime order by timestamp";
		}

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setInteger("courseId", course.getId());
			query.setInteger("userId", user.getId());
			query.setTimestamp("lastUpdateTime", lastUpdateTime);

			List<?> list = query.list();
			tx.commit();
			for (Object o : list) {
				comments.add((Comment) o);
			}
			return comments;
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return comments;
	}

	@Override
	public List<SystemUpdate> getSystemUpdates(User user, Date lastUpdateTime) {

		List<SystemUpdate> updates = new ArrayList<SystemUpdate>();
		Session session = getSession();

		int updateType = SystemUpdate.STUDENT;
		if (user.getRole() == User.TEACHER) {
			updateType = SystemUpdate.TEACHER;
		}

		String queryString;

		if (user.getRole() == User.ADMIN) {
			queryString = "select s from SystemUpdate as s where "
					+ "timestamp > :lastUpdateTime order by timestamp";
		} else {
			queryString = "select s from SystemUpdate as s where "
					+ "(type = :updateType or type = :updateAll) "
					+ "and timestamp > :lastUpdateTime order by timestamp";
		}

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			if (user.getRole() != User.ADMIN) {
				query.setInteger("updateType", updateType);
				query.setInteger("updateAll", SystemUpdate.ALL);
			}
			query.setTimestamp("lastUpdateTime", lastUpdateTime);

			List<?> list = query.list();
			tx.commit();
			for (Object o : list) {
				updates.add((SystemUpdate) o);
			}
			return updates;
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return updates;
	}

	@Override
	public String getNextStudentNumber(int schoolId, int gender) {
		Session session = getSession();
		String queryString = "SELECT u from User as u WHERE schoolId = :schoolId AND gender = :gender AND role=2";
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setInteger("schoolId", schoolId);
			query.setInteger("gender", gender);
			List<?> list = query.list();
			if (list == null || list.size() == 0) {
				return "01";
			} else {
				if (list.size() < 9) {
					return "0" + (list.size() + 1);
				} else {
					return "" + (list.size() + 1);
				}

			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return "01";
	}

	@Override
	public String getNextTeacherNumber(int schoolId) {
		Session session = getSession();
		String queryString = "SELECT u from User as u WHERE schoolId = :schoolId AND role=1";
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setInteger("schoolId", schoolId);
			List<?> list = query.list();
			if (list == null || list.size() == 0) {
				return "01";
			} else {
				if (list.size() < 9) {
					return "0" + (list.size() + 1);
				} else {
					return "" + (list.size() + 1);
				}

			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return "01";

	}

	@Override
	public int getNextSchoolNumber(String country) {
		Session session = getSession();
		String queryString = "SELECT s from School as s WHERE country = :country";
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setString("country", country);
			List<?> list = query.list();
			if (list == null) {
				return 1;
			} else {
				return list.size() + 1;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return 1;
	}

	@Override
	public boolean isUserInCourse(User user, int courseId) {
		Course course = getCourse(courseId);
		return course.isTeacherInCourse(user.getId())
				|| course.isStudentInCourse(user.getId());
	}

	@Override
	public List<BookUpdate> getBookUpdates(Course course, User user,
			Date lastUpdateTime, String lang) {
		List<BookUpdate> updates = new ArrayList<BookUpdate>();
		Session session = getSession();

		int updateType = BookUpdate.STUDENT;
		if (user.getRole() == User.TEACHER) {
			updateType = BookUpdate.TEACHER;
		}
		String queryString;
		if(lang == null){
			queryString = "select b from BookUpdate as b where "
					+ "book_id IN (:bookIds) and (type = :updateType or type = :updateAll) "
					+ "and timestamp > :lastUpdateTime and lang IS NULL order by timestamp";
		} else if(lang.toLowerCase().equals("all")){
			queryString = "select b from BookUpdate as b where "
					+ "book_id IN (:bookIds) and (type = :updateType or type = :updateAll) "
					+ "and timestamp > :lastUpdateTime order by timestamp";
		} else{
			queryString = "select b from BookUpdate as b where "
				+ "book_id IN (:bookIds) and (type = :updateType or type = :updateAll) "
				+ "and timestamp > :lastUpdateTime and lang = :lang order by timestamp";
		}
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setParameterList("bookIds", course.getBookIds());
			query.setInteger("updateType", updateType);
			query.setInteger("updateAll", BookUpdate.ALL);
			query.setTimestamp("lastUpdateTime", lastUpdateTime);
			if(lang != null){
				if(!lang.toLowerCase().equals("all")){
					query.setString("lang", lang);
				}
			}

			List<?> list = query.list();
			tx.commit();
			for (Object o : list) {
				updates.add((BookUpdate) o);
			}
			return updates;
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return updates;
	}

	@Override
	public void addLogEntry(int type, int userId, int courseId, String data,
			String ip, String extraParams) {
		LogEntry entry = new LogEntry();
		entry.setData(data);
		entry.setType(type);
		entry.setUserId(userId);
		entry.setCourseId(courseId);
		entry.setClientIp(ip);
		entry.setExtraParams(extraParams);
		createElement(entry);
	}

	@Override
	public void addTrafficLogEntry(int userId, String ip, String extraParams) {
		TrafficLogEntry entry = new TrafficLogEntry();
		entry.setUserId(userId);
		entry.setClientIp(ip);
		entry.setExtraParams(extraParams);
		createElement(entry);
	}

	@Override
	public Exam getExamById(int id) {
		Session session = getSession();
		Transaction tx = null;
		Exam e = null;
		List exams;
		try {
			tx = session.beginTransaction();
			exams = session
					.createQuery("select e from Exam as e where id= :id")
					.setInteger("id", id).list();
			tx.commit();
			if (exams.size() > 0) {
				e = (Exam) exams.iterator().next();
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw ex;
			}
		}
		closeSession(session);
		return e;
	}

	@Override
	public void addExamAccess(int userId, int examId, Date validUntil) {
		ExamAccess access = new ExamAccess();
		access.setUserId(userId);
		access.setExamId(examId);
		access.setValidUntil(validUntil);
		createElement(access);
	}

	@Override
	public ExamAccess getExamAccess(int userId, int examId) {
		Session session = getSession();
		Transaction tx = null;
		ExamAccess e = null;
		List accesses;
		try {
			tx = session.beginTransaction();
			accesses = session
					.createQuery("select e from ExamAccess as e where user_id= :userId and exam_id= :examId")
					.setInteger("userId", userId).setInteger("examId", examId).list();
			tx.commit();
			if (accesses.size() > 0) {
				e = (ExamAccess) accesses.iterator().next();
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw ex;
			}
		}
		closeSession(session);
		return e;
		
	}

	@Override
	public List<DummySystemUpdate> getDummySystemUpdates(
			String bookVersion, int updateType, Date lastUpdateTime) {
		List<DummySystemUpdate> updates = new ArrayList<DummySystemUpdate>();
		Session session = getSession();

		String queryString = "select s from DummySystemUpdate as s where "
				+ "(type = :updateType or type = :updateAll) "
				+ "and timestamp > :lastUpdateTime and bookVersion= :bookVersion order by timestamp";
	

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setInteger("updateType", updateType);
			query.setInteger("updateAll", SystemUpdate.ALL);			
			query.setTimestamp("lastUpdateTime", lastUpdateTime);
			query.setString("bookVersion", bookVersion);

			List<?> list = query.list();
			tx.commit();
			for (Object o : list) {
				updates.add((DummySystemUpdate) o);
			}
			return updates;
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		} finally {
			closeSession(session);
		}
		return updates;
	}

	@Override
	public SystemUpdate getSystemUpdateByTitle(String title) {
		Session session = getSession();
		Transaction tx = null;
		SystemUpdate s = null;
		List systemUpdates;
		try {
			tx = session.beginTransaction();
			systemUpdates = session
					.createQuery("select s from SystemUpdate as s where title= :title")
					.setString("title", title).list();
			tx.commit();
			if (systemUpdates.size() == 1) {
				s = (SystemUpdate) systemUpdates.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return s;
	}

	@Override
	public BookUpdate getBookUpdateByTitle(String title, String lang) {
		Session session = getSession();
		Transaction tx = null;
		BookUpdate s = null;
		List bookUpdates;
		try {
			tx = session.beginTransaction();
			if(lang != null){
				bookUpdates = session
					.createQuery("select s from BookUpdate as s where title= :title AND lang= :lang")
					.setString("title", title)
					.setString("lang", lang).list();
			} else{
				bookUpdates = session
						.createQuery("select s from BookUpdate as s where title= :title AND lang IS NULL")
						.setString("title", title).list();
			}
			tx.commit();
			if (bookUpdates.size() == 1) {
				s = (BookUpdate) bookUpdates.iterator().next();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				// throw again the first exception
				throw e;
			}
		}
		closeSession(session);
		return s;
	}
	
	
}
