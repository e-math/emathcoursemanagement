package org.emath.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.emath.model.book.AddedContent;
import org.emath.model.book.AssignmentSolution;
import org.emath.model.book.Book;
import org.emath.model.book.BookUpdate;
import org.emath.model.book.Comment;
import org.emath.model.communication.Backup;
import org.emath.model.communication.Course;
import org.emath.model.communication.Exam;
import org.emath.model.communication.ExamAccess;
import org.emath.model.communication.ExamSolution;
import org.emath.model.communication.History;
import org.emath.model.communication.LogEntry;
import org.emath.model.communication.School;
import org.emath.model.communication.SystemUpdate;
import org.emath.model.communication.User;
import org.emath.persistence.hibernate.DatabaseFacade;
import org.emath.service.ServiceImpl;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

/**
 * The methods of this class are used to test the service layer.
 *
 */
public class ServiceTests extends TestCase {
	
	private DatabaseFacade mockDb;
	private ServiceImpl service;

	@Before
	protected void setUp() throws Exception {
		mockDb = createStrictMock(DatabaseFacade.class);
		service = new ServiceImpl(mockDb);
	}
	
	@Test
	public void testIsValidUserWithValidUsernameAndInvalidPassword(){
		User u = new User();
		u.setUserHash("qwerty");
		u.setPassword("pass");
		
		expect(mockDb.getUserByUserHash("qwerty")).andReturn(u);
		
		mockDb.addLogEntry(LogEntry.USER_VALIDATION_FAILED, -1, -1, "",
				"1.1.1.1", "username: " + u.getUserHash() + "userkey: " + "wrongpassword"
				+ " courseid: " + "C01" + " user-agent: " );
		expectLastCall().once();
		replay(mockDb);
        assertNull(service.isValidUser(u.getUserHash(), "wrongpassword", "C01", "1.1.1.1",""));
        verify(mockDb);
		
	}
	
	@Test
	public void testIsValidUserWithInvalidUsername(){
		User u = new User();
		u.setUserHash("qwerty");
		u.setPassword("pass");
		
		expect(mockDb.getUserByUserHash("wrong")).andReturn(null);
		
		mockDb.addLogEntry(LogEntry.USER_VALIDATION_FAILED, -1, -1, "",
				"1.1.1.1", "username: " + "wrong" + "userkey: " + u.getPassword()
				+ " courseid: " + "C01" + " user-agent: " );
		expectLastCall().once();
		replay(mockDb);
        assertNull(service.isValidUser("wrong", u.getPassword(), "C01", "1.1.1.1",""));
        verify(mockDb);
		
	}
	
	
	
	@Test
	public void testIsValidUserWithValidAdminCredentialsWithoutCourse(){
		User u = new User();
		u.setUserHash("qwerty");
		u.setPassword("pass");
		u.setRole(User.ADMIN);
		
		expect(mockDb.getUserByUserHash("qwerty")).andReturn(u);
		replay(mockDb);
        assertEquals(u, service.isValidUser("qwerty", "pass", "-", "-",""));
        verify(mockDb);
		
	}
	
	@Test
	public void testIsUserInCourseWithValidTeacher(){
		User u = new User();
		u.setId(15);
		u.setUserHash("qwerty");
		u.setPassword("pass");
		u.setRole(User.TEACHER);
		School s = new School();
		s.setId(1);
		Course c = new Course();
		c.setSchool(s);
		c.addTeacher(u);
		expect(mockDb.getCourse(10)).andReturn(c);
		replay(mockDb);
        assertEquals(c, service.isUserInCourse(u,"C10", "1.1.1.1", "Mojiraaa"));
		
	}
	
	@Test
	public void testIsUserInCourseWithValidStudent(){
		User u = new User();
		u.setId(15);
		u.setUserHash("qwerty");
		u.setPassword("pass");
		u.setRole(User.STUDENT);
		School s = new School();
		s.setId(1);
		Course c = new Course();
		c.setSchool(s);
		c.addStudent(u);
		expect(mockDb.getCourse(10)).andReturn(c);
		replay(mockDb);
        assertEquals(c, service.isUserInCourse(u,"C10", "1.1.1.1", "Mojiraaa"));
		
	}
	
	
	
