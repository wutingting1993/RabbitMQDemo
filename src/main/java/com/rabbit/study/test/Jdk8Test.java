package com.rabbit.study.test;

import static java.util.stream.Collectors.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by WuTing on 2017/11/24.
 */
public class Jdk8Test {

	public static void main(String[] args) throws Exception {
		//processFile();
		//sort();

		List<Integer> numbers1 = new ArrayList<>();
		numbers1.add(25);
		numbers1.add(30);
		numbers1.add(40);
		numbers1.add(50);
		numbers1.add(60);
		numbers1.add(70);
		numbers1.add(80);
		System.out.println(getCollect(numbers1, 25));
		System.out.println(getCollect(numbers1, 50));
		System.out.println(getCollect(numbers1, 75));

		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		System.out.println(LocalDateTime.now(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter
			.ISO_LOCAL_DATE_TIME));
		System.out.println(LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		System.out.println(LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		List<Apple> list = new ArrayList<>();
		list.add(new Apple("name1", "make1", 123));
		list.add(new Apple("name5", "make1", 2));
		list.add(new Apple("name2", "make1", 3));
		list.add(new Apple("name1", "make3", 1));

		System.out.println(list.stream().map(Apple::getWeight).reduce(0, Integer::sum));
		System.out.println(list.stream().collect(summingDouble(Apple::getWeight)));

		list.stream().filter(apple -> apple.getMake().equalsIgnoreCase("make1"))
			.findAny()
			.ifPresent(apple -> System.out.println(apple));

		Map<String, Map<String, List<Apple>>> group = list.stream()
			.collect(
				groupingBy(Apple::getName,
					groupingBy(apple -> {
						if (apple.getWeight() < 2) {
							return "low";
						} else {
							return "high";
						}
					})));

		System.out.println(group);
		System.out.println(list.stream().collect(groupingBy(Apple::getName, averagingInt(Apple::getWeight))));
		System.out.println(
			list.stream()
				.collect(groupingBy(Apple::getName,
					collectingAndThen(maxBy(Comparator.comparing(Apple::getWeight)), Optional::get))));

		System.out.println(list.stream().allMatch(apple -> apple.getMake().equalsIgnoreCase("make1")));

		List<Integer> lens = list.stream().map(Apple::getName)
			.map(String::length)
			.collect(toList());

		System.out.println(lens);

		String[] strs = {"hello", "world"};
		System.out.println(Arrays.stream(strs).collect(joining(",")));
		List<String> strlist = Arrays.stream(strs)
			.map(str -> str.split(""))
			.flatMap(Arrays::stream)
			.distinct()
			.collect(toList());
		System.out.println(strlist);
	}

	public static List<Integer> getCollect(List<Integer> numbers1, Integer i) {
		return numbers1.stream()
			.filter(getIntegerPredicate(i))
			.collect(toList());
	}

	public static Predicate<Integer> getIntegerPredicate(Integer i) {
		return e -> e > i;
	}

	public static void sort() {
		List<Apple> list = new ArrayList<>();
		list.add(new Apple("name1", "make1", 123));
		list.add(new Apple("name5", "make1", 2));
		list.add(new Apple("name2", "make1", 3));
		list.add(new Apple("name1", "make3", 1));
		list.sort(Comparator.comparing(Apple::getMake)
			.thenComparing(Comparator.comparing(Apple::getName)));
		System.out.println(list);
		list.sort(Comparator.comparing(Apple::getMake).reversed());
		System.out.println(list);
	}

	public static void processFile() throws Exception {
		processFile((BufferedReader reader) -> reader.readLine() + reader.readLine());
		processFile((BufferedReader reader) -> reader.readLine());
	}

	private static void processFile(BufferReaderProcess process) throws Exception {
		FileReader in = new FileReader("F:\\jooq\\com\\navercorp\\ncp\\cla\\db\\Tables.java");
		BufferedReader reader = new BufferedReader(in);
		System.out.println(process.process(reader));
	}
}
