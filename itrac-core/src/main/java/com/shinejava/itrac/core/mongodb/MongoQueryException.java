/**
 * 
 */
package com.shinejava.itrac.core.mongodb;

/**
 * com.shinejava.itrac.core.mongodb.MongoQueryException.java
 *
 * @author wangcee
 *
 * @version $Revision: 28804 $
 *          $Author: wangc@SHINETECHCHINA $
 */
public class MongoQueryException extends RuntimeException {

	private static final long serialVersionUID = 3589303857328331964L;

	public MongoQueryException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MongoQueryException(String message) {
		super(message);
	}
	
}
