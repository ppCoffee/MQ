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

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Queue_Producer {
	
	//JmsMessagingTemplate是Springboot的Jms模板,Spring的是JmsTemplate
	@Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    //把ConfigBean类的ActiveMQQueue注入进来
	@Resource
    private ActiveMQQueue activeMQQueue;

    //发送Queue的方法
    public void producerMsg() {
    	
        jmsMessagingTemplate.convertAndSend(activeMQQueue, "**************" + UUID.randomUUID().toString());
    }
    
    //间隔3秒投递,SpringBoot的Scheduled用来定时执行
    @Scheduled(fixedDelay = 3000)
    public void producerMsgScheduled() {
        jmsMessagingTemplate.convertAndSend(activeMQQueue, "**************" + UUID.randomUUID().toString());
        System.out.println("Scheduled定时投递");
    }

}
