package com.sotosmen.socialnetwork.amqp.vote;

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
public class RabbitMQConfigurationVote {
	@Value("${vote.rabbitmq.exchange}")
	private String voteExchange;
	@Value("${vote.rabbitmq.queuename.post}")
	private String voteQueueNamePost;
	@Value("${vote.rabbitmq.queuename.put}")
	private String voteQueueNamePut;
	@Value("${vote.rabbitmq.queuename.delete}")
	private String voteQueueNameDelete;
	
	@Bean(name="voteQueuePost")
	Queue queuePost() {
		return new Queue(voteQueueNamePost,false);
	}
	@Bean(name="voteQueuePut")
	Queue queuePut() {
		return new Queue(voteQueueNamePut,false);
	}
	@Bean(name="voteQueueDelete")
	Queue queueDelete() {
		return new Queue(voteQueueNameDelete,false);
	}
	@Bean(name="directExchangeVote")
	DirectExchange directExchangeVote() {
		return new DirectExchange(voteExchange);
	}
	@Bean(name="bindingPostVote")
	Binding bindingPostVote(@Qualifier("voteQueuePost") Queue queue
							 ,@Qualifier("directExchangeVote") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingPutVote")
	Binding bindingPutVote(@Qualifier("voteQueuePut") Queue queue
							,@Qualifier("directExchangeVote") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean(name="bindingDeleteVote")
	Binding bindingDeleteVote(@Qualifier("voteQueueDelete") Queue queue
							   ,@Qualifier("directExchangeVote") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
	}
	@Bean
	public MessageConverter messageConverterVote() {
		return new Jackson2JsonMessageConverter();
	}
	@Bean(name="rabbitTemplateVote")
	public RabbitTemplate rabbitTemplateVote(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverterVote());
		return rabbitTemplate;
	}
}
