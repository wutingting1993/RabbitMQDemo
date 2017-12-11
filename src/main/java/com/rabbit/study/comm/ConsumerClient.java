package com.rabbit.study.comm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * Created by WuTing on 2017/11/10.
 */
public class ConsumerClient implements Consumer {
	private final Channel channel;
	private volatile String consumerTag;

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ConsumerClient(Channel channel) {
		this.channel = channel;
	}

	public void handleConsumeOk(String consumerTag) {
		this.consumerTag = consumerTag;
	}

	public void handleCancelOk(String consumerTag) {
	}

	public void handleCancel(String consumerTag) throws IOException {
	}

	public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
	}

	public void handleRecoverOk(String consumerTag) {
	}

	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
		byte[] body) throws IOException {
		try {
			String message = new String(body, "UTF-8");
			System.out.println("[x] Consumer '" + message + "', RoutingKey=" + envelope.getRoutingKey());
		} finally {
			System.out.println("have done, " + dateFormat.format(new Date())+"\n");
			channel.basicAck(envelope.getDeliveryTag(), false);
		}
	}

	public Channel getChannel() {
		return this.channel;
	}

	public String getConsumerTag() {
		return this.consumerTag;
	}
}
