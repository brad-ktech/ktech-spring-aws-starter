package com.ktech.starter.utilities;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.util.StopWatch;

public class Chronometer {
	
	
	
	private static Map<String, StopWatch> watches = null;
	
	private Chronometer(){
		
	}
	
	public static void start(String key){
		
		StopWatch watch =  new StopWatch();
		watch.start();
		
		if(watches==null){
			watches =  new Hashtable<>();
		}
		
		watches.put(key, watch);
	}
	
	
	public static void stop(String key){
		
		
		
		if(watches != null && watches.containsKey(key)){
			
			StopWatch watch = watches.remove(key);
			if(watch.isRunning()){
				watch.stop();
			}
			
			System.out.println(watch.prettyPrint());
			
		}
		
		
		
		
	}
	
	

}
