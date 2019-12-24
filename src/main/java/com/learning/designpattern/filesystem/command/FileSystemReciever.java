package com.learning.designpattern.filesystem.command;

public interface FileSystemReciever {
	
	void openFile();
	void closeFile();
	void writeFile();

}
