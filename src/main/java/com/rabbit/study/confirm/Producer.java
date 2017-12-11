package com.rabbit.study.confirm;

import java.io.IOException;

import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by WuTing on 2017/11/7.
 */
public class Producer {

	public static final String QUEUE_NAME = "confirm_queue";

	private static volatile long deliveryTag = 0;

	public static void main(String[] args) throws Exception {

		RabbitClient producer = new RabbitClient();
		ConnectionFactory factory = producer.createConnectionFactory();
		Channel channel = producer.createChannel(factory);
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		channel.confirmSelect();

		final long confirmId = deliveryTag + 1;
		channel.addConfirmListener(new ConfirmListener() {
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				if (confirmId == deliveryTag) {
					System.out.println(confirmId + ", Confirm received!");
				}
			}

			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				if (confirmId == deliveryTag) {
					System.out.println(confirmId + ", message lost!");
				}
			}
		});
		String message = "publish message, " + System.currentTimeMillis();
		System.out.println("[x] send '" + message + "'");

		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		if (!channel.waitForConfirms()) {
			System.out.println("send message failed.");
		}
		producer.close();
	}
}
