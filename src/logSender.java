import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.filefilter.AgeFileFilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class logSender {
	private static final String exchange_name="logs1";
	
	public static void main(String[] args) throws IOException {
		
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(exchange_name,"fanout");
		
		Scanner sc = new Scanner(System.in);
		String strMesaj = sc.nextLine();
		String[] arrMesaj =new String[] {strMesaj};
		String mesaj = mesajAl(arrMesaj);
		
		channel.basicPublish(exchange_name,"",null,mesaj.getBytes());
		System.out.println("*'"+mesaj+"'");
		
		channel.close();
		connection.close();
	}
	
	private static String mesajAl(String[] string){
		if(string.length<1){
			return "Patladin!!!";
		}
		return stringAyir(string,"-");
	}

	private static String stringAyir(String[] string,String ayrac){
		int uzunluk = string.length;
		if(uzunluk==0)
			return "";
		StringBuilder harf = new StringBuilder(string[0]);
		for(int i=1;i<uzunluk;i++){
			harf.append(ayrac).append(string[i]);
		}
			
		return harf.toString();
	}
	
}
