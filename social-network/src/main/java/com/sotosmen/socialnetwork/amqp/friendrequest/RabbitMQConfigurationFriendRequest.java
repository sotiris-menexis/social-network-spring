package com.sotosmen.socialnetwork.amqp.friendrequest;

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
public class RabbitMQConfigurationFriendRequest {
	@Value("${friendrequest.rabbitmq.exchange}")
	private String friendRequestExchange;
	@Value("${friendrequest.rabbitmq.queuename.post}")
	private String friendRequestQueueNamePost;
	@Value("${friendrequest.rabbitmq.queuename.put}")
	private String friendRequestQueueNamePut;
	@Value("${friendrequest.rabbitmq.queuename.delete}")
	private String friendRequestQueueNameDelete;
	
	@Bean(name="friendRequestQueuePost")
	Queue queuePost() {
		return new Queue(friendRequestQueueNamePost,false);
	}
	@Bean(name="friendRequestQueuePut")
	Queue queuePut() {
		return new Queue(friendRequestQueueNamePut,false);
	}
	@Bean(name="friendRequestQueueDelete")
	Queue queueDelete() {
		return new Queue(friendRequestQueueNameDelete,false);
	}
	@Bean(name="directExchangeFriendRequest")
	DirectExchange directExchangeFriendRequest() {
		return new DirectExchange(friendRequestExchange);
	}
	@Bean(name="bindingPostFriendRequest")
	Binding bindingFriendRequestPost(@Qualifier("friendRequestQueuePost") Queue queue
						   ,@Qualifier("directExchangeFriendRequest")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingPutFriendRequest")
	Binding bindingFriendRequestPut(@Qualifier("friendRequestQueuePut") Queue queue
						  ,@Qualifier("directExchangeFriendRequest")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingDeleteFriendRequest")
	Binding bindingFriendRequestDelete(@Qualifier("friendRequestQueueDelete") Queue queue
							 ,@Qualifier("directExchangeFriendRequest")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean
	public MessageConverter messageConverterFriendRequest() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean(name="rabbitTemplateFriendRequest")
	public RabbitTemplate rabbitTemplateFriendRequest(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverterFriendRequest());
		return rabbitTemplate;
	}
}
