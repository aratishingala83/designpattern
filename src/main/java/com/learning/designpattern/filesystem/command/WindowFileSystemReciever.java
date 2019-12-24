package com.learning.designpattern.filesystem.command;

public class WindowFileSystemReciever implements FileSystemReciever {

	@Override
	public void openFile() {
		System.out.println("W Open File...");

	}

	@Override
	public void closeFile() {
		System.out.println("W Close File...");

	}

	@Override
	public void writeFile() {
		System.out.println("W Write File...");

	}

}
