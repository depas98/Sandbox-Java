package com.depas.test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskTest {

	Timer timer;
	 
	public TimerTaskTest(int seconds) {
		timer = new Timer();
//		timer.schedule(new TimerTaskReminder(), seconds * 1000);
		
		timer.schedule(new TimerTaskReminder(), 0, // initial delay
				1 * 1000); // subsequent rate
	}
 
	class TimerTaskReminder extends TimerTask {
		public void run() {
			System.out.format("Timer Task Finished..!%n");
			timer.cancel(); // Terminate the timer thread
		}
	}
	
	public static void main(String[] args) {		
		new TimerTaskTest(5);
		System.out.format("Task scheduled.. Now wait for 5 sec to see next message..%n");
	}

}
