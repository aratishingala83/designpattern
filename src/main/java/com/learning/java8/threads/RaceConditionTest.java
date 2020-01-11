package com.learning.java8.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * RaceCondition is when two different thread try to perform read and write on
 * same object/resource at same time.
 * 
 * @author vijay
 *
 */
public class RaceConditionTest {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Mail method started");
		LongWrapper l = new LongWrapper(0L);
		
		//Loop 1000 times and increment long number
		Runnable r = () -> {
			for (int i = 0; i < 1_000; i++) {
				l.increamentL();
			}
		};

		//Create 100 threads; EACH thread call Task which do 1000 times to increment long value
		Thread[] threads = new Thread[1_000];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(r);
			threads[i].start();
		}
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
		
		//Here ideally 1000*1000 = 1000000 should get print 
		//but because of RACE condition this not print 1000000; it will print LESS THAN 100000
		
		//Solution :Method -increamentL should be synchronized then below line will print 100000
		System.out.println("Long value "+l.getL());
		
		System.out.println("Mail method completed");
	}

	public static void longRunningMethod(String arg) {
		System.out.println(arg + "...Started");
		sleep();
		System.out.println(arg + "...Completed");
	}

	private static void sleep() {
		try {

			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class LongWrapper {

	private long l;

	public LongWrapper(long l) {
		this.l = l;
	}

	public long getL() {
		return l;
	}

	 long increamentL() {
		return l = l + 1;
	}
}
