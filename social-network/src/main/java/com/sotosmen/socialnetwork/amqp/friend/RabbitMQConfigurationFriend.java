package com.sotosmen.socialnetwork.amqp.friend;

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
public class RabbitMQConfigurationFriend {
	@Value("${friend.rabbitmq.exchange}")
	private String friendExchange;
	@Value("${friend.rabbitmq.queuename.post}")
	private String friendQueueNamePost;
	@Value("${friend.rabbitmq.queuename.put}")
	private String friendQueueNamePut;
	@Value("${friend.rabbitmq.queuename.delete}")
	private String friendQueueNameDelete;
	
	@Bean(name="friendQueuePost")
	Queue queuePost() {
		return new Queue(friendQueueNamePost,false);
	}
	@Bean(name="friendQueuePut")
	Queue queuePut() {
		return new Queue(friendQueueNamePut,false);
	}
	@Bean(name="friendQueueDelete")
	Queue queueDelete() {
		return new Queue(friendQueueNameDelete,false);
	}
	@Bean(name="directExchangeFriend")
	DirectExchange directExchangeFriend() {
		return new DirectExchange(friendExchange);
	}
	@Bean(name="bindingPostFriend")
	Binding bindingFriendPost(@Qualifier("friendQueuePost") Queue queue
						   ,@Qualifier("directExchangeFriend")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingPutFriend")
	Binding bindingFriendPut(@Qualifier("friendQueuePut") Queue queue
						  ,@Qualifier("directExchangeFriend")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingDeleteFriend")
	Binding bindingFriendDelete(@Qualifier("friendQueueDelete") Queue queue
							 ,@Qualifier("directExchangeFriend")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean
	public MessageConverter messageConverterFriend() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean(name="rabbitTemplateFriend")
	public RabbitTemplate rabbitTemplateFriend(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverterFriend());
		return rabbitTemplate;
	}
}
