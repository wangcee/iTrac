/**
 * 
 */
package com.shinejava.itrac.webapp.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shinejava.itrac.core.report.ReportService;
import com.shinejava.itrac.core.report.ReportVO;
import com.shinejava.itrac.core.ticket.TicketEntity;
import com.shinejava.itrac.webapp.BaseController;

/**
 * com.shinejava.itrac.webapp.report.ReportController.java
 *
 * @author wangcee
 *
 * @version $Revision: 29184 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseController {
	
	@Autowired
	ReportService reportService;

	@RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
		String userId = getUserIdFromCookie(request);
		if (StringUtils.isBlank(userId)) {
			return new ModelAndView("redirect:/user/signin");
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId", userId);
		
		return new ModelAndView("/pages/report/list.jsp", model);
    }
	
	/**
	 * <p>/report/150?userId=cee&start=20130101&end=20130927&pagenum=1&pagesize=10</p>
	 * 
	 * @param reportId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{reportId}", method = RequestMethod.GET)
    public ModelAndView reportPage(@PathVariable("reportId") int reportId, 
    		HttpServletRequest request) {
		String cookieUserId = getUserIdFromCookie(request);
		
		String userId = request.getParameter("userId");
		if (StringUtils.isBlank(userId)) {
			userId = cookieUserId;
		}
		
		String startDate = request.getParameter("start");
		String endDate = request.getParameter("end");
		String pagenum = request.getParameter("pagenum");
		String pagesize = request.getParameter("pagesize");
		
		ReportVO report = new ReportVO(reportId);
		report.setUserId(userId);
		report.setStartDate(startDate);
		report.setEndDate(endDate);
		report.setPageNum(pagenum);
		report.setPageSize(pagesize);
		
		List<TicketEntity> list = reportService.getReportResultPage(report);
		
		report.setDataList(list);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId", userId);
		model.put("start", startDate);
		model.put("end", endDate);
		model.put("report", report);
		
		setupPaginationBaseUri(request);
		return new ModelAndView("/pages/report/type100.jsp", model);
    }
	
}
