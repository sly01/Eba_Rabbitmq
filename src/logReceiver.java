import java.io.IOException;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;


public class logReceiver {
	
	private static String exchange_name = "logs1";
	
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		
		channel.exchangeDeclare(exchange_name, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, exchange_name, "");
		
		System.out.println("Mesajlar bekleniyor.....");
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName,true ,consumer);
		while(true){
			
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String gelenMesaj = new String(delivery.getBody());
			System.out.println("Alinan Mesajlar--- "+gelenMesaj+" "+new Date());
		}
		
	}

}
