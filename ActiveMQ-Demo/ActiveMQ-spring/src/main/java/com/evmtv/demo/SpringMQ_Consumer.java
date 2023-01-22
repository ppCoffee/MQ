/**
 * SpringMQ_Consumer.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月22日
 */

package com.evmtv.demo;

import javax.annotation.Resource;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringMQ_Consumer {
	
	@Resource
	private JmsTemplate jmsTemplate;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
		SpringMQ_Consumer consumer = (SpringMQ_Consumer)ctx.getBean("springMQ_Consumer");
		
		while(true) {
			
			String returnValue = (String)consumer.jmsTemplate.receiveAndConvert();
			if(returnValue.isEmpty())
				return;
			
			System.out.println("****消费者收到的消息:   " + returnValue);
		}
	}

}
