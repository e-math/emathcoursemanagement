package org.emath.model.communication;

/**
 * This class represents a user instance.
 *
 */
public class User {
	/**
	 * Teacher role.
	 */
	public static final int TEACHER = 1;
	/**
	 * Student role.
	 */
	public static final int STUDENT = 2;
	/**
	 * Admin role.
	 */
	public static final int ADMIN = 3;
	/**
	 * Male gender.
	 */
	public static final int BOY = 1;
	/**
	 * Female gender.
	 */
	public static final int GIRL = 2;
	/**
	 * Full type that can create new students if user.getRole() == User.TEACHER
	 */
	public static final int FULL = 1;
	/**
	 * Demo type that receives only DummySystemUpdates.
	 */
	public static final int DEMO = 2;
	/**
	 * Type that means, that this user is not allowed to create new students and should
	 * have no student management system. The student management system depends on the e-book 
	 * and is not managed by the server-side.
	 */
	public static final int NOSTUDENTMANAGEMENT = 3;

	private int id;
	/**
	 * The type of user.
	 */
	private int type;
	/**
	 * The userHash of the user.
	 */
	private String userHash;
	/**
	 * Name of the user. This field is optional.
	 */
	private String name;
	/**
	 * E-mail of the user. This field is optional.
	 */
	private String email;
	/**
	 * Institution of the user. This field is optional.
	 */
	private String institution;
	/**
	 * Role of the user.
	 */
	private int role;
	/**
	 * Gender of the user.
	 */
	private int gender;
	/**
	 * The id of the school that this user is an student/teacher of. Also admin needs to have 
	 * a schoolId.
	 */
	private int schoolId;
	/**
	 * The password of this user.
	 */
	private String password;
	/**
	 * Disabled users always fail the user validation.
	 */
	private int disabled;

	public User() {
		this.type = User.FULL;
	}

	public User(int id, String name, String institution) {
		this.id = id;
		this.name = name;
		this.institution = institution;
		this.type = User.FULL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserHash() {
		return userHash;
	}

	public void setUserHash(String userHash) {
		this.userHash = userHash;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
