/**
 * 
 */
package com.shinejava.itrac.webapp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * com.shinejava.itrac.webapp.BaseController.java
 *
 * @author wangcee
 *
 * @version $Revision: 29139 $
 *          $Author: wangc@SHINETECHCHINA $
 */
public abstract class BaseController {
	
	public static final String COOKIE_GLOBAL_USER_ID = "it_global_user_id";
	
	public String getCookieValue(HttpServletRequest request, String cookieName) {
    	Cookie[] cookies = request.getCookies();
    	
    	if (cookies == null || StringUtils.isBlank(cookieName)) {
    		return null;
    	}
    	for (Cookie cookie : cookies) {
    		if (StringUtils.equalsIgnoreCase(cookieName, cookie.getName())) {
    			return cookie.getValue();
    		}
    	}
    	return null;
    }
	
	public String getUserIdFromCookie(HttpServletRequest request) {
		return getCookieValue(request, COOKIE_GLOBAL_USER_ID);
	}
	
	public void setupPaginationBaseUri(HttpServletRequest request) {
		String baseUri = request.getRequestURI() + "?";
		String queryString = request.getQueryString();
		
		if (StringUtils.isBlank(queryString)) {
			baseUri = baseUri + "pagenum=";
		} else if (!StringUtils.contains(queryString, "pagenum")) {
			baseUri = baseUri + queryString + "&pagenum=";
		} else {
			baseUri = baseUri + StringUtils.substringBefore(queryString, "pagenum");
			queryString = StringUtils.substringAfter(queryString, "pagenum");
			baseUri = baseUri + StringUtils.substringAfter(queryString, "&");
			if (baseUri.endsWith("&") || StringUtils.endsWith(baseUri, "?")) {
				baseUri = baseUri + "pagenum=";
			} else {
				baseUri = baseUri + "&pagenum=";
			}
		}
		
		request.setAttribute("paginationBaseUri", baseUri);
	}
}
