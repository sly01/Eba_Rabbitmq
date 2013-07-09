package com.rabbitmqobject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Kisi implements Serializable {

	public String isim;
	public String soyisim;
	public String sehir;

	public Kisi() {
		// TODO Auto-generated constructor stub
	}

	public Kisi(String isim, String soyisim, String sehir) {
		super();
		this.isim = isim;
		this.soyisim = soyisim;
		this.sehir = sehir;
	}

	public String getIsim() {
		return isim;
	}

	public void setIsim(String isim) {
		this.isim = isim;
	}

	public String getSoyisim() {
		return soyisim;
	}

	public void setSoyisim(String soyisim) {
		this.soyisim = soyisim;
	}

	public String getSehir() {
		return sehir;
	}

	public void setSehir(String sehir) {
		this.sehir = sehir;
	}

	@Override
	public String toString() {
		
		return "Isim:"+this.isim+" "+"Soyisim:"+this.soyisim+" "+"Sehir:"+this.sehir;
	}
	public  byte[] byteCevir() {
		byte[] bytes = null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.flush();
			oos.reset();
			// Burayi anlamadim
			bytes = baos.toByteArray();
			oos.close();
			baos.close();
		} catch (IOException e) {
			System.out.println("Byte yazmada hata yaptik ya laa!");
			e.printStackTrace();
		}

		return bytes;
	}
	public static Kisi fromBytes(byte[] body) {
        Kisi obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(body);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = (Kisi) ois.readObject();
            ois.close();
            bis.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
}


