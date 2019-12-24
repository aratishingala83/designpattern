package com.learning.designpattern.remotecontrol.command;

public class DeviceInvoker {

	private Command command;
	
	public DeviceInvoker(Command command) {
		this.command = command;
	}
	
	public void execute() {
		this.command.execute();
	}
	
}
