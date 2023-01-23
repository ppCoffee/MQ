/**
 * MqConfig.java
 * Copyright(JAVA) EnRich DTV Group co.,Ltd
 * 功能描述：
 *   
 * 创建者：zhangzy@evmtv.com 
 * 编辑者: zhangzy@evmtv.com
 * 2023年1月22日
 */

package com.evmtv.mq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

@Component
@EnableJms //开启Springboot的Jms
public class MqConfig {

    @Value("${myQueueName}")
    private String myQueueName;

    @Bean
    ActiveMQQueue queue() {
        //创建一个ActiveMQQueue
        return new ActiveMQQueue(myQueueName);
    }
    
}
