package com.rabbit.study.route;

import com.rabbit.study.comm.ConsumerClient;
import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by WuTing on 2017/11/7.
 */
public class Consumer {
	public static final String EXCHANGE_NAME = "LOG";

	public static void main(String[] args) throws Exception {
		RabbitClient receive = new RabbitClient();
		ConnectionFactory factory = receive.createConnectionFactory();
		final Channel channel = receive.createChannel(factory);
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, EXCHANGE_NAME, "info");
		channel.queueBind(queue, EXCHANGE_NAME, "error");

		channel.basicConsume(queue, false, new ConsumerClient(channel));
	}
}