	@Test
	public void testIsUserInCourseWithStudentThatIsNotInCourse(){
		User u = new User();
		u.setId(15);
		u.setUserHash("qwerty");
		u.setPassword("pass");
		u.setRole(User.STUDENT);
		School s = new School();
		s.setId(1);
		Course c = new Course();
		c.setId(10);
		c.setSchool(s);
		expect(mockDb.getCourse(10)).andReturn(c);
		mockDb.addLogEntry(LogEntry.COURSE_VALIDATION_FAILED, u.getId(), 10,
				"", "1.1.1.1", "username: " + u.getUserHash() + "userkey: "
						+ u.getPassword() + "courseid: " + "C10" + " user-agent: Mojiraaa");
		expectLastCall().once();
		replay(mockDb);
        service.isUserInCourse(u,"C10", "1.1.1.1", "Mojiraaa");
        verify(mockDb);
		
	}
	
	@Test
	public void testIsUserInCourseWithTeacherThatIsNotInCourse(){
		User u = new User();
		u.setId(15);
		u.setUserHash("qwerty");
		u.setPassword("pass");
		u.setRole(User.TEACHER);
		School s = new School();
		s.setId(1);
		Course c = new Course();
		c.setId(10);
		c.setSchool(s);
		expect(mockDb.getCourse(10)).andReturn(c);
		mockDb.addLogEntry(LogEntry.COURSE_VALIDATION_FAILED, u.getId(), 10,
				"", "1.1.1.1", "username: " + u.getUserHash() + "userkey: "
						+ u.getPassword() + "courseid: " + "C10" + " user-agent: Mojiraaa");
		expectLastCall().once();
		replay(mockDb);
        service.isUserInCourse(u,"C10", "1.1.1.1", "Mojiraaa");
        verify(mockDb);
		
	}
	
	@Test
	public void testIsUserInCourseWithInvalidCourseId(){
		User u = new User();
		u.setId(15);
		u.setUserHash("qwerty");
		u.setPassword("pass");
		u.setRole(User.STUDENT);
		School s = new School();
		s.setId(2);
		Course c = new Course();
		c.setId(10);
		c.setSchool(s);
		c.addStudent(u);
		expect(mockDb.getCourse(10)).andReturn(c);
		mockDb.addLogEntry(LogEntry.COURSE_VALIDATION_FAILED, u.getId(), 10,
				"", "1.1.1.1", "username: " + u.getUserHash() + "userkey: "
						+ u.getPassword() + "courseid: " + "U10" + " user-agent: Mojiraaa");
		expectLastCall().once();
		replay(mockDb);
        service.isUserInCourse(u,"U10", "1.1.1.1", "Mojiraaa");
        verify(mockDb);
		
	}
	
	
	@Test
	public void testSaveBookUpdateWithNonAdminUser(){
		User u = new User();
		u.setRole(User.TEACHER);
		assertEquals(-1, service.saveBookUpdate(u, "text title=\"ada\" some more text", "", "", "", null));
	}
	
	@Test
	public void testSaveBookUpdateWithAdminAndExistingUpdate(){
		User u = new User();
		u.setRole(User.ADMIN);
		u.setId(100);
		
		expect(mockDb.getBookUpdateByTitle(eq("ada"), eq("fi"))).andReturn(null);
		mockDb.saveOrUpdateElement(isA(BookUpdate.class));
		expectLastCall().once();
		replay(mockDb);
		service.saveBookUpdate(u, "text title=\"ada\" some more text", "20", "1", "clientIp", "fi");
		verify(mockDb);
	}
	
	@Test
	public void testSaveBookUpdateWithAdminAndNonExistingUpdate(){
		User u = new User();
		u.setRole(User.ADMIN);
		u.setId(100);
		String lang = null;
		expect(mockDb.getBookUpdateByTitle(eq("ada"), eq(lang))).andReturn(null);
		mockDb.saveOrUpdateElement(isA(BookUpdate.class));
		expectLastCall().once();
		replay(mockDb);
		//"title=" must be in the data because else addDBIdtoTiddlerData fails
		service.saveBookUpdate(u, "text title=\"ada\" some more text", "20", "1", "clientIp", lang);
		verify(mockDb);
	}
	
