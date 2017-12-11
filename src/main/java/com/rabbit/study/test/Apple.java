package com.rabbit.study.test;

/**
 * Created by WuTing on 2017/11/24.
 */
public class Apple {
	private Integer weight;
	private String name;
	private String make;

	public Apple(String name, String make, Integer weight) {
		this.weight = weight;
		this.name = name;
		this.make = make;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	@Override
	public String toString() {
		return name+":"+make+":"+ weight;
	}
}
