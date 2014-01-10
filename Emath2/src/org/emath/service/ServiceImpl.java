package org.emath.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import org.emath.model.book.AddedContent;
import org.emath.model.book.AddedContent.ContentType;
import org.emath.model.book.AssignmentSolution;
import org.emath.model.book.Book;
import org.emath.model.book.BookUpdate;
import org.emath.model.book.Comment;
import org.emath.model.communication.Backup;
import org.emath.model.communication.ClientBook;
import org.emath.model.communication.Course;
import org.emath.model.communication.DummySystemUpdate;
import org.emath.model.communication.Exam;
import org.emath.model.communication.ExamAccess;
import org.emath.model.communication.ExamSolution;
import org.emath.model.communication.History;
import org.emath.model.communication.LogEntry;
import org.emath.model.communication.School;
import org.emath.model.communication.SystemUpdate;
import org.emath.model.communication.User;
import org.emath.persistence.hibernate.DatabaseFacade;
import org.emath.persistence.hibernate.HibernateImpl;

import com.google.gson.Gson;


/**
 * The implementation of the e-math service layer contract.
 *
 */
public class ServiceImpl implements Service{
	
	private static DatabaseFacade db;
	private static Gson gson = new Gson();
	
	public ServiceImpl(){
		ServiceImpl.db = new HibernateImpl();
	}
	
	public ServiceImpl(DatabaseFacade db){
		ServiceImpl.db = db;
	}
	
	
	@Override
	public User isValidUser(String userName, String password, String courseString,
			String clientIp, String userAgent) {
		
		// Check that user with userName and corresponding password exists
		User user = checkPassword(userName, password);

		if (user == null) {
			db.addLogEntry(LogEntry.USER_VALIDATION_FAILED, -1, -1, "",
					clientIp, "username: " + userName + "userkey: " + password
							+ " courseid: " + courseString + " user-agent: " + userAgent);
			return null;
		}
		//if user has been disabled
		if(user.getDisabled() == 1){
			db.addLogEntry(LogEntry.USER_VALIDATION_FAILED, -1, -1, "",
					clientIp, "username: " + userName + "userkey: " + password
							+ " courseid: " + courseString + " user-agent: " + userAgent);
			return null;
		}
		return user;
	}
			
	@Override
	public Course isUserInCourse(User user, String courseString,
			String clientIp, String userAgent){

		// If the course id cannot be parsed
		int courseId = parseCourseId(courseString);
		if (courseId == -1) {
			db.addLogEntry(LogEntry.COURSE_VALIDATION_FAILED, user.getId(), -1,
					"", clientIp, "username: " + user.getUserHash() + "userkey: "
							+ user.getPassword() + "courseid: " + courseString + " user-agent: " + userAgent);
			return null;
		}

		Course course = db.getCourse(courseId);
		//if the course with the parsed id doesn't exist
		if (course == null) {
			db.addLogEntry(LogEntry.COURSE_VALIDATION_FAILED, user.getId(), -1,
					"", clientIp, "username: " + user.getUserHash() + "userkey: "
							+ user.getPassword() + "courseid: " + courseString + " user-agent: " + userAgent);
			return null;
		}

		String courseChar = courseString.substring(0, 1).toLowerCase();

		// If the school id in course is 1, the character at the beginning of
		// course id can be anything.
		// For other schools the character must be 'C' or 'c'
		if (course.getSchool().getId() != 1 && !courseChar.equals("c")) {
			db.addLogEntry(LogEntry.COURSE_VALIDATION_FAILED, user.getId(),
					course.getId(), "", clientIp, "username: " + user.getUserHash()
					+ "userkey: " + user.getPassword() + "courseid: "
					+ courseString + " user-agent: " + userAgent);
			return null;
		}

		// Check that the user sent as a request parameter is in the course
		if (!course.isUserInCourse(user.getId())) {
			db.addLogEntry(LogEntry.COURSE_VALIDATION_FAILED, user.getId(),
					course.getId(), "", clientIp, "username: " + user.getUserHash()
					+ "userkey: " + user.getPassword() + "courseid: "
					+ courseString + " user-agent: " + userAgent);
			return null;
		}
		return course;
	}
	
