/**
 * 
 */
package com.shinejava.itrac.webapp.ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shinejava.itrac.core.ticket.TicketEntity;
import com.shinejava.itrac.core.ticket.TicketManager;
import com.shinejava.itrac.core.user.UserEntity;
import com.shinejava.itrac.core.user.UserManager;
import com.shinejava.itrac.webapp.BaseController;

/**
 * com.shinejava.itrac.webapp.ticket.TicketController.java
 *
 * @author wangcee
 *
 * @version $Revision: 29135 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@Controller
@RequestMapping(value = "/ticket")
public class TicketController extends BaseController {

	@Autowired
	private TicketManager ticketManager;
	
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView openCreateTicketPage(HttpServletRequest request) {
		String userId = getUserIdFromCookie(request);
		if (StringUtils.isBlank(userId)) {
			return new ModelAndView("redirect:/user/signin");
		}
		
		List<UserEntity> allUsers = userManager.getAllUsers();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("allUsers", allUsers);
		
		return new ModelAndView("/pages/ticket/create.jsp", model);
    }
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createTicket(HttpServletRequest request) {
		String userId = getUserIdFromCookie(request);
		
		TicketEntity ticket = new TicketEntity();
		ticket.setAuthorId(userId);
		ticket.setTitle(request.getParameter("title"));
		ticket.setDescription(request.getParameter("description"));
		ticket.setAssigned(request.getParameter("assigned"));
		ticket.setPriority(NumberUtils.toInt(request.getParameter("priority")));
		ticket.setStatus(request.getParameter("status"));
		
		String ticketId = ticketManager.createTicket(ticket);
		
		return new ModelAndView("redirect:/ticket/" + ticketId);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView ticketDetail(@PathVariable( "id" ) String id, 
    		HttpServletRequest request) {
		String userId = getUserIdFromCookie(request);
		if (StringUtils.isBlank(userId)) {
			return new ModelAndView("redirect:/user/signin");
		}
		
		TicketEntity ticket = ticketManager.getById(id);
		List<UserEntity> allUsers = userManager.getAllUsers();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("allUsers", allUsers);
		model.put("ticket", ticket);
		
		return new ModelAndView("/pages/ticket/detail.jsp", model);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ModelAndView updateTicketStatus(@PathVariable( "id" ) String id, 
    		HttpServletRequest request) {
		String userId = getUserIdFromCookie(request);
		
		TicketEntity ticket = new TicketEntity();
		ticket.setId(id);
		ticket.setAssigned(request.getParameter("assigned"));
		ticket.setAssistBy(request.getParameter("assistBy"));
		ticket.setPriority(NumberUtils.toInt(request.getParameter("priority")));
		ticket.setStatus(request.getParameter("status"));
		
		ticketManager.updateStatus(ticket, userId);
		
		return new ModelAndView("redirect:/ticket/" + id);
    }
	
	
}
