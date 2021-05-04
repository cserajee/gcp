package com.td.delivery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.td.delivery.service.DeliveryService;

@Configuration
public class GCPConfig {

	@Autowired
	DeliveryService deliveryService;

	@Bean
	public MessageChannel inputMessageChannel() {
		return new DirectChannel();
	}

	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
			@Qualifier("inputMessageChannel") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "GCPPackage-sub1");
		adapter.setOutputChannel(inputChannel);
		return adapter;
	}

	@ServiceActivator(inputChannel = "inputMessageChannel")
	public void messageReceiver(String payload,
			@Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
		Boolean status = deliveryService.saveDelivery(payload);
		System.out.println(status);
		message.ack();
	}

	@Bean
	@ServiceActivator(inputChannel = "deliveryOutputChannel")
	public PubSubMessageHandler messageSender(PubSubTemplate pubsubTemplate) {
		return new PubSubMessageHandler(pubsubTemplate, "GCPDelivery");
	}

	@MessagingGateway(defaultRequestChannel = "deliveryOutputChannel")
	public interface PubsubOutboundGateway {
		void sendToPubsub(String text);
	}

}
