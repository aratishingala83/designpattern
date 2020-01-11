package com.learning.java8.optional;

public class OptionalApp {
	
	public static void main(String[] args) {
		OptionalExample example = new OptionalExample();
		
		//Optional HandleException for null
		try {
			example.getRollNumber();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
