/**
 * Jms_Topic_Consumer_Persist.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月20日
 */

package com.evmtv.topic;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Jms_Topic_Consumer_Persist {
	
	private static final String ACTIVEMQ_URL = "tcp://192.168.146.128:61616";
	private static final String ACTIVEMQ_TOPIC_NAME = "Topic-Persist";

	public static void main(String[] args) throws JMSException, IOException {

		System.out.println("我是3号消费者王五");
		
		// 1.创建连接工厂，按照给定的URL，采用默认的用户名密码
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		
		// 2.通过连接工厂,获得connection,设置connectionID
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.setClientID("王五");
		
		// 3.创建会话session
		// 两个参数transacted=事务,acknowledgeMode=确认模式(签收)
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		// 4.创建目的地(具体是队列queue还是主题topic)
		Topic topic = session.createTopic(ACTIVEMQ_TOPIC_NAME);
		
		// 5.通过session创建持久化订阅
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark");
		
		// 6.启动连接
		connection.start();
		
		// 7.接收消息
		topicSubscriber.setMessageListener(message -> {
			
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("收到的持久化订阅消息: " + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		topicSubscriber.close();
		session.close();
		connection.close();
		
		/**
		 * 一定要先运行一次消费者,类似于像MQ注册,我订阅了这个主题 然后再运行主题生产者
		 * 无论消费着是否在线,都会接收到,在线的立即接收到,不在线的等下次上线把没接收到的接收
		 */
	}
}
