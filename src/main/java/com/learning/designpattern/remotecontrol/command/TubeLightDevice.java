package com.learning.designpattern.remotecontrol.command;

public class TubeLightDevice implements RecieverDevice {

	@Override
	public void on() {
		System.out.println("On...");	

	}

	@Override
	public void off() {
		System.out.println("Off...");

	}

}
