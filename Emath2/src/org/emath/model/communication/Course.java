package org.emath.model.communication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents a single course.
 */
public class Course {

	private int id;
	/**
	 * All students of this course.
	 */
	private Set<User> students = new LinkedHashSet<User>();
	/**
	 * The languages in which the book is used in this course or null id all languages that 
	 * are available in the book are also used in the course.
	 */
	private String bookLanguages; 
	/**
	 * The teachers of this course.
	 */
	private Set<User> teachers = new HashSet<User>();
	/**
	 * The books that are used in this course.
	 */
	private Set<ClientBook> books = new HashSet<ClientBook>();
	/**
	 * The school that holds this course.
	 */
	private School school;

	public Course() {

	}

	/*
	 * public ClientCourse(Course c) { this.id = c.getId();
	 * this.setName(c.getName()); this.setDescription(c.getDescription());
	 * Iterator<Book> i = c.getBooks().iterator(); while (i.hasNext()) {
	 * this.addBook(new ClientBook(i.next())); } Iterator<User> j =
	 * c.getUsers().iterator(); while (j.hasNext()) { this.addUser(new
	 * ClientUser(j.next())); }
	 * 
	 * }
	 */

	public Course(String json) {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<User> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<User> teachers) {
		this.teachers = teachers;
	}

	public Set<ClientBook> getBooks() {
		return books;
	}

	public void setBooks(Set<ClientBook> books) {
		this.books = books;
	}

	public Set<User> getStudents() {
		return students;
	}

	public void setStudents(Set<User> students) {
		this.students = students;
	}

	public void addStudent(User student) {
		this.students.add(student);
	}

	public boolean isStudentInCourse(int studentId) {
		Iterator<User> i = students.iterator();
		while (i.hasNext()) {
			if (i.next().getId() == studentId) {
				return true;
			}
		}
		return false;

	}

	public boolean isTeacherInCourse(int teacherId) {
		for (User user : teachers) {
			if (user.getId() == teacherId) {
				return true;
			}
		}

		return false;
	}

	public boolean isUserInCourse(int userId) {
		return isTeacherInCourse(userId) || isStudentInCourse(userId);
	}

	public List<Integer> getTeachersInCourseIds() {
		List<Integer> result = new ArrayList<Integer>();
		for (User teacher : teachers) {
			result.add(teacher.getId());
		}
		// List empty add -1 for the hibernate querys to work
		if (result.isEmpty()) {
			result.add(-1);
		}
		return result;
	}

	public List<Integer> getStudentsInCourseIds() {
		List<Integer> result = new ArrayList<Integer>();
		for (User student : students) {
			result.add(student.getId());
		}
		// List empty add -1 for the hibernate querys to work
		if (result.isEmpty()) {
			result.add(-1);
		}
		return result;
	}

	public List<Integer> getAllUsersInCourseIds() {
		List<Integer> result = getTeachersInCourseIds();
		result.addAll(getStudentsInCourseIds());
		return result;
	}

	public List<Integer> getBookIds() {
		List<Integer> result = new ArrayList<Integer>();
		for (ClientBook book : books) {
			result.add(book.getId());
		}
		return result;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public void printUsers() {
		for (User user : teachers) {
			System.out.println("TID: " + user.getId());
		}
		for (User user : students) {
			System.out.println("SID: " + user.getId());
		}
	}

	public void addBook(ClientBook book) {
		this.books.add(book);
	}

	public void addTeacher(User teacher) {
		this.teachers.add(teacher);
	}

	public String getBookLanguages() {
		return bookLanguages;
	}

	public void setBookLanguages(String bookLanguages) {
		this.bookLanguages = bookLanguages;
	}

}
