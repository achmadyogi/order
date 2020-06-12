package dana.order.usecase.broadcast;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class VoucherListener {

    @RabbitListener(queues = "${spring.rabbitmq.queue.listener}",containerFactory = "createListener")
    public void recieveMessage(String aan)
    {
        System.out.println(aan);
    }

}
