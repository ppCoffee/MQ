/**
 * Topic_Producer.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月23日
 */

package com.evmtv.mq.service;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class Topic_Producer {

	
	//JmsMessagingTemplate是Springboot的Jms模板,Spring的是JmsTemplate
	@Resource
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@Resource
    private ActiveMQTopic activeMQTopic;
	
    public void producer() {
        jmsMessagingTemplate.convertAndSend(activeMQTopic, "主题消息:    " + UUID.randomUUID().toString());
    }
}
