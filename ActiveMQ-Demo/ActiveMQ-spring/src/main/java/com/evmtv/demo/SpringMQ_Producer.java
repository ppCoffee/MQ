/**
 * SpringMQ_Producer.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月22日
 */

package com.evmtv.demo;

import javax.annotation.Resource;
import javax.jms.TextMessage;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringMQ_Producer {
	
	@Resource
	private JmsTemplate jmsTemplate;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
		SpringMQ_Producer producer = (SpringMQ_Producer)ctx.getBean("springMQ_Producer");

		producer.jmsTemplate.send((session) -> {
			
			TextMessage text = session.createTextMessage("***Spring和ActiveMQ的整合case.....");
			
			return text; 
		}); 
		
		System.out.println("********send task over");
	}

}
