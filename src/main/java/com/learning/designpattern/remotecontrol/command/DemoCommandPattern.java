package com.learning.designpattern.remotecontrol.command;

public class DemoCommandPattern {
	
	public static void main(String[] args) { //Client
		
		TubeLightDevice tubeLightDevice = new TubeLightDevice(); // Receiver
		Command lightOnCommand = new LightOnCommand( tubeLightDevice ); // command	-Encaptulate Reciever	
		DeviceInvoker invokerOnDevice = new DeviceInvoker( lightOnCommand ); //invoker -Encaptulate Command
		invokerOnDevice.execute(); //execute
		
		Command lightOffCommand = new LightOffCommand( tubeLightDevice ); // command		
		DeviceInvoker invokerOffDevice = new DeviceInvoker( lightOffCommand ); //invoker
		invokerOffDevice.execute(); //execute
		
		
	}

}
