package com.learning.java8.threads;

public class ThreadJoinTest {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Main started...");
		
		Runnable r =   ()->{
			System.out.println("Current thread name : "+ Thread.currentThread().getName());
			sleep();
		};
		
		Thread t = new Thread(r);
		t.start();
		
		/**
		    Output WITHOUT join method
				    Main started...
				    Main completed...
					Current thread name : Thread-0
		            
		 */
		t.join(); // After ward steps will not executed until thread t finish it's operation.
		
		/**
	    Output WITH method
				    Main started...
					Current thread name : Thread-0
			        Main completed...
	    */
		
		
		System.out.println("Main completed...");
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
