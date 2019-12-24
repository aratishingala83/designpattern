package com.learning.designpattern.remotecontrol.command;

public class LightOffCommand implements Command {
	
	TubeLightDevice tubeLightDevice;
	
	public LightOffCommand( TubeLightDevice tubeLightDevic ){
		this.tubeLightDevice = tubeLightDevic;
	}

	@Override
	public void execute() {
		tubeLightDevice.off();
	}

}
