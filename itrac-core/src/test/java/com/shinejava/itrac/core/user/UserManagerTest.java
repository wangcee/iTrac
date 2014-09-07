package com.shinejava.itrac.core.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.shinejava.itrac.core.SpringBaseTest;
import com.shinejava.itrac.core.mongodb.MongoQueryException;

/**
 * 
 * com.shinejava.itrac.core.user.UserManagerTest.java
 *
 * @author wangcee
 *
 * @version $Revision: 28804 $
 *          $Author: wangc@SHINETECHCHINA $
 */
public class UserManagerTest extends SpringBaseTest {
	
	String TEST_USER_ID = "junit-test";
	
	@Autowired
	UserManager userManager;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		userManager.removeUser(TEST_USER_ID);
	}
	
	@Test
	public void testGenerateId() {
		assertEquals("wangcee", UserManager.generateId("wangcee"));
		
		assertEquals("wang-cee-test", UserManager.generateId(" wAng cEe test "));
		
		assertEquals(TEST_USER_ID, UserManager.generateId(" junit.teST "));
	}
	
	
	@Test
	public void testCreateUser() {
		
		UserEntity user = new UserEntity();
		user.setName("junit.test");
		
		userManager.createUser(user);
		assertEquals(TEST_USER_ID, user.getId());
	}
	
	@Test
	public void testUpdateUser() {
		UserEntity user = new UserEntity();
		user.setName("junit.test");
		
		userManager.createUser(user);
		assertEquals(TEST_USER_ID, user.getId());
		
		assertNull(user.getEmail());
		assertEquals(user.getPassword(), UserEntity.PASSWORD_DEFAULT);
		assertNull(user.getRole());
		
		user.setName("jUnit Test");
		user.setEmail("junit-test@aaa.com");
		user.setPassword("shine55");
		user.setRole(null);
		
		userManager.update(user);
		
		user = userManager.getUserByNamePassword("jUnit Test", "shine55");
		
		assertNotNull(user);
		assertEquals("jUnit Test", user.getName());
		assertEquals("junit-test@aaa.com", user.getEmail());
		assertEquals("shine55", user.getPassword());
		assertNull(user.getRole());
		
		assertEquals(TEST_USER_ID, user.getId());
	}
	
	@Ignore
	@Test(expected = MongoQueryException.class)
	public void testCreateUserDuplicate() {
//		expectedEx.expect(MongoQueryException.class);
		
		UserEntity user = new UserEntity();
		user.setName("junit.test");
		
		userManager.createUser(user);
		assertEquals(TEST_USER_ID, user.getId());
		
		UserEntity user2 = new UserEntity();
		user2.setName("junit test ");
		
		userManager.createUser(user2);
		
//		try {
//			userManager.createUser(user2);
//			assertTrue(false);
//		} catch (Exception e) {
//			assertTrue(e instanceof MongoQueryException);
//		}
	}

}
