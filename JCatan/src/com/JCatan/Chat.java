package com.JCatan;

import java.util.LinkedList;
import java.util.List;

public class Chat {
	
	List<String> chat;
	int maxLines;
	
	public Chat() {
		chat = new LinkedList<>();
		maxLines = 15;
	}
	
	public void addToChat(String string) {
		chat.add(string);
		if(chat.size() > maxLines) {
			chat.remove(0);
		}
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		
		for(String line: chat) {
			string.append(line).append("\n");
		}
		
		return string.toString();
	}
	
	
	
	

}
