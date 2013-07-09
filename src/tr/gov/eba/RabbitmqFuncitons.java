package tr.gov.eba;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JOptionPane;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmqobject.Kisi;

public class RabbitmqFuncitons {

	public static String QUEUE_NAME = "test";
	public static Connection connection;
	public static Channel channel;
	public static QueueingConsumer consumer;

	public void CreateConnection() throws IOException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("guest");
		factory.setPassword("1234");
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		consumer = new QueueingConsumer(channel);

	}

	public void SendData(Object obj) throws IOException {

		// channel.queueDelete(QUEUE_NAME);
		// channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		channel.basicPublish("", QUEUE_NAME, null, byteCevir(obj));

		System.out.println("Mesaj is sended " + new Date());

	}

	public Object GetData() throws IOException, ShutdownSignalException,
			ConsumerCancelledException, InterruptedException {

		// channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		System.out.println("Mesajlar bekleniyor....");

		// QueueingConsumer consumer = new QueueingConsumer(channel);

		channel.basicConsume(QUEUE_NAME, false, consumer);
		while (true) {
			Object obj = null;
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			obj = fromBytes(delivery.getBody());
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			return obj;
		}

	}

	public void writeToData(tr.gov.eba.Kisi kisi) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/EbaTaskWatcher","root","sly6465");
		    String sorgu="insert into tasks(name) values(?)";
		    PreparedStatement prstm = conn.prepareStatement(sorgu);
		    prstm.setString(1,kisi.getIsim());
		    prstm.executeUpdate();
		  
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			  JOptionPane.showMessageDialog(null, "Database Yazim Basarili");
		}

	}

	

	public void CloseConnection() throws IOException {
		channel.close();
		connection.close();

	}

	public byte[] byteCevir(Object obj) {
		byte[] bytes = null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			oos.reset();
			bytes = baos.toByteArray();
			oos.close();
			baos.close();
		} catch (IOException e) {
			System.out.println("Byte yazmada hata yaptik ya laa!");
			e.printStackTrace();
		}

		return bytes;
	}

	public Object fromBytes(byte[] body) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(body);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

}
