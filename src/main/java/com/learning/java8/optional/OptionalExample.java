package com.learning.java8.optional;

import java.util.Optional;

public class OptionalExample {

	
	void throwExceptionJ8ForOptional() {
		Optional.ofNullable(getRollNumber()).orElseThrow(NullPointerException::new);
	}
	
	
	Integer getRollNumber() {
		return null;
	}
	
}
