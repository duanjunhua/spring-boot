package com.michael.msg.listener;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiveListener {

	//Personal channel 
	@StreamListener("rabbit")
	public void receive(byte[] message) {
		System.out.println("MessageReceiveListener rev msg: " + new String(message));
	}
	
	@StreamListener(Processor.INPUT)
	public void source(Object payload) {
		System.out.println("Received Msg By Default Source class : " + payload);
	}
}
