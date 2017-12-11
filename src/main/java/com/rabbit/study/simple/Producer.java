package com.rabbit.study.simple;

import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by WuTing on 2017/11/7.
 */
public class Producer {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		RabbitClient producer = new RabbitClient();
		ConnectionFactory factory = producer.createConnectionFactory();
		Channel channel = producer.createChannel(factory);
		producer.queueDeclare(channel, QUEUE_NAME, false);

		String message = "hello world, " + System.currentTimeMillis();
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

		System.out.println("[x] response '" + message + "'");
		producer.close();
	}
}
