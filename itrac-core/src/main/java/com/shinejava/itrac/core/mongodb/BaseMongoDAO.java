/**
 * 
 */
package com.shinejava.itrac.core.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * com.shinejava.itrac.core.mongodb.BaseMongoDAO.java
 * 
 * @author wangcee
 * 
 * @version $Revision: 28804 $ $Author: wangc@SHINETECHCHINA $
 */
public abstract class BaseMongoDAO {

	@Autowired
	protected MongoTemplate mongoTemplate;

	protected abstract String collectionTable();

	protected static BasicDBObject getQueryParmater(Map<String, Object> param) {
		BasicDBObject obj = new BasicDBObject();
		if (param == null || param.isEmpty()) {
			return obj;
		}
		for (String key : param.keySet()) {
			obj.put(key, param.get(key));
		}
		return obj;
	}

	protected boolean insert(DBObject dbObject) throws MongoQueryException {
		DBCollection coll = mongoTemplate.getCollection(collectionTable());

		try {
			coll.insert(dbObject);
			return true;
		} catch (MongoException.DuplicateKey e) {
			throw new MongoQueryException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param q search query for old object to update
     * @param o object with which to update <tt>q</tt>
     * @param upsert if the database should create the element if it does not exist
     * @param multi if the update should be applied to all objects matching (db version 1.1.3 and above)
     *              See http://www.mongodb.org/display/DOCS/Atomic+Operations
	 * @return
	 */
	protected boolean update(DBObject query, DBObject update, boolean upsert, boolean multi) {
		DBCollection coll = mongoTemplate.getCollection(collectionTable());
		
		try {
			coll.update(query, update, upsert, multi);
			return true;
		} catch (MongoException e) {
			throw new MongoQueryException(e.getMessage());
		}
	}
	
	protected boolean updateOne(DBObject query, DBObject update) {
		return update(query, update, false, false);
	}
	
	protected boolean remove(DBObject dbObject) {
		DBCollection coll = mongoTemplate.getCollection(collectionTable());
		coll.remove(dbObject);
		return true;
	}
	
	protected String findOneJson(Map<String, Object> queryParams,
			String orderBy, boolean orderDesc) throws MongoQueryException {

		BasicDBObject orderByObj = null;
		if (StringUtils.isNotBlank(orderBy)) {
			orderByObj = new BasicDBObject();
			if (orderDesc) {
				orderByObj.put(orderBy, -1);
			} else {
				orderByObj.put(orderBy, 1);
			}
		}

		BasicDBObject queryParamsObj = getQueryParmater(queryParams);

		DBCollection coll = mongoTemplate.getCollection(collectionTable());

		String json = null;
		DBCursor cursor = null;
		try {
			if (orderByObj == null) {
				cursor = coll.find(queryParamsObj).limit(1);
			} else {
				cursor = coll.find(queryParamsObj).sort(orderByObj).limit(1);
			}

			if (cursor.hasNext()) {
				DBObject doc = cursor.next();
				if (doc != null) {
					json = doc.toString();
				}
			}

		} catch (Exception e) {
			throw new MongoQueryException(e.getMessage(), e);
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}

		return json;
	}

	protected List<String> findJsonList(Map<String, Object> queryParams,
			String orderBy, boolean orderDesc) throws MongoQueryException {
		return findJsonList(queryParams, orderBy, orderDesc, null);
	}

	protected List<String> findJsonList(Map<String, Object> queryParams,
			String orderBy, boolean orderDesc, BasicDBObject returnColumns)
			throws MongoQueryException {

		List<DBObject> result = findDBObjectList(queryParams, orderBy,
				orderDesc, returnColumns);

		List<String> jsonList = new ArrayList<String>();
		if (result == null || result.isEmpty()) {
			return jsonList;
		}

		for (DBObject doc : result) {
			if (doc != null) {
				String json = doc.toString();
				jsonList.add(json);
			}
		}
		return jsonList;
	}

	/**
	 * 
	 * @param queryParams
	 * @param orderBy
	 * @param orderDesc
	 * @param returnColumns
	 *            (can be null), e.g. BasicDBObject returnColumns = new
	 *            BasicDBObject() .append("user_name", true) .append("user_age",
	 *            false) .append("user_phone", 1);
	 * @return
	 * @throws MongoQueryException
	 */
	protected List<DBObject> findDBObjectList(Map<String, Object> queryParams,
			String orderBy, boolean orderDesc, BasicDBObject returnColumns)
			throws MongoQueryException {

		BasicDBObject orderByObj = null;
		if (StringUtils.isNotBlank(orderBy)) {
			orderByObj = new BasicDBObject();
			if (orderDesc) {
				orderByObj.put(orderBy, -1);
			} else {
				orderByObj.put(orderBy, 1);
			}
		}

		BasicDBObject queryParamsObj = getQueryParmater(queryParams);

		DBCollection coll = mongoTemplate.getCollection(collectionTable());

		List<DBObject> result = new ArrayList<DBObject>();
		DBCursor cursor = null;
		try {
			if (orderByObj == null) {
				if (returnColumns != null) {
					cursor = coll.find(queryParamsObj, returnColumns);
				} else {
					cursor = coll.find(queryParamsObj);
				}
			} else {
				if (returnColumns != null) {
					cursor = coll.find(queryParamsObj, returnColumns).sort(orderByObj);
				} else {
					cursor = coll.find(queryParamsObj).sort(orderByObj);
				}
			}
			
			while (cursor.hasNext()) {
				DBObject doc = cursor.next();
				if (doc != null) {
					result.add(doc);
				}
			}

		} catch (Exception e) {
			throw new MongoQueryException(e.getMessage(), e);
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}

		return result;
	}

	protected BasicDBList findBasicDBListByAggregation(DBObject firstOp,
			DBObject... additionalOps) throws MongoQueryException {
		DBCollection coll = mongoTemplate.getCollection(collectionTable());

		AggregationOutput output = null;
		try {
			output = coll.aggregate(firstOp, additionalOps);
			BasicDBList outPutList = (BasicDBList) output.getCommandResult()
					.get("result");
			return outPutList;
		} catch (Exception e) {
			throw new MongoQueryException(e.getMessage(), e);
		}
	}
	
	protected List<String> findJsonList(
			BasicDBObject queryParamsObj,
			BasicDBObject orderByObj, 
			BasicDBObject returnColumns, 
			int skip, 
			int limit)
			throws MongoQueryException {

		List<DBObject> result = findDBObjectList(queryParamsObj, orderByObj, returnColumns, skip, limit);

		List<String> jsonList = new ArrayList<String>();
		if (result == null || result.isEmpty()) {
			return jsonList;
		}

		for (DBObject doc : result) {
			if (doc != null) {
				String json = doc.toString();
				jsonList.add(json);
			}
		}
		return jsonList;
	}

	protected List<DBObject> findDBObjectList(BasicDBObject queryParamsObj,
			BasicDBObject orderByObj, 
			BasicDBObject returnColumns, 
			int skip, 
			int limit)
			throws MongoQueryException {

		DBCollection coll = mongoTemplate.getCollection(collectionTable());

		List<DBObject> result = new ArrayList<DBObject>();
		DBCursor cursor = null;
		try {
			
			if (orderByObj == null) {
				if (returnColumns != null) {
					cursor = coll.find(queryParamsObj, returnColumns).skip(skip).limit(limit);
				} else {
					cursor = coll.find(queryParamsObj).skip(skip).limit(limit);
				}
			} else {
				if (returnColumns != null) {
					cursor = coll.find(queryParamsObj, returnColumns).sort(orderByObj).skip(skip).limit(limit);
				} else {
					cursor = coll.find(queryParamsObj).sort(orderByObj).skip(skip).limit(limit);
				}
			}
			
			while (cursor.hasNext()) {
				DBObject doc = cursor.next();
				if (doc != null) {
					result.add(doc);
				}
			}

		} catch (Exception e) {
			throw new MongoQueryException(e.getMessage(), e);
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}

		return result;
	}
	
}
