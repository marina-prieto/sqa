package dissw24.sqa;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.util.ArrayList;

//latest version, testing
public class PruebaSerial {

	public static void main(String[] args) throws Exception {
		Persona pepe = new Persona("Pepe", "Perez");
		Persona ana = new Persona("Ana", "Lopez");
		
		ArrayList<Persona> personas = new ArrayList<>();
		personas.add(pepe);
		personas.add(ana);
		
		FileOutputStream out = new FileOutputStream("/Users/thala/Desktop/pepe.obj");
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(pepe);
		out.close();
		
		out = new FileOutputStream("/Users/thala/Desktop/ana.obj");
		oos = new ObjectOutputStream(out);
		oos.writeObject(ana);
		out.close();
		
		out = new FileOutputStream("/Users/thala/Desktop/personas.obj");
		oos = new ObjectOutputStream(out);
		oos.writeObject(personas);
		out.close();
	}
}

class Persona implements Serializable{
	
	private String n;
	private String a;
	
	public Persona(String n, String a) {
		this.n = n;
		this.a = a;
	}
}