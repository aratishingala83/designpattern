package com.learning.designpattern.filesystem.command;

public class FileSystemInvoker {
	
	private Command command;
	
	public FileSystemInvoker( Command command) {
		this.command = command;
	}
	
	public void execute() {
		this.command.execute();
	}

}
