/**
 * 
 */
package com.shinejava.itrac.core.ticket;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shinejava.itrac.core.util.FormatUtils;
import com.shinejava.itrac.core.util.IdGenerator;

/**
 * com.shinejava.itrac.core.ticket.TicketManager.java
 *
 * @author wangcee
 *
 * @version $Revision: 29134 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@Component
public class TicketManager {

	@Autowired
	private TicketMongoDAO ticketMongoDAO;
	
	/**
	 * 
	 * @param entity
	 * @return	the NEW ticket ID
	 */
	public String createTicket(TicketEntity entity) {
		if (isWrikeTicket(entity)) {
			
			String ticketId = entity.getId();
			if (StringUtils.isBlank(ticketId)) {
				String wrikeIdStr = getTicketIdByWrikePermalink(entity.getTitle());
				entity.setId(wrikeIdStr);
				entity.setExternalUrl(entity.getTitle());
				entity.setTitle("");
			}
			
		} else {
			
			String ticketId = IdGenerator.generateRNDString(8);
			entity.setId(ticketId);
		}
		
		Date today = new Date();
		int createDate = FormatUtils.getDateIntValue(today);
		if (entity.getCreateDate() < 20000000) {
			entity.setCreateDate(createDate);
		}
		if (entity.getUpdateDate() < 20000000) {
			entity.setUpdateDate(createDate);
		}
		
		ticketMongoDAO.insert(entity);
		return entity.getId();
	}
	
	public TicketEntity getById(String id) {
		return ticketMongoDAO.findById(id);
	}
	
	public boolean updateStatus(TicketEntity entity, String userId) {
		if (StringUtils.isBlank(userId) || 
				entity == null || 
				StringUtils.isBlank(entity.getId())) {
			return false;
		}
		
		TicketEntity old = getById(entity.getId()); 
		if (old == null) {
			return false;
		}
		
		Date today = new Date();
		int updateDate = FormatUtils.getDateIntValue(today);
		String updateTime = FormatUtils.getTimeIntStr(today);
		
		TicketCommentEntity comment = new TicketCommentEntity();
		comment.setUserId(userId);
		comment.setCreateDate(updateDate);
		comment.setCreateTime(updateTime);
		comment.setContent(getCommentContentOfUpdateStatus(entity, old));
		
		entity.setUpdateDate(updateDate);
		
		ticketMongoDAO.updateStatus(entity);
		ticketMongoDAO.addComment(entity.getId(), comment);
		return true;
	}
	
	public boolean updateTitleForWrikeImport(String ticketId, String title) {
		ticketMongoDAO.updateTitle(ticketId, title);
		return true;
	}
	
	public boolean addComment(String ticketId, String userId, String content) {
		Date today = new Date();
		int updateDate = FormatUtils.getDateIntValue(today);
		String updateTime = FormatUtils.getTimeIntStr(today);
		
		TicketCommentEntity comment = new TicketCommentEntity();
		comment.setUserId(userId);
		comment.setCreateDate(updateDate);
		comment.setCreateTime(updateTime);
		comment.setContent(content);
		
		ticketMongoDAO.addComment(ticketId, comment);
		ticketMongoDAO.updateUpdateDate(ticketId, updateDate);
		return true;
	}

	private String getCommentContentOfUpdateStatus(TicketEntity entity, TicketEntity old) {
		StringBuilder commentContent = new StringBuilder("[!UPDATE]: ");
		if (!StringUtils.equalsIgnoreCase(old.getAssigned(), entity.getAssigned())) {
			commentContent.append("Assigned: ")
					.append(old.getAssigned())
					.append(" --- ")
					.append(entity.getAssigned())
					.append("; ");
		}
		if (!StringUtils.equalsIgnoreCase(old.getAssistBy(), entity.getAssistBy())) {
			commentContent.append("AssistBy: ")
					.append(entity.getAssistBy())
					.append("; ");
		}
		if (!StringUtils.equalsIgnoreCase(old.getStatus(), entity.getStatus())) {
			commentContent.append("Status: ")
					.append(entity.getStatus())
					.append("; ");
		}
		if (old.getPriority() != entity.getPriority()) {
			commentContent.append("Priority: ")
					.append(entity.getPriority())
					.append("; ");
		}
		
		return commentContent.toString();
	}
	
	
	public static boolean isWrikeTicket(TicketEntity entity) {
		if (entity == null) {
			return false;
		}
		String ticketId = entity.getId();
		if (StringUtils.isNotBlank(ticketId)) {
			//if ticket ID is a number
			int id = NumberUtils.toInt(ticketId);
			if (id > 0) {
				return true;
			}
		} else {
			String wrikeIdStr = getTicketIdByWrikePermalink(entity.getTitle());
			return StringUtils.isNotBlank(wrikeIdStr);
		}
		return false;
	}
	
	/**
	 * Wrike.com Permalink is <b>https://www.wrike.com/open.htm?id=17687494</b>
	 * 
	 * @param str
	 * @return
	 */
	public static String getTicketIdByWrikePermalink(String str) {
		if (!StringUtils.contains(str, "www.wrike.com/")) {
			return null;
		}
		str = StringUtils.trim(str);
		String wrikeIdStr = StringUtils.substringAfterLast(str, "id=");
		int wrikeId = NumberUtils.toInt(wrikeIdStr);
		if (wrikeId > 0) {
			return String.valueOf(wrikeId);
		}
		return null;
	}
}
