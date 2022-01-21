package com.ktech.starter.utilities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;



import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.fasterxml.uuid.impl.TimeBasedGenerator;


public class UUIDGenerator {
	
	static final private TimeBasedGenerator  timeBasedGenerator;
	static final private RandomBasedGenerator randomBasedGenerator;
	
	static
	{
		timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
		randomBasedGenerator = Generators.randomBasedGenerator();
	}
	
	public static UUID getTimeBasedUUID(){
		UUID ret;
		synchronized(timeBasedGenerator)
		{
			ret = timeBasedGenerator.generate();
		}
		return ret;
	}
	
	public static UUID getRandomBasedUUID(){
		
		return randomBasedGenerator.generate();
		
	}
	
	
	public static LocalDateTime getTimeFromUUID(UUID uuid){
		
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(uuid.timestamp()), ZoneId.systemDefault());
	}

}
