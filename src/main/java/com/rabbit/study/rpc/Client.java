package com.rabbit.study.rpc;

/**
 * Created by WuTing on 2017/11/9.
 */
public class Client {
	public static void main(String[] args) throws Exception {
		RPCClient client = new RPCClient();
		System.out.println(" [x] Consumer message：");
		client.call("rpc-queue", "hello, zhangsan " + System.currentTimeMillis());
		client.close();
		System.out.println("client closed");
	}
}