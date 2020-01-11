package com.learning.java8.threads;

public class DeadLockTest {

	public static void main(String[] args) throws InterruptedException {
		KeyHolderClass obj = new KeyHolderClass();
		Runnable r1 = ()->obj.a();
		Runnable r2 = ()->obj.b();
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}
}






class KeyHolderClass {
	
	// Please make note : in diff article some called key/lock/monitor.
	final Object key1 = new Object();
	final Object key2 = new Object();
	
	void a() {
		synchronized (key1) {
			System.out.println("Inside a method and acquired key1...");
			b();
		}
		
	}
	
	 void b() {
		 synchronized (key2) {
			 System.out.println("Inside b method and acquired key2...");
				c();
		}
	}
	
	 void c() {
		 synchronized (key1) {
				System.out.println("Inside c method and acquired key1...");
			}
	}
	
}
