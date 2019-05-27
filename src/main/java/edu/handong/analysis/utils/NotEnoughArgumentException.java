package edu.handong.analysis.utils;

public class NotEnoughArgumentException extends Exception{

	public NotEnoughArgumentException() {
		super("There is an error, CLI EXCEPTION. You should put a file path.");
	}

	NotEnoughArgumentException(String message){
		super(message);
	}
}
