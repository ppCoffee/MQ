/**
 * JmsConsumerTopic.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月19日
 */

package com.evmtv.topic;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumerTopic {
	
	private static final String ACTIVEMQ_URL = "tcp://192.168.146.128:61616";
	public static final String TOPIC_NAME = "topic01";

	public static void main(String[] args) throws JMSException, IOException {
		
		System.out.println("我是2号消费者");
		
		// 1.创建连接工厂，按照给定的URL，采用默认的用户名密码
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		
		// 2.通过连接工厂,获得connection并启动访问
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		
		// 3.创建会话session
		// 两个参数transacted=事务,acknowledgeMode=确认模式(签收)
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		// 4.创建目的地(具体是队列queue还是主题topic)
		Topic topic = session.createTopic(TOPIC_NAME);
		
		// 5.创建消息的消费者
		MessageConsumer messageConsumer = session.createConsumer(topic);
		// 5.创建消息的消费者,指定消费哪一个队列里面的消息
		messageConsumer.setMessageListener(message -> {
			if (message instanceof TextMessage) {
				try {
					String text = ((TextMessage) message).getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		System.in.read();
	}

}
