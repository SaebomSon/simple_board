package com.newsp.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.newsp.dao.UsersDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UsersTest {
	
	@Autowired
	private UsersDao dao; 
	
	@Test
	public void testIdCheck() throws Exception{
		System.out.println(dao.signupIdCheck("aspp"));
	}
	
}
