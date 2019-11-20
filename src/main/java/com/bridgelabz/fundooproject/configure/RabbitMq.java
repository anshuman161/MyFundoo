package com.bridgelabz.fundooproject.configure;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMq {

@Value("${rabbitmq.exchange}")
   private String EXCHANGE_NAME;

@Value("${rabbitmq.queue}")
public String  queueName;

@Value("${rabbitmq.routingkey}")
   public String ROUTING_KEY;;
   
   @Bean
   public TopicExchange tipsExchange() {
       return new TopicExchange(EXCHANGE_NAME);
   }

   @Bean
   public Queue defaultParsingQueue() {
       return new Queue(queueName, true);
   }

   @Bean
   public Binding queueToExchangeBinding() {
       return BindingBuilder.bind(defaultParsingQueue()).to(tipsExchange()).with(ROUTING_KEY);
   }

   @Bean
   public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
       RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
       return rabbitTemplate;
   }

}
