/**
 * 
 */
package com.shinejava.itrac.core.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.shinejava.itrac.core.mongodb.BaseMongoDAO;
import com.shinejava.itrac.core.mongodb.MongoQueryException;
import com.shinejava.itrac.core.mongodb.MongoToEntityException;

/**
 * com.shinejava.itrac.core.user.UserMongoDAO.java
 * 
 * @author wangcee
 * 
 * @version $Revision: 28804 $ $Author: wangc@SHINETECHCHINA $
 */
@Repository
public class UserMongoDAO extends BaseMongoDAO {

	@Override
	protected String collectionTable() {
		return "itusers";
	}

	private UserEntity toEntity(String jsonData) {
		UserEntity entity = null;
		if (StringUtils.isBlank(jsonData)) {
			return entity;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			entity = objectMapper.readValue(jsonData, UserEntity.class);
		} catch (Exception e) {
			throw new MongoToEntityException(String.format(
					"Exception: convert Bson %s \n\r to %s", jsonData,
					"UserEntity.class"), e);
		}
		return entity;
	}

	public boolean insert(UserEntity userEntity) {
		if (userEntity == null || 
				StringUtils.isBlank(userEntity.getId()) || 
				StringUtils.isBlank(userEntity.getName())) {
			return false;
		}

		DBObject dbObj = new BasicDBObject().append("_id", userEntity.getId())
				.append("name", userEntity.getName())
				.append("email", userEntity.getEmail())
				.append("password", userEntity.getPassword())
				.append("role", userEntity.getRole());

		return insert(dbObj);
	}
	
	public boolean update(UserEntity userEntity) {
		if (userEntity == null || 
				StringUtils.isBlank(userEntity.getId())) {
			return false;
		}
		
		DBObject query = new BasicDBObject("_id", userEntity.getId());
		
		DBObject update = new BasicDBObject("name", userEntity.getName())
				.append("email", userEntity.getEmail())
				.append("password", userEntity.getPassword())
				.append("role", userEntity.getRole());
		
		return updateOne(query, new BasicDBObject("$set", update));
	}
	
	protected UserEntity findOneBy(String property, String value)
			throws MongoQueryException, MongoToEntityException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put(property, value);
		String json = findOneJson(queryParams, null, false);

		return toEntity(json);
	}
	
	public UserEntity findByName(String name) {
		return findOneBy("name", name);
	}
	
	public UserEntity findById(String id) {
		return findOneBy("_id", id);
	}

	public UserEntity findByName(String name, String password)
			throws MongoQueryException, MongoToEntityException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", name);
		queryParams.put("password", password);
		String json = findOneJson(queryParams, null, false);

		return toEntity(json);
	}

	public boolean remove(String id) {
		if (StringUtils.isBlank(id)) {
			return false;
		}
		DBObject dbObj = new BasicDBObject("_id", id);
		return remove(dbObj);
	}

	public List<UserEntity> findAllUsers() {
		BasicDBObject returnColumns = new BasicDBObject()
				.append("name", 1)
				.append("email", 1)
				.append("role", 1);
		
		List<String> bsonList = findJsonList(null, 
				"name", 
				false, 
				returnColumns);
		
		List<UserEntity> userList = new ArrayList<UserEntity>();
		if (bsonList == null || bsonList.isEmpty()) {
			return userList;
		}
		for (String bson : bsonList) {
			UserEntity user = toEntity(bson);
			userList.add(user);
		}
		return userList;
	}
}
