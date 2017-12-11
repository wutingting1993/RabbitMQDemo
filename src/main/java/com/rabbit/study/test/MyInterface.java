package com.rabbit.study.test;

/**
 * Created by WuTing on 2017/12/1.
 */
public interface MyInterface {
	void normalInterfaceMethod();

	default void interfaceMethodWithDefault() {
		init();
		normalInterfaceMethod();
	}

	default void anotherDefaultMethod() {
		init();
		System.out.println("anotherDefaultMethod");
	}

	private void init() {
		System.out.println("Initializing");
	}
}
