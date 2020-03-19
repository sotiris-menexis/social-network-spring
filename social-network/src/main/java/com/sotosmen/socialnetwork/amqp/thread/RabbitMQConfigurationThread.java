package com.sotosmen.socialnetwork.amqp.thread;

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
public class RabbitMQConfigurationThread {
	@Value("${thread.rabbitmq.exchange}")
	private String threadExchange;
	@Value("${thread.rabbitmq.queuename.post}")
	private String threadQueueNamePost;
	@Value("${thread.rabbitmq.queuename.put}")
	private String threadQueueNamePut;
	@Value("${thread.rabbitmq.queuename.delete}")
	private String threadQueueNameDelete;
	
	@Bean(name="threadQueuePost")
	Queue queuePost() {
		return new Queue(threadQueueNamePost,false);
	}
	@Bean(name="threadQueuePut")
	Queue queuePut() {
		return new Queue(threadQueueNamePut,false);
	}
	@Bean(name="threadQueueDelete")
	Queue queueDelete() {
		return new Queue(threadQueueNameDelete,false);
	}
	@Bean(name="directExchangeThread")
	DirectExchange directExchangeThread() {
		return new DirectExchange(threadExchange);
	}
	@Bean(name="bindingPostThread")
	Binding bindingPostThread(@Qualifier("threadQueuePost") Queue queue
							 ,@Qualifier("directExchangeThread") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingPutThread")
	Binding bindingPutThread(@Qualifier("threadQueuePut") Queue queue
							,@Qualifier("directExchangeThread") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingDeleteThread")
	Binding bindingDeleteThread(@Qualifier("threadQueueDelete") Queue queue
							   ,@Qualifier("directExchangeThread") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean
	public MessageConverter messageConverterThread() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean(name="rabbitTemplateThread")
	public RabbitTemplate rabbitTemplateThread(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverterThread());
		return rabbitTemplate;
	}
}
