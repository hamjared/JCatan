package com.JCatan;

public class Timer {
	
	static long startTime;
	static long endTime;
	static long duration;

	public static void startTimer() {
		System.out.println("Starting timer...");
		startTime = System.currentTimeMillis();
	}
	
	public static void endTimer() {
		endTime = System.currentTimeMillis();
		System.out.println("Ending timer...");
		duration = endTime - startTime;
		System.out.println("Duration to run: " + duration + " milliseconds");
	}
	
}
