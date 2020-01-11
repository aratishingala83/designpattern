package com.learning.java8;

import java.util.stream.IntStream;

public class IntStreamTest {
	
	public static void main(String[] args) {
		System.out.println(IntStream.rangeClosed(1, 10).map(i->i+1).sum());
	}

}
