/**
 * 
 */
package com.shinejava.itrac.webapp.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinejava.itrac.core.user.UserEntity;
import com.shinejava.itrac.core.user.UserManager;
import com.shinejava.itrac.webapp.BaseController;

/**
 * com.shinejava.itrac.webapp.user.UserController.java
 *
 * @author wangcee
 *
 * @version $Revision: 28845 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	
	@Autowired
	UserManager userManager;
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView openSigninPage(HttpServletRequest request) {
		return new ModelAndView("/pages/user/signin.jsp");
    }
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ModelAndView signin(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserEntity userEntity = userManager.getUserByNamePassword(userName, password);
		if (userEntity == null) {
			return new ModelAndView("redirect:/user/signin");
		}
		
		Cookie userCookie = new Cookie(COOKIE_GLOBAL_USER_ID, userEntity.getId());
		userCookie.setPath(request.getContextPath());
		userCookie.setMaxAge(60 * 60 * 24 * 7);
		response.addCookie(userCookie);
		
		return new ModelAndView("redirect:/report");
    }
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView openProfilePage(HttpServletRequest request) {
		String userId = getUserIdFromCookie(request);
		
		UserEntity user = userManager.getUserById(userId);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);
		
		return new ModelAndView("/pages/user/profile.jsp", model);
    }
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	@ResponseBody
    public String updateProfile(HttpServletRequest request) {
		String userId = getUserIdFromCookie(request);
		UserEntity user = userManager.getUserById(userId);
		if (user == null) {
			return "please login";
		}
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		user.setEmail(email);
		user.setPassword(password);
		
		userManager.update(user);
		return "itOK";
    }
	
	
}
