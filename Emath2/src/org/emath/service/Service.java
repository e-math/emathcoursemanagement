package org.emath.service;

import java.util.List;
import java.util.Set;

import org.emath.model.book.AssignmentSolution;
import org.emath.model.book.Book;
import org.emath.model.book.Comment;
import org.emath.model.communication.ClientBook;
import org.emath.model.communication.Course;
import org.emath.model.communication.DummySystemUpdate;
import org.emath.model.communication.Exam;
import org.emath.model.communication.School;
import org.emath.model.communication.SystemUpdate;
import org.emath.model.communication.User;

/**
 * Defines the service layer contract of the e-math server-side system.
 *
 */
public interface Service {
	/**
	 * Returns the User object if the user is valid, else returns null. For teachers and students
	 * the user is considered valid if 1) username and password match 2) course id can be parsed 3)
	 * course exists 4) first character of course is 'c' or 'C' and user is in course. For admins
	 * only username and password need to be correct.
	 */
	public User isValidUser(String userName, String password, String courseString, String clientIp, String userAgent);
	/**
	 * Saves the BookUpdate and returns the database id of the inserted BookUpdate.
	 * @param user The user who inserts the BookUpdate
	 * @param data The data content of the BookUpdate
	 * @param bookId
	 * @param updateType
	 * @param clientIp The ip address of the client.
	 * @param lang The language for which the content is meant or null if for all languages.
	 * @return The database id
	 */
	public int saveBookUpdate(User user, String data, String bookId, String updateType, 
			String clientIp, String lang);
	/**
	 * Inserts a new Course to the database and returns the database id.
	 * @param schoolId
	 * @param teachersText
	 * @param booksText
	 * @return database id
	 */
	public int addCourse(int schoolId, String teachersText, String booksText);
	/**
	 * Inserts a new Teacher to the database.
	 * @param name
	 * @param email
	 * @param schoolId
	 * @return course id in database
	 */
	public User addTeacher(String name, String email, int schoolId);
	/**
	 * Gets information about the newest entry in the solution database table for a User and a Course.
	 * @param userIdToCheck The user id.
	 * @param courseId The course id.
	 * @return The teacher
	 */
	public String getNewestEntry(int userIdToCheck, int courseId);
	/**
	 * Gets information about the newest entry in the addedcontent database table for a Course.
	 * @param courseId The course id
	 * @return A JSON-string
	 */
	public String getAddedContentInfo(int courseId);
	/**
	 * Gets information about the newest comment for a course.
	 * @param courseId The course id
	 * @return A JSON-string
	 */
	public String getCommentsInfo(int courseId);
	/**
	 * Adds a Book with the given title to the Database.
	 * @param title The title of the Book
	 * @return A JSON-string
	 */
	public Book addBook(String title);
	/**
	 * Adds a School with the given country and name to the database.
	 * @param country The country string (for example 'FI')
	 * @param name The name of the school
	 * @return the School
	 */
	public School addSchool(String country, String name);
	/**
	 * Inserts a SystemUpdate into the database if user.getRole() == User.ADMIN. Else simply 
	 * returns "-1". If insert is successful then database id is returned.
	 * @param user The user who is attempting insert.
	 * @param data The data content of the SystemUpdate
	 * @param updateType
	 * @param clientIp The ip address of the client
	 * @return database id || -1 if unsuccessful
	 */
	public int saveSystemUpdate(User user, String data, String updateType, String clientIp);
	/**
	 * Gets SystemUpdates from the database that are newer than lastUpdateTime.
	 * @param user The User who is getting updates
	 * @param lastUpdateTime The time when the Users book was previously updated
	 * @param requestTime The time when the request arrived
	 * @return A list of SystemUpdates 
	 */
	public List<SystemUpdate> getSystemUpdates(User user, String lastUpdateTime, String requestTime);
	/**
	 * Saves the solution to the database as a new solution if a solution with exactly the same
	 * title in its data doesn't exist. Else performs update to the existing solution.
	 * @param user The user that is saving the solution
	 * @param course The course that the User is in
	 * @param data The data content of the Solution
	 * @param clientIp The ip address of the client
	 * @param isPublic solution published or not.
	 * @param id The database id of the solution or null if new
	 * @return database id of the solution
	 */
	public int saveSolution(User user, Course course, String data, String clientIp, int isPublic,
			String id);
	/**
	 * Inserts or modifies AddedContent and saves it to the database if no illegal operations have 
	 * been attempted.
	 * @param user The User who is saving the AddedContent
	 * @param course The course to which the AddedContent is added to
	 * @param clientIp The ip address of the client
	 * @param isPublic AddedContent published or not
	 * @param available The date when the AddedContent will be available
	 * @param id The database id || null if new AddedContent
	 * @param data The data content of the AddedContent
	 * @return if illegal operation is attempted returns "-1", else AddedContents database id
	 */
	public int saveContent(User user, Course course, String clientIp, int isPublic, 
			String available, String id, String data);
	/**
	 * Inserts or modifies Comment and saves it to the database if no illegal operations have 
	 * been attempted.
	 * @param user The User who is saving the Comment
	 * @param course The course to which the Comment is added to
	 * @param data The data content of the Comment
	 * @param clientIp The ip address of the client
	 * @param userHash The userhash of the student
	 * @param id The database id || null if new Comment
	 * @return if illegal operation is attempted returns -1, else Comments database id
	 */
	public int saveComment(User user, Course course, String data, String clientIp,
			String userHash, String id);
	/**
	 * Gets all comments from the database that were added after lastUpdateTime.
	 * @param user The User who is getting Comments
	 * @param course The Course for which Comments are wanted 
	 * @param lastUpdateTime The time when the Users book was previously updated 
	 * @param requestTime The time when the request was received
	 * @return A storearea element that contains the Comments
	 */
	public List<Comment> getComments(User user, Course course, String lastUpdateTime, String requestTime);
	/**
	 * Gets a list of all ebooks.
	 */
	public Set<ClientBook> getBookList();
	/**
	 * Gets a list of all schools.
	 */
	public Set<School> getSchoolList();
	/**
	 * Gets a list of all students of the school that has the given course.
	 * @param courseId The database id of the course.
	 */
	public Set<User> getStudentList(int courseId);
	/**
	 * Gets all courses with a certain Teacher.
	 * @param teacherId The database id of the teacher
	
	 */
	public Set<Course> getCoursesByTeacherId(int teacherId);
	/**
	 * Gets all teachers of a certain school.
	 * @param schoolId The database id of the school
	 * @return All teachers from school were School.getId() == schoolId
	 */
	public Set<User> getTeachers(int schoolId);
	/**
	 * Gets all updates that were inserted into the database after lastUpdateTime.
	 * @param user The User that has requested updates
	 * @param course The course that the user is getting updates for
	 * @param lastUpdateTime The time when the Users book was previously updated 
	 * @param requestTime The time when the request was received
	 * @return A storearea element that contains the Updates
	 */
	public String getUpdates(User user, Course course, String lastUpdateTime, String requestTime);
	/**
	 * Gets all solutions from the database that were inserted after lastUpdateTime.
	 * @param user The User requesting solutions
	 * @param course The course for which solutions are being fetched
	 * @param lastUpdateTime The time when the Users book was previously updated 
	 * @param requestTime The time when the request was received
	 * @return A list of Solutions
	 */
	public List<AssignmentSolution> getSolutions(User user, Course course, String lastUpdateTime, String requestTime);
	/**
	 * Locks the given course for the given user.
	 * @param user The user who will have access to the locked course
	 * @param course The course that will be locked
	 * @return The database id of the lock
	 */
	public int requestLock(User user, Course course);
	/**
	 * Removes an existing course lock.
	 * @param user The user that is removing the lock
	 * @param course The course that is unlocked
	 * @param lockId
	 * @return 1 if successful else 0
	 */
	public int removeLock(User user, Course course, String lockId);
	/**
	 * Generates new students and adds them to the course.
	 * @param boysSum The amount of boy students to be generated
	 * @param girlsSum The amount of girl students to be generated
	 * @param courseString The course with letter (for example "C60")
	 * @return A set of new students
	 */
	public Set<User> generateUsers(int boysSum, int girlsSum, String courseString);
	/**
	 * Gets the list of students who are participating in the course.
	 * @param courseString The course with letter (for example "C60")
	 * @return
	 */
	public Set<User> getCourseStudents(String courseString);
	/**
	 * Parses the course and saves it to the database.
	 * @param courseJson
	 * @return 1 if successful else 0
	 */
	public int saveCourse(Course clientCourse);
	/**
	 * Adds a new entry to the traffic log.
	 * @param userId The database id of the user
	 * @param remoteAddr The ip address of the client
	 * @param requestInfo All information that was parsed from the request
	 */
	public void addTrafficLogEntry(int userId, String remoteAddr, String requestInfo);
	/**
	 * Returns the Course object for which getId() would return courseId.
	 * @param courseId The database id of the course
	 * @return The Course object
	 */
	public Course getCourse(int courseId);
	/**
	 * This method is deprecated and is only be used by old ebooks. Use generateUsers-method instead.
	 */
	@Deprecated
	public String generateUsersLegacy(int boysSum, int girlsSum, int courseId);
	/**
	 * This method is deprecated and is only be used by old ebooks. Use getCourseStudents-method instead.
	 */
	@Deprecated
	public String getCourseStudentsLegacy(int courseId);
	
