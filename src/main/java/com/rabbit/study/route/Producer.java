package com.rabbit.study.route;

import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by WuTing on 2017/11/7.
 */
public class Producer {

	public static final String EXCHANGE_NAME = "LOG";

	public static void main(String[] args) throws Exception {
		RabbitClient producer = new RabbitClient();
		ConnectionFactory factory = producer.createConnectionFactory();
		Channel channel = producer.createChannel(factory);
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		String routingKey = "info";

		for (int i = 0; i < 10; i++) {
			String message = "publish message, " + routingKey + ", " + System.currentTimeMillis();
			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());

			System.out.println("[x] response '" + message + "'");
		}

		producer.close();
	}
}
