package com.learning.designpattern.filesystem.command;

public class DemoFileSystemClient {
	
	public static void main(String[] args) {
		
		
		FileSystemReciever reciever = new WindowFileSystemReciever(); // Reciever
		Command command = new OpenFileCommand(reciever); //Command encaptulate Reciever
		
		//Below will be common code for every command
		FileSystemInvoker invoker = new FileSystemInvoker(command); //Invoker
		invoker.execute();
	
	}

}
