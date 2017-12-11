package com.rabbit.study.rpc;

import java.io.IOException;

import com.rabbit.study.comm.RabbitClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Created by WuTing on 2017/11/7.
 */
public class RPCServer {
	public String queue;
	private static RabbitClient client;

	public RPCServer(String queue) throws Exception {
		if (client == null) {
			client = new RabbitClient();
			ConnectionFactory factory = client.createConnectionFactory();
			client.createChannel(factory);
			Channel channel = client.createChannel(factory);
			client.queueDeclare(channel, queue, false);
			channel.basicQos(1);
		}
		this.queue = queue;

	}

	public void response() throws IOException {

		System.out.println(" [x] Awaiting RPC requests：");

		Consumer consumer = new DefaultConsumer(client.getChannel()) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
				byte[] body) throws IOException {
				AMQP.BasicProperties replyProps = new AMQP.BasicProperties()
					.builder()
					.correlationId(properties.getCorrelationId())
					.build();

				String response = new String(body) + ",  server response";

				client.getChannel().basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
				client.getChannel().basicAck(envelope.getDeliveryTag(), false);

				synchronized (this) {
					this.notify();
				}
			}
		};

		client.getChannel().basicConsume(queue, false, consumer);

		while (true) {
			synchronized (consumer) {
				try {
					consumer.wait();
				} catch (Exception e) {
					e.printStackTrace();
					client.close();
					break;
				}
			}
		}
	}
}
