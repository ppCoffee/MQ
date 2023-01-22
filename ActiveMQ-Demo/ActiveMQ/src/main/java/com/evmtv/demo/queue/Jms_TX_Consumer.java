/**
 * Jms_TX_Consumer.java
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
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Jms_TX_Consumer {
	
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
		// 两个参数transacted=事务,acknowledgeMode=确认模式(签收)  事务>签收
		Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
		
		// 4.创建目的地(具体是队列queue还是主题topic)
		Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
		
		// 5.创建消息的消费者,指定消费哪一个队列里面的消息
		MessageConsumer messageConsumer = session.createConsumer(queue);
		
		boolean allReceive = true;
		
		int i = 3;
		while(true) {
			TextMessage textMessage = (TextMessage) messageConsumer.receive(3000L);
			if (textMessage != null && textMessage instanceof TextMessage) {
				
				try {
					
					if(i == 2) {
						i = 1 / 0 ;
					}
					
					System.out.println("****消费者接收到的消息:  " + textMessage.getText());
//					textMessage.acknowledge();
					i++;
					
				}catch (Exception e) {
					System.out.println("出现异常，消费失败，放弃消费");
					
					session.rollback();
					allReceive = false;
					break;
				}
				
			} else {
				break;
			}
		}
		
		if(allReceive)
			session.commit();
		// 6.通过监听的方式消费消息
//		messageConsumer.setMessageListener(new MessageListener() {
//			
//			int a = 0;
//
//			@Override
//			public void onMessage(Message message) {
//				if (message instanceof TextMessage) {
//					try {
//						if (a == 2) {
//							System.out.println(1 / 0);
//						}
//						TextMessage textMessage = (TextMessage) message;
//						System.out.println("***消费者接收到的消息:   " + textMessage.getText());
//						session.commit();
//						a = a + 1;
//					} catch (Exception e) {
//						System.out.println("出现异常，消费失败，放弃消费");
//						try {
//							session.rollback();
//							a = 0;
//						} catch (JMSException ex) {
//							ex.printStackTrace();
//						}
//					}
//				}
//			}
//		});
		
		// 7.关闭资源
		messageConsumer.close();
		session.close();
		connection.close();
	}
}
