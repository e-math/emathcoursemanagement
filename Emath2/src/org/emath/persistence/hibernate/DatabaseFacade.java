package org.emath.persistence.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.emath.model.book.AddedContent;
import org.emath.model.book.AssignmentSolution;
import org.emath.model.book.Book;
import org.emath.model.book.BookUpdate;
import org.emath.model.book.Comment;
import org.emath.model.communication.ClientBook;
import org.emath.model.communication.Course;
import org.emath.model.communication.DummySystemUpdate;
import org.emath.model.communication.Exam;
import org.emath.model.communication.ExamAccess;
import org.emath.model.communication.School;
import org.emath.model.communication.SystemUpdate;
import org.emath.model.communication.User;
/**
 * This interface defines the contract for the persistence layer of the e-math server-side system.
 *
 */
public interface DatabaseFacade {	
	/**
	 * Gets a list of all courses.
	 * @return
	 */
	public Set<Course> getCourseList();
	/**
	 * Gets a list of books as ClientBook-objects.
	 * @return
	 */
	public Set<ClientBook> getClientBooks();
	/**
	 * Gets a list of all schools.
	 * @return
	 */
	public Set<School> getSchools();
	/**
	 * Gets the School s for which s.id=schoolId or null if s does not exist. 
	 * @param schoolId
	 * @return
	 */
	public School getSchoolById(int schoolId);
	/**
	 * Gets a list of teachers as User objects  who are working at the given school or null if the school has no teachers.
	 * @param schoolId
	 * @return
	 */
	public Set<User> getTeachers(int schoolId);	
	/**
	 * Gets the teacher t as an User object whos t.userhash = userHash. 
	 * @param userHash
	 * @return
	 */
	public User getTeacherByUserHash(String userHash);	
	/**
	 * Gets the teacher t as an User object whos t.id = id. 
	 * @param id
	 * @return
	 */
	public User getTeacherById(int id);
	/**
	 * Gets all students as User objects who are students at the school s that holds the course.
	 * @param courseId
	 * @return
	 */
	public Set<User> getSchoolStudentsByCourseId(int courseId);
	/**
	 * Gets the book b for which b.id=bookId or null if b does not exist.
	 * @param bookId
	 * @return
	 */
	public Book getBookByBookId(int bookId);
	/**
	 * Gets a list of all books.
	 * @return
	 */
	public Set<Book> getBooks();

	
	public String listElements();
	/**
	 * Deletes the element from the database and returns boolean to express if the operation was 
	 * successful.
	 */
	public boolean deleteElement(Object element);
	/**
	 * Creates the element into the database and returns boolean to express if the operation was 
	 * successful.
	 */
	public boolean createElement(Object element);	
	/**
	 * Updates the element data in the database.
	 */
	public void updateElement(Object element);	
	/**
	 * Gets the Book object b from database for which b.getId() == bookId.
	 *  
	 * @param bookId The database id of the book
	 * @return The book object or null if the book doesn't exist 
	 */
	public Book getBook(int bookId);	
	/**
	 * Gets the Course object c from database for which c.getId() == courseId.
	 *  
	 * @param courseId The database id of the course
	 * @return The course object or null if the course doesn't exist 
	 */
	public Course getCourse(int courseId);
	/**
	 * Gets all courses that the teacher with the given database id is teacher in.
	 * @param teacherId The database id of the teacher
	 * @return A list of courses
	 */
	public Set<Course> getCoursesByTeacherId(int teacherId);
	/**
	 * Gets the timestamp of the latest solution submitted by a user in a course.
	 * @param courseId The database id of the course
	 * @param userId The database id of user
	 * @return The timestamp as a String
	 */
	public String getNewestSolution(int courseId, int userId);
	/**
	 * Gets timestamp when a Comment was last submitted for a course.
	 * @param courseId The database id of the course
	 * @return The timestamp as a String
	 */
	public String getNewestCommentInfo(int courseId);
	/**
	 * Gets timestamp when an AddedContent was last submitted for a course.
	 * @param courseId The database id of the course
	 * @return The timestamp as a String
	 */
	public String getNewestAddedContentInfo(int courseId);
	/**
	 * Gets the database id of a User by userHash.
	 * @param userHash The userHash of the User
	 * @return Database id
	 */
	public int getUserId(String userHash);
	/**
	 * Gets the User by userHash.
	 * @param userHash The userHash of the User
	 * @return The User object
	 */
	public User getUserByUserHash(String userHash);
	/**
	 * Returns content added to the db after the lastUpdateTime. What
	 **/
	public List<AddedContent> getAddedContents(Course course, User user,
			Date lastUpdateTime);
	/**
	 * Updates the data of an element if it already exists or else creates a
	 * new element into the database
	 * @param element The element that is being saved or updated
	 */
	public void saveOrUpdateElement(Object element);
	/**
	 * Adds a new Lock to the Course for the User and returns the lock id.
	 * @param course The course that the lock is added for
	 * @param user The user who requested the lock
	 * @return Database id
	 */
	public int requestLock(Course course, User user);
	/**
	 * Removes the lock with the given data. 
	 * @param lockId The database id of the lock
	 * @param course The course that the lock is for
	 * @param user The user who requested the lock
	 * @return 1 if successful, else -1
	 */
	public int removeLock(String lockId, Course course, User user);
	/**
	 * Gets all AssignmentSolutions by User and Course that were received after 
	 * lastUpdateTime.
	 * @param course The course
	 * @param user The user
	 * @param lastUpdateTime Only newer solutions than this are being
	 * fetched. 
	 * @return A list of AssignmentSolutions
	 */
	public List<AssignmentSolution> getSolutions(Course course, User user,
			Date lastUpdateTime);
	/**
	 * Gets all Comments by User and Course that were received after 
	 * lastUpdateTime.
	 * @param course The course
	 * @param user The user
	 * @param lastUpdateTime Only newer comments than this are being
	 * fetched. 
	 * @return A list of Comments
	 */
	public List<Comment> getComments(Course course, User user,
			Date lastUpdateTime);
	/**
	 * Gets all SystemUpdates by User that were received after 
	 * lastUpdateTime.
	 * @param user The user
	 * @param lastUpdateTime Only newer SystemUpdates than this are being
	 * fetched. 
	 * @return A list of SystemUpdates
	 */
	public List<SystemUpdate> getSystemUpdates(User user, Date lastUpdateTime);
	/**
	 * Counts the students of the School with the schoolId and returns 
	 * the next number that can be assigned to a student.
	 * @param schoolId The id of the School
	 * @param gender 1 for boy, 2 for girl
	 * @return The number as a String with leading zero if number < 10
	 */
	public String getNextStudentNumber(int schoolId, int gender);
	/**
	 * Counts the teachers of the School with the schoolId and returns 
	 * the next number that can be assigned to a teacher.
	 * @param schoolId The id of the School
	 * @return The number as a String with leading zero if number < 10
	 */
	public String getNextTeacherNumber(int schoolId);
	/**
	 * Counts the schools of the country (i.e. 'FI') and returns 
	 * the next number that can be assigned to a school.
	 * @param country FI, EE, ...
	 * @return The next number
	 */
	public int getNextSchoolNumber(String country);
	/**
	 * Returns true, if user is teacher or student in the course, else returns
	 * false.
	 * @param user The user to check
	 * @param courseId The course id
	 * @return true/false
	 */
	public boolean isUserInCourse(User user, int courseId);
	/**
	 * Gets all BookUpdates, language specific BookUpdates or language 
	 * generic BookUpdates based on the lang parameter by date and course that were received after 
	 * lastUpdateTime. user is used to specify the role of the user.
	 * @param course The course
	 * @param user The User
	 * @param date Last update time
	 * @param lang null for generic updates, lang code for language specific 
	 * BookUpdates or the keyword 'all' for all BookUpdates
	 * @return A list of BookUpdates 
	 */
	public List<BookUpdate> getBookUpdates(Course course, User user, Date date, String lang);	
	/**
	 * Adds a new entry to the log that is being used to log unsuccesful 
	 * authentication attempts.
	 * @param type The type of request
	 * @param userId The id of the user if user with submitted userHash and 
	 * password exists
	 * @param courseId The id of the course
	 * @param data The content of the data parameter of the request
	 * @param ip The ip address of the client that sent the request
	 * @param extraParams Additional info
	 */
	public void addLogEntry(int type, int userId, int courseId, String data,
			String ip, String extraParams);
	/**
	 * Adds a new entry to the traffic log that is used to log every received 
	 * request.
	 * @param userId The id of the user that made the request.
	 * @param ip The ip address of the client
	 * @param extraParams Additional info
	 */
	public void addTrafficLogEntry(int userId, String ip, String extraParams);	
	/**
	 * Gets an Exam by id.
	 * @param id Database id
	 * @return The Exam object or null if exam doesn't exist
	 */
	public Exam getExamById(int id);
	/**
	 * Gives the User with userId access to the Exam with examId until validUntil.
	 * @param userId The id of the User
	 * @param examId The id of the Exam
	 * @param validUntil The Date until which the Exam can be accessed by the
	 * User.
	 */
	public void addExamAccess(int userId, int examId, Date validUntil);
	/**
	 * Returns the ExamAccess on base of userId and examId  
	 * or null if it doesn't exist.
	 * @param userId The id of the User
	 * @param examId The id of the Exam
	 * @return ExamAccess object or null.
	 */
	public ExamAccess getExamAccess(int userId, int examId);
	/**
	 * Returns DummySystemUpdates for a dummy book.
	 * @param bookVersion Is the unique dummy book identifier
	 * @param updateType 0 for teacher book, 1 for student book
	 * @param lastUpdateTime The time when the book instance was updated last time
	 * @return A list of DummySystemUpdates.
	 */
	public List<DummySystemUpdate> getDummySystemUpdates(String bookVersion, int updateType, Date lastUpdateTime);

	/** Returns the SystemUpdate with the given title or null if there is no SystemUpdate
	 * with the given title.
	 * @param title The title attribute
	 * @return SystemUpdate || null
	 */
	public SystemUpdate getSystemUpdateByTitle(String title);
	
	/** Returns the BookUpdate with the given title or null if there is no BookUpdate
	 * with the given title.
	 * @param title The title attribute
	 * @return BookUpdate || null
	 */
	public BookUpdate getBookUpdateByTitle(String title, String lang);
}
