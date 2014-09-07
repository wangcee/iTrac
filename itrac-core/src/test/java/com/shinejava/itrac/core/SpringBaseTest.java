/**
 * 
 */
package com.shinejava.itrac.core;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * com.shinejava.itrac.core.SpringBaseTest.java
 *
 * @author wangcee
 *
 * @version $Revision: 28804 $
 *          $Author: wangc@SHINETECHCHINA $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext-core.xml", "classpath*:/spring/applicationContext-mongo-test.xml" })
public abstract class SpringBaseTest {

}
