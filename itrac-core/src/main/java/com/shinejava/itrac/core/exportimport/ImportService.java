/**
 * 
 */
package com.shinejava.itrac.core.exportimport;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinejava.itrac.core.ticket.TicketEntity;
import com.shinejava.itrac.core.ticket.TicketManager;
import com.shinejava.itrac.core.user.UserEntity;
import com.shinejava.itrac.core.user.UserManager;

/**
 * com.shinejava.itrac.core.exportimport.ImportService.java
 *
 * @author wangcee
 *
 * @version $Revision: 28804 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@Service
public class ImportService {
	
	public static final String WRIKE_DEFAULT_STATUS = "open";
	public static final int WRIKE_DEFAULT_PRIORITY = 8;
	
	@Autowired
	TicketManager ticketManager;
	
	@Autowired
	UserManager userManager;

	public String importWrikeRss(List<Map<String, String>> list, String whoImportXml) {
		String message = "{insert:%d, exist:%d}";
		int insertCount = 0;
		int existCount = 0;
		
		if (list == null || list.isEmpty()) {
			return String.format(message, insertCount, existCount);
		}
		
		for (Map<String, String> map : list) {
			String title = map.get("title");
			String link = map.get("link");
			String ticketId = TicketManager.getTicketIdByWrikePermalink(link);
			
			TicketEntity ticket = ticketManager.getById(ticketId);
			if (ticket != null) {
				existCount++;
				//update title
				if (StringUtils.isBlank(ticket.getTitle())) {
					ticket.setTitle(title);
					ticketManager.updateTitleForWrikeImport(ticketId, title);
				}
				
			} else {
				insertCount++;
				String authorName = map.get("author");
				String updateDateStr = map.get("updated");
				
				TicketEntity newTicket = new TicketEntity();
				newTicket.setId(ticketId);
				newTicket.setTitle(title);
				newTicket.setExternalUrl(link);
				newTicket.setPriority(WRIKE_DEFAULT_PRIORITY);
				newTicket.setStatus(WRIKE_DEFAULT_STATUS);
				
				UserEntity authorUser = createAuthor(authorName);
				newTicket.setAuthorId(authorUser.getId());
				
				//parse Date
				if (StringUtils.isNotBlank(updateDateStr)) {
					updateDateStr = StringUtils.left(updateDateStr, 10);
					updateDateStr = updateDateStr.replaceAll("-", "");
					
					int updateDate = NumberUtils.toInt(updateDateStr);
					if (updateDate > 20000000) {
						newTicket.setCreateDate(updateDate);
						newTicket.setUpdateDate(updateDate);
					}
				}
				
				ticketManager.createTicket(newTicket);
			}
		}
		
		return String.format(message, insertCount, existCount);
	}

	private UserEntity createAuthor(String authorName) {
		//parse Author
		String authorId = UserManager.generateId(authorName);
		UserEntity authorUser = userManager.getUserById(authorId);
		if (authorUser == null) {
			authorUser = new UserEntity();
			authorUser.setId(authorId);
			authorUser.setName(authorName);
			
			userManager.createUser(authorUser);
		}
		return authorUser;
	}
	
}
