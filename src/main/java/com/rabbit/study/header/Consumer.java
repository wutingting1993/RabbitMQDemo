package com.rabbit.study.header;

import java.util.HashMap;
import java.util.Map;

import com.rabbit.study.comm.ConsumerClient;
import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by WuTing on 2017/11/7.
 */
public class Consumer {

	public static final String EXCHANGE_NAME = "header-exchange";

	public static void main(String[] args) throws Exception {
		RabbitClient receive = new RabbitClient();
		ConnectionFactory factory = receive.createConnectionFactory();
		final Channel channel = receive.createChannel(factory);

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("x-match", "any");
		headers.put("name", "zhangsan2");

		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, EXCHANGE_NAME, "", headers);

		System.out.println("Start receive message: ");

		channel.basicConsume(queue, false, new ConsumerClient(channel));
	}
}
