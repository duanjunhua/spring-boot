package com.demo.ribbon;

import org.springframework.stereotype.Component;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;

/**
 * @author Michael
 * @Date: 2018年12月4日_下午4:31:51
 * @Version: v0.1
 */
@Component
public class FeignDemoPingUrl implements IPing {

	private IPing pingUrl;
	
	public FeignDemoPingUrl(IPing ping) {
		this.pingUrl = ping;
	}


	public boolean isAlive(Server server) {
		System.out.println(server.getHost() + " : " + server.getHostPort());
		return pingUrl.isAlive(server);
	}
	
}
