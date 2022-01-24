package com.blueprint;

import com.blueprint.controller.MainController;
import com.blueprint.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class BlueprintApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void givenInScopeComponents_whenSearchingInApplicationContext_thenFindThem() {
		System.out.println(applicationContext.getApplicationName());
		System.out.println(applicationContext.getAutowireCapableBeanFactory());
		System.out.println(applicationContext.getParent());
		System.out.println(applicationContext.getBean(MainController.class));
		System.out.println(applicationContext.getBean(UserService.class));
		assertNotNull(applicationContext.getBean(MainController.class));
		assertNotNull(applicationContext.getBean(UserService.class));
//		assertNotNull(applicationContext.getBean(RepositoryExample.class));
//		assertNotNull(applicationContext.getBean(ComponentExample.class));
//		assertNotNull(applicationContext.getBean(CustomComponentExample.class));
	}
}
