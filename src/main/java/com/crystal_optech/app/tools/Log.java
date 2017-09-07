package com.crystal_optech.app.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Log{
	
	private static Logger log; 
	
	public Log(Class<?> cls) {
		log = LoggerFactory.getLogger(cls.getName());
	}

}
