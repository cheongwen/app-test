package com.crystal_optech.app.testcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crystal_optech.app.tools.Log;

public class BaseCase {
	

	private static final Logger LOG = LoggerFactory.getLogger(BaseCase.class);
	
	public static void main(String[] args) {
		LOG.info("test log");
		LOG.debug("TEST LOG");
		LOG.error("TEST LOGSSSSSS");
	}
	
	
}
