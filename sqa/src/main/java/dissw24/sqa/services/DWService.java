package dissw24.sqa.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public class DWService {

	//latest version
	public String generarCodigo(int[][] matriz) throws Exception{
		String template = this.read("dwaveTemplate.txt");
		
		StringBuilder sb = new StringBuilder("x = [");
		for (int i = 0; i < matriz.length; i++) {
			sb.append("\"x" + i + "\", ");
		}
		sb.append("]\n");
		String lineaX = sb.toString();
		
		int posRules = template.indexOf("#RULES#");
		String inicio = template.substring(0, posRules);
		
		String fin = template.substring(posRules);
		
		//sustituir los puntos suspensivos
		String inicializacionMatriz = "...";
		String code = inicio + lineaX + inicializacionMatriz + fin;
		return code;
	}
	
	//latest version
	public String read(String fileName) throws FileNotFoundException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		try (InputStream fis = classLoader.getResourceAsStream(fileName)) {
			byte[] b = new byte[fis.available()];
			fis.read(b);
			String s = new String(b);
			return s;
		}
	}
	
	//latest version
	public String generarCodigo(String codigo) {
		//TODO
		return "Este es el código";
	}
}
