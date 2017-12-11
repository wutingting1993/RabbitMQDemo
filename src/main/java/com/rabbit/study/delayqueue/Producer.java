package com.rabbit.study.delayqueue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by WuTing on 2017/11/7.
 */
public class Producer {

	private static final String MAIN_QUEUE = "MAIN_QUEUE";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String DELAY_QUEUE = "DELAY_QUEUE";

	public static void main(String[] args) throws Exception {
		RabbitClient producer = new RabbitClient();
		ConnectionFactory factory = producer.createConnectionFactory();
		Channel channel = producer.createChannel(factory);

		channel.queueDeclare(DELAY_QUEUE, true, false, false, null);
		channel.queueBind(DELAY_QUEUE, "amq.direct", DELAY_QUEUE);

		HashMap<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("x-dead-letter-exchange", "amq.direct");
		arguments.put("x-dead-letter-routing-key", DELAY_QUEUE);

		channel.queueDeclare(MAIN_QUEUE, true, false, false, arguments);

		for (int i = 0; i < 10; i++) {
			String expiration = "" + (1+ i) * 6000;
			String message = "hello world, " + DATE_FORMAT.format(new Date()) + ", expiration=" + expiration;
			System.out.println("[x] send '" + message);
			AMQP.BasicProperties properties = new AMQP.BasicProperties()
				.builder()
				.expiration(expiration)
				.build();
			channel.basicPublish("", MAIN_QUEUE, properties, message.getBytes());
		}

		producer.close();
	}
}
