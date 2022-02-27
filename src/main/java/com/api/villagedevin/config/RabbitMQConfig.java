package com.api.villagedevin.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Bean
	public MessageConverter getMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue getReportQueue() {
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-message-ttl", 5000);
		return new Queue("devin.report", true, false, false, arguments);
	}

	@Bean
	public DirectExchange getReportExchange() {
		return new DirectExchange("direct.report", true, false);
	}

	@Bean
	public Binding getReportBindingReportQueueToReportExchange() {
		Map<String, Object> arguments = new HashMap<>();
		return new Binding("devin.report", Binding.DestinationType.QUEUE, "direct.report", "devin.report", arguments);
	}

	@Bean
	public Queue getReportQueueDLX() {
		Map<String, Object> arguments = new HashMap<>();

		return new Queue("devin.report.dlx", true, false, false, arguments);
	}

	@Bean
	public DirectExchange getReportExchangeDLX() {
		return new DirectExchange("direct.report.dlx", true, false);
	}

	@Bean
	public Binding getReportBindingReportQueueToReportExchangeDLX() {
		Map<String, Object> arguments = new HashMap<>();
		return new Binding("devin.report.dlx", Binding.DestinationType.QUEUE, "direct.report.dlx", "devin.report.dlx",
				arguments);
	}

}
