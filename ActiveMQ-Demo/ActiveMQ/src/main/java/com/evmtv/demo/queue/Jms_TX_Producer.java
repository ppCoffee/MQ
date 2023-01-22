/**
 * Jms_TX_Producer.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月20日
 */

package com.evmtv.demo.queue;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Jms_TX_Producer {
	
	private static final String ACTIVEMQ_URL = "tcp://192.168.146.128:61616";
//	private static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
	private static final String ACTIVEMQ_QUEUE_NAME = "Queue-TX";

	public static void main(String[] args) throws JMSException {

		// 1.创建连接工厂，按照给定的URL，采用默认的用户名密码
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		
		
		// 2.通过连接工厂,获得connection并启动访问
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		
		// 3.创建会话session
		// 两个参数transacted=事务,acknowledgeMode=确认模式(签收)
		// 开启事务需要commit
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		
		// 4.创建目的地(具体是队列queue还是主题topic)
		Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
		
		// 5.创建消息的生产者,并设置不持久化消息
		MessageProducer producer = session.createProducer(queue);
		
		// 6.通过使用消息生产者,生产三条消息,发送到MQ的队列里面
		try {
			for (int i = 0; i < 3; i++) {
				TextMessage textMessage = session.createTextMessage("tx msg--" + i);
				producer.send(textMessage);
			}
			// 7.提交事务
			session.commit();
			System.out.println("消息发送完成");
		} catch (Exception e) {
			System.out.println("出现异常,消息回滚");
			session.rollback();
		} finally {
			// 8.关闭资源
			producer.close();
			session.close();
			connection.close();
		}

	}
}
