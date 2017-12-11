package com.rabbit.study.rpc;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Created by WuTing on 2017/11/7.
 */
public class RPCClient {
	private static RabbitClient client;
	private String replyQueueName;
	private Channel channel;

	public RPCClient() throws Exception {
		if (client == null) {
			client = new RabbitClient();
			ConnectionFactory factory = client.createConnectionFactory();
			client.createChannel(factory);
			channel = client.createChannel(factory);
			replyQueueName = channel.queueDeclare().getQueue();
		}
	}

	public String call(String queue, String message) throws Exception {

		System.out.println(" [request] '" + message + "'");
		final String clusterId = UUID.randomUUID().toString();
		AMQP.BasicProperties props = new AMQP.BasicProperties()
			.builder()
			.correlationId(clusterId)
			.replyTo(replyQueueName)
			.build();

		channel.basicPublish("", queue, props, message.getBytes("UTF-8"));

		final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

		channel.basicConsume(replyQueueName, false, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
				byte[] body) throws IOException {
				if (clusterId.equals(properties.getCorrelationId())) {
					response.offer(new String(body, "UTF-8"));
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		});

		String msg = response.take();
		System.out.println(" [response] '" + msg + "'");
		return msg;
	}

	public void close() {
		client.close();
	}

}
