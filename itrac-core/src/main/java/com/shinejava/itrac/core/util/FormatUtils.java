/**
 * 
 */
package com.shinejava.itrac.core.util;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * com.shinejava.itrac.core.util.FormatUtils.java
 *
 * @author wangcee
 *
 * @version $Revision: 28804 $
 *          $Author: wangc@SHINETECHCHINA $
 */
public class FormatUtils {
	
	public static final String PATTERN_DATE_INT = "yyyyMMdd";
	public static final String PATTERN_TIME_INT = "HH:mm:ss";
	
	public static int getDateIntValue(Date date) {
		if (date == null) {
			date = new Date();
		}
		
		String dateStr = DateFormatUtils.format(date, PATTERN_DATE_INT);
		return NumberUtils.toInt(dateStr);
	}
	
	public static String getTimeIntStr(Date date) {
		if (date == null) {
			date = new Date();
		}
		
		return DateFormatUtils.format(date, PATTERN_TIME_INT);
	}

}
