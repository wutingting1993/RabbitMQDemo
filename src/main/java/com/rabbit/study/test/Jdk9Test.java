package com.rabbit.study.test;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

/**
 * Created by WuTing on 2017/11/30.
 */
public class Jdk9Test {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println(Map.of("1", 1, "2", 1));
		System.out.println(List.of(1, 2, 3, 4, 5));
		System.out.println(Set.of(1, 2, 3, 4, 5));
		IntStream.iterate(1, i -> i < 10, i -> i + 1).forEach(System.out::println);

		MyInterface myInterface = new MyClass();
		myInterface.anotherDefaultMethod();
		myInterface.interfaceMethodWithDefault();
		myInterface.normalInterfaceMethod();

		System.out.println();

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest req = HttpRequest.newBuilder(URI.create("http://www.google.com"))
			.header("User-Agent", "Java")
			.GET()
			.build();

		HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandler.asString());
		System.out.println(resp.body());




	}
}
