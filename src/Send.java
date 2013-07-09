import java.io.IOException;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class Send {
	private final static String QUEUE_NAME = "test";

	public static void main(String[] args) throws IOException,
			ShutdownSignalException, ConsumerCancelledException,
			InterruptedException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		String message1 = "Deneme 1";
		String message2 = "Deneme 2";

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		channel.basicPublish("", QUEUE_NAME, null, message1.getBytes());
		System.out.println(" [x] Gonderildi '" + message1 + "'" + new Date());

		channel.basicPublish("", QUEUE_NAME, null, message2.getBytes());
		System.out.println(" [x] Gonderildi '" + message2 + "'" + new Date());

		channel.close();
		connection.close();

	}

}
