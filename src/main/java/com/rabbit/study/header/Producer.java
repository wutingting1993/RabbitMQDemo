package com.rabbit.study.header;

import java.util.HashMap;
import java.util.Map;

import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by WuTing on 2017/11/7.
 */
public class Producer {

	public static final String EXCHANGE_NAME = "header-exchange";

	public static void main(String[] args) throws Exception {

		RabbitClient producer = new RabbitClient();
		ConnectionFactory factory = producer.createConnectionFactory();
		Channel channel = producer.createChannel(factory);

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("name", "zhangsan1");

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);

		AMQP.BasicProperties properties = new AMQP.BasicProperties()
			.builder()
			.headers(headers)
			.build();

		for (int i = 0; i < 10; i++) {
			String message = "publish message,  " + System.currentTimeMillis();
			channel.basicPublish(EXCHANGE_NAME, "", properties, message.getBytes());
			System.out.println("[x] response '" + message + "'");
		}

		producer.close();
	}
}
