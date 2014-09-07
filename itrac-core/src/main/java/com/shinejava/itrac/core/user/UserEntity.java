/**
 * 
 */
package com.shinejava.itrac.core.user;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * com.shinejava.itrac.core.user.UserEntity.java
 *
 * @author wangcee
 *
 * @version $Revision: 28970 $
 *          $Author: wangc@SHINETECHCHINA $
 */
public class UserEntity {
	
	//default user for ticket.assigned
	public static final String USER_UNKNOWN = "unknown";
	
	public static final String PASSWORD_DEFAULT = "shine99";
	
	@JsonProperty("_id")
	private String id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String role;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
