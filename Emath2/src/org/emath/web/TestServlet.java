package org.emath.web;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.emath.model.book.AssignmentSolution;
import org.emath.model.book.Book;
import org.emath.model.book.Comment;
import org.emath.model.communication.ClientBook;
import org.emath.model.communication.Course;
import org.emath.model.communication.DummySystemUpdate;
import org.emath.model.communication.Exam;
import org.emath.model.communication.LoginAttempt;
import org.emath.model.communication.School;
import org.emath.model.communication.SystemUpdate;
import org.emath.model.communication.User;
import org.emath.service.Service;
import org.emath.service.ServiceImpl;
import org.emath.service.Util;

import com.google.gson.Gson;

/**
 * Servlet implementation class TestServlet
 */

public class TestServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private static Gson gson = new Gson();

	private static Service service = new ServiceImpl();
	/**
	 * The RequestType is used to distinguish different kinds of requests from each other.
	 *
	 */
	public enum RequestType {
		GET_UPDATED_CONTENT(0),
		REQUEST_LOCK(1),
		SAVE_CONTENT(2),
		REMOVE_LOCK(3),
		CHECK_UPDATES(4),
		SAVE_SOLUTION(5),
		GET_SOLUTIONS(6),
		SAVE_COMMENT(7),
		GET_COMMENTS(8),
		SAVE_SYSTEM_UPDATE(9),
		GET_SYSTEM_UPDATES(10),
		SAVE_BOOK_UPDATE(11),
		CHECK_FIRST_LOGIN(12),
		SAVE_COURSE(13),
		GET_COURSE_STUDENTS(14),
		GENERATE_STUDENTS(15),
		GET_TEACHER_LIST(16),
		GET_STUDENT_LIST(17),
		ADD_COURSE(18),
		GET_SCHOOL_LIST(19),
		GET_BOOK_LIST(20),
		ADD_TEACHER(21),
		ADD_SCHOOL(22),
		ADD_BOOK(23),
		GET_COURSES_BY_TEACHER(24),
		GET_NEWEST_ENTRY(25),
		GET_ADDEDCONTENT_INFO(26),
		GET_COMMENTS_INFO(27),
		GRANT_EXAM_ACCESS(28),
		GET_EXAM(29),
		SUBMIT_EXAM(30),
		SAVE_HISTORY(31),
		GET_USER_ROLE(32);

		private final int value;

		RequestType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}



	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestServlet() {

	}

	@Override
	public void init() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		/*
		if ((request.getParameter("boys") != "" && request.getParameter("boys") != null)
				|| (request.getParameter("girls") != "" && request
				.getParameter("girls") != null)) {
			int boysSum = new Integer(request.getParameter("boys")).intValue();
			int girlsSum = new Integer(request.getParameter("girls")).intValue();
			int courseId = new Integer(request.getParameter("courseId")).intValue();
			service.generateUsersLegacy(boysSum, girlsSum, courseId);
		}
		if (request.getParameter("getcoursestudents") != ""
				&& request.getParameter("getcoursestudents") != null) {
			int courseId = new Integer(request.getParameter("courseId")).intValue();
			service.getCourseStudentsLegacy(courseId);
		}*/
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");

		/* the time when the request was received */
		Date requestTime = new Date();


		/* if the book is a dummy book */
		if(request.getParameter("dummyversion") != null && request.getParameter("dummy") != ""){
			String bookVersion = request.getParameter("dummyversion");
			int updateType = Integer.parseInt(request.getParameter("updatetype"));
			String lastUpdateTime = request.getParameter("lastcheck");
			if(lastUpdateTime == null || lastUpdateTime == ""){
				lastUpdateTime = "0";
			}
			List<DummySystemUpdate> updates = service.getDummySystemUpdates(bookVersion, updateType, lastUpdateTime);
			print(response, gson.toJson(updates));
		} else{

			String userName = request.getParameter("username");
			String password = request.getParameter("userkey");
			String courseString = request.getParameter("courseid");
			String clientIp = request.getRemoteAddr();
			String userAgent = request.getHeader("User-Agent");

			User user = service.isValidUser(userName, password, courseString,
					clientIp, userAgent);
			/*db logging of invalid users is handled in service layer. Response to client is handled here */
			if(user == null){
				print(response, gson.toJson(new LoginAttempt(LoginAttempt.USER_VALIDATION_FAILED,
						"User identification failed!")));
				return;
			}

			Course course = null;
			/* may be null if user is an admin! */
			if(user.getRole() != User.ADMIN){
				course = service.isUserInCourse(user, courseString,
						clientIp, userAgent);
				if(course == null){
					print(response, gson.toJson(new LoginAttempt(LoginAttempt.COURSE_VALIDATION_FAILED,
							"Course identification failed")));
					return;
				}
			} else{
				int courseId = parseCourseId(courseString);
				if(courseId != -1){
					course = service.getCourse(courseId);}
			}

			
			
			




			/* for traffic logging */
			String requestString = requestToString(request);
			service.addTrafficLogEntry(user.getId(), request.getRemoteAddr(),
					requestString);

			if (request.getParameter("type") != null) {
				int type;
				if(user.getType() == User.DEMO){
					type = RequestType.CHECK_FIRST_LOGIN.getValue();
				} else{
					type = Integer.parseInt(request.getParameter("type"));
				}
				if (type == RequestType.SAVE_SOLUTION.getValue()) {
					String data = request.getParameter("data");
					int isPublic = Integer.parseInt(request.getParameter("ispublic"));
					String checkNro = request.getParameter("checknro");
					int id = service.saveSolution(user, course, data, clientIp,
							isPublic, checkNro);
					print(response, "" + id);
				} else if (type == RequestType.SAVE_CONTENT.getValue()) {
					int isPublic = Integer.parseInt(request.getParameter("ispublic"));
					String available = request.getParameter("available");
					String checkNro = request.getParameter("checknro");
					String data = request.getParameter("data");
					int id = service.saveContent(user, course, clientIp, isPublic,
							available, checkNro, data);
					print(response, "" + id);

				} else if (type == RequestType.CHECK_UPDATES.getValue()) {
					// Check updates no longer used
				} else if (type == RequestType.GET_UPDATED_CONTENT.getValue()) {
					String lastCheck = request.getParameter("lastcheck");
					String result = service.getUpdates(user, course, lastCheck, convertDateToString(requestTime));
					print(response, result);
				} else if (type == RequestType.REQUEST_LOCK.getValue()) {
					int id = service.requestLock(user, course);
					print(response, "" + id);
				} else if (type == RequestType.REMOVE_LOCK.getValue()) {
					String lockId = request.getParameter("lockid");
					int success = service.removeLock(user, course, lockId);
					print(response, "" + success);
				} else if (type == RequestType.GET_SOLUTIONS.getValue()) {
					String lastCheck = request.getParameter("lastcheck");
					List<AssignmentSolution> solutions = service.getSolutions(user, course, lastCheck, convertDateToString(requestTime));
					String data = solutionListToString(solutions, convertDateToString(requestTime));
					print(response, data);
				} else if (type == RequestType.SAVE_COMMENT.getValue()) {
					String data = request.getParameter("data");
					String student = request.getParameter("student");
					String checkNro = request.getParameter("checknro");
					int id = service.saveComment(user, course, data, clientIp, student,
							checkNro);
					print(response, "" + id);
				} else if (type == RequestType.GET_COMMENTS.getValue()) {
					String lastCheck = request.getParameter("lastcheck");
					List<Comment> comments = service.getComments(user, course, lastCheck, convertDateToString(requestTime));
					String data = commentListToString(comments, convertDateToString(requestTime));
					print(response, data);

				} else if (type == RequestType.SAVE_SYSTEM_UPDATE.getValue()) {
					String data = request.getParameter("data");
					String updateType = request.getParameter("updatetype");
					//String checkNro = request.getParameter("checknro");
					int id = service.saveSystemUpdate(user, data, updateType, clientIp);
					print(response, "" + id);
				} else if (type == RequestType.GET_SYSTEM_UPDATES.getValue()) {
					String lastCheck = request.getParameter("lastcheck");
					List<SystemUpdate> updates = service.getSystemUpdates(user, lastCheck, convertDateToString(requestTime));
					String data = systemUpdateListToString(updates, convertDateToString(requestTime));
					print(response, data);
				} else if (type == RequestType.SAVE_BOOK_UPDATE.getValue()) {
					String data = request.getParameter("data");
					String bookId = request.getParameter("bookid");
					String lang = request.getParameter("lang");
					if(lang != null){
						if(lang.equals("")){
							lang = null;
						}
					}
					String updateType = request.getParameter("updatetype");
					//checknro not used anymore
					//String checkNro = request.getParameter("checknro");
					int ret = service.saveBookUpdate(user, data, bookId, updateType,
							clientIp, lang);
					print(response, ""+ ret);
				} else if (type == RequestType.CHECK_FIRST_LOGIN.getValue()) {
					boolean allOk = true;
					/* The User has been validated already so just check the course book
					 * if book list was sent.
					 */
					String bookIdsParam = request.getParameter("bookids");
					String[] bookIds;
					if(bookIdsParam != null && user.getRole() != User.ADMIN){
						allOk = false;
						ArrayList<ClientBook> courseBooks = new ArrayList<ClientBook>();
						Iterator<ClientBook> i = course.getBooks().iterator();
						while(i.hasNext()){
							courseBooks.add(i.next());
						}
						bookIds = bookIdsParam.split(",");
						for(String bookId : bookIds){
							int id = Integer.parseInt(bookId.trim());
							for(int j = 0; j < courseBooks.size(); ++j){
								if(courseBooks.get(j).getId() == id){
									allOk = true;
								}
							}
							
						}
					}					
					if(allOk){
						print(response, "OK");
					} else{
						String courseBook = course.getBooks().iterator().next().getTitle();
						print(response, gson.toJson(new LoginAttempt(LoginAttempt.BOOK_VALIDATION_FAILED, courseBook)));
					}
				} else if (type == RequestType.SAVE_COURSE.getValue()) {
					if(user.getType() == User.NOSTUDENTMANAGEMENT){
						print(response, "" + 0);
					} else{
						String courseJson = request.getParameter("course");
						Course clientCourse = gson.fromJson(courseJson, Course.class);
						int success = service.saveCourse(clientCourse);
						print(response, "" + success);}
				} else if (type == RequestType.GET_COURSE_STUDENTS.getValue()) {
					String courseIdParam = request.getParameter("courseid");
					Set<User> students = service.getCourseStudents(courseIdParam);
					print(response, gson.toJson(students));
				} else if (type == RequestType.GENERATE_STUDENTS.getValue()) {
					if(user.getType() == User.NOSTUDENTMANAGEMENT){
						print(response, "{\"boys\":" + gson.toJson(new HashSet<User>()) + ",\"girls\":"
								+ gson.toJson(new HashSet<User>()) + "}");
					} else if ((request.getParameter("boys") != "" && request
							.getParameter("boys") != null)
							|| (request.getParameter("girls") != "" && request
							.getParameter("girls") != null)) {
						int boysSum = Integer.parseInt(request.getParameter("boys"));
						int girlsSum = Integer.parseInt(request.getParameter("girls"));
						String courseIdParam = request.getParameter("courseid");
						Set<User> students = service.generateUsers(boysSum, girlsSum,
								courseIdParam);
						Set<User> boys = new HashSet<User>();
						Set<User> girls = new HashSet<User>();
						Iterator<User> i = students.iterator();
						while(i.hasNext()){
							User u = i.next();
							if(u.getGender() == User.BOY){
								boys.add(u);
							} else{
								girls.add(u);
							}
						}
						print(response, "{\"boys\":" + gson.toJson(boys) + ",\"girls\":"
								+ gson.toJson(girls) + "}");
					}
				} else if (type == RequestType.GET_SCHOOL_LIST.getValue()) {
					if (user.getRole() != User.ADMIN) {
						print(response, "User identification failed!");
						return;
					}

					Set<School> schools = service.getSchoolList();
					print(response, gson.toJson(schools));
				} else if (type == RequestType.GET_BOOK_LIST.getValue()) {
					if (user.getRole() != User.ADMIN) {
						print(response, "User identification failed!");
						return;
					}

					Set<ClientBook> books = service.getBookList();
					print(response, gson.toJson(books));
				} else if (type == RequestType.GET_TEACHER_LIST.getValue()) {
					if (user.getRole() != User.ADMIN) {
						print(response, "User identification failed!");
						return;
					}
					int schoolId = Integer.parseInt(request.getParameter("schoolid"));
					Set<User> teachers = service.getTeachers(schoolId);
					String res = "";
					Iterator<User> i = teachers.iterator();
					// if there are 0 teachers return empty array
					if (!i.hasNext()) {
						res = "[]";
					} else {
						res = "[";
						while (i.hasNext()) {
							User teacher = i.next();
							Set<Course> courses = service.getCoursesByTeacherId(teacher.getId());
							res += "{\"teacher\":" + gson.toJson(teacher)
									+ ", \"courses\":" + gson.toJson(courses) + "},";
						}
						res = res.substring(0, res.length() - 1);
						res += "]";
					}
					print(response, res);
				} else if (type == RequestType.GET_STUDENT_LIST.getValue()) {
					String courseIdParam = request.getParameter("courseid");
					int courseIdInt = parseCourseId(courseIdParam);
					Set<User> students = service.getStudentList(courseIdInt);
					print(response, gson.toJson(students));
				} else if (type == RequestType.ADD_COURSE.getValue()) {
					if (user.getRole() != User.ADMIN) {
						print(response, "User identification failed!");
						return;
					}
					int schoolId = Integer.parseInt(request.getParameter("schoolid"));
					String teachers = request.getParameter("teachers");
					String books = request.getParameter("books");
					int ret = service.addCourse(schoolId, teachers, books);
					print(response, "" + ret);
				} else if (type == RequestType.ADD_TEACHER.getValue()) {
					if (user.getRole() != User.ADMIN) {
						print(response, "User identification failed!");
						return;
					}
					String name = request.getParameter("name");
					String email = request.getParameter("email");
					int schoolId = Integer.parseInt(request.getParameter("schoolid"));
					User teacher = service.addTeacher(name, email, schoolId);
					print(response, "[{\"teacher\":" + gson.toJson(teacher)
							+ ", \"courses\":[]}]");

				} else if (type == RequestType.ADD_SCHOOL.getValue()) {
					if (user.getRole() != User.ADMIN) {
						print(response, "User identification failed!");
						return;
					}
					String country = request.getParameter("country");
					String name = request.getParameter("name");
					School s = service.addSchool(country, name);
					print(response, gson.toJson(s));
				} else if (type == RequestType.ADD_BOOK.getValue()) {
					if (user.getRole() != User.ADMIN) {
						print(response, "User identification failed!");
						return;
					}
					String title = request.getParameter("title");
					Book b = service.addBook(title);
					Set<ClientBook> books = new LinkedHashSet<ClientBook>();
					books.add(new ClientBook(b));
					print(response, gson.toJson(books));
				} else if (type == RequestType.GET_COURSES_BY_TEACHER.getValue()) {
					if (user.getRole() != User.ADMIN) {
						print(response, "User identification failed!");
						return;
					}
					int teacherId = Integer.parseInt(request.getParameter("teacherid"));
					Set<Course> courses = service.getCoursesByTeacherId(teacherId);
					print(response, gson.toJson(courses));
				} else if (type == RequestType.GET_NEWEST_ENTRY.getValue()) {
					int userIdToCheck = Integer.parseInt(request.getParameter("useridtocheck"));
					int courseIdParam = Integer.parseInt(request.getParameter(request.getParameter("courseid")));
					String result = service.getNewestEntry(userIdToCheck, courseIdParam);
					print(response, result);
				} else if (type == RequestType.GET_ADDEDCONTENT_INFO.getValue()) {
					int courseIdParam = Integer.parseInt(request.getParameter(request.getParameter("courseid")));
					String result = service.getAddedContentInfo(courseIdParam);
					print(response, result);
				} else if (type == RequestType.GET_COMMENTS_INFO.getValue()) {
					int courseIdParam = Integer.parseInt(request.getParameter(request.getParameter("courseid")));
					String result = service.getCommentsInfo(courseIdParam);
					print(response, result);
				} else if (type == RequestType.GRANT_EXAM_ACCESS.getValue()){
					if (user.getRole() != User.ADMIN && user.getRole() != User.TEACHER) {
						print(response, "User identification failed!");
						return;
					}
					int examId = Integer.parseInt(request.getParameter("exam_id"));
					int[] students = gson.fromJson(request.getParameter("students"), int[].class);
					int timeLimit;
					try{
						timeLimit = Integer.parseInt(request.getParameter("time_limit"));
					} catch(Exception e){
						timeLimit = 600;
					}
					int success = service.grantExamAccess(students, examId, timeLimit);
					print(response, "" + success);

				} else if (type == RequestType.GET_EXAM.getValue()){
					int examId = Integer.parseInt(request.getParameter("exam_id"));
					Exam e = service.getExam(user, examId);
					print(response, gson.toJson(e));
				} else if (type == RequestType.SUBMIT_EXAM.getValue()){
					int examId = Integer.parseInt(request.getParameter("exam_id"));
					String data = request.getParameter("data");
					int success = service.saveExamSolution(user, examId, data);
					print(response, "" + success);
				} else if (type == RequestType.SAVE_HISTORY.getValue()){
					int bookId = -1;
					try{
						bookId = Integer.parseInt(request.getParameter("book_id"));
					} catch(Exception e){

					}
					String data = request.getParameter("data");
					int success = service.saveHistory(user, bookId, course.getId(), data);
					print(response, "" + success);
				} else if (type == RequestType.GET_USER_ROLE.getValue()){
					print(response, "" + user.getRole());
				}
			}

			/* for traffic logging because of encoding problem
			request.setCharacterEncoding("UTF-8");
			String requestString2 = "REQUEST_TO_UTF: " + requestToString(request);
			//System.out.println(requestString2);
			service.addTrafficLogEntry(user.getId(), request.getRemoteAddr(),
					requestString2);
			*/

		}
	}


	/*	private String updatedContentListToString(List<AddedContent> contents,
			String requestTime) {
		StringBuilder result = new StringBuilder();
		// Add timestamp to the beginning
		result.append(requestTime);
		// Add delimiter
		result.append(TestServlet.TIMESTAMP_DELIMITER);
		result.append("<div id=\"storeArea\">\\n");
		for (AddedContent content : contents) {
			result.append(content.getData());
			result.append("\\n");
		}
		result.append("</div>");
		return result.toString();
	}*/























	/*
	public void test() {

		// The ebook that is being read into the system
		String bookFile = "/Users/temira/Desktop/emath/Lukio-1v0.3.html";
		String outputFile = "/Users/temira/Desktop/outputFile.txt";

		try {
			byte[] buffer = new byte[(int) new File(bookFile).length()];
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(bookFile));
			try {
				bis.read(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String input = new String(buffer);
			int start = input.indexOf("<div id=\"storeArea\">") + 20;
			int end = input.indexOf("<!--POST-STOREAREA-->") - 8;

			String storeArea = input.substring(start, end);
			BufferedWriter elementFile = new BufferedWriter(new FileWriter(
					outputFile));
			elementFile.write("<html>");
			elementFile.write(parseDivWithExactTitle(input, "SettingsEbook"));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"assignment", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"ebook_assignmentlist", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"chapter", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"examplebox", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"examplebox", "_data"));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"section", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"textelement", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"textelement", "_data"));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"theorybox", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"theorybox", "_data"));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"graphelement", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"image", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"subsection", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"examplesd", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"examplesd", "_data"));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"prerequisites", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"prerequisites", "_data"));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"discussion", ""));
			elementFile.write(parseDivsWithNumbersByTitleAttribute(storeArea,
					"discussion", "_data"));

			// bw.write(output);
			elementFile.write("</html>");
			elementFile.close();
			// System.out.println(input);
			// System.out.println("OUTPUT:");
			// System.out.println(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			ElementParser ep = new ElementParser(outputFile);
			System.out.println(ep.getSettingsEbook().getContent());

			System.out.println("chapters: " + ep.getChapters().size());
			System.out.println("sections: " + ep.getSections().size());
			System.out.println("subsections: " + ep.getSubSections().size());
			System.out.println("assignmentlists: "
					+ ep.getAssignmentLists().size());
			System.out.println("assignments: " + ep.getAssignments().size());
			System.out.println("exampleSds: " + ep.getExampleSds().size());
			System.out.println("exampleSdDatas: "
					+ ep.getExampleSdDatas().size());
			System.out
					.println("prerequisites: " + ep.getPrerequisites().size());
			System.out.println("prerequisiteDatas: "
					+ ep.getPrerequisiteDatas().size());
			System.out.println("discussions: " + ep.getDiscussions().size());
			System.out.println("images: " + ep.getImages().size());

			Book book = new Book();

			Set<Chapter> chapters = ep.getChapters();

			for (Chapter c : chapters) {
				book.addChapter(c);
			}

			db.createElement(book);

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String parseDivsWithNumbersByTitleAttribute(String text,
			String title, String end) {

		int b = 0;
		int e = 0;
		String ret = "";
		while (b != text.lastIndexOf("<div title=\"" + title)
				&& text.lastIndexOf("<div title=\"" + title) != -1) {
			b = text.indexOf("<div title=\"" + title, b + 1);
			e = text.indexOf("</div>", b);
			String divElement = text.substring(b, e + 7);
			if (divElement.matches("[\\S|\\s]*title=\"" + title + "_[0-9]{1,}"
					+ end + "\"[\\S|\\s]*")) {
				ret += divElement;
			}
		}
		return ret;
	}

	public static String parseDivWithExactTitle(String text, String title) {

		int b = 0;
		int e = 0;
		String ret = "";
		if (text.indexOf("<div title=\"" + title + "\"") != -1) {
			b = text.indexOf("<div title=\"" + title + "\"");
			e = text.indexOf("</div>", b);
			ret = text.substring(b, e + 7);
		}
		return ret;
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
	 */






	private void print(HttpServletResponse response, String string) throws IOException{
		PrintWriter out = response.getWriter();
		out.print(string);
		out.flush();
		out.close();
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




	private String requestToString(HttpServletRequest request) {
		String result = "username: " + request.getParameter("username") + ", ";
		result += "lastcheck: " + request.getParameter("lastcheck") + ", ";
		result += "type: " + request.getParameter("type") + ", ";
		result += "courseid: " + request.getParameter("courseid") + ", ";
		result += "userkey: " + request.getParameter("userkey") + ", ";
		result += "checknro: " + request.getParameter("checknro") + ", ";
		result += "data: " + request.getParameter("data") + ", ";
		result += "clientip: " + request.getRemoteAddr() + ", ";
		result += "charset: " + request.getCharacterEncoding();

		return result;
	}

	private String systemUpdateListToString(List<SystemUpdate> updates,
			String requestTime) {
		StringBuilder result = new StringBuilder();
		// Add timestamp to the beginning
		result.append(requestTime);
		// Add delimiter
		result.append(Util.TIMESTAMP_DELIMITER);
		result.append("<div id=\"storeArea\">\\n");
		for (SystemUpdate update : updates) {
			result.append(update.getData());
			result.append("\\n");
		}
		result.append("</div>");
		return result.toString();
	}

	private String commentListToString(List<Comment> comments,
			String requestTime) {
		StringBuilder result = new StringBuilder();
		// Add timestamp to the beginning
		result.append(requestTime);
		// Add delimiter
		result.append(Util.TIMESTAMP_DELIMITER);
		result.append("<div id=\"storeArea\">\\n");
		for (Comment comment : comments) {
			result.append(comment.getData());
			result.append("\\n");
		}
		result.append("</div>");
		return result.toString();
	}

	private String solutionListToString(List<AssignmentSolution> solutions,
			String requestTime) {
		StringBuilder result = new StringBuilder();
		// Add timestamp to the beginning
		result.append(requestTime);
		// Add delimiter
		result.append(Util.TIMESTAMP_DELIMITER);
		result.append("<div id=\"storeArea\">\\n");
		for (AssignmentSolution solution : solutions) {
			result.append(solution.getData());
			result.append("\\n");
		}
		result.append("</div>");
		return result.toString();
	}

	private String convertDateToString(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

}