	@Test
	public void testAddCourseWithWrongSchoolId(){
		expect(mockDb.getSchoolById(100)).andReturn(null);
		replay(mockDb);
		assertEquals(-1, service.addCourse(100, "", ""));
		verify(mockDb);
	}
	
	@Test
	public void testAddCourseWithWrongTeachersText(){
		School s = new School();
		User u = new User();
		expect(mockDb.getSchoolById(100)).andReturn(s);
		expect(mockDb.getTeacherById(1)).andReturn(u);
		replay(mockDb);
		assertEquals(-1, service.addCourse(100, "1,adad", ""));
		verify(mockDb);
	}
	
	@Test
	public void testAddCourseWithWrongBooksText(){
		School s = new School();
		User u = new User();
		Book b = new Book();
		expect(mockDb.getSchoolById(100)).andReturn(s);
		expect(mockDb.getTeacherById(1)).andReturn(u);
		expect(mockDb.getTeacherById(2)).andReturn(u);
		expect(mockDb.getBook(1)).andReturn(b);
		expect(mockDb.getBook(3)).andReturn(b);
		replay(mockDb);
		assertEquals(-1, service.addCourse(100, "1,2", "1,3,dad"));
		verify(mockDb);
	}
	
	@Test
	public void testAddCourseWithValidData(){
		School s = new School();
		User u = new User();
		Book b = new Book();
		expect(mockDb.getSchoolById(100)).andReturn(s);
		expect(mockDb.getTeacherById(1)).andReturn(u);
		expect(mockDb.getTeacherById(2)).andReturn(u);
		expect(mockDb.getBook(1)).andReturn(b);
		expect(mockDb.getBook(3)).andReturn(b);
		expect(mockDb.createElement(isA(Course.class))).andReturn(true);
		replay(mockDb);
		service.addCourse(100, "1,2", "1,3");
		verify(mockDb);
	}
	
	
	
	@Test 
	public void testGetCourseWithValidId(){
		Course result = new Course();
		expect(mockDb.getCourse(13)).andReturn(result);
		replay(mockDb);
        assertSame(result, service.getCourse(13));
        verify(mockDb);
	}
	
	@Test 
	public void testGetCourseWithInvalidId(){
		expect(mockDb.getCourse(13)).andReturn(null);
		replay(mockDb);
        assertNull(service.getCourse(13));
        verify(mockDb);
	}
	
	@Test
	public void testAddTeacherWithValidArguments(){
		School s = new School();
		s.setCountry("FI");
		s.setSchool(13);
		expect(mockDb.getSchoolById(13)).andReturn(s);
		expect(mockDb.getNextTeacherNumber(13)).andReturn("15");
		expect(mockDb.createElement(isA(User.class))).andReturn(true);
		replay(mockDb);
		User u = service.addTeacher("jim", "jim@example.com", 13);
		assertEquals(u.getRole(), User.TEACHER);
		assertEquals(u.getSchoolId(), s.getSchool());
		assertEquals(u.getName(), "jim");
		assertEquals(u.getEmail(), "jim@example.com");
		assertEquals(u.getUserHash(), "FI13T15");
		verify(mockDb);
	}
	
	@Test
	public void testAddTeacherWithInvalidSchool(){
		expect(mockDb.getSchoolById(13)).andReturn(null);
		replay(mockDb);
		assertNull(service.addTeacher("jim", "jim@example.com", 13));
		verify(mockDb);
	}
	
	@Test
	public void testAddBookWithNullTitle(){
		assertNull(service.addBook(null));
	}
	
	@Test
	public void testAddBookWithEmptyTitle(){
		assertNull(service.addBook(""));
	}
	
	@Test
	public void testAddBookWithValidTitle(){
		expect(mockDb.createElement(isA(Book.class))).andReturn(true);
	}
	
	@Test
	public void testAddSchoolWithValidArguments(){
		expect(mockDb.getNextSchoolNumber("FI")).andReturn(13);
		expect(mockDb.createElement(isA(School.class))).andReturn(true);
		replay(mockDb);
		School s = service.addSchool("FI", "schoolname");
		assertEquals("FI", s.getCountry());
		assertEquals(13, s.getSchool());
		assertEquals("schoolname", s.getName());
		verify(mockDb);
		
	}
	
