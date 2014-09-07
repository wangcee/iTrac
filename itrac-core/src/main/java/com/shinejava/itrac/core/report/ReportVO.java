/**
 * 
 */
package com.shinejava.itrac.core.report;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * com.shinejava.itrac.core.report.ReportVO.java
 *
 * @author wangcee
 *
 * @version $Revision: 29184 $
 *          $Author: wangc@SHINETECHCHINA $
 */
public class ReportVO {
	
	public static final int PAGESIZE_DEFAULT = 20;
	public static final int PAGENUM_FIRST = 1;
	
	private int reportId;

	private String userId;
	
	private Integer startDate;
	
	private Integer endDate;
	
	private Integer pageNum;
	
	private Integer pageSize;
	
	private List dataList;
	
	public ReportVO(int reportId) {
		this.reportId = reportId;
	}
	
	//Record in the current page
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public int getReportId() {
		return reportId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getStartDate() {
		return startDate;
	}

	public void setStartDate(String value) {
		if (StringUtils.isNotBlank(value)) {
			int date = NumberUtils.toInt(value);
			if (date > 20000000) {
				this.startDate = date;
			}
		}
	}
	
	public void setStartDate(Integer value) {
		this.startDate = value;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(String value) {
		if (StringUtils.isNotBlank(value)) {
			int date = NumberUtils.toInt(value);
			if (date > 20000000) {
				this.endDate = date;
			}
		}
	}
	
	public void setEndDate(Integer value) {
		this.endDate = value;
	}

	public Integer getPageNum() {
		if (pageNum == null) {
			return PAGENUM_FIRST;
		}
		return pageNum;
	}

	public void setPageNum(String value) {
		if (StringUtils.isNotBlank(value)) {
			int num = NumberUtils.toInt(value);
			if (num >= 1) {
				this.pageNum = num;
			}
		}
	}

	public Integer getPageSize() {
		if (pageSize == null) {
			return PAGESIZE_DEFAULT;
		}
		return pageSize;
	}

	public void setPageSize(String value) {
		if (StringUtils.isNotBlank(value)) {
			int num = NumberUtils.toInt(value);
			if (num >= 1) {
				this.pageSize = num;
			}
			if (num > 100) {
				this.pageSize = 100;
			}
		}
	}
	
	public int getSkip() {
		return (getPageNum() - 1) * getPageSize();
	}
	
	public boolean isHasMorePages() {
		if (dataList == null || dataList.isEmpty()) {
			return false;
		}
		return dataList.size() >= getPageSize();
	}
	
}
