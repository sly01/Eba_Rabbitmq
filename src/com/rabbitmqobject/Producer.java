package com.rabbitmqobject;

import java.io.IOException;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public static String QUEUE_NAME = "test";
	
	public static void main(String[] args) throws IOException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		Kisi kisi1 = new Kisi("Ahmet", "Erkoc", "Adana");
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.basicPublish("",QUEUE_NAME, null,kisi1.byteCevir());
		
		System.out.println("Mesaj is sended "+new Date());
		
		channel.close();
		connection.close();
		
	}
	
}
