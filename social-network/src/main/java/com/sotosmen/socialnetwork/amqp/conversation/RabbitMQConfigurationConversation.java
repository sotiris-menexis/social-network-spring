package com.sotosmen.socialnetwork.amqp.conversation;

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
public class RabbitMQConfigurationConversation {
	@Value("${conversation.rabbitmq.exchange}")
	private String conversationExchange;
	@Value("${conversation.rabbitmq.queuename.post}")
	private String conversationQueueNamePost;
	@Value("${conversation.rabbitmq.queuename.put}")
	private String conversationQueueNamePut;
	@Value("${conversation.rabbitmq.queuename.delete}")
	private String conversationQueueNameDelete;
	
	@Bean(name="conversationQueuePost")
	Queue queuePost() {
		return new Queue(conversationQueueNamePost,false);
	}
	@Bean(name="conversationQueuePut")
	Queue queuePut() {
		return new Queue(conversationQueueNamePut,false);
	}
	@Bean(name="conversationQueueDelete")
	Queue queueDelete() {
		return new Queue(conversationQueueNameDelete,false);
	}
	@Bean(name="directExchangeConversation")
	DirectExchange directExchangeConversation() {
		return new DirectExchange(conversationExchange);
	}
	@Bean(name="bindingPostConversation")
	Binding bindingConversationPost(@Qualifier("conversationQueuePost") Queue queue
						   ,@Qualifier("directExchangeConversation")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingPutConversation")
	Binding bindingConversationPut(@Qualifier("conversationQueuePut") Queue queue
						  ,@Qualifier("directExchangeConversation")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingDeleteConversation")
	Binding bindingConversationDelete(@Qualifier("conversationQueueDelete") Queue queue
							 ,@Qualifier("directExchangeConversation")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean
	public MessageConverter messageConverterConversation() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean(name="rabbitTemplateConversation")
	public RabbitTemplate rabbitTemplateConversation(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverterConversation());
		return rabbitTemplate;
	}
}
