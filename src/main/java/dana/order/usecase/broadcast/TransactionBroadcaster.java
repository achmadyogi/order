package dana.order.usecase.broadcast;

import dana.order.entity.Transaction;
import dana.order.usecase.port.DatabaseMapper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionBroadcaster {
/*
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    FanoutExchange fanoutExchange;

    @Autowired
    DatabaseMapper databaseMapper;

 */

    public void send(Integer idTransaction){
        /*
        Transaction transaction = databaseMapper.getTransactionById(idTransaction);
        System.out.println(transaction.toJsonString());
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", transaction);

         */
    }
}
