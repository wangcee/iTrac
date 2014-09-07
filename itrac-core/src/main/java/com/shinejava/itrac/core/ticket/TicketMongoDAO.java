/**
 * 
 */
package com.shinejava.itrac.core.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.shinejava.itrac.core.mongodb.BaseMongoDAO;
import com.shinejava.itrac.core.mongodb.MongoQueryException;
import com.shinejava.itrac.core.mongodb.MongoToEntityException;

/**
 * com.shinejava.itrac.core.ticket.TicketMongoDAO.java
 *
 * @author wangcee
 *
 * @version $Revision: 29218 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@Repository
public class TicketMongoDAO extends BaseMongoDAO {
	
	private static final BasicDBObject REPORT_RETURN_COLUMNS = new BasicDBObject()
			.append("title", 1)
			.append("externalUrl", 1)
			.append("authorId", 1)
			.append("assigned", 1)
			.append("assistBy", 1)
			.append("status", 1)
			.append("priority", 1)
			.append("updateDate", 1);

	/* (non-Javadoc)
	 * @see com.shinejava.itrac.core.mongodb.BaseMongoDAO#collectionTable()
	 */
	@Override
	protected String collectionTable() {
		return "ittickets";
	}
	
	private TicketEntity toEntity(String jsonData) {
		TicketEntity entity = null;
		if (StringUtils.isBlank(jsonData)) {
			return entity;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			entity = objectMapper.readValue(jsonData, TicketEntity.class);
		} catch (Exception e) {
			throw new MongoToEntityException(String.format(
					"Exception: convert Bson %s \n\r to %s", jsonData,
					"TicketEntity.class"), e);
		}
		return entity;
	}
	
	public boolean insert(TicketEntity entity) throws MongoQueryException {
		if (entity == null || 
				StringUtils.isBlank(entity.getId()) || 
				StringUtils.isBlank(entity.getAuthorId())) {
			return false;
		}
		
		String assigned = entity.getAssigned();
		if (StringUtils.isBlank(assigned)) {
			assigned = TicketEntity.ASSIGNED_UNKNOWN;
		}

		BasicDBObject dbObj = new BasicDBObject().append("_id", entity.getId())
				.append("authorId", entity.getAuthorId())
				.append("assigned", assigned)
				.append("status", entity.getStatus())
				.append("priority", entity.getPriority())
				.append("title", entity.getTitle())
				.append("createDate", entity.getCreateDate())
				.append("updateDate", entity.getUpdateDate());
		
		if (StringUtils.isNotBlank(entity.getAssistBy())) {
			dbObj.append("assistBy", entity.getAssistBy());
		}
		
		if (StringUtils.isNotBlank(entity.getDescription())) {
			dbObj.append("description", entity.getDescription());
		}

		if (StringUtils.isNotBlank(entity.getExternalUrl())) {
			dbObj.append("externalUrl", entity.getExternalUrl());
		}
				
		return insert(dbObj);
	}
	
	public TicketEntity findById(String id) {
		return findOneBy("_id", id);
	}
	
	protected TicketEntity findOneBy(String property, String value) {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put(property, value);
		String json = findOneJson(queryParams, null, false);

		return toEntity(json);
	}
	
	public boolean updateStatus(TicketEntity entity) {
		if (entity == null || StringUtils.isBlank(entity.getId())) {
			return false;
		}
		
		DBObject query = new BasicDBObject("_id", entity.getId());
		
		BasicDBObject update = new BasicDBObject("assigned", entity.getAssigned())
				.append("status", entity.getStatus())
				.append("priority", entity.getPriority())
				.append("updateDate", entity.getUpdateDate());
		
		if (StringUtils.isNotBlank(entity.getAssistBy())) {
			update.append("assistBy", entity.getAssistBy());
		}
		
		return updateOne(query, new BasicDBObject("$set", update));
	}
	
	public boolean updateTitle(String id, String title) {
		if (StringUtils.isBlank(id)) {
			return false;
		}
		
		DBObject query = new BasicDBObject("_id", id);
		
		BasicDBObject update = new BasicDBObject("title", title);
		
		return updateOne(query, new BasicDBObject("$set", update));
	}
	
	public boolean updateUpdateDate(String ticketId, int updateDate) {
		if (StringUtils.isBlank(ticketId)) {
			return false;
		}
		
		DBObject query = new BasicDBObject("_id", ticketId);
		
		DBObject update = new BasicDBObject("updateDate", updateDate);
		
		return updateOne(query, new BasicDBObject("$set", update));
	}
	
	public boolean addComment(String ticketId, TicketCommentEntity commentEntity) {
		if (StringUtils.isBlank(ticketId) || 
				commentEntity == null || 
				StringUtils.isBlank(commentEntity.getUserId())) {
			return false;
		}
		
		BasicDBObject comment = new BasicDBObject("userId", commentEntity.getUserId())
				.append("content", commentEntity.getContent())
				.append("createDate", commentEntity.getCreateDate())
				.append("createTime", commentEntity.getCreateTime());

		DBCollection coll = mongoTemplate.getCollection(collectionTable());
		
        WriteResult result = coll.update(new BasicDBObject("_id", ticketId),
                new BasicDBObject("$push", new BasicDBObject("comments", comment)), 
                false, 
                false);
		
        return result.getN() > 0;
	}

	public List<TicketEntity> getByStatusAndAssistBy(String status, String userId, int skip, int limit) {
		
		BasicDBObject query = new BasicDBObject();
		query.append("status", status);
		query.append("assistBy", userId);
		
		BasicDBObject order = new BasicDBObject();
		order.append("priority", -1);
		order.append("updateDate", -1);
		
		List<String> bsonList = findJsonList(
				query, 
				order, 
				REPORT_RETURN_COLUMNS, 
				skip, 
				limit);
		
		List<TicketEntity> ticketList = new ArrayList<TicketEntity>();
		if (bsonList == null || bsonList.isEmpty()) {
			return ticketList;
		}
		for (String bson : bsonList) {
			TicketEntity entity = toEntity(bson);
			ticketList.add(entity);
		}
		return ticketList;
	}
	
	public List<TicketEntity> getByStatusAndAssistBy(String[] statusArray, String userId, int skip, int limit, boolean sortByPriority) {
		if (statusArray == null || statusArray.length <= 1) {
			return null;
		}
		
		BasicDBList statusParams = new BasicDBList();
		for (String status : statusArray) {
			statusParams.add(new BasicDBObject("status", status));
		}
		
		BasicDBObject query = new BasicDBObject();
		query.put("assistBy", userId);
		query.put("$or", statusParams);
		
		BasicDBObject order = new BasicDBObject();
		if (sortByPriority) {
			order.append("priority", -1);
			order.append("updateDate", -1);
		} else {
			order.append("updateDate", -1);
			order.append("priority", -1);
		}
		
		List<String> bsonList = findJsonList(
				query, 
				order, 
				REPORT_RETURN_COLUMNS, 
				skip, 
				limit);
		
		List<TicketEntity> ticketList = new ArrayList<TicketEntity>();
		if (bsonList == null || bsonList.isEmpty()) {
			return ticketList;
		}
		for (String bson : bsonList) {
			TicketEntity entity = toEntity(bson);
			ticketList.add(entity);
		}
		return ticketList;
	}
	
	public List<TicketEntity> getByStatusAndAssigned(String status, String userId, int skip, int limit, boolean sortByPriority) {
		
//		BasicDBObject returnColumns = new BasicDBObject()
//				.append("title", 1)
//				.append("externalUrl", 1)
//				.append("authorId", 1)
//				.append("assigned", 1)
//				.append("assistBy", 1)
//				.append("status", 1)
//				.append("priority", 1)
//				.append("updateDate", 1);
		
		BasicDBObject query = new BasicDBObject();
		query.append("status", status);
		
		if (StringUtils.isNotBlank(userId)) {
			query.append("assigned", userId);
		}
		
		BasicDBObject order = new BasicDBObject();
		if (sortByPriority) {
			order.append("priority", -1);
			order.append("updateDate", -1);
		} else {
			order.append("updateDate", -1);
			order.append("priority", -1);
		}
		
		List<String> bsonList = findJsonList(
				query, 
				order, 
				REPORT_RETURN_COLUMNS, 
				skip, 
				limit);
		
		List<TicketEntity> ticketList = new ArrayList<TicketEntity>();
		if (bsonList == null || bsonList.isEmpty()) {
			return ticketList;
		}
		for (String bson : bsonList) {
			TicketEntity entity = toEntity(bson);
			ticketList.add(entity);
		}
		return ticketList;
	}
	
	public List<TicketEntity> getByCommentAuthor(String commentAuthor, int startDate, int endDate, int skip, int limit) {
		
		BasicDBObject query = new BasicDBObject();
		query.append("comments.userId", commentAuthor);
		query.append("comments.createDate", new BasicDBObject("$gte", startDate).append("$lte", endDate));
		
		BasicDBObject order = new BasicDBObject();
		order.append("comments.createDate", -1);
		order.append("comments.createTime", -1);
		order.append("priority", -1);
		
		List<String> bsonList = findJsonList(
				query, 
				order, 
				REPORT_RETURN_COLUMNS, 
				skip, 
				limit);
		
		List<TicketEntity> ticketList = new ArrayList<TicketEntity>();
		if (bsonList == null || bsonList.isEmpty()) {
			return ticketList;
		}
		for (String bson : bsonList) {
			TicketEntity entity = toEntity(bson);
			ticketList.add(entity);
		}
		return ticketList;
	}
	
}
