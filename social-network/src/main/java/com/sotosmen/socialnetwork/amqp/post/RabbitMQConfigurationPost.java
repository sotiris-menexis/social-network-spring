package com.sotosmen.socialnetwork.amqp.post;

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
public class RabbitMQConfigurationPost {
	@Value("${post.rabbitmq.exchange}")
	private String postExchange;
	@Value("${post.rabbitmq.queuename.post}")
	private String postQueueNamePost;
	@Value("${post.rabbitmq.queuename.put}")
	private String postQueueNamePut;
	@Value("${post.rabbitmq.queuename.delete}")
	private String postQueueNameDelete;
	
	@Bean(name="postQueuePost")
	Queue queuePost() {
		return new Queue(postQueueNamePost,false);
	}
	@Bean(name="postQueuePut")
	Queue queuePut() {
		return new Queue(postQueueNamePut,false);
	}
	@Bean(name="postQueueDelete")
	Queue queueDelete() {
		return new Queue(postQueueNameDelete,false);
	}
	@Bean
	DirectExchange directExchangePost() {
		return new DirectExchange(postExchange);
	}
	@Bean
	Binding bindingPostPost(@Qualifier("postQueuePost") Queue queue,DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean
	Binding bindingPostPut(@Qualifier("postQueuePut") Queue queue,DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean
	Binding bindingPostDelete(@Qualifier("postQueueDelete") Queue queue,DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean
	public MessageConverter messageConverterPost() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean
	public RabbitTemplate rabbitTemplatePost(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverterPost());
		return rabbitTemplate;
	}
}