	@Test
	public void testAddSchoolWithNullCountry(){
		assertNull(service.addSchool(null, "schoolname"));
	}
	
	@Test
	public void testAddSchoolWithEmptyCountry(){
		assertNull(service.addSchool("", "schoolname"));
	}
	
	@Test
	public void testAddSchoolWithNullName(){
		assertNull(service.addSchool("FI", null));
	}
	
	@Test
	public void testAddSchoolWithEmptyName(){
		assertNull(service.addSchool("FI", ""));
	}
	
	@Test
	public void testSaveSystemUpdateWithExistingAndValidUpdate(){
		User u = new User();
		u.setRole(User.ADMIN);
		SystemUpdate s = new SystemUpdate();
		s.setId(50);
		String title = "text title=\"thistitle\" some more text";
		expect(mockDb.getSystemUpdateByTitle("thistitle")).andReturn(s);
		mockDb.saveOrUpdateElement(isA(SystemUpdate.class) );
		expectLastCall().once();
		replay(mockDb);
		assertEquals(50, service.saveSystemUpdate(u, title, "5", "1.1.1.1"));
		verify(mockDb);
	}
	
	@Test
	public void testSaveSystemUpdateWithNewAndValidUpdate(){
		User u = new User();
		u.setRole(User.ADMIN);
		String title = "text title=\"thistitle\" some more text";
		expect(mockDb.getSystemUpdateByTitle("thistitle")).andReturn(null);
		//expect(mockDb.createElement(isA(SystemUpdate.class))).andReturn(true);
		mockDb.saveOrUpdateElement(isA(SystemUpdate.class));
		expectLastCall().once();
		replay(mockDb);
		//"title=" must be in the data because else addDBIdtoTiddlerData fails
		assertEquals(0, service.saveSystemUpdate(u, title, "5", "1.1.1.1"));
		verify(mockDb);
	}
	
	@Test
	public void testSaveSystemUpdateWithNonAdminUser(){
		User u = new User();
		u.setRole(User.TEACHER);
		String title = "text title=\"thistitle\" some more text";
		assertEquals(-1, service.saveSystemUpdate(u, title, "5", "1.1.1.1"));
	}
	
	@Test
	public void testGetSystemUpdatesWithValidParameters(){
		User u = new User();
		List<SystemUpdate> l = new ArrayList<SystemUpdate>();
		expect(mockDb.getSystemUpdates(eq(u), isA(Date.class))).andReturn(l);
		replay(mockDb);
		List<SystemUpdate> l2 = service.getSystemUpdates(u, "0", "0");
		assertEquals(l, l2);
		verify(mockDb);
	
	}
	
	@Test
	public void testSaveSolutionWithExistingSolution(){
		User u = new User();
		u.setUserHash("EE7B01");
		Course c = new Course();
		c.setId(13);
		mockDb.saveOrUpdateElement(isA(AssignmentSolution.class));
		expectLastCall().once();
		expect(mockDb.createElement(isA(Backup.class))).andReturn(true);
		replay(mockDb);
		service.saveSolution(u, c, " title=\"C" + c.getId() + "_" + u.getUserHash() + "_" + 
		"someassignment\"", 
				"1.1.1.1", 1, "13");	
		verify(mockDb);
	}
	
	@Test
	public void testSaveSolutionWithDoubleTitle(){
		User u = new User();
		u.setId(12);
		u.setUserHash("EE7B01");
		Course c = new Course();
		c.setId(13);
		String data = " title=\"C" + c.getId() + "_" + u.getUserHash() + "_" + 
				" title=\"C" + c.getId() + "_" + u.getUserHash() + "_";
		String ip = "1.1.1.1";
		mockDb.addLogEntry(LogEntry.ILLEGAL_DATA_SUBMITTED, 12, 13, data, ip, "");
		expectLastCall().once();
		replay(mockDb);
		service.saveSolution(u, c, data, 
				ip, 1, "13");	
		verify(mockDb);
	}
	
