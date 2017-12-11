package com.rabbit.study.workqueue;

import com.rabbit.study.comm.ConsumerClient;
import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by WuTing on 2017/11/7.
 */
public class Consumer {
	private final static String QUEUE_NAME = "hello-world";

	public static void main(String[] args) throws Exception {
		RabbitClient receive = new RabbitClient();
		ConnectionFactory factory = receive.createConnectionFactory();
		final Channel channel = receive.createChannel(factory);
		receive.queueDeclare(channel, QUEUE_NAME, true);
		channel.basicQos(1);

		System.out.println("[*]等待消息。要退出按CTRL + C");

		channel.basicConsume(QUEUE_NAME, false, new ConsumerClient(channel));
	}
}
