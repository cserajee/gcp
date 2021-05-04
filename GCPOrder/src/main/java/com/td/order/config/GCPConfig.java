package com.td.order.config;

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
import com.td.order.service.OrderService;

@Configuration
public class GCPConfig {

	@Autowired
	OrderService orderService;

	@Bean
	public MessageChannel packageMessageChannel() {
		return new DirectChannel();
	} 
	
	@Bean
	public PubSubInboundChannelAdapter packageChannelAdapter(
			@Qualifier("packageMessageChannel") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "GCPPackage-sub");
		adapter.setOutputChannel(inputChannel);
		return adapter;
	}

	@ServiceActivator(inputChannel = "packageMessageChannel")
	public void packagemessageReceiver(String payload,
			@Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
		Boolean status = orderService.updatePackageStaus(payload);
		System.out.println(status);
		message.ack();
	}
	

	@Bean
	public MessageChannel deliveryMessageChannel() {
		return new DirectChannel();
	} 
	
	@Bean
	public PubSubInboundChannelAdapter deliveryChannelAdapter(
			@Qualifier("deliveryMessageChannel") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "GCPDelivery-sub");
		adapter.setOutputChannel(inputChannel);
		return adapter;
	}

	@ServiceActivator(inputChannel = "deliveryMessageChannel")
	public void deliverymessageReceiver(String payload,
			@Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
		Boolean status = orderService.updateDeliveryStaus(payload);
		System.out.println(status);
		message.ack();
	}
	
	
	@Bean
	@ServiceActivator(inputChannel = "orderOutputChannel")
	public PubSubMessageHandler messageSender(PubSubTemplate pubsubTemplate) {
		return new PubSubMessageHandler(pubsubTemplate, "GCPOrder");
	}

	@MessagingGateway(defaultRequestChannel = "orderOutputChannel")
	public interface PubsubOutboundGateway {
		void sendToPubsub(String text);
	}

}