	@Test
	public void testSaveSolutionWithNewSolution(){
		User u = new User();
		u.setUserHash("ee7B01");
		Course c = new Course();
		c.setId(13);
		expect(mockDb.createElement(isA(AssignmentSolution.class))).andReturn(true);
		mockDb.saveOrUpdateElement(isA(AssignmentSolution.class));
		expectLastCall().once();
		expect(mockDb.createElement(isA(Backup.class))).andReturn(true);
		replay(mockDb);
		service.saveSolution(u, c, " title=\"C" + c.getId() + "_" + u.getUserHash() + "_"  + 
				"someassignment\"", "1.1.1.1", 1, null);	
		verify(mockDb);
	}
	
	@Test
	public void testSaveSolutionWithSolutionWithWrongTitle(){
		User u = new User();
		u.setUserHash("FI3B01");
		Course c = new Course();
		c.setId(15);
		assertEquals(-2, service.saveSolution(u, c, " title=\"C" + (c.getId() + 1) + "_" + u.getUserHash() + "_" + 
		"someassignment\"", "1.1.1.1", 1, "13"));	
		
	}
	
	@Test
	public void testSaveAddedContentWithNewAddedContent(){
		User u = new User();
		u.setRole(User.TEACHER);
		Course c = new Course();
		expect(mockDb.createElement(isA(AddedContent.class))).andReturn(true);
		mockDb.saveOrUpdateElement(isA(AddedContent.class));
		expectLastCall().once();
		expect(mockDb.createElement(isA(Backup.class))).andReturn(true);
		replay(mockDb);
		//"title=\"title\" must be in the data because else parseTiddlerTitle fails
		service.saveContent(u, c, "1.1.1.1", 1, null, null, "text title=\"sometitle\" more text");	
		verify(mockDb);
	}
	
	@Test
	public void testSaveAddedContentWithExistingAddedContent(){
		User u = new User();
		u.setRole(User.ADMIN);
		Course c = new Course();
		mockDb.saveOrUpdateElement(isA(AddedContent.class));
		expectLastCall().once();
		expect(mockDb.createElement(isA(Backup.class))).andReturn(true);
		replay(mockDb);
		//"title=\"title\" must be in the data because else parseTiddlerTitle fails
		service.saveContent(u, c, "1.1.1.1", 1, null, "13", "text title=\"sometitle\" more text");	
		verify(mockDb);
	}
	
	@Test
	public void testSaveAddedContentAsStudent(){
		User u = new User();
		u.setId(13);
		u.setRole(User.STUDENT);
		Course c = new Course();
		c.setId(5);
		String ip = "1.1.1.1";
		//"title=\"title\" must be in the data because else parseTiddlerTitle fails
		String data = "text title=\"sometitle\" more text";
		mockDb.addLogEntry(LogEntry.ILLEGAL_DATA_SUBMITTED, 13, 5, data, ip, "");
		expectLastCall().once();
		replay(mockDb);
		service.saveContent(u, c, ip, 1, null, "13", data);	
		verify(mockDb);
	}
	
	@Test
	public void testSaveCommentWithNewAddedComment(){
		User u = new User();
		Course c = new Course();
		expect(mockDb.getUserId("FI3B12")).andReturn(15);
		expect(mockDb.createElement(isA(Comment.class))).andReturn(true);
		mockDb.saveOrUpdateElement(isA(Comment.class));
		expectLastCall().once();
		expect(mockDb.createElement(isA(Backup.class))).andReturn(true);
		replay(mockDb);
		//"title=\"title\" must be in the data because else parseTiddlerTitle fails
		service.saveComment(u, c, "text title=\"sometitle\" more text", "1.1.1.1", "FI3B12", null);	
		verify(mockDb);
	}
	
	@Test
	public void testSaveCommentWithExistingAddedComment(){
		User u = new User();
		Course c = new Course();
		expect(mockDb.getUserId("FI3B12")).andReturn(15);
		mockDb.saveOrUpdateElement(isA(Comment.class));
		expectLastCall().once();
		expect(mockDb.createElement(isA(Backup.class))).andReturn(true);
		replay(mockDb);
		//"title=\"title\" must be in the data because else parseTiddlerTitle fails
		service.saveComment(u, c, "text title=\"sometitle\" more text", "1.1.1.1", "FI3B12", "22");	
		verify(mockDb);
	}
	
	
	
