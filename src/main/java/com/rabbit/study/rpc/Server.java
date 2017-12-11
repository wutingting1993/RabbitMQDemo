package com.rabbit.study.rpc;

/**
 * Created by WuTing on 2017/11/9.
 */
public class Server {
	public static void main(String[] args) throws Exception {
		RPCServer server = new RPCServer("rpc-queue");
		server.response();
		System.out.println("server closed");

	}
}
