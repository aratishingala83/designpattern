package com.learning.designpattern.filesystem.command;

public class OpenFileCommand implements Command {
	
	FileSystemReciever reciver;
	
	public OpenFileCommand(FileSystemReciever reciever) {
		this.reciver = reciever;
	}

	@Override
	public void execute() {
		reciver.openFile();
	}

}