	@Override
	public int saveBookUpdate(User user, String data, String bookId, String updateType, 
			String clientIp, String lang){
		int returnId = -1;

		if (user.getRole() == User.ADMIN) {

			BookUpdate bookUpdate = new BookUpdate();
			bookUpdate.setData(data);
			bookUpdate.setUserId(user.getId());
			bookUpdate.setBookId(Integer.parseInt(bookId));
			bookUpdate.setType(Integer.parseInt(updateType));
			bookUpdate.setClientIp(clientIp);
			bookUpdate.setLang(lang);

			//String id = request.getParameter("checknro");
			
			
			String newTitle = parseTiddlerTitle(data);
			bookUpdate.setTitle(newTitle);
			BookUpdate old = db.getBookUpdateByTitle(newTitle, bookUpdate.getLang());
			if(old != null){
				bookUpdate.setId(old.getId());
			}
			
			db.saveOrUpdateElement(bookUpdate);
			returnId = bookUpdate.getId();
			
			/*
			if (id != null) {
				bookUpdate.setId(Integer.parseInt(id));
				db.saveOrUpdateElement(bookUpdate);
			} else {
				db.createElement(bookUpdate);
				String updatedData = addDBIdToTiddlerData(bookUpdate.getData(),
						bookUpdate.getId());
				bookUpdate.setData(updatedData);
				db.saveOrUpdateElement(bookUpdate);
			}

			returnId = bookUpdate.getId();
			*/

		}
		return returnId;
		

	}
	
	@Override
	public int addCourse(int schoolId, String teachersText, String booksText) {
		Course course = new Course();

		//int schoolId = Integer.parseInt(request.getParameter("schoolid"));
		course.setSchool(db.getSchoolById(schoolId));
		if(course.getSchool() == null){
			return -1;
		}

		//String teachersText = request.getParameter("teachers");
		StringTokenizer st = new StringTokenizer(teachersText, ",");
		if(st.countTokens() == 0){
			return -1;
		}
		while (st.hasMoreTokens()) {
			try{
			course.addTeacher(db.getTeacherById(Integer.parseInt(st.nextToken())));
			}catch(Exception e){
				return -1;
			}
		}

		//String booksText = request.getParameter("books");
		st = new StringTokenizer(booksText, ",");
		if(st.countTokens() == 0){
			return -1;
		}
		while (st.hasMoreTokens()) {
			try{
			course.addBook(new ClientBook(db.getBook(Integer.parseInt(st
					.nextToken()))));
			}catch(Exception e){
				return -1;
			}
		}

		db.createElement(course);
		
		return course.getId();
		

	}
	
	@Override
	public User addTeacher(String name, String email, int schoolId) {
		//String name = request.getParameter("name");
		//String email = request.getParameter("email");
		//int schoolId = Integer.parseInt(request.getParameter("schoolid"));
		User teacher = UserGenerator.createTeacher(name, email, schoolId);
		/*
		 * there will be only 1 teacher but client side is expecting to get an
		 * array of teachers
		 */
		// Set<User> teachers = new LinkedHashSet<User>();
		// teachers.add(teacher);
		return teacher;
		
		
		

	}
	
	@Override
	public String getNewestEntry(int userIdToCheck, int courseId) {
		//int userIdToCheck = Integer.parseInt(request
		//		.getParameter("useridtocheck"));
		//int courseId = Integer.parseInt(request.getParameter("courseid"));

		String solutionText = db.getNewestSolution(courseId, userIdToCheck);
		String[] solutionParts = solutionText.split(":");
		
		return "{\"solutiontime\": \"" + solutionParts[0]
				+ "\", \"userid\": \"" + userIdToCheck
				+ "\", \"solutionsamount\": \"" + solutionParts[1] + "\"}";
		

	}
	
	@Override
	public String getAddedContentInfo(int courseId) {
		//int courseId = Integer.parseInt(request.getParameter("courseid"));

		String contentText = db.getNewestAddedContentInfo(courseId);
		String[] contentParts = contentText.split(":");
		return "{\"contenttime\": \"" + contentParts[0]
				+ "\", \"contentsamount\": \"" + contentParts[1] + "\"}";
		

	}
	
	@Override
	public String getCommentsInfo(int courseId) {
		//int courseId = Integer.parseInt(request.getParameter("courseid"));
		String commentText = db.getNewestCommentInfo(courseId);
		String[] commentParts = commentText.split(":");
		return "{\"commenttime\": \"" + commentParts[0]
				+ "\", \"commentsamount\": \"" + commentParts[1] + "\"}";
		

	}
	
