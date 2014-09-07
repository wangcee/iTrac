/**
 * 
 */
package com.shinejava.itrac.core.ticket;

/**
 * com.shinejava.itrac.core.ticket.TicketCommentEntity.java
 *
 * @author wangcee
 *
 * @version $Revision: 28804 $
 *          $Author: wangc@SHINETECHCHINA $
 */
public class TicketCommentEntity {

	private String userId;
	
	private int createDate;
	
	private String createTime;
	
	private String content;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCreateDate() {
		return createDate;
	}

	public void setCreateDate(int createDate) {
		this.createDate = createDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
