package com.learning.java8.predicates;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 
 * @author vijay
 *
 */
public class PredicateChain {

	List<String> names = Arrays.asList("Adam", "Alexander", "John", "Tom");
	List<String> resultList = new ArrayList<>();
	List<Predicate<String>> allPredicates = new ArrayList<Predicate<String>>();
    
    
	public void predicateChainProcess() {

		//Way one with two filter 
		System.out.println("============");
		resultList = names.stream().filter(o -> o.startsWith("A")).filter(o -> o.length() > 4).collect(toList());
		resultList.forEach(System.out::println);
		System.out.println("============");
		resultList = names.parallelStream().filter(name->name.startsWith("A") && name.length()>4).collect(toList());
		resultList.forEach(System.out::println);
		System.out.println("============");
		resultList = names.parallelStream().filter(name->name.startsWith("A") || name.length()>4).collect(toList());
		resultList.forEach(System.out::println);
		
		System.out.println("============");
		
        allPredicates.add(str -> str.startsWith("A"));
        allPredicates.add(str -> str.contains("d"));
        allPredicates.add(str -> str.length() > 4);
        resultList = names.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(toList());
        resultList.forEach(System.out::println);
        

	}
}
