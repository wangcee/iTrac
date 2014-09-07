/**
 * 
 */
package com.shinejava.itrac.core.mongodb;

/**
 * com.shinejava.itrac.core.mongodb.MongoToEntityException.java
 *
 * @author wangcee
 *
 * @version $Revision: 28804 $
 *          $Author: wangc@SHINETECHCHINA $
 */
public class MongoToEntityException extends RuntimeException {

	private static final long serialVersionUID = 5521843646848419442L;

	public MongoToEntityException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MongoToEntityException(String message) {
		super(message);
	}
}
