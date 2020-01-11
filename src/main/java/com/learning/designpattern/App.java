package com.learning.designpattern;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        
        
        List<Person> result = isPersonNameStartWithV(Person.getBuyerList(), p->p.getName().startsWith("V"));
        result.stream().forEach(p->System.out.println("Name start with V n Name is : "+p.getName()));
        
         
        
         intPredicateTest();
         intToDoubleTest();
        
        //Method reference example
        
        //Constructor method reference 
        Supplier<Person> person = Person::new;
        person.get();
        
        //Static method reference
        Supplier<List<Person>> persons = Person::getBuyerList;
        persons.get().stream().forEach(System.out::println);
        
        //Instance method reference
        Person p = person.get();
        Supplier<String> pName = p::getName;
        pName.get();
        
           
    }

	private static void intToDoubleTest() {
		IntToDoubleFunction f=i->Math.sqrt(i);
		System.out.println("intToDoubleTest = "+f.applyAsDouble(9));
		
	}

	private static void intPredicateTest() {
		int[] intArray = {0,5,10,15,20,25,30,35};
        IntPredicate p  = i -> i%2==0; //IntPredicate always accept int as argument instead of Object - T
        for(int x :intArray) {
        	if (p.test(x)) {
        		System.out.println("Even number "+x);
        	}
        }
	}
	
	
	static List<Person> isPersonNameStartWithV(List<Person> persons , Predicate<Person> predicate) {
		List<Person> result = new ArrayList<Person>();
		for(Person p : persons) {
			if(predicate.test(p)) {
				result.add(p);
				
			}
		}
		
		return result;
	}
}



class Person {
	
	private String name;
	private int price;
	
	
	public Person() {
		System.out.println("Person no-args constructor...");
	}
	public Person(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}
	public String getName() {
		System.out.println("Get Person Name...");
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public static List<Person> getBuyerList(){
		List< Person> list = new ArrayList<Person>();
		list.add(new Person("Vijay", 0));
		list.add(new Person("Vikas", 0));
		list.add(new Person("CC", 0));
		list.add(new Person("DD", 0));
		list.add(new Person("Vishnu", 0));
		return list;
		
	}
	
	
	
}
