package com.standalone;

import lombok.extern.log4j.Log4j2;

/**
 * @author Michael
 * @Date: 2018年12月6日_下午4:52:11
 * @Version: v0.1
 */
@Log4j2
public class CustomXmlConfigService {

	public String testXmlFileConfigBean() {
		log.info("This is not a bean under springboot scan folder.");
		return "success called";
	}
}
