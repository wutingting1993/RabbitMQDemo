package com.rabbit.study.comm;

import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by WuTing on 2017/11/7.
 */
public class RabbitClient {

	protected Connection connect;

	protected Channel channel;
	protected ConnectionFactory factory;

	public ConnectionFactory createConnectionFactory(String username, String password, String host, Integer port)
		throws
		Exception {
		factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		return factory;
	}

	public ConnectionFactory createConnectionFactory() throws Exception {
		factory = new ConnectionFactory();
		return factory;
	}

	public Channel createChannel(ConnectionFactory factory) throws Exception {
		connect = factory.newConnection();
		channel = connect.createChannel();

		return channel;
	}

	public void queueDeclare(Channel channel, String queueName, boolean durable) throws Exception {
		channel.queueDeclare(queueName, durable, false, false, null);
	}
	public void queueDeclare(Channel channel, String queueName, boolean durable, Map<String, Object> properties) throws Exception {
		;
		channel.queueDeclare(queueName, durable, false, false, properties);
	}
	public void close() {

		try {
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Channel getChannel() {
		return channel;
	}

}
