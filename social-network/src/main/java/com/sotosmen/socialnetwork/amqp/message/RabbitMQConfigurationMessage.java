package com.sotosmen.socialnetwork.amqp.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfigurationMessage {
	@Value("${message.rabbitmq.exchange}")
	private String messageExchange;
	@Value("${message.rabbitmq.queuename.post}")
	private String messageQueueNamePost;
	@Value("${message.rabbitmq.queuename.put}")
	private String messageQueueNamePut;
	@Value("${message.rabbitmq.queuename.delete}")
	private String messageQueueNameDelete;
	
	@Bean(name="messageQueuePost")
	Queue queuePost() {
		return new Queue(messageQueueNamePost,false);
	}
	@Bean(name="messageQueuePut")
	Queue queuePut() {
		return new Queue(messageQueueNamePut,false);
	}
	@Bean(name="messageQueueDelete")
	Queue queueDelete() {
		return new Queue(messageQueueNameDelete,false);
	}
	@Bean(name="directExchangeMessage")
	DirectExchange directExchangeMessage() {
		return new DirectExchange(messageExchange);
	}
	@Bean(name="bindingPostMessage")
	Binding bindingMessagePost(@Qualifier("messageQueuePost") Queue queue
						   ,@Qualifier("directExchangeMessage")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingPutMessage")
	Binding bindingMessagePut(@Qualifier("messageQueuePut") Queue queue
						  ,@Qualifier("directExchangeMessage")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingDeleteMessage")
	Binding bindingMessageDelete(@Qualifier("messageQueueDelete") Queue queue
							 ,@Qualifier("directExchangeMessage")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean
	public MessageConverter messageConverterMessage() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean(name="rabbitTemplateMessage")
	public RabbitTemplate rabbitTemplateMessage(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverterMessage());
		return rabbitTemplate;
	}
}
