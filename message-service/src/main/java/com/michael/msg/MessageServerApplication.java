package com.michael.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

import com.michael.msg.channel.MessageProviderService;

//@EnableBinding(MessageProviderService.class)	//Bind channel
@EnableBinding(value = {MessageProviderService.class, Processor.class})
@SpringBootApplication
public class MessageServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageServerApplication.class, args);
	}

}
