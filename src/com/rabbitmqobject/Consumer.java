package com.rabbitmqobject;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Consumer {

	private static String QUEUE_NAME="test";
	
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		
		System.out.println("Mesajlar bekleniyor....");
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME,true,consumer);
		
		while (true) {
			Kisi k=null;
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			k.fromBytes(delivery.getBody());
			System.out.println("Alinan mesaj" + k.toString());
			
		}
		
		
	}
	
	
}
