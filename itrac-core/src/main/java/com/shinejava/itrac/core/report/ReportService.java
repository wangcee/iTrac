/**
 * 
 */
package com.shinejava.itrac.core.report;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinejava.itrac.core.ticket.TicketEntity;
import com.shinejava.itrac.core.ticket.TicketMongoDAO;
import com.shinejava.itrac.core.user.UserEntity;
import com.shinejava.itrac.core.util.FormatUtils;


/**
 * com.shinejava.itrac.core.report.ReportService.java
 *
 * @author wangcee
 *
 * @version $Revision: 29218 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@Service
public class ReportService {
	
	private static final int MIN_DATE = 20000000;
	
	@Autowired
	TicketMongoDAO ticketMongoDAO;
	
	public List<TicketEntity> getReportResultPage(ReportVO report) {
		
		if (150 == report.getReportId()) {
			// [status = open, assigned = me]
			return getReport150(report);
		}
		if (151 == report.getReportId()) {
			// [status = open, assistBy = me]
			return getReport151(report);
		}
		if (152 == report.getReportId()) {
			// [status = (needFeedback, review, needTest), assistBy = me]
			return getReport152(report);
		}
		if (153 == report.getReportId()) {
			// [status = needTest]
			return getReportByStatus(report, TicketEntity.STATUS_NEEDTEST, true);
		}
		if (154 == report.getReportId()) {
			// [comment.user = me, comment.updateDate = Last 2 days]
			return getReport154(report);
		}
		if (155 == report.getReportId()) {
			// [status = needDeploy]
			return getReportByStatus(report, TicketEntity.STATUS_NEEDDEPLOY, true);
		}
		if (156 == report.getReportId()) {
			// [status = deployed]
			return getReportByStatus(report, TicketEntity.STATUS_DEPLOYED, false);
		}
		if (157 == report.getReportId()) {
			// [status = holdOff]
			return getReportByStatus(report, TicketEntity.STATUS_HOLDOFF, true);
		}
		if (158 == report.getReportId()) {
			// [status = completed]
			return getReportByStatus(report, TicketEntity.STATUS_COMPLETED, false);
		}
		if (160 == report.getReportId()) {
			// [status = open, assigned = unknown]
			return getReport160(report);
		}
		if (161 == report.getReportId()) {
			// [status = open]
			return getReportByStatus(report, TicketEntity.STATUS_OPEN, true);
		}
		
		return null;
	}

	private List<TicketEntity> getReport154(ReportVO report) {
		Date today = new Date();
		Date yesterday = DateUtils.addDays(today, -1);
		
		if (report.getStartDate() == null || report.getStartDate().intValue() < MIN_DATE) {
			report.setStartDate(FormatUtils.getDateIntValue(yesterday));
		}
		if (report.getEndDate() == null || report.getEndDate().intValue() < MIN_DATE) {
			report.setEndDate(FormatUtils.getDateIntValue(today));
		}
		
		List<TicketEntity> ticketList = ticketMongoDAO.getByCommentAuthor(
				report.getUserId(),
				report.getStartDate(), 
				report.getEndDate(), 
				report.getSkip(), 
				report.getPageSize());
		
		return ticketList;
	}

	// [status = open, assistBy = me]
	protected List<TicketEntity> getReport151(ReportVO report) {
		
		List<TicketEntity> ticketList = ticketMongoDAO.getByStatusAndAssistBy(
				TicketEntity.STATUS_OPEN, 
				report.getUserId(), 
				report.getSkip(), 
				report.getPageSize());
		
		return ticketList;
	}
	
	// [status = (needFeedback, review, needTest), assistBy = me]
	protected List<TicketEntity> getReport152(ReportVO report) {
		String[] statusArray = new String[]{
				TicketEntity.STATUS_NEEDFEEDBACK, 
				TicketEntity.STATUS_REVIEW, 
				TicketEntity.STATUS_NEEDTEST};
		
		List<TicketEntity> ticketList = ticketMongoDAO.getByStatusAndAssistBy(
				statusArray, 
				report.getUserId(), 
				report.getSkip(), 
				report.getPageSize(),
				true);
		
		return ticketList;
	}
	
	// [status = open, assigned = me]
	protected List<TicketEntity> getReport150(ReportVO report) {
		
		List<TicketEntity> ticketList = ticketMongoDAO.getByStatusAndAssigned(
				TicketEntity.STATUS_OPEN, 
				report.getUserId(), 
				report.getSkip(), 
				report.getPageSize(),
				true);
		
		return ticketList;
	}
	
	private List<TicketEntity> getReportByStatus(ReportVO report, String status, boolean sortByPriority) {
		List<TicketEntity> ticketList = ticketMongoDAO.getByStatusAndAssigned(
				status, 
				null, 
				report.getSkip(), 
				report.getPageSize(),
				sortByPriority);
		
		return ticketList;
	}
	
	// [status = open, assigned = unknown]
	protected List<TicketEntity> getReport160(ReportVO report) {
		
		List<TicketEntity> ticketList = ticketMongoDAO.getByStatusAndAssigned(
				TicketEntity.STATUS_OPEN, 
				UserEntity.USER_UNKNOWN, 
				report.getSkip(), 
				report.getPageSize(),
				true);
		
		return ticketList;
	}
	
}
