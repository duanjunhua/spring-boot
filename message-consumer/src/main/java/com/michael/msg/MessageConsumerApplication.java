package com.michael.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

import com.michael.msg.channel.MessageReceiverService;

//@EnableBinding(MessageReceiverService.class)	//Bind channel
@EnableBinding(value = {MessageReceiverService.class, Processor.class})	//Processor(extend Source、Sink): Spring Cloud Stream provider default channel
@SpringBootApplication
public class MessageConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageConsumerApplication.class, args);
	}

}
