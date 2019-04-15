package com.michael.msg.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageReceiverService {

	@Input("rabbit")		//declare channel name
	SubscribableChannel channel();
}
