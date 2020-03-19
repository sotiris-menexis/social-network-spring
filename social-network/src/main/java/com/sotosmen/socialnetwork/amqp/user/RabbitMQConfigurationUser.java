package com.sotosmen.socialnetwork.amqp.user;

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
public class RabbitMQConfigurationUser {
	@Value("${user.rabbitmq.exchange}")
	private String userExchange;
	@Value("${user.rabbitmq.queuename.post}")
	private String userQueueNamePost;
	@Value("${user.rabbitmq.queuename.put}")
	private String userQueueNamePut;
	@Value("${user.rabbitmq.queuename.delete}")
	private String userQueueNameDelete;
	@Bean(name="userQueuePost")
	Queue queuePost() {
		return new Queue(userQueueNamePost,false);
	}
	@Bean(name="userQueuePut") 
	Queue queuePut() {
		return new Queue(userQueueNamePut,false);
	}
	@Bean(name="userQueueDelete") 
	Queue queueDelete() {
		return new Queue(userQueueNameDelete,false);
	}
	@Bean(name="directExchangeUser")
	DirectExchange directExchangeUser() {
		return new DirectExchange(userExchange);
	}
	@Bean(name="bindingPostUser")
	Binding bindingPostUser(@Qualifier("userQueuePost")Queue queue
						   ,@Qualifier("directExchangeUser") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingPutUser")
	Binding bindingPutUser(@Qualifier("userQueuePut")Queue queue
						  ,@Qualifier("directExchangeUser") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingDeleteUser")
	Binding bindingDeleteUser(@Qualifier("userQueueDelete")Queue queue
							 ,@Qualifier("directExchangeUser") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean
	public MessageConverter messageConverterUser() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean(name="rabbitTemplateUser")
	public RabbitTemplate rabbitTemplateUser(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverterUser());
		return rabbitTemplate;
	}
}
