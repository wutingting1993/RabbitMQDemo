package com.rabbit.study.delayqueue;

import com.rabbit.study.comm.ConsumerClient;
import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by WuTing on 2017/11/7.
 */
public class Consumer {
	private final static String DELAY_QUEUE = "DELAY_QUEUE";

	public static void main(String[] args) throws Exception {
		RabbitClient receive = new RabbitClient();
		ConnectionFactory factory = receive.createConnectionFactory();
		Channel channel = receive.createChannel(factory);
		channel.queueDeclare(DELAY_QUEUE, true, false, false, null);

		System.out.println("[*]等待消息。要退出按CTRL + C");
		channel.basicConsume(DELAY_QUEUE, false, new ConsumerClient(channel));
	}
}
