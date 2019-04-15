package com.michael.msg.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface MessageProviderService {

	@Output("rabbit")		//declare channel name, bind
	SubscribableChannel channel();
}