	@Override
	public Book addBook(String title) {
		//String title = request.getParameter("title");
		if(title == null || title.equals("")){
			return null;
		}
		Book b = new Book();
		b.setTitle(title);
		db.createElement(b);
		return b;
		
		

	}
	
	@Override
	public School addSchool(String country, String name) {
		//String country = request.getParameter("country");
		//String name = request.getParameter("name");
		if(country == null || country.equals("") || name == null || name.equals("")){
			return null;
		}
		int schoolNumber = db.getNextSchoolNumber(country);

		School school = new School();
		school.setCountry(country);
		school.setName(name);
		school.setSchool(schoolNumber);
		db.createElement(school);
		return school;

	}
	
	@Override
	public int saveSystemUpdate(User user, String data, String updateType, String clientIp) {

		int returnId = -1;

		if (user.getRole() == User.ADMIN) {

			//String data = request.getParameter("data");
			//int updateType = Integer.parseInt(request
			//		.getParameter("updatetype"));
			
			SystemUpdate systemUpdate = new SystemUpdate(data, Integer.parseInt(updateType));
			systemUpdate.setUserId(user.getId());
			systemUpdate.setClientIp(clientIp);
			
			
			String newTitle = parseTiddlerTitle(data);
			systemUpdate.setTitle(newTitle);
			SystemUpdate old = db.getSystemUpdateByTitle(newTitle);
			if(old != null){
				systemUpdate.setId(old.getId());
			}
			
			db.saveOrUpdateElement(systemUpdate);
			
			
			//String id = request.getParameter("checknro");

			/*if (id != null) {
				systemUpdate.setId(Integer.parseInt(id));
				db.saveOrUpdateElement(systemUpdate);
			} else {
				db.createElement(systemUpdate);
				String updatedData = addDBIdToTiddlerData(
						systemUpdate.getData(), systemUpdate.getId());
				systemUpdate.setData(updatedData);
				db.saveOrUpdateElement(systemUpdate);
			}*/

			returnId = systemUpdate.getId();

		}
		return returnId;

	}
	
	@Override
	public List<SystemUpdate> getSystemUpdates(User user, String lastUpdateTime, String requestTime) {

		//String lastUpdateTime = request.getParameter("lastcheck");

		Date date = new Date(0);
		if (!lastUpdateTime.equals("0")) {
			date = this.convertStringToDate(lastUpdateTime);
		}

		List<SystemUpdate> updates = db.getSystemUpdates(user, date);
		
		return updates;
		
		
	}
	
