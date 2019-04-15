package com.michael.msg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michael.msg.channel.MessageProviderService;

@RestController
@RequestMapping("message-provider")
public class MessageProviderController {

	@Autowired
	private MessageProviderService msgProService;
	
	@Autowired
	Processor processor;
	
	@GetMapping("/send")
	public void send() {
		
		Message<byte[]> msg = MessageBuilder.withPayload("Hello World!".getBytes()).build();
		msgProService.channel().send(msg);
		
		System.out.println("Send Successful");
	}
	
	@GetMapping("/source")
	public void source() {
		
		Message<byte[]> msg = MessageBuilder.withPayload("Hello World With Spring Cloud Stream Default of Source!".getBytes()).build();
		processor.input().send(msg);
		
		System.out.println("Send winth Source Successful");
	}
}
