package com.test.web;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
@SpringBootConfiguration
@TestExecutionListeners(ExecutionListener.class)
@RunWith(SpringRunner.class)
class ApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("contextLoads");
	}

	@Test
	void test1() {
		System.out.println("test1");
	}

	@Test
	void test2() {
		System.out.println("test2");
	}
}