	@Test
	public void testSaveCommentWithNonExistingUserHash(){
		User u = new User();
		Course c = new Course();
		expect(mockDb.getUserId("FI3B12")).andReturn(-1);
		replay(mockDb);
		//"title=\"title\" must be in the data because else parseTiddlerTitle fails
		assertEquals(-1, service.saveComment(u, c, "text title=\"sometitle\" more text", "1.1.1.1", "FI3B12", "22"));	
		verify(mockDb);
	}
	
	
	
	@Test
	public void testGetUpdates(){
		User u = new User();
		Course c = new Course();
		
		expect(mockDb.getAddedContents(same(c), same(u),
				isA(Date.class))).andReturn(new ArrayList<AddedContent>());
		expect(mockDb.getSolutions(same(c), same(u), isA(Date.class))).andReturn(new ArrayList<AssignmentSolution>());
		expect(mockDb.getComments(same(c), same(u), isA(Date.class))).andReturn(new ArrayList<Comment>());
		expect(mockDb.getBookUpdates(same(c), same(u), isA(Date.class), eq("all"))).andReturn(new ArrayList<BookUpdate>());
		replay(mockDb);
		service.getUpdates(u, c, "0", "0");
		verify(mockDb);
	}
	
	@Test
	public void testGetSolutions(){
		User u = new User();
		Course c = new Course();
		List l = new ArrayList<AssignmentSolution>();
		expect(mockDb.getSolutions(same(c), same(u), isA(Date.class))).andReturn(l);
		replay(mockDb);
		assertEquals(l, service.getSolutions(u, c, "0", "0"));
		verify(mockDb);
	}
	
	@Test
	public void testRequestLock(){
		User u = new User();
		Course c = new Course();
		expect(mockDb.requestLock(c, u)).andReturn(15);
		replay(mockDb);
		assertEquals(15, service.requestLock(u, c));
		verify(mockDb);
	}
	
	@Test
	public void testRemoveLock(){
		User u = new User();
		Course c = new Course();
		String lockId = "12";
		expect(mockDb.removeLock(lockId, c, u)).andReturn(1);
		replay(mockDb);
		assertEquals(1, service.removeLock(u, c, lockId));
		verify(mockDb);
	}
	
	@Test
	public void testGenerateUsers(){
		int boys = 2;
		int girls = 2;
		Course c = new Course();
		School s = new School();
		s.setId(13);
		c.setSchool(s);
		String courseString = "C60";
		expect(mockDb.getCourse(60)).andReturn(c);
		expect(mockDb.getNextStudentNumber(13, User.BOY)).andReturn("12");
		expect(mockDb.createElement(isA(User.class))).andReturn(true);
		
		expect(mockDb.getCourse(60)).andReturn(c);
		expect(mockDb.getNextStudentNumber(13, User.BOY)).andReturn("13");
		expect(mockDb.createElement(isA(User.class))).andReturn(true);
		
		expect(mockDb.getCourse(60)).andReturn(c);
		expect(mockDb.getNextStudentNumber(13, User.GIRL)).andReturn("14");
		expect(mockDb.createElement(isA(User.class))).andReturn(true);
		
		expect(mockDb.getCourse(60)).andReturn(c);
		expect(mockDb.getNextStudentNumber(13, User.GIRL)).andReturn("15");
		expect(mockDb.createElement(isA(User.class))).andReturn(true);
		
		replay(mockDb);
		assertEquals(boys + girls, service.generateUsers(boys, girls, courseString).size());
		verify(mockDb);
		
	}
	
	@Test
	public void testGetCourseStudents(){
		Course c = new Course();
		Set<User> students = new LinkedHashSet<User>();
		students.add(new User());
		students.add(new User());
		c.setStudents(students);
		String courseString = "C60";
		expect(mockDb.getCourse(60)).andReturn(c);
		replay(mockDb);
		assertSame(students, service.getCourseStudents(courseString));
		verify(mockDb);
		
	}
	
	/* NOT TESTED YET!! */
	@Test
	public void testSaveCourse(){
		
	}
	
