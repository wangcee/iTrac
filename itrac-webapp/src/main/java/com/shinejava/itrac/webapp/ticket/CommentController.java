/**
 * 
 */
package com.shinejava.itrac.webapp.ticket;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shinejava.itrac.core.ticket.TicketManager;
import com.shinejava.itrac.webapp.BaseController;

/**
 * com.shinejava.itrac.webapp.ticket.CommentController.java
 *
 * @author wangcee
 *
 * @version $Revision: 29129 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController extends BaseController {
	
	@Autowired
	private TicketManager ticketManager;

	@RequestMapping(value = "/{ticketId}", method = RequestMethod.POST)
    public ModelAndView updateTicketStatus(@PathVariable("ticketId") String ticketId, 
    		HttpServletRequest request) {
		String userId = getUserIdFromCookie(request);
		
		String content = request.getParameter("content");
		
		ticketManager.addComment(ticketId, userId, content);
		
		return new ModelAndView("redirect:/ticket/" + ticketId);
    }
}
