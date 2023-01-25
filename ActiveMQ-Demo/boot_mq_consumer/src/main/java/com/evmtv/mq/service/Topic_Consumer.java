/**
 * Queue_Producer.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月22日
 */

package com.evmtv.mq.service;


import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class Topic_Consumer {
	
	//监听接收的方法
	@JmsListener(destination = "${myTopicName}",containerFactory = "jmsListenerContainerFactory")
	public void consumerMsg(TextMessage textMessage) throws JMSException {
	    String text = textMessage.getText();
	    System.out.println("***消费者收到的消息:    " + text);
	}

}
