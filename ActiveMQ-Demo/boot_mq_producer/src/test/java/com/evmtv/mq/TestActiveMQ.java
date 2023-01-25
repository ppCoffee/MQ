/**
 * TestActiveMQ.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月22日
 */

package com.evmtv.mq;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import com.evmtv.mq.service.Queue_Producer;
import com.evmtv.mq.service.Topic_Producer;

@SpringBootTest
@WebAppConfiguration
public class TestActiveMQ {
	
	@Resource
	private Queue_Producer queue_producer;
	
	@Resource
	private Topic_Producer topic_producer;
	
	@Test
	public void testSend() {
		
//		queue_producer.producerMsg();
		
		topic_producer.producer();
	}
}
