/**
 * 
 */
package com.shinejava.itrac.core.ticket;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.shinejava.itrac.core.user.UserEntity;

/**
 * com.shinejava.itrac.core.ticket.TicketEntity.java
 *
 * @author wangcee
 *
 * @version $Revision: 29218 $
 *          $Author: wangc@SHINETECHCHINA $
 */
public class TicketEntity {
	
	public static final String ASSIGNED_UNKNOWN = UserEntity.USER_UNKNOWN;
	
	public static final String STATUS_OPEN = "open";
	public static final String STATUS_NEEDFEEDBACK = "needFeedback";
	public static final String STATUS_NEEDTEST = "needTest";
	public static final String STATUS_REVIEW = "review";
	public static final String STATUS_NEEDDEPLOY = "needDeploy";
	public static final String STATUS_DEPLOYED = "deployed";
	public static final String STATUS_HOLDOFF = "holdOff";
	public static final String STATUS_COMPLETED = "completed";
	
	@JsonProperty("_id")
	private String id;
	
	private String title;
	
	private String externalUrl;
	
	private String description;
	
	private String authorId;
	
	private String assigned;
	
	private String assistBy;
	
	private String status;
	
	private int priority;
	
	private int createDate;
	
	private int updateDate;
	
	private List<TicketCommentEntity> comments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExternalUrl() {
		return externalUrl;
	}

	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getAssigned() {
		return assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public String getAssistBy() {
		return assistBy;
	}

	public void setAssistBy(String assistBy) {
		this.assistBy = assistBy;
	}

	public int getCreateDate() {
		return createDate;
	}

	public void setCreateDate(int createDate) {
		this.createDate = createDate;
	}

	public int getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(int updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public List<TicketCommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<TicketCommentEntity> comments) {
		this.comments = comments;
	}
	
}
