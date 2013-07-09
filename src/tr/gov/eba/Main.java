package tr.gov.eba;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException,
			InterruptedException {

		RabbitmqFuncitons test1 = new RabbitmqFuncitons();
		test1.CreateConnection();

		
		Kisi kisi1=new Kisi("Ahmet", "Erkoc", "Adana");
		Kisi kisi2=new Kisi("Fatih", "Ersinadim", "Istanbul");
		Kisi kisi3=new Kisi("Talha","Ozcan","Angara");
		Kisi kisi4=new Kisi("Fehmi","Erkoc","Tekir");
		Kisi kisi5=new Kisi("Mehmet","Hamsi","Trabzon");
		Kisi kisi6=new Kisi("Dursun","Laz","Rize");
		
		//Kisi[] b = new Kisi[6];
		
		Kisi b = new Kisi();
		
		test1.SendData(kisi1);
		test1.SendData(kisi2);
		test1.SendData(kisi3);
		test1.SendData(kisi4);
		test1.SendData(kisi5);
		test1.SendData(kisi6);
		
		//Thread.sleep(5000);
		
		for(int i=0;i<6;i++){
		b=(Kisi)test1.GetData();
		System.out.println(b.toString());
		//b[i]=(Kisi)test1.GetData();
		//test1.writeToData(b[i]);
		//System.out.println(b[i].toString());
		}
		
		
		//System.out.println(kisi3.toString());
		/*for (int i = 0; i < 5; i++) {

			test1.SendData(kisi[i]);

		}

		for (int i = 0; i < 5; i++) {
			b[i] =(Kisi)test1.GetData(kisi[i]);
			System.out.println(b[i].toString());
		}*/
		test1.CloseConnection();

	}

}
