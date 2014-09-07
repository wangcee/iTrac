/**
 * 
 */
package com.shinejava.itrac.core.user;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * com.shinejava.itrac.core.user.UserManager.java
 *
 * @author wangcee
 *
 * @version $Revision: 28804 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@Component
public class UserManager {

	@Autowired
	private UserMongoDAO userMongoDAO;
	
	public UserEntity getUserById(String id) {
		return userMongoDAO.findById(id);
	}
	
	public UserEntity getUserByNamePassword(String name, String password) {
		return userMongoDAO.findByName(name, password);
	}
	
	public List<UserEntity> getAllUsers() {
		return userMongoDAO.findAllUsers();
	}
	
	public UserEntity createUser(String name, String password, String email, String role) {
		UserEntity user = new UserEntity();
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		user.setRole(role);
		return createUser(user);
	}
	
	public UserEntity createUser(UserEntity userEntity) {
		if (userEntity == null) {
			return null;
		}
		String id = generateId(userEntity.getName());
		if (StringUtils.isBlank(id)) {
			return null;
		}
		
		userEntity.setId(id);
		
		if (StringUtils.isBlank(userEntity.getPassword())) {
			userEntity.setPassword(UserEntity.PASSWORD_DEFAULT);
		}
		
		userMongoDAO.insert(userEntity);
		return userEntity;
	}
	
	public boolean update(UserEntity userEntity) {
		if (userEntity == null || StringUtils.isBlank(userEntity.getId())) {
			return false;
		}
		return userMongoDAO.update(userEntity);
	}
	
	public boolean removeUser(String id) {
		return userMongoDAO.remove(id);
	}
	
	public static String generateId(String userName) {
		if (StringUtils.isBlank(userName)) {
			return null;
		}
		userName = StringUtils.lowerCase(StringUtils.trim(userName));
		userName = userName.replaceAll("[^0-9a-zA-Z]+", "-");
		if (StringUtils.isNotBlank(userName)) {
			userName = userName.replaceAll("-+", "-");
		}
		if (StringUtils.isNotBlank(userName)) {
			//The result only contains A..Z a..z 0..9 and - 
			userName = userName.replaceAll("[^A-Za-z0-9\\-]", "");
		}
		return userName;
	}
}