	@Test
	public void testAddTrafficLogEntry(){
		int userId = 13;
		String clientIp = "1.1.1.1";
		String requestInfo = "some content";
		mockDb.addTrafficLogEntry(userId, clientIp, requestInfo);
		expectLastCall().once();
		replay(mockDb);
		service.addTrafficLogEntry(userId, clientIp, requestInfo);
		verify(mockDb);
	}
	
	@Test
	public void testGetCourse(){
		int id = 15;
		Course c = new Course();
		expect(mockDb.getCourse(id)).andReturn(c);
		replay(mockDb);
		assertEquals(c, service.getCourse(id));
		verify(mockDb);
		
	}
	
	@Test
	public void testGrantExamAccess(){
		Exam e = new Exam();
		ExamAccess ea = new ExamAccess();
		expect(mockDb.getExamById(13)).andReturn(e);
		
		expect(mockDb.getExamAccess(15, 13)).andReturn(null);
		mockDb.addExamAccess(eq(15), eq(13), isA(Date.class));
		expectLastCall();
		
		expect(mockDb.getExamAccess(17, 13)).andReturn(ea);
		expect(mockDb.deleteElement(ea)).andReturn(true);
		mockDb.addExamAccess(eq(17), eq(13), isA(Date.class));
		expectLastCall();
		replay(mockDb);
		assertEquals(1, service.grantExamAccess(new int[]{15,17}, 13, 30));
		verify(mockDb);
	}
	
	@Test
	public void testGetExamWithoutPermission(){
		User u = new User();
		u.setId(13);
		Exam e = new Exam();
		e.setCourseId(15);
		e.setData("TEXT");
		Course c = new Course();
		
		expect(mockDb.getExamAccess(u.getId(), 19)).andReturn(null);
		expect(mockDb.getExamById(19)).andReturn(e);
		expect(mockDb.getCourse(15)).andReturn(c);
		replay(mockDb);
		Exam res = service.getExam(u, 19);
		assertSame(res, e);
		assertEquals(null, res.getData());
		verify(mockDb);

	}
	
	@Test
	public void testGetExamWithPermission(){
		User u = new User();
		u.setId(13);
		Exam e = new Exam();
		e.setCourseId(15);
		e.setData("TEXT");
		Course c = new Course();
		Date d = new Date();
		d = new Date(d.getTime() + 10000000);
		ExamAccess ea = new ExamAccess();
		ea.setValidUntil(d);
		
		expect(mockDb.getExamAccess(u.getId(), 19)).andReturn(ea);
		expect(mockDb.getExamById(19)).andReturn(e);
		expect(mockDb.getCourse(15)).andReturn(c);
		expect(mockDb.deleteElement(ea)).andReturn(true);
		replay(mockDb);
		Exam res = service.getExam(u, 19);
		assertSame(res, e);
		assertEquals("TEXT", res.getData());
		verify(mockDb);
		
	}
	
	@Test
	public void testGetExamWithTeacher(){
		User u = new User();
		u.setId(13);
		Exam e = new Exam();
		e.setCourseId(15);
		e.setData("TEXT");
		Course c = new Course();
		c.addTeacher(u);
				
		expect(mockDb.getExamAccess(u.getId(), 19)).andReturn(null);
		expect(mockDb.getExamById(19)).andReturn(e);
		expect(mockDb.getCourse(15)).andReturn(c);
		replay(mockDb);
		Exam res = service.getExam(u, 19);
		assertSame(res, e);
		assertEquals("TEXT", res.getData());
		verify(mockDb);
	}
	
	@Test
	public void testSaveExamSolution(){
		User u = new User();
		expect(mockDb.createElement(isA(ExamSolution.class))).andReturn(true);
		replay(mockDb);
		service.saveExamSolution(u, 13, "{some data}");
		verify(mockDb);
	}
	
	@Test
	public void testSaveHistory(){
		User u = new User();
		u.setId(150);
		
		expect(mockDb.createElement(isA(History.class))).andReturn(true);
		replay(mockDb);
		assertEquals(1, service.saveHistory(u, 13, 100, "[data]"));
		verify(mockDb);
	}
	
	
	
	
	
	

}