	/**
	 * Grants one-time access to an exam to students based on their id:s.
	 * @param students An array of student id:s
	 * @param examId The id of the exam to which access is granted
	 * @param timeLimit The time that the access permission is valid in seconds 
	 * @return if successful return 1, else return 0
	 */
	public int grantExamAccess(int[] students, int examId, int timeLimit);
	
	/**
	 * Gets the exam e for which e.id=examId. If User u has permission to get the whole exam. Else the 
	 * user will get an exam where e.getData == null.
	 * @param u The user who is calling getExam
	 * @param examId The database id of the exam
	 * @return The requested exam
	 */
	public Exam getExam(User u, int examId);
	/**
	 * Saves the answers to an exam.
	 * @param u The user who has sent the answers
	 * @param examId The id that the answers are for
	 * @param data The answer data
	 * @return 1 if successful, else 0
	 */
	public int saveExamSolution(User u, int examId, String data);
	/**
	 * Saves the book "session" history to the database.
	 * @param u The user who sent his/her navigation history
	 * @param bookId The database id of the book
	 * @param courseId The database id of the course
	 * @param data The navigation data
	 * @return 1 if successful, else 0
	 */
	public int saveHistory(User u, int bookId, int courseId, String data);
	/**
	 * Gets the system updates for a dummy book.
	 * @param bookVersion The version of the book that updates are requested for
	 * @param updateType 0 for teacher book, 1 for student book
	 * @param lastUpdateTime The time when the book instance was updated last time
	 * @return A list of DummySystemUpdates
	 */
	public List<DummySystemUpdate> getDummySystemUpdates(String bookVersion, int updateType, String lastUpdateTime);

	/**
	 * This method confirms if the given user is either a teacher or a student in 
	 * the course. 
	 * @param user
	 * @param courseString The course. For example "C30"
	 * @param clientIp The ip address of the client
	 * @param userAgent The user-agent info String from the request
	 * @return The course if the user is in the course. Else returns null
	 */
	public Course isUserInCourse(User user, String courseString,
			String clientIp, String userAgent);
}
