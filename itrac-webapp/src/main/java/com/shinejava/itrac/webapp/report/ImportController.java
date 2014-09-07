/**
 * 
 */
package com.shinejava.itrac.webapp.report;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinejava.itrac.core.exportimport.ImportService;
import com.shinejava.itrac.webapp.BaseController;

/**
 * com.shinejava.itrac.webapp.report.ImportController.java
 *
 * @author wangcee
 *
 * @version $Revision: 29129 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@Controller
@RequestMapping(value = "/import")
public class ImportController extends BaseController {
	
	@Autowired
	ImportService importService;

	@RequestMapping(value = "/wrikerss", method = RequestMethod.GET)
    public ModelAndView openWrikeRssImportPage(HttpServletRequest request) {
		String userId = getUserIdFromCookie(request);
		if (StringUtils.isBlank(userId)) {
			return new ModelAndView("redirect:/user/signin");
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/pages/import/wrikeRss.jsp", model);
	}
	
	@RequestMapping(value = "/wrikerss", method = RequestMethod.POST)
	@ResponseBody
    public String postWrikeRssXml(HttpServletRequest request) {
		String userId = getUserIdFromCookie(request);
		
		String wrikeRssXmlText = request.getParameter("xmltxt");
		
		List list = parseWrikeRss(wrikeRssXmlText);
		
		String message = importService.importWrikeRss(list, userId);
		
		return message;
	}
	
	public List parseWrikeRss(String xmlContent) {
		if (StringUtils.isBlank(xmlContent)) {
			return null;
		}
		xmlContent = StringUtils.trim(xmlContent);
		
		Binding binding = new Binding();
		binding.setVariable("xmlContent", xmlContent);
		
		URL[] classpath = new URL[]{Thread.currentThread().getContextClassLoader().getResource("")};
		
		List<Map<String, String>> list = null;
		try {
//			GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine("src/main/resources/groovy");
			GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine(classpath);
			Object obj = groovyScriptEngine.run("groovy/WrikeRssParser.groovy", binding);
			
			list = (List<Map<String, String>>)obj;
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
