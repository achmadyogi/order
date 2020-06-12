package dana.order.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BroadcastingConfig {

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.virtualHost}")
    private String virtualHost;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Bean
    ConnectionFactory connectionFactory()
    {
        AbstractConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPort(port);
        connectionFactory.setPassword(password);
        connectionFactory.setUsername(username);
        return connectionFactory;
    }

    @Bean
    MessageConverter messageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    SimpleRabbitListenerContainerFactory createListener(ConnectionFactory connectionFactory)
    {
        SimpleRabbitListenerContainerFactory containerFactory =
                new SimpleRabbitListenerContainerFactory();

        containerFactory.setConnectionFactory(connectionFactory);

        containerFactory.setMaxConcurrentConsumers(10);

        containerFactory.setConcurrentConsumers(5);
        containerFactory.setAutoStartup(true);
        containerFactory.setMessageConverter(messageConverter());
        containerFactory.setPrefetchCount(1);
        containerFactory.setDefaultRequeueRejected(false);
        return containerFactory;
    }

    @Bean
    public FanoutExchange fanout(){
        return new FanoutExchange("deals.order.yes1sfe94hksjf");
    }

}
