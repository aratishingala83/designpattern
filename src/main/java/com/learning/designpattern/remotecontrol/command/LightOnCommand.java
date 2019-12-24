package com.learning.designpattern.remotecontrol.command;

public class LightOnCommand implements Command {

	TubeLightDevice lightDevice;
	
	public LightOnCommand(TubeLightDevice lightDevice) {
		this.lightDevice = lightDevice;
	}
	
	@Override
	public void execute() {
		lightDevice.on();
	}

}