	@Override
	public int saveSolution(User user, Course course, String data, String clientIp, int isPublic,
			String id) {
		
		//String solutionData = request.getParameter("data");
		String solutionData = replaceAllAakkoset(data);
		
		/* errorCode is set if error occurs. If errorCode == 0, then no error */
		int errorCode = 0;
		if (checkDataForIllegalOperations(solutionData, user, course, clientIp) ) {
			errorCode = -1;	
		}
		
		String[] titleParts = parseTiddlerTitle(solutionData).split("_");
		if(titleParts.length > 2){
			if(!titleParts[0].trim().toLowerCase().equals("c" + course.getId()) ||
				!titleParts[1].trim().toLowerCase().equals(user.getUserHash().toLowerCase())){
				errorCode = -2;
			}
		} else{
			errorCode = -2;
		}
		
		if(!dataHasOneTitleAttribute(solutionData)){
			errorCode = -3;
		}
		
		if(errorCode != 0){
			db.addLogEntry(LogEntry.ILLEGAL_DATA_SUBMITTED, user.getId(),
					course.getId(), solutionData, clientIp, "");
			return errorCode;
		}

		//String isPublic = request.getParameter("ispublic");

		String solutionTo = Util.parseSolutionTo(solutionData);

		AssignmentSolution solution = new AssignmentSolution(course.getId(),
				user.getId(), solutionData);
		solution.setClientIp(clientIp);
		solution.setSolutionTo(solutionTo);
		if (isPublic == 1) {
			solution.setIsPublic(true);
		}

		if (id != null) {
			solution.setId(Integer.parseInt(id));
			db.saveOrUpdateElement(solution);
		} else {
			db.createElement(solution);
			String updatedData = addDBIdToTiddlerData(solution.getData(),
					solution.getId());
			solution.setData(updatedData);
			db.saveOrUpdateElement(solution);
		}

		// Create solution backup
		try {
			Backup backup = new Backup(Backup.SOLUTION, solution.getId(),
					solution.getData(), solution.getUserId(),
					solution.getCourseId(), solution.getClientIp());
			db.createElement(backup);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return solution.getId();

	}
	
	@Override
	public int saveContent(User user, Course course, String clientIp, int isPublic, 
			String available, String id, String data) {
		
		//String data = request.getParameter("data");
		data = replaceAllAakkoset(data);

		if (checkDataForIllegalOperations(data, user, course, clientIp) || 
				(user.getRole() != User.TEACHER && user.getRole() != User.ADMIN)) {
			db.addLogEntry(LogEntry.ILLEGAL_DATA_SUBMITTED, user.getId(),
					course.getId(), data, clientIp, "");
			return -1;
		}

		ContentType cType = Util.parseContentType(data);

		String title = parseTiddlerTitle(data);

		

		//String available = request.getParameter("available");

		AddedContent content = new AddedContent();
		content.setCourseId(course.getId());
		content.setTitle(title);
		content.setData(data);
		content.setType(cType.getIntValue());
		content.setClientIp(clientIp);
		if (isPublic == 1) {
			content.setIsPublic(true);
		}
		Date availableDate = new Date();
		if (available != null) {
			availableDate = convertStringToDate(available);
		}

		content.setAvailable(availableDate);

		content.setUserId(user.getId());

		//String id = request.getParameter("checknro");
		if (id != null) {
			content.setId(Integer.parseInt(id));
			db.saveOrUpdateElement(content);
		} else {
			db.createElement(content);
			String updatedData = addDBIdToTiddlerData(content.getData(),
					content.getId());
			content.setData(updatedData);
			db.saveOrUpdateElement(content);
		}

		try {
			Backup backup = new Backup(Backup.TEACHER_CONTENT, content.getId(),
					content.getData(), content.getUserId(),
					content.getCourseId(), content.getClientIp());
			db.createElement(backup);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.getId();
	
	}
	
	@Override
	public int saveComment(User user, Course course, String data, String clientIp,
			String userHash, String id) {

		//String data = request.getParameter("data");
		data = replaceAllAakkoset(data);

		if (checkDataForIllegalOperations(data, user, course, clientIp)) {
			return -1;
		}

		String title = parseTiddlerTitle(data);

		//String studentName = request.getParameter("student");
		int studentId = db.getUserId(userHash);
		if(studentId == -1){
			return -1;
		}

		Comment comment = new Comment(title, data, course.getId(),
				user.getId(), studentId);
		comment.setClientIp(clientIp);

		//String id = request.getParameter("checknro");
		if (id != null) {
			comment.setId(Integer.parseInt(id));
			db.saveOrUpdateElement(comment);
		} else {
			db.createElement(comment);
			String updatedData = addDBIdToTiddlerData(comment.getData(),
					comment.getId());
			comment.setData(updatedData);
			db.saveOrUpdateElement(comment);
		}

		try {
			Backup backup = new Backup(Backup.COMMENT, comment.getId(),
					comment.getData(), comment.getTeacherId(),
					comment.getCourseId(), comment.getClientIp());
			db.createElement(backup);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comment.getId();

	}
	
	@Override
	public List<Comment> getComments(User user, Course course, String lastUpdateTime, String requestTime) {

		//String lastUpdateTime = request.getParameter("lastcheck");

		Date date = new Date(0);
		if (!lastUpdateTime.equals("0")) {
			date = this.convertStringToDate(lastUpdateTime);
		}

		List<Comment> comments = db.getComments(course, user, date);
		return comments;
		
	}
	
	@Override
	public Set<ClientBook> getBookList() {
		Set<ClientBook> books = db.getClientBooks();
		return books;
		
	}
	
	@Override
	public Set<School> getSchoolList() {
		Set<School> schools = db.getSchools();
		return schools;
	}
	
	@Override
	public Set<User> getStudentList(int courseId) {
		//int schoolId = Integer.parseInt(request.getParameter("schoolid"));
		Set<User> students = db.getSchoolStudentsByCourseId(courseId);
		return students;
		
	}
	
	@Override
	public Set<Course> getCoursesByTeacherId(int teacherId) {

		//int teacherId = Integer.parseInt(request.getParameter("teacherid"));
		Set<Course> courses = db.getCoursesByTeacherId(teacherId);
		return courses;
	
	}
	
	@Override
	public Set<User> getTeachers(int schoolId) {
		//PrintWriter out = response.getWriter();
		//int schoolId = Integer.parseInt(request.getParameter("schoolid"));
		Set<User> teachers = db.getTeachers(schoolId);
		
		return teachers;
		
		
		
	}
	
	@Override
	public String getUpdates(User user, Course course, String lastUpdateTime, String requestTime) {


		if (lastUpdateTime.equals("0")) {
			lastUpdateTime = "2000-01-01 00:00:00";
		}

		Date date = this.convertStringToDate(lastUpdateTime);

		List<AddedContent> addedContents = db.getAddedContents(course, user,
				date);

		List<AssignmentSolution> solutions = db
				.getSolutions(course, user, date);

		List<Comment> comments = db.getComments(course, user, date);
		
		List<BookUpdate> bookUpdates;
		
		if(course.getBookLanguages() == null){
			bookUpdates = db.getBookUpdates(course, user, date, "all");
		} else{
			/* non language specific updates */
			bookUpdates = db.getBookUpdates(course, user, date, null);
			String[] langs = course.getBookLanguages().split(",");
			/* language specific updates */
			for(int i = 0; i < langs.length; ++i){
				bookUpdates.addAll(db.getBookUpdates(course, user, date, langs[i]));
			}
		}
			
		String data = allCourseUpdatesToString(addedContents, solutions,
				comments, bookUpdates, requestTime);
		return data;
		
	}
	
	
	@Override
	public List<AssignmentSolution> getSolutions(User user, Course course, String lastUpdateTime, String requestTime) {

		//String lastUpdateTime = request.getParameter("lastcheck");

		Date date = new Date(0);
		if (!lastUpdateTime.equals("0")) {
			date = this.convertStringToDate(lastUpdateTime);
		}

		List<AssignmentSolution> solutions = db
				.getSolutions(course, user, date);
		return solutions;
		
	}
	
	@Override
	public int requestLock(User user, Course course) {

		int lockId = db.requestLock(course, user);
		return lockId;
		
	}
	
	@Override
	public int removeLock(User user, Course course, String lockId) {
		//String lockId = request.getParameter("lockid");

		int success = db.removeLock(lockId, course, user);

		return success;
		
	}
	
	@Override
	public Course getCourse(int courseId){
		return db.getCourse(courseId);
	}
	
	private User checkPassword(String userHash, String password) {
		User u = db.getUserByUserHash(userHash);
		if (u != null && password.equals(u.getPassword())) {
			return u;
		}
		return null;
	}
	
	@Override
	public Set<User> generateUsers(int boysSum, int girlsSum, String courseString) {
		
		//PrintWriter out = response.getWriter();
		
		int courseId = Integer.parseInt(courseString.substring(1));
		Set<User> students = new HashSet<User>();
		for (int i = 0; i < boysSum; ++i) {
			students.add(UserGenerator.createStudent(courseId, User.BOY));

		}
		for (int i = 0; i < girlsSum; ++i) {
			students.add(UserGenerator.createStudent(courseId, User.GIRL));
		}
		return students;

	}
	
	
	@Override
	public Set<User> getCourseStudents(String courseString) {
		int courseId = Integer.parseInt(courseString.substring(1));
		//request.getParameter("courseid");
		//		.substring(1)).intValue();
		Set<User> courseStudents = db.getCourse(courseId)
				.getStudents();
		return courseStudents;
		
	}
	
	@Override
	public int saveCourse(Course clientCourse) {
		
		boolean save = true;
		//String json = request.getParameter("course");
		
		Course serverCourse = db.getCourse(clientCourse.getId());
		clientCourse.setTeachers(serverCourse.getTeachers());
		clientCourse.setBooks(serverCourse.getBooks());
		clientCourse.setSchool(serverCourse.getSchool());

		Iterator<User> scIterator = serverCourse.getStudents().iterator();
		while (scIterator.hasNext()) {
			User s = scIterator.next();
			if (!clientCourse.isStudentInCourse(s.getId())) {
				save = false;
			}
		}
		if (save) {
			db.saveOrUpdateElement(clientCourse);
			try {
				// In E-Math project for each student we created also accounts
				// in another system. That was done here.
				//URL url = new URL("url to a place");
				//HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				//if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//	conn.getInputStream();
				//} else {
				//	conn.getErrorStream();
				//}
			} catch (Exception e) {

			}
			return 1;
		} else {
			return 0;
		}
		
	}
	
	public void addTrafficLogEntry(int userId, String remoteAddr, String requestInfo){
		db.addTrafficLogEntry(userId, remoteAddr, requestInfo);
	}
	
	/* OLD VERSION! ONLY TO BE USED BY OLD EBOOKS! */
	@Override
	public String generateUsersLegacy(int boysSum, int girlsSum, int courseId){
		
		Set<User> boys = new HashSet<User>();
		Set<User> girls = new HashSet<User>();
		for (int i = 0; i < boysSum; ++i) {
			boys.add(UserGenerator.createStudent(courseId, User.BOY));

		}
		for (int i = 0; i < girlsSum; ++i) {
			girls.add(UserGenerator.createStudent(courseId, User.GIRL));
		}
		
		return "{\"boys\":" + gson.toJson(boys) + ",\"girls\":"
				+ gson.toJson(girls) + "}";
	}
	
	/* OLD VERSION! ONLY TO BE USED BY OLD EBOOKS! */	
	@Override
	public String getCourseStudentsLegacy(int courseId){
		Set<User> courseStudents = db.getCourse(courseId)
				.getStudents();
		return gson.toJson(courseStudents);
	}
	
	/**
	 * Try to parse course id from the request parameter.
	 * 
	 * @param courseIdString
	 * @return course id number or -1 if the id can't be parsed
	 */
	private int parseCourseId(String courseIdString) {
		if (courseIdString == null || courseIdString.length() < 2) {
			return -1;
		}

		int courseId = -1;

		try {
			// Remove character from the beginning of the course
			// parameter
			courseId = Integer.parseInt(courseIdString.substring(1));
		} catch (NumberFormatException ne) {

		}

		return courseId;
	}
	
	private String addDBIdToTiddlerData(String tiddlerData, int dbId) {
		String begin = tiddlerData.substring(0, tiddlerData.indexOf("title="));
		String middle = "checknro=\"" + dbId + "\" ";
		String end = tiddlerData.substring(tiddlerData.indexOf("title="));

		return begin + middle + end;

	}
	
	private Date convertStringToDate(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date result = df.parse(date);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	private String replaceAllAakkoset(String data) {
		data = data.replace("Ã¤", "ä");
		data = data.replace("Ã„", "Ä");
		data = data.replace("Ã¥", "å");
		data = data.replace("Ã…", "Å");
		data = data.replace("Ã¶", "ö");
		data = data.replace("Ã–", "Ö");
		return data;
	}
	
	/** Checks if the data contains illegal operations. */
	private boolean checkDataForIllegalOperations(String tiddlerData,
			User user, Course course, String clientIp) {
		// Admins have all privileges
		if (user.getRole() == User.ADMIN) {
			return false;
		}

		if (tiddlerData.contains("systemConfig")
				|| tiddlerData.contains("EbookAutorun")
				|| tiddlerData.contains("&lt;html&gt;")) {
			// Add entry to the log
			db.addLogEntry(LogEntry.ILLEGAL_DATA_SUBMITTED, user.getId(),
					course.getId(), tiddlerData, clientIp, "");
			return true;
		}

		return false;

	}
	
	private boolean dataContainsSubstring(String subStr, String data){
		if(data.toLowerCase().contains(subStr.toLowerCase())){
			return true;
		} else{
			return false;
		}
	}
	
	private boolean dataHasOneTitleAttribute(String data){
		String subStr;
		String title = " title=";
		if(data.contains(">")){
			subStr = data.substring(0, data.indexOf(">")).toLowerCase();}
		else{
			subStr = data.toLowerCase();
		}
		if(!subStr.contains(title)){
			return false;
		} else{
			subStr = subStr.replaceFirst(title, "");
			return !subStr.contains(title);}
		
	}
	
	private String parseTiddlerTitle(String tiddler) {
		String result = tiddler.substring(tiddler.indexOf("title=\"") + 7);
		return result.substring(0, result.indexOf("\""));
	}
	
	
	
	private String allCourseUpdatesToString(List<AddedContent> contents,
			List<AssignmentSolution> solutions, List<Comment> comments,
			List<BookUpdate> bookUpdates, String requestTime) {
		StringBuilder result = new StringBuilder();
		// Add timestamp to the beginning
		result.append(requestTime);
		// Add delimiter
		result.append(Util.TIMESTAMP_DELIMITER);
		result.append("<div id=\"storeArea\">\\n");
		for (AddedContent content : contents) {
			result.append(content.getData());
			result.append("\\n");
		}
		for (AssignmentSolution solution : solutions) {
			result.append(solution.getData());
			result.append("\\n");
		}
		for (Comment comment : comments) {
			result.append(comment.getData());
			result.append("\\n");
		}
		for (BookUpdate bookUpdate : bookUpdates) {
			result.append(bookUpdate.getData());
			result.append("\\n");
		}
		result.append("</div>");
		return result.toString();
	}
	
	
	
	private static class UserGenerator {
		private static PasswordGenerator pg = new PasswordGenerator();
		
		private static User createStudent(int courseId, int gender) {
			Course c = db.getCourse(courseId);
			School s = c.getSchool();
			User user = new User();
			user.setRole(User.STUDENT);
			user.setGender(gender);
			user.setSchoolId(s.getId());
			user.setPassword(pg.generate(12));
			if (gender == User.BOY) {
				user.setUserHash(s.getCountry() + s.getSchool() + "B"
						+ db.getNextStudentNumber(s.getId(), gender));
			} else if (gender == User.GIRL) {
				user.setUserHash(s.getCountry() + s.getSchool() + "G"
						+ db.getNextStudentNumber(s.getId(), gender));
			}

			db.createElement(user);
			return user;
		}

		private static User createTeacher(String name, String email,
				int schoolId) {
			User teacher = new User();
			School school = db.getSchoolById(schoolId);
			if(school == null){
				return null;
			}
			teacher.setRole(User.TEACHER);
			teacher.setName(name);
			teacher.setEmail(email);
			teacher.setSchoolId(schoolId);
			teacher.setPassword(pg.generate(12));
			teacher.setUserHash(school.getCountry() + school.getSchool() + "T"
					+ db.getNextTeacherNumber(schoolId));
			db.createElement(teacher);
			return teacher;
		}
	}



	@Override
	public int grantExamAccess(int[] students, int examId, int timeLimit) {
		Date now = new Date();
		Date validUntil = new Date(now.getTime() + (timeLimit * 1000));
		Exam e = db.getExamById(examId);
		if(e == null){
			return 0;
		}
		for(int i = 0; i < students.length; ++i){
			ExamAccess ea = db.getExamAccess(students[i], examId);
			if(ea != null){
				db.deleteElement(ea);
			
			}
			db.addExamAccess(students[i], examId, validUntil);
		}
		return 1;
		
	}

	@Override
	public Exam getExam(User u, int examId) {
		Date requestTime = new Date();
		ExamAccess ea = db.getExamAccess(u.getId(), examId);
		Exam e = db.getExamById(examId);
		Course c = db.getCourse(e.getCourseId());		
		if(e == null || c == null){
			return null;
		}
		else if(c.isTeacherInCourse(u.getId())){
			return e;
		} else if(ea == null){
			e.setData(null);
			return e;
		} else if(ea.getValidUntil().compareTo(requestTime) >= 0){
			db.deleteElement(ea);
			return e; 
		} else{
			e.setData(null);
			return e;
		}
	}

	@Override
	public int saveExamSolution(User u, int examId, String data) {
		Date d = new Date();
		ExamSolution es = new ExamSolution();
		es.setUserId(u.getId());
		es.setExamId(examId);
		es.setTimestamp(d);
		es.setData(data);
		if(db.createElement(es)){
			return 1;
		} else{
			return 0;
		}
	}

	@Override
	public int saveHistory(User u, int bookId, int courseId, String data) {
		History history = new History();
		history.setUserId(u.getId());
		history.setData(data);
		history.setBookId(bookId);
		history.setCourseId(courseId);
		if(db.createElement(history)){
			return 1;}
		else{
			return 0;}
	}

	@Override
	public List<DummySystemUpdate> getDummySystemUpdates(String bookVersion,
			int updateType, String lastUpdateTime) {
		Date date = new Date(0);
		if (!lastUpdateTime.equals("0")) {
			date = this.convertStringToDate(lastUpdateTime);
		}
		return db.getDummySystemUpdates(bookVersion, updateType, date);
	}


}
